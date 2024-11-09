package utils;

import java.time.Duration;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {
	
	public static WebDriver driver;
	WebDriverWait wait;

	
	public void waitForElementDisplayed(WebDriver driver, WebElement element) {
		wait = new WebDriverWait(driver,Duration.ofSeconds(5));
		wait.until(ExpectedConditions.visibilityOf(element));
	}
	
	public void waitInvisibilityPageLoader(WebDriver driver) {
		wait = new WebDriverWait(driver,Duration.ofSeconds(10));
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("loader")));
	}
	
	public void switchWindow(WebDriver driver) {
		String parent=driver.getWindowHandle();

		Set<String>s=driver.getWindowHandles();

		// Now iterate using Iterator
		Iterator<String> I1= s.iterator();

		while(I1.hasNext())
		{

		String child_window=I1.next();


		if(!parent.equals(child_window))
		{
		driver.switchTo().window(child_window);

		
		}

		} 
	}
	
	public static WebDriver initBrowser(String browser){
		try {
			switch (browser) {
			case "chrome":
				System.setProperty("webdriver.chrome.driver","Resources\\chromedriver.exe");
				
				driver = new ChromeDriver();
								
				break;

			default:
				break;
			}
			//return driver;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return driver;
		
		
		
	}
	
}
