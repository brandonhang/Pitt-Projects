/**
 * @author Brandon S. Hang
 * @version 1.800
 * CS 1632
 * Deliverable 2
 * February 16, 2016
 * 
 * This class contains JUnit tests that test CoffeeGame.java.
 */

package coffee_maker_junit_tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.is;

import org.junit.Test;
import org.mockito.Mockito;
import coffee_hunter_pro.*;

public class CoffeeGameTest {
	
	QuestHouse mockHouse = Mockito.mock(QuestHouse.class);			// A mocked QuestHouse object
	QuestRoom mockRoom = Mockito.mock(QuestRoom.class);				// A mocked QuestRoom object; usually used as the initial room.
	QuestRoom mockNextRoom = Mockito.mock(QuestRoom.class);			// A mocked QuestRoom object; used as an alternate or subsequent room.
	CoffeeGame game = new CoffeeGame();							// Creates a new CoffeeGame object
	
	
	// 0================================================================0
	// |                       MOVE COMMANDS TEST                       |
	// 0================================================================0
	
	/**
	 * Tests that the command of "N" is recognized by the program when
	 * a north QuestRoom object exists.
	 */
	@Test
	public void testValidMoveNorth() {
		Mockito.when(mockRoom.hasNorthDoor()).thenReturn(true);				// Stubbed hasNorthDoor method returns true
		Mockito.when(mockRoom.enterNorthDoor()).thenReturn(mockNextRoom);		// Stubbed enterNorthDoor method returns a mocked QuestRoom
		
		mockRoom = game.parseUserInput("N", mockRoom);
		assertEquals("Going north", game.getLastCommand());				// The lastCommand String should match "Going north"
	}
	
	
	/**
	 * Tests that the command of "N" returns a north QuestRoom object
	 * when one exists.
	 */
	@Test
	public void testValidMoveNorthReturn() {
		Mockito.when(mockRoom.hasNorthDoor()).thenReturn(true);
		Mockito.when(mockRoom.enterNorthDoor()).thenReturn(mockNextRoom);
		
		mockRoom = game.parseUserInput("N", mockRoom);
		assertSame(mockNextRoom, mockRoom);					// Asserts that the returned QuestRoom is the same as the north QuestRoom
	}
	
	
	/**
	 * Tests that the command of "S" is recognized by the program when
	 * a south QuestRoom object exists.
	 */
	@Test
	public void testValidMoveSouth() {
		Mockito.when(mockRoom.hasSouthDoor()).thenReturn(true);				// Stubbed hasSouthDoor method returns true
		Mockito.when(mockRoom.enterSouthDoor()).thenReturn(mockNextRoom);		// Stubbed enterSouthDoor method returns a mocked QuestRoom
		
		mockRoom = game.parseUserInput("S", mockRoom);
		assertEquals("Going south", game.getLastCommand());				// The lastCommand String should match "Going south"
	}
	
	
	/**
	 * Tests that the command of "S" returns a south QuestRoom object
	 * when one exists.
	 */
	@Test
	public void testValidMoveSouthReturn() {
		Mockito.when(mockRoom.hasSouthDoor()).thenReturn(true);
		Mockito.when(mockRoom.enterSouthDoor()).thenReturn(mockNextRoom);
		
		mockRoom = game.parseUserInput("S", mockRoom);
		assertSame(mockNextRoom, mockRoom);					// Asserts that the returned QuestRoom is the same as the south QuestRoom
	}
	
	
	/**
	 * Tests that the command of "N" results in staying in the current
	 * QuestRoom object as no north QuestRoom object exists.  Should
	 * return the same QuestRoom object.
	 */
	@Test
	public void testInvalidMoveNorth() {
		Mockito.when(mockRoom.hasNorthDoor()).thenReturn(false);		// Stubbed hasNorthDoor method returns false
		
		mockNextRoom = game.parseUserInput("N", mockRoom);
		assertEquals(mockNextRoom, mockRoom);				// Asserts that the returned QuestRoom is the same as the original QuestRoom
	}
	
	
	/**
	 * Tests that the command of "S" results in staying in the current
	 * QuestRoom object as no south QuestRoom object exists.  Should
	 * return the same QuestRoom object.
	 */
	@Test
	public void testInvalidMoveSouth() {
		Mockito.when(mockRoom.hasSouthDoor()).thenReturn(false);		// Stubbed hasSouthDoor method returns false
		
		mockNextRoom = game.parseUserInput("S", mockRoom);
		assertEquals(mockNextRoom, mockRoom);				// Asserts that the returned QuestRoom is the same as the original QuestRoom
	}
	
	
	/**
	 * Tests that entering an empty string returns the same QuestRoom
	 * that the player is currently in.
	 */
	@Test
	public void testEmptyInput() {
		mockNextRoom = game.parseUserInput("", mockRoom);		// Input is an empty string
		assertSame(mockNextRoom, mockRoom);					// Asserts that the returned QuestRoom is the same as the original QuestRoom
	}
	
	
	/**
	 * Tests that entering a number returns the same QuestRoom that
	 * the player is currently in
	 */
	@Test
	public void testNumberInput() {
		mockNextRoom = game.parseUserInput("888", mockRoom);	// Input is a number as a string
		assertSame(mockNextRoom, mockRoom);					// Asserts that the returned QuestRoom is the same as the original QuestRoom
	}
	
	
	// 0================================================================0
	// |                       LOOK COMMANDS TEST                       |
	// 0================================================================0
	
