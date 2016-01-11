/**
 * @author Brandon S. Hang
 * @version 1.00
 * CS 0445
 * Assignment 3B
 * October 21, 2015
 * 
 * Requires WordSearch.java
 * 
 * This class reads a word search puzzle from a file and prompts
 *   the user to search for single words or phrases within the
 *   puzzle. Unlike Assign3.java, individual letters can change
 *   direction at any and all times. If a search is successful,
 *   each word in the phrase will have every location listed
 *   since it may not be in a straight line. No command-line
 *   arguments are to be entered as all interactivity comes
 *   from within the program. Also has a contingency should the
 *   phrase be a single letter.
 *   
 */


import java.util.Scanner;							// Imports the Scanner class
import java.util.StringTokenizer;					// Imports the StringTokenizer class

public class Assign3B {

	protected static int rowSize;					// Stores the row size of the word search puzzle
	protected static int colSize;					// Stores the column size of the words search puzzle
	private static boolean phraseFound = false;		// Boolean indicating if the phrase has been found; initialized to false
	private static WordSearch puzzle;				// Variable for a WordSearch object
	private static String phrase;					// String holding the phrase to search for
	private static String phraseTrimmed;			// The phrase without spaces
	
	
	public static void main(String[] args) {
		
		Scanner input = new Scanner(System.in);			// Creates a new Scanner object for user input
		
		System.out.println("Please enter the file name of the puzzle.");		// Message prompting the filename of the puzzle
		
		puzzle = new WordSearch(input.nextLine());		// Creates a new WordSearch object from the user input
		
		rowSize = puzzle.getRowSize();					// Assigns the row size from the WordSearch object
		colSize = puzzle.getColSize();					// Assigns the column size from the WordSearch object
		
		do {							// Begins word search loop
			System.out.println("Enter a phrase to search for (separated by single spaces).");		// Prompts user for a phrase
			System.out.println("Enter an empty line to quit.");				// Instructs the user how to exit
			phrase = input.nextLine().toLowerCase();			// Stores input as a lowercase string
			
			if (phrase.length() == 0) {							// Conditional if the phrase length is empty
				System.out.println("Goodbye!");					// Message indicating program termination
				System.exit(0);									// Exits program
			}
			
			StringTokenizer phraseTokens = new StringTokenizer(phrase);			// Stores and tokenizes the phrase
			phraseTrimmed = phrase.replaceAll(" ", "");						// Removes all spaces from the phrase
			
			System.out.println("Searching for the phrase \"" + phrase			// Message stating the search process has begun
					+ "\".\n\t(contains " + phraseTokens.countTokens() + " words)\n");		// Also displays the number of words
																							//   in the phrase
			puzzle.resetLocationList();				// Clears the list of coordinates
			puzzle.resetCase();						// Resets the puzzle to all lowercase
			phraseFound = false;					// Resets the phraseFound boolean to false
			
			if (phraseTrimmed.length() == 1) {							// Conditional if the phrase is a single letter
				for (int i = 0; i < rowSize; i++) {				// Nested loop searches every space in the 2-dimensional array
					for (int j = 0; j < colSize; j++)
						if (phraseTrimmed.charAt(0) == puzzle.getLetter(i, j)) {		// Conditional if the puzzle letter mathes the phrase
							puzzle.capitalizeLetter(i, j);			// Capitalizes the current letter in the puzzle
							puzzle.addLocation(i, j);				// Adds the current location to the list twice
							phraseFound = true;						// Sets the found phrase boolean to true
							break;									// Breaks out of the inner loop
						}
					if (phraseFound)
						break;										// Breaks out of the outer loop if the letter is found
				}
			}
			
			else {											// Otherwise:
				for (int i = 0; i < rowSize; i++) {				// Nested loop searches every space in the 2-dimensional array
					for (int j = 0; j < colSize; j++) {
						searchAllDirections(i, j, 0);			// Sequentially searches each space
						if (phraseFound)
							break;								// Breaks out of the inner loop if the phrase is found
						}
					if (phraseFound)
						break;									// Breaks out of the outer loop if the phrase is found
				}
			}
			
			System.out.print("The phrase \"" + phrase + "\" ");				// Message displaying the phrase searched for
			if (phraseFound) {									// Conditional if the phrase was found
				System.out.println("was found.");				// Completes the last message with "was found"
				
				if (phrase.length() == 1) {					// Conditional if the phrase is a single letter
					System.out.print(phraseTokens.nextToken() + ": ");		// Prints out the next word in the phrase
					System.out.println(puzzle.getFirstLocation());			// Prints out the coordinates of the word
				}
				else {
					int limit = phraseTokens.countTokens();			// Sets the loop limiter based on token count
					
					for (int i = 0; i < limit; i++) {				// Loops for the number of words in the phrase
						String word = phraseTokens.nextToken();		
						System.out.print(word + ": ");				// Prints out a word from the phrase
						for (int j = 0; j < word.length(); j++)			// Loop prints out locations for each letter of the word
							System.out.print(puzzle.getFirstLocation() + " ");
						System.out.println();				// Prints a newline for formatting purposes
					}
				}
				
				puzzle.printPuzzle();			// Prints out the word search puzzle with the found phrase capitalized
			}
			
			else
				System.out.println("was not found.\n");		// Otherwise prints a message stating the phrase was not found
			
		} while (phraseTrimmed.length() != 0);			// Ends the loop if the phrase is empty (redundant)
	}
	
	
	public static void searchAllDirections(int row, int col, int charPos) {
		
		if (row >= rowSize)			// Returns if the row number is greater than or equal to the row size
			return;
		
		if (col >= colSize)			// Returns if the column number is greater than or equal to the row size
			return;
		
		if (row < 0)				// Returns if the row number is negative
			return;
		
		if (col < 0)				// Returns if the column number is negative
			return;
		
		if (phraseTrimmed.charAt(charPos) == puzzle.getLetter(row, col)) {			// Conditional if the phrase letter matches the puzzle letter
			puzzle.addLocation(row, col);									// Adds the current location to the list
			puzzle.capitalizeLetter(row, col);								// Capitalizes the current letter in the puzzle
			
			if (charPos == (phraseTrimmed.length() - 1)) {			// Conditional if the end of the phrase is reached
				phraseFound = true;									// Sets the found phrase boolean to true and returns
				return;
			}
			
			searchAllDirections(row, col + 1, charPos + 1);			// Searches to the right of the current space
			if (phraseFound)
				return;												// Returns if the phrase was found
			
			searchAllDirections(row + 1, col, charPos + 1);			// Searches down from the current space
			if (phraseFound)
				return;												// Returns if the phrase was found
			
			searchAllDirections(row, col - 1, charPos + 1);			// Searches to the left of the current space
			if (phraseFound)
				return;												// Returns if the phrase was found
			
			searchAllDirections(row - 1, col, charPos + 1);			// Searches up from the current space
			if (phraseFound)
				return;												// Returns if the phrase was found
			
			puzzle.removeLocation();						// Removes the last location from the list (since the phrase was not found)
			puzzle.uncapitalizeLetter(row, col);			// Uncapitalizes the current puzzle letter (since the phrase was not found)
		}
		return;
	}
}
