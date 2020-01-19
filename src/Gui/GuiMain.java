package Gui;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Server.Game_Server;
import Server.game_service;
import dataStructure.*;
import gameClient.Fruit;
import gameClient.myGame;
import gameClient.Robot;
import utils.Point3D;

public class GuiMain {

	/**
	 * @param data - denote some data to be scaled
	 * @param r_min the minimum of the range of your data
	 * @param r_max the maximum of the range of your data
	 * @param t_min the minimum of the range of your desired target scaling
	 * @param t_max the maximum of the range of your desired target scaling
	 * @return
	 */
	private static double scale(double data, double r_min, double r_max, double t_min, double t_max)
	{
		double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
		return res;
	}

	private static double[] scaleHelper(HashMap<Integer, node_data> n) {
		double [] ans = {Double.MAX_VALUE, Double.MIN_VALUE ,Double.MAX_VALUE ,Double.MIN_VALUE};
		n.forEach((k, v) -> {
			if (v.getLocation().x()<ans[0]) ans[0] = v.getLocation().x();
			if (v.getLocation().x()>ans[1]) ans[1] = v.getLocation().x();
			if (v.getLocation().y()<ans[2]) ans[2] = v.getLocation().y();
			if (v.getLocation().y()>ans[3]) ans[3] = v.getLocation().y();
		});

		return ans;
	}

	public static void main(String[] args) {
		
		ImageIcon robo = new ImageIcon("robotB.png");
		// Set the game Level - [0,23]
		String[] options = {"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
		int gameNum = JOptionPane.showOptionDialog(null, "Choose the Level you would like to display", "Click a button",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, robo, options, options[0]);
		if (gameNum<0) gameNum=0;
		System.out.println(gameNum);
		// Set the game mode - Manual/Automate
		String[] Mode = {"Automate", "Manual"};
		int ModeNum = JOptionPane.showOptionDialog(null, "Choose the Mode you would like to display", "Click a button",
				JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, robo, Mode, Mode[0]);
		if (ModeNum<0) ModeNum=0;
		System.out.println(ModeNum);


		game_service game = Game_Server.getServer(gameNum);
		game.addRobot(0);game.addRobot(1);game.addRobot(2);game.addRobot(3);game.addRobot(4);
		String str = game.getGraph(); // graph as string.
		DGraph g = new DGraph();

		//init the graph from json for the game.
		g.init(str);

		//add objects parameters to GraphGui:
		myGame mg = new myGame(g, game);
		//get fruits.
		ArrayList<Fruit> fr = mg.fru_list;
		if (fr != null) {
			for (int i=0; i<fr.size(); i++) {
				fr.get(i).from = mg.fruitToEdge(fr.get(i), g).getSrc();
				fr.get(i).to   = mg.fruitToEdge(fr.get(i), g).getDest();
			}
		}
		//get robots.
		ArrayList<Robot> rob = mg.robo_list;

		//relocate nodes to valid coordination.
		double [] size = scaleHelper(g.nodes);
		g.nodes.forEach((k, v) -> {
			Point3D loc = v.getLocation();
			Point3D newL = new Point3D((int)scale(loc.x(),size[0],size[1],50,1230), (int)scale(loc.y(),size[2],size[3],80,670));
			v.setLocation(newL);
		});

		//Init gui
		theGraphGui a = new theGraphGui(g,size ,mg);
		//Let the Show Begin !
		a.setVisible(true);
		//mg.game.startGame();
		int i=0;
		while(mg.game.isRunning()) {

			try {
				a.mg.updategame(game);
				System.out.println(mg.game.timeToEnd()/1000);
				Thread.sleep(100);
				a.mg.upDate();		

				a.repaint();	

			} catch (InterruptedException e) {e.printStackTrace();}
		}
		JOptionPane.showMessageDialog(null, a.mg.score);
	}
}