	/**
	 * Tests that the command of "L" is recognized by the program when
	 * a QuestRoom object contains coffee.
	 */
	@Test
	public void testLookAndCoffeeExists() {
		Mockito.when(mockRoom.hasItem()).thenReturn(true);			// Stubbed hasItem method returns true
		Mockito.when(mockRoom.getItem()).thenReturn("coffee");		// Stubbed getItem method returns "coffee"
		
		game.parseUserInput("L", mockRoom);
		assertTrue(game.hasCoffee());				// Asserts that the player has coffee
	}
	
	
	/**
	 * Tests that the command of "L" is recognized by the program when
	 * a QuestRoom object contains cream.
	 */
	@Test
	public void testLookAndCreamExists() {
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("cream");		// Stubbed getItem method returns "cream"
		
		game.parseUserInput("L", mockRoom);
		assertTrue(game.hasCream());				// Asserts that the player has cream
	}
	
	
	/**
	 * Tests that the command of "L" is recognized by the program when
	 * a QuestRoom object contains sugar.
	 */
	@Test
	public void testLookAndSugarExists() {
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("sugar");		// Stubbed getItem method returns "sugar"
		
		game.parseUserInput("L", mockRoom);
		assertTrue(game.hasSugar());				// Asserts that the player has sugar
	}
	
	
	/**
	 * Tests that the command of "L" is recognized by the program when
	 * a QuestRoom object does not hold an item.
	 */
	@Test
	public void testLookAndNoItem() {
		Mockito.when(mockRoom.hasItem()).thenReturn(false);			// Stubbed hasItem method returns false
		
		game.parseUserInput("L", mockRoom);
		assertEquals("No item", game.getLastCommand());			// Should match the lastCommand String of "No item"
	}
	
	
	/**
	 * Tests the command "L" when a room contains an unrecognizable item.
	 */
	@Test
	public void testLookAndItemNotRecognized() {
		Mockito.when(mockRoom.hasItem()).thenReturn(true);				// Stubbed hasItem returns true
		Mockito.when(mockRoom.getItem()).thenReturn("Rotten beans");	// Stubbed getItem returns "Rotten beans", an unrecognized item
		
		game.parseUserInput("L", mockRoom);
		assertEquals("Unrecognized item", game.getLastCommand());			// Should match the lastCommand String of "Unrecognized item"
	}
	
	
	// 0================================================================0
	// |                         INVENTORY TEST                         |
	// 0================================================================0
	
	/**
	 * Tests that the player starts with no coffee.
	 */
	@Test
	public void testNoInitialCoffee() {
		assertFalse(game.hasCoffee());			// Asserts that the player has no coffee
	}
	
	
	/**
	 * Tests that the player starts with no cream.
	 */
	@Test
	public void testNoInitialCream() {
		assertFalse(game.hasCream());			// Asserts that the player has no cream
	}
	
	
	/**
	 * Tests that the player starts with no sugar.
	 */
	@Test
	public void testNoInitialSugar() {
		assertFalse(game.hasSugar());			// Asserts that the player has no sugar
	}
	
	
	/**
	 * Tests that the command of "I" is recognized by the program when
	 * the player holds no inventory items.
	 */
	@Test
	public void testInventoryNoItems() {
		game.parseUserInput("I", mockRoom);
		assertEquals("-coffee -cream -sugar", game.getLastCommand());		// Should match the lastCommand String when the player has no items
	}
	
	
	/**
	 * Tests that the command of "I" is recognized by the program when
	 * the player holds all inventory items.
	 */
	@Test
	public void testInventoryAllItems() {
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("coffee");
		game.parseUserInput("L", mockRoom);				// The stubbed and real methods place coffee in the player's inventory
		
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("cream");
		game.parseUserInput("L", mockRoom);				// The stubbed and real methods place cream in the player's inventory
		
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("sugar");
		game.parseUserInput("L", mockRoom);				// The stubbed and real methods place sugar in the player's inventory
		
		game.parseUserInput("I", mockRoom);
		assertEquals("+coffee +cream +sugar", game.getLastCommand());		// Should match the lastCommand String when the player has all items
	}
	
	
	// 0================================================================0
	// |                           HELP TEST                            |
	// 0================================================================0
	
