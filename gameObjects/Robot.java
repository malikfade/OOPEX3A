package gameObjects;

import utils.Point3D;

/**
 * In this class we know what a Robot is.
 * We read the information we need(data) from the Json File and apply it to the Robot/Robots
 *we have.
 *Each robot should know : 
 *Its position , and where it is going, and how much fruits he has eaten.
 * @author Malik and Fadi
 *
 */

public class Robot {
	int src; // The Robot's source.
	int dest; // The Robot's Dest.
	int id; //Robot's id.
	public double value=0; //How much points did he collect so far.
	Point3D pos; //Robot's position. 

	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

	public int getDest() {
		return dest;
	}

	public void setDest(int dest) {
		this.dest = dest;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public void setPos(Point3D pos) {
		this.pos = pos;
	}

	public Robot(int ID, int src, int dest,Point3D pos,double value) {
		this.id=ID;
		this.src=src;
		this.dest=dest;
		this.pos=pos;
	}

	public Robot() {
		
	}
	
	public Point3D getPos() { return this.pos; }



	public int getID() { return this.id; }

}