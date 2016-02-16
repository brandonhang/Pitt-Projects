/**
 * @author Brandon S. Hang
 * @version 2.000
 * CS 1632
 * Deliverable 2
 * February 16, 2016
 * 
 * This class provides most of the gameplay's functionality for Coffee
 * Maker Quest.  It contains a method to first initialize the game by
 * building the QuestHouse object and its QuestRooms and other methods
 * that are called based on the user's input.  This class also retains
 * the player's current inventory and determines whether the player
 * wins or loses at the game's conclusion.
 */

package coffee_hunter_pro;

public class CoffeeGame {
								// Arrays of adjectives that describe each of the rooms in the original coffeemaker.jar
	private static final String[] ROOM_ADJ = {"Small", "Funny", "Refinanced", "Dumb", "Bloodthirsty",
	"Rough"};
	private static final String[] FURNISHING = {"sofa", "record player", "pizza", "energy drink",
		"bag of money", "air hockey table"};
	private static final String[] FURN_ADJ = {"Quaint", "Sad", "Tight", "Flat", "Beautiful", "Perfect"};
	private static final String[] N_DOOR = {"Magenta", "Beige", "Dead", "Vivacious", "Purple", null};
	private static final String[] S_DOOR = {null, "Massive", "Smart", "Slim", "Sandy", "Minimalist"};
	protected final String INSTRUCTIONS = " INSTRUCTIONS (N,S,L,I,D,H)\n";			// The instructions to be displayed on every screen print
	private static boolean coffee;				// Booleans representing the player's current inventory
	private static boolean cream;
	private static boolean sugar;
	private static String lastCommand;			// A string denoting the last command that was entered; only used for debugging.
	
	
	/**
	 * Creates a new CoffeeGame object and sets its values to false (or null)
	 */
	public CoffeeGame() {
		coffee = false;
		cream = false;
		sugar = false;
		lastCommand = null;
	}
	
	
	/**
	 * Gets the user's input and determines what command was entered, if valid
	 * @param input A string representing the player's input
	 * @param room The current QuestRoom the player is located in
	 * @return The next QuestRoom the player should be placed in (can be the same as the parameter room)
	 */
	public QuestRoom parseUserInput(String input, QuestRoom room) {
		
		if (0 == input.length()) {				// Conditional if the player does not enter anything (I.e., an empty string)
			System.out.printf("What?\n%s\n%s", room.toString(), INSTRUCTIONS);		// Prints "What?" per the requirements
			lastCommand = "Invalid input";
			return room;					// Returns the original QuestRoom that was passed in
		}
		
		input = input.toUpperCase();		// Transforms the player's input to all uppercase letters (case-insensitivity)
		
		switch (input) {
			case "N":				// Matches the command to move north
				if (room.hasNorthDoor()) {			// Conditional if the current room has a north door
					room = room.enterNorthDoor();			// Sets the return QuestRoom to the north QuestRoom
					System.out.printf("%s\n%s", room.toString(), INSTRUCTIONS);		// Prints the north room
					lastCommand = "Going north";
				}
				else {				// Otherwise, no north door/room exists
					System.out.printf("Try as you might, you cannot breach the wall.\n"		// Prints the current room
							+ "There is no door leading North.\n%s\n%s",
							room.toString(), INSTRUCTIONS);
					lastCommand = "Staying put";
				}
				break;
			case "S":				// Matches the command to move south
				if (room.hasSouthDoor()) {			// Conditional if the current room has a south door
					room = room.enterSouthDoor();			// Sets the return QuestRoom to the south QuestRoom
					System.out.printf("%s\n%s", room.toString(), INSTRUCTIONS);		// Prints the south door
					lastCommand = "Going south";
				}
				else {				// Otherwise, no south door/room exists
					System.out.printf("Try as you might, you cannot breach the wall.\n"		// Prints the current room
							+ "There is no door leading South.\n%s\n%s",
							room.toString(), INSTRUCTIONS);
					lastCommand = "Staying put";
				}
				break;
			case "L":				// Matches the command to look around the room
				lastCommand = examineRoom(room);		// Calls the method that looks around the room
				System.out.printf("%s\n%s", room.toString(), INSTRUCTIONS);			// Prints the current room
				break;
			case "I":				// Matches the command to display the player's inventory
				lastCommand = displayInventory();		// Calls the method to display the player's inventory
				System.out.printf("%s\n%s", room.toString(), INSTRUCTIONS);			// Prints the current room
				break;
			case "H":				// Matches the command to display the help panel
				lastCommand = printHelp();				// Calls the method to display the help panel
				System.out.printf("%s\n%s", room.toString(), INSTRUCTIONS);			// Prints the current room
				break;
			default:				// Otherwise, the player's input does not match any known commands
				lastCommand = "Invalid input";
				System.out.printf("What?\n%s\n%s", room.toString(), INSTRUCTIONS);		// Prints "What?" per the requirements
				break;
		}
		return room;			// Returns a QuestRoom object (either this QuestRoom or the north/south QuestRoom)
	}
	
	
	/**
	 * Searches the current QuestRoom for an item
	 * @param room The current QuestRoom the player is in
	 * @return A lastCommand String for debugging
	 */
	private String examineRoom(QuestRoom room) {
		
		String lookResult;			// The String to return for debugging purposes
		
		if (room.hasItem()) {			// Conditional if the room contains an item
			System.out.printf("There might be something here...\n");
			
			switch (room.getItem()) {
				case "coffee":				// If the room contains coffee
					System.out.printf("You found some caffeinated coffee!\n");
					coffee = true;				// Places coffee in the player's inventory
					lookResult = "Item found";
					break;
				case "cream":				// If the room contains cream
					System.out.printf("You found some creamy cream!\n");
					cream = true;				// Places cream in the player's inventory
					lookResult = "Item found";
					break;
				case "sugar":				// If the room contains sugar
					System.out.printf("You found some sweet sugar!\n");
					sugar = true;				// Places sugar in the player's inventory
					lookResult = "Item found";
					break;
				default:				// An unknown String was set as the room's item
					System.out.printf("...I guess it was just my imagination.\n");
					lookResult = "Unrecognized item";
					break;
			}
			room.collectItem();			// Effectively "removes" the item from the room
		}
		else {				// Otherwise, the room does not contain an item
			System.out.printf("You don't see anything out of the ordinary.\n");
			lookResult = "No item";
		}
		return lookResult;
	}
	
	
	/**
	 * Displays the inventory items the player currently possesses
	 * @return A lastCommand String for debugging
	 */
	private String displayInventory() {
		
		String inventory;			// A String used for debugging purposes only
		
		if (hasCoffee()) {				// Conditional if the player has coffee
			System.out.printf("You have a cup of delicious coffee.\n");
			inventory = "+coffee ";
		}
		else {							// Otherwise, the player does not have coffee
			System.out.printf("YOU HAVE NO COFFEE!\n");
			inventory = "-coffee ";
		}
		
		if (hasCream()) {				// Conditional if the player has cream
			System.out.printf("You have some fresh cream.\n");
			inventory = inventory.concat("+cream ");
		}
		else {							// Otherwise, the player does not have cream
			System.out.printf("YOU HAVE NO CREAM!\n");
			inventory = inventory.concat("-cream ");
		}
		
		if (hasSugar()) {				// Conditional if the player has sugar
			System.out.printf("You have some tasty sugar.\n");
			inventory = inventory.concat("+sugar");
		}
		else {							// Otherwise, the player does not have sugar
			System.out.printf("YOU HAVE NO SUGAR!\n");
			inventory = inventory.concat("-sugar");
		}
		return inventory;
	}
	
	
	/**
	 * Prints a help panel to the screen that displays all the valid commands of Coffee Maker Quest
	 * @return A lastCommand string used for debugging
	 */
	private String printHelp() {
		
		System.out.printf(
				"0================================ HELP ================================0\n"
				+ "| ~~~ Valid Commands ~~~                                               |\n"
				+ "|'N'     Moves the player to the next room in the North.  Only works if|\n"
				+ "|        a door leading North exists.                                  |\n"
				+ "|'S'     Moves the player to the next room in the South.  Only works if|\n"
				+ "|        a door leading South exists.                                  |\n"
				+ "|'L'     Looks around the room for items to collect.  If an item is    |\n"
				+ "|        found, it will be placed in the player's inventory.           |\n"
				+ "|'I'     Displays the player's current inventory.  Shows which items   |\n"
				+ "|        the player has and has not collected so far.                  |\n"
				+ "|'D'     Attempts to make coffee from the collected items.  If the     |\n"
				+ "|        three ingredients of coffee, cream, and sugar are in the      |\n"
				+ "|        player's inventory, the game is won.  Otherwise, the player   |\n"
				+ "|        will lose.                                                    |\n"
				+ "|'H'     Displays the help table.  Show the valid commands of the game.|\n"
				+ "|        You're looking at it right now!                               |\n"
				+ "0======================================================================0\n");
		return "Help";		// Returns a String used for debugging purposes only
	}
	
	
	/**
	 * Uses the player's inventory to determine if the player wins or loses the game
	 * @return An integer representing the scenario number; used for debugging purposes only.
	 */
	public int checkVictoryConditions() {
		
		displayInventory();			// Prints the player's inventory first
		System.out.printf("\n");		// Formatting newline
		
		if (coffee && cream && sugar) {				// Conditional if the player has all items; this is the only victory scenario.
			System.out.printf("You drink the beverage and are ready to study!\n"
					+ "You win!\n");
			return 1;
		}
		else if (coffee && cream && !sugar) {			// Conditional if the player only has coffee and cream
			System.out.printf("Without sugar, the coffee is too bitter.  You cannot study.\n"
					+ "You lose!\n");
			return 2;
		}
		else if (coffee && !cream && sugar) {			// Conditional if the player only has coffee and sugar
			System.out.printf("Without cream, you get an ulcer and cannot study.\n"
					+ "You lose!\n");
			return 3;
		}
		else if (coffee && !cream && !sugar) {			// Conditional if the player only has coffee
			System.out.printf("Without cream, you get an ulcer and cannot study.\n"
					+ "You lose!\n");
			return 4;
		}
		else if (!coffee && cream && sugar) {			// Conditional if the player only has cream and sugar
			System.out.printf("You drink the sweetened cream, but without caffeine, you cannot study.\n"
					+ "You lose!\n");
			return 5;
		}
		else if (!coffee && cream && !sugar) {			// Conditional if the player only has cream
			System.out.printf("Without sugar, the coffee is too bitter.  You cannot study.\n"
					+ "You lose!\n");
			return 6;
		}
		else if (!coffee && !cream && sugar) {			// Conditional if the player only has sugar
			System.out.printf("You eat the sugar, but without caffeine, you cannot study.\n"
					+ "You lose!\n");
			return 7;
		}
		else {					// Otherwise, the player has no items in their inventory
			System.out.printf("You drink the air, as you have no coffee, sugar, or cream.\n"
					+ "The air is invigorating, but not invigorating enough.  You cannot study.\n"
					+ "You lose!\n");
			return 8;
		}
	}
	
	
	/**
	 * Prints game titles to the screen depending on the integer passed [ASCII art credit: patorjk.com/software/taag/]
	 * @param num An integer that determines if the game title or game over is printed
	 * @return A lastCommand String for debugging
	 */
	public String printTitles(int num) {
		
		switch (num) {
			case 0:						// Prints the game's title - "Coffee Maker Quest ---- Powered by Java"
				System.out.printf(
						"  _____     ______          __  ___     __             ____               __\n"
						+ " / ___/__  / _/ _/__ ___   /  |/  /__ _/ /_____ ____  / __ \\__ _____ ___ / /_\n"
						+ "/ /__/ _ \\/ _/ _/ -_) -_) / /|_/ / _ `/  '_/ -_) __/ / /_/ / // / -_|_-</ __/\n"
						+ "\\___/\\___/_//_/ \\__/\\__/ /_/  /_/\\_,_/_/\\_\\\\__/_/    \\___\\_\\_,_/\\__/___/\\__/\n"
						+ "  _____ _____ _____ _____ _____ _____ _____ _____ _____ _____ _____ _____\n"
						+ "  \\____\\\\____\\\\____\\\\____\\\\____\\\\____\\\\____\\\\____\\\\____\\\\____\\\\____\\\\____\\\n"
						+ "    _____                         _    _             __ _____ _____ _____\n"
						+ "   |  _  |___ _ _ _ ___ ___ ___ _| |  | |_ _ _    __|  |  _  |  |  |  _  |\n"
						+ "   |   __| . | | | | -_|  _| -_| . |  | . | | |  |  |  |     |  |  |     |\n"
						+ "   |__|  |___|_____|___|_| |___|___|  |___|_  |  |_____|__|__|\\___/|__|__|\n"
						+ "                                          |___|\n");
				return "Coffee Maker Quest";
			case 1:						// Prints game over
				System.out.printf(
						"\n _______  _______  __   __  _______    _______  __   __  _______  ______\n"
						+ "|       ||   _   ||  |_|  ||       |  |       ||  | |  ||       ||    _ |\n"
						+ "|    ___||  |_|  ||       ||    ___|  |   _   ||  |_|  ||    ___||   | ||\n"
						+ "|   | __ |       ||       ||   |___   |  | |  ||       ||   |___ |   |_||_\n"
						+ "|   ||  ||       ||       ||    ___|  |  |_|  ||       ||    ___||    __  |\n"
						+ "|   |_| ||   _   || ||_|| ||   |___   |       | |     | |   |___ |   |  | |\n"
						+ "|_______||__| |__||_|   |_||_______|  |_______|  |___|  |_______||___|  |_|\n");
				return "GAME OVER";
			default:					// Otherwise, an invalid integer was entered
				return "Bad print job";
		}
	}
	
	
	/**
	 * Gets the status of coffee in the player's inventory
	 * @return Returns true if the player possesses coffee
	 */
	public boolean hasCoffee() {
		
		return coffee;
	}
	
	
	/**
	 * Gets the status of cream in the player's inventory
	 * @return Returns true if the player possesses cream
	 */
	public boolean hasCream() {
		
		return cream;
	}
	
	
	/**
	 * Gets the status of sugar in the player's inventory
	 * @return Returns true if the player possesses sugar
	 */
	public boolean hasSugar() {
		
		return sugar;
	}
	
	
	/**
	 * Gets the string of the last command that was entered by the player; used for debugging purposes only.
	 * @return A String representing the last command entered
	 */
	public String getLastCommand() {
		
		return lastCommand;
	}
	
	
	/**
	 * Builds an exact replica of the rooms found in the original coffeemaker.jar
	 * @param roomComplex The QuestHouse object to add QuestRooms to
	 */
	public QuestHouse buildRooms(QuestHouse houseOfRooms) {
		
		if (houseOfRooms == null) {
			throw new IllegalArgumentException("Error: The QuestHouse object was not initialized!");
		}
		QuestRoom room = new QuestRoom();		// Creates a new, blank QuestRoom object
		houseOfRooms.setInitialRoom(room);		// Sets the QuestHouse object's initial room to the newly created room
		
		for (int i = 0; i < ROOM_ADJ.length; i++) {			// Loops through the adjective arrays to build and add new QuestRoom objects
			room.renovateRoom(ROOM_ADJ[i], FURNISHING[i], FURN_ADJ[i], N_DOOR[i], S_DOOR[i]);		// Sets the adjectives for each room

			switch (i) {
				case 0:						// Sets the item of the first room to "cream"
					room.setItem("cream");
					break;
				case 2:						// Sets the item of the third room to "coffee"
					room.setItem("coffee");
					break;
				case 5:						// Sets the item of the last room to "sugar"
					room.setItem("sugar");
					break;
				default:					// No item is set
					break;
			}
			
			room.setNorthRoom(new QuestRoom());			// Sets the north QuestRoom as a new QuestRoom
			room = room.enterNorthDoor();				// Sets the current QuestRoom as the newly created one
			houseOfRooms.incrementNumberOfRooms();		// Increments the number of rooms by 1
		}
		
		return houseOfRooms;
	}
}
