package com.rs.game.player.dialogues;

import com.rs.game.WorldTile;
import com.rs.game.player.content.LividFarm;
import com.rs.game.player.content.magic.Magic;
import com.rs.utils.ShopsHandler;

// Referenced classes of package com.rs.game.player.dialogues:
//            Dialogue

public class ForumPoint extends Dialogue {

	public ForumPoint() {
	}

	@Override
	public void start() {
		stage = 1;
		sendOptionsDialogue("Forum Points", "What are Forum Points?",
				"I want to check my current Forum Points.", "Show me the Forum Point Store.", 
				"Nevermind.");

	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == 1) {
			if (componentId == OPTION_1) {
				player.getPackets().sendOpenURL(
                        "http://tinyurl.com/RageForumPoint");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_2) {
			player.sm("Forum Tokens are used as a currenct, apply for them on the forums.");
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_3) {
				ShopsHandler.openShop(player, 30);
				player.getInterfaceManager().closeChatBoxInterface();
			} else if (componentId == OPTION_4) {
				player.getInterfaceManager().closeChatBoxInterface();
			}
		} 
}
	

	@Override
	public void finish() {
	}

}
