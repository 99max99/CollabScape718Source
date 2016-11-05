package com.rs.game.player.dialogues;

import com.rs.game.player.content.dungeoneering.DungeonRewards;
import com.rs.game.player.content.dungeoneering.DungeonRewards.DungeonReward;

public class DungRewardConfirm extends Dialogue {

	DungeonReward item;

 @Override
	public void start() {
		item = (DungeonReward) parameters[0];
		player.getInterfaceManager().sendChatBoxInterface(1183);
		player.getPackets().sendIComponentText(1183, 12, "It will cost "+ item.getCost() +" for "+item.getName()+".");
		player.getPackets().sendItemOnIComponent(1183, 13, item.getId(), 1);
		player.getPackets().sendIComponentText(1183, 7, "Are you sure you want to buy this?");
		player.getPackets().sendIComponentText(1183, 22, "Confirm Purchase");
	}

@Override
	public void run(int interfaceId, int componentId) {
		player.getPackets().sendGameMessage("COMPONENTID: "+componentId);
		if (componentId == 9) {
			if (player.getInventory().getFreeSlots() >= 1) {
				player.setDungTokens(player.getDungTokens() - item.getCost());
				player.getInventory().addItem(item.getId(), 1);
				DungeonRewards.refresh(player);
			} else {
				player.getPackets().sendGameMessage("You need more inventory space.");
			}
		}
		end();
	}

@Override
	public void finish() {

	}

}