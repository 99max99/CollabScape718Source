package org.dawn.tools.map.viewer.menu.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.dawn.tools.map.viewer.MapTool;
import org.dawn.tools.map.viewer.Selection;

public class SelectionHoverPopup extends JPopupMenu {

	private MapTool context;
	private Selection hover;
	
	public SelectionHoverPopup(MapTool context, Selection hover) {
		this.context = context;
		this.hover = hover;
		this.add(new Activate());
		this.addSeparator();
		this.add("Cancel");
	}
	
	public class Activate extends JMenuItem implements ActionListener {

		public Activate() {
			super("Activate "+hover.getName());
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			context.getList().setSelectedValue(hover, true);
			context.getMap().repaint();
		}

	}
	
}
