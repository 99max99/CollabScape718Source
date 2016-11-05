package com.rs.game.player.content;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.minigames.WarriorsGuild;
import com.rs.game.player.Player;
import com.rs.game.player.QuestManager.Quests;

public class ItemConstants {
	
    public static boolean isDestroy(Item item) {
	return item.getDefinitions().isDestroyItem() || item.getDefinitions().isLended();
    }

	public static int getDegradeItemWhenWear(int id) {
		// pvp armors
		if (id == 13958 || id == 13961 || id == 13964 || id == 13967
				|| id == 13970 || id == 13973 || id == 13858 || id == 13861
				|| id == 13864 || id == 13867 || id == 13870 || id == 13873
				|| id == 13876 || id == 13884 || id == 13887 || id == 13890
				|| id == 13893 || id == 13896 || id == 13899 || id == 13902
				|| id == 13905 || id == 13908 || id == 13911 || id == 13914
				|| id == 13917 || id == 13920 || id == 13923 || id == 13926
				|| id == 13929 || id == 13932 || id == 13935 || id == 13938
				|| id == 13941 || id == 13944 || id == 13947 || id == 13950
				|| id == 13958)
			return id + 2; // if you wear it it becomes corrupted LOL
		return -1;
	}
	

	// return amt of charges
	public static int getItemDefaultCharges(int id) {
		// pvp armors
		if (id == 13910 || id == 13913 || id == 13916 || id == 13919
				|| id == 13922 || id == 13925 || id == 13928 || id == 13931
				|| id == 13934 || id == 13937 || id == 13940 || id == 13943
				|| id == 13946 || id == 13949 || id == 13952)
			return 1500;
		if (id == 13960 || id == 13963 || id == 13966 || id == 13969
				|| id == 13972 || id == 13975)
			return 3000;
		if (id == 13860 || id == 13863 || id == 13866 || id == 13869
				|| id == 13872 || id == 13875 || id == 13878 || id == 13886
				|| id == 13889 || id == 13892 || id == 13895 || id == 13898
				|| id == 13901 || id == 13904 || id == 13907 || id == 13960)
			return 6000; // 1hour
		// nex armors
		if (id == 20137 || id == 20141 || id == 20145 || id == 20149
				|| id == 20153 || id == 20157 || id == 20161 || id == 20165
				|| id == 20169 || id == 20173)
			return 60000;
		return -1;
	}

	// return what id it degrades to, -1 for disapear which is default so we
	// dont add -1
	public static int getItemDegrade(int id) {
		if (id == 11285) // DFS
			return 11283;
		// nex armors
		if (id == 20137 || id == 20141 || id == 20145 || id == 20149
				|| id == 20153 || id == 20157 || id == 20161 || id == 20165
				|| id == 20169 || id == 20173)
			return id + 1;
		return -1;
	}

	public static int getDegradeItemWhenCombating(int id) {
		// nex armors
		if (id == 20135 || id == 20139 || id == 20143 || id == 20147
				|| id == 20151 || id == 20155 || id == 20159 || id == 20163
				|| id == 20167 || id == 20171)
			return id + 2;
		return -1;
	}

	public static boolean itemDegradesWhileHit(int id) {
		if (id == 2550)
			return true;
		return false;
	}

	public static boolean itemDegradesWhileWearing(int id) {
		String name = ItemDefinitions.getItemDefinitions(id).getName()
				.toLowerCase();
		if (name.contains("c. dragon") || name.contains("corrupt dragon")
				|| name.contains("vesta's") || name.contains("statius'")
				|| name.contains("morrigan's") || name.contains("zuriel's"))
			return true;
		return false;
	}

	public static boolean itemDegradesWhileCombating(int id) {
		String name = ItemDefinitions.getItemDefinitions(id).getName()
				.toLowerCase();
		// nex armors
		if (name.contains("torva") || name.contains("pernix")
				|| name.contains("virtux") || name.contains("zaryte"))
			return true;
		return false;
	}

	@SuppressWarnings("unused")
	public static boolean canWear(Item item, Player player) {
		if (player.getRights() == 2)
			return true;
		if (player.getRights() == 7)
			return true;
		if ((item.getId() == 20769 || item.getId() == 20771)) {
			if (!player.isCompletedFightKiln()) {
				player.getPackets()
						.sendGameMessage(
								"");
				return true;
			}
			if (!player.isKilledQueenBlackDragon()) {
				player.getPackets()
						.sendGameMessage(
								"");
				return true;
			}
		} else if (item.getId() == 6570) {
			if (!player.isCompletedFightCaves()) {
				player.getPackets()
						.sendGameMessage(
								"You must complete the fightcaves to wear this.");
				return false;
			}
		} else if (item.getId() == 8856) {
		    if (!WarriorsGuild.inCatapultArea(player)) {
			player.getPackets().sendGameMessage("You may not equip this shield outside of the catapult room in the Warrior's Guild.");
			return false;
		    }
		} else if (item.getId() == 14642 || item.getId() == 14645
				|| item.getId() == 15433 || item.getId() == 15435
				|| item.getId() == 14641 || item.getId() == 15432
				|| item.getId() == 15434) {
			if (!player.getQuestManager().completedQuest(Quests.NOMADS_REQUIEM)) {
				player.getPackets()
						.sendGameMessage(
								"");
				return true;
			}
		}
		String itemName = item.getName();
		/*
		 * if (itemName.contains("goliath gloves") ||
		 * itemName.contains("spellcaster glove") ||
		 * itemName.contains("swift glove")) { if
		 * (player.getDominionTower().getKilledBossesCount() < 50) {
		 * player.getPackets().sendGameMessage(
		 * "You need to have kill atleast 50 bosses in the Dominion tower to wear these gloves."
		 * ); return true; } }
		 */
		return true;
	}

	public static boolean isTradeable(Item item) {
		if (item.getDefinitions().getName().toLowerCase()
				.contains("flaming skull"))
			return false;
		if (item.getDefinitions().isDestroyItem()
				|| item.getDefinitions().isLended()
				|| ItemConstants.getItemDefaultCharges(item.getId()) != -1)
			return false;
		String name = ItemDefinitions.getItemDefinitions(item.getId())
				.getName().toLowerCase();
		if (name.contains("fire cape")
				|| name.contains("tokhaar") || name.contains("defender")
				|| name.contains("lucky")|| name.contains("lamp")
				|| name.contains("spin ticket") || name.contains("ceremonial")
				|| name.contains("diamond jubilee") || name.contains("souvenir")
				|| name.contains("diamond sceptre") || name.contains("diamond crown") || name.contains("token"))
			return false;
		if (name.contains("aura") || name.contains("supreme"))
			return true;
		if (name.contains("goliath") || name.contains("swift") || name.contains("spellcaster"))
			return true;
		switch (item.getId()) {
		case 6570:
			return false;
		case 15440:
			return false;
		case 15439:
			return false;
		case 14936:
			return false;
		case 14937:
			return false;
		case 14938:
			return false;
		case 14939:
			return false;
		case 11323:
			return false;
		case 4251:
			return false;
		case 11328:
			return false;
		case 11329:
			return false;
		case 11330:
			return false;
		case 11331:
			return false;
		case 11332:
			return false;
		case 11333:
			return false;
		case 773:
			return false;
			
		case 455:
			return false;
		default:
			return true;
		}
	}
}
