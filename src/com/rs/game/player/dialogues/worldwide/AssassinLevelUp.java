package com.rs.game.player.dialogues.worldwide;

import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.dialogues.Dialogue;

public final class AssassinLevelUp extends Dialogue {

	public static final int[] SKILL_LEVEL_UP_MUSIC_EFFECTS = { 37, 37, 37, 37,
			37 };

	private int skill;

	@Override
	public void start() {
		skill = (Integer) parameters[0];
		int level = player.getSkills().getAssassinLevelForXp(skill);
		player.getTemporaryAttributtes().put("leveledUp", skill);
		player.getTemporaryAttributtes().put("leveledUp[" + skill + "]",
				Boolean.TRUE);
		player.setNextGraphics(new Graphics(199));
		if (level == 99)
			player.setNextGraphics(new Graphics(1765));
		player.getInterfaceManager().sendChatBoxInterface(740);
		String name = Skills.SKILL_NAME_ASSASSIN[skill];
		player.getPackets().sendIComponentText(
				740,
				0,
				"Congratulations, you have just advanced a"
						+ (name.startsWith("A") ? "n" : "") + " " + name
						+ " level!");
		player.getPackets().sendIComponentText(740, 1,
				"You have now reached level " + level + ".");
		player.getPackets().sendGameMessage(
				"You've just advanced a" + (name.startsWith("A") ? "n" : "")
						+ " " + name + " level! You have reached level "
						+ level + ".");
		//player.getPackets().sendConfigByFile(4757, 20);
		int musicEffect = SKILL_LEVEL_UP_MUSIC_EFFECTS[skill];
		if (musicEffect != -1)
			player.getPackets().sendMusicEffect(musicEffect);
		if (level == 99) {
			sendNews(player, skill, level);
		}
	}

	public static void sendNews(Player player, int skill, int level) {
			player.getSquealOfFortune().giveEarnedSpins(3);
			player.sm("<col=008000>You have been awarded three Squeal of Fortune spins!");
			if (player.inform99s == true) {
			World.sendWorldMessage(
					"<img=6><col=ff8c38>News: " + player.getDisplayName()
							+ " has achieved " + level + " "
							+ Skills.SKILL_NAME_ASSASSIN[skill] + ".", false);
			}
			return;
}


	@Override
	public void run(int interfaceId, int componentId) {
		end();
	}

	@Override
	public void finish() {
		// player.getPackets().sendConfig(1179, SKILL_ICON[skill]); //removes
		// random flash
	}
}