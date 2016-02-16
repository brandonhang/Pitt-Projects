/**
 * @author Brandon S. Hang
 * @version 1.500
 * CS 1632
 * Deliverable 2
 * February 16, 2016
 * 
 * This class creates and executes the game Coffee Maker Quest.  It first creates
 * a QuestHouse object that contains a number of QuestRooms (think like a linked
 * list with nodes).  Individual QuestRoom objects are then passed as arguments
 * along with user input to run the game.
 */

package coffee_hunter_pro;

import java.util.Scanner;

public class CoffeeMakerQuest {
	
	public static void main(String[] args) {
		
		CoffeeGame game = new CoffeeGame();			// Starts a new CoffeeMakerQuest game
		QuestHouse roomComplex = new QuestHouse();		// Creates a new QuestHouse object
		roomComplex = game.buildRooms(roomComplex);
		QuestRoom currentRoom = roomComplex.getInitialRoom();		// Creates a new QuestRoom object from the initial QuestHouse room
		
		@SuppressWarnings("resource")
		Scanner scan = new Scanner(System.in);
		String userInput;				// Holds the user's input
		boolean didNotDrinkYet = true;			// Boolean denoting whether the command "D" was entered yet
		
		game.printTitles(0);				// Prints the game's opening title
		System.out.printf("%s\n%s", currentRoom, game.INSTRUCTIONS);		// Prints the initial room of the game
		
		while (didNotDrinkYet) {			// Loops as long as the command "D" has not been entered
			userInput = scan.nextLine();
			if (userInput.equalsIgnoreCase("D")) {		// When "D" is entered, the game continues to the various end-game scenarios
				didNotDrinkYet = false;
			}
			else {					// Otherwise, the game parses the user's input to determine what command was entered
				currentRoom = game.parseUserInput(userInput, currentRoom);
			}
		}
		game.checkVictoryConditions();			// After "D" is entered, the game determines if the player wins or loses
		game.printTitles(1);				// Prints the game's closing title
		
		System.exit(0);
	}
}
