package com.rs.game.player.content;
 
import java.io.Serializable;
 
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.utils.Utils;
import com.rs.game.npc.*;
 
/**
 * @author Wolfey
 * @author Mystic Flow
 * @author Bandoswhips
 * @author Nexon/Fuzen Seth
 */
public class SlayerTask implements Serializable {
 
        /**
         *
         */
        private static final long serialVersionUID = -3885979679549716755L;
 
        public static void sendShop(Player player) {
        //player.getPackets().sendIComponentText(1308, 342, + player.getSlayerPoints() + "");
                player.getInterfaceManager().sendInterface(1308);
        }
         
         // Level, Lowest To Kill, Highest To Kill, XP Multiplier
       
        public enum Master {
                KURADAL(9085, new Object[][] //hardest
                                { { "General Graardor", 75, 5, 15, 350.0 },
                                { "Kree'arra", 75, 5, 15, 355.0 },
                                { "Cow", 1, 25, 125, 25.0 },
                                { "K'ril Tsutsaroth", 75, 5, 15, 275.0 },
                                { "Gargoyle", 75, 5, 15, 255.0 },
                                { "Dark beast", 75, 5, 15, 285.0 },
                                { "Corporal Beast", 75, 5, 15, 310.0 } }),
               
                SPRIA(8462, new Object[][] //easiest
                                { { "Cow", 1, 25, 125, 25.0 },
                                { "Rock Crab", 1, 25, 125, 25.0 },
                                { "Crawling hand", 1, 50, 125, 55.0 } }),
 
                MAZCHNA(8464, new Object[][] //middle
                                { { "Abyssal demon", 50, 25, 130, 155.0 },
                                { "Nechryael", 50, 45, 85, 112.0 },
                                { "Cow", 1, 25, 125, 25.0 },
                                { "Infernal mage", 50, 25, 60, 150.0 },
                                { "Ganodermic beast", 50, 50, 120, 165.0 },
                                { "Gargoyle", 50, 150, 200, 185.0 },
                                { "Jelly", 50, 25, 60, 175.0 },
                                { "Dark beast", 50, 40, 75, 200.0 },
                                { "Bloodveld", 50, 30, 100, 145.0 },
                                { "Aberrant spectre", 50, 30, 100, 115.0 } });
 
