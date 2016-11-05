package com.rs.game.player.actions.divination;


import com.rs.game.Animation;
import com.rs.game.Entity;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.actions.Action;
import com.rs.game.tasks.WorldTask;
import com.rs.game.tasks.WorldTasksManager;
import com.rs.utils.Utils;

/**
 * 
 * @author Nosz & and little to jason 
 * 
 */
public class HarvestWisp extends Action {

	private enum Creature {

		PALE_WISP(18150, 2, 22857, 29313, 29384, 10, 5, 1), 
		
		PALE_SPRING(18173, 2, 22857, 29313, 29384, 10, 5, 1),
		
		FLICKERING_WISP(18151, 2, 22857, 29190, 29385, 10, 5, 10),
		
		FLICKERING_SPRING(18174, 2, 29190, 29190, 29385, 10, 5, 10),
		
		ENRICHED_FLICKERING_SPRING(18175, 4, 29190, 29190, 29385, 10, 10, 10),
		
		BRIGHT_WISP(18153, 2, 29315, 29315, 29386, 10, 5, 20),
		
		BRIGHT_SPRING(18176, 2, 29315, 29315, 29386, 10, 5, 20),
		
		ENRICHED_BRIGHT_SPRING(18177, 4, 29315, 29315, 29386, 10, 10, 20),
		
		GLOWING_WISP(18155, 3, 29192, 29316, 29387, 10, 5, 30),
		
		GLOWING_SPRING(18178, 3, 29192, 29316, 29387, 10, 5, 30),
		
		ENRICHED_GLOWING_SPRING(18179, 6, 29192, 29316, 29387, 10, 10, 30),
		
		SPARKLING_WISP(18157, 4, 29193, 29193, 29388, 10, 5, 40),
		
		SPARKLING_SPRING(18180, 4, 29193, 29193, 29388, 10, 5, 40),
		
		ENRICHED_SPARKLING_SPRING(18181, 8, 29193, 29193, 29388, 10, 10, 40),
		
		GLEAMING_WISP(18159, 5, 29194, 29194, 29389, 10, 5, 50),
		
		GLEAMING_SPRING(18182, 5, 29194, 29194, 29389, 10, 5, 50),
		
		ENRICHED_GLEAMING_SPRING(18183, 10, 29194, 29194, 29389, 10, 10, 50),
		
		VIBRANT_WISP(18161, 6, 29195, 29195, 29390, 10, 5, 60),
		
		VIBRANT_SPRING(18184, 6, 29195, 29195, 29390, 10, 5, 60),
		
		ENRICHED_VIBRANT_SPRING(18185, 12, 29195, 29195, 29390, 10, 10, 60),
		
		LUSTROUS_WISP(18163, 7, 29196, 29196, 29391, 10, 5, 70),
		
		LUSTROUS_SPRING(18186, 7, 29196, 29196, 29391, 10, 5, 70),
		
		ENRICHED_LUSTROUS_SPRING(18187, 14, 29196, 29196, 29391, 10, 10, 70),
		
		ELDER_WISP(13614, 8, 31312, 31312, 31326, 10, 5, 75),
		
		ELDER_SPRING(13616, 8, 31312, 31312, 31326, 10, 5, 75),
		
		ENRICHED_ELDER_SPRING(13627, 16, 31312, 31312, 31326, 10, 10, 75),
		
		BRILLIANT_WISP(18165, 9, 29197, 29197, 29392, 10, 5, 80),
		
		BRILLIANT_SPRING(18188, 9, 29197, 29197, 29392, 10, 5, 80),
		
		ENRICHED_BRILLIANT_SPRING(18189, 18, 29197, 29197, 29392, 10, 10, 80),
		
		RADIANT_WISP(18167, 10, 29198, 29198, 29393, 10, 5, 85),
		
		RADIANT_SPRING(18190, 10, 29198, 29198, 29393, 10, 5, 85),
		
		ENRICHED_RADIANT_SPRING(18191, 20, 29198, 29198, 29393, 10, 10, 85),
		
		LUMINOUS_WISP(18169, 11, 29323, 29323, 29394, 10, 5, 90),
		
		LUMINOUS_SPRING(18192, 11, 29323, 29323, 29394, 10, 5, 90),
		
		ENRICHED_LUMINOUS_SPRING(18193, 22, 29323, 29323, 29394, 10, 10, 90),
		
