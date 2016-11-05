package com.rs.game;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.rs.Launcher;
import com.rs.Settings;
import com.rs.cores.CoresManager;
import com.rs.game.Hit.HitLook;
import com.rs.game.item.FloorItem;
import com.rs.game.item.Item;
import com.rs.game.minigames.GodWarsBosses;
import com.rs.game.minigames.ZarosGodwars;
import com.rs.game.minigames.clanwars.FfaZone;
import com.rs.game.minigames.clanwars.RequestController;
import com.rs.game.minigames.duel.DuelControler;
import com.rs.game.mysql.DatabaseManager;
import com.rs.game.npc.NPC;
import com.rs.game.npc.corp.CorporealBeast;
import com.rs.game.npc.dragons.KingBlackDragon;
import com.rs.game.npc.godwars.GodWarMinion;
import com.rs.game.npc.godwars.armadyl.GodwarsArmadylFaction;
import com.rs.game.npc.godwars.armadyl.KreeArra;
import com.rs.game.npc.godwars.bandos.GeneralGraardor;
import com.rs.game.npc.godwars.bandos.GodwarsBandosFaction;
import com.rs.game.npc.godwars.saradomin.CommanderZilyana;
import com.rs.game.npc.godwars.saradomin.GodwarsSaradominFaction;
import com.rs.game.npc.godwars.zammorak.GodwarsZammorakFaction;
import com.rs.game.npc.godwars.zammorak.KrilTstsaroth;
import com.rs.game.npc.godwars.zaros.Nex;
import com.rs.game.npc.godwars.zaros.NexMinion;
import com.rs.game.npc.kalph.KalphiteQueen;
import com.rs.game.npc.nomad.FlameVortex;
import com.rs.game.npc.nomad.Nomad;
import com.rs.game.npc.others.Bork;
import com.rs.game.npc.others.ItemHunterNPC;
import com.rs.game.npc.others.LivingRock;
import com.rs.game.npc.others.Lucien;
import com.rs.game.npc.others.MasterOfFear;
import com.rs.game.npc.others.MercenaryMage;
import com.rs.game.npc.others.Revenant;
import com.rs.game.npc.others.TormentedDemon;
import com.rs.game.npc.others.Werewolf;
import com.rs.game.npc.slayer.GanodermicBeast;
import com.rs.game.npc.slayer.Strykewyrm;
import com.rs.game.npc.others.Elemental;
import com.rs.game.player.OwnedObjectManager;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.BoxAction.HunterNPC;
import com.rs.game.player.actions.objects.EvilTree;
import com.rs.game.player.content.ItemConstants;
import com.rs.game.player.content.LivingRockCavern;
import com.rs.game.player.content.PenguinEvent;
import com.rs.game.player.content.SinkHoles;
import com.rs.game.player.content.TriviaBot;
import com.rs.game.player.content.XPWell;
//import com.rs.game.player.content.GrandExchange.Offers;
import com.rs.game.player.content.botanybay.BotanyBay;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.AntiFlood;
import com.rs.utils.IPBanL;
import com.rs.utils.KillStreakRank;
import com.rs.utils.Logger;
import com.rs.utils.Misc;
import com.rs.utils.PkRank;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;

import com.rs.game.WorldTile;
import com.rs.game.player.controlers.dung.RuneDungGame;
import com.rs.game.WorldObject;
import com.rs.game.minigames.WarriorsGuild;
import com.rs.game.npc.glacor.Glacor;

public final class World {
	
	
	public static Object fucker;
    public static int wellAmount;
    public static boolean wellActive = false;
	public static int exiting_delay;
	public static long exiting_start;
	private static DatabaseManager database = new DatabaseManager();
	
	public static DatabaseManager database() {
		return database;
	}
    public static int getWellAmount() {
        return wellAmount;
    }
 
    public static void addWellAmount(String displayName, int amount) {
        wellAmount += amount;
        sendWorldMessage("<col=FF0000>" + displayName + " has contributed " + NumberFormat.getNumberInstance(Locale.US).format(amount) + " GP to the XP well! Progress now: " + ((World.getWellAmount() * 100) / Settings.WELL_MAX_AMOUNT) + "%!", false);
    }
 
    private static void setWellAmount(int amount) {
        wellAmount = amount;
    }
 
    public static void resetWell() {
        wellAmount = 0;
        wellActive = false;
        sendWorldMessage("<col=FF0000>The XP well has been reset!", false);
    }
 
    public static boolean isWellActive() {
        return wellActive;
    }
 
    public static void setWellActive(boolean wellActive) {
        World.wellActive = wellActive;
    }
    public static void loadWell() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("./data/well/data.txt"));
        String line = null;
        while ((line = reader.readLine()) != null) {
            String[] args = line.split(" ");
            if (args[0].contains("true")) {
                World.setWellActive(true);
                XPWell.taskTime = Integer.parseInt(args[1]);
                XPWell.taskAmount = Integer.parseInt(args[1]);
                XPWell.setWellTask();
            } else {
                setWellAmount(Integer.parseInt(args[1]));
            }
        }
    }
	private static final EntityList<Player> players = new EntityList<Player>(
			Settings.PLAYERS_LIMIT);

	private static final EntityList<NPC> npcs = new EntityList<NPC>(
			Settings.NPCS_LIMIT);
	private static final Map<Integer, Region> regions = Collections
			.synchronizedMap(new HashMap<Integer, Region>());

	// private static final Object lock = new Object();
	
    public static final boolean containsObjectWithId(WorldTile tile, int id) {
	return getRegion(tile.getRegionId()).containsObjectWithId(tile.getPlane(), tile.getXInRegion(), tile.getYInRegion(), id);
    }

    public static final WorldObject getObjectWithId(WorldTile tile, int id) {
	return getRegion(tile.getRegionId()).getObjectWithId(tile.getPlane(), tile.getXInRegion(), tile.getYInRegion(), id);
    }

	
	public static NPC getNPC(int npcId) {
		for (NPC npc : getNPCs()) {
			if(npc.getId() == npcId) {
				return npc;
			}
		}
		return null;
	}
	
	
	
	private static BotanyBay botanyBay;
	
	public static BotanyBay getBotanyBay() {
		return botanyBay;
	}
	
	private static void growPatchesTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					for (Player player : players) {
						if (player != null && player.getFarmings() != null && !player.hasFinished()) {
							player.getFarmings().growAllPatches(player);
						}
					}
				} catch (Throwable e) {
				}
			}
		}, 5, 5, TimeUnit.MINUTES);
	}

	
	public static final Player get(int index) {
		for (Player player : getPlayers()) {
			if (player == null)
				continue;
			if (player.definition().index() == index)
				return player;
		}
		return null;
	}
	
	public static void spinPlate(final Player player) {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override 
			public void run() {
				try {
	if(player.spinTimer > 0) {
		player.spinTimer--;
	}
	if(player.spinTimer == 1) {
		if(Misc.random(2) == 1) {
			player.setNextAnimation(new Animation(1906));
			player.getInventory().deleteItem(4613, 1);
			addGroundItem(new Item(4613, 1), new WorldTile(player.getX(), player.getY(), player.getPlane()), player, false, 180, true);
		}
	}
				} catch (Throwable e) {
					Logger.handle(e);
	}
			}
		}, 0, 1000);
	}
	
	public static void addTime(final Player player) {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					player.onlinetime++;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 60000);
	}
	
