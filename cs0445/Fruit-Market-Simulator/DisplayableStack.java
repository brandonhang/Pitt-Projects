/**
 * @author Brandon S. Hang
 * @version 1.00
 * CS 0445
 * Assignment 2
 * October 14, 2015
 * 
 * Requires LinkedStack.java, StackInterface.java
 * 
 * This class extends LinkedStack.java with a toString
 *   method. Displays stack contents by popping each
 *   item T into a temporary stack and pushing them
 *   back once printed.
 */

public class DisplayableStack<T> extends LinkedStack<T> {
	
	/**
	 * Returns a string representing the contents of the stack as long as the stack is not empty.
	 *   
	 */
	public String toString() {
		
		String display = "";								// Initializes an empty string representing the stack
		LinkedStack<T> tempStack = new LinkedStack<T>();	// Creates a temporary stack to hold items
		
		while (!this.isEmpty()) {							// Loops as long as the target stack is not empty
			T item = this.pop();							// Pops an item off the stack and stores it
			display = display.concat(item + "\n");			// Adds the string representation of the item to the stack string
			tempStack.push(item);							// Pushes the item into the temporary stack
		}
		
		while (!tempStack.isEmpty())						// Loops as long as the temporary stack is not empty
			this.push(tempStack.pop());						// Pops an item off the temp. stack and pushes it into the target stack
		
		return display;								// Returns the stack string
	}
}
