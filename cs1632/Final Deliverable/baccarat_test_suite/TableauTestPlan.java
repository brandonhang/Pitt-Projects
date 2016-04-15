/**
 * @author Brandon S. Hang
 * @version 1.200
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This is the JUnit test plan for the Tableau.java class.  
 */

package baccarat_test_suite;

import static org.junit.Assert.*;
import org.junit.Test;

import baccarat.Tableau;

public class TableauTestPlan {
	
	private Tableau tableau = new Tableau();
	
	// 0================================================================0
	// |                 PLAYER DRAW CONDITIONS TESTS                   |
	// 0================================================================0
	
	/**
	 * Tests conditions where the player needs to draw a third card
	 */
	@Test
	public void testPlayerDraws() {
		
		for (int i = 0; i < 6; i++) {
			assertTrue(tableau.checkPlayerDraw(i));
		}
	}
	
	/**
	 * Tests conditions where the player does not draw a third card
	 */
	@Test
	public void testPlayerDoesNotDraw() {
		
		assertFalse(tableau.checkPlayerDraw(6));
		assertFalse(tableau.checkPlayerDraw(7));
	}
	
	/**
	 * Tests an illegal but plausible points parameter
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidPointsValue() {
		
		tableau.checkPlayerDraw(8);
	}
	
	/**
	 * Tests an illegal negative points value
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalNegativePoints() {
		
		tableau.checkPlayerDraw(-256);
	}
	
	/**
	 * Tests an illegal maximum int value
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalLargeInteger() {
		
		tableau.checkPlayerDraw(Integer.MAX_VALUE);
	}
	
	// 0================================================================0
	// |                 BANKER DRAW CONDITIONS TESTS                   |
	// 0================================================================0
	
	/**
	 * Tests that the banker draws a card if their total points is 2 or less
	 */
	@Test
	public void testBankerDraw2PointsOrLess() {
		
		for (int i = 0; i < 3; i++) {
			for (int j = 1; j < 13; j++) {
				assertTrue(tableau.checkBankerDraw(i, j));
			}
		}
	}
	
	/**
	 * Tests that the banker draws a card if their total points is 3 and the player's
	 * third card is not 8
	 */
	@Test
	public void testBankerDraw3Points() {
		
		for (int i = 1; i < 8; i++) {
			assertTrue(tableau.checkBankerDraw(3, i));
		}
		for (int i = 9; i < 14; i++) {
			assertTrue(tableau.checkBankerDraw(3, i));
		}
	}
	
	/**
	 * Tests that the banker does not draw a card if their point total is 3 and the player
	 * draws an 8
	 */
	@Test
	public void testBankerStandPoints() {
		
		assertFalse(tableau.checkBankerDraw(3, 8));
	}
	
	/**
	 * Tests that the banker draws a card if their point total is 4 and the player draws
	 * a 2, 3, 4, 5, 6, or 7
	 */
	@Test
	public void BankerDraw4Points() {
		
		for (int i = 2; i < 8; i++) {
			assertTrue(tableau.checkBankerDraw(4, i));
		}
	}
	
	/**
	 * Tests that the banker does not draw a card if their point total is 4 and the player
	 * draws an Ace, 8, 9, 10, Jack, Queen, or King
	 */
	@Test
	public void testBankerStand4Points() {
		
		assertFalse(tableau.checkBankerDraw(4, 1));
		
		for (int i = 8; i < 14; i++) {
			assertFalse(tableau.checkBankerDraw(4, i));
		}
	}
	
	/**
	 * Tests that the banker draws a card if their point total is 5 and the player draws
	 * a 4, 5, 6, or 7
	 */
	@Test
	public void BankerDraw5Points() {
		
		for (int i = 4; i < 8; i++) {
			assertTrue(tableau.checkBankerDraw(5, i));
		}
	}
	
	/**
	 * Tests that the banker does not draw a card if their point total is 4 and the player
	 * draws an Ace, 2, 3, 8, 9, 10, Jack, Queen, or King
	 */
	@Test
	public void testBankerStand5Points() {
		
		for (int i = 1; i < 4; i++) {
			assertFalse(tableau.checkBankerDraw(5, i));
		}
		for (int i = 8; i < 14; i++) {
			assertFalse(tableau.checkBankerDraw(5, i));
		}
	}
	
	/**
	 * Tests that the banker draws a card if their point total is 6 and the player draws
	 * a 6 or 7
	 */
	@Test
	public void BankerDraw6Points() {
		
		assertTrue(tableau.checkBankerDraw(6, 6));
		assertTrue(tableau.checkBankerDraw(6, 7));
	}
	
	/**
	 * Tests that the banker does not draw a card if their point total is 4 and the player
	 * does not draw a 6 or 7
	 */
	@Test
	public void testBankerStand6Points() {
		
		for (int i = 1; i < 6; i++) {
			assertFalse(tableau.checkBankerDraw(6, i));
		}
		for (int i = 8; i < 14; i++) {
			assertFalse(tableau.checkBankerDraw(6, i));
		}
	}
	
	/**
	 * Tests that the banker does not draw a card
	 */
	@Test
	public void testBankerStand7Points() {
		
		for (int i = 1; i < 14; i++) {
			assertFalse(tableau.checkBankerDraw(7, i));
		}
	}
	
	/**
	 * Tests an illegal but plausible points parameter
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidPointValue() {
		
		tableau.checkBankerDraw(8, 8);
	}
	
	/**
	 * Tests an illegal points parameter of the maximum int value
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalLargePoints() {
		
		tableau.checkBankerDraw(Integer.MAX_VALUE, 8);
	}
	
	/**
	 * Tests an illegal negative points parameter
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalNegativeBankerPoints() {
		
		tableau.checkBankerDraw(-8315, 8);
	}
	
	/**
	 * Tests an illegal positive card parameter
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalPositiveCard() {
		
		tableau.checkBankerDraw(5, 83215);
	}
	
	/**
	 * Tests an illegal negative card parameter
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalNegativeCard() {
		tableau.checkBankerDraw(5, Integer.MIN_VALUE);
	}
}
