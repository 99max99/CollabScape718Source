/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rs.game.player.dialogues;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.actions.slayer.Slayer;
import com.rs.game.player.actions.slayer.SlayerMaster;

public class Kuradel extends Dialogue {

    /**
     * Starts the dialogue
     */
    public Kuradel() {
    }

    @Override
    public void start() {
        npcId = 9085;
        sendEntityDialogue((short) 241, new String[]{NPCDefinitions.getNPCDefinitions(npcId).name, "Hello, brave warrior. What would you like?"}, (byte) 1, npcId, 9827);
    }

    /**
     * Runs the dialogue
     */
    @Override
    public void run(int interfaceId, int componentId) {
		switch(stage) {
		case -1:
            stage = 0;
            sendOptionsDialogue("What would you like to say?", "I would like a slayer task.", "What is my current slayer task?", "Can I cancel my curren task?");
            break;
		case 0:
            if (componentId == OPTION_1) {
                if (player.getSkills().getCombatLevel() >= 110 && player.getSkills().getLevel(18) >= 75) {
                    if (player.hasTask == false) {
                        Slayer.assignTask(player, SlayerMaster.KURADEL);
                        sendEntityDialogue((short) 241, new String[]{NPCDefinitions.getNPCDefinitions(9085).name, "Your slayer task is to kill " + player.slayerTask.getTaskMonstersLeft() + " " + player.slayerTask.getTask().simpleName}, (byte) 1, 9085, 9827);
                    } else {
                        sendEntityDialogue((short) 243, new String[]{NPCDefinitions.getNPCDefinitions(9085).name, "You already have a slayer task! ", "You need to kill " + player.slayerTask.getTaskMonstersLeft() + " " + player.slayerTask.getTask().simpleName}, (byte) 1, 9085, 9827);
                    }
                } else if (player.getSkills().getCombatLevel() < 110 && player.getSkills().getLevel(18) < 75){
                    sendEntityDialogue((short) 241, new String[]{NPCDefinitions.getNPCDefinitions(npcId).name, "Sorry, you need atleast level 110 combat and 75 Slayer to use me."}, (byte) 1, npcId, 9827);
                } else if (player.getSkills().getCombatLevel() >= 110 && player.getSkills().getLevel(18) < 75) {
                    sendEntityDialogue((short) 241, new String[]{NPCDefinitions.getNPCDefinitions(npcId).name, "Sorry, you need atleast 75 Slayer to use me."}, (byte) 1, npcId, 9827);
                } else if (player.getSkills().getCombatLevel() < 110 && player.getSkills().getLevel(18) >= 75) {
                    sendEntityDialogue((short) 241, new String[]{NPCDefinitions.getNPCDefinitions(npcId).name, "Sorry, you need atleast 110 combat to use me."}, (byte) 1, npcId, 9827);
                }
            } else if (componentId == OPTION_2) {
                if (player.hasTask == true) {
                    sendEntityDialogue((short) 242, new String[]{NPCDefinitions.getNPCDefinitions(9085).name, "You have a short memory, don't you?", "You need to kill " + player.slayerTask.getTaskMonstersLeft() + " " + player.slayerTask.getTask().simpleName}, (byte) 1, 9085, 9827);
                } else {
                    sendEntityDialogue((short) 241, new String[]{NPCDefinitions.getNPCDefinitions(9085).name, "Foolish warrior. You don't have a slayer task!"}, (byte) 1, 9085, 9827);
                }
                stage = -1;
            } else if (componentId == OPTION_3) {
                Slayer.displayPoints(player, 1);
            }
                break;
        }
    }

    @Override
    public void finish() {
    }
    /**
     * Declares the npc ID
     */
    private int npcId;
}
