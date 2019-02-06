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
		String inbuffer = "";
		Word[] words = new Word[100];
		boolean go = true;
		String inFile = "";
		String outFile = "";
		String choice = "";
		boolean overwrite = false;
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
			System.out.println("Please enter an input file name. Enter nothing to quit.");
			inFile = kbd.readLine();
			if (inFile.equals("")){
				go = false;
			}
		}
		// Check/Prompt for output file
		if(outFile.equals("") && (go)) {
			System.out.println("Please enter an output file name. Enter nothing to quit.");
			outFile = kbd.readLine();
			if (outFile.equals("")){
				go = false;
			}
		}
		// Check if output file exists
		// Condition - keeps running until the file does not exist 
		//             or overwrite = true 
		while((fileExists(outFile) && !overwrite) && (go)){
			System.out.println(outFile + " already exists. Enter 1 to enter a new file name. Enter 2 to backup " + outFile 
				+ "Enter 3 to overwrite. Enter nothing to quit.");
			choice = kbd.readLine();
			if (choice.equals("")) {
				go = false;
			} else if (choice.equals("1")) {
				System.out.println("Please enter an output file name. Enter nothing to quit.");
				outFile = kbd.readLine();
				if (outFile.equals("")){
					go = false;
				}
			} else if (choice.equals("2")) {
				backUp(outFile);
				if (outFile.equals(inFile)) {
					System.out.println("Can not overwrite the input file");
				} else {
					overwrite = true;
				}
			} else if (choice.equals("3")) {
				if (outFile.equals(inFile)) {
					System.out.println("Can not overwrite the input file");
				} else {
					overwrite = true;
				}
			} 
		}

		if (go) {
			StringTokenizer inline = new StringTokenizer(
				inbuffer, " \t\n!@#$%^&*()_-+=[]{}\\|\":;/.,<>");
			// Read file
		}
	} // End main

	public static boolean fileExists(String name) {
		boolean exist = false;
		if (name.length() != 0) {
			File file = new File(name);
			exist = file.exists();
		}
		return exist;
	}

	public static void backUp(String file) {
		String backupFileName = file.substring(0, file.lastIndexOf(".")
			+ 1).concat(".bak") ;
		System.out.println("Need to add backup feature " + file);
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