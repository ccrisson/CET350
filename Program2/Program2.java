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
		
		// Variable Declarations
		String word = "";
		String inbuffer = "";
		Word[] words = new Word[100];
		boolean go = true;
		String inFile = "";
		String outFile = "";
		String choice = "";
		boolean overwrite = false;
		int uniqueWords = 0;
		int sumOfInts = 0;
		// For keyboard input
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
		if((outFile.equals("")) && (go)) {
			System.out.println("Please enter an output file name. Enter nothing to quit.");
			outFile = kbd.readLine();
			if (outFile.equals("")){
				go = false;
			}
		}
		// Check if output file exists
		// Condition - keeps running until the file does not exist 
		//             OR overwrite = true 
		while((fileExists(outFile) && !overwrite) && (go)){
			System.out.println(outFile + " already exists. Enter 1 to enter a new file name. Enter 2 to backup " + outFile 
				+ " Enter 3 to overwrite. Enter nothing to quit.");
			// Get and process users choice
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

		// If they havent quit yet
		if (go) {
			// Open the input file
			File inF = new File(inFile);
			// Open a buffered reader with the input file 
			BufferedReader in  = null;
			try {
				in = new BufferedReader(new FileReader(inF));
			}
			catch (IOException e){
				System.out.println(inF + " not found. Quitting");
				go = false;
			}
			if (go){
				// Loop while the input file is not EOF
				while((inbuffer = in.readLine()) != null){
					// Construct StringTokenizer with updated string
					StringTokenizer inline = new StringTokenizer(
						inbuffer, " \t\n!@#$%^&*()_+=[]{}\\|\":;/.,<>");
					// Loop while tokenizer has tokens left
					while((inline.countTokens()) != 0) {
						word = inline.nextToken();
						// Check if word is an Integer
						if (isInt(word)){
							sumOfInts = sumOfInts + Integer.parseInt(word);
						} else {
							// Check that word begins with alpha char
							// If it doesn't - move along
							if (Character.isLetter(word.charAt(0))){
								// Check if the words array is empty
								if(words[0] != null) {
									wordSearch(word, words);
								} else {
									words[0] = new Word(word);
								}
							}	
						}
						
					}
				}
			}
		}

		

		if (go) {
			//Write to output file
			FileWriter fileWriter = new FileWriter(outFile);
				PrintWriter printWriter = new PrintWriter(fileWriter);
				// Write the array
				for (int i = 0; i < words.length; i++) {
					if (words[i] != null) {
						uniqueWords++;
						printWriter.printf("%s %d%n",words[i].getWord(), words[i].getQuantity());
					}
				}
				// Write the numbers
				printWriter.printf("Unique words: %d%n",uniqueWords);
				printWriter.printf("Sum of integers: %d%n",sumOfInts);
				printWriter.close();
		} 
	} // End main

	public static void wordSearch(String word, Word[] words) {
		boolean exists = false;
		int i = 0;
		while ((words[i] != null) && (!exists)) {
			if (words[i].isWordIgnoreCase(word)) {
				words[i].incrementQuantity();
				exists = true;
			}
			i++;
		}
		if (!exists && (i < 99)) {
			words[i] = new Word(word);
		}
	}

	public static boolean isInt(String str) {
		int num = 0;
		try {
			num = Integer.parseInt(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
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

	public static void backUp(String file) {
		String backupFileName = file.substring(0, file.lastIndexOf(".")
			+ 1).concat("bak") ;
		File old = new File(file);
		File bak = new File(backupFileName);
		if (fileExists(backupFileName)) {
			bak.delete();
		}
		old.renameTo(bak);
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