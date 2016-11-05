package com.rs.game.player.content;

import com.rs.cache.loaders.ItemDefinitions;
import com.rs.game.Entity;
import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.npc.fightkiln.HarAken;
import com.rs.game.npc.fightkiln.HarAkenTentacle;
import com.rs.game.npc.godwars.zaros.NexMinion;
import com.rs.game.npc.pest.PestPortal;
import com.rs.game.npc.qbd.QueenBlackDragon;

public final class Combat {

	public static boolean hasAntiDragProtection(Entity target) {
		if (target instanceof NPC)
			return false;
		Player p2 = (Player) target;
		int shieldId = p2.getEquipment().getShieldId();
		return shieldId == 1540 || shieldId == 11283 || shieldId == 11284 || shieldId == 29957;
	}
	
    private boolean forceCheckClipAsRange(Entity target) {
	return target instanceof PestPortal || target instanceof NexMinion || target instanceof HarAken || target instanceof HarAkenTentacle || target instanceof QueenBlackDragon;
    }

	public static int getSlayerLevelForNPC(int id) {
		switch (id) {
		case 9463:
			return 93;
case 14696:
			return 95;
case 14697:
			return 95;
case 14698:
			return 95;
case 14699:
			return 95;
case 13822:
			return 91;
case 2783:
			return 90;

case 14688:
			return 88;
case 14689:
			return 88;
case 13821:
			return 86;
case 1615:
			return 85;
case 6278:
			return 83;
case 6221:
			return 83;
case 6231:
			return 83;
case 6257:
			return 83;
case 14700:
			return 82;
case 14701:
			return 82;
case 13820:
			return 80;
case 1613:
			return 80;
case 9172:
			return 78;
case 9465:
			return 77;
case 1610:
			return 75;
case 9467:
			return 73;
case 3068:
			return 72;
case 3069:
			return 72;
case 3070:
			return 72;
case 3071:
			return 72;
case 1608:
			return 70;
case 1609:
			return 70;
case 6219:
			return 68;
case 6229:
			return 68;
case 6255:
			return 68;
case 6277:
			return 68;
case 1624:
			return 65;
case 10700:
			return 63;
case 6276:
			return 63;
case 6256:
			return 63;
case 6230:
			return 63;
case 6220:
			return 63;
case 1604:
			return 60;
case 1605:
			return 60;
case 1606:
			return 60;
case 1607:
			return 60;
case 4353:
			return 58;
case 4354:
			return 58;
case 4355:
			return 58;
case 4356:
			return 58;
case 4357:
			return 58;
case 3346:
			return 57;
case 3347:
			return 57;
case 6296:
			return 56;
case 6285:
			return 56;
case 6286:
			return 56;
case 6287:
			return 56;
case 6288:
			return 56;
case 6289:
			return 56;
case 6290:
			return 56;
case 6291:
			return 56;
case 6292:
			return 56;
case 6293:
			return 56;
case 6294:
			return 56;
case 6295:
			return 56;
case 1623:
			return 55;
case 1637:
			return 52;
case 1638:
			return 52;
case 1639:
			return 52;
case 1640:
			return 52;
case 1641:
			return 52;
case 1642:
			return 52;
case 7462:
			return 50;
case 7463:
			return 50;
case 1618:
			return 50;
case 1643:
			return 45;
case 1644:
			return 45;
case 1645:
			return 45;
case 1646:
			return 45;
case 1647:
			return 45;
case 1616:
			return 40;
case 1617:
			return 40;
case 114:
			return 32;
case 7823:
			return 35;
case 3201:
			return 37;
case 3202:
			return 37;
case 3153:
			return 33;
case 1633:
			return 30;
case 1634:
			return 30;
case 1635:
			return 30;
case 1636:
			return 30;
case 1620:
			return 25;
case 2804:
			return 22;
case 2805:
			return 22;
case 2806:
			return 22;
case 1631:
			return 20;
case 1632:
			return 20;
case 1831:
			return 17;
case 1612:
			return 15;
case 1600:
			return 10;
case 1601:
			return 10;
case 1602:
			return 10;
case 1603:
			return 10;
case 1832:
			return 7;
case 1648:
			return 5;
case 1649:
			return 5;
case 1650:
			return 5;
case 1651:
			return 5;
case 1652:
			return 5;
case 1653:
			return 5;
case 1654:
			return 5;
case 1655:
			return 5;
case 1656:
			return 5;
case 1657:
			return 5;

		default:
			return 0;
		}
	}

