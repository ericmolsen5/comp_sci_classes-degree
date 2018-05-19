//package homework2;
/* 
 * File Name: Menu.java
 * Author: Eric Olsen
 */
public class Menu {

	MenuNode rootNode;
	
	public Menu(){
		//Create an empty root-node
		rootNode = new MenuNode(""); //we give it a blank value to avoid the null pointer exception
	}
	
	//this is our inner class which will define all of the linked list nodes
	protected class MenuNode{
		private String menuElement; //actual menu element
		private  MenuNode nextItem; //pointer to the next node
		private MenuNode prevItem; //pointer to the previous node
		
		//this should only be used for our rootNode.
		public MenuNode (String element){
			menuElement = element;
			nextItem = null;
			prevItem = null;
		}

		//this is our parameterized constructor and should be used for the rest of this program
		public MenuNode(String element, MenuNode nextNode, MenuNode prevNode){
			menuElement = element;
			nextItem = nextNode;
			prevItem = prevNode;
		}
		//we'll need getters and setters for most of our operations
		public String getElement(){
			return menuElement;
		}
		public MenuNode getNextNode(){
			return nextItem;
		}
		public MenuNode getPrevNode(){
			return prevItem;
		}
		public void setMenuElement (String element){
			menuElement = element;
		}
		public void setNextItem(MenuNode nextNode){
			nextItem = nextNode;
		}
		public void setPrevItem(MenuNode prevNode){
			prevItem = prevNode;
		}
	}//end of inner class linked data structure
	
	//add an item to the end of the list //we're supposed to add a boolean???
	public boolean addMenuItem(String newItem){
		boolean successful = false;
		//if the root node is empty
		if (rootNode.getElement().isEmpty() || (rootNode.getClass().equals(""))){
			//create the root node			//it only creates one node though.
			rootNode.setMenuElement(newItem);
			System.out.println("\nRoot Node created.\n");
			successful = true;
		}
		//if there is only a root node, we're creating the tail for the structure
		else if(rootNode.nextItem == null){
			//instantiate a new MenuNode
			MenuNode tailNode = new MenuNode(newItem, rootNode, rootNode);
			//update the rootNode's pointers
			rootNode.setNextItem(tailNode);
			rootNode.setPrevItem(tailNode);
			successful = true;
		}
		//there are values between the root node and its next node 
		else if (rootNode.nextItem.getElement() != null){ //root's neighbor has a value
			//since it's a doubly and circularly linked list we can traverse the 
			//entire data structure...or just go back two values
			MenuNode newTail = new MenuNode(newItem, rootNode, rootNode.prevItem);
			//update the 2nd to last node's next pointer
			rootNode.prevItem.setNextItem(newTail); 
			//finally set the rootNode's previous item to the new node
			rootNode.setPrevItem(newTail);
			successful = true;
		}
		return successful;
	} //end of addMenuItem()
	
	/*
	 * When ba is true, we insert before. When ba is false, we insert after.
	 * First we need to detect the existing item. Since we don't know where it is, we
	 * must traverse the data structure. This could at worst take up to Big-O(n). Actual data
	 * insertion is quick and will always be Big-O(1).
	 */
	
