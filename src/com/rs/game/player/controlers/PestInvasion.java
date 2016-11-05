package com.rs.game.player.controlers;

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
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Summoning;
import com.rs.game.player.content.pet.Pets;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.Utils;
import com.rs.game.npc.pestinvasion.*;

public class PestInvasion extends Controler {

	public static final WorldTile OUTSIDE = new WorldTile(4520, 5516, 0);

	private static final int VOID_KNIGHT = 12282;

	private static final int[] MUSICS = {1088, 1082, 1086};

	public void playMusic() {
		player.getMusicsManager().playMusic(selectedMusic);
	}

	private final int[][] WAVES = {
			{3730,3740,3745,3750,3760,3770,3775}
			,{3730,3740,3745,3750,3760,3770,3775,3730,3740,3745,3750,3760,3770,3775}
			,{3731,3741,3746,3751,3761,3771,3776}
			,{3731,3741,3746,3751,3761,3771,3776,3730,3740,3745,3750,3760,3770,3775}
			,{3731,3741,3746,3751,3761,3771,3776,3730,3740,3745,3750,3760,3770,3775,3730,3740,3745,3750,3760,3770,3775}
			,{3731,3741,3746,3751,3761,3771,3776,3731,3741,3746,3751,3761,3771,3776}
			,{6344,6344,6344,6344,6344}
			,{6344,6344,6344,6344,6344,3730,3740,3745,3750,3760,3770,3775}
			,{6344,6344,6344,6344,6344,3730,3740,3745,3750,3760,3770,3775,3730,3740,3745,3750,3760,3770,3775}
			,{6344,6344,6344,6344,6344,3731,3741,3746,3751,3761,3771,3776}
			,{6344,6344,6344,6344,6344,3731,3741,3746,3751,3761,3771,3776,3730,3740,3745,3750,3760,3770,3775}
			,{6344,6344,6344,6344,6344,3731,3741,3746,3751,3761,3771,3776,3730,3740,3745,3750,3760,3770,3775,3730,3740,3745,3750,3760,3770,3775}
			,{6344,6344,6344,6344,6344,3731,3741,3746,3751,3761,3771,3776,3731,3741,3746,3751,3761,3771,3776}
			,{6344,6344,6344,6344,6344,6344,6344,6344,6344,6344}
			,{11696,8320,8316,8312} //Wave 15
			,{11696,8320,8316,8312,8320,8316,8312}
			,{11696,11696,11696,11696,11696,8320,8316,8312}
			,{11696,8320,8320,8316,8316,8312}
			,{11696,8320,8316,8312,11696,8320,8316,8312,11696,8320,8316,8312}
			,{11696,8320,8320,8316,8316,8312,11696,8320,8320,8316,8316,8312}
			,{6344,6344,6344,6344,6344,3730,3740,3745,3750,3760,3770,3775,11696,8320,8316,8312}
			,{11910,11910,11910} //Wave 22
			,{11910,11910,11895}
			,{11910,11910,11916,11895}
			,{11916,11916,11916,11895,11895}
			,{11916,11910,11902}
			,{11910,11910,11916,11895,11902}
			,{11916,11916,11916,11924,11924,11909}
			,{11902,11909,11909}
			,{11902,11902,11902,11902,11925}
			,{11902,11909,11902,11902,11930}
			,{11902,11909,11902,11902,11939}
			,{11925,11930,11939} 
			,{941} //Wave 34
			,{941,941}
			,{941,1591}
			,{941,1591,1591}
			,{1591,1592}
			,{941,1592,1592}
			,{51,51,51,1592}
			,{51,51,5362}
			,{5362,5363}
			,{5363,5363}
			,{50}
			,{11871} //Wave 45
			,{11871,11871}
			,{12841}
			,{8335} //Wave 48
			,{6358} //Wave 49 (Pest Queen)

	};


	private int[] boundChuncks;
	private Stages stage;
	private boolean logoutAtEnd;
	private boolean login;
	public boolean spawned;
	public int selectedMusic;

	public static void enterPestInvasion(Player player) {
		if(player.getFamiliar() != null || player.getPet() != null || Summoning.hasPouch(player) || Pets.hasPet(player)) {
			player.getDialogueManager().startDialogue("SimpleNPCMessage", VOID_KNIGHT, "No pets in this minigame! This is a fight for YOU, not your friends!");
			return;
		}
		player.getControlerManager().startControler("PestInvasionControler", 1); //start at wave 1
	}

	private static enum Stages {
		LOADING,
		RUNNING,
		DESTROYING
	}

