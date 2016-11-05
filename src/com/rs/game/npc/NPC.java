package com.rs.game.npc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import com.rs.Settings;
import com.rs.cache.loaders.ItemDefinitions;
import com.rs.cache.loaders.NPCDefinitions;
import com.rs.cores.CoresManager;
import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.ForceTalk;
import com.rs.game.Graphics;
import com.rs.game.Hit;
import com.rs.game.Hit.HitLook;
import com.rs.game.SecondaryBar;
import com.rs.game.World;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.minigames.zombies.Zombies;
import com.rs.game.npc.combat.NPCCombat;
import com.rs.game.npc.combat.NPCCombatDefinitions;
import com.rs.game.npc.familiar.Familiar;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.SlayerManager;
import com.rs.game.player.actions.HerbCleaning;
import com.rs.game.player.content.Assassins;
import com.rs.game.player.content.Burying;
import com.rs.game.player.content.Deaths;
import com.rs.game.player.content.FriendChatsManager;
import com.rs.game.player.controlers.FightCaves;
import com.rs.game.player.controlers.FightKiln;
import com.rs.game.player.controlers.WGuildControler;
import com.rs.game.player.controlers.Wilderness;
import com.rs.game.player.controlers.darkinvasion.DarkInvasion;
import com.rs.game.player.controlers.dung.RuneDungGame;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Logger;
import com.rs.utils.MapAreas;
import com.rs.utils.Misc;
import com.rs.utils.NPCBonuses;
import com.rs.utils.NPCCombatDefinitionsL;
import com.rs.utils.NPCDrops;
import com.rs.utils.Utils;

public class NPC extends Entity implements Serializable {

	private static final long serialVersionUID = -4794678936277614443L;

	private int id;
	private WorldTile respawnTile;
	private int mapAreaNameHash;
	private boolean canBeAttackFromOutOfArea;
	private boolean randomwalk;
	private int[] bonuses; // 0 stab, 1 slash, 2 crush,3 mage, 4 range, 5 stab
	// def, blahblah till 9
	private boolean spawned;
	private transient NPCCombat combat;
	public WorldTile forceWalk;
	public int whatToSay = 0;
	private long lastAttackedByTarget;
	private boolean cantInteract;
	private int capDamage;
	private int lureDelay;
	private boolean cantFollowUnderCombat;
	private boolean forceAgressive;
	private int forceTargetDistance;
	private boolean forceFollowClose;
	private boolean forceMultiAttacked;
	private boolean noDistanceCheck;

	// npc masks
	private transient Transformation nextTransformation;
	private transient SecondaryBar nextSecondaryBar;
	//name changing masks
	private String name;
	private transient boolean changedName;
	private int combatLevel;
	private transient boolean changedCombatLevel;
	private transient boolean locked;
	
	public NPC(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea) {
		this(id, tile, mapAreaNameHash, canBeAttackFromOutOfArea, false);
	}
	

	/*
	 * creates and adds npc
	 */
	public NPC(int id, WorldTile tile, int mapAreaNameHash,
			boolean canBeAttackFromOutOfArea, boolean spawned) {
		super(tile);
		this.id = id;
		this.respawnTile = new WorldTile(tile);
		this.mapAreaNameHash = mapAreaNameHash;
		this.canBeAttackFromOutOfArea = canBeAttackFromOutOfArea;
		this.setSpawned(spawned);
		combatLevel = -1;
		setHitpoints(getMaxHitpoints());
		setDirection(getRespawnDirection());
		for (int i : Settings.NON_WALKING_NPCS1) {
            if (i == id)
                setRandomWalk(false);
            else
                setRandomWalk((getDefinitions().walkMask & 0x2) != 0
                        || forceRandomWalk(id));
        }
		bonuses = NPCBonuses.getBonuses(id);
		combat = new NPCCombat(this);
		capDamage = -1;
		lureDelay = 12000;
		// npc is inited on creating instance
		initEntity();
		World.addNPC(this);
		World.updateEntityRegion(this);
		// npc is started on creating instance
		loadMapRegions();
		checkMultiArea();
	}

	@Override
	public boolean needMasksUpdate() {
		return super.needMasksUpdate() || nextSecondaryBar != null || nextTransformation != null || changedCombatLevel || changedName;
	}

	public void transformIntoNPC(int id) {
		setNPC(id);
		nextTransformation = new Transformation(id);
	}
	
	public void setNextNPCTransformation(int id) {
		setNPC(id);
		nextTransformation = new Transformation(id);
		if (getCustomCombatLevel() != -1)
		    changedCombatLevel = true;
		if (getCustomName() != null)
		    changedName = true;
	    }
	
