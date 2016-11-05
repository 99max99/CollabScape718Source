package com.rs.game.player.content;

import java.util.Arrays;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.player.Player;

public final class ClanCapeCustomizer {

	private ClanCapeCustomizer() {

	}

	public static void resetClanCapes(Player player) {
		player.setClanCapeCustomized(Arrays.copyOf(ItemDefinitions.getItemDefinitions(20708).originalModelColors, 4));
		int[] clantex = new int[2];
		clantex[0] = ItemDefinitions.getItemDefinitions(20708).originalTextureIds[0];
		clantex[1] = ItemDefinitions.getItemDefinitions(20708).originalTextureIds[1];
		player.setClanCapeSymbols(clantex);
	}

	public static void startCustomizing(Player player) {
		int[] skillCape = player.getClanCapeCustomized();
		player.getInterfaceManager().sendInterface(1105);
		for (int i = 0; i < 4; i++)
			player.getPackets().sendConfigByFile(9254 + i, skillCape[i]);
		player.getPackets().sendIComponentModel(1105, 55, player.getAppearence().isMale() ? ItemDefinitions.getItemDefinitions(20708).getMaleWornModelId1() : ItemDefinitions.getItemDefinitions(20708).getFemaleWornModelId1());
	}

	public static void handleSkillCapeCustomizerColor(Player player, int colorId) {
		if (player.getRights() >= 2) {
			player.getPackets().sendGameMessage("Customize color: "+colorId);
		}

		Integer part = (Integer) player.getTemporaryAttributtes().get("ClanCapeCustomizeID");
		if (part == null)
			return;
		int[] skillCape = player.getClanCapeCustomized();
		skillCape[part] = colorId;
		player.getPackets().sendConfigByFile(9254 + part, colorId);
		player.getInterfaceManager().sendInterface(20);
	}

	public static void handleSkillCapeCustomizer(Player player, int buttonId) {
		if (player.getRights() >= 2) {
			player.getPackets().sendGameMessage("Customize button: "+buttonId);
		}
		
		int[] skillCape = player.getClanCapeCustomized();
		if (buttonId == 58) { // reset
				player.setClanCapeCustomized(Arrays.copyOf(ItemDefinitions.getItemDefinitions(20708).originalModelColors, 4));
			for (int i = 0; i < 4; i++)
				player.getPackets().sendConfigByFile(9254 + i, skillCape[i]);
		} else if (buttonId == 34) { // detail top
			player.getTemporaryAttributtes().put("SkillcapeCustomize", 0);
			player.getInterfaceManager().sendInterface(19);
			player.getPackets().sendConfig(2174, skillCape[0]);
		} else if (buttonId == 71) { // background top
			player.getTemporaryAttributtes().put("SkillcapeCustomize", 1);
			player.getInterfaceManager().sendInterface(19);
			player.getPackets().sendConfig(2174, skillCape[1]);
		} else if (buttonId == 83) { // detail button
			player.getTemporaryAttributtes().put("SkillcapeCustomize", 2);
			player.getInterfaceManager().sendInterface(19);
			player.getPackets().sendConfig(2174, skillCape[2]);
		} else if (buttonId == 95) { // background button
			player.getTemporaryAttributtes().put("SkillcapeCustomize", 3);
			player.getInterfaceManager().sendInterface(19);
			player.getPackets().sendConfig(2174, skillCape[3]);
		} else if (buttonId == 114 || buttonId == 142) { // done / close
			player.getAppearence().generateAppearenceData();
			player.closeInterfaces();
		}
	}
}
