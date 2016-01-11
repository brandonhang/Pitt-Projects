/**
 * @author Brandon S. Hang
 * @version 5.30
 * CS 0445
 * Assignment 3A
 * October 21, 2015
 * 
 * Requires WordSearch.java
 * 
 * This class reads a word search puzzle from a file and prompts
 *   the user to search for single words or phrases within the
 *   puzzle. Phrases can change direction only when an entire
 *   word has been found first. No command-line arguments are to
 *   be entered as all interactivity comes from within the
 *   program. Also has a contingency should the phrase be a
 *   single letter.
 *   
 */

import java.util.Scanner;							// Imports the Scanner class
import java.util.StringTokenizer;					// Imports the StringTokenizer class

public class Assign3A {
	
	private static int rowSize;					// Stores the row size of the word search puzzle
	private static int colSize;					// Stores the column size of the words search puzzle
	private static boolean phraseFound = false;		// Boolean indicating if the phrase has been found; initialized to false
	private static WordSearch puzzle;				// Variable for a WordSearch object
	private static String phrase;					// String holding the phrase to search for
	
	
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
			
			System.out.println("Searching for the phrase \"" + phrase			// Message stating the search process has begun
					+ "\".\n\t(contains " + phraseTokens.countTokens() + " words)\n");		// Also displays the number of words
																							//   in the phrase
			puzzle.resetLocationList();				// Clears the list of coordinates
			puzzle.resetCase();						// Resets the puzzle to all lowercase
			phraseFound = false;					// Resets the phraseFound boolean to false
			
			if (phrase.length() == 1) {							// Conditional if the phrase is a single letter
				for (int i = 0; i < rowSize; i++) {				// Nested loop searches every space in the 2-dimensional array
					for (int j = 0; j < colSize; j++)
						if (phrase.charAt(0) == puzzle.getLetter(i, j)) {		// Conditional if the puzzle letter matches the phrase
							puzzle.capitalizeLetter(i, j);			// Capitalizes the current letter in the puzzle
							puzzle.addLocation(i, j);				// Adds the current location to the list
							phraseFound = true;						// Sets the found phrase boolean to true
							break;									// Breaks out of the inner loop
						}
					if (phraseFound)
						break;										// Breaks out of the outer loop if the letter is found
				}
			}
			
			else {											// Otherwise:
				for (int i = 0; i < rowSize; i++) {				// Nested loop searches every space in the 2-dimensional array
					for (int j = 0; j < colSize; j++)
						if (phrase.charAt(0) == puzzle.getLetter(i, j)) {		// Conditional if the puzzle letter matches the first phrase letter
							puzzle.capitalizeLetter(i, j);			// Capitalizes the current letter in the puzzle
							initialSearch(i, j, 1);					// Calls the intial search in all directions
							if (phraseFound)
								break;								// Breaks out of the inner loop if the phrase search is successful
							puzzle.uncapitalizeLetter(i, j);		// Reverts the letter back to lowercase otherwise
						}
					if (phraseFound)
						break;					// Breaks out of the outer loop if the phrase search is successful
				}
			}
			
			System.out.print("The phrase \"" + phrase + "\" ");				// Message displaying the phrase searched for
			
			if (phraseFound) {									// Conditional if the phrase was found
				System.out.println("was found.");				// Completes the last message with "was found"
				
				if (phrase.length() == 1) {					// Conditional if the phrase is a single letter
					System.out.print(phraseTokens.nextToken() + ": ");		// Prints out the next word in the phrase
					System.out.println(puzzle.getFirstLocation());			// Prints out the coordinates of the word
				}
				else {					// Conditional when the phrase's length is greater than 1
					int limit = phraseTokens.countTokens();			// Sets the loop limiter based on token count
					
					for (int i = 0; i < limit; i++) {		// Loops for the number of words in the phrase
						System.out.print(phraseTokens.nextToken() + ": ");		// Prints out the next word in the phrase
						System.out.println(puzzle.getFirstLocation() + " to "	// Prints out the coordinates of the word
								+ puzzle.getFirstLocation());
					}
				}
				
				puzzle.printPuzzle();			// Prints out the word search puzzle with the found phrase capitalized
			}
			
