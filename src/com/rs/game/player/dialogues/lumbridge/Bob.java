package com.rs.game.player.dialogues.lumbridge;


import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.item.Item;
import com.rs.game.player.dialogues.Dialogue;
import com.rs.utils.Misc;
import com.rs.utils.ShopsHandler;


public class Bob extends Dialogue {


	private int npcId;


	@Override
	public void start() {
		npcId = (Integer) parameters[0];
		sendOptionsDialogue(SEND_DEFAULT_OPTIONS_TITLE, "I'd like to trade.",
				"Can you repair my items for me?");
	}


	@Override
	public void run(int interfaceId, int componentId) {
		switch (stage) {
		case -1:
			switch (componentId) {
			case OPTION_1:
				stage = 0;
				sendPlayerDialogue(9827, "I'd like to trade.");
				break;
			case OPTION_2:
				stage = 1;
				sendPlayerDialogue(9827, "Can you repair my items for me?");
				break;
			}
			break;
		case 0:
			stage = 3;
			sendNPCDialogue(npcId, 9827,
					"Great! I buy and sell pickaxes and hatchets. "
							+ "There are plenty to choose from.");
			break;
		case 1:
			sendNPCDialogue(npcId, 9827,
					"Of course I can, though materials may cost you. Just "
							+ "hand me the item and I'll take a look.");
			stage = 4;
			break;
		case 3:
		default:
			ShopsHandler.openShop(player, 1);
			stage = 4;
			break;
		case 4:
			end();
			break;
		}
	}
	
	/*private Object[][] prices = new Object[][] { 
			{ "torag's", 15000000 },
			{ "dharok's", 15000000 },
			{ "guthan's", 15000000 },
			{ "verac's", 15000000 },
			{ "karil's", 15000000 },
			{ "ahrim's", 15000000 }, 
			{ "torva", 50000000 },
			{ "pernix", 50000000 },
			{ "virtus", 50000000 },
			{ "zaryte", 50000000 }
	};
	
	private int getRepairPrice() {
		int price = 0;
		for (Item item : player.getInventory().getItems().toArray()) {
			if (item == null)
				continue;
			String name = item.getName();
			if (isRepairable(item)) {
				for (int i = 0; i < prices.length; i++) {
					String firstPart = name.split(" ")[0].toLowerCase();
					if (firstPart.equalsIgnoreCase((String) prices[i][0])) {
						try {
							if (isBarrows(item) && getCharges(item) != -1) {
								price += (int) prices[i][1];
							} else if (!isBarrows(item)) {
								price += (int) prices[i][1];
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		return price;
	}

	private int getCharges(Item item) {
		String name = item.getName();
		int charges = 0;
		try {
			charges =Integer.parseInt(name.split(" ")[name.split(" ").length - 1]);
		} catch (Exception e) {
			charges = -1;
		}
		return charges;
	}

	private int getRepairId(Item item) {
		String name = item.getName();
		if (isBarrows(item) && getCharges(item) != -1) {
			String newName = name.substring(0, name.indexOf("" + getCharges(item)) - 1);
			return ItemNames.getTradeableId(newName);
		} else if (!isBarrows(item)) {
			String newName = name.substring(0, name.indexOf("(broken)") - 1);
			return ItemNames.getTradeableId(newName);
		}
		return -1;
	}

	private boolean isBarrows(Item item) {
		return item.getId() < 20000;
	}

	private void repairAll() {
		for (Item item : player.getInventory().getItems().toArray()) {
			if (item == null)
				continue;
			if (isRepairable(item)) {			
				int newId = (ItemDefinitions.getItemDefinitions(getRepairId(item)).isNoted() ? ItemDefinitions.getItemDefinitions(getRepairId(item)).getCertId() : getRepairId(item));
				item.setId(newId);
				player.getInventory().refresh();
			}
		}
	}

	private boolean isRepairable(Item item) {
		try {
			String name = item.getName();
			for (int i = 0; i < prices.length; i++) {
				if (((String) prices[i][0]).equalsIgnoreCase(name.split(" ")[0])) {
					if (isBarrows(item)) {
						if (getCharges(item) != -1)
							return true;
					} else {
						if (getRepairId(item) != -1)
							return true;
					}
				}
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}*/


	@Override
	public void finish() {
		// TODO Auto-generated method stub


	}


}