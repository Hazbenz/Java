/**
 * Created by Hasna Benzekri on 05/15/16.
 */

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ExecutionException;
import javax.swing.*;

public class SimulatorGUI extends JFrame{
    private JButton simulation = new JButton(); //Area to display the the Environment map, has gridlayout as component
    private JTextField textSims = new JTextField("0"); //displays the simulation count
    private JTextField textCarns = new JTextField("0"); //displays the Carnivore count
    private JTextField textHerbs = new JTextField("0");//displays the Herbivore count
    private JTextField textPlants = new JTextField("0");//displays the Plant count


    private GridBagConstraints constraint = new GridBagConstraints(); //gives the Layout in the JFrame some constraints
    private final int eLength = Environment.getInstance().getLength(); //length of the Environment
    private final int eWidth = Environment.getInstance().getWidth(); //width of the Environment
    private JTextField gridValues[][] = new JTextField[eLength][eWidth]; //references the values of the GirdLayout for easy modification.

    public SimulatorGUI() {
        super("A Simulator For Wildlife"); //Title of the Window
        setLayout(new GridBagLayout());

    }
    public void init(){
        centerLayout(); //Center Layout, Simulation Outputs
        eastLayout(); //Eastern Layout status and start functionality
        southLayout(); //Southern Layout living legend and symbols

    }
    public void centerLayout(){ //responsible for the components on the Center window
        //Layout for Center Layout
        simulation.setLayout(new GridLayout(eLength, eWidth));
        constraint.weightx = 10;
        constraint.weighty = 10;
        constraint.fill = GridBagConstraints.BOTH;
        initCenter(simulation);
        //add simulation to GridBagLayout
        add(simulation, constraint);

    }
    private void eastLayout(){ //responsible for the components on the Eastern window
        final JButton status = new JButton();
        //Layout constraints for the EasterLayout
        constraint.fill = GridBagConstraints.BOTH;
        constraint.weightx = 1.0;
        constraint.gridwidth = GridBagConstraints.REMAINDER;

        status.setLayout(new BoxLayout(status, BoxLayout.Y_AXIS)); // set a BoxLayout inside the Eastern Layout

        //# of Simulation Button with properties
        JButton nSims = new JButton("Number of Simulation");
        status.add(nSims);
        textSims.setEditable(false);
        textSims.setHorizontalAlignment(JTextField.CENTER);
        status.add(textSims);

        //# of Carnivores Button with properties
        JButton nCarns = new JButton("Number of Carnivores");
        status.add(nCarns);
        textCarns.setEditable(false);
        textCarns.setHorizontalAlignment(JTextField.CENTER);
        status.add(textCarns);

        //# of Herbivores Button with properties
        JButton nHerbs = new JButton("Number of Herbivores");
        status.add(nHerbs);
        textHerbs.setEditable(false);
        textHerbs.setHorizontalAlignment(JTextField.CENTER);
        status.add(textHerbs);

        //# of Plants Button with properties
        JButton nPlants = new JButton("Number of Plants");
        status.add(nPlants);
        textPlants.setEditable(false);
        textPlants.setHorizontalAlignment(JTextField.CENTER);
        status.add(textPlants);

        status.add(startButton());

        add(status, constraint);
    }
    private void southLayout(){ //responsible for the components on the Southern window
        final String LIVING = "Carnivore: @    Herbivore: &    Plant: *    empty space: ."; //legend and symbols
        final JButton legend = new JButton(LIVING); //create legend as a button, no action will be set to it though, just for display
        legend.setToolTipText("Symbols and representation of content"); //description of legends
        legend.setForeground(Color.blue);
        constraint.weightx = 0;
        constraint.weighty = 0;

        constraint.gridwidth = GridBagConstraints.REMAINDER;
        constraint.fill = GridBagConstraints.HORIZONTAL;
        add(legend, constraint);
    }
    private JButton startButton(){
        ImageIcon simButton = new ImageIcon((getClass().getResource("Button.png")));
        final JButton startButton = new JButton(simButton);
        startButton.setToolTipText("Simulation Start"); //description of button functionality

        //creates a handler to handle events of startButton being clicked.
        StartButtonHandler handler = new StartButtonHandler(); //registers a button handler
        startButton.addActionListener(handler); //startButton is now associated with this handler

        return startButton;
    }

