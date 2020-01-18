package gameObjects;

import java.util.ArrayList;
import java.util.List;

public class RobotCollector {

    private List<Robot> RC = new ArrayList<>();

    //getter

    public List<Robot> getRC() {
        return this.RC;
    }

    


    public int getSize() {
        return this.RC.size();
    }

    public void addRobot(Robot r) {
        this.RC.add(r);
    }

    public Robot getRobot(int id) {
        for (Robot r : RC) {
            if(r.getID() == id) return r;
        }
        return null;
    }


}