//package project2;
/* 
 * File Name: Node.java
 * Author: Eric Olsen
 */

//also from the reading. I've added hasChild() and flipChildren()
public interface Node {
	
	public String preOrderWalk();

	public String inOrderWalk();

	public String postOrderWalk();
	
	public boolean hasChild();
	
	public void flipChildren();
	
}
