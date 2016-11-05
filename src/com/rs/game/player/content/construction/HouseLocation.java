package com.rs.game.player.content.construction;

import java.util.ArrayList;

/**
 * Enum storing all the house portal locations
 * 
 * @author hiloser12221
 *
 */
public enum HouseLocation {

	RIMMINGTON(2955, 3224, 2951, 3222, 10000, "Rimmington"),
	TAVELRY(2894, 3465, 2891, 3463, 50000, "Tavelry"),
	YANILLE(2544, 3096, 2542, 3097, 250000, "Yanille");
	
	private int coordX;
	private int coordY;
	
	private int portalX;
	private int portalY;
	
	private int cost;
	
	private String location;
	
	private static ArrayList<HouseLocation> locations;
	
	private HouseLocation(int x, int y, int x1, int y1, int c, String location) {
		this.coordX = x;
		this.coordY = y;
		this.portalX = x1;
		this.portalY = y1;
		this.cost = c;
		this.location = location;
	}
	
	public static void init() {
		locations = new ArrayList<HouseLocation>();
		locations.add(RIMMINGTON);
		locations.add(TAVELRY);
		locations.add(YANILLE);
	}
	
	public int getX() {
		return coordX;
	}
	
	public int getY() {
		return coordY;
	}
	
	public int getPortalX() {
		return portalX;
	}
	
	public int getPortalY() {
		return portalY;
	}
	
	public int getHouseCost() {
		return cost;
	}
	
	public static HouseLocation getForCoords(int x, int y) {
		for (HouseLocation h : locations) {
			if (x == h.getPortalX() && y == h.getPortalY()) {
				return h;
			}
		}
		return null;
	}
	
	public String getName() {
		return location;
	}
	
}
