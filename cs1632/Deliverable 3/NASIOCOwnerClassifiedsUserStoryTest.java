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
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * As a Subaru owner
 * I want to browse private classifieds
 * So that I can buy, sell, or trade car parts
 */
public class NASIOCOwnerClassifiedsUserStoryTest {
	
	static WebDriver driver = new HtmlUnitDriver();
	
	@Before
	public void setUp() throws Exception {
		
		driver.get("http://forums.nasioc.com/forums/forumdisplay.php?f=162");
	}
	
	
	/*
	 * Given that I am on the "Complete Car Part-Out" page
	 * When I view the title
	 * Then I should see that it contains "Complete Car Part-Out"
	 */
	@Test
	public void testClassifiedsTitle() {
		
		String partOut = driver.getTitle();
		
		assertTrue(partOut.contains("Complete Car Part-Out"));		// Test fails if the title does not contain this text
	}
	
	
	/*
	 * Given that I am on the "Complete Car Part-Out" page
	 * When I view the "Threads in Forum" section
	 * Then I should see a link to a "Part-Out Forum Guidelines" sticky (a "permanent" thread)
	 */
	@Test
	public void testGuidelinesSticky() {
		
		try {
			driver.findElement(By.linkText("Part-Out Forum Guidelines, READ BEFORE POSTING!"));
		}
		catch (NoSuchElementException e) {			// Fails if the link does not exist
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the "Complete Car Part-Out" page
	 * When I view the forum table headers
	 * Then I should see that it is separated by "Thread / Thread Starter", "Last Post", "Replies", and "Views"
	 */
	@Test
	public void testForumTableHeaders() {
		
		try {
			driver.findElement(By.linkText("Thread"));
			driver.findElement(By.linkText("Thread Starter"));
			driver.findElement(By.linkText("Last Post"));
			driver.findElement(By.linkText("Replies"));
			driver.findElement(By.linkText("Views"));
		}
		catch (NoSuchElementException e) {			// Test fails if the headers do not exist
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the "Complete Car Part-Out" page
	 *    And I view the "Display Options" section
	 * When I click the "Prefix" drop menu
	 * Then I should see options for "(any prefix)", "(no prefix)",
	 *    "FS: (For Sale)", or "FS/FT: (For Sale or Trade)"
	 */
	@Test
	public void testDisplayOptionsMenu() {
		
		String[] optionStrings = {"(any prefix)", "(no prefix)", "FS: (For Sale)", "FS/FT: (For Sale or Trade)"};
		
		WebElement dropMenuElement = driver.findElement(By.id("sel_prefixid"));
		Select dropdownMenu = new Select(dropMenuElement);				// Gets the options from the menu as a Select object
		List<WebElement> optionsList = dropdownMenu.getOptions();
		
		for (int i = 0; i < optionsList.size(); i++) {			// This loop gets the options from the List and compares each one to the String array
			WebElement option = optionsList.get(i);
			String optionText = option.getText();
			assertTrue(optionText.contains(optionStrings[i]));
		}
	}
	
	
	/*
	 * Given that I am on the "Complete Car Part-Out" page
	 * When I view the "Threads in Forum" section
	 * Then I should see an button (as an image) to start a new thread
	 */
	@Test
	public void testNewThreadButton() {
		
		WebElement newThread = driver.findElement(By.id("inlinemodform"));
		
		try {
			newThread.findElement(By.tagName("img"));		// Test fails if it cannot find the "New Thread" image/button
		}
		catch (NoSuchElementException e) {
			fail();
		}
	}
}
