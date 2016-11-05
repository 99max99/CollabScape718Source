package com.rs.game.player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.rs.utils.Logger;

public class StarterMap {

        private static StarterMap INSTANCE = new StarterMap();

        public static StarterMap getSingleton() {
                return INSTANCE;
        }

        public List<String> starters = new ArrayList<String>();
        private final String path = "./data/starters.ini";
        private File map = new File(path);
		private BufferedReader reader;

        public void init() {
                try {
                        Logger.log("StarterMap", "Loading Starters");
                        reader = new BufferedReader(new FileReader(map));
                        String s;
                        while ((s = reader.readLine()) != null) {
                                starters.add(s);
                        }
                        Logger.log("StarterMap", "Loaded Starter map, There are "
                                        + starters.size() + " IP's in Configuration");
                } catch (Throwable e) {
                        e.printStackTrace();
                }
        }

        private void save() {
                BufferedWriter bf;
                try {
                        clearMapFile();
                        bf = new BufferedWriter(new FileWriter(path, true));
                        for (String ip : starters) {
                                bf.write(ip);
                                bf.newLine();
                        }
                        bf.flush();
                        bf.close();
                } catch (IOException e) {
                        System.err.println("Error saving starter map!");
                }
        }

        private void clearMapFile() {
                PrintWriter writer;
                try {
                        writer = new PrintWriter(map);
                        writer.print("");
                        writer.close();
                } catch (Throwable e) {
                        e.printStackTrace();
                }
        }

        public void addIP(String ip) {
                if (getCount(ip) >= Starter.MAX_STARTER_COUNT)
                        return;
                starters.add(ip);
                save();
        }

        public int getCount(String ip) {
                int count = 0;
                for (String i : starters) {
                        if (i.equals(ip))
                                count++;
                }
                return count;
        }

}