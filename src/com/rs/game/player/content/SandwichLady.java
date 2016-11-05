package com.rs.game.player.content;

import java.util.Random;

import com.rs.game.WorldTile;
import com.rs.game.player.Player;

public class SandwichLady {
	/**
	 * SandwichLady class instantiation
	 */
	private static SandwichLady instance = new SandwichLady();
	
	/**
	 * The random sandwich variable
	 */
	private int sandwichId;
	
	/**
	 * The name of the sandwich
	 */
	private String sandwich = "There was a problem! I don't know what sandwich to give you!"; //The set dialogue should never appear!
	
	/**
	 * Checks if the random event is active
	 */
	private boolean inEvent = false;
	
	/**
	 * Starts the random event
	 */
	public void start(Player player) {
		generate();
		setInEvent(true);
		player.getInterfaceManager().sendInterface(297);
		player.getPackets().sendIComponentText(297, 48, "Please select " + getSandwich());
	
		if (inEvent()) {
			player.addFreezeDelay(8000, false);
		}
	}
	
        /*
         * Finishes/Ends the random event
         */
	private void finish(final Player player) {
		setInEvent(false);
		player.getInterfaceManager().closeScreenInterface();
	}
	
	/**
	 * Generates a random sandwich
	 */
	private void generate() {
		Random random = new Random();
		int number = random.nextInt(7);
		sandwichId = number;
	}
	
	private String getSandwich() {
		switch (sandwichId) {
		case 1:
			return sandwich = "baguette";
		case 2:
			return sandwich = "triangle sandwich";
		case 3:
			return sandwich = "square sandwich";
		case 4:
			return sandwich = "bread roll";
		case 5:
			return sandwich = "meat pie";
		case 6: 
			return sandwich = "doughnut";
		case 7: 
			return sandwich = "chocolate bar";
		}
		return sandwich;
	}
	
	/**
	 * Handles all the button actions for the sandwich lady interface
	 * 
	 * @param player
	 *            The player the random event is running for
	 * 
	 * @param interfaceId
	 *            The interface id for the sandwich lady random event
	 * 
	 * @param buttonId
	 *            The button id's on the sandwich lady random event interface
	 */
	public void handleButtons(Player player, int interfaceId, int buttonId) {
		if (interfaceId == 297) {
			switch (buttonId) {
			case 10: //baguette 
				if (sandwichId == 1) {
					player.getPackets().sendGameMessage("You choose the correct item!");
					player.getInventory().addItem(29980, 100);
				} else {
					player.setNextWorldTile(new WorldTile(2967, 3401, 0));
					player.getPackets().sendGameMessage("You choose the wrong item! \nYou have been teleported home.");
				}
				finish(player);
				break;
			case 12: //triangle sandwich
				if (sandwichId == 2) {
					player.getPackets().sendGameMessage("You choose the correct item!");
					player.getInventory().addItem(29980, 100);
				} else {
					player.setNextWorldTile(new WorldTile(2967, 3401, 0));
					player.getPackets().sendGameMessage("You choose the wrong item! \nYou have been teleported home.");
				}
				finish(player);
				break;
			case 14: //square sandwich
				if (sandwichId == 3) {
					player.getPackets().sendGameMessage("You choose the correct item!");
					player.getInventory().addItem(29980, 100);
				} else {
					player.setNextWorldTile(new WorldTile(2967, 3401, 0));
					player.getPackets().sendGameMessage("You choose the wrong item! \nYou have been teleported home.");
				}
				finish(player);
				break;
			case 16: //bread roll
				if (sandwichId == 4) {
					player.getPackets().sendGameMessage("You choose the correct item!");
					player.getInventory().addItem(29980, 100);
				} else {
					player.setNextWorldTile(new WorldTile(2967, 3401, 0));
					player.getPackets().sendGameMessage("You choose the wrong item! \nYou have been teleported home.");
				}
				finish(player);
				player.getPackets().sendGameMessage("You selected: " + 4);
				break;
			case 18: //meat pie
				if (sandwichId == 5) {
					player.getPackets().sendGameMessage("You choose the correct item!");
					player.getInventory().addItem(29980, 100);
				} else {
					player.setNextWorldTile(new WorldTile(2967, 3401, 0));
					player.getPackets().sendGameMessage("You choose the wrong item! \nYou have been teleported home.");
				}
				finish(player);
				break;
			case 20: //doughnut
				if (sandwichId == 6) {
					player.getPackets().sendGameMessage("You choose the correct item!");
					player.getInventory().addItem(29980, 100);
				} else {
					player.setNextWorldTile(new WorldTile(2967, 3401, 0));
					player.getPackets().sendGameMessage("You choose the wrong item! \nYou have been teleported home.");
				}
				finish(player);
				break;
			case 22: //chocolate bar
				if (sandwichId == 7) {
					player.getPackets().sendGameMessage("You choose the correct item!");
					player.getInventory().addItem(29980, 100);
				} else {
					player.setNextWorldTile(new WorldTile(2967, 3401, 0));
					player.getPackets().sendGameMessage("You choose the wrong item! \nYou have been teleported home.");
				}
				finish(player);
				break;
			case 47: //exit button
				player.getPackets().sendGameMessage("You must choose a sandwich!");
				break;
			}
		}
	}
	
	public static SandwichLady getInstance() {
		if (instance == null)
			instance = new SandwichLady();
		return instance;
	}
	
	public boolean inEvent() {
		return inEvent;
	}
	
	public boolean setInEvent(boolean inEvent) {
		return this.inEvent = inEvent;
	}

}