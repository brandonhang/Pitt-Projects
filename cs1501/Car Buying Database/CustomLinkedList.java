/**
 * @author Brandon S. Hang
 * @version 1.100
 * CS 1501
 * Assignment 3
 * March 21, 2016
 * 
 * This class functions as a basic doubly-linked list for storing
 * objects.  It contains to modify/access the head of the list and
 * add new objects.  Most functionality lies in the linked list
 * node class.
 */

public class CustomLinkedList<T> {
	
	private CustomNode<T> listHead;			// The linked list head
	
	
	/**
	 * Creates a new, empty linked list
	 */
	public CustomLinkedList() {
		
		setListHead(new CustomNode<T>());			// Sets the list head as a new node with a value of null
	}
	
	
	/**
	 * Creates a new linked list with the list head set to an object
	 * @param obj The object to set as the linked list head
	 */
	public CustomLinkedList(T obj) {
		
		setListHead(new CustomNode<T>(obj));			// Sets the list head as a new node with a value of the object
	}
	
	
	/**
	 * Gets the list head of the linked list
	 * @return
	 */
	public CustomNode<T> getListHead() {
		
		return listHead;
	}
	
	
	/**
	 * Sets the list head to a specified node
	 * @param node A CustomNode object
	 */
	protected void setListHead(CustomNode<T> node) {
		
		listHead = node;
	}
	
	
	/**
	 * Adds an object to the linked list by inserting it into the beginning
	 * @param obj The object to insert
	 */
	public void add(T obj) {
		
		CustomNode<T> oldHead = getListHead();				// Gets the current list head node
		CustomNode<T> newHead = new CustomNode<T>(obj);		// Creates a new node using the object parameter
		setListHead(newHead);							// Sets the list head as the newly created node
		newHead.setNextNode(oldHead);					// Sets the next node of the new list head as the old list head node
		oldHead.setLastNode(newHead);					// Sets the last node of the old list head as the new list head node
	}
	
	
	/**
	 * Returns a string representation of the linked list
	 */
	public String toString() {
		
		CustomNode<T> node = getListHead();				// Gets the linked list head node
		String str = "";						// Creates a new empty string
		
		while (node.hasData()) {
			str += (node.getData().toString() + "\n");		// Concatenates the string with the string representation of the node's data
			node = node.getNextNode();						// Gets the next node in the linked list
		}
		
		return str;
	}
}
