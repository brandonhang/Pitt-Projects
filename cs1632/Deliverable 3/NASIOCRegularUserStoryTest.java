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

/**
 * As a regular user of NASIOC
 * I want to see forum links organized in different ways
 * So that I can decide which sections to visit
 */
public class NASIOCRegularUserStoryTest {
	
	static WebDriver driver = new HtmlUnitDriver();
	
	@Before
	public void setUp() throws Exception {
		
		driver.get("http://forums.nasioc.com/forums");
	}
	
	
	/*
	 * Given that I am on the main forums page
	 *    And I am not logged in
	 * When I view the welcome banner
	 * Then I should see a message that I am "currently viewing our forum as a guest"
	 */
	@Test
	public void testViewingAsGuest() {
		
		WebElement splashHeader = driver.findElement(By.cssSelector("div.midfont"));
		String splashText = splashHeader.getText();
		
		assertTrue(splashText.contains("currently viewing our forum as a guest"));		// Banner displays the guest status text
	}
	
	
	/*
	 * Given that I am on the main forums page
	 * Then I should see different NASIOC subforums categorized as "Technical",
	 *    "Classifieds", "Reviews", and "Chapters"
	 */
	@Test
	public void testSubForumLinks() {
		
		try {
			driver.findElement(By.linkText("NASIOC Technical"));
			driver.findElement(By.linkText("NASIOC Classifieds"));
			driver.findElement(By.linkText("NASIOC Reviews"));
			driver.findElement(By.linkText("NASIOC Chapters"));
		}
		catch (NoSuchElementException e) {			// Test fails if none of the categories/links are present
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the main forums page
	 * When I view the "NASIOC General" section
	 * Then I should see links for "General Community", "Motorsports", "Member's Car Gallery", and "News & Rumors" subforums
	 */
	@Test
	public void testNASIOCGeneralFollowLink() {
		
		WebElement generalLink = driver.findElement(By.linkText("NASIOC General"));		// Finds and clicks the subforum link
		generalLink.click();
		
		try {
			driver.findElement(By.linkText("General Community"));
			driver.findElement(By.linkText("Motorsports"));
			driver.findElement(By.linkText("Member's Car Gallery"));
			driver.findElement(By.linkText("News & Rumors"));
		}
		catch (NoSuchElementException e) {				// Test fails if the links are not found in the resulting page			
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the main forums page
	 * When I click the "Search" link in the navigation bar
	 * Then I should be redirected to the "Search" page
	 */
	@Test
	public void testSearchPage() {
		
		WebElement searchLink = driver.findElement(By.linkText("Search"));
		searchLink.click();
		
		try {
			driver.findElement(By.name("query"));
			driver.findElement(By.name("searchuser"));
			driver.findElement(By.name("forumchoice[]"));
			driver.findElement(By.name("dosearch"));
		}
		catch (NoSuchElementException e) {			// Test fails if a link is not present
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the main forums page
	 * When I view the "NASIOC Chapters" section
	 * Then I should see icons denoting if a new post has been made
	 */
	@Test
	public void testNewPostIcons() {
		
		WebElement chaptersLink = driver.findElement(By.linkText("NASIOC Chapters"));
		chaptersLink.click();
		
		try {				// Since the two images are mutually exclusive, this test covers both forum_old_nasioc.gif and forum_new_nasioc.gif
			driver.findElement(By.id("forum_statusicon_20"));			// in the (rare) event that only 1 icon type is displayed
			driver.findElement(By.id("forum_statusicon_26"));
			driver.findElement(By.id("forum_statusicon_19"));
		}
		catch (NoSuchElementException e) {
			fail();
		}
	}
}
