//package project3;

import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JOptionPane;

/*
 *  File Name: IntegerTree.java
 *  Author: Eric Olsen
 */

public class FractionTree {
	
	private Node root;
	
	//inner class for our data structure definitions
	public static class Node implements Comparable{
		//don't use doubles for fractinos!!!
		private String fractionRep;
		private int numerator;
		private int denominator;
		private Node left;
		private Node right;
		
		//inner class constructor. Most of the error control is in the constructor
		Node (String fractionAsString) throws InputMismatchException,
			NumberFormatException{
			fractionRep = fractionAsString;	//string for displaying on the GUI
			Scanner scanIn = new Scanner(fractionAsString).useDelimiter("/");
			//InputMismatchException and NumberFormatException should handle the errors here
			numerator = scanIn.nextInt();
			denominator = scanIn.nextInt();
						
			//here we'll check for double divisor entries
			if (!checkForValidFraction(fractionAsString)){
				throw new NumberFormatException();
			}
		}
		
		
		//this enables our check for double divisors
		private boolean checkForValidFraction(String str){
			int divCounter = 0;
			for (int i = 0; i < str.length(); i++){
				if (str.charAt(i) == '/'){
					divCounter++;
				}
			}
			if (divCounter == 1)
				return true;
			else
				return false;
		}

		//unlike the integer tree, we'll have to instantiate this node before it's added
		public boolean addNode(Node insertedFraction){
			//start by sending fractions to the left
			if ( (insertedFraction.compareTo(this) == -1 ||
					insertedFraction.compareTo(this) == 0)){
				//check to see if there's a null value
				if (left == null){
					left = insertedFraction;
					return true;
				} else {
					//recursive call
					return left.addNode(insertedFraction);
				}
			}
				//now we check to the right
				else if(insertedFraction.compareTo(this) == 1)  {
					if (right == null){
						right = insertedFraction;
						return true;
					} else {
						//recursive call
						return right.addNode(insertedFraction);
					}
				}
			return false;
			}
		
		/* This part got tricky. The key was to give both fractions a common denominator.
		 * So multiply each numerator by the other's denominator. Then compare the
		 * numerators to each other. This works MUCH better than using double values. 
		 */
		public int compareTo(Object nextFraction) {
			Node nextNode = (Node) nextFraction; //first cast to Node
			//now get all four integers ready
			int nextNumerator = nextNode.numerator;
			int nextDenominator = nextNode.denominator;
			int thisNumerator = numerator;
			int thisDenominator = denominator;
			//put them under the same denominator value
			thisNumerator *= nextDenominator;
			nextNumerator *= thisDenominator;
			//this part works, but seems a little backwards to me
			if (nextNumerator < thisNumerator)
				return 1; // --> goes to the right
			else if (nextNumerator == thisNumerator)
				return 0; // --> goes to the left
			else 
				return -1; // --> goes to the left
		}
		
		//needed for GUI display
		public String getFractionString(){
			return fractionRep;
		}

	}//end of inner class
	
	
	//getter for GUI calls to the root
	public Node getRoot(){
		if (root == null){
			emptyTreeMessage();
			System.out.println("This tree is empty.");
			return null;
		} else {
			return root;
		}
	}
	
	//if no values are passed.
	private void emptyTreeMessage(){
		String message = "There are no values to add.";
		JOptionPane.showMessageDialog(null, message, "Invalid Input", 
				JOptionPane.INFORMATION_MESSAGE);
	}	
	
	//this method takes the space delimited fraction in String form and 
	//inserts it into the tree. The compareTo is used in the inner class.
	public boolean add(String nextFraction){
		if (root == null){
			root = new Node(nextFraction); //new object created here
			return true;
		} else{
			return root.addNode(new Node(nextFraction)); //new object created here
		}
	}
	
	public void generateTree(String unsorted){
		String tempString = "";
		Scanner stringInput = new Scanner(unsorted).useDelimiter(" ");
		//data parsing operation. There could still be parsing errors within each fraction.
		//the Node's constructor should catch these errors.
		while (stringInput.hasNext()){
			tempString = stringInput.next();
			add(tempString);		//add the parsed String
		}
	}
	
	
	public String inOrder(Node root){
		if (root != null){ //exit criteria
			String left = inOrder(root.left); //recursive call
			String right = inOrder(root.right); //recursive call
			return left + root.getFractionString() + " " + right; //lett->root->right = ascending
		}
		else return "";
	}
	
	public String reverseInOrder(Node root){
		if (root != null){ //exit criteria
			String right = reverseInOrder(root.right); //recursive call
			String left = reverseInOrder(root.left); //recursive call
			return right + root.getFractionString() + " " + left; //right->root->left = descencing
		}
		else return "";
	}
}//end of FractionTreeClass
