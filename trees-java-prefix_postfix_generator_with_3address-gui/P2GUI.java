//package project2;
/* 
 * File Name: P2GUI.java
 * Author: Eric Olsen
 */
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

//a large portion of this GUI was taken from the P1GUI submitted during week 2.
//I'll leave most of my old comments because they help me remember how to program GUIs
public class P2GUI {
	
	//These fields should help manage the growth and complexity of this GUI
	private static final int GUI_WIDTH = 640;
	private static final int GUI_HEIGHT = 180;
	private static final String FRAME_TITLE = "Three Address Generator";
		
	//here is our hand-coded GUI. Comments explain most of the details
	public P2GUI(){
		JFrame frame = new JFrame(FRAME_TITLE);
		frame.setSize(GUI_WIDTH, GUI_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//this let's the OS decide where to put the frame
		frame.setLocationByPlatform(true);
		
		//we'll use three panels within the frame
		
		//top panel
		JPanel topPanel = new JPanel();
		//top left label
		JLabel postExpressionLabel = new JLabel("Enter Postfix Expression");
		//this is needed so the text field doesn't crowd out the label
		topPanel.add(postExpressionLabel, BorderLayout.WEST);
		//top right textfield
		JTextField postfix = new JTextField("3 5 9 + - 2 3 * / ");
		postfix.setPreferredSize(new Dimension (GUI_WIDTH/3, 24));
		//tool tip for leftover quirks with the program
		postfix.setToolTipText("example: 22+ is 2+2. This program will attempt to throw "
				+ "warnings if proper postfix notation is not followed. But nothing is perfect.");
		topPanel.add(postfix, BorderLayout.EAST);
		
		//middle panel
		JPanel middlePanel = new JPanel();
		//Evaluate button
		JButton constructTreeButton = new JButton("Construct Tree");
		
		middlePanel.add(constructTreeButton, BorderLayout.CENTER);
		
		//bottom panel
		JPanel bottomPanel = new JPanel();
		//result label
		JLabel infixExpressionLabel = new JLabel("Infix Expression");
		bottomPanel.add(infixExpressionLabel);
		//result text field
		JTextField infixExpressionTextField = new JTextField("");
		infixExpressionTextField.setPreferredSize(new Dimension (GUI_WIDTH/3, 24));
		infixExpressionTextField.setEditable(false);
		bottomPanel.add(infixExpressionTextField);
		
		//add the panels to the frame
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(middlePanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		
		//this makes it pretty and saves me work
		frame.pack();
		
		//action listener for clicking the button
		constructTreeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				String postExpression = postfix.getText();
				//call to private method below
				infixExpressionTextField.setText(callInfixGenerator(postExpression));				
			}
		});
			
		//take the enter key to run the calculation
		postfix.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					String postExpression = postfix.getText();
					//call to private method below
					infixExpressionTextField.setText(callInfixGenerator(postExpression));
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
	
	//this private method generates two outputs. The first is the infix in the jtextfield
	//the other is the saved file. I left the print screen command to ease in program testing.
	private String callInfixGenerator(String postfix){
		PostfixEval calculate = new PostfixEval();
		String prefix = null;
		//this must be called first because it generates ALL of the needed data structures
		try{
			prefix = calculate.generatePrefix(postfix);
		} catch (InvalidTokenException e){
			e.printStackTrace();
		} catch (IncorrectSyntaxException e){
			e.printStackTrace();
		}
		
		//this must be called second so the list of operatorNodes exists
		String threeAddress = calculate.generateInstructions();
		String filePath = "output.txt"; 
		
		try{
			FileWriter fileWriter = new FileWriter(filePath);
			System.out.println("Saved string:\n" + threeAddress); //to see what was saved to file
			fileWriter.write(threeAddress);
			fileWriter.close();
		}catch (IOException io){
			JOptionPane.showMessageDialog(null, io.getMessage(),
					"There was an issue writing to file", JOptionPane.ERROR_MESSAGE);
		}
		return prefix;
	}
	
	//main method that only starts up the GUI
	public static void main (String[] args){
		P2GUI app = new P2GUI();
		
	}

}