		INCANDESCENT_WISP(18171, 12, 29324, 29324, 29395, 10, 5, 95),
		
		INCANDESCENT_SPRING(18194, 12, 29324, 29324, 29395, 10, 5, 95),
		
		ENRICHED_INCANDESCENT_SPRING(18195, 24, 29324, 29324, 29395, 10, 10, 95),
;

		private int npcId, energyId, memoryId, playerEmoteId, npc2Id, npcLife, levelRequired;
		@SuppressWarnings("unused")
		private double xp, pointValue;
		private Creature(int npcId, double xp, int playerEmoteId, int energyId, int memoryId,
				int npc2Id, int npcLife, int levelRequired) {
			this.npcId = npcId;
			this.xp = xp;
			this.playerEmoteId = playerEmoteId;
			this.energyId = energyId;
			this.memoryId = memoryId;
			this.npc2Id = npc2Id;
			this.npcLife = npcLife;
			this.levelRequired = levelRequired;
		}

		/**
		 * Gets the rune id from the essling enum.
		 * 
		 * @return runeId
		 */
		public int getenergyId() {
			return energyId;
		}
		public int getmemoryId() {
			return memoryId;
		}
		public double getExp() {
			return xp;
		}
		
		/**
		 * 
		 * @return the levelRequired
		 */
		public int getLevelRequired() {
			return levelRequired;
		}

	}

	private Creature creatures;
	private NPC creature;
	private boolean started;
	private int npcLife;

	/**
	 * RSRuneCrafting constructor for npcs.
	 * @param creatures
	 * @param creature
	 */
	public HarvestWisp(Creature creatures, NPC creature) {
		this.creatures = creatures;
		this.creature = creature;
	}

	public static boolean siphon(Player player, NPC npc) {
		Creature creature = getCreature(npc.getId());
		if (!player.withinDistance(npc, 2))
			return false;
		if(creature == null)
			return false;
		player.getActionManager().setAction(new HarvestWisp(creature, npc));
		return true;
	}
	
	private static Creature getCreature(int id) {
		for(Creature creature : Creature.values())
			if(creature.npcId == id)
				return creature;
		return null;
	}

	@Override
	public boolean start(Player player) {
		if(checkAll(player)) {
			npcLife = creatures.npcLife;
			return true;
		}
		return false;
	}

	@Override
	public boolean process(Player player) {
		return checkAll(player);
	}
	
	/**
	 * Checks the players requirements.
	 * @param player
	 * @return requirements.
	 */
	
	public boolean checkAll(final Player player) {
		if(player.isLocked()) {
			return false;
		}
		if (creature.hasFinished())
			return false;
		if(!started && !player.withinDistance(creature, 1)) 
			return true;
		if(player.getSkills().getDivinationLevel() < creatures.getLevelRequired()) {
			player.getDialogueManager().startDialogue("SimpleMessage",
					"This creature requires level " + creatures.getLevelRequired() + " divination to harvest.");
			player.setNextAnimation(new Animation(21229));
			//player.stopAll();
			return false;
		}
		if (!player.getInventory().hasFreeSlots())  {
			player.getPackets().sendGameMessage(
					"Not enough space in your inventory.");
			player.setNextAnimation(new Animation(21229));
			return false;
		}
		if(!started) {
			creature.resetWalkSteps();
			player.resetWalkSteps();
			started = true;
		}
		return true;
	}

	@Override
		public int processWithDelay(final Player player) {
		checkAll(player);
		if (started) {
			int level = player.getSkills().getDivinationLevel();
			if (Utils.getRandom(level <= 50 ? 2 : 1) == 1) {
				npcLife--;
				player.getInventory().addItem(creatures.getenergyId(), Utils.random(2 , 4));
				player.getInventory().addItem(creatures.getmemoryId(), 1);
				addXp(player);
			} else {// When you don't get a rune random chance to get xp.
				player.getSkills().addDivinationXp(Utils.random(0 , 2));
			}
			if (npcLife == 0) {
				processEsslingDeath(player);
				return -1;
			}
			player.setNextAnimation(new Animation(21228));
			creature.setNextFaceWorldTile(player);
			creature.resetWalkSteps();
			player.setNextFaceWorldTile(creature);
			player.setNextGraphics(new Graphics(4235));
			
		}
		return 1;
		}
	
