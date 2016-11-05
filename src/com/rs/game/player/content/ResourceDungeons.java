package com.rs.game.player.content;


import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.content.magic.Magic;

public class ResourceDungeons {

        public static boolean handleObjects(final Player player, int objectId) {
                        if (objectId == 52859) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 85) {
                                        Magic.resourcesTeleport(player, 1297, 4510, 0);
                                } else {
                                        player.sm("You need a dungeoneering level of 85 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52875) { 
                                Magic.resourcesTeleport(player, 3033, 9599, 0);
                                return true;
                        }
                        if (objectId == 52855) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 15) {
                                        Magic.resourcesTeleport(player, 1041, 4575, 0);
                                } else {
                                        player.sm("You need a dungeoneering level of 15 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52864) {
                                Magic.resourcesTeleport(player, 3034, 9772, 0);
                                return true;
                        }
                        if (objectId == 52860) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 75) {
                                        Magic.resourcesTeleport(player, 1182, 4515, 0);
                                } else {
                                        player.sm("You need a dungeoneering level of 75 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52872) {
                                Magic.resourcesTeleport(player, 3298, 3307, 0);
                                return true;
                        }
                        
                        if (objectId == 52849) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 10) {
                                        Magic.resourcesTeleport(player, 991, 4585, 0);
                                } else {
                                player.sm("You need a dungeoneering level of 10 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52867) {
                                Magic.resourcesTeleport(player, 3132, 9933, 0);
                                return true;
                        }
                        
                        if (objectId == 52853) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 20) {
                                        Magic.resourcesTeleport(player, 1134, 4589, 0);
                                } else {
                                player.sm("You need a dungeoneering level of 20 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52868) {
                                Magic.resourcesTeleport(player, 3104, 9826, 0);
                                return true;
                        }
                        
                        if (objectId == 52850) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 25) {
                                        Magic.resourcesTeleport(player, 1186, 4598, 0);
                                } else {
                                player.sm("You need a dungeoneering level of 25 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52869) {
                                Magic.resourcesTeleport(player, 2845, 9557, 0);
                                return true;
                        }
                        
                        if (objectId == 52861) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 30) {
                                        Magic.resourcesTeleport(player, 3498, 3633, 0);
                                } else {
                                player.sm("You need a dungeoneering level of 30 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52862) {
                                Magic.resourcesTeleport(player, 3513, 3666, 0);
                                return true;
                        }
                        
                        if (objectId == 52857) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 35) {
                                        Magic.resourcesTeleport(player, 1256, 4592, 0);
                                } else {
                                player.sm("You need a dungeoneering level of 35 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52873) {
                                Magic.resourcesTeleport(player, 2578, 9898, 0);
                                return true;
                        }
                        
                        if (objectId == 52856) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 45) {
                                        Magic.resourcesTeleport(player, 1052, 4521, 0);
                                } else {
                                player.sm("You need a dungeoneering level of 45 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52866) {
                                Magic.resourcesTeleport(player, 3022, 9741, 0);
                                return true;
                        }
                        
                        if (objectId == 52851) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 55) {
                                        Magic.resourcesTeleport(player, 1394, 4588, 0);
                                } else {
                                player.sm("You need a dungeoneering level of 55 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52870) {
                                Magic.resourcesTeleport(player, 2854, 9841, 0);
                                return true;
                        }
                        
                        if (objectId == 52852) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 60) {
                                        Magic.resourcesTeleport(player, 1000, 4522, 0);
                                } else {
                                player.sm("You need a dungeoneering level of 60 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52865) {
                                Magic.resourcesTeleport(player, 2912, 9810, 0);
                                return true;
                        }
                        
                        if (objectId == 52863) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 65) {
                                        Magic.resourcesTeleport(player, 1312, 4590, 0);
                                } else {
                                player.sm("You need a dungeoneering level of 65 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52876) {
                                Magic.resourcesTeleport(player, 3164, 9878, 0);
                                return true;
                        }
                        
                        if (objectId == 52858) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 70) {
                                        Magic.resourcesTeleport(player, 1238, 4524, 0);
                                } else {
                                player.sm("You need a dungeoneering level of 70 to unlock this resource dungeon.");
                                }
                                return true;
                        }
                        if (objectId == 52874) {
                                Magic.resourcesTeleport(player, 3160, 5521, 0);
                                return true;
                        }
                        
                        if (objectId == 77579) {
                                if (player.getSkills().getLevel(Skills.DUNGEONEERING) >= 80) {
                                        Magic.resourcesTeleport(player, 1140, 4499, 0);
                                } else {
                                        player.sm("You need a dungeoneering level of 80 to unlock this resource dungeon.");
                                }
                                return true;
                                }
                        if (objectId == 77580) {
                                Magic.resourcesTeleport(player, 2697, 9442, 0);
                                return true;
                        }
                return false;
        }
}