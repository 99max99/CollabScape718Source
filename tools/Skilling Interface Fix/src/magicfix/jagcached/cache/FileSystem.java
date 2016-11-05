package magicfix.jagcached.cache;

import magicfix.jagcached.Helper;
import magicfix.utilities.ByteBuffer;
import magicfix.utilities.Whirlpool;

public class FileSystem {

	/**
	 * Underlying cache.
	 */
	private Cache cache;
	/**
	 * Contains ID of the file system.
	 */
	private int id;
	/**
	 * Wheter to use automatic version incrementor.
	 */
	private boolean useAutomaticVersionIncremetor = true;
	
	
	/**
	 * Contains all folders.
	 */
	private Folder[] folders;
	
	/**
	 * Wheter whirlpool hashes are used.
	 */
	private boolean useWhirlpool;
	/**
	 * Wheter names are used.
	 */
	private boolean useNames;
	
	
	/**
	 * Version of this file system.
	 */
	private int version;
	/**
	 * CRC32 of this file system.
	 */
	private int crc;
	/**
	 * 64byte whirlpool digest 
	 * of this file system.
	 */
	private byte[] digest;
	
	
	/**
	 * Contains packed filesystem file
	 * which was provided with load() call.
	 */
	private ByteBuffer packed;
	/**
	 * Wheter any changes were made to 
	 * file system properties such as version or properties.
	 */
	private boolean changed;
	
	
	public FileSystem(int id, Cache cache) {
		this.id = id;
		this.cache = cache;
	}
	
	
	/**
	 * Tries to load this file system from cache.
	 */
	public void load() {
		if (isLoaded())
			throw new RuntimeException("Already loaded!");
		ByteBuffer buffer = cache.getInformationStore().get(id);
		if (buffer == null)
			throw new RuntimeException("Missing filesystem file.");
		
		ByteBuffer unpacked = Helper.decodeFITContainer(buffer);
		
		unpacked.setPosition(0);
		
		int protocol = unpacked.readUByte();
		if (protocol >= 6)
			version = unpacked.readInt();
		
		int properties = unpacked.readUByte();
		useNames     = (properties & 0x1) != 0;
		useWhirlpool = (properties & 0x2) != 0;
		
		folders = new Folder[protocol >= 7 ? unpacked.readSmart32() : unpacked.readUnsignedShort()];
		
		int[]    folderIDS        = new int [folders.length];
		int[]    folderNames      = new int [folders.length];
		int[]    folderCRCS       = new int [folders.length];
		byte[][] folderDigests    = new byte[folders.length][64];
		int[]    folderVersions   = new int [folders.length];
		int[][]  folderFilesIDS   = new int [folders.length][];
		int[][]  folderFilesNames = new int [folders.length][];
		
		for (int offset = 0, i = 0; i < folders.length; i++)
			folderIDS[i] = offset += (protocol >= 7 ? unpacked.readSmart32() : unpacked.readUnsignedShort());
		
		for (int i = 0; i < folders.length; i++) 
			folderNames[i] = useNames ? unpacked.readInt() : -1;
		
		for (int i = 0; i < folders.length; i++)
			folderCRCS[i] = unpacked.readInt();
		
		if (useWhirlpool)
			for (int i = 0; i < folders.length; i++)
				unpacked.readBytes(folderDigests[i], 0, 64);
		
		for (int i = 0; i < folders.length; i++)
			folderVersions[i] = unpacked.readInt();
		
		for (int i = 0; i < folders.length; i++) {
			int filesCount = protocol >= 7 ? unpacked.readSmart32() : unpacked.readUnsignedShort();
			folderFilesIDS[i] = new int[filesCount];
			folderFilesNames[i] = new int[filesCount];
		}
		
		for (int i = 0; i < folders.length; i++)
			for (int offset = 0, a = 0; a < folderFilesIDS[i].length; a++)
				folderFilesIDS[i][a] = offset += (protocol >= 7 ? unpacked.readSmart32() : unpacked.readUnsignedShort());
	
		for (int i = 0; i < folders.length; i++)
			for (int a = 0; a < folderFilesIDS[i].length; a++)
				folderFilesNames[i][a] = useNames ? unpacked.readInt() : -1;
				
		for (int i = 0; i < folders.length; i++)
			folders[i] = new Folder(folderIDS[i],folderNames[i],folderVersions[i],folderCRCS[i],folderDigests[i],folderFilesIDS[i],folderFilesNames[i]);
		
		updateHashes(buffer);
		
		packed = buffer;
	}
	
