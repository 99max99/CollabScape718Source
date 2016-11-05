package com.rs.net.decoders.handlers;

import com.rs.game.player.Player;
import com.rs.io.InputStream;

public class GESearchHandler {

	public static void handlePacket(Player player, InputStream packet) {
		int itemId = packet.readShort();
		//player.grandExchange().set(itemId);
	}

}
