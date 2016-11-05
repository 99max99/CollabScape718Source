package org.dawn.tools.map.viewer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MapPanel extends JPanel {

	/**
	 * The map panels context (parent)
	 */
	private MapTool context;

	/**
	 * The buffered image were we draw parts from
	 */
	private BufferedImage img;

	/**s
	 * The zoom percentage, 100% means 1 pixel per tile
	 */
	private int zoom = 100;

	/**
	 * X coordinate of the center of the map portion we are viewing
	 */
	public int centerX = 3200;

	/**
	 * Y coordinate of the center of the map portion we are viewing
	 */
	public int centerY = 3200;

	/**
	 * Map plane we are viewing
	 */
	private int plane = 0;

	/**
	 * Creates a MapPanel, loads the map and registers listeners for the component
	 * @param mapViewer 
	 * @param menu
	 */
	public MapPanel(MapTool mapViewer) {
		this.context = mapViewer;
		loadMap();
		MouseAdapter ma = new MapPanelMouseAdapter();
		addMouseMotionListener(ma);
		addMouseWheelListener(ma);
		setFocusable(true); //focus is required for keyboard input
		addKeyListener(new MapPanelKeyAdapter());
	}

	/**
	 * Clears the panel, draws the section of the map being viewed and then draws the selections over the map
	 */
	@Override
	public void paintComponent(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.drawImage(img,  0, 0, getWidth(), getHeight(), centerX - getHorizontalViewSize(), img.getHeight() - (centerY + getVerticalViewSize()), centerX + getHorizontalViewSize(), img.getHeight() - (centerY - getVerticalViewSize()), null);
		Graphics2D g2d = (Graphics2D) g;
		Enumeration<Selection> selections = context.getListModel().elements();
		while(selections.hasMoreElements()) {
			Selection selection = selections.nextElement();
			if(!selection.isHidden() || selection == context.getActive()) { //Don't draw if hidden, unless it is the active area
				selection.draw(this, g2d);
			}
		}

		context.getActive().drawWayPoints(this, g2d);

		g2d.setColor(new Color(0x4400FFFF, true));
		Selection tmp = new Selection(null);
		tmp.setArea(new Area(context.getJMenuBar().drag.getTool().zoomTo));
		tmp.draw(this, g2d);

		//Draw players/npcs etc if you want in realtime here
	}

	/**
	 * Sets the tooltip
	 * @param ex
	 * @param ey
	 */
	public void setTooltip(int ex, int ey) {
		setToolTipText(null);
		Point2D waypoint = context.getActive().getPoint(this, ex, ey);
		if(waypoint != null) {
			setToolTipText(context.getActive().getName()+" ["+(int)waypoint.getX()+","+(int)waypoint.getY()+"]");
		} else {
			Selection hover = getHover(ex, ey);
			if(hover != null) {
				setToolTipText(hover.getName());
			}
		}
	}

	public Selection getHover(int ex, int ey) {
		int x = getMapX(ex);
		int y = getMapY(ey);
		Enumeration<Selection> selections = context.getListModel().elements();
		while(selections.hasMoreElements()) {
			Selection selection = selections.nextElement();
			if(selection.getArea().contains(x, y)) {
				return selection;
			}
		}
		return null;
	}

	public void setView(Rectangle rect) {
		if(rect.getWidth() == 0 && rect.getHeight() == 0) {
			return;
		}
		//Calculate the preferred zoom for the rect size
		int zoomX = (int) (context.getMap().getWidth() * 100 / rect.getWidth());
		int zoomY = (int) (context.getMap().getHeight() * 100 / rect.getHeight());
		//Center the map on the center of the selected rectangle
		context.getMap().centerX = (int) rect.getCenterX();
		context.getMap().centerY = (int) rect.getCenterY();
		//Use the minimum zoom value to ensure everything selected is shown
		context.getMap().setZoom(Math.min(zoomX, zoomY));
		repaint();
	}

	/**
	 * Calculates the amount of tiles we can to view east or west from the centered tile
	 * @return The view x size
	 */
	public int getHorizontalViewSize() {
		return getWidth() * 100 / zoom / 2;
	}

	/**
	 * Calculates the amount of tiles we can view to the north or south from the centered tile
	 * @return The view y size
	 */
	public int getVerticalViewSize() {
		return getHeight() * 100 / zoom / 2;
	}

	/**
	 * Calculates a X coordinate on the map given the X coordinate on the panel
	 * @param panelX
	 * @return
	 */
	public int getMapX(int panelX) {
		return centerX - getHorizontalViewSize() + (2 * getHorizontalViewSize() * panelX / getWidth());
	}

	/**
	 * Calculates a Y coordinate on the map given the Y coordinate on the panel
	 * @param panelX
	 * @return
	 */
	public int getMapY(int panelY) {
		return (int) ((centerY - getVerticalViewSize()) + getVerticalViewSize() / (getHeight() / 2D) * (getHeight() - panelY));
	}

	/**
	 * Calculates a X coordinate on the panel given the X coordinate on the map
	 * @param mapX
	 * @return
	 */
	public int getPanelX(double mapX) {
		return ((int)mapX - centerX + getHorizontalViewSize()) * getWidth() / 2 / getHorizontalViewSize();
	}

	/**
	 * Calculates a X coordinate on the panel given the X coordinate on the map
	 * @param mapX
	 * @return
	 */
	public int getPanelY(double mapY) {
		return -(((int)mapY - centerY - getVerticalViewSize()) * getHeight() / 2 / getVerticalViewSize());
	}


	public int getTileCenterX(double mapX) {
		return getPanelX(mapX) + context.getWidth() / getHorizontalViewSize() / 4;
	}

	public int getTileCenterY(double mapY) {
		return getPanelY(mapY) - context.getHeight() / getVerticalViewSize() / 4;
	}

	private void loadMap() {
		try {
			this.img = ImageIO.read(new File("data/map/sprites/map/map"+MapTool.VERSION+"_"+plane+".png"));
		} catch (IOException e) {
			System.err.println("FATAL ERROR: Could not load map"+MapTool.VERSION+"_"+plane+".png");
			e.printStackTrace();
			System.exit(1);
		}
	}

	/**
	 * Adds to the zoom
	 * @param clicks The number of mousewheel 'clicks'
	 */
	public void setZoom(int z) {
		zoom = z;
		/**
		 * Enforce minimum/maximum zoom
		 */
		if(zoom < 7) {
			zoom = 7;
		}
		if(zoom > 2000) {
			zoom = 2000; //tiles are 21x21 pixels at this zoom value, should be big enough not much use enlarging more
		}
		context.getJMenuBar().setZoom(zoom);
		/**
		 * Check if the map is completely offscreen and put it back onscreen
		 */
		if(getMapX(getWidth()) < 0) {
			centerX = 0;
		} else if(getMapX(0) > 6400) {
			centerX = 6400;
		}
		if(getMapY(0) < 1000) {
			centerY = 1000;
		} else if(getMapY(getHeight()) > 11000) {
			centerY = 11000;
		}
	}

	public class MapPanelMouseAdapter extends MouseAdapter {

		@Override
		public void mouseMoved(MouseEvent e) {
			context.getJMenuBar().setCoord(getMapX(e.getX()), getMapY(e.getY()), plane);
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			setZoom((int) (zoom-e.getWheelRotation()*Math.sqrt(Math.abs(zoom))));
			repaint();
		}
	}


	public class MapPanelKeyAdapter extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				centerY += getVerticalViewSize() / 8;
				repaint();
				break;
			case KeyEvent.VK_DOWN:
				centerY -= getVerticalViewSize() / 8;
				repaint();
				break;
			case KeyEvent.VK_LEFT:
				centerX -= getHorizontalViewSize() / 8;
				repaint();
				break;
			case KeyEvent.VK_RIGHT:
				centerX += getHorizontalViewSize() / 8;
				repaint();
				break;
			case KeyEvent.VK_PAGE_UP:
				if(plane < 3) {
					plane++;
					loadMap();
					repaint();
				}
				break;
			case KeyEvent.VK_PAGE_DOWN:
				if(plane > 0) {
					plane--;
					loadMap();
					repaint();
				}
				break;
			case KeyEvent.VK_ADD:
				setZoom((int) (zoom+Math.sqrt(Math.abs(zoom))));
				repaint();
				break;
			case KeyEvent.VK_SUBTRACT:
				setZoom((int) (zoom-Math.sqrt(Math.abs(zoom))));
				repaint();
				break;
			}
		}
	}

}