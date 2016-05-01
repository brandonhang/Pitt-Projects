/**
 * @author Brandon S. Hang
 * @version 1.100
 * CS 1501
 * Assignment 4
 * April 7, 2016
 * 
 * This class functions as a simple data structure to use for Dijkstra's
 * algorithm of finding the shortest weighted path in a graph.  It holds
 * indices representing the node's place in a tentative distance array
 * and where the node was traversed from previously.  It also holds the
 * tentative distance calculated thus far in the algorithm.
 * 
 */

public class DijkstraNode {
	
	private int index, previous;			// Indices used in the tentative distance array
	private double distance;				// The tentative distance
	
	
	/**
	 * Creates a new node with the tentative distance set to the maximum value of a double
	 * @param idx The node's index in the tentative distance array
	 */
	public DijkstraNode(int idx) {
		
		setIndex(idx);
		setDistance(Double.POSITIVE_INFINITY);
		setPrevious(-1);				// Initializes the previous value to -1 (I.e., not having a previous index)
	}
	
	
	/**
	 * Sets the tentative distance of the node
	 * @param dst The tentative distance
	 */
	public void setDistance(double dst) {
		
		distance = dst;
	}
	
	
	/**
	 * Sets where the node was previously linked to as a path
	 * @param prev The previous index
	 */
	public void setPrevious(int prev) {
		
		previous = prev;
	}
	
	
	/**
	 * Sets the index of the node in the tentative distance array
	 * @param idx The array index
	 */
	private void setIndex(int idx) {
		
		index = idx;
	}
	
	
	/**
	 * Gets the tentative distance of the node
	 * @return The tentative distance
	 */
	public double getDistance() {
		
		return distance;
	}
	
	
	/**
	 * Gets the previous index where the node was linked to
	 * @return The previous index
	 */
	public int getPrevious() {
		
		return previous;
	}
	
	
	/**
	 * Gets the index of the node in the tentative distance array
	 * @return The array index
	 */
	public int getIndex() {
		
		return index;
	}
}
