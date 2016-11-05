package com.rs.tools;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import com.rs.cache.Cache;
import com.rs.cache.loaders.NPCDefinitions;

/*
*
*@author Displee & Ridiculous
*
*/

public class NPCExamineDumper {

	public static void main(String[] args) throws IOException {
		Cache.init();
		BufferedWriter writer = new BufferedWriter(new FileWriter("data/npcs/unpackedExamines.txt"));
		for(int i = 0; i <= 20000; i++) {
			try{
			NPCDefinitions def = NPCDefinitions.getNPCDefinitions(i);
			if (def != null) {
				String examine = getNpcExamine(def.getName());
				if (examine.contains("Unknown") || examine.length() <= 1) {
					System.out.println("NPC: " + i + " Invalid Examine, Evading!");	
				} else {
					writer.write(i + " -" + examine);
					writer.flush();
					writer.newLine();
					System.out.println("NPC: " + i + " -" + examine);
				}
			}
			} catch(ArrayIndexOutOfBoundsException e){
	            System.out.println("Oops, something went wrong, better skip this NPC!");
	            continue;
	        }
		}

		writer.close();
	}

	public static String getNpcExamine(String name) throws IOException {
		try {
			String npcName = name.replace(" ", "_");
			URL url = new URL("http://runescape.wikia.com/wiki/" + npcName);
			String line;
			URLConnection urlConnection = url.openConnection();
			urlConnection.setReadTimeout(10000);
			BufferedReader stream = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			while ((line = stream.readLine()) != null) {
				if (line.startsWith("<th style=\"white-space: nowrap;\"><a href=\"/wiki/Examine\" title=\"Examine\">Examine</a>")) {
					String nextLine = stream.readLine();
					String examine = nextLine.replace("</th><td>", "").replace("*", "").replace("#160;", "").replace("&lt;", "").replace("&gt;", "").replace("<br />", "");
					if (examine.endsWith("</small>")) {
						String toRemove = examine.substring(examine.indexOf("<small>"), examine.indexOf("</small>")+8);
						return examine.replace(toRemove, "");
					}
					return examine;
				}
			}
			stream.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return " Unknown";
	}
}