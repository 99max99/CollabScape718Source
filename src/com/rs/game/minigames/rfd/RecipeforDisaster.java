package com.rs.game.minigames.rfd;

import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.rs.Settings;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.npc.zombies.ZombieCaves;
import com.rs.game.npc.zombies.ZombiesNPC;
import com.rs.game.player.Player;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;

/**
 * 
 * @author Adam
 * @since Aug, 1st.
 */

public class RecipeforDisaster extends Controler{
	/**
	 * Data
	 */
	
	private int[] regionChucks;
	private RecipeStages stage;
	private boolean logoutAtEnd;
	private boolean login;
	public boolean spawned;
	public static boolean canpray = false;

	
	
	
	
	/**
	 * Holds the Zombies
	 */
	
	
	
	private final int[][] MONSTERS = {
			 {3493}
			,{3494}
			,{3495}
			,{3496}
			,{3491}
	};

	/*
	 * 14281//135
	 * 14339//85
	 */
	
	
	
	/**
	 * 
	 * @author Adam
	 *
	 */
	private static enum RecipeStages {
		LOADING,
		RUNNING,
		DESTROYING
	}

	
	
	/**
	 * Starts game
	 */
	@Override
	public void start() {
		startGame(false);
		
	}
	
	
	
	
	
	/**
	 * Starts the game & loads the map.
	 * @param login
	 */
	
