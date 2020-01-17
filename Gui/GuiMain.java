package Gui;

import dataStructure.*;

public class GuiMain {

	public static void main(String[] args) {

		graphFactory r = new graphFactory();
		graph g = r.randomGraphSmallConnected();
		theGraphGui a = new theGraphGui(g);
		a.setVisible(true);
	}
}