	@Override
	public void start() {
		loadCave(false);
	}

	@Override
	public boolean processButtonClick(int interfaceId, int componentId, int slotId, int packetId) {
		if(stage != Stages.RUNNING)
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
		if(object.getId() == 26866) {
			if(stage != Stages.RUNNING)
				return false;
			exitCave(1);
			return false;
		}
		return true;
	}


	/*
	 * return false so wont remove script
	 */
	@Override
	public boolean login() {
		loadCave(true);
		return false;
	}


	public void loadCave(final boolean login) {
		this.login = login;
		stage = Stages.LOADING;
		player.lock(); //locks player
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				//finds empty map bounds
				boundChuncks = RegionBuilder.findEmptyChunkBound(8, 8); 
				//copys real map into the empty map
				RegionBuilder.copyAllPlanesMap(561, 695, boundChuncks[0], boundChuncks[1], 8);
				//selects a music
				selectedMusic = MUSICS[Utils.random(MUSICS.length)];
				player.setNextWorldTile(!login ? getWorldTile(23, 25) : getWorldTile(24, 38) );
				//1delay because player cant walk while teleing :p, + possible issues avoid
				WorldTasksManager.schedule(new WorldTask()  {
					@Override
					public void run() {
						if(!login) {
							WorldTile walkTo = getWorldTile(24, 38);
							player.addWalkSteps(walkTo.getX(), walkTo.getY());
						}
						player.getDialogueManager().startDialogue("SimpleNPCMessage", VOID_KNIGHT, "You're on your own now, JalYt.<br>Prepare to fight for your life!");
						player.setForceMultiArea(true);
						playMusic();
						player.unlock(); //unlocks player
						stage = Stages.RUNNING;
					}

				}, 1);
				if(!login) {
					/*
					 * lets stress less the worldthread, also fastexecutor used for mini stuff
					 */
					CoresManager.fastExecutor.schedule(new TimerTask() {

						@Override
						public void run() {
							if(stage != Stages.RUNNING)
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

	public WorldTile getSpawnTile() {
		switch(Utils.random(5)) {
		case 0:
			return getWorldTile(32, 54);
		case 1:
			return getWorldTile(14, 54);
		case 2:
			return getWorldTile(13, 38);
		case 3:
			return getWorldTile(35, 39);
		case 4:
		default:
			return getWorldTile(87, 118);
		}
	}

	@Override
	public void moved() {
		if(stage != Stages.RUNNING || !login)
			return;
		login = false;
		setWaveEvent();
	}

	public void startWave() {
		int currentWave = getCurrentWave();
		if(currentWave > WAVES.length) {
			win();
			return;
		}
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 11 : 0, 316);
		player.getPackets().sendConfig(639, currentWave);
		if(stage != Stages.RUNNING)
			return;
		for(int id : WAVES[currentWave-1]) {
			if (id == 6358)
				new PestQueen(id, getSpawnTile(), this);
			else
				new PestInvasionNpcs(id, getSpawnTile());
		}
		spawned = true;
	}

	public void spawnHealers() {
		if(stage != Stages.RUNNING)
			return;
		for(int i = 0; i < 8; i++)
			new PestInvasionNpcs(6344, getSpawnTile());
	}

	public void win() {
		if(stage != Stages.RUNNING)
			return;
		exitCave(4);
	}


	public void nextWave() {
		playMusic();
		setCurrentWave(getCurrentWave()+1);
		if(logoutAtEnd) {
			player.forceLogout();
			return;
		}
		setWaveEvent();
	}

	public void setWaveEvent() {
		if(getCurrentWave() == 49) {
			player.getDialogueManager().startDialogue("SimpleNPCMessage", VOID_KNIGHT, "Holy shit, the Pest Queen is here!");
		player.getInventory().addItem(23351, 8);
		}
		if(getCurrentWave() == 15) {
			player.getDialogueManager().startDialogue("SimpleNPCMessage", VOID_KNIGHT, "Congratulations, the pests are dead! Are those elite knights?!");
		player.getInventory().addItem(23351, 5);
		}
		if(getCurrentWave() == 22) {
			player.getDialogueManager().startDialogue("SimpleNPCMessage", VOID_KNIGHT, "What are the these creatures? We must fight on!");
		player.getInventory().addItem(23351, 5);
		}
		if(getCurrentWave() == 48) {
			player.getDialogueManager().startDialogue("SimpleNPCMessage", VOID_KNIGHT, "Watch out, this is the deadliest mage on Noszscape!");
		player.getInventory().addItem(23351, 5);
		}	
		if(getCurrentWave() == 45) {
			player.getDialogueManager().startDialogue("SimpleNPCMessage", VOID_KNIGHT, "Damn creatures of the dead, die already!");
		player.getInventory().addItem(23351, 5);
		}
		if(getCurrentWave() == 34) {
			player.getDialogueManager().startDialogue("SimpleNPCMessage", VOID_KNIGHT, "I see dragons ahead, quick take this shield!");
			player.getInventory().addItem(1540, 1);
		}
		CoresManager.fastExecutor.schedule(new TimerTask() {

			@Override
			public void run() {
				try {
					if(stage != Stages.RUNNING)
						return;
					startWave();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, 600);
	}

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
					player.getPackets().sendGameMessage("You have been defeated!");
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


	@Override
	public void magicTeleported(int type) {
		exitCave(2);
	}

	/*
	 * logout or not. if didnt logout means lost, 0 logout, 1, normal,  2 tele
	 */
	public void exitCave(int type) {
		stage = Stages.DESTROYING;
		WorldTile outside = new WorldTile(OUTSIDE); //radomizes alil
		if(type == 0 || type == 2)
			player.setLocation(outside);
		else {
			player.setForceMultiArea(false);
			player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ?  11 : 0);
			if(type == 1 || type == 4) {
				player.setNextWorldTile(outside);
				if(type == 4) {
					player.setCompletedFightCaves();
					player.reset();
					player.getInventory().addItem(19785, 1);
					player.getInventory().addItem(19786, 1);
					player.setCompletedPestInvasion();
					player.getDialogueManager().startDialogue("SimpleNPCMessage", VOID_KNIGHT, "You even defeated the Pest Queen, I am most impressed! Please accept this gift as a reward.");
					player.getPackets().sendGameMessage("You were victorious!!");
					if(!player.getInventory().addItem(22401, 1)) {
						World.addGroundItem(new Item(22401, 1), new WorldTile(player), player, true, 180);
						World.addGroundItem(new Item(995, 5000000), new WorldTile(player), player, true, 180);
					}else if(!player.getInventory().addItemMoneyPouch(995, 5000000)) 
						World.addGroundItem(new Item(995, 5000000), new WorldTile(player), player, true, 180);
				}else if(getCurrentWave() == 1) 
					player.getDialogueManager().startDialogue("SimpleNPCMessage", VOID_KNIGHT, "Well I suppose you tried... better luck next time.");
				else{
					int coins = getCurrentWave() * 100000 / WAVES.length;
					coins *= Settings.DROP_RATE; //10x more
					if(!player.getInventory().addItemMoneyPouch(995, coins)) 
						World.addGroundItem(new Item(995, coins), new WorldTile(player), player, true, 180);
					player.getDialogueManager().startDialogue("SimpleNPCMessage", VOID_KNIGHT, "Well done in the cave, here, take TokKul as reward.");
					//TODO tokens
				}
			}
			removeControler();
		}
		/*
		 * 1200 delay because of leaving
		 */
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				RegionBuilder.destroyMap(boundChuncks[0], boundChuncks[1], 8, 8);
			}
		}, 1200, TimeUnit.MILLISECONDS);
	}

	/*
	 * gets worldtile inside the map
	 */
	public WorldTile getWorldTile(int mapX, int mapY) {
		return new WorldTile(boundChuncks[0]*8 + mapX, boundChuncks[1]*8 + mapY, 0);
	}


	/*
	 * return false so wont remove script
	 */
	@Override
	public boolean logout() {
		/*
		 * only can happen if dungeon is loading and system update happens
		 */
		if(stage != Stages.RUNNING)
			return false;
		exitCave(0);
		return false;
		
	}
    @Override
    public boolean processMagicTeleport(WorldTile toTile) {
	player.getDialogueManager().startDialogue("SimpleMessage", "You cannot teleport out of the fight caves!");
	return false;
    }

    @Override
	public boolean processItemTeleport(WorldTile toTile) {
	player.getDialogueManager().startDialogue("SimpleMessage", "You cannot teleport out of the fight caves!");
	return false;
    }

    @Override
	public boolean processObjectTeleport(WorldTile toTile) {
            player.getDialogueManager().startDialogue("SimpleMessage", "You cannot teleport out of the fight caves!");
	return false;

	}

	public int getCurrentWave() {
		if (getArguments() == null || getArguments().length == 0) 
			return 0;
		return (Integer) getArguments()[0];
	}

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
		if(stage != Stages.RUNNING)
			return;
		exitCave(2);
	}
}
