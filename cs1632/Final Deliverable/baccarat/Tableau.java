/**
 * @author Brandon S. Hang
 * @version 1.300
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This class is functions as the "tableau" of drawing rules.  In baccarat, the tableau
 * is consulted when neither the player nor the banker draw a "natural."
 */

package baccarat;

public class Tableau {
	
	/**
	 * Determines if the player should draw a third card
	 * @param playerPoints The points of the player's initial hand
	 * @return True if the points are less than 6, false otherwise
	 */
	public boolean checkPlayerDraw(int playerPoints) {
		
		if (playerPoints >= 0 && playerPoints < 6) {		// Conditional if the player has 0-5 points
			return true;
		}
		else if (playerPoints >= 6 && playerPoints <= 7) {		// Conditional if the player has 6-7 points
			System.out.printf("You and the banker stand.\n");
			return false;
		}
		else {
			throw new IllegalArgumentException("An illegal number of points was entered");		// Otherwise, throws an exception
		}
	}
	
	
	/**
	 * Determines if the banker should draw a third card
	 * @param bankerPoints The points of the banker's initial hand
	 * @param cardValue The player's third card
	 * @return
	 */
	public boolean checkBankerDraw(int bankerPoints, int cardValue) {
		
		if (cardValue > 13 || cardValue < 1) {
			throw new IllegalArgumentException("An invalid card was drawn!\n");		// Throws an exception if a card value is outside the normal range
		}
		if (bankerPoints < 0 || bankerPoints > 7) {
			throw new IllegalArgumentException("Illegal amount of banker points!\n");		// Throws an exception if the banker's points are outside the normal range
		}
		
		switch (bankerPoints) {
			case 0:
			case 1:
			case 2:
				return true;				// The banker always draws a card if their points are less than 3
			case 3:
				if (cardValue != 8) {
					return true;				// Draws a card if the player does not draw an 8
				}
				break;
			case 4:
				if (cardValue >= 2 && cardValue <= 7) {		// Draws a card if the player draws cards 2-7
					return true;
				}
				break;
			case 5:
				if (cardValue >= 4 && cardValue <= 7) {		// Draws a card if the player draws cards 4-7
					return true;
				}
				break;
			case 6:
				if (cardValue >= 6 && cardValue <= 7) {		// Draws a card if the player draws a 6 or 7
					return true;
				}
				break;
			default:
		}
		
		System.out.printf("The banker stands.\n");
		return false;			// Otherwise, no card is drawn
	}
}
