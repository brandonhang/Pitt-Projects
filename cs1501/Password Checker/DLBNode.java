/**
 * @author Brandon S. Hang
 * @version 1.100
 * CS 1501
 * Assignment 1
 * February 12, 2016
 * 
 * This class functions as the nodes for a de la Briandais trie.  The node class was designed
 * to be generic as to allow usability in future assignments if desired.  The "nextNode"
 * designation refers to the next node within the linked list whereas the "nextList"
 * designation refers to the head of a linked list that is attached to the node.
 * 
 * @param <S> The key the node will use
 * @param <T> The type of object the node will hold
 */

public class DLBNode<S, T> {
	
	private S key;				// The key of the node
	private T value;			// The value of completed node path
	private DLBNode<S, T> nextNode, nextList, lastNode, lastList;			// Holds references to adjacent nodes
	
	
	/**
	 * Creates a new de la Briandais trie node with all variables set to null.
	 */
	public DLBNode() {
		
		key = null;
		value = null;
		nextNode = null;
		nextList = null;
		lastNode = null;
		lastList = null;
	}
	
	
	/**
	 * Creates a new de la Briandais trie node by copying an existing one
	 * @param node
	 */
	public DLBNode(DLBNode<S, T> node) {
		
		key = node.getKey();
		value = node.getValue();
		nextNode = node.getNextNode();
		nextList = node.getNextList();
		lastNode = node.getLastNode();
		lastList = node.getLastList();
	}
	
	
	/**
	 * Accesses the node's key
	 * @return The key of the node
	 */
	public S getKey() {
		
		return key;
	}
	
	
	/**
	 * Stores key into the node
	 * @param value The key to store in the node
	 */
	public void setKey(S keyValue) {
		
		key = keyValue;
	}
	
	
	/**
	 * Checks if the node currently has any key
	 * @return Returns true if the key is null
	 */
	public boolean hasNoKey() {
		
		return (key == null);
	}
	
	
	/**
	 * Checks to see if the node has a stored value
	 * @return Returns true if the node has a value
	 */
	public boolean hasValue() {
		
		return (value != null);
	}
	
	
	/**
	 * Accesses the node's value
	 * @return The value of the node's end path
	 */
	public T getValue() {
		
		return value;
	}
	
	
	/**
	 * Sets the node's value
	 * @param val The value to be stored
	 */
	public void setValue(T val) {
		
		value = val;
	}
	
	
	/**
	 * Checks to see if the node has a neighboring node within the linked list
	 * @return Returns true if the next node reference is not null
	 */
	public boolean hasNextNode() {
		
		return (nextNode != null);
	}
	
	
	/**
	 * Checks to see if the node has a separate linked list attached to it
	 * @return Returns true if the next list reference is not null
	 */
	public boolean hasNextList() {
		
		return (nextList != null);
	}
	
	
	/**
	 * Checks to see if the node has a previous node attached to it
	 * @return Returns true if the previous node reference is not null
	 */
	public boolean hasLastNode() {
		
		return (lastNode != null);
	}
	
	
	/**
	 * Checks to see if the node has a previous linked list attached to it
	 * @return Returns true if the last list reference is not null
	 */
	public boolean hasLastList() {
		
		return (lastList != null);
	}
	
	/**
	 * Fetches the reference for an adjacent node
	 * @return The reference of the neighboring node
	 */
	public DLBNode<S, T> getNextNode() {
		
		return nextNode;
	}
	
	
	/**
	 * Fetches the reference for an adjacent linked list's root node
	 * @return The reference of the neighboring linked list head
	 */
	public DLBNode<S, T> getNextList() {
		
		return nextList;
	}
	
	
	/**
	 * Fetches the reference for the previous node
	 * @return The reference of the previous node
	 */
	public DLBNode<S, T> getLastNode() {
		
		return lastNode;
	}
	
	
	/**
	 * Fetches the reference for a previous linked list node
	 * @return The reference of a previous linked list node
	 */
	public DLBNode<S, T> getLastList() {
		
		return lastList;
	}
	
	
	/**
	 * Sets the reference for the adjacent node
	 * @param node The node to attach
	 */
	public void setNextNode(DLBNode<S, T> node) {
		
		nextNode = node;
		node.setLastNode(this);
	}
	
	
	/**
	 * Sets the reference for the neighboring linked list
	 * @param list The head of the linked list to attach
	 */
	public void setNextList(DLBNode<S, T> list) {
		
		nextList = list;
		list.setLastList(this);
	}
	
	
	/**
	 * Sets the reference for the previous node
	 * @param node The previous node
	 */
	public void setLastNode(DLBNode<S, T> node) {
		
		lastNode = node;
	}
	
	
	/**
	 * Sets the reference for the previous linked list
	 * @param list A node of the previous linked list
	 */
	public void setLastList(DLBNode<S, T> list) {
		
		lastList = list;
	}
}
