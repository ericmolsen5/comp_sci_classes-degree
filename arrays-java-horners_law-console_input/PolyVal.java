//package homework1;
/* 
 * File Name: PolyVal.java
 * Author: Eric Olsen
 */
import java.util.Random;

public class PolyVal {
	
	private final int polynomialLength = 10; //for default constructor
	private int[] polynomial; //The index # is the power and index the coefficient
	private int randomInt; //our x variable
	Random rand = new Random(); //our coeffieicnt and variable generation
	
	//This default constructor will assemble a polynomial to the ninth power
	public PolyVal(){
		polynomial = new int[polynomialLength]; //instantiate and cap the length of the polynomial
		randomInt = rand.nextInt(8) + 1; //question b2: the lowest value is 1 and highest is 9
		for (int i = 0; i < polynomialLength; i++){ //question b1: coefficients between -9 and 9
			polynomial[i] = rand.nextInt(9); //fills a random value between 0 and 9
			if (rand.nextBoolean()){
				polynomial[i] *= -1;	// randomly turns values negative or positive
			}
		}//end of integer array generator
	} //end of default constructor
	
	//This constructor allows us to change the number of powers
	public PolyVal(int polyLength){
		polynomial = new int[polyLength];
		randomInt = rand.nextInt(8) + 1; //question b2
		for (int i = 0; i < polyLength; i++){
			polynomial[i] = rand.nextInt(9); //question b1
			if (rand.nextBoolean()){
				polynomial[i] *= -1;
			}
		} //end of coefficient generator
	}//end of paramaterized constructor
	
	//this generates and returns a String for printing to the console
	public String displayPolynomial(){
		String str = "";
		for (int i = 0; i < polynomial.length; i++){
			if (polynomial[i] < 0){ //if less than 0, negative sign becomes arithmetic operator
				String nextTerm = " " + polynomial[i] + "x^" + i; 
				str += nextTerm; //append to the preceding String text
			}
			else{
				if (i == 0){ //if it is the first term, do not use the plus operator
					String nextTerm = polynomial[i] + "x^" + i;
					str += nextTerm; //append to the blank String
				}
				else { //executes for positive coefficients that require a plus sign
					String nextTerm = " + " + polynomial[i] + "x^" + i;
					str += nextTerm; //append to the preceding String text
				}
			}
		} //end of String generation
		return str;
	} //end of method
	
	//we have a getter so we can print this from the main method
	protected int getRandomInt(){
		return randomInt;
	}
	
	//if we generate the polynomial from the default constructor, we need a getter
	//so we can then pass the polynomial into the calculation methods as parameters.
	protected int[] getPoly(){
		return polynomial;
	}
	
	//This accepts an integer array as the polynomial and the integer variable value
	public int calculateBruteForce(int[] poly, int variable){
		//Basic error control. The program closes if more than 10 terms are passed
		if (poly.length > 10){
			System.out.println("This program cannot calculate polynomial larger than the tenth degree.\n"
					+ "Please generate a smalle polynomial.");
			System.exit(0);
		}
		int solution = 0; 
		for (int i = 0; i < poly.length; i++){
			solution += poly[i] * power(variable, i); //i is the power we are raising this to
		}
		return solution;
	}
	
	//method calculates powers without using the Math Class in the java language
	private int power(int variable, int power){
		int newValue = 0; //initialize the variable
		int updatedVar = variable;
		if (power == 0){ //when the exponent equals 0
			newValue = 1;
		}
		else {
			for (int i = 0; i < power - 1; i++){ //lesson learned: you must decrement the second field
				updatedVar *= variable;
			}
			newValue = updatedVar; //this is needed, but I'm still trying to figure out why
		}
		return newValue;
	}
	
	//horner method takes the same integer array and integer variable
	protected int calculateHorner(int[] poly, int variable){
		//Error control. The program closes if more than 10 terms are passed
				if (poly.length > 10){
					System.out.println("This program cannot calculate polynomial larger "
							+ "than the tenth degree.\n"
							+ "Please generate a smalle polynomial.");
					System.exit(0);
				}
		int newValue = poly[poly.length - 1]; // the inner-most parenthesis
		//P(x) = a0 + x * (a1 + x * (a2 + … x * (an-2 + ( x * an-1) ) ) … ) 
		//working from the inside to the outside of parentheses
		for (int i = poly.length-2; i >= 0; i--){ 
			newValue = newValue * variable + poly[i];  
		}
		return newValue;
	}
	
	
	//main argument
	public static void main (String[] arg){ 
		
		PolyVal app = new PolyVal(10); //We use the parameterized constructor
		String polyDisplay = app.displayPolynomial(); //the String for printing our polynomial
		int[] poly = app.getPoly(); //call the polynomial
		int randomInt = app.getRandomInt(); //call the Random int
		
		System.out.println("Welcome to the Brute Force of Horner Calculation tool\n"); //intro to program
		
		System.out.println("Our generated polynomial is:");	
		System.out.println(polyDisplay + "\n");
		
		System.out.println("Our randomly tested value is: " + randomInt + "\n");
		
		//question b3: we display that both methods work properly
		System.out.println("Our BruteForce method generates: "
				+ app.calculateBruteForce(poly, randomInt));
		System.out.println("The Horner method generates: "
				+ app.calculateHorner(poly, randomInt) + "\n");
		
		System.out.println("Below are the time requirements for these methods at 1,000 executions: \n");
		//this first loop exists so we can discard the first 10 calculation iterations
		for (int i = 0; i < 10; i++){
			app.calculateBruteForce(poly, randomInt);
		}
		long startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++){
			app.calculateBruteForce(poly, randomInt);
		}
		long curTime = System.nanoTime();
		long bruteForceTime = curTime - startTime;
		System.out.println("The brute force calucluation required " + bruteForceTime 
				+ " nanoseconds."); 
		
		//this first loop exists so we can discard the first 10 calculation iterations
		for (int i = 0; i < 10; i++){
			app.calculateHorner(poly, randomInt);
		}
		startTime = System.nanoTime();
		for (int i = 0; i < 1000; i++){
			app.calculateHorner(poly, randomInt);
		}
		curTime = System.nanoTime();
		long hornerTime = curTime - startTime;
		System.out.println("The horner calucluation required " + hornerTime 
				+ " nanoseconds. \n"); 
		
		//question b4: this shows that the horner method executes much faster
		System.out.println("The Horner method executed " + (bruteForceTime - hornerTime)
				+ " nanoseconds faster."); 
	}

}
