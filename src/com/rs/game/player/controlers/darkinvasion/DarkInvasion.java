package com.rs.game.player.controlers.darkinvasion;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.RegionBuilder;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.others.DIZombie;
import com.rs.game.player.Equipment;
import com.rs.game.player.Inventory;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.ChatColors;
import com.rs.utils.Misc;

/**
 * @author Tyluur <itstyluur@gmail.com>
 * @since 2012-10-26
 */
public class DarkInvasion extends Controler {

	private int[] boundChunks;
	private int[] zombies;
	private int wave, zombiesKilled, random, bandagesUsed, bandagesGiven;
	private long time;
	private String[] messages;

	private ArrayList<DIZombie> monsters;

	public ArrayList<DIZombie> getMonsters() {
		return monsters;
	}

	@Override
	public void start() {
		random = Misc.random(2, 10);
		sendGenerateMapChunks();
		zombies = new int[] { 5318, 5319, 5320, 5321, 5322, 5404, 5393, 5380,
				5378, 5377, 8149, 8150, 8151, 8152, 8153, 5318, 5319, 5320,
				5321, 5322, 5404, 5393, 5380, 5378, 5377, 8149, 8150, 8151,
				8152, 8153, 5318, 5319, 5320, 5321, 5322, 5404, 5393, 5380,
				5378, 5377, 8149, 8150, 8151, 8152, 8153, 5318, 5319, 5320,
				5321, 5322, 5404, 5393, 5380, 5378, 5377, 8149, 8150, 8151,
				8152, 8153 };
		setMessages(new String[] { "Brrrraiiiiiiiinnsssss", "Give me your headdd", "Your body belongs to ussss"});
		zombiesKilled = 0;
		wave = 0;
		monsters = new ArrayList<DIZombie>();
		time = Misc.currentTimeMillis() + 120000;
		player.setForceMultiArea(true);
		bandagesGiven = player.getInventory().getFreeSlots();
		player.getInventory().addItem(4049, player.getInventory().getFreeSlots());
		player.getPrayer().closeAllPrayers();
	}
	
    @Override
    public boolean processMagicTeleport(WorldTile toTile) {
	player.getDialogueManager().startDialogue("SimpleMessage", "You cannot teleport out of the dark invasion!");
	return false;
    }

    @Override
	public boolean processItemTeleport(WorldTile toTile) {
	player.getDialogueManager().startDialogue("SimpleMessage", "You cannot teleport out of the dark invasion!");
	return false;
    }

    @Override
	public boolean processObjectTeleport(WorldTile toTile) {
            player.getDialogueManager().startDialogue("SimpleMessage", "You cannot teleport out of the dark invasion!");
	return false;

	}

	@Override
	public void process() {
		sendInterface();
		if (monsters.size() < 1) {
			startMonsters();
		}
		if (secondsLeft() < 1 && monsters.size() != 0) {
			finishGame();
		}
		if (monsters != null) {
			for (DIZombie zombie : monsters) {
				zombie.getCombat().setTarget(player);
			}
		}
	}

	public void startMonsters() {
		random = Misc.random(3, 10);
		wave++;
		bandagesUsed = 0;
		time = Misc.currentTimeMillis() + 120000;
		zombiesKilled = 0;
		for (DIZombie zombies : monsters) {
			World.removeNPC(zombies);
		}
		if (getZombieCount() > zombies.length) {
			finishGame();
		}
		for (int i = 0; i < getZombieCount(); i++) {
			monsters.add(new DIZombie(zombies[i], getRandomTile(), -1, true));
		}
	}
	@Override
	public boolean processObjectClick1(WorldObject object) {
		if (object.getId() == 13179) {
			long time = (player.getAttributes().get("last_di_timer") == null ? -1 : (Long) player.getAttributes().get("last_di_timer"));
			if (player.getAttributes().get("last_di_timer") == null || time - System.currentTimeMillis() < 1) {
				time = time - (TimeUnit.SECONDS.toMillis(10));
				player.getAttributes().put("last_di_timer", Misc.currentTimeMillis() + TimeUnit.SECONDS.toMillis(10));
				player.getInventory().addItem(4049, 2);
			} else {
				player.sendMessage("You can only get bandages every 10 seconds. You have " + TimeUnit.MILLISECONDS.toSeconds(time - System.currentTimeMillis()) + " seconds left.");
			}
			return false;
		}
		return true;
	}

