//package project1;
/* 
 * File Name: P1GUI
 * Author: Eric Olsen
 */

import java.awt.BorderLayout;
import java.awt.Dimension;
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



public class P1GUI {
	
	//These fields should help manage the growth and complexity of this GUI
	private static final int GUI_WIDTH = 640;
	private static final int GUI_HEIGHT = 180;
	private static final String FRAME_TITLE = "Infix Expression Evaluator";
	
	//here is our hand-coded GUI. Comments explain most of the details
	public P1GUI(){
		JFrame frame = new JFrame(FRAME_TITLE);
		frame.setSize(GUI_WIDTH, GUI_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//this let's the OS decide where to put the frame
		frame.setLocationByPlatform(true);
		
		//we'll use three panels within the frame
		
		//top panel
		JPanel topPanel = new JPanel();
		//top left label
		JLabel infixExpressionLabel = new JLabel("Enter Infix Expression");
		//this is needed so the text field doesn't crowd out the label
		topPanel.add(infixExpressionLabel, BorderLayout.WEST);
		//top right textfield
		JTextField infix = new JTextField("");
		infix.setPreferredSize(new Dimension (GUI_WIDTH/3, 24));
		//this addresses a weird quirk that I didn't fix with the program
		infix.setToolTipText("You may only use the basic 4 operators ('+', '-', '*', "
				+ " and '/' ). If you leave spaces between multiple digit numbers,\n"
				+ " this program will also ignore spaces between numbers"
				+ "and consider them one large number."
				+ "Example: 2 2 2 will be 222.");
		topPanel.add(infix, BorderLayout.EAST);
		
		//middle panel
		JPanel middlePanel = new JPanel();
		//Evaluate button
		JButton evaluateButton = new JButton("Evaluate");
		
		middlePanel.add(evaluateButton, BorderLayout.CENTER);
		
		//bottom panel
		JPanel bottomPanel = new JPanel();
		//result label
		JLabel resultLabel = new JLabel("Result");
		bottomPanel.add(resultLabel);
		//result text field
		JTextField resultTextField = new JTextField("");
		resultTextField.setPreferredSize(new Dimension (GUI_WIDTH/3, 24));
		resultTextField.setEditable(false);
		bottomPanel.add(resultTextField);
		
		//add the panels to the frame
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(middlePanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		
		//this makes it pretty and saves me work
		frame.pack();
		
		//action listener for clicking the button
		evaluateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String infixExpression = infix.getText();
				//send String into our class for calculations
				resultTextField.setText(
						callInfixEvaluator(infixExpression));
			}
		});
		
		//take the enter key to run the calculation
		infix.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					String infixExpression = infix.getText();
					//call to private method below
					resultTextField.setText(
							callInfixEvaluator(infixExpression));
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				// not used
			}
			@Override
			public void keyTyped(KeyEvent e) {
				// not used
			}
		});
		
		//lesson learned: put this at the end or you won't see your stuff
		frame.setVisible(true);
	}
	
	//private method added to avoid repetitious coding
	//this is our call to the InfixEval Class which passes a String of the infix
	//and receives a String of the result.
	private String callInfixEvaluator(String infixExpression){
		String answer = "";
		try{
			InfixEval stackCalculator = new InfixEval();
			//the evaluate method in InfixEval accepts a String and returns an int
			int result = stackCalculator.evaluate(infixExpression);
			//we make the returned int into a String and send it back to the calling method
			answer = String.valueOf(result);
			return answer;
		//Our first custom exception. JOptionPane call happens in it's own class
		}catch (IllegalOperatorException e) {
			e.printStackTrace();
		//this mostly works, but the arithmetic exception keeps taking over this exception
		}catch (DivideByZeroException e){
			e.printStackTrace();
		}
		return answer;
	}
	
	public static void main (String[] args){
		P1GUI app = new P1GUI();
		
	}

}