	/**
	 * Delete's all folders on this filesystem.
	 * Reset's version to 0.
	 */
	public void reset() {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		changed = true;
		folders = new Folder[0];
		version = 0;
	}
	

	/**
	 * Finishe's any changes to this filesystem.
	 */
	public void finish() {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		if (!needsRepack())
			return;
		
		for (int i = 0; i < folders.length; i++) {
			if (folders[i].isFileSystemInfoChanged()) {
				if (useAutomaticVersionIncremetor)
					folders[i].setVersion(folders[i].getVersion() + 1);
				ByteBuffer buffer = folders[i].finish();
				if (!cache.getFilesStore(id).put(folders[i].getID(), buffer, buffer.getBuffer().length))
					throw new RuntimeException("Couldn't update folder:" + folders[i]);
			}
		}
		
		if (useAutomaticVersionIncremetor)
			version++;
		
		packed = pack();
		updateHashes(packed);
		
		if (!cache.getInformationStore().put(id, packed, packed.getBuffer().length))
			throw new RuntimeException("Couldn't update packed filesystem.");
		
		changed = false;
		for (int i = 0; i < folders.length; i++)
			folders[i].markFileSystemInfoAsNotChanged();
		
		
	}
	
	/**
	 * Pack's this filesystem.
	 */
	private ByteBuffer pack() {
		
		int protocol = decideProtocol();
		ByteBuffer pack = new ByteBuffer(calculatePackedAllocSize(protocol));
		
		pack.writeByte(protocol);
		if (protocol >= 6)
			pack.writeInt(version);
		
		pack.writeByte((useNames ? 0x1 : 0x0) | (useWhirlpool ? 0x2 : 0x0));
		
		if (protocol >= 7)
			pack.writeSmart32(folders.length);
		else
			pack.writeShort(folders.length);
		
		for (int delta = 0, i = 0; i < folders.length; i++) {
			if (protocol >= 7)
				pack.writeSmart32(folders[i].getID() - delta);
			else
				pack.writeShort(folders[i].getID() - delta);
			delta = folders[i].getID();
		}
		
		if (useNames)
			for (int i = 0; i < folders.length; i++)
				pack.writeInt(folders[i].getName());
		
		for (int i = 0; i < folders.length; i++)
			pack.writeInt(folders[i].getCRC32());
		
		if (useWhirlpool)
			for (int i = 0; i < folders.length; i++)
				pack.writeBytes(folders[i].getDigest(), 0, 64);
		
		for (int i = 0; i < folders.length; i++)
			pack.writeInt(folders[i].getVersion());
		
		for (int i = 0; i < folders.length; i++)
			if (protocol >= 7)
				pack.writeSmart32(folders[i].filesCount());
			else
				pack.writeShort(folders[i].filesCount());
				
		for (int i = 0; i < folders.length; i++) {
			for (int delta = 0, a = 0; a < folders[i].filesCount(); a++) {
				if (protocol >= 7)
					pack.writeSmart32(folders[i].getFiles()[a].getID() - delta);
				else
					pack.writeShort(folders[i].getFiles()[a].getID() - delta);
				delta = folders[i].getFiles()[a].getID();
			}
		}
		
		if (useNames)
			for (int i = 0; i < folders.length; i++)
				for (int a = 0; a < folders[i].filesCount(); a++)
					pack.writeInt(folders[i].getFiles()[a].getName());
		
		return Helper.encodeFITContainer(pack, version);
	}
	
	/**
	 * Calculate's new packed filesystem size.
	 */
	private int calculatePackedAllocSize(int protocol) {
		int size = 2;
		if (protocol >= 6)
			size += 4;
		
		size += protocol >= 7 ? (folders.length > 0x7FFF ? 4 : 2) : 2;
		for (int delta = 0, i = 0; i < folders.length; i++) {
			if (protocol >= 7 && (folders[i].getID() - delta) > 0x7FFF)
				size += 4;
			else
				size += 2;
			delta = folders[i].getID();
		}
		
		if (useNames)
			size += folders.length * 4; // names
		size += folders.length * 4; // crc's
		if (useWhirlpool)
			size += folders.length * 64; // whirlpool
		size += folders.length * 4; // versions
		
		for (int i = 0; i < folders.length; i++)
			size += protocol >= 7 ? (folders[i].filesCount() > 0x7FFF ? 4 : 2) : 2;
			
		for (int i = 0; i < folders.length; i++) {
			for (int delta = 0, a = 0; a < folders[i].filesCount(); a++) {
				if (protocol >= 7 && (folders[i].getFiles()[a].getID() - delta) > 0x7FFF)
					size += 4;
				else
					size += 2;
				delta = folders[i].getFiles()[a].getID();
			}
		}
		
		for (int i = 0; i < folders.length; i++)
			for (int a = 0; a < folders[i].filesCount(); a++)
				size += 4;
		return size;
	}
	