	/**
	 * Tests that the command of "H" is recognized by the program.
	 */
	@Test
	public void testHelp() {
		game.parseUserInput("H", mockRoom);
		assertEquals("Help", game.getLastCommand());			// Should match the lastCommand String of "Help"
	}
	
	
	// 0================================================================0
	// |                     EQUIVALENT CASES TEST                      |
	// 0================================================================0
	
	/**
	 * Tests that the commands "n" and "N" are equivalent.
	 */
	@Test
	public void testEquivalentCaseN() {
		Mockito.when(mockRoom.hasNorthDoor()).thenReturn(true);				// Stubs methods relating to moving north
		Mockito.when(mockRoom.enterNorthDoor()).thenReturn(mockNextRoom);
		String lastCommandUppercase, lastCommandLowercase;		// lastCommand Strings for upper and lowercase inputs
		
		game.parseUserInput("N", mockRoom);				// Uppercase input
		lastCommandUppercase = game.getLastCommand();
		game.parseUserInput("n", mockRoom);				// Lowercase input
		lastCommandLowercase = game.getLastCommand();
		assertEquals(lastCommandUppercase, lastCommandLowercase);		// Asserts that the lastCommand Strings are the same
	}
	
	
	/**
	 * Tests that the commands "s" and "S" are equivalent.
	 */
	@Test
	public void testEquivalentCaseS() {
		Mockito.when(mockRoom.hasSouthDoor()).thenReturn(true);				// Stubs methods relating to moving south
		Mockito.when(mockRoom.enterSouthDoor()).thenReturn(mockNextRoom);
		String lastCommandUppercase, lastCommandLowercase;
		
		game.parseUserInput("S", mockRoom);				// Uppercase input
		lastCommandUppercase = game.getLastCommand();
		game.parseUserInput("s", mockRoom);				// Lowercase input
		lastCommandLowercase = game.getLastCommand();
		assertEquals(lastCommandUppercase, lastCommandLowercase);		// Asserts that the lastCommand Strings are the same
	}
	
	
	/**
	 * Tests that the commands "i" and "I" are equivalent.
	 */
	@Test
	public void testEquivalentCaseI() {
		String lastCommandUppercase, lastCommandLowercase;
		
		game.parseUserInput("I", mockRoom);				// Uppercase input
		lastCommandUppercase = game.getLastCommand();
		game.parseUserInput("i", mockRoom);				// Lowercase input
		lastCommandLowercase = game.getLastCommand();
		assertEquals(lastCommandUppercase, lastCommandLowercase);		// Asserts that the lastCommand Strings are the same
	}
	
	
	/**
	 * Tests that the commands "h" and "H" are equivalent.
	 */
	@Test
	public void testEquivalentCaseH() {
		String lastCommandUppercase, lastCommandLowercase;
		
		game.parseUserInput("H", mockRoom);				// Uppercase input
		lastCommandUppercase = game.getLastCommand();
		game.parseUserInput("h", mockRoom);				// Lowercase input
		lastCommandLowercase = game.getLastCommand();
		assertEquals(lastCommandUppercase, lastCommandLowercase);		// Asserts that the lastCommand Strings are the same
	}
	

	// 0================================================================0
	// |                      GAME OVER SCENARIOS                       |
	// 0================================================================0
	
