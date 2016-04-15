/**
 * @author Brandon S. Hang
 * @version 1.300
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This class is responsible for printing messages to the console (I.e., it interfaces
 * the player with the program).  Each print method returns some type of information
 * in order to convey success or failure as opposed to just being void.
 */

package baccarat;

import java.util.regex.Pattern;

public class GameUI {
	
	/**
	 * Parses the player's input for a valid integer.  Returns 0 if invalid.  Returns the
	 * player's entire balance if the integer exceeds it.
	 * @param input The player's input as a string
	 * @param playerBalance The player's current balance
	 * @return The player's bet
	 */
	public int getBet(String input, int playerBalance) {
		
		int bet = 0;
		
		try {
			bet = Integer.parseInt(input);
		}
		catch (NumberFormatException nfe) {
			System.out.printf("You entered something other than an integer!  The bet will default to $0.\n");
			bet = 0;
		}
		if (bet < 0) {
			System.out.printf("You can't bet a negative amount!  The bet will default to $0.\n");
			bet = 0;
		}
		if (bet > playerBalance) {				// Bets the entire player's balance if the parsed integer exceeds it
			System.out.printf("You don't have enough money!  I guess that means you're going all in!\n");
			bet = playerBalance;
		}
		
		return bet;
	}
	
	
	/**
	 * Prints the player's record and balance while prompting them for their bet.
	 * @param record The player's record
	 * @param playerBalance The player's balance
	 * @return True if the prompt was successfully displayed, false otherwise
	 */
	public boolean promptPlayer(String record, int playerBalance) {
		
		if (Pattern.matches("^[0-9]*W-[0-9]*L-[0-9]*T$", record)) {		// Regular expression checks if the record is properly formatted
			System.out.printf("Current Record: %s\n", record);
			System.out.printf("Current Balance: $%,d\n", playerBalance);
			System.out.printf("How much would you like to bet?  Enter the amount as an integer.\n");
			System.out.printf("\tBET: $");
			
			return true;
		}
		else {
			System.out.printf("An improper record format was used!\n");
			System.out.printf("Unable to display the player's record.\n");
			
			return false;
		}
	}
	
	
	/**
	 * Prints either the player's or the banker's initially drawn hand
	 * @param hand The initial hand
	 * @param mode 0 for the player, 1 for the banker
	 * @return True if the initial hand was successfully displayed, false otherwise
	 */
	public boolean printInitialHand(Card[] hand, int mode) {
		
		if (mode < 0 || mode > 1) {						// Error message if an invalid mode parameter is used
			throw new IllegalArgumentException("Can't figure out whose hand this is!\n");
		}
		if (hand[0] == null || hand[1] == null) {					// Error message if any initial card is null
			System.out.printf("\nSomebody drew the joker!  One or more initial cards were null!\n");
			System.out.printf("Unable to display the initial cards drawn.\n");
			
			return false;
		}
		
		if (mode == 0) {
			int card1 = hand[0].getValue();
			int card2 = hand[1].getValue();
			
			System.out.printf("You draw a");
			
			if (card1 == 1 || card1 == 8) {				// Prints "an" if the card is an ace or an 8
				System.out.printf("n");
			}
			System.out.printf(" %s and a", hand[0]);
			if (card2 == 1 || card1 == 8) {				// Prints "an" if the card is an ace or an 8
				System.out.printf("n");
			}
			System.out.printf(" %s.\n", hand[1]);
			
			return true;
		}
		else {
			int card1 = hand[0].getValue();
			int card2 = hand[1].getValue();
			
			System.out.printf("The banker draws a");
			
			if (card1 == 1 || card1 == 8) {				// Prints "an" if the card is an ace or an 8
				System.out.printf("n");
			}
			System.out.printf(" %s and a", hand[0]);
			if (card2 == 1 || card1 == 8) {				// Prints "an" if the card is an ace or an 8
				System.out.printf("n");
			}
			System.out.printf(" %s.\n\n", hand[1]);
			
			return true;
		}
	}
	
	
	/**
	 * Checks the deck size to determine if the cut card was found
	 * @param deck The baccarat deck
	 * @return True if the cut card was found, false otherwise
	 */
	public boolean isLastRound(Deck deck) {
		
		if (deck == null) {						// Error message if the deck is null
			System.out.printf("\nUnable to check the size of a null deck!\n");
			return false;
		}
		
		int deckSize = deck.getSize();
		
		if (deckSize < 6) {						// Only prints a message if the cut card was found
			System.out.printf("\nLast round!  The cut-card was encountered!\n");
			return true;
		}
		else {
			return false;
		}
	}
	
	
	/**
	 * Prints the extra drawn card for either the player or banker
	 * @param card The card to display
	 * @param mode Specifies either the player or banker; 0 for the player, 1 for the banker
	 * @return True if the extra card was successfully printed, false otherwise
	 */
	public boolean printExtraCard(Card card, int mode) {
		
		if (mode < 0 || mode > 1) {					// Error message if an invalid mode parameter was used
			System.out.printf("Cannot print the card of a non-existent contestant!\n");
			return false;
		}
		if (card == null) {							// Error message if the card is null
			System.out.printf("The joker was drawn!  The drawn card was null!\n");
			System.out.printf("Unable to display the extra card\n");
			
			return false;
		}
		
		if (mode == 0) {
			System.out.printf("You draw a %s.\n", card);
		}
		else {
			System.out.printf("The banker draws a %s.\n", card);
		}
		
		return true;
	}
	
	
	/**
	 * Determines the winner of the coup
	 * @param playerPts The player's points
	 * @param bankerPts The banker's points
	 * @return 1 if the player wins, -1 if the banker wins, 0 if there is a tie
	 */
	public boolean printNatural(int playerPts, int bankerPts) {
		
		if (playerPts < 0 || playerPts > 9) {					// Error message if the player has an invalid number of points
			System.out.printf("The player has an illegal amount of points!\n");
			return false;
		}
		if (bankerPts < 0 || bankerPts > 9) {					// Error message if the banker has an invalid number of points
			System.out.printf("The banker has an illegal amount of points!\n");
			return false;
		}
		
		if (playerPts < 8 && bankerPts < 8) {
			System.out.printf("Neither you nor the banker drew a \"natural.\"\n");
			System.out.printf("Consulting the tableau of drawing rules...\n");
			
			return false;
		}
		else if (playerPts >= 8 && bankerPts < 8) {
			System.out.printf("You drew a \"natural!\"");
		}
		else if (bankerPts >= 8 && playerPts < 8) {
			System.out.printf("The banker drew a \"natural!\"");
		}
		else {
			System.out.printf("Both you and the banker drew a \"natural!\"");
		}
		
		System.out.printf("  Finishing this coup...\n");
		return true;
	}
	
	
	/**
	 * Determines the winner of the coup
	 * @param playerPts The player's points
	 * @param bankerPts The banker's points
	 * @return 1 if the player wins, -1 if the banker wins, 0 if a tie occurs
	 */
	public int determineWinner(Integer playerPts, Integer bankerPts) {
		
		if (playerPts < 0 || playerPts > 9) {					// Error message if the player has an invalid number of points
			throw new IllegalArgumentException("The player has an illegal amount of points!\n");
		}
		if (bankerPts < 0 || bankerPts > 9) {					// Error message if the banker has an invalid number of points
			throw new IllegalArgumentException("The banker has an illegal amount of points!\n");
		}
		
		return playerPts.compareTo(bankerPts);
	}
	
	
	/**
	 * Prints out the point totals of the player's and baker's hands
	 * @param playerPts The player's points
	 * @param bankerPts The banker's points
	 * @return True if the points were successfully printed, false otherwise
	 */
	public boolean printCoup(int playerPts, int bankerPts) {
		
		if (playerPts < 0 || playerPts > 9) {					// Error message if the player has an invalid number of points
			System.out.printf("The player has an illegal amount of points!\n\n");
			return false;
		}
		if (bankerPts < 0 || bankerPts > 9) {					// Error message if the banker has an invalid number of points
			System.out.printf("The banker has an illegal amount of points!\n\n");
			return false;
		}
		
		if (playerPts == 1) {
			System.out.printf("You have %d point.  ", playerPts);		// Prints the singluar noun for 1 point
		}
		else {
			System.out.printf("You have %d points.  ", playerPts);		// Prints the plural noun for multiple or zero points
		}
		
		if (bankerPts == 1) {
			System.out.printf("The banker has %d point.\n\n", bankerPts);		// Prints the singluar noun for 1 point
		}
		else {
			System.out.printf("The banker has %d points.\n\n", bankerPts);		// Prints the plural noun for multiple or zero points
		}
		return true;
	}
	
	
	/**
	 * Prints the introduction screen
	 * @return True if the intro was successfully printed
	 */
	public boolean printIntro() {
		
		System.out.printf("\n                            Brandon S. Hang\n");
		System.out.printf("                                presents\n");
		System.out.printf(" ______   _______  _______  _______  _______  _______  _______ _________\n"
				+ "(  ___ \\ (  ___  )(  ____ \\(  ____ \\(  ___  )(  ____ )(  ___  )\\__   __/\n"
				+ "| (   ) )| (   ) || (    \\/| (    \\/| (   ) || (    )|| (   ) |   ) (\n"
				+ "| (__/ / | (___) || |      | |      | (___) || (____)|| (___) |   | |\n"
				+ "|  __ (  |  ___  || |      | |      |  ___  ||     __)|  ___  |   | |\n"
				+ "| (  \\ \\ | (   ) || |      | |      | (   ) || (\\ (   | (   ) |   | |\n"
				+ "| )___) )| )   ( || (____/\\| (____/\\| )   ( || ) \\ \\__| )   ( |   | |\n"
				+ "|/ \\___/ |/     \\|(_______/(_______/|/     \\||/   \\__/|/     \\|   )_(\n\n");
		
		return true;
	}
	
	
	/**
	 * Prints the game over screen
	 * @return True if the game over was successfully printed
	 */
	public boolean printGameOver() {
		
		System.out.printf(" ______\n"
				+ "|4    _|____\n"
				+ "| %%  |A     |   ___   __   _  _  ____     __   _  _  ____  ____\n"
				+ "|    | ,'`. |  / __) / _\\ ( \\/ )(  __)   /  \\ / )( \\(  __)(  _ \\\n"
				+ "| %%  |(_,._)| ( (_ \\/    \\/ \\/ \\ ) _)   (  O )\\ \\/ / ) _)  )   /\n"
				+ "|____|  /\\  |  \\___/\\_/\\_/\\_)(_/(____)   \\__/  \\__/ (____)(__\\_)\n"
				+ "     |_____A|\n");
		
		return true;
	}
	
	
	public boolean endGame(Player player) {
		
		if (player == null) {
			System.out.printf("The player doesn't exist!\n");
			return false;
		}
		else {
			int funds = player.checkBalance();
			
			if (funds == 0) {
				System.out.printf("Looks like you gambled big but lost everything.\n");
				System.out.printf("At least you still have the shirt on your back!\n");
			}
			else if (funds > 10000) {
				System.out.printf("Congratulations!  You walk away with $%,d!\n", funds);
				System.out.printf("That's $%,d more than what you started with!\n", funds - 10000);
			}
			else {
				System.out.printf("Well, that game could have gone better.\n");
				System.out.printf("You finish with $%,d, a loss of $%,d.\n", funds, 10000 - funds);
			}
			
			return true;
		}
	}
}