	/**
	 * Decide's protocol ID to be used when packing 
	 * this filesystem.
	 */
	private int decideProtocol() {
		if (folders.length > 0xFFFF)
			return 7;
		for (int delta = 0, i = 0; i < folders.length; i++) {
			if ((folders[i].getID() - delta) > 0xFFFF)
				return 7;
			delta = folders[i].getID();
		}
		
		for (int i = 0; i < folders.length; i++) {
			if (folders[i].filesCount() > 0xFFFF)
				return 7;
			for (int delta = 0, a = 0; a < folders[i].filesCount(); a++) {
				if ((folders[i].getFiles()[a].getID() - delta) > 0xFFFF)
					return 7;
				delta = folders[i].getFiles()[a].getID();
			}
		}
		
		return version != 0 ? 6 : 5;
	}
	
	
	/**
	 * Find's folder by id.
	 * Returns null if none found.
	 */
	public Folder findFolderByID(int id) {
		return findFolderByID(id,null);
	}
	
	/**
	 * Find's folder by id.
	 * Returns null if none found.
	 */
	public Folder findFolderByID(int id,int[] xtea) {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		for (int i = 0; i < folders.length; i++)
			if (folders[i].getID() == id) {
				Folder folder = folders[i];
				if (folder.isLoaded())
					return folder;
				ByteBuffer data = cache.getFilesStore(this.id).get(id);
				if (data == null)
					throw new RuntimeException("Missing folder:" + id);
				folder.load(data, xtea);
				return folder;
			}
		return null;
	}
	
	/**
	 * Find's folder by name.
	 * Returns null if none found.
	 */
	public Folder findFolderByName(String name) {
		return findFolderByName(name,null);
	}
	
	/**
	 * Find's folder by name.
	 * Returns null if none found.
	 */
	public Folder findFolderByName(int name) {
		return findFolderByName(name,null);
	}
	
	/**
	 * Find's folder by name.
	 * Returns null if none found.
	 */
	public Folder findFolderByName(String name,int[] xtea) {
		return findFolderByName(Helper.strToI(name), xtea);
	}
	
	/**
	 * Find's folder by name.
	 * Returns null if none found.
	 */
	public Folder findFolderByName(int name, int[] xtea) {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		if (name == -1)
			return null;
		for (int i = 0; i < folders.length; i++)
			if (folders[i].getName() == name) {
				Folder folder = folders[i];
				if (folder.isLoaded())
					return folder;
				ByteBuffer data = cache.getFilesStore(this.id).get(folder.getID());
				if (data == null)
					throw new RuntimeException("Missing folder:" + folder.getID());
				folder.load(data, xtea);
				return folder;
			}
		return null;
	}
	
	/**
	 * Add's new folder to this filesystem.
	 * If there's already a folder with same id then 
	 * it get's overwriten.
	 */
	public void addFolder(Folder folder) {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		if (!folder.isLoaded())
			throw new RuntimeException("folder is not loaded.");
		if (findFolderIndex(folder) != -1)
			return;
		if (folder.getID() == -1)
			folder.setID(getFreeFolderID());
		int index = -1;
		for (int i = 0; i < folders.length; i++)
			if (folders[i].getID() == folder.getID()) {
				index = i;
				break;
			}
		if (index == -1) {
			Folder[] newFolders = new Folder[folders.length + 1];
			System.arraycopy(folders, 0, newFolders, 0, folders.length);
			index = folders.length;
			folders = newFolders;
		}
		folders[index] = folder;
		folder.markFileSystemInfoAsChanged(); // cause it needs to be packed to store.
		changed = true;
	}
	
	/**
	 * Delete's given folder.
	 */
	public void deleteFolder(Folder folder) {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		int index = findFolderIndex(folder);
		if (index == -1)
			return;
		Folder[] newFolders = new Folder[folders.length - 1];
		for (int write = 0,i = 0; i < folders.length; i++)
			if (folders[i] != folder)
				newFolders[write++] = folders[i];
		folders = newFolders;
		changed = true;
	}
	
