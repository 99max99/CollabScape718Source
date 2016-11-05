package com.rs.game.player.actions.slayer;

import java.io.Serializable;

import com.rs.game.npc.NPC;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;

/**
 * Slayer is a members-only skill that allows players to kill monsters which are
 * often otherwise immune to damage. Slayer was introduced on 26 January 2005.
 * Players get a Slayer task from one of seven Slayer Masters, and players gain
 * Slayer experience for killing monsters that they are assigned.
 *
 * @author Emperial
 *
 */
public class SlayerTask implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -7184740247844324413L;
    /**
     * The players current assigned task
     */
    private SlayerTasks currentTask;
    /*
     * Player level for slayer task
     */
    private SlayerTasks level;
    /**
     * The monsters left.
     */
    private int monstersLeft = -1;

    /**
     * Slayer Level for Task
     */
    public SlayerTasks getLevel() {
        return level;
    }

    public void setLevel(SlayerTasks lvl) {
        level = lvl;
    }
    
	public int getTaskAmount() {
		return monstersLeft;
	}

	public void decreaseAmount() {
		monstersLeft--;
	}

    /**
     * The slayer task
     *
     * @return
     */
    public SlayerTasks getTask() {
        return currentTask;
    }

    /**
     * The monsters left to kill
     */
    public int getTaskMonstersLeft() {
        return monstersLeft;
    }

    /**
     * Sets the player current task
     *
     * @param task
     */
    public void setTask(SlayerTasks task) {
        currentTask = task;
    }

    /**
     * Sets monsters left to kill
     *
     * @param i
     */
    public void setMonstersLeft(int i) {
        monstersLeft = i;
    }
    

    /**
     * Called on npc death if part of task.
     */
    public void onMonsterDeath(Player player, NPC n) {
    	
        player.getSkills().addXp(Skills.SLAYER, (n.getCombatDefinitions().getHitpoints()/10));
        monstersLeft--;
        int[] checkpoints = new int[]{1, 2, 3, 5, 10, 15, 20, 25, 30, 35, 40, 45, 50};
        for (int i : checkpoints) {
            if (monstersLeft == i) {
                player.getPackets().sendGameMessage(
                        "You're doing great, Only " + monstersLeft + " "
                        + getTask().simpleName + " left to slay.");
                player.hasTask = true;
            }
        }
        if (monstersLeft < 1) {
            player.setSlayerTaskAmount(player.getSlayerTaskAmount() + 1);
            int[] get50Points = new int[]{10, 20, 30, 40, 60, 70, 80, 90, 110, 120, 130, 140, 150, 160, 170,
                180, 190, 210, 220, 230, 240, 260, 270, 280, 290, 310, 320, 330, 340, 350, 360, 370, 380, 390, 410, 420,
                430, 440, 460, 470, 480, 490};
            int[] get100Points = new int[]{50, 150, 250, 350, 450, 550};
            int[] get500Points = new int[]{100, 200, 300, 400, 500, 600};
            int[] get10Points = new int[]{10, 20, 30, 40, 60, 70, 80, 90, 110, 120, 130, 140, 150, 160, 170,
                180, 190, 210, 220, 230, 240, 260, 270, 280, 290, 310, 320, 330, 340, 350, 360, 370, 380, 390, 410, 420,
                430, 440, 460, 470, 480, 490, 50, 150, 250, 350, 450, 550, 100, 200, 300, 400, 500, 600};
            for (int ii : get50Points) {
                if (player.getSlayerTaskAmount() == ii) {
                    if (player.getSlayerMaster() == 1) {
                       // player.setSlayerPoints(player.getSlayerPoints() + 3);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 3 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 2) {
                        //player.setSlayerPoints(player.getSlayerPoints() + 5);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 5 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 3) {
                       // player.setSlayerPoints(player.getSlayerPoints() + 38);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 38 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 4) {
                        //player.setSlayerPoints(player.getSlayerPoints() + 60);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 60 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 5) {
                        //player.setSlayerPoints(player.getSlayerPoints() + 125);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 125 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    }
                }
            }
            for (int iii : get100Points) {
                if (player.getSlayerTaskAmount() == iii) {
                    if (player.getSlayerMaster() == 1) {
                        //player.setSlayerPoints(player.getSlayerPoints() + 5);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 5 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 2) {
                       // player.setSlayerPoints(player.getSlayerPoints() + 10);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 10 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 3) {
                        //player.setSlayerPoints(player.getSlayerPoints() + 75);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 75 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 4) {
                       // player.setSlayerPoints(player.getSlayerPoints() + 125);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 125 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 5) {
                        //player.setSlayerPoints(player.getSlayerPoints() + 250);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 250 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    }
                }
            }
            for (int iiii : get500Points) {
                if (player.getSlayerTaskAmount() == iiii) {
                    if (player.getSlayerMaster() == 1) {
                       // player.setSlayerPoints(player.getSlayerPoints() + 10);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 10 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 2) {
                       // player.setSlayerPoints(player.getSlayerPoints() + 15);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 15 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 3) {
                       // player.setSlayerPoints(player.getSlayerPoints() + 150);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 150 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 4) {
                      //  player.setSlayerPoints(player.getSlayerPoints() + 250);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 250 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 5) {
                       // player.setSlayerPoints(player.getSlayerPoints() + 500);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 500 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    }
                }
            }
            for (int iiiii : get10Points) {
                if (player.getSlayerTaskAmount() != iiiii && player.hasTask == true) {
                    if (player.getSlayerMaster() == 1) {
                       // player.setSlayerPoints(player.getSlayerPoints() + 1);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 1 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 2) {
                       // player.setSlayerPoints(player.getSlayerPoints() + 2);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 2 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 3) {
                        //player.setSlayerPoints(player.getSlayerPoints() + 10);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 10 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 4) {
                        //player.setSlayerPoints(player.getSlayerPoints() + 15);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 15 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    } else if (player.getSlayerMaster() == 5) {
                        //player.setSlayerPoints(player.getSlayerPoints() + 18);
                        player.getPackets().sendGameMessage("You have completed " + player.slayerTaskAmount + " Slayer Tasks in a row and receive 18 Slayer Points!");
                        player.getPackets().sendGameMessage("You have finished your slayer task, talk to a slayer master for a new one.");
                        player.hasTask = false;
                    }
                }
            }
        }
    }
}