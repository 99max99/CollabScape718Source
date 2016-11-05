package com.rs.game.player.actions.slayer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.rs.game.player.Player;
import com.rs.utils.Utils;

public class Slayer {

    Player player;

    public static void handlePointsInterface(final Player p, int interfaceId, int buttonId) {
        //int currentPoints = p.getSlayerPoints();
        switch (interfaceId) {
            case 161:
                switch (buttonId) {
                    case 14: // "Learn" button.
                        p.getPackets().sendGameMessage("Available soon.");
                        break;

                    case 15: // "Buy" button.
                        p.getPackets().sendGameMessage("Available soon.");
                        break;
                    case 26: // Cancel Slayer Task.
                    case 23:
                       // if (currentPoints < 15) {
                       //     p.getPackets().sendGameMessage("You need atleast 15 Slayer points to cancel your Slayer task.");
                            break;
                      //  }
                      //  if (p.hasTask == false) {
                      //      p.getPackets().sendGameMessage("You are not currently assigned a Slayer task.");
                       //     break;
                       // }
                       //p.setSlayerPoints(currentPoints - 15);
                       // displayPoints(p, 1);
                       // p.getPackets().sendGameMessage("You have cancelled your slayer task.");
                       // p.hasTask = false;

                    case 24: // Permanently remove current task.
                        p.getPackets().sendGameMessage("Currently Under Development.");
                        break;
                }
                break;
        }
    }
    public static void displayPoints(Player p, int status) {
        //p.defaultSettings();
        switch (status) {
            case 1:
               // p.getPackets().sendIComponentText(161, 19, " " + p.getSlayerPoints());
                p.getPackets().sendIComponentText(161, 23, "Cancel your current assignment.");
                p.getPackets().sendIComponentText(161, 24, "Permentatly block current assignment.");
                p.getPackets().sendIComponentText(161, 26, "15 Points");
                p.getPackets().sendIComponentText(161, 27, "50 Points");
                p.getInterfaceManager().sendInterface(161);
                break;

        }
    }

    /**
     * 0 = Hello 1 = Option
     *
     * OTHER 2 = For your first task I'm assigning you to 3 = You still have a
     * task 4 = Great your doing great, Your new task is
     */
    public static String assignTask(Player player, SlayerMaster master) {
        player.hasTask = true;
        SlayerTask tasks = player.slayerTask;
        checkLevel(player, master);
        return tasks.getTaskMonstersLeft() + " " + tasks.getTask().simpleName;
    }

    public static void checkLevel(Player player, SlayerMaster master) {
        SlayerTask tasks = player.slayerTask;
        List<SlayerTasks> possibleTasks = new ArrayList<SlayerTasks>();
        for (SlayerTasks task : SlayerTasks.values()) {
            if (task.type == master.type) {
                possibleTasks.add(task);
            }
        }
        if (possibleTasks.isEmpty()) {
            return;
        }
        for (int i = 0; i < 10; i++) {
            SlayerTasks task = possibleTasks.get(i);
            Collections.shuffle(possibleTasks);
            if (player.getSkills().getLevel(18) < task.level) {
                player.getPackets().sendGameMessage("Too low of a slayer level...");
                Collections.shuffle(possibleTasks);
            } else {
                tasks.setTask(task);
                player.setSlayerMaster(master.masterID);
                tasks.setMonstersLeft(Utils.random(tasks.getTask().min, tasks.getTask().max));
            }
        }
    }
}
