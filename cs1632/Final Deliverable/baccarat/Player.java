/**
 * @author Brandon S. Hang
 * @version 1.100
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This class is responsible for the baccarat player's information.  It stores the player's
 * record and current balance with their associated methods.
 */

package baccarat;

public class Player {
	
	private int[] record;
	private int balance;
	
	
	/**
	 * Creates a new player with a starting balance of $10,000 and a blank record
	 */
	public Player() {
		
		balance = 10000;
		record = new int[3];
		for (int i = 0; i < 3; i++) {
			record[i] = 0;
		}
	}
	
	
	/**
	 * Gets the balance of the player
	 * @return The amount of money in the player's balance
	 */
	public int checkBalance() {
		
		return balance;
	}
	
	
	/**
	 * Adds to the player's balance
	 * @param funds The amount to add
	 */
	public void addFunds(int funds) {
		
		if (funds < 0) {				// Changes nothing if the funds are negative
			return;
		}
		
		if (funds + balance < 0) {				// Detects mathematical overflow and sets the balance to the maximum int value
			balance = Integer.MAX_VALUE;
		}
		else {
			balance += funds;
		}
	}
	
	
	/**
	 * Subtracts from the player's balance
	 * @param funds The amount to subtract
	 */
	public void subtractFunds(int funds) {
		
		if (funds < 0 || funds > balance) {		// Changes nothing if the funds are negative or exceed the balance
			return;
		}
		else {
			balance -= funds;
		}
	}
	
	
	/**
	 * Adds a win to the player's record
	 */
	public void addWin() {
		
		record[0]++;
	}
	
	
	/**
	 * Adds a loss to the player's record
	 */
	public void addLoss() {
		
		record[1]++;
	}
	
	
	/**
	 * Adds a tie to the player's record
	 */
	public void addTie() {
		
		record[2]++;
	}
	
	
	/**
	 * Returns a string representation of the player's record
	 */
	public String printRecord() {
		
		return record[0] + "W-" + record[1] + "L-" + record[2] + "T";
	}
}
