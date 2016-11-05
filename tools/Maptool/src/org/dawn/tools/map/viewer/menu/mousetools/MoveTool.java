package org.dawn.tools.map.viewer.menu.mousetools;

import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import org.dawn.tools.map.viewer.MapTool;
import org.dawn.tools.map.viewer.Selection;
import org.dawn.tools.map.viewer.menu.popups.PointPopup;
import org.dawn.tools.map.viewer.menu.popups.SelectionHoverPopup;

public class MoveTool extends MouseTool {

	private MapTool context;
	private int lastDragX;
	private int lastDragY;
	private boolean isLeftDrag = false;
	public Area original;
	public Point2D editPoint;

	private int baseX;
	private int baseY;
	public Rectangle zoomTo = new Rectangle();

	public MoveTool(MapTool ctx) {
		context = ctx;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(editPoint != null) { //If we are holding a point using the pickup option
			context.getActive().setArea((Area) original.clone()); //Reset area to original state since moving a point can create/delete other points
			context.getActive().movePoint(editPoint.getX(), editPoint.getY(), context.getMap().getMapX(e.getX()), context.getMap().getMapY(e.getY()));
			context.getMap().repaint();
		} else {
			context.getMap().setTooltip(e.getX(), e.getY());
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			//DRAG TO MOVE
			isLeftDrag = true;
			lastDragX = context.getMap().getMapX(e.getX());
			lastDragY = context.getMap().getMapY(e.getY());
		} else {
			//ZOOM TO SELECTION
			isLeftDrag = false;
			baseX = context.getMap().getMapX(e.getX());
			baseY = context.getMap().getMapY(e.getY());
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if(isLeftDrag) {
			//Calculate amount of tiles that the cursor moved
			int ox = context.getMap().getMapX(e.getX()) - lastDragX;
			int oy = context.getMap().getMapY(e.getY()) - lastDragY;
			if(ox != 0 || oy != 0) { //check if the tile changed
				//center around the new tile
				context.getMap().centerX -= context.getMap().getMapX(e.getX()) - lastDragX;
				context.getMap().centerY -= context.getMap().getMapY(e.getY()) - lastDragY;
				lastDragX = context.getMap().getMapX(e.getX());
				lastDragY = context.getMap().getMapY(e.getY());
				context.getMap().repaint();
			}
		} else {
			//DRAW SELECTION ZOOM RECTANGLE
			int currX = context.getMap().getMapX(e.getX());
			int currY = context.getMap().getMapY(e.getY());
			//swap start and end coordinates if needed (height and width must be positive)
			int x = Math.min(baseX, currX);
			int y = Math.min(baseY, currY);
			int w = Math.max(baseX, currX) - x;
			int h = Math.max(baseY, currY) - y;
			zoomTo = new Rectangle(x, y, w, h);
			context.getMap().repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() != MouseEvent.BUTTON1) {
			context.getMap().setView(zoomTo);
			zoomTo = new Rectangle();
			/*//Calculate the required view distance of the selected area
			int viewX = Math.abs(baseX - context.getMap().getMapX(e.getX())) /2;
			int viewY = Math.abs(baseY - context.getMap().getMapY(e.getY())) /2;
			if(viewX != 0 && viewY != 0) {
				//Calculate the preferred zoom for the view distances
				int zoomX = context.getMap().getWidth() * 100 / viewX /2;
				int zoomY = context.getMap().getHeight() * 100 / viewY /2;
				//Center the map on the center of the selected rectangle
				context.getMap().centerX = (baseX + context.getMap().getMapX(e.getX())) /2;
				context.getMap().centerY = (baseY + context.getMap().getMapY(e.getY())) /2;
				//Use the minimum zoom value to ensure everything selected is shown
				context.getMap().setZoom(Math.min(zoomX, zoomY));
			}
			context.getMap().repaint();*/
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(editPoint != null) {
			//DROP POINT
			if(e.getButton() != MouseEvent.BUTTON1) { //If we are holding a point and press any other button then m1 reset to original and don't move the point
				context.getActive().setArea(original);
				context.getMap().repaint();
			}
			editPoint = null;
		} else {
			Point2D waypoint = context.getActive().getPoint(context.getMap(), e.getX(), e.getY());
			if(waypoint != null) {
				new PointPopup(context, waypoint).show(context.getMap(), e.getX(), e.getY());
			} else {
				Selection hover = context.getMap().getHover(e.getX(), e.getY());
				if(hover != null && hover != context.getActive()) {
					new SelectionHoverPopup(context, hover).show(context.getMap(), e.getX(), e.getY());
				}
			}
		}
	}

	@Override
	public String getIconName() {
		return "move";
	}

	@Override
	public String getDescription() {
		return "Move";
	}

}
