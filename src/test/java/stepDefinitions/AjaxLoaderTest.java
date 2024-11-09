package stepDefinitions;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.AjaxPage;
import pages.HomePage;
import runner.TestRunner;
import utils.TestBase;



public class AjaxLoaderTest extends TestBase {
	
	public WebDriver driver;
	HomePage homePage;
	AjaxPage ajaxPage;
	String parentWindow;
	

    // Hook for setup (Before each scenario)
    @Before
    public void setUp() throws Exception {
    	driver = initBrowser(TestRunner.browser);
        driver.manage().window().maximize();
        homePage = new HomePage(driver);
        ajaxPage = new AjaxPage(driver);
    }

    // Step to launch the URL
    @Given("I launch the URL {string}")
    public void i_launch_the_url(String url) {
        driver.get(url);
    }

    // Step to verify AJAX LOADER is present
    @When("I verify the AJAX LOADER is present")
    public void i_verify_the_ajax_loader_is_present() {
        waitForElementDisplayed(driver, homePage.ajaxLoaderLink);
        Assert.assertTrue("AJAX Loader is not present!", homePage.ajaxLoaderLink.isDisplayed());
    }

    // Step to click on AJAX LOADER link
    @When("I click on the AJAX LOADER link")
    public void i_click_on_the_ajax_loader_link() {
        
        homePage.ajaxLoaderLink.click();
    }

    // Step to click on "Click Me!" button
    @When("I click on the Click Me button")
    public void i_click_on_the_button() {
    	parentWindow = driver.getWindowHandle();
    	switchWindow(driver);
    	waitInvisibilityPageLoader(driver);
    	waitForElementDisplayed(driver, ajaxPage.clickMeButton);
        ajaxPage.clickMeButton.click();
    }

    // Step to verify the popup is displayed
    @Then("I verify the popup is displayed")
    public void i_verify_the_popup_is_displayed() throws InterruptedException {
    	
    	waitForElementDisplayed(driver, ajaxPage.modalPopup);
        Assert.assertTrue("Popup is not displayed!", ajaxPage.modalPopup.isDisplayed());        
        ajaxPage.modalPopupClose.click();
        driver.close();        
        driver.switchTo().window(parentWindow);
    }

 

    // Hook for teardown (After each scenario)
    @After
    public void tearDown(Scenario scenario) {
        // Ensure that the browser is closed after each test
    	if (!scenario.isFailed()) {
        if (driver != null) {
            driver.quit();
        }
    	}
    }
}

