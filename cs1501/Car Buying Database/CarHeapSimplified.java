/**
 * @author Brandon S. Hang
 * @version 1.200
 * CS 1501
 * Assignment 3
 * March 21, 2016
 * 
 * This class functions as a simplified heap that stores CarInfo
 * objects in a minimum priority queue.  This class does not use
 * an indirection data structure to index object positions as it
 * is the simplified version of CarHeap.java.
 */

public class CarHeapSimplified {
	
	private CarInfo[] heap;				// Stores CarInfo objects in an array
	private char mode;					// Determines the how the priority queue is sorted; 'p' for price, 'm' for mileage
	private int nextLeaf;				// Determines where the next entry is added to the heap
	
	
	/**
	 * Creates a new simplified CarHeap object
	 * @param md The priority queue determiner
	 */
	public CarHeapSimplified(char md) {
		
		heap = new CarInfo[32];
		mode = md;
	}
	
	
	/**
	 * Gets the object of highest priority
	 * @return The car with the lowest price or mileage
	 */
	public CarInfo getMinimumCar() {
		
		return heap[0];
	}
	
	
	/**
	 * Adds a CarInfo object to the heap
	 * @param car The car to add to the heap
	 */
	public void addCar(CarInfo car) {
		
		if (nextLeaf == heap.length) {			// Resizes the array if there is no room available
			heap = resizeHeap(heap);
		}
		
		heap[nextLeaf] = car;				// Adds the car to the next available leaf position
		floatCar();							// Floats the car in the heap
		nextLeaf++;							// Increments the index of the next available leaf position
	}
	
	private void floatCar() {
		
		int parentIndex = (int)Math.floor(nextLeaf / 2.0);
		
		if (getMode() == 'p') {
			floatByPrice(parentIndex, nextLeaf);
		}
		else {
			floatByMileage(parentIndex, nextLeaf);
		}
	}
	
	
	/**
	 * Floats a CarInfo object by price
	 * @param parentIndex The index of the car's parent
	 * @param insertedIndex The index of the current car
	 */
	private void floatByPrice(int parentIndex, int insertedIndex) {
		
		if (parentIndex < 0) {					// If the parent index is less than zero, then the current car is located at the root
			return;
		}
		
		double parentPrice = heap[parentIndex].getPrice();				// Gets the price of the parent car
		double insertedPrice = heap[insertedIndex].getPrice();			// Gets the price of the current car
		
		if (parentPrice > insertedPrice) {						// Swaps the parent and current cars if the parent price is greater than the current price
			CarInfo tempCar = heap[parentIndex];
			heap[parentIndex] = heap[insertedIndex];
			heap[insertedIndex] = tempCar;
			
			insertedIndex = parentIndex;								// Sets the current car's index as the parent index
			parentIndex = (int)Math.floor((parentIndex - 1) / 2.0);		// Calculates a new parent index from the current car's new index
			
			floatByPrice(parentIndex, insertedIndex);				// Recursively calls the float method
		}
	}
	
	
	/**
	 * Floats a CarInfo object by mileage; functions the same as floatByPrice except by using a car's mileage instead
	 * @param parentIndex The index of the car's parent
	 * @param insertedIndex The index of the current car
	 */
	private void floatByMileage(int parentIndex, int insertedIndex) {
		
		if (parentIndex < 0) {
			return;
		}
		
		int parentMileage = heap[parentIndex].getMileage();
		int insertedMileage = heap[insertedIndex].getMileage();
		
		if (parentMileage > insertedMileage) {
			CarInfo tempCar = heap[parentIndex];
			heap[parentIndex] = heap[insertedIndex];
			heap[insertedIndex] = tempCar;
			
			insertedIndex = parentIndex;
			parentIndex = (int)Math.floor((parentIndex - 1) / 2.0);
			
			floatByMileage(parentIndex, insertedIndex);
		}
	}
	
	
	/**
	 * Gets the heap's method of determining priority
	 * @return The character representing the priority mode; 'p' for price or 'm' for mileage
	 */
	public char getMode() {
		
		return mode;
	}
	
	
	/**
	 * Resizes the heap's array its maximum size has been exceeded
	 * @param oldHeap The current array that has not been resized
	 * @return The new array of doubled size
	 */
	private CarInfo[] resizeHeap(CarInfo[] oldHeap) {
		
		int doubledSize = oldHeap.length * 2;
		CarInfo[] newHeap = new CarInfo[doubledSize];		// Creates a new array by doubling the size of the old array
		
		for (int i = 0; i < oldHeap.length; i++) {			// Copies all objects from the old array to the new array
			newHeap[i] = oldHeap[i];
		}
		
		return newHeap;
	}
	
	
	/**
	 * Returns a string representation of the heap
	 */
	public String toString() {
		
		String heapDump = "";
		
		if (nextLeaf == 0) {			// If the next available leaf position is zero, then the heap is empty
			heapDump = "Nothing in the heap";
		}
		else if (getMode() == 'p') {				// Dumps the heap's contents based on price
			for (int i = 0; i < nextLeaf; i++) {
				heapDump += ("$" + heap[i].getPrice() + " - " + heap[i].getMake() + " " + heap[i].getModel() + "\n");
			}
		}
		else {										// Dumps the heaps' contents based on mileage
			for (int i = 0; i < nextLeaf; i++) {
				heapDump += (heap[i].getMileage() + " mi. - " + heap[i].getMake() + " " + heap[i].getModel() + "\n");
			}
		}
		
		return heapDump;
	}
}
