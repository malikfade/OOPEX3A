package gameClient;

import utils.Point3D;

/**
 * this class represtents a fruit.
 * can be a banana or apple(-1 = banana, 1 = apple).
 * we need to read data from json file and fetch it to the fruit.
 * such:
 * where he is, his points, and the fruit itself.
 * @authors malik and fade.
 *
 */
public class Fruit {
	double value;
	int type;
	Point3D pos;
	public int from;
	public int to;
	
	
	
	public Fruit(double value, int y, Point3D pos) {
		this.value=value;
		this.pos=pos;
		this.type=y;
	}
	public Fruit() {
		
	}

	public int getType() { return this.type; }
	public void setPos(Point3D np) { this.pos = np; }
	public Point3D getPos() { return this.pos; }
	public void setV(double v) { this.value=v; }
	public double getV() { return this.value; }
}