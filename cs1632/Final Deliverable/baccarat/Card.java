/**
 * @author Brandon S. Hang
 * @version 1.200
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This class is responsible for the manipulation of individual playing cards.  It has
 * methods that pertain to their creation and access of card information.
 */

package baccarat;

public class Card {
	
	private final String[] CARD_RANKS = {"Ace", "Two", "Three", "Four", "Five", "Six",
			"Seven", "Eight", "Nine", "Ten", "Jack", "Queen", "King"};
	private final String[] CARD_SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};
	private int value;
	private int suit;
	
	
	/**
	 * Creates a new card
	 * @param val The card's value
	 * @param st The card's suit
	 */
	public Card(int val, int st) {
		
		if (val < 1 || val > 13 || st < 1 || st > 4) {					// Throws an exception if an inappropriate parameter is used
			throw new IllegalArgumentException("Cannot set a card to a non-existent value or suit!\n");
		}
		
		value = val;
		suit = st;
	}
	
	
	/**
	 * Gets the value of the card as an int
	 * @return
	 */
	public int getValue() {
		
		return value;
	}
	
	
	/**
	 * Gets the suit of the card as a char
	 * @return A char representing the card's suit
	 */
	public char getSuit() {
		
		switch(suit) {
			case 1:
				return 'C';			// Club
			case 2:
				return 'D';			// Diamond
			case 3:
				return 'H';			// Heart
			default:
				return 'S';			// Spade
		}
	}
	
	
	/**
	 * Returns a string representation of the card
	 */
	public String toString() {
		
		return CARD_RANKS[value - 1] + " of " + CARD_SUITS[suit - 1];
	}
	
	
	/**
	 * Checks whether this card is equal to another card
	 * @param card The card to compare to
	 * @return True if the cards are equal, false otherwise
	 */
	public boolean equals(Card card) {
		
		if (getValue() == card.getValue() && getSuit() == card.getSuit()) {
			return true;
		}
		else {
			return false;
		}
	}
}
