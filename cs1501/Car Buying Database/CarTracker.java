/**
 * @author Brandon S. Hang
 * @version 1.900
 * CS 1501
 * Assignment 3
 * March 21, 2016
 * 
 * This class is the main executable that sorts cars by their price
 * or mileage using a priority queue.  Methods include adding new
 * cars, updating existing cars, removing cars from consideration,
 * and retrieving lowest priced/mileage cars from the entire
 * database or by specific make and model.
 */

import java.util.Scanner;

public class CarTracker {
	
	private static CarHeap carPrices = new CarHeap('p');			// Creates a new heap based on car prices
	private static CarHeap carMileage = new CarHeap('m');			// Creates a new heap based on car mileage
	private static CarWayTrie pricesByVIN = new CarWayTrie();			// Creates an R-Way Trie for the prices heap
	private static CarWayTrie mileageByVIN = new CarWayTrie();			// Creates an R-Way Trie for the mielage heap
	private static CarHashTable carGroups = new CarHashTable();			// Creates a hash table for similar car models
	private static Scanner input = new Scanner(System.in);				// Scanner object for input
	private static String menuOption;								// Holds the user's input
	
	public static void main(String[] args) {
		
		CarUI.displayWelcome();					// Prints the welcome message
		CarUI.displayMenu();					// Prints the menu
		
		do {					// Loops until the user enters the exit option
			System.out.printf("Enter \"M\" to show the menu.\nSelect an option: ");
			menuOption = input.nextLine();							// Gets input from the user
			
			switch (menuOption) {
				case "1":						// If the input is "1", adds a new car to the database
					addNewCar();
					break;
				case "2":						// If the input is "2", updates a car in the database
					updateCar();
					break;
				case "3":						// If the input is "3", removes a car from the database
					removeCar();
					break;
				case "4":						// If the input is "4", gets the lowest priced car from the database
					getLowestPriceCar();
					break;
				case "5":						// If the input is "5", gets the lowest mileage car from the database
					getLowestMilesCar();
					break;
				case "6":
				case "7":						// If the input is "6" or "7", gets the lowest priced/mileage car of a specific make and model
					getLowestSpecificCar();
					break;
				case "8":						// If the input is "8", exits the program
					CarUI.displayExit();		// Prints the exit message
					System.exit(0);
				case "m":
				case "M":						// If the input is "m" or "M", prints the menu
					CarUI.displayMenu();
					break;
				default:								// Otherwise, an invalid command was entered
					System.out.printf("The command entered could not be recognized.  Please enter a valid integer.\n");
					break;
			}
		} while (true);
	}
	
	
	/**
	 * Adds a new car to the database
	 */
	private static void addNewCar() {
		
		CarUI.displayAddCar();				// Prints the add car heading
		String vin, make, model, color;
		double price;
		int miles;
		boolean valid;				// A boolean representing if the input was valid or not
		
		do {
			valid = true;
			
			System.out.printf("Enter the VIN number: ");
			vin = input.nextLine().toUpperCase();			// Gets the VIN from the user
			
			if (vin.length() != 17 || !vin.matches("[0-9A-Z&&[^IOQ]]*")) {		// Checks for required length and valid characters
				System.out.printf("The entered VIN number was invalid.  Please try again.\n");
				valid = false;					// If the input was invalid, the user will be prompted to re-enter the VIN
			}
		} while (!valid);					// Loops while the VIN is invalid
		
		do {
			valid = true;
			
			System.out.printf("Enter the make: ");
			make = input.nextLine();					// Gets the make from the user
			
			if (make.length() == 0) {					// Checks if the input is empty
				System.out.printf("The car's make cannot be left blank!  Please try again.\n");
				valid = false;				// If the input was empty, the user will be prompted to re-enter the make
			}
		} while (!valid);					// Loops while the make is invalid
		
		do {
			valid = true;
			
			System.out.printf("Enter the model: ");
			model = input.nextLine();					// Gets the model from the user
			
			if (model.length() == 0) {					// Checks if the input is empty
				System.out.printf("The car's model cannot be left blank!  Please try again.\n");
				valid = false;				// If the input was empty, the user will be prompted to re-enter the model
			}
		} while (!valid);					// Loops while the model is invalid
		
		System.out.printf("Enter the price: $");
		String priceString = input.nextLine();				// Gets the price from the user
		
		try {
			price = Double.parseDouble(priceString);
		}
		catch (NumberFormatException nfe) {				// If the price was not entered as a double, the price defaults to 0.0
			System.out.printf("The entered price was invalid.  The price will default to $0.00\n");
			price = 0.0;
		}
		
		if (price < 0.0) {							// If the price entered was negative, the price defaults to 0.0
			System.out.printf("The entered price cannot be negative.  The price will default to $0.00\n");
			price = 0.0;
		}
		
		System.out.printf("Enter the mileage as an integer: ");
		String milesString = input.nextLine();				// Gets the mileage from the user
		
		try {
			miles = Integer.parseInt(milesString);
		}
		catch (NumberFormatException nfe) {				// If the mileage was not entered as an integer, the mileage defaults to 0
			System.out.printf("The entered mileage was invalid.  The mileage will default to 0 mi.\n");
			miles = 0;
		}
		
		if (miles < 0) {							// If the mileage entered was negative, the mileage defaults to 0
			System.out.printf("The entered mileage cannot be negative.  The mileage will default to 0 mi.\n");
			miles = 0;
		}
		
		System.out.printf("Enter the car's color: ");
		color = input.nextLine();				// Gets the color from the user; this is the only field that can be empty
		
		CarInfo newCar = new CarInfo(vin, make, model, price, miles, color);		// Creates a new CarInfo object from the inputs
		carPrices.addCar(newCar, pricesByVIN);						// Adds the car to the prices heap and updates the prices trie
		carMileage.addCar(newCar, mileageByVIN);					// Adds the car to the mileage heap and updates the mileage trie
		carGroups.insertCar(newCar);						// Adds the car to a linked list in the hash table
		System.out.printf("The %s %s with VIN %s was successfully added!\n\n", make, model, vin);		// Prints a success message
	}
	
	
	/**
	 * Updates an existing car in the database
	 */
	public static void updateCar() {
		
		CarUI.displayUpdateCar();				// Prints the update car submenu
		String updateOption = input.nextLine();			// Gets the menu option from the user
		if (!updateOption.matches("[1234]")) {			// Checks if the input is valid
			System.out.printf("The command entered could not be recognized.  Returning to the main menu.\n\n");
			return;					// Returns to the main menu if the input is invalid
		}
		
		if (updateOption.compareTo("4") == 0) {				// If the input is "4" (cancel), returns to the main menu
			System.out.printf("Returning to the main menu.\n");
			return;
		}
		
		System.out.printf("Enter the VIN number: ");
		String vin = input.nextLine().toUpperCase();			// Gets the VIN from the user
		
		if (vin.length() != 17 || !vin.matches("[0-9A-Z&&[^IOQ]]*")) {		// Checks if the VIN is of valid length and characters
			System.out.printf("The entered VIN number was invalid.  No car was updated.  Returning to the main menu.\n\n");
			return;				// Returns to the main menu if the input is invalid
		}
		
		int pricesHeap = pricesByVIN.getHeapIndex(vin);				// Gets the index of the car in the prices heap
		int milesHeap = mileageByVIN.getHeapIndex(vin);				// Gets the index of the car in the mileage heap
		
		if (pricesHeap < 0 || milesHeap < 0) {				// If either of the indices are less than 0, returns to the main menu
			System.out.printf("The car with the VIN %s does not exist in the database!  Returning to the main menu.\n\n", vin);
			return;
		}
		
		switch (updateOption) {
			case "1":								// If the input for option is "1", updates the car's price
				System.out.printf("Enter the car's updated price: $");
				String priceString = input.nextLine();			// Gets the price from the user
				double price;
				
				try {
					price = Double.parseDouble(priceString);
				}
				catch (NumberFormatException nfe) {				// Defaults to 0.0 if the price entered is invalid
					System.out.printf("The entered price was invalid.  The price will default to $0.00\n");
					price = 0.0;
				}
				
				CarInfo car = carPrices.getCar(pricesHeap);			// Gets the CarInfo object from the prices heap
				car.setPrice(price);							// Updates the price of the car
				carPrices.setCar(car, pricesHeap);				// Stores the car in the prices heap
				carPrices.checkCarUpdates(pricesHeap, pricesByVIN);			// Repositions the updated car in the prices heap, if necessary
				carMileage.setCar(car, milesHeap);				// Stores the car in the mileage heap (no repositioning necessary)
				carGroups.updatePrice(car, price);				// Updates the car in the hash table
				
				System.out.printf("The price of the %s %s with VIN %s was successfully updated!\n\n", car.getMake(), car.getModel(), vin);
				break;
				
			case "2":								// If the input for option is "2", updates the car's mileage
				System.out.printf("Enter the car's updated mileage: ");
				String milesString = input.nextLine();			// Gets the mileage from the user
				int miles;
				
				try {
					miles = Integer.parseInt(milesString);
				}
				catch (NumberFormatException nfe) {				// Defaults to 0 if the mileage entered is invalid
					System.out.printf("The entered mileage was invalid.  The mileage will default to 0 mi.\n");
					miles = 0;
				}
				
				car = carMileage.getCar(milesHeap);					// Gets the CarInfo object from the mileage heap
				car.setMileage(miles);							// Updates the mileage of the car
				carMileage.setCar(car, milesHeap);				// Stores the car in the mileage heap
				carMileage.checkCarUpdates(milesHeap, mileageByVIN);		// Repositions the updated car in the mileage heap, if necessary
				carPrices.setCar(car, pricesHeap);				// Stores the car in the prices heap (no repositioning necessary)
				carGroups.updateMileage(car, miles);			// Updates the car in the hash table
				
				System.out.printf("The mileage of the %s %s with VIN %s was successfully updated!\n\n", car.getMake(), car.getModel(), vin);
				break;
				
			default:								// Otherwise, updates the car's color
				System.out.printf("Enter the car's updated color: ");
				String color = input.nextLine();				// Gets the color from the user
				
				car = carPrices.getCar(pricesHeap);				// Gets the CarInfo object from the prices heap
				car.setColor(color);							// Updates the color of the car
				carPrices.setCar(car, pricesHeap);				// Stores the car in the prices heap
				carMileage.setCar(car, milesHeap);				// Stores the car in the mileage heap
				carGroups.updateColor(car, color);				// Updates the car in the hash table
				
				System.out.printf("The color of the %s %s with VIN %s was successfully updated!\n\n", car.getMake(), car.getModel(), vin);
				break;
		}
	}
	
	
	/**
	 * Removes a car from the database
	 */
	private static void removeCar() {
		
		CarUI.displayRemoveCar();				// Prints the remove car heading
		System.out.printf("Enter the VIN number of the car you would like to remove: ");
		String vin = input.nextLine().toUpperCase();			// Gets the VIN from the user
		
		if (vin.length() != 17 || !vin.matches("[0-9A-Z&&[^IOQ]]*")) {		// Checks the VIN for valid length and characters
			System.out.printf("The entered VIN number was invalid.  No car was removed.  Returning to the main menu.\n\n");
			return;							// Returns to the main menu if the VIN is invalid
		}
		
		int heapIndex;
		heapIndex = pricesByVIN.getHeapIndex(vin);				// Gets the index of the CarInfo object in the prices heap
		
		if (heapIndex < 0) {						// If the index is less than zero, returns to the main menu
			System.out.printf("The car with the VIN %s does not exist in the database!  Returning to the main menu.\n\n", vin);
			return;
		}
		
		CarInfo car = carPrices.getCar(heapIndex);				// Gets the CarInfo object from the prices heap
		pricesByVIN.removeHeapIndex(vin);						// Removes the CarInfo object from the prices heap
		carPrices.removeCar(heapIndex, pricesByVIN);			// Removes the car's heap index from the prices trie
		
		heapIndex = mileageByVIN.getHeapIndex(vin);				// Gets the index of the CarInfo object in the mileage heap
		
		if (heapIndex < 0) {						// If the index is less than zero, returns to the main menu
			System.out.printf("The car with the VIN %s does not exist in the database!  Returning to the main menu.\n\n", vin);
			return;
		}
		
		mileageByVIN.removeHeapIndex(vin);						// Removes the CarInfo object from the mileage heap
		carMileage.removeCar(heapIndex, mileageByVIN);			// Removes the car's heap index from the mileage trie
		
		carGroups.removeCar(car);								// Removes the car from its linked list in the hash table
		
		System.out.printf("The %s %s with VIN %s was removed from consideration.\n\n", car.getMake(), car.getModel(), vin);
	}
	
	
	/**
	 * Gets the lowest priced car from the database
	 */
	private static void getLowestPriceCar() {
		
		CarUI.displayLowestPrice();						// Prints the lowest price heading
		CarInfo car = carPrices.getMinimumCar();			// Gets the highest priority CarInfo object from the prices heap
		
		if (car == null) {				// If the car is null, notifies the user of an empty database
			System.out.printf("There are no cars in the database!  Returning to the main menu.\n\n");
		}
		else {
			System.out.printf("Lowest priced car listing:\n");
			printCar(car);				// Otherwise, prints the car's information
		}
	}
	
	
	/**
	 * Gets the lowest mileage car from the database
	 */
	private static void getLowestMilesCar() {
		
		CarUI.displayLowestMiles();						// Prints the lowest mileage heading
		CarInfo car = carMileage.getMinimumCar();			// Gets the highest priority CarInfo object from the mileage heap
		
		if (car == null) {				// If the car is null, notifies the user of an empty database
			System.out.printf("There are no cars in the database!  Returning to the main menu.\n\n");
		}
		else {
			System.out.printf("Lowest mileage car listing:\n");
			printCar(car);				// Otherwise, prints the car's information
		}
	}
	
	
	/**
	 * Gets the lowest priced or mileage car of a specific make and model
	 */
	private static void getLowestSpecificCar() {
		
		CarUI.displayFindCar();							// Prints the lowest specific car heading
		System.out.printf("Enter the make of the car you want to find: ");
		String make = input.nextLine();					// Gets the make from the user
		
		System.out.printf("Enter the model of the car you want to find: ");
		String model = input.nextLine();				// Gets the model from the user
		
		if (make.length() == 0 || model.length() == 0) {
			System.out.printf("A make or model field was left blank!  Returning to the main menu.\n\n");
			return;
		}
		
		CarLinkedList list = carGroups.getList(make, model);			// Gets the linked list of the specific car's model
		
		if (list == null) {						// If the linked list is null, returns to the main menu
			System.out.printf("The car you entered was not found in the database.  Returning to the main menu.\n\n");
			return;
		}
		else {
			CustomNode<CarInfo> node = list.getListHead();			// Gets the linked list head
			CarHeapSimplified tempHeap;					// Declares a temporary, simplified heap
			
			if (menuOption.compareTo("6") == 0) {			// If the menu option is "6", creates a simplified heap based on price
				tempHeap = new CarHeapSimplified('p');
			}
			else {											// Otherwise, creates a simplified heap based on mileage
				tempHeap = new CarHeapSimplified('m');
			}
			
			if (node == null || !node.hasData()) {				// Returns to the main menu if the node is null or does not have data
				System.out.printf("The car you entered was not found in the database.  Returning to the main menu.\n\n");
				return;
			}
			
			while (node.hasData()) {						// Traverses through the entire linked list and adds cars to the heap
				tempHeap.addCar(node.getData());
				node = node.getNextNode();
			}
			
			System.out.printf("\nLowest mileage %s %s listing:\n", make, model);
			printCar(tempHeap.getMinimumCar());				// Prints the highest priority CarInfo object from the temporary heap
		}
	}
	
	
	/**
	 * Prints a CarInfo object's information in a formatted manner
	 * @param car The car to print
	 */
	private static void printCar(CarInfo car) {
		
		System.out.printf("\tVIN:     %s\n"
				+ "\tMake:    %s\n"
				+ "\tModel:   %s\n"
				+ "\tPrice:   $%,.2f\n"
				+ "\tMileage: %,d mi.\n"
				+ "\tColor:   %s\n\n", car.getVIN(), car.getMake(),
				car.getModel(), car.getPrice(), car.getMileage(), car.getColor());
	}
}
