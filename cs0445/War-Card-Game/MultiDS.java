/**
 * @author Brandon Hang
 * @version 2.00
 * CS 0445
 * Assignment 1
 * September 23, 2015
 * 
 * Requires PrimitiveQ.java, Reorder.java
 * 
 * This class acts as a simple data structure
 *   in which data is stored as a queue. The
 *   shuffle method is a pseudo-random method
 *   that is based on Durstenfeld's version
 *   of the Fisher-Yates shuffle. See
 *   https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle#Modern_method
 *   for more information.
 *   
 */

import java.util.Random;							// Imports the Random class

public class MultiDS<T> implements PrimitiveQ<T>, Reorder {

	private T[] queueArray;							// Array that functions as a simple queue
	private int numItems;							// Holds the current number of items in the array
	
	/**
	 * Creates a new, empty array of specified size and sets its number of items to zero.
	 * @param arraySize The desired size of the array
	 * 
	 */
	@SuppressWarnings("unchecked")
	public MultiDS(int arraySize) {
		
		if (arraySize < 1)																// Throws an exception if the
			throw new IllegalArgumentException("Array length must be greater than 1");		// array length is less than 1
		
		queueArray = (T[])new Object[arraySize];					// Creates a new array of specified length
		numItems = 0;												// Sets the number of items to zero
	}
	
	
	public boolean addItem(T item) {
		
		if (queueArray == null)												// Throws an exception if the array is
			throw new IllegalStateException("Array not initialized");		//   not initialized
		
		if (numItems == queueArray.length)					// Returns false if the array is full (failed addition)
			return false;
		
		queueArray[numItems] = item;						// Stores the item in the appropriate array location
		numItems++;											// Increments the number of items by one
		return true;										// Returns true (successfull addition)
	}
	
	
	public T removeItem() {
		
		if (queueArray == null)												// Throws an exception if the array is
			throw new IllegalStateException("Array not initialized");		//   not initialized
		
		if (numItems == 0)									// Returns null if the number of items is zero
			return null;
		
		T itemRemoved = queueArray[0];						// Variable that stores the first (oldest) array entry
		for (int i = 0; i < numItems - 1; i++)				// Loop that shifts the array items to the left to close
			queueArray[i] = queueArray[i + 1];				//   gaps in the array
		
		numItems--;											// Decrements the number of items by one
		
		queueArray[numItems] = null;						// Sets the old position of the last array item to null
		
		return itemRemoved;									// Returns the item that was removed from the array
	}
	
	
	public boolean full() {
		
		if (queueArray == null)												// Throws an exception if the array is
			throw new IllegalStateException("Array not initialized");		//   not initialized
		
		if (numItems == queueArray.length)						// Returns true if the number of items equals the number
			return true;										//   of array spaces
		
		else return false;										// Returns false otherwise
	}
	
	
	public boolean empty() {
		
		if (queueArray == null)												// Throws an exception if the array is
			throw new IllegalStateException("Array not initialized");		//   not initialized
		
		if (numItems == 0)										// Returns true if the number of items is zero
			return true;
		
		else return false;										// Returns false otherwise
	}
	
	
	public int size() {
		
		if (queueArray == null)												// Throws an exception if the array is
			throw new IllegalStateException("Array not initialized");		//   not initialized
		
		return numItems;										// Returns the number of items
	}
	
	
	@SuppressWarnings("unchecked")
	public void clear() {
		
		if (queueArray == null)												// Throws an exception if the array is
			throw new IllegalStateException("Array not initialized");		//   not initialized
		
		queueArray = (T[])new Object[queueArray.length];				// Clears the array by referring it to a new empty one
		
		numItems = 0;													// Resets the number of items to zero
	}
	
	
	@SuppressWarnings("unchecked")
	public void reverse() {
		
		if (queueArray == null)												// Throws an exception if the array is
			throw new IllegalStateException("Array not initialized");		//   not initialized
		
		T[] tempArray = (T[])new Object[queueArray.length];				// Creates a new array to temporarily store items
		int reverseCounter = queueArray.length - 1;						// Stores an integer denoting the last index of the array
		
		for (int i = 0; i < queueArray.length; i++) {					// Loop that Stores the items of the queue array into the
			tempArray[reverseCounter] = queueArray[i];					//   temporary array in reverse order
			reverseCounter--;											// Decrements the reverse counter (countdown)
		}
		for (int i = 0; i < queueArray.length; i++)						// Loop that places the (now reversed) items back into
			queueArray[i] = tempArray[i];								//   the array
		
		if (numItems != queueArray.length) {							// Shifts the items to the left if the array is not full
			for (int i = 0; i < numItems; i++)										// Loop that shifts items to the beginning
				queueArray[i] = queueArray[i + (queueArray.length - numItems)];		//   of the array
			for (int i = numItems; i < queueArray.length; i++)			// Loop that replaces old unused positions with null
				queueArray[i] = null;
		}
	}
	
	
	public void shiftRight() {
		
		if (queueArray == null)												// Throws an exception if the array is
			throw new IllegalStateException("Array not initialized");		//   not initialized
		
		T storedItem = queueArray[numItems - 1];					// Temporarily stores the last item in the array
		for (int i = numItems - 1; i > 0; i--)						// Loop that shifts items in the array to the right
			queueArray[i] = queueArray[i - 1];
		queueArray[0] = storedItem;									// Reinserts the stored item in the first spot of the array
	}
	
	
	public void shiftLeft() {
		
		if (queueArray == null)												// Throws an exception if the array is
			throw new IllegalStateException("Array not initialized");		//   not initialized
		
		T storedItem = queueArray[0];								// Temporarily stores the first item in the array
		for (int i = 0; i < numItems - 1; i++)						// Loop that shifts items in the array to the left
			queueArray[i] = queueArray[i + 1];
		queueArray[numItems - 1] = storedItem;						// Reinserts the stored item as the last item in the array
	}
	
	
	public void shuffle() {
		
		if (queueArray == null)												// Throws an exception if the array is
			throw new IllegalStateException("Array not initialized");		//   not initialized
		
		T storedItem;											// Stores a generic item of type T
		int randomNumber;										// Stores a randomly generated integer (as an array index)
		Random randomNumGen = new Random();						// Creates a new Random object

		for (int i = numItems - 1; i > 0; i--) {				// Durstenfeld version of the Fisher-Yates shuffle
			randomNumber = randomNumGen.nextInt(i);				// Generates a random number from 0 to i
			storedItem = queueArray[i];							// Temporarily stores the item in the array at i
			queueArray[i] = queueArray[randomNumber];			// Replaces the item at i with the item at the random number
			queueArray[randomNumber] = storedItem;				// Reinserts the stored item at the random number
			}
	}
	
	
	/**
	 * Returns a string representing the contents of the array in the form "Contents: x, y, z ...".
	 * The string will not print out null objects in the array.
	 * 
	 */
	public String toString() {
		
		if (queueArray == null)												// Throws an exception if the array is
			throw new IllegalStateException("Array not initialized");		//   not initialized
		
		String contents = "Contents: \n";							// Heading for the string to be returned
		
		for (int i = 0; i < queueArray.length; i++)					// Loop to create a string of the array contents
			if (queueArray[i] != null)								// Ignores null values when creating the string
				contents = contents.concat(queueArray[i] + " ");	// Concatenates the values together with spaces
		
		return contents;											// Returns the contents as a string
	}
}
