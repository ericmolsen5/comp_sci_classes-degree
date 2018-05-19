//package project3;
/*
 *  File Name: P3GUI.java
 *  Author: Eric Olsen
 */
import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class P3GUI {
	
	//These fields should help manage the growth and complexity of this GUI
	private static final int GUI_WIDTH = 400;
	private static final int GUI_HEIGHT = 320;
	private static final String FRAME_TITLE = "Binary Search Tree Sort";
			
		//here is our hand-coded GUI. Comments explain most of the details
		public P3GUI(){
		JFrame frame = new JFrame(FRAME_TITLE);
		frame.setSize(GUI_WIDTH, GUI_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//this let's the OS decide where to put the frame
		frame.setLocationByPlatform(true);
		
		//There's a few panels inside of panels for this GUI
		//It resembles something like this:
		/*   top panel
		 * [  [toptopPanel]    ]
		 * [ [topmiddlePanel]  ]
		 * [  [topbottomPanel] ]
		 *   bottom panel
		 *   [bottom   [bottom
		 *    left      right
		 *    panel ]   panel ]
		 */   
		JPanel topPanel = new JPanel (new GridLayout (3,1));
		
		JPanel topTopPanel = new JPanel();

		//top left label
		JLabel originalListLabel = new JLabel("Original List");
		topTopPanel.add(originalListLabel, BorderLayout.WEST);
		
		//top right textfield
		JTextField originalList = new JTextField("4 8 2 1 23 16 8 16 3 14 2 10 24");
		originalList.setPreferredSize(new Dimension (GUI_WIDTH/2 + 25, 24));
		//tool tip for leftover quirks with the program
		originalList.setToolTipText("Please ensure that only spaces are used to"
				+ " separate the sorted elements. Fractions should use a backslash"
				+ " and, as an example, look like this: 1/2 3/4 3/2...");
		topTopPanel.add(originalList, BorderLayout.CENTER);
		
		JPanel topMiddlePanel = new JPanel();
				
		//second down label
		JLabel sortedListLabel = new JLabel("Sorted List");
		topMiddlePanel.add(sortedListLabel, BorderLayout.WEST);
		
		//second down textfield
		JTextField sortedList = new JTextField("");
		sortedList.setPreferredSize(new Dimension (GUI_WIDTH/2 + 25, 24));
		//tool tip for leftover quirks with the program
		//sortedList.setToolTipText("instructions for the program");
		sortedList.setEditable(false);
		topMiddlePanel.add(sortedList, BorderLayout.CENTER);
		
		JPanel topBottomPanel = new JPanel();
				
		JButton performSortButton = new JButton("Perform Sort");
		performSortButton.setPreferredSize(new Dimension(GUI_WIDTH/3, 24));
		topBottomPanel.add(performSortButton, BorderLayout.CENTER);
		
		topPanel.add(topTopPanel);
		topPanel.add(topMiddlePanel);
		topPanel.add(topBottomPanel);
		
		
		//bottom panels for the radio buttons, which will be two panels within a panel
		JPanel bottomPanel = new JPanel(new GridLayout (1,1) );
		
		JPanel bottomLeftPanel = new JPanel();
		//bottomLeftPanel.add("Sort Order", bottomLeftPanel);
		//box layout within the panel
		bottomLeftPanel.setBorder(BorderFactory.createTitledBorder("Sort Order"));
		bottomLeftPanel.setLayout(new BoxLayout(bottomLeftPanel, BoxLayout.Y_AXIS));
		
		ButtonGroup sortOrderGroup = new ButtonGroup();
		//ascending button
		JRadioButton ascendingRadioButton = new JRadioButton("Ascending"); 
		ascendingRadioButton.setSelected(true);
		//descending button
		JRadioButton descendingRadioButton = new JRadioButton("Descending");
		//add radio buttons to button group
		sortOrderGroup.add(ascendingRadioButton);
		sortOrderGroup.add(descendingRadioButton);
		//add buttons to box
		bottomLeftPanel.add(ascendingRadioButton);
		bottomLeftPanel.add(descendingRadioButton);
		
		//bottom left panel
		JPanel bottomRightPanel = new JPanel();
		//box layout within the panel
		bottomRightPanel.setBorder(BorderFactory.createTitledBorder("Numeric Type"));
		bottomRightPanel.setLayout(new BoxLayout(bottomRightPanel, BoxLayout.Y_AXIS));
		
		ButtonGroup numericTypeGroup = new ButtonGroup();
		//ascending button
		JRadioButton integerRadioButton = new JRadioButton("Integer");
		integerRadioButton.setSelected(true);
		//descending button
		JRadioButton fractionRadioButton = new JRadioButton("Fraction");
		//add radio buttons to button group
		numericTypeGroup.add(integerRadioButton);
		numericTypeGroup.add(fractionRadioButton);
		
		//add buttons to box
		bottomRightPanel.add(integerRadioButton);
		bottomRightPanel.add(fractionRadioButton);
		
		
		bottomPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		bottomPanel.add(bottomLeftPanel);
		bottomPanel.add(bottomRightPanel);
		

		//add the panels to the frame
		frame.add(topPanel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		
		
		//action listener for clicking the button
		performSortButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				//ascending integer
				if (ascendingRadioButton.isSelected() && 
						integerRadioButton.isSelected()){
					String unsorted = originalList.getText();
					unsorted = unsorted.replaceAll("\\s+", " "); //removes extra spaces
					sortedList.setText(sortAscendingInteger(unsorted));
				//descending integer	
				}else if (descendingRadioButton.isSelected() && 
						integerRadioButton.isSelected()){
					String unsorted = originalList.getText();
					unsorted = unsorted.replaceAll("\\s+", " ");//removes extra spaces
					sortedList.setText(sortDescendingInteger(unsorted));
				//ascending fraction
				}else if (ascendingRadioButton.isSelected() &&
						fractionRadioButton.isSelected()){
					String unsorted = originalList.getText();
					unsorted = unsorted.replaceAll("\\s+", " ");//removes extra spaces
					sortedList.setText(sortAscendingFraction(unsorted));
				//descending fraction	
				}else if (descendingRadioButton.isSelected() && 
						fractionRadioButton.isSelected()){
					String unsorted = originalList.getText();
					unsorted = unsorted.replaceAll("\\s+", " ");//removes extra spaces
					sortedList.setText(sortDescendingFraction(unsorted));					
				} 
				//code should never reach here
				else {
					System.out.println("weird error occured with the GUI");
				}
			}
		});
			
		//take the enter key to run the calculation
		originalList.addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER){
					//ascending integer
					if (ascendingRadioButton.isSelected() && 
							integerRadioButton.isSelected()){
						String unsorted = originalList.getText();
						unsorted = unsorted.replaceAll("\\s+", " ");//removes extra spaces
						sortedList.setText(sortAscendingInteger(unsorted));
					//descending integer	
					}else if (descendingRadioButton.isSelected() && 
							integerRadioButton.isSelected()){
						String unsorted = originalList.getText();
						unsorted = unsorted.replaceAll("\\s+", " ");//removes extra spaces
						sortedList.setText(sortDescendingInteger(unsorted));
					//ascending fraction
					}else if (ascendingRadioButton.isSelected() &&
							fractionRadioButton.isSelected()){
						String unsorted = originalList.getText();
						unsorted = unsorted.replaceAll("\\s+", " ");//removes extra spaces
						sortedList.setText(sortAscendingFraction(unsorted));
					//descending fraction	
					}else if (descendingRadioButton.isSelected() && 
							fractionRadioButton.isSelected()){
						String unsorted = originalList.getText();
						unsorted = unsorted.replaceAll("\\s+", " ");//removes extra spaces
						sortedList.setText(sortDescendingFraction(unsorted));					
					} 
					//code should never reach here
					else {
						System.out.println("weird error occured with the GUI");
					}
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
		
		frame.setVisible(true);
	}
	
	//call to IntegerTree for ascending sorting
	private String sortAscendingInteger(String unsorted){
		GenericTree<Integer> sortAscending = new GenericTree<Integer>();
		Integer[] unsortedInt = generateUnsortedIntArray(unsorted);
		String sortedList = "";
		try{
			sortAscending.generateTree(unsortedInt);
			//quickfix
			//System.out.println(sortAscending.getRoot().right.right.left.toString());
			sortedList = sortAscending.inOrder(sortAscending.getRoot());
		} catch (NumberFormatException e){
			displayErrorMessage(); //general error
			e.printStackTrace();
		} catch (InputMismatchException e){ 
			e.printStackTrace();
			displayErrorMessage(); //general error
		}
		return sortedList; //final display
	}
	
	//call to IntegerTree for descenging sorting
	private String sortDescendingInteger(String unsorted){
		GenericTree<Integer> sortDescending = new GenericTree<Integer>();
		Integer[] unsortedInt = generateUnsortedIntArray(unsorted);
		String sortedList = "";
		try{
			sortDescending.generateTree(unsortedInt);
			sortedList = sortDescending.reverseInOrder(sortDescending.getRoot());
		} catch (NumberFormatException e){
			displayErrorMessage();//general error
			e.printStackTrace();
		} catch (InputMismatchException e){ 
			e.printStackTrace();
			displayErrorMessage();//general error
		}
		return sortedList; //final display
	}
	
	//call to fraction tree for ascending list
	private String sortAscendingFraction(String unsorted){
		FractionTree sortAscending = new FractionTree();
		String sortedList = "";
		try{
			sortAscending.generateTree(unsorted);
			sortedList = sortAscending.inOrder(sortAscending.getRoot());
		} catch (NumberFormatException e){
			doubleDivisorErrorMessage(); //improper character within fraction
			e.printStackTrace();
		} catch (InputMismatchException e){ 
			e.printStackTrace();
			displayErrorMessage(); //general error
		} catch (NoSuchElementException e){
			e.printStackTrace();
			displayErrorMessage(); //general error
		}
		return sortedList;
	}
	
	//call to fraction tree for descending list
	private String sortDescendingFraction(String unsorted){
		FractionTree sortDescending = new FractionTree();
		String sortedList = "";
		try{
			sortDescending.generateTree(unsorted);
			sortedList = sortDescending.reverseInOrder(sortDescending.getRoot());
		} catch (NumberFormatException e){
			doubleDivisorErrorMessage(); //improper character within fraction
			e.printStackTrace();
		} catch (InputMismatchException e){ //if no division signs exist
			e.printStackTrace();
			displayErrorMessage();//general error
		} catch (NoSuchElementException e){ //if no division signs exist
				e.printStackTrace();
				displayErrorMessage(); //general error
		}
		return sortedList;
	}
	
	//this is a bit of a workaround from my issues building the generic tree
	private Integer[] generateUnsortedIntArray(String unsorted){
		ArrayList<Integer> tempList = new ArrayList<Integer>();
		Scanner stringInput = new Scanner(unsorted).useDelimiter(" ");
		int tempInt = 0;
		//parse the integers out of the String and into an array
		try{
			while(stringInput.hasNext()){
				tempInt = stringInput.nextInt();
				tempList.add(tempInt);
			}
		} catch (InputMismatchException e){
			displayErrorMessage();
			e.printStackTrace();
			return null;
		} finally{
			stringInput.close();
		}
		Integer[] intList = new Integer[tempList.size()];
		for (int i = 0; i < intList.length; i++){
			intList[i] = (Integer) tempList.get(i);
		}
		return intList;
	}
	
	//this displays for the majority of exceptions 
	private void displayErrorMessage(){
		String errorMessage = "We encountered an error with your input. "
				+ "Please use only numbers, a single division marker for fractions,"
				+ " and separate using only spaces.";
		JOptionPane.showMessageDialog(null, errorMessage, "Invalid Input", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	//this should catch double divisors, division by zero, and other improper characters
	//within the fraction.
	private void doubleDivisorErrorMessage(){
		String errorMessage = "It looks like you included two divisors."
				+ "Please use only a single divisor for all fractions.";
		JOptionPane.showMessageDialog(null, errorMessage, "Double Divisor", 
				JOptionPane.ERROR_MESSAGE);
	}
	
	//main method
	public static void main (String[] args){
		P3GUI app = new P3GUI();
	}
		
		
}
