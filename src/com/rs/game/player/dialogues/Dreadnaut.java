package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;

public class Dreadnaut extends Dialogue {

    private int npcId;

    @Override
    public void start() {
	npcId = (Integer) parameters[0];
	if(player.Nstage3 == 1){
		sendNPCDialogue(npcId, 9827, "You should talk with Cassie for your reward!");
		stage = 2;
	} else {
		sendNPCDialogue(npcId, 9827, "Would you like to try fighting the Dreadnaut and his minions?");
		stage = 0;
	}
    }

    @Override
    public void run(int interfaceId, int componentId) {

   	 if(stage == 0) {
   		sendOptionsDialogue("Enter?", "Yes", "No");
   		stage = 1;
 	 }
   	 else if(stage == 1){
   		if(componentId == OPTION_1) {
   			player.setNextWorldTile(new WorldTile(2463, 4782, 0));
  			player.Nstage1 = 0;
  			player.Nstage2 = 0;
  			player.Nstage3 = 0;
  			end();
  		} else if(componentId == OPTION_2) {
  			player.Nstage1 = 0;
  			player.Nstage2 = 0;
  			player.Nstage3 = 0;
  			stage = 2;
  		}else if (stage == 2) {
  			end();
  		}
   	 }
	}

    @Override
    public void finish() {

    }

}
