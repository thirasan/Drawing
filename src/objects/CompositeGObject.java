package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class CompositeGObject extends GObject {

	private List<GObject> gObjects;

	public CompositeGObject() {
		super(0, 0, 0, 0);
		gObjects = new ArrayList<GObject>();
	}

	public void add(GObject gObject) {
		// TODO: Implement this method.
		gObjects.add(gObject);
	}

	public void remove(GObject gObject) {
		// TODO: Implement this method.
		gObjects.remove(gObject);
	}

	@Override
	public void move(int dX, int dY) {
		// TODO: Implement this method.
		int tmpX,tmpY;
		tmpX = this.x;
		tmpY = this.y;
		this.x = dX;
		this.y = dY;
		for(GObject i: gObjects){
			i.x += this.x - tmpX;
			i.y += this.y - tmpY;
		}
	}
	
	public void recalculateRegion() {
		// TODO: Implement this method.
		int maxX = 0,minX = 4000,maxY = 0,minY = 3000;
		for(GObject i: gObjects){
			if(i.x < minX){
				minX = i.x;
			}
			if(i.x+i.width > maxX){
				maxX = i.x+i.width;
			}
			if(i.y < minY){
				minY = i.y;
			}
			if(i.y+i.height > maxY){
				maxY = i.y+i.height;
			}
		}
		this.x = minX;
		this.y = minY;
		this.width = maxX-minX;
		this.height = maxY-minY;
	}

	@Override
	public void paintObject(Graphics g) {
		// TODO: Implement this method.
		for(GObject i: gObjects){
			i.paintObject(g);
		}
	}

	@Override
	public void paintLabel(Graphics g) {
		// TODO: Implement this method.
		g.drawString("Group", x, y+height+10);
		
	}
	
}