	public void fade (final Player player) {
		final long time = FadingScreen.fade(player);
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					FadingScreen.unfade(player, time, new Runnable() {
						@Override
						public void run() {
							
					
				
				
				}
					});
			} catch (Throwable e) {
				Logger.handle(e);
			}
			}
		
	});
	}
	
	
	
	public void startGame(final boolean login) {
	
		fade(player);
		this.login = login;
		stage = RecipeStages.LOADING;
		player.lock(); //locks player
		canpray = true;
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				//regionChucks = RegionBuilder.findEmptyChunkBound(9, 9);
               // RegionBuilder.copyAllPlanesMap(456, 439, regionChucks[0],// mhmk ima eat icecream have fun
                //		regionChucks[1], 9);
				regionChucks = RegionBuilder.findEmptyChunkBound(8, 8); 
				RegionBuilder.copyAllPlanesMap(235, 667, regionChucks[0], regionChucks[1], 8);//is this rightno urs is abovethes
				
				player.setNextWorldTile(getWorldTile(10, 19) );
				WorldTasksManager.schedule(new WorldTask()  {
					@Override
					public void run() {
						canpray = true;
						player.unlock(); 
						stage = RecipeStages.RUNNING;
					}

				}, 1);
				if(!login) {
					CoresManager.fastExecutor.schedule(new TimerTask() {

						@Override
						public void run() {
							if(stage != RecipeStages.RUNNING)
								return;
							try {
								startWave();
							} catch (Throwable t) {
								Logger.handle(t);
							}
						}
					}, 6000);
				}
			}
		});
	}
	
	
	
	
	/**
	 * 
	 * @return
	 */
	
	
	
	public WorldTile getSpawnTile() {
			return getWorldTile(15, 19);
		}
	

	
	
	
	/**
	 * 
	 * @param player
	 */
	
	
	
	
	public static void enterRfd(Player player) {
		player.getControlerManager().startControler("RecipeforDisaster", 1); 
	}

	
	
	
	
	/**
	 * Handles the buttons.
	 */
	
	
	
	
	@Override
	public boolean processButtonClick(int interfaceId, int componentId, int slotId, int packetId) {
		if(stage != RecipeStages.RUNNING)
			return false;
		if(interfaceId == 182 && (componentId == 6 || componentId == 13)) {
			if(!logoutAtEnd) {
				logoutAtEnd = true;
				player.getPackets().sendGameMessage("<col=ff0000>You will be logged out automatically at the end of this wave.");
				player.getPackets().sendGameMessage("<col=ff0000>If you log out sooner, you will have to repeat this wave.");
			}else
				player.forceLogout();
			return false;
		}
		return true;
	}
	
	
	
	
	
	
	
	/**
	 * return process normaly
	 */
	
	
	
	
	@Override
	public boolean processObjectClick1(WorldObject object) {
		if(object.getId() == 12356) {
			if(stage != RecipeStages.RUNNING)
				return false;
			exitCave(1);
			return false;
		}
		return true;
	}
	
	@Override
	public void moved() {
		if(stage != RecipeStages.RUNNING || !login)
			return;
		login = false;
		setWaveEvent();
	}
	
	public void win() {
		if(stage != RecipeStages.RUNNING)
			return;
		exitCave(4);
	}

	
	public void startWave() {
		int currentWave = getCurrentWave();
		if(currentWave > MONSTERS.length) {
			win();
			return;
		}
		if(stage != RecipeStages.RUNNING)
			return;
		for(int id : MONSTERS[currentWave-1]) {
			new ZombiesNPC(id, getSpawnTile());
			NPC Monster = findNPC(id);
			player.getHintIconsManager().addHintIcon(Monster, 0, -1, false);
		}
		spawned = true;
	
		if (getCurrentWave() == 2) {
			player.rfd1 = true;
			player.getBank().addItem(7453, 1, true);
			player.getBank().addItem(7454, 1, true);
			player.out("2 Pairs of gloves have been added to your bank");
		} else if (getCurrentWave()  == 3) {
			player.rfd2 = true;
			player.getBank().addItem(7455, 1, true);
			player.getBank().addItem(7456, 1, true);
			player.out("2 Pairs of gloves have been added to your bank");
		} else if (getCurrentWave() == 4) {
			player.rfd3 = true;
			player.getBank().addItem(7457, 1, true);
			player.getBank().addItem(7458, 1, true);
			player.out("2 Pairs of gloves have been added to your bank");
		} else if (getCurrentWave() == 5) {
			player.rfd4 = true;
			player.getBank().addItem(7459, 1, true);
			player.getBank().addItem(7460, 1, true);
			player.out("2 Pairs of gloves have been added to your bank");
		}
	}
	
	public static NPC findNPC(int id) {
		for (NPC npc : World.getNPCs()) {
			if (npc == null || npc.getId() != id)
				continue;
			return npc;
		}
		return null;
	}

	
	
	/**
	 * 
	 */
	public void setWaveEvent() {
		
		if (getCurrentWave() == 5) 
			player.getDialogueManager().startDialogue("SimpleNPCMessage", 3491, "You DARE come here !?!?");
		CoresManager.fastExecutor.schedule(new TimerTask() {
			
			
			@Override
			public void run() {
				try {
					if(stage != RecipeStages.RUNNING)
						return;
					startWave();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 600);
	}
	
	
	
	
	/**
	 * Processing.
	 */
	
	
	@Override
	public void process() {
		if(spawned) {
			List<Integer> npcs = World.getRegion(player.getRegionId()).getNPCsIndexes();
			if(npcs == null || npcs.isEmpty())  {
				spawned = false;
				nextWave();
			}
		}
	}
	
	
	/**
	 * Sets the next wave.
	 */
	
	public void nextWave() {
		setCurrentWave(getCurrentWave()+1);
		if(logoutAtEnd) {
			player.forceLogout();
			return;
		}
		setWaveEvent();
	}
	
	
	/**
	 * Death method.
	 */
	@Override
	public boolean sendDeath() {
		player.lock(7);
		player.stopAll();
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					player.setNextAnimation(new Animation(836));
				} else if (loop == 1) {
					player.getPackets().sendGameMessage("Oh, dear you have died.");
				} else if (loop == 3) {
					player.reset();
					exitCave(1);
					player.setNextAnimation(new Animation(-1));
				} else if (loop == 4) {
					player.getPackets().sendMusicEffect(90);
					stop();
				}
				loop++;
			}
		}, 0, 1);
		return false;
	}
	
	
	
	/**
	 * 
	 */
	
	@Override
	public void magicTeleported(int type) {
		exitCave(2);
	}
	
	/*
	 * logout or not. if didnt logout means lost, 0 logout, 1, normal,  2 tele
	 */
	public void exitCave(int type) {
		stage = RecipeStages.DESTROYING;
		int i;
		if (player.isPker)
			i = 1;
		else
			i = 0;
		WorldTile outside = new WorldTile(Settings.HOME_PLAYER_LOCATION[i]); 
		if(type == 0 || type == 2)
			player.setLocation(outside);
		else {
			player.setForceMultiArea(false);
				if(type == 1 || type == 4) {
				player.setNextWorldTile(outside);
				if(type == 4) {
					fade(player);
					player.reset();
					player.rfd5 = true;
					player.getBank().addItem(7461, 1, true);
					player.getBank().addItem(7462, 1, true);
					player.out("2 Pairs of gloves have been added to your bank.");
					canpray = false;
				}
			}
				canpray = false;
			removeControler();
		}
	
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				RegionBuilder.destroyMap(regionChucks[0], regionChucks[1], 8, 8);
			}
		}, 1200, TimeUnit.MILLISECONDS);

	}
	
	
	
	
	
	/*
	 * gets worldtile inside the map
	 */
	public WorldTile getWorldTile(int mapX, int mapY) {
		return new WorldTile(regionChucks[0]*8 + mapX, regionChucks[1]*8 + mapY, 2);
	}
	
	
	
	
	/*
	 * return false so wont remove script
	 */
	@Override
	public boolean logout() {
		/*
		 * only can happen if dungeon is loading and system update happens
		 */
		if(stage != RecipeStages.RUNNING)
			return false;
		exitCave(0);
		return false;

	}
	
	
	
	/**
	 * 
	 * @return
	 */

	public int getCurrentWave() {
		if (getArguments() == null || getArguments().length == 0) 
			return 0;
		return (Integer) getArguments()[0];
	}
	
	
	
	
	
	/**
	 * 
	 * @param wave
	 */

	public void setCurrentWave(int wave) {
		if(getArguments() == null || getArguments().length == 0)
			this.setArguments(new Object[1]);
		getArguments()[0] = wave;
	}

	@Override
	public void forceClose() {
		/*
		 * shouldnt happen
		 */
		if(stage != RecipeStages.RUNNING)
			return;
		exitCave(2);
	}
	
	
	
	public void spawnZombieMembers() {
		if(stage != RecipeStages.RUNNING)
			return;
		for(int i = 0; i < 4; i++)
			new ZombieCaves(2746, getSpawnTile());
	}
	

}
