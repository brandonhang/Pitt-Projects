/**
 * @author Brandon S. Hang
 * @version 1.500
 * CS 1632
 * Deliverable 6
 * April 19, 2016
 * 
 * This is the JUnit test plan for the GameUI.java class.  
 */

package baccarat_test_suite;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

import baccarat.GameUI;
import baccarat.Deck;
import baccarat.Card;
import baccarat.Player;

public class GameUITestPlan {
	
	private GameUI ui = new GameUI();
	private Deck mockDeck = Mockito.mock(Deck.class);
	private Card mockCard = Mockito.mock(Card.class);
	
	// 0================================================================0
	// |                   BET STRING PARSING TESTS                     |
	// 0================================================================0
	
	/**
	 * Tests that a parsable string of a valid integer returns the correct amount
	 */
	@Test
	public void testParsableString() {
		
		assertEquals(ui.getBet("24", 10000), 24);
	}
	
	/**
	 * Tests that a parsable string of a negative integer returns 0
	 */
	@Test
	public void testNegativeIntegerString() {
		
		assertEquals(ui.getBet("-1234", 10000), 0);
	}
	
	/**
	 * Tests that a parsable string of more than the player's balance returns the player's balance
	 */
	@Test
	public void testParseMoreFundsThanAlloted() {
		
		assertEquals(ui.getBet("33643", 10000), 10000);
	}
	
	/**
	 * Tests that an unparsable string returns 0
	 */
	@Test
	public void testUnparsableString() {
		
		assertEquals(ui.getBet("Farewell, Kobe!", 10000), 0);
	}
	
	/**
	 * Tests that an unparsable decimal returns 0
	 */
	@Test
	public void testUnparsableDecimal() {
		
		assertEquals(ui.getBet("3.141592653", 10000), 0);
	}
	
	// 0================================================================0
	// |                  PRINT PLAYER PROMPT TESTS                     |
	// 0================================================================0
	
	/**
	 * Tests that printing the player prompt with a properly formatted record returns true
	 */
	@Test
	public void testPrintPlayerPrompt() {
		
		assertTrue(ui.promptPlayer("0W-0L-0T", 10000));
	}
	
	/**
	 * Tests that printing the player prompt with an improperly formatted record returns false
	 */
	@Test
	public void testPrintPlayerImproperRecordFormat() {
		
		assertFalse(ui.promptPlayer("79-3", 10000));
	}
	
	// 0================================================================0
	// |                   INITIAL DRAWN HAND TESTS                     |
	// 0================================================================0
	
	/**
	 * Tests that printing the player's initial hand with valid cards returns true
	 */
	@Test
	public void testPrintInitialPlayerHand() {
		
		Card mockCard2 = Mockito.mock(Card.class);
		Card[] mockHand = {mockCard, mockCard2, null};
		
		for (int i = 1; i < 14; i++) {
			for (int j = 1; j < 14; j++) {
				Mockito.when(mockCard.getValue()).thenReturn(i);
				Mockito.when(mockCard2.getValue()).thenReturn(j);
				assertTrue(ui.printInitialHand(mockHand, 0));
			}
		}
	}
	
	/**
	 * Tests that printing the banker's initial hand with valid cards returns true
	 */
	@Test
	public void testPrintInitialBankerHand() {
		
		Card mockCard2 = Mockito.mock(Card.class);
		Card[] mockHand = {mockCard, mockCard2, null};
		
		for (int i = 1; i < 14; i++) {
			for (int j = 1; j < 14; j++) {
				Mockito.when(mockCard.getValue()).thenReturn(i);
				Mockito.when(mockCard2.getValue()).thenReturn(j);
				assertTrue(ui.printInitialHand(mockHand, 1));
			}
		}
	}
	
	/**
	 * Tests that printing an initial hand with invalid cards returns false
	 */
	@Test
	public void testPrintNullInitialHand() {
		
		Card[] nullHand = {null, null, null};
		assertFalse(ui.printInitialHand(nullHand, 0));
	}
	
	/**
	 * Tests that an invalid player/banker parameter throws an exception
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testPrintHandInvalidParameter() {
		
		Card[] mockHand = {mockCard, mockCard, null};
		ui.printInitialHand(mockHand, 23);
	}
	
	// 0================================================================0
	// |                        CUT-CARD TESTS                          |
	// 0================================================================0
	
	/**
	 * Tests that the cut card is found when the deck size is 3
	 */
	@Test
	public void testCutCardFoundCommon() {
		
		Mockito.when(mockDeck.getSize()).thenReturn(3);
		assertTrue(ui.isLastRound(mockDeck));
	}
	
