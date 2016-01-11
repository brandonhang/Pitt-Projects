/**
 * @author Brandon S. Hang
 * @version 1.00
 * CS 0445
 * Assignment 2
 * October 14, 2015
 * 
 * This class is a representation of a crate of produce
 *   and is to be used with Assign2.java.  It contains
 *   data for an expiration date (as an integer), the
 *   initial and current number of produce, and the cost
 *   of the crate.
 *   
 */

import java.util.StringTokenizer;						// Imports the StringTokenizer class

public class Crate implements Comparable<Crate> {

	private int expiration, count;					// Integers holding the expiration date and current count
	private final int initial;						// Integer holding the initial count; it is final once set
	private double cost;							// Double holding the cost of the crate
	
	
	/**
	 * Creates a crate object from a string read from a text file. Holds data for an expiration date, initial and
	 * final counts, and the crate cost.
	 * @param line A string representation of a line from a text file
	 */
	public Crate(String line) {
		
		StringTokenizer string = new StringTokenizer(line);		// Tokenizes the string using whitespace as a delimiter
		
		expiration = Integer.parseInt(string.nextToken());		// Parses the first token as an integer for the expiration date
		count = Integer.parseInt(string.nextToken());			// Parses the second token as an integer for the current count
		cost = Double.parseDouble(string.nextToken());			// Parses the last token as a double for the crate cost
		initial = count;										// Sets the (final) initial count to the current count
	}
	
	
	/**
	 * Sets the expiration date of the crate.
	 * @param exp An integer representing the expiration date
	 */
	public void setExpiration(int exp) {
		
		expiration = exp;				// Sets the crate's expiration date
	}
	
	
	/**
	 * Sets the current count of the crate.
	 * @param ct An integer representing the current count
	 */
	public void setCount(int ct) {
		
		count = ct;						// Sets the crate's current count
	}
	
	
	/**
	 * Sets the cost of the crate.
	 * @param price A double representing the crate cost
	 */
	public void setCost(int price) {
		
		cost = price;					// Sets the crate's cost
	}
	
	
	/**
	 * Gets the expiration date of the crate.
	 * @return An integer representing the expiration date
	 */
	public int getExpiration() {
		
		return expiration;				// Returns the crate's expiration date
	}
	
	
	/**
	 * Gets the initial count of the crate.
	 * @return An integer representing the initial count
	 */
	public int getInitial() {
		
		return initial;					// Returns the crate's initial count
	}
	
	
	/**
	 * Gets the current count of the crate.
	 * @return An integer representing the current count
	 */
	public int getCount() {
		
		return count;					// Returns the crate's current count
	}
	
	
	/**
	 * Gets the cost of the crate
	 * @return A double representing the crate cost
	 */
	public double getCost() {
		
		return cost;					// Returns the crate's cost
	}
	
	
	/**
	 * Compares the expiration dates between two crate objects.
	 */
	public int compareTo(Crate crate) {
		
		return Integer.compare(this.expiration, crate.getExpiration());			// Returns an integer of the compare result
	}
	
	
	/**
	 * Returns a string containing information about the crate (expiration date, initial and current counts, and cost)
	 */
	public String toString() {
		
		return "Expiration: " + this.getExpiration() + "   Initial Count: "			// Returns as string representing the crate
					+ this.getInitial() + "   Current Count: " + this.getCount()
					+ "   Cost: $" + this.getCost();
	}
}