	/**
	 * Process the creatures death.
	 * @param player
	 */
		public void processEsslingDeath(final Player player) {
		WorldTasksManager.schedule(new WorldTask() {
			@Override
			public void run() {
				player.setNextAnimation(new Animation(21229));
				player.getPackets().sendGameMessage("The spring has been drained.");
				creature.finish();
				stop();
			}
		}, 2);
	}
	@Override
		public void stop(Player player) {
		setActionDelay(player, 3);
		}
	
		public static void resetboons(final Player player){
			player.createdFlickeringBoon = false;
			player.createdBrightBoon = false;
			player.createdGlowingBoon = false;
			player.createdSparklingBoon = false;
			player.createdGleamingBoon = false;
			player.createdVibrantBoon = false;
			player.createdLustrousBoon = false;
			player.createdBrilliantBoon = false;
			player.createdRadiantBoon = false;
			player.createdLuminousBoon = false;
			player.createdIncandescentBoon = false;
		}
	
		public static void createboon(final Player player, final int memoryId, final int memoryAmount, final int bloonId, final int exp, final int lvl) {
			if(player.getSkills().getDivinationLevel() >= lvl) {
				if(player.getInventory().containsItem(memoryId, memoryAmount)) {
					if(bloonId == 29373 && player.createdFlickeringBoon == false){
			player.createdFlickeringBoon = true;
			player.getInventory().deleteItem(memoryId, memoryAmount);
			player.getInventory().addItem(bloonId, 1);
			player.getSkills().addDivinationXp(exp);
			player.setNextAnimation(new Animation(21251));
			player.setNextGraphics(new Graphics(4250));
			player.sendMessage("You create a flickering boon.");
		} else if(bloonId == 29374 && player.createdBrightBoon == false){
			player.createdBrightBoon = true;
			player.getInventory().deleteItem(memoryId, memoryAmount);
			player.getInventory().addItem(bloonId, 1);
			player.getSkills().addDivinationXp(exp);
			player.setNextAnimation(new Animation(21251));
			player.setNextGraphics(new Graphics(4250));
			player.sendMessage("You create a bright boon.");
		} else if(bloonId == 29375 && player.createdGlowingBoon == false){
			player.createdGlowingBoon = true;
			player.getInventory().deleteItem(memoryId, memoryAmount);
			player.getInventory().addItem(bloonId, 1);
			player.getSkills().addDivinationXp(exp);
			player.setNextAnimation(new Animation(21251));
			player.setNextGraphics(new Graphics(4250));
			player.sendMessage("You create a glowing boon.");
		} else if(bloonId == 29376 && player.createdSparklingBoon == false){
			player.createdSparklingBoon = true;
			player.getInventory().deleteItem(memoryId, memoryAmount);
			player.getInventory().addItem(bloonId, 1);
			player.getSkills().addDivinationXp(exp);
			player.setNextAnimation(new Animation(21251));
			player.setNextGraphics(new Graphics(4250));
			player.sendMessage("You create a sparkling boon.");
		} else if(bloonId == 29377 && player.createdGleamingBoon == false){
			player.createdGleamingBoon = true;
			player.getInventory().deleteItem(memoryId, memoryAmount);
			player.getInventory().addItem(bloonId, 1);
			player.getSkills().addDivinationXp(exp);
			player.setNextAnimation(new Animation(21251));
			player.setNextGraphics(new Graphics(4250));
			player.sendMessage("You create a gleaming boon.");
		} else if(bloonId == 29378 && player.createdVibrantBoon == false){
			player.createdVibrantBoon = true;
			player.getInventory().deleteItem(memoryId, memoryAmount);
			player.getInventory().addItem(bloonId, 1);
			player.getSkills().addDivinationXp(exp);
			player.setNextAnimation(new Animation(21251));
			player.setNextGraphics(new Graphics(4250));
			player.sendMessage("You create a vibrant boon.");
		} else if(bloonId == 29379 && player.createdLustrousBoon == false){
			player.createdLustrousBoon = true;
			player.getInventory().deleteItem(memoryId, memoryAmount);
			player.getInventory().addItem(bloonId, 1);
			player.getSkills().addDivinationXp(exp);
			player.setNextAnimation(new Animation(21251));
			player.setNextGraphics(new Graphics(4250));
			player.sendMessage("You create a lustrous boon.");
		} else if(bloonId == 29380 && player.createdBrilliantBoon == false){
			player.createdBrilliantBoon = true;
			player.getInventory().deleteItem(memoryId, memoryAmount);
			player.getInventory().addItem(bloonId, 1);
			player.getSkills().addDivinationXp(exp);
			player.setNextAnimation(new Animation(21251));
			player.setNextGraphics(new Graphics(4250));
			player.sendMessage("You create a brilliant boon.");
		} else if(bloonId == 29381 && player.createdRadiantBoon == false){
			player.createdRadiantBoon = true;
			player.getInventory().deleteItem(memoryId, memoryAmount);
			player.getInventory().addItem(bloonId, 1);
			player.getSkills().addDivinationXp(exp);
			player.setNextAnimation(new Animation(21251));
			player.setNextGraphics(new Graphics(4250));
			player.sendMessage("You create a radiant boon.");
		} else if(bloonId == 29382 && player.createdLuminousBoon == false){
			player.createdLuminousBoon = true;
			player.getInventory().deleteItem(memoryId, memoryAmount);
			player.getInventory().addItem(bloonId, 1);
			player.getSkills().addDivinationXp(exp);
			player.setNextAnimation(new Animation(21251));
			player.setNextGraphics(new Graphics(4250));
			player.sendMessage("You create a luminous boon.");
		} else if(bloonId == 29383 && player.createdIncandescentBoon == false){
			player.createdIncandescentBoon = true;
			player.getInventory().deleteItem(memoryId, memoryAmount);
			player.getInventory().addItem(bloonId, 1);
			player.getSkills().addDivinationXp(exp);
			player.setNextAnimation(new Animation(21251));
			player.setNextGraphics(new Graphics(4250));
			player.sendMessage("You create a incandescent boon.");
		} else if(bloonId == 29383 && player.createdIncandescentBoon == false){
			player.createdIncandescentBoon = true;
			player.getInventory().deleteItem(memoryId, memoryAmount);
			player.getInventory().addItem(bloonId, 1);
			player.getSkills().addDivinationXp(exp);
			player.setNextAnimation(new Animation(21251));
			player.setNextGraphics(new Graphics(4250));
			player.sendMessage("You create a incandescent boon.");
		} else 
			player.sendMessage("You have already created this boon.");
		return;
		} else 
			player.sendMessage("You need "+memoryAmount+" memories to create this boon.");
		} else {
			player.sendMessage("You need "+lvl+" divination to create this boon.");
			}
		}
		public static void convertboth(final Player player, final int memoryId, final int energyId, final double xp) {
		final int xStart = player.getX();
		final int yStart = player.getY();
		player.sendMessage("You convert your memories & energy into experience.");
		WorldTasksManager.schedule(new WorldTask() {
		    @Override
		    public void run() {
		if(player.getInventory().containsItem(memoryId, 5) && player.getInventory().containsItem(energyId, 1)) {
			int x = player.getX();
			int y = player.getY();
			if((xStart == x) && (yStart == y)) {
				player.setNextAnimation(new Animation(21234));
				player.setNextGraphics(new Graphics(4239));
				player.getInventory().deleteItem(memoryId, 1);
				player.getInventory().deleteItem(energyId, 5);
				player.getSkills().addDivinationXp(xp*3);
			} else {
				player.setNextAnimation(new Animation(-1));	
				stop();
				return;
			}
		}
		if(!player.getInventory().containsItem(memoryId, 1)) {
				player.setNextAnimation(new Animation(-1));		 
					 stop();		 
						}
			    return;
		    }
		}, 0, 0);
		}
		public static void converytoenergy(final Player player, final int memoryId, final int energyId, final double xp) {
		final int[] reward = { 1, 2, 3, 4, 5 };
		final int xStart = player.getX();
		final int yStart = player.getY();
		player.sendMessage("You convert your memories into energy.");
		WorldTasksManager.schedule(new WorldTask() {
		    @Override
		    public void run() {
		if(player.getInventory().containsItem(memoryId, 1)) {
			int x = player.getX();
			int y = player.getY();
			if((xStart == x) && (yStart == y)) {
				player.setNextAnimation(new Animation(21234));
				player.setNextGraphics(new Graphics(4239));
				player.getInventory().deleteItem(memoryId, 1);
				player.getInventory().addItem(energyId, reward[Utils.random(reward.length-1)]);
				player.getSkills().addDivinationXp(xp*2);
			} else {
				player.setNextAnimation(new Animation(-1));	
				stop();
				return;
			}
		}
		if(!player.getInventory().containsItem(memoryId, 1)) {
				player.setNextAnimation(new Animation(-1));		 
					 stop();		 
						}
			    return;
		    }
			}, 0, 0);
		}
	
