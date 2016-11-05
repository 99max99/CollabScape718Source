package com.rs.game.player.dialogues;

public class DeathTaskMaster extends Dialogue {

    private int npcId;

    @Override
    public void start() {
	npcId = (Integer) parameters[0];
	sendNPCDialogue(npcId, 9827, "Greetings Noszscape player,  are you after death?!");
	    stage = 0;
    }

    @Override
    public void run(int interfaceId, int componentId) {

	if (stage == 0) {
	    	sendNPCDialogue(npcId, 9827, "Here take a Death Task.");
	    	player.getDeathsManager().getTask(0);
	    	player.sendMessage("You must kill "+player.getDeathsManager().getAmount()+" "+player.getDeathsManager().getName()+"'s.");
	
	    		stage = 1;
	} else if (stage == 1) {
    	player.getDeathsManager().getTask(0);
    	sendNPCDialogue(npcId, 9827, "You must kill "+player.getDeathsManager().getAmount()+" "+player.getDeathsManager().getName()+"'s.");
    	stage = 2;
	} else if (stage == 2) {
		end();
	} else  {
		end();
	}
	}

    @Override
    public void finish() {

    }

}
