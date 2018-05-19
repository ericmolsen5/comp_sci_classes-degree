//package project4;
/*
 *  File Name: CyclicGraphException.java
 *  Author: Eric Olsen
 */
import javax.swing.JOptionPane;

public class CyclicGraphException extends Exception{
	public CyclicGraphException(){
		String errorMessage = "There seems to be a cycle in these classes. Please revise"
				+ " your imported text file.";
		JOptionPane.showMessageDialog(null, errorMessage, "Cycle Detected", 
				JOptionPane.ERROR_MESSAGE);
		}
}
