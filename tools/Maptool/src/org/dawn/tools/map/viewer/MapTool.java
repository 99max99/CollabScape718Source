package org.dawn.tools.map.viewer;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.io.File;
import java.io.FileInputStream;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ToolTipManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.dawn.tools.map.viewer.menu.MapMenuBar;
import org.dawn.tools.map.viewer.menu.popups.ListPopup;
import org.dawn.tools.map.viewer.util.XStreamUtil;

public class MapTool extends JFrame {

	public static final int VERSION = 667;

	public static void main(String[] args) {
		MapTool frame = new MapTool();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.initComponents();
		frame.setVisible(true);
	}

	private MapPanel map;
	private MapMenuBar menu;
	private JList<Selection> list;
	private DefaultListModel<Selection> model;

	@SuppressWarnings("unchecked")
	private void initComponents() {
		ToolTipManager.sharedInstance().setInitialDelay(0);
		map = new MapPanel(this);
		menu = new MapMenuBar(this);
		menu.drag.register();
		try {
			model = (DefaultListModel<Selection>) XStreamUtil.getXStream().fromXML(new FileInputStream("data/map/path.xml"));
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error loading path.xml creating a empty selection list");
			model = new DefaultListModel<Selection>();
			model.addElement(new Selection("Default", SelectionType.MULTICOMBAT));
		}
		list = new JList<Selection>(model);
		list.setSelectedIndex(0);
		list.setFocusable(false);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent le) {
				Selection selection = list.getSelectedValue();
				if(selection == null) {
					list.setSelectedIndex(le.getFirstIndex()); //Disable deselecting by re-selecting the deselected index
				} else {
					map.repaint();
				}
			}

		});
		list.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				maybeShowPopup(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				maybeShowPopup(e);
			}

			private void maybeShowPopup(MouseEvent e) {
				if (e.isPopupTrigger() && list.locationToIndex(e.getPoint()) == list.getSelectedIndex()) {
					new ListPopup(MapTool.this).show(list, e.getX(), e.getY());
				}
			}

		});
		list.setCellRenderer(new DefaultListCellRenderer() {

			@Override
			public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
				JLabel label =  (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
				label.setIcon(((Selection)value).getType().getIcon());
				return label;
			}	

		});
		JScrollPane listScroll = new JScrollPane(list);
		JPanel listPanel = new JPanel(new BorderLayout());
		listPanel.setPreferredSize(new Dimension(200, 10000));
		listPanel.add(BorderLayout.CENTER, listScroll);
		listPanel.add(BorderLayout.SOUTH, new JTextField("TODO: Filter..."));
		setJMenuBar(menu);
		getContentPane().add(BorderLayout.EAST, listPanel);
		getContentPane().add(BorderLayout.CENTER, map);
		setTitle("Vincent's 2D Map Tool");
		setSize(1000, 800);
		
		
		//Testing code used to generate the missing area's
		/*
		for(File f : new File("data/map/MISSING").listFiles()) {
			int rx = Integer.parseInt(f.getName().split("_")[0]);
			int ry = Integer.parseInt(f.getName().split("_")[1]);
			int rid = rx << 8 | ry;
			Selection selection = new Selection("MISSING REGION #"+rid, SelectionType.UNDEFINED);
			selection.getArea().add(new Area(new Rectangle(rx << 6, ry << 6, 64, 64)));
			getListModel().addElement(selection);
			getList().setSelectedValue(selection, true);
		}
		*/
		

	}

	public MapPanel getMap() {
		return map;
	}

	@Override
	public MapMenuBar getJMenuBar() {
		return menu;
	}

	public JList<Selection> getList() {
		return list;
	}

	public DefaultListModel<Selection> getListModel() {
		return model;
	}

	public Selection getActive() {
		return list.getSelectedValue();
	}

}