			else
				System.out.println("was not found.\n");		// Otherwise prints a message stating the phrase was not found
			
		} while (phrase.length() != 0);			// Ends the loop if the phrase is empty (redundant)
	}
	
	
	/**
	 * Searches for a character from the phrase in all cardinal directions. Uses a space in the word search puzzle
	 * as the origin.  Only called when the first letter of the phrase is found.
	 * @param row The row of the origin in the word search puzzle
	 * @param col The column of the origin in the word search puzzle
	 * @param charPos The position of the phrase's character to search for
	 */
	private static void initialSearch(int row, int col, int charPos) {
		
		puzzle.addLocation(row, col);				// Adds the initially found letter's location to the coordinate list
		
		searchDirection('R', row, col, charPos);	// Searches right for the next letter in the phrase
		if (phraseFound)
			return;									// Returns if the phrase was found
		
		searchDirection('D', row, col, charPos);	// Searches down for the next letter in the phrase
		if (phraseFound)
			return;									// Returns if the phrase was found
		
		searchDirection('L', row, col, charPos);	// Searches left for the next letter in the phrase
		if (phraseFound)
			return;									// Returns if the phrase was found
		
		searchDirection('U', row, col, charPos);	// Searches up for the next letter in the phrase
		if (phraseFound)
			return;									// Returns if the phrase was found
		
		puzzle.removeLocation();		// Removes the initial location from the list (since the phrase was not found)
	}
	
	
	/**
	 * Searches for a character from the phrase in all cardinal directions. Uses a space in the word search puzzle
	 * as the origin. Called in all subsequent words within a phrase after the first.
	 * @param row The row of the origin in the word search puzzle
	 * @param col The column of the origin in the word search puzzle
	 * @param charPos The position of the phrase's character to search for
	 */
	private static void searchEverywhere(int row, int col, int charPos) {
		
		puzzle.addLocation(row, col + 1);			// Adds the starting location of the next word to the coordinate list
		searchDirection('R', row, col, charPos);	// Searches right for the next letter in the phrase
		if (phraseFound)
			return;									// Returns if the phrase was found
		puzzle.removeLocation();					// Removes the starting location from the coordinate list (the phrase was not found)
		
		
		puzzle.addLocation(row + 1, col);			// Adds the starting location of the next word to the coordinate list
		searchDirection('D', row, col, charPos);	// Searches down for the next letter in the phrase
		if (phraseFound)
			return;									// Returns if the phrase was found
		puzzle.removeLocation();					// Removes the starting location from the coordinate list (the phrase was not found)
		
		
		puzzle.addLocation(row, col - 1);			// Adds the starting location of the next word to the coordinate list
		searchDirection('L', row, col, charPos);	// Searches left for the next letter in the phrase
		if (phraseFound)
			return;									// Returns if the phrase was found
		puzzle.removeLocation();					// Removes the starting location from the coordinate list (the phrase was not found)
		
		
		puzzle.addLocation(row - 1, col);			// Adds the starting location of the next word to the coordinate list
		searchDirection('U', row, col, charPos);	// Searches up for the next letter in the phrase
		if (phraseFound)
			return;									// Returns if the phrase was found
		puzzle.removeLocation();					// Removes the starting location from the coordinate list (the phrase was not found)
	}
	
	
	/**
	 * Searches for a character from the phrase in a specified direction. Calls itself if there are more letters in the
	 * word, calls searchEverywhere if the end of the word is reached, or returns that the phrase was found.
	 * @param direction A character denoting which cardinal direction to search
	 * @param row The row of the last character found
	 * @param col The column of the last character found
	 * @param charPos The position of the phrase's character to search for
	 */
	private static void searchDirection(char direction, int row, int col, int charPos) {
		
		switch (direction) {		// Switch statement regarding the direction's character
			case 'R':				// Case is 'R'
				col++;				// Increments the column number (search right)
				break;
			case 'D':				// Case is 'D'
				row++;				// Increments the row number (search down)
				break;
			case 'L':				// Case is 'L'
				col--;				// Decrements the column number (search left)
				break;
			default:				// Default case ('U')
				row--;				// Decrements the row number (search up)
				break;
		}
		
		if (row >= rowSize)			// Returns if the row number is greater than or equal to the row size
			return;
		
		if (col >= colSize)			// Returns if the column number is greater than or equal to the row size
			return;
		
		if (row < 0)				// Returns if the row number is negative
			return;
		
		if (col < 0)				// Returns if the column number is negative
			return;
		
		if (phrase.charAt(charPos) == puzzle.getLetter(row, col)) {			// Conditional if the phrase letter matches the puzzle letter
			puzzle.capitalizeLetter(row, col);								// Capitalizes the last character in the phrase
			
			if (charPos == (phrase.length() - 1)) {							// Conditional if the character position is one less than the phrase length
				puzzle.addLocation(row,  col);								// Adds the final character's position to the coordinate list
				phraseFound = true;											// Changes the phraseFound boolean to true
				return;														// Starts the chain of returns back to the original call
			}
			
			if (phrase.charAt(charPos + 1) == ' ') {						// Conditional if the next character in the phrase is a space
				puzzle.addLocation(row, col);								// Adds the current location to the coordinate list
				searchEverywhere(row, col, charPos + 2);					// Searches for the next letter after the space in all cardinal directions
				if (phraseFound)
					return;													// Returns if the phrase is found
				puzzle.uncapitalizeLetter(row, col);						// Otherwise reverts the letter back to lowercase
				puzzle.removeLocation();									// Also removes last location if phrase is not found
			}
																		// Else:
			searchDirection(direction, row, col, charPos + 1);				// Continues searching in the same direction for the next letter
			if (phraseFound)
				return;														// Returns if the phrase is found
			puzzle.uncapitalizeLetter(row, col);							// Otherwise reverts the letter back to lowercase
		}
	}
}
