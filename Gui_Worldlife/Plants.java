import java.awt.*;

/**
 * Created by Hasna Benzekri on 05/15/16.
 */
public class Plants implements Living, Runnable{
    public static Integer numPlants = 0;
    private Point location; //Plants are represented as a Point on a 2D map
    private boolean alive = true; //Plants also have energy, but does increase
    public Plants() { //constructor to create an Plant at location (0,0)
        location = new Point();
    }
    public Plants(int x, int y) { //constructor to create an Plant at an arbitrary location(x,y)
        location = new Point(x, y);
    }
    public Point getLocation(){
        return this.location;
    }
    public boolean isAlive(){ //used as a reference to check if plant is active
      return alive;
    }
    @Override
    public void run(){
            if (isAlive()) { //if plant is alive meaning hasn't been eaten, let it live of 15 seconds
                try {
                    Thread.sleep(15000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                death();//kill this instance of a Plants

            }

    }
    public void death(){ //method for removing the plant from the Environment
        if(Environment.getInstance().getMap(this.location.y,this.location.x) != null) {
            Environment.getInstance().setNull(this.location.y, this.location.x);
            this.alive = false;
        }
    }
    @Override
    public String toString(){
        return " * ";
    }
}
