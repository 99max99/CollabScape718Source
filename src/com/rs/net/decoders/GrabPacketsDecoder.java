package com.rs.net.decoders;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rs.cache.Cache;
import com.rs.io.InputStream;
import com.rs.net.Session;

/**
 * 
 * @author Jonathan
 *
 */
public final class GrabPacketsDecoder extends Decoder {

	private LinkedList<String> requests = new LinkedList<String>();
	private static ExecutorService updateService = Executors.newFixedThreadPool(1);

	public GrabPacketsDecoder(Session connection) {
		super(connection);
	}

	@Override
	public final void decode(InputStream stream) {
		while (stream.getRemaining() > 0 && session.getChannel().isConnected()) {
			int packetId = stream.readUnsignedByte();
			decodeRequestCacheContainer(stream, packetId);
		}
		requests.clear();
	}

	private final void decodeRequestCacheContainer(InputStream stream, final int priority) {
		final int indexId = stream.readUnsignedByte();
		final int archiveId = stream.readInt();
		if (archiveId < 0)
			return;
		if (indexId != 255) {
			if (Cache.STORE.getIndexes().length <= indexId || Cache.STORE.getIndexes()[indexId] == null || !Cache.STORE.getIndexes()[indexId].archiveExists(archiveId))
				return;
		} else if (archiveId != 255)
			if (Cache.STORE.getIndexes().length <= archiveId || Cache.STORE.getIndexes()[archiveId] == null)
				return;
		switch (priority) {
			case 0:
				requests.add(indexId + "," + archiveId);
				break;
			case 1:
				updateService.execute(new Runnable() {
					@Override
					public void run() {
						session.getGrabPackets().sendCacheArchive(indexId, archiveId, true);
					}
				});
				break;
			case 2:
			case 3:
				requests.clear();
				break;
			case 4:
				session.getGrabPackets().setEncryptionValue(stream.readUnsignedByte());
				if (stream.readUnsignedShort() != 0) {
					session.getChannel().close();
				}
				break;
			case 7:
				session.getChannel().close();
				break;
		}
		while (requests.size() > 0) {
			String[] request = requests.removeFirst().split(",");
			session.getGrabPackets().sendCacheArchive(Integer.parseInt(request[0]), Integer.parseInt(request[1]), false);
		}
	}

}
