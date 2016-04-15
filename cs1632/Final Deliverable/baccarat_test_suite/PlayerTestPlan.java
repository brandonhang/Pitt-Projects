/**
 * @author Brandon S. Hang
 * @version 1.300
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This is the JUnit test plan for the Player.java class.  
 */

package baccarat_test_suite;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import baccarat.Player;

public class PlayerTestPlan {
	
	private Player player;
	
	@Before
	public void createNewPlayer() {
		
		player = new Player();
	}
	
	// 0================================================================0
	// |                   PLAYER INITIALIZATION TEST                   |
	// 0================================================================0
	
	/**
	 * Tests that a player starts with a balance of $10,000
	 */
	@Test
	public void testStartBalance() {
		
		assertEquals(player.checkBalance(), 10000);
	}
	
	// 0================================================================0
	// |                 FINANCE ADDITION TESTS                         |
	// 0================================================================0
	
	/**
	 * Tests correctly adding to a player's balance
	 */
	@Test
	public void testAddToBalance() {
		
		player.addFunds(888);
		assertEquals(player.checkBalance(), 10888);
	}

	/**
	 * Tests that adding the maximum int value results in changing the player's balance to the maximum int value
	 */
	@Test
	public void testAddTooMuchMoney() {
		
		player.addFunds(Integer.MAX_VALUE);
		assertEquals(player.checkBalance(), Integer.MAX_VALUE);
	}
	
	/**
	 * Tests that adding a negative integer results in no change to the player's balance
	 */
	@Test
	public void testAddNegativeMoney() {
		
		player.addFunds(-23);
		assertEquals(player.checkBalance(), 10000);
	}
	
	// 0================================================================0
	// |                   FINANCE SUBTRACTION TESTS                    |
	// 0================================================================0
	
	/**
	 * Tests correctly subtracting from a player's balance
	 */
	@Test
	public void testSubtractFromBalance() {
		
		player.subtractFunds(5000);
		assertEquals(player.checkBalance(), 5000);
	}
	
	/**
	 * Tests that subtracting more funds that what is available in the player's balance results in no change
	 */
	@Test
	public void testSubtractMaxInt() {
		
		player.subtractFunds(Integer.MAX_VALUE);
		assertEquals(player.checkBalance(), 10000);
	}
	
	/**
	 * Tests that subtracting negative funds results in no change to the player's balance
	 */
	@Test
	public void testSubtractNegativeMoney() {
		
		player.subtractFunds(-23);
		assertEquals(player.checkBalance(), 10000);
	}
	
	// 0================================================================0
	// |                   RECORD MANIPULATION TESTS                    |
	// 0================================================================0
	
	/**
	 * Tests that a player starts with a win/loss/tie record of 0-0-0
	 */
	@Test
	public void testStartingRecord() {
		
		assertEquals(player.printRecord(), "0W-0L-0T");
	}
	
	/**
	 * Tests adding a win to the player's record
	 */
	@Test
	public void testAddWin() {
		
		player.addWin();
		assertEquals(player.printRecord(), "1W-0L-0T");
	}
	
	/**
	 * Tests adding a loss to the player's record
	 */
	@Test
	public void testAddLoss() {
		
		player.addLoss();
		assertEquals(player.printRecord(), "0W-1L-0T");
	}
	
	/**
	 * Tests adding a tie to the player's record
	 */
	@Test
	public void testAddTie() {
		
		player.addTie();
		assertEquals(player.printRecord(), "0W-0L-1T");
	}
}
