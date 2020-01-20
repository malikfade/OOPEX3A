package gameClient;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
import dataStructure.*;
import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import utils.Point3D;

/**
 * 
 * This Class Represents the automatic version of the game.
 * @author malik and fade
 *
 */



public class theGameManager {
	myGame g;

	
	public theGameManager(myGame ng) {
		this.g=ng;
	}
	
	public theGameManager() {
		
	}
	
	public static int whereToStart(graph graph) {
		int max =0;
		node_data ans=null;
		
		for(node_data nd : graph.getV()) {
			Collection<edge_data> ed=graph.getE(nd.getKey());
			if(ed==null) break;
			if(ed.size()>max) {
				max=ed.size();
				ans=nd;
			}
		}
		return ans.getKey();
	}

	public static int whereNext(graph graph) {
		node_data ans=null;
		
		for(node_data nd : graph.getV()) {
			Collection<edge_data> ed=graph.getE(nd.getKey());
			if(ed==null) break;
			ans=nd;
		}
		return ans.getKey();
		
	}
	


}
