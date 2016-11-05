package org.dawn.tools.map.viewer.menu.popups;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import org.dawn.tools.map.viewer.MapTool;
import org.dawn.tools.map.viewer.Selection;
import org.dawn.tools.map.viewer.SelectionType;

public class ListPopup extends JPopupMenu {

	private MapTool context;

	public ListPopup(MapTool context) {
		this.context = context;
		this.add(new Rename());
		this.add(new ChangeType());
		this.add(new Delete());
		this.add(new Merge());
		this.add(new Exclude());
		this.add(new HideShow());
		this.add(new ZoomTo());
		this.addSeparator();
		this.add("Cancel");
	}

	public class Rename extends JMenuItem implements ActionListener {

		public Rename() {
			super("Rename "+context.getActive().getName());
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog(context, "Name:", "Rename "+context.getActive().getName(), JOptionPane.PLAIN_MESSAGE);
			if(name != null && !name.equals("")) {
				context.getActive().setName(name);
				context.getList().repaint();
			}
		}

	}

	public class ChangeType extends JMenuItem implements ActionListener {

		public ChangeType() {
			super("Change Type");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			SelectionType type = (SelectionType) JOptionPane.showInputDialog(context, "Type:", "Select type", JOptionPane.PLAIN_MESSAGE, context.getActive().getType().getIcon(), SelectionType.values(), context.getActive().getType());
			if(type != null) {
				context.getActive().setType(type);
				context.getMap().repaint();
				context.getList().repaint();
			}
		}

	}

	public class Delete extends JMenuItem implements ActionListener {

		public Delete() {
			super("Delete");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(context.getListModel().size() > 1) {
				if(JOptionPane.showConfirmDialog(context, "DELETE "+context.getActive().getName()+"?", "Confirm deletion", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
					context.getListModel().removeElement(context.getActive());
					context.getList().setSelectedIndex(0);
				}
			} else {
				JOptionPane.showMessageDialog(context, "Please create a new selection first before deleting the last one.");
			}
		}

	}

	public class Merge extends JMenuItem implements ActionListener {

		public Merge() {
			super("Merge...");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Selection merge = (Selection) JOptionPane.showInputDialog(context, "Merge "+context.getActive().getName()+" with ...", "Merge area...", JOptionPane.PLAIN_MESSAGE, context.getActive().getType().getIcon(), context.getListModel().toArray(), context.getActive());
			if(merge != null) {
				if(JOptionPane.showConfirmDialog(context, "'"+context.getActive().getName()+"' += '"+merge.getName()+"' ?", "Confirm merge", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
					context.getActive().getArea().add(merge.getArea());
					context.getMap().repaint();
				}
			}
		}

	}

	public class Exclude extends JMenuItem implements ActionListener {

		public Exclude() {
			super("Exclude...");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Selection exlude = (Selection) JOptionPane.showInputDialog(context, "Exclude ... from "+context.getActive().getName(), "Exclude area...", JOptionPane.PLAIN_MESSAGE, context.getActive().getType().getIcon(), context.getListModel().toArray(), context.getActive());
			if(exlude != null) {
				if(JOptionPane.showConfirmDialog(context, "'"+context.getActive().getName()+"' -= '"+exlude.getName() +"' ?", "Confirm exclude", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION) {
					context.getActive().getArea().subtract(exlude.getArea());
					context.getMap().repaint();
				}
			}
		}

	}

	public class HideShow extends JMenuItem implements ActionListener {

		public HideShow() {
			super(context.getActive().isHidden() ? "Show" : "Hide");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			context.getActive().setHidden(!context.getActive().isHidden());
			context.getMap().repaint();
		}

	}
	
	public class ZoomTo extends JMenuItem implements ActionListener {

		public ZoomTo() {
			super("Zoom to region");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			context.getMap().setView(context.getActive().getArea().getBounds());
		}

	}
	
}
