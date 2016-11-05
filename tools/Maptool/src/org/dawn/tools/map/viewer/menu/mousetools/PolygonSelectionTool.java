package org.dawn.tools.map.viewer.menu.mousetools;

import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;

import org.dawn.tools.map.viewer.MapTool;

public class PolygonSelectionTool extends MouseTool {
	
	private MapTool context;
	private Area original;
	private boolean add;
	private GeneralPath drawing = new GeneralPath(GeneralPath.WIND_NON_ZERO);
	
	public PolygonSelectionTool(MapTool context) {
		this.context = context;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		//set the polygons base coordinates to the clicked coordinates
		drawing.reset();
		drawing.moveTo(context.getMap().getMapX(e.getX()), context.getMap().getMapY(e.getY()));
		//create a backup which we will rollback to every drag update
		original = (Area) context.getActive().getArea().clone();
		//if the polygon being selected should be added or subtracted to/from the active selection
		add = e.getButton() == MouseEvent.BUTTON1;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//rollback to original area how it was when we pressed our mouse
		context.getActive().setArea((Area) original.clone());
		//update the drawing and add or subtract it to/from the active selection
		drawing.lineTo(context.getMap().getMapX(e.getX()), context.getMap().getMapY(e.getY()));
		if(add) {
			context.getActive().getArea().add(new Area(drawing));
		} else {
			context.getActive().getArea().subtract(new Area(drawing));
		}
		context.getMap().repaint();
	}

	@Override
	public String getIconName() {
		return "poly";
	}

	@Override
	public String getDescription() {
		return "Polygonal selection";
	}

}
