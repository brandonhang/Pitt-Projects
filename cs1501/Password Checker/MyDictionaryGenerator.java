/**
 * @author Brandon S. Hang
 * @version 1.500
 * CS 1501
 * Assignment 1
 * February 12, 2016
 * 
 * This class creates my_dictionary.txt and subsequently calls the class
 * that creates good_passwords.txt.  This class depends on dictionary.txt
 * being present in the same directory.  It will display an exception if
 * it is not found.  The program will create a de la Briandais trie and
 * populate it with words (and their enumerations) found in
 * dictionary.txt.  However, it will only add words that are 5 characters
 * or less as passwords can only be 5 characters.  As such, words longer
 * than 5 characters will never appear in passwords as that automatically
 * renders the password invalid. 
 */

import java.util.Scanner;
import java.io.*;
import java.lang.StringBuilder;

public class MyDictionaryGenerator {
	
	private static Scanner scan;			// Parses through dictionary.txt
	private static String word;				// A word from the text file
	private static DLBTrie<Character, String> trie;		// A trie containing words (and their enumerations) not to be found in passwords
	private static PrintWriter myDictionary;			// Prints words and variants to my_dictionary.txt
	
	
	public MyDictionaryGenerator() throws IOException {
		
		try {				// Attempts to open dictionary.txt for parsing
			File dictionary = new File("dictionary.txt");
			scan = new Scanner(dictionary);
		}
		
		catch (FileNotFoundException e) {			// Throws a file not found exception if it is missing
			System.out.printf("Error: Could not find \"dictionary.txt\".  Please check your directory for the text file.\n");
			System.exit(1);
		}
		
		myDictionary = new PrintWriter(new File("my_dictionary.txt"));		// Creates my_dictionary.txt
		trie = new DLBTrie<Character, String>();				// Initializes the dictionary trie
		
		System.out.printf("\nCreating my_dictionary.txt...\n");
		
		while (scan.hasNextLine()) {				// Loops while dictionary.txt has a line to parse
			word = scan.nextLine().toLowerCase();			// Pulls a word from dictionary.txt and converts it to lowercase
			
			if (word.length() > 5) {				// If the word is greater than 5 letters, it is skipped
				continue;
			}
			
			DLBTCharMethods.addWord(trie, word);		// Adds the word to the dictionary trie
			myDictionary.println(word);					// Adds the word to my_dictionary.txt
			
			convertWord(word, 0);			// Takes the word and finds all possible enumerations to add
		}
		
		myDictionary.close();				// Closes my_dictionary.txt
		
		new GoodPasswords(trie);			// Calls the class that creates good_passwords.txt
	}
	
	
	/**
	 * Converts a word into all of its possible enumerations based on the rules given in the project readme.  It
	 * recursively substitutes letters using substrings and an index marker in order to do so.  For example, the
	 * word "that" will have enumerations of "7hat", "7h4t", "7h47", "7ha7", "th4t", "th47", and "tha7".
	 * @param word The word to enumerate
	 * @param n The index marking the location to begin enumerating
	 */
	private void convertWord(String word, int n) {
		
		StringBuilder convertedWord;		// A StringBuilder object is used to substitute specific characters at locations
		
		for (int i = n; i < word.length(); i++) {		// Loops through the entire word to make substitutions
			switch (word.charAt(i)) {
				case 'a':
					convertedWord = new StringBuilder(word);		// Creates a new StringBuilder string
					convertedWord.setCharAt(i, '4');				// Substitutes 'a' with '4'
					DLBTCharMethods.addWord(trie, convertedWord.toString());		// Adds the converted word to the trie
					myDictionary.println(convertedWord.toString());					// Adds the converted word to my_dictionary.txt
					convertWord(convertedWord.toString(), i);				// Checks this converted word for more substitutions
					break;
					
				case 'e':					// These cases all follow the same format; they only differ in the letter that is substituted
					convertedWord = new StringBuilder(word);
					convertedWord.setCharAt(i, '3');				// Substitutes 'e' with '3'
					DLBTCharMethods.addWord(trie, convertedWord.toString());
					myDictionary.println(convertedWord.toString());
					convertWord(convertedWord.toString(), i);
					break;
					
				case 'i':
					convertedWord = new StringBuilder(word);
					convertedWord.setCharAt(i, '1');				// Substitutes 'i' with '1'
					DLBTCharMethods.addWord(trie, convertedWord.toString());
					myDictionary.println(convertedWord.toString());
					convertWord(convertedWord.toString(), i);
					break;
					
				case 'l':
					convertedWord = new StringBuilder(word);
					convertedWord.setCharAt(i, '1');				// Substitutes 'l' with 'i'
					DLBTCharMethods.addWord(trie, convertedWord.toString());
					myDictionary.println(convertedWord.toString());
					convertWord(convertedWord.toString(), i);
					break;
					
				case 'o':
					convertedWord = new StringBuilder(word);
					convertedWord.setCharAt(i, '0');				// Substitutes 'o' with '0'
					DLBTCharMethods.addWord(trie, convertedWord.toString());
					myDictionary.println(convertedWord.toString());
					convertWord(convertedWord.toString(), i);
					break;
					
				case 's':
					convertedWord = new StringBuilder(word);
					convertedWord.setCharAt(i, '$');				// Substitutes 's' with '$'
					DLBTCharMethods.addWord(trie, convertedWord.toString());
					myDictionary.println(convertedWord.toString());
					convertWord(convertedWord.toString(), i);
					break;
					
				case 't':
					convertedWord = new StringBuilder(word);
					convertedWord.setCharAt(i, '7');				// Substitutes 't' with '7'
					DLBTCharMethods.addWord(trie, convertedWord.toString());
					myDictionary.println(convertedWord.toString());
					convertWord(convertedWord.toString(), i);
					break;
					
				default:
					break;
			}
		}
	}
}
