/**
 * 
 */
package com.rs.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Item Definition class
 * @author Graham
 *
 */
public class LoadPrices {

	private final static int [] itemPriceMin = new int[34000];
	private final static int [] itemPriceMed = new int[34000];
	private final static int [] itemPriceMax = new int[34000];
	
	public void DumpData(int Id) {
		try {
			BufferedWriter  bf = new BufferedWriter(new FileWriter("./data/mapdata/special/"+Id+".txt", false));
			bf.write("0");
			bf.newLine();
			bf.flush();
			bf.write("0");
			bf.newLine();
			bf.flush();
			bf.write("0");
			bf.newLine();
			bf.flush();
			bf.write("0");
			bf.newLine();
			bf.flush();
			bf.flush();
			bf.close();
			bf = null; 
		} catch(Exception e) {
		}
	}
	
	public static void LoadObjects() {
	
	}

	public static void init() {
		/*for(int i = 0; i < 17500; i++) {
			DumpData(i);
		}*/
		int amt = 0;
		String line = "", token = "", token2 = "", token2_2 = "", token3[] = new String[10];
		BufferedReader list = null;
		try {
			list = new BufferedReader(new FileReader("./data/prices.txt"));
			line = list.readLine().trim();
		} catch (Exception e) {
			Logger.log("LoadPrices", "Error loading item list.");
		}
		while (line != null) {
			int spot = line.indexOf("=");
			if (spot > -1) {
				token = line.substring(0, spot).trim();
				token2 = line.substring(spot + 1).trim();
				token2_2 = token2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token2_2 = token2_2.replaceAll("\t\t", "\t");
				token3 = token2_2.split("\t");
				if (token.equals("Item")) {
					amt ++;
					int ItemId = Integer.parseInt(token3[0]);
					itemPriceMin[ItemId] = Integer.parseInt(token3[1]);
					itemPriceMed[ItemId] = Integer.parseInt(token3[2]);
					itemPriceMax[ItemId] = Integer.parseInt(token3[3]);
				}             
			} else {
				if (line.equals("[ENDOFPRICELIST]")) {
					try {
						LoadObjects();
						Logger.log("PricesLoader","Loaded "+ amt + " item prices.");
						list.close();
					} catch (Exception exception) {
					}
					list = null;
					return;
				}
			}
			try {
				line = list.readLine().trim();
			} catch (Exception exception1) {
				try {
					list.close();
				} catch (Exception exception) {
				}
				list = null;
				return;
			}
		}
	}

	public static int getMinimumPrice(int Id) {
		return itemPriceMin[Id];
	}

	public static int getNormalPrice(int Id) {
		return itemPriceMed[Id];
	}

	public static int getMaximumPrice(int Id) {
		return itemPriceMax[Id];
	}

}