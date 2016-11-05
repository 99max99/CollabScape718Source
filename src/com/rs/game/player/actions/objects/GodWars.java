package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.net.decoders.handlers.ObjectHandler;

public class GodWars {
	
	public static void JumpDown(Player player,
			final WorldObject object) {
		player.setNextWorldTile(new WorldTile(2920, 5274, 0));
		 player.getPackets().sendGameMessage("You tied the rope, and jumped down.");
	}

	public static void ArmadylSwing(Player player, final WorldObject object) {
		if (player.getX() == 2872 && player.getY() == 5280) {
		if (player.getSkills().getLevel(Skills.RANGE) >= 70) {
			player.setNextWorldTile(new WorldTile(2872, 5272, 0));
			 player.getPackets().sendGameMessage("You swing yourself over...");
		} else {
			player.getPackets().sendGameMessage("You must have 70 Ranged to enter the Armadyl GodWars.");
		}
		} else if (player.getX() == 2872 && player.getY() == 5272) {
			player.setNextWorldTile(new WorldTile(2872, 5280, 0));
			 player.getPackets().sendGameMessage("You swing yourself over...");
		}
	}

	public static void SaradominWall(Player player, final WorldObject object) {
			if (player.getSkills().getLevel(Skills.AGILITY) >= 70) {
		if (player.getX() == 2911)
		{
			player.setNextWorldTile(new WorldTile(2915, 5298, 0));
			 player.getPackets().sendGameMessage("You climb down the rocky wall...");
		}
		if (player.getX() == 2915)
		{
			player.setNextWorldTile(new WorldTile(2911, 5298, 0));
			 player.getPackets().sendGameMessage("You climb up the rocky wall...");
		}
		if (player.getY() == 5279)
		{
			player.setNextWorldTile(new WorldTile(2920, 5275, 0));
			 player.getPackets().sendGameMessage("You climb down the rocky wall...");
		}
		if (player.getY() == 5275)
		{
			player.setNextWorldTile(new WorldTile(2920, 5279, 0));
			 player.getPackets().sendGameMessage("You climb up the rocky wall...");
		}
	} else {
		player.getPackets().sendGameMessage("You must have 70 Agility to enter the Saradomin GodWars.");
	}
	}

	public static void SaradominLair(Player player, final WorldObject object) {
		if (World.saradomin == false) {
		if (player.saradomin >= 40) {
		if (player.getX() == 2923 && player.getY() == 5257)
		{
			player.setNextWorldTile(new WorldTile(2923, 5256, 0));
			 player.getPackets().sendGameMessage("You Enter Saradomins Lair... Prepare to fight!");
			 player.saradomin = 0;
		}
		} else {
			player.getPackets().sendGameMessage("You must have a kill count of 40 to enter.");
		}
		} else {
			player.setNextWorldTile(new WorldTile(2923, 5256, 0));
			 player.getPackets().sendGameMessage("You Enter Saradomins Lair... Prepare to fight!");
		}
		if (player.getX() == 2923 && player.getY() == 5256)
		{
			player.setNextWorldTile(new WorldTile(2923, 5257, 0));
			 player.getPackets().sendGameMessage("You left Saradomins Lair...");
		}
	}
	
	public static void BandosLair(Player player, final WorldObject object) {
		if (World.bandos == false) {
		if (player.bandos >= 40) {
		if (player.getX() == 2862 && player.getY() == 5357)		{
			player.setNextWorldTile(new WorldTile(2863, 5357, 0));
			 player.getPackets().sendGameMessage("You Enter The Bandos Lair... Prepare to fight!");
			 player.bandos = 0;
		}
		} else {
			player.getPackets().sendGameMessage("You must have a kill count of 40 to enter.");
		}
		} else {
				player.setNextWorldTile(new WorldTile(2863, 5357, 0));
			 player.getPackets().sendGameMessage("You Enter The Bandos Lair... Prepare to fight!");
		}
		if (player.getX() == 2863 && player.getY() == 5357)
		{
			player.setNextWorldTile(new WorldTile(2862, 5357, 0));
			 player.getPackets().sendGameMessage("You left Bandos Lair...");
		}
	}
	
	public static void ArmadylLair(Player player, final WorldObject object) {
		if (World.armadyl == false) {
		if (player.armadyl >= 40) {
		if (player.getX() == 2835 && player.getY() == 5294)
		{
			player.setNextWorldTile(new WorldTile(2835, 5295, 0));
			 player.getPackets().sendGameMessage("You enter Armadyls Lair... Prepare to fight!");
			 player.armadyl = 0;
		}
		} else {
			player.getPackets().sendGameMessage("You must have a kill count of 40 to enter.");
		}
		} else {
			player.setNextWorldTile(new WorldTile(2835, 5295, 0));
			 player.getPackets().sendGameMessage("You enter Armadyls Lair... Prepare to fight!");
		}
		if (player.getX() == 2835 && player.getY() == 5295)
		{
			player.setNextWorldTile(new WorldTile(2835, 5294, 0));
			 player.getPackets().sendGameMessage("You left Armadyls Lair...");
		}
	}
	
