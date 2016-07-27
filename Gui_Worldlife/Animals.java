/**
 * Created by Maureen Hanna on 05/15/16.
 */
import java.awt.Point;

//An abstract class is a class that is declared abstract—it may or may not include abstract methods. Abstract
//classes cannot be instantiated, but they can be subclassed.
//An abstract method is a method that is declared without an implementation (without braces, and followed by a
//semicolon

public abstract class Animals implements Living,Movable, Runnable {
    private int age = 1; //every Animal has an age of 1
    private int energy = 10;//every Animal has Energy of 6 (lasts 10 rounds without eating)
    public final int sight = 3;//how far around an Animal can see, example this Animal can alone find targets 3 distances from it
    private Point location; //Animals are represented as a Point on a 2d map
    public Animals() { //constructor to create an Animal at location (0,0)
        location = new Point();
    }

    public Animals(int x, int y) { //constructor to create an Animal at an arbitrary location(x,y)
        location = new Point(x, y);
    }
    /*House keeping methods for age, energy and a way to determine if the Animal is alive*/
    public int getAge(){
        return this.age;
    }
    public void incAge(){
        this.age++;
    }
    public int getEnergy(){
        return this.energy;
    }
    public void setEnergy(int newEnergy){
        this.energy = newEnergy;
    }

    public boolean isAlive(){ //used as a reference to check if Animal is active
        if(energy <= 0){
            return false;
        }
        else{return true;}
    }
    public void death(){ //method for removing the Animals from the Environment
        if(Environment.getInstance().getMap(this.location.y,this.location.x) != null) {
            Environment.getInstance().setNull(this.location.y, this.location.x);
            setEnergy(0); //energy is set to Zero, isAlive for animals checks the energy to determine if thread should continue
        }
    }
    public Point getLocation() {
        return location;
    }
    public void setLocation(Point location) {
        this.location = location;
    }
    public void move(Point location) {
        if (validMove(location)) {
            Environment.getInstance().setNull(this.location.y, this.location.x); //remove old location on Environment
            Environment.getInstance().setMap(location.y, location.x, this); //move to new location on Environment
            this.setLocation(location);//update new to location
        }
    }
    public void tryRandomMove() {
        int x = (int) location.getX();
        int y = (int) location.getY();

        int randomMove = (int) (Math.random() * 4);

        switch (randomMove) {
            case NORTH:
                move(new Point(x, y - 1));
                break;
            case SOUTH:
                move(new Point(x, y + 1));
                break;
            case WEST:
                move(new Point(x - 1, y));
                break;
            case EAST:
                move(new Point(x + 1, y));
                break;
        }
    }

    public void moveToward(Point location) {
        Point thisLocation = getLocation();

        double xDist = location.getX() - thisLocation.getX();
        double yDist = location.getY() - thisLocation.getY();

        if( Math.abs(xDist) > Math.abs(yDist) )
        {
            if( xDist < 0 )
            {
                move(new Point((int)(thisLocation.getX() - 1),(int)thisLocation.getY()));
            }
            else
            {
                move(new Point((int)(thisLocation.getX() + 1),(int)thisLocation.getY()));
            }
        }
        else
        {
            if( yDist < 0 )
            {
                move(new Point((int)thisLocation.getX(),(int)(thisLocation.getY()-1)));
            }
            else
            {
                move(new Point((int) thisLocation.getX(), (int) (thisLocation.getY() + 1)));
            }
        }
    }
    public boolean eat(Point food) {
        if((food.distance(this.getLocation()) == 1) && (getEnergy() < 17)){//checks if target is close by and Animal is hungry
            Living alive = Environment.getInstance().getMap(food.y, food.x);
            if(alive != null) {
                alive.death();
                Environment.getInstance().setNull(this.getLocation().y, this.getLocation().x); //remove previous location on the Environment
                Environment.getInstance().setMap(food.y, food.x, this); //move to preys location on Environment (eaten)
                this.setLocation(food);//update new to location

                setEnergy(getEnergy() + 3); //increment energy by 3 to account for the food eaten.
                //System.out.printf("%s%s%n", this.getClass().getName(), ": Just ate, Yum!");
                return true;
            }
        }
        return false;
    }
    public void birth() { //
        if (getEnergy() >= 13 && age >= 3) {
            int x = (int) location.getX();
            int y = (int) location.getY();

            int randomMove = (int) (Math.random() * 4); //find a random location close by to give birth

            switch (randomMove) {
                case NORTH:
                    tryBirth(new Point(x, y - 1));
                    break;
                case SOUTH:
                    tryBirth(new Point(x, y + 1));
                    break;
                case WEST:
                    tryBirth(new Point(x - 1, y));
                    break;
                case EAST:
                    tryBirth(new Point(x + 1, y));
                    break;
            }
        }
    }
    public abstract void tryBirth(Point spot);
    public boolean validMove(Point location) {
        int x = location.x;
        int y = location.y;

        int width = Environment.getInstance().getWidth();
        int length = Environment.getInstance().getLength();

        //can only move if within boundaries and point moving to is null
        if ((!(x >= width || y >= length || x < 0 || y < 0))){
            return (Environment.getInstance().getMap(y,x) == null);
        }
        return false;
    }
    //public abstract void moveToward(Point location);

    /*The method (findTarget) below is very crucial, the Animal looks for a target to eat within 3 distances.
      It first searches for the closest target, then then closest and so on.
      If a target is found it moves towards that target with the helper function moveTowards.
      Upon moving towards that target on it's way, it may encounter a much closer target.
      If a much closer target is found, it updates its target and sets path for this new target.
      If it doesn't encounter any new targets on its movement, it continues towards the direction
      of the original target. If the original target dies while the Animal is on the prowl to kill it,
      the Animal looks for a another target. All of this is done while the Animal is still active (alive)
    */
    public abstract void findTarget();
    public abstract void run();
    public String toString() {
        return "A";
    }
}