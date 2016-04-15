/**
 * @author Brandon S. Hang
 * @version 1.200
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This is the JUnit test plan for the Card.java class.  
 */

package baccarat_test_suite;

import static org.junit.Assert.*;
import org.junit.Test;

import baccarat.Card;

public class CardTestPlan {
	
	// 0================================================================0
	// |                  CARD CREATION TESTS - VALUE                   |
	// 0================================================================0
	
	/**
	 * Tests the common case card values of 2-12 (Two to Queen)
	 */
	@Test
	public void testCardValue8() {
		
		for (int i = 2; i < 13; i++) {
			Card card = new Card(i, 2);
			assertEquals(card.getValue(), i);
		}
	}
	
	/**
	 * Tests the edge case card value of 1 (Ace)
	 */
	@Test
	public void testCardValueEdge1() {
		
		Card card = new Card(1, 2);
		assertEquals(card.getValue(), 1);
	}
	
	/**
	 * Tests the edge case card value of 13 (King)
	 */
	@Test
	public void testCardValueEdge13() {
		
		Card card = new Card(13, 2);
		assertEquals(card.getValue(), 13);
	}
	
	/**
	 * Tests the corner case card value of 0
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCardValueCornerCase0() {
		
		new Card(0, 2);
	}
	
	/**
	 * Tests the corner case card value of -88
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCardValueCornerCaseMinus88() {
		
		new Card(-88, 2);
	}
	
	/**
	 * Tests the corner case card value of MAX_INT
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCardValueCornerCaseMax() {
		
		new Card(Integer.MAX_VALUE, 2);
	}
	
	// 0================================================================0
	// |                   CARD CREATION TESTS - SUIT                   |
	// 0================================================================0
	
	/**
	 * Tests the common case card suit of 2 (Diamond)
	 */
	@Test
	public void testCardSuitCommon2() {
		
		Card card = new Card(8, 2);
		assertEquals(card.getSuit(), 'D');
	}
	
	/**
	 * Tests the common case card suit of 1 (Heart)
	 */
	@Test
	public void testCardSuitCommon3() {
		
		Card card = new Card(8, 3);
		assertEquals(card.getSuit(), 'H');
	}
	
	/**
	 * Tests the edge case card suit of 1 (Club)
	 */
	@Test
	public void testCardSuitEdge1() {
		
		Card card = new Card(8, 1);
		assertEquals(card.getSuit(), 'C');
	}
	
	/**
	 * Tests the edge case card suit of 4 (Spade)
	 */
	@Test
	public void testCardSuitEdge4() {
		
		Card card = new Card(8, 4);
		assertEquals(card.getSuit(), 'S');
	}
	
	/**
	 * Tests the corner case card suit of 0
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCardSuitEdge0() {
		
		new Card(8, 0);
	}
	
	/**
	 * Tests the corner case card suit of -23
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCardSuitEdgeMinus23() {
		
		new Card(8, -23);
	}
	
	/**
	 * Tests the corner case card suit of MAX_INT
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCardSuitEdgeMax() {
		
		new Card(8, Integer.MAX_VALUE);
	}
	
	// 0================================================================0
	// |                        CARD STRING TEST                        |
	// 0================================================================0
	
	/**
	 * Tests that a card returns the correct string when stringified
	 */
	@Test
	public void testStringifyCard() {
		
		String[] value = {"Ace", "Two", "Three", "Four", "Five", "Six",
				"Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
		String[] suit = {"Clubs", "Diamonds", "Hearts", "Spades"};
		Card card;
		
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 13; j++) {
				card = new Card(j, i);
				assertEquals(card.toString(), value[j - 1] + " of " + suit[i - 1]);
			}
		}
	}
	
	// 0================================================================0
	// |                     CARD COMPARISON TESTS                      |
	// 0================================================================0
	
	/**
	 * Tests that 2 identical cards return true when compared
	 */
	@Test
	public void testEqualCards() {
		
		Card card1, card2;
		
		for (int i = 1; i < 14; i++) {
			for (int j = 1; j < 5; j++) {
				card1 = new Card(i, j);
				card2 = new Card(i, j);
				assertTrue(card1.equals(card2));
			}
		}
	}
	
	/**
	 * Tests that 2 different cards return false when compared in the common case
	 */
	@Test
	public void testUnequalCardsCommon() {
		
		Card card1 = new Card(5, 2);
		Card card2 = new Card(9, 3);
		assertFalse(card1.equals(card2));
	}
	
	/**
	 * Tests that 2 different cards return false when compared in different edge cases
	 */
	@Test
	public void testUnequalCardsEdge() {
		
		Card card1 = new Card(13, 4);				// Outer boundaries of suits and ranks
		Card card2 = new Card(1, 1);
		assertFalse(card1.equals(card2));
		
		card1 = new Card(6, 3);						// Inner boundaries of suits and ranks
		card2 = new Card(5, 2);
		assertFalse(card1.equals(card2));
		
		card1 = new Card(7, 1);						// Differ only by suit
		card2 = new Card(7, 2);
		assertFalse(card1.equals(card2));
		
		card1 = new Card(10, 3);					// Differ only by rank
		card2 = new Card(9, 3);
		assertFalse(card1.equals(card2));
	}
}
