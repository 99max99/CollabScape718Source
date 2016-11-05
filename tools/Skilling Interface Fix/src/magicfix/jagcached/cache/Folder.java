package magicfix.jagcached.cache;

import magicfix.jagcached.Helper;
import magicfix.utilities.ByteBuffer;
import magicfix.utilities.Whirlpool;

public class Folder {

	/**
	 * Contains ID of the folder.
	 */
	private int id;
	/**
	 * Contains name hash of this folder.
	 */
	private int name;
	/**
	 * Contains version of packed folder file.
	 */
	private int version;
	
	/**
	 * Contains crc of packed folder file.
	 */
	private int crc;
	/**
	 * Contains 64-byte whirlpool digest. 
	 */
	private byte[] digest;
	
	
	/**
	 * Contains all files in this folder.
	 */
	private File[] files;
	

	
	/**
	 * Contains packed files buffer 
	 * which was provided with load() call.
	 */
	private ByteBuffer packedFiles;
	/**
	 * Contains xtea keys. (int[4])
	 * Can be null.
	 */
	private int[] xtea;
	/**
	 * Wheter any changes were made.
	 */
	private boolean needsRepack;
	/**
	 * Wheter any of those things changed (id,name,version,crc,digest,files and their data).
	 */
	private boolean fileSystemInfoChanged;
	
	/**
	 * Constructor for copy() methods.
	 */
	private Folder() {
		
	}
	
	/**
	 * Create's new folder with autoassigned id from given data.
	 * Each file must have it's buffer attached.
	 */
	public Folder(File[] files) {
		this(-1,-1,0,files);
	}
	
	/**
	 * Create's new folder with autoassigned id from given data.
	 * Each file must have it's buffer attached.
	 */
	public Folder(String name, int version, File[] files) {
		this(-1,Helper.strToI(name),version,files);
	}
	
	/**
	 * Create's new folder with autoassigned id from given data.
	 * Each file must have it's buffer attached.
	 */
	public Folder(int name, int version, File[] files) {
		this(-1,name,version,files);
	}
	
	/**
	 * Create's new folder from given data.
	 * Each file must have it's buffer attached.
	 */
	public Folder(int folderID, String name, int version, File[] files) {
		this(folderID,Helper.strToI(name),version,files);
	}
	
	/**
	 * Create's new folder from given data.
	 * Each file must have it's buffer attached.
	 */
	public Folder(int folderID, int name, int version, File[] files) {
		for (int i = 0; i < files.length; i++)
			if (!files[i].isLoaded())
				throw new RuntimeException("Nonloaded file.");
		
		this.id = folderID;
		this.name = name;
		this.version = version;
		this.files = files;
		for (int i = 0; i < files.length; i++)
			if (files[i].getID() == -1)
				files[i].setID(getFreeFileID());
		updateHashes(this.packedFiles = pack());
	}
	
	/**
	 * Create's new unloaded folder from given FIT data.
	 */
	public Folder(int folderID, int name, int version,int crc32,byte[] digest, int[] filesIDS, int[] filesNames) {
		if (digest.length != 64 || filesIDS.length != filesNames.length)
			throw new RuntimeException("Invalid data provided.");
		this.id = folderID;
		this.name = name;
		this.version = version;
		this.crc = crc32;
		this.digest = digest;
		this.files = new File[filesIDS.length];
		for (int i = 0; i < filesIDS.length; i++)
			files[i] = new File(filesIDS[i],filesNames[i]);
	}
	
	/**
	 * Copy's this folder and every file in it.
	 */
	public Folder copy() {
		Folder copy = new Folder();
		copy.id = id;
		copy.name = name;
		copy.version = version;
		copy.crc = crc;
		if (digest != null) {
			copy.digest = new byte[64];
			System.arraycopy(digest, 0, copy.digest, 0, 64);
		}
		if (files != null) {
			copy.files = new File[files.length];
			for (int i = 0; i < files.length; i++)
				copy.files[i] = files[i].copy();
		}
		if (packedFiles != null) {
			copy.packedFiles = new ByteBuffer(packedFiles.toArray(0, packedFiles.getBuffer().length),packedFiles.getPosition());
		}
		if (xtea != null) {
			copy.xtea = new int[4];
			System.arraycopy(xtea, 0, copy.xtea, 0, 4);
		}
		copy.needsRepack = needsRepack;
		copy.fileSystemInfoChanged = fileSystemInfoChanged;
		return copy;
	}
	
	
	/**
	 * Load's packed folder file.
	 */
	public void load(ByteBuffer packedFiles) {
		load(packedFiles,null);
	}
	
