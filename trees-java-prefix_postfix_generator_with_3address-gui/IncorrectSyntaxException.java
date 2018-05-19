//package project2;
/* 
 * File Name: IncorrectSyntaxException.java
 * Author: Eric Olsen
 */
import javax.swing.JOptionPane;

public class IncorrectSyntaxException extends Exception{
	public IncorrectSyntaxException(){
		String errorMessage = "An error occured processing this expression."
				+ " Please ensure that it is in proper postfix notation";
		JOptionPane.showMessageDialog(null, errorMessage, "Incorrect Syntax", 
				JOptionPane.ERROR_MESSAGE);
		}

}
