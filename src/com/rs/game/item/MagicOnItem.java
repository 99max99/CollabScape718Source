package com.rs.game.item;


import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.crafting.Enchanting;
import com.rs.utils.EconomyPrices;


public class MagicOnItem {


    public static final int LOW_ALCHEMY = 38;
    public static final int HIGH_ALCHEMY = 59;
    public static final int SUPER_HEAT = 50;
    public static final int LV1_ENCHANT = 29;
    public static final int LV2_ENCHANT = 41;
    public static final int LV3_ENCHANT = 53;
    public static final int LV4_ENCHANT = 61;
    public static final int LV5_ENCHANT = 76;
    public static final int LV6_ENCHANT = 88;

    public static void handleMagic(Player player, int magicId, Item item) {
        int itemId = item.getId();
        switch (magicId) {
        
            case LOW_ALCHEMY:
                processAlchemy(player, item, true);
                break;
                
            case HIGH_ALCHEMY:
                processAlchemy(player, item, false);
                break;
                
            case SUPER_HEAT:
                break;
                
            case LV1_ENCHANT:
                Enchanting.startEnchant(player, itemId, 1);
                break;
                
            case LV2_ENCHANT:
                Enchanting.startEnchant(player, itemId, 2);
                break;
                
            case LV3_ENCHANT:
                Enchanting.startEnchant(player, itemId, 3);
                break;
                
            case LV4_ENCHANT:
                Enchanting.startEnchant(player, itemId, 4);
                break;
                
            case LV5_ENCHANT:
                Enchanting.startEnchant(player, itemId, 5);
                break;
                
            case LV6_ENCHANT:
                Enchanting.startEnchant(player, itemId, 6);
                break;

            default:
                player.sendMessage("Invalid Magic Id: "+magicId+"");
                break;
        }
    }
    
    public static void processAlchemy(Player player, Item item, boolean low) {
        if (player.getSkills().getLevel(Skills.MAGIC) < (low == true ? 21 : 55)) {
            player.getPackets().sendGameMessage("You do not have the required level to cast this spell.");
            return;
        }
        if (item.getId() == 995) {
            player.getPackets().sendGameMessage("You can't alch this!");
            return;
        }
        if (!player.getInventory().containsItem(561, 1) || !player.getInventory().containsItem(554, (low == true ? 3 : 5))) {
            player.getPackets().sendGameMessage("You do not have the required runes to cast this spell.");
            return;
        }
        player.setNextAnimation(new Animation(713));
        player.setNextGraphics(new Graphics(113));
        player.getInventory().deleteItem(561, 1);
        player.getInventory().deleteItem(554, (low == true ? 3 : 5));
        player.getInventory().deleteItem(item.getId(), 1);
        int baseValue = item.getDefinitions().getTipitPrice();
        int value = baseValue / (low == true ? 2 : 5);
        player.getInventory().addItemMoneyPouch(new Item(995, value));
        player.getSkills().addXp(Skills.MAGIC, 10);
    }
    
}