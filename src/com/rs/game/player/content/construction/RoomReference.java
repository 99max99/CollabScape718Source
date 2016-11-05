package com.rs.game.player.content.construction;

import java.io.Serializable;

import com.rs.game.player.Player;
import com.rs.utils.Logger;

public class RoomReference implements Serializable {

	private static final long serialVersionUID = 738514883298748999L;

	public RoomReference() {
		
	}
	
	public RoomReference(Room room, int x, int y, int plane, int rotation) {
        this.room = room;
        this.x = (byte) x;
        this.y = (byte) y;
        this.plane = (byte) plane;
        this.rotation = (byte) rotation;
    }
    private Room room;
    private byte x, y, plane, rotation;
    
    public byte getRotation() {
    	return rotation;
    }
    
    public byte getPlane() {
    	return plane;
    }
    
    public byte getY() {
    	return y;
    }
    
    public byte getX() {
    	return x;
    }
    
    public Room getRoom() {
    	return room;
    }
    
    public Room getCurrentRoom(Player player) {
    	for (RoomReference room : player.getRooms()) {
			//room = new RoomReference();
			if (room.getX() == player.getRoomX() && room.getY() == player.getRoomY() && room.getPlane() == player.getPlane()) {
				System.out.println(room.getRoom().getName());
				Logger.logMessage(room.getRoom().getName());
				return room.getRoom();
			}
		}
		return null;
    }
}
