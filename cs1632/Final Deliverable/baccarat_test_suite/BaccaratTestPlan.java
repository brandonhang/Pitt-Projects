/**
 * @author Brandon S. Hang
 * @version 1.300
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This is the JUnit test plan for the Baccarat.java class.  
 */

package baccarat_test_suite;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import baccarat.Baccarat;
import baccarat.Deck;
import baccarat.Card;

public class BaccaratTestPlan {
	
	private Card mockCard1 = Mockito.mock(Card.class);
	private Card mockCard2 = Mockito.mock(Card.class);
	private Card mockCard3 = Mockito.mock(Card.class);
	private Deck mockDeck = Mockito.mock(Deck.class);
	private Baccarat puntoBanco = new Baccarat();
	
	@Before
	public void setUp() throws Exception {
	}
	
	// 0================================================================0
	// |                  BACCARAT POINT VALUE TESTS                    |
	// 0================================================================0
	
	/**
	 * Tests that aces through nines have point values of themselves (aces are 1 point)
	 */
	@Test
	public void testNumberCardValue() {
		
		for (int i = 1; i < 10; i++) {
			Mockito.when(mockCard1.getValue()).thenReturn(i);
			assertEquals(puntoBanco.getCardPoints(mockCard1), i);
		}
	}
	
	/**
	 * Tests that tens and face cards are worth zero points
	 */
	@Test
	public void testFaceCardValue() {
		
		for (int i = 10; i < 14; i++) {
			Mockito.when(mockCard1.getValue()).thenReturn(i);
			assertEquals(puntoBanco.getCardPoints(mockCard1), 0); 
		}
	}
	
	/**
	 * Tests that a null card (Card = null) is worth zero points
	 */
	@Test
	public void testNullCard() {
		
		assertEquals(puntoBanco.getCardPoints(null), 0);
	}
	
	// 0================================================================0
	// |                    DRAW VERIFICATION TEST                      |
	// 0================================================================0
	
	/**
	 * Verifies that 4 cards are initially drawn at the beginning of each round
	 */
	@Test
	public void testDrawInitialHand() {
		
		puntoBanco.drawInitialHand(mockDeck, new Card[3], new Card[3]);
		Mockito.verify(mockDeck, Mockito.times(4)).draw();
	}
	
	// 0================================================================0
	// |                    CARD HAND POINTS TESTS                      |
	// 0================================================================0
	
	/**
	 * Tests that a hand of 2 cards returns the correct number of points
	 */
	@Test
	public void testTwoCardsInHandPoints() {
		
		Card[] mockHand = {mockCard1, mockCard2, null};
		
		for (int i = 1; i < 14; i++) {
			for (int j = i; j < 14; j++) {
				Mockito.when(mockCard1.getValue()).thenReturn(i);
				Mockito.when(mockCard2.getValue()).thenReturn(j);
				
				int sum = puntoBanco.getCardPoints(mockCard1)
						+ puntoBanco.getCardPoints(mockCard2);
				
				assertEquals(puntoBanco.getPointsInHand(mockHand), (sum % 10));
			}
		}
	}
	
	/**
	 * Tests that a hand of 3 cards returns the correct number of points
	 */
	@Test
	public void testThreeCardsInHandPoints() {
		
		Card[] mockHand = {mockCard1, mockCard2, mockCard3};
		
		for (int i = 1; i < 14; i++) {
			for (int j = i; j < 14; j++) {
				for (int k = i; k < 14; k++) {
					Mockito.when(mockCard1.getValue()).thenReturn(i);
					Mockito.when(mockCard2.getValue()).thenReturn(j);
					Mockito.when(mockCard3.getValue()).thenReturn(k);
					
					int sum = puntoBanco.getCardPoints(mockCard1)
							+ puntoBanco.getCardPoints(mockCard2)
							+ puntoBanco.getCardPoints(mockCard3);
					
					assertEquals(puntoBanco.getPointsInHand(mockHand), (sum % 10));
				}
			}
		}
	}
	
	/**
	 * Tests that a null hand returns 0 points
	 */
	@Test
	public void testNullHandPoints() {
		
		Card[] nullHand = new Card[3];
		assertEquals(puntoBanco.getPointsInHand(nullHand), 0);
	}
	
	// 0================================================================0
	// |                   NATURAL DRAWN HAND TESTS                     |
	// 0================================================================0
	
	/**
	 * Tests that a "natural" has occurred after drawing initial hands
	 */
	@Test
	public void testNaturalDraw() {
		
		for (int i = 8; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				assertTrue(puntoBanco.isNatural(i, j));
				assertTrue(puntoBanco.isNatural(j, i));
			}
		}
	}
	
	/**
	 * Tests that a "natural" has not occurred after drawing initial hands
	 */
	@Test
	public void testNotNaturalDraw() {
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				assertFalse(puntoBanco.isNatural(i, j));
				assertFalse(puntoBanco.isNatural(j, i));
			}
		}
	}
}
