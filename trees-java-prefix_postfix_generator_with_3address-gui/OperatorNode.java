//package project2;
/* 
 * File Name: P2GUI.java
 * Author: Eric Olsen
 */

public class OperatorNode implements Node {

	private Node left, right;
	private Operator operator;

	public OperatorNode(Operator operator, Node left, Node right) {
		this.operator = operator;
		this.left = left;
		this.right = right;
	}
	
	public Node getLeft(){
		return left;
	}
	public Node getRight(){
		return right;
	}
	public Operator getOperator(){
		return operator;
	}

	public String preOrderWalk() {
		String leftValue = left.preOrderWalk();
		String rightValue = right.preOrderWalk();
		return "" + operator + " " + leftValue + " " + rightValue;
	}

	public String inOrderWalk() {
		String leftValue = left.inOrderWalk();
		String rightValue = right.inOrderWalk();
		return "(" + leftValue + " " + operator + " " + rightValue + ")";
	}

	public String postOrderWalk() {
		String leftValue = left.postOrderWalk();
		String rightValue = right.postOrderWalk();
		return leftValue + " " + rightValue + " " + operator;
	}

	public String toString() {
		return operator.toString();
	}
	
	public String toAddressString() {
		return operator.toAddressString();
	}
	
	public String entireNodeToString(){
		String str = this.left.toString() + " " + operator.toString() + " "
				+ this.right.toString();
		return str;
	}
	
	//boolean check for tree generation
	public boolean hasChild(){
		return true;
	}
	//this was necessary becuase of how I popped tree nodes from a stack
	public void flipChildren(){
		Node temp1 = left;
		Node temp2 = right;
		left = temp2;
		right = temp1;
	}
}

