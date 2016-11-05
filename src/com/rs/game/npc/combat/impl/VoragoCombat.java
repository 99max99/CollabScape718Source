package com.rs.game.npc.combat.impl;

import java.util.ArrayList;

import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.World;
import com.rs.game.Animation;
import com.rs.game.item.Item;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.World;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.game.npc.combat.CombatScript;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.npc.vorago.Vorago;
import com.rs.utils.Utils;

public class VoragoCombat extends CombatScript {
	
	public static boolean CanMaul = false;
	public static boolean hasJumped = false;
	private int GoR;
	private int P5ReflectCount = 0;
	private int P4ReflectCount = 0;
	private int P3ReflectCount = 0;
	private int P5Count = 0;
	private int P4Count = 0;
	private int P3Count = 0;
	public static int StoneDeadCount = 0;
	public WorldTile centre = new WorldTile(3552, 9503, 0);
	private int CX = centre.getX();
	private int CY = centre.getY();
	private WorldTile Scop1 = new WorldTile(3545, 9497, 0);
	private WorldTile Scop2 = new WorldTile(3558, 9496, 0);
	public static boolean ScopDead;
	public static int ScopEnrage = 1;
	private int wfloc2;
	public static boolean FieldOut = false;
	public static boolean CloneDead = true;
	public static int Phase = 1;
	public static int tsCount = 0;
	public static int reflectCount = 0;
	public static boolean isReflecting;
	public static int wfCount = 0;
	public static boolean spawnScop;
	public static boolean SpawnTask;
	public static boolean isSpawning;
	public static boolean isMauling = false;

	private void SpawnClone() {
		CloneDead = false;	
	WorldTasksManager.schedule(new WorldTask() {
		private int count = 0;
		@Override
		public void run() {
			if (count == 7) {
				World.spawnNPC(17158, centre, -1, true, true);
				stop();
				return;
			}
			count++;
		}
	}, 0, 1);
	}
	
	private void SendGField() {
		int RanB = Utils.random(-7, 7);
		int x = centre.getX();
		int y = centre.getY();
		World.spawnNPC(1, new WorldTile(x+RanB, y+RanB, 0), -1, true, true);//This should be an Object not sure how to pack an object
		FieldOut = true;
	}
	
	public WorldTile[] END_PHASE_TELEPORTS = { new WorldTile(3556, 9509, 0), // northeast
			new WorldTile(3545, 9508, 0), // northwest
			new WorldTile(3545, 9497, 0), // southwest
			new WorldTile(3558, 9496, 0), //southeast
			new WorldTile(3552, 9503, 0),}; //centre
	
	/***TODO list
	    * Gravity fields - Done should be an object though
	    * Bring it down
	    * Make the 5 drops method better with it being based on most damage on each phase
	    * Make reflects only onto one player
	    * Make reflects happen every other teamsplit/vitalis on P3
	    * Make the bombs have splash damage
	    * Improve the P5 push back mechanics
	    * Some other stuff I can't think of
	*/
	

	
	@Override
	public Object[] getKeys() {
		return new Object[] { 17182 };
	}

