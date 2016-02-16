/**
 * @author Brandon S. Hang
 * @version 1.100
 * CS 1632
 * Deliverable 2
 * February 16, 2016
 * 
 * This class functions as a type of linked list specific for Coffee Maker Quest
 * and holds QuestRoom objects as nodes.  This class mostly functions as a
 * container or skeleton structure and the QuestRoom objects it holds contain
 * most of the true functionality of the data structure.
 */

package coffee_hunter_pro;

public class QuestHouse {
	
	private QuestRoom initialRoom;		// The head of the QuestHouse
	private int numberOfRooms;			// The number of rooms of the QuestHouse
	
	
	/**
	 * Creates a new QuestHouse with the initial room set to null and the number of rooms set to zero
	 */
	public QuestHouse() {
		
		initialRoom = null;
		numberOfRooms = 0;
	}
	
	
	/**
	 * Sets the initial room of the QuestHouse
	 * @param room The room to set as the initial QuestRoom
	 */
	public void setInitialRoom(QuestRoom room) {
		
		initialRoom = room;
	}
	
	
	/**
	 * Gets the initial room of the QuestHouse
	 * @return The initial QuestRoom of the object
	 */
	public QuestRoom getInitialRoom() {
		
		return initialRoom;
	}
	
	
	/**
	 * Increments the number of rooms the QuestHouse contains by 1
	 */
	public void incrementNumberOfRooms() {
		
		numberOfRooms++;
	}
	
	
	/**
	 * Returns the current number of rooms the QuestHouse has
	 * @return The number of rooms of the QuestHouse
	 */
	public int getNumberOfRooms() {
		
		return numberOfRooms;
	}
}