	public boolean insertMenuItemBA(String newItem, String existingItem, boolean ba) {
		boolean successful = false; //our return variable
		//first check for weird calls to this method
		boolean rootHasValue = true; //this is an error control boolean
		if (rootNode.getElement().matches("")){
			System.out.println("you must first declare a root by using the addMenuItem('...') method");
			rootHasValue = false;
		}
		// Making a huge if statement isn't very elegant, but it should control the error if the 
		// root node is empty 
		else if (rootHasValue){
			//check to see if our searched item is the root node
			if (existingItem.matches(rootNode.getElement())){
				//check to see if the root node's neighbors are empty
				if (rootNode.nextItem == null){
					//ba is irrelevant since the second element will be before and after the root
					addMenuItem(newItem);
					successful = true;
					return successful;
				}
				//now the root node is the searched item and the root has a next and prev neighbor
				else if (rootNode.nextItem != null && ba == true){
					//If ba is true we insert before the rootNode.
					//this is the same as creating an updated tail. AddMenuItem(String) does this.
					addMenuItem(newItem);
					successful = true;
					return successful;
				}
				else if (rootNode.nextItem != null && ba == false){
					//now we insert an element between the root and the root's neighbor
					//first instantiate the new node
					MenuNode nodeNextToRoot = new MenuNode(newItem, rootNode.nextItem, rootNode);
					//next update the old neighbor's prev pointer to the new node
					rootNode.nextItem.setPrevItem(nodeNextToRoot);
					//next update the root's next pointer...it's important to do this last
					rootNode.setNextItem(nodeNextToRoot);
					successful = true;
					return successful;
				}
			}
			
			//our searched item is not the root node
			if (!existingItem.matches(rootNode.getElement())){
				//now we must traverse the structure
				MenuNode tempNode = rootNode; //temporary node for our traversal
				//while our temporary node does not match our searched item, move to the next node
				while ( (!tempNode.menuElement.matches(existingItem)) ){
					tempNode = tempNode.getNextNode();
					//in the event that we traverse the entire structure with no match
					if (tempNode == rootNode){
						System.out.println("specified value not found!!");
						return successful;
					} 
					
					//otherwise we locate the String within the structure
					else if (tempNode.getElement().matches(existingItem)){
						//Reminder: if ba is true we insert the new node before the located node
						if(ba){
							//first instantiate the new node
							MenuNode insertBeforeNode = new MenuNode(newItem, tempNode, 
									tempNode.prevItem);
							//next update the tempNode's prevNeighbor's next value
							tempNode.prevItem.setNextItem(insertBeforeNode);
							//then update the tempNode's prev value to the new node (do this last)
							tempNode.setPrevItem(insertBeforeNode);
							successful = true;
							return successful;
						}//end of inserting before
						//if ba is false the new value goes after the searched node
						else if (!ba){
							//instantiate it first
							MenuNode insertAfterNode = new MenuNode(newItem, tempNode.nextItem, 
									tempNode);
							//next update the tempNode's nextNeighbor's value to this new item
							tempNode.nextItem.setPrevItem(insertAfterNode);
							//then update the tempNode's next value to this new node (do this last)
							tempNode.setNextItem(insertAfterNode);
							successful = true;
							return successful;
						}//end of insterting after				
					}//end of inserting before or after located item		
				}//end of traversing data structure	
			}//end of operation when searched item is not the root node (very ugly check)
		}//end of our check if the root is not null
		return successful;
	}//end of insertMenuItemBA(...)

	
	//I'm assuming here that we reassign the pointers and then let the java garbage
	//collector do its thing.
	public boolean deleteMenuItem(String deletedItem){
		boolean successful = false;
		//deleting a root node that is all by itself.
		if ((rootNode.getElement().matches(deletedItem)) && (rootNode.nextItem == null)){
			rootNode.setMenuElement("");
			System.out.println("\nRoot node was assigned a blank value.\n");
		}
		//delete the root node and promote its neighbor.
		else if ((rootNode.getElement().matches(deletedItem)) && 
				(rootNode.nextItem.nextItem == rootNode)) {
			//assign the root node to its neighbor
			rootNode.setMenuElement(rootNode.getNextNode().getElement());
			//now set its neighbor values to null
			rootNode.setNextItem(null);
			rootNode.setPrevItem(null);
			System.out.println("\nRoot node was deleted, it's neighbor was promoted.\n");
			successful = true;
			
		}
		//should execute only if the deleted item is adjacent to the root node
		else if (rootNode.getElement().matches(deletedItem)){
			//first re-assign the root node's neighbors' pointers
			rootNode.nextItem.setPrevItem(rootNode.prevItem);
			rootNode.prevItem.setNextItem(rootNode.nextItem);
			//the pointers should now skip over the root node, 
			//we also need re-assign the rootNode. 
			rootNode = rootNode.nextItem; //It could be the next or prev, we'll choose next.
			successful = true;	
		}
		//now we're not deleting the root node
		else {
			MenuNode tempNode = rootNode; //set up a temporary node for traversal. Start at the root
			while (!tempNode.getElement().matches(deletedItem)){
				tempNode = tempNode.getNextNode(); //traverse to the next node
				//if we get back to the root node, we did not find a match
				if (tempNode == rootNode){
					System.out.println("specified value not found!!");
					return successful;
				}
				//otherwise we locate the node
				else if (tempNode.getElement().matches(deletedItem)){
					//reassign the deleted node's next pointer to its previous neighbor
					tempNode.getPrevNode().setNextItem(tempNode.getNextNode());
					//reassign the deleted node's prev pointer to its next neighbor
					tempNode.getNextNode().setPrevItem(tempNode.getPrevNode());
					//the garbage collector should handle the rest.
					successful = true;
				}//end of deleted node
			}//end of data structure traversal
		}//end of non root node deletion
		return successful;
	}//end of deletMenuItem(...)
		
	public String toString(){
		String str = ""; //start with blank string
		str += rootNode.menuElement; //concantenate the root node
		MenuNode tempNode = rootNode; //temporary node for our while loop
		if (tempNode.nextItem != null){ //check the next value to avoid the null pointer exception
			tempNode = tempNode.nextItem; 
			while (tempNode != rootNode){ //when we detect the root node, we've arrived at the end
				str += ", " + tempNode.menuElement; //add a comma and concantenate
				tempNode = tempNode.nextItem; //jump to the next item
			}
		} 
		return str;
	}//end of toString() method
	
}//end of class
