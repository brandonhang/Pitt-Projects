/**
 * @author Brandon S. Hang
 * @version 1.000
 * CS 1501
 * Assignment 3
 * March 21, 2016
 * 
 * This class functions as the UI for the CarTracker.java executable.
 * Methods of this class simply print out different messages to the
 * console screen.
 */

public class CarUI {
	
	/**
	 * Prints the welcome message
	 */
	public static void displayWelcome() {
		
		System.out.printf("\n"
				+ "================================================\n"
				+ " Welcome to the Grease Monkey Car Shopping App!\n"
				+ "================================================\n\n");
	}
	
	
	/**
	 * Prints the main menu
	 */
	public static void displayMenu() {
		
		System.out.printf("-------- MAIN MENU --------\n"
				+ "Please select an option by entering the number next to the option name.\n"
				+ "\t1. Add a new car for consideration\n"
				+ "\t2. Update an existing car\n"
				+ "\t3. Remove a car from consideration\n"
				+ "\t4. Retrieve the lowest price car\n"
				+ "\t5. Retrieve the lowest mileage car\n"
				+ "\t6. Retrieve the lowest price car by make and model\n"
				+ "\t7. Retrieve the lowest mileage car by make and model\n"
				+ "\t8. Exit\n");
	}
	
	
	/**
	 * Prints the new car heading
	 */
	public static void displayAddCar() {
		
		System.out.printf("-------- ADD NEW CAR --------\n");
	}
	
	
	/**
	 * Prints the update car heading and submenu
	 */
	public static void displayUpdateCar() {
		
		System.out.printf("-------- UPDATE CAR --------\n"
				+ "Select an attribute to update by entering the number next to the attribute name.\n"
				+ "\t1. Update price\n"
				+ "\t2. Update mileage\n"
				+ "\t3. Update color\n"
				+ "\t4. Cancel\n"
				+ "Select an option: ");
	}
	
	
	/**
	 * Prints the remove car heading
	 */
	public static void displayRemoveCar() {
		
		System.out.printf("-------- REMOVE CAR --------\n");
	}
	
	
	/**
	 * Prints the the lowest price heading
	 */
	public static void displayLowestPrice() {
		
		System.out.printf("-------- LOWEST PRICED CAR --------\n\n");
	}
	
	
	/**
	 * Prints the lowest mileage heading
	 */
	public static void displayLowestMiles() {
		
		System.out.printf("-------- LOWEST MILEAGE CAR --------\n\n");
	}
	
	
	/**
	 * Prints the find car heading
	 */
	public static void displayFindCar() {
		
		System.out.printf("-------- FIND SPECIFIC CAR --------\n");
	}
	
	
	/**
	 * Prints the exit message
	 */
	public static void displayExit() {
		
		System.out.printf("\nThank you for using the Grease Monkey Shopping App!\n"
				+ "Always remember to buckle your seat belt and if in doubt, flat out!\n"
				+ "Good-bye!\n");
	}
}
