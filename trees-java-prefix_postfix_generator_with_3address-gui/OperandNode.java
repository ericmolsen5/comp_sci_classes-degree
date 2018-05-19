//package project2;
/* 
 * File Name: OperandNode.java
 * Author: Eric Olsen
 */

//this is also from the reading. I've added a get method, hasChild boolean check
//and a flipChildren method that I needed when popping tree nodes out of a stack
public class OperandNode implements Node {
	private int value;

	public OperandNode(int value) {
		this.value = value;
	}

	//my edit
	public double getValue() {
		return value;
	}

	public String preOrderWalk() {
		return String.valueOf(value);
	}

	public String inOrderWalk() {
		return String.valueOf(value);
	}

	public String postOrderWalk() {
		return String.valueOf(value);
	}
	
	public String toString(){
		return String.valueOf(value);
	}
	//my method for recursive call
	public boolean hasChild(){
		return false;
	}

	@Override
	public void flipChildren() {
		// not used, but implemented by Node and used in OperatorNode
		
	}
}
