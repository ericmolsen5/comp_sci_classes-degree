//package project2;
/* 
 * File Name: Operator.java
 * Author: Eric Olsen
 */

//this Class is taken from the week's reading. I've added a second String method
//to each class so I can display both infix characters and address instructions
public abstract class Operator {

	abstract String toAddressString();
}

class AddOperator extends Operator {

	public String toString() {
		return "+";
	}
	public String toAddressString() {
		return "Add";
	}
}

class MulOperator extends Operator {

	public String toString() {
		return "*";
	}
	public String toAddressString() {
		return "Mul";
	}
}

class SubOperator extends Operator {

	public String toString() {
		return "-";
	}
	public String toAddressString() {
		return "Sub";
	}
}

class DivOperator extends Operator {

	public String toString() {
		return "/";
	}
	public String toAddressString() {
		return "Div";
	}
}
