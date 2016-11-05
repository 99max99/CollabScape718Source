package com.rs.game.player.dialogues;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class AssassinMaster extends Dialogue {

    private int npcId;

    @Override
    public void start() {
	npcId = (Integer) parameters[0];
	sendNPCDialogue(npcId, 9827, "Greetings fellow Assassin, what are you after?");
	    stage = -1;
    }

    @Override
    public void run(int interfaceId, int componentId) {
	if (stage == -1) {
	    if (player.getAssassinsManager().getTask() != null) {
	    	sendOptionsDialogue("Chose an option", "What is my current task?", "What do you have in your shop?", "May I have an Assassin's Orb?", "Nothing, Nevermind.");
			stage = 0;
	    } else {
	    	sendOptionsDialogue("Chose an option", "Please give me a task.", "What do you have in your shop?", "May I have an Assassin's Orb?", "Nothing, Nevermind.");
			stage = 1;
	    }
	} else if (stage == 0) {
	    if (componentId == OPTION_1) {
	    	sendNPCDialogue(npcId, 9827, "Sure, here is your task.");
	    	if (player.getAssassinsManager().getGameMode() == 1)
	    		stage = 7;
	    	else if (player.getAssassinsManager().getGameMode() == 2)
    			stage = 8;
	    	else if (player.getAssassinsManager().getGameMode() == 3)
	    		stage = 9;
	    	else if (player.getAssassinsManager().getGameMode() == 4)
	    		stage = 10;
	    } else if (componentId == OPTION_2) {
	    	ShopsHandler.openShop(player, 29);
	    	end();
	    } else if (componentId == OPTION_3) {
	    	player.getInventory().addItem(15073, 1);
	    	sendNPCDialogue(npcId, 9827, "Here you go, try not to lose it!");
	    	stage = 4;
	    } else
	    	end();
	} else if (stage == 1) {
	    if (componentId == OPTION_1) {
	    	if (player.getAssassinsManager().getTask() == null) {
	    		sendOptionsDialogue("Chose a Skill", "Assassin Call", "Final Blow", "Swift Speed", "Stealth Moves", "None");
	    		stage = 6;
	    	} else {
	    		sendNPCDialogue(npcId, 9827, "You already have a task!");
	    	}
	    } else if (componentId == OPTION_2) {
	    	ShopsHandler.openShop(player, 137);
	    	end();
	    } else if (componentId == OPTION_3) {
	    	player.getInventory().addItem(15073, 1);
	    	sendNPCDialogue(npcId, 9827, "Here you go, try not to lose it!");
	    	stage = 4;
	    } else
	    	end();
	} else if (stage == 2) {
	    	sendOptionsDialogue("Chose an Option", "Sure", "No Thanks");
	    	stage = 3;
	} else if (stage == 3) {
	    if (componentId == OPTION_1) {
	    	if (player.getInventory().containsItem(995, 5000000)) {
	    		player.getInventory().removeItemMoneyPouch(995, 5000000);
	    		player.getAssassinsManager().resetTask();
	    		sendNPCDialogue(npcId, 9827, "There you go, your task has been reset.");
	    	} else {
	    		sendNPCDialogue(npcId, 9827, "You don't have that kind of money!");
	    	}
	    	stage = 4;
	    } else if (componentId == OPTION_2) {
	    	sendNPCDialogue(npcId, 9827, "Come back when you have finished your task!");
	    	stage = 4;
	    }
	} else if (stage == 4) {
	    	end();
	} else if (stage == 5) {
	    	ShopsHandler.openShop(player, 137);
	    	end();
	} else if (stage == 6) {
	    if (componentId == OPTION_1) {
	    	player.getAssassinsManager().getTask(1);
	    	sendNPCDialogue(npcId, 9827, "You must kill a bulk of "+player.getAssassinsManager().getAmount()+" "+player.getAssassinsManager().getName()+"'s.");
	    	stage = 4;
	    } else if (componentId == OPTION_2) {
	    	player.getAssassinsManager().getTask(2);
	    	sendNPCDialogue(npcId, 9827, "You must kill "+player.getAssassinsManager().getAmount()+" "+player.getAssassinsManager().getName()+" using the weapon "+player.getAssassinsManager().getWeaponName()+".");
	    	stage = 4;
	    } else if (componentId == OPTION_3) {
	    	player.getAssassinsManager().getTask(3);
	    	sendNPCDialogue(npcId, 9827, "You must kill "+player.getAssassinsManager().getAmount()+" "+player.getAssassinsManager().getName()+" within "+player.getAssassinsManager().getSpeed()+" seconds.");
	    	stage = 4;
	    } else if (componentId == OPTION_4) {
	    	player.getAssassinsManager().getTask(4);
	    	sendNPCDialogue(npcId, 9827, "You must kill "+player.getAssassinsManager().getAmount()+" "+player.getAssassinsManager().getName()+" without dieing.");
	    	stage = 4;
	    } else
	    	end();
	} else if (stage == 7) {
		sendNPCDialogue(npcId, 9827, "You must kill a bulk of "+player.getAssassinsManager().getAmount()+" "+player.getAssassinsManager().getName()+".");
    	stage = 4;
	} else if (stage == 8) {
		sendNPCDialogue(npcId, 9827, "You must kill "+player.getAssassinsManager().getAmount()+" "+player.getAssassinsManager().getName()+" using the weapon "+player.getAssassinsManager().getWeaponName()+".");
    	stage = 4;
	} else if (stage == 9) {
		sendNPCDialogue(npcId, 9827, "You must kill "+player.getAssassinsManager().getAmount()+" "+player.getAssassinsManager().getName()+" within "+player.getAssassinsManager().getSpeed()+" seconds.");
    	stage = 4;
	} else if (stage == 10) {
		sendNPCDialogue(npcId, 9827, "You must kill "+player.getAssassinsManager().getAmount()+" "+player.getAssassinsManager().getName()+" without dieing.");
    	stage = 4;
    }
    }

    @Override
    public void finish() {

    }

}
