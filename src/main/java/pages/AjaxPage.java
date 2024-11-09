package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AjaxPage {
	
	WebDriver driver;
	
	@FindBy(id="button1")
	public WebElement clickMeButton;
	
	@FindBy(id="myModalClick")
	public WebElement modalPopup;
	
	@FindBy(xpath = "*//button[@class='close']")
	public WebElement modalPopupClose;
	
	public AjaxPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver,this);
	}

}
