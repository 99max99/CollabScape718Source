package com.rs.game.player.content.dungeoneering;

import java.util.HashMap;
import java.util.Map;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

public class DungeonRewards {
	
	public enum DungeonReward {
		BONECRUSHER(18337, 0, 21, 34000),
		HERBICIDE(19675, 5, 21, 34000),
		GEM_BAG(18338, 10, 25, 2000),
		COAL_BAG(18339, 35, 35, 4000),
		MAGICAL_BLASTBOX(19671, 30, 30, 40000),
		CELESTIAL_SURGEBOX(19868, 135, 70, 65000),
		LONGBOW_SIGHT(18330, 45, 45, 10000),
		LAW_STAFF(18342, 45, 75, 10000),
		NATURE_STAFF(18341, 100, 53, 12500),
		ANTI_POISON_TOTEM(18340, 110, 60, 44000),
		SCROLL_OF_LIFE(18336, 15, 25, 10000),
		SCROLL_OF_CLEANSING(19890, 40, 49, 20000),
		SCROLL_OF_EFFICIENCY(19670, 105, 55, 20000),
		SCROLL_OF_AUGURY(18344, 150, 77, 153000),
		SCROLL_OF_RIGOUR(18839, 145, 74, 140000),
		SCROLL_OF_RENEWAL(18343, 125, 65, 107000),
		MERCENARY_GLOVES(18347, 140, 73, 48500),
		TOME_OF_FROST(18346, 80, 48, 43000),
		ARCANE_PULSE_NECKLACE(18333, 20, 30, 6500),
		GRAVITE_SHORTBOW(18373, 70, 45, 40000),
		GRAVITE_LONGSWORD(18367, 55, 45, 40000),
		GRAVITE_RAPIER(18365, 50, 45, 40000),
		GRAVITE_STAFF(18371, 65, 45, 40000),
		GRAVITE_2H(18369, 60, 45, 40000),
		SPIRIT_CAPE(19893, 95, 50, 45000),
		AMULET_OF_ZEALOT(19892, 85, 48, 40000),
		ARCANE_BLAST_NECKLACE(18334, 90, 50, 15500),
		RING_OF_VIGOUR(19669, 120, 62, 50000),
		ARCANE_STREAM_NECKLACE(18335, 130, 70, 30500),
		CHAOTIC_RAPIER(18349, 155, 80, 200000),
		CHAOTIC_LONGSWORD(18351, 160, 80, 200000),
		CHAOTIC_MAUL(18353, 165, 80, 200000),
		CHAOTIC_STAFF(18355, 170, 80, 200000),
		CHAOTIC_CROSSBOW(18357, 175, 80, 200000),
		CHAOTIC_KITESHIELD(18359, 180, 80, 200000),
		EAGLE_EYE_KITESHIELD(18361, 185, 80, 200000),
		FARSEER_KITESHIELD(18363, 190, 80, 200000),
		SNEAKERPEEPER(19894, 195, 80, 85000),
		TWISTEDNECKLACE(19886, 25, 30, 8500),
		DRAGONTOOTHNECKLACE(19887, 115, 60, 17000),
		DEMONHORNNECKLACE(19888, 200, 90, 35000);

		private static Map<Integer, DungeonReward> monsters = new HashMap<Integer, DungeonReward>();

		public static DungeonReward forId(int id) {
			return monsters.get(id);
		}

		static {
			for (DungeonReward monster : DungeonReward.values())
				monsters.put(monster.slotId, monster);
		}

		private int id;
		private int req;
		private int cost;
		private int slotId;
		private String name;

		private DungeonReward(int id, int slotId, int req, int cost) {
			this.id = id;
			this.req = req;
			this.cost = cost;
			this.slotId = slotId;
			this.name = ItemDefinitions.getItemDefinitions(id).getName();
		}

		public int getId() {
			return id;
		}
		
		public String getName() {
			return name;
		}
		
		public int getCost() {
			return cost;
		}
		
		public int getSlotId() {
			return slotId;
		}

		public int getRequirement() {
			return req;
		}
	}
	
	public static void openRewardsShop(Player player) {
		player.getInterfaceManager().sendInterface(940);
		player.getPackets().sendIComponentSettings(940, 2, 0, 205, 1278);
		refresh(player);
	}
	
	public static void refresh(Player player) {
		player.getPackets().sendIComponentText(940, 31, ""+player.getDungTokens());
	}
	
	public static void handleButtons(Player player, int componentId, int slotId, int packetId) {
		if (componentId == 64 && packetId == 14) {
			if (player.getTemporaryAttributtes().get("dungReward") != null) {
				DungeonReward reward = (DungeonReward) player.getTemporaryAttributtes().get("dungReward");
				if (reward != null) {
					if (player.getSkills().getLevelForXp(Skills.DUNGEONEERING) < reward.getRequirement()) {
						player.getPackets().sendGameMessage("You need "+reward.getRequirement()+" dungeoneering to buy this reward.");
						return;
					}
					if (player.getDungTokens() < reward.getCost()) {
						player.getPackets().sendGameMessage("You need "+reward.getCost()+" dungeoneering tokens to buy this reward.");
						return;
					}
					player.getDialogueManager().startDialogue("DungRewardConfirm", reward);
				} else {
					player.getPackets().sendGameMessage("You must choose a reward before trying to buy something.");
				}
			}
			return;
		}
		if (componentId == 2) {
			DungeonReward reward = DungeonReward.forId(slotId);
			if (reward == null) {
				player.getPackets().sendGameMessage("That reward is not added yet. "+slotId);
				return;
			} else {
				player.getTemporaryAttributtes().put("dungReward", reward);
				player.getPackets().sendGameMessage(reward.getName()+" requires "+ reward.getRequirement() +" dungeoneering and costs "+reward.getCost()+" dungeoneering tokens.");
			}
		}
	}

}