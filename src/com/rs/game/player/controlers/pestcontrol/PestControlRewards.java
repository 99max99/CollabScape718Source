package com.rs.game.player.controlers.pestcontrol;

import com.rs.game.player.Player;

public class PestControlRewards {

	public static void openRewardsInterface(Player player) {
		player.getPackets().sendIComponentText(267, 93, "Void Knights' Reward Options");
		player.getPackets().sendIComponentText(267, 71, " ");
		player.getPackets().sendIComponentText(267, 72, " ");
		player.getPackets().sendIComponentText(267, 81, " ");
		player.getPackets().sendIComponentText(267, 74, "Elite Void top");
		player.getPackets().sendIComponentText(267, 78, "500 Pts");
		player.getPackets().sendIComponentText(267, 73, "Elite Void robe");
		player.getPackets().sendIComponentText(267, 84, "500 Pts");
		player.getPackets().sendIComponentText(267, 101, "Your points:");
		player.getPackets().sendIComponentText(267, 75, " ");
		player.getPackets().sendIComponentText(267, 76, " ");
		player.getPackets().sendIComponentText(267, 77, " ");
		player.getPackets().sendIComponentText(267, 82, " ");
		player.getPackets().sendIComponentText(267, 83, " ");
		player.getPackets().sendIComponentText(267, 79, " ");
		player.getPackets().sendIComponentText(267, 80, " ");
		player.getPackets().sendIComponentText(267, 85, " ");
		player.getPackets().sendIComponentText(267, 86, " ");
		player.getPackets().sendIComponentText(267, 104, "" + player.getPestPoints());
		player.getInterfaceManager().sendInterface(267);
	}

	public static void handleMainButtons(Player player, int componentId, int slotId) {
		switch (componentId) {
		case 41:
			if (player.getPestPoints() < 250) {
				player.sm("You need atleast 250 Pest Control points to buy this.");
				return;
			} else if (player.getPestPoints() >= 250) {
				player.setPestPoints(player.getPestPoints() - 250);
				player.getInventory().addItem(8841, 1);
				player.sm("You buy a Void Knight's mace.");
			}
			player.getPackets().sendIComponentText(267, 104, "" + player.getPestPoints());
			break;
		case 42:
			if (player.getPestPoints() < 250) {
				player.sm("You need atleast 250 Pest Control points to buy this.");
				return;
			} else if (player.getPestPoints() >= 250) {
				player.setPestPoints(player.getPestPoints() - 250);
				player.getInventory().addItem(8839, 1);
				player.sm("You buy a Void Knight's top.");
			}
			player.getPackets().sendIComponentText(267, 104, "" + player.getPestPoints());
			break;
		case 43:
			if (player.getPestPoints() < 250) {
				player.sm("You need atleast 250 Pest Control points to buy this.");
				return;
			} else if (player.getPestPoints() >= 250) {
				player.setPestPoints(player.getPestPoints() - 250);
				player.getInventory().addItem(8840, 1);
				player.sm("You buy a Void knight's robe.");
			}
			player.getPackets().sendIComponentText(267, 104, "" + player.getPestPoints());
			break;
		case 44:
			if (player.getPestPoints() < 150) {
				player.sm("You need atleast 150 Pest Control points to buy this.");
				return;
			} else if (player.getPestPoints() >= 200) {
				player.setPestPoints(player.getPestPoints() - 150);
				player.getInventory().addItem(8842, 1);
				player.sm("You buy a Void knight's gloves.");
			}
			player.getPackets().sendIComponentText(267, 104, "" + player.getPestPoints());
			break;
		case 67:
			if (player.getPestPoints() < 200) {
				player.sm("You need atleast 200 Pest Control points to buy this.");
				return;
			} else if (player.getPestPoints() >= 200) {
				player.setPestPoints(player.getPestPoints() - 200);
				player.getInventory().addItem(11674, 1);
				player.sm("You buy a Void knight's Mage helm.");
			}
			player.getPackets().sendIComponentText(267, 104, "" + player.getPestPoints());
			break;
		case 68:
			if (player.getPestPoints() < 200) {
				player.sm("You need atleast 200 Pest Control points to buy this.");
				return;
			} else if (player.getPestPoints() >= 200) {
				player.setPestPoints(player.getPestPoints() - 200);
				player.getInventory().addItem(11675, 1);
				player.sm("You buy a Void knight's Ranger helm.");
			}
			player.getPackets().sendIComponentText(267, 104, "" + player.getPestPoints());
			break;
		case 69:
			if (player.getPestPoints() < 200) {
				player.sm("You need atleast 200 Pest Control points to buy this.");
				return;
			} else if (player.getPestPoints() >= 200) {
				player.setPestPoints(player.getPestPoints() - 200);
				player.getInventory().addItem(11676, 1);
				player.sm("You buy a Void knight's Melee helm.");
			}
			player.getPackets().sendIComponentText(267, 104, "" + player.getPestPoints());
			break;
		case 78:
			if (player.getPestPoints() < 500) {
				player.sm("You need atleast 500 Pest Control points to buy this.");
				return;
			} else if (player.getPestPoints() >= 500) {
				player.setPestPoints(player.getPestPoints() - 500);
				player.getInventory().addItem(19787, 1);
				player.sm("You buy an Elite Void knight's top.");
			}
			player.getPackets().sendIComponentText(267, 104, "" + player.getPestPoints());
			break;
		case 84:
			if (player.getPestPoints() < 500) {
				player.sm("You need atleast 500 Pest Control points to buy this.");
				return;
			} else if (player.getPestPoints() >= 500) {
				player.setPestPoints(player.getPestPoints() - 500);
				player.getInventory().addItem(19788, 1);
				player.sm("You buy an Elite Void knight's bottoms.");
			}
			player.getPackets().sendIComponentText(267, 104, "" + player.getPestPoints());
			break;
		}
	}

}