		public static void convertexp(final Player player, final int memoryId, final double xp) {
		final int xStart = player.getX();
		final int yStart = player.getY();
		player.sendMessage("You convert your memories into experience.");
		WorldTasksManager.schedule(new WorldTask() {
		    @Override
		    public void run() {
		if(player.getInventory().containsItem(memoryId, 1)) {
			int x = player.getX();
			int y = player.getY();
			if((xStart == x) && (yStart == y)) {
				player.setNextAnimation(new Animation(21234));
				player.setNextGraphics(new Graphics(4239));
				player.getInventory().deleteItem(memoryId, 1);
				player.getSkills().addDivinationXp(xp*2);
			} else {
				player.setNextAnimation(new Animation(-1));	
				stop();
				return;
			}
		}
		if(!player.getInventory().containsItem(memoryId, 1)) {
				player.setNextAnimation(new Animation(-1));		 
					 stop();		 
						}
			    return;
		    	}
			}, 0, 0);
		}
	
		public static void beginharvest(final Player player, final NPC npc, final int np1) {
				WorldTasksManager.schedule(new WorldTask() {
					int ticks;
					@Override
					public void run() {
						ticks++;
						if(ticks == 1){
							if (!player.withinDistance(npc, 2)) {
								player.addWalkSteps(npc.getX(), npc.getY());
								return;
							}
							}
						if(ticks == 2){
							player.setNextAnimation(new Animation(21231));
						}
						if(ticks == 3) {
							Entity npc1 = World.spawnNPC(np1, npc, -1, true);
							final NPC npc2 = (NPC)npc1;
							player.faceEntity(npc1);
							player.setNextAnimation(new Animation(21231));
							player.addFreezeDelay(2700);
							npc.setLongRespawnTask();
							HarvestWisp.siphon(player, npc2);
							WorldTasksManager.schedule(new WorldTask() {
								int ticks;
								@Override
								public void run() {
									ticks++;
									if(ticks == 150) {
										npc2.finish();
										player.setNextAnimation(new Animation(21229));			
								}
									if(ticks == 151) {
										stop();
								}
								return;
							}
						}, 0, 0);
							
						}
						if(ticks == 5){
							player.setNextAnimation(new Animation(-1));
						}
						if(ticks == 16){
							
						}
						return;
					}
				}, 0, 0);
		}
		
