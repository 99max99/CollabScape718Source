package com.rs.game.player.content.magic;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.item.Item;
import com.rs.game.player.Equipment;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Smelting.SmeltingBar;
import com.rs.game.player.content.ItemConstants;

public class Alchemy {
	
	public static void handleSuperheat(Player player, Item item) {
		player.getInterfaceManager().openGameTab(7);
		if (!player.canAlch()) {
			return;
		}
		SmeltingBar bar = SmeltingBar.forOre(item.getId());
		if (bar == null) {
			player.getPackets().sendGameMessage("You can only cast this spell on an ore.");
			return;
		}
		
		if (!player.getInventory().containsItem(bar.getItemsRequired()[0].getId(), bar.getItemsRequired()[0].getAmount())) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You need " + bar.getItemsRequired()[0].getDefinitions().getName() + " to create a " + bar.getProducedBar().getDefinitions().getName() + ".");
			return;
		}
		if (bar.getItemsRequired().length > 1) {
			if (!player.getInventory().containsItem(bar.getItemsRequired()[1].getId(), bar.getItemsRequired()[1].getAmount())) {
				player.getDialogueManager().startDialogue("SimpleMessage", "You need " + bar.getItemsRequired()[1].getDefinitions().getName() + " to create a " + bar.getProducedBar().getDefinitions().getName() + ".");
				return;
			}
		}
		if (player.getSkills().getLevel(Skills.SMITHING) < bar.getLevelRequired()) {
			player.getDialogueManager().startDialogue("SimpleMessage", "You need a Smithing level of at least " + bar.getLevelRequired() + " to smelt " + bar.getProducedBar().getDefinitions().getName());
			return;
		}
		
		if (!Magic.checkSpellRequirements(player, 43, true, Magic.NATURE_RUNE, 1, Magic.FIRE_RUNE, 4))
			return;
	
		player.setNextAnimation(new Animation(722));
		player.setNextGraphics(new Graphics(148));
		for (Item itemReq : bar.getItemsRequired())
			player.getInventory().deleteItem(itemReq);
		player.getInventory().addItem(bar.getProducedBar().getId(), 1);
		player.getSkills().addXp(Skills.MAGIC, 53);
		player.getSkills().addXp(Skills.SMITHING, bar.getExperience());
		player.setAlchDelay(1000L);
	}
	
	public static void handleAlchemy(Player player, Item item, boolean lowAlch) {
		player.getInterfaceManager().openGameTab(7);
		if (!player.canAlch()) {
			return;
		}
		if ((!ItemConstants.isTradeable(item)) || item.getId() == 995) {
			player.getPackets().sendGameMessage("That isn't alchable!");
			return;
		}
		if (lowAlch) {
			if (!Magic.checkSpellRequirements(player, 21, true, Magic.NATURE_RUNE, 1, Magic.FIRE_RUNE, 3))
				return;
		} else {
			if (!Magic.checkSpellRequirements(player, 55, true, Magic.NATURE_RUNE, 1, Magic.FIRE_RUNE, 5))
				return;
		}
		ItemDefinitions def = ItemDefinitions.getItemDefinitions(item.getId());
		if (def == null)
			return;
		ItemDefinitions wDef = null;
		if (player.getEquipment().getItem(Equipment.SLOT_WEAPON) != null) {
			wDef = ItemDefinitions.getItemDefinitions(player.getEquipment().getWeaponId());
		}
		if (lowAlch) {
			if (wDef != null && wDef.getName().toLowerCase().contains("staff")) {
				player.setNextAnimation(new Animation(9625));
				player.setNextGraphics(new Graphics(1692));
			} else {
				player.setNextAnimation(new Animation(712));
				player.setNextGraphics(new Graphics(112));
			}
			player.getInventory().deleteItem(item.getId(), 1);
			player.getInventory().addItemMoneyPouch(995, (int) (def.getTipitPrice()/2));
			player.getSkills().addXp(Skills.MAGIC, 31);
		} else {
			if (wDef != null && wDef.getName().toLowerCase().contains("staff")) {
				player.setNextAnimation(new Animation(9633));
				player.setNextGraphics(new Graphics(1693));
			} else {
				player.setNextAnimation(new Animation(713));
				player.setNextGraphics(new Graphics(113));
			}
			player.getInventory().deleteItem(item.getId(), 1);
			player.getInventory().addItemMoneyPouch(995, def.getTipitPrice()-(def.getTipitPrice()/3));
			player.getSkills().addXp(Skills.MAGIC, 65);
		}
		player.setAlchDelay(1100L);
	}
	
}
