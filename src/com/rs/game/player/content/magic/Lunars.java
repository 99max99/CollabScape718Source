package com.rs.game.player.content.magic;

import java.util.ArrayList;
import java.util.List;

import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.Region;
import com.rs.game.World;
import com.rs.game.item.Item;
//import com.rs.game.minigames.ectofuntus.Ectofuntus;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.FillAction.Filler;
import com.rs.game.player.content.farming.Patch;
import com.rs.game.player.content.farming.PatchConstants;
import com.rs.utils.Utils;

public class Lunars {
	
	public static int[] logs =   { 1511, 1521, 6333, 6332  };
	public static int[] planks = { 960,  8778, 8780, 8782 };
	
	public static int[] unstrung = { 1673, 1675, 1677, 1679, 1681, 1683, 1714, 1720, 6579 };
	public static int[] strung   = { 1692, 1694, 1696, 1698, 1700, 1702, 1716, 1722, 6581 };
	
	public static Player[] getNearPlayers(Player player, int distance, int maxTargets) {
		List<Entity> possibleTargets = new ArrayList<Entity>();
		stop: for (int regionId : player.getMapRegionsIds()) {
			Region region = World.getRegion(regionId);
				List<Integer> playerIndexes = region.getPlayerIndexes();
				if (playerIndexes == null)
					continue;
				for (int playerIndex : playerIndexes) {
					Player p2 = World.getPlayers().get(playerIndex);
					if (p2 == null || p2 == player || p2.isDead() || !p2.hasStarted() || p2.hasFinished() 
					 || !p2.withinDistance(player, distance))
						continue;
					possibleTargets.add(p2);
					if (possibleTargets.size() == maxTargets)
						break stop;
				}
		}
		return possibleTargets.toArray(new Player[possibleTargets.size()]);
	}
	
	public static boolean hasUnstrungs(Player player) {
		for (Item item : player.getInventory().getItems().getItems()) {
			if (item == null)
				continue;
			if (getStrungIndex(item.getId()) != -1)
				return true;
		}
		return false;
	}
	
	public static int getStrungIndex(int ammy) {
		for(int i = 0;i < unstrung.length;i++) {
			if (unstrung[i] == ammy)
				return i;
		}
		return -1;
	}
	
	public static int getPlankIdx(int logId) {
		for(int i = 0;i < logs.length;i++) {
			if (logs[i] == logId)
				return i;
		}
		return -1;
	}
	
	public static void openRemoteFarm(Player player) {
		if (!player.canAlch()) {
			player.getPackets().sendGameMessage("There is a 5 second delay to this.");
			return;
		}
		player.setAlchDelay(5000L);
		Patch patch = null;
		int[] names = new int[] { 30, 32, 34, 36, 38, 49, 51, 53, 55, 57, 59,
				  62, 64, 66, 68, 70, 72, 74, 76, 190, 79, 81, 
				  83, 85, 88, 90, 92, 94, 97, 99, 101, 104, 106,
				  108, 110, 115, 117, 119, 121, 123, 125, 131, 
				  127, 129, 2, 173, 175, 177, 182, 184, 186, 188 };
		
		player.getInterfaceManager().sendInterface(1082);
		for (int i = 0;i < names.length;i++) {
			if (i < PatchConstants.WorldPatches.values().length) {
				player.getPackets().sendIComponentText(1082, names[i], PatchConstants.WorldPatches.values()[i].name().replace("_", " ").toLowerCase());
			} else {
				player.getPackets().sendIComponentText(1082, names[i], "");
			}
		}
		for (int i = 0;i < names.length;i++) {
			if (i < player.getFarmings().patches.length) {
				patch = player.getFarmings().patches[i];
				if (patch != null) {
					if (!patch.raked) {
						player.getPackets().sendIComponentText(1082, names[i]+1, "Full of weeds");
					} else if (patch.dead) {
						player.getPackets().sendIComponentText(1082, names[i]+1, "<col=8f13b5>Is dead!");
					} else if (patch.diseased) {
						player.getPackets().sendIComponentText(1082, names[i]+1, "<col=FF0000>Is disased!");
					} else if (patch.healthChecked) {
						player.getPackets().sendIComponentText(1082, names[i]+1, "<col=00FF00>Is ready for health check");
					} else if (patch.grown && patch.yield > 0) {
						player.getPackets().sendIComponentText(1082, names[i]+1, "<col=00FF00>Is fully grown with produce available");
					} else if (patch.grown) {
						player.getPackets().sendIComponentText(1082, names[i]+1, "<col=00FF00>Is fully grown with no produce available");
					} else if (patch.raked) {
						player.getPackets().sendIComponentText(1082, names[i]+1, "Is empty");
					}
				} else {
					player.getPackets().sendIComponentText(1082, names[i]+1, "");
				}
			} else {
				player.getPackets().sendIComponentText(1082, names[i]+1, "");
			}
		}	
	}
	
