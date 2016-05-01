/**
 * @author Brandon S. Hang
 * @version 1.100
 * CS 1501
 * Assignment 3
 * March 21, 2016
 * 
 * This class functions as a node for each level of the CarWayTrie data
 * structure.  Nodes are simply arrays with an extra int variable to
 * store index values for the heap.  Methods of this class access
 * and modify the heap index as well as visit other nodes within the
 * data structure.
 */

public class CarWayTrieNode {
	
	int indexOfVIN;					// Stores the heap index
	CarWayTrieNode[] trieNode;			// An array where other nodes are stored
	
	
	/**
	 * Creates a new node by setting its heap index to -1 and initializing the array
	 */
	public CarWayTrieNode() {
		
		trieNode = new CarWayTrieNode[33];		// 33 is the alphabet size of the VIN
		setHeapIndex(-1);
	}
	
	
	/**
	 * Sets the heap index of a node in the trie
	 * @param index The index of the CarInfo object in its heap
	 */
	public void setHeapIndex(int index) {
		
		indexOfVIN = index;
	}
	
	
	/**
	 * Gets the heap index of a node in the trie
	 * @return The index of a CarInfo object in its heap
	 */
	public int getHeapIndex() {
		
		return indexOfVIN;
	}
	
	
	/**
	 * Visits the index of an array of a node; enables traversal through a trie
	 * @param index The index of the array within the node
	 * @return A node of the next level in the trie
	 */
	public CarWayTrieNode visitTrieIndex(int index) {
		
		return trieNode[index];
	}
	
	
	/**
	 * Creates a new node in the trie
	 * @param index The index of the array within the node
	 */
	public void setTrieNode(int index) {
		
		trieNode[index] = new CarWayTrieNode();
	}
}
