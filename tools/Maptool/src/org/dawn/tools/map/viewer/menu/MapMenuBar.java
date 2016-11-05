package org.dawn.tools.map.viewer.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.dawn.tools.map.viewer.MapPanel;
import org.dawn.tools.map.viewer.MapTool;
import org.dawn.tools.map.viewer.Selection;
import org.dawn.tools.map.viewer.SelectionType;
import org.dawn.tools.map.viewer.menu.mousetools.MoveTool;
import org.dawn.tools.map.viewer.menu.mousetools.PolygonSelectionTool;
import org.dawn.tools.map.viewer.menu.mousetools.RectangleSelectionTool;
import org.dawn.tools.map.viewer.util.XStreamUtil;

public class MapMenuBar extends JMenuBar {

	private MapTool context;

	private JMenuItem coordItem;
	private JMenuItem zoomItem;
	private JMenu file;
	private JMenu tool;

	public MouseToolItem<MoveTool> drag;
	public MouseToolItem<RectangleSelectionTool> rect;
	public MouseToolItem<PolygonSelectionTool> poly;

	public MouseToolItem<?> currentItem;

	public MapMenuBar(MapTool mapViewer) {
		context = mapViewer;
		file = new JMenu("File");

		file.add(new New());
		file.add(new Save());
		file.add(new Benchmark());
		//TODO: import/export ?
		file.addSeparator();
		file.add("Cancel");
		add(file);


		tool = new JMenu();

		drag = new MouseToolItem<MoveTool>(this, new MoveTool(context));
		rect = new MouseToolItem<RectangleSelectionTool>(this, new RectangleSelectionTool(context));
		poly = new MouseToolItem<PolygonSelectionTool>(this, new PolygonSelectionTool(context));


		tool.add(drag);
		tool.add(rect);
		tool.add(poly);
		add(tool);

		coordItem = new JMenuItem();
		setCoord(3200, 3200, 0);
		add(coordItem);

		zoomItem = new JMenuItem();
		setZoom(100);
		add(zoomItem);
	}

	public void setCoord(int x, int y, int z) {
		coordItem.setText("Coord: ("+x+", "+y+", "+z+") ("+(x>>6)+"_"+(y>>6)+")");
	}

	public void setZoom(int zoom) {
		zoomItem.setText("Zoom: "+zoom+"%");
	}

	public MapPanel getMap() {
		return context.getMap();
	}
	
	public class Save extends JMenuItem implements ActionListener {

		public Save() {
			super("Save");
			setMnemonic(KeyEvent.VK_S);
			setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.Event.CTRL_MASK));
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				XStreamUtil.getXStream().toXML(context.getListModel(), new FileOutputStream("data/map/path.xml"));
			} catch (FileNotFoundException ex) {
				ex.printStackTrace();
			}
		}

	}
	
	public class New extends JMenuItem implements ActionListener {

		public New() {
			super("New");
			setMnemonic(KeyEvent.VK_N);
			setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.Event.CTRL_MASK));
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String name = JOptionPane.showInputDialog(context, "Name:", "Create a new selection", JOptionPane.PLAIN_MESSAGE);
			if(name != null && !name.equals("")) {
				Selection selection = new Selection(name, SelectionType.UNDEFINED);
				context.getListModel().addElement(selection);
				context.getList().setSelectedValue(selection, true);
			}
		}

	}
	
	public class Benchmark extends JMenuItem implements ActionListener {

		public Benchmark() {
			super("Benchmark");
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			long start = System.currentTimeMillis();
			for(int i = 0; i < 1000000; i++) { //Test how long it takes to generate 1 million coordinate sets and do a hit test on the active selection
				int x = (int) (Math.random() * 10000);
				int y = (int) (Math.random() * 10000);
				context.getActive().getArea().contains(x, y);
			}
			long end = System.currentTimeMillis();
			for(int i = 0; i < 1000000; i++) { //Test how long it takes to generate 1 million coordinate sets and subtract this from the previous result
				@SuppressWarnings("unused")
				int x = (int) (Math.random() * 10000);
				@SuppressWarnings("unused")
				int y = (int) (Math.random() * 10000);
			}
			System.out.println("Hit test on "+context.getActive().getName()+": "+((end-start)-(System.currentTimeMillis()-end))+" nanoseconds each");
		}

	}
	
}
