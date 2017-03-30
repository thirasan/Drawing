package main;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import objects.*;

public class DrawingBoard extends JPanel {

	private MouseAdapter mouseAdapter; 
	private List<GObject> gObjects;
	private GObject target;
	
	private int gridSize = 10;
	
	public DrawingBoard() {
		gObjects = new ArrayList<GObject>();
		mouseAdapter = new MAdapter();
		addMouseListener(mouseAdapter);
		addMouseMotionListener(mouseAdapter);
		setPreferredSize(new Dimension(800, 600));
	}
	
	public void addGObject(GObject gObject) {
		// TODO: Implement this method.
		gObjects.add(gObject);
		repaint();
	}
	
	public void groupAll() {
		// TODO: Implement this method.
		if(!(gObjects.get(0) instanceof CompositeGObject)){
			CompositeGObject group = new CompositeGObject();
			for(GObject i: gObjects){
				group.add(i);
			}
			group.recalculateRegion();
			gObjects.clear();
			addGObject(group);
		}
		else{
			CompositeGObject group = (CompositeGObject) gObjects.get(0);
			gObjects.remove(0);
			for(GObject i: gObjects){
				group.add(i);
			}
			group.recalculateRegion();
			gObjects.clear();
			addGObject(group);
		}
	}

	public void deleteSelected() {
		// TODO: Implement this method.
		gObjects.remove(target);
		target = null;
		repaint();
	}
	
	public void clear() {
		// TODO: Implement this method.
		gObjects.clear();
		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		paintBackground(g);
		paintGrids(g);
		paintObjects(g);
	}

	private void paintBackground(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	private void paintGrids(Graphics g) {
		g.setColor(Color.lightGray);
		int gridCountX = getWidth() / gridSize;
		int gridCountY = getHeight() / gridSize;
		for (int i = 0; i < gridCountX; i++) {
			g.drawLine(gridSize * i, 0, gridSize * i, getHeight());
		}
		for (int i = 0; i < gridCountY; i++) {
			g.drawLine(0, gridSize * i, getWidth(), gridSize * i);
		}
	}

	private void paintObjects(Graphics g) {
		for (GObject go : gObjects) {
			go.paint(g);
		}
	}

	class MAdapter extends MouseAdapter {

		// TODO: You need some variables here
		
		private void deselectAll() {
			// TODO: Implement this method.
			if(target != null){
				target.deselected();
				target = null;
				repaint();
			}
		}
		
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO: Implement this method.
			int check = 0;
			for(GObject i: gObjects){
				if(i.pointerHit(e.getX(), e.getY())){
					if(target != i){
						if(target != null)
							target.deselected();
						i.selected();
						target = i;
					}
					else{
						target.deselected();
						target = null;
					}
					repaint();
					check = 1;
					break;
				}
			}
			if(check == 0)
				deselectAll();
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			// TODO: Implement this method.
			if(target != null){
				target.move(e.getX(), e.getY());
				repaint();
			}
		}
	}
	
}