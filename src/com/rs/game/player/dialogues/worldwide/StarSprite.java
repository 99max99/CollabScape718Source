package com.rs.game.player.dialogues.worldwide;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.player.content.magic.Magic;
import com.rs.game.player.dialogues.Dialogue;

public class StarSprite extends Dialogue {

        private int npcId = 8091;
        

        @Override
        public void start() {
    		if (player.recievedGift == true) {
    			//sendNPCDialogue(npcId, 9827, "eek la moon le fita.");
    			 sendEntityDialogue(SEND_2_TEXT_CHAT,
                         new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
                                         "eek la moon le fita, I have to go nowwww"}, IS_NPC, npcId, 9827);
    			end();
    		} else if (player.starSprite == false) {

    		} else {
   			 sendEntityDialogue(SEND_2_TEXT_CHAT,
                     new String[] { NPCDefinitions.getNPCDefinitions(npcId).name,
                                     "Greetings Earthling."}, IS_NPC, npcId, 9827);
    		}
        }
        
        @Override
        public void run(int interfaceId, int componentId) {
                if (stage == -1) {
                	sendPlayerDialogue(9827, "Ahhhh, What!? who are you!?!?");
                        stage = 1;
                } else if (stage == 1) {
                        	sendNPCDialogue(npcId, 9827, "Me called Star Sprite, I come to give you reward for freeeeing me.");
                                stage = 2;
                } else if (stage == 2) {
                	sendPlayerDialogue(9827, "Whoaaa! What do I get?");
                	stage = 3;
                } else if (stage == 3) {
                	sendNPCDialogue(npcId, 9827, "You can have whatever is in my pocket... here you go.");
                	player.getInventory().addItem(6542, 1);
                	player.recievedGift = true;
                	stage = 4;
                } else if (stage == 4) { 
                	sendPlayerDialogue(9827, "Whoaa Thanks alot!");
                	stage = 5;
                } else if (stage == 5) { 	
                	end();
                }
    		}


        @Override
        public void finish() {

        }
}