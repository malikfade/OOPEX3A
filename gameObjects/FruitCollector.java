package gameObjects;

import Server.fruits;
import Server.game_service;
import dataStructure.DGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FruitCollector {

    private List<Fruit> FC = new ArrayList<>();

    //constructor

    //methods

    public int getSize() {
        return this.FC.size();
    }

    public void addFruit(Fruit f) {
        this.FC.add(f);
    }

    public List<Fruit> getFC(){return FC;}

    public Fruit getFruit(int src , int dest) {
        for (Fruit f : FC) {
            if(f.getE().getSrc() == src && f.getE().getDest() == dest) return f;
        }
        return null;
    }

    public void fruitID_SRC(game_service game, DGraph graph){
        List<String> info = game.getFruits();
        Iterator<String> itr = info.iterator();
        int i=0;
        while(itr.hasNext()){
          String fruit1 = itr.next();
          Fruit fruit = new Fruit(fruit1);
          fruit.setID(i);
          fruit.getE(graph.getE(graph));
          addFruit(fruit);
          i++;

        }
    }

}