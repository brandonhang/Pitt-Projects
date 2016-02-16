/**
 * @author Brandon S. Hang
 * @version 1.100
 * CS 1632
 * Deliverable 2
 * February 16, 2016
 * 
 * This class contains JUnit tests that test QuestHouse.java.
 */

package coffee_maker_junit_tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.*;
import coffee_hunter_pro.*;

public class QuestHouseTest {
	
	QuestRoom mockRoom = Mockito.mock(QuestRoom.class);			// A mocked QuestRoom
	
	
	// 0================================================================0
	// |                    NEW QUEST BUILDING TESTS                    |
	// 0================================================================0
	
	/**
	 * Tests that a new QuestBuilding object will return null if you try to
	 * get its initial QuestRoom object.
	 */
	@Test
	public void testEmptyInitialRoom() {
		QuestHouse houseOfRooms = new QuestHouse();
		assertNull(houseOfRooms.getInitialRoom());			// Asserts that the initial room is null
	}
	
	
	/**
	 * Tests that a new QuestBuilding object will start with zero rooms.
	 */
	@Test
	public void testZeroStartingRooms() {
		QuestHouse houseOfRooms = new QuestHouse();
		int numRooms = houseOfRooms.getNumberOfRooms();
		assertEquals(0, numRooms);					// Asserts that the initial number of rooms is zero
	}
	
	
	// 0================================================================0
	// |                       INITIAL ROOM TESTS                       |
	// 0================================================================0
	
	/**
	 * Tests that setting the initial room of a QuestBuilding object
	 * with a non-null QuestRoom object will return a non-null
	 * QuestRoom object.  This is not a particularly good test but it
	 * does show that an initial room will be set to something other
	 * than null.
	 */
	@Test
	public void testSetInitialRoom() {
		QuestHouse houseOfRooms = new QuestHouse();
		houseOfRooms.setInitialRoom(mockRoom);		
		assertNotNull(houseOfRooms.getInitialRoom());			// Asserts that the initial room is not null
	}
	
	
	/**
	 * Tests that incrementing the number of rooms by a specific amount
	 * will return the same amount when getNumberOfRooms is called.
	 */
	@Test
	public void testIncrementNumberOfRooms() {
		QuestHouse houseOfRooms = new QuestHouse();
		int numLoops = 5;
		for (int i = 0; i < numLoops; i++) {
			houseOfRooms.incrementNumberOfRooms();
		}
		assertEquals(numLoops, houseOfRooms.getNumberOfRooms());		// Asserts that the number of rooms returned matches the loop amount
	}
	
	
	/**
	 * Tests that the initial QuestRoom returned is the same as the
	 * initial QuestRoom that is set.
	 */
	@Test
	public void testSameInitialRoom() {
		QuestHouse houseOfRooms = new QuestHouse();
		houseOfRooms.setInitialRoom(mockRoom);
		assertSame(mockRoom, houseOfRooms.getInitialRoom());		// Asserts that the QuestRoom objects are the same
	}
}
