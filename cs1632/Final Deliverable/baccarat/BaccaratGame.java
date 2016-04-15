/**
 * @author Brandon S. Hang
 * @version 1.400
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This class simulates the game of baccarat by implementing each of the other created
 * classes.  The game is a modified version of "Punto banco" in that it uses only 1
 * deck as opposed to 6 or 8 decks.  Additionally, the cut-card is placed before the
 * fifth-last card in the deck rather than the seventh-last.  Some terminology:
 *      Punto banco - The most popular variant of baccarat played at casinos
 *      Coup - Name of the round of drawn cards
 *      Natural - The event where an initial hand has 8 or 9 points
 *      Tableau - A "board" of drawing rules to consult
 *      Égalité - French word for equality
 *      Cut-card - Signals the last coup of the game.
 */

package baccarat;

import java.util.Scanner;

public class BaccaratGame {
	
	private static Baccarat puntoBanco;
	private static Tableau tableau;
	private static Player player;
	private static Deck deck;
	private static GameUI ui;
	private static Scanner scan;
	private static CardPrint cardPrinter;
	private static Card[] playerHand;
	private static Card[] bankerHand;
	private static int bet;
	
	
	public static void main(String[] args) {
		
		puntoBanco = new Baccarat();
		tableau = new Tableau();
		player = new Player();
		deck = new Deck();
		deck.fill();
		deck.shuffle();
		cardPrinter = new CardPrint();
		ui = new GameUI();
		scan = new Scanner(System.in);
		String input;
		bet = 0;
		
		ui.printIntro();
		
		while (deck.getSize() > 5 && player.checkBalance() > 0) {			// Loops while the cut-card is not encountered or the player has funds
			ui.promptPlayer(player.printRecord(), player.checkBalance());			// Prints the player prompt
			input = scan.nextLine();
			bet = ui.getBet(input, player.checkBalance());				// Gets the bet from the player
			
			playerHand = new Card[3];					// Resets both the player's and banker's hands
			bankerHand = new Card[3];
			puntoBanco.drawInitialHand(deck, playerHand, bankerHand);			// Draws the initial hands
			ui.isLastRound(deck);							// Checks if the cut-card was found
			
			ui.printInitialHand(playerHand, 0);					// Prints the player's and banker's initial hands
			cardPrinter.printHand(playerHand);
			ui.printInitialHand(bankerHand, 1);
			cardPrinter.printHand(bankerHand);
			
			int playerPts = puntoBanco.getPointsInHand(playerHand);			// Prints the player's and banker's initial points
			int bankerPts = puntoBanco.getPointsInHand(bankerHand);
			ui.printCoup(playerPts, bankerPts);
			
			if (!ui.printNatural(playerPts, bankerPts)) {			// Conditional if no "natural" was drawn
				if (tableau.checkPlayerDraw(playerPts)) {					// Conditional if the player needs to draw a card
					playerHand[2] = deck.draw();
					int thirdCard = playerHand[2].getValue();
					playerPts = puntoBanco.getPointsInHand(playerHand);
					ui.printExtraCard(playerHand[2], 0);							// Prints the player's extra card
					cardPrinter.printHand(new Card[]{playerHand[2], null, null});
					
					if (tableau.checkBankerDraw(bankerPts, thirdCard)) {		// Conditional if the banker needs to draw a card
						bankerHand[2] = deck.draw();
						bankerPts = puntoBanco.getPointsInHand(bankerHand);
						ui.printExtraCard(bankerHand[2], 1);							// Prints the banker's extra card
						cardPrinter.printHand(new Card[]{bankerHand[2], null, null});
					}
				}
				
				ui.printCoup(playerPts, bankerPts);				// Prints the resulting points after drawing extra card(s)
			}
			
			int result = ui.determineWinner(playerPts, bankerPts);			// Determines the winner of the coup
			switch (result) {
				case 1:
					System.out.printf("You win this coup!  You win double your bet!\n\n");
					player.addFunds(2 * bet);
					player.addWin();
					break;
				case -1:
					System.out.printf("You lose this coup!  You lose your bet...\n\n");
					player.subtractFunds(bet);
					player.addLoss();
					break;
				default:
					System.out.printf("Égalité!  Ties win eight times the bet!\n\n");
					player.addFunds(8 * bet);
					player.addTie();
					break;
			}
		}
		
		ui.endGame(player);
		ui.printGameOver();
		
		System.exit(0);
	}
}
