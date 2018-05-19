//package homework2;

/* 
 * File Name: TestMenu.java
 * Author: Eric Olsen 
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class TestMenu {

	public static void main(String[] args) {

		Menu app = new Menu();
		File relativePath = new File("Menu.txt");
		String userInput;
		String menuItem;
		String addedItem = null; // for our addMenuItem and insertMenuItem methods
	
		String searched = null; // for
		boolean beforeOrAfter = false;
		boolean methodSuccess = false;
		boolean running = false;

		// this is our initial reading of the text file
		try {
			Scanner fileRead = new Scanner(relativePath).useDelimiter(";");
			while (fileRead.hasNext()) {
				menuItem = fileRead.next();// lesson learned, don't use the nextLine()!!!!					
				app.addMenuItem(menuItem);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// bBlow are some strings for display. This should preserve some white
		// space.
		Scanner scanIn = new Scanner(System.in);

		String insertOrDeleteQuestion = "Would you like to insert or delete a menu item? Type 'I'"
				+ "for insert or 'D' for delete.\nRemember you can type 'exit' to close.";

		String acceptInputMessage = "Please type in the value you wish to insert. The above"
				+ "shows all the \nvalues currently in the structure.";

		String acceptExistingItemMessage = "Now type in an object that exists in this"
				+ "Linked List.\nAs a reference please look above at the existing items.";

		String acceptBooleanMessage = "Now type either 'T' for true, which means the insertion"
				+ " will occur before this item, \nor 'F' for false in which case it will insert " + "after.";

		String booleanInputError = "\nThere seems to be an issue with the key that you typed.";

		String generalError = "There was some kind of issue with your key input. Please try again"
				+ " or type 'exit' \nto exit the program.";

		String deleteNode = "Please type the item that you wish to delete.";

		//this section was built to specifically answer questions 2b and 2c
		running = true;
		while (running) {
			while (true) {
				System.out.println("Current menu items are: \n\n" + app.toString() + "\n");
				System.out.println(acceptInputMessage);
				addedItem = scanIn.next();
				// if the root node is deleted, use the addMenuItem
				if (app.rootNode.getElement().matches("")) {
					app.addMenuItem(addedItem);
					break;
				}
				System.out.println(acceptExistingItemMessage);
				searched = scanIn.next();
				System.out.println(acceptBooleanMessage);
				userInput = scanIn.next();
				char c1 = Character.valueOf(userInput.charAt(0));
				// some error control before calling the method
				if ((c1 == 't') || (c1 == 'T')) {
					beforeOrAfter = true;
				} else if ((c1 == 'f') || (c1 == 'F')) {
					beforeOrAfter = false;
				} else {
					System.out.println(booleanInputError);
					break;
				}
				methodSuccess = app.insertMenuItemBA(addedItem, searched, beforeOrAfter);
				if (!methodSuccess) {
					System.out.println("\nThere seems to be an error in the last operation."
							+ "Please try again or exit the program\n.");
				}
				System.out.println("Current menu items are: \n\n" + app.toString() + "\n");

				System.out.println(deleteNode);
				searched = scanIn.next();
				methodSuccess = app.deleteMenuItem(searched);
				if (!methodSuccess) {
					System.out.println(booleanInputError);
				}
				break;

			} // end of inner while loop
			System.out.println("Current menu items are: \n\n" + app.toString() + "\n");
			break;
		} // end of user input outer while loop
		
		//this last section will allow for continued user input
		System.out.println("******continuous input*************");
		running = true;		
		while(running){
			while(true){
				System.out.println("Current menu items are: \n\n" + app.toString()+"\n");
				System.out.println(insertOrDeleteQuestion);
				userInput = scanIn.next();
				char c = Character.valueOf(userInput.charAt(0));
				if ((c == 'I') || (c == 'i') || (c == 'D') || (c == 'd') ){
					//adding items
					if ((c == 'I') || (c == 'i')){
						System.out.println(acceptInputMessage);
						addedItem = scanIn.next();
						//if the root node is deleted, use the addMenuItem
						if (app.rootNode.getElement().matches("")){
							app.addMenuItem(addedItem);
							break;
						}
						System.out.println(acceptExistingItemMessage);
						searched = scanIn.next();
						System.out.println(acceptBooleanMessage);
						userInput = scanIn.next();
						char c1 = Character.valueOf(userInput.charAt(0));
						if ((c1 == 't') || (c1 == 'T')){
							beforeOrAfter = true;
							
						} else if ((c1 == 'f') || (c1 == 'F')){
							beforeOrAfter = false;
						} else {
							System.out.println(booleanInputError);
							break;
						}
						methodSuccess = app.insertMenuItemBA(addedItem, searched, beforeOrAfter);
						if(!methodSuccess){
							System.out.println("\nThere seems to be an error in the last operation."
									+ "Please try again or exit the program\n.");
						}
						break;
					}
					//deleting items
					else if ((c == 'D') || (c == 'd')){
						if (app.rootNode.getElement().equals("")){
							System.out.println("There is no item to delete. Please insert an item first.");
							break; //otherwise we'll hit the null pointer exception
						}
							System.out.println(deleteNode);
							searched = scanIn.next();
							methodSuccess = app.deleteMenuItem(searched);
							if(!methodSuccess){
								System.out.println(booleanInputError);
							}
							break;
						}
				}
				else if (userInput.contains("exit")){
					System.out.println("Closing.");
					running = false;
					break;
				}
				System.out.println(generalError);
			}//end of inner while loop
		}//end of user input outer while loop
	}// end of main method
}// end of tester class
