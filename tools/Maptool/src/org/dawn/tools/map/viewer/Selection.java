package org.dawn.tools.map.viewer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;

import org.dawn.tools.map.viewer.util.Resources;


public class Selection {

	/**
	 * The name of this selection
	 */
	private String name;

	/**
	 * The type of this selection
	 */
	private SelectionType type;

	/**
	 * The area that covers this selection
	 */
	private Area area;

	//TODO: undo-ing
	//private transient Area previous;

	/**
	 * Specifies whether this selection shouldn't be drawn if it is not active
	 */
	private boolean hidden;

	/**
	 * Constructs a empty selection with the specified name and default type
	 * @param name The name to assign to the selection
	 */
	public Selection(String name) {
		this(name, SelectionType.UNDEFINED);
	}

	/**
	 * Constructs a empty selection with the specified name and type
	 * @param name The name to assign to the selection
	 * @param type The type to assign to the selection
	 */
	public Selection(String name, SelectionType type) {
		this.name = name;
		this.type = type;
		this.area = new Area();
	}

	/**
	 * Translates this area made of map coordinates to a area made of panel coordinates
	 * @param context The panel context to translate the area to
	 * @return The translated area
	 */
	public Area translate(MapPanel context) {
		GeneralPath onScreen = new GeneralPath(GeneralPath.WIND_EVEN_ODD);
		PathIterator it = area.getPathIterator(null);
		while(!it.isDone()) {
			double[] coords = new double[6];
			switch(it.currentSegment(coords)) {
			case PathIterator.SEG_LINETO:
				onScreen.lineTo(context.getPanelX(coords[0]), context.getPanelY(coords[1]));
				break;
			case PathIterator.SEG_MOVETO:
				onScreen.moveTo(context.getPanelX(coords[0]), context.getPanelY(coords[1]));
				break;
			case PathIterator.SEG_CLOSE:
				onScreen.closePath();
				break;
			default:
				System.err.println("path translate unhandled op!! "+it.currentSegment(coords)+" "+coords[0]+" "+coords[1]);
				break;
			}
			it.next();
		}
		return new Area(onScreen);
	}

	/**
	 * Draws a border and fills the shape made by the selections path
	 * @param context
	 * @param graphics
	 */
	public void draw(MapPanel context, Graphics2D g) {
		Area screenArea = translate(context);
		g.setColor(new Color(0x44000000 | (type.getColor().getRGB() & 0xFFFFFF), true)); //Very transparent
		g.fill(screenArea); //Fill the shape
		g.setColor(new Color(0xAA000000 | (type.getColor().getRGB() & 0xFFFFFF), true)); //Less transparent
		g.fill(new BasicStroke(3).createStrokedShape(screenArea)); //Draw a border around it
	}

	/**
	 * Draws a dot on all the waypoints of the path
	 * @param panel
	 * @param g0
	 */
	public void drawWayPoints(MapPanel panel, Graphics2D g) {
		Area onScreen = translate(panel);
		PathIterator it = onScreen.getPathIterator(null);
		while(!it.isDone()) {
			double[] coords = new double[6];
			switch(it.currentSegment(coords)) {
			case PathIterator.SEG_LINETO:
			case PathIterator.SEG_MOVETO:
				g.drawImage(Resources.IMG_DOT, null, (int)coords[0] - 3, (int)coords[1] - 3);
				break;
			}
			it.next();
		}
	}

	/**
	 * Searches a LINETO or MOVETO segment in this path with the specified old coordinates and replaces it with the specified new coordinates
	 * @param oldX
	 * @param oldY
	 * @param newX
	 * @param newY
	 */
	public void movePoint(double oldX, double oldY, int newX, int newY) {
		PathIterator it = area.getPathIterator(null);
		GeneralPath newPath = new GeneralPath();
		boolean didChange = false;
		while(!it.isDone()) {
			double[] coords = new double[6];
			switch(it.currentSegment(coords)) {
			case PathIterator.SEG_LINETO:
				if(!didChange && coords[0] == oldX && coords[1] == oldY) {
					coords[0] = newX;
					coords[1] = newY;
					didChange = true;
				}
				newPath.lineTo(coords[0], coords[1]);
				break;
			case PathIterator.SEG_MOVETO:
				if(!didChange && coords[0] == oldX && coords[1] == oldY) {
					coords[0] = newX;
					coords[1] = newY;
					didChange = true;
				}
				newPath.moveTo(coords[0], coords[1]);
				break;
			case PathIterator.SEG_CLOSE:
				newPath.closePath();
				break;
			default:
				System.err.println("unimplemented segment opcode in selection.movepoint");
				break;
			}
			it.next();
		}
		area = new Area(newPath);
	}

	public void deletePoint(double oldX, double oldY) {
		PathIterator it = area.getPathIterator(null);
		GeneralPath newPath = new GeneralPath();
		boolean deletedMoveTo = false;
		while(!it.isDone()) {
			double[] coords = new double[6];
			switch(it.currentSegment(coords)) {
			case PathIterator.SEG_LINETO:
				if(coords[0] != oldX || coords[1] != oldY) {
					if(deletedMoveTo) {
						deletedMoveTo = false;
						newPath.moveTo(coords[0], coords[1]);
					} else {
						newPath.lineTo(coords[0], coords[1]);
					}
				}
				break;
			case PathIterator.SEG_MOVETO:
				if(coords[0] != oldX || coords[1] != oldY) {
					newPath.moveTo(coords[0], coords[1]);
				} else {
					deletedMoveTo = true;
				}
				break;
			case PathIterator.SEG_CLOSE:
				newPath.closePath();
				break;
			default:
				System.err.println("unimplemented segment opcode in selection.movepoint");
				break;
			}
			it.next();
		}
		area = new Area(newPath);
	}

	/**
	 * Gets the waypoint drawn at the specified screen position, if any
	 * @param x The x on the panel
	 * @param y The y on the panel
	 * @return The waypoint as a Point2D, null if there is none
	 */
	public Point2D getPoint(MapPanel context, int ex, int ey) {
		PathIterator it = area.getPathIterator(null);
		while(!it.isDone()) {
			double[] coords = new double[6];
			switch(it.currentSegment(coords)) {
			case PathIterator.SEG_LINETO:
			case PathIterator.SEG_MOVETO:
				int drawX = context.getPanelX(coords[0]);
				int drawY = context.getPanelY(coords[1]);
				double ox = Math.abs(drawX - ex);
				double oy = Math.abs(drawY - ey);
				if(ox < 3 && oy < 3) {
					return new Point2D.Double(coords[0], coords[1]);
				}
				break;
			}
			it.next();
		}
		return null;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * @return The name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name The name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 
	 * @return
	 */
	public SelectionType getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 */
	public void setType(SelectionType type) {
		this.type = type;
	}

	/**
	 * @return The area covered by this selection
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * 
	 * @param area
	 */
	public void setArea(Area area) {
		this.area = area;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isHidden() {
		return hidden;
	}

	/**
	 * 
	 * @param hidden
	 */
	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

}
