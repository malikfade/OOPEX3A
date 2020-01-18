package gameObjects;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.*;

import utils.Point3D;

/**
 * In this class we Introduce the fruit we want to use.
 * We do have 2 options, an apple or a banana (APPLE= 1 , BANANA = -1).
 * we receive  the data we want from the Json file and apply it to the fruit.
 * such as:
 * the position of the fruit, how many points each one has.
 * @author Malik and Fadi.
 *
 */
public class Fruit {
	
		double value;
		int type;
		Point3D pos;
		public int from;
		public int to;
		
		public Fruit() {
			this.value=0;
			this.pos= pos;
			this.type=0;
			this.from = 0;
			this.to = 0;
		}
		
		public Fruit(double value, int y, Point3D pos) {
			this.value=value;
			this.pos=pos;
			this.type=y;
		}
		
		public Fruit(String fruit) throws JSONException {
			Fruit myFruit = new Fruit();
			JSONObject fruit1 = new JSONObject(fruit);
			JSONObject fruit2 = fruit1.getJSONObject("myFruit");
			myFruit.type = fruit2.getInt("type");
            myFruit.value = fruit2.getDouble("value");
            myFruit.from = fruit2.getInt("from");
            myFruit.to = fruit2.getInt("to");
            String pos1 = fruit2.getString("pos");
            myFruit.pos = new Point3D(pos1);
            
		}

		public int getType() { return this.type; }
		public void setPos(Point3D np) { this.pos = np; }
		public Point3D getPos() { return this.pos; }
		public void setV(double v) { this.value=v; }
		public double getV() { return this.value; }
	
	
}

