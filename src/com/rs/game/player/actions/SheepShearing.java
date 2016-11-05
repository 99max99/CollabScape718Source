package com.rs.game.player.actions;

import java.util.concurrent.TimeUnit;

import com.rs.game.Animation;
import com.rs.game.ForceTalk;
import com.rs.game.item.Item;
import com.rs.game.npc.Transformation;
import com.rs.game.player.Player;
import com.rs.game.npc.NPC;

public class SheepShearing extends Action {

	public static final int SHEARS = 1735;
	public SheepShearing() {
		
	}

	@Override
	public boolean start(Player player) {
		if (!player.getInventory().containsItemToolBelt(1735)) {
		    player.getPackets().sendGameMessage("You need a pair of shears in order to sheer the sheep.");
		    return false;
		}
		return true;
	}

	@Override
	public boolean process(Player player) {
		return player.getInventory().hasFreeSlots() && player.getInventory().containsItem(SHEARS, 1);
	}

	@Override
	public int processWithDelay(Player player) {
		player.lock(1);
		player.setNextAnimation(new Animation(893));
		player.getInventory().addItem(1737, 1);
		
	//transformIntoNPC(1158);
	//getCombatDefinitions().getRespawnDelay(),
	//TimeUnit.MILLISECONDS);
		return 1;
	}

	@Override
	public void stop(Player player) {
		setActionDelay(player, 1);
	}

}