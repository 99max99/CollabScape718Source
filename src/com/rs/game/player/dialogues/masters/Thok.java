package com.rs.game.player.dialogues.masters;

import com.rs.cache.loaders.NPCDefinitions;
import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;

public class Thok extends Dialogue {

	public static int SKILLCAPE = 18509;
	public static int SKILLHOOD = 18510;
	public static int ONE = 1;

	private int npcId;

	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendEntityDialogue(SEND_2_TEXT_CHAT,
				new String[] {
						NPCDefinitions.getNPCDefinitions(npcId).name,
						"Hey " + player.getDisplayName() + "," + 
						" you currently have <col=FF0000>" + player.getDungTokens() + " </col>tokens." }, IS_NPC,
				npcId, 9760);
		stage = -1;
	}

	@Override
	public void run(int interfaceId, int componentId) {
		if (stage == -1) {
			sendOptionsDialogue("Dungeoneering Options", "I would like a skill cape.", "I would like a mastery cape.", "Show me your rewards shop.", "I would like to enter the lobby.", "Cancel.");
		}
		if (componentId == OPTION_1) {
			if (player.getSkills().getLevelForXp(Skills.DUNGEONEERING) < 99) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getPackets().sendGameMessage(
						"You need a Dungeoneering level of 99.");
				return;
			}
			if (player.getInventory().containsItem(995, 99000)) {
				player.getInventory().removeItemMoneyPouch(995, 99000);
				player.getInventory().refresh();
				player.getInventory().addItem(SKILLCAPE, ONE);
				player.getInventory().addItem(SKILLHOOD, ONE);
			} else {
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You need 99,000 gold to buy a skillcape.");
			}
		}
		if (componentId == OPTION_2) {
			if (player.getSkills().getLevelForXp(Skills.DUNGEONEERING) < 120) {
				player.getInterfaceManager().closeChatBoxInterface();
				player.getPackets().sendGameMessage(
						"You need a Dungeoneering level of 120.");
				return;
			}
			if (player.getInventory().containsItem(995, 99000)) {
				player.getInventory().removeItemMoneyPouch(995, 99000);
				player.getInventory().refresh();
				player.getInventory().addItem(19709, 1);
				player.getInventory().addItem(SKILLHOOD, ONE);
			} else {
				player.getInterfaceManager().closeChatBoxInterface();
				player.sm("You need 99,000 gold to buy a mastery skillcape.");
			}
		}
		if (componentId == OPTION_3) {
			player.getInterfaceManager().sendDungShop();
			player.getInterfaceManager().closeChatBoxInterface();
		}
		if (componentId == OPTION_4) {
			player.getInterfaceManager().closeChatBoxInterface();
			player.getBank().depositAllEquipment(false);
			player.getBank().depositAllInventory(false);
			player.getControlerManager().startControler(
					"RuneDungLobby", 1);
			player.getPackets().sendGameMessage("Dungeoneering is disabled(Dungeoneering is being remade!");
		}
		if (componentId == OPTION_5) {
			player.getInterfaceManager().closeChatBoxInterface();
		}
	}

	@Override
	public void finish() {

	}

}
