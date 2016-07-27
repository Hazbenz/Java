/**
 * Created by Maureen Hanna on 05/15/16.
 */
public class Simulator{
    private final static int ROUNDS = 30;
    public final static int WAIT_TIME = 1000;

    private final static String EMPTY = " . ";

    public static void main(String[] args) throws InterruptedException
    {
        Environment environment = Environment.createNewInstance(20, 20);
        environment.start();

        for (int i = 0; i < ROUNDS; i++) {
            System.out.printf("%s%d%n","Simulation: ", i+1);
            Thread.sleep(WAIT_TIME);
            displayWorld(environment);
            int xP = (int) (Math.random() * Environment.getInstance().getWidth());
            int yP = (int) (Math.random() * Environment.getInstance().getLength());
            if((ROUNDS % 5) == 0){ //create a new plant at a random location every 3 rounds
                Environment.getInstance().setMap(yP, xP, new Plants(xP, yP));
                new Thread((Plants)(Environment.getInstance().getMap(yP, xP))).start();
            }
        }

    }
    private static void displayWorld(Environment environment)
    {
        Carnivores.numCarns = 0;
        Herbivores.numHerbs = 0;
        Plants.numPlants =0;
        for (int i = 0; i < environment.getWidth(); i++) {
            for (int j = 0; j < environment.getLength(); j++) {
                Living livingThing = Environment.getInstance().getMap(i,j);
                if(livingThing != null) {
                    if (livingThing instanceof Carnivores)
                        Carnivores.numCarns++;
                    else if (livingThing instanceof Herbivores)
                        Herbivores.numHerbs++;
                    else if (livingThing instanceof Plants)
                        Plants.numPlants++;
                    System.out.print(livingThing);//Polymorphism
                }
                else{System.out.print(EMPTY);}
            }
            System.out.println();
        }
        System.out.printf("%n%s%d%s%d%s%d%n", "# of Carnivores: ", Carnivores.numCarns," # of Herbivores: ",
                Herbivores.numHerbs, " # of Plants: ", Plants.numPlants);
        for (int j = 0; j < environment.getWidth(); j++) {
            System.out.print("---");
        }
        System.out.println();
    }

}
