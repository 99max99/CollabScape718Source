package com.rs.game.player.actions.mining;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.Deaths;
import com.rs.game.player.content.ShootingStar;
import com.rs.game.player.content.Skillers;
import com.rs.utils.Misc;
import com.rs.utils.Utils;

public final class Mining extends MiningBase {

	public enum RockDefinitions {

		Clay_Ore(1, 5, 434, 10, 1, 11552, 5, 0), Copper_Ore(1, 17.5, 436, 10, 1, 11552, 5, 0), Tin_Ore(1, 17.5, 438, 15,
				1, 11552, 5, 0), Iron_Ore(15, 35, 440, 15, 1, 11552, 10, 0), Sandstone_Ore(
						35, 30, 6971, 30, 1, 11552, 10, 0), Silver_Ore(20, 40, 442, 25,
								1, 11552, 20, 0), Coal_Ore(30, 50, 453, 50, 10, 11552, 30, 0), Granite_Ore(
										45, 50, 6979, 50, 10, 11552, 20, 0), Gold_Ore(40, 60, 444, 80,
												20, 11554, 40, 0), Mithril_Ore(55, 80, 447, 100, 20, 11552, 60,
														0), Adamant_Ore(70, 95, 449, 130, 25, 11552, 180, 0), 
														Runite_Ore(85, 125, 451, 150, 30, 11552, 360, 0), 
																LRC_Coal_Ore(77, 50, 453, 50, 10, -1, -1, -1), 
																LRC_Gold_Ore(80, 60, 444, 40, 10, -1, -1, -1), 
		CRASHED_STAR(70, 65, 13727, 2, 30, -1, -1, -1),
		GEM_ROCK(40, 65, -1, 80, 15, 11152, 60, 0),
		SAND_STONE(81, 60, 23194, 50, 10, -1, -1, -1),
		LIMESTONE(10, 20, 3211, 15, 1, 4028, 10, 0),
		DIVINE_RUNE_ORE(85, 125, 451, 10, 10, 87290, 360, -1),
		DIVINE_ADAMANTITE_ORE(70, 125, 449, 10, 10, 87289, 360, -1),
		DIVINE_MITHRIL_ORE(55, 125, 447, 10, 10, 87288, 360, -1),
		DIVINE_COAL_ORE(30, 125, 453, 10, 10, 87287, 360, -1),
		DIVINE_IRON_ORE(15, 125, 440, 10, 10, 87286, 360, -1),
		DIVINE_BRONZE_ORE(1, 125, 436, 10, 10, 87285, 360, -1);


		private int level;
		private double xp;
		private int oreId;
		private int oreBaseTime;
		private int oreRandomTime;
		private int emptySpot;
		private int respawnDelay;
		private int randomLifeProbability;

		private RockDefinitions(int level, double xp, int oreId,
				int oreBaseTime, int oreRandomTime, int emptySpot,
				int respawnDelay, int randomLifeProbability) {
			this.level = level;
			this.xp = xp;
			this.oreId = oreId;
			this.oreBaseTime = oreBaseTime;
			this.oreRandomTime = oreRandomTime;
			this.emptySpot = emptySpot;
			this.respawnDelay = respawnDelay;
			this.randomLifeProbability = randomLifeProbability;
		}

		public int getLevel() {
			return level;
		}

		public double getXp() {
			return xp;
		}

		public int getOreId() {
			return oreId;
		}

		public int getOreBaseTime() {
			return oreBaseTime;
		}

		public int getOreRandomTime() {
			return oreRandomTime;
		}

		public int getEmptyId() {
			return emptySpot;
		}

		public int getRespawnDelay() {
			return respawnDelay;
		}

		public int getRandomLifeProbability() {
			return randomLifeProbability;
		}
	}

	private WorldObject rock;
	private RockDefinitions definitions;

	public Mining(WorldObject rock, RockDefinitions definitions) {
		this.rock = rock;
		this.definitions = definitions;
	}

	@Override
	public boolean start(Player player) {
		if (!checkAll(player))
			return false;
		player.getPackets().sendGameMessage("You swing your pickaxe at the rock.", true);
		setActionDelay(player, getMiningDelay(player));
		return true;
	}

	private int getMiningDelay(Player player) {
		int summoningBonus = 0;
		if (player.getFamiliar() != null) {
			if (player.getFamiliar().getId() == 7342 || player.getFamiliar().getId() == 7342)
				summoningBonus += 10;
			else if (player.getFamiliar().getId() == 6832|| player.getFamiliar().getId() == 6831)
				summoningBonus += 1;
		}
		int mineTimer = definitions.getOreBaseTime() - (player.getSkills().getLevel(Skills.MINING) + summoningBonus) - Utils.getRandom(pickaxeTime);
		if (mineTimer < 1 + definitions.getOreRandomTime())
			mineTimer = 1 + Utils.getRandom(definitions.getOreRandomTime());
		mineTimer /= player.getAuraManager().getMininingAccurayMultiplier();
		return mineTimer;
	}

	private boolean checkAll(Player player) {
		if (!hasPickaxe(player)) {
			player.getPackets().sendGameMessage("You need a pickaxe to mine this rock.");
			return false;
		}
		if (!setPickaxe(player)) {
			player.getPackets().sendGameMessage("You dont have the required level to use this pickaxe.");
			return false;
		}
		if (!hasMiningLevel(player))
			return false;
		if (!player.getInventory().hasFreeSlots()) {
			player.getPackets().sendGameMessage("Not enough space in your inventory.");
			return false;
		}
		return true;
	}

