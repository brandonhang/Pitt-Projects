/**
 * @author Brandon S. Hang
 * @version 1.200
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This class is responsible for printing graphical ASCII representations of the cards.
 * It supplements the existing GameUI.java by adding some visual flair other than
 * simple text.
 */

package baccarat;

public class CardPrint {
	
	private final String[][] suits = {
			{"| (``) |", "|  /\\  |", "| _  _ |", "| ,'`. |"},
			{"|(``'')|", "| <  > |", "|( `' )|", "|(_,._)|"},
			{"| `/\\' |", "|  \\/  |", "| `.,' |", "|  /\\  |"}
	};
	
	
	/**
	 * Prints out ASCII graphical representations of a player's or banker's hand
	 * @param hand A hand of Card objects
	 * @return An abbreviated string of the hand (E.g., 8H 11D 3S)
	 */
	public String printHand(Card[] hand) {
		
		if (hand == null) {
			throw new NullPointerException("Can't print a null hand!\n");
		}
		
		String handString = "";			// The abbreviated hand to build
		int suit, rank;
		char suitChar;
		
		for (int i = 0; i < 3; i++) {			// Prints out the top borders of the cards if they exist
			if (hand[i] != null) {
				System.out.printf(" ______ ");
			}
		}
		System.out.printf("\n");
		
		for (int i = 0; i < 3; i++) {			// Prints out the top row of ranks/faces of the cards if they exist
			if (hand[i] != null) {
				rank = hand[i].getValue();
				System.out.printf("|%s    |", convertRank(rank, 0));
			}
		}
		System.out.printf("\n");
		
		for (int i = 0; i < 3; i++) {			// Prints out the bodies of the cards if they exist
			if (hand[0] != null) {
				suitChar = hand[0].getSuit();
				suit = convertSuit(suitChar);
				System.out.printf("%s", suits[i][suit]);
			}
			if (hand[1] != null) {
				suitChar = hand[1].getSuit();
				suit = convertSuit(suitChar);
				System.out.printf("%s", suits[i][suit]);
			}
			if (hand[2] != null) {
				suitChar = hand[2].getSuit();
				suit = convertSuit(suitChar);
				System.out.printf("%s", suits[i][suit]);
			}
			if (hand[i] != null) {
				handString += hand[i].getValue();
				handString += hand[i].getSuit();
				handString += " ";
			}
			System.out.printf("\n");
		}
		
		for (int i = 0; i < 3; i++) {			// Prints out the bottom row of ranks/faces of the cards if they exist
			if (hand[i] != null) {
				rank = hand[i].getValue();
				System.out.printf("|____%s|", convertRank(rank, 1));
			}
		}
		System.out.printf("\n\n");
		
		return handString;
	}
	
	
	/**
	 * Converts the suit of the card into an array index
	 * @param suit The suit of the card
	 * @return An index for the array
	 */
	private int convertSuit(char suit) {
		
		switch (suit) {
			case 'C':
				return 0;
			case 'D':
				return 1;
			case 'H':
				return 2;
			case 'S':
				return 3;
			default:							// Throws an exception if a non-standard suit is encountered
				throw new IllegalArgumentException("Illegal suit value encountered!\n");
		}
	}
	
	
	/**
	 * Converts the rank/face of the card into a formatted string based on the position
	 * @param rank The value of the card
	 * @param position 0 for the top row, 1 for the bottom
	 * @return
	 */
	private String convertRank(int rank, int position) {
		
		if (position == 0) {			// Specifies the top row
			switch (rank) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				return Integer.toString(rank) + " ";		// Appends a space after the single-digit number
			case 10:
				return Integer.toString(rank);
			case 11:
				return "J ";
			case 12:
				return "Q ";
			case 13:
				return "K ";
			default:
				throw new IllegalArgumentException("An invalid rank was entered!\n");
			}
		}
		else {						// Otherwise, specifies the bottom row
			switch (rank) {
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				return "_" + Integer.toString(rank);		// Prefixes an underscore before the single-digit number
			case 10:
				return Integer.toString(rank);
			case 11:
				return "_J";
			case 12:
				return "_Q";
			default:
				return "_K";
			}
		}
	}
}