/*	public static void depletePendant(final Player player) {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					if (player.pendantTime == 55)
						player.sm("You have 5 more minutes until your pendant depletes.");
					if (player.pendantTime == 60) {
						player.getEquipment().getItems().set(2, new Item(24712, 1));
						player.sm("Your pendant has been depleted of its power.");
					}
					if (player.getPendant().hasAmulet()) {
					player.pendantTime++;
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 60000);
	}*/
	
	public static void startSmoke(final Player player) {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
				if (player.getEquipment().getHatId() == 4164 || player.getEquipment().getHatId() == 13277 || player.getEquipment().getHatId() == 13263 || player.getEquipment().getHatId() == 14636 || player.getEquipment().getHatId() == 14637 || player.getEquipment().getHatId() == 15492
					|| player.getEquipment().getHatId() == 15496 || player.getEquipment().getHatId() == 15497 || player.getEquipment().getHatId() == 22528 || player.getEquipment().getHatId() == 22530 || player.getEquipment().getHatId() == 22532 || player.getEquipment().getHatId() == 22534
					|| player.getEquipment().getHatId() == 22536 || player.getEquipment().getHatId() == 22538 || player.getEquipment().getHatId() == 22540 || player.getEquipment().getHatId() == 22542 || player.getEquipment().getHatId() == 22544 || player.getEquipment().getHatId() == 22546
					|| player.getEquipment().getHatId() == 22548 || player.getEquipment().getHatId() == 22550) {
					player.getPackets().sendGameMessage("Your equipment protects you from the smoke.");
				} else {
				if (player.getHitpoints() > 120) {
					player.applyHit(new Hit(player, 120, HitLook.REGULAR_DAMAGE));
					player.getPackets().sendGameMessage("You take damage from the smoke.");
				}
				}
				if (!player.isAtSmokeyArea()) {
					cancel();
				}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 12000);
	}
	
	public static boolean killedTree;
	public static boolean treeEvent;
	
	public static void startEvilTree() {
		WorldObject evilTree = new WorldObject(11922,
				10, 0, 2456,
				2835, 0);	
		final WorldObject deadTree = new WorldObject(12715,
				10, 0, 2456,
				2835, 0);	
		spawnObject(evilTree, true);
		EvilTree.health = 5000;
		treeEvent = true;
		killedTree = false;
		sendWorldMessage("<img=6><col=FFA500><shad=000000>An Evil Tree has appeared nearby Moblishing Armies, talk to a Spirit Tree to reach it!", false);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
				if (EvilTree.health <= 0) {
					spawnTemporaryObject(deadTree, 600000, true);
					killedTree = true;
					sendWorldMessage("<img=6><col=FFA500><shad=000000>The Evil Tree has been defeated!", false);
					executeTree();
					cancel();
					WorldTasksManager.schedule(new WorldTask() {
						int loop = 0;
						@Override
						public void run() {
							if (loop == 600) {
								treeEvent = false;
								killedTree = false;
								cancel();
							} 
							loop++;
						}
					}, 0, 1);
				}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1000);
	}
	
	public static void executeTree() {
		final int time = Misc.random(3600000, 7200000);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			int loop = 0;
			@Override
			public void run() {
				try {
				if (loop == time) {
				startEvilTree();
				cancel();
				}
				loop++;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1);
	}
	public static void startDesert(final Player player) {
		int mili = 90000;
		if ((player.getEquipment().getHatId() == 6382 && (player.getEquipment().getChestId() == 6384 || player.getEquipment().getChestId() == 6388) && (player.getEquipment().getLegsId() == 6390 || player.getEquipment().getLegsId() == 6386))
		|| (player.getEquipment().getChestId() == 1833 && player.getEquipment().getLegsId() == 1835 && player.getEquipment().getBootsId() == 1837)
		|| ((player.getEquipment().getHatId() == 6392 || player.getEquipment().getHatId() == 6400) && (player.getEquipment().getChestId() == 6394 || player.getEquipment().getChestId() == 6402) && (player.getEquipment().getLegsId() == 6396 || player.getEquipment().getLegsId() == 6398 || player.getEquipment().getLegsId() == 6404 || player.getEquipment().getLegsId() == 6406))
		|| (player.getEquipment().getChestId() == 1844 && player.getEquipment().getLegsId() == 1845 && player.getEquipment().getBootsId() == 1846)) {
			mili = 120000;
		}
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override 
			public void run() {
				try {
				if (!player.isAtDesertArea()) {
					cancel();
				}
				for (int i = 0; i <= 28; i++) {
					evaporate(player);
				}
				if (player.isAtDesertArea()) {
				if (player.getInventory().containsItem(1823, 1)) {
					player.setNextAnimation(new Animation(829));
					player.getInventory().deleteItem(1823, 1);
					player.getInventory().addItem(1825, 1);
					player.getPackets().sendGameMessage("You drink from the waterskin.");
				} else if (player.getInventory().containsItem(1825, 1)) {
					player.setNextAnimation(new Animation(829));
					player.getInventory().deleteItem(1825, 1);
					player.getInventory().addItem(1827, 1);
					player.getPackets().sendGameMessage("You drink from the waterskin.");
				} else if (player.getInventory().containsItem(1827, 1)) {
					player.setNextAnimation(new Animation(829));
					player.getInventory().deleteItem(1827, 1);
					player.getInventory().addItem(1829, 1);
					player.getPackets().sendGameMessage("You drink from the waterskin.");
				} else if (player.getInventory().containsItem(1829, 1)) {
					player.setNextAnimation(new Animation(829));
					player.getInventory().deleteItem(1829, 1);
					player.getInventory().addItem(1831, 1);
					player.getPackets().sendGameMessage("You drink from the waterskin.");
				} else if (player.getInventory().containsItem(6794, 1)) {
					player.setNextAnimation(new Animation(829));
					player.getInventory().deleteItem(6794, 1);
					player.getPackets().sendGameMessage("You eat one of your choc-ices.");
					player.heal(70);
				} else {
					int damage = Misc.random(100, 300);
					if (player.getEquipment().getShieldId() == 18346) {
					player.applyHit(new Hit(player, (damage - 50), HitLook.REGULAR_DAMAGE));
					} else {
					player.applyHit(new Hit(player, damage, HitLook.REGULAR_DAMAGE));
					}
					player.getPackets().sendGameMessage("You take damage from the desert heat.");
				}
				}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, mili);
	}

	public static void evaporate(final Player player) {
		if (player.getInventory().containsItem(227, 1)) {
			player.getInventory().deleteItem(227, 1);
			player.getInventory().addItem(229, 1);
		} else if (player.getInventory().containsItem(1921, 1)) {
			player.getInventory().deleteItem(1921, 1);
			player.getInventory().addItem(1923, 1);
		} else if (player.getInventory().containsItem(1929, 1)) {
			player.getInventory().deleteItem(1929, 1);
			player.getInventory().addItem(1925, 1);
		} else if (player.getInventory().containsItem(1937, 1)) {
			player.getInventory().deleteItem(1937, 1);
			player.getInventory().addItem(1935, 1);
		} else if (player.getInventory().containsItem(4458, 1)) {
			player.getInventory().deleteItem(4458, 1);
			player.getInventory().addItem(1980, 1);
		}
			
	}
	


	public static final void init() {
		// addLogicPacketsTask();
		//spawnStar();
		startEvilTree();
		bossRaid();
		penguinHS();
		sinkHoles();
		growPatchesTask();
		autoEvent();
		addTriviaBotTask();
		addRestoreRunEnergyTask();
		addDrainPrayerTask();
		addRestoreHitPointsTask();
		addRestoreSkillsTask();
		addRestoreSpecialAttackTask();
		addRestoreShopItemsTask();
		addSummoningEffectTask();
		addOwnedObjectsTask();
		LivingRockCavern.init();
		addListUpdateTask();
		WarriorsGuild.init();
	}

	/*
	 * private static void addLogicPacketsTask() {
	 * CoresManager.fastExecutor.scheduleAtFixedRate(new TimerTask() {
	 * 
	 * @Override public void run() { for(Player player : World.getPlayers()) {
	 * if(!player.hasStarted() || player.hasFinished()) continue;
	 * player.processLogicPackets(); } }
	 * 
	 * }, 300, 300); }
	 */
	
	public static List<WorldTile> restrictedTiles = new ArrayList<WorldTile>();
	
	public static void deleteObject(WorldTile tile){
		restrictedTiles.add(tile);
	}
	
	private static void addOwnedObjectsTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					OwnedObjectManager.processAll();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1, TimeUnit.SECONDS);
	}
	
	public static int star = 0;
	
	private static final void addListUpdateTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead()
								|| !player.isRunning())
							continue;
						player.getPackets().sendIComponentText(751, 16, "Players Online: <col=00FF00>" + getPlayers().size());
						if(!(player.getControlerManager().getControler() instanceof RuneDungGame) && player.isInDung()){
							for (Item item : player.getInventory().getItems().toArray()) {
								if (item == null) continue;
								player.getInventory().deleteItem(item);
								player.setNextWorldTile(new WorldTile(3450, 3718, 0));
							}
							for (Item item : player.getEquipment().getItems().toArray()) {
								if (item == null) continue;
								player.getEquipment().deleteItem(item.getId(), item.getAmount());
							}
							if (player.getFamiliar() != null) {
								if (player.getFamiliar().getBob() != null)
									player.getFamiliar().getBob().getBeastItems().clear();
								player.getFamiliar().dissmissFamiliar(false);
							}
							player.setInDung(false);
							}
					}

				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 10);
	}
	
	//Automated Events
	public static boolean bandos;
	public static boolean armadyl;
	public static boolean zamorak;
	public static boolean saradomin;
	public static boolean dungeoneering;
	public static boolean cannonball;
	public static boolean doubleexp;
	public static boolean nex;
	public static boolean sunfreet;
	public static boolean corp;
	public static boolean doubledrops;
	public static boolean slayerpoints;
	public static boolean moreprayer;
	public static boolean quadcharms;
	
	public static void autoEvent() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				bandos = false;
				armadyl = false;
				zamorak = false;
				saradomin = false;
				dungeoneering = false;
				cannonball = false;
				doubleexp = false;
				nex = false;
				sunfreet = false;
				corp = false;
				doubledrops = false;
				slayerpoints = false;
				moreprayer = false;
				quadcharms = false;
				try {
					int event = Misc.random(225);
					if (event >= 1 && event <= 15) {
						bandos = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Bandos! No Kill Count required!", false);
					} else if (event >= 16 && event <= 30) {
						armadyl = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Armadyl! No Kill Count required!", false);
					} else if (event >= 31 && event <= 45) {
						zamorak = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Zamorak! No Kill Count required!", false);	
					} else if (event >= 46 && event <= 60) {
						saradomin = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Saradomin! No Kill Count required!", false);	
					} else if (event >= 61 && event <= 65) {
						dungeoneering = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Group Dungeoneering event! Come explore Dungeons with Double EXP and Tokens!", false);	
					} else if (event >= 66 && event <= 75) {
						cannonball = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] The furnaces have improved, take advantage to make 2x the amount of Cannonballs!", false);	
					} else if (event == 76 || event == 80) {
						doubleexp = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Enjoy some Double Exp in every skill! Take advantage to max out!", false);	
					} else if (event >= 81 && event <= 100) {
						nex = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Nex! Get your torva now!", false);	
					} else if (event >= 101 && event <= 120) {
						sunfreet = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Sunfreet! Get your riches now!", false);	
					} else if (event >= 121 && event <= 145) {
						corp = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Mass Bossing event at Corp! Get your sigils now!", false);	
					} else if (event == 146) {
						doubledrops = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Double Drops are now on, take advantage and gain double the wealth!", false);	
					} else if (event >= 147 && event <= 160) {
						slayerpoints = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Gain double the amount of slayer points when completing a task!", false);	
					} else if (event >= 161 && event <= 180) {
						moreprayer = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Gain double the amount Prayer EXP when burying or using an altar!", false);	
					} else if (event >= 181 && event <= 190) {
						quadcharms = true;
					World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Monsters are now dropping quadruple the amounts of charms!", false);	
					} else {
						World.sendWorldMessage("<img=7><col=ff0000>[Event Manager] Unfortunately, there are no events occuring this hour!", false);		
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 3600000);
	}
	
	public static void killRaid() {
		for (NPC n : World.getNPCs()) {
			if (n == null || (n.getId() != 3064 && n.getId() != 10495 && n.getId() != 3450 && n.getId() != 3063 && n.getId() != 3058 && n.getId() != 4706 && n.getId() != 10769 && n.getId() != 10761 && n.getId() != 10717 && n.getId() != 15581 && n.getId() != 999 && n.getId() != 998 && n.getId() != 1000 && n.getId() != 14550 && n.getId() != 8335 && n.getId() != 2709 && n.getId() != 2710 && n.getId() != 2711 && n.getId() != 2712))
				continue;
			n.sendDeath(n);
		}
	}
	

	public static void bossRaid() {
		int time = Misc.random(3600000, 7200000);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					int raid = Misc.random(6);
					if (raid == 1) {
						killRaid();
						spawnNPC(3064, new WorldTile(2487, 2856, 0), -1, true, true);
						spawnNPC(10495, new WorldTile(2492, 2856, 0), -1, true, true);
						spawnNPC(10495, new WorldTile(2496, 2852, 0), -1, true, true);
						spawnNPC(10495, new WorldTile(2487, 2849, 0), -1, true, true);
					World.sendWorldMessage("<img=7><col=ff0000>[Boss Raids] The Champion of all Demons has appeared just outside of home! Fight him for Demon Flesh Armor and more!", false);
					} else if (raid == 2) {
						killRaid();
						spawnNPC(3063, new WorldTile(2487, 2856, 0), -1, true, true);
						spawnNPC(3450, new WorldTile(2492, 2856, 0), -1, true, true);
						spawnNPC(3450, new WorldTile(2496, 2852, 0), -1, true, true);
						spawnNPC(3450, new WorldTile(2487, 2849, 0), -1, true, true);
					World.sendWorldMessage("<img=7><col=ff0000>[Boss Raids] The Champion of all Jogres has appeared just outside of home! Fight him for Trading Sticks, Bone Masses and more!", false);
					} else if (raid == 3) {
						killRaid();
						spawnNPC(3058, new WorldTile(2491, 2852, 0), -1, true, true);
						spawnNPC(4706, new WorldTile(2487, 2856, 0), -1, true, true);
						spawnNPC(10769, new WorldTile(2492, 2856, 0), -1, true, true);
						spawnNPC(10717, new WorldTile(2496, 2852, 0), -1, true, true);
						spawnNPC(10761, new WorldTile(2487, 2849, 0), -1, true, true);
					World.sendWorldMessage("<img=7><col=ff0000>[Boss Raids] The Champion of all Giants has appeared just outside of home! Fight him for Defenders, Masses of Charms and more!", false);
					} else if (raid == 4) {
						killRaid();
						spawnNPC(15581, new WorldTile(2491, 2852, 0), -1, true, true);
						spawnNPC(999, new WorldTile(2487, 2856, 0), -1, true, true);
						spawnNPC(998, new WorldTile(2492, 2856, 0), -1, true, true);
						spawnNPC(1000, new WorldTile(2496, 2852, 0), -1, true, true);
						spawnNPC(14550, new WorldTile(2487, 2849, 0), -1, true, true);
					World.sendWorldMessage("<img=7><col=ff0000>[Boss Raids] The Vicious Party Demon has appeared just outside of home! Fight him for Demon Slayer Armor, Dragon Weapons and more!", false);
					} else if (raid == 5) {
						killRaid();
						spawnNPC(8335, new WorldTile(2491, 2852, 0), -1, true, true);
						spawnNPC(2712, new WorldTile(2487, 2856, 0), -1, true, true);
						spawnNPC(2710, new WorldTile(2492, 2856, 0), -1, true, true);
						spawnNPC(2711, new WorldTile(2496, 2852, 0), -1, true, true);
						spawnNPC(2709, new WorldTile(2487, 2849, 0), -1, true, true);
					World.sendWorldMessage("<img=7><col=ff0000>[Boss Raids] The Ultimate Mercenary Mage has appeared just outside of home! Fight him for Infinity Sets, Masses of Runes and more!", false);
					} else {
						World.sendWorldMessage("<img=7><col=ff0000>[Boss Raids] Luckily, our home is well protected and there are no current attacks!", false);		
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, time);
	}
	
	public static void sinkHoles() {
		final int time = Misc.random(3600000, 15000000);
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					SinkHoles.startEvent();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, time);
	}
	
	public static void penguinHS() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player players : World.getPlayers()) {
						if (players == null)
							continue;
						players.penguin = false;
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8104)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8105)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8107)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8108)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8109)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8110)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 14766)
							continue;
						n.sendDeath(n);
					}
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 14415)
							continue;
						n.sendDeath(n);
					}
					PenguinEvent.startEvent();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 3600000);
	}
	
	
	public static void crashedStar() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					star = 0;
					World.sendWorldMessage("<img=7><col=ff0000>News: A Shooting Star has just struck Falador!", false);
					World.spawnObject(new WorldObject(38660, 10, 0 , 3028, 3365, 0), true);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1200000);
	}
	/*public static void spawnStar() {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 1200) {
					star = 0;
					ShootingStar.spawnRandomStar();
					}
					loop++;
					}
				}, 0, 1);
	}*/
	
	public static void removeStarSprite(final Player player) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;
			@Override
			public void run() {
				if (loop == 50) {
					for (NPC n : World.getNPCs()) {
						if (n == null || n.getId() != 8091)
							continue;
						n.sendDeath(n);
					}
				}
					loop++;
					}
				}, 0, 1);
	}
	
	private static void addRestoreShopItemsTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					ShopsHandler.restoreShops();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 30, TimeUnit.SECONDS);
	}
	
	/**
	 * Lobby Stuff
	 */
	
	private static final EntityList<Player> lobbyPlayers = new EntityList<Player>(Settings.PLAYERS_LIMIT);

	public static final Player getLobbyPlayerByDisplayName(String username) {
		String formatedUsername = Utils.formatPlayerNameForDisplay(username);
		for (Player player : getLobbyPlayers()) {
			if (player == null) {
				continue;
			}
			if (player.getUsername().equalsIgnoreCase(formatedUsername)
					|| player.getDisplayName().equalsIgnoreCase(formatedUsername)) {
				return player;
			}
		}
		return null;
	}

	public static final EntityList<Player> getLobbyPlayers() {
		return lobbyPlayers;
	}
		
	public static final void addPlayer(Player player) {
		players.add(player);
		if (World.containsLobbyPlayer(player.getUsername())) {
			World.removeLobbyPlayer(player);
			AntiFlood.remove(player.getSession().getIP());
		}
		AntiFlood.add(player.getSession().getIP());
	}

	public static final void addLobbyPlayer(Player player) {
		lobbyPlayers.add(player);
		AntiFlood.add(player.getSession().getIP());
	}

	public static final boolean containsLobbyPlayer(String username) {
		for (Player p2 : lobbyPlayers) {
			if (p2 == null) {
				continue;
			}
			if (p2.getUsername().equalsIgnoreCase(username)) {
				return true;
			}
		}
		return false;
	}

	public static void removeLobbyPlayer(Player player) {
		for (Player p : lobbyPlayers) {
			if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
				if (player.getCurrentFriendChat() != null) {
					player.getCurrentFriendChat().leaveChat(player, true);
				}
				lobbyPlayers.remove(p);
			}
		}
		AntiFlood.remove(player.getSession().getIP());
	}

	public static void removePlayer(Player player) {
		for (Player p : players) {
			if (p.getUsername().equalsIgnoreCase(player.getUsername())) {
				players.remove(p);
			}
		}
		AntiFlood.remove(player.getSession().getIP());
	}

	private static final void addSummoningEffectTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.getFamiliar() == null || player.isDead()
								|| !player.hasFinished())
							continue;
						if (player.getFamiliar().getOriginalId() == 6814) {
							player.heal(20);
							player.setNextGraphics(new Graphics(1507));
						}
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 15, TimeUnit.SECONDS);
	}

	private static final void addRestoreSpecialAttackTask() {

		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead()
								|| !player.isRunning())
							continue;
						player.getCombatDefinitions().restoreSpecialAttack();
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 30000);
	}

	private static boolean checkAgility;
	public static Object deleteObject;

	private static final void addTriviaBotTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					TriviaBot.Run();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 400000);
	}

	private static final void addRestoreRunEnergyTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null
								|| player.isDead()
								|| !player.isRunning()
								|| (checkAgility && player.getSkills()
										.getLevel(Skills.AGILITY) < 70))
							continue;
						player.restoreRunEnergy();
					}
					checkAgility = !checkAgility;
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 1000);
	}

	private static final void addDrainPrayerTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead()
								|| !player.isRunning())
							continue;
						player.getPrayer().processPrayerDrain();
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 600);
	}

	private static final void addRestoreHitPointsTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || player.isDead()
								|| !player.isRunning())
							continue;
						player.restoreHitPoints();
					}
					for (NPC npc : npcs) {
						if (npc == null || npc.isDead() || npc.hasFinished())
							continue;
						npc.restoreHitPoints();
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 6000);
	}

	private static final void addRestoreSkillsTask() {
		CoresManager.fastExecutor.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					for (Player player : getPlayers()) {
						if (player == null || !player.isRunning())
							continue;
						int ammountTimes = player.getPrayer().usingPrayer(0, 8) ? 2
								: 1;
						if (player.isResting())
							ammountTimes += 1;
						boolean berserker = player.getPrayer()
								.usingPrayer(1, 5);
						for (int skill = 0; skill < 25; skill++) {
							if (skill == Skills.SUMMONING)
								continue;
							for (int time = 0; time < ammountTimes; time++) {
								int currentLevel = player.getSkills().getLevel(
										skill);
								int normalLevel = player.getSkills()
										.getLevelForXp(skill);
								if (currentLevel > normalLevel) {
									if (skill == Skills.ATTACK
											|| skill == Skills.STRENGTH
											|| skill == Skills.DEFENCE
											|| skill == Skills.RANGE
											|| skill == Skills.MAGIC) {
										if (berserker
												&& Utils.getRandom(100) <= 15)
											continue;
									}
									player.getSkills().set(skill,
											currentLevel - 1);
								} else if (currentLevel < normalLevel)
									player.getSkills().set(skill,
											currentLevel + 1);
								else
									break;
							}
						}
					}
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 30000);

	}

	public static final Map<Integer, Region> getRegions() {
		// synchronized (lock) {
		return regions;
		// }
	}

	public static final Region getRegion(int id) {
		return getRegion(id, false);
	}

	public static final Region getRegion(int id, boolean load) {
		// synchronized (lock) {
		Region region = regions.get(id);
		if (region == null) {
			region = new Region(id);
			regions.put(id, region);
		}
		if(load)
			region.checkLoadMap();
		return region;
		// }
	}

	public static final void addNPC(NPC npc) {
		npcs.add(npc);
	}

	public static final void removeNPC(NPC npc) {
		npcs.remove(npc);
	}

	public static final NPC spawnNPC(int id, WorldTile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea,
			boolean spawned) {
		NPC n = null;
		HunterNPC hunterNPCs = HunterNPC.forId(id);
		if (hunterNPCs != null) {
			if (id == hunterNPCs.getNpcId())
				n = new ItemHunterNPC(id, tile, mapAreaNameHash,
						canBeAttackFromOutOfArea, spawned);
		}
		else if (id >= 5533 && id <= 5558)
		    n = new Elemental(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 14301)
		    n = new Glacor(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea);
		else if (id == 7134)
			n = new Bork(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id >= 6026 && id <= 6045)
		    n = new Werewolf(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 9441)
			n = new FlameVortex(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id >= 8832 && id <= 8834)
			n = new LivingRock(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id >= 13465 && id <= 13481)
			n = new Revenant(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 1158 || id == 1160)
			n = new KalphiteQueen(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id >= 8528 && id <= 8532)
			n = new Nomad(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6215 || id == 6211 || id == 3406 || id == 6216|| id == 6214 || id == 6215|| id == 6212 || id == 6219 || id == 6221 || id == 6218)
			n = new GodwarsZammorakFaction(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6254  && id == 6259)
			n = new GodwarsSaradominFaction(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6246 || id == 6236 || id == 6232 || id == 6240 || id == 6241 || id == 6242 || id == 6235 || id == 6234 || id == 6243 || id == 6236 || id == 6244 || id == 6237 || id == 6246 || id == 6238 || id == 6239 || id == 6230)
			n = new GodwarsArmadylFaction(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6281 || id == 6282 || id == 6275 || id == 6279|| id == 9184 || id == 6268 || id == 6270 || id == 6274 || id == 6277 || id == 6276 || id == 6278)
			n = new GodwarsBandosFaction(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6261 || id == 6263 || id == 6265)
			n = GodWarsBosses.graardorMinions[(id - 6261) / 2] = new GodWarMinion(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6260)
			n = new GeneralGraardor(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6222)
			n = new KreeArra(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 6223 || id == 6225 || id == 6227)
			n = GodWarsBosses.armadylMinions[(id - 6223) / 2] = new GodWarMinion(
					id, tile, mapAreaNameHash, canBeAttackFromOutOfArea,
					spawned);
		else if (id == 6203)
			n = new KrilTstsaroth(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 6204 || id == 6206 || id == 6208)
			n = GodWarsBosses.zamorakMinions[(id - 6204) / 2] = new GodWarMinion(
					id, tile, mapAreaNameHash, canBeAttackFromOutOfArea,
					spawned);
		else if (id == 50 || id == 2642)
			n = new KingBlackDragon(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id >= 9462 && id <= 9467)
			n = new Strykewyrm(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea);
		else if (id == 6248 || id == 6250 || id == 6252)
			n = GodWarsBosses.commanderMinions[(id - 6248) / 2] = new GodWarMinion(
					id, tile, mapAreaNameHash, canBeAttackFromOutOfArea,
					spawned);
		else if (id == 6247)
			n = new CommanderZilyana(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 8133)
			n = new CorporealBeast(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 13447)
			n = ZarosGodwars.nex = new Nex(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 13451)
			n = ZarosGodwars.fumus = new NexMinion(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 13452)
			n = ZarosGodwars.umbra = new NexMinion(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 13453)
			n = ZarosGodwars.cruor = new NexMinion(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 13454)
			n = ZarosGodwars.glacies = new NexMinion(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 14256)
			n = new Lucien(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea,
					spawned);
		else if (id == 8335)
			n = new MercenaryMage(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea,
					spawned);
		else if (id == 8349 || id == 8450 || id == 8451)
			n = new TormentedDemon(id, tile, mapAreaNameHash,
					canBeAttackFromOutOfArea, spawned);
		else if (id == 15149)
			n = new MasterOfFear(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else if (id == 14696)
			n = new GanodermicBeast(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		else 
			n = new NPC(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea,
					spawned);
		return n;
	}

	public static final NPC spawnNPC(int id, WorldTile tile,
			int mapAreaNameHash, boolean canBeAttackFromOutOfArea) {
		return spawnNPC(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, false);
	}
	/*
	 * check if the entity region changed because moved or teled then we update
	 * it
	 */
	public static final void updateEntityRegion(Entity entity) {
		if (entity.hasFinished()) {
			if (entity instanceof Player)
				getRegion(entity.getLastRegionId()).removePlayerIndex(entity.getIndex());
			else 
				getRegion(entity.getLastRegionId()).removeNPCIndex(entity.getIndex());
			return;
		}
		int regionId = entity.getRegionId();
		if (entity.getLastRegionId() != regionId) { // map region entity at
			// changed
			if (entity instanceof Player) {
				if (entity.getLastRegionId() > 0)
					getRegion(entity.getLastRegionId()).removePlayerIndex(
							entity.getIndex());
				Region region = getRegion(regionId);
				region.addPlayerIndex(entity.getIndex());
				Player player = (Player) entity;
				int musicId = region.getMusicId();
				if (musicId != -1)
					player.getMusicsManager().checkMusic(musicId);
				player.getControlerManager().moved();
				if(player.hasStarted())
					checkControlersAtMove(player);
			} else {
				if (entity.getLastRegionId() > 0)
					getRegion(entity.getLastRegionId()).removeNPCIndex(
							entity.getIndex());
				getRegion(regionId).addNPCIndex(entity.getIndex());
			}
			entity.checkMultiArea();
			entity.checkSmokeyArea();
			entity.checkDesertArea();
			entity.checkSinkArea();
			entity.checkMorytaniaArea();
			entity.setLastRegionId(regionId);
		} else {
			if (entity instanceof Player) {
				Player player = (Player) entity;
				player.getControlerManager().moved();
				if(player.hasStarted())
					checkControlersAtMove(player);
			}
			entity.checkMultiArea();
			entity.checkSmokeyArea();
			entity.checkDesertArea();
			entity.checkSinkArea();
			entity.checkMorytaniaArea();
		}
	}

	private static void checkControlersAtMove(Player player) {
		if (!(player.getControlerManager().getControler() instanceof RequestController) && RequestController.inWarRequest(player))
			player.getControlerManager().startControler("clan_wars_request");
		else if (DuelControler.isAtDuelArena(player)) 
			player.getControlerManager().startControler("DuelControler");
		else if (FfaZone.inArea(player)) 
			player.getControlerManager().startControler("clan_wars_ffa");
	}

	/*
	 * checks clip
	 */
	public static boolean canMoveNPC(int plane, int x, int y, int size) {
		for (int tileX = x; tileX < x + size; tileX++)
			for (int tileY = y; tileY < y + size; tileY++)
				if (getMask(plane, tileX, tileY) != 0)
					return false;
		return true;
	}

	/*
	 * checks clip
	 */
	public static boolean isNotCliped(int plane, int x, int y, int size) {
		for (int tileX = x; tileX < x + size; tileX++)
			for (int tileY = y; tileY < y + size; tileY++)
				if ((getMask(plane, tileX, tileY) & 2097152) != 0)
					return false;
		return true;
	}

	public static int getMask(int plane, int x, int y) {
		WorldTile tile = new WorldTile(x, y, plane);
		int regionId = tile.getRegionId();
		Region region = getRegion(regionId);
		if (region == null)
			return -1;
		int baseLocalX = x - ((regionId >> 8) * 64);
		int baseLocalY = y - ((regionId & 0xff) * 64);
		return region.getMask(tile.getPlane(), baseLocalX, baseLocalY);
	}

	public static void setMask(int plane, int x, int y, int mask) {
		WorldTile tile = new WorldTile(x, y, plane);
		int regionId = tile.getRegionId();
		Region region = getRegion(regionId);
		if (region == null)
			return;
		int baseLocalX = x - ((regionId >> 8) * 64);
		int baseLocalY = y - ((regionId & 0xff) * 64);
		region.setMask(tile.getPlane(), baseLocalX, baseLocalY, mask);
	}

	public static int getRotation(int plane, int x, int y) {
		WorldTile tile = new WorldTile(x, y, plane);
		int regionId = tile.getRegionId();
		Region region = getRegion(regionId);
		if (region == null)
			return 0;
		//int baseLocalX = x - ((regionId >> 8) * 64);
		//int baseLocalY = y - ((regionId & 0xff) * 64);
		//return region.getRotation(tile.getPlane(), baseLocalX, baseLocalY);
		return 0;
	}
	
	

	private static int getClipedOnlyMask(int plane, int x, int y) {
		WorldTile tile = new WorldTile(x, y, plane);
		int regionId = tile.getRegionId();
		Region region = getRegion(regionId);
		if (region == null)
			return -1;
		int baseLocalX = x - ((regionId >> 8) * 64);
		int baseLocalY = y - ((regionId & 0xff) * 64);
		return region
				.getMaskClipedOnly(tile.getPlane(), baseLocalX, baseLocalY);
	}

	public static final boolean checkProjectileStep(int plane, int x, int y,
			int dir, int size) {
		int xOffset = Utils.DIRECTION_DELTA_X[dir];
		int yOffset = Utils.DIRECTION_DELTA_Y[dir];
		/*
		 * int rotation = getRotation(plane,x+xOffset,y+yOffset); if(rotation !=
		 * 0) { dir += rotation; if(dir >= Utils.DIRECTION_DELTA_X.length) dir =
		 * dir - (Utils.DIRECTION_DELTA_X.length-1); xOffset =
		 * Utils.DIRECTION_DELTA_X[dir]; yOffset = Utils.DIRECTION_DELTA_Y[dir];
		 * }
		 */
		if (size == 1) {
			int mask = getClipedOnlyMask(plane, x
					+ Utils.DIRECTION_DELTA_X[dir], y
					+ Utils.DIRECTION_DELTA_Y[dir]);
			if (xOffset == -1 && yOffset == 0)
				return (mask & 0x42240000) == 0;
			if (xOffset == 1 && yOffset == 0)
				return (mask & 0x60240000) == 0;
			if (xOffset == 0 && yOffset == -1)
				return (mask & 0x40a40000) == 0;
			if (xOffset == 0 && yOffset == 1)
				return (mask & 0x48240000) == 0;
			if (xOffset == -1 && yOffset == -1) {
				return (mask & 0x43a40000) == 0
						&& (getClipedOnlyMask(plane, x - 1, y) & 0x42240000) == 0
						&& (getClipedOnlyMask(plane, x, y - 1) & 0x40a40000) == 0;
			}
			if (xOffset == 1 && yOffset == -1) {
				return (mask & 0x60e40000) == 0
						&& (getClipedOnlyMask(plane, x + 1, y) & 0x60240000) == 0
						&& (getClipedOnlyMask(plane, x, y - 1) & 0x40a40000) == 0;
			}
			if (xOffset == -1 && yOffset == 1) {
				return (mask & 0x4e240000) == 0
						&& (getClipedOnlyMask(plane, x - 1, y) & 0x42240000) == 0
						&& (getClipedOnlyMask(plane, x, y + 1) & 0x48240000) == 0;
			}
			if (xOffset == 1 && yOffset == 1) {
				return (mask & 0x78240000) == 0
						&& (getClipedOnlyMask(plane, x + 1, y) & 0x60240000) == 0
						&& (getClipedOnlyMask(plane, x, y + 1) & 0x48240000) == 0;
			}
		} else if (size == 2) {
			if (xOffset == -1 && yOffset == 0)
				return (getClipedOnlyMask(plane, x - 1, y) & 0x43a40000) == 0
				&& (getClipedOnlyMask(plane, x - 1, y + 1) & 0x4e240000) == 0;
			if (xOffset == 1 && yOffset == 0)
				return (getClipedOnlyMask(plane, x + 2, y) & 0x60e40000) == 0
				&& (getClipedOnlyMask(plane, x + 2, y + 1) & 0x78240000) == 0;
			if (xOffset == 0 && yOffset == -1)
				return (getClipedOnlyMask(plane, x, y - 1) & 0x43a40000) == 0
				&& (getClipedOnlyMask(plane, x + 1, y - 1) & 0x60e40000) == 0;
			if (xOffset == 0 && yOffset == 1)
				return (getClipedOnlyMask(plane, x, y + 2) & 0x4e240000) == 0
				&& (getClipedOnlyMask(plane, x + 1, y + 2) & 0x78240000) == 0;
			if (xOffset == -1 && yOffset == -1)
				return (getClipedOnlyMask(plane, x - 1, y) & 0x4fa40000) == 0
				&& (getClipedOnlyMask(plane, x - 1, y - 1) & 0x43a40000) == 0
				&& (getClipedOnlyMask(plane, x, y - 1) & 0x63e40000) == 0;
			if (xOffset == 1 && yOffset == -1)
				return (getClipedOnlyMask(plane, x + 1, y - 1) & 0x63e40000) == 0
				&& (getClipedOnlyMask(plane, x + 2, y - 1) & 0x60e40000) == 0
				&& (getClipedOnlyMask(plane, x + 2, y) & 0x78e40000) == 0;
			if (xOffset == -1 && yOffset == 1)
				return (getClipedOnlyMask(plane, x - 1, y + 1) & 0x4fa40000) == 0
				&& (getClipedOnlyMask(plane, x - 1, y + 1) & 0x4e240000) == 0
				&& (getClipedOnlyMask(plane, x, y + 2) & 0x7e240000) == 0;
			if (xOffset == 1 && yOffset == 1)
				return (getClipedOnlyMask(plane, x + 1, y + 2) & 0x7e240000) == 0
				&& (getClipedOnlyMask(plane, x + 2, y + 2) & 0x78240000) == 0
				&& (getClipedOnlyMask(plane, x + 1, y + 1) & 0x78e40000) == 0;
		} else {
			if (xOffset == -1 && yOffset == 0) {
				if ((getClipedOnlyMask(plane, x - 1, y) & 0x43a40000) != 0
						|| (getClipedOnlyMask(plane, x - 1, -1 + (y + size)) & 0x4e240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getClipedOnlyMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == 0) {
				if ((getClipedOnlyMask(plane, x + size, y) & 0x60e40000) != 0
						|| (getClipedOnlyMask(plane, x + size, y - (-size + 1)) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + size, y + sizeOffset) & 0x78e40000) != 0)
						return false;
			} else if (xOffset == 0 && yOffset == -1) {
				if ((getClipedOnlyMask(plane, x, y - 1) & 0x43a40000) != 0
						|| (getClipedOnlyMask(plane, x + size - 1, y - 1) & 0x60e40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == 0 && yOffset == 1) {
				if ((getClipedOnlyMask(plane, x, y + size) & 0x4e240000) != 0
						|| (getClipedOnlyMask(plane, x + (size - 1), y + size) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0)
						return false;
			} else if (xOffset == -1 && yOffset == -1) {
				if ((getClipedOnlyMask(plane, x - 1, y - 1) & 0x43a40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getClipedOnlyMask(plane, x - 1, y + (-1 + sizeOffset)) & 0x4fa40000) != 0
					|| (getClipedOnlyMask(plane, sizeOffset - 1 + x,
							y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == -1) {
				if ((getClipedOnlyMask(plane, x + size, y - 1) & 0x60e40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + size, sizeOffset
							+ (-1 + y)) & 0x78e40000) != 0
							|| (getClipedOnlyMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == -1 && yOffset == 1) {
				if ((getClipedOnlyMask(plane, x - 1, y + size) & 0x4e240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getClipedOnlyMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0
					|| (getClipedOnlyMask(plane, -1 + (x + sizeOffset),
							y + size) & 0x7e240000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == 1) {
				if ((getClipedOnlyMask(plane, x + size, y + size) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getClipedOnlyMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0
					|| (getClipedOnlyMask(plane, x + size, y
							+ sizeOffset) & 0x78e40000) != 0)
						return false;
			}
		}
		return true;
	}

	public static final boolean checkWalkStep(int plane, int x, int y, int dir,
			int size) {
		int xOffset = Utils.DIRECTION_DELTA_X[dir];
		int yOffset = Utils.DIRECTION_DELTA_Y[dir];
		int rotation = getRotation(plane, x + xOffset, y + yOffset);
		if (rotation != 0) {
			for (int rotate = 0; rotate < (4 - rotation); rotate++) {
				int fakeChunckX = xOffset;
				int fakeChunckY = yOffset;
				xOffset = fakeChunckY;
				yOffset = 0 - fakeChunckX;
			}
		}

		if (size == 1) {
			int mask = getMask(plane, x + Utils.DIRECTION_DELTA_X[dir], y
					+ Utils.DIRECTION_DELTA_Y[dir]);
			if (xOffset == -1 && yOffset == 0)
				return (mask & 0x42240000) == 0;
			if (xOffset == 1 && yOffset == 0)
				return (mask & 0x60240000) == 0;
			if (xOffset == 0 && yOffset == -1)
				return (mask & 0x40a40000) == 0;
			if (xOffset == 0 && yOffset == 1)
				return (mask & 0x48240000) == 0;
			if (xOffset == -1 && yOffset == -1) {
				return (mask & 0x43a40000) == 0
						&& (getMask(plane, x - 1, y) & 0x42240000) == 0
						&& (getMask(plane, x, y - 1) & 0x40a40000) == 0;
			}
			if (xOffset == 1 && yOffset == -1) {
				return (mask & 0x60e40000) == 0
						&& (getMask(plane, x + 1, y) & 0x60240000) == 0
						&& (getMask(plane, x, y - 1) & 0x40a40000) == 0;
			}
			if (xOffset == -1 && yOffset == 1) {
				return (mask & 0x4e240000) == 0
						&& (getMask(plane, x - 1, y) & 0x42240000) == 0
						&& (getMask(plane, x, y + 1) & 0x48240000) == 0;
			}
			if (xOffset == 1 && yOffset == 1) {
				return (mask & 0x78240000) == 0
						&& (getMask(plane, x + 1, y) & 0x60240000) == 0
						&& (getMask(plane, x, y + 1) & 0x48240000) == 0;
			}
		} else if (size == 2) {
			if (xOffset == -1 && yOffset == 0)
				return (getMask(plane, x - 1, y) & 0x43a40000) == 0
				&& (getMask(plane, x - 1, y + 1) & 0x4e240000) == 0;
			if (xOffset == 1 && yOffset == 0)
				return (getMask(plane, x + 2, y) & 0x60e40000) == 0
				&& (getMask(plane, x + 2, y + 1) & 0x78240000) == 0;
			if (xOffset == 0 && yOffset == -1)
				return (getMask(plane, x, y - 1) & 0x43a40000) == 0
				&& (getMask(plane, x + 1, y - 1) & 0x60e40000) == 0;
			if (xOffset == 0 && yOffset == 1)
				return (getMask(plane, x, y + 2) & 0x4e240000) == 0
				&& (getMask(plane, x + 1, y + 2) & 0x78240000) == 0;
			if (xOffset == -1 && yOffset == -1)
				return (getMask(plane, x - 1, y) & 0x4fa40000) == 0
				&& (getMask(plane, x - 1, y - 1) & 0x43a40000) == 0
				&& (getMask(plane, x, y - 1) & 0x63e40000) == 0;
			if (xOffset == 1 && yOffset == -1)
				return (getMask(plane, x + 1, y - 1) & 0x63e40000) == 0
				&& (getMask(plane, x + 2, y - 1) & 0x60e40000) == 0
				&& (getMask(plane, x + 2, y) & 0x78e40000) == 0;
			if (xOffset == -1 && yOffset == 1)
				return (getMask(plane, x - 1, y + 1) & 0x4fa40000) == 0
				&& (getMask(plane, x - 1, y + 1) & 0x4e240000) == 0
				&& (getMask(plane, x, y + 2) & 0x7e240000) == 0;
			if (xOffset == 1 && yOffset == 1)
				return (getMask(plane, x + 1, y + 2) & 0x7e240000) == 0
				&& (getMask(plane, x + 2, y + 2) & 0x78240000) == 0
				&& (getMask(plane, x + 1, y + 1) & 0x78e40000) == 0;
		} else {
			if (xOffset == -1 && yOffset == 0) {
				if ((getMask(plane, x - 1, y) & 0x43a40000) != 0
						|| (getMask(plane, x - 1, -1 + (y + size)) & 0x4e240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == 0) {
				if ((getMask(plane, x + size, y) & 0x60e40000) != 0
						|| (getMask(plane, x + size, y - (-size + 1)) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getMask(plane, x + size, y + sizeOffset) & 0x78e40000) != 0)
						return false;
			} else if (xOffset == 0 && yOffset == -1) {
				if ((getMask(plane, x, y - 1) & 0x43a40000) != 0
						|| (getMask(plane, x + size - 1, y - 1) & 0x60e40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == 0 && yOffset == 1) {
				if ((getMask(plane, x, y + size) & 0x4e240000) != 0
						|| (getMask(plane, x + (size - 1), y + size) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size - 1; sizeOffset++)
					if ((getMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0)
						return false;
			} else if (xOffset == -1 && yOffset == -1) {
				if ((getMask(plane, x - 1, y - 1) & 0x43a40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getMask(plane, x - 1, y + (-1 + sizeOffset)) & 0x4fa40000) != 0
					|| (getMask(plane, sizeOffset - 1 + x, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == -1) {
				if ((getMask(plane, x + size, y - 1) & 0x60e40000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getMask(plane, x + size, sizeOffset + (-1 + y)) & 0x78e40000) != 0
					|| (getMask(plane, x + sizeOffset, y - 1) & 0x63e40000) != 0)
						return false;
			} else if (xOffset == -1 && yOffset == 1) {
				if ((getMask(plane, x - 1, y + size) & 0x4e240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getMask(plane, x - 1, y + sizeOffset) & 0x4fa40000) != 0
					|| (getMask(plane, -1 + (x + sizeOffset), y + size) & 0x7e240000) != 0)
						return false;
			} else if (xOffset == 1 && yOffset == 1) {
				if ((getMask(plane, x + size, y + size) & 0x78240000) != 0)
					return false;
				for (int sizeOffset = 1; sizeOffset < size; sizeOffset++)
					if ((getMask(plane, x + sizeOffset, y + size) & 0x7e240000) != 0
					|| (getMask(plane, x + size, y + sizeOffset) & 0x78e40000) != 0)
						return false;
			}
		}
		return true;
	}

	public static final boolean containsPlayer(String username) {
		for (Player p2 : players) {
			if (p2 == null)
				continue;
			if (p2.getUsername().equals(username))
				return true;
		}
		return false;
	}

	public static Player getPlayer(String username) {
		for (Player player : getPlayers()) {
			if (player == null)
				continue;
			if (player.getUsername().equals(username))
				return player;
		}
		return null;
	}
	public static Player getPlayer(Player username) {
		for (Player player : getPlayers()) {
			if (player == null)
				continue;
			if (player.getUsername().equals(username))
				return player;
		}
		return null;
	}
	public static final Player getPlayerByDisplayName(String username) {
		String formatedUsername = Utils.formatPlayerNameForDisplay(username);
		for (Player player : getPlayers()) {
			if (player == null)
				continue;
			if (player.getUsername().equalsIgnoreCase(formatedUsername)
					|| player.getDisplayName().equalsIgnoreCase(formatedUsername))
				return player;
		}
		return null;
	}
	public static final EntityList<Player> getPlayers() {
		return players;
	}

	public static final EntityList<NPC> getNPCs() {
		return npcs;
	}

	private World() {

	}

	public static final void safeShutdown(final boolean restart, int delay) {
		if (exiting_start != 0)
			return;
		exiting_start = Utils.currentTimeMillis();
		exiting_delay = delay;
		for (Player player : World.getPlayers()) {
			if (player == null || !player.hasStarted() || player.hasFinished())
				continue;
			player.getPackets().sendSystemUpdate(delay);
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					for (Player player : World.getPlayers()) {
						if (player == null || !player.hasStarted())
							continue;
						player.realFinish();
					
					player.getControlerManager().removeControlerWithoutCheck();
				if (player.getControlerManager().getControler() instanceof RuneDungGame)  {
					player.getControlerManager().forceStop();
					player.getControlerManager().removeControlerWithoutCheck();
					player.lock(10); 
					for (Item item : player.getInventory().getItems().toArray()) {
						if (item == null) continue;
						player.getInventory().deleteItem(item);
						player.setNextWorldTile(new WorldTile(3450, 3718, 0)); }
				}
					
					IPBanL.save();
					PkRank.save();
					KillStreakRank.save();
					}
					//Offers.saveOffers();
					if (restart)
						Launcher.restart();
					else
						Launcher.shutdown();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, delay, TimeUnit.SECONDS);
	}

	/*
	 * by default doesnt changeClipData
	 */
	public static final void spawnTemporaryObject(final WorldObject object,
			long time) {
		spawnTemporaryObject(object, time, false);
	}
	public static final void spawnTemporaryDivineObject(final WorldObject object,
			long time, final Player player) {
		spawnTemporaryDivineObject(object, time, player,false);
	}
	public static final void spawnTemporaryDivineObject(final WorldObject object,
			long time, final Player player, final boolean clip) {
		spawnObject(object);
		CoresManager.slowExecutor.schedule(new Runnable() {
		    @Override
		    public void run() {
			try {
			    if (!World.isSpawnedObject(object))
				return;
			    removeObject(object);
			    player.divine = 0;
			}
			catch (Throwable e) {
			    Logger.handle(e);
			}
		    }

		}, time, TimeUnit.MILLISECONDS);
	    }
	public static final void spawnTemporaryObject(final WorldObject object,
			long time, final boolean clip) {
		spawnObject(object);
		CoresManager.slowExecutor.schedule(new Runnable() {
		    @Override
		    public void run() {
			try {
			    if (!World.isSpawnedObject(object))
				return;
			    removeObject(object);
			}
			catch (Throwable e) {
			    Logger.handle(e);
			}
		    }

		}, time, TimeUnit.MILLISECONDS);
	    }

	public static final boolean isSpawnedObject(WorldObject object) {
		return getRegion(object.getRegionId()).getSpawnedObjects().contains(object);
    }

	public static final boolean removeTemporaryObject(final WorldObject object,
			long time, final boolean clip) {
		removeObject(object);
		CoresManager.slowExecutor.schedule(new Runnable() {
		    @Override
		    public void run() {
			try {
			    spawnObject(object);
			}
			catch (Throwable e) {
			    Logger.handle(e);
			}
		    }

		}, time, TimeUnit.MILLISECONDS);
		return true;
	    }

	public static final void removeObject(WorldObject object, boolean clip) {
		getRegion(object.getRegionId()).removeObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion());
	}
	

	public static final WorldObject getObject(WorldTile tile) {
		return getRegion(tile.getRegionId()).getStandartObject(tile.getPlane(), tile.getXInRegion(), tile.getYInRegion());
    }

	public static final WorldObject getObject(WorldTile tile, int type) {
		return getRegion(tile.getRegionId()).getObjectWithType(tile.getPlane(), tile.getXInRegion(), tile.getYInRegion(), type);
    }

	public static final void spawnObject(WorldObject object, boolean clip) {
		getRegion(object.getRegionId()).spawnObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion(), false);
	}
	
	public static final void spawnObjectSpawns(WorldObject object, boolean clip) {
		getRegion(object.getRegionId()).spawnObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion(), true);
	}
	

	
  /*  public static final void addGroundItem(final Item item, final WorldTile tile, final Player owner/*
		     * null
		     * for
		     * default
		     , boolean invisible, long hiddenTime/*
							    * default
							    * 3
							    * minutes
							    ) {
    	addGroundItem(item, tile, owner, invisible, hiddenTime, 2, 150);
    }
    
    public static final FloorItem addGroundItem(final Item item, final WorldTile tile, final Player owner/*
			  * null
			  * for
			  * default
			  , boolean invisible, long hiddenTime/*
								 * default
								 * 3
								 * minutes
								 , int type) {
    	return addGroundItem(item, tile, owner, invisible, hiddenTime, type, 150);
    }*/
    
	/*	    public static final FloorItem addGroundItem(final Item item, final WorldTile tile, final Player owner, boolean invisible, long hiddenTime/*

				     
				     
				     
				     
				     
				     
				     , int type, final int publicTime) {
		if (type != 2) {
		if ((type == 0 && !ItemConstants.isTradeable(item)) || type == 1 && ItemConstants.isDestroy(item)) {
		
		int price = item.getDefinitions().getValue();
		if (price <= 0)
		return null;
		item.setId(995);
		item.setAmount(price);
		}
		}
		final FloorItem floorItem = new FloorItem(item, tile, owner, owner != null, invisible);
		final Region region = getRegion(tile.getRegionId());
		region.getGroundItemsSafe().add(floorItem);
		if (invisible) {
		if (owner != null)
		owner.getPackets().sendGroundItem(floorItem);
		// becomes visible after x time
		if (hiddenTime != -1) {
		CoresManager.slowExecutor.schedule(new Runnable() {
		@Override
		public void run() {
		try {
		turnPublic(floorItem, publicTime);
		}
		catch (Throwable e) {
		Logger.handle(e);
		}
		}
		}, hiddenTime, TimeUnit.SECONDS);
		}
		} else {
		// visible
		int regionId = tile.getRegionId();
		for (Player player : players) {
		if (player == null || !player.hasStarted() || player.hasFinished() || player.getPlane() != tile.getPlane() || !player.getMapRegionsIds().contains(regionId))
		continue;
		player.getPackets().sendGroundItem(floorItem);
		}
		// disapears after this time
		if (publicTime != -1)
		removeGroundItem(floorItem, publicTime);
		}
		return floorItem;
		}*/
    
  /*  public static final void turnPublic(FloorItem floorItem, int publicTime) {
	if (!floorItem.isInvisible())
	    return;
	int regionId = floorItem.getTile().getRegionId();
	final Region region = getRegion(regionId);
	if (!region.getGroundItemsSafe().contains(floorItem))
	    return;
	//Player realOwner = floorItem.hasOwner() ? World.getPlayer(floorItem.getOwner()) : null;
	if (!ItemConstants.isTradeable(floorItem)) {
	    region.getGroundItemsSafe().remove(floorItem);
	    if (realOwner != null) {
		if (realOwner.getMapRegionsIds().contains(regionId) && realOwner.getPlane() == floorItem.getTile().getPlane())
		    realOwner.getPackets().sendRemoveGroundItem(floorItem);
	    }
	    return;
	}
	floorItem.setInvisible(false);
	for (Player player : players) {
	  //  if (player == null || player == realOwner || !player.hasStarted() || player.hasFinished() || player.getPlane() != floorItem.getTile().getPlane() || !player.getMapRegionsIds().contains(regionId))
		//continue;
	    player.getPackets().sendGroundItem(floorItem);
	}
	// disapears after this time
	if (publicTime != -1)
	    removeGroundItem(floorItem, publicTime);
    }*/

	public static final void addGroundItem(final Item item, final WorldTile tile) {
		final FloorItem floorItem = new FloorItem(item, tile, null, false,
				false);
		final Region region = getRegion(tile.getRegionId());
		region.forceGetFloorItems().add(floorItem);
		int regionId = tile.getRegionId();
		for (Player player : players) {
			if (player == null || !player.hasStarted() || player.hasFinished()
					|| player.getPlane() != tile.getPlane()
					|| !player.getMapRegionsIds().contains(regionId))
				continue;
			player.getPackets().sendGroundItem(floorItem);
		}
	}

	public static final void addGroundItem(final Item item, final WorldTile tile, final Player owner, final boolean underGrave, long hiddenTime, boolean invisible) {
		addGroundItem(item, tile, owner, underGrave, hiddenTime, invisible, false, 180);
	}
	
	public static final void addGroundItem(final Item item, final WorldTile tile, final Player owner, final boolean underGrave, long hiddenTime, boolean invisible, boolean intoGold) {
		addGroundItem(item, tile, owner, underGrave, hiddenTime, invisible, intoGold, 180);
	}
	
	public static final void addGroundItem(final Item item,
			final WorldTile tile, final Player owner/* null for default */,
			final boolean underGrave, long hiddenTime/* default 3minutes */,
			boolean invisible, boolean intoGold, final int publicTime) {
		final FloorItem floorItem = new FloorItem(item, tile, owner,
				owner == null ? false : underGrave, invisible);
		final Region region = getRegion(tile.getRegionId());
		region.forceGetFloorItems().add(floorItem);
		if (invisible && hiddenTime != -1) {
			if (owner != null)
				owner.getPackets().sendGroundItem(floorItem);
			CoresManager.slowExecutor.schedule(new Runnable() {
				@Override
				public void run() {
					try {
						if (!region.forceGetFloorItems().contains(floorItem))
							return;
						int regionId = tile.getRegionId();
						if (underGrave || !ItemConstants.isTradeable(floorItem) || item.getName().contains("99max99")) {
							region.forceGetFloorItems().remove(floorItem);
							if(owner != null) {
								if (owner.getMapRegionsIds().contains(regionId) && owner.getPlane() == tile.getPlane())
									owner.getPackets().sendRemoveGroundItem(floorItem);
							}
							return;
						}

						floorItem.setInvisible(false);
						for (Player player : players) {
							if (player == null
									|| player == owner
									|| !player.hasStarted()
									|| player.hasFinished()
									|| player.getPlane() != tile.getPlane()
									|| !player.getMapRegionsIds().contains(
											regionId))
								continue;
							player.getPackets().sendGroundItem(floorItem);
						}
						removeGroundItem(floorItem, publicTime);
					} catch (Throwable e) {
						Logger.handle(e);
					}
				}
			}, hiddenTime, TimeUnit.SECONDS);
			return;
		}
		int regionId = tile.getRegionId();
		for (Player player : players) {
			if (player == null || !player.hasStarted() || player.hasFinished()
					|| player.getPlane() != tile.getPlane()
					|| !player.getMapRegionsIds().contains(regionId))
				continue;
			player.getPackets().sendGroundItem(floorItem);
		}
		removeGroundItem(floorItem, publicTime);
	}

@Deprecated
public static final void addGroundItemForever(Item item, final WorldTile tile) {
int regionId = tile.getRegionId();
final FloorItem floorItem = new FloorItem(item, tile, true);
final Region region = getRegion(tile.getRegionId());
region.getGroundItemsSafe().add(floorItem);
for (Player player : players) {
if (player == null || !player.hasStarted() || player.hasFinished() || player.getPlane() != floorItem.getTile().getPlane() || !player.getMapRegionsIds().contains(regionId))
continue;
player.getPackets().sendGroundItem(floorItem);
}
}


public static final void updateGroundItem(Item item, final WorldTile tile, final Player owner) {
final FloorItem floorItem = World.getRegion(tile.getRegionId()).getGroundItem(item.getId(), tile, owner);
if (floorItem == null) {
addGroundItem(item, tile, owner, true, 360);
return;
}
floorItem.setAmount(floorItem.getAmount() + item.getAmount());
owner.getPackets().sendRemoveGroundItem(floorItem);
owner.getPackets().sendGroundItem(floorItem);

}

private static final void removeGroundItem(final FloorItem floorItem, long publicTime) {
CoresManager.slowExecutor.schedule(new Runnable() {
@Override
public void run() {
try {
int regionId = floorItem.getTile().getRegionId();
Region region = getRegion(regionId);
if (!region.getGroundItemsSafe().contains(floorItem))
return;
region.getGroundItemsSafe().remove(floorItem);
for (Player player : World.getPlayers()) {
if (player == null || !player.hasStarted() || player.hasFinished() || player.getPlane() != floorItem.getTile().getPlane() || !player.getMapRegionsIds().contains(regionId))
continue;
player.getPackets().sendRemoveGroundItem(floorItem);
}
}
catch (Throwable e) {
Logger.handle(e);
}
}
}, publicTime, TimeUnit.SECONDS);
}

public static final boolean removeGroundItem(Player player, FloorItem floorItem) {
return removeGroundItem(player, floorItem, true);
}

public static final boolean removeGroundItem(Player player, final FloorItem floorItem, boolean add) {
int regionId = floorItem.getTile().getRegionId();
Region region = getRegion(regionId);
if (!region.getGroundItemsSafe().contains(floorItem))
return false;
if (player.getInventory().getFreeSlots() == 0 && (!floorItem.getDefinitions().isStackable() || !player.getInventory().containsItem(floorItem.getId(), 1))) {
player.getPackets().sendGameMessage("Not enough space in your inventory.");
return false;
}
region.getGroundItemsSafe().remove(floorItem);
if (add)
    player.getInventory().addItemMoneyPouch(new Item(floorItem.getId(), floorItem.getAmount()));
if (floorItem.isInvisible()) {
player.getPackets().sendRemoveGroundItem(floorItem);
return true;
} else {
for (Player p2 : World.getPlayers()) {
if (p2 == null || !p2.hasStarted() || p2.hasFinished() || p2.getPlane() != floorItem.getTile().getPlane() || !p2.getMapRegionsIds().contains(regionId))
continue;
p2.getPackets().sendRemoveGroundItem(floorItem);
}
if (floorItem.isForever()) {
CoresManager.slowExecutor.schedule(new Runnable() {
@Override
public void run() {
try {
addGroundItemForever(floorItem, floorItem.getTile());
}
catch (Throwable e) {
Logger.handle(e);
}
}
}, 60, TimeUnit.SECONDS);
}
return true;
}
}

	public static final void sendObjectAnimation(WorldObject object, Animation animation) {
		sendObjectAnimation(null, object, animation);
	}

	public static final void sendObjectAnimation(Entity creator, WorldObject object, Animation animation) {
		if (creator == null) {
			for (Player player : World.getPlayers()) {
				if (player == null || !player.hasStarted()
						|| player.hasFinished() || !player.withinDistance(object))
					continue;
				player.getPackets().sendObjectAnimation(object, animation);
			}
		} else {
			for (int regionId : creator.getMapRegionsIds()) {
				List<Integer> playersIndexes = getRegion(regionId)
						.getPlayerIndexes();
				if (playersIndexes == null)
					continue;
				for (Integer playerIndex : playersIndexes) {
					Player player = players.get(playerIndex);
					if (player == null || !player.hasStarted()
							|| player.hasFinished()
							|| !player.withinDistance(object))
						continue;
					player.getPackets().sendObjectAnimation(object, animation);
				}
			}
		}
	}


	public static final void sendGraphics(Entity creator, Graphics graphics,
			WorldTile tile) {
		if (creator == null) {
			for (Player player : World.getPlayers()) {
				if (player == null || !player.hasStarted()
						|| player.hasFinished() || !player.withinDistance(tile))
					continue;
				player.getPackets().sendGraphics(graphics, tile);
			}
		} else {
			for (int regionId : creator.getMapRegionsIds()) {
				List<Integer> playersIndexes = getRegion(regionId)
						.getPlayerIndexes();
				if (playersIndexes == null)
					continue;
				for (Integer playerIndex : playersIndexes) {
					Player player = players.get(playerIndex);
					if (player == null || !player.hasStarted()
							|| player.hasFinished()
							|| !player.withinDistance(tile))
						continue;
					player.getPackets().sendGraphics(graphics, tile);
				}
			}
		}
	}

	public static final void sendProjectile(Entity shooter,
			WorldTile startTile, WorldTile receiver, int gfxId,
			int startHeight, int endHeight, int speed, int delay, int curve,
			int startDistanceOffset) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId)
					.getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null
						|| !player.hasStarted()
						|| player.hasFinished()
						|| (!player.withinDistance(shooter) && !player
								.withinDistance(receiver)))
					continue;
				player.getPackets().sendProjectile(null, startTile, receiver,
						gfxId, startHeight, endHeight, speed, delay, curve,
						startDistanceOffset, 1);
			}
		}
	}

	public static final void sendProjectile(WorldTile shooter, Entity receiver,
			int gfxId, int startHeight, int endHeight, int speed, int delay,
			int curve, int startDistanceOffset) {
		for (int regionId : receiver.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId)
					.getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null
						|| !player.hasStarted()
						|| player.hasFinished()
						|| (!player.withinDistance(shooter) && !player
								.withinDistance(receiver)))
					continue;
				player.getPackets().sendProjectile(null, shooter, receiver,
						gfxId, startHeight, endHeight, speed, delay, curve,
						startDistanceOffset, 1);
			}
		}
	}

	public static final void sendProjectile(Entity shooter, WorldTile receiver,
			int gfxId, int startHeight, int endHeight, int speed, int delay,
			int curve, int startDistanceOffset) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId)
					.getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null
						|| !player.hasStarted()
						|| player.hasFinished()
						|| (!player.withinDistance(shooter) && !player
								.withinDistance(receiver)))
					continue;
				player.getPackets().sendProjectile(null, shooter, receiver,
						gfxId, startHeight, endHeight, speed, delay, curve,
						startDistanceOffset, shooter.getSize());
			}
		}
	}

	public static final void sendProjectile(Entity shooter, Entity receiver,
			int gfxId, int startHeight, int endHeight, int speed, int delay,
			int curve, int startDistanceOffset) {
		for (int regionId : shooter.getMapRegionsIds()) {
			List<Integer> playersIndexes = getRegion(regionId)
					.getPlayerIndexes();
			if (playersIndexes == null)
				continue;
			for (Integer playerIndex : playersIndexes) {
				Player player = players.get(playerIndex);
				if (player == null
						|| !player.hasStarted()
						|| player.hasFinished()
						|| (!player.withinDistance(shooter) && !player
								.withinDistance(receiver)))
					continue;
				int size = shooter.getSize();
				player.getPackets().sendProjectile(
						receiver,
						new WorldTile(shooter.getCoordFaceX(size), shooter
								.getCoordFaceY(size), shooter.getPlane()),
								receiver, gfxId, startHeight, endHeight, speed, delay,
								curve, startDistanceOffset, size);
			}
		}
	}

	public static final boolean isMultiArea(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		int regionId = tile.getRegionId();
		return  (destX >= 3462 && destX <= 3511 && destY >= 9481 && destY <= 9521 && tile.getPlane() == 0) //kalphite queen lair
				|| (destX >= 4540 && destX <= 4799 && destY >= 5052 && destY <= 5183 && tile.getPlane() == 0) // thzaar city
				|| (destX >= 1721 && destX <= 1791 && destY >= 5123 && destY <= 5249) // mole
				|| (destX >= 3029 && destX <= 3374 && destY >= 3759 && destY <= 3903)//wild
				|| (destX >= 2250 && destX <= 2280 && destY >= 4670 && destY <= 4720)
				|| (destX >= 1363 && destX <= 1385 && destY >= 6613 && destY <= 6635)//Blink
				|| (destX >= 2517 && destX <= 2538 && destY >= 5227 && destY <= 5243)//Yk
				|| (destX >= 3198 && destX <= 3380 && destY >= 3904 && destY <= 3970)
				|| (destX >= 3530 && destX <= 3575 && destY >= 9480 && destY <= 9520)//vorago
				|| (destX >= 3191 && destX <= 3326 && destY >= 3510 && destY <= 3759)
				|| (destX >= 2987 && destX <= 3006 && destY >= 3912 && destY <= 3937)
				|| (destX >= 2894 && destX <= 2948 && destY >= 4423 && destY <= 4479)
				|| (destX >= 2245 && destX <= 2295 && destY >= 4675 && destY <= 4720)
				|| (destX >= 2450 && destX <= 3520 && destY >= 9450 && destY <= 9550)
				|| (destX >= 3006 && destX <= 3071 && destY >= 3602 && destY <= 3710)
				|| (destX >= 3134 && destX <= 3192 && destY >= 3519 && destY <= 3646)
				|| (destX >= 2815 && destX <= 2966 && destY >= 5240 && destY <= 5375)//wild
				|| (destX >= 2840 && destX <= 2950 && destY >= 5190 && destY <= 5230) // godwars
				|| (destX >= 3547 && destX <= 3555 && destY >= 9690 && destY <= 9699) // zaros
				// godwars
				|| KingBlackDragon.atKBD(tile) // King Black Dragon lair
				|| TormentedDemon.atTD(tile) // Tormented demon's area
				|| Bork.atBork(tile) // Bork's area
				|| (destX >= 2970 && destX <= 3000 && destY >= 4365 && destY <= 4400)// corp
				|| (destX >= 3195 && destX <= 3327 && destY >= 3520
				&& destY <= 3970 || (destX >= 2376 && 5127 >= destY
				&& destX <= 2422 && 5168 <= destY))
				|| (destX >= 2374 && destY >= 5129 && destX <= 2424 && destY <= 5168) // pits
				|| (destX >= 2864 && destY >= 2981 && destX <= 2878 && destY <= 2995) // boss raids
				|| (destX >= 2622 && destY >= 5696 && destX <= 2573 && destY <= 5752) // torms
				|| (destX >= 2368 && destY >= 3072 && destX <= 2431 && destY <= 3135) // castlewars
				|| (destX >= 3780 && destY >= 3542 && destX <= 3834 && destY <= 3578) // Sunfreet 
				// out
				|| (destX >= 2365 && destY >= 9470 && destX <= 2436 && destY <= 9532) // castlewars
				|| (destX >= 2948 && destY >= 5537 && destX <= 3071 && destY <= 5631) // Risk
				// ffa.
				|| (destX >= 2756 && destY >= 5537 && destX <= 2879 && destY <= 5631)	//Safe ffa
				|| (destX >= 1490 && destX <= 1515 && destY >= 4696 && destY <= 4714) // chaos dwarf battlefield
				|| (destX >= 3333 && destX <= 3383 && destY >= 9345 && destY <= 9450) //Smokey well 1
				|| (destX >= 3072 && destX <= 3136 && destY >= 4287 && destY <= 4416) //Smokey well 2
				|| (destX >= 3140 && destX <= 3331 && destY >= 5441 && destY <= 5568) //CHAOS TUNNELS
				|| (destX >= 1986 && destX <= 2045 && destY >= 4162 && destY <= 4286) || regionId == 16729 //glacors
				|| (destX >= 3261 && destX <= 3329 && destY >= 4287 && destY <= 4416) //smokey well 3
				|| (destX >= 2483 && destX <= 2499 && destY >= 2847 && destY <= 2861) //Boss Raids
				|| (tile.getX() >= 3011 && tile.getX() <= 3132 && tile.getY() >= 10052 && tile.getY() <= 10175
				&& (tile.getY() >= 10066 || tile.getX() >= 3094)) //fortihrny dungeon
				
				;
		// in

		// multi
	}
	
	
	//Dreadnaut Minigame - items are safe
	public static boolean isMiniGame(WorldTile tile){
		int destX = tile.getX();
		int destY = tile.getY();
		return  (destX >= 3154 && destX <= 3185 && destY >= 9755 && destY <=  9775); //DreadnautRoom
	}
	
	public static boolean isRestrictedCannon(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return  ((destX >= 2250 && destX <= 2302 && destY >= 4673 && destY <=  4725) //King Black Dragon
				|| (destX >= 3082 && destX <= 3122 && destY >= 5512 && destY <= 5560) //Bork
				|| (destX >= 3462 && destX <= 3512 && destY >= 9473 && destY <= 9525) //Kalphite Queen
				|| (destX >= 2894 && destX <= 2948 && destY >= 4423 && destY <= 4479) //Dagganoth Kings
				|| (destX >= 2574 && destX <= 2634 && destY >= 5692 && destY <= 5759) //Tormented Demons
				|| (destX >= 2969 && destX <= 3005 && destY >= 4357 && destY <= 4405) //Corporeal Beast
				|| (destX >= 2898 && destX <= 2946 && destY >= 5181 && destY <= 5226) //Nex
				|| (destX >= 2822 && destX <= 2936 && destY >= 5238 && destY <= 5379) //God Wars
				|| (destX >= 2943 && destX <= 3561 && destY >= 3521 && destY <= 4052) //Wilderness
				|| (destX >= 2518 && destX <= 2543 && destY >= 5226 && destY <= 5241) //Yklagor
				|| (destX >= 2844 && destX <= 2868 && destY >= 9625 && destY <= 9650) //Sunfreet
				|| (destX >= 1365 && destX <= 1387 && destY >= 6613 && destY <= 6636) //Blink
				|| (destX >= 3154 && destX <= 3185 && destY >= 9755 && destY <=  9775)) //DreadnautRoom
				;
	}
	
	public static final boolean isMorytaniaArea(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return  ((destX >= 3423 && destX <= 3463 && destY >= 3205 && destY <=  3268) //haunted mine
				|| (destX >= 3464 && destX <= 3722 && destY >= 3160 && destY <= 3602) //Rest of mory
				|| (destX >= 3413 && destX <= 3463 && destY >= 3206 && destY <= 3346) //Snail maze
				|| (destX >= 3399 && destX <= 3463 && destY >= 3347 && destY <= 3450) //Part of swamp
				|| (destX >= 3413 && destX <= 3463 && destY >= 3451 && destY <= 3467) //Part of swamp
				|| (destX >= 3420 && destX <= 3463 && destY >= 3468 && destY <= 3607) //Part of swamp
				|| (destX >= 3398 && destX <= 3463 && destY >= 3508 && destY <= 3607) //Part of upper part
				|| (destX >= 3409 && destX <= 3419 && destY >= 3499 && destY <= 3507) //Part of upper part
				|| (destX >= 3397 && destX <= 3660 && destY >= 9607 && destY <= 9852) //morytania underground
				|| (destX >= 3466 && destX <= 3588 && destY >= 9857 && destY <= 9978) //werewolf agil, experiments
				|| (destX >= 3643 && destX <= 3728 && destY >= 9847 && destY <= 9935) //Ectofun
				|| (destX >= 2254 && destX <= 2287 && destY >= 5142 && destY <= 5162) //Drakan tomb
				|| (destX >= 2686 && destX <= 2818 && destY >= 4416 && destY <= 4606) //haunted mine floors
				|| (destX >= 3106 && destX <= 3218 && destY >= 4540 && destY <= 4680)) //Tarn's lair
				;
	}
	public static final boolean isSmokeyArea(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return  ((destX >= 3199 && destX <= 3330 && destY >= 9341 && destY <=  9406) //Smoke dungeon
				|| (destX >= 3333 && destX <= 3383 && destY >= 9345 && destY <= 9450) //Smokey well 1
				|| (destX >= 3072 && destX <= 3136 && destY >= 4287 && destY <= 4416) //Smokey well 2
				|| (destX >= 3261 && destX <= 3329 && destY >= 4287 && destY <= 4416)) //Smokey well 3
				;
	}
	
	public static final boolean isDesertArea(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return  ((destX <= 3294 && destX >= 3133 && destY <= 3132 && destY >= 2614)
	|| (destX <= 3566 && destX >= 3295 && destY <= 3115 && destY >= 2585)
	|| (destX <= 3512 && destX >= 3315 && destY <= 3132 && destY >= 3110)
	|| (destX <= 3355 && destX >= 3327 && destY <= 3156 && destY >= 3131))
	;
}
	
	public static final boolean isSinkArea(WorldTile tile) {
		int destX = tile.getX();
		int destY = tile.getY();
		return  ((destX <= 1613 && destX >= 1534 && destY <= 4425 && destY >= 4346))
	;
}

	public static final boolean isPvpArea(WorldTile tile) {
		return (Wilderness.isAtWild(tile));
	}
	

	public static void destroySpawnedObject(WorldObject object, boolean clip) {
		getRegion(object.getRegionId()).removeObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion());
    }

	public static void destroySpawnedObject(WorldObject object) {
		getRegion(object.getRegionId()).removeObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion());
    }
	
	
	


    public static final void spawnTempGroundObject(final WorldObject object, final int replaceId, long time) {
	spawnObject(object);
	CoresManager.slowExecutor.schedule(new Runnable() {
	    @Override
	    public void run() {
		try {
		    removeObject(object);
		    addGroundItem(new Item(replaceId), object, null, false, 180);
		}
		catch (Throwable e) {
		    Logger.handle(e);
		}
	    }
	}, time, TimeUnit.MILLISECONDS);
    }

	public static void sendWorldMessage(String message, boolean forStaff) {
		for (Player p : World.getPlayers()) {
			if (p == null || !p.isRunning() || p.isYellOff() || (forStaff && p.getRights() == 0))
				continue;
			p.getPackets().sendGameMessage(message);
		}
	}

	public static final void sendProjectile(WorldObject object, WorldTile startTile,
			WorldTile endTile, int gfxId, int startHeight, int endHeight, int speed, int delay,
			int curve, int startOffset) {
		for(Player pl : players) {
			if(pl == null || !pl.withinDistance(object, 20))
				continue;
			pl.getPackets().sendProjectile(null, startTile, endTile, gfxId,
					startHeight, endHeight, speed, delay, curve, startOffset, 1);
		}
	}

	public static void deleteObject(int i, int j, boolean b) {
		// TODO Auto-generated method stub
		
	}

	public static void spawnObject(int i, WorldTile worldTile, int j, boolean b) {
		// TODO Auto-generated method stub
		
	}
	
    public static final void turnPublic(FloorItem floorItem, int publicTime) {
	if (!floorItem.isInvisible())
	    return;
	int regionId = floorItem.getTile().getRegionId();
	final Region region = getRegion(regionId);
	if (!region.getGroundItemsSafe().contains(floorItem))
	    return;
	Player realOwner = floorItem.hasOwner() ? World.getPlayer(floorItem.getOwner()) : null;
	if (!ItemConstants.isTradeable(floorItem)) {
	    region.getGroundItemsSafe().remove(floorItem);
	    if (realOwner != null) {
		if (realOwner.getMapRegionsIds().contains(regionId) && realOwner.getPlane() == floorItem.getTile().getPlane())
		    realOwner.getPackets().sendRemoveGroundItem(floorItem);
	    }
	    return;
	}
	floorItem.setInvisible(false);
	for (Player player : players) {
	    if (player == null || player == realOwner || !player.hasStarted() || player.hasFinished() || player.getPlane() != floorItem.getTile().getPlane() || !player.getMapRegionsIds().contains(regionId))
		continue;
	    player.getPackets().sendGroundItem(floorItem);
	}
	// disapears after this time
	if (publicTime != -1)
	    removeGroundItem(floorItem, publicTime);
    }
    

    public static final void addGroundItem(final Item item, final WorldTile tile, final Player owner/*
												     * null
												     * for
												     * default
												     */, boolean invisible, long hiddenTime/*
																	    * default
																	    * 3
																	    * minutes
																	    */) {
	addGroundItem(item, tile, owner, invisible, hiddenTime, 2, 150);
    }

    public static final FloorItem addGroundItem(final Item item, final WorldTile tile, final Player owner/*
													  * null
													  * for
													  * default
													  */, boolean invisible, long hiddenTime/*
																		 * default
																		 * 3
																		 * minutes
																		 */, int type) {
	return addGroundItem(item, tile, owner, invisible, hiddenTime, type, 150);
    }
    
    /*
     * type 0 - gold if not tradeable
     * type 1 - gold if destroyable
     * type 2 - no gold
     */
    public static final FloorItem addGroundItem(final Item item, final WorldTile tile, final Player owner, boolean invisible, long hiddenTime/*
																	      * default
																	      * 3
																	      * minutes
																	     
																	     
																	     
																	     
																	     
																	     
																	     */, int type, final int publicTime) {
	/*if (type != 2) {
	    if ((type == 0 && !ItemConstants.isTradeable(item)) || type == 1 && ItemConstants.isDestroy(item)) {

		int price = item.getDefinitions().getValue();
		if (price <= 0)
		    return null;
		item.setId(995);
		item.setAmount(price);
	    }
	}*/
	final FloorItem floorItem = new FloorItem(item, tile, owner, owner != null, invisible);
	final Region region = getRegion(tile.getRegionId());
	region.getGroundItemsSafe().add(floorItem);
	if (invisible) {
	    if (owner != null)
		owner.getPackets().sendGroundItem(floorItem);
	    // becomes visible after x time
	    if (hiddenTime != -1) {
		CoresManager.slowExecutor.schedule(new Runnable() {
		    @Override
		    public void run() {
			try {
			    turnPublic(floorItem, publicTime);
			}
			catch (Throwable e) {
			    Logger.handle(e);
			}
		    }
		}, hiddenTime, TimeUnit.SECONDS);
	    }
	} else {
	    // visible
	    int regionId = tile.getRegionId();
	    for (Player player : players) {
		if (player == null || !player.hasStarted() || player.hasFinished() || player.getPlane() != tile.getPlane() || !player.getMapRegionsIds().contains(regionId))
		    continue;
		if ((owner.isPker && !player.isPker) || (!owner.isPker && player.isPker))
			continue;
		player.getPackets().sendGroundItem(floorItem);
	    }
	    // disapears after this time
	    if (publicTime != -1)
		removeGroundItem(floorItem, publicTime);
	}
	return floorItem;
    }
    
    public static final void spawnObject(WorldObject object) {
	getRegion(object.getRegionId()).spawnObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion(), false);
    }

    public static final void removeObject(WorldObject object) {
	getRegion(object.getRegionId()).removeObject(object, object.getPlane(), object.getXInRegion(), object.getYInRegion());
    }
    

    public static final WorldObject getObjectWithSlot(WorldTile tile, int slot) {
	return getRegion(tile.getRegionId()).getObjectWithSlot(tile.getPlane(), tile.getXInRegion(), tile.getYInRegion(), slot);
    }

}