	public static void ZamorakLair(Player player, final WorldObject object) {
		if (World.zamorak == false) {
		if (player.zamorak >= 40) {
		if (player.getX() == 2925 && player.getY() == 5333) 
		{
		player.setNextWorldTile(new WorldTile(2925, 5332, 0));
		 player.getPackets().sendGameMessage("You enter Zamoraks Lair... Prepare to fight!");
		 player.zamorak = 0;
	}
	} else {
		player.getPackets().sendGameMessage("You must have a kill count of 40 to enter.");
	}
		} else {
			player.setNextWorldTile(new WorldTile(2925, 5332, 0));
			 player.getPackets().sendGameMessage("You enter Zamoraks Lair... Prepare to fight!");
		}
		if (player.getX() == 2925 && player.getY() == 5332) {
			player.setNextWorldTile(new WorldTile(2925, 5333, 0));
			 player.getPackets().sendGameMessage("You left Zamoraks Lair...");
		}
	}
	
	public static void ZamorakBridge(Player player, final WorldObject object) {
		if (player.getSkills().getLevel(Skills.HITPOINTS) >= 70) {
		if (player.getY() == 5338)
		{
			player.setNextWorldTile(new WorldTile(2887, 5344, 0));
			 player.getPackets().sendGameMessage("You cross the icey bridge...");
		}
		if (player.getY() == 5344)
		{
			player.setNextWorldTile(new WorldTile(2887, 5338, 0));
			 player.getPackets().sendGameMessage("You cross the icey bridge...");
		}
		} else {
			player.getPackets().sendGameMessage("You must have 70 Constitution to enter the Zamorak GodWars.");
		}
	}
	
	public static void BandosWall(Player player, final WorldObject object) {
		if (player.getSkills().getLevel(Skills.STRENGTH) >= 70) {
		if (player.getX() == 2851 && player.getY() == 5334)
		{
			player.setNextWorldTile(new WorldTile(2850, 5334, 0));
			 player.getPackets().sendGameMessage("You destroyed the door, and walked into Bandos lair...");
		}
		if (player.getX() == 2850 && player.getY() == 5334) 
		{
			player.setNextWorldTile(new WorldTile(2851, 5334, 0));
			 player.getPackets().sendGameMessage("You left the Bandos lair...");
		}
		} else {
			player.getPackets().sendGameMessage("You must have 70 Strength to enter the Bandos GodWars.");
		}
	}
	
	public static void NexDoor(Player player, final WorldObject object) {
		if (player.getInventory().containsItem(20120, 1)) {
			player.getInventory().deleteItem(20120, 1);
			player.getPackets().sendGameMessage("You pass through the Zaros door.");
			player.setNextWorldTile(new WorldTile(2887, 5277, 0));
		} else {
			player.getPackets().sendGameMessage("You need a frozen key in order to enter.");
		}
	}
	
	public static void NexDown(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(2855, 5222, 0), 1, 2);
	}
	
	public static void NexUp(Player player,
			final WorldObject object) {
		player.useStairs(828, new WorldTile(2887, 5276, 0), 1, 2);
	}
	
	public static void NexSlide(Player player,
			final WorldObject object) {
		if (player.getX() == 2680) {
			player.addWalkSteps(2863, 5219, -1, false);
		} else if (object.getX() == 2683) {
			player.addWalkSteps(2860, 5219, -1, false);
		}
		player.setNextAnimation(new Animation(839));
	}
	
	public static void NexDoor2(Player player,
			final WorldObject object) {
		ObjectHandler.handleDoor(player, object, 3000);
	}

	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 26444) {
			GodWars.JumpDown(player, object);
		}
		if (id == 75463 && player.getX() == 2872 && player.getY() == 5280) {
			GodWars.ArmadylSwing(player, object);
		}
		if (id == 75462) {
			GodWars.SaradominWall(player, object);
		}
		if (id == 26427) {
			GodWars.SaradominLair(player, object);
		}
		if (id == 26425) {
			GodWars.BandosLair(player, object);
		}
		if (id == 26426) {
			GodWars.ArmadylLair(player, object);
		}
		if (id == 26428) {
			GodWars.ZamorakLair(player, object);
		}
		if (id == 26439) {
			GodWars.ZamorakBridge(player, object);
		}
		if (id == 26384) {
			GodWars.BandosWall(player, object);
		}
		if (id == 75089) {
			GodWars.NexDoor(player, object);
		}
		if (id == 57256) {
			GodWars.NexDown(player, object);
		}
		if (id == 57260) {
			GodWars.NexUp(player, object);
		}
		if (id == 57234) {
			GodWars.NexSlide(player, object);
		}
		if (id == 57258) {
			GodWars.NexDoor2(player, object);
		}
	}

}
