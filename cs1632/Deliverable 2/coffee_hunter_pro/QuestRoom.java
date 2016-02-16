/**
 * @author Brandon S. Hang
 * @version 1.200
 * CS 1632
 * Deliverable 2
 * February 16, 2016
 * 
 * This class functions similarly to a linked list node where the linked list
 * container is a QuestHouse object.  This class contains most of the
 * functionality of the data structure with methods to set and get most
 * internal values.  It also contains methods to add more QuestRoom objects
 * and traverse the data structure as a whole.
 */

package coffee_hunter_pro;

public class QuestRoom {
	
	private String roomAdj;				// Adjective that describes the room
	private String furnishing;			// The unique furnishing
	private String furnishingAdj;		// Adjective that describes the furnishing
	private String northDoor;			// Adjective that describes the north door
	private String southDoor;			// Adjective that describes the south door
	private QuestRoom northRoom;		// The north QuestRoom object
	private QuestRoom southRoom;		// The south QuestRoom object
	private String item;				// The item found in the room
	private boolean containsItem;		// Boolean denotes whether the room contains an item
	
	
	/**
	 * Creates a new QuestRoom object with all of its internal values set to null (or false)
	 */
	public QuestRoom() {
		
		roomAdj = null;
		furnishing = null;
		furnishingAdj = null;
		northDoor = null;
		southDoor = null;
		northRoom = null;
		southRoom = null;
		item = null;
		containsItem = false;
	}
	
	
	/**
	 * Renovates the room by setting its descriptors and adjectives
	 * @param room The adjective describing the room
	 * @param furniture The furnishing of the room
	 * @param furnAdj The adjective describing the furnishing
	 * @param nDoor The adjective describing the north door
	 * @param sDoor The adjective describing the south door
	 */
	public void renovateRoom(String room, String furniture, String furnAdj, String nDoor, String sDoor) {
		
		roomAdj = room;
		furnishing = furniture;
		furnishingAdj = furnAdj;
		northDoor = nDoor;
		southDoor = sDoor;
	}
	
	
	/**
	 * Sets the north QuestRoom reference.  This method extends the QuestHouse object as a whole.
	 * @param room The room to set as the north QuestRoom
	 */
	public void setNorthRoom(QuestRoom room) {
		
		northRoom = room;				// Sets the north QuestRoom reference
		room.setSouthRoom(this);		// Sets the south QuestRoom reference of the north QuestRoom
	}
	
	
	/**
	 * Sets the south QuestRoom reference; only called when setNorthRoom is called.
	 * @param room
	 */
	private void setSouthRoom(QuestRoom room) {
		
		southRoom = room;
	}
	
	
	/**
	 * Sets the item of the room and changes the boolean flag to true
	 * @param ingredient The item to add to the QuestRoom object
	 */
	public void setItem(String ingredient) {
		
		item = ingredient;
		containsItem = true;
	}
	
	
	/**
	 * Checks to see if the QuestRoom object has a north door
	 * @return Returns true if the door descriptor is not null
	 */
	public boolean hasNorthDoor() {
		
		return (northDoor != null);
	}
	
	
	/**
	 * Checks to see if the QuestRoom object has a south door
	 * @return Returns true if the door descriptor is not null
	 */
	public boolean hasSouthDoor() {
		
		return (southDoor != null);
	}
	
	
	/**
	 * Checks to see if the QuestRoom object holds an item
	 * @return Returns true if the boolean containsItem is true
	 */
	public boolean hasItem() {
		
		return (containsItem);
	}
	
	
	/**
	 * Traverses to the north QuestRoom object
	 * @return The north QuestRoom object
	 */
	public QuestRoom enterNorthDoor() {
		
		return northRoom;
	}
	
	
	/**
	 * Traverses to the south QuestRoom object
	 * @return The south QuestRoom object
	 */
	public QuestRoom enterSouthDoor() {
		
		return southRoom;
	}
	
	
	/**
	 * Gets the adjective that describes the room
	 * @return A String describing the room
	 */
	public String getRoomAdj() {
		
		return roomAdj;
	}
	
	
	/**
	 * Gets the furnishing and the adjective of the furnishing in the room
	 * @return A complete furnishing String
	 */
	public String getFurnishing() {
		
		if (furnishing == null || furnishingAdj == null) {
			return null;					// Returns null if either the furnishing or its adjective is null
		}
		else {
			return (furnishingAdj + " " + furnishing);
		}
	}
	
	
	/**
	 * Gets the adjective that describes the north door
	 * @return A String describing the north door
	 */
	public String getNorthDoor() {
		
		return northDoor;
	}
	
	
	/**
	 * Gets the adjective that describes the south door
	 * @return A String describing the south door
	 */
	public String getSouthDoor() {
		
		return southDoor;
	}
	
	
	/**
	 * Gets the item of the room
	 * @return A String of the room's item
	 */
	public String getItem() {
		
		return item;
	}
	
	
	/**
	 * Changes the containsItem boolean to false; used when collecting an item from the room.
	 */
	public void collectItem() {
		
		containsItem = false;
	}
	
	
	/**
	 * Returns a String representation of the room
	 */
	public String toString() {
		
		String roomDescription = "\nYou see a " + getRoomAdj() + " room.\n"
				+ "It has a " + getFurnishing() + ".\n";
		if (hasNorthDoor()) {										// Prints the north door only if it exists
			roomDescription = roomDescription.concat("A " + getNorthDoor() + " door leads North.\n");
		}
		if (hasSouthDoor()) {										// Prints the south door only if it exists
			roomDescription = roomDescription.concat("A " + getSouthDoor() + " door leads South.\n");
		}
		
		return roomDescription;
	}
}
