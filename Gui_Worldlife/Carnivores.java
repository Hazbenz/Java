/**
 * Created by Maureen Hanna on 05/15/16.
 */
import java.awt.Point;
public class Carnivores extends Animals {
    public static Integer numCarns = 0;
    private Point target; //point of target for the Carnivore
    public Carnivores(int x, int y)
    {
        super(x, y);
    }

    @Override //implements the super class findTarget method
    public void findTarget(){

        int thisX = getLocation().x;
        int thisY = getLocation().y;

        //Scans the Environment looking for a close target
        for(int i = thisY - sight; i < thisY + sight;i++){ //length
            for (int j = thisX - sight; j < thisX + sight;j++) {//width

                try {//tries to look for  a nearby Herbivore
                    if (Environment.getInstance().getMap(i,j) instanceof Herbivores) {//we are only interested in eating Herbivores
                        Point newTarget = ((Herbivores)(Environment.getInstance().getMap(i,j))).getLocation();
                        if (target == null) { //if there is no previous target, initialize target as the first Herbivore seen yet
                            target = newTarget; //initializes target as first seen
                            //System.out.println("Initialized target");
                        }
                        else {//if there is a closer target update target
                            if (newTarget.distance(this.getLocation()) < target.distance(this.getLocation())) {
                                target = newTarget;//sets the target to the closest target.
                                //System.out.println("Updated target");
                            }
                        }
                    }
                } catch (IndexOutOfBoundsException | ClassCastException | NullPointerException e){} //catch the error of looking for a herbivore that's not in the range of the Environment
            }
        }
        if(target == null){ //if there were no possible Herbivores to locate then move randomly.
            tryRandomMove();
            //System.out.println("No target, did random move");
            return;
        }
        if(eat(target)){ //checks if Carnivore is within range to eat a Herbivore, eat and return
            target = null; //just ate, so now Carnivore has no target, for now..
            return;
        }
        moveToward(target); //else move closer to the assigned target
    }

    @Override
    public void tryBirth(Point location) {//makes an attempt to create a new Carnivore
        if (validMove(location)) {//uses validMove to help determine a close location to give birth in.
            setEnergy(getEnergy()-3); //decrement parent Energy by 3 for giving birth
            Environment.getInstance().setMap(location.y, location.x, new Carnivores(location.x, location.y));
            new Thread ((Animals)Environment.getInstance().getMap(location.y, location.x)).start(); //Polymorphism
        }
    }
    @Override
    public void run(){
        {
            while (isAlive()) {
                try {
                    Thread.sleep((long) (Simulator.WAIT_TIME));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                findTarget();
                incAge();//increment age by 1
                birth();
                setEnergy(getEnergy() - 1); //Every movements results in decreased energy
            }
            death();
        }
    }
    public String toString()
    {
        return " @ ";
    }

}