	private boolean hasMiningLevel(Player player) {
		if (definitions.getLevel() > player.getSkills().getLevel(Skills.MINING)) {
			player.getPackets().sendGameMessage("You need a mining level of " + definitions.getLevel() + " to mine this rock.");
			return false;
		}
		return true;
	}

	@Override
	public boolean process(Player player) {
		if (player.ChillBlastMining == true) {
			player.setNextAnimation(new Animation(17310));
			player.setNextGraphics(new Graphics(3304));
			return checkRock(player);
		} else {
			player.setNextAnimation(new Animation(emoteId));
		}
		return checkRock(player);
	}

	private boolean usedDeplateAurora;

	@Override
	public int processWithDelay(Player player) {
		addOre(player);
		if(definitions.getEmptyId() != -1) {		
				Skillers manager4 = player.getSkillersManager();
				if (manager4.getTask() != null) {
					if(definitions.getOreId() == player.TASK) {
							manager4.completeTask();
					}
				}
			
			
			if (!usedDeplateAurora
					&& (1 + Math.random()) < player.getAuraManager()
					.getChanceNotDepleteMN_WC()) {
				usedDeplateAurora = true;
			} else if (Utils.getRandom(definitions.getRandomLifeProbability()) == 0 && definitions != RockDefinitions.DIVINE_RUNE_ORE && definitions != RockDefinitions.DIVINE_ADAMANTITE_ORE && definitions != RockDefinitions.DIVINE_MITHRIL_ORE && definitions != RockDefinitions.DIVINE_COAL_ORE && definitions != RockDefinitions.DIVINE_IRON_ORE && definitions != RockDefinitions.DIVINE_BRONZE_ORE) {
				World.spawnTemporaryObject(new WorldObject(definitions.getEmptyId(), rock.getType(), rock.getRotation(), rock.getX(), rock.getY(), rock.getPlane()), definitions.respawnDelay * 600, false);
				player.setNextAnimation(new Animation(-1));
				return -1;
			}
		}
		if (!player.getInventory().hasFreeSlots() && definitions.getOreId() != -1) {
			player.setNextAnimation(new Animation(-1));
			player.getPackets().sendGameMessage("Not enough space in your inventory.");
			return -1;
		}
		return getMiningDelay(player);
	}

	private void addOre(Player player) {

		double xpBoost = 0;
		int idSome = 0;
		if (definitions == RockDefinitions.Granite_Ore) {
			idSome = Utils.getRandom(2) * 2;
			if (idSome == 2)
				xpBoost += 10;
			else if (idSome == 4)
				xpBoost += 25;
		} else if (definitions == RockDefinitions.Sandstone_Ore) {
			idSome = Utils.getRandom(3) * 2;
			xpBoost += idSome / 2 * 10;
	} else if (definitions == RockDefinitions.Runite_Ore) {
		player.runiteOre++;
	} else if (definitions == RockDefinitions.CRASHED_STAR) {
		player.getShootingStar().mineShootingStar(player);
	} else if (definitions == RockDefinitions.DIVINE_BRONZE_ORE) {
		player.getInventory().addItem(438, 1);
	}
		int gem = Misc.random(55);
		if (gem == 1)
		RockDefinitions.GEM_ROCK.oreId = 1617;
		else if (gem >= 2 && gem <= 5)
		RockDefinitions.GEM_ROCK.oreId = 1619;
		else if (gem >= 6 && gem <= 10)
		RockDefinitions.GEM_ROCK.oreId = 1621;
		else if (gem >= 11 && gem <= 18)
		RockDefinitions.GEM_ROCK.oreId = 1623;
		else if (gem >= 19 && gem <= 28)
		RockDefinitions.GEM_ROCK.oreId = 1629;
		else if (gem >= 29 && gem <= 40)
		RockDefinitions.GEM_ROCK.oreId = 1627;
		else if (gem >= 41 && gem <= 55)
		RockDefinitions.GEM_ROCK.oreId = 1625;
		
		RockDefinitions.GEM_ROCK.emptySpot = 11552;
	
		double totalXp = definitions.getXp() + xpBoost;
		if (hasMiningSuit(player))
			totalXp *= 1.025;
		player.getSkills().addXp(Skills.MINING, totalXp);
		player.randomevent(player);
		if (definitions.getOreId() != -1) {
			player.Oreid = definitions.getOreId();
				//if (player.DailyTask == player.Oreid) {
						//	player.sendMessage("this is a test please ignore");
						//	manager3.completeTask();
				//}
					
				
			player.getInventory().addItem(definitions.getOreId() + idSome, 1);
			String oreName = ItemDefinitions.getItemDefinitions(definitions.getOreId() + idSome).getName().toLowerCase();
			player.getPackets().sendGameMessage("You mine some " + oreName + ".", true);
		}
	}


	private boolean hasMiningSuit(Player player) {
		if (player.getEquipment().getHatId() == 20789 && player.getEquipment().getChestId() == 20791
				&& player.getEquipment().getLegsId() == 20790 && player.getEquipment().getBootsId() == 20788)
			return true;
		return false;
	}

    private boolean checkRock(Player player) {
	return World.containsObjectWithId(rock, rock.getId());
    }

}
