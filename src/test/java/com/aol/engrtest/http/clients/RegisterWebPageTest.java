package com.aol.engrtest.http.clients;

import junit.framework.TestCase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 *
 * @author Pankaj Patel
 */
public class RegisterWebPageTest extends TestCase {

    private WebDriver driver;

    public RegisterWebPageTest(String testName) {
        super(testName);
    }

    @Override
    protected void setUp() throws Exception {
        driver = new FirefoxDriver();
        driver.get("http://localhost:8080/aws2321/");
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        driver.quit();
    }

    public void testRegister() {
        // Verify title of register page
        verifyTitle("Signup");

        //Enter firstname
        enterInputValue("firstname", "Pankaj");
        //Enter lastname
        enterInputValue("lastname", "Patel");
        //Enter email
        enterInputValue("email", "pankajnpatel@yahoo.com");
        //Enter password
        enterInputValue("password", "Pankaj1");
        //Enter conformation
        enterInputValue("confirmation", "Pankaj1");

        //Submit registration form
        clickButton("btn_bogin");
        
        // Sleep until the div we want is visible or 20 seconds is over
        long end = System.currentTimeMillis() + 20000;
        while (System.currentTimeMillis() < end) {
            WebElement resultsDiv = driver.findElement(By.className("error"));
            if (resultsDiv.isDisplayed()) {
                break;
            }
        }

        WebElement error = driver.findElement(By.xpath("//div[@class='error']"));
        
        assertNull("Error while creating user account: " +error.getText(), error.getText());

    }

    private void verifyTitle(String expectedTitle) {
        String actualTitle = driver.getTitle();
        assertEquals("Call register.jsp page to create user account", actualTitle, expectedTitle);
    }

    private void enterInputValue(String htmlTag, String userName) {
        WebElement element = driver.findElement(By.name(htmlTag));
        element.sendKeys(userName);
    }

    private void clickButton(String htmlTag) {
        WebElement element = driver.findElement(By.name(htmlTag));
        element.submit();
    }
}
