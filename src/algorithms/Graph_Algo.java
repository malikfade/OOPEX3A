package algorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


import dataStructure.*;
import Gui.theGraphGui;
import utils.Point3D;

/**
 * This class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2.
 * @author malik faris and fadi amer
 */
public class Graph_Algo implements graph_algorithms, Serializable{

	private static final long serialVersionUID = 1L;
	
	private DGraph gr;

	public Graph_Algo() {
		this.gr=new DGraph();
	}

	public Graph_Algo(graph g) {
		this.gr=(DGraph)g;
	}
	

	@Override
	public void init(graph g) {
		if(g instanceof DGraph) { this.gr=(DGraph)g; }
		else { throw new RuntimeException("Error initialaizing the graph"); }
	}
	
	/**
	 * deSerialize
	 */
	@Override
	public void init(String file_name) {
		try {    
			FileInputStream file = new FileInputStream(file_name); 
			ObjectInputStream in = new ObjectInputStream(file);  
			gr = (DGraph) in.readObject(); 
			in.close(); 
			file.close();   
			System.out.println("Object has been deserialized"); 

		} 

		catch(IOException ex) 
		{ 
			System.out.println("IOException is caught"); 
		} 

		catch(ClassNotFoundException ex) 
		{ 
			System.out.println("ClassNotFoundException is caught"); 
		} 		
	}
	
	/**
	 * Serialize 
	 */
	@Override
	public void save(String file_name) {
		try {    
			FileOutputStream file = new FileOutputStream(file_name); 
			ObjectOutputStream out = new ObjectOutputStream(file); 

			out.writeObject(gr);
			out.close(); 
			file.close();  
			System.out.println("Object has been serialized"); 
		}   
		catch(IOException ex)  { 
			System.out.println("IOException is caught"); 
		}
	}

