/**
 * @author Brandon S. Hang
 * @version 1.00
 * CS 0445
 * Assignment 3
 * October 21, 2015
 * 
 * Requires Coordinates.java
 * 
 * This class creates a WordSearch object that holds the word search
 *   from a file in a 2-dimensional array. Also contains a linked
 *   list for storing Coordinate objects. Contains accessors and
 *   mutators for both sets of data.
 *   
 */

import java.io.*;					// Imports classes needed for input and output
import java.util.Scanner;			// Imports the Scanner class
import java.util.LinkedList;		// Imports the Linked List class

public class WordSearch {

	private static char[][] wordGrid;					// 2-dimensional array holding the word search
	private static LinkedList<Coordinates> locationList = new LinkedList<Coordinates>();	// Creates a new linked list of Coordinates
	private static int rowSize;
	private static int colSize;
	
	/**
	 * Creates a new WordSearch object from a properly formatted file
	 * @param filename The file containing the word search
	 */
	public WordSearch(String filename) {
		
		buildWordSearchGrid(getFile(filename));			// Calls the buildWordSearchGrid function using the file returned from getFile
	}
	
	
	/**
	 * Adds an ordered pair to the location list.
	 * @param row The row integer to be added
	 * @param col The column integer to be added
	 */
	public void addLocation(int row, int col) {
		
		locationList.add(new Coordinates(row, col));		// Adds a new coordinate to the location list
	}
	
	
	/**
	 * Adds a Coordinate object to the location list.
	 * @param location The Coordinate object to be added
	 */
	public void addLocation(Coordinates location) {
		
		locationList.add(location);				// Adds an existing coordinate to the location list
	}
	
	
	/**
	 * Removes the last location from the coordinate list.
	 */
	public void removeLocation() {
		
		locationList.removeLast();				// Removes the last location from the list
	}
	
	
	/**
	 * From a given location, returns the character stored in the 2-dimensional array.
	 * @param row The row of the location in the 2-dimensional array
	 * @param col The column of the location in the 2-dimensional array
	 * @return The character stored in the location specified
	 */
	public char getLetter(int row, int col) {
		
		return wordGrid[row][col];				// Returns a character from the 2-dimensional array
	}
	
	
	/**
	 * Capitalizes a letter at a given location in the 2-dimensional array.
	 * @param row The row of the location in the 2-dimensional array
	 * @param col The column of the location in the 2-dimensional array
	 */
	public void capitalizeLetter(int row, int col) {
		
		wordGrid[row][col] = Character.toUpperCase(wordGrid[row][col]);			// Capitalizes the character stored in the location
	}
	
	
	/**
	 * Changes a character in the 2-dimensional array to lowercase.
	 * @param row The row of the location in the 2-dimensional array
	 * @param col The column of the location in the 2-dimensional array
	 */
	public void uncapitalizeLetter(int row, int col) {
		
		wordGrid[row][col] = Character.toLowerCase(wordGrid[row][col]);			// Changes the character stored in the location to lowercase
	}
	
	
	/**
	 * Changes all characters in the 2-dimensional array to lowercase.
	 */
	public void resetCase() {
		
		for (int i = 0; i < wordGrid.length; i++)					// Nested loop goes through every position in the 2-dimensional array
			for (int j = 0; j < wordGrid[0].length; j++)
				uncapitalizeLetter(i, j);							// Calls uncapitalizeLetter function for each location
	}
	
	
	/**
	 * Prints out the current state of the word search puzzle. This may include capitalized phrases that were
	 * found in previous searches.
	 */
	public void printPuzzle() {
		
		for (int i = 0; i < rowSize; i++) {				// Nested loop goes through every location in the 2-dimensional array
			for (int j = 0; j < colSize; j++)
				System.out.print(wordGrid[i][j] + " ");			// Prints out the character stored in the location
			System.out.println("");								// Prints out a newline after every finished row
		}
		System.out.println();					// Prints a newline for formatting purposes
	}
	
	
	/**
	 * Removes the first item in the location list and returns it.
	 * @return The first Coordinate object in the list
	 */
	public Coordinates getFirstLocation() {
		
		return locationList.removeFirst();		// Removes and returns the first Coordinate object
	}
	
	
	/**
	 * Gets the size of the location list.
	 * @return The size of the location list
	 */
	public int listSize() {
		
		return locationList.size();			// Gets the size of the location list
	}
	
	
	/**
	 * Clears the location list of its contents
	 */
	public void resetLocationList() {
		
		locationList.clear();				// Clears the location list of its contents
	}
	
	
	/**
	 * Gets the row size from the file.
	 * @return The integer row size
	 */
	public int getRowSize() {
		
		return rowSize;				// Returns the row size
	}
	
	
	/**
	 * Gets the column size from the file.
	 * @return The integer column size
	 */
	public int getColSize() {
		
		return colSize;				// Returns the column size
	}
	
	
	/**
	 * Transfers the word search puzzle into a 2-dimensional array and creates a new WordSearch object.
	 * @param file The File object to build the WordSearch object from
	 */
	private void buildWordSearchGrid(File file) {
		
		try {
			Scanner scan = new Scanner(file);		// Creates a new Scanner using the just created File object
			String line = "";						// Initializes a string variable
			
			rowSize = scan.nextInt();		// Sets the row size of to the first integer in the file
			colSize = scan.nextInt();		// Sets the column size to the second integer in the file
			
			wordGrid = new char[rowSize][colSize];		// Creates a new 2-dimensional array using the row and column sizes
			
			while(scan.hasNextLine())					// Loops while the file has another line
				line = line.concat(scan.nextLine());	// Creates one long string from the letters in the file
			
			int index = 0;								// Integer index for character locations in the string
			
			for (int i = 0; i < rowSize; i++)			// Nested loop that inserts a character from the string into the 2-dimensional array
				for (int j = 0; j < colSize; j++) {
					wordGrid[i][j] = line.charAt(index);		// Assigns a character from the string to the 2-dimensional array location
					index++;				// Manually increments the string index
				}
			
			printPuzzle();					// Prints the puzzle
		}
		
		catch (FileNotFoundException e) {		// Catches a FileNotFoundException
			System.out.println("The requested file \"" + file + "\" was not "		// Message displayed if the
					+ "found. Please check your input and try again.");				//   file entered was not found
			System.exit(1);						// Terminates program from an anticipated error
		}
	}
	
	
	/**
	 * Retrieves and sets the file name from the command-line argument. If the .txt extension was left off of
	 * the argument string, this method will concatenate it with the extension.
	 * @param args The command-line argument string array
	 * @return A File object using the appended, if necessary, string from args[0]
	 */
	private File getFile(String fileName) {
		
		if (fileName.length() == 0)		// Conditional if the string is of size 0 (no file entered)
			throw new IllegalArgumentException("No file was entered.  Please enter the file name."); // Displays an exception
																		// and requests the user enter a file name
		if (!fileName.toLowerCase().endsWith(".txt"))	// Checks if the filename has a ".txt" extension
			fileName = fileName.concat(".txt");			// Appends a ".txt" extension to the string if not present
		
		return new File(fileName);			// Returns a File object using the (appended) command-line string
	}
}
