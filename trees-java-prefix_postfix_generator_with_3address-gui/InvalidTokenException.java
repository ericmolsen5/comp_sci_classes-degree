//package project2;
/* 
 * File Name: InavlidTokenException.java
 * Author: Eric Olsen
 */
import javax.swing.JOptionPane;

public class InvalidTokenException extends Exception{
	public InvalidTokenException(){
		String errorMessage = "There was an error with our operators. "
				+ "You may only use '+', '-', '*', and '/'.";
		JOptionPane.showMessageDialog(null, errorMessage, "Invalid Operator Use", 
				JOptionPane.ERROR_MESSAGE);
		}

}