	/**
	 * Unload's this folder , can be called
	 * only by filesystem unloadCachedData() method.
	 */
	public void unload() {
		if (!isLoaded())
			throw new RuntimeException("Using nonloaded folder.");
		packedFiles = null;
		for (int i = 0; i < files.length; i++)
			files[i].unload();
	}
	
	/**
	 * Load's packed folder file.
	 */
	public void load(ByteBuffer packedFiles,int[] xtea) {
		if (isLoaded())
			throw new RuntimeException("Already loaded.");
		unpack(packedFiles);
		this.packedFiles = packedFiles;
		this.xtea = xtea;
	}
	
	
	/**
	 * Finishe's any changes by generating new packedFiles buffer and updating hashes
	 * if something was changed.
	 */
	public ByteBuffer finish() {
		if (!isLoaded())
			throw new RuntimeException("Altering nonloaded folder.");
		
		if (needsRepack()) {
			sortFiles();
			packedFiles = pack();
			updateHashes(packedFiles);
			markAsNotNeedRepack();
			return packedFiles;
		}
		return packedFiles;
	}
	
	/**
	 * Unpack's given packed files buffer.
	 */
	private void unpack(ByteBuffer packed) {
		
		if (xtea != null && xtea[0] != 0 && xtea[1] != 0 && xtea[2] != 0 && xtea[3] != 0) {
			Helper.decryptContainer(packed, xtea);
		}
		
		packed = Helper.decodeFilesContainer(packed);
		
		if (filesCount() <= 1) {
			if (filesCount() <= 0)
				return;
			ByteBuffer copy = new ByteBuffer(packed.getBuffer().length);
			copy.writeBytes(packed.getBuffer(), 0, packed.getBuffer().length);
			files[0].load(copy);
			return;
		}
		
		int sectorsCount = packed.getBuffer()[packed.getBuffer().length - 1] & 0xFF;
		int[] lengths = new int[filesCount()];
		packed.setPosition(packed.getBuffer().length - ((sectorsCount * filesCount() * 4) + 1));
		for (int sectorID = 0; sectorID < sectorsCount; sectorID++)
			for (int i = 0,length = 0; i < filesCount(); i++)
				lengths[i] += (length += packed.readInt());
		for (int i = 0; i < lengths.length; i++) {
			files[i].load(new ByteBuffer(lengths[i]));
			lengths[i] = 0;
		}
		packed.setPosition(packed.getBuffer().length - ((sectorsCount * filesCount() * 4) + 1));
		for (int fRead = 0, sectorID = 0; sectorID < sectorsCount; sectorID++)
			for (int i = 0,length = 0; i < filesCount(); i++) {
				length += packed.readInt();
				System.arraycopy(packed.getBuffer(), fRead, files[i].getData().getBuffer(), lengths[i], length);
				lengths[i] += length;
				fRead += length;
			}
		
	}
	
	/**
	 * Pack's unpacked files.
	 */
	private ByteBuffer pack() {
		if (filesCount() <= 1) {
			if (filesCount() <= 0) {
				ByteBuffer container = Helper.encodeFilesContainer(new ByteBuffer(new byte[0]), version);
				if (xtea != null && xtea[0] != 0 && xtea[1] != 0 && xtea[2] != 0 && xtea[3] != 0) {
					Helper.encryptContainer(container, xtea);
				}
				return container;
			}
			ByteBuffer packed = new ByteBuffer(files[0].getData().getBuffer().length);
			packed.writeBytes(files[0].getData().getBuffer(), 0, files[0].getData().getBuffer().length);	
			ByteBuffer container = Helper.encodeFilesContainer(packed, version);
			if (xtea != null && xtea[0] != 0 && xtea[1] != 0 && xtea[2] != 0 && xtea[3] != 0) {
				Helper.encryptContainer(container, xtea);
			}
			return container;
		}
		
		int allocLength = 1 + (4 * filesCount()); // sector header
		for (int i = 0; i < files.length; i++)
			allocLength += files[i].getData().getBuffer().length;
		ByteBuffer packed = new ByteBuffer(allocLength);
		for (int i = 0; i < files.length; i++)
			packed.writeBytes(files[i].getData().getBuffer(), 0, files[i].getData().getBuffer().length);
		for (int i = 0,delta = 0; i < files.length; i++, delta = (files[i - 1].getData().getBuffer().length))
			packed.writeInt(files[i].getData().getBuffer().length - delta);
		packed.writeByte(1); // 1 sector
		ByteBuffer container = Helper.encodeFilesContainer(packed, version);
		if (xtea != null && xtea[0] != 0 && xtea[1] != 0 && xtea[2] != 0 && xtea[3] != 0) {
			Helper.encryptContainer(container, xtea);
		}
		return container;
	}
	