	/**
	 * Delete's all folders in this fs.
	 */
	public void deleteAllFolders() {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		folders = new Folder[0];
		changed = true;
	}
	
	/**
	 * Load's folder if it's not yet loaded.
	 */
	public void load(Folder folder) {
		load(folder,null);
	}
	
	/**
	 * Load's folder if it's not yet loaded.
	 */
	public void load(Folder folder,int[] xtea) {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		int index = findFolderIndex(folder);
		if (index == -1)
			return;
		if (folder.isLoaded())
			return;
		ByteBuffer data = cache.getFilesStore(id).get(folder.getID());
		if (data == null)
			throw new RuntimeException("Missing folder:" + folder.getID());
		folder.load(data, xtea);
	}
	
	/**
	 * Finishe's any pending caches on this filesystem
	 * and then unload's some buffered files.
	 */
	public void unloadCachedFiles() {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		finish();
		for (int i = 0; i < folders.length; i++)
			if (folders[i].isLoaded())
				folders[i].unload();
	}
	
	/**
	 * Get's free folder ID.
	 */
	private int getFreeFolderID() {
		if (folders.length == 0)
			return 0;
		int highest = -1;
		for (int i = 0; i < folders.length; i++)
			if (folders[i].getID() > highest)
				highest = folders[i].getID();
		return highest + 1;
	}
	
	/**
	 * Finds folder index.
	 */
	private int findFolderIndex(Folder folder) {
		for (int i = 0; i < folders.length; i++)
			if (folders[i] == folder)
				return i;
		return -1;
	}
	
	
	/**
	 * Update's crc32 and whirlpool hash to one's from 
	 * given buffer.
	 */
	private void updateHashes(ByteBuffer packed) {
		crc = Helper.crc32(packed,0,packed.getBuffer().length);
		digest = Whirlpool.whirlpool(packed.getBuffer(), 0, packed.getBuffer().length);
	}
	
	
	/**
	 * Wheter this filesystem is loaded.
	 */
	public boolean isLoaded() {
		return packed != null;
	}
	
	/**
	 * Wheter file system file needs repack.
	 */
	private boolean needsRepack() {
		if (changed)
			return true;
		for (int i = 0; i < folders.length; i++)
			if (folders[i].isFileSystemInfoChanged())
				return true;
		return false;
	}
	
	/**
	 * Get's ID of this filesystem.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Wheter this filesystem uses names.
	 */
	public boolean usesNames() {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		return useNames;
	}
	
	/**
	 * Wheter this filesystem uses whirlpool.
	 */
	public boolean usesWhirlpool() {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		return useWhirlpool;
	}
	
	/**
	 * Set's wheter this filesystem uses names.
	 */
	public void setUsesNames(boolean uses) {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		if (this.useNames != uses) {
			changed = true;
			this.useNames = uses;
		}
	}
	
	/**
	 * Set's wheter this filesystem uses whirlpool.
	 */
	public void setUsesWhirlpool(boolean uses) {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		if (this.useWhirlpool != uses) {
			changed = true;
			this.useWhirlpool = uses;
		}
	}
	
	/**
	 * Get's version of this filesystem.
	 */
	public int getVersion() {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		return version;
	}
	
	/**
	 * Set's version of this filesystem.
	 */
	public void setVersion(int version) {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		if (this.version != version) {
			changed = true;
			this.version = version;
		}
	}
	
	/**
	 * Get's crc32 of packed version of this filesystem.
	 */
	public int getCRC32() {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		return crc;
	}
	
	/**
	 * Get's whirlpool digest of packed version of this filesystem.
	 */
	public byte[] getDigest() {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		return digest;
	}
	
	/**
	 * Wheter versions are automatically incremented
	 * each time finish() is called.
	 */
	public boolean usingAutomaticVersionsIncremetor() {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		return useAutomaticVersionIncremetor;
	}


	/**
	 * Set's wheter to use automatic versions incrementor.
	 */
	public void setUseAutomaticVersionsIncremetor(boolean use) {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded filesystem.");
		this.useAutomaticVersionIncremetor = use;
	}

	/**
	 * Get's all folders.
	 * Returned folders array can't be modified
	 * in any way.
	 */
	public Folder[] getFolders() {
		return folders;
	}

}
