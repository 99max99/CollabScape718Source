package com.rs.game.player.content.construction;

import java.util.HashMap;
import java.util.Map;

public enum Room {
	//Tele to 1884 5106, do coords, get rx and ry
    PARLOUR(232, 639, 1, 93, 1000, "Parlour", false, true),
    GARDEN(232, 633, 1, 94, 1000, "Garden", false, false),
    KITCHEN(234, 639, 5, 95, 5000, "Kitchen", false, true),
    DININGROOM(236, 639, 10, 96, 5000, "Dining room", false, true),
    WORKSHOP(232, 637, 15, 97, 10000, "Workshop", false, true),
    BEDROOM(238, 639, 20, 98, 10000, "Bedroom", false, true),
    SKILLHALL1(233, 638, 25, 99, 15000, "Skill hall 1", false, true),
    SKILLHALL2(235, 638, 0, 0, 0, "Skill hall 2", false, true),
    GAMESROOM(237, 636, 30, 100, 25000, "Games room", false, true),
    BOXINGROOM(235, 636, 32, 101, 25000, "Combat room", false, true),
    QUESTHALL1(237, 638, 35, 102, 25000, "Quest hall 1", false, true),
    QUESTHALL2(239, 638, 0, 0, 0, "Quest hall 2", false, true),
    STUDY(236, 637, 40, 104, 50000, "Study", false, true),
    COSTUMEROOM(238, 633, 42, 105, 50000, "Costume room", false, true),
    CHAPEL(234, 637, 45, 106, 50000, "Chapel", false, true),
    PORTALROOM(233, 636, 50, 107, 100000, "Portal room", false, true),
    FANCYGARDEN(234, 633, 55, 108, 75000, "Formal garden", false, false),
    THRONEROOM(238, 637, 60, 109, 150000, "Throne room", false, true);

    private int chunkX, chunkY, level, componentId, cost;
    private String name;
    private boolean showRoof;
    private boolean hasDoors;
    private Room(int chunkX, int chunkY, int level, int componentId, int cost, String name, boolean showRoof, boolean hasDoors) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.level = level;
        this.componentId = componentId;
        this.cost = cost;
        this.name = name;
        this.showRoof = showRoof;
        this.hasDoors = hasDoors;
    }
    
    public int getChunkX() {
    	return chunkX;
    }
    
    public int getChunkY() {
    	return chunkY;
    }
    
    public int getLevel() {
    	return level;
    }
    
    public int getCost() {
    	return cost;
    }
    
    public String getName() {
    	return name;
    }
    
    public boolean isShowRoof() {
    	return showRoof;
    }
    
    public boolean doesHaveDoors() {
    	return hasDoors;
    }
    
    private static Map<Integer, Room> rooms = new HashMap<Integer, Room>();
    
    public static Room forId(int i) {
		return rooms.get(i);
	}
    
    static {
		for (final Room room : Room.values()) {
			rooms.put(room.componentId, room);
		}
	}
}
