package com.rs.game.player.actions;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.npc.others.LiquidGoldNymph;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.mining.MiningBase;
import com.rs.utils.Utils;

public class LavaFlowMine extends MiningBase {
	
	private WorldObject object;
	private double XPBoost = 1.0;
	
	public LavaFlowMine(WorldObject object) {
		this.object = object;
	}

	@Override
	public boolean start(Player player) {
		if (!checkAll(player))
			return false;
		player.getPackets().sendGameMessage(
				"You swing your pickaxe at the rock.", true);
		setActionDelay(player, getMiningDelay(player));
		return true;
	}

	private int getMiningDelay(Player player) { 
		int summoningBonus = 0;
		if (player.getFamiliar() != null) {
			if (player.getFamiliar().getId() == 7342
					|| player.getFamiliar().getId() == 7342)
				summoningBonus += 10;
			else if (player.getFamiliar().getId() == 6832
					|| player.getFamiliar().getId() == 6831)
				summoningBonus += 1;
		}
		int oreBaseTime = 50;
		int oreRandomTime = 20;
		int mineTimer = oreBaseTime
				- (player.getSkills().getLevel(Skills.MINING) + summoningBonus)
				- Utils.getRandom(pickaxeTime);
		if (mineTimer < 1 + oreRandomTime)
			mineTimer = 1 + Utils.getRandom(oreRandomTime);
		mineTimer /= player.getAuraManager().getMininingAccurayMultiplier();
		return mineTimer;
	}

	@Override
	public boolean process(Player player) {
		player.setNextAnimation(new Animation(emoteId));
		player.faceObject(object);
		if (checkAll(player)) {
			if (Utils.getRandom(18) == 0) {
				AddXP(player);
			}
			if (Utils.getRandom(80) == 0) {
				player.stopAll();
			}
			if(Utils.random(250) == 0) {
				new LiquidGoldNymph(new WorldTile(player.getX(), player.getY(), player.getPlane()), player);
				player.getPackets().sendGameMessage("<col=ff0000>A Liquid Gold Nymph emerges from the mined away crust!");
			}
			return true;
		}
		return false;
	}
	
	private boolean checkAll(Player player) {
		if (!setPickaxe(player)) {
			player.getPackets().sendGameMessage(
					"You need a pickaxe to mine this rock.");
			return false;
		}
		if (!hasPickaxe(player)) {
			player.getPackets().sendGameMessage(
					"You dont have the required level to use this pickaxe.");
			return false;
		}
		if (!hasMiningLevel(player)) {
			return false;
		}
		
		return true;
	}
	
	private boolean hasMiningLevel(Player player) {
		if (68 > player.getSkills().getLevel(Skills.MINING)) {
			player.getPackets().sendGameMessage(
					"You need a mining level of 68 to mine this rock.");
			return false;
		}
		return true;
	}

	@Override
	public int processWithDelay(Player player) {
		AddXP(player);
		return getMiningDelay(player);
	}

	private void AddXP(Player player) {
		double xpBoost = XPBoost;
		double totalXp = Utils.random(65, 80) * xpBoost;
		if (hasMiningSuit(player))
			totalXp *= 1.056;
		player.getSkills().addXp(Skills.MINING, totalXp);
			player.getPackets().sendGameMessage(
					"You mine away some crust.", true);
	}
	
	private boolean hasMiningSuit(Player player) {
		if (player.getEquipment().getHatId() == 20789 && player.getEquipment().getChestId() == 20791 
				&& player.getEquipment().getBootsId() == 20787
				&& player.getEquipment().getLegsId() == 20790 && player.getEquipment().getBootsId() == 20788)
			return true;
		return false;
	}

	@Override
	public void stop(Player player) {
		setActionDelay(player, 3);
	}

}
