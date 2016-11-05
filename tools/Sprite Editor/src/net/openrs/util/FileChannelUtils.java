package net.openrs.util;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Contains {@link FileChannel}-related utility methods.
 * @author Graham
 * @author `Discardedx2
 */
public final class FileChannelUtils {

	/**
	 * Reads as much as possible from the channel into the buffer.
	 * @param channel The channel.
	 * @param buffer The buffer.
	 * @param ptr The initial position in the channel.
	 * @throws IOException if an I/O error occurs.
	 * @throws EOFException if the end of the file was reached and the buffer
	 * could not be completely populated.
	 */
	public static void readFully(FileChannel channel, ByteBuffer buffer, long ptr) throws IOException {
		while (buffer.remaining() > 0) {
			long read = channel.read(buffer, ptr);
			if (read == -1) {
				throw new EOFException();
			} else {
				ptr += read;
			}
		}
	}

	/**
	 * Default private constructor to prevent instantiation.
	 */
	private FileChannelUtils() {
		
	}

}
