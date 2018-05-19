//package homework3;
/* 
 * File Name: TestQ1.java
 * Author: Eric Olsen
 */
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

//a large portion of this GUI was taken from the P1GUI submitted during week 2.
//I'll leave most of my old comments because they help me remember how to program GUIs
public class TestQ1 {
	
	//These fields should help manage the growth and complexity of this GUI
	private static final int GUI_WIDTH = 640;
	private static final int GUI_HEIGHT = 400;
	private static final String FRAME_TITLE = "Recursive Base Calculator";
	
	//the general constructor sets up the GUI components and applicable listener methods
	public TestQ1(){
		JFrame frame = new JFrame(FRAME_TITLE);
		frame.setSize(GUI_WIDTH, GUI_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationByPlatform(true);
		
		//we'll use three panels within the frame
		
		//top panel
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(4, 2, 20, 20));
		
		JLabel decimalLabel = new JLabel("base 10 number");
		decimalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		JTextField decimalNumber = new JTextField("");
		decimalNumber.setToolTipText("This is our starting base-10 number");

		JLabel baseLabel = new JLabel("selected base");
		baseLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		JTextField baseNumber = new JTextField("");
		baseNumber.setToolTipText("Enter a number between 2 and 9");
		
		JButton convert1Button = new JButton("convert1");
		JTextField convert1Result = new JTextField("");

		
		topPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		topPanel.add(decimalLabel);
		topPanel.add(decimalNumber);
		topPanel.add(baseLabel);
		topPanel.add(baseNumber);
		topPanel.add(convert1Button);
		topPanel.add(convert1Result);
		
		
		//bottom panel
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(3, 2, 20, 20));
		
		JLabel stringLabel = new JLabel("string number representation");
		stringLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		JTextField stringInput = new JTextField("");
		stringInput.setToolTipText("This is our number in a non-base-10");
		
		JLabel baseSystemLabel = new JLabel("base system");
		baseSystemLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		JTextField baseSystem = new JTextField("");
		baseSystem.setToolTipText("This is the base that it's currently in");
				
		JButton convert2Button = new JButton("convert2");
		JTextField convert2Result = new JTextField("");
		
		bottomPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		bottomPanel.add(stringLabel);
		bottomPanel.add(stringInput);
		bottomPanel.add(baseSystemLabel);
		bottomPanel.add(baseSystem);
		bottomPanel.add(convert2Button);
		bottomPanel.add(convert2Result);
				
		
		//add the panels to the frame
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		//this makes it pretty and saves me work
		frame.pack();
		
		//action listener for clicking the top button
		convert1Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
			boolean error = false;
			String tempString1 = decimalNumber.getText();
			int decimal = 0;
			//using a private method below to check for valid numbers
			if (isNumber(tempString1)){
				decimal = Integer.valueOf(decimalNumber.getText());
			} else {
				JOptionPane.showMessageDialog(null,"There was an issue processing your inputs"	, 
						"Input Error", JOptionPane.ERROR_MESSAGE);
				error = true;
			}
			
			String tempString2 = baseNumber.getText();
			int base = 0;
			
			if (isNumber(tempString2)){
				base = Integer.valueOf(baseNumber.getText());
				if (base < 2 || base > 9){
					JOptionPane.showMessageDialog(null,"The base must be between 2 and 9"	, 
							"Improper Base", JOptionPane.ERROR_MESSAGE);
					error = true;
				}
			}else {
				JOptionPane.showMessageDialog(null,"There was an issue processing your inputs"	, 
						"Input Error", JOptionPane.ERROR_MESSAGE);
				error = true;
			}
			
			//call to private method below. The numbers should be in proper format
			if (!error){
				int answer = convert1(decimal, base);
				convert1Result.setText(String.valueOf(answer));
			}
			
			}
		});
		
		//action listener for clicking the bottom button
		convert2Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
			boolean error = false;
			String stringRep = stringInput.getText();
			if (isNumber(stringRep)){
				
			} else {
				JOptionPane.showMessageDialog(null,"There was an issue processing your inputs"	, 
						"Input Error", JOptionPane.ERROR_MESSAGE);
				error = true;
			}
			
			String tempString1 = baseSystem.getText();
			int base = 0;
			if (isNumber(tempString1)){
				base = Integer.valueOf(baseSystem.getText());
				if (base < 2 || base > 9){
					JOptionPane.showMessageDialog(null,"The base must be between 2 and 9"	, 
							"Improper Base", JOptionPane.ERROR_MESSAGE);
					error = true;
				}
			} else{
				JOptionPane.showMessageDialog(null,"There was an issue processing your inputs"	, 
						"Input Error", JOptionPane.ERROR_MESSAGE);
				error = true;
			}
			//input parameters should now be correct
			//call to private method below
			if (!error){
				int answer = convert2(stringRep, base);
				convert2Result.setText(String.valueOf(answer));
			}
			
			}
		});
				
		frame.setVisible(true);
	}

	private static int convert1(int n, int base){ //base must between 2 and 9
		if (n < base){
			return n;
		} else {
			return (n % base) + convert1(n/base, base) * 10;
		}
	}
	
	//private boolean method for ensuring proper method inputs
	private boolean isNumber(String str){
		try{
			int num = Integer.parseInt(str);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}
	
	private static int convert2(String number, int base){ //base must between 2 and 9
		int digit = Integer.parseInt(number.substring(number.length() - 1, number.length())); //right-most value
		if (number.length() < 2){
			return digit;
		} else {
			number = number.substring(0, number.length()-1); //shorten the right-most value
			return digit + convert2(number, base) * base; //add what we have, call again, multiply by the base
		}
	}

	
	//main method that only starts up the GUI
	public static void main (String[] args){
		TestQ1 app = new TestQ1();
		
	}
}
