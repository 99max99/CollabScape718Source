package com.rs.game.player.content;

import com.rs.game.player.Player;

public class SpanStore {
	
	public static int INTERFACE_ID = 1273;
	public static int TAB_INTERFACE_ID = 0;
	
	public static void sendShop(Player player) {
      	player.getPackets().sendIComponentText(1273, 66, + player.RuneSpanPoints + "");
      	player.getPackets().sendWindowsPane(INTERFACE_ID, 0);
      	player.getInterfaceManager().closeChatBoxInterface();
	}
	
	public static void closeShop(Player player) {
		player.getPackets().sendWindowsPane(
				player.getInterfaceManager().hasRezizableScreen() ? 746
						: 548, 0);
	}

}
