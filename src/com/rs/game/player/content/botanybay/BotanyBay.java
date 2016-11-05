package com.rs.game.player.content.botanybay;

import java.util.ArrayList;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.controlers.Controler;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

/**
 * 
 * @author Taylor Moon
 * 
 */
public class BotanyBay extends Controler {

	/**
	 * Starts the controller
	 */
	@Override
	public void start() {
		player.saveLocation(false);
		if (!running || playersAtIsland == null || playersAtIsland.size() < 1) {
			run();
		}
		if (!playersAtIsland.contains(player)) {
			playersAtIsland.add(player);
		}
		// randomizes squars 2x2 and randomizes the side the player will appear
		// on
		if (player.getRegionId() != 14648) {
			Magic.sendNormalTeleportSpell(player, 0, 0,
					Utils.random(2) == 1 ? new WorldTile(new WorldTile(3689,
							3616, 0), 2) : new WorldTile(new WorldTile(3672,
							3616, 0), 2));
		}

	}

	/** Represents an empty array of bots to be trialed */
	private ArrayList<Player> bots;

	/** Represents an empty NPC instance */
	private NPC accused;

	/** Represents an empty array of players in the class */
	private ArrayList<Player> playersAtIsland;

	/** Represents the current trial time */
	private volatile int trialTime = -1;

	/** Determins whether the class is running or isn't */
	protected boolean running;

	/**
	 * Runs the class; Trials bots and keeps the gears turning.
	 */
	public void run() {
		if (running == false)
			running = true;
		if (bots == null)
			bots = new ArrayList<Player>();
		if (playersAtIsland == null)
			playersAtIsland = new ArrayList<Player>();
	}

	/**
	 * Trials a bot at botany bay
	 * 
	 * @param bot
	 *            - bot to be trialed
	 */
	public void trialBot(final Player bot, int daysBanned) {
		if (trialTime > 0) {// trial already in progress
			sendCrush(bot, daysBanned);
			return;
		}
		if (bot.hasFinished() || bot == null) {
			restart(false);
			return;
		}
		announceEvent();
		setUpConfigurations(bot);
		sendBan(daysBanned, bot);
		if (bots.contains(bot)) {
			bots.remove(bot);
		}

		WorldTasksManager.schedule(new WorldTask() {

			public void run() {

				switch (trialTime) {
				case 2:
					accused.setNextAnimation(new Animation(17537));// claw
					accused.setNextWorldTile(new WorldTile(3680, 3616, 0));
					spawnPillar();
					break;
				case 55:
					sendPunishment(Utils.random(3) == 0 ? 1
							: Utils.random(3) == 2 ? 2 : 3);// TODO voting
					break;
				case 64:
					accused.setLocation(1, 1, 0);
					stop();
					restart(false);

				}
				trialTime++;
			}
		}, 0, 1);
	}

	/**
	 * Allows the event to be spread accross the game
	 */
	private void announceEvent() {
		for (NPC npcs : World.getNPCs()) {
			if (!npcs.getName().equals("trial announcer"))
				continue;
			npcs.setNextForceTalk(new ForceTalk(
					"There is a new bot being trialed at botany bay!"));
		}
		World.sendWorldMessage(
				"<col=ff0000><img=7>[Trial Announcer]: There is a new bot being trialed at Botany Bay!",
				false);
	}

	/**
	 * Crushes the bot if a trial is already going on
	 * 
	 * @param bot
	 *            - the bot to be crushed
	 * @param daysBanned
	 *            - the days for the bot to be banned; 0 if perm
	 * @return - true if called
	 */
	private boolean sendCrush(final Player bot, final int daysBanned) {
		WorldTasksManager.schedule(new WorldTask() {
			int tick = 0;

			@Override
			public void run() {
				if (tick == 0) {
					bot.setNextGraphics(new Graphics(3402));
					bot.getAppearence().transformIntoNPC(15782);
				} else if (tick == 3) {
					sendBan(daysBanned, bot);
				}
				tick++;
			}
		}, 0, 1);
		return true;
	}

