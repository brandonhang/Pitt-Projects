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
 * As a NASIOC member
 * I want to log in
 * So that I can start internet fights
 */
public class NASIOCMemberUserStoryTest {
	
	static WebDriver driver = new HtmlUnitDriver();
	
	@Before
	public void setUp() throws Exception {
		
		driver.get("http://forums.nasioc.com");
	}
	
	
	/*
	 * Given that I am on the home page
	 *    And I am not logged in
	 * When I try to log in with a valid username and an invalid password
	 * Then I should receive an "invalid password" error page
	 */
	@Test
	public void testInvalidPasswordPage() {
		
		driver.findElement(By.name("vb_login_username")).sendKeys("BringBackTheHatch");		// Note: this is not my actual username... but the sentiment is real
		driver.findElement(By.name("vb_login_password")).sendKeys("password");
		
		WebElement loginBox = driver.findElement(By.id("collapseobj_module_9"));
		WebElement loginButton = loginBox.findElement(By.className("button"));
		
		loginButton.click();
		
		try {																	// Checks to see if the invalid username/password message appears
			WebElement messageBox = driver.findElement(By.className("panelsurround"));
			String message = messageBox.getText();
			
			assertTrue(message.contains("You have entered an invalid username or password") ||		// Default invalid credentials page
					message.contains("Wrong username or password"));				// Alternative error page when too many attempts have been made
		}
		catch (NoSuchElementException e) {
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the home page
	 *    And I am not logged in
	 * When I try to log in with no username and no password
	 * Then I should receive an "invalid password" error page
	 */
	@Test
	public void testNoLoginCredentials() {
		
		driver.findElement(By.name("vb_login_username")).sendKeys("");
		driver.findElement(By.name("vb_login_password")).sendKeys("");
		
		WebElement loginBox = driver.findElement(By.id("collapseobj_module_9"));
		WebElement loginButton = loginBox.findElement(By.className("button"));
		
		loginButton.click();
		
		try {																	// Checks to see if the invalid username/password message appears
			WebElement messageBox = driver.findElement(By.className("panelsurround"));
			String message = messageBox.getText();
			
			assertTrue(message.contains("You have entered an invalid username or password") ||
					message.contains("Wrong username or password"));
		}
		catch (NoSuchElementException e) {
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the home page
	 *    And I am not logged in
	 * When I try to log in with a valid username and an invalid password
	 * Then I should receive an error page with a link to reset my password
	 */
	@Test
	public void testErrorPageResetPassword() {
		
		driver.findElement(By.name("vb_login_username")).sendKeys("BringBackTheHatch");
		driver.findElement(By.name("vb_login_password")).sendKeys("password");
		
		WebElement loginBox = driver.findElement(By.id("collapseobj_module_9"));
		WebElement loginButton = loginBox.findElement(By.className("button"));
		
		loginButton.click();
		
		try {																	// Checks to see if the reset password link is present
			driver.findElement(By.linkText("here"));
		}
		catch (NoSuchElementException e) {
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the home page
	 *    And I am not logged in
	 * When I click the "Forgot Password" link in the footer
	 * Then I should see a form to allow me to reset my password
	 */
	@Test
	public void testForgotPasswordForm() {
		
		WebElement forgotPassword = driver.findElement(By.linkText("Forgot Password"));
		
		forgotPassword.click();			// Clicks the "Forgot Password" link
		
		try {							// Checks for the presence of a form with an input field and a submit button
			driver.findElement(By.name("email"));
			
			WebElement form = driver.findElement(By.className("panelsurround"));
			form.findElement(By.cssSelector("input[type='submit']"));		// Thanks, CS 1520!
		}
		catch (NoSuchElementException e) {
			fail();
		}
	}
	
	
	/*
	 * Given that I am on the home page
	 *    And I am not logged in
	 * When I view the "Log in" section
	 * Then I should see a "Remember Me?" check box
	 */
	@Test
	public void testRememberMe() {
		
		WebElement rememberMe = driver.findElement(By.id("collapseobj_module_9"));
		String rememberMeText = rememberMe.getText();
		assertTrue(rememberMeText.contains("Remember Me?"));		// Checks for the displayed text
		
		try {								// Tries to find the check box to "remember me"
			driver.findElement(By.id("cb_cookieuser"));
		}
		catch (NoSuchElementException e) {			// Fails if the check box is missing
			fail();
		}
	}
}