	/**
	 * Tests that the cut card is found when the deck size is 5
	 */
	@Test
	public void testCutCardFoundEdge() {
		
		Mockito.when(mockDeck.getSize()).thenReturn(5);
		assertTrue(ui.isLastRound(mockDeck));
	}
	
	/**
	 * Tests that the cut card is not found when the deck size is 35
	 */
	@Test
	public void testCutCardNotFoundCommon() {
		
		Mockito.when(mockDeck.getSize()).thenReturn(35);
		assertFalse(ui.isLastRound(mockDeck));
	}
	
	/**
	 * Tests that the cut card is not found when the deck size is 6
	 */
	@Test
	public void testCutCardNotFoundEdge() {
		
		Mockito.when(mockDeck.getSize()).thenReturn(6);
		assertFalse(ui.isLastRound(mockDeck));
	}
	
	/**
	 * Tests that a null deck throws returns false
	 */
	@Test
	public void testCutCardNullDeck() {
		
		assertFalse(ui.isLastRound(null));
	}
	
	// 0================================================================0
	// |                    PRINT EXTRA CARD TESTS                      |
	// 0================================================================0
	
	/**
	 * Tests that printing the player's third card returns true when the card is valid
	 */
	@Test
	public void testPrintPlayerExtraCard() {
		
		assertTrue(ui.printExtraCard(mockCard, 0));
	}
	
	/**
	 * Tests that printing the player's third card throws returns false when the card is invalid
	 */
	@Test
	public void testPrintPlayerInvalidExtraCard() {
		
		assertFalse(ui.printExtraCard(null, 0));
	}
	
	/**
	 * Tests that printing the banker's third card returns true when the card is valid
	 */
	@Test
	public void testPrintBankerExtraCard() {
		
		assertTrue(ui.printExtraCard(mockCard, 1));
	}
	
	/**
	 * Tests that printing the banker's third card returns false when the card is invalid
	 */
	@Test
	public void testPrintBankerInvalidExtraCard() {
		
		assertFalse(ui.printExtraCard(null, 1));
	}
	
	/**
	 * Tests that an illegal print card parameter returns false
	 */
	@Test
	public void testInvalidPrintExtraCardParameter() {
		
		assertFalse(ui.printExtraCard(mockCard, 34));
	}
	
	// 0================================================================0
	// |                    "NATURAL" STATUS TESTS                      |
	// 0================================================================0
	
	/**
	 * Tests that printing the "natural" status returns true when only the player has a "natural"
	 */
	@Test
	public void testPrintNaturalPlayerOnly() {
		
		for (int i = 0; i < 8; i++) {
			assertTrue(ui.printNatural(8, i));
			assertTrue(ui.printNatural(9, i));
		}
	}
	
	/**
	 * Tests that printing the "natural" status returns true when only the banker has a "natural"
	 */
	@Test
	public void testPrintNaturalBankerOnly() {
		
		for (int i = 0; i < 8; i++) {
			assertTrue(ui.printNatural(i, 8));
			assertTrue(ui.printNatural(i, 9));
		}
	}
	
	/**
	 * Tests that printing the "natural" status returns true when both the player and the banker
	 * have a "natural"
	 */
	@Test
	public void testPrintNaturalBoth() {
		
		assertTrue(ui.printNatural(8, 8));
		assertTrue(ui.printNatural(8, 9));
		assertTrue(ui.printNatural(9, 8));
		assertTrue(ui.printNatural(9, 9));
	}
	
