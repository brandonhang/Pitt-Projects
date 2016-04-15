/**
 * @author Brandon S. Hang
 * @version 1.300
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This class is responsible for the manipulation of a deck of playing cards.  I.e., it
 * creates a collection of Card objects to manipulate.
 */

package baccarat;

import java.util.Random;

public class Deck {
	
	public final int DECK_SIZE = 52;
	private Card[] deck;
	private int deckPosition;
	private int deckSize;
	
	
	/**
	 * Creates a new empty deck
	 */
	public Deck() {
		
		deck = new Card[DECK_SIZE];
		deckPosition = 0;
		deckSize = 0;
	}
	
	
	/**
	 * Gets the number of cards currently in the deck
	 * @return The number of cards in the deck
	 */
	public int getSize() {
		
		return deckSize;
	}
	
	
	/**
	 * Completely fills the deck with DECK_SIZE cards
	 */
	public void fill() {
		
		int index = 0;
		
		for (int i = 1; i <= 4; i++) {				// Suit values
			for (int j = 1; j <= 13; j++) {			// Rank/Face values
				deck[index] = new Card(j, i);
				index++;
			}
		}
		
		deckSize = DECK_SIZE;
	}
	
	
	/**
	 * Draws the next card from the deck
	 * @return A Card object from the deck
	 */
	public Card draw() {
		
		if (deckSize == 0) {
			return null;			// Returns null if the deck is empty
		}
		
		deckSize--;
		return deck[deckPosition++];		// Returns a card and moves the deck pointer
	}
	
	
	/**
	 * Shuffles the deck using the Durstenfeld version of the Fisher-Yates shuffle algorithm
	 */
	public void shuffle() {
		
		if (deckSize <= 1) {			// Returns if the deck is empty or has only 1 card
			return;
		}
		
		Random randGen = new Random();
		int index;
		Card tempCard;					// A temporary card for swapping cards in the deck
		
		for (int i = DECK_SIZE - 1; i > deckPosition; i--) {
			index = randGen.nextInt(i);
			tempCard = deck[i];
			deck[i] = deck[index];
			deck[index] = tempCard;
		}
	}
	
	
	/**
	 * CHEATS - Gets the entire deck of cards
	 * @return An array containing the entire deck of cards
	 */
	public Card[] cheat() {
		
		return deck;
	}
}
