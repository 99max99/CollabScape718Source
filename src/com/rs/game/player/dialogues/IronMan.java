package com.rs.game.player.dialogues;

public class IronMan extends Dialogue {
	

	@Override
	public void start() {
		sendEntityDialogue(SEND_1_TEXT_CHAT,
				new String[] { player.getDisplayName(), "Do I really need this, since I'm playing as an Iron Man? Noo way, I should rather avoid doing this, I do not need this!" },							
				IS_PLAYER, player.getIndex(), 9827);	
	}		
	

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			stage = 0;
			end();
		}
	}
	
	

	@Override
	public void finish() {

	}

	


}