	/**
	 * Update's crc32 and whirlpool hash to one's from 
	 * given buffer.
	 */
	private void updateHashes(ByteBuffer packedFiles) {
		crc = Helper.crc32(packedFiles,0,packedFiles.getBuffer().length - 2);
		digest = Whirlpool.whirlpool(packedFiles.getBuffer(), 0, packedFiles.getBuffer().length - 2);
	}
	
	
	/**
	 * Add's new file to this folder.
	 * If there's already a file with same id then 
	 * it get's overwriten.
	 */
	public void addFile(File file) {
		if (!isLoaded())
			throw new RuntimeException("Altering nonloaded folder.");
		if (findFileIndex(file) != -1)
			return;
		if (file.getID() == -1)
			file.setID(getFreeFileID());
		File toOverwrite = findFileByID(file.getID());
		int index = toOverwrite != null ? findFileIndex(toOverwrite) : -1;
		if (index == -1) {
			File[] newFiles = new File[files.length + 1];
			System.arraycopy(files, 0, newFiles, 0, files.length);
			index = files.length;
			files = newFiles;
		}
		files[index] = file;
		needsRepack = fileSystemInfoChanged = true;
	}
	
	/**
	 * Remove's file from this folder.
	 */
	public void removeFile(File file) {
		if (!isLoaded())
			throw new RuntimeException("Altering nonloaded folder.");
		int index = findFileIndex(file);
		if (index == -1)
			return;
		File[] newFiles = new File[files.length - 1];
		for (int write = 0,i = 0; i < files.length; i++)
			if (i != index)
				newFiles[write++] = files[i];
		files = newFiles;
		needsRepack = fileSystemInfoChanged = true;
	}
	
	/**
	 * Delete's all files on this folder.
	 */
	public void deleteAllFiles() {
		if (!isLoaded())
			throw new RuntimeException("Altering nonloaded folder.");
		files = new File[0];
		needsRepack = fileSystemInfoChanged = true;
	}
	
	/**
	 * Recalculate's folder's hashes.
	 */
	public void recalculate() {
		if (!isLoaded())
			throw new RuntimeException("Altering nonloaded folder.");
		updateHashes(packedFiles);
		fileSystemInfoChanged = true;
	}
	
	
	/**
	 * Sort's files.
	 */
	private void sortFiles() {
		if (files.length > 1) {
			int highestID = -1;
			int highestIDFile = -1;
			for (int i = 0; i < files.length; i++)
				if (files[i].getID() > highestID) {
					highestID = files[i].getID();
					highestIDFile = i;
				}
			if (highestIDFile == -1)
				throw new RuntimeException("N/A");
			if (highestIDFile == 0)
				return;
			File highest         = files[highestIDFile];
			File at0             = files[0];
			files[0]             = highest;
			files[highestIDFile] = at0;
			needsRepack = fileSystemInfoChanged = true;
		}
		
	}

	/**
	 * Get's free file ID.
	 */
	private int getFreeFileID() {
		if (files.length == 0)
			return 0;
		int highest = -1;
		for (int i = 0; i < files.length; i++)
			if (files[i].getID() > highest)
				highest = files[i].getID();
		return highest + 1;
	}

	/**
	 * Find's file by id's id.
	 * Returns null if not found.
	 */
	public File findFileByID(int id) {
		if (!isLoaded())
			throw new RuntimeException("Folder is not loaded.");
		for (int i = 0; i < files.length; i++)
			if (files[i].getID() == id)
				return files[i];
		return null;
	}
	
	/**
	 * Find's file by name.
	 * Returns null if not found.
	 */
	public File findFileByName(String name) {
		return findFileByName(Helper.strToI(name));
	}
	
