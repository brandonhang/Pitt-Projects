/**
 * @author Brandon S. Hang
 * @version 1.00
 * CS 0445
 * Assignment 2
 * October 14, 2015
 * 
 * Requires Crate.java, DisplayableStack.java, LinkedStack.java, StackInterface.java
 * 
 * This class simulates a store selling and receiving
 *   produce using crates from a stack ADT. The class
 *   is read from the command-line and reads a text
 *   file containing commands and crate information.
 *   
 */

import java.util.Scanner;			// Imports the Scanner class
import java.io.*;					// Imports classes needed for input and output

public class Assign2 {

	private static int numMoves, lastNumMoves, totalCrates, lastNumCrates, day = 0;		// Static ints for day and recent/total crates & crate moves
	private static double lastShipCost, totalProduceCost = 0;		// Static double for recent shipment and total produce cost
	private static Scanner contents;							// Static Scanner variable for reading text file contents
	private static Crate displayCrate;							// Static crate object used as the "display crate"
	private static DisplayableStack<Crate> storeStack = new DisplayableStack<Crate>();		// Static store stack
	private static LinkedStack<Crate> tempStack = new LinkedStack<Crate>();					// Static temporary stack
	
	public static void main(String[] args) throws IOException {
		
		try {
			File file = getFile(args);			// Calls getFile and creates a new File object using the command-line argument
			contents = new Scanner(file);		// Creates a new Scanner using the just created File object
		}
		
		catch (FileNotFoundException e) {		// Catches a FileNotFoundException
			System.out.println("The requested file \"" + args[0] + "\" was not "	// Message displayed if the
					+ "found. Please check your input and try again.");				//   file entered was not found
			System.exit(1);						// Terminates program from an anticipated error
		}
		
		System.out.println("Opening file \"" + args[0] + "\"\n");	// Message displaying the file being opened
		
		System.out.println("---Begin Simulation---\n");			// Announces starting of the store simulation
		
		while (contents.hasNextLine()) {						// Loops while the text file has another line
			String nextLine = contents.nextLine();				// Stores the next line in the file as a string
			
			switch (nextLine.toLowerCase()) {	// Converts string to all lower case as a precaution for file errors
				case "receive":					// Case if the string matches "receive"
					receiveProduce(Integer.parseInt(contents.nextLine()));	// Parses the next line for an integer
					break;													//   and passes it to receiveProduce
				case "use":						// (Antiquated) Case if the string matches "use"
					sellProduce(Integer.parseInt(contents.nextLine()));		// Parses the next line for an integer
					break;													//   and passes it to sellProduce
				case "sell":					// Case if the string matches "sell"
					sellProduce(Integer.parseInt(contents.nextLine()));		// Parses the next line for an integer
				break;														//   and passes it to sellProduce
				case "display":					// Case if the string matches "display"
					displayProduce();			// Calls displayProduce
					break;
				case "skip":					// Case if the string matches "skip"
					advanceDay();				// Calls advanceDay
					break;
				case "report":					// Case if the string matches "report"
					reportExpenses();			// Calls reportExpenses
					break;
				default:						// Throws an exception if the string does not match any case
					throw new IllegalArgumentException("\"" + nextLine + "\" is not a valid command. "
							+ "Please check the contents of the file and try again.");
			}	
		}
		
		contents.close();				// Closes the file once finished
		
		System.out.println("---End of Simulation---");		// Message announcing simulation end
		
		System.exit(0);			// Exits program
	}
	
	
	/**
	 * Fills the store stack with the specified number of crates from the file. Also keeps track of recent and total number
	 * of moves, crates, and shipment cost.
	 * @param numCrates The number of crates to add
	 */
	private static void receiveProduce(int numCrates) {

		lastShipCost = 0;				// Resets the most recent shipment cost to $0
		lastNumMoves = 0;				// Resets the most number of labor moves to 0
		
		System.out.println("Receiving " + numCrates + " crates of dragon fruit.\n");	// Message for number of creates being received
		
		for (int i = 0; i < numCrates; i++) {					// Loops for the number of crates received
			Crate crate = new Crate(contents.nextLine());		// Creates a new crate object from the next line in the text file
			lastShipCost += crate.getCost();					// Adds the crate's cost to the most recent shipment cost
			organizeStack(crate);								// Calls organizeStack to sort the new crate
			}
		while (!tempStack.isEmpty()) {				// Only runs if the temporary stack holds a crate(s)
			storeStack.push(tempStack.pop());		// Pushes all crates from the temporary stack into the store stack
			lastNumMoves++;							// Increments the most recent number of labor moves by 1
		}
		numMoves += lastNumMoves;				// Adds the most recent number of labor moves to the total number of moves
		lastNumCrates = numCrates;				// Sets the most recent number of crates to the method argument number of crates
		totalCrates += numCrates;				// Adds the most recent number of crates to the total number of crates
		totalProduceCost += lastShipCost;
	}
	
	
	/**
	 * Sells produce from the display crate. If the display crate does not have enough produce, it will be replaced
	 * by a new display crate from the top of the store stack. If the store runs out of produce (no display crate
	 * and the store stack is empty), will return a message indicating so.
	 * @param quantity The number of produce to sell
	 */
	private static void sellProduce(int quantity) {
		
		System.out.println(quantity + " dragon fruit needed for this order.");			// Message for number of produce to sell
		
		while (quantity != 0) {												// Loops while the quantity to sell is not zero
			if ((displayCrate == null) && !storeStack.isEmpty()) {			// Conditional if there isn't a display crate and the store stack is not empty
				displayCrate = storeStack.pop();							// Pops the top crate from the store stack as a standalone display crate
				System.out.println("Getting a display crate from the stack...\n\t"		// Message indicating getting a display
						+ displayCrate);												//   crate
			}
			
			if ((displayCrate == null) && storeStack.isEmpty()) {			// Conditional if there isn't a display crate and the store stack is empty
				System.out.println("We can't fill this order because"		// Message indicating failure to sell the specified
						+ " we've completely run out of dragon fruit!\n");	//   number of produce
				return;														// Void return from the method
			}
			
			if (quantity >= displayCrate.getCount()) {			// Conditional if the number to sell is greater than or equal to the crate's current count
				System.out.println(displayCrate.getCount()		// Message indicating the number of produce that was sold from the crate
						+ " dragon fruit sold from the display crate.\n");
				quantity -= displayCrate.getCount();			// Subtracts the current crate count from the number to sell
				displayCrate = null;					// Sets the display crate to null (i.e. removes it since it is now empty)
			}
			
			else {
				System.out.println(quantity	+ " dragon fruit sold from"		// Message indicating the number of produce sold
						+ " the display crate.\n");							//   from the display crate
				displayCrate.setCount(displayCrate.getCount() - quantity);	// Subtracts the produce sold from the crate's current count
				quantity = 0;					//  Sets the number of produce to sell to 0 (breaks the loop)
			}
		}
	}
	
	
	/**
	 * Displays a formatted text readout of the display crate (if applicable) and the store stack contents. If there
	 * are no crates in the stack, the message will indicate as such.
	 */
	private static void displayProduce() {
		
		if (displayCrate != null)			// Conditional if there is a display crate
			System.out.println("Display Crate:\n" + displayCrate);		// Displays the contents of the display crate
		if (storeStack.isEmpty())			// Conditional if the store stack is empty
			System.out.println("There's no crates in the stack... order some more!");	// Message indicating the store stack is empty
		else
			System.out.println("Stack Inventory (top to bottom):\n" + storeStack);		// Displays the contents of the store stack
	}
	
	
	/**
	 * Displays a formatted text readout of the recent shipment expenses and the overall expenses. Lists the number
	 * of crates, produce cost, labor amount, and labor cost.
	 */
	private static void reportExpenses() {
		
		System.out.println("~~~Hang's Market of Exotic Fruits Financial Statement~~~\n"		// Formatted text readout of recent expenses
				+ "\tMost Recent Shipment\n"
				+ "\t\tCrates: " + lastNumCrates + "\n"				// Shows most recent number of crates
				+ "\t\tProduce Cost: $" + lastShipCost + "\n"			// Shows most recent produce cost
				+ "\t\tLabor Amount: " + lastNumMoves + "\n"			// Shows most recent number of moves
				+ "\t\tLabor Cost: $" + (double)lastNumMoves + "\n"		// Shows (typecasted) most recent labor cost
				+ "\t\t------------------------\n"					// Divider
				+ "\t\tTotal: $" + (lastShipCost + (double)lastNumMoves) + "\n");	// Total cost of most recent shipment
		System.out.println("\tOverall Expenses\n"					// Formatted text readout of total expenses
				+ "\t\tCrates: " + totalCrates + "\n"				// Shows total number of crates received
				+ "\t\tProduce Cost: $" + totalProduceCost + "\n"		// Shows total cost of produce received
				+ "\t\tLabor Amount: " + numMoves + "\n"			// Shows total number of labor moves
				+ "\t\tLabor Cost: $" + (double)numMoves + "\n"			// Shows total cost of labor
				+ "\t\t------------------------\n"				// Divider
				+ "\t\tTotal: $" + (totalProduceCost + (double)numMoves) + "\n");	// Total cost of all received shipments
	}
	