	public static void handlePlankMake(Player player, Item item) {
		player.getInterfaceManager().openGameTab(7);
		if (!player.canAlch()) {
			return;
		}
		int index = getPlankIdx(item.getId());
		if (index == -1) {
			player.getPackets().sendGameMessage("You can only cast this spell on a log.");
			return;
		}
		
		if (!player.getInventory().containsItem(logs[index], 1))
			return;
		
		if (!Magic.checkSpellRequirements(player, 86, true, Magic.NATURE_RUNE, 1, Magic.ASTRAL_RUNE, 2, Magic.EARTH_RUNE, 15))
			return;
	
		player.setNextAnimation(new Animation(6298));
		player.setNextGraphics(new Graphics(1063, 0, 50));
		player.getInventory().deleteItem(logs[index], 1);
		player.getInventory().addItem(planks[index], 1);
		player.getSkills().addXp(Skills.MAGIC, 90);
		player.setAlchDelay(1100L);
	}
	
	public static void handleVengeance(Player player) {
		if (player.getSkills().getLevel(Skills.MAGIC) < 94) {
			player.getPackets().sendGameMessage("Your Magic level is not high enough for this spell.");
			return;
		} else if (player.getSkills().getLevel(Skills.DEFENCE) < 40) {
			player.getPackets().sendGameMessage("You need a Defence level of 40 for this spell");
			return;
		}
		Long lastVeng = (Long) player.getTemporaryAttributtes().get("LAST_VENG");
		if (lastVeng != null && lastVeng + 30000 > Utils.currentTimeMillis()) {
			player.getPackets().sendGameMessage("You may only cast vengeance once every 30 seconds.");
			return;
		}
		if (!Magic.checkRunes(player, true, Magic.ASTRAL_RUNE, 4, Magic.DEATH_RUNE, 2, Magic.EARTH_RUNE, 10))
			return;
		player.setNextGraphics(new Graphics(726, 0, 100));
		player.setNextAnimation(new Animation(4410));
		player.setCastVeng(true);
		player.getSkills().addXp(Skills.MAGIC, 112);
		player.getTemporaryAttributtes().put("LAST_VENG", Utils.currentTimeMillis());
	}
	
	public static void handleHumidify(Player player) {
		if (hasFillables(player)) {
			if (Magic.checkSpellRequirements(player, 68, true, Magic.ASTRAL_RUNE, 1, Magic.WATER_RUNE, 3, Magic.FIRE_RUNE, 1)) {
				player.setNextGraphics(new Graphics(1061));
				player.setNextAnimation(new Animation(6294));
				player.getSkills().addXp(Skills.MAGIC, 65);
				fillFillables(player);
			}
		} else {
			player.getPackets().sendGameMessage("You need to have something to humidify before using this spell.");
		}
	}
	
	public static void fillFillables(Player player) {
		for (Item item : player.getInventory().getItems().getItems()) {
			if (item == null)
				continue;
			Filler fill = Filler.forId((short) item.getId());
			if (fill != null) {
				if (player.getInventory().containsItem(fill.getEmptyItem().getId(), 1)) {
					player.getInventory().deleteItem(fill.getEmptyItem());
					player.getInventory().addItem(fill.getFilledItem());
				}
			}
		}
	}
	
	public static boolean hasFillables(Player player) {
		for (Item item : player.getInventory().getItems().getItems()) {
			if (item == null)
				continue;
			Filler fill = Filler.forId((short) item.getId());
			if (fill != null) {
				return true;
			}
		}
		return false;
	}

	public static void handleStringJewelry(Player player) {
		if (hasUnstrungs(player)) {
			if (Magic.checkSpellRequirements(player, 80, true, Magic.ASTRAL_RUNE, 2, Magic.EARTH_RUNE, 10, Magic.WATER_RUNE, 5)) {
				player.setNextGraphics(new Graphics(728, 0, 100));
				player.setNextAnimation(new Animation(4412));
				player.getSkills().addXp(Skills.MAGIC, 87);
				for (Item item : player.getInventory().getItems().getItems()) {
					if (item == null)
						continue;
					int strungId = getStrungIndex(item.getId());
					if (strungId != -1) {
						player.getInventory().deleteItem(item.getId(), 1);
						player.getInventory().addItem(strung[strungId], 1);
					}
				}
			}
		} else {
			player.getPackets().sendGameMessage("You need to have unstrung jewelry to cast this spell.");
		}
	}

	public static void handleRestorePotionShare(Player player, Item item) {
		// TODO Auto-generated method stub
		
	}

