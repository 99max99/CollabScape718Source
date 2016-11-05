package com.rs.game.player.controlers;


import java.util.ArrayList;
import java.util.concurrent.TimeUnit;



import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.EntityList;
import com.rs.game.RegionBuilder;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.FadingScreen;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.ChatColors;
import com.rs.utils.Misc;


public class KingBlackDragon extends Controler {
	
	private int[] boundChunks;

	protected ArrayList<NPC> monsters;
	public static EntityList<Player> playersInGame = new EntityList<Player>(25);
	static int sizeX = 16; // horizontal size
	static int sizeY = 16; // vertical size
	
	static int chunkX = 464; // bottom left chunk x
	static int chunkY = 1168; // bottom left chunk y
	
	@Override
	public void start() {
		monsters = new ArrayList<NPC>();
		player.lock();
		final long time = FadingScreen.fade(player);
		CoresManager.slowExecutor.schedule(new Runnable() {		
			@Override
			public void run() {
				boundChunks = RegionBuilder.findEmptyChunkBound(sizeX, sizeY); 
				RegionBuilder.copyAllPlanesMap(281, 583, boundChunks[0], boundChunks[1], sizeX, sizeY);
				FadingScreen.unfade(player, time, new Runnable() {
					@Override
					public void run() {
						int maxPrayer = player.getSkills().getLevelForXp(Skills.PRAYER) * 10;
						player.setForceMultiArea(true);
						player.getPrayer().restorePrayer(maxPrayer);
						player.heal(player.getHitpoints() * 10);
						player.setNextWorldTile(getWorldTile(25, 20));
						player.getCombatDefinitions().resetSpells(true);
						player.stopAll();
						player.unlock();
					    if (monsters.size() < 1) {
		                    startMonsters();
					    }  
						
					}
				});
			}
		}, 3000, TimeUnit.MILLISECONDS);
	}

    public void startMonsters() {
        monsters.add(new NPC(50, getRandomSpawnTile2(), -1, true));               
    }
    public void removeMonsters() {
		//monsters.removeMonsters();
		monsters.remove(50);
	}
	@Override
	public boolean processMagicTeleport(WorldTile toTile) {
		destroyRoom(true);
		removeControler();
		return false;
	}


	@Override
	public boolean processItemTeleport(WorldTile toTile) {
		destroyRoom(true);
		removeControler();
		return false;
	}


	@Override
	public boolean processObjectTeleport(WorldTile toTile) {
		destroyRoom(true);
		removeControler();
		return false;
	}
	
	@Override
	public boolean logout() {
		if(playersInGame.size() < 0) {
		//playersInGame.add(player);
			for (Player players : playersInGame) {
				player.sendMessage("someone left the instance.");
			}
		}else {
		destroyRoom(true);
		finishGame();
		removeMonsters();
		for (Player players : playersInGame) {
			player.sendMessage("this is broken.");
		}
		}
		return true;
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
                                    finishGame();
                                    player.setNextAnimation(new Animation(-1));
                            } else if (loop == 4) {
                                    player.getPackets().sendMusicEffect(90);
                                    stop();
                                   // finishGame();
                            }
                            loop++;
                    }
            }, 0, 1);
            return false;
    }
    
	public void setNewOwner() {
		monsters = new ArrayList<NPC>();
			if (monsters.size() < 1) {
		                    startMonsters();
					    }  				
					}
	
	private void removeMapChunks() {
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				RegionBuilder.destroyMap(boundChunks[0], boundChunks[1], sizeX, sizeY);
			}
		}, 1200, TimeUnit.MILLISECONDS);
	}
	
    public WorldTile getRandomSpawnTile2() {
        WorldTile[] tiles = new WorldTile[] {
                        new WorldTile(boundChunks[0] * 8 + 25, boundChunks[1] * 8 + 30, 0),

        };
        return tiles[Misc.random(tiles.length)];
    }
    
	public void destroyRoom(boolean logout) {
		player.setForceMultiArea(false);
		removeMapChunks();
	}
	
    public void finishGame() {
        destroyRoom(true);
        player.setNextWorldTile(new WorldTile(2845, 10210, 0));
                player.getPackets().sendBlackOut(0);
                player.getPackets().closeInterface(10);
                removeControler();
                player.sendMessage("<col=" + ChatColors.RED + ">Your instance for King black dragon has been closed.");
        destroyRoom(true);
        player.getPackets().sendBlackOut(0);
        player.getPackets().closeInterface(10);
        removeControler();
    }
	public WorldTile getWorldTile(int mapX, int mapY) {
		return new WorldTile(boundChunks[0] * 8 + mapX, boundChunks[1] * 8 + mapY, 0);
	}
	
}