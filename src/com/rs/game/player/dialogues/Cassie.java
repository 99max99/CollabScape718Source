package com.rs.game.player.dialogues;

import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.ShopsHandler;

public class Cassie extends Dialogue {

    private int npcId;

    @Override
    public void start() {
	npcId = (Integer) parameters[0];
	if(player.Nstage3 != 1){
		sendNPCDialogue(npcId, 9827, "I have nothing for you.");
		stage = 1;
	}
	else if(player.Nstage3 == 1){
		sendOptionsDialogue("Reward Options.", "Mystery Box", "Coins");
	    stage = 2;	
	}
    }

    @Override
    public void run(int interfaceId, int componentId) {

   	 if(stage == 1){
   		 end();
   	 }
   	 else if(stage == 2){
  		if(componentId == OPTION_1) {
 			player.getInventory().addItem(6199, 1);
 			player.Nstage1 = 0;
 			player.Nstage2 = 0;
 			player.Nstage3 = 0;
 			stage = 1;
 			end();
 		} else if(componentId == OPTION_2) {
 			player.getInventory().addItem(995, 1000000);
 			player.Nstage1 = 0;
 			player.Nstage2 = 0;
 			player.Nstage3 = 0;
 			stage = 1;
 			end();
 		}
  		stage =1;
   	 }
	}

    @Override
    public void finish() {

    }

}
