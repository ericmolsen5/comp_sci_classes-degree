//package project4;
/*
 *  File Name: VertexGraph.java
 *  Author: Eric Olsen
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;


public class VertexGraph <T extends Comparable<T>>{
	
/////////////////////////////////start transitioning from vertexList to vertexHashSet	
//	private HashSet <Vertex> vertexSet = new HashSet<Vertex>();
	
	private ArrayList<Vertex> vertexList = new ArrayList<Vertex>();
	private int sortCounter = 0;
	private boolean cycleDetected = false;
	
	//inner class for our data structure
	public class Vertex{
		
		//inner class fields
		private T element; 	
		private int vertexID;
		private int inDegree;		
		private int topSort;
		private boolean visited;
		private LinkedList<Integer> adjacencyList = new LinkedList<Integer>();
		private HashMap<Integer, T> adjacencies = new HashMap<Integer, T>();
		
		//somewhat complicated constructor
		public Vertex(T element, int vertexID){
			this.element = element;
			this.vertexID = vertexID;
			inDegree = 0;
			topSort = -1;
			visited = false;
		}
		
		public void setVisited(){
			visited = true;
		}
		
		public void setUnvisited(){
			visited = false;
		}
		
		public LinkedList<Integer> getAdjacencyList(){
			return adjacencyList;
		}
		
		public void setAdjacencyList(LinkedList<Integer> adjacencyList){
			this.adjacencyList = adjacencyList;
		}
		
		public void setAdjacencies(){
			adjacencies.put(vertexID, element);
		}
		
		public void setTopSort(int topSort){
			this.topSort = topSort;
		}
		
		public int getTopSort(){
			return topSort;
		}
		
		public int getVertexID(){
			return vertexID;
		}
		
		public T getElement(){
			return element;
		}
		
		public void inDegreePlusOne(){
			inDegree++;
		}

	}//end of inner class
	
	//method called from the GUI. The GUI handles dispalying success or failure messages
	public boolean generateGraph(ArrayList<LinkedList<T>> vertexDependencies)throws CyclicGraphException{
		vertexList.clear();//clear the existing Graph so multiple method calls don't corrupt the data
		
		//call for private add method below, each LinkedList will be processed
		for (int i = 0; i < vertexDependencies.size(); i++){
			add(vertexDependencies.get(i)); //add method is below
		} //end of adding all vertex dependencies
		
		//final cycle check before returning data to the GUI
		Vertex rootVertex = vertexList.get(0);
		String str = topologicalSort(rootVertex.element);
		
		//print adjacency list
		System.out.println(vertexListToString());
		//print toplogical sort
		System.out.println("****Default topological sort is:***\n" + str);
		
		//this is the boolean we return back to the GUI
		if (graphHasData() && !cycleDetected){
			return true;
		} else{
			return false; //primarily triggered by a detected cycle
		}
	}
	
	//each element in the linked list is processed. Ex: ClassA, then ClassC, then ClassE
	private void add(LinkedList<T> elementDependencies) throws CyclicGraphException{
		
		//during this first iteration, we're only concerned about creating the vertices
		for (int i = 0; i < elementDependencies.size(); i++){ 		//start a list iteration 
			
			T nextElement = elementDependencies.get(i); 		


			if (!checkForDuplicates(nextElement)){
				int vertexID = vertexList.size(); 						//the vertexID size of HashSet
				Vertex nextVertex = new Vertex(nextElement, vertexID); 	//vertex instantiation
				vertexList.add(nextVertex);		
				nextVertex.setAdjacencies(); 							//set's the hasmap of vertex ID to T element
			} else {
				continue;
			}
			
		}//end of new vertex instantiation
		
		//Now iterate through this linked list to determine dependencies
		Vertex rootVertex = findVertex(elementDependencies.get(0));	//findVertex is private method below
		
		LinkedList<Integer> tempList = rootVertex.getAdjacencyList(); //this should be empty
		
		for (int i = 1; i < elementDependencies.size(); i++){	//traverse this LinkedList one more time
			
			Vertex tempVertex = findVertex(elementDependencies.get(i));	//temporary vertex for processing
			
			//unique error check: see if there's a cycle within a root's own dependency list
			if (tempVertex.element.compareTo(rootVertex.element) == 0){
				throw new CyclicGraphException();
			}
			
			//not required, but if we generate an inDegree we have a default starting point for the entire graph
			tempVertex.inDegreePlusOne();	
			
			//now we add our integer representation to the Vertex's dependency list
			int dependentElementID = tempVertex.getVertexID();
			tempList.add(dependentElementID);
			
		} //end of dependency vertex addition
				
		rootVertex.setAdjacencyList(tempList);	//replace the blank adjacency list with our generated one above 
	
	} //end of add method
	
	//topographic sort method
	public String topologicalSort(T element) throws CyclicGraphException{		
		//first reset counter and topSort values for multiple method calls from GUI
		sortCounter = 0; 
		
		for (int i = 0; i < vertexList.size(); i++){
			vertexList.get(i).setTopSort(-1);
		}
		
		//at a minimum, we'll return the vertex passed into this method
		Vertex rootVertex = findVertex(element); //linearly traversing the structure looking for a match seems wasteful
		rootVertex.setTopSort(sortCounter);		//root vertex starts with 0 topSort value (must be more than -1)
		String str = "";						//instantiate String for later processing

		//set all vertex boolean values to false before our topological sort
		for (int i = 0; i < vertexList.size(); i++){
			vertexList.get(i).setUnvisited();
		}
		
		//process vertices with dependent nodes first, then those without second
		assignOrderDependents(rootVertex);
		assignOrderNoDependents(rootVertex);
		
		//now piece the returned string together
		for (int i = 0; i < sortCounter+1; i++){
			Vertex nextVertex = findVertexByTopSort(i);
			T insertElement = nextVertex.element;	//grab element from HashMap
			String insert = insertElement.toString();
			str += " " + insert;
		}
		return str;
	}
	
	//on the first pass, we'll only process vertices that have links
	private void assignOrderDependents(Vertex rootVertex)throws CyclicGraphException{
		LinkedList<Integer> tempList = rootVertex.getAdjacencyList();
		//if our root vertx is isolated and has no dependencies
		if (tempList.isEmpty()){
			rootVertex.setVisited();
			return;
		} else {
			for (int i = 0; i < tempList.size(); i++){
				int nextID = tempList.get(i);
				Vertex nextVertex = findVertexByID(nextID);
				
				//skip over non-dependent elements the first time around
				if (nextVertex.getAdjacencyList().isEmpty()){
					continue;
				}
				else{
					if(nextVertex.visited){
						System.out.println("Cycle Detected!");
						//break;
						throw new CyclicGraphException();  // <----------changed this during last edit
					}
					nextVertex.setVisited();
					sortCounter++;
					nextVertex.setTopSort(sortCounter);
					assignOrderDependents(nextVertex);
				}
			}
		}
	}
	
	//now the second path, we'll assign sort order for vertices with no dependends
	private void assignOrderNoDependents(Vertex rootVertex) throws CyclicGraphException{
		LinkedList<Integer> tempList = rootVertex.getAdjacencyList();
		//again, if it's an isolated vertex, just return
		if (tempList.isEmpty()){
			return;
		} else{
			for (int i = 0; i < tempList.size(); i++){
				int nextID = tempList.get(i);
				Vertex nextVertex = findVertexByID(nextID);
				if (nextVertex.getAdjacencyList().isEmpty()){
					if(nextVertex.visited){
						System.out.println("Cycle Detected!");
						throw new CyclicGraphException();
					}
					nextVertex.setVisited();
					sortCounter++;
					nextVertex.setTopSort(sortCounter);
				} else{
					
					//skip over the verticies identified with dependencies
					assignOrderNoDependents(nextVertex);
				}
			}
		}
	}
	
	////////////////////////////private helper methods////////////////////////////////
	
	//this is mostly a tester method to see if everything populated properly
	public String vertexListToString(){
		String str = "*****Adjacency Lists**************\n";
		for (int i = 0; i < vertexList.size(); i++){
			int setIndex = vertexList.get(i).vertexID;
			T setElement = vertexList.get(i).element;
			String adjList = vertexList.get(i).adjacencyList.toString();
			
			String nextLine = "[ "  + setIndex + ", " + setElement.toString() + "] : " + 
					adjList + "\n";
			
			str += nextLine;
		}
		return str;
	}
	
	//This traverses the data structure and detects a match using the compareTo method from Comparable.
	private Vertex findVertex(T element){  //we should only use this initially to detect duplicate verticies
		for (int i = 0; i < vertexList.size(); i++){
			if (element.compareTo(vertexList.get(i).element) == 0){
				return vertexList.get(i);
			}
		}
		return null;
	}
	
	//this searches by topSort integer and returns a Vertex
	private Vertex findVertexByTopSort(int topSort){
		for (int i = 0; i < vertexList.size(); i++){
			if (topSort == vertexList.get(i).getTopSort()){
				return vertexList.get(i);
			} 
		}
		return null;
	}
	
	private Vertex findVertexByID(int vertexID){
		for (int i = 0; i < vertexList.size(); i++){
			if (vertexID == vertexList.get(i).getVertexID()){
				return vertexList.get(i);
			}
		}
		return null;
	}
	
	//runs a linear traversal of the graph to look for any matches. Adding to a set would be a better solution
	private boolean checkForDuplicates(T element){
		for (int i = 0; i < vertexList.size(); i++){
			if (element.compareTo(vertexList.get(i).element) == 0){
				return true;
			}
		}
		return false;
	}
	
	//check from GUI for displaying results
	public boolean graphHasData(){
		if (vertexList.size() > 0){
			return true;
		} else {
			return false;
		}
	}

}




