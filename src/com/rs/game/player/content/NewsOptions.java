package com.rs.game.player.content;

import com.rs.game.player.Player;
/**
 * 
 * @author Jazzy Ya | Nexon | Jaguar
 *
 */
public class NewsOptions {

	public static void TogglePlayerInfo(Player player) {
		player.informwelcome = true;
		player.sm("<col=10A315>Enabled!");
		player.sm("<col=7E2217>We will tell you if new players join.");
		player.sm("<col=7E2217>Please re-log for it to take affect.");
	}
	public static void unTogglePlayerInfo(Player player) {
		player.informwelcome = false;
		player.sm("<col=FF0000>Disabled!");
		player.sm("<col=7E2217>We will not tell you if new players join.");
		player.sm("<col=7E2217>Please re-log for it to take affect.");
		player.getInterfaceManager().closeChatBoxInterface();
	}
	public static void setNewsNegative(Player player) {
		player.setnews = false;
		player.sm("<col=FF0000>Disabled!");
		player.sm("<col=7E2217>You have disabled the news upon login.");
		player.getInterfaceManager().closeChatBoxInterface();
	}
	public static void setNewsPositive(Player player) {
		player.setnews = true;
		player.sm("<col=10A315>Enabled!");
		player.sm("<col=7E2217>You have activated the news upon login.");
		player.getInterfaceManager().closeChatBoxInterface();
	}
	public static void setMessagesNegative(Player player) {
		player.informmessages = false;
		player.sm("<col=FF0000>Disabled!");
		player.sm("<col=7E2217>You have disabled the server messages.");
		player.sm("<col=7E2217>Please re-log for it to take affect.");
		player.getInterfaceManager().closeChatBoxInterface();
	}
	public static void setMessagesPositive(Player player) {
		player.informmessages = true;
		player.sm("<col=10A315>Enabled!");
		player.sm("<col=7E2217>You have activated the server messages.");
		player.sm("<col=7E2217>Please re-log for it to take affect.");
		player.getInterfaceManager().closeChatBoxInterface();
	}
	public static void set99sNegative(Player player) {
		player.inform99s = false;
		player.sm("<col=FF0000>Disabled!");
		player.sm("<col=7E2217>You have disabled the 99 messages.");
		player.sm("<col=7E2217>Please re-log for it to take affect.");
		player.getInterfaceManager().closeChatBoxInterface();
	}
	public static void set99sPositive(Player player) {
		player.inform99s = true;
		player.sm("<col=10A315>Enabled!");
		player.sm("<col=7E2217>You have activated the 99 messages.");
		player.sm("<col=7E2217>Please re-log for it to take affect.");
		player.getInterfaceManager().closeChatBoxInterface();
	}
}
