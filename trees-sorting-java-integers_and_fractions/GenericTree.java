//package project3;

import java.util.InputMismatchException;

import javax.swing.JOptionPane;

/*
 *  File Name: GenericTree.java
 *  Author: Eric Olsen
 */

//General note, this started as an IntegerTree class that just dealt
//with primitive int data. This class was a bit of a hasty re-write.
//There may be a few lingering weird things from its previous version.

public class GenericTree<T extends Comparable<T>> {
	
	private Node root;//single field
	
	//inner class for our tree nodes
	public class Node{
		//each node has three fields
		private T element;
		private Node left;
		private Node right;
		
		//inner class constructor
		Node (T element){
			this.element = element;
		}
		
		public String toString(){
			return element.toString();
		}
	} //end of inner Node class
	
	//called by the insert() method to see if a root needs to be instantiated
	public boolean treeEmpty(){
		if (root == null)
			return true;
		else
			return false;
	}
	
	//called from the generate tree method below. Type parameters should be fed
	//into this method.
	public void insert(T element){
		if (treeEmpty()){
			root = new Node(element);
		} else{
			add (root, element);
		}
	}
	
	//bulk of the sorting happens here.
	private void add(Node node, T element){
		// new node is less than or equal to compared node
		if (element.compareTo(node.element) <= 0){
			//check to see if left child is empty
			if (node.left == null){
				node.left = new Node(element);
			} else{
				//recursive call
				add(node.left, element);
			}	
		}
		// new node is more than the compared node
		else if (element.compareTo(node.element) > 0){
			//check to see if right child is empty
			if (node.right == null){
				node.right = new Node(element);
			}else {
				//recursive call
				add(node.right, element);
			}
		}
	}
	
	//the GUI will need to grab the tree root as a staring point
	public Node getRoot(){
		if (root == null){
			emptyTreeMessage();
			return null;
		} else{
			return root;
		}
	}
	
	//if no values are passed. This should probably be at the GUI
	private void emptyTreeMessage(){
		String message = "There are no values to add.";
		JOptionPane.showMessageDialog(null, message, "Invalid Input", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	//IMPORTANT NOTE: I was trying to parse the unsorted String here and it created
	//nodes that behaved like Strings. So...processing an array of type objects is a
	//fairly safe way to ensure that the calling method handles the typecase appropriately.
	public void generateTree(T[] unsorted) throws InputMismatchException{
		for (int i = 0; i < unsorted.length; i++){
			insert(unsorted[i]);
		}
	}
	
	//this is unchanged from Project 2
	public String inOrder(Node root){
		if (root != null){ //exit criteria 
			String left = inOrder(root.left);
			String right = inOrder(root.right);
			//left-->root-->right = ascending
			return left.toString() + root.toString() +" " + right.toString();
		}
		else return "";
	}
	
	public String reverseInOrder(Node root){
		if (root != null){ //exit criteria
			String left = reverseInOrder(root.left);
			String right = reverseInOrder(root.right);
			//right-->root-->left = descending
			return right.toString() + root.toString() +" " + left.toString();
		}
		else return "";
	}
	
} //end of GenericTree Class

	

