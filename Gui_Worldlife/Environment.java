/**
 * Created by Maureen Hanna on 05/15/16.
 */
public class Environment
{
    private final int width;
    private final int length;

    private final static int NUM_ANIMALS = 25;
    private Object[][] map;

    private static Environment instance;

    private Environment(int width, int length)
    {
        this.width = width;
        this.length = length;
        map = new Object[length][width];
    }

    public boolean start()
    {
        initialize();
        return true;
    }

    private void initialize()
    {
       for (int i = 0; i < NUM_ANIMALS; i++) {
           //Initialize Animals
            int xL = (int) (Math.random() * width);
            int yL = (int) (Math.random() * length);

            if( Math.random() < 0.6 ) {
                map[yL][xL] = new Herbivores(xL, yL);
            }
            else {
                map[yL][xL] = new Carnivores(xL, yL);
            }
            new Thread((Animals)map[yL][xL]).start(); //Polymorphism

            //Initialize Plants
            int xP = (int) (Math.random() * width);
            int yP = (int) (Math.random() * length);
            if(Math.random() < 0.4){
                map[yP][xP] = new Plants(xP, yP);
                new Thread((Plants) map[yP][xP]).start();
            }

        }
    }


    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }

    public static Environment getInstance()
    {
        if( instance == null)
            return null;

        return instance;
    }

    public static Environment createNewInstance(int x, int y)
    {
        if( instance != null)
            return null;

        instance = new Environment(x , y);

        return instance;
    }
    public synchronized Living getMap(int i, int j) { //synchronizes the access of the map
        return (Living)map[i][j];
    }
    public synchronized void setMap(int i, int j, Living life) { //synchronize the  modification of the map
        map[i][j] = life;
    }
    public synchronized void setNull(int i, int j) { //synchronize the  modification of the map
        map[i][j] = null;
    }

}