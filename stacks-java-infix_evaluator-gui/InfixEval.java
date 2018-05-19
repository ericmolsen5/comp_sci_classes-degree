//package project1;
/* 
 * File Name: InfixEval
 * Author: Eric Olsen
 */

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;



public class InfixEval {
	
	private static Stack<Character> operatorStack = new Stack<Character>();
	private static Stack<Integer> operandStack = new Stack<Integer>();
	private boolean errorOccured = false; //need this for some of the exeption handling
	
	protected int evaluate(String infix) throws IllegalOperatorException, DivideByZeroException{
		int result = 0; //declare this for popping
		String token;
		//this helps us detect double digit integers and arithmetic exceptions
		char lastChar = ' ';  //dummy value which will change
		//while there is an input to receive and no exception has been thrown
		while(!infix.isEmpty() && !errorOccured){
			token = infix.substring(0, 1); //token extracts the first character
			char c = token.charAt(0); //declare a char for our if statements
			infix = infix.substring(1); //shorten the infix by one character
			//check to see if the character is an Ingeger
			if (Character.isDigit(c)){
				//check last character and see if thie operand is multiple digits
				if (Character.isDigit(lastChar)){
					int tempInt = operandStack.pop();
					tempInt *= 10;
					tempInt += Integer.parseInt(token);
					operandStack.push(tempInt);
				}
				//otherwise it's just a single digit
				else{
					//print operation
					System.out.println("current stacks  OperatorStack: " + operatorStack + 
							" OperandStack: " + operandStack);
					System.out.println("pushing " + c + " to operand stack.");
					//push result and update last character
					operandStack.push(Integer.parseInt(token));
					lastChar = c;
				}
			}//end of operand check
			//left parenthesis check
			else if ( c == '(' ){
				//print stacks before operation
				System.out.println("current stacks  OperatorStack: " + operatorStack + 
						" OperandStack: " + operandStack);
				System.out.println("adding " + c + " to operator stack.");
				//push parenthesis and update last character
				operatorStack.push(c);
				lastChar = c;
			}
			//if it's a right parenthesis
			else if ( c == ')' ){
				//check to make sure there are not two left parenthesis in the operator stack
				while ( (operatorStack.peek() != '(' ) ){
					//print stacks before our operation
					System.out.println("current stacks  OperatorStack: " + operatorStack + 
							" OperandStack: " + operandStack);
					System.out.print("scanned token: " + token + 
							" Calculating:");
					//conduct calculations
					popAndCalculate();
				}
				//pop remaining operator because it's '('
				//show stacks before operation
				System.out.println("current stacks  OperatorStack: " + operatorStack + 
						" OperandStack: " + operandStack);
				System.out.println("popping " + c + " from operator stack.");
				//remove the left parenthesis
				operatorStack.pop();
			}
			
			//now check for the 4 utilized math operators
			else if ( c == '+' || c == '-' || c == '*' || c == '/') {
				//this should check for operator double entries
				if (lastChar == '+' || lastChar == '-' || lastChar == '*'
						|| lastChar =='/'){
					//flip the error boolean so the next loop stops its operations
					errorOccured  = true;
					throw new IllegalOperatorException();
				}
				//this is the tricky while loop
				while (!operatorStack.empty() && (
						//always conduct multiplication or division
						( (operatorStack.peek() == '*') ||(operatorStack.peek() == '/') ) 
						//or conduct addition and subtraction if there already is one in the stack
						|| ( ((c=='+') || c=='-')) && (operatorStack.peek()=='+' ||
						operatorStack.peek()=='-') ) ) {
					//do the calculation
					popAndCalculate();
				}
				
				//this pushes the lower precedence operator to top of stack if not already there
				//print the stacks
				System.out.println("current stacks  OperatorStack: " + operatorStack + 
						" OperandStack: " + operandStack);
				System.out.println("pushing " + c + " to operator stack.");
				//push the operator and update the last character
				operatorStack.push(c);
				lastChar = c;
				
			//this should catch any other characters that aren't our for operators
			//we are allowing, but not requiring, spaces so they are allowed on this statement
			}else if (c != ' '){
				errorOccured = true;
				throw new IllegalOperatorException();
			}//end of check for inproper characters
		}//end of input reading section
		
		// all input has concluded and we just need to finish the remaining calculations
		while ( !operatorStack.empty() && !errorOccured ){
			//print the stacks before the calcultion
			System.out.print("calculating: "
					+" OperatorStack: " + operatorStack + 
					" OperandStack: " + operandStack + "\n");
			popAndCalculate();
		}
		
		//the last item in the operand stack is our answer
		result = operandStack.pop();
		System.out.println("Popping the final answer which is " + result);
		return result;
	}//end of evaluate method
	
	//here is our internal method for all stack calculations
	private void popAndCalculate() throws DivideByZeroException{
		try{
			char operator = operatorStack.pop();
			//Very important! The right operand is the first item to be popped
			int rightOperand = operandStack.pop();
			//left operand is the second item to be popped
			int leftOperand = operandStack.pop();
			//show the arithmetic operation
			System.out.println(leftOperand + " " + operator + " " + rightOperand);
			int solution = 0;
			if (operator == '+')
				solution = leftOperand + rightOperand;
			if (operator == '-')
				solution = leftOperand - rightOperand;
			if (operator == '*')
				solution = leftOperand * rightOperand;
			if (operator == '/'){
					if (leftOperand % rightOperand > 0){
						JOptionPane.showMessageDialog(null, "This division operation"
								+ " dropped off a remainder. Your calculation may be a"
								+ " bit off.");
					}
					if (rightOperand == 0){
						errorOccured  = true; //should terminate either loop
						//throw new DivideByZeroException();
					}
					//complete the division operation
					solution = leftOperand / rightOperand;
				}
			//show the stacks before pushing the solution
			System.out.println("current stacks  OperatorStack: " + operatorStack + 
					" OperandStack: " + operandStack);
			System.out.println("Pushing " + solution + " to Operand stack.");
			//push the solution back to the operand stack
			operandStack.push(solution);
		//I think it makes sense to catch the empty stack exception here, in the only class
		//that utilizes stacks. So I will not throw this back to the GUI
		} catch (EmptyStackException e) {
			String errorMessage = "There are too many operators. Please edit your expression"
					+ "and ensure there are extra operators.";
			JOptionPane.showMessageDialog(null, errorMessage, "Illegal Operator Use", 
					JOptionPane.ERROR_MESSAGE);
			errorOccured = true; //should terminate the loop
		} 
		//this is really weird, but if you first catch the ArithmeticException, you can throw the
		//customized DivideByZeroException
		catch (ArithmeticException e){
			throw new DivideByZeroException();
		}
	}
	
}
