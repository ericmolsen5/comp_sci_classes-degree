//package homework3;
/* 
 * File Name: TestQ2.java
 * Author: Eric Olsen
 */
import java.util.Random;
import java.util.Scanner;

public class TestQ2 {
	
	//here is the prescribed recursive unsortedArray method
	public static int findValueUnsortedArray(int[] unsortedArray, int searchedValue){
		if (unsortedArray[0] == searchedValue){ //recursive exit criteria
			return 0;
		}
		//if there are 2 or more elements in the array, we can subdivide
		else if (unsortedArray.length > 1){
			//this is a call to our helper method below
			return findValueUnsortedArray(unsortedArray, 0, unsortedArray.length-1, 
					searchedValue);
		}
		//if no element is detected, return a -1
		else{
			return -1;
		}
	}
	//unsorted array helper method. Here we add a starting and ending index value
	private static int findValueUnsortedArray(int[] unsortedArray, int start, int end,
			int searchedValue){
		//check the front end of the divided array
		if (unsortedArray[start] == searchedValue){
			return start;
		//check the tail end of the divided array. Only an array greater than size two will proceed.
		}else if (unsortedArray[end] == searchedValue){
			return end;
		}else if(end - start > 1){ 
			//the right half calls itself with the middle value as a start and the end decremented by 1
			int rightsearch =  findValueUnsortedArray(unsortedArray, end/2 + 1, 
					end - 1, searchedValue);
			//the left half increments it's zero value by one and it's end value is the middle
			int leftSearch = findValueUnsortedArray(unsortedArray, start + 1, 
					end/2, searchedValue);
			//Since not finding the value results in a negative one, I can return the positive result
			//from the left or right. Otherwise I have to painfully try to plug two recursive calls into
			//one method which becomes confusing very quickly.
			return Math.max(leftSearch, rightsearch);
		}else{
			return -1;
		}
	}
		
	//binary search method
	public static int findValueSortedArray(int[] sortedArray, int searchedValue){
		
		return findValueSortedArray(sortedArray, 0, sortedArray.length-1, searchedValue);
	}
	
	//binary search helper method
	private static int findValueSortedArray(int[] sortedArray, int start, int end, 
			int searchedValue){
	
		int middle = (end + start) / 2; //determine the new middle point by averaging the start and end
		//since we decremented the length by one, this should return -1 in the event that we decrement too far
		//lesson learned: put this check at the top to avoid ArrayIndexOutOfBoundExceptions
		if (end < start){
			return -1;
		}
		if (sortedArray[middle] == searchedValue){
			System.out.println("returned middle");
			return middle;
		}
		//continue the binary search criteria and recursively call this helper method
		else if(searchedValue < sortedArray[middle]){
			return findValueSortedArray(sortedArray, start, middle - 1, searchedValue);			
		}
		//continue calling this helper method recursively
		else {
			return findValueSortedArray(sortedArray, middle + 1, end, searchedValue);
		}
	}
	
	public String toString(int[] array){
		String str = "";
		for (int i = 0; i < array.length; i++){
			str += i + ": " + array[i] + ", \n";
		}
		return str;
	}
	
	//method to error check on user input. This is much simpler than cramming it into
	//the print lines int he main method
	public boolean isNumber(String str){
		try{
			int num = Integer.parseInt(str);
		} catch (NumberFormatException e){
			return false;
		}
		return true;
	}
	
	public static void main (String[] args) { 
		
		TestQ2 app = new TestQ2();
		Random rand = new Random();
		Scanner scanIn = new Scanner(System.in);
		
		//instantiate our unsorted array 
		int[] unsortedArray = new int[20];	
		//generate the random values between -100 and 100
		for (int i = 0; i < 20; i++){
			unsortedArray[i] = rand.nextInt(199)-100;
			//run a reverse for loop to detect any duplicates
			for (int k = i; k > 0; --k ){
				if (unsortedArray[k] == unsortedArray[i]){
					unsortedArray[i] = rand.nextInt(199)-100; //this is a 99% solution, but not perfect
				}
			}//end of duplicate check
		}//end of random number generation
		
		//generate a sorted array
		int[] sortedArray = new int[20];
		//this will be our lowest value
		int tempInt = -99;
		//generate incrementing values for 20 indexes
		for (int i = 0; i < 20; i++){
			sortedArray[i] = (tempInt += rand.nextInt(10)+1); 
			if (sortedArray[i] >= 100)
				sortedArray[i] -= 17; //just in case it exceeds 100
		} //end of sorted number generation
	
		//both arrays are now generated and ready
		System.out.println("Welcome to the recursive search driver program.");
		
		boolean running = true;
		while (running){
			
			System.out.println("Below is the randomly generated unsorted array (index: value):");
			System.out.println("\n" + app.toString(unsortedArray));
			int unsortedSearchItem = 0;
			
			while (true){
				System.out.println("\nPlease enter the number you wish to recursively search for.");
				String userInput1 = scanIn.next();
				if (app.isNumber(userInput1)){
					unsortedSearchItem = Integer.parseInt(userInput1);
					break;
				}
				else{
					System.out.println("There was an issue processing your input, please try again.\n");
				}
			}//end of user input loop
			
			//first method call
			int result1 = findValueUnsortedArray(unsortedArray, unsortedSearchItem);
			//message for successful lookup
			if (result1 != -1){
			System.out.println("The value: " + unsortedSearchItem + " was located at index "
					+ result1);
			} 
			//message for unsuccessful lookup
			else{
				System.out.println("The search method was unable to locate your number.");
			}

			System.out.println("\nNow we'll check using binary search.");
			
			int sortedSearchItem = 0;
			while(true){
				System.out.println("Our sorted array is listed below (index: value");
				System.out.println("\n" + app.toString(sortedArray));
				System.out.println("\nPlease enter the number you wish to recursively search for.");
				String userInput2 = scanIn.next();
				if (app.isNumber(userInput2)){
					sortedSearchItem = Integer.parseInt(userInput2);
					break;
				} else{
					System.out.println("There was an issue processing your input, please try again.\n");
				}
			}//end of user input loop
			
			int result2 = findValueSortedArray(sortedArray, sortedSearchItem);
			//successful run of the method
			if (result2 != -1){
			System.out.println("The value: " + sortedSearchItem + " was located at index "
					+ result2);
			} 
			//message if value is not found
			else{
				System.out.println("The search method was unable to locate your number.");
			}	
			System.out.println("\nWould you like to continue using this program?");
			System.out.println("type 'N' to quit or any other key to continue.");
			String userInput3 = scanIn.next();
			char c = Character.valueOf(userInput3.charAt(0));
			if ((c=='N') || (c == 'n'))
				running = false;
		}//end of user interface loop
		System.exit(0);
	}
}