	/**
	 * Find's file by name.
	 * Returns null if not found.
	 */
	public File findFileByName(int name) {
		if (!isLoaded())
			throw new RuntimeException("Folder is not loaded.");
		
		if (name == -1)
			return null;
		
		for (int i = 0; i < files.length; i++)
			if (files[i].getName() == name)
				return files[i];
		return null;
	}
	
	/**
	 * Find's file index.
	 */
	private int findFileIndex(File file) {
		for (int i = 0; i < files.length; i++)
			if (files[i] == file)
				return i;
		return -1;
	}
	
	
	/**
	 * Wheter this folder was loaded.
	 */
	public boolean isLoaded() {
		return packedFiles != null;
	}
	
	/**
	 * Wheter this folder needs repacking.
	 */
	private boolean needsRepack() {
		if (needsRepack)
			return true;
		for (int i = 0; i < files.length; i++)
			if (files[i].isDataChanged())
				return true;
		return false;
	}
	
	/**
	 * Mark's this folder as already repacked.
	 */
	private void markAsNotNeedRepack() {
		needsRepack = false;
		for (int i = 0; i < files.length; i++)
			files[i].markDataAsNotChanged();
	}

	
	/**
	 * Wheter this folder was changed in any way.
	 */
	public boolean isFileSystemInfoChanged() {
		if (fileSystemInfoChanged)
			return true;
		for (int i = 0; i < files.length; i++)
			if (files[i].isFileSystemInfoChanged())
				return true;
		return false;
	}

	/**
	 * Mark's this folder as not changed, this
	 * happens when fit is repacked.
	 */
	public void markFileSystemInfoAsNotChanged() {
		fileSystemInfoChanged = false;
		for (int i = 0; i < files.length; i++)
			files[i].markFileSystemInfoAsNotChanged();
	}
	
	/**
	 * Mark's this folder file system info as changed,
	 * get's called by FS only.
	 */
	public void markFileSystemInfoAsChanged() {
		fileSystemInfoChanged = true;
	}
	
	
	
	/**
	 * Get's count of files this folder has.
	 */
	public int filesCount() {
		return files.length;
	}
	
	/**
	 * Get's all files that this folder has.
	 * Modifying the array in any way is not allowed.
	 */
	public File[] getFiles() {
		return files;
	}
	
	/**
	 * Get's ID of this folder.
	 */
	public int getID() {
		return id;
	}
	
	/**
	 * Set's ID of this folder.
	 */
	public void setID(int id) {
		if (!isLoaded())
			throw new RuntimeException("Altering nonloaded folder.");
		if (this.id != id)
			this.fileSystemInfoChanged = true;
		this.id = id;
	}
	
	/**
	 * Get's name of this folder.
	 */
	public int getName() {
		return name;
	}
	
	/**
	 * Set's name of this folder.
	 */
	public void setName(String name) {
		setName(Helper.strToI(name));
	}
	
	/**
	 * Set's name of this folder.
	 */
	public void setName(int name) {
		if (!isLoaded())
			throw new RuntimeException("Altering nonloaded folder.");
		if (this.name != name)
			this.fileSystemInfoChanged = true;
		this.name = name;
	}
	
	/**
	 * Get's version of this folder.
	 */
	public int getVersion() {
		return version;
	}
	
	/**
	 * Set's version of this folder.
	 */
	public void setVersion(int version) {
		if (!isLoaded())
			throw new RuntimeException("Altering nonloaded folder.");
		if (this.version != version)
			this.fileSystemInfoChanged = this.needsRepack = true;
		this.version = version;
	}
	
	/**
	 * Set's XTEA keys of this folder.
	 */
	public void setXTEA(int[] xtea) {
		if (!isLoaded())
			throw new RuntimeException("Altering nonloaded folder.");
		if (this.xtea != xtea)
			this.fileSystemInfoChanged = this.needsRepack = true;
		this.xtea = xtea;
	}
	
	
	/**
	 * Get's crc32 of packed version of this folder.
	 */
	public int getCRC32() {
		return crc;
	}
	
	/**
	 * Get's whirlpool digest of packed version of this folder.
	 */
	public byte[] getDigest() {
		return digest;
	}
	
	/**
	 * Get's packed files buffer.
	 * Call to finish() is a must if you want
	 * to get up-to-date version.
	 */
	public ByteBuffer getPackedFiles() {
		return packedFiles;
	}
	
	
}
