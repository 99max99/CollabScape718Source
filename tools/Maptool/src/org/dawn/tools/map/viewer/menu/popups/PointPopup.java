package org.dawn.tools.map.viewer.menu.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Area;
import java.awt.geom.Point2D;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.dawn.tools.map.viewer.MapTool;

public class PointPopup extends JPopupMenu {
	
	private MapTool context;
	private Point2D point;

	public PointPopup(MapTool ctx, Point2D waypoint) {
		this.context = ctx;
		this.point = waypoint;
		this.add(new Pickup());
		this.add(new Delete());
		this.addSeparator();
		this.add("Cancel");
	}

	public class Pickup extends JMenuItem implements ActionListener {

		public Pickup() {
			super("Pickup");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			context.getJMenuBar().drag.getTool().original = (Area) context.getActive().getArea().clone();
			context.getJMenuBar().drag.getTool().editPoint = point;
		}

	}
	
	public class Delete extends JMenuItem implements ActionListener {

		public Delete() {
			super("Delete");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			context.getActive().deletePoint(point.getX(), point.getY());
			context.getMap().repaint();
		}

	}

}
