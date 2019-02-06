/*Program2.java
  CET-350 Technical Computing Using Java
  Group 9
  Chris Crisson CRI4537@calu.edu
  Matthew Bedillion BED9714@calu.edu
  Mark Blatnik BLA9072@calu.edu
  Josh Williams WIL6155@calu.edu
*/

import java.io.*;
import java.util.*;

class Program2 {
	public static void main(String[] args) throws IOException {
		
		String word = "";
		String inbuffer;
		Word[] words = new Word[100];
		boolean go = true;
		String inFile = "";
		String outFile = "";
		String choice = "";

		BufferedReader kbd = new BufferedReader(
			new InputStreamReader(System.in));

		// Populate variables with arguments if provided from 
		// the command line
		if(args.length > 1) {
			outFile = args[1];
		}
		if(args.length > 0) {
			inFile = args[0];
		}

		// Check/Prompt for input file
		while((!fileExists(inFile)) && (go)){
			System.out.println("Please enter an input file name. Enter nothing to quit");
			inFile = kbd.readLine();
			if (inFile.equals("")){
				go = false;
			}
		}
		// Check/Prompt for output file
		if(outFile.equals("") && (go)) {
			System.out.println("Please enter an output file name. Enter nothing to quit");
			outFile = kbd.readLine();
			if (outFile.equals("")){
				go = false;
			}
		}
		// Check if output file exists
		while((fileExists(outFile)) && (go)){
			System.out.println("That file already exists. Enter 1 to enter a new file name. Enter nothing to quit. Enter 2 to overwrite");
			choice = kbd.readLine();
			if (choice.equals("")) {
				go = false;
			} 
		}
		


	}

	public static boolean fileExists(String name) {
		boolean exist = false;
		if (name.length() != 0) {
			File file = new File(name);
			exist = file.exists();
		}
		return exist;
	}

}

// Word class is used to hold each unique word and the quantity
class Word {
	private String word;
	private int quantity;

	// Constructor
	public Word (String word) {
		this.word = word;
		this.quantity = 1;
	}

	// Getters
	public String getWord() {
		return word;
	}
	public int getQuantity() {
		return quantity;
	}

	// Setter(s)
	public void incrementQuantity() {
		quantity++;
	}

	// Comparison methods
	// Case-sensitive
	public boolean isWord(String word) {
		return this.word.equals(word);
	}
	// Case-insensitive 
	public boolean isWordIgnoreCase(String word) {
		return this.word.equalsIgnoreCase(word);
	}
}