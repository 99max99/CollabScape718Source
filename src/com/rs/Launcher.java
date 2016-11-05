package com.rs;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import com.alex.store.Index;
import com.rs.cache.Cache;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cache.loaders.ObjectDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.GameEngine;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.npc.combat.CombatScriptsHandler;
import com.rs.game.player.LendingManager;
import com.rs.game.player.Player;
import com.rs.game.player.content.FishingSpotsHandler;
import com.rs.game.player.content.FriendChatsManager;
import com.rs.game.player.content.botanybay.BotanyBay;
import com.rs.game.player.content.clans.ClansManager;
import com.rs.game.player.content.grandExchange.GrandExchange;
import com.rs.game.player.controlers.ControlerHandler;
import com.rs.game.player.cutscenes.CutscenesHandler;
import com.rs.game.player.dialogues.DialogueHandler;
import com.rs.game.worldlist.WorldList;
import com.rs.net.ServerChannelHandler;
import com.rs.utils.Censor;
import com.rs.utils.DTRank;
import com.rs.utils.DisplayNames;
import com.rs.utils.IPBanL;
import com.rs.utils.ItemBonuses;
import com.rs.utils.ItemExamines;
import com.rs.utils.ItemSpawns;
import com.rs.utils.KillStreakRank;
import com.rs.utils.Logger;
import com.rs.utils.MapArchiveKeys;
import com.rs.utils.MapAreas;
import com.rs.utils.MusicHints;
import com.rs.utils.NPCBonuses;
import com.rs.utils.NPCCombatDefinitionsL;
import com.rs.utils.NPCDrops;
import com.rs.utils.NPCExamines;
import com.rs.utils.NPCSpawning;
import com.rs.utils.NPCSpawns;
import com.rs.utils.ObjectSpawning;
import com.rs.utils.ObjectSpawns;
import com.rs.utils.PkRank;
import com.rs.utils.RewardHandler;
import com.rs.utils.SerializableFilesManager;
import com.rs.utils.ShopsHandler;
import com.rs.utils.Utils;
import com.rs.utils.huffman.Huffman;
import com.rspserver.motivote.Motivote;

public final class Launcher {

	public static void main(String[] args) throws Exception {
		long currentTime = Utils.currentTimeMillis();
		Logger.log("Exylum", "Reading Cache Intake...");
		Cache.init();
		/*
		 * Database
		 */
		//Logger.log("Exylum", "Preparing MYSQL Database...");
		//World.database().connect();
		KillStreakRank.init();
		GrandExchange.init();
		ItemSpawns.init();
		Huffman.init();
		Logger.log("Exylum", "Loaded Cache...");
		Logger.log("Exylum", "Loading Data...");
		new Motivote(new RewardHandler(), "http://www.Exylum.org/vote/", "66df0b2b").start();
		World.loadWell(); // remove this if server doesnt start properly
		WorldList.init();
		Censor.init();
		DisplayNames.init();
		IPBanL.init();
		PkRank.init();
		DTRank.init();
		MapArchiveKeys.init();
		MapAreas.init();
		ObjectSpawns.init();
		NPCSpawns.init();
		NPCCombatDefinitionsL.init();
		NPCBonuses.init();
		NPCDrops.init();
		ItemExamines.init();
		ItemBonuses.init();
		MusicHints.init();
		BotanyBay.init();
		ShopsHandler.init();
		NPCExamines.init();
		Logger.log("Exylum", "Loading Global Spawns...");
		NPCSpawning.spawnNPCS();
		ObjectSpawning.spawnNPCS();
		FishingSpotsHandler.init();
		Logger.log("Exylum", "Loading Combat Scripts...");
		CombatScriptsHandler.init();
		Logger.log("Launcher", "Initing Clans Manager...");
		ClansManager.init();
		Logger.log("Launcher", "Initing Lent Items...");
		LendingManager.init();
		Logger.log("Exylum", "Reading Local Handlers...");
		Logger.log("Exylum", "Reading Local Controlers...");
		Logger.log("Exylum", "Reading Local Managers...");
		/*
		 * Game Engine
		 */
		Logger.log("Exylum", "Preparing Game Engine...");
		//GameEngine.get().init();
		/*
		 * Grand Exchange
		 */
		Logger.log("Exylum", "Preparing Grand Exchange...");
		//tradeAbleItems.initialize();
		//GrandExchangePriceLoader.initialize();
		//Offers.load();
		DialogueHandler.init();
		ControlerHandler.init();
		CutscenesHandler.init();
		FriendChatsManager.init();
		Startup();
		Logger.log("Launcher", "Initing Control Panel...");
		CoresManager.init();
		Logger.log("Exylum", "Loading World...");
		World.init();
		Logger.log("Exylum", "Loading Region Builder...");
		RegionBuilder.init();
		try {
			ServerChannelHandler.init();
		} catch (Throwable e) {
			Logger.handle(e);
			Logger.log("Exylum",
					"Failed initing Server Channel Handler. Shutting down...");
			System.exit(1);
			return;
		}
		Logger.log("Exylum", "Server took "
				+ (Utils.currentTimeMillis() - currentTime)
				+ " milliseconds to launch.");
		addAccountsSavingTask();
		if (Settings.HOSTED)
		addCleanMemoryTask();
		// Donations.init();
		//HalloweenEvent.startEvent();
		addrecalcPricesTask();
		//World.spawnObject(new WorldObject(87309, 10, 3122, 3225, 0, 0));
		Logger.log("World", "Exylum is now Online!");
	}
	
