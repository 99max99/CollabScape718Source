package com.rs.game.player.actions;

import com.rs.game.Animation;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 *
 * @author Humid
 */
public class ConTable 
{
 public static int modifier = 1;
 public static void handleCon(Player p, int componentId) {
     if (componentId <=44 || componentId >= 61) {
         if (p.getInventory().containsItem(2347, 2)) {
             switch (componentId) {
            
               case 45: if (p.getInventory().containsItem(960, 3)) {
                   tableConfigs(p,"Rocking Chair", 96, 8500, 960, 2);
               } else {
                   tableCloseMsg(p, "2 planks");
               }
                   break;
                   
               case 46:
                   if (Level(p, 29) == true) {
                       if (p.getInventory().containsItem(8778, 2)) {
                           tableConfigs(p, "Oakbookcase", 189, 8512, 8778, 2);
                       } else {
                           tableCloseMsg(p, "2 Oak planks");
                       }
                   }
                   break;
               case 47:
                   if (Level(p, 36) == true) {
                       if (p.getInventory().containsItem(8778, 2) && p.getInventory().containsItem(2353, 2)) {
                            tableConfigs(p, "Dragon Bitter barrel", 224, 8524, 8778, 2);
								p.getInventory().deleteItem(2353, 1);
								} else {
									tableCloseMsg(p, "2 Oak planks and 1 Steel bar");
								}
							}
					break;
               case 48:
                   if(Level(p, 52) ==true) {
                       if (p.getInventory().containsItem(8780, 4)) {
                           tableConfigs(p, "Teak kitchen table", 270, 8532, 8780, 3);
								} else {
									tableCloseMsg(p, "3 Teak planks");
								}
							}
					break;
               case 49:
					if (Level(p, 52) == true) {
						if (p.getInventory().containsItem(8782, 7)) {
								tableConfigs(p, "Mahogany table", 840, 8558, 8782, 6);
								} else {
									tableCloseMsg(p, "6 Mahogany planks");
								}
							}
					break;
					
					case 50:
					if (Level(p, 61) == true) {
						if (p.getInventory().containsItem(8782, 5) && p.getInventory().containsItem(8784, 5)) {
								tableConfigs(p, "Gilded bench", 1760, 8574, 8782, 4);
								p.getInventory().deleteItem(8784, 4);
								} else {
									tableCloseMsg(p, "4 Mahogany planks and 4 Gold leaves");
								}
							}
					break;
					
					case 51:
					if (Level(p, 53) == true) {
						if (p.getInventory().containsItem(8782, 4)) {
                                                    
						tableConfigs(p, "Mahogany 4-Poster", 450, 8586, 8782, 3);
							} else {
							tableCloseMsg(p, "3 Mahogany planks");
							}
						}
					break;
					
					case 52:
					if (Level(p, 64) == true) {
						if (p.getInventory().containsItem(8782, 3)) {
						tableConfigs(p, "Mahogany dresser", 281, 8606, 8782, 2);
							} else {
							tableCloseMsg(p, "2 Mahogany planks");
							}
						}
					break;
					
					case 53: 
					if (Level(p, 87) == true) {
						if (p.getInventory().containsItem(8782, 4) && p.getInventory().containsItem(8784, 2)) {
						tableConfigs(p, "Gilded wardrobe", 720, 8622, 8782, 3);
						p.getInventory().deleteItem(8784, 1);
							} else {
							tableCloseMsg(p, "3 Mahogany planks and 1 Gold leaf");
							}
						}
					break;
					
					case 54:
					if (Level(p, 85) == true) {
						if (p.getInventory().containsItem(8782, 3) && p.getInventory().containsItem(8784, 2)) {
								tableConfigs(p, "Gilded clock", 602, 8594, 8782, 2);
								p.getInventory().deleteItem(8784, 1);
								} else {
									tableCloseMsg(p, "2 Mahogany planks and 1 Gold leaf");
								}
							}
					break;
					
					case 55:
					if (Level(p, 90) == true) {
						if (p.getInventory().containsItem(8786, 2)) {
							tableConfigs(p, "Marble cape rack", 500, 9847, 8786, 1);
								} else {
								tableCloseMsg(p, "1 Marble block");
								}
						}
					break;
					
					case 56:
					if (Level(p, 96) == true) {
                                                    if (p.getInventory().containsItem(8786, 2)) {
						tableConfigs(p, "Marble magic wardrobe", 550, 9858, 8786, 1);
							} else {
							tableCloseMsg(p, "1 Marble block");
							}
						}
					break;
					
					case 57:
					if (Level(p, 82) == true) {
						if (p.getInventory().containsItem(8782, 4)) {
						tableConfigs(p, "Mahogany armour case", 420, 9861, 8782, 3);
							} else {
							tableCloseMsg(p, "3 Mahogany planks");
							}
						}
					break;
					
					case 58:
					if (Level(p, 84) == true) {
						if (p.getInventory().containsItem(8782, 3)) {
						tableConfigs(p, "Mahogany treasure chest", 280, 9864, 8782, 2);
							} else {
							tableCloseMsg(p, "2 Mahogany planks");
							}
						}
					break;
					
					case 59:
					if (Level(p, 80) == true) {
						if (p.getInventory().containsItem(8782, 3)) {
						tableConfigs(p, "Mahogany costume box", 280, 9867, 8782, 2);
							} else {
							tableCloseMsg(p, "2 Mahogany planks");
							}
						}
					break;
					
					case 60:
					if (Level(p, 80) == true) {
						if (p.getInventory().containsItem(8782, 3)) {
						tableConfigs(p, "Mahogany toy box", 280, 9851, 8782, 2);
							} else {
							tableCloseMsg(p, "2 Mahogany planks");
							}
						}
					break;
             }
              } else {
    p.sm("You must have a saw and hammer in your inventory to construct this item.");
         }
     }
  
 }
public static void tableConfigs(Player p, String name, int exp, int id, int plank, int amount)	{
    p.getInventory().deleteItem(plank, amount);
    p.closeInterfaces();
    p.getInventory().addItem(id, 11);
    //animate/p.setNextAnimation(7276);
      p.setNextAnimation(new Animation(7276));
    p.getSkills().addXp(22, exp * modifier);
    p.sm("you have packed a new " + name + "!");
}
public static void tableCloseMsg(Player p, String string) {
    p.closeInterfaces();
    p.sm("Minimum " + string + " required");
}
public static boolean Level(Player p, int lvl) {
    if (p.getSkills().getLevel(Skills.CONSTRUCTION) >= lvl) {
        return true;
    } else {
        lvlClose(p, lvl);// close
        return false;
    }
}

public static void lvlClose(Player p, int lvl) { //made this before "Level" boolean, so.. I kept it lol.
				// close inter
				p.getPackets().sendGameMessage("Level " + lvl + " Construction is required to construct this item.");
                                
		}
}