//package project4;
/*
 *  File Name: P4GUI.java
 *  Author: Eric Olsen
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


public class P4GUI {
	
	private static final int GUI_WIDTH = 550;
	private static final int GUI_HEIGHT = 350;
	private static final String FRAME_TITLE = "Class Dependency Graph";
	
	//create our instance of the VertexGraph class
	//I need this as a GUI field so both event listeners can call on the same class
	VertexGraph<String> graph = new VertexGraph<String>();
	
	//hand coded GUI
	public P4GUI(){
		
		JFrame frame = new JFrame(FRAME_TITLE);
		frame.setSize(GUI_WIDTH, GUI_HEIGHT);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLocationByPlatform(true);
		
		//I think we'll need two panels
		//top panel is a 2 x 3 grid bag layout
		//bottom panel is a bordered text area that is grayed out..I'm a little boggled with centering text
		
		JPanel topPanel = new JPanel (new GridBagLayout());
		//top panel constratings --> tc
		GridBagConstraints tc = new GridBagConstraints();
		tc.fill = (GridBagConstraints.HORIZONTAL);
		tc.insets = new Insets(10, 10, 10, 10);
		tc.weightx = 1; //look this up again
		
		//this is what the screen shot looks like, one space where the title should be
		topPanel.setBorder(BorderFactory.createTitledBorder(" "));
		
		//input filename label
		JLabel inputFileNameLabel = new JLabel("Input file name:");
		inputFileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tc.anchor = GridBagConstraints.CENTER;
		tc.gridx = 0;
		tc.gridy = 0;
		topPanel.add(inputFileNameLabel, tc);
		
		//text field for input filename
		JTextField inputFilePath = new JTextField("Graph.txt");
		inputFilePath.setPreferredSize(new Dimension (GUI_WIDTH/3, 24));
		tc.gridx = 1;
		tc.gridy = 0;
		topPanel.add(inputFilePath, tc);
		
		//button to build Directed Graph
		JButton buildDirectedGraph = new JButton("Build Directed Graph");
		tc.gridx = 2;
		tc.gridy = 0;
		topPanel.add(buildDirectedGraph, tc);
		
		//Class to recompile label
		JLabel classToRecompileLabel = new JLabel("Class to recompile:");
		classToRecompileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		tc.anchor = GridBagConstraints.CENTER;
		tc.gridx = 0;
		tc.gridy = 1;
		topPanel.add(classToRecompileLabel, tc);
		
		//text field for class selection
		JTextField inputClassRecompile = new JTextField("ClassA");
		inputClassRecompile.setPreferredSize(new Dimension (GUI_WIDTH/3, 24));
		tc.gridx = 1;
		tc.gridy = 1;
		topPanel.add(inputClassRecompile, tc);
		
		//topological order sort button
		JButton topologicalOrderButton = new JButton("Topological Order");
		tc.gridx = 2;
		tc.gridy = 1;
		topPanel.add(topologicalOrderButton, tc);
		
		////top panel complete, because it's grid bag layout there's no mass adding of objects////
		
		//bottom panel
		JPanel bottomPanel = new JPanel();
		
		//make border around text area
		bottomPanel.setBorder(BorderFactory.createTitledBorder("Recompilation Order"));
		//for the color difference between panels
		bottomPanel.setBackground(Color.WHITE);
		
			
		//Text pane is the answer for centering the text; JTextArea doesn't support horizontal text alignment
		JTextPane recompileTextPane = new JTextPane();
		recompileTextPane.setPreferredSize(new Dimension (GUI_WIDTH, GUI_HEIGHT/2 - 20));
		StyledDocument doc = recompileTextPane.getStyledDocument();
		SimpleAttributeSet center = new SimpleAttributeSet();
		StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
		doc.setParagraphAttributes(0, doc.getLength(), center, false);
		
		bottomPanel.add(recompileTextPane);
		
		
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		
		//we definitely need the pack method this time around
		frame.pack();
		
		////////////////////////event listeners///////////////////////////
		
		//build graph button
		buildDirectedGraph.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String graphData = null;
				try {
					graphData = readDataFromFile(inputFilePath.getText());
				} catch (FileNotFoundException e1){
					JOptionPane.showMessageDialog(null,"File Could Not Be Opened", 
							"File Read Error", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
				createGraph(graphData); //private method below
			}
		});
		
		//hit enter from the filepath textfield
		inputFilePath.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					String graphData = null;
					try {
						graphData = readDataFromFile(inputFilePath.getText());
					} catch (FileNotFoundException e1){
						JOptionPane.showMessageDialog(null,"File Could Not Be Opened", 
								"File Read Error", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
					createGraph(graphData);	//private method below
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
		
		//hit topological order button
		topologicalOrderButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				try{
					String start = inputClassRecompile.getText();
					recompileTextPane.setText(graph.topologicalSort(start));
				} catch (NullPointerException e1){
					JOptionPane.showMessageDialog(null,"The specified class is not in the graph.", 
							"Object Not Found", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}catch (CyclicGraphException e1){
					e1.printStackTrace();
				}
				
			}
		});
		
		//hit enter from the filepath textfield
		inputClassRecompile.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER){
					try{
						String start = inputClassRecompile.getText();
						recompileTextPane.setText(graph.topologicalSort(start));
					}catch (NullPointerException e1){
						JOptionPane.showMessageDialog(null,"The specified class is not in the graph.", 
								"Object Not Found", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					} catch (CyclicGraphException e1){
						e1.printStackTrace();
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
		
		//last method as always...otherwise the GUI is missing half its stuff
		frame.setVisible(true);
	}

	//use the try and catch from the originating method
	private String readDataFromFile(String filePath) throws FileNotFoundException{
		String graphData = "";
		File path = new File("" + filePath);// <---------------replace with 'src/project4/' for Eclipse
		Scanner fileRead = new Scanner(path);
		while(fileRead.hasNext()){
			graphData += "\n" + fileRead.nextLine(); // the line break identifier come first
		}
		fileRead.close();
		return graphData;
	}
	
	//the main focus of this private method is to clean up the data for the VertexGraph class
	private void createGraph(String testData){
		//we initially generate an arrray list with each vertex dependency string per line
		ArrayList<String> dependenciesList = new ArrayList<String>();
		//this is the ArrayList that the GUI passes to VertexGraph
		ArrayList<LinkedList<String>> vertexDependencies = new ArrayList<LinkedList<String>>(); 
		
		Scanner stringInput = new Scanner(testData).useDelimiter("\n"); //scan each String line
		while(stringInput.hasNext()){
			String dependencyString = stringInput.next(); //this would be different if our elements weren't Strings
			dependenciesList.add(dependencyString);
			
			//now we'll take the string of elements and parse the individual elements into values on a linked list
			LinkedList<String> dependentElements = new LinkedList<String>();
			Scanner elementInput = new Scanner(dependencyString).useDelimiter(" ");	//single spaces delimit elements
			while (elementInput.hasNext()){
				String str = elementInput.next(); //we are again assuming String data types
				dependentElements.add(str);
			}
			//add the linked list to the index of the vertexDependencies Array List
			vertexDependencies.add(dependentElements);
		}
		
		//now we have an array list with a linked list of all String elements per index
		try {
			if(graph.generateGraph(vertexDependencies)){
				JOptionPane.showMessageDialog(null,"Graph Built Successfully", 
						"Successful Build", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null,"There was an issue building this Graph", 
						"Empty Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (CyclicGraphException e) {
			e.printStackTrace();
		} 
	}
	
	public static void main (String[] args){
		P4GUI app = new P4GUI();
	}

}
