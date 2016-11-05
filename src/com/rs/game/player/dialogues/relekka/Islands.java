package com.rs.game.player.dialogues.relekka;

import com.rs.game.WorldTile;
import com.rs.game.player.dialogues.Dialogue;

public class Islands extends Dialogue {


    int npcId;
    
    @Override
    public void start() {
        npcId = (Integer) parameters[0];
        sendNPCDialogue(npcId, 9827, "Hello, which island would you like to travel to?");
    }


    @Override
    public void run(int interfaceId, int componentId) {
        switch (stage) {
        case -1:
        	stage = 0;
        	sendOptionsDialogue("Pick an Island.", "Waterbirth", "Miscellania", "Lunar", "Neitznot", "Jatizso");
        	break;
        case 0:
        	if(componentId == OPTION_1) {
        		player.setNextWorldTile(new WorldTile(2547, 3761, 0));
        		sendNPCDialogue(npcId, 9827, "Good luck on your travels adventurer.");
        		stage = 4;
                break;
        	} else if(componentId == OPTION_2) {
    			player.setNextWorldTile(new WorldTile(2581, 3845, 0));
    			sendNPCDialogue(npcId, 9827, "Good luck on your travels adventurer.");
    			stage = 4;
            break;
        	} else if(componentId == OPTION_3) {
    			player.setNextWorldTile(new WorldTile(2118, 3895, 0));
    			sendNPCDialogue(npcId, 9827, "Good luck on your travels adventurer.");
    			stage = 4;
            break;
        	} else if(componentId == OPTION_4) {
    			player.setNextWorldTile(new WorldTile(2311, 3780, 0));
    			sendNPCDialogue(npcId, 9827, "Good luck on your travels adventurer.");
    			stage = 4;
            break;
        	} else if(componentId == OPTION_5) {
    			player.setNextWorldTile(new WorldTile(2422, 3780, 0));
    			sendNPCDialogue(npcId, 9827, "Good luck on your travels adventurer.");
    			stage = 4;
            break;
            }
        case 4:
            default:
                end();
            break;
        }
    }
    
    public void finish() {
        
    }
}