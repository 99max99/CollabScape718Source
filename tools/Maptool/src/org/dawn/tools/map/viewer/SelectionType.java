package org.dawn.tools.map.viewer;

import java.awt.Color;

import javax.swing.ImageIcon;

/**
 * 
 * @author Vincent
 *
 */
public enum SelectionType {
	
	//TODO: add better colors/icons, add different color dots for waypoints
	UNDEFINED("Undefined","undefined.png", Color.MAGENTA),
	JUNK("Junk","undefined.png", Color.GRAY),
	MUSIC("Music","note.png", Color.CYAN),
	PVP_SAFE("PvP safe","safe.png", Color.YELLOW),
	PVP_RISK("PvP risk","risk.png", Color.RED),
	MULTICOMBAT("Multi combat","multi.png", Color.GREEN);
	;

	private String description;
	private ImageIcon icon;
	private Color color;
	
	private SelectionType(String desc, String iconName, Color color) {
		this.description = desc;
		this.icon = new ImageIcon("data/map/sprites/type/"+iconName);
		this.color = color;
	}
	
	public Color getColor() {
		return color;
	}

	public ImageIcon getIcon() {
		return icon;
	}
	
	@Override
	public String toString() {
		return description;
	}
	
}