                /**VANNAKA(9085, new Object[][] { { "Crawling hand", 1, 50, 100, 100.0 },
                                { "Cow", 1, 25, 130, 112.0 },
                                { "Ghoul", 1, 25, 130, 115.0 },
                                { "Abyssal demon", 85, 25, 130, 285.0 },
                                { "Nechryael", 80, 45, 85, 112.0 },
                                { "Aberrant spectre", 60, 30, 100, 115.0 },
                                { "Infernal mage", 45, 25, 60, 150.0 },
                                { "Ganodermic beast", 95, 50, 120, 330 },
                                { "Gargoyle", 75, 150, 200, 200.0 },
                                { "Jelly", 1, 25, 60, 100.0 },
                                { "Dark beast", 90, 40, 75, 310.0 },
                                { "Bloodveld", 50, 30, 100, 145.0 } }),
 
                CHAELDAR(9085, new Object[][] { { "Crawling hand", 1, 50, 100, 100.0 },
                                { "Cow", 1, 25, 130, 112.0 },
                                { "Ghoul", 1, 25, 130, 115.0 },
                                { "Abyssal demon", 85, 25, 130, 285.0 },
                                { "Nechryael", 80, 45, 85, 112.0 },
                                { "Aberrant spectre", 60, 30, 100, 115.0 },
                                { "Infernal mage", 45, 25, 60, 150.0 },
                                { "Ganodermic beast", 95, 50, 120, 330 },
                                { "Gargoyle", 75, 150, 200, 200.0 },
                                { "Jelly", 1, 25, 60, 100.0 },
                                { "Dark beast", 90, 40, 75, 310.0 },
                                { "Bloodveld", 50, 30, 100, 145.0 } }),
 
                SUMONA(9085, new Object[][] { { "Crawling hand", 1, 50, 100, 100.0 },
                                { "Cow", 1, 25, 130, 112.0 },
                                { "Ghoul", 1, 25, 130, 115.0 },
                                { "Abyssal demon", 85, 25, 130, 285.0 },
                                { "Nechryael", 80, 45, 85, 112.0 },
                                { "Aberrant spectre", 60, 30, 100, 115.0 },
                                { "Infernal mage", 45, 25, 60, 150.0 },
                                { "Ganodermic beast", 95, 50, 120, 330 },
                                { "Gargoyle", 75, 150, 200, 200.0 },
                                { "Jelly", 1, 25, 60, 100.0 },
                                { "Dark beast", 90, 40, 75, 310.0 },
                                { "Bloodveld", 50, 30, 100, 145.0 } }),
 
                LAPALOK(9085, new Object[][] { { "Crawling hand", 1, 50, 100, 100.0 },
                                { "Cow", 1, 25, 130, 112.0 },
                                { "Ghoul", 1, 25, 130, 115.0 },
                                { "Abyssal demon", 85, 25, 130, 285.0 },
                                { "Nechryael", 80, 45, 85, 112.0 },
                                { "Aberrant spectre", 60, 30, 100, 115.0 },
                                { "Infernal mage", 45, 25, 60, 150.0 },
                                { "Ganodermic beast", 95, 50, 120, 330 },
                                { "Gargoyle", 75, 150, 200, 200.0 },
                                { "Jelly", 1, 25, 60, 100.0 },
                                { "Dark beast", 90, 40, 75, 310.0 },
                                { "Bloodveld", 50, 30, 100, 145.0 } });**/
 
                private int id;
                private Object[][] data;
 
                private Master(int id, Object[][] data) {
                        this.id = id;
                        this.data = data;
                }
 
                public static Master forId(int id) {
                        for (Master master : Master.values()) {
                                if (master.id == id) {
                                        return master;
                                }
                        }
                        return null;
                }
 
                public int getId() {
                        return id;
                }
 
        }
 
        private Master master;
        private int taskId;
        private int taskAmount;
        private int amountKilled;
 
        public SlayerTask(Master master, int taskId, int taskAmount) {
                this.master = master;
                this.taskId = taskId;
                this.taskAmount = taskAmount;
        }
 
        public String getName() {
                return (String) master.data[taskId][0];
        }
 
        public static SlayerTask random(Player player, Master master) {
                SlayerTask task = null;
                while (true) {
                        int random = Utils.random(master.data.length - 1);
                        int requiredLevel = (Integer) master.data[random][1];
                        if (player.getSkills().getLevel(Skills.SLAYER) < requiredLevel) {
                                continue;
                        }
                        int minimum = (Integer) master.data[random][2];
                        int maximum = (Integer) master.data[random][3];
                        if (task == null) {
                                task = new SlayerTask(master, random, Utils.random(minimum,
                                                maximum));
                        }
                        break;
                }
                return task;
        }
 
        public int getTaskId() {
                return taskId;
        }
 
        public int getTaskAmount() {
                return taskAmount;
        }
 
        public void decreaseAmount() {
                taskAmount--;
        }
       
       
 
        public int getXPAmount() {
                Object obj = master.data[taskId][4];
                if (obj instanceof Double) {
                        return (int) Math.round((Double) obj);
                }
                if (obj instanceof Integer) {
                        return (Integer) obj;
                }
                return 0;
        }
 
        public Master getMaster() {
                return master;
        }
 
        /**
         * @return the amountKilled
         */
        public int getAmountKilled() {
                return amountKilled;
        }
 
        /**
         * @param amountKilled
         *            the amountKilled to set
         */
        public void setAmountKilled(int amountKilled) {
                this.amountKilled = amountKilled;
        }
 

}