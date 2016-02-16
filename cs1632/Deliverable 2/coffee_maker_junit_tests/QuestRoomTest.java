/**
 * @author Brandon S. Hang
 * @version 1.700
 * CS 1632
 * Deliverable 2
 * February 16, 2016
 * 
 * This class contains JUnit tests that test QuestRoom.java.
 */

package coffee_maker_junit_tests;

import static org.junit.Assert.*;

import org.junit.Test;
import coffee_hunter_pro.*;

public class QuestRoomTest {
	
	String[] adjArr1 = {"Poorly-lit", "computer", "Pathetic", "Flimsy", "Broken"};		// Used to test specific QuestRoom methods
	
	
	// 0================================================================0
	// |                    NULL/NEW QUEST ROOM TEST                    |
	// 0================================================================0
	
	/**
	 * Tests that a new QuestRoom object will have a null room adjective.
	 */
	@Test
	public void testNewRoomNullAdj() {
		QuestRoom room = new QuestRoom();
		assertNull(room.getRoomAdj());			// Asserts that the room's adjective is null
	}
	
	
	/**
	 * Tests that a new QuestRoom object will have a null furnishing.
	 */
	@Test
	public void testNewRoomNullFurnishing() {
		QuestRoom room = new QuestRoom();
		assertNull(room.getFurnishing());		// Asserts that the full furnishing String is null
	}
	
	
	/**
	 * Tests that a new QuestRoom object will have a null north door.
	 */
	@Test
	public void testNewRoomNullNorthDoor() {
		QuestRoom room = new QuestRoom();
		assertNull(room.getNorthDoor());			// Asserts that the north door is null
	}
	
	
	/**
	 * Tests that a new QuestRoom object will have a null south door.
	 */
	@Test
	public void testNewRoomNullSouthDoor() {
		QuestRoom room = new QuestRoom();
		assertNull(room.getSouthDoor());			// Asserts that the south door is null
	}
	
	
	/**
	 * Tests that a new QuestRoom object will have a null item.
	 */
	@Test
	public void testNewRoomNullItem() {
		QuestRoom room = new QuestRoom();
		assertNull(room.getItem());					// Asserts that the room's item is null
	}
	
	
	/**
	 * Tests that moving to a north room when one is
	 * not set returns in a null QuestRoom object.
	 */
	@Test
	public void testNullNorthRoom() {
		QuestRoom room = new QuestRoom();
		assertNull(room.enterNorthDoor());			// Asserts that the north QuestRoom reference is null
	}
	
	
	/**
	 * Tests that moving to a south room when one is
	 * not set returns in a null QuestRoom object.
	 */
	@Test
	public void testNullSouthRoom() {
		QuestRoom room = new QuestRoom();
		assertNull(room.enterSouthDoor());			// Asserts that the south QuestRoom reference is null
	}
	
	
	// 0================================================================0
	// |                    ROOM DESCRIPTORS TESTS                      |
	// 0================================================================0
    
	/**
	 * Tests returning the room's adjective when one is set.
	 */
	@Test
	public void testRoomAdjective() {
		QuestRoom room = new QuestRoom();
		room.renovateRoom(adjArr1[0], adjArr1[1], adjArr1[2], adjArr1[3], adjArr1[4]);		// Sets the room's descriptors/adjectives
		String adj = room.getRoomAdj();
		assertEquals("Poorly-lit", adj);				// Asserts that the room is "Poorly-lit"
	}
	
	
	/**
	 * Tests returning the room's furnishing when one is set.
	 */
	@Test
	public void testFurnishing() {
		QuestRoom room = new QuestRoom();
		room.renovateRoom(adjArr1[0], adjArr1[1], adjArr1[2], adjArr1[3], adjArr1[4]);
		String furnishing = room.getFurnishing();
		assertEquals("Pathetic computer", furnishing);			// Asserts that the furnishing is a "Pathetic computer" (I.e., my laptop)
	}
	
	
	/**
	 * Tests returning the north door when one is set.
	 */
	@Test
	public void testNorthDoor() {
		QuestRoom room = new QuestRoom();
		room.renovateRoom(adjArr1[0], adjArr1[1], adjArr1[2], adjArr1[3], adjArr1[4]);
		String door = room.getNorthDoor();
		assertEquals("Flimsy", door);				// Asserts that the north door is "Flimsy"
	}
	
	
	/**
	 * Tests returning the south door when one is set.
	 */
	@Test
	public void testSouthDoor() {
		QuestRoom room = new QuestRoom();
		room.renovateRoom(adjArr1[0], adjArr1[1], adjArr1[2], adjArr1[3], adjArr1[4]);
		String door = room.getSouthDoor();
		assertEquals("Broken", door);				// Asserts that the south door is "Broken"
	}
	
	
	/**
	 * Tests returning the item when getItem is called.
	 */
	@Test
	public void testGetItem() {
		QuestRoom room = new QuestRoom();
		room.setItem("Mushy broccoli");
		assertEquals("Mushy broccoli", room.getItem());			// Asserts the the item returned is "Mushy broccoli"
	}
	
	
	// 0================================================================0
	// |                       TEST SET METHODS                         |
	// 0================================================================0
	
