//package project1;
/* 
 * File Name: IllegalOperatorException
 * Author: Eric Olsen
 */
import javax.swing.JOptionPane;

public class IllegalOperatorException extends Exception{
	public IllegalOperatorException(){
		String errorMessage = "There was an error with our operators. "
				+ "You may not input double operations like '2 + + 2' or any operator"
				+ "besides '+', '-', '*', and '/'.";
		JOptionPane.showMessageDialog(null, errorMessage, "Illegal Operator Use", 
				JOptionPane.ERROR_MESSAGE);
		}
}
