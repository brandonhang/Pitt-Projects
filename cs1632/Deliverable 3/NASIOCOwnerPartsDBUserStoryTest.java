/**
 * @author Brandon S. Hang
 * @version 1.000
 * CS 1632
 * Deliverable 3
 * March 3, 2016
 */

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;

import java.util.List;

/**
 * As a Subaru owner
 * I want to research OEM and aftermarket parts
 * So that I find parts vendors and distributors
 */
public class NASIOCOwnerPartsDBUserStoryTest {
	
	static WebDriver driver = new HtmlUnitDriver();
	
	@Before
	public void setUp() throws Exception {
		
		driver.get("http://products.nasioc.com");
	}
	
	
	/*
	 * Given that I am on the "NASIOC dBase" page
	 * When I view the title
	 * Then I should see that it contains "NASIOC Products dBase"
	 */
	@Test
	public void testDBTitle() {
		
		String dBaseTitle = driver.getTitle();
		
		assertTrue(dBaseTitle.contains("NASIOC Products dBase"));		// Asserts that the title contains "NASIOC Products dBase"
	}
	
	
	/*
	 * Given that I am on the "NASIOC dBase" page
	 * Then I should see links for "Brakes", "Drivetrain", "Engine", "Interior", and "Wheels"
	 */
	@Test
	public void testProductCategories() {
		
		try {
			driver.findElement(By.linkText("Brakes"));
			driver.findElement(By.linkText("Drivetrain"));
			driver.findElement(By.linkText("Engine"));
			driver.findElement(By.linkText("Interior"));
			driver.findElement(By.linkText("Wheels"));
		}
		catch (NoSuchElementException e) {			// Test fails if the categories are missing
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the "NASIOC dBase" page
	 * When I view the search bar
	 * Then I should see radio buttons for "Web" and "NASIOC.com" 
	 */
	@Test
	public void testSearchRadioButtons() {
		
		try {
			List<WebElement> radioList = driver.findElements(By.name("sitesearch"));		// Gets the radio buttons themselves
			
			assertEquals(2, radioList.size());					// Asserts that there are exactly 2 radio buttons
			
			List<WebElement> radioLabels = driver.findElements(By.cssSelector("input + font"));		// Gets the labels for the buttons
			WebElement radioLabel = radioLabels.get(0);
			String label = radioLabel.getText();
			
			assertTrue(label.contains("Web"));				// Tests that the radio buttons have the appropriate labels
			
			radioLabel = radioLabels.get(1);
			label = radioLabel.getText();
			
			assertTrue(label.contains("NASIOC.com"));
		}
		catch (NoSuchElementException e) {
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the "NASIOC dBase" page
	 * When I search for "Advan" in the search bar
	 * Then I should see the term "Advan" appear in the search results
	 */
	@Test
	public void testSearchBar() {
		
		WebElement submitSearch = driver.findElement(By.name("sa"));
		WebElement googleSearch = driver.findElement(By.name("q"));			// Side note: 'q' is possibly the worst name for an input field ever
		
		googleSearch.sendKeys("Advan");
		submitSearch.click();
		
		try {
			String results = driver.getTitle();				// I used getTitle() as the search redirects to Google which we know is finicky with Selenium
			
			assertTrue(results.contains("Advan"));			// Test fails if the search results do not contain "Advan"
		}
		catch (NoSuchElementException e) {
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the "NASIOC dBase" page
	 * When I click the link for the "Tires" category
	 * Then I should be redirected to the "Tires" page
	 */
	@Test
	public void testTiresLink() {
		
		WebElement tiresLink = driver.findElement(By.linkText("Tires"));
		tiresLink.click();
		
		try {
			WebElement tiresPage = driver.findElement(By.cssSelector("div > table"));
			String tiresText = tiresPage.getText();
			
			assertTrue(tiresText.contains("Tires"));
		}
		catch (NoSuchElementException e) {			// Test fails if the heading is not present
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the "NASIOC dBase" page
	 * When I click the link for the "Suspension & Handling" category
	 * Then I should see a "Return to Main Listing" link in the resulting page
	 */
	@Test
	public void testReturnToMainDBasePage() {
		
		WebElement springsLink = driver.findElement(By.linkText("Suspension & Handling"));
		springsLink.click();
		
		try {
			driver.findElement(By.linkText("Return to Main Listing"));
		}
		catch (NoSuchElementException e) {
			fail();
		}
	}
}
