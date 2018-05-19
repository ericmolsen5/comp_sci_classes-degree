//package project1;
/* 
 * File Name: DivideByZeroException
 * Author: Eric Olsen
 */
import javax.swing.JOptionPane;

public class DivideByZeroException extends ArithmeticException{
	public DivideByZeroException(){
		String errorMessage = "You may not divide by zero. \n"
				+ "Please input a function in a proper format.";
		JOptionPane.showMessageDialog(null, errorMessage, "Division by zero", 
				JOptionPane.ERROR_MESSAGE);
		}
	}
