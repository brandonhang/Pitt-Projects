/**
 * @author Brandon S. Hang
 * @version 1.400
 * CS 1501
 * Assignment 3
 * March 21, 2016
 * 
 * This class functions as an R-Way Trie to store array indices for CarInfo
 * objects stored in a CarHeap.java heap.  This trie uses the VIN number as
 * keys to reach the heap index.  Since VIN numbers have a specific
 * character set, each character in the VIN number corresponds to an array
 * index in the R-Way trie.  The trie returns -1 when a specified VIN
 * number is not present in its corresponding heap (I.e., it has been
 * removed).
 */

public class CarWayTrie {
	
	private CarWayTrieNode trieHead;		// The first node in the trie
	
	
	/**
	 * Creates a new trie by setting its initial node
	 */
	public CarWayTrie() {
		
		trieHead = new CarWayTrieNode();
	}
	
	
	/**
	 * Stores an index in the trie using a car's VIN as keys
	 * @param vin The VIN of the car
	 * @param heapIndex The index of the CarInfo object in its heap
	 */
	public void storeHeapIndex(String vin, int heapIndex) {
		
		CarWayTrieNode trieNode = trieHead;					// The starting node of the trie
		
		for (int i = 0; i < vin.length(); i++) {					// For each character of the VIN, navigates through each node of the trie
			int trieIndex = translateCharToInt(vin.charAt(i));
			if (trieNode.visitTrieIndex(trieIndex) == null) {		// If a node does not exist, a new one is created
				trieNode.setTrieNode(trieIndex);
			}
			trieNode = trieNode.visitTrieIndex(trieIndex);
		}
		trieNode.setHeapIndex(heapIndex);					// Stores the index in the last node
	}
	
	
	/**
	 * Retrieves a car's index in the heap by using its VIN as keys
	 * @param vin The VIN of the car
	 * @return The index of the CarInfo object in its heap
	 */
	public int getHeapIndex(String vin) {
		
		CarWayTrieNode trieNode = trieHead;					// The starting node of the trie
		
		for (int i = 0; i < vin.length(); i++) {					// For each character of the VIN, navigates through each node of the trie
			int trieIndex = translateCharToInt(vin.charAt(i));
			if (trieNode.visitTrieIndex(trieIndex) == null) {		// If a node does not exist, the method returns -1 (VIN is not registered)
				return -1;
			}
			trieNode = trieNode.visitTrieIndex(trieIndex);
		}
		return trieNode.getHeapIndex();					// Returns the index of the car in its heap
	}
	
	
	/**
	 * Removes a car from the trie by setting its index to -1
	 * @param vin The VIN of the car
	 */
	public void removeHeapIndex(String vin) {
		
		CarWayTrieNode trieNode = trieHead;					// The starting node of the trie
		
		for (int i = 0; i < vin.length(); i++) {					// For each character of the VIN, navigates through each node of the trie
			int trieIndex = translateCharToInt(vin.charAt(i));
			if (trieNode.visitTrieIndex(trieIndex) == null) {		// If a node does not exist, the method prints a message and returns
				System.out.printf("The entered VIN does not exist!\n");
				return;
			}
			trieNode = trieNode.visitTrieIndex(trieIndex);
		}
		trieNode.setHeapIndex(-1);						// Sets the heap index to -1
	}
	
	
	/**
	 * Transforms a character from the string into an index of the array
	 * @param ch A character of the VIN
	 * @return An index for the array
	 */
	private int translateCharToInt(char ch) {
		
		switch (ch) {
			case '0':
				return 0;
			case '1':
				return 1;
			case '2':
				return 2;
			case '3':
				return 3;
			case '4':
				return 4;
			case '5':
				return 5;
			case '6':
				return 6;
			case '7':
				return 7;
			case '8':
				return 8;
			case '9':
				return 9;
			case 'A':
				return 10;
			case 'B':
				return 11;
			case 'C':
				return 12;
			case 'D':
				return 13;
			case 'E':
				return 14;
			case 'F':
				return 15;
			case 'G':
				return 16;
			case 'H':
				return 17;
			case 'J':
				return 18;
			case 'K':
				return 19;
			case 'L':
				return 20;
			case 'M':
				return 21;
			case 'N':
				return 22;
			case 'P':
				return 23;
			case 'R':
				return 24;
			case 'S':
				return 25;
			case 'T':
				return 26;
			case 'U':
				return 27;
			case 'V':
				return 28;
			case 'W':
				return 29;
			case 'X':
				return 30;
			case 'Y':
				return 31;
			default:			// Character 'Z'
				return 32;
		}
	}
}