	/**
	 * Sends a ban toward a bot
	 * 
	 * @param daysBanned
	 *            - days to be banned; 0 if perm
	 * @param bot
	 *            - bot to be banned
	 * @return - true if perm, false otherwise
	 */
	private boolean sendBan(int daysBanned, Player bot) {
		if (daysBanned < 1) {
			bot.setPermBanned(true);
			bot.getSession().getChannel().disconnect();
			return true;
		}
		bot.addBan_Days(daysBanned);
		bot.getSession().getChannel().disconnect();
		return false;
	}

	/**
	 * Spawns the pillar for the bot to stand on
	 */
	private void spawnPillar() {
		World.spawnObject(new WorldObject(75478, 10, 0, 3679, 3615, 0), false);
	}

	/**
	 * Sets up the bot's details. The accused feild is initiated here, as well
	 * as the conversation is called. aram bot - the bot being set up
	 */
	private void setUpConfigurations(Player bot) {
		Chatting.conversationOne(bot.getUsername());
		accused = new NPC(15783, new WorldTile(1, 1, 0), 0, false);
		accused.setName(Utils.formatPlayerNameForDisplay(bot.getUsername())
				+ ", the accused");
		accused.setNextFaceWorldTile(new WorldTile(3680, 3619, 0));
		bot.setNextGraphics(new Graphics(3401));
	}

