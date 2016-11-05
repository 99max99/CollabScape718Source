package com.rs.game.npc.vorago;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rs.Settings;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.Hit.HitLook;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.npc.combat.impl.VoragoCombat;
import com.rs.game.npc.NPC;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.player.Player;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

@SuppressWarnings("serial")
public class Vorago extends NPC {
	/*** This is to decide where the drops will appear ***/
	int dtX = 3552;
	int dtY = 9508;
	private WorldTile Drop1 = new WorldTile(dtX-1, dtY-1, 0);
	private WorldTile Drop2 = new WorldTile(dtX-1, dtY+1, 0);
	private WorldTile Drop3 = new WorldTile(dtX, dtY, 0);
	private WorldTile Drop4 = new WorldTile(dtX+1, dtY-1, 0);
	private WorldTile Drop5 = new WorldTile(dtX+1, dtY+1, 0);
	
	/*** Used at the end of phases ***/
	private WorldTile disappear = new WorldTile(3555, 9470, 0);
	private WorldTile P5Start = (new WorldTile(3552, 9497, 0));
	private WorldTile P5StartP = (new WorldTile(3552, 9495, 0));
	private WorldTile centre = new WorldTile(3552, 9503, 0);
	private Graphics stompgfx = new Graphics(1834);
	private WorldTile[] END_PHASE_TELEPORTS = { new WorldTile(3556, 9509, 0), // northeast
			new WorldTile(3545, 9508, 0), // northwest
			new WorldTile(3545, 9497, 0), // southwest
			new WorldTile(3558, 9496, 0), //southeast
			new WorldTile(3552, 9503, 0),}; //centre
	
	public static int Phase = 1;
	private int hp = getHitpoints();
	private int maxhp = getMaxHitpoints();
	public static boolean StartWfs = false;
	public static boolean StartP3 = false;
	public static boolean StartP2 = false;
	public static boolean StartP5 = false;
	private VoragoCombat rago;
	private static long lastReflect = 0;
	private static long lastP5 = 0;
	private ArrayList<Entity> possibleTargets = getPossibleTargets();
	public static final List<Player> playersOn = Collections
			.synchronizedList(new ArrayList<Player>());
	WorldObject portal = new WorldObject(11899, 10, 3, 3551, 9511, 0);

