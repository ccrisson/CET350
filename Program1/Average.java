/*Average.java
  CET-350 Technical Computing Using Java
  Group 9
  Chris Crisson CRI4537@calu.edu
  Matthew Bedillion BED9714@calu.edu
  Mark Blatnik BLA9072@calu.edu
  Josh Williams WIL6155@calu.edu
*/

import java.io.*;
import java.text.DecimalFormat;

class Average {
	public static void main(String[] args) throws IOException {
		// Open the keyboard as BufferedReader
		BufferedReader kbd = new BufferedReader(
			new InputStreamReader(System.in));
		
		String input = "";
		double grade = 0;
		double total = 0;
		double average = 0;
		int gradesCounter = 0;
		boolean go = true; // Loop control variable

		// Main program Loop
		while(go){
			System.out.println("Enter a grade. " + 
				"Enter less than 0 or greater than 100 when finished.");
			input = kbd.readLine();
			// Check that input is a number. If not reprompt.
			while (!isNumeric(input)){
				System.out.println(input + " is not a number. \n" +
					"Enter a grade. " +
					"Enter less than 0 or greater than 100 when finished.");
				input = kbd.readLine();
			}
			grade = Double.parseDouble(input);
			// Check if user wants to quit
			if((grade > 100) || (grade < 0)){
				go = false;
			}else{
				total = total + grade;
				gradesCounter++;
			}
		}
		// calculate average
		average = total / gradesCounter;
		// Display results
		if(Double.isNaN(average)){
			System.out.println("You didn't enter any grades.");
		} else{
			System.out.println("The sum of grades entered is " + total);
			System.out.println("The number of grades entered is " + gradesCounter);
			System.out.println("The average of grades entered is " +
				average);
		}
		
	}

	// Check if parameter is a double
	public static boolean isNumeric(String str){
		try{
			// Convert input to double
			double temp = Double.parseDouble(str);
			return true;
		} catch (NumberFormatException err){
			return false;
		}
	}
}