	public boolean processButtonClick(int interfaceId, int componentId, int slotId, int packetId) {
		if (interfaceId == 271 && packetId == 14 || (interfaceId == 749 && componentId == 4)) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You are not allowed to use prayers here.");
			return false;
		} else if (interfaceId == Inventory.INVENTORY_INTERFACE) {
			Item item = player.getInventory().getItem(slotId);
			if (item != null) {
				if (item.getId() == 4049) {
					int gloves = player.getEquipment().getGlovesId();
					player.heal((int) (player.getMaxHitpoints() * (gloves >= 11079
							&& gloves <= 11084 ? 0.15 : 0.10)));
					int restoredEnergy = (int) (player.getRunEnergy() * 1.3);
					player.setRunEnergy(restoredEnergy > 100 ? 100 : restoredEnergy);
					player.getInventory().deleteItem(item);
					time = time - (TimeUnit.SECONDS.toMillis(3));
					player.sendMessage("<col=" + ChatColors.RED + ">The bandage heals you but you lose 3 seconds on the timer.");
					bandagesUsed++;
					return false;
				} else {
					if (item.getDefinitions().getEquipSlot() != Equipment.SLOT_ARROWS && item.getDefinitions().getEquipSlot() != Equipment.SLOT_WEAPON 
							&& item.getId() != 15342) {
						player.sendMessage("You cannot do that here.");
						return false;
					}
				}
			}
		}
		return true;
	}

	public void addTime(long timeAdded) {
		this.time += timeAdded;
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
				}
				loop++;
			}
		}, 0, 1);
		return false;
	}

	public void sendGenerateMapChunks() {
		CoresManager.slowExecutor.execute(new Runnable() {
			@Override
			public void run() {
				boundChunks = RegionBuilder.findEmptyChunkBound(2, 2); 
				RegionBuilder.copyAllPlanesMap(295, 631, boundChunks[0], boundChunks[1], 4);
				RegionBuilder.copyAllPlanesMap(292, 628, boundChunks[0], boundChunks[1], 4);
				player.setNextWorldTile(new WorldTile(boundChunks[0] * 8 + 5, boundChunks[1] * 8 + 5, 0));
				player.setNextAnimation(new Animation(-1));
			}
		});
	}

	public WorldTile getRandomTile() {
		WorldTile[] tiles = new WorldTile[] { 
				new WorldTile(boundChunks[0] * 8 + 3, boundChunks[1] * 8 + 20, 0),
				new WorldTile(boundChunks[0] * 8 + 20, boundChunks[1] * 8 + 20, 0),
				new WorldTile(boundChunks[0] * 8 + 20, boundChunks[1] * 8 + 8, 0),
				new WorldTile(boundChunks[0] * 8 + 15, boundChunks[1] * 8 + 19, 0)
		};
		return tiles[Misc.random(tiles.length)];
	}

	public boolean waveFinished() {
		return monsters.size() == 0;
	}

	private long secondsLeft() {
		return TimeUnit.MILLISECONDS.toSeconds(time - Misc.currentTimeMillis());
	}

	public void sendInterface() {
		player.getInterfaceManager().sendTab(player.getInterfaceManager().hasRezizableScreen() ? 8 : 10, 487);
		player.getPackets().sendIComponentText(487, 1, "Wave: " + wave);
		player.getPackets().sendIComponentText(487, 3, "Zombies Killed: " + (zombiesKilled) + "/" + getZombieCount());
		player.getPackets().sendIComponentText(487, 5, "Seconds Left: " + secondsLeft());
		player.getPackets().sendIComponentText(487, 7, "");
		player.getPackets().sendHideIComponent(487, 6, true);
	}

	@Override
	public void forceClose() {
		finishGame();
	}

	public int getZombieCount() {
		return wave * random / 2;
	}

	@Override
	public void magicTeleported(int type) {
		finishGame();
	}
	
	public boolean logout() {
		player.setLocation(new WorldTile(1890, 3178, 0));
		return false;
	}

	public void destroyRoom() {
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				RegionBuilder.destroyMap(boundChunks[0], boundChunks[1], 8, 8);
			}
		}, 1200, TimeUnit.MILLISECONDS);
	}

	public void sendReward() {
		int points = random + (wave * 4);
		player.getInterfaceManager().sendInterface(497);
		player.getPackets().sendIComponentText(497, 3, "You have finished Dark Invasion!");
		player.getPackets().sendIComponentText(497, 66, "");
		player.getPackets().sendIComponentText(497, 68, "");
		player.getPackets().sendIComponentText(497, 69, "You have received " + points + " invasion points.");
		player.getPackets().sendIComponentText(497, 78, "");
		player.getPackets().sendIComponentText(497, 70, "You finished on wave:");
		player.getPackets().sendIComponentText(497, 80, "" + wave);
		player.getPackets().sendIComponentText(497, 77, "REWARDS");
		player.getPlayerData().setInvasionPoints(player.getPlayerData().getInvasionPoints() + points);
		player.sendMessage("<col=" + ChatColors.PURPLE + ">You have completed the Dark Invasion minigame and received " + points + " invasion points.");
	}

	public void finishGame() {
		destroyRoom();
		player.setNextWorldTile(new WorldTile(1890, 3178, 0));
		if (bandagesGiven - bandagesUsed < 10 && wave > 1) {
			monsters = null;
			wave = 0;
			time = 0;
			bandagesGiven = 0;
			bandagesUsed = 0;
			player.getPackets().sendBlackOut(0);
			player.getPackets().closeInterface(10);
			player.getInventory().deleteItem(4049, 28);
			player.reset();
			removeControler();
			player.getDialogueManager().startDialogue("SimpleMessage", "<col=" + ChatColors.RED + ">You used more than ten bandages on one your final wave. You did not receive a reward.");
			return;
		}
		if (wave > 1)
			sendReward();
		else
			player.sendMessage("<col=" + ChatColors.RED + ">YOU LOSE! YOU DID NOT EVEN COMPLETE THE FIRST WAVE. HOW DISAPPOINTING.");
		if (monsters != null) {
			for (DIZombie zombies : monsters) {
				World.removeNPC(zombies);
			}
		}
		monsters = null;
		wave = 0;
		time = 0;
		bandagesGiven = 0;
		bandagesUsed = 0;
		player.getPackets().sendBlackOut(0);
		player.getPackets().closeInterface(10);
		player.getInventory().deleteItem(4049, 28);
		removeControler();
	}

	public int getZombiesKilled() {
		return zombiesKilled;
	}

	public void setZombiesKilled(int zombiesKilled) {
		this.zombiesKilled = zombiesKilled;
	}

	public void addKills() {
		zombiesKilled++;
	}

	public String[] getMessages() {
		return messages;
	}

	public void setMessages(String[] messages) {
		this.messages = messages;
	}

}