	public static int getDefenceEmote(Entity target) {
		if (target instanceof NPC) {
			NPC n = (NPC) target;
			return n.getCombatDefinitions().getDefenceEmote();
		} else {
			Player p = (Player) target;
			int shieldId = p.getEquipment().getShieldId();
			String shieldName = shieldId == -1 ? null : ItemDefinitions
					.getItemDefinitions(shieldId).getName().toLowerCase();
			if (shieldId == -1
					|| (shieldName.contains("book") && shieldId != 18346)) {
				int weaponId = p.getEquipment().getWeaponId();
				if (weaponId == -1)
					return 424;
				String weaponName = ItemDefinitions
						.getItemDefinitions(weaponId).getName().toLowerCase();
				if (weaponName != null && !weaponName.equals("null")) {
					if (weaponName.contains("scimitar")
							|| weaponName.contains("korasi sword"))
						return 15074;
					if(weaponName.contains("noxious scythe") || weaponName.contains("halberd"))
						return 18294;
					if (weaponName.contains("whip"))
						return 11974;
					if (weaponName.contains("staff of light"))
						return 12806;
					if (weaponName.contains("longsword")
							|| weaponName.contains("darklight")
							|| weaponName.contains("silverlight")
							|| weaponName.contains("excalibur"))
						return 388;
					if (weaponName.contains("dagger"))
						return 378;
					if (weaponName.contains("rapier"))
						return 13038;
					if (weaponName.contains("pickaxe"))
						return 397;
					if (weaponName.contains("mace"))
						return 403;
					if (weaponName.contains("claws"))
						return 404;
					if (weaponName.contains("hatchet"))
						return 397;
					if (weaponName.contains("greataxe"))
						return 12004;
					if (weaponName.contains("wand"))
						return 415;
					if (weaponName.contains("chaotic staff"))
						return 13046;
					if (weaponName.contains("staff"))
						return 420;
					if (weaponName.contains("warhammer")
							|| weaponName.contains("tzhaar-ket-em"))
						return 403;
					if (weaponName.contains("maul")
							|| weaponName.contains("tzhaar-ket-om"))
						return 1666;
					if (weaponName.contains("zamorakian spear"))
						return 12008;
					if (weaponName.contains("spear")
							|| weaponName.contains("halberd")
							|| weaponName.contains("hasta"))
						return 430;
					if (weaponName.contains("2h sword")
							|| weaponName.contains("godsword")
							|| weaponName.equals("saradomin sword"))
						//return 7050;
						return 18293;
					if(weaponName.contains("bow"))
						return 18299;
				}
				return 424;
			}
			if (shieldName != null) {
				int weaponId = p.getEquipment().getWeaponId();
				String weaponName = ItemDefinitions
						.getItemDefinitions(weaponId).getName().toLowerCase();
				if(shieldName.contains("shield") && (p.getAppearence().getRenderEmote() == 2687 || p.getAppearence().getRenderEmote() == 2694))
					return 18345;
				if (shieldName.contains("shield"))
					return 1156;
				if (shieldName.contains("defender"))
					return 4177;
				if(shieldName.contains("off-hand"))
					return 18299;
			}
			switch (shieldId) {
			case -1:
			default:
				return 424;
			}
		}
	}

	private Combat() {
	}
}
