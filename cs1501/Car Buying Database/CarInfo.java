/**
 * @author Brandon S. Hang
 * @version 1.000
 * CS 1501
 * Assignment 3
 * March 21, 2016
 * 
 * This class functions as an object that stores pertinent information about a
 * car when buying or selling one.  It holds the car's VIN number, make, model,
 * price, mileage, and color and contains methods that both access or modify
 * this information.
 */

public class CarInfo {
	
	private String vinNumber;
	private String make;
	private String model;
	private double price;
	private int mileage;
	private String color;
	
	
	/**
	 * Creates a new CarInfo object with the assigned attributes
	 * @param vin The VIN number
	 * @param mk The car's make
	 * @param mdl The car's model
	 * @param cost The car's price
	 * @param miles The car's mileage
	 * @param col The car's color
	 */
	public CarInfo(String vin, String mk, String mdl, double cost, int miles, String col) {
		
		setVIN(vin);		// The following methods set each of the car's attributes
		setMake(mk);
		setModel(mdl);
		setPrice(cost);
		setMileage(miles);
		setColor(col);
	}
	
	
	/**
	 * Sets the VIN number of the car
	 * @param vin The car's VIN
	 */
	private void setVIN(String vin) {
		
		vinNumber = vin;
	}
	
	
	/**
	 * Sets the car's make
	 * @param mk The car's make
	 */
	private void setMake(String mk) {
		
		make = mk;
	}
	
	
	/**
	 * Sets the car's model
	 * @param mdl The car's model
	 */
	private void setModel(String mdl) {
		
		model = mdl;
	}
	
	
	/**
	 * Sets the car's price
	 * @param cost The car's price
	 */
	public void setPrice(double cost) {
		
		price = cost;
	}
	
	
	/**
	 * Sets the car's mileage
	 * @param miles The car's mileage
	 */
	public void setMileage(int miles) {
		
		mileage = miles;
	}
	
	
	/**
	 * Sets the car's color
	 * @param col The car's color
	 */
	public void setColor(String col) {
		
		color = col;
	}
	
	
	/**
	 * Gets the VIN number of the car
	 * @return The car's VIN
	 */
	public String getVIN() {
		
		return vinNumber;
	}
	
	
	/**
	 * Gets the car's make
	 * @return The car's make
	 */
	public String getMake() {
		
		return make;
	}
	
	
	/**
	 * Gets the car's model
	 * @return The car's model
	 */
	public String getModel() {
		
		return model;
	}
	
	
	/**
	 * Gets the car's price
	 * @return The car's price
	 */
	public double getPrice() {
		
		return price;
	}
	
	
	/**
	 * Gets the car's mileage
	 * @return The car's mileage
	 */
	public int getMileage() {
		
		return mileage;
	}
	
	
	/**
	 * Gets the car's color
	 * @return The car's color
	 */
	public String getColor() {
		
		return color;
	}
	
	
	/**
	 * Returns a string representation of the car
	 */
	public String toString() {
		
		String car = getMake() + " " + getModel() + ", " + getColor() + ", " + getMileage() + " mi., $" + getPrice();
		
		return car;
	}
}