	/**
	 * Tests that printing the "natural" status returns false when neither the player nor the
	 * banker have a "natural"
	 */
	@Test
	public void testPrintNaturalNeither() {
		
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				assertFalse(ui.printNatural(i, j));
			}
		}
	}
	
	/**
	 * Tests that printing the "natural" status of unnatural numbers (heh heh) returns false
	 */
	@Test
	public void testPrintNaturalError() {
		
		assertFalse(ui.printNatural(23, 5));
		assertFalse(ui.printNatural(6, -18));
		assertFalse(ui.printNatural(-81, 52));
		assertFalse(ui.printNatural(117, -32));
	}
	
	// 0================================================================0
	// |                      COUP WINNER TESTS                         |
	// 0================================================================0
	
	/**
	 * Tests conditions where the player wins the coup
	 */
	@Test
	public void testPlayerWin() {
		
		for (int i = 0; i < 9; i++) {
			assertEquals(ui.determineWinner(i + 1, i), 1);
		}
	}
	
	/**
	 * Tests conditions where the player loses the coup
	 */
	@Test
	public void testPlayerLoss() {
		
		for (int i = 0; i < 9; i++) {
			assertEquals(ui.determineWinner(i, i + 1), -1);
		}
	}
	
	/**
	 * Tests conditions where the player ties the coup
	 */
	@Test
	public void testPlayerTie() {
		
		for (int i = 0; i < 10; i++) {
			assertEquals(ui.determineWinner(i, i), 0);
		}
	}
	
	/**
	 * Tests conditions where the player has too many points
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalCoupConditionsPlayerOver() {
		
		ui.determineWinner(56, 5);
	}
	
	/**
	 * Tests conditions where the player has too few points
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalCoupConditionsPlayerUnder() {
		
		ui.determineWinner(-18, 5);
	}
	
	/**
	 * Tests conditions where the banker has too many points
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalCoupConditionsBankerOver() {
		
		ui.determineWinner(5, 12);
	}
	
	/**
	 * Tests conditions where the player has too many points
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalCoupConditionsBankerUnder() {
		
		ui.determineWinner(5, -88);
	}
	
	/**
	 * Tests conditions where the player and the banker have too many points
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIllegalCoupConditionsBothOver() {
		
		ui.determineWinner(56, 85);
	}
	
	// 0================================================================0
	// |                     COUP PRINTING TESTS                        |
	// 0================================================================0
	
	/**
	 * Tests printing drawn cards with valid points returns true
	 */
	@Test
	public void testPrintCoupPoints() {
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				assertTrue(ui.printCoup(i, j));
			}
		}
	}
	
	/**
	 * Tests printing drawn cards with invalid player points returns false
	 */
	@Test
	public void testIllegalPlayerPoints() {
		
		ui.printCoup(93, 5);
	}
	
	/**
	 * Tests printing drawn cards with invalid player points returns false
	 */
	@Test
	public void testIllegalBankerPoints() {
		
		ui.printCoup(4, -82);
	}
	
	// 0================================================================0
	// |                    SCREEN PRINTING TESTS                       |
	// 0================================================================0
	
	/**
	 * Tests printing the introduction
	 */
	@Test
	public void testPrintIntro() {
		
		assertTrue(ui.printIntro());
	}
	
	/**
	 * Tests printing game over
	 */
	@Test
	public void testPrintGameOver() {
		
		assertTrue(ui.printGameOver());
	}
	
	// 0================================================================0
	// |                   END GAME SCENARIO TESTS                      |
	// 0================================================================0
	
	/**
	 * Tests printing the end game results with a net gain in money
	 */
	@Test
	public void testNormalGameOverWonMoney() {
		
		Player mockPlayer = Mockito.mock(Player.class);
		Mockito.when(mockPlayer.checkBalance()).thenReturn(18302);
		Mockito.when(mockPlayer.printRecord()).thenReturn("18W-3L-4T");
		assertTrue(ui.endGame(mockPlayer));
	}
	
	/**
	 * Tests printing the end game results with a net loss in money
	 */
	@Test
	public void testNormalGameOverLostMoney() {
		
		Player mockPlayer = Mockito.mock(Player.class);
		Mockito.when(mockPlayer.checkBalance()).thenReturn(9001);
		Mockito.when(mockPlayer.printRecord()).thenReturn("7W-12L-0T");
		assertTrue(ui.endGame(mockPlayer));
	}
	
	/**
	 * Tests printing the end game results with no change in money
	 */
	@Test
	public void testNormalGameOverSameMoney() {
		
		Player mockPlayer = Mockito.mock(Player.class);
		Mockito.when(mockPlayer.checkBalance()).thenReturn(10000);
		Mockito.when(mockPlayer.printRecord()).thenReturn("24W-24L-0T");
		assertTrue(ui.endGame(mockPlayer));
	}
	
	/**
	 * Tests printing the end game results where the player lost everything
	 */
	@Test
	public void testNormalGameOverBlewItAll() {
		
		Player mockPlayer = Mockito.mock(Player.class);
		Mockito.when(mockPlayer.checkBalance()).thenReturn(0);
		Mockito.when(mockPlayer.printRecord()).thenReturn("0W-1L-0T");
		assertTrue(ui.endGame(mockPlayer));
	}
	
	/**
	 * Tests printing the end game results with a null player
	 */
	@Test
	public void testNullPlayerGameOver() {
		
		assertFalse(ui.endGame(null));
	}
}