		public void addXp(final Player player) {
			double totalXp = creatures.getExp();
			if(player.createdFlickeringBoon == true && player.createdBrightBoon == false && player.createdGlowingBoon == false 
					&& player.createdSparklingBoon == false && player.createdGleamingBoon == false && player.createdVibrantBoon == false
					&& player.createdLustrousBoon == false && player.createdBrilliantBoon == false && player.createdRadiantBoon == false
					&& player.createdLuminousBoon == false && player.createdIncandescentBoon == false) {
				if(creature.getId() == 18151) {
				player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18174){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18175){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
			} 
			else if(player.createdFlickeringBoon == true && player.createdBrightBoon == true && player.createdGlowingBoon == false 
					&& player.createdSparklingBoon == false && player.createdGleamingBoon == false && player.createdVibrantBoon == false
					&& player.createdLustrousBoon == false && player.createdBrilliantBoon == false && player.createdRadiantBoon == false
					&& player.createdLuminousBoon == false && player.createdIncandescentBoon == false && creature.getId() == 18151) {
				if(creature.getId() == 18153) {
				player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18176){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18175){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				
			} 
			else if(player.createdFlickeringBoon == true && player.createdBrightBoon == true && player.createdGlowingBoon == true 
					&& player.createdSparklingBoon == false && player.createdGleamingBoon == false && 	player.createdVibrantBoon == false
					&& player.createdLustrousBoon == false && player.createdBrilliantBoon == false && player.createdRadiantBoon == false
					&& player.createdLuminousBoon == false && player.createdIncandescentBoon == false && creature.getId() == 18151) {
				if(creature.getId() == 18155) {
				player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18178){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18177){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
			} 
			else if(player.createdFlickeringBoon == true && player.createdBrightBoon == true && player.createdGlowingBoon == true 
					&& player.createdSparklingBoon == true && player.createdGleamingBoon == false && player.createdVibrantBoon == false
					&& player.createdLustrousBoon == false && player.createdBrilliantBoon == false && player.createdRadiantBoon == false
					&& player.createdLuminousBoon == false && player.createdIncandescentBoon == false) {
				if(creature.getId() == 18157) {
				player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18180){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18179){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
			}
			else if(player.createdFlickeringBoon == true && player.createdBrightBoon == true && player.createdGlowingBoon == true 
					&& player.createdSparklingBoon == true && player.createdGleamingBoon == true && player.createdVibrantBoon == false
					&& player.createdLustrousBoon == false && player.createdBrilliantBoon == false && player.createdRadiantBoon == false
					&& player.createdLuminousBoon == false && player.createdIncandescentBoon == false) {
				if(creature.getId() == 18159) {
				player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18182){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18181){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
			}
			else if(player.createdFlickeringBoon == true && player.createdBrightBoon == true && player.createdGlowingBoon == true 
					&& player.createdSparklingBoon == true && player.createdGleamingBoon == true && player.createdVibrantBoon == true
					&& player.createdLustrousBoon == false && player.createdBrilliantBoon == false && player.createdRadiantBoon == false
					&& player.createdLuminousBoon == false && player.createdIncandescentBoon == false) {
				if(creature.getId() == 18161) {
				player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18184){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18183){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
			}
			else if(player.createdFlickeringBoon == true && player.createdBrightBoon == true && player.createdGlowingBoon == true 
					&& player.createdSparklingBoon == true && player.createdGleamingBoon == true && player.createdVibrantBoon == true
					&& player.createdLustrousBoon == true && player.createdBrilliantBoon == false && player.createdRadiantBoon == false
					&& player.createdLuminousBoon == false && player.createdIncandescentBoon == false) {
				if(creature.getId() == 18163) {
				player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18186){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18185){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
			}
			else if(player.createdFlickeringBoon == true && player.createdBrightBoon == true && player.createdGlowingBoon == true 
					&& player.createdSparklingBoon == true && player.createdGleamingBoon == true && player.createdVibrantBoon == true
					&& player.createdLustrousBoon == true && player.createdBrilliantBoon == true && player.createdRadiantBoon == false
					&& player.createdLuminousBoon == false && player.createdIncandescentBoon == false) {
				if(creature.getId() == 18165) {
				player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18188){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18187){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
			}
			else if(player.createdFlickeringBoon == true && player.createdBrightBoon == true && player.createdGlowingBoon == true 
					&& player.createdSparklingBoon == true && player.createdGleamingBoon == true && player.createdVibrantBoon == true
					&& player.createdLustrousBoon == true && player.createdBrilliantBoon == true && player.createdRadiantBoon == true
					&& player.createdLuminousBoon == false && player.createdIncandescentBoon == false) {
				if(creature.getId() == 18167) {
				player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18190){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18189){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
			}
			else if(player.createdFlickeringBoon == true && player.createdBrightBoon == true && player.createdGlowingBoon == true 
					&& player.createdSparklingBoon == true && player.createdGleamingBoon == true && player.createdVibrantBoon == true
					&& player.createdLustrousBoon == true && player.createdBrilliantBoon == true && player.createdRadiantBoon == true
					&& player.createdLuminousBoon == true && player.createdIncandescentBoon == false) {
				if(creature.getId() == 18169) {
				player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18192){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18193){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
			}
			else if(player.createdFlickeringBoon == true && player.createdBrightBoon == true && player.createdGlowingBoon == true 
					&& player.createdSparklingBoon == true && player.createdGleamingBoon == true && player.createdVibrantBoon == true
					&& player.createdLustrousBoon == true && player.createdBrilliantBoon == true && player.createdRadiantBoon == true
					&& player.createdLuminousBoon == true && player.createdIncandescentBoon == true) {
				if(creature.getId() == 18171) {
				player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18194){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
				else if(creature.getId() == 18195){
					player.getSkills().addDivinationXp(totalXp * 1.1);
				}
			} else {
					player.getSkills().addDivinationXp(totalXp);
				}
			}
		}