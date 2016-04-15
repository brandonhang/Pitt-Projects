/**
 * @author Brandon S. Hang
 * @version 1.500
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This is the JUnit test plan for the CardPrint.java class.  
 */

package baccarat_test_suite;

import static org.junit.Assert.*;

import org.mockito.Mockito;
import org.junit.Test;

import baccarat.CardPrint;
import baccarat.Card;

public class CardPrintTestPlan {
	
	private CardPrint cardRow = new CardPrint();
	private Card mockCard = Mockito.mock(Card.class);
	private char[] suitChars = {'C', 'D', 'H', 'S'};		// Coincidentally, these are the initials of my high school
	
	// 0================================================================0
	// |                GRAPHICAL CARD PRINTING TESTS                   |
	// 0================================================================0
	
	/**
	 * Tests printing of a full hand of 3 cards
	 */
	@Test
	public void testGraphicalPrintFullHand() {
		
		for (int i = 1; i <= 4; i++) {
			for (int j = 1; j <= 13; j++) {
				Card[] mockHand = {mockCard, mockCard, mockCard};
				Mockito.when(mockCard.getValue()).thenReturn(j);
				Mockito.when(mockCard.getSuit()).thenReturn(suitChars[i - 1]);

				String cardAbbr = "";					// A string of the abbreviated hand
				cardAbbr += mockCard.getValue();
				cardAbbr += mockCard.getSuit();
				
				cardAbbr = cardAbbr + " " + cardAbbr + " " + cardAbbr + " ";
				assertEquals(cardRow.printHand(mockHand), cardAbbr);
			}
		}
	}
	
	/**
	 * Tests printing of a hand composed only of null cards
	 */
	@Test
	public void testPrintHandOfNullCards() {
		
		Card[] nullHand = {null, null, null};
		assertEquals(cardRow.printHand(nullHand), "");		// The abbreviated hand is an empty string
	}
	
	/**
	 * Tests printing of a null hand
	 */
	@Test(expected=NullPointerException.class)
	public void testPrintNullHand() {
		
		cardRow.printHand(null);
	}
	
	/**
	 * Tests printing of an illegal card value
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalCardValue() {
		
		Card[] mockHand = {mockCard, null, null};
		Mockito.when(mockCard.getValue()).thenReturn(18);		// 18 is an invalid card value
		Mockito.when(mockCard.getSuit()).thenReturn('8');
		cardRow.printHand(mockHand);
	}
	
	/**
	 * Tests printing of an illegal card suit
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalCardSuit() {
		
		Card[] mockHand = {mockCard, null, null};
		Mockito.when(mockCard.getValue()).thenReturn(7);
		Mockito.when(mockCard.getSuit()).thenReturn('Z');		// 'Z' is an invalid card suit
		cardRow.printHand(mockHand);
	}
}
