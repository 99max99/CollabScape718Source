package com.rs.game.npc.others;

import com.rs.game.WorldTile;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

@SuppressWarnings("serial")
public class LiquidGoldNymph extends NPC {
	
	private static final int[] GOLD = { 20787, 20788, 20789, 20790, 20791};

	private Player target;
	private long createTime;
	
	public LiquidGoldNymph(WorldTile tile, Player target) {
		super(14, tile, -1, true, true);
		this.target = target;
		createTime = Utils.currentTimeMillis();
	}
	
	@Override
	public void processNPC() {
		if(target.hasFinished() || createTime + 60000 < Utils.currentTimeMillis()) 
			finish();
	}
	
	public void giveReward(final Player player) {
		if(player != target || player.isLocked())
			return;
		player.lock();
		WorldTasksManager.schedule(new WorldTask() {

			@Override
			public void run() {
				player.unlock();
			    player.getInventory().addItem(GOLD[Utils.random(GOLD.length - 1)], 1);
				player.getPackets().sendGameMessage("The Liquid Gold Nymph gave you a reward to say thank you.");
				finish();
			}
			
		}, 1);
	}
	
	@Override
	public boolean withinDistance(Player tile, int distance) {
		return tile == target && super.withinDistance(tile, distance);
	}

}
