/**
 * @author Brandon S. Hang
 * @version 1.600
 * CS 1501
 * Assignment 3
 * March 21, 2016
 * 
 * This class extends the basic doubly-linked list by adding specific
 * methods regarding CarInfo objects.  These methods include removing
 * CarInfo objects from the linked list by using a car's VIN number
 * and methods for updating a car's price, mileage, and color.
 */

public class CarLinkedList extends CustomLinkedList<CarInfo> {
	
	/**
	 * Creates a new, empty linked list
	 */
	public CarLinkedList() {
		
		setListHead(new CustomNode<CarInfo>());
	}
	
	
	/**
	 * Creates a new linked list with an initial CarInfo object as its list head node
	 * @param car
	 */
	public CarLinkedList(CarInfo car) {
		
		setListHead(new CustomNode<CarInfo>(car));
	}
	
	
	/**
	 * Removes a CarInfo object from the linked list based on its VIN
	 * @param vin The VIN of the car
	 * @return The CarInfo object that was deleted
	 */
	public CarInfo removeByVIN(String vin) {
		
		CustomNode<CarInfo> previousNode = null;			// Initializes the last node as null
		CustomNode<CarInfo> currentNode = getListHead();		// Gets the list head node
		
		while (currentNode.hasData()) {						// Loops while the node has a CarInfo object stored
			CarInfo listCar = currentNode.getData();
			String listVIN = listCar.getVIN();				// Gets the VIN of the node's CarInfo object
			
			if (vin.compareTo(listVIN) == 0) {					// Conditional if the VIN parameter matches the VIN of the node
				CustomNode<CarInfo> nextNode = currentNode.getNextNode();		// Gets the next node of the current node
				if (previousNode == null) {
					setListHead(nextNode);			// Sets the next node to be the new list head if the previous node is null
				}
				else {
					previousNode.setNextNode(nextNode);			// Otherwise, sets the previous node's next reference to be the next node
				}
				nextNode.setLastNode(previousNode);			// Sets the next node's last reference to be the previous node
				return listCar;
			}
			else {
				previousNode = currentNode;					// If VINs do not match, traverses the linked list to the next node
				currentNode = currentNode.getNextNode();
			}
		}
		return null;				// Returns null if nothing was removed
	}
	
	
	/**
	 * Updates the price of a CarInfo object by using its VIN
	 * @param vin The car's VIN
	 * @param price The updated price
	 * @return The updated CarInfo object
	 */
	public CarInfo updatePrice(String vin, double price) {
		
		CustomNode<CarInfo> currentNode = getListHead();		// Gets the linked list head
		
		while (currentNode.hasData()) {				// Traverses the linked list while the node has data
			CarInfo listCar = currentNode.getData();			// Gets the CarInfo object of the node
			String listVIN = listCar.getVIN();					// Gets the car's VIN
			
			if (vin.compareTo(listVIN) == 0) {				// Conditional if the VINs match
				listCar.setPrice(price);					// Sets the price of the CarInfo object
				currentNode.setData(listCar);				// Saves it back into the node
				return listCar;								// Returns the updated CarInfo object
			}
			else {
				currentNode = currentNode.getNextNode();		// Otherwise, traverses the linked list to the next node
			}
		}
		return null;				// Returns null if nothing was updated
	}
	
	
	/**
	 * Updates the mileage of a CarInfo object by using its VIN; functions the same as updatePrice except by using mileage instead
	 * @param vin The car's VIN
	 * @param mileage The updated mileage
	 * @return The updated CarInfo object
	 */
	public CarInfo updateMileage(String vin, int mileage) {
		
		CustomNode<CarInfo> currentNode = getListHead();
		
		while (currentNode.hasData()) {
			CarInfo listCar = currentNode.getData();
			String listVIN = listCar.getVIN();
			
			if (vin.compareTo(listVIN) == 0) {
				listCar.setMileage(mileage);
				currentNode.setData(listCar);
				return listCar;
			}
			else {
				currentNode = currentNode.getNextNode();
			}
		}
		return null;
	}
	
	
	/**
	 * Updates the color of a CarInfo object by using its VIN; functions the same as updatePrice except by using color instead
	 * @param vin
	 * @param color
	 * @return
	 */
	public CarInfo updateColor(String vin, String color) {
		
		CustomNode<CarInfo> currentNode = getListHead();
		
		while (currentNode.hasData()) {
			CarInfo listCar = currentNode.getData();
			String listVIN = listCar.getVIN();
			
			if (vin.compareTo(listVIN) == 0) {
				listCar.setColor(color);
				currentNode.setData(listCar);
				return listCar;
			}
			else {
				currentNode = currentNode.getNextNode();
			}
		}
		return null;
	}
}
