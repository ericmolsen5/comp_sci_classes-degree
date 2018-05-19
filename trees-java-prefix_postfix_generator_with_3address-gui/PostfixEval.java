//package project2;
/* 
 * File Name: PostfixEval.java
 * Author: Eric Olsen
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;



public class PostfixEval {

	private static Stack<OperandNode> operandStack = new Stack<OperandNode>();
	private static Stack<OperatorNode> operatorNodeStack = new Stack<OperatorNode>();
	private static List<OperatorNode> instructionsList = new ArrayList<OperatorNode>();
	
	
	public String generatePrefix(String postfix) throws InvalidTokenException, 
	 IncorrectSyntaxException {
		instructionsList.clear(); //for multiple inputs fromt he GUI
		operandStack.clear();
		operatorNodeStack.clear();
		
		//tokenize input
		System.out.println("postfix expression received: " + postfix);
		String token;
		char lastChar = ' ';
		
		while (!postfix.isEmpty()){
			boolean properCharacter = false; //this exists to catch invalid operators
			token = postfix.substring(0,1);
			char c = token.charAt(0);
			postfix = postfix.substring(1); //remove first character of the String for the next loop
			
			//create Operand node if we see a digit
			if(Character.isDigit(c)){
				properCharacter = true;
				int digitValue = Integer.parseInt(token);
				//now check if the last character was a digit
				if (Character.isDigit(lastChar)){
					//pop the operandStack, update the value, make a new operand, then push that operand
					OperandNode tempOperand = operandStack.pop();
					double tempInt = tempOperand.getValue(); //clean up the code and make this an int
					tempInt *= 10;
					digitValue += tempInt;
					OperandNode nextOperand = new OperandNode(digitValue);
					operandStack.push(nextOperand);
					lastChar = c;
				}
				else {
					OperandNode nextOperand = new OperandNode(digitValue);
					operandStack.push(nextOperand);
					System.out.println("pushed: " + nextOperand.toString());
					lastChar = c;
				}
				
			}
			//if a space is encountered
			if(c == ' '){
				lastChar = c;
				continue;
			}
			else if((c == '+') || (c == '-') || (c == '*') || (c == '/') ){
				properCharacter = true;
				//generate an inner while loop in order to prevent double executions of the if statements
				while (true){
					//if there are two operands in the operand stack, we're dealing with leaf nodes
					if(operandStack.size() > 1){
						OperandNode rightLeafNode = operandStack.pop();
						OperandNode leftLeafNode = operandStack.pop();
						OperatorNode parentNode = null;
						if (c == '+'){
							parentNode = new OperatorNode(new AddOperator(), leftLeafNode, rightLeafNode);
							operatorNodeStack.push(parentNode);
							instructionsList.add(parentNode);
						}
						else if (c == '-'){
							parentNode = new OperatorNode(new SubOperator(), leftLeafNode, rightLeafNode);
							operatorNodeStack.push(parentNode);
							instructionsList.add(parentNode);
						}
						else if (c == '*'){
							parentNode = new OperatorNode(new MulOperator(), leftLeafNode, rightLeafNode);
							operatorNodeStack.push(parentNode);
							instructionsList.add(parentNode);
						}
						else if (c == '/'){
							parentNode = new OperatorNode(new DivOperator(), leftLeafNode, rightLeafNode);
							operatorNodeStack.push(parentNode);
							instructionsList.add(parentNode);
						}
						
						System.out.println("Operator Node created: " + operatorNodeStack.peek().entireNodeToString());
						int size = instructionsList.size()-1;
						System.out.println("3 address instruction list added: " + instructionsList.get(size).getOperator().toString()
								+ " R" + size + ", " + instructionsList.get(size).getLeft().toString() + ", " + 
								instructionsList.get(size).getRight().toString());
						lastChar = c;
						break;
					}
					//if there is only one operand, the operand is the left child and previous operator node is
					//the right childe
					else if(operandStack.size() == 1){
						OperandNode leftChild = operandStack.pop();
						if (operatorNodeStack.isEmpty()){
							throw new IncorrectSyntaxException();
						}
						OperatorNode rightChild = operatorNodeStack.peek();
						OperatorNode parentNode = null;
						if (c == '+'){
							parentNode = new OperatorNode(new AddOperator(), leftChild, rightChild);
							operatorNodeStack.push(parentNode);
							instructionsList.add(parentNode);
						}
						if (c == '-'){
							parentNode = new OperatorNode(new SubOperator(), leftChild, rightChild);
							operatorNodeStack.push(parentNode);
							instructionsList.add(parentNode);
						}
						if (c == '*'){
							parentNode = new OperatorNode(new MulOperator(), leftChild, rightChild);
							operatorNodeStack.push(parentNode);
							instructionsList.add(parentNode);
						}
						if (c == '/'){
							parentNode = new OperatorNode(new DivOperator(), leftChild, rightChild);
							operatorNodeStack.push(parentNode);
							instructionsList.add(parentNode);
						}
						System.out.println("Operator Node created: " + operatorNodeStack.peek().entireNodeToString());
						System.out.println("Operator Node created: " + operatorNodeStack.peek().entireNodeToString());
						int size = instructionsList.size()-1;
						System.out.println("3 address instruction list added: " + instructionsList.get(size).getOperator().toString()
								+ " R" + size + ", " + instructionsList.get(size).getLeft().toString() + ", " + 
								instructionsList.get(size).getRight().toString());
						lastChar = c;
						break;
					}
					//if there are no operands in the operand stack, both children of this operator node are
					//operator nodes
					else if(operandStack.empty()){
						//this is a little messy, but I have to pop the first value in order to peek the sed
						OperatorNode rightChild = operatorNodeStack.pop(); //pop the value so we can peek the next
						if (operatorNodeStack.isEmpty()){
							throw new IncorrectSyntaxException();
						}
						OperatorNode leftChild = operatorNodeStack.peek(); //now we have the left child
						operatorNodeStack.push(rightChild); //push the value back to the stack
						OperatorNode parentNode = null;
						if (c == '+'){
							parentNode = new OperatorNode(new AddOperator(), leftChild, rightChild);
							operatorNodeStack.push(parentNode);
							instructionsList.add(parentNode);
						}
						if (c == '-'){
							parentNode = new OperatorNode(new SubOperator(), leftChild, rightChild);
							operatorNodeStack.push(parentNode);
							instructionsList.add(parentNode);
						}
						if (c == '*'){
							parentNode = new OperatorNode(new MulOperator(), leftChild, rightChild);
							operatorNodeStack.push(parentNode);
							instructionsList.add(parentNode);
						}
						if (c == '/'){
							parentNode = new OperatorNode(new DivOperator(), leftChild, rightChild);
							operatorNodeStack.push(parentNode);
							instructionsList.add(parentNode);
						}
						System.out.println("Node created: " + operatorNodeStack.peek().entireNodeToString());
						System.out.println("Operator Node created: " + operatorNodeStack.peek().entireNodeToString());
						int size = instructionsList.size()-1;
						System.out.println("3 address instruction list added: " + instructionsList.get(size).getOperator().toString()
								+ " R" + size + ", " + instructionsList.get(size).getLeft().toString() + ", " + 
								instructionsList.get(size).getRight().toString());
						lastChar = c;
						break;
					}
				}
			}//end of syntactically correct tokens
			if (!properCharacter){ //not a valid operator
				throw new InvalidTokenException();
			}
		}//end of tokenized input and node creation
		
		int treeDepth = operatorNodeStack.size();	
		System.out.println("\n\nTree depth is: " + treeDepth);
		
		Node tree = generateTree();
		
		//if there are still operator nodes left in the stack
		if (!operatorNodeStack.isEmpty()){
			JOptionPane.showMessageDialog(null, "Some operator nodes were not "
					+ "included in this tree. You may want to re-evaluate your postfix"
					+ "notation", "Tree Nodes Missing", JOptionPane.INFORMATION_MESSAGE);
		}
		
		//I'm assuming this flipping of children is necessary because of how the nodes are
		//stored in the stack. Since the right-most value is popped first, some kind of reversal
		//is needed for trees with a depth greater than 1.
		if (treeDepth > 1){
			tree.flipChildren(); //I have no idea why this was needed
		}
		
		String prefix = tree.inOrderWalk();
		return prefix;
	}

	private Node generateTree(){
		OperatorNode rootNode = operatorNodeStack.pop();
		Node tree = new OperatorNode (rootNode.getOperator(), getTreeNode(rootNode.getLeft()), 
				getTreeNode(rootNode.getRight()));
		return tree;
	}

	//I can't believe it's only 5 lines
	private Node getTreeNode(Node node){
		if (!node.hasChild())
			return node;
		else{
			return generateTree();
		}
	}
	
	//we still need to turn these if statements into decrementing for loops
		//this will only work on a tree with a height of 3!
		public String generateInstructions(){
			String str = ""; 								//instantiate our returned string
			int length = instructionsList.size(); 			//call for the list size once
			for (int i = 0; i < length; i++ ){ 				//for each node of the list
				OperatorNode tempNode = instructionsList.get(i);
				//the left side String value is the value directly after the register number
				String leftSide = null;
				if (!tempNode.getLeft().hasChild()){ 			//first check to see if the node has children
					leftSide = tempNode.getLeft().toString(); 	//if so put it's numberic value to string
				} else{ 									//otherwise it's an operator 
					for (int k = i; k >=0; --k){			//search backwards until the operator matches a previous value
						if (tempNode.getLeft().equals(instructionsList.get(k))){
							leftSide = "R" + k;				//when it matches, create it referenced list item
						}
					}
				}
				
				//now conduct the same procedure for the right
				String rightSide = null;
				if (!tempNode.getRight().hasChild()){ //leaf node
					rightSide = tempNode.getRight().toString();
				}else{
					for (int k = i; k >=0; --k){
						if (tempNode.getRight().equals(instructionsList.get(k))){
							rightSide = "R" + k;
						}
					}
				}
				
				//now we have all the elements to print the String value of our 3 address instructions.
				str +=	instructionsList.get(i).getOperator().toAddressString() + " R" + i + " "
						+ leftSide + " "
						+ rightSide + "\n";
			}//end of for loop
			return str;
		}
	
}