	/**
	 * Allows the bot to recieve his or her punishment.
	 * 
	 * @param type
	 *            - type of punishment
	 */
	private void sendPunishment(final int type) {
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			public void run() {
				switch (type) {
				case 1:// crush
					accused.setNextAnimation(new Animation(Death.CRUSH
							.getEmoteId()));
					World.spawnObject(new WorldObject(26342, 10, 0, 3679, 3615,
							0), false);
					stop();
					break;
				case 2:// swallow
					World.spawnObject(new WorldObject(26445, 10, 0, 3679, 3615,
							0), false);
					accused.setNextAnimation(new Animation(17530));
					stop();
					break;
				case 3:// diety
					accused.setNextAnimation(new Animation(Death.DIETY
							.getEmoteId()));
					accused.setNextGraphics(new Graphics(3398));
					World.spawnObject(new WorldObject(26444, 10, 0, 3679, 3615,
							0), false);
					stop();
				default:
					player.out("An error occured");
					break;
				}
				if (loop == 10) {
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}

	/**
	 * Completely shuts down the process of trialing bots. The original use was
	 * to stop the while loop in {@code run()} but instead, it just changes the
	 * boolean {@code running} to false.
	 */
	public void shutDown() {
		running = false;
	}

	/**
	 * Restarts the process of trialing bots. While it restarts, it will refresh
	 * all the feilds.
	 * 
	 * @param kickAll
	 *            - the true if you want all the players at the island to be
	 *            sent to their saved location when the method is called, false
	 *            otherwise
	 */
	public void restart(boolean kickAll) {
		Vote.refreshVotes();
		if (accused != null) {
			accused.sendDeath(player);
			World.removeNPC(accused);
			accused = null;
		}
		running = false;
		World.removeObject(new WorldObject(75478, 10, 0, 3679, 3615, 0), false);
		World.removeObject(new WorldObject(26444, 10, 0, 3679, 3615, 0), false);
		if (World.getObject(new WorldTile(3679, 3615, 0)) != null) {
			World.removeObject(new WorldObject(26445, 10, 0, 3679, 3615, 0),
					false);
		}
		trialTime = -1;
		if (kickAll) {
			for (Player all : playersAtIsland) {
				int i;
				if (player.isPker)
					i = 1;
				else
					i = 0;
				all.setNextWorldTile(new WorldTile(
						Settings.RESPAWN_PLAYER_LOCATION[i]));
				playersAtIsland.remove(all);
				playersAtIsland = null;
			}
		}
	}

	/**
	 * If the method is called, it will restart the class. The player will
	 * leave, and be sent to the saved location
	 */
	@Override
	public void forceClose() {
		removeControler();
		player.setNextWorldTile(player.getSavedLocation());
		leave();
	}

	/**
	 * Returns the trial time of a case.
	 * 
	 * @return trial time
	 */
	public long getTrialTime() {
		return trialTime;
	}

	/**
	 * A method returning the players at the island
	 * 
	 * @return playersAtIsland
	 */
	public ArrayList<Player> getPlayersAtIsland() {
		return playersAtIsland;
	}

	/**
	 * Removes a player from the array list
	 */
	public void leave() {
		playersAtIsland.remove(player);
	}

	/**
	 * Return normally, leaves the area.
	 */
	@Override
	public void magicTeleported(int type) {
		removeControler();
		leave();
	}

	/**
	 * Return normally. if {@code playersAtIsland} doesn't contain the player,
	 * the player will be added.
	 */
	@Override
	public boolean login() {
		start();
		if (!playersAtIsland.contains(player))
			playersAtIsland.add(player);
		return true;
	}

	/**
	 * Handles clicking the portal
	 * 
	 * @return - process normally
	 */
	@Override
	public boolean processObjectClick1(WorldObject object) {
		int id = object.getId();
		switch (id) {
		case 75491:
			if (player.getSavedLocation() == null) {
				player.out("An error occured, maybe you should try "
						+ "using a different mean of transportation");
				return false;
			}
			player.sendToSavedLocation(0, new Runnable() {
				public void run() {
					removeControler();
					leave();
				}
			});
			return false;
		case 75481:
			player.lock();
			player.getInventory().addItem(2518, 1);
			player.unlock();
			return false;
		}
		return true;
	}

	/**
	 * Processes object click 2. returns process normally
	 */
	@Override
	public boolean processObjectClick2(WorldObject object) {
		int id = object.getId();
		if (id == 75481) {
			player.lock();
			player.getInventory().addItem(2518, 10);
			player.unlock();
			return false;
		}
		return true;

	}

	/**
	 * Sends a death to the protagonist. Incase of poinson; if any other
	 * reasons, it would be a glitch.
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
					player.getPackets().sendGameMessage(
							"Oh dear, you have died.");
				} else if (loop == 3) {
					player.reset();
					leave();
					removeControler();
					player.sendToSavedLocation(0, null);
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
	 * Process the interface click normally. Handles voting, basically.
	 */
	@Override
	public boolean processButtonClick(int interfaceId, int componentId,
			int slotId, int packetId) {
		if (interfaceId == 1348) {
			switch (componentId) {
			case 21:
				Vote.castVote(Vote.CRUSH_TYPE, true);
				player.getPackets().sendWindowsPane(
						player.getInterfaceManager().hasRezizableScreen() ? 746
								: 548, -1);
				break;
			case 25:
				Vote.castVote(Vote.SWALLOW_TYPE, true);
				player.getPackets().sendWindowsPane(
						player.getInterfaceManager().hasRezizableScreen() ? 746
								: 548, -1);
				break;
			case 29:
				Vote.castVote(Vote.DIETY_TYPE, true);
				player.getPackets().sendWindowsPane(
						player.getInterfaceManager().hasRezizableScreen() ? 746
								: 548, -1);
				break;
			}
			return false;
		}
		return true;
	}

	/**
	 * Adds the default NPC's
	 */
	public static void init() {
		NPC npc = World.spawnNPC(15784, new WorldTile(3680, 3623, 0), 0, false);
		npc.setNextFaceWorldTile(new WorldTile(3680, 3621, 0));
	}

	/**
	 * Not really used at all
	 * 
	 * @author Taylor Moon
	 */
	enum Death {

		CRUSH(17523), SWALLOW(17530), DIETY(17532);

		private int emoteId;

		Death(int emoteId) {
			this.emoteId = emoteId;
		}

		public int getEmoteId() {
			return emoteId;
		}

	}

}
