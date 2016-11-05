/*package com.rs.game.player;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.FriendChatsManager;

public class Starter {
private void giveitems(Player player) {
    player.getInventory().addItem(11814, 1);//Bronze Armour Set
    player.getInventory().addItem(11834, 1);//addy Armour Set
    player.getInventory().addItem(6568, 1);//Obby Cape
    player.getInventory().addItem(1725, 1);//Amulet of strength
    player.getInventory().addItem(1321, 1);//Bronze Scimitar
    player.getInventory().addItem(1333, 1);//Rune Scimitar
    player.getInventory().addItem(4587, 1);//Dragon Scimitar
    player.getInventory().addItem(386, 250);//Shark
    player.getInventory().addItem(15273, 100);//Rocktail
    player.getInventory().addItem(2435, 15);//Prayer Potions
    player.getInventory().addItem(2429, 10);//Attack Potions
    player.getInventory().addItem(114, 10);//strength Potions
    player.getInventory().addItem(2433, 10);//Defence Potions
    player.getInventory().addItemMoneyPouch(995, 500000);//2.5m cash 
}
        public static final int MAX_STARTER_COUNT = 1;

    	public static void appendStarter(Player player) {
        		 player.getHintIconsManager().removeUnsavedHintIcon();
                player.getMusicsManager().reset();
                player.getCombatDefinitions().setAutoRelatie(false);
                player.getCombatDefinitions().refreshAutoRelatie();
                player.starter = 3;
                player.starterstage = 3;
					player.getInterfaceManager().sendInterfaces();
					player.closeInterfaces();
					player.unlock();
                FriendChatsManager.joinChat("99max99", player);
    			FriendChatsManager.refreshChat(player);
    			player.appendStarter();
    			//player.setNextWorldTile(new WorldTile(1346, 5197, 0));
    			player.getPackets().sendGameMessage("Welcome to Noszscape!");
                player.getPackets().sendGameMessage("The world is huge and can be confusing!");
                player.getPackets().sendGameMessage("If you have any questions you can check out the forums for guides!");
                player.getPackets().sendGameMessage("...and if that doesn't answer your questions you can ask any staff online!");
    	}
}
*/
package com.rs.game.player;

import com.rs.game.player.content.FriendChatsManager;

public class Starter {

	public static final int MAX_STARTER_COUNT = 1;
	
	//private static int amount = 100000;

	public static void appendStarter(Player player) {
		String ip = player.getSession().getIP();
		int count = StarterMap.getSingleton().getCount(ip);
		player.getStarter = true;
		if (count >= MAX_STARTER_COUNT) {
			player.sendMessage("You have already recieved your starter kit!");
			return;
		}
		
		//player.getInventory().addItem(995, amount);
	    player.getInventory().addItem(11814, 1);//Bronze Armour Set
	    player.getInventory().addItem(11834, 1);//addy Armour Set
	    player.getInventory().addItem(6568, 1);//Obby Cape
	    player.getInventory().addItem(1725, 1);//Amulet of strength
	    player.getInventory().addItem(1321, 1);//Bronze Scimitar
	    player.getInventory().addItem(1333, 1);//Rune Scimitar
	    player.getInventory().addItem(4587, 1);//Dragon Scimitar
	    player.getInventory().addItem(386, 250);//Shark
	    player.getInventory().addItem(15273, 100);//Rocktail
	    player.getInventory().addItem(2435, 15);//Prayer Potions
	    player.getInventory().addItem(2429, 10);//Attack Potions
	    player.getInventory().addItem(114, 10);//strength Potions
	    player.getInventory().addItem(2433, 10);//Defence Potions
	    player.getInventory().addItemMoneyPouch(995, 10000000);//2.5m cash 
	    player.getInventory().addItem(841, 1); // short bow
	    player.getInventory().addItem(882, 1000);  // bronze arrows
	    player.getInventory().addItem(17009, 1);  // staff
	    player.getInventory().addItem(558, 1000);  // mind rune
	    player.getInventory().addItem(554, 1000);  // fire rune
	    player.getInventory().addItem(562, 1000); // chaos rune
        player.starter = 3;
        player.starterstage = 3;
        FriendChatsManager.joinChat("Help", player);
		FriendChatsManager.refreshChat(player);
		player.getPackets().sendGameMessage("Welcome to CollabScape718!");
        player.getPackets().sendGameMessage("The world is huge and can be confusing!");
        player.getPackets().sendGameMessage("If you have any questions you can check out the forums for guides!");
        player.getPackets().sendGameMessage("...and if that doesn't answer your questions you can ask any staff online!");
		player.getHintIconsManager().removeUnsavedHintIcon();
		player.getMusicsManager().reset();
		player.getCombatDefinitions().setAutoRelatie(false);
		player.getCombatDefinitions().refreshAutoRelatie();
		StarterMap.getSingleton().addIP(ip);
	}
}