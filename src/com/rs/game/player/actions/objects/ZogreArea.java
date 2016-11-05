package com.rs.game.player.actions.objects;

import java.util.Random;

import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.net.decoders.handlers.ObjectHandler;
import com.rs.utils.Misc;
import com.rs.utils.Utils;

public class ZogreArea {
	
	public static void BarricadeIn(Player player,
			final WorldObject object) {
		player.useStairs(839, new WorldTile(2457, 3047, 0), 1, 2); //Barricade going in
	}

	public static void BarricadeOut(Player player, final WorldObject object) {
		player.useStairs(839, new WorldTile(2455, 3047, 0), 1, 2); //Barricade going out
	}

	public static void Stairs1(Player player, final WorldObject object) {
		player.setNextWorldTile(new WorldTile(2477, 9437, 2));
	}

	public static void Stairs2(Player player, final WorldObject object) {
		player.setNextWorldTile(new WorldTile(2485, 3045, 0));
	}

	public static void SlashBash(Player player, final WorldObject object) {
		 if (player.getInventory().containsItem(605, 1)) {
		        player.getInventory().deleteItem(605, 1);
		        player.getPackets().sendGameMessage("You summon Slash Bash");
		        World.spawnNPC(2060, new WorldTile(2479, 9446, 0), -1, true, true);
		 } else if (!player.getInventory().containsItem(605, 1)) {
		    	player.getDialogueManager().startDialogue("SimpleMessage","You must obtain a bone key to use this.");
		        return;
		    }
	}
	
	public static void SlashEnter(Player player, final WorldObject object) {
	       // player.getPackets().sendGameMessage("You open the door and go through");
	    	//player.setNextWorldTile(new WorldTile(2441, 3242, 0));
		ObjectHandler.handleGate(player, object);
	}
	
	public static void SlashEnter2(Player player, final WorldObject object) {
	       // player.getPackets().sendGameMessage("You open the door and go through");
	    	//player.setNextWorldTile(new WorldTile(2442, 9433, 0));
	    	  ObjectHandler.handleGate(player, object);
	}
	
	public static void Stairs3(Player player, final WorldObject object) {
		player.setNextWorldTile(new WorldTile(2442, 9417, 0));
	}
	
	public static void Stairs4(Player player, final WorldObject object) {
		player.setNextWorldTile(new WorldTile(2446, 9417, 2));
	}

	public static void Coffin(Player player, final WorldObject object) {
		
		int CommonReward[][] = {{1351, 1}, {1349, 1}, {1353, 1},
        		{1265, 1}, {1267, 1}, {1269, 1}, {946, 1},
        		{4819, Misc.random(11)}, {4820, Misc.random(11)}, {1539, Misc.random(11)},
        		{4821, Misc.random(11)},};
		
		int UnCommonReward[][] = {{995, Misc.random(6, 20)}, {1205, 1}, {1203, 1},
        		{1207, 1}, {686, 1}, {697, 1}, {1129, 1}, {605, 1}};
		
		int RareReward[][] = {{590, 1}, {688, 1}, {1627, 1},
        		{1625, 1}, {2485, 1}};
		
		
	    if (player.getInventory().containsItem(4850, 1)) {
	        player.getInventory().deleteItem(4850, 1);
	        player.getPackets().sendGameMessage("You open the coffin and find something inside...");
	        int rew = Misc.random(100);
	        int bones = Misc.random(10000);
	        if (rew >= 0 && rew <= 59) {
	        	int rand = Utils.random(CommonReward.length);
	        	player.getInventory().addItem(CommonReward[rand][0], CommonReward[rand][1]);
	        } else if(rew >= 60 && rew <= 99) {
	        	int rand = Utils.random(UnCommonReward.length);
	        	player.getInventory().addItem(UnCommonReward[rand][0], UnCommonReward[rand][1]);
	        } else if(rew == 100) {
	        	int rand = Utils.random(RareReward.length);
	        	player.getInventory().addItem(RareReward[rand][0], RareReward[rand][1]);
	        }
	        if (bones >=1 && bones <= 4996) {
	        	player.getInventory().addItem(4812, 1);
	        } else if(bones >= 4997 && bones <= 6082) {
	        	player.getInventory().addItem(4830, 1);
	        } else if(bones >= 6082 && bones <= 6848) {
	        	player.getInventory().addItem(4832, 1);
	        } else if(bones >= 6849 && bones <= 7223) {
	        	player.getInventory().addItem(4834, 1);
	        }
	        
	    } else if (!player.getInventory().containsItem(4850, 1)) {
	    	player.getDialogueManager().startDialogue("SimpleMessage","You must have a certain key to open this.");
	        return;
	    }
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if ((id == 6881) && (object.getX() == 2456) && (object.getY() == 3047) && (player.getX() == 2455) && (player.getY() == 3047)) {
			ZogreArea.BarricadeIn(player, object);
		}
		if ((id == 6881) && (object.getX() == 2456) && (object.getY() == 3047) && (player.getX() == 2457) && (player.getY() == 3047)) {
			ZogreArea.BarricadeOut(player, object);
		}
		if ((id == 6841) && (object.getX() == 2485) && (object.getY() == 3042)) {
			ZogreArea.Stairs1(player, object);
		}
		if ((id == 6842) && (object.getX() == 2478) && (object.getY() == 9437)) {
			ZogreArea.Stairs2(player, object);
		}
		if ((id == 6897) && (object.getX() == 2483) && (object.getY() == 9445)) {
			ZogreArea.SlashBash(player, object);
		}
		if (id == 6871) {
			ZogreArea.SlashEnter(player, object);
		}
		if (id == 6872) {
			ZogreArea.SlashEnter2(player, object);
		}
		if ((id == 6841) && (object.getX() == 2443) && (object.getY() == 9417)) {
			ZogreArea.Stairs3(player, object);
		}
		if ((id == 6842) && (object.getX() == 2442) && (object.getY() == 9417)) {
			ZogreArea.Stairs4(player, object);
		}
		if (id == 6848 || id == 36714 || id == 35079 || id == 6850 || id == 6883 || id == 6843) {
			ZogreArea.Coffin(player, object);
		}
	}

}