    public void initCenter(JComponent simulation){
        //sets the GridLayout to have the same number of rows and columns as the Environment
        //simulation.setLayout(new GridLayout(Environment.getInstance().getWidth(), Environment.getInstance().getLength()));
        for(int row = 0; row < eLength;row++){
            for(int col = 0; col < eWidth; col++){
                JTextField element = new JTextField();
                element.setEditable(false);
                element.setText("."); //since no living things are on the Environment yet, represent everything as empty
                element.setHorizontalAlignment(JTextField.CENTER);
                element.setForeground(Color.black);
                gridValues[row][col] = element; //stores this JTexField for later use.
                simulation.add(gridValues[row][col]);
            }
        }
    }

    /*This displayWorld method is not as efficient as the displayWorld method in Simulator Class as I would have hoped.
    * The trouble arises when using Gridlayout and multithreading, I plan on optimizing this code even after submission for the deadline*/
    public void displayWorld(){
            /*  SwingWorker to create a new thread and compute what to display in the background for efficiency */
        SwingWorker<JTextField[][], Void> worker = new SwingWorker<JTextField[][], Void>() {
            @Override
            protected JTextField[][] doInBackground() throws Exception { //update gridValues in the background
                simulation.invalidate();
                Carnivores.numCarns = 0;//stores the number of Carnivores seen
                Herbivores.numHerbs = 0;//stores the number of Herbivores seen
                Plants.numPlants = 0;//stores the number of Plants seen
                for (int row = 0; row < eLength; row++) {
                    //System.out.println();
                    for (int col = 0; col < eWidth; col++) {
                        JTextField element = gridValues[row][col]; //gets the JTextField at the specified index of the GridLayout
                        Living livingThing = Environment.getInstance().getMap(row, col);//references the Living Object at the same index of the Environment map
                        if (livingThing != null) {//if there is an instance of a Living present
                            element.setText(livingThing.toString()); //The JTextField should be set to the string representation of the Living Object
                            if (livingThing instanceof Carnivores) {
                                element.setForeground(Color.red);//all Carnivores are colored red.
                                Carnivores.numCarns++;//increment the number of carnivores seen, for Eastern Layout display
                            } else if (livingThing instanceof Herbivores) {
                                element.setForeground(Color.cyan); //all Herbivores are colored cyan.
                                Herbivores.numHerbs++; //increment the number of Herbivores seen, for Eastern Layout display
                            } else if (livingThing instanceof Plants) {
                                element.setForeground(Color.green); //all Herbivores are colored green.
                                Plants.numPlants++; //increment the number of carnivores seen, for Eastern Layout display
                            }
                            //System.out.print(livingThing);
                        }
                        else {
                            element.setText(".");//if null, represent location by an empty space
                            element.setForeground(Color.blue); //all empty spaces are colored gray.
                                                    }

                        element.revalidate();
                        element.repaint();
                    }
                   
                }
               
                textCarns.setText(Carnivores.numCarns.toString()); //update the JTextField responsible for displaying the number of Carnivores
                textHerbs.setText(Herbivores.numHerbs.toString());  //update the JTextField responsible for displaying the number of Herbivores
                textPlants.setText(Plants.numPlants.toString()); //update the JTextField responsible for displaying the number of Plants
                return gridValues;
            }

            @Override
            protected void done() { //runs after the background computation has finished

                for(int i = 0; i< eLength;i++){
                    for(int j = 0; j< eWidth; j++){
                        try {
                            get()[i][j].revalidate(); //revalidate the component hierarchy.
                            get()[i][j].repaint(); //update the the additions in JTextField
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                    //After the JTextFields has been updated, validate and repaint the whole layout.
                    simulation.validate();
                    simulation.repaint();
                }
            }
        };
        worker.execute();
    }

    private class StartButtonHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent event){
            if(Environment.getInstance().start()){ //if the the Environment has already been started, just display
                RunSimulatorGUI.numSims++; //Increment the number of simulations
                displayWorld();  //displays the current state of the Environment to the GUI

            }
            else{ //else start the Environment and display it.
                Environment.getInstance().start();
                RunSimulatorGUI.numSims++; // Increment the number of simulations
                displayWorld(); //displays the current state of the Environment to the GUI
            }
            textSims.setText(RunSimulatorGUI.numSims.toString()); //update the JTextField responsible for displaying the number of simulations
        }
    }
    
}

