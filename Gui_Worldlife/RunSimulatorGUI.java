import javax.swing.*;

/**
 * Created by Maureen Hanna on 05/15/16.
 */

public class RunSimulatorGUI {
    protected static Integer  numSims = 0; //stores the number of simulations
    public static void main(String[] args){
        //fire up the Environment properties
        Environment.createNewInstance(20, 20);

        //fire up the simulation
        SimulatorGUI simulator = new SimulatorGUI();
        simulator.init(); //initializes the GUI with contents
        simulator.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        simulator.setSize(700,400);
        simulator.setVisible(true);
    }
}