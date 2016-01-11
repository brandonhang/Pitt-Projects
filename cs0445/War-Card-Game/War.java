/**
 * @author Brandon Hang
 * @version 1.00
 * CS 0445
 * Assignment 1
 * September 23, 2015
 * 
 * Requires MultiDS.java, Card.java
 * 
 * This class simulates the card game War with two
 *   non-controllable players. The number of
 *   rounds that are played is entered through
 *   the command line and must be an integer
 *   greater than or equal to 1.
 *   
 */

public class War {

	private static final int DECK_SIZE = 52;				// Static final variable denoting deck size
	
	private static int roundsCompleted = 0;					// Static variable holding the number of rounds completed
	
	private static MultiDS<Card> deck = new MultiDS<Card>(DECK_SIZE);					// Creates the initial deck
	private static MultiDS<Card> playerOneHand = new MultiDS<Card>(DECK_SIZE);			// Creates player 1's hand
	private static MultiDS<Card> playerTwoHand = new MultiDS<Card>(DECK_SIZE);			// Creates player 2's hand
	private static MultiDS<Card> playerOneDiscard = new MultiDS<Card>(DECK_SIZE);		// Creates player 1's discard pile
	private static MultiDS<Card> playerTwoDiscard = new MultiDS<Card>(DECK_SIZE);		// Creates player 2's discard pile
	private static MultiDS<Card> sharedPile = new MultiDS<Card>(DECK_SIZE);				// Creates a common discard pile
	
	
	public static void main(String[] args) {
		
		int numRounds;							// Variable to store the number of rounds to be played
		
		if (args.length == 0)					// Throws an exception if args is empty (no command-line argument entered)
			throw new IllegalArgumentException("Please enter the number of rounds.");
		if (!args[0].matches("[0-9]+"))			// Throws an exception if the string contains symbols or letters
			throw new NumberFormatException("Please enter an integer greater than 0.");
		else {
			numRounds = Integer.parseInt(args[0]);			// Reads and stores the string as an integer
			if (numRounds < 1)								// Throws an exception if the number of rounds is less than 1
				throw new IllegalArgumentException("The number of rounds must be greater than 0.");
			}
		
		System.out.println("\n\t\t\"Appear weak when you are strong, and strong when you are weak.\"");	// Quote from
		System.out.println("\t\t-Sun Tzu, The Art of War");												//   Sun Tzu
		
		System.out.println("\nThis is War: The Digital Card Game!\n");				// Greeting message for the game
		
		createDeck(deck);													// Calls the method to create the deck
		
		System.out.println("Shuffling the deck...\n");						// Message for shuffling the deck
		
		deck.shuffle();														// Calls the method to shuffle the deck
		
		System.out.println("Dealing cards to the players...\n");			// Message for dealing cards to players
		
		dealCards(deck, playerOneHand, playerTwoHand);						// Calls the method to deal cards
		
		System.out.println("Player 1's hand:");						// Player 1 message
		System.out.println(playerOneHand.toString());				// Displays player 1's hand
		
		System.out.println("\nPlayer 2's hand:");					// Player 2 message
		System.out.println(playerTwoHand.toString());				// Displays player 2's hand
		
		System.out.println("\nLet the war begin!\n");				// Message to begin the game
		
		do {
			drawCards();								// Calls the method to draw cards from each player
			roundsCompleted++;							// Increments the number of rounds by 1
		} while (roundsCompleted != numRounds);		// Game ends when the number of rounds is completed unless other
													//   victory conditions are met
		int p1Total = playerOneHand.size() + playerOneDiscard.size();		// Sums player 1's hand and discard pile
		int p2Total = playerTwoHand.size() + playerTwoDiscard.size();		// Sums player 2's hand and discard pile
		
		System.out.println("\nAfter " + numRounds + " rounds of battle:"	// Message indicating number of rounds finished
				+ "\n\tPlayer 1 has " + p1Total + " cards."				// Message displaying player 1's card total
				+ "\n\tPlayer 2 has " + p2Total + " cards.");			// Message displaying player 2's card total
		
		if (p1Total > p2Total)											// Conditional if player 1 has more cards
			System.out.println("Player 1 is triumphant!");				// Victory message for player 1
		else if (p1Total < p2Total)										// Conditional if player 2 has more cards
			System.out.println("Player 2 is triumphant!");				// Victory message for player 2
		else
			System.out.println("Looks like it's a stalemate...");		// Message for a tie
		
		System.out.println("\nGAME OVER\n"								// Game over message
				+ "\n--------------------------------------------\n");	// Divider
		
		System.exit(0);													// Exits the program
	}
	
	
	/**
	 * Fills the MultiDS<Card> object with a standard French playing card deck (4 suits, 13 ranks).
	 * @param deck The initial deck
	 */
	private static void createDeck(MultiDS<Card> deck) {
		
		for (int s = 0; s < Card.Suits.values().length; s++)				// Nested loop that fills the deck with cards
			for (int r = 0; r < Card.Ranks.values().length; r++)			//   using the enum types from the Card class
				deck.addItem(new Card(Card.Suits.values()[s], Card.Ranks.values()[r]));	
	}
	
	
	/**
	 * Deals the cards from the MultiDS<card> object by removing cards and alternately adding them to player 1
	 * and player 2's hands
	 * @param deck The initial deck
	 * @param p1 Player 1's hand
	 * @param p2 Player 2's hand
	 */
	private static void dealCards(MultiDS<Card> deck, MultiDS<Card> p1, MultiDS<Card> p2) {
		
		for (int i = 0; i < DECK_SIZE; i += 2) {			// Loop that distributes the cards from the deck
			p1.addItem(deck.removeItem());					// Adds a removed card from the deck to player 1's hand
			p2.addItem(deck.removeItem());					// Adds a removed card from the deck to player 2's hand
		}
	}
	
	
	/**
	 * Method that draws cards from players' hands and compares them. Also calls a method to check that 
	 * players do not have empty hands or no cards at all.
	 */
	private static void drawCards() {
		
		checkCardCount();						// Calls the method to check players' number of cards is not 0
		
		Card p1DrawnCard = playerOneHand.removeItem();		// Removes a card from player 1's hand and stores it
		Card p2DrawnCard = playerTwoHand.removeItem();		// Removes a card from player 2's hand and stores it
		
		sharedPile.addItem(p1DrawnCard);					// Adds player 1's removed card to a shared pile
		sharedPile.addItem(p2DrawnCard);					// Adds player 2's removed card to a shared pile
		
		compareCards(p1DrawnCard, p2DrawnCard);			// Calls the method to compare the drawn cards
	}
	
	
	/**
	 * Compares two drawn cards. Recursively calls drawCards() in the case of a tie.
	 * @param p1 Player 1's drawn card
	 * @param p2 Player 2's drawn card
	 */
	private static void compareCards(Card p1, Card p2) {
		
		int battleResult = p1.compareTo(p2);				// Compares the drawn cards; returns -1, 0, or 1
		int sharedPileSize = sharedPile.size();				// Stores the size of the discard pile
		
		if (battleResult > 0) {												// Conditional if battleResult is 1
			System.out.println("Player 1 wins: " + p1 + " beats " + p2);	// Message stating player 1's card won
			for (int i = 0; i < sharedPileSize; i++)						// Loop that adds all of the cards from the
				playerOneDiscard.addItem(sharedPile.removeItem());			//   shared pile into player 1's discard pile
		}
		else if (battleResult < 0) {										// Conditional if battleResult is -1
			System.out.println("Player 2 wins: " + p2 + " beats " + p1);	// Message stating player 2's card won
			for (int i = 0; i < sharedPileSize; i++)						// Loop that adds all of the cards from the
				playerTwoDiscard.addItem(sharedPile.removeItem());			//   shared pile into player 2's discard pile
		}
		else {																// Conditional for a tie (battleResult = 0)
			System.out.println("WAR: " + p1 + " ties with " + p2);			// Message stating the cards tied
			
			checkCardCount();											// Checks players' hands before drawing more cards
			
			Card p1CardAtRisk = playerOneHand.removeItem();				// Removes a non-compared card from player 1
			Card p2CardAtRisk = playerTwoHand.removeItem();				// Removes a non-compared card from player 2
			
			sharedPile.addItem(p1CardAtRisk);							// Adds player 1's removed card to the shared pile
			sharedPile.addItem(p2CardAtRisk);							// Adds player 2's removed card to the shared pile
			
			System.out.println("\tPlayer 1's " + p1CardAtRisk + " and Player 2's "		// Message displaying what cards
					+ p2CardAtRisk + " are in danger!");								//   are at stake
			
			drawCards();						// Recursively calls the drawCards() method
		}
	}
	
	
	/**
	 * Checks the players' hands to see if they contain cards. If a player's hand contains no cards, the discard  
	 * pile is then checked. If their discard pile contains cards, they are added back to their hand and shuffled.   
	 * If the player has no cards in either their hand or discard pile, that player loses and the game ends.
	 */
	private static void checkCardCount() {
		
		int p1DiscardSize = playerOneDiscard.size();			// Stores the size of player 1's discard pile
		int p2DiscardSize = playerTwoDiscard.size();			// Stores the size of player 2's discard pile
		
		if (playerOneHand.size() == 0) {						// Conditional if player 1's hand is empty
			System.out.println("\tReloading and shuffling Player 1's discard pile.");	// Message for fetching the pile
			if (p1DiscardSize == 0) {											// Conditional if the discard pile is empty
				System.out.println("\nPlayer 1 has no cards remaining!"			// Message saying player 1 has no cards
						+ "\nPlayer 2 is victorious!\n");						//   along with player 2's victory
				System.out.println("GAME OVER\n"								// Game over message
						+ "\n--------------------------------------------\n");	// Divider
				System.exit(0);									// Exits the program
			}
			else {											// Conditional if player 1 has cards in their discard pile
				for (int i = 0; i < p1DiscardSize; i++)						// Loop that adds cards back into player 1's
					playerOneHand.addItem(playerOneDiscard.removeItem());	//   hand by removing them from the pile
				playerOneHand.shuffle();					// Shuffles player 1's hand
			}
		}
		if (playerTwoHand.size() == 0) {						// Conditional if player 2's hand is empty
			System.out.println("\tReloading and shuffling Player 2's discard pile.");	// Message for fetching the pile
			if (p2DiscardSize == 0) {											// Conditional if the discard pile is empty
				System.out.println("\nPlayer 2 has no cards remaining!"			// Message saying player 2 has no cards
						+ "\nPlayer 1 is victorious!\n");						//   along with player 1's victory
				System.out.println("GAME OVER\n"								// Game over message
						+ "\n--------------------------------------------\n");	// Divider
				System.exit(0);									// Exits the program
			}
			else {											// Conditional if player 2 has cards in their discard pile
				for (int i = 0; i < p2DiscardSize; i++)						// Loop that adds cards back into player 2's
					playerTwoHand.addItem(playerTwoDiscard.removeItem());	//   hand by removing them from the pile
				playerTwoHand.shuffle();					// Shuffles player 2's hand
			}
		}
	}
}
