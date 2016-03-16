/**
 * @author Brandon S. Hang
 * @version 2.000
 * CS 1501
 * Assignment 1
 * February 12, 2016
 * 
 * This class creates good_passwords.txt by creating permutations of
 * length 5 using the valid characters.  It only adds passwords that
 * follow rules found in the project readme and also do not contain
 * words found in the dictionary trie.  
 */

import java.io.*;

public class GoodPasswords {
	
	private static DLBTrie<Character, String> trie;			// The dictionary trie created from MyDictionaryGenerator.java
	private static PrintWriter goodPasswords;					// Prints good passwords to good_passwords.txt
	private static boolean validPassword, keepSearching;		// Booleans denoting whether a password is valid or to keep searching for a password
	protected final static char PASS_CHARS[] = {'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j',		// Characters a, i, 1, and 4 are not permissible as they are common, single-letter words
			'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',		// This array is protected so it may be accessed by pw_check.java
			'z', '2', '3', '5', '6', '7', '8', '9', '0', '!', '@', '$', '^', '_', '*'};
	
	
	/**
	 * @param dlbt The trie of dictionary words created from MyDictionaryGenerator.java
	 */
	public GoodPasswords(DLBTrie<Character, String> dlbt) throws IOException {
		
		trie = dlbt;			// Sets the trie passed in the parameter
		goodPasswords = new PrintWriter(new File("good_passwords.txt"));		// Creates good_passwords.txt
		System.out.printf("Creating good_passwords.txt...\n");
		
		buildPermutations("", 5);			// Creates permutations of length 5 and attempts to add it to the text file; "" is the empty string
		
		goodPasswords.close();			// Closes the good_passwords.txt file
	}
	
	
	/**
	 * Recursively builds permutations of the PASS_CHARS array characters into strings of length passLength.
	 * @param password The password to be built.  The method is always called first with "", the empty string
	 * @param passLength The length of the password to build.  It signifies the number of characters left to add to the string
	 */
	private void buildPermutations(String password, int passLength) {
		
		if (0 == passLength) {				// Conditional if the number of characters to add is 0
			tryPassword(password);			// Checks the built password with the dictionary trie
			return;
		}
		for (int i = 0; i < PASS_CHARS.length; i++) {			// Loop that builds the password using array indices
			String newPassword = password + PASS_CHARS[i];		// Concatenates the current password with a character from the array
			buildPermutations(newPassword, passLength - 1);		// Recursive call with a decremented number of characters to add
		}
	}
	
	
	/**
	 * Once a password permutation is created, this method checks to see if it found within the dictionary trie.  If it
	 * is not found, it will be added to good_passwords.txt. 
	 * @param password The password to check for validity
	 */
	private void tryPassword(String password) {
		
		int numLetters = 0, numNumbers = 0, numSymbols = 0;			// Variables holding the number of each type of character
		
		for (int i = 0; i < password.length(); i++) {				// Loops through the password to get character counts
			if (Character.isLetter(password.charAt(i))) {
				numLetters++;
			}
			else if (Character.isDigit(password.charAt(i))) {
				numNumbers++;
			}
			else {				// If it is not a letter or number, then it must be a symbol
				numSymbols++;
			}
		}
						// If the password does not contain the minimum number of each character or exceeds the maximum number of a type,
		if (numLetters > 3 || numLetters < 1) {			// the password is rejected and the method returns
			return;
		}
		if (numNumbers > 2 || numNumbers < 1) {
			return;
		}
		if (numSymbols > 2 || numSymbols < 1) {
			return;
		}
		
		String passwordSubstring = new String(password);		// Copies the password as a new string to be analyzed for common words
		validPassword = true;					// Sets the validity of the password to true (until proven otherwise)
		
		while (passwordSubstring.length() > 2 && validPassword) {		// Loops until either the string length is less than true or the password is rejected
														// When the substring is of length 1, it is automatically a valid character as invalid characters were omitted
			DLBNode<Character, String> currentNode;
			currentNode = trie.getRootNode();				// Sets the starting node of the trie for the program to check
			keepSearching = true;					// Boolean to continue searching through the trie
			
			for (int i = 0; i < passwordSubstring.length(); i++) {
				currentNode = findCharacter(passwordSubstring.charAt(i), currentNode);		// Recursively searches for a character in the trie
				
				if (!validPassword) {		// If at any time the password is invalid, the method will return
					return;
				}
				if (!keepSearching) {		// If the boolean to keep searching is false, it breaks out of the loop
					break;
				}
			}
			
			passwordSubstring = passwordSubstring.substring(1);		// Restarts the while loop with a substring of the password to search for in the trie
		}
		
		goodPasswords.println(password);				// If everything passes, the password is added to good_passwords.txt
		
		return;
	}
	
	
	/**
	 * This method gets a key from a node and compares it to the character passed in the parameter.  It also traverses through
	 * the trie using recursion and node returns.
	 * @param ch A character of the password to test
	 * @param node A node of the dictionary trie to search in
	 * @return The next node to search through, if applicable
	 */
	private DLBNode<Character, String> findCharacter(Character ch, DLBNode<Character, String> node) {
		
		if (ch.equals(node.getKey())) {			// If the key is the same as the password character
			node = node.getNextList();			// Returns with the node of the next linked list in the trie
		}
		else if (node.getKey().equals('#')) {		// If the key of the node is '#', then the password contains a dictionary word
			validPassword = false;				// The password is then rejected
		}
		else if (node.hasNextNode()) {			// If they are not the same and there is a next node, it will search the next node
			node = node.getNextNode();
			node = findCharacter(ch, node);			// Recursive call using the next node
		}
		else {				// Otherwise, the character was not found and the search stops
			keepSearching = false;
		}
		
		return node;
	}
	
}
