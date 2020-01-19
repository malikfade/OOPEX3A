package gameClient;

import dataStructure.edge_data;
import utils.Point3D;

/**
 * this class represents Robot.
 * we need to read data from json file and fetch it to our robot
 * the robot knows:
 * where he is, and where he is going.
 * the score (fruits) he collected.
 * @author Eldar and Yossi
 *
 */
public class Robot {
	int src; // the source of the robot.
	int dest; // the dest of the robot.
	int id; // the id of the robot.
	double value=0; // amount of points collected.
	Point3D pos; //3D pos of robot.
	int speed;

	public Robot(int rid, int src, int dest,Point3D pos,double value,int s) {
		this.id=rid;
		this.src=src;
		this.dest=dest;
		this.value=value;
		this.pos=pos;
		this.speed=s;
	}

	public Robot() {

	}
	public int getSpeed() { return this.speed; }
	public void incSpeed() { this.speed++; } 
	public double getV() { return this.value; }
	public void setV(double v) { this.value=v; }
	public int getID() { return this.id; }
	public void setSrc(int s) { this.src=s; }
	public int getSrc() { return this.src; }
	public int getDest() { return this.dest; }
	public void setDest(int d) { this.dest=d; }
	public Point3D getPos() { return this.pos; }
	public void setPos(Point3D np) { this.pos= np; }
	public void setPos(double x, double y, double z) {
		this.pos=new Point3D(x,y,z);
	}

}

