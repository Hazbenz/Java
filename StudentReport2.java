import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class StudentReport2 extends JFrame
{
private JLabel firstLabel, lastLabel, amountLabel, memoLabel, passLabel;
private JTextField firstTF, lastTF, amountTF, memoTF;
private JComboBox monthCB,dayCB,yearCB;
private JRadioButton singleRB,marriedRB,divorcedRB;
private ButtonGroup radioButtonGroup;
private JCheckBox veteranChB,fullTimeChB;
private JPasswordField passPF;
private JButton reportButton,addButton;
private JPanel pTop,pBottom,p1,p2,p3,p4,p5,p6;
private JTextArea reportTA;
private String [] months = {"Months", "January", "February", "March", "April", "May",
 "June", "July", "August", "September","October", "November", "December" };
private String [] days = {"Days","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20",
"21","22","23","24","25","26","27","28","29","30","31",};
private String [] years = {"Years","2005","2006","2007","2008","2009","2010","2011","2012","2013","2014","2015"};


public StudentReport2()
{
setLayout(new GridLayout(3,1));
		
		pTop = new JPanel();
		pBottom = new JPanel();
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		p4 = new JPanel();
		p5 = new JPanel();
		p6 = new JPanel();
		
		add(pTop);
		add(pBottom);
		add(p6);
		
		pTop.setLayout(new BorderLayout() );
		pTop.add(p1, BorderLayout.NORTH);
		pTop.add(p2, BorderLayout.EAST);
		pTop.add(p3, BorderLayout.CENTER);
		pTop.add(p4, BorderLayout.WEST);
		pTop.add(p5, BorderLayout.SOUTH);

firstLabel = new JLabel("First Name");
firstTF = new JTextField(10);
lastLabel = new JLabel("Last Name");
lastTF = new JTextField(10);
amountLabel = new JLabel("Amount");
amountTF = new JTextField(10);
memoLabel = new JLabel("Memo");
memoTF = new JTextField(10);
reportButton = new JButton("Report");
addButton =new JButton("Add Person");
passLabel = new JLabel("Password");
passPF = new JPasswordField(10);
//---------------------------------------
monthCB= new JComboBox(months);
dayCB= new JComboBox(days);
yearCB= new JComboBox(years);
//--------------------------------------
singleRB = new JRadioButton("Single",true);
marriedRB = new JRadioButton("Married", false);
divorcedRB = new JRadioButton("Divorced", false);
radioButtonGroup = new ButtonGroup();
radioButtonGroup.add(singleRB);
radioButtonGroup.add(marriedRB);
radioButtonGroup.add(divorcedRB);

//---------------------------------------------------
veteranChB = new JCheckBox("Veteran", false);
fullTimeChB = new JCheckBox("Full-Time Student", false);
//------------------------------------------------------
p1.add(firstLabel);
p1.add(firstTF); 
p1.add(lastLabel);
p1.add(lastTF);
p1.add(amountLabel);
p1.add(amountTF);
p1.add(memoLabel);
p1.add(memoTF);

amountTF.addActionListener(new ActionListener()
{
public void actionPerformed( ActionEvent e)	
			{
            double numb = 0.0;
            String amount = amountTF.getText();
            numb  = Double.parseDouble(amount);
                   

            if(numb < 0.0 || numb> 999.99){
            JOptionPane.showMessageDialog(null, "Enter A Number Between 0 and 999.99 ");
            amountTF.setEditable(true);
            
            }
            else{
            JOptionPane.showMessageDialog(null, "Correct Input");
            amountTF.setEditable(false);
            }
         }}
);


reportButton = new JButton("STUDENT REPORT");
p4.add(reportButton);

reportTA = new JTextArea(100,40);
pBottom.add(reportTA);

reportButton.addActionListener(new ActionListener()
		{
			
			public void actionPerformed( ActionEvent e)	
			{
            double numb = 0.0;
            String amount = amountTF.getText();
            numb  = Double.parseDouble(amount);
            String memo = memoTF.getText();    				
				String fName = firstTF.getText();
				String lName = lastTF.getText();
				int index = monthCB.getSelectedIndex();
				String month = months[ index ];
				String status1="", status2 = "", status3= "";
				
				index = dayCB.getSelectedIndex();
				String day = days [ index ];
            index = yearCB.getSelectedIndex();
				String year = years [ index ];
				String report = "";
            String passStr = passPF.getText();
             
              if ( e.getSource()== reportButton){
               p1.setVisible( false);
					p2.setVisible( false);
					p3.setVisible( false);
               p4.setVisible( false);
               p5.setVisible( false);
               pBottom.setVisible( false);
               p6.setVisible( false);
                              
               if ( passStr.equals( "BMCC"))
               {
                pBottom.setVisible( true);
                p6.setVisible( true);
                p4.setVisible( true);
               if (fullTimeChB.isSelected()){
					 status1 = "F";
               }else{
               status1="P";
               }
               if(veteranChB.isSelected()){
               status2 = "V";
               }else{
               status2="N";
               }
               
               if(singleRB.isSelected()){
               status3="S";
               }
               else if( marriedRB.isSelected()){
               status3="M";
               }
               else{
               status3="D";
               }              

               report += "STUDENT REPORT: " + "\n\nStudent: " + fName + " " + lName + "\nFullTime: "
                        + status1 + "\nVeteran: " + status2 + "\nMarital Status: " + status3 
				            +"\nMemo: " + memo +"\nRegistration Date : " + " Month :  " + month + " Day :  " + day +
                        " Year:  " + year + "\nAmount Due : " + numb ;
				    reportTA. append ( report);
               
                
                }
				
				else{	
					JOptionPane.showMessageDialog(null, "Enter Your Password Please. This report is for " + fName + " " + lName );
				   p2.setVisible(true);
               p4.setVisible( true);
               }
			}
       
				
	  	 }	} );
p6.add(addButton);
p3.add(monthCB);
p3.add(dayCB);
p3.add(yearCB);
p3.add(singleRB);
p3.add(marriedRB);
p3.add(divorcedRB);
p5.add(veteranChB);
p5.add(fullTimeChB);
p2.add(passPF);
p2.setVisible(false);
addButton.addActionListener(new clearFieldsActionListener());
 }
private class clearFieldsActionListener implements ActionListener
{
public void actionPerformed(ActionEvent a)
{
firstTF.setText(" ");
lastTF.setText(" ");
amountTF.setText(" ");
memoTF.setText(" ");
passPF.setText("");
reportTA.setText("");
amountTF.setEditable(true);

p1.setVisible( true);
p2.setVisible( false);
p3.setVisible( true);
p4.setVisible( true);
p5.setVisible( true);
pBottom.setVisible( false);
p6.setVisible( true);

} 
}
}
 class StudentReport1Test
{
public static void main (String[] args)
{
StudentReport2 w = new StudentReport2();
w.setSize(350,350);

w.setVisible(true);
w.setTitle("STUDENT REPORT FOR BMCC");
w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
}
}
