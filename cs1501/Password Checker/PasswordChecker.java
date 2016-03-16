/**
 * @author Brandon S. Hang
 * @version 1.800
 * CS 1501
 * Assignment 1
 * February 12, 2016
 * 
 * This is the main class that is called to check a password for its validity.
 * Passwords must be 5 characters long and must contain at least 1 letter,
 * number, and symbol.  The program must be run with the "-g" command line
 * argument first in order to generate a list of words (and their enumerations)
 * and a list of valid passwords.  These are saved in the text files
 * my_dictionary.txt and good_passwords.txt respectively.  When the program is
 * run without any arguments, it prompts the user for a password to check.  If
 * the password is valid, it notifies the user of its validity.  Otherwise, it
 * will display 10 passwords that are valid using the largest prefix of what
 * the user entered.  The program will loop until the user enters "no" when
 * asked to continue.
 */

import java.io.*;
import java.util.Scanner;

public class PasswordChecker {
	
	private static Scanner parse, scan;					// Scan is for getting user input, Parse is for scanning text files
	private static File goodPasswords;					// The good_passwords.txt File object
	private static DLBTrie<Character, String> goodPasswordTrie;			// Stores good passwords in a de la Briandais trie
	private static boolean validPassword;				// Determines if a password is valid or not
	
	
	public static void main(String[] args) throws IOException {
		
		switch (args.length) {
			case 0:					// Case where PasswordChecker is run without arguments; checks user input for password validity
				String userInput;
				scan = new Scanner(System.in);
				
				do {
					System.out.printf("\nPlease enter your password to check: ");
					userInput = scan.nextLine().toLowerCase();			// Gets user input and converts it to all lowercase letters
					System.out.printf("\n");						// Newline for display formatting only
					
					initializeTrie(userInput);				// Method that fills the good password trie (if needed)
					checkPassword(userInput);				// Checks the user's password against those found in the trie
					
					System.out.printf("\nWould you like to try another password (type \"yes\" or \"no\")? ");		// Input must be "yes" or "no"
					userInput = scan.nextLine().toLowerCase();			// Input to continue program is case-insensitive
				} while (userInput.equals("yes"));						// Loops while input is "yes"
				
				parse.close();			// Closes the good password file
				System.out.printf("\nGood-bye!\n");			// Good-bye!
				
				break;
				
			case 1:					// Case where PasswordChecker is run with a single argument; generates my_dictionary.txt and good_passwords.txt
				if (0 == args[0].compareToIgnoreCase("-g")) {		// Argument matches "-g"
					new MyDictionaryGenerator();				// Creates my_dictionary.txt
				}
				else {
					throw new IllegalArgumentException("An invalid argument was entered in the command line!  Please try again.");
				}
				
				System.out.printf("\nDone!\n");
				
				break;
				
			default:				// Case where there is more than 1 argument
				throw new IllegalArgumentException("Too many arguments were passed into the command line!  Please try again.");
		}
		
		System.exit(0);
	}
	
	
	/**
	 * Builds the good password trie using the first character of the entered password.  If no password is entered or the first
	 * character of the password is illegal (a, i, 1, 4), then the trie is built using a pseudo-randomly selected character from
	 * the PASS_CHARS array in GoodPasswords.java.  If a trie already exists (from a previous search), the method checks the first
	 * key and compares it to the first character in the entered password.  If they are the same, the method will not create a new
	 * trie and will reuse the existing one.
	 * @param password The user-entered password
	 */
	private static void initializeTrie(String password) throws IOException {
		
		Character firstTrieKey;				// The trie will only contain passwords starting with this character to save memory usage
		String filePassword;
		
		try {
			goodPasswords = new File("good_passwords.txt");
			parse = new Scanner(goodPasswords);
		}
		catch (FileNotFoundException e) {				// Exception when good_passwords.txt does not exist
			System.out.printf("Error: Could not find \"good_passwords.txt\".  Please check your directory for the text file"
					+ " or run the program with the command line argument '-g'.\n");
			System.exit(1);
		}
		
		if (goodPasswordTrie != null) {			// Conditional if a trie already exists
			if (0 == password.length()) {			// If an empty password is entered, the method returns as a trie already exists
				return;
			}
			if (goodPasswordTrie.getRootNode().getKey().equals(password.charAt(0))) {
				return;					// Gets the first key from the trie and compares it to the first character in the entered password
			}				// returns if they are equal (no need to create a new trie)
		}
		
		if (0 == password.length() || !Character.toString(password.charAt(0)).matches("[b-hj-z0235-9!@$^*_]")) {	// If the password is blank or contains an illegal character (regular expression)
			int randomIndex = (int)(Math.random() * GoodPasswords.PASS_CHARS.length);		// Creates a random index using the PASS_CHARS array size
			firstTrieKey = GoodPasswords.PASS_CHARS[randomIndex];			// Sets the first trie key to a random legal character
		}
		
		else {
			firstTrieKey = password.charAt(0);			// Otherwise, sets the first trie key to the first character of the entered password
		}
		
		goodPasswordTrie = new DLBTrie<Character, String>();			// Creates a new trie (the old one, if it exists, will be garbage collected)
		parse = new Scanner(goodPasswords);						// Creates a new Scanner for parsing the file; this resets the file pointer to the beginning
		
		System.out.printf("Searching the database...\n\n");
		
		while (parse.hasNextLine()) {			// Parses every line of the file but only adds passwords beginning with the first trie key
			filePassword = parse.nextLine();
			
			if (firstTrieKey.equals(filePassword.charAt(0))) {
				DLBTCharMethods.addWord(goodPasswordTrie, filePassword);		// Calls method to add the password to the trie
			}
		}
	}
	
	
	/**
	 * Takes the user-entered password and searches for it in the good password trie.  If the password is found, the program
	 * will display a success message denoting that the user has entered a valid password.  Otherwise, it will suggest 10
	 * passwords using the largest valid prefix of the password that was entered.  This method will also truncate the password
	 * if it exceeds 5 characters.  The program will notify the user of the truncated password if it is valid.
	 * @param password
	 */
	private static void checkPassword(String password) {
		
		validPassword = true;				// Boolean assumes the password is valid until proven otherwise
		boolean truncated = false;			// Boolean assumes the password is of normal length until proven otherwise
		DLBNode<Character, String> node = goodPasswordTrie.getRootNode();			// The initial key of the trie
		String passwordSearch;					// The password to search for after modification(s)
		
		if (password.length() > 5) {				// Truncates the password if it exceeds 5 characters
			System.out.printf("Your password is too long!  It has been truncated to meet requirements.\n");
			password = password.substring(0, 5);
			truncated = true;
		}
		
		passwordSearch = password.concat("#");			// Concatenates the terminal character of the trie
		
		for (int i = 0; i < passwordSearch.length(); i++) {				// Searches through the trie starting from the first key
			node = findCharacter(node, passwordSearch.charAt(i));
			
			if (!validPassword) {				// If at any point the password is invalid, exits out of the loop
				break;
			}
		}
		if (validPassword && !truncated) {			// If the password is valid and not truncated, prints a success message
			System.out.printf("The password %s was found in the database!\n", node.getValue());
			System.out.printf("Congratulations!  Your password is a great choice!\n");
		}
		
		else if (validPassword && truncated) {		// If the password is valid but was truncated, displays a success/warning message
			System.out.printf("The truncated password %s was found in the database!\n", node.getValue());
			System.out.printf("Please remember that your password was shortened to meet requirements.\n");
		}
		
		else {					// Otherwise, displays an invalid password message
			System.out.printf("The password %s was not found in the database.\n", password);
			System.out.printf("Here are some suggested passwords you could use instead:\n");
			DLBTCharMethods.printValues(node, 10);			// Prints 10 valid passwords using the largest prefix of the entered password
		}
	}
	
	
	/**
	 * Searches for the character at the given node using a recursive method.  It compares the key of the current node with
	 * a character from the entered password.
	 * @param currentNode The current node in the trie
	 * @param ch A character of the entered password
	 * @return Returns a node of the trie that is either the next node to search for or an end-of-path node with a stored value
	 */
	private static DLBNode<Character, String> findCharacter(DLBNode<Character, String> currentNode, Character ch) {
		
		if (ch.equals(currentNode.getKey()) && !ch.equals('#')) {		// If the character matches the key and is not the terminal symbol
			currentNode = currentNode.getNextList();			// Sets the node to be returned as the next linked list of the trie
		}
		else if (ch.equals(currentNode.getKey()) && ch.equals('#')) {		// If the character matches the key and is the terminal symbol
			return currentNode;								// Returns the current node (to pull the password from it)
		}
		else if (currentNode.hasNextNode()) {				// If the key and character do not match and the current node has an adjacent node
			currentNode = currentNode.getNextNode();
			currentNode = findCharacter(currentNode, ch);		// Searches for the character in the next node of the linked list
		}
		else {								// Otherwise, the password is invalid as there are no more characters to search for
			validPassword = false;
		}
		
		return currentNode;				// Returns a node from the trie
	}
}
