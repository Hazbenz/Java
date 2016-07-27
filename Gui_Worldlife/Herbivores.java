/**
 * Created by Maureen Hanna on 05/15/16.
 */
import java.awt.*;

public class Herbivores extends Animals {
    public static Integer numHerbs = 0;
    private Point target; //point of target for the Herbivore
    public Herbivores( int x, int y) {
        super(x, y);
    }
    @Override //implements the super class findTarget method
    public void findTarget(){

        int thisX = getLocation().x;
        int thisY = getLocation().y;
        target = null; //initially no targets.
        for(int i = thisY - sight; i < thisY + sight;i++){
            for (int j = thisX - sight; j < thisX + sight;j++) {

                try {//tries to look for a nearby Plant
                    if (Environment.getInstance().getMap(i,j) instanceof Plants) {//we are only interested in eating plants
                        Point newTarget = ((Plants)(Environment.getInstance().getMap(i,j))).getLocation();
                        if (target == null) {//if there is no previous target, initialize target as the first Plant seen yet
                            target = newTarget; //initializes target as first seen
                            //System.out.println("Initialized target");
                        }
                        else {//if there is a closer target update target
                            if (newTarget.distance(this.getLocation()) < target.distance(this.getLocation())) {
                                target = newTarget; //sets the target to the closest target.
                                //System.out.println("Updated target");
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException | ClassCastException | NullPointerException e){} //catch the error of looking for a plant that's not in the range of the Environment
            }
        }
        if(target == null){ //if there were no possible plants to locate then move randomly.
            tryRandomMove();
            //System.out.println("No target, did random move");
            return;
        }
        if(eat(target)){ //if Herbivore is within range to eat a Plant, eat and return
            target = null; //just ate, so now Herbivore has no target, for now..
            return;
        }
        moveToward(target); //else move closer to the assigned target
    }
    @Override
    public void tryBirth(Point location) {//makes an attempt to create a new Carnivore
        if (validMove(location)) {
            setEnergy(getEnergy() - 3); //decrement parent Energy by 3 for giving birth
            Environment.getInstance().setMap(location.y, location.x, new Herbivores(location.x, location.y));
            new Thread ((Animals)Environment.getInstance().getMap(location.y, location.x)).start(); //Polymorphism
        }
    }
    @Override
    public void run(){
            while (isAlive()) {
                try {
                    Thread.sleep((long) (Simulator.WAIT_TIME *2)); //Herbivores move twice as slow as Carnivores
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                findTarget();
                incAge();//increment age by 1
                birth();
                setEnergy(getEnergy()-1); //Every movements results in decreased energy

            }
        //System.out.printf("%s%s%n", this.getClass().getName(), " life Ends after here.");
        death();

    }
    public String toString() {
        return " & ";
    }

}