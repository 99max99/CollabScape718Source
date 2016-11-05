package net.openrs.cache;

import java.nio.ByteBuffer;

import net.openrs.util.ByteBufferUtils;

/**
 * A {@link Sector} contains a header and data. The header contains information
 * used to verify the integrity of the cache like the current file id, type and
 * chunk. It also contains a pointer to the next sector such that the sectors
 * form a singly-linked list. The data is simply up to 512 bytes of the file.
 * @author Graham
 * @author `Discardedx2
 */
public final class Sector {

	/**
	 * The size of the header within a sector in bytes.
	 */
	public static final int HEADER_SIZE = 8;

	/**
	 * The size of the data within a sector in bytes.
	 */
	public static final int DATA_SIZE = 512;

	/**
	 * The total size of a sector in bytes.
	 */
	public static final int SIZE = HEADER_SIZE + DATA_SIZE;

	/**
	 * Decodes the specified {@link ByteBuffer} into a {@link Sector} object.
	 * @param buf The buffer.
	 * @return The sector.
	 */
	public static Sector decode(ByteBuffer buf) {
		if (buf.remaining() != SIZE)
			throw new IllegalArgumentException();

		int id = buf.getShort() & 0xFFFF;
		int chunk = buf.getShort() & 0xFFFF;
		int nextSector = ByteBufferUtils.getTriByte(buf);
		int type = buf.get() & 0xFF;
		byte[] data = new byte[DATA_SIZE];
		buf.get(data);

		return new Sector(type, id, chunk, nextSector, data);
	}

	/**
	 * The type of file this sector contains.
	 */
	private final int type;

	/**
	 * The id of the file this sector contains.
	 */
	private final int id;

	/**
	 * The chunk within the file that this sector contains.
	 */
	private final int chunk;

	/**
	 * The next sector.
	 */
	private final int nextSector;

	/**
	 * The data in this sector.
	 */
	private final byte[] data;

	/**
	 * Creates a new sector.
	 * @param type The type of the file.
	 * @param id The file's id.
	 * @param chunk The chunk of the file this sector contains.
	 * @param nextSector The sector containing the next chunk.
	 * @param data The data in this sector.
	 */
	public Sector(int type, int id, int chunk, int nextSector, byte[] data) {
		this.type = type;
		this.id = id;
		this.chunk = chunk;
		this.nextSector = nextSector;
		this.data = data;
	}

	/**
	 * Gets the type of file in this sector.
	 * @return The type of file in this sector.
	 */
	public int getType() {
		return type;
	}

	/**
	 * Gets the id of the file within this sector.
	 * @return The id of the file in this sector.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the chunk of the file this sector contains.
	 * @return The chunk of the file this sector contains.
	 */
	public int getChunk() {
		return chunk;
	}

	/**
	 * Gets the next sector.
	 * @return The next sector.
	 */
	public int getNextSector() {
		return nextSector;
	}

	/**
	 * Gets this sector's data.
	 * @return The data within this sector.
	 */
	public byte[] getData() {
		return data;
	}

	/**
	 * Encodes this sector into a {@link ByteBuffer}.
	 * @return The encoded buffer.
	 */
	public ByteBuffer encode() {
		ByteBuffer buf = ByteBuffer.allocate(SIZE);

		buf.putShort((short) id);
		buf.putShort((short) chunk);
		ByteBufferUtils.putTriByte(buf, nextSector);
		buf.put((byte) type);
		buf.put(data);

		return (ByteBuffer) buf.flip();
	}

}