	public static void handleLeatherMake(Player player, Item item) {
		// TODO Auto-generated method stub
		
	}

	public static void handleBoostPotionShare(Player player, Item item) {
		// TODO Auto-generated method stub
		
	}

	public static void handleBakePie(Player player) {
		// TODO Auto-generated method stub
		
	}

	public static void handleCureMe(Player player) {
		if (player.getPoison().isPoisoned()) {
			if (Magic.checkSpellRequirements(player, 71, true, Magic.ASTRAL_RUNE, 2, 564, 2)) {
				player.setNextGraphics(new Graphics(729, 0, 100));
				player.setNextAnimation(new Animation(4409));
				player.getSkills().addXp(Skills.MAGIC, 69);
				player.addPoisonImmune(1000);
			}
		} else {
			player.getPackets().sendGameMessage("You are not poisoned.");
		}
	}

	public static void handleHunterKit(Player player) {
		// TODO Auto-generated method stub
		
	}

	public static void handleCureGroup(Player player) {
		if (!player.canAlch())
			return;
		if (Magic.checkSpellRequirements(player, 74, true, Magic.ASTRAL_RUNE, 2, 564, 2)) {
			player.getActionManager().addActionDelay(4);
			player.setNextGraphics(new Graphics(729, 0, 100));
			player.setNextAnimation(new Animation(4411));
			player.addPoisonImmune(1000);
			player.setAlchDelay(1100L);
			for (Player other : getNearPlayers(player, 1, 10)) {
				if (other.getPoison().isPoisoned()) {
					player.setNextGraphics(new Graphics(729, 0, 100));
					player.addPoisonImmune(1000);
					player.getPackets().sendGameMessage("Your poison has been cured!");
				}
			}
		}
	}

	public static void handleSuperGlassMake(Player player) {
		if (!player.getInventory().containsItem(401, 1) ||
			!player.getInventory().containsItem(1783, 1)) {
			player.getPackets().sendGameMessage("You need seaweed and buckets of sand to make molten glass.");
			return;
		}
		if (Magic.checkSpellRequirements(player, 77, true, Magic.ASTRAL_RUNE, 2, Magic.FIRE_RUNE, 6, Magic.AIR_RUNE, 10)) {
			player.setNextGraphics(new Graphics(729, 0, 100));
			player.setNextAnimation(new Animation(4413));
			player.getSkills().addXp(Skills.MAGIC, 78);
			for(int i = 0;i < 15;i++) {
				if (player.getInventory().containsItem(401, 1) && player.getInventory().containsItem(1783, 1)) {
					player.getInventory().deleteItem(401, 1);
					player.getInventory().deleteItem(1783, 1);
					player.getInventory().addItem(1775, 1);
					//player.getInventory().addItem(Ectofuntus.EMPTY_BUCKET, 1);
				}
			}
		}
	}

	public static void handleRemoteFarm(Player player) {
		openRemoteFarm(player);
	}

	public static void handleDream(Player player) {
		// TODO Auto-generated method stub
		
	}

	public static void handleMagicImbue(Player player) {
		// TODO Auto-generated method stub
		
	}

	public static void handleDisruptionShield(Player player) {
		// TODO Auto-generated method stub
		
	}

	public static void handleGroupVengeance(Player player) {
		Long lastVeng = (Long) player.getTemporaryAttributtes().get("LAST_VENG");
		if (lastVeng != null && lastVeng + 30000 > Utils.currentTimeMillis())  {
			player.getPackets().sendGameMessage("You may only cast vengeance spells once every 30 seconds.");
			return;
		}
		if (Magic.checkSpellRequirements(player, 95, true, Magic.ASTRAL_RUNE, 4, Magic.DEATH_RUNE, 3, Magic.EARTH_RUNE, 11)) {
			player.setNextGraphics(new Graphics(725, 0, 100));
			player.setNextAnimation(new Animation(4411));
			player.setCastVeng(true);
			player.getSkills().addXp(Skills.MAGIC, 112);
			player.getTemporaryAttributtes().put("LAST_VENG", Utils.currentTimeMillis());
			for (Player other : getNearPlayers(player, 3, 10)) {
				Long otherVeng = (Long) other.getTemporaryAttributtes().get("LAST_VENG");
				if (otherVeng != null && otherVeng + 30000 > Utils.currentTimeMillis()) 
					continue;
				other.setNextGraphics(new Graphics(725, 0, 100));
				other.setCastVeng(true);
				other.getTemporaryAttributtes().put("LAST_VENG", Utils.currentTimeMillis());
			}
		}
	}

	public static void handleHealGroup(Player player) {
		// TODO Auto-generated method stub
		
	}

	public static void handleSpellbookSwap(Player player) {
		// TODO Auto-generated method stub
		
	}
	
}
