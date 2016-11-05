package org.dawn.tools.map.viewer.menu.mousetools;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;

import org.dawn.tools.map.viewer.MapTool;

public class RectangleSelectionTool extends MouseTool {

	private MapTool context;
	private int baseX;
	private int baseY;
	private Area original;
	private boolean add;

	public RectangleSelectionTool(MapTool context) {
		this.context = context;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//set the rectangle base coordinates to the clicked coordinates
		baseX = context.getMap().getMapX(e.getX());
		baseY = context.getMap().getMapY(e.getY());
		//create a backup which we will rollback to every drag update
		original = (Area) context.getActive().getArea().clone();
		//if the rectangle being selected should be added or subtracted to/from the active selection
		add = e.getButton() == MouseEvent.BUTTON1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		int currX = context.getMap().getMapX(e.getX());
		int currY = context.getMap().getMapY(e.getY());
		//swap start and end coordinates if needed (height and width must be positive)
		int x = Math.min(baseX, currX);
		int y = Math.min(baseY, currY);
		int w = Math.max(baseX, currX) - x;
		int h = Math.max(baseY, currY) - y;
		//rollback to original area how it was when we pressed our mouse
		context.getActive().setArea((Area) original.clone());
		//add or subtract the area to/from the active area
		if(add) {
			context.getActive().getArea().add(new Area(new Rectangle(x, y, w, h)));
		} else {
			context.getActive().getArea().subtract(new Area(new Rectangle(x, y, w, h)));
		}
		context.getMap().repaint();
	}

	@Override
	public String getIconName() {
		return "rect";
	}

	@Override
	public String getDescription() {
		return "Rectangular selection";
	}

}
