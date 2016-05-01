/**
 * @author Brandon S. Hang
 * @version 1.700
 * CS 1501
 * Assignment 3
 * March 21, 2016
 * 
 * This class functions as a heap that stores CarInfo objects.  CarInfo objects
 * are stored using a minimum priority queue using the car's price or mileage
 * as the priority determiner.  The actual data structure in which objects are
 * stored in is an array that resizes when expansion is necessary.  Methods of
 * this class can add, remove, and update CarInfo objects.  It is also
 * designed to work in conjunction with CarWayTrie.java as the second class
 * enables indexing of specific CarInfo objects.
 * 
 */

public class CarHeap {
	
	private CarInfo[] heap;				// Stores CarInfo objects in an array
	private char mode;					// Determines the how the priority queue is sorted; 'p' for price, 'm' for mileage
	private int nextLeaf;				// Determines where the next entry is added to the heap
	
	
	/**
	 * Creates a new CarHeap object
	 * @param md The priority queue determiner
	 */
	public CarHeap(char md) {
		
		heap = new CarInfo[32];
		mode = md;
		nextLeaf = 0;
	}
	
	
	/**
	 * Gets the object of highest priority
	 * @return The car with the lowest price or mileage
	 */
	public CarInfo getMinimumCar() {
		
		return heap[0];
	}
	
	
	/**
	 * Adds a CarInfo object to the heap; also updates the trie with the car's index in the heap
	 * @param car The car to add to the heap
	 * @param trie The trie to update with the car's heap index
	 */
	public void addCar(CarInfo car, CarWayTrie trie) {
		
		if (nextLeaf == heap.length) {			// Resizes the array if there is no room available
			heap = resizeHeap(heap);
		}
		
		heap[nextLeaf] = car;				// Adds the car to the next available leaf position
		floatCar(trie);						// Floats the car in the heap
		nextLeaf++;							// Increments the index of the next available leaf position
	}
	
	
	/**
	 * Removes a CarInfo object from the heap; also removes its index position from the trie
	 * @param index The index of the car to remove from the heap
	 * @param trie The trie to update
	 */
	public void removeCar(int index, CarWayTrie trie) {
		
		nextLeaf--;							// Decrements the index of the next available leaf position
		heap[index] = heap[nextLeaf];			// Overwrites the car in the specified index with the car in the last leaf
		
		if (index == nextLeaf) {					// Conditional if the index is the same as the last leaf
			trie.removeHeapIndex(heap[index].getVIN());		// Removes the car's heap index from the trie
			heap[index] = null;						// Sets the car in the heap to null
			return;
		}		
		else {
			heap[nextLeaf] = null;				// Sets the last leaf to null
			
			if (getMode() == 'p') {
				sinkByPrice(index, trie);			// Calls the method to sink the CarInfo object if the heap is based on price
			}
			else {
				sinkByMileage(index, trie);			// Calls the method to sink the CarInfo object if the heap is based on mileage
			}
		}
	}
	
	
	/**
	 * Gets the car from the specified index in the heap array
	 * @param index The index of the car
	 * @return The CarInfo object at the index in the array
	 */
	public CarInfo getCar(int index) {
		
		return heap[index];
	}
	
	
	/**
	 * Sets the car at the specified index in the heap array
	 * @param car The CarInfo object to set
	 * @param index The index to set the car in the array
	 */
	public void setCar(CarInfo car, int index) {
		
		heap[index] = car;
	}
	
	
	/**
	 * Checks the CarInfo object at the index specified and determines if it needs to be floated or sunk in the heap
	 * @param index The index of the car
	 * @param trie The trie to update the car's position in the heap
	 */
	public void checkCarUpdates(int index, CarWayTrie trie) {
		
		int parentIndex = (int)Math.floor((index - 1) / 2.0);			// Calculates the parent index of the CarInfo object
		
		if (getMode() == 'p') {							// Floats or sinks the car if the priority queue is based on price
			floatByPrice(parentIndex, index, trie);
			sinkByPrice(index, trie);
		}
		else {											// Floats or sinks the car if the priority queue is based on mileage
			floatByMileage(parentIndex, index, trie);
			sinkByMileage(index, trie);
		}
	}
	
	
	/**
	 * Determines if a CarObject is to be floated based on price or mileage after adding a new car
	 * @param trie The trie to update heap indices
	 */
	private void floatCar(CarWayTrie trie) {
		
		int parentIndex = (int)Math.floor((nextLeaf - 1) / 2.0);			// Calculates the parent index using the leaf index
		
		if (getMode() == 'p') {
			floatByPrice(parentIndex, nextLeaf, trie);			// Floats the car if the priority queue is based on price
		}
		else {
			floatByMileage(parentIndex, nextLeaf, trie);		// Floats the car if the priority queue is based on mileage
		}
	}
	
	
	/**
	 * Floats a CarInfo object by price
	 * @param parentIndex The index of the car's parent
	 * @param insertedIndex The index of the current car
	 * @param trie The trie to update
	 */
	private void floatByPrice(int parentIndex, int insertedIndex, CarWayTrie trie) {
		
		if (parentIndex < 0) {					// If the parent index is less than zero, then the current car is located at the root
			trie.storeHeapIndex(heap[0].getVIN(), 0);			// Updates the trie for the current car
			return;
		}
		
		double parentPrice = heap[parentIndex].getPrice();				// Gets the price of the parent car
		double insertedPrice = heap[insertedIndex].getPrice();			// Gets the price of the current car
		
		if (parentPrice > insertedPrice) {						// Swaps the parent and current cars if the parent price is greater than the current price
			CarInfo tempCar = heap[parentIndex];
			heap[parentIndex] = heap[insertedIndex];			
			heap[insertedIndex] = tempCar;
			
			trie.storeHeapIndex(heap[insertedIndex].getVIN(), insertedIndex);		// Updates the current car's index in the trie
			trie.storeHeapIndex(heap[parentIndex].getVIN(), parentIndex);			// Updates the parent car's index in the trie
			
			insertedIndex = parentIndex;								// Sets the current car's index as the parent index
			parentIndex = (int)Math.floor((parentIndex - 1) / 2.0);		// Calculates a new parent index from the current car's new index
			
			floatByPrice(parentIndex, insertedIndex, trie);				// Recursively calls the float method
		}
		else {
			trie.storeHeapIndex(heap[insertedIndex].getVIN(), insertedIndex);		// Otherwise updates the current car's index in the trie
		}
	}
	
	
	/**
	 * Floats a CarInfo object by mileage; functions the same as floatByPrice except by using a car's mileage instead
	 * @param parentIndex The index of the car's parent
	 * @param insertedIndex The index of the current car
	 * @param trie The trie to update
	 */
	private void floatByMileage(int parentIndex, int insertedIndex, CarWayTrie trie) {
		
		if (parentIndex < 0) {
			trie.storeHeapIndex(heap[0].getVIN(), 0);
			return;
		}
		
		int parentMileage = heap[parentIndex].getMileage();
		int insertedMileage = heap[insertedIndex].getMileage();
		
		if (parentMileage > insertedMileage) {
			CarInfo tempCar = heap[parentIndex];
			heap[parentIndex] = heap[insertedIndex];
			heap[insertedIndex] = tempCar;
			
			trie.storeHeapIndex(heap[insertedIndex].getVIN(), insertedIndex);
			trie.storeHeapIndex(heap[parentIndex].getVIN(), parentIndex);
			
			insertedIndex = parentIndex;
			parentIndex = (int)Math.floor((parentIndex - 1) / 2.0);
			
			floatByMileage(parentIndex, insertedIndex, trie);
		}
		else {
			trie.storeHeapIndex(heap[insertedIndex].getVIN(), insertedIndex);
		}
	}
	
	
	/**
	 * Sinks a CarInfo object by its price
	 * @param parentIndex The parent car's index
	 * @param trie The trie to update
	 */
	private void sinkByPrice(int parentIndex, CarWayTrie trie) {
		
		int leftIndex = 2 * parentIndex + 1;		// Calculates the left child's index
		int rightIndex = 2 * parentIndex + 2;		// Calculates the right child's index
		int comparedIndex;						// The index to eventually compare to the parent
		CarInfo leftCar, rightCar;				// CarInfo objects of the left and right children
		
		if (leftIndex > heap.length - 1) {		// Sets the left child to null if the index exceeds the heap size
			leftCar = null;
		}
		else {
			leftCar = heap[leftIndex];			// Otherwise, retrieves the left child CarInfo object
		}
		
		if (rightIndex > heap.length - 1) {		// Sets the right child to null if the index exceeds the heap size
			rightCar = null;
		}
		else {
			rightCar = heap[rightIndex];		// Otherwise, retrieves the right child CarInfo object
		}
		
		if (leftCar == null && rightCar == null) {			// Conditional if both children are null
			trie.storeHeapIndex(heap[parentIndex].getVIN(), parentIndex);		// Updates the parent car's index in the trie
			return;
		}
		else if (leftCar != null && rightCar == null) {		// Conditional if only the right child is null
			comparedIndex = leftIndex;				// Sets the compared index to the left child index
		}
		else if (leftCar == null && rightCar != null) {		// Conditional if only the left child is null (contingency; shouldn't happen normally)
			comparedIndex = rightIndex;				// Sets the compared index to the right child index
		}
		else {							// Otherwise, gets the left and right children's prices
			double leftPrice = leftCar.getPrice();
			double rightPrice = rightCar.getPrice();
			
			if (leftPrice <= rightPrice) {			// Sets the compared index to the left child if the left child price is greater than or equal to the right child
				comparedIndex = leftIndex;
			}
			else {
				comparedIndex = rightIndex;			// Otherwise, sets the compared index to the right child index
			}
		}
		
		double parentPrice = heap[parentIndex].getPrice();			// Gets prices of the parent and child cars
		double childPrice = heap[comparedIndex].getPrice();
		
		if (parentPrice > childPrice) {							// Swaps CarInfo objects if the parent price is greater than the child price
			CarInfo tempCar = heap[comparedIndex];
			heap[comparedIndex] = heap[parentIndex];
			heap[parentIndex] = tempCar;
			
			trie.storeHeapIndex(heap[comparedIndex].getVIN(), comparedIndex);		// Updates the child car's index in the trie
			trie.storeHeapIndex(heap[parentIndex].getVIN(), parentIndex);			// Updates the parent car's index in the trie
			
			sinkByPrice(comparedIndex, trie);						// Recursively calls the sink method
		}
		else {
			trie.storeHeapIndex(heap[parentIndex].getVIN(), parentIndex);		// Otherwise, updates the parent car's index in the trie
		}
	}
	
	
	/**
	 * Sinks a CarInfo object by its mileage; functions the same as sinkByPrice except by using a car's mileage instead
	 * @param parentIndex The parent car's index
	 * @param trie The trie to update
	 */
	private void sinkByMileage(int parentIndex, CarWayTrie trie) {
		
		int leftIndex = 2 * parentIndex + 1;
		int rightIndex = 2 * parentIndex + 2;
		int comparedIndex;
		CarInfo leftCar, rightCar;
		
		if (leftIndex > heap.length - 1) {
			leftCar = null;
		}
		else {
			leftCar = heap[leftIndex];
		}
		
		if (rightIndex > heap.length - 1) {
			rightCar = null;
		}
		else {
			rightCar = heap[rightIndex];
		}
		
		if (leftCar == null && rightCar == null) {
			trie.storeHeapIndex(heap[parentIndex].getVIN(), parentIndex);
			return;
		}
		else if (leftCar != null && rightCar == null) {
			comparedIndex = leftIndex;
		}
		else if (leftCar == null && rightCar != null) {
			comparedIndex = rightIndex;
		}
		else {
			int leftMileage = leftCar.getMileage();
			int rightMileage = rightCar.getMileage();
			
			if (leftMileage <= rightMileage) {
				comparedIndex = leftIndex;
			}
			else {
				comparedIndex = rightIndex;
			}
		}
		
		int parentMileage = heap[parentIndex].getMileage();
		int childMileage = heap[comparedIndex].getMileage();
		
		if (parentMileage > childMileage) {
			CarInfo tempCar = heap[comparedIndex];
			heap[comparedIndex] = heap[parentIndex];
			heap[parentIndex] = tempCar;
			
			trie.storeHeapIndex(heap[comparedIndex].getVIN(), comparedIndex);
			trie.storeHeapIndex(heap[parentIndex].getVIN(), parentIndex);
			
			sinkByMileage(comparedIndex, trie);
		}
		else {
			trie.storeHeapIndex(heap[parentIndex].getVIN(), parentIndex);
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
