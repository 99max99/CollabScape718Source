package com.rs.cache.loaders;

import java.util.concurrent.ConcurrentHashMap;

import com.rs.cache.Cache;
import com.rs.io.InputStream;

public final class ClientVarpBitDefinitions {

	private static final ConcurrentHashMap<Integer, ClientVarpBitDefinitions> varpbitDefs = new ConcurrentHashMap<Integer, ClientVarpBitDefinitions>();

	public int varpBitId;
	public int varpId;
	public int tillBitshift;
	public int fromBitshift;

	public static final ClientVarpBitDefinitions getClientVarpBitDefinitions(int id) {
		ClientVarpBitDefinitions script = varpbitDefs.get(id);
		if (script != null)//open new txt document
			return script;
		byte[] data =  Cache.STORE.getIndexes()[22].getFile(id >>> 1416501898,id & 0x3ff);
		script = new ClientVarpBitDefinitions();
		script.varpBitId = id;
		if (data != null)
			script.readValueLoop(new InputStream(data));
		varpbitDefs.put(id, script);
		return script;

	}

	private void readValueLoop(InputStream stream) {
		for (;;) {
			int opcode = stream.readUnsignedByte();
			if (opcode == 0)
				break;
			readValues(stream, opcode);
		}
	}

	private void readValues(InputStream stream, int opcode) {
		if (opcode == 1) {
			varpId = stream.readUnsignedShort();
			fromBitshift = stream.readUnsignedByte();
			tillBitshift = stream.readUnsignedByte();
		}
	}

	private ClientVarpBitDefinitions() {

	}
}