	/**
	 * Advances to the next day in the simulation. Any crates that have an expiration date less than the day will be
	 * thrown out.
	 */
	private static void advanceDay() {
		
		day++;			// Increments the day counter
		
		System.out.println("Time goes on.  It is now Day " + day + ".\n");		// Message displaying what day it is
		
		if (displayCrate != null) {						// Conditional if there currently is a display crate
			if (displayCrate.getExpiration() < day) {		// Conditional if the crate's expiration date is less than the current day
				System.out.println("The display crate (" + displayCrate + ") has expired!"		// Message indicating that the crate
						+ "  Throwing it out...");												//   is being thrown out
				displayCrate = null;				// Sets the display crate to null (effectively "throws out" current crate)
			}
		}
		
		while (!storeStack.isEmpty() && (storeStack.peek().getExpiration() < day)) {		// Loops while the store stack is not empty and the top
			System.out.println("The top crate (" + storeStack.pop() + ") has expired!"		//   crate's expiration date is less than the current day
					+ "  Throwing it out...");						// Message indicating the top crate of the stack is being thrown out
		}
		System.out.println();			// Prints a newline for formatting purposes
	}
	
	
	/**
	 * Puts the received crate into the proper place in the store stack (or temporary stack) using only
	 * the minimum number of necessary moves. Is called by the receiveProduce method when receiving new
	 * shipments of produce.
	 * @param crate The crate to be organized in the stack
	 */
	private static void organizeStack(Crate crate) {
		
		if (storeStack.isEmpty()) {			// Conditional if the store stack is empty
			if (tempStack.isEmpty()) {		// Conditional if the temporary stack is empty
				storeStack.push(crate);		// Pushes the crate to the store stack
				lastNumMoves++;				// Increments the recent number of moves
			}
			else if (crate.compareTo(tempStack.peek()) == 0) {	// Conditional if the crate's exp. date equals that of the store stack's
				storeStack.push(tempStack.pop());		// Pushes the crate from the temporary stack into the store stack
				lastNumMoves++;						// Increments the recent number of moves
				organizeStack(crate);				// Recursively calls the organizeStack method with the same crate
			}
			else {
				storeStack.push(crate);				// Pushes the crate to the store stack
				lastNumMoves++;						// Increments the recent number of moves
			}
		}
		else if (crate.compareTo(storeStack.peek()) < 1) {		// Conditional if the crate's exp. date is less than or equal to
			if (tempStack.isEmpty()) {			// Conditional if the temporary stack is empty		// that of the store stack's
				storeStack.push(crate);			// Pushes the crate to the store stack
				lastNumMoves++;					// Increments the recent number of moves
			}
			else if (crate.compareTo(tempStack.peek()) < 1) {	// Conditional if the crate's exp. date is less than or equal to that of the temp. stack's
				storeStack.push(tempStack.pop());		// Pops a crate from the temp. stack and pushes it to the store stack
				lastNumMoves++;					// Increments the recent number of moves
				organizeStack(crate);			// Recursively calls the organizeStack method with the same crate
			}
			else {
				storeStack.push(crate);			// Pushes the crate to the store stack
				lastNumMoves++;					// Increments the recent number of moves
			}
		}
		else {
			tempStack.push(storeStack.pop());		// Pops a crate from the store stack and pushes it to the temp. stack
			lastNumMoves++;						// Increments the recent number of moves
			organizeStack(crate);				// Recursively calls the organizeStack method with the same crate
		}
	}
	
	
	/**
	 * Retrieves and sets the file name from the command-line argument. If the .txt extension was left off of
	 * the argument string, this method will concatenate it with the extension.
	 * @param args The command-line argument string array
	 * @return A File object using the appended, if necessary, string from args[0]
	 */
	private static File getFile(String[] args) {
		
		if (args.length == 0)		// Conditional if the array is of size 0 (no command-line argument entered)
			throw new IllegalArgumentException("No file was entered.  Please enter the file name."); // Displays an exception
																		// and requests the user enter a file name
		if (!args[0].toLowerCase().endsWith(".txt"))	// Checks if the filename has a ".txt" extension
			args[0] = args[0].concat(".txt");			// Appends a ".txt" extension to the string if not present
		
		return new File(args[0]);			// Returns a File object using the (appended) command-line string
	}
}