	@Override
	public int attack(final NPC npc, final Entity target) {
		final int hp = npc.getHitpoints();
		final int maxhp = npc.getMaxHitpoints();
		final NPCCombatDefinitions defs = npc.getCombatDefinitions();
		final ArrayList<Entity> possibleTargets = npc.getPossibleTargets();
		Entity targets[] = possibleTargets.toArray(new Entity[possibleTargets.size()]);
		int highest = Utils.getDistance(npc, targets[0]);
		Entity farthest = targets[0];
		for (int index = 1; index < targets.length; index ++) {//This is finding the farthest away player so the bombs only go to them
	        if (Utils.getDistance(npc, targets[index]) > highest) {
	            highest = Utils.getDistance(npc, targets[index]);
	           farthest = targets[index];
	        }
	    }
		if (Utils.random(10) == 0) {
			if (Vorago.getWeek() == 2 && Phase == 3) {//Only Blue bombs on scop p3
				npc.setNextAnimation(new Animation(20356));
				World.sendProjectile(npc, farthest, 4016, 85, 16, 40, 35, 16, 0);
				delayHit(
						npc,
						2,
						farthest,
						getMagicHit(
								npc,
								getRandomMaxHit(npc, 355,
										NPCCombatDefinitions.MAGE, farthest)));	
			} else {
			final Player f = (Player) farthest;
			f.getPackets().sendGameMessage(
					"<col=ff0000>Vorago has sent a red bomb after you. Run!</col>");
			World.sendProjectile(npc, f, 4023, 85, 16, 27, 10, 16, 0);
			WorldTasksManager.schedule(new WorldTask() {
				private int count = 0;
				@Override
				public void run() {
					if (count == 2) {
						/*** TODO Change this to be how close to other players farthest is***/
						int disX = f.getX() - npc.getX();
						int disY = f.getY() - npc.getY();
						int disZ = (int)Math.round((Math.pow(Math.pow(disX, 2) + Math.pow(disY, 2), 0.5))/2);
						if (disZ == 0) {
						int RBDmg = (1000 - Utils.random(100));
						f.setNextGraphics(new Graphics(2942));
						delayHit(npc, 0, f, getRegularHit(npc, RBDmg));
						if (Vorago.Phase == 5) {
							npc.heal(RBDmg/5);
						}
						} else {
							int RBDmg = ((1000/disZ)-Utils.random(100));
							f.setNextGraphics(new Graphics(2942));
							delayHit(npc, 0, f, getRegularHit(npc, RBDmg));
							if (Vorago.Phase == 5) {
								npc.heal(RBDmg/5);
							}
						}
						
						
						stop();
						return;
					}
					count++;
				}
			}, 0, 1);
		}
		} else if (Utils.random(3) == 0) {//Blue Bomb
			npc.setNextAnimation(new Animation(20356));
		World.sendProjectile(npc, farthest, 4016, 85, 16, 40, 35, 16, 0);
		delayHit(
				npc,
				2,
				farthest,
				getMagicHit(
						npc,
						getRandomMaxHit(npc, 355,
								NPCCombatDefinitions.MAGE, farthest)));		
		} else {//Melee
			if (Vorago.getWeek() == 2 && Vorago.Phase == 3) {//Only Blue bombs on scop p3
				npc.setNextAnimation(new Animation(20356));
				World.sendProjectile(npc, farthest, 4016, 85, 16, 40, 35, 16, 0);
				delayHit(
						npc,
						2,
						farthest,
						getMagicHit(
								npc,
								getRandomMaxHit(npc, 355,
										NPCCombatDefinitions.MAGE, farthest)));	
			} else {
			npc.setNextAnimation(new Animation(20355));
			int MDmg = getRandomMaxHit(npc, 355,
					NPCCombatDefinitions.MELEE, target);
			delayHit(npc, 0, target, getMeleeHit(npc, MDmg));	
			if (Vorago.Phase == 5) {
				npc.heal(MDmg/5);
			}
			}
		}
		

 
		if (Vorago.Phase == 1) {//All the Vorago.Phase one stuff
			if (!hasJumped && hp < maxhp*0.28) {
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
				p.getPackets().sendGameMessage("Vorago heals.");
				npc.heal((int) Math.round(maxhp*0.3));
					}
				}
			}
		} else if (Vorago.Phase == 2 && Vorago.StartP2) {//All the Vorago.Phase 2 stuff
			if (reflectCount < 4 && Vorago.getLastReflect() < Utils.currentTimeMillis()
					&& !isReflecting && !FieldOut){
				reflectCount = reflectCount+1;
				isReflecting = true;
				SendGField();
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
				p.getPackets().sendGameMessage(
						"<col=123582>Vorago reflects damage to surrounding foes!</col");
					}
				}
				Vorago.setLastReflect(Utils.currentTimeMillis() + 30000);
				
			WorldTasksManager.schedule(new WorldTask() {
				private int count = 0;
				@Override
				public void run() {
					if (count == 7) {
						for (final Entity t : possibleTargets) {
							if (t instanceof Player) {
								final Player p = (Player) t;
								p.setNextGraphics(new Graphics(2670));
						p.getPackets().sendGameMessage(
								"<col=15ff00>Vorago releases his mental link with you.</col");
							}
						}
						isReflecting = false;
						stop();
						return;
					}
					count++;
				}
			}, 0, 1);
			}
				if (reflectCount < 4 && hp < maxhp*0.28) {
					for (final Entity t : possibleTargets) {
						if (t instanceof Player) {
							final Player p = (Player) t;
					p.getPackets().sendGameMessage("Vorago heals.");
						}
					}
					npc.heal((int) Math.round(maxhp*0.3));
				}
		} else if (Vorago.Phase == 3 && Vorago.StartP3) {//All the Vorago.Phase 3 
			if (P3Count == P3ReflectCount && !isReflecting) {
			if (Vorago.getWeek() == 1 && Utils.random(100) > 50) {//Teamsplit TODO make it actually split the team
			Vorago.setLastReflect(Utils.currentTimeMillis() + 40000);
			P3Count = P3Count + 1;
			for (final Entity t : possibleTargets) {
				if (t instanceof Player) {
					final Player p = (Player) t;
			p.getPackets().sendGameMessage("<col=ff0000>Vorago charges up a massive fire attack, run into your"
					+ " corresponding square to avoid it.</col>");
				}
			}
			GoR = Utils.random(100);
			int RanB = Utils.random(-7, 7);
			int RanC = Utils.random(-7, 7);
			final int RedX = CX+RanC;
			final int RedY = CY+RanC;;
			final int GreenX = CX+RanB;;
			final int GreenY = CY+RanB;;
			World.spawnTemporaryObject(new WorldObject(14182,
					10, 8, RedX, RedY, 0), 5000, false);
			World.spawnTemporaryObject(new WorldObject(14182,
					10, 8, RedX+1, RedY, 0), 5000, false);
			World.spawnTemporaryObject(new WorldObject(14182,
					10, 8, RedX, RedY+1, 0), 5000, false);
					World.spawnTemporaryObject(new WorldObject(14182,
					10, 8, RedX+1, RedY+1, 0), 5000, false);
			World.spawnTemporaryObject(new WorldObject(14181,
					10, 8, GreenX, GreenY, 0), 5000, false);
			World.spawnTemporaryObject(new WorldObject(14181,
					10, 8, GreenX+1, GreenY, 0), 5000, false);
					World.spawnTemporaryObject(new WorldObject(14181,
					10, 8, GreenX, GreenY+1, 0), 5000, false);
				World.spawnTemporaryObject(new WorldObject(14181,
					10, 8, GreenX+1, GreenY+1, 0), 5000, false);
			if (GoR < 50) {//Red
			 for (final Entity t : possibleTargets) {
							if (t instanceof Player) {
								final Player p = (Player) t;
				p.getAppearence().setGlowRed(true);
				p.getAppearence().generateAppearenceData();
				}
			}
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						for (final Entity t : possibleTargets) {
							if (t instanceof Player) {
								final Player p = (Player) t;
				if (p.getX() < RedX-2 || p.getX() > RedX+1 || p.getY() < RedY-1 || p.getY() > RedY+1 ) {
					p.getPackets()
					.sendGameMessage(
							"You didn't make it into the square.");
				int damg = 800;
				delayHit(npc, 1, p,
						getRegularHit(npc, damg));
				} else {
						p.getPackets()
						.sendGameMessage(
								"<col=15ff00>You avoid the damage.</col>");
				}
				npc.setNextGraphics(new Graphics(287));
				p.getAppearence().setGlowRed(false);
						p.getAppearence().generateAppearenceData();
							}
							}
						}
				}, 8);
				return 24;
			} else {//Green
			 for (final Entity t : possibleTargets) {
							if (t instanceof Player) {
								final Player p = (Player) t;
//				p.getAppearence().setGlowGreen(true);
				p.getAppearence().generateAppearenceData();
				}
			}
			
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
					 for (final Entity t : possibleTargets) {
							if (t instanceof Player) {
								final Player p = (Player) t;
				if (p.getX() < GreenX-2 || p.getX() > GreenX+1 || p.getY() < GreenY-1 || p.getY() > GreenY+1 ) {
					p.getPackets()
					.sendGameMessage(
							"You didn't make it into the square.");
				int damg = 800;
				delayHit(npc, 1, p,
						getRegularHit(npc, damg));
				} else {
						p.getPackets()
						.sendGameMessage(
								"<col=15ff00>You avoid the damage.</col>");
				}
				npc.setNextGraphics(new Graphics(287));
//				p.getAppearence().setGlowGreen(false);
						p.getAppearence().generateAppearenceData();}
						}
						}
				}, 8);
				return 24;
			}
			} else if (Vorago.getWeek() == 2) {//Scopulus
				if (spawnScop == false) {
					npc.setCapDamage(0);
					npc.setNextWorldTile(centre);
 					World.spawnNPC(13805, Scop1, -1, true, true);
					World.spawnNPC(13805, Scop2, -1, true, true);
					spawnScop = true;
				} else if (spawnScop == true && ScopDead == true) {
					npc.sendDeath(target);
					npc.setCapDamage(1000);
				}
			} else if (Vorago.getWeek() == 3 && Utils.random(100) > 50) {//Vitalis
				P3Count= P3Count + 1;
				npc.setNextWorldTile(centre);
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
				p.getPackets().sendGameMessage("<col=ff0000>Vorago sends a Vitalis orb!</col>");
					}
				}
					int c = Utils.random(-6, 6);
					int d = Utils.random (-6, 6);
					final int vitX = npc.getX()+d;
					final int vitY = npc.getY()+c;
					World.sendProjectile(npc, new WorldTile(vitX,  vitY, 0), 3201, 0, 0, 15, 10, 0, 0);
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							for (final Entity t : possibleTargets) {
								if (t instanceof Player) {
									final Player p = (Player) t;
					if (p.getX() < vitX-2 || p.getX() > vitX+2 || p.getY() < vitY-2 || p.getY() > vitY+2 ) {
				World.spawnNPC(13807, p, -1, true, true);		
				} else {
									p.getPackets().sendGameMessage("<col=15ff00>You avoid the Vitalis</col>");
								}
							}
							}
						}
					}, 8);
					return 24;
				}
			} else if (P3Count > P3ReflectCount && Vorago.getLastReflect() < Utils.currentTimeMillis()) {//Vorago.Phase 3 reflects
				P3ReflectCount = P3ReflectCount + 1;
				isReflecting = true;
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
				p.getPackets().sendGameMessage(
						"<col=123582>Vorago reflects damage to surrounding foes!</col");
					}
				}				
			WorldTasksManager.schedule(new WorldTask() {
				private int count = 0;
				@Override
				public void run() {
					if (count == 7) {
						for (final Entity t : possibleTargets) {
							if (t instanceof Player) {
								final Player p = (Player) t;
								p.setNextGraphics(new Graphics(2670));
						p.getPackets().sendGameMessage(
								"<col=15ff00>Vorago releases his mental link with you.</col");
							}
						}
						isReflecting = false;
						stop();
						return;
					}
					count++;
				}
			}, 0, 1);
			}
		} else if (Vorago.Phase == 4 && Vorago.StartWfs) {
			if (CloneDead == true && npc.getCapDamage() < 500) {
				npc.setCapDamage(1000);
			}
			if (hp < maxhp*0.28 && wfCount < 3) {
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
					p.getPackets().sendGameMessage("Vorago heals.");
					}
				}
					npc.heal((int) Math.round(maxhp*0.3));
			}
			if (P4Count < StoneDeadCount) {
			P4Count = P4Count + 1;
			Vorago.setLastReflect(Utils.currentTimeMillis() + 30000);
			if (Vorago.getWeek() == 1) {//Teamsplit P4
				npc.setNextWorldTile(centre);
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
				p.getPackets().sendGameMessage("<col=ff0000>Vorago charges up a massive fire attack, run into your"
						+ " corresponding square to avoid it.</col>");
					}
				}
				GoR = Utils.random(100);
				int RanB = Utils.random(-7, 7);
				int RanC = Utils.random(-7, 7);
				final int RedX = CX+RanC;
				final int RedY = CY+RanC;;
				final int GreenX = CX+RanB;;
				final int GreenY = CY+RanB;;
				World.spawnTemporaryObject(new WorldObject(14182,
						10, 8, RedX, RedY, 0), 5000, false);
				World.spawnTemporaryObject(new WorldObject(14182,
						10, 8, RedX+1, RedY, 0), 5000, false);
				World.spawnTemporaryObject(new WorldObject(14182,
						10, 8, RedX, RedY+1, 0), 5000, false);
						World.spawnTemporaryObject(new WorldObject(14182,
						10, 8, RedX+1, RedY+1, 0), 5000, false);
				World.spawnTemporaryObject(new WorldObject(14181,
						10, 8, GreenX, GreenY, 0), 5000, false);
				World.spawnTemporaryObject(new WorldObject(14181,
						10, 8, GreenX+1, GreenY, 0), 5000, false);
						World.spawnTemporaryObject(new WorldObject(14181,
						10, 8, GreenX, GreenY+1, 0), 5000, false);
					World.spawnTemporaryObject(new WorldObject(14181,
						10, 8, GreenX+1, GreenY+1, 0), 5000, false);
				if (GoR < 50) {//Red
				 for (final Entity t : possibleTargets) {
								if (t instanceof Player) {
									final Player p = (Player) t;
					p.getAppearence().setGlowRed(true);
					p.getAppearence().generateAppearenceData();
					}
				}
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							for (final Entity t : possibleTargets) {
								if (t instanceof Player) {
									final Player p = (Player) t;
					if (p.getX() < RedX-2 || p.getX() > RedX+1 || p.getY() < RedY-1 || p.getY() > RedY+1 ) {
						p.getPackets()
						.sendGameMessage(
								"You didn't make it into the square.");
					int damg = 800;
					delayHit(npc, 1, p,
							getRegularHit(npc, damg));
					} else {
							p.getPackets()
							.sendGameMessage(
									"<col=15ff00>You avoid the damage.</col>");
					}
					npc.setNextGraphics(new Graphics(287));
					p.getAppearence().setGlowRed(false);
							p.getAppearence().generateAppearenceData();
								}
								}
							}
					}, 8);
					return 24;
				} else {//Green
				 for (final Entity t : possibleTargets) {
								if (t instanceof Player) {
									final Player p = (Player) t;
//					p.getAppearence().setGlowGreen(true);
					p.getAppearence().generateAppearenceData();
					}
				}
				
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
						 for (final Entity t : possibleTargets) {
								if (t instanceof Player) {
									final Player p = (Player) t;
					if (p.getX() < GreenX-2 || p.getX() > GreenX+1 || p.getY() < GreenY-1 || p.getY() > GreenY+1 ) {
						p.getPackets()
						.sendGameMessage(
								"You didn't make it into the square.");
					int damg = 800;
					delayHit(npc, 1, p,
							getRegularHit(npc, damg));
					} else {
							p.getPackets()
							.sendGameMessage(
									"<col=15ff00>You avoid the damage.</col>");
					}
					npc.setNextGraphics(new Graphics(287));
//					p.getAppearence().setGlowGreen(false);
							p.getAppearence().generateAppearenceData();}
							}
							}
					}, 8);
					return 24;
				}
			} else if (Vorago.getWeek() == 3) {
				npc.setNextWorldTile(centre);
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
				p.getPackets().sendGameMessage("<col=ff0000>Vorago sends a Vitalis orb!</col>");
					}
				}
					int c = Utils.random(-6, 6);
					int d = Utils.random (-6, 6);
					final int vitX = npc.getX()+d;
					final int vitY = npc.getY()+c;
					World.sendProjectile(npc, new WorldTile(vitX,  vitY, 0), 3201, 0, 0, 15, 10, 0, 0);
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							for (final Entity t : possibleTargets) {
								if (t instanceof Player) {
									final Player p = (Player) t;
					if (p.getX() < vitX-2 || p.getX() > vitX+2 || p.getY() < vitY-2 || p.getY() > vitY+2 ) {
				World.spawnNPC(13807, p, -1, true, true);		
				} else {
									p.getPackets().sendGameMessage("<col=15ff00>You avoid the Vitalis</col>");
								}
							}
							}
						}
					}, 8);
					return 24;
				}
			}
			
			
			if (P4Count > P4ReflectCount && Vorago.getLastReflect() < Utils.currentTimeMillis()) {
				isReflecting = true;
				P4ReflectCount = P4ReflectCount + 1;
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
				p.getPackets().sendGameMessage(
						"<col=123582>Vorago reflects damage to surrounding foes!</col");
					}
				}
			WorldTasksManager.schedule(new WorldTask() {
				private int count = 0;
				@Override
				public void run() {
					if (count == 7) {
						for (final Entity t : possibleTargets) {
							if (t instanceof Player) {
								final Player p = (Player) t;
								p.setNextGraphics(new Graphics(2670));
						p.getPackets().sendGameMessage(
								"<col=15ff00>Vorago releases his mental link on you.</col");
							}
						}
						isReflecting = false;
						stop();
						return;
					}
					count++;
				}
			}, 0, 1);
			}
			if (wfCount < 3 && StoneDeadCount == P4ReflectCount && StoneDeadCount == wfCount
					&& Utils.random(50) < 25 && !isReflecting) {//Waterfalls
				wfCount = wfCount+1;
				npc.setNextWorldTile(centre);
				int wfloc = Utils.random(12);
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
				p.getPackets().sendGameMessage(
						"<col=ff0000>Vorago Starts to charge a massive "
						+ "fire attack, run inder the waterfall to avoid it!</col>");
					}
				}
				if (wfloc < 3) {//South west
					World.spawnTemporaryObject(new WorldObject(34915,
						10, 2, 3541, 9495, 0), 8000, false);
				World.spawnTemporaryObject(new WorldObject(34915,
						10, 3, 3544, 9492, 0), 8000, false);
				wfloc2 = 1;
				} else if (wfloc >= 3 && wfloc < 6) {//North West
					World.spawnTemporaryObject(new WorldObject(34915,
						10, 4, 3542, 9507, 0), 8000, false);
				World.spawnTemporaryObject(new WorldObject(34915,
						10, 7, 3545, 9510, 0), 8000, false);
				wfloc2 = 2;
				} else if (wfloc >= 6 && wfloc < 9) { // North East
					World.spawnTemporaryObject(new WorldObject(34915,
						10, 4, 3559, 9508, 0), 8000, false);
				World.spawnTemporaryObject(new WorldObject(34915,
						10, 1, 3556, 9511, 0), 8000, false);
				wfloc2 = 3;
				} else {//South East
					World.spawnTemporaryObject(new WorldObject(34915,
						10, 1, 3557, 9492, 0), 8000, false);
				World.spawnTemporaryObject(new WorldObject(34915,
						10, 6, 3560, 9495, 0), 8000, false);
				wfloc2 = 4;
				}
				WorldTasksManager.schedule(new WorldTask() {
					@Override
					public void run() {
						for (final Entity t : possibleTargets) {
							if (t instanceof Player) {
								final Player p = (Player) t;
					if (wfloc2 == 1) {	//South west
						npc.setNextGraphics(new Graphics(287));
						if (p.getX() < 3540 || p.getX() > 3545 || p.getY() < 9492 || p.getY() > 9496) {
							p.getPackets()
							.sendGameMessage(
									"You didn't make it into the waterfall");
						int damg = 1000;
						delayHit(npc, 1, p,
								getRegularHit(npc, damg));
						} else {
							npc.setNextGraphics(new Graphics(287));
								p.getPackets()
								.sendGameMessage(
										"<col=15ff00>You avoid the damage.</col>");
						}
					} else if (wfloc2 == 2){ //North west
						npc.setNextGraphics(new Graphics(287));
						if (p.getX() < 3540 || p.getX() > 3546 || p.getY() < 9509 || p.getY() > 9514) {
							p.getPackets()
							.sendGameMessage(
									"You didn't make it into the waterfall");
						int damg = 1000;
						delayHit(npc, 1, p,
								getRegularHit(npc, damg));
						} else {
							npc.setNextGraphics(new Graphics(287));
								p.getPackets()
								.sendGameMessage(
										"<col=15ff00>You avoid the damage.</col>");
						}
					} else if (wfloc2 == 3) { //North East
						npc.setNextGraphics(new Graphics(287));
						if (p.getX() < 3558 || p.getX() > 3563 || p.getY() < 9509 || p.getY() > 9515) {
							p.getPackets()
							.sendGameMessage(
									"You didn't make it into the waterfall");
						int damg = 1000;
						delayHit(npc, 1, p,
								getRegularHit(npc, damg));
						} else {
							npc.setNextGraphics(new Graphics(287));
								p.getPackets()
								.sendGameMessage(
										"<col=15ff00>You avoid the damage.</col>");
						}
					} else if (wfloc2 == 4) {//South east
						npc.setNextGraphics(new Graphics(287));
						if (p.getX() < 3559 || p.getX() > 3563 || p.getY() < 9491 || p.getY() > 9496) {
							p.getPackets()
							.sendGameMessage(
									"You didn't make it into the waterfall");
						int damg = 1000;
						delayHit(npc, 1, p,
								getRegularHit(npc, damg));
						} else {
							npc.setNextGraphics(new Graphics(287));
								p.getPackets()
								.sendGameMessage(
										"<col=15ff00>You avoid the damage.</col>");
						}
						}
				if (wfCount == 3) {
					p.getPackets().sendGameMessage("<col=19FF00>The weapon part becomes loose"
							+ " and hits the floor!</col>");
					
				} else {
				p.getPackets().sendGameMessage(
						"<col=ff7400>Part of the weapon loosens as Vorago unleashes the attack!</col>");
				}
					}
					}
				if (wfCount == 3) {
					World.addGroundItem(new Item(29604), centre);
				}
				SpawnClone();
				npc.setCapDamage(40);
				}
			}, 8);
			return 48;
			}
		}
		
		if (Vorago.Phase == 5 && isMauling) {
			npc.setCantInteract(true);
		}
		if (Vorago.Phase == 5 && !CanMaul) {
			int pb1 = 9500;
			if (hp < maxhp*0.92 && hp >= maxhp*.084) {
				npc.setNextWorldTile(new WorldTile(3552, pb1, 0));
			} else if (hp < maxhp*0.84 && hp >= maxhp*0.76) {
				npc.setNextWorldTile(new WorldTile(3552, pb1+1, 0));
			} else if (hp < maxhp*0.76 && hp >= maxhp*0.68) {
				npc.setNextWorldTile(new WorldTile(3552, pb1+2, 0));
			} else if (hp < maxhp*0.68 && hp >= maxhp*0.60) {
				npc.setNextWorldTile(new WorldTile(3552, pb1+3, 0));
			} else if (hp < maxhp*0.60 && hp >= maxhp*0.52) {
				npc.setNextWorldTile(new WorldTile(3552, pb1+4, 0));
			} else if (hp < maxhp*0.52 && hp >= maxhp*0.44) {
				npc.setNextWorldTile(new WorldTile(3552, pb1+5, 0));
			} else if (hp < maxhp*0.44 && hp >= maxhp*0.36) {
				npc.setNextWorldTile(new WorldTile(3552, pb1+6, 0));
			} else if (hp < maxhp*0.36 && hp >= maxhp*0.28) {
				npc.setNextWorldTile(new WorldTile(3552, pb1+7, 0));
			} else if (hp < maxhp*0.20) {
				CanMaul = true;
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
				p.getPackets().sendGameMessage("Only the Maul of Omens is enough to destroy him now!");
					}
				}
				npc.setNextWorldTile(new WorldTile(3552, pb1+8, 0));
				npc.setCapDamage(0);
			}
			if (Vorago.getLastP5() < Utils.currentTimeMillis() && P5Count == P5ReflectCount && !isReflecting) {
			P5Count = P5Count + 1;
			Vorago.setLastReflect(Utils.currentTimeMillis() + 30000);
			if (Vorago.getWeek() == 1) {//TeamSplit p5
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
				p.getPackets().sendGameMessage("<col=ff0000>Vorago charges up a massive fire attack, run into your"
						+ " corresponding square to avoid it.</col>");
					}
				}
				if (hp > maxhp*0.67) {
					GoR = Utils.random(100);
					final int RedX5 = 3553;
					final int RedY5 = 9493;
					final int GreenX5 = 3550;
					final int GreenY5 = 9493;
					World.spawnTemporaryObject(new WorldObject(14182,
							10, 8, RedX5, RedY5, 0), 5000, false);
					World.spawnTemporaryObject(new WorldObject(14182,
							10, 8, RedX5+1, RedY5, 0), 5000, false);
					World.spawnTemporaryObject(new WorldObject(14182,
							10, 8, RedX5, RedY5+1, 0), 5000, false);
							World.spawnTemporaryObject(new WorldObject(14182,
							10, 8, RedX5+1, RedY5+1, 0), 5000, false);
					World.spawnTemporaryObject(new WorldObject(14181,
							10, 8, GreenX5, GreenY5, 0), 5000, false);
					World.spawnTemporaryObject(new WorldObject(14181,
							10, 8, GreenX5+1, GreenY5, 0), 5000, false);
							World.spawnTemporaryObject(new WorldObject(14181,
							10, 8, GreenX5, GreenY5+1, 0), 5000, false);
						World.spawnTemporaryObject(new WorldObject(14181,
							10, 8, GreenX5+1, GreenY5+1, 0), 5000, false);
					if (GoR < 50) {//Red
						for (final Entity t : possibleTargets) {
							if (t instanceof Player) {
								final Player p = (Player) t;
						p.getAppearence().setGlowRed(true);
						p.getAppearence().generateAppearenceData();
							}
						}
						WorldTasksManager.schedule(new WorldTask() {
							@Override
							public void run() {
								for (final Entity t : possibleTargets) {
									if (t instanceof Player) {
										final Player p = (Player) t;
						if (p.getX() < RedX5-2 || p.getX() > RedX5+1 || p.getY() < RedY5-1 || p.getY() > RedY5+1 ) {
							p.getPackets()
							.sendGameMessage(
									"You didn't make it into the square.");
						int damg = 800;
						delayHit(npc, 1, p,
								getRegularHit(npc, damg));
						} else {
								p.getPackets()
								.sendGameMessage(
										"<col=15ff00>You avoid the damage.</col>");
						}
						npc.setNextGraphics(new Graphics(287));
						p.getAppearence().setGlowRed(false);
								p.getAppearence().generateAppearenceData();
							}
						}
								}
						}, 8);
						return 24;
					} else {//Green
						for (final Entity t : possibleTargets) {
							if (t instanceof Player) {
								final Player p = (Player) t;
//						p.getAppearence().setGlowGreen(true);
						p.getAppearence().generateAppearenceData();
							}
						}
						WorldTasksManager.schedule(new WorldTask() {
							@Override
							public void run() {
								for (final Entity t : possibleTargets) {
									if (t instanceof Player) {
										final Player p = (Player) t;
						if (p.getX() < GreenX5-2 || p.getX() > GreenX5+1 || p.getY() < GreenY5-1 || p.getY() > GreenY5+1 ) {
							p.getPackets()
							.sendGameMessage(
									"You didn't make it into the square.");
						int damg = 800;
						delayHit(npc, 1, p,
								getRegularHit(npc, damg));
						} else {
								p.getPackets()
								.sendGameMessage(
										"<col=15ff00>You avoid the damage.</col>");
						}
						npc.setNextGraphics(new Graphics(287));
//						p.getAppearence().setGlowGreen(false);
								p.getAppearence().generateAppearenceData();
							}
						}
							}
						}, 8);
						return 24;
					}
					} else {
						GoR = Utils.random(100);
						final int RedX5 = 3551;
						final int RedY5 = 9497;
						final int GreenX5 = 3551;
						final int GreenY5 = 9500;
						World.spawnTemporaryObject(new WorldObject(14182,
								10, 8, RedX5, RedY5, 0), 5000, false);
						World.spawnTemporaryObject(new WorldObject(14182,
								10, 8, RedX5+1, RedY5, 0), 5000, false);
						World.spawnTemporaryObject(new WorldObject(14182,
								10, 8, RedX5, RedY5+1, 0), 5000, false);
								World.spawnTemporaryObject(new WorldObject(14182,
								10, 8, RedX5+1, RedY5+1, 0), 5000, false);
						World.spawnTemporaryObject(new WorldObject(14181,
								10, 8, GreenX5, GreenY5, 0), 5000, false);
						World.spawnTemporaryObject(new WorldObject(14181,
								10, 8, GreenX5+1, GreenY5, 0), 5000, false);
								World.spawnTemporaryObject(new WorldObject(14181,
								10, 8, GreenX5, GreenY5+1, 0), 5000, false);
							World.spawnTemporaryObject(new WorldObject(14181,
								10, 8, GreenX5+1, GreenY5+1, 0), 5000, false);
						if (GoR == 1) {//Red
							for (final Entity t : possibleTargets) {
								if (t instanceof Player) {
									final Player p = (Player) t;
							p.getAppearence().setGlowRed(true);
							p.getAppearence().generateAppearenceData();
								}
							}
							WorldTasksManager.schedule(new WorldTask() {
								@Override
								public void run() {
									for (final Entity t : possibleTargets) {
										if (t instanceof Player) {
											final Player p = (Player) t;
							if (p.getX() < RedX5-2 || p.getX() > RedX5+1 || p.getY() < RedY5-1 || p.getY() > RedY5+1 ) {
								p.getPackets()
								.sendGameMessage(
										"You didn't make it into the square.");
							int damg = 800;
							delayHit(npc, 1, p,
									getRegularHit(npc, damg));
							} else {
									p.getPackets()
									.sendGameMessage(
											"<col=15ff00>You avoid the damage.</col>");
							}
							npc.setNextGraphics(new Graphics(287));
							p.getAppearence().setGlowRed(false);
									p.getAppearence().generateAppearenceData();
										}
										}
									}
							}, 8);
							return 24;
						} else {//Green
							for (final Entity t : possibleTargets) {
								if (t instanceof Player) {
									final Player p = (Player) t;
						
//							p.getAppearence().setGlowGreen(true);
							p.getAppearence().generateAppearenceData();
								}
							}
							WorldTasksManager.schedule(new WorldTask() {
								@Override
								public void run() {
									for (final Entity t : possibleTargets) {
										if (t instanceof Player) {
											final Player p = (Player) t;
							if (p.getX() < GreenX5-2 || p.getX() > GreenX5+1 || p.getY() < GreenY5-1 || p.getY() > GreenY5+1 ) {
								p.getPackets()
								.sendGameMessage(
										"You didn't make it into the square.");
							int damg = 800;
							delayHit(npc, 1, p,
									getRegularHit(npc, damg));
							} else {
									p.getPackets()
									.sendGameMessage(
											"<col=15ff00>You avoid the damage.</col>");
							}
							npc.setNextGraphics(new Graphics(287));
//							p.getAppearence().setGlowGreen(false);
									p.getAppearence().generateAppearenceData();
										}
									}
								}
							}, 8);
							return 24;
						}
					}
			} else if (Vorago.getWeek() == 3) {
				final int vitX5;
				final int vitY5;
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
				p.getPackets().sendGameMessage("<col=ff0000>Vorago sends a Vitalis orb!</col>");
					}
				}
					int c = Utils.random(3, 7);
					int d = Utils.random (-2, 2);
					vitX5 = npc.getX()+d;
					vitY5 = npc.getY()+c;
					World.sendProjectile(npc, new WorldTile(vitX5,  vitY5, 0), 3201, 0, 0, 15, 10, 0, 0);
					WorldTasksManager.schedule(new WorldTask() {
						@Override
						public void run() {
							for (final Entity t : possibleTargets) {
								if (t instanceof Player) {
									final Player p = (Player) t;
					if (p.getX() < vitX5-2 || p.getX() > vitX5+2 || p.getY() < vitY5-2 || p.getY() > vitY5+2 ) {
				World.spawnNPC(13807, p, -1, true, true);		
				} else {
									p.getPackets().sendGameMessage("Vitalis avoided.");
								}
							
						}
								}
							}
					}, 8);
					return 24;
			}
		}
			if (P5Count > P5ReflectCount && Utils.currentTimeMillis() > Vorago.getLastReflect()) {//Reflects p5
				P5ReflectCount = P5ReflectCount+1;
				isReflecting = true;
				for (final Entity t : possibleTargets) {
					if (t instanceof Player) {
						final Player p = (Player) t;
				p.getPackets().sendGameMessage(
						"<col=123582>Vorago reflects damage to surrounding foes!</col");
					}
				}
				Vorago.setLastP5(Utils.currentTimeMillis() + 30000);	
			WorldTasksManager.schedule(new WorldTask() {
				private int count = 0;
				@Override
				public void run() {
					for (final Entity t : possibleTargets) {
						if (t instanceof Player) {
							final Player p = (Player) t;
					if (count == 7) {
								p.setNextGraphics(new Graphics(2670));
						p.getPackets().sendGameMessage(
								"<col=15ff00>Vorago releases his mental link on you.</col");
						isReflecting = false;
						stop();
						return;
					}
					count++;
				}
						}
					}
			}, 0, 1);
			}
		}
		
		return defs.getAttackDelay();
	}



}