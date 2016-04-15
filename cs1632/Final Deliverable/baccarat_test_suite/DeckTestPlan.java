/**
 * @author Brandon S. Hang
 * @version 1.200
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This is the JUnit test plan for the Deck.java class.  
 */

package baccarat_test_suite;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.instanceOf;
import org.junit.Before;
import org.junit.Test;
import java.util.LinkedList;

import baccarat.Deck;
import baccarat.Card;

public class DeckTestPlan {
	
	private Deck deck;
	
	@Before
	public void initializeDeck() {
		
		deck = new Deck();
	}
	
	// 0================================================================0
	// |                       DECK CREATION TEST                       |
	// 0================================================================0
	
	/**
	 * Tests that a new deck is empty
	 */
	@Test
	public void testNewDeckSize() {
		
		assertEquals(deck.getSize(), 0);
	}
	
	// 0================================================================0
	// |                        DECK DRAW TESTS                         |
	// 0================================================================0
	
	/**
	 * Tests that a filled deck has 52 cards
	 */
	@Test
	public void testFillDeckSize() {
		
		deck.fill();
		assertEquals(deck.getSize(), deck.DECK_SIZE);
	}
	
	/**
	 * Tests that a drawn card decreases the deck size by 1 when the deck
	 * still has cards
	 */
	@Test
	public void testDrawCardSize() {
		
		deck.fill();
		int initSize = deck.getSize();
		deck.draw();
		assertEquals(deck.getSize(), initSize - 1);
	}
	
	/**
	 * Tests that an empty deck does not have any cards drawn from it
	 */
	@Test
	public void testDrawEmptyDeck() {
		
		int initSize = deck.getSize();
		deck.draw();
		assertEquals(deck.getSize(), initSize);
	}
	
	/**
	 * Tests that a Card object is drawn from a non-empty deck
	 */
	@Test
	public void testDrawRealCard() {
		
		deck.fill();
		assertThat(deck.draw(), instanceOf(Card.class));
	}
	
	/**
	 * Tests that a card drawn from an empty deck returns null
	 */
	@Test
	public void testDrawNullCard() {
		
		assertNull(deck.draw());
	}
	
	/**
	 * Tests that sequentially drawn cards are not the same
	 */
	@Test
	public void testDrawSameCard() {
		
		deck.fill();
		Card card1 = deck.draw();
		Card card2 = deck.draw();
		assertFalse(card1.equals(card2));
	}
	
	// 0================================================================0
	// |                       DECK SHUFFLE TESTS                       |
	// 0================================================================0
	
	/**
	 * Tests that a shuffled deck has the same size as an unshuffled deck
	 */
	@Test
	public void testShuffleSize() {
		
		deck.fill();
		int initSize = deck.getSize();
		deck.shuffle();
		assertEquals(deck.getSize(), initSize);
	}
	
	/**
	 * Tests that a shuffled deck contains the same cards as the unshuffled deck
	 */
	@Test
	public void testShuffleSameContents() {
		
		deck.fill();
		Card[] unshArr = deck.cheat();
		deck.shuffle();
		Card[] shufArr = deck.cheat();
		LinkedList<Card> shuffled = new LinkedList<Card>();
		LinkedList<Card> unshuffled = new LinkedList<Card>();
		
		for (int i = 0; i < deck.DECK_SIZE; i++) {
			shuffled.add(shufArr[i]);
			unshuffled.add(unshArr[i]);
		}
		
		for (int i = 0; i < deck.DECK_SIZE; i++) {
			Card card = shuffled.pop();
			assertTrue(unshuffled.remove(card));
		}
	}
	
	/**
	 * Tests that shuffling an empty deck does nothing (deck remains empty)
	 */
	@Test
	public void testShuffleEmptyDeck() {
		
		deck.shuffle();
		assertEquals(deck.getSize(), 0);
		
	}
	
	/*
	 * NOTE: I considered asserting that an unshuffled and a shuffled deck were arranged differently,
	 * but it is possible (albeit incredibly unlikely) that shuffling a deck will result in the exact
	 * same arrangement of cards.
	 */
}
