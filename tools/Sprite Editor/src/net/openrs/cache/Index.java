package net.openrs.cache;

import java.nio.ByteBuffer;

import net.openrs.util.ByteBufferUtils;

/**
 * An {@link Index} points to a file inside a {@link FileStore}.
 * @author Graham
 * @author `Discardedx2
 */
public final class Index {

	/**
	 * The size of an index, in bytes.
	 */
	public static final int SIZE = 6;

	/**
	 * Decodes the specified {@link ByteBuffer} into an {@link Index} object.
	 * @param buf The buffer.
	 * @return The index.
	 */
	public static Index decode(ByteBuffer buf) {
		if (buf.remaining() != SIZE)
			throw new IllegalArgumentException();

		int size = ByteBufferUtils.getTriByte(buf);
		int sector = ByteBufferUtils.getTriByte(buf);
		return new Index(size, sector);
	}

	/**
	 * The size of the file in bytes.
	 */
	private int size;

	/**
	 * The number of the first sector that contains the file.
	 */
	private int sector;

	/**
	 * Creates a new index.
	 * @param size The size of the file in bytes.
	 * @param sector The number of the first sector that contains the file.
	 */
	public Index(int size, int sector) {
		this.size = size;
		this.sector = sector;
	}

	/**
	 * Gets the size of the file.
	 * @return The size of the file in bytes.
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Gets the number of the first sector that contains the file.
	 * @return The number of the first sector that contains the file.
	 */
	public int getSector() {
		return sector;
	}

	/**
	 * Encodes this index into a byte buffer.
	 * @return The buffer.
	 */
	public ByteBuffer encode() {
		ByteBuffer buf = ByteBuffer.allocate(Index.SIZE);
		ByteBufferUtils.putTriByte(buf, size);
		ByteBufferUtils.putTriByte(buf, sector);
		return (ByteBuffer) buf.flip();
	}

}