	public static void moo() {
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				String[] mooing = { "Moo", "Moooo", "MOOOOOOOOO", "derp", "Mooooooooo", "Neigh" };
				int i = Misc.random(1, 5);
				for (NPC n : World.getNPCs()) {
					if (!n.getName().equalsIgnoreCase("Cow")) {
						continue;
					}
					n.setNextForceTalk(new ForceTalk(mooing[i]));
				}
			}
		}, 0, 5); //time in seconds
	}

	public void setNPC(int id) {
		this.id = id;
		bonuses = NPCBonuses.getBonuses(id);
	}

	@Override
	public void resetMasks() {
		super.resetMasks();
		nextTransformation = null;
		changedCombatLevel = false;
		changedName = false;
		nextSecondaryBar = null;
	}

	public int getMapAreaNameHash() {
		return mapAreaNameHash;
	}

	public void setCanBeAttackFromOutOfArea(boolean b) {
		canBeAttackFromOutOfArea = b;
	}
	
	public boolean canBeAttackFromOutOfArea() {
		return canBeAttackFromOutOfArea;
	}
	
	public void setBonuses(int[] bonuses) {
		this.bonuses = bonuses;
	}

	public NPCDefinitions getDefinitions() {
		return NPCDefinitions.getNPCDefinitions(id);
	}

	public NPCCombatDefinitions getCombatDefinitions() {
		return NPCCombatDefinitionsL.getNPCCombatDefinitions(id);
	}

	@Override
	public int getMaxHitpoints() {
		return getCombatDefinitions().getHitpoints();
	}

	public int getId() {
		return id;
	}

	public void processNPC() {
		if (isDead() || locked)
			return;
		if (!combat.process()) { // if not under combat
			if (!isForceWalking()) {// combat still processed for attack delay
				// go down
				// random walk
				if (!cantInteract) {
					if (!checkAgressivity()) {
						if (getFreezeDelay() < Utils.currentTimeMillis()) {
							if (((hasRandomWalk()) && World.getRotation(
									getPlane(), getX(), getY()) == 0) // temporary
									// fix
									&& Math.random() * 1000.0 < 100.0) {
								int moveX = (int) Math
										.round(Math.random() * 10.0 - 5.0);
								int moveY = (int) Math
										.round(Math.random() * 10.0 - 5.0);
								resetWalkSteps();
								if (getMapAreaNameHash() != -1) {
									if (!MapAreas.isAtArea(getMapAreaNameHash(), this)) {
										forceWalkRespawnTile();
										return;
									}
									addWalkSteps(getX() + moveX, getY() + moveY, 5);
								}else 
									addWalkSteps(respawnTile.getX() + moveX, respawnTile.getY() + moveY, 5);
							}
						}
					}
				}
			}
		}
		if(id == 6137 && Utils.random(10) == 0){
			setNextAnimation(new Animation(6083));
			if(whatToSay == 0){
				setNextForceTalk(new ForceTalk("Come try your luck agaisnt the Dreadnaut!"));
				whatToSay = 1;
			}else{
				setNextForceTalk(new ForceTalk("Kill the Dreadnaut and get your very own fighter torso!"));
				whatToSay = 0;
			}
		}
		if(id == 3709 && Utils.random(10) == 0){
			if(whatToSay == 0){
				setNextForceTalk(new ForceTalk("Hey over here! Come spend your points!!"));
				whatToSay = 1;
			}else{
				setNextForceTalk(new ForceTalk("I dont have something you need? Do ::shops!"));
				whatToSay = 0;
			}
		}
		if(id == 12192 && Utils.random(25)==0){
			setNextForceTalk(new ForceTalk("Hey buddy, come buy some runes! The first one is always free."));
		}
		if(id == 12306 && Utils.random(25)==0){
			setNextForceTalk(new ForceTalk("Increase your defenses!!"));
		}
		if(id == 13172 && Utils.random(25)==0){
			setNextForceTalk(new ForceTalk(" One does not simply cast with armour, try my wares for increased efficiency!!"));
		}
		if(id == 1847 && Utils.random(25)==0){
			setNextForceTalk(new ForceTalk("Hi. I'm a dwarf. So obviously I sell pickaxes."));
		}
		if(id == 13464 && Utils.random(25)==0){
			setNextForceTalk(new ForceTalk("Weapons of all shapes, sizes, colors, and anchors!!"));
		}
		if(id == 14160 && Utils.random(25)==0){
			setNextForceTalk(new ForceTalk("Fishbowls, frog heads, and camel masks, what more can you ask for?!"));
		}
		if(id == 15151 && Utils.random(25) == 0){
			setNextForceTalk(new ForceTalk("Hands up! Buy something or I swear I'll shoot!!"));
		}
		if(id == 15582 && Utils.random(25)==0){
			setNextForceTalk(new ForceTalk("If you're lookin' for swag, I'm sure I have what you need."));
		}
		if(id == 3547 && Utils.random(25)==0){
			setNextForceTalk(new ForceTalk("Donate gold to me, and I can change the worlds experience rate!."));
		}
		//Changing npc combat levels
		if(id == 5219){
			setCombatLevel(125);
		}
		if(id == 5218){
			setCombatLevel(110);
		}
		 if (id == 15581) {//Party Demon
			 setCombatLevel(1500);
	        }
		 if (id == 3064) {//Lesser Demon Champion
			 setCombatLevel(900);
	        }
		 if (id == 3058) {//Giant Champion
			 setCombatLevel(800);
	        }
		 if (id == 3063) {//Jogre Champion
			 setCombatLevel(850);
	        }
		 if (id == 15187) {//TokHaar-Ket Champion
			 setCombatLevel(1100);
	        }
		 if (id == 10495) {//High level Lesser Demon
			 setCombatLevel(550);
	        }
		 if (id == 4706) {//High Level Moss giant
			 setCombatLevel(600);
	        }
		 if (id == 10769) {//High level ice giant
			 setCombatLevel(650);
	        }
		 if (id == 10717) {//High level Hill giant
			 setCombatLevel(550);
	        }
		 if (id == 10761) {//High level Fire giant
			 setCombatLevel(680);
	        }
		 if (id == 3450) {//High level Jogre
			 setCombatLevel(600);
	        }
		 if (id == 999) {//Doomion
			 setCombatLevel(900);
	        }
		 if (id == 998) {//Othainian
			 setCombatLevel(900);
	        }
		 if (id == 1000) {//Holthion
			 setCombatLevel(900);
	        }
		 if (id == 14550) {//Chronozon
			 setCombatLevel(950);
	        }
		 if (id == 14503) {//Agrith Naar
			 setCombatLevel(850);
	        }
		 if (id == 9356) {//Agrith Naar
			 setCombatLevel(100);
	        }
		//Renaming basic npcs
		 if (id == 3547) {
			 setRandomWalk(false);
	        }
		 if (id == 578) {
			 setRandomWalk(false);
	           setName("Herblore Shop");
	        }
		 if (id == 2732) {
			 setName("Crafting Shop");
	           setRandomWalk(false);
		 }
		 if (id == 220) {
			 
	           setName("Donator Points Shop");
	        }
		 if (id == 541) {
			 
	           setName("Donator Points Shop 2");
	        }
		 if (id == 11254) {
			 setRandomWalk(false);
	           setName("Consumables Shop");
	        }
		 if (id == 13324) {
			 setRandomWalk(false);
	           setName("Pets Shop");
	        }
		 if (id == 455) {
			 setRandomWalk(false);
	           setName("Herblore Shop");
	        }
		 if (id == 6539) {		 
	           setName("Level Reset");
	           setRandomWalk(false);
	        }
		 if(id == 12862){
			 setRandomWalk(true);
		 }
		 if(id == 11882){
			 setRandomWalk(true);
		 }
		 if (id == 8031) {		 
	           setName("Dark Invasion");
	           setRandomWalk(false);
	        }
		 
		 
		 if (id == 2262) {		 
	           setName("Prestige");
	        }
		 
		 if (id == 2262) {		 
	           setName("Prestige");
	        }
		 
		 if (id == 3709) {		 
	           setName("Point Shop");
	        }
		 
		 if (id == 1917) {		 
	           setName("Player Owned Shops");
	        }
		// new shop npcs
		 if(id == 564){
			 setName("Master Capes");
			 setRandomWalk(false);
		 }
				 if (id == 12192) {		 
			           setName("Runes Shop");
			           setRandomWalk(false);
			        }
				 if (id == 12306) {		 
			           setName("Armour Shop");
			           setRandomWalk(false);
			        }
				 if (id == 13172) {		 
			           setName("Magic Shop");
			           setRandomWalk(false);
			        }
				 if (id == 1847) {		 
			           setName("Mining Shop");
			           setRandomWalk(false);
			        }
				 if (id == 13464) {		 
			           setName("Weapons Shop");
			           setRandomWalk(false);
			        }
				 if (id == 13789) {		 
			           setName("Skilling Shop");
			           setRandomWalk(false);
			        }
				 if (id == 14160) {		 
			           setName("Pure's Shop");
			           setRandomWalk(false);
			        }
				 if (id == 15151) {		 
			           setName("Archer Shop");
			           setRandomWalk(false);
			        }
				 if (id == 15582) {		 
			           setName("Outfit Shop");
			           setRandomWalk(false);
			        }
				 if (id == 13389) {		 
			           setName("Donator Shop");
			           setRandomWalk(false);
			        }
				 if (id == 576) {		 
			           setName("Fishing Shop");
			           setRandomWalk(false);
			        }
		 //edit npc's movement
		
		if (isForceWalking()) {
			if (getFreezeDelay() < Utils.currentTimeMillis()) {
				if (getX() != forceWalk.getX() || getY() != forceWalk.getY()) {
					if (!hasWalkSteps())
						addWalkSteps(forceWalk.getX(), forceWalk.getY(),
								getSize(), true);
					if (!hasWalkSteps()) { // failing finding route
						setNextWorldTile(new WorldTile(forceWalk)); // force
						// tele
						// to
						// the
						// forcewalk
						// place
						forceWalk = null; // so ofc reached forcewalk place
					}
				} else
					// walked till forcewalk place
					forceWalk = null;
			}
		}
	}

	@Override
	public void processEntity() {
		super.processEntity();
		processNPC();
	}

	public int getRespawnDirection() {
		NPCDefinitions definitions = getDefinitions();
		if (definitions.anInt853 << 32 != 0 && definitions.respawnDirection > 0
				&& definitions.respawnDirection <= 8)
			return (4 + definitions.respawnDirection) << 11;
		return 0;
	}

	/*
	 * forces npc to random walk even if cache says no, used because of fake
	 * cache information
	 */
	private static boolean forceRandomWalk(int npcId) {
		switch (npcId) {
		case 11226:
			return true;
		case 3341:
		case 3342:
		case 3343:
			return true;
		default:
			return false;
			/*
			 * default: return NPCDefinitions.getNPCDefinitions(npcId).name
			 * .equals("Icy Bones");
			 */
		}
	}
	
	public void sendSoulSplit(final Hit hit, final Entity user) {
		final NPC target = this;
		if (hit.getDamage() > 0)
			World.sendProjectile(user, this, 2263, 11, 11, 20, 5, 0, 0);
		user.heal(hit.getDamage() / 5);
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				setNextGraphics(new Graphics(2264));
				if (hit.getDamage() > 0)
					World.sendProjectile(target, user, 2263, 11, 11, 20, 5, 0,
							0);
			}
		}, 1);
	}

	@Override
	public void handleIngoingHit(final Hit hit) {
		if (capDamage != -1 && hit.getDamage() > capDamage)
			hit.setDamage(capDamage);
		if (hit.getLook() != HitLook.MELEE_DAMAGE
				&& hit.getLook() != HitLook.RANGE_DAMAGE
				&& hit.getLook() != HitLook.MAGIC_DAMAGE)
			return;
		Entity source = hit.getSource();
		if (source == null)
			return;
		if (source instanceof Player) {
			final Player p2 = (Player) source;
			if (p2.getPrayer().hasPrayersOn()) {
				if (p2.getPrayer().usingPrayer(1, 18)) 
					sendSoulSplit(hit, p2);
				if (hit.getDamage() == 0)
					return;
				if (!p2.getPrayer().isBoostedLeech()) {
					if (hit.getLook() == HitLook.MELEE_DAMAGE) {
						if (p2.getPrayer().usingPrayer(1, 19)) {
							p2.getPrayer().setBoostedLeech(true);
							return;
						} else if (p2.getPrayer().usingPrayer(1, 1)) { // sap
							// att
							if (Utils.getRandom(4) == 0) {
								if (p2.getPrayer().reachedMax(0)) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your sap curse has no effect.",
											true);
								} else {
									p2.getPrayer().increaseLeechBonus(0);
									p2.getPackets()
									.sendGameMessage(
											"Your curse drains Attack from the enemy, boosting your Attack.",
											true);
								}
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2214));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2215, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2216));
									}
								}, 1);
								return;
							}
						} else {
							if (p2.getPrayer().usingPrayer(1, 10)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.getPrayer().reachedMax(3)) {
										p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your leech curse has no effect.",
												true);
									} else {
										p2.getPrayer().increaseLeechBonus(3);
										p2.getPackets()
										.sendGameMessage(
												"Your curse drains Attack from the enemy, boosting your Attack.",
												true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.getPrayer().setBoostedLeech(true);
									World.sendProjectile(p2, this, 2231, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2232));
										}
									}, 1);
									return;
								}
							}
							if (p2.getPrayer().usingPrayer(1, 14)) {
								if (Utils.getRandom(7) == 0) {
									if (p2.getPrayer().reachedMax(7)) {
										p2.getPackets()
										.sendGameMessage(
												"Your opponent has been weakened so much that your leech curse has no effect.",
												true);
									} else {
										p2.getPrayer().increaseLeechBonus(7);
										p2.getPackets()
										.sendGameMessage(
												"Your curse drains Strength from the enemy, boosting your Strength.",
												true);
									}
									p2.setNextAnimation(new Animation(12575));
									p2.getPrayer().setBoostedLeech(true);
									World.sendProjectile(p2, this, 2248, 35,
											35, 20, 5, 0, 0);
									WorldTasksManager.schedule(new WorldTask() {
										@Override
										public void run() {
											setNextGraphics(new Graphics(2250));
										}
									}, 1);
									return;
								}
							}

						}
					}
					if (hit.getLook() == HitLook.RANGE_DAMAGE) {
						if (p2.getPrayer().usingPrayer(1, 2)) { // sap range
							if (Utils.getRandom(4) == 0) {
								if (p2.getPrayer().reachedMax(1)) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your sap curse has no effect.",
											true);
								} else {
									p2.getPrayer().increaseLeechBonus(1);
									p2.getPackets()
									.sendGameMessage(
											"Your curse drains Range from the enemy, boosting your Range.",
											true);
								}
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2217));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2218, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2219));
									}
								}, 1);
								return;
							}
						} else if (p2.getPrayer().usingPrayer(1, 11)) {
							if (Utils.getRandom(7) == 0) {
								if (p2.getPrayer().reachedMax(4)) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your leech curse has no effect.",
											true);
								} else {
									p2.getPrayer().increaseLeechBonus(4);
									p2.getPackets()
									.sendGameMessage(
											"Your curse drains Range from the enemy, boosting your Range.",
											true);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2236, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2238));
									}
								});
								return;
							}
						}
					}
					if (hit.getLook() == HitLook.MAGIC_DAMAGE) {
						if (p2.getPrayer().usingPrayer(1, 3)) { // sap mage
							if (Utils.getRandom(4) == 0) {
								if (p2.getPrayer().reachedMax(2)) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your sap curse has no effect.",
											true);
								} else {
									p2.getPrayer().increaseLeechBonus(2);
									p2.getPackets()
									.sendGameMessage(
											"Your curse drains Magic from the enemy, boosting your Magic.",
											true);
								}
								p2.setNextAnimation(new Animation(12569));
								p2.setNextGraphics(new Graphics(2220));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2221, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2222));
									}
								}, 1);
								return;
							}
						} else if (p2.getPrayer().usingPrayer(1, 12)) {
							if (Utils.getRandom(7) == 0) {
								if (p2.getPrayer().reachedMax(5)) {
									p2.getPackets()
									.sendGameMessage(
											"Your opponent has been weakened so much that your leech curse has no effect.",
											true);
								} else {
									p2.getPrayer().increaseLeechBonus(5);
									p2.getPackets()
									.sendGameMessage(
											"Your curse drains Magic from the enemy, boosting your Magic.",
											true);
								}
								p2.setNextAnimation(new Animation(12575));
								p2.getPrayer().setBoostedLeech(true);
								World.sendProjectile(p2, this, 2240, 35, 35,
										20, 5, 0, 0);
								WorldTasksManager.schedule(new WorldTask() {
									@Override
									public void run() {
										setNextGraphics(new Graphics(2242));
									}
								}, 1);
								return;
							}
						}
					}

					// overall

					if (p2.getPrayer().usingPrayer(1, 13)) { // leech defence
						if (Utils.getRandom(10) == 0) {
							if (p2.getPrayer().reachedMax(6)) {
								p2.getPackets()
								.sendGameMessage(
										"Your opponent has been weakened so much that your leech curse has no effect.",
										true);
							} else {
								p2.getPrayer().increaseLeechBonus(6);
								p2.getPackets()
								.sendGameMessage(
										"Your curse drains Defence from the enemy, boosting your Defence.",
										true);
							}
							p2.setNextAnimation(new Animation(12575));
							p2.getPrayer().setBoostedLeech(true);
							World.sendProjectile(p2, this, 2244, 35, 35, 20, 5,
									0, 0);
							WorldTasksManager.schedule(new WorldTask() {
								@Override
								public void run() {
									setNextGraphics(new Graphics(2246));
								}
							}, 1);
							return;
						}
					}
				}
			}
		}

	}

	@Override
	public void reset() {
		super.reset();
		setDirection(getRespawnDirection());
		combat.reset();
		bonuses = NPCBonuses.getBonuses(id); // back to real bonuses
		forceWalk = null;
	}

	@Override
	public void finish() {
		if (hasFinished())
			return;
		setFinished(true);
		World.updateEntityRegion(this);
		World.removeNPC(this);
		setRespawnTask();
	}

	public void setRespawnTask() {
		if (!hasFinished()) {
			reset();
			setLocation(respawnTile);
			finish();
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					spawn();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, getCombatDefinitions().getRespawnDelay() * 600,
		TimeUnit.MILLISECONDS);
	}
	public void setLongRespawnTask() {
		if (!hasFinished()) {
			reset();
			setLocation(respawnTile);
			finish();
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					spawn();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, getCombatDefinitions().getRespawnDelay() * 2000,
		TimeUnit.MILLISECONDS);
	}
	public void removespring() {
		if (!hasFinished()) {
			World.removeNPC(null);
			finish();
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					//spawn();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, getCombatDefinitions().getRespawnDelay() * 2000,
		TimeUnit.MILLISECONDS);
	}
	public void setLongerRespawnTask() {
		if (!hasFinished()) {
			reset();
			setLocation(respawnTile);
			finish();
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					spawn();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, getCombatDefinitions().getRespawnDelay() * 5000,
		TimeUnit.MILLISECONDS);
	}
	public void setremovenpcTask() {
		if (!hasFinished()) {
			reset();
			setLocation(respawnTile);
			finish();
		}
		CoresManager.slowExecutor.schedule(new Runnable() {
			@Override
			public void run() {
				try {
					spawn();
				} catch (Throwable e) {
					Logger.handle(e);
				}
			}
		}, getCombatDefinitions().getRespawnDelay() * -1,
		TimeUnit.MILLISECONDS);
	}
	public void deserialize() {
		if (combat == null)
			combat = new NPCCombat(this);
		spawn();
	}

	public void spawn() {
		setFinished(false);
		World.addNPC(this);
		setLastRegionId(0);
		World.updateEntityRegion(this);
		loadMapRegions();
		checkMultiArea();
	}

	public NPCCombat getCombat() {
		return combat;
	}

	@Override
	public void sendDeath(Entity source) {
		final NPCCombatDefinitions defs = getCombatDefinitions();
		resetWalkSteps();
		combat.removeTarget();
		setNextAnimation(null);
		WorldTasksManager.schedule(new WorldTask() {
			int loop;

			@Override
			public void run() {
				if (loop == 0) {
					setNextAnimation(new Animation(defs.getDeathEmote()));
				} else if (loop >= defs.getDeathDelay()) {
					drop();
					reset();
					setLocation(respawnTile);
					finish();
					if (!isSpawned())
						setRespawnTask();
					stop();
				}
				loop++;
			}
		}, 0, 1);
	}

	public void drop() {
		try {
			
			Player killer1 = getMostDamageReceivedSourcePlayer();
			/* Player slayerPartner;
			   String partner;
			   partner = killer1.getSlayerHost();
			   slayerPartner = World.getPlayerByDisplayName(partner);
			if (killer1.getSlayerPartner() != null && slayerPartner.getTask() != null 
					&& World.containsPlayer(partner)) {
				if (getDefinitions().name.toLowerCase().equalsIgnoreCase(
						slayerPartner.getTask().getName().toLowerCase())) {
					slayerPartner.getSkills().addXp(Skills.SLAYER,
							slayerPartner.getTask().getXPAmount() / 5);
					killer1.getSkills().addXp(Skills.SLAYER,
							CooperativeSlayer.bonusXP + killer1.getSkills().SLAYER / 2);
					slayerPartner.getTask().decreaseAmount();
					if (slayerPartner.getTask().getTaskAmount() < 1) {
						if (slayerPartner.getEquipment().getRingId() == 13281) {
							slayerPartner.setSlayerPoints(slayerPartner.getSlayerPoints() + 40);
							slayerPartner.getPackets()
									.sendGameMessage(
											"You have finished your slayer task, talk to Spria for a new task.");
							slayerPartner.getPackets().sendGameMessage(
									"Spria rewards you 40 Slayer points! You now have "
											+ slayerPartner.getSlayerPoints()
											+ " Slayer points.");
						} else {
							slayerPartner.setSlayerPoints(slayerPartner.getSlayerPoints() + 20);
							slayerPartner.getPackets()
									.sendGameMessage(
											"You have finished your slayer task, talk to Spria for a new task.");
							slayerPartner.getPackets().sendGameMessage(
									"Spria rewards you 20 Slayer points! You now have "
											+ slayerPartner.getSlayerPoints()
											+ " Slayer points.");
						}
						slayerPartner.setTask(null);
						return;
					}
					slayerPartner.getTask().setAmountKilled(
							slayerPartner.getTask().getAmountKilled() + 1);
					slayerPartner.sm("<col=2290C7>Your partner, " + slayerPartner.getSlayerPartner() + ", has defeated a "
							+ slayerPartner.getTask().getName().toLowerCase() + " you have been assigned.</col>");
					slayerPartner.getPackets().sendGameMessage(
							"You need to defeat "
									+ slayerPartner.getTask().getTaskAmount() + " "
									+ slayerPartner.getTask().getName().toLowerCase()
									+ " to complete your task.");
				}
			}*/
			Drop[] drops = NPCDrops.getDrops(id);
			if (killer1.getControlerManager().getControler() instanceof RuneDungGame || killer1.getControlerManager().getControler() instanceof FightCaves
					|| killer1.getControlerManager().getControler() instanceof FightKiln || killer1.getControlerManager().getControler() instanceof Zombies
					|| killer1.getControlerManager().getControler() instanceof DarkInvasion) {
				return;
			}
			if (id == 2677)
				return;
			int size = getSize();
			if (this.getCombatLevel() < 70) {
				if (Misc.random(300) == 1) {
					World.addGroundItem(new Item(2700, 1), new WorldTile(this.getCoordFaceX(size), getCoordFaceY(size), getPlane()), killer1, true, 180);
					World.sendWorldMessage("<col=ff8c38><img=7>News: "+ killer1.getDisplayName() + " has just recieved an easy clue drop!"+ "</col> ", false);					
				}
			} else if (this.getCombatLevel() >= 70 && this.getCombatLevel() < 130) {
				if (Misc.random(200) == 1) {
					World.addGroundItem(new Item(13080, 1), new WorldTile(this.getCoordFaceX(size), getCoordFaceY(size), getPlane()), killer1, true, 180);
					World.sendWorldMessage("<col=ff8c38><img=7>News: "+ killer1.getDisplayName() + " has just recieved a medium clue drop!"+ "</col> ", false);
				}
			} else if (this.getCombatLevel() >= 130 && this.getCombatLevel() < 500) {
				if (Misc.random(150) == 1) {
					World.addGroundItem(new Item(13010, 1), new WorldTile(this.getCoordFaceX(size), getCoordFaceY(size), getPlane()), killer1, true, 180);
					World.sendWorldMessage("<col=ff8c38><img=7>News: "+ killer1.getDisplayName() + " has just recieved a hard clue drop!"+ "</col> ", false);
				}
			} else if (this.getCombatLevel() >= 500) {
				if (Misc.random(100) == 1) {
					World.addGroundItem(new Item(19064, 1), new WorldTile(this.getCoordFaceX(size), getCoordFaceY(size), getPlane()), killer1, true, 180);
					World.sendWorldMessage("<col=ff8c38><img=7>News: "+ killer1.getDisplayName() + " has just recieved an elite clue drop!"+ "</col> ", false);
				}
			}
			final int[] charms = { 12158, 12159, 12160, 12161, 12162, 12163, 12164,
					12165, 12166, 12167, 12168 };
			final int randomCharm = charms[Utils.random(charms.length - 1)];
			int effigy = Misc.random(750);
			if (effigy == 1)
				World.addGroundItem(new Item(18778, 1), new WorldTile(this.getCoordFaceX(size),
						getCoordFaceY(size), getPlane()),
				killer1, true, 180);
			int charmamount = 4;
			if (World.quadcharms == true) {
				charmamount = 1;
			}
			if (this.getId() == 9356) {
				killer1.VS = 4;
			}
			
			World.addGroundItem(
					new Item(randomCharm,
							new Item(randomCharm)
									.getDefinitions()
									.isStackable() ? 100 -Utils
									.random(25*charmamount) : 1),
					new WorldTile(this.getCoordFaceX(size),
							getCoordFaceY(size), getPlane()),
					killer1, true, 180);
			Player killer = getMostDamageReceivedSourcePlayer();
			if (killer == null)
				return;
			Player otherPlayer = killer.getSlayerManager().getSocialPlayer();
			SlayerManager manager = killer.getSlayerManager();
			if (manager.isValidTask(getName())) {
			    manager.checkCompletedTask(getDamageReceived(killer), otherPlayer != null ? getDamageReceived(otherPlayer) : 0);
			}
			Assassins manager2 = killer.getAssassinsManager();
			if (manager2.getTask() != null) {
			if (getId() == manager2.getNpcId()) {
				if (manager2.getGameMode() == 3) {
					if (manager2.getSpeed() <= 0) {
						manager2.resetTask();
						killer.sm("You have run out of time and can no longer complete your task.");
					} else {
						manager2.completeTask();
					}
				} else if (manager2.getGameMode() == 2) {
					if (killer.getEquipment().getWeaponId() == manager2.getWeapon()) {
						manager2.completeTask();
					} else {
						killer.sm("You must be using a "+manager2.getWeaponName()+" to kill this monster.");
					}
				} else {
						manager2.completeTask();
				}
			}
			}
			
			Deaths manager3 = killer.getDeathsManager();
			if (manager3.getTask() != null) {
			if (getId() == manager3.getNpcId()) {

						manager3.completeTask();
				}
			}
			//Boss kill count
			if(this.getId() == 6222){
				killer1.KCarmadyl++;
				killer1.sendMessage("<col=15FF00>You now have "+killer1.KCarmadyl+" KreeArra kills!</col>");
			}
			if(this.getId()== 6260){
				killer1.KCbandos++;
				killer1.sendMessage("<col=15FF00>You now have "+killer1.KCbandos+" General Graador kills!</col>");
			}
			if(this.getId() == 6203){
				killer1.KCzammy++;
				killer1.sendMessage("<col=15FF00>You now have "+killer1.KCzammy+" Kril Tsutsaroth kills!</col>");
			}
			if(this.getId() == 6247){
				killer1.KCsaradomin++;
				killer1.sendMessage("<col=15FF00>You now have "+killer1.KCsaradomin+" Commander Zilyana kills!</col>");
			}
			if(this.getId() == 8133){
				killer1.KCcorp++;
				killer1.sendMessage("<col=15FF00>You now have "+killer1.KCcorp+" Corporeal Beast kills!</col>");
			}
			if(this.getId() == 15222){
				killer1.KCsunfreet++;
				killer1.sendMessage("<col=15FF00>You now have "+killer1.KCsunfreet+" Sunfreet kills!</col>");
			}
			if(this.getId() == 3334){
				killer1.KCwild++;
				killer1.sendMessage("<col=15FF00>You now have "+killer1.KCwild+" Wildy Wyrm kills!</col>");
			}
			if(this.getId()== 12878){
				killer1.KCblink++;
				killer1.sendMessage("<col=15FF00>You now have "+killer1.KCblink+" Blink kills!</col>");
			}
			if(this.getId()==11872){
				killer1.KCThunder++;
				killer1.sendMessage("<col=15FF00>You now have "+killer1.KCThunder+" Yk'Lagor kills!</col>");
			}
			
			
			if(this.getId() == 5044 || this.getId() == 5045){
				killer1.Nstage1++;
				killer1.sendMessage("<col=00F5FF>You now have "+killer1.Nstage1+"/10 of the required kills to continue.</col>");
				if(killer1.Nstage1 >= 10){
					killer1.setNextWorldTile(new WorldTile(2649, 9393, 0));
					killer1.sendMessage("You have advanced to the next stage! Good luck!");
					killer1.Nstage1 = 0;
				}
			}
			if(this.getId() == 5218 || this.getId() == 5219){
				killer1.Nstage2++;
				killer1.sendMessage("<col=00F5FF>You now have "+killer1.Nstage2+"/10 of the required kills to continue.</col>");
				if(killer1.Nstage2 >= 10){
					//killer1.setNextWorldTile(new WorldTile(3174, 9766, 0));
					killer1.getControlerManager().startControler("DreadnautControler");
					killer1.Nstage2 = 0;
				}
			}
			if(this.getId() == 12862){
				killer1.Nstage3 = 1;
				killer1.sendMessage("<col=00F5FF>You have killed the Dreadnaut! Talk to Cassie at home for your reward!</col>");
				killer1.setNextWorldTile(new WorldTile(2966, 3397, 0));
			}
			if (drops == null)
				return;

			// SlayerTask task = killer.getSlayerTask();
			if (killer.slayerTask.getTaskMonstersLeft() > 0) {
				for (String m : killer.slayerTask.getTask().slayable) {
					if (getDefinitions().name.equals(m)) {
						killer.slayerTask.onMonsterDeath(killer, this);
						break;
					}
				}
			}
			Drop[] possibleDrops = new Drop[drops.length];
			int possibleDropsCount = 0;
			for (Drop drop : drops) {
				if (drop.getRate() == 100)
					sendDrop(killer, drop);
				else {
					if ((Utils.getRandomDouble(99) + 1) <= drop.getRate() * 1.0)
						possibleDrops[possibleDropsCount++] = drop;
				}
			}
			if (possibleDropsCount > 0)
				sendDrop(killer,
						possibleDrops[Utils.getRandom(possibleDropsCount - 1)]);
		} catch (Exception e) {
			e.printStackTrace();
		} catch (Error e) {
			e.printStackTrace();
		}
	}

	public void sendDrop(final Player player, Drop drop) {
		final int size = getSize();
		int bonus = 1;
		int random = Utils.random(1, 100);
		if (World.doubledrops == true) {
			bonus = 2;
		}
		String dropName = ItemDefinitions.getItemDefinitions(drop.getItemId())
				.getName().toLowerCase();
		Item item = ItemDefinitions.getItemDefinitions(drop.getItemId())
				.isStackable() ? new Item(drop.getItemId(),
				(drop.getMinAmount() * Settings.DROP_RATE*bonus)
						+ Utils.getRandom(drop.getExtraAmount()
								* Settings.DROP_RATE*bonus)) : new Item(
				drop.getItemId(), drop.getMinAmount()
						+ Utils.getRandom(drop.getExtraAmount()));
		if (item.getId() == 995) {
			player.getInventory().addItemMoneyPouch(item);
			return;
		}
		if (player.getInventory().containsItem(18337, 1)// Bonecrusher
				&& item.getDefinitions().getName().toLowerCase()
						.contains("bones")) {
			player.getSkills().addXp(Skills.PRAYER,
					Burying.Bone.forId(drop.getItemId()).getExperience());
			return;
		}
		if (player.getInventory().containsItem(19675, 1)// Herbicide
				&& item.getDefinitions().getName().toLowerCase()
						.contains("grimy")) {
			if (player.getSkills().getLevelForXp(Skills.HERBLORE) >= HerbCleaning.getHerb(item.getId()).getLevel()) {
				player.getSkills().addXp(
						Skills.HERBLORE,
						HerbCleaning.getHerb(drop.getItemId())
								.getExperience() * 2);
				return;
			}
		}
		if (random >= 95 && random <= 100) {
			if (this.getId() == 116
				||this.getId() == 4292
				||this.getId() == 4291
				||this.getId() == 6078
				||this.getId() == 6079
				||this.getId() == 6080
				&& drop.getItemId() == 8844) {
				WGuildControler.dropDefender(player, this);
				return;
			}
		}
		if (this.getId() == 6230 || this.getId() == 6231 || this.getId() == 6229
				|| this.getId() == 6232 || this.getId() == 6240 || this.getId() == 6241
				|| this.getId() == 6242 || this.getId() ==  6233 || this.getId() == 6234
				|| this.getId() == 6243 || this.getId() == 6244 || this.getId() == 6245
				|| this.getId() == 6246 || this.getId() == 6238 || this.getId() == 6239
				|| this.getId() == 6227 || this.getId() == 6625 || this.getId() == 6223
				|| this.getId() == 6222) {
			player.armadyl++;
	        player.getPackets().sendIComponentText(601, 8,  ""+ player.armadyl +"");
		}
		if (this.getId() == 6278 || this.getId() == 6277 || this.getId() == 6276
				|| this.getId() == 6283 || this.getId() == 6282 || this.getId() == 6280
				|| this.getId() == 6281 || this.getId() == 6279 || this.getId() == 6275
				|| this.getId() == 6271 || this.getId() == 6272 || this.getId() == 6273
				|| this.getId() == 6274 || this.getId() == 6269 || this.getId() ==  6270
				|| this.getId() == 6268 || this.getId() == 6265 || this.getId() == 6263
				|| this.getId() == 6261 || this.getId() == 6260) {
			player.bandos++;
			player.getPackets().sendIComponentText(601, 9,  ""+ player.bandos +"");
		}
		if (this.getId() == 6257 || this.getId() == 6255 || this.getId() == 6256
				|| this.getId() == 6258 || this.getId() == 6259 || this.getId() == 6254
				|| this.getId() == 6252 || this.getId() == 6250 || this.getId() == 6248
				|| this.getId() == 6247) {
			player.saradomin++;
			 player.getPackets().sendIComponentText(601, 10,  ""+ player.saradomin +"");
		}
		if (this.getId() == 6221 || this.getId() == 6219 || this.getId() == 6220
				|| this.getId() == 6217 || this.getId() == 6216 || this.getId() == 6215
				|| this.getId() == 6214 || this.getId() == 6213 || this.getId() == 6212
				|| this.getId() == 6211 || this.getId() == 6218 || this.getId() == 6208
				|| this.getId() == 6206 || this.getId() == 6204 || this.getId() == 6203) {
			player.zamorak++;
			player.getPackets().sendIComponentText(601, 11,  ""+ player.zamorak +"");
		}
		/*LootShare/CoinShare*/
		FriendChatsManager fc = player.getCurrentFriendChat();
		if(player.lootshareEnabled()) {
			if(fc != null) {
				CopyOnWriteArrayList<Player> players = fc.getPlayers();
				CopyOnWriteArrayList<Player> playersWithLs = new CopyOnWriteArrayList<Player>();
				for(Player p : players) {
					if(p.lootshareEnabled() && p.getRegionId() == player.getRegionId()) //If players in FC have LS enabled and are also in the same map region.
						playersWithLs.add(p);
				}
				if (item.getDefinitions().getTipitPrice() >= 1000000) {
				int playeramount = playersWithLs.size();
				int dividedamount = (item.getDefinitions().getTipitPrice() / playeramount);
				for(Player p : playersWithLs) {
					p.getInventory().addItemMoneyPouch(new Item(995, dividedamount));
					p.sendMessage(String.format("<col=115b0d>You received: %sx coins from a split of the item %s.</col>", dividedamount, dropName));
					return;
				}
				} else {
				Player luckyPlayer = playersWithLs.get((int)(Math.random()*playersWithLs.size())); //Choose a random player to get the drop.
				World.addGroundItem(item, new WorldTile(getCoordFaceX(size), getCoordFaceY(size), getPlane()), luckyPlayer, true, 180);
				luckyPlayer.sendMessage(String.format("<col=115b0d>You received: %sx %s.</col>", item.getAmount(), dropName));
				for(Player p : playersWithLs) {
					if(!p.equals(luckyPlayer))
					p.sendMessage(String.format("%s received: %sx %s.", luckyPlayer.getDisplayName(), item.getAmount(), dropName));
				}
				}
				return;
			}
		} 
		/*End of LootShare/CoinShare*/
		player.npcLog(player, item.getId(), item.getAmount(), item.getName(), this.getName(), this.getId());
		if (!player.isPker) {
			World.addGroundItem(item, new WorldTile(getCoordFaceX(size),
					getCoordFaceY(size), getPlane()), player, true, 180);
		} else {
			World.addGroundItem(item, new WorldTile(getCoordFaceX(size),
					getCoordFaceY(size), getPlane()), player, true, 1800000000);
		}
		if (dropName.contains("pernix") || dropName.contains("torva")
				|| dropName.contains("virtus") || dropName.contains("bandos")
				|| dropName.contains("hilt")
				|| dropName.contains("hati") || dropName.contains("korasi")
				|| dropName.contains("divine")
				|| (dropName.contains("saradomin")  && !dropName.contains("brew"))
				|| dropName.contains("visage")
				|| dropName.contains("zamorakian")
				|| dropName.contains("spectral")
				|| dropName.contains("elysian")
				|| dropName.contains("steadfast")
				|| dropName.contains("armadyl chest")
				|| dropName.contains("armadyl plate")
				|| dropName.contains("armadyl boots")
				|| dropName.contains("armadyl helmet")
				|| dropName.contains("armadyl gloves")
				|| dropName.contains("armadyl_chest")
				|| dropName.contains("armadyl_plate")
				|| dropName.contains("armadyl_boots")
				|| dropName.contains("armadyl_helmet")
				|| dropName.contains("armadyl_gloves")
				|| dropName.contains("armadyl_chainskirt")
				|| dropName.contains("armadyl chainskirt")
				|| dropName.contains("buckler")
				|| dropName.contains("glaiven")
				|| dropName.contains("ragefire")
				|| dropName.contains("spirit shield")
				|| dropName.contains("spirit_shield")
				|| dropName.contains("elixer")
				|| dropName.contains("fury")
				|| dropName.contains("arcane")
				|| dropName.contains("vine")
				|| dropName.contains("goliath")
				|| dropName.contains("swift")
				|| dropName.contains("spellcaster")
				|| dropName.contains("gorgonite")
				|| dropName.contains("promethium")
				|| dropName.contains("primal")
				|| dropName.contains("polypore_stick")
				|| dropName.contains("polypore stick")
				|| dropName.contains("ganodermic gloves")
				|| dropName.contains("ganodermic_gloves")
				|| dropName.contains("ganodermic boots")
				|| dropName.contains("ganodermic_boots")
				|| dropName.contains("vesta")
				|| dropName.contains("statius")
				|| dropName.contains("zuriel")
				|| dropName.contains("morrigan")
				|| dropName.contains("crystal_key")
				|| dropName.contains("crystal key")
				|| dropName.contains("clue")
				|| dropName.contains("clue_scroll_(elite)")
				|| dropName.contains("clue_scroll_(easy)")
				|| dropName.contains("clue_scroll_(hard)")
				|| dropName.contains("clue_scroll_(medium)")
				|| dropName.contains("deathtouched")
				|| dropName.contains("dragon_chain")
				|| dropName.contains("dragon chain")
				|| dropName.contains("dragon_full")
				|| dropName.contains("dragon full")
				|| dropName.contains("dragon_kite")
				|| dropName.contains("dragon kite")
				|| dropName.contains("dragon_rider")
				|| dropName.contains("dragon rider")
				|| dropName.contains("inferno")
				|| dropName.contains("gilded")
				|| dropName.contains("mask")
				|| dropName.contains("chaotic")
				|| dropName.contains("seismic_wand")
				|| dropName.contains("seismic_singularity")
				|| dropName.contains("abyssal_whip")
				|| dropName.contains("drygore_mace")
				|| dropName.contains("drygore_rapier")
				|| dropName.contains("drygore_longsword")
				|| dropName.contains("Off-hand_drygore_rapier")
				|| dropName.contains("Off-hand_drygore_mace")
				|| dropName.contains("Off-hand_drygore_longsword")){
			  World.sendGraphics(player, new Graphics(4422), new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane()));
			  
			  WorldTasksManager.schedule(new WorldTask() {

				@Override
				public void run() {
					int loop = 0;
					if (loop == 1) {
					 World.sendGraphics(player, new Graphics(4422), new WorldTile(getCoordFaceX(size),
								getCoordFaceY(size), getPlane()));
					}
					if (loop == 2) {
						 World.sendGraphics(player, new Graphics(4422), new WorldTile(getCoordFaceX(size),
									getCoordFaceY(size), getPlane()));
						}
					if (loop == 3) {
						 World.sendGraphics(player, new Graphics(4422), new WorldTile(getCoordFaceX(size),
									getCoordFaceY(size), getPlane()));
						}
					if (loop == 4) {
						 stop();
						}
					 loop++;
				}
				  
			  }, 0, 1);
			  
			  player.sm("<col=ff8c38>A golden beam shines over one of your items.");
			World.sendWorldMessage("<col=ff8c38><img=7>News: "+ player.getDisplayName() + " has just recieved a " + dropName + " drop!"+ "</col> ", false);
		}
	}

	@Override
	public int getSize() {
		return getDefinitions().size;
	}

	public int getMaxHit() {
		return getCombatDefinitions().getMaxHit();
	}

	public int[] getBonuses() {
		return bonuses;
	}

	@Override
	public double getMagePrayerMultiplier() {
		return 0;
	}

	@Override
	public double getRangePrayerMultiplier() {
		return 0;
	}

	@Override
	public double getMeleePrayerMultiplier() {
		return 0;
	}

	public WorldTile getRespawnTile() {
		return respawnTile;
	}

	public boolean isUnderCombat() {
		return combat.underCombat();
	}

	@Override
	public void setAttackedBy(Entity target) {
		super.setAttackedBy(target);
		if (target == combat.getTarget()
				&& !(combat.getTarget() instanceof Familiar))
			lastAttackedByTarget = Utils.currentTimeMillis();
	}

	public boolean canBeAttackedByAutoRelatie() {
		return Utils.currentTimeMillis() - lastAttackedByTarget > lureDelay;
	}

	public boolean isForceWalking() {
		return forceWalk != null;
	}

	public void setTarget(Entity entity) {
		if (isForceWalking()) // if force walk not gonna get target
			return;
		combat.setTarget(entity);
		lastAttackedByTarget = Utils.currentTimeMillis();
	}

	public void removeTarget() {
		if (combat.getTarget() == null)
			return;
		combat.removeTarget();
	}

	public void forceWalkRespawnTile() {
		setForceWalk(respawnTile);
	}

	public void setForceWalk(WorldTile tile) {
		resetWalkSteps();
		forceWalk = tile;
	}

	public boolean hasForceWalk() {
		return forceWalk != null;
	}

	public ArrayList<Entity> getPossibleTargets() {
		ArrayList<Entity> possibleTarget = new ArrayList<Entity>();
		for (int regionId : getMapRegionsIds()) {
			List<Integer> playerIndexes = World.getRegion(regionId)
					.getPlayerIndexes();
			if (playerIndexes != null) {
				for (int playerIndex : playerIndexes) {
					Player player = World.getPlayers().get(playerIndex);
					if (player == null
							|| player.isDead()
							|| player.hasFinished()
							|| !player.isRunning()
							|| !player
							.withinDistance(
									this,
									forceTargetDistance > 0 ? forceTargetDistance
											: (getCombatDefinitions()
													.getAttackStyle() == NPCCombatDefinitions.MELEE ? 4
															: getCombatDefinitions()
															.getAttackStyle() == NPCCombatDefinitions.SPECIAL ? 64
																	: 8))
																	|| (!forceMultiAttacked
																			&& (!isAtMultiArea() || !player
																					.isAtMultiArea())
																					&& player.getAttackedBy() != this && (player
																							.getAttackedByDelay() > Utils.
																							currentTimeMillis() || player
																							.getFindTargetDelay() > Utils
																							.currentTimeMillis()))
																							|| !clipedProjectile(player, false)
																							|| (!forceAgressive && !Wilderness.isAtWild(this) && immunity(player)))
						continue;
					possibleTarget.add(player);
				}
			}
		}
		return possibleTarget;
	}
	
	public boolean immunity(Player player) {
		if (player.stealth) {
		if (player.getSkills().getAssassinLevel(Skills.STEALTH_MOVES) <= 10) {
			if (player.getSkills().getCombatLevelWithSummoning() >= getCombatLevel() * 2)
				return true;
			else
				return false;
		} else if (player.getSkills().getAssassinLevel(Skills.STEALTH_MOVES) >= 11 && player.getSkills().getAssassinLevel(Skills.STEALTH_MOVES) <= 98) {
			int level = player.getSkills().getCombatLevelWithSummoning() + (player.getSkills().getAssassinLevel(Skills.STEALTH_MOVES)*2);
			if (level >= getCombatLevel() * 2)
				return true;
			else
				return false;
		} else {
			return true;
		}
		} else {
			if (player.getSkills().getCombatLevelWithSummoning() >= getCombatLevel() * 2)
				return true;
			else
				return false;
		}
	}

	public boolean checkAgressivity() {
		// if(!(Wilderness.isAtWild(this) &&
		// getDefinitions().hasAttackOption())) {
		if (!forceAgressive) {
			NPCCombatDefinitions defs = getCombatDefinitions();
			if (defs.getAgressivenessType() == NPCCombatDefinitions.PASSIVE)
				return false;
		}
		// }
		ArrayList<Entity> possibleTarget = getPossibleTargets();
		if (!possibleTarget.isEmpty()) {
			Entity target = possibleTarget.get(Utils.random(possibleTarget.size()));
			setTarget(target);
			target.setAttackedBy(target);
			target.setFindTargetDelay(Utils.currentTimeMillis() + 10000);
			return true;
		}
		return false;
	}

	public boolean isCantInteract() {
		return cantInteract;
	}

	public void setCantInteract(boolean cantInteract) {
		this.cantInteract = cantInteract;
		if (cantInteract)
			combat.reset();
	}

	public int getCapDamage() {
		return capDamage;
	}

	public void setCapDamage(int capDamage) {
		this.capDamage = capDamage;
	}

	public int getLureDelay() {
		return lureDelay;
	}

	public void setLureDelay(int lureDelay) {
		this.lureDelay = lureDelay;
	}

	public boolean isCantFollowUnderCombat() {
		return cantFollowUnderCombat;
	}

	public void setCantFollowUnderCombat(boolean canFollowUnderCombat) {
		this.cantFollowUnderCombat = canFollowUnderCombat;
	}

	public Transformation getNextTransformation() {
		return nextTransformation;
	}

	@Override
	public String toString() {
		return getDefinitions().name + " - " + id + " - " + getX() + " "
				+ getY() + " " + getPlane();
	}

	public boolean isForceAgressive() {
		return forceAgressive;
	}

	public void setForceAgressive(boolean forceAgressive) {
		this.forceAgressive = forceAgressive;
	}

	public int getForceTargetDistance() {
		return forceTargetDistance;
	}

	public void setForceTargetDistance(int forceTargetDistance) {
		this.forceTargetDistance = forceTargetDistance;
	}

	public boolean isForceFollowClose() {
		return forceFollowClose;
	}

	public void setForceFollowClose(boolean forceFollowClose) {
		this.forceFollowClose = forceFollowClose;
	}

	public boolean isForceMultiAttacked() {
		return forceMultiAttacked;
	}

	public void setForceMultiAttacked(boolean forceMultiAttacked) {
		this.forceMultiAttacked = forceMultiAttacked;
	}

	public boolean hasRandomWalk() {
		return randomwalk;
	}

	public void setRandomWalk(boolean forceRandomWalk) {
		this.randomwalk = forceRandomWalk;
	}

	public String getCustomName() {
		return name;
	}

	public void setName(String string) {
		this.name = getDefinitions().name.equals(string) ? null : string;
		changedName = true;
	}

	public int getCustomCombatLevel() {
		return combatLevel;
	}

	public int getCombatLevel() {
		return combatLevel >= 0 ? combatLevel : getDefinitions().combatLevel;
	}

	public String getName() {
		return name != null ? name : getDefinitions().name;
	}
	
	public String getName2() {
		return getDefinitions().name != null ? getDefinitions().name : name;
	}

	public void setCombatLevel(int level) {
		combatLevel  = getDefinitions().combatLevel == level ? -1 : level;
		changedCombatLevel = true;
	}

	public boolean hasChangedName() {
		return changedName;
	}

	public boolean hasChangedCombatLevel() {
		return changedCombatLevel;
	}

	public WorldTile getMiddleWorldTile() {
		int size = getSize();
		return new WorldTile(getCoordFaceX(size),getCoordFaceY(size), getPlane());
	}

	public boolean isSpawned() {
		return spawned;
	}

	public void setSpawned(boolean spawned) {
		this.spawned = spawned;
	}

	public boolean isNoDistanceCheck() {
		return noDistanceCheck;
	}

	public void setNoDistanceCheck(boolean noDistanceCheck) {
		this.noDistanceCheck = noDistanceCheck;
	}
	
	public boolean withinDistance(Player tile, int distance) {
		return super.withinDistance(tile, distance);
	}

	/**
	 * Gets the locked.
	 * @return The locked.
	 */
	public boolean isLocked() {
		return locked;
	}

	/**
	 * Sets the locked.
	 * @param locked The locked to set.
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
    public SecondaryBar getNextSecondaryBar() {
	return nextSecondaryBar;
    }

    public void setNextSecondaryBar(SecondaryBar secondaryBar) {
	this.nextSecondaryBar = secondaryBar;
    }
	
    public ArrayList<Entity> getPossibleTargets(boolean checkNPCs, boolean checkPlayers) {
	int size = getSize();
	//int agroRatio = getCombatDefinitions().getAgroRatio();
	ArrayList<Entity> possibleTarget = new ArrayList<Entity>();
	for (int regionId : getMapRegionsIds()) {
	    if (checkPlayers) {
		List<Integer> playerIndexes = World.getRegion(regionId).getPlayerIndexes();
		if (playerIndexes != null) {
		    for (int playerIndex : playerIndexes) {
			Player player = World.getPlayers().get(playerIndex);
			if (player == null || player.isDead() || player.hasFinished() || !player.isRunning() || player.getAppearence().isHidden() || !Utils.isOnRange(getX(), getY(), size, player.getX(), player.getY(), player.getSize(), forceTargetDistance) || (!forceMultiAttacked && (!isAtMultiArea() || !player.isAtMultiArea()) && (player.getAttackedBy() != this && (player.getAttackedByDelay() > Utils.currentTimeMillis() || player.getFindTargetDelay() > Utils.currentTimeMillis()))) || !clipedProjectile(player, false) || (!forceAgressive && !Wilderness.isAtWild(this) && player.getSkills().getCombatLevelWithSummoning() >= getCombatLevel() * 2))
			    continue;
			possibleTarget.add(player);
		    }
		}
	    }
	    if (checkNPCs) {
		List<Integer> npcsIndexes = World.getRegion(regionId).getNPCsIndexes();
		if (npcsIndexes != null) {
		    for (int npcIndex : npcsIndexes) {
			NPC npc = World.getNPCs().get(npcIndex);
			if (npc == null || npc == this || npc.isDead() || npc.hasFinished() || !Utils.isOnRange(getX(), getY(), size, npc.getX(), npc.getY(), npc.getSize(), forceTargetDistance) || !npc.getDefinitions().hasAttackOption() || ((!isAtMultiArea() || !npc.isAtMultiArea()) && npc.getAttackedBy() != this && npc.getAttackedByDelay() > Utils.currentTimeMillis()) || !clipedProjectile(npc, false))
			    continue;
			possibleTarget.add(npc);
		    }
		}
	    }
	}
	return possibleTarget;
    }
    
}
