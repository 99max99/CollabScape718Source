package com.rs.game.player.controlers;

import java.util.ArrayList;
import java.util.List;

import com.rs.Settings;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.npc.others.DreadnautBoss;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

public final class DreadnautControler extends Controler {
	
	private DreadnautBoss target;
	
	
	
	@Override
	public boolean canAttack(Entity target) {
		if (target instanceof DreadnautBoss && target != this.target) {
			player.getPackets().sendGameMessage("This isn't your target.");
			return false;
		}
		return true;
	}
	

	
	private void leave(boolean logout) {
		if(target != null)
			target.finish(); //target also calls removing hint icon at remove
		if(!logout) {
			player.getPackets().sendBlackOut(0); //unblacks minimap
			if (player.getHiddenBrother() == -1)
				player.getPackets().sendStopCameraShake();
			else
				player.getPackets().closeInterface(player.getInterfaceManager().hasRezizableScreen() ? 11 : 0); //removes inter
			removeControler();
		}
	}
	
	@Override
	public boolean sendDeath() {
		leave(false);
		return true;
	}
	

	@Override
	public void magicTeleported(int type) {
		leave(false);
	}
	


	
	

	
	public void targetDied() {
		player.getHintIconsManager().removeUnsavedHintIcon();
		target = null;
		
	}
	
	public void targetFinishedWithoutDie() {
		player.getHintIconsManager().removeUnsavedHintIcon();
		target = null;
	}
	
	public void setBrotherSlained(int index) {

	}
	
	public void sendTarget(int id, WorldTile tile) {
		if(target != null) 
			target.disapear();
		target = new DreadnautBoss(id, tile, this);
		target.setTarget(player);
		target.setNextForceTalk(new ForceTalk("Im going to crush you!!"));
		player.getHintIconsManager().addHintIcon(target, 1, -1, false);
	}
	

	public DreadnautControler() {
	}
	
	//component 9, 10, 11
	
	private int headComponentId;
	private int timer;
	
	
	public int getAndIncreaseHeadIndex() {
		Integer head = (Integer) player.getTemporaryAttributtes().remove("DreadHead");
		if(head == null || head == player.getKilledBarrowBrothers().length-1)
			head = 0;
		player.getTemporaryAttributtes().put("DreadHead", head+1);
		return player.getKilledBarrowBrothers()[head] ? head : -1;
	}
	
	@Override
	public void process() {
		if(timer > 0) {
			timer--;
			return;
		}
		if(headComponentId == 0) {
			int headIndex = getAndIncreaseHeadIndex();
			if(headIndex == -1) {
				resetHeadTimer();
				return;
			}
			headComponentId = 9 + Utils.random(2);
			player.getPackets().sendItemOnIComponent(24, headComponentId, 4761 + headIndex, 0);
			player.getPackets().sendIComponentAnimation(9810, 24, headComponentId);
			timer = 3;
		}else{
			player.getPackets().sendItemOnIComponent(24, headComponentId, -1, 0);
			headComponentId = 0;
			resetHeadTimer();
		}
	}
	
	public void resetHeadTimer() {
		timer = 20 + Utils.random(6);
	}
	
	
	@Override
	public void sendInterfaces() {
	}
	
	public void loadData() {
		resetHeadTimer();
	}
	
	
	@Override
	public void start() {
		player.sendMessage("You are now fighting the dreadnaut");
		player.setNextWorldTile(new WorldTile(3174, 9766, 0));
		loadData();
		sendInterfaces();
		this.sendTarget(12862,new WorldTile(3163, 9766, 0));
	}
	
	@Override
	public boolean login() {
		//if(player.getHiddenBrother() == -1)
			//player.getPackets().sendCameraShake(3, 25, 50, 25, 50);
		//	if(player.startBoss == true){
			//	sendTarget(12862,player);
				//player.startBoss = false;
			//}
		loadData();
		sendInterfaces();
		return false;
	}
	
	@Override
	public boolean logout() {
		leave(true);
		return false;
	}
	
	@Override
	public void forceClose() {
		leave(true);
	}
	

}