	/**
	 * Scenario 1: Tests checkVictoryConditions when all items are
	 * collected.
	 */
	@Test
	public void testVictoryScenario1() {
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("coffee");
		game.parseUserInput("L", mockRoom);					// The stubbed and real methods place coffee in the player's inventory
		
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("cream");
		game.parseUserInput("L", mockRoom);					// The stubbed and real methods place cream in the player's inventory
		
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("sugar");
		game.parseUserInput("L", mockRoom);					// The stubbed and real methods place sugar in the player's inventory
		
		assertEquals(1, game.checkVictoryConditions());			// Asserts that scenario 1 is returned
	}
	
	
	/**
	 * Scenario 2: Tests checkVictoryConditions when coffee and cream
	 * are collected.
	 */
	@Test
	public void testDefeatScenario2() {
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("coffee");
		game.parseUserInput("L", mockRoom);					// The stubbed and real methods place coffee in the player's inventory
		
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("cream");
		game.parseUserInput("L", mockRoom);					// The stubbed and real methods place cream in the player's inventory
		
		assertEquals(2, game.checkVictoryConditions());			// Asserts that scenario 2 is returned
	}
	
	
	/**
	 * Scenario 3: Tests checkVictoryConditions when coffee and sugar
	 * are collected.
	 */
	@Test
	public void testDefeatScenario3() {
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("coffee");
		game.parseUserInput("L", mockRoom);					// The stubbed and real methods place coffee in the player's inventory
		
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("sugar");
		game.parseUserInput("L", mockRoom);					// The stubbed and real methods place sugar in the player's inventory
		
		assertEquals(3, game.checkVictoryConditions());			// Asserts that scenario 3 is returned
	}
	
	
	/**
	 * Scenario 4: Tests checkVictoryConditions when only coffee is
	 * collected.
	 */
	@Test
	public void testDefeatScenario4() {
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("coffee");
		game.parseUserInput("L", mockRoom);					// The stubbed and real methods place coffee in the player's inventory
		
		assertEquals(4, game.checkVictoryConditions());			// Asserts that scenario 4 is returned
	}
	
	
	/**
	 * Scenario 5: Tests checkVictoryConditions when cream and sugar
	 * are collected.
	 */
	@Test
	public void testDefeatScenario5() {
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("cream");
		game.parseUserInput("L", mockRoom);					// The stubbed and real methods place cream in the player's inventory
		
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("sugar");
		game.parseUserInput("L", mockRoom);					// The stubbed and real methods place sugar in the player's inventory
		
		assertEquals(5, game.checkVictoryConditions());			// Asserts that scenario 5 is returned
	}
	
	
	/**
	 * Scenario 6: Tests checkVictoryConditions when only cream is
	 * collected.
	 */
	@Test
	public void testDefeatScenario6() {
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("cream");
		game.parseUserInput("L", mockRoom);					// The stubbed and real methods place cream in the player's inventory
		
		assertEquals(6, game.checkVictoryConditions());			// Asserts that scenario 6 is returned
	}
	
	
	/**
	 * Scenario 7: Tests checkVictoryConditions when only sugar is
	 * collected.
	 */
	@Test
	public void testDefeatScenario7() {
		Mockito.when(mockRoom.hasItem()).thenReturn(true);
		Mockito.when(mockRoom.getItem()).thenReturn("sugar");
		game.parseUserInput("L", mockRoom);					// The stubbed and real methods place sugar in the player's inventory
		
		assertEquals(7, game.checkVictoryConditions());			// Asserts that scenario 7 is returned
	}
	
	
	/**
	 * Scenario 8: Tests checkVictoryConditions when no items are
	 * collected.
	 */
	@Test
	public void testDefeatScenario8() {
		assertEquals(8, game.checkVictoryConditions());			// Asserts that scenario 8 is returned
	}
	
	
	// 0================================================================0
	// |                        GAME BUILD TESTS                        |
	// 0================================================================0
	
	/**
	 * Tests that the buildRooms correctly creates a QuestHouse with 6
	 * QuestRoom objects.
	 */
	@Test
	public void testBuildGame() {
		game.buildRooms(mockHouse);
		Mockito.verify(mockHouse, Mockito.times(6)).incrementNumberOfRooms();		// Verifies that the internal loop was called 6 times
	}
	
	
	/**
	 * Tests that passing a null object in the buildRooms method throws
	 * an IllegalArgumentException.
	 */
	@Test
	public void testBuildGameWithNull() {
		try {
			game.buildRooms(null);
			fail("Expected an IllegalArgumentException to be thrown.");			// If no Exception is thrown, the test fails
		}
		catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("Error: The QuestHouse object was not initialized!"));	// Asserts that the Exception was thrown
		}
	}
	
	
	// 0================================================================0
	// |                          MISC. TESTS                           |
	// 0================================================================0
	
	/**
	 * Tests that the correct opening game title is printed.
	 */
	@Test
	public void testTitlePrint() {
		assertEquals("Coffee Maker Quest", game.printTitles(0));		// Should match the lastCommand String when printing the game title
	}
	
	
	/**
	 * Tests that the correct closing game ending is printed.
	 */
	@Test
	public void testGameOverPrint() {
		assertEquals("GAME OVER", game.printTitles(1));			// Should match the lastCommand String when printing game over
	}
	
	
	/**
	 * Tests that nothing is printed when an invalid number is passed
	 * as an argument.
	 */
	@Test
	public void testBadPrint() {
		assertEquals("Bad print job", game.printTitles(8));			// Should match the lastCommand String when an invalid integer is passed
	}
}
