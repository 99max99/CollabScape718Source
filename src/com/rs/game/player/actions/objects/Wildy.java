package com.rs.game.player.actions.objects;

import com.rs.game.Animation;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.player.Player;
import com.rs.game.player.content.magic.Magic;

/*
 * @Author Danny
 * Wilderness
 */

public class Wildy {
	
	public static void EssencePortal(Player player,
			final WorldObject object) {
		Magic.sendNormalTeleportSpell(player, 0, 0, new WorldTile(3253, 3401, 0));
	}
	
	public static void MiningCart(Player player,
			final WorldObject object) {
		player.getDialogueManager().startDialogue("Conductor", 2180);
	}
	
	public static void ChaosTunnelPortals(Player player,
			final WorldObject object) {

				if (object.getX() == 3287 && object.getY() == 5448) {
			player.setNextWorldTile(new WorldTile(3283, 5448, 0));
			
			} else if (object.getX() == 3283 && object.getY() == 5448) {
			player.setNextWorldTile(new WorldTile(3287, 5448, 0));
			
				} else if (object.getX() == 3307 && object.getY() == 5496) {
			player.setNextWorldTile(new WorldTile(3317, 5496, 0));
		
		} else if (object.getX() == 3317 && object.getY() == 5496) {
			player.setNextWorldTile(new WorldTile(3307, 5496, 0));
		
		} else if (object.getX() == 3318 && object.getY() == 5481) {
			player.setNextWorldTile(new WorldTile(3322, 5480, 0));
		
		} else if (object.getX() == 3322 && object.getY() == 5480) {
			player.setNextWorldTile(new WorldTile(3318, 5481, 0));
		
		} else if (object.getX() == 3299 && object.getY() == 5484) {
			player.setNextWorldTile(new WorldTile(3303, 5477, 0));
		
		} else if (object.getX() == 3303 && object.getY() == 5477) {
			player.setNextWorldTile(new WorldTile(3299, 5484, 0));
		
		} else if (object.getX() == 3286 && object.getY() == 5470) {
			player.setNextWorldTile(new WorldTile(3285, 5474, 0));
		
		} else if (object.getX() == 3285 && object.getY() == 5474) {
			player.setNextWorldTile(new WorldTile(3286, 5470, 0));
		
		} else if (object.getX() == 3290 && object.getY() == 5463) {
			player.setNextWorldTile(new WorldTile(3302, 5469, 0));
		
		} else if (object.getX() == 3302 && object.getY() == 5469) {
			player.setNextWorldTile(new WorldTile(3290, 5463, 0));
		
		} else if (object.getX() == 3296 && object.getY() == 5455) {
			player.setNextWorldTile(new WorldTile(3299, 5450, 0));
		
		} else if (object.getX() == 3299 && object.getY() == 5450) {
			player.setNextWorldTile(new WorldTile(3296, 5455, 0));
		
		} else if (object.getX() == 3280 && object.getY() == 5501) {
			player.setNextWorldTile(new WorldTile(3285, 5508, 0));
		
		} else if (object.getX() == 3285 && object.getY() == 5508) {
			player.setNextWorldTile(new WorldTile(3280, 5501, 0));
		
		} else if (object.getX() == 3300 && object.getY() == 5514) {
			player.setNextWorldTile(new WorldTile(3297, 5510, 0));
		
		} else if (object.getX() == 3297 && object.getY() == 5510) {
			player.setNextWorldTile(new WorldTile(3300, 5514, 0));
		
		} else if (object.getX() == 3289 && object.getY() == 5533) {
			player.setNextWorldTile(new WorldTile(3288, 5536, 0));
		
		} else if (object.getX() == 3288 && object.getY() == 5536) {
			player.setNextWorldTile(new WorldTile(3289, 5533, 0));
		
		} else if (object.getX() == 3285 && object.getY() == 5527) {
			player.setNextWorldTile(new WorldTile(3282, 5531, 0));
		
		} else if (object.getX() == 3282 && object.getY() == 5531) {
			player.setNextWorldTile(new WorldTile(3285, 5527, 0));
		
		} else if (object.getX() == 3325 && object.getY() == 5518) {
			player.setNextWorldTile(new WorldTile(3323, 5531, 0));
		
		} else if (object.getX() == 3323 && object.getY() == 5531) {
			player.setNextWorldTile(new WorldTile(3325, 5518, 0));
		
		} else if (object.getX() == 3299 && object.getY() == 5533) {
			player.setNextWorldTile(new WorldTile(3297, 5536, 0));
		
		} else if (object.getX() == 3297 && object.getY() == 5536) {
			player.setNextWorldTile(new WorldTile(3299, 5533, 0));
		
		} else if (object.getX() == 3321 && object.getY() == 5554) {
			player.setNextWorldTile(new WorldTile(3315, 5552, 0));
		
		} else if (object.getX() == 3315 && object.getY() == 5552) {
			player.setNextWorldTile(new WorldTile(3321, 5554, 0));
		
		} else if (object.getX() == 3291 && object.getY() == 5555) {
			player.setNextWorldTile(new WorldTile(3285, 5556, 0));
		
		} else if (object.getX() == 3285 && object.getY() == 5556) {
			player.setNextWorldTile(new WorldTile(3291, 5555, 0));
		
		} else if (object.getX() == 3266 && object.getY() == 5552) {
			player.setNextWorldTile(new WorldTile(3262, 5552, 0));
		
		} else if (object.getX() == 3262 && object.getY() == 5552) {
			player.setNextWorldTile(new WorldTile(3266, 5552, 0));
		
		} else if (object.getX() == 3256 && object.getY() == 5561) {
			player.setNextWorldTile(new WorldTile(3253, 5561, 0));
		
		} else if (object.getX() == 3253 && object.getY() == 5561) {
			player.setNextWorldTile(new WorldTile(3256, 5561, 0));
		
		} else if (object.getX() == 3249 && object.getY() == 5546) {
			player.setNextWorldTile(new WorldTile(3252, 5543, 0));
		
		} else if (object.getX() == 3252 && object.getY() == 5543) {
			player.setNextWorldTile(new WorldTile(3249, 5546, 0));
		
		} else if (object.getX() == 3261 && object.getY() == 5536) {
			player.setNextWorldTile(new WorldTile(3268, 5534, 0));
		
		} else if (object.getX() == 3268 && object.getY() == 5534) {
			player.setNextWorldTile(new WorldTile(3261, 5536, 0));
		
		} else if (object.getX() == 3243 && object.getY() == 5526) {
			player.setNextWorldTile(new WorldTile(3241, 5529, 0));
		
		} else if (object.getX() == 3241 && object.getY() == 5529) {
			player.setNextWorldTile(new WorldTile(3243, 5526, 0));
		
		} else if (object.getX() == 3230 && object.getY() == 5547) {
			player.setNextWorldTile(new WorldTile(3226, 5553, 0));
		
		} else if (object.getX() == 3226 && object.getY() == 5553) {
			player.setNextWorldTile(new WorldTile(3230, 5547, 0));
		
		} else if (object.getX() == 3206 && object.getY() == 5553) {
			player.setNextWorldTile(new WorldTile(3204, 5546, 0));
		
		} else if (object.getX() == 3204 && object.getY() == 5546) {
			player.setNextWorldTile(new WorldTile(3206, 5553, 0));
		
		} else if (object.getX() == 3211 && object.getY() == 5533) {
			player.setNextWorldTile(new WorldTile(3214, 5533, 0));
		
		} else if (object.getX() == 3214 && object.getY() == 5533) {
			player.setNextWorldTile(new WorldTile(3211, 5533, 0));
		
		} else if (object.getX() == 3208 && object.getY() == 5527) {
			player.setNextWorldTile(new WorldTile(3211, 5523, 0));
		
		} else if (object.getX() == 3211 && object.getY() == 5523) {
			player.setNextWorldTile(new WorldTile(3208, 5527, 0));
		
		} else if (object.getX() == 3201 && object.getY() == 5531) {
			player.setNextWorldTile(new WorldTile(3197, 5529, 0));
		
		} else if (object.getX() == 3197 && object.getY() == 5529) {
			player.setNextWorldTile(new WorldTile(3201, 5531, 0));
		
		} else if (object.getX() == 3202 && object.getY() == 5515) {
			player.setNextWorldTile(new WorldTile(3196, 5512, 0));
		
		} else if (object.getX() == 3196 && object.getY() == 5512) {
			player.setNextWorldTile(new WorldTile(3202, 5515, 0));
		
		} else if (object.getX() == 3190 && object.getY() == 5515) {
			player.setNextWorldTile(new WorldTile(3190, 5519, 0));
		
		} else if (object.getX() == 3190 && object.getY() == 5519) {
			player.setNextWorldTile(new WorldTile(3190, 5515, 0));
		
		} else if (object.getX() == 3185 && object.getY() == 5518) {
			player.setNextWorldTile(new WorldTile(3181, 5517, 0));
		
		} else if (object.getX() == 3181 && object.getY() == 5517) {
			player.setNextWorldTile(new WorldTile(3185, 5518, 0));
		
		} else if (object.getX() == 3187 && object.getY() == 5531) {
			player.setNextWorldTile(new WorldTile(3182, 5530, 0));
		
		} else if (object.getX() == 3182 && object.getY() == 5530) {
			player.setNextWorldTile(new WorldTile(3187, 5531, 0));
		
		} else if (object.getX() == 3169 && object.getY() == 5510) {
			player.setNextWorldTile(new WorldTile(3159, 5501, 0));
		
		} else if (object.getX() == 3159 && object.getY() == 5501) {
			player.setNextWorldTile(new WorldTile(3169, 5510, 0));
		
		} else if (object.getX() == 3165 && object.getY() == 5515) {
			player.setNextWorldTile(new WorldTile(3173, 5530, 0));
		
		} else if (object.getX() == 3173 && object.getY() == 5530) {
			player.setNextWorldTile(new WorldTile(3165, 5515, 0));
		
		} else if (object.getX() == 3156 && object.getY() == 5523) {
			player.setNextWorldTile(new WorldTile(3152, 5520, 0));
		
		} else if (object.getX() == 3152 && object.getY() == 5520) {
			player.setNextWorldTile(new WorldTile(3156, 5523, 0));
		
		} else if (object.getX() == 3148 && object.getY() == 5533) {
			player.setNextWorldTile(new WorldTile(3153, 5537, 0));
		
		} else if (object.getX() == 3153 && object.getY() == 5537) {
			player.setNextWorldTile(new WorldTile(3148, 5533, 0));
		
		} else if (object.getX() == 3143 && object.getY() == 5535) {
			player.setNextWorldTile(new WorldTile(3147, 5541, 0));
		
		} else if (object.getX() == 3147 && object.getY() == 5541) {
			player.setNextWorldTile(new WorldTile(3143, 5535, 0));
		
		} else if (object.getX() == 3168 && object.getY() == 5541) {
			player.setNextWorldTile(new WorldTile(3171, 5542, 0));
		
		} else if (object.getX() == 3171 && object.getY() == 5542) {
			player.setNextWorldTile(new WorldTile(3168, 5541, 0));
		
		} else if (object.getX() == 3190 && object.getY() == 5549) {
			player.setNextWorldTile(new WorldTile(3190, 5554, 0));
		
		} else if (object.getX() == 3190 && object.getY() == 5554) {
			player.setNextWorldTile(new WorldTile(3190, 5549, 0));
		
		} else if (object.getX() == 3180 && object.getY() == 5557) {
			player.setNextWorldTile(new WorldTile(3174, 5558, 0));
		
		} else if (object.getX() == 3174 && object.getY() == 5558) {
			player.setNextWorldTile(new WorldTile(3180, 5557, 0));
		
		} else if (object.getX() == 3162 && object.getY() == 5557) {
			player.setNextWorldTile(new WorldTile(3158, 5561, 0));
		
		} else if (object.getX() == 3158 && object.getY() == 5561) {
			player.setNextWorldTile(new WorldTile(3162, 5557, 0));
		
		} else if (object.getX() == 3166 && object.getY() == 5553) {
			player.setNextWorldTile(new WorldTile(3162, 5545, 0));
		
		} else if (object.getX() == 3162 && object.getY() == 5545) {
			player.setNextWorldTile(new WorldTile(3166, 5553, 0));
			
			} else if (object.getX() == 3273 && object.getY() == 5460) {
			player.setNextWorldTile(new WorldTile(3280, 5460, 0));
			
			} else if (object.getX() == 3280 && object.getY() == 5460) {
			player.setNextWorldTile(new WorldTile(3273, 5460, 0));
			
		} else if (object.getX() == 3115 && object.getY() == 5528) {
			 player.setNextWorldTile(new WorldTile(3142, 5545, 0));
		} else  if (object.getX() == 3142 && object.getY() == 5545) {
			player.getControlerManager().startControler("BorkControler", 0, null);
		}
			player.setNextAnimation(new Animation(7312));
	}
	public static void ChaosTunnelsEntranceExit(Player player,
			final WorldObject object) {
	
		 if (object.getX() == 3183 && object.getY() == 5470) {
			 player.setNextWorldTile(new WorldTile(3059, 3549, 0));
		
		} else if (object.getX() == 3248 && object.getY() == 5490) {
			 player.setNextWorldTile(new WorldTile(3120, 3571, 0));
		
		} else if (object.getX() == 3292 && object.getY() == 5479) {
			 player.setNextWorldTile(new WorldTile(3166, 3561, 0));
		
		} else if (object.getX() == 3291 && object.getY() == 5538) {
			 player.setNextWorldTile(new WorldTile(3166, 3618, 0));
		
		} else if (object.getX() == 3234 && object.getY() == 5559) {
			 player.setNextWorldTile(new WorldTile(3107, 3640, 0));
			 
		} else if (object.getX() == 3118 && object.getY() == 3570) {
			 player.setNextWorldTile(new WorldTile(3248, 5490, 0));

		} else if (object.getX() == 3129 && object.getY() == 3587) {
			 player.setNextWorldTile(new WorldTile(3234, 5559, 0));
		}
		player.setNextAnimation(new Animation(828));
	}

	public static boolean isObject(final WorldObject object) {
		switch (object.getId()) {
		case 77745:
		case 28779:
		case 65203:
		case 28782:
		return true;
		default:
		return false;
		}
	}
	
	public static void HandleObject(Player player, final WorldObject object) {
		final int id = object.getId();
		if (id == 77745 || id == 28779) { 
			Wildy.ChaosTunnelPortals(player, object); 
		}
		if (id == 65203 || id == 28782) { 
			Wildy.ChaosTunnelsEntranceExit(player, object); 
		}
		
	}

}
