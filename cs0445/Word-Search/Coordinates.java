/**
 * @author Brandon S. Hang
 * @version 1.00
 * CS 0445
 * Assignment 3
 * October 21, 2015
 * 
 * Requires Coordinates.java
 * 
 * This class creates a Coordinates object that acts as a container for holding
 *   an x and a y integer. It has accessor and mutator methods as well as a
 *   toString method.
 *   
 */

public class Coordinates {
	
	private int xPosition;			// Holds an integer denoting the x-position
	private int yPosition;			// Holds an integer denoting the y-position

	
	/**
	 * Creates a new Coordinate object using two integers.
	 * @param x The x integer
	 * @param y The y integer
	 */
	public Coordinates(int x, int y) {
		
		xPosition = x;			// Assigns the x integer
		yPosition = y;			// Assigns the y integer
	}
	
	
	/**
	 * Creates a no argument Coordinate object. Values are set to zero by default.
	 */
	public Coordinates() {
		
		xPosition = 0;			// Sets the x integer to zero
		yPosition = 0;			// Sets the y integer to zero
	}
	
	
	/**
	 * Accesses the x integer's value.
	 * @return The x integer
	 */
	public int getX() {
		
		return xPosition;		// Gets the x integer
	}
	
	
	/**
	 * Accesses the y integer's value
	 * @return The y integer
	 */
	public int getY() {
		
		return yPosition;		// Gets the y integer
	}
	
	
	/**
	 * Sets both the x and y values in the Coordinate object.
	 * @param x The x integer
	 * @param y The y integer
	 */
	public void setCoordinates(int x, int y) {
		
		xPosition = x;			// Assigns the x integer
		yPosition = y;			// Assigns the y integer
	}
	
	
	/**
	 * Returns a string representation of the Coordinate object
	 */
	public String toString() {
		
		return ("(" + getX() + ", " + getY() + ")");			// Returns a string representing the Coordinate object
	}
}