	/**
	 * Tests that creating a new QuestRoom object and setting that room
	 * as the north room of an initial QuestRoom object returns the same
	 * room when enterNorthDoor is called on the initial QuestRoom
	 * object.  Ensures that QuestRoom objects are set and linked
	 * correctly.
	 */
	@Test
	public void testSetNorthDoor() {
		QuestRoom firstRoom = new QuestRoom();
		QuestRoom northRoom = new QuestRoom();
		firstRoom.setNorthRoom(northRoom);				// Sets the north QuestRoom of the firstRoom
		assertSame(northRoom, firstRoom.enterNorthDoor());			// Asserts that the returned QuestRoom is the same as the set QuestRoom
	}
	
	
	/**
	 * Tests that setting an item in a room results in the room's item
	 * being something other than null (not a particularly good test).
	 */
	@Test
	public void testSetItem() {
		QuestRoom room = new QuestRoom();
		room.setItem("Soggy tofu");
		assertNotNull(room.getItem());				// Asserts that the item in the room is not null
	}
	
	
	// 0================================================================0
	// |                      ROOM BOOLEAN TESTS                        |
	// 0================================================================0
	
	/**
	 * Tests that a room with no north door will return false when
	 * hasNorthDoor is called.
	 */
	@Test
	public void testNoNorthDoor() {
		QuestRoom room = new QuestRoom();
		assertFalse(room.hasNorthDoor());			// Asserts that the room does not have a north door
	}
	
	
	/**
	 * Tests that a room with no south door will return false when
	 * hasSouthDoor is called.
	 */
	@Test
	public void testNoSouthDoor() {
		QuestRoom room = new QuestRoom();
		assertFalse(room.hasSouthDoor());			// Asserts that the room does not have a south door
	}
	
	
	/**
	 * Tests that a room containing no item will return false when
	 * hasItem is called.
	 */
	@Test
	public void testNoItem() {
		QuestRoom room = new QuestRoom();
		assertFalse(room.hasItem());				// Asserts that the room does not have an item
	}
	
	
	/**
	 * Tests that a room with a north door will return true when
	 * hasNorthDoor is called.
	 */
	@Test
	public void testHasNorthDoor() {
		QuestRoom room = new QuestRoom();
		room.renovateRoom(adjArr1[0], adjArr1[1], adjArr1[2], adjArr1[3], adjArr1[4]);
		assertTrue(room.hasNorthDoor());								// Asserts that the room has a north door
	}
	
	
	/**
	 * Tests that a room with a south door will return true when
	 * hasSouthDoor is called.
	 */
	@Test
	public void testHasSouthDoor() {
		QuestRoom room = new QuestRoom();
		room.renovateRoom(adjArr1[0], adjArr1[1], adjArr1[2], adjArr1[3], adjArr1[4]);
		assertTrue(room.hasSouthDoor());								// Asserts that the room has a south door
	}
	
	
	/**
	 * Tests that a room containing an item will return true when
	 * hasItem is called.
	 */
	@Test
	public void testHasItem() {
		QuestRoom room = new QuestRoom();
		room.setItem("Burnt ramen");
		assertTrue(room.hasItem());					// Asserts that the room contains an item
	}
	
	
	/**
	 * Tests that collecting an item from a room will result in the
	 * room returning false when hasItem is called.  This is the
	 * only JUnit test case with more than one assertion.
	 */
	@Test
	public void testCollectItem() {
		QuestRoom room = new QuestRoom();
		room.setItem("Rancid cabbage");
		assertTrue(room.hasItem());				// First asserts that the room contains an item
		room.collectItem();
		assertFalse(room.hasItem());			// Then asserts that the room no longer contains an item
	}
	
	
	// 0================================================================0
	// |                        TO STRING TEST                          |
	// 0================================================================0
	
	/**
	 * Tests that when a QuestRoom object is printed, it prints a correctly
	 * formatted String.
	 */
	@Test
	public void testToString() {
		QuestRoom room = new QuestRoom();
		room.renovateRoom(adjArr1[0], adjArr1[1], adjArr1[2], adjArr1[3], adjArr1[4]);
		String roomDesc = "\nYou see a Poorly-lit room.\n"
				+ "It has a Pathetic computer.\n"
				+ "A Flimsy door leads North.\n"
				+ "A Broken door leads South.\n";
		assertEquals(roomDesc, room.toString());		// Asserts that a printed QuestRoom matches the roomDesc String
	}
}
