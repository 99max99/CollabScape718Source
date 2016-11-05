package com.rs.game.player.dialogues.impl;


import java.text.NumberFormat;
import java.util.Locale;

import com.rs.Settings;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.controlers.InstancedBossControler;
import com.rs.game.player.controlers.InstancedBossControler.Difficulty;
import com.rs.game.player.controlers.InstancedBossControler.Instance;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Utils;


public class InstancedDungeonDialogue extends Dialogue {

	private Instance instance;
	private Difficulty difficulty;
	public static final int cost = 250000;

	@Override
	public void start() {
		instance = (Instance) parameters[0];
		player.instance = instance;
		sendOptionsDialogue("What would you like to do?", new String[] { "Enter boss room.", "Start a new session.", "Join an existing session." });
		stage = START;
	}
	@Override
	public boolean run(String name) {
		joinBossSession(name);
		return true;
	}
	@Override
	public boolean run(int amount) {
		if (amount < 1 || amount > 100) {
			return true;
		}
		startBossSession(amount);
		end();
		return true;
	}
	
	public static final int START = 1, CHOOSE_TYPE = 2, PAY = 3;
	public static int MAXIMUM_PLAYERS = 4;
	public static final int CONFIRM_PAY = 5;
	
	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
			case START:
				if (componentId == OPTION_1) {
					if (instance == Instance.KBD) {
						//Magic.pushLeverTeleport(player,WorldTile(2273, 4681, 0));
						player.setNextWorldTile(new WorldTile(2273, 4681, 2));
					} else if (instance == Instance.CORP) {
						player.setNextWorldTile(new WorldTile(2974, 4384, 2));
						player.getControlerManager().startControler("CorpBeastControler");
					} else if (instance == Instance.ARMADYL) {
						player.setNextWorldTile(instance.getCoords());
						player.sm("You Enter Armadyls Lair... Prepare to fight!");
						player.armadyl -= 40;
					} else if (instance == Instance.BANDOS) {
						player.setNextWorldTile(instance.getCoords());
						player.sm("You Enter Bandos Lair... Prepare to fight!");
						player.bandos -= 40;
					} else if (instance == Instance.SARADOMIN) {
						player.setNextWorldTile(instance.getCoords());
						player.sm("You Enter Saradomins Lair... Prepare to fight!");
						player.saradomin -= 40;
					} else if (instance == Instance.ZAMORAK) {
						player.setNextWorldTile(instance.getCoords());
						player.sm("You Enter Zamoraks Lair... Prepare to fight!");
						player.zamorak -= 40;
					} else
						player.setNextWorldTile(instance.getCoords());
					end();
				} else if (componentId == OPTION_2) {
					sendOptionsDialogue("Choose your session type:", 
							new String[] { "Regular Session", "Quick Session", "Hard Session", "Quick and Hard Session" });
					stage = CHOOSE_TYPE;
				} else if (componentId == OPTION_3) {
					player.getPackets().sendInputNameScript("Enter the name of a player in battle you wish to join.");
					player.getTemporaryAttributtes().put("JOINUSER", Boolean.TRUE);
				}
				break;
			case CHOOSE_TYPE:
				if (componentId == OPTION_1) {
					difficulty = Difficulty.Normal;
					player.difficulty = difficulty;
				} else if (componentId == OPTION_2) {
					difficulty = Difficulty.Quick;
					player.difficulty = difficulty;
				} else if (componentId == OPTION_3) {
					difficulty = Difficulty.Hard;
					player.difficulty = difficulty;
				} else if (componentId == OPTION_4) {
					difficulty = Difficulty.QuickHard;
					player.difficulty = difficulty;
				}
				if (player.isLegendaryDonator()) {// These donators don't pay
					player.getPackets().sendInputIntegerScript("Choose the maximum number of players in this battle. (1-100)");
					stage = MAXIMUM_PLAYERS;
				} else {
					sendDialogue("Starting a new session against this foe cost gp."+ Utils.formatNumber(cost) +" Do you wish to pay?");
					stage = PAY;
				}
				break;
			case PAY:
				sendOptionsDialogue("Pay "+ Utils.formatNumber(cost) +" gp to start a new battle?", "Pay.", "Don't pay.");
				stage = CONFIRM_PAY;
				break;
			case CONFIRM_PAY:
				if (componentId == OPTION_1) {
					//player.sm("Legendary donators can create instanced boss session for free.");
					if (player.getInventory().getCoinsAmount() < cost) {
						player.sm("You don't have enough coins to start a new session.");
						sendDialogue("You don't have enough coins to start a new session.");
						end();
					} else {
						player.getPackets().sendInputIntegerScript("Choose the maximum number of players in this battle. (1-100)");
						player.getTemporaryAttributtes().put("PLAYER_AMOUNT", Boolean.TRUE);
						end();
					}
						
				} else if (componentId == OPTION_2) {
					end();
				}
				break;
		}
	}
	
	public void joinBossSession(String username) {
		Player target = World.getPlayerByDisplayName(username);
		end();
		if (target == null) {
			player.getDialogueManager().startDialogue("SimpleMessage", "Unable to find player.");
			return;
		}
		if (target.getControlerManager().getControler() == null) {

			return;
		}
		if (!(target.getControlerManager().getControler() instanceof InstancedBossControler)) {
			return;
		}
		InstancedBossControler.joinSession(player, target);
	}
	
	public void startBossSession(int maxPlayers) {
		if (instance == Instance.ARMADYL) {
			if (player.armadyl < 40) {
				player.sm("You must have a kill count of at least 40 to enter the Armadyl Lair.");
				return;
			} else {
				player.sm("You enter the Armadyl Lair... Prepare to fight!");
				player.armadyl -= 40;
			}
		} else if (instance == Instance.BANDOS) {
			if (player.bandos < 40) {
				player.sm("You must have a kill count of at least 40 to enter the Bandos Lair.");
				return;
			} else {
				player.sm("You enter the Bandos Lair... Prepare to fight!");
				player.bandos -= 40;
			}
		} else if (instance == Instance.SARADOMIN) {
			if (player.saradomin < 40) {
				player.sm("You must have a kill count of at least 40 to enter the Saradomin Lair.");
				return;
			} else {
				player.sm("You enter the Saradomin Lair... Prepare to fight!");
				player.saradomin -= 40;
			}
		} else if (instance == Instance.ZAMORAK) {
			if (player.zamorak < 40) {
				player.sm("You must have a kill count of at least 40 to enter the Zamorak Lair.");
				return;
			} else {
				player.sm("You enter the Zamorak Lair... Prepare to fight!");
				player.zamorak -= 40;
			}
		}
		InstancedBossControler.startSession(player, instance, difficulty, player.playerAmount);
		if (!player.isLegendaryDonator())
			player.getInventory().removeItemMoneyPouch(995, InstancedDungeonDialogue.cost);
	}

	public void finish() {
		// TODO Auto-generated method stub

	}

}