	@SuppressWarnings("unused")
	public Vorago(int id, WorldTile tile, int mapAreaNameHash, boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, spawned);
		final NPCCombatDefinitions defs = getCombatDefinitions();
		setForceAgressive(true);
		setCapDamage(1000);
		final Entity target;
		World.removeObject(portal, false);
	}
	
		
	public void testEnd(){
		setHitpoints(15000);
		VoragoCombat.reflectCount = 3;
	}
	private static int Week = 1;
	/*
	 * 1 = TeamSplit
	 * 2 = Scopulus
	 * 3 = Vitalis
	 * 
	 */
	public static int getWeek() {
		return Vorago.Week;
	}
	
	public static void setWeek(int value) {
		Vorago.Week = value;
	}

	public static long getLastReflect() {
		return lastReflect;
	}

	public static void setLastReflect(long lastReflect) {
		Vorago.lastReflect = lastReflect;
	}
	
	public static long getLastP5() {
		return lastP5;
	}

	public static void setLastP5(long lastP5) {
		Vorago.lastReflect = lastP5;
	}
	
	
	@Override
	public double getMagePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getRangePrayerMultiplier() {
		return 0.6;
	}

	@Override
	public double getMeleePrayerMultiplier() {
		return 0.6;
	}
	

		
		@Override
	public void handleIngoingHit(Hit hit) {
		if (hit.getLook() != HitLook.MELEE_DAMAGE && hit.getLook() != HitLook.RANGE_DAMAGE && hit.getLook() != HitLook.MAGIC_DAMAGE)
			return;
		super.handleIngoingHit(hit);
		if (hit.getSource() != null && VoragoCombat.isReflecting == true) {	
			int recoil = (int) (hit.getDamage() * 2);
			if (recoil > 0)
				hit.getSource().applyHit(
						new Hit(this, recoil, HitLook.REFLECTED_DAMAGE));
		}
	}
	
		/***Vorago Drops***/
	Item[] REWARDS = { new Item(220, Utils.random(15, 20)), new Item(15271, Utils.random(75, 152)),
				new Item(1128, Utils.random(3, 9)), new Item(5304, Utils.random(4,10)), 
				new Item(452, Utils.random(15,25)), new Item(1748, Utils.random(35, 55)), 
				new Item(9245, Utils.random(20, 50)), new Item(1514, Utils.random(35, 110)),
				new Item(18778, 1), };
	Item[] RARES = { new Item(28617, 1), new Item(28621, 1),//Seis wand, singularity, ancient summoning stone
			new Item(29626, 1),  };
	
	@Override
	public void drop() {
		Player killer = getMostDamageReceivedSourcePlayer();
		int idx1 = Utils.random(REWARDS.length);
		int idx2 = Utils.random(REWARDS.length);
		int idx3 = Utils.random(REWARDS.length);
		int idx4 = Utils.random(REWARDS.length);
		int idx5 = Utils.random(REWARDS.length);
		Item baseDrop = new Item(28627, Utils.random(2));
		World.addGroundItem(REWARDS[idx1], Drop1);
		World.addGroundItem(REWARDS[idx2], Drop2);
		World.addGroundItem(REWARDS[idx3], Drop3);
		World.addGroundItem(REWARDS[idx4], Drop4);
		World.addGroundItem(REWARDS[idx5], Drop5);
		World.addGroundItem(baseDrop, Drop1);
		World.addGroundItem(baseDrop, Drop2);
		World.addGroundItem(baseDrop, Drop3);
		World.addGroundItem(baseDrop, Drop4);
		World.addGroundItem(baseDrop, Drop5);
		if (Utils.random(10) == 0);
		int rares = Utils.random(RARES.length);
		if (Utils.random(1, 5) == 1) {
			World.addGroundItem(RARES[rares], Drop1, killer, false, 180, true);
			World.sendGraphics(killer, new Graphics(364), Drop1);
			World.sendWorldMessage("<img=7><col=FF6600>News: "+killer.getDisplayName()+" has recieved a "+RARES[rares].getName()+" as a drop!</col>", false);
			killer.setNextAnimation(new Animation (15529));
		} else if (Utils.random(1, 5) == 2) {
			World.addGroundItem(RARES[rares], Drop2, killer, false, 180, true);
			World.sendGraphics(killer, new Graphics(364), Drop2);
			World.sendWorldMessage("<img=7><col=FF6600>News: "+killer.getDisplayName()+" has recieved a "+RARES[rares].getName()+" as a drop!</col>", false);
			killer.setNextAnimation(new Animation (15529));
		} else if (Utils.random(1, 5) == 3) {
			World.addGroundItem(RARES[rares], Drop3, killer, false, 180, true);
			World.sendGraphics(killer, new Graphics(364), Drop3);
			World.sendWorldMessage("<img=7><col=FF6600>News: "+killer.getDisplayName()+" has recieved a "+RARES[rares].getName()+" as a drop!</col>", false);
			killer.setNextAnimation(new Animation (15529));
		} else if (Utils.random(1, 5) == 4) {
			World.addGroundItem(RARES[rares], Drop4, killer, false, 180, true);
			World.sendGraphics(killer, new Graphics(364), Drop4);
			World.sendWorldMessage("<img=7><col=FF6600>News: "+killer.getDisplayName()+" has recieved a "+RARES[rares].getName()+" as a drop!</col>", false);
			killer.setNextAnimation(new Animation (15529));
		} else if (Utils.random(1, 5) == 5) {
			World.addGroundItem(RARES[rares], Drop5, killer, false, 180, true);
			World.sendGraphics(killer, new Graphics(364), Drop5);
			World.sendWorldMessage("<img=7><col=FF6600>News: "+killer.getDisplayName()+" has recieved a "+RARES[rares].getName()+" as a drop!</col>", false);
			killer.setNextAnimation(new Animation (15529));
		}
			}
	
	@Override
	public void sendDeath(Entity source) {
		if (Phase < 5) {
			
			setNextAnimation(new Animation(-1));
			resetWalkSteps();
			reset();
			finish();
			setLocation(3552, 9503, 0);
			Phase++;
			spawn();
			EndTask();
			} else if (Phase == 5) {
			setNextAnimation(new Animation(-1));
			setNextAnimation(new Animation(getCombatDefinitions().getDeathEmote()));
			resetWalkSteps();
			WorldTasksManager.schedule(new WorldTask() {
				public void run() {
					Phase = 1;
		        	VoragoCombat.reflectCount = 0;
		        	VoragoCombat.wfCount = 0;
		        	VoragoCombat.isReflecting = false;
		        	VoragoCombat.spawnScop = false;
		        	VoragoCombat.CanMaul = false;
					drop();
					reset();
					finish();
					World.spawnObject(portal, false);
					setLocation(3552, 9503, 0);
					 if (!isSpawned()) {
	                        setRespawnTask();
	                    }
					stop();
				}
		}, 7);
		}
	}
	
	public void EndTask() {
		setCantInteract(true);
		int idx = Utils.random(END_PHASE_TELEPORTS.length);
		final WorldTile nwt = END_PHASE_TELEPORTS[idx];//Next WorldTile
		int idx2 = Utils.random(END_PHASE_TELEPORTS.length);
		final WorldTile nwt2 = END_PHASE_TELEPORTS[idx2];//Next WorldTile
		int idx3 = Utils.random(END_PHASE_TELEPORTS.length);
		final WorldTile nwt3 = END_PHASE_TELEPORTS[idx3];//Next WorldTile
		setNextFaceWorldTile(nwt);
		WorldTasksManager.schedule(new WorldTask() {
			private int count = 0;
			@Override
			public void run() {
				if (count == 2) {
					setNextWorldTile(disappear);
				}
				if (count == 3) {
				setNextWorldTile(nwt);
				setNextGraphics(stompgfx);
				setNextFaceWorldTile(nwt2);
				}
				if (count == 5) {
					setNextWorldTile(disappear);
				}
				if (count == 6) {
					setNextWorldTile(nwt2);
					setNextGraphics(stompgfx);
					setNextFaceWorldTile(nwt3);
				} if (count == 8) {
					setNextWorldTile(disappear);
				}
				if (count == 9) {
					setNextWorldTile(nwt3);
					setNextGraphics(stompgfx);
					if (Phase != 5) {
					setNextFaceWorldTile(centre);
					} else {
						setNextFaceWorldTile(P5Start);
					}
				}
			
				if (count == 11) {
					setNextWorldTile(disappear);
				}
				if (count == 12) {
					setHitpoints(getMaxHitpoints());
					if (Phase == 5) {
						setNextWorldTile(P5Start);
						setNextGraphics(stompgfx);
						for (final Entity t : possibleTargets) {
							if (t instanceof Player) {
								final Player p = (Player) t;
						p.setNextWorldTile(P5StartP);
							}
							}
						StartP5 = true;
					} else if (Phase == 3) {
						setNextWorldTile(centre);
						setNextGraphics(stompgfx);
							StartP3 = true;
						} else if (Phase == 4) {
							StartWfs = true;
							setNextWorldTile(centre);
							setNextGraphics(stompgfx);
						} else if (Phase == 2) {
							setNextWorldTile(centre);
							setNextGraphics(stompgfx);
							setLastReflect(Utils.currentTimeMillis()+15000);
								StartP2 = true;
						} else {
							setNextWorldTile(centre);
							setNextGraphics(stompgfx);
						}
					setCantInteract(false);
					for (final Entity t : possibleTargets) {
						if (t instanceof Player) {
							final Player p = (Player) t;
					p.getPackets().sendGameMessage("Phase "+Phase+" begins now!");
							}
						}
					setCantInteract(false);
					stop();
				}
				count++;
			}
		}, 0, 1);
	}
}