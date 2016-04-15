/**
 * @author Brandon S. Hang
 * @version 1.500
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This class is functions as the croupier, or dealer, of the baccarat game.
 * It acts as the intermediary between the player/banker and the deck of cards.
 */

package baccarat;

public class Baccarat {
	
	/**
	 * Gets the point value of a card according to baccarat rules
	 * @param card
	 * @return
	 */
	public int getCardPoints(Card card) {
		
		if (card == null) {
			return 0;
		}
		
		int points = card.getValue();
		
		switch (points) {
			case 10:
			case 11:
			case 12:
			case 13:
				return 0;			// Face cards are worth 0 points
			default:
				return points;
		}
	}
	
	
	/**
	 * Alternately draws the initial two cards for the player and the banker
	 */
	public void drawInitialHand(Deck deck, Card[] userHand, Card[] bankHand) {
		
		for (int i = 0; i < 2; i++) {
			userHand[i] = deck.draw();
			bankHand[i] = deck.draw();
		}
	}
	
	
	/**
	 * Gets the point total of the player or banker's hand
	 * @param hand The player or banker's hand
	 * @return The point value of the hand
	 */
	public int getPointsInHand(Card[] hand) {
		
		int card1, card2, card3;
		
		if (hand[0] == null) {			// If any of the cards are null, the card's value is zero
			card1 = 0;
		}
		else {
			card1 = getCardPoints(hand[0]);
		}
		
		if (hand[1] == null) {
			card2 = 0;
		}
		else {
			card2 = getCardPoints(hand[1]);
		}
		
		if (hand[2] == null) {
			card3 = 0;
		}
		else {
			card3 = getCardPoints(hand[2]);
		}
		
		return (card1 + card2 + card3) % 10;			// The point value is the sum of the cards modulo 10
	}
	
	
	/**
	 * Determines if a natural draw has occurred (I.e., either the player or banker has a
	 * hand worth 8 or 9 points
	 * @param playerPoints The player's points
	 * @param bankerPoints The banker's points
	 * @return True if either the player or banker has 8 or 9 points.  False otherwise.
	 */
	public boolean isNatural(int playerPoints, int bankerPoints) {
		
		if (playerPoints == 8 || playerPoints == 9 || bankerPoints == 8 || bankerPoints == 9) {
			return true;
		}
		else {
			return false;
		}
	}	
}
