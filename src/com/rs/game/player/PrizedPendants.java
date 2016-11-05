package com.rs.game.player;

public class PrizedPendants {

	private Player player;
	
	public PrizedPendants(Player p) {
		this.player = p;
	}
	
	public enum Pendants {
		
		ATTACK_PENDANT(24714, Skills.ATTACK, 2.0),
		STRENGTH_PENDANT(24716, Skills.STRENGTH, 2.0),
		DEFENCE_PENDANT(24718, Skills.DEFENCE, 2.0),
		RANGING_PENDANT(24720, Skills.RANGE, 2.0),
		MAGIC_PENDANT(24722, Skills.MAGIC, 2.0),
		PRAYER_PENDANT(24724, Skills.PRAYER, 2.0),
		RUNECRAFTING_PENDANT(24726, Skills.RUNECRAFTING, 2.0),
		CONSTRUCTION_PENDANT(24728, Skills.CONSTRUCTION, 2.0),
		DUNGEONEERING_PENDANT(24730, Skills.DUNGEONEERING, 2.0),
		CONSTITUTION_PENDANT(24732, Skills.HITPOINTS, 2.0),
		AGILITY_PENDANT(24734, Skills.AGILITY, 2.0),
		HERBLORE_PENDANT(24736, Skills.HERBLORE, 2.0),
		THIEVERY_PENDANT(24738, Skills.THIEVING, 2.0),
		CRAFTING_PENDANT(24740, Skills.CRAFTING, 2.0),
		FLETCHING_PENDANT(24742, Skills.FLETCHING, 2.0),
		SLAYING_PENDANT(24744, Skills.SLAYER, 2.0),
		HUNTING_PENDANT(24746, Skills.HUNTER, 2.0),
		MINING_PENDANT(24748, Skills.MINING, 2.0),
		SMITHING_PENDANT(24750, Skills.SMITHING, 2.0),
		FISHING_PENDANT(24752, Skills.FISHING, 2.0),
		COOKING_PENDANT(24754, Skills.COOKING, 2.0),
		FIREMAKING_PENDANT(24756, Skills.FIREMAKING, 2.0),
		WOODCUTTING_PENDANT(24758, Skills.WOODCUTTING, 2.0),
		FARMING_PENDANT(24760, Skills.FARMING, 2.0),
		SUMMONING_PENDANT(24762, Skills.SUMMONING, 2.0);
		
		int id;
		int skill;
		double modifier;
		
		Pendants(int id, int skill, double modifier) {
			this.id = id;
			this.skill = skill;
			this.modifier = modifier;
		}
		
		public double getModifier() {
			return modifier;
		}
		public int getSkillId() {
			return skill;
		}
	}
	
	public Pendants forId(int itemId) {
		for (Pendants pendant : Pendants.values()) {
			if (player.getEquipment().getAmuletId() == pendant.id || itemId == pendant.id) {
				return pendant;
			}
		}
		return null;
	}
	
	public boolean hasAmulet() {
		Pendants pendant = forId(player.getEquipment().getAmuletId());
		if (pendant != null) {
			return true;
		}
		return false;
	}
	
	public double getModifier() {
		Pendants pendant = forId(player.getEquipment().getAmuletId());
		if (pendant != null) {
			return pendant.getModifier();
		}
		return 1.0;
	}
	
	public int getSkill() {
		Pendants pendant = forId(player.getEquipment().getAmuletId());
		if (pendant != null) {
			return pendant.getSkillId();
		}
		return -1;
	}
	
	public PrizedPendants() {
		
	}
	
}
