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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * As a visitor to NASIOC
 * I want information about this forum
 * So that I can learn what this site is all about
 */
public class NASIOCVisitorUserStoryTest {
	
	static WebDriver driver = new HtmlUnitDriver();
	
	@Before
	public void setUp() throws Exception {
		
		driver.get("http://forums.nasioc.com");
	}
	
	
	/*
	 * Given that I am on the home page
	 * When I view the title
	 * Then I should see that it contains "North American Subaru Impreza Owners Club"
	 */
	@Test
	public void testHomeTitle() {
		
		String title = driver.getTitle();
		
		assertTrue(title.contains("North American Subaru Impreza Owners Club"));
	}
	
	
	/*
	 * Given that I am on the home page
	 * When I view the header
	 * Then I should see today's date
	 */
	@Test
	public void testTodaysDate() {
		
		DateFormat dateFormat = new SimpleDateFormat("EEEE MMMM d, y");		// Uses a built-in library of Java for date formatting
		String todaysDate = dateFormat.format(new Date());
		
		WebElement header = driver.findElement(By.id("nointelliTXT"));
		String headerText = header.getText();
		
		assertTrue(headerText.contains(todaysDate));			// Asserts that the dates match
	}
	
	
	/*
	 * Given that I am on the home page
	 * When I see the "Site Navigation" sidebar
	 * Then I should see links for the "About", "Rules", and "FAQ" sections
	 */
	@Test
	public void testSidebarLinks() {
		
		WebElement sidebar = driver.findElement(By.id("collapseobj_module_14"));
		
		try {
			sidebar.findElement(By.linkText("About"));
			sidebar.findElement(By.linkText("Rules"));
			sidebar.findElement(By.linkText("FAQ"));
		}
		catch (NoSuchElementException e) {			// Test fails if sidebar links are not found
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the home page
	 * When I click the "Privacy" link in the footer
	 * Then I should be redirected to the "Privacy Policy" page
	 */
	@Test
	public void testPrivacyPolicyLink() {
		
		WebElement privacy = driver.findElement(By.linkText("Privacy"));
		privacy.click();
		
		WebElement privacyPage;
		String privacyPolicy;
		
		try {												// Tries to find the title of the privacy policy
			privacyPage = driver.findElement(By.className("tcat"));
			privacyPolicy = privacyPage.getText();
			
			assertTrue(privacyPolicy.contains("Privacy Policy"));
		}
		catch (NoSuchElementException e) {
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the home page
	 * When I view the "Log in" section
	 * Then I should see that it contains the phrase "Not a member yet?"
	 */
	@Test
	public void testNotAMemberPhrase() {
		
		WebElement loginBox = driver.findElement(By.id("collapseobj_module_9"));
		String loginText = loginBox.getText();
		
		assertTrue(loginText.contains("Not a member yet?"));		// Looks for the displayed text
	}
	
	
	/*
	 * Given that I am on the home page
	 * When I click the "Register Now!" link in the Log in section
	 * Then I should be redirected to the forum rules page
	 */
	@Test
	public void testRegistrationLink() {
		
		WebElement registration = driver.findElement(By.linkText("Register Now!"));
		registration.click();
		
		try {
			WebElement rulesPage = driver.findElement(By.cssSelector(".page form"));
			String rulesText = rulesPage.getText();
			
			assertTrue(rulesText.contains("NASIOC Rules"));
		}
		catch (NoSuchElementException e) {				// Test fails if the heading could not be found
			fail();
		}
	}
}