    private static void addrecalcPricesTask() {
	Calendar c = Calendar.getInstance();
	c.add(Calendar.DAY_OF_MONTH, 1);
	c.set(Calendar.HOUR, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	int minutes = (int) ((c.getTimeInMillis() - Utils.currentTimeMillis()) / 1000 / 60);
	int halfDay = 12 * 60;
	if (minutes > halfDay)
	    minutes -= halfDay;
	CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
	    @Override
	    public void run() {
		try {
		    GrandExchange.recalcPrices();
		}
		catch (Throwable e) {
		    Logger.handle(e);
		}

	    }
	}, minutes, halfDay, TimeUnit.MINUTES);
    }
	

	private static void addCleanMemoryTask() {
		CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					cleanMemory(Runtime.getRuntime().freeMemory() < Settings.MIN_FREE_MEM_ALLOWED);
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 0, 10, TimeUnit.MINUTES);
	}
	
	 public static void Startup() {
		  Gui.main(null);
		 }

		private static void addAccountsSavingTask() {
			CoresManager.slowExecutor.scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {
					try {
						saveFiles();
						Logger.log("Online", 
								"There are currently " + (World.getPlayers().size())
										+ " players playing " + Settings.SERVER_NAME
										+ ".");
					} catch (Throwable e) {
						Logger.handle(e);
					}

				}
			}, 1, 1, TimeUnit.MINUTES);//can be changed to seconds using "TimeUnit.SECONDS" as of now every one minute it will save the players.
		}

	public static void saveFiles() {
		for (Player player : World.getPlayers()) {
			if (player == null || !player.hasStarted() || player.hasFinished())
				continue;
			SerializableFilesManager.savePlayer(player);
		}
		DisplayNames.save();
		IPBanL.save();
		PkRank.save();
		DTRank.save();
		KillStreakRank.save();
		GrandExchange.save();
	}

	public static void cleanMemory(boolean force) {
		if (force) {
			ItemDefinitions.clearItemsDefinitions();
			NPCDefinitions.clearNPCDefinitions();
			ObjectDefinitions.clearObjectDefinitions();
			/*for (Region region : World.getRegions().values())
				region.removeMapFromMemory();*/
		}
		for (Index index : Cache.STORE.getIndexes())
			index.resetCachedFiles();
		CoresManager.fastExecutor.purge();
		System.gc();
	}

	public static void shutdown() {
		try {
			GameEngine.get().shutdown();
			closeServices();
		} finally {
			System.exit(0);
		}
	}

	public static void closeServices() {
		ServerChannelHandler.shutdown();
		CoresManager.shutdown();
		if (Settings.HOSTED) {
			try {
			} catch (Throwable e) {
				Logger.handle(e);
			}
		}
	}

	public static void restart() {
		closeServices();
		System.gc();
		try {
			Runtime.getRuntime().exec("java -server -Xms2048m -Xmx20000m -cp bin;/data/libs/netty-3.5.2.Final.jar;/data/libs/FileStore.jar Launcher false false true false");
			System.exit(0);
		} catch (Throwable e) {
			Logger.handle(e);
		}

	}

	private Launcher() {

	}

}
