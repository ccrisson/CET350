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
	public static void main(String[] args) {
		
		String word = "";
		String inbuffer;
		Word[] words = new Word[100];

		// Testing section
		words[0] = new Word("Lappa");
		words[0].incrementQuantity();
		System.out.println(words[0].getWord() + words[0].getQuantity());
		System.out.println(words[0].isWord("Lappa"));
		System.out.println(words[0].isWordIgnoreCase("lappa"));
		System.out.println(words[0].isWord("La"));

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