	/**
	 * Check if the current graph is strongly connected.
	 * return true if it is, else false.
	 */
	@Override
	public boolean isConnected() { // WORKING
		DGraph temp = (DGraph) this.copy();
		if (temp.edgesCounter<2) { return false; }
		for(int key:temp.nodes.keySet()) {
			temp.nodes.get(key).setTag(0);
		}
		Collection<node_data> ns = temp.getV();
		for(node_data t:ns) {
		DFS(t); 
		break;
		}
		
		for (node_data node : ns) {
			if (node.getTag() == 0) {
				return false;
			}
		}
		reverse(temp);
		for(int key:temp.nodes.keySet()) {
			temp.nodes.get(key).setTag(0);
		}
		Collection<node_data> nss = temp.getV();
		for(node_data t:nss) {
		DFS(t); 
		break;
		}
		for (node_data node1 : nss) {
			if (node1.getTag() == 0) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * famous DFS algorithm.
	 * used for traversing a graph.
	 * for further reading:
	 * https://en.wikipedia.org/wiki/Depth-first_search
	 * @param node
	 */
	private void DFS(node_data node) {
		node.setTag(1);
		Collection<edge_data> t_c = this.gr.getE(node.getKey());
		if (t_c != null) {
			for (edge_data edge : t_c) {
				if (gr.getNode(edge.getDest()) != null && gr.getE(edge.getDest()) != null && gr.getNode(edge.getDest()).getTag() == 0) {
					DFS(gr.getNode(edge.getDest()));
				}
			}
		}
	}
	
	/**
	 * reverse garph's edges
	 * for each edge, replace dest with source.
	 * @param g
	 */
	public void reverse (graph g) {
		Collection<node_data> n = gr.getV();
		for(node_data nodesource:n) {
			Collection<edge_data> e = gr.getE(nodesource.getKey());
			for(edge_data edge:e) {
				edgeData c = new edgeData((edgeData)edge);
				int tag = c.getTag();
				e.remove(c);
				gr.connect(edge.getDest(), edge.getSrc(), edge.getWeight());
				gr.getEdge(edge.getDest(), edge.getSrc()).setTag(tag);
			}
		}	
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		try {
			dijakstra(src);
			return gr.getNode(dest).getWeight();
		} 
		catch (NullPointerException e) {
			return -1;
		}
	}
	
	/**
	 * Calculates shortest path between two selected nodes.
	 * if there is'nt a path
	 * returns double.MAX_VALUE
	 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		dijakstra(src);
		
		List<node_data> ans = new ArrayList<node_data>();
		node_data current = gr.getNode(dest);
		while(!current.getInfo().isEmpty()||current.getKey()==gr.getNode(src).getKey())	{
			ans.add(0, current);
			current = gr.getNode(Integer.parseInt(current.getInfo()));
			if(current.getKey()==gr.getNode(src).getKey()) break;
		}
		ans.add(0, current);
		return ans;
	}
	
	/**
	 * The famous dijakstra algorithm for calculating the paths from a selected node.
	 * to all other nodes in graph.
	 * for further reading:
	 * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
	 * @param src
	 */
	public void dijakstra(int src) {
		
		List<node_data>  unvisited = new LinkedList<node_data>();
		Collection<node_data> n = gr.getV(); 
		for(node_data nd:n) unvisited.add(nd);
		List<node_data>  visited = new LinkedList<node_data>();

		for(int key:this.gr.nodes.keySet())	{
			this.gr.nodes.get(key).setWeight(Double.MAX_VALUE);		
		}

		this.gr.nodes.get(src).setWeight(0);
		
		node_data current = gr.nodes.get(src); //this node

		while(!unvisited.isEmpty()||Infinity(gr)||current!=null) {
			
			visited.add(current);

			unvisited.remove(current);
			
			Collection<edge_data> e = gr.getE(current.getKey()); //this is all the edges starts current node
			if(e==null) { //case when no edges coming out of the node
				
				current = unvistedmin(unvisited);
				if(current==null) break;
				continue;
				
			}
			for(edge_data edge:e) {
				node_data destnode = gr.getNode(edge.getDest()); // this is one of the neighbors of the current node
				if(current.getWeight()+edge.getWeight()<=gr.nodes.get(destnode.getKey()).getWeight()) { //if this node weight + the edge starting
					gr.nodes.get(destnode.getKey()).setWeight(edge.getWeight()+current.getWeight()); 
					destnode.setInfo(""+current.getKey());																		// at this node weight is less than the dest node weight
				}
			}
			
			current = unvistedmin(unvisited);	
			if(current==null) break;
			
		}
		return;
	}
	
	/**
	 * Creates a list of unvisited nodes.
	 * @param ar
	 * @return list of unvisited nodes
	 */
	public node_data unvistedmin(List<node_data> ar) {
		if(ar.isEmpty()) return null;
		double min = Double.MAX_VALUE;
		node_data mini=null;
		for(int i=0; i<ar.size();i++) {
			if(ar.get(i).getWeight()<min) {
				min=ar.get(i).getWeight();
				mini=ar.get(i);
			}
		}
		
		return mini;
	}

	/**
	 * Method that check if there are nodes that weight infinity.
	 * @param g
	 * @return true if there are, else false.
	 */
	public boolean Infinity(graph g) {
		Collection<node_data> s = g.getV();
		for(node_data n : s) {
			if(n.getWeight()==Double.MAX_VALUE) return true;
		}
		return false;
	}
	
	/**
	 * Method that calculates and returns a path
	 * that is passing thorugh every node in 
	 * the target list
	 * runtime complexity : forever.
	 */

	@Override
	public List<node_data> TSP(List<Integer> targets) { 
	
		//if(!isConnected()) return null; // *** yossi **** : ma osim im ze ?? 
		if(targets.size()==0) throw new RuntimeException("Empty list of targets");
		ArrayList<node_data> ans = new ArrayList<node_data>();
		if(targets.size()==1) {
			ans.add(gr.getNode(targets.get(0)));
			return ans;
		}
		
		Collection<node_data> all_nodes = gr.getV(); //collection of all nodes in the graph
		ArrayList<node_data> target_nodes = new ArrayList<node_data>();//array list that will contain target nodes ONLY
		ArrayList<node_data> all_nodesList = new ArrayList<node_data>();//array list that will contain ALL nodes
		for(node_data nd:all_nodes) {
			nd.setTag(0);
			all_nodesList.add(nd);
			if(targets.contains(nd.getKey())) {
				target_nodes.add(nd);
			}
		}
	
		
		for(int i=1;i<2020;i++) {
			for(int j=1;j<target_nodes.size();j++) {
				ans.addAll(isTherePath(target_nodes.get(j-1), target_nodes.get(j)));
				target_nodes.get(j-1).setTag(1);
				target_nodes.get(j).setTag(1);
			}
			if(allTargetsVisted(target_nodes)) break;
			
			shuffle(target_nodes);
		}
		if(ans.size()<2) return null;
		return ans;
	
	
	}
	/**
	 * checks if all target nodes have been visited
	 * helper of the TSP
	 * @param list
	 * @return 
	 */
	private boolean allTargetsVisted(List<node_data> list) {
		for(node_data data:list) {
			if(data.getTag()==0) return false;
		}
		return true;
	}

	/**
	 * based on javas shuffle method
	 * @param target_nodes
	 */

	private void shuffle(ArrayList<node_data> target_nodes) {
		Collections.shuffle(target_nodes);
		
	}
	/**
	 * used to check if there is a path between 2 nodes
	 * @param node_data
	 * @param node_data2
	 * @return
	 */

	private List<node_data> isTherePath(node_data node_data, node_data node_data2) {
		List<node_data> ans = shortestPath(node_data.getKey(), node_data2.getKey());
		if(ans!=null) return ans;
		return null;
	}
	/**
	 * checks if two nodes are connected
	 * @param node_data
	 * @param node_data2
	 * @return
	 */
	private boolean youMyNeigbour(node_data node_data,node_data node_data2) {
		Collection<edge_data> edge = gr.getE(node_data.getKey());
		if(edge==null) return false;
		for(edge_data e:edge) {
			if(e.getDest()==node_data2.getKey()) return true;
			
		}
		return false;
	}
	/**
	 * deep copy
	 */

	@Override
	public graph copy() {
		graph copy = new DGraph(this.gr);
		return copy;
	}
	public static void main(String[] args) {
		Point3D p1 = new Point3D(99, 95);
		Point3D p2 = new Point3D(203, 96);
		Point3D p3 = new Point3D(154, 152);
		Point3D p4 = new Point3D(455, 151);
		Point3D p5 = new Point3D(687, 206);
		Point3D p6 = new Point3D(142, 702);
		Point3D p7 = new Point3D(232, 437);
		Point3D p8 = new Point3D(191, 602);
		nodeData n1 = new nodeData(p1);
		nodeData n2 = new nodeData(p2);
		nodeData n3 = new nodeData(p3);
		nodeData n4 = new nodeData(p4);
		nodeData n5 = new nodeData(p5);
		nodeData n6 = new nodeData(p6);
		nodeData n7 = new nodeData(p7);
		nodeData n8 = new nodeData(p8);
		
		DGraph g = new DGraph();
		g.addNode(n1);//0
		g.addNode(n2);//1
		g.addNode(n3);//2
		g.addNode(n4);//3
		g.addNode(n5);//4
		g.addNode(n6);//5
		g.addNode(n7);//6
		g.addNode(n8);//7
		g.connect(n1.getKey(), n2.getKey(), 7);
		g.connect(n2.getKey(), n1.getKey(), 2.77);
		g.connect(n2.getKey(), n3.getKey(), 4.5);
		g.connect(n5.getKey(), n3.getKey(), 10);
		g.connect(n6.getKey(), n4.getKey(), 4.11);
		g.connect(n3.getKey(), n5.getKey(), 3.55);
		g.connect(n5.getKey(), n7.getKey(), 42);
		g.connect(n1.getKey(), n5.getKey(), 23);
		g.connect(n6.getKey(), n2.getKey(), 4.20);
		
		Graph_Algo h = new Graph_Algo(g);
		
		List<Integer> nd = new ArrayList<Integer>();
		nd.add(2);
		nd.add(5);
		
		
		List<node_data> nl = h.TSP(nd);
		
		
		
		
		

	
	

		
		
	}

}