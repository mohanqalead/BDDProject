package runner;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import stepDefinitions.AjaxLoaderTest;

@CucumberOptions(
    features = "src/test/java/features",
    glue = "stepDefinitions",
    plugin = {"pretty", "html:target/cucumber-reports.html"}
)

public class TestRunner extends AbstractTestNGCucumberTests  {
	WebDriver driver;
	public static String browser;
	AjaxLoaderTest ajax;
	
	private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    static {
        // Initialize ExtentReports
    	ExtentSparkReporter htmlReporter = new ExtentSparkReporter("test-output/ExtentReport.html");
        htmlReporter.config().setDocumentTitle("Cucumber Test Report");
        htmlReporter.config().setReportName("Test Report");
        htmlReporter.config().setTheme(Theme.STANDARD);

        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Tester", "Tester Name");
    }
    
    @Parameters("browser")
    @BeforeClass
    public void browserSetup(@Optional("chrome")String browser) {
    	this.browser = browser;
    	ajax = new AjaxLoaderTest();
    }
    
    @Parameters("browser")
    @BeforeMethod
    public void setup(@Optional("chrome")String browser) {   	
    	
        ExtentTest extentTest = extent.createTest("Test Scenario");
        test.set(extentTest);
        
    }
    
   
    
    @AfterMethod
    public void teardown(ITestResult result) {
    	
        ExtentTest extentTest = test.get();

        if (result.getStatus() == ITestResult.FAILURE) {
            // Mark the test as failed in the report
        	driver = ajax.driver;
            extentTest.log(Status.FAIL, "Test Failed: " + result.getThrowable());

            // Capture screenshot on failure
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = "test-output/screenshots/" + result.getName() + ".png";
            try {
                //Files.createDirectories(Paths.get("test-output/screenshots/"));
                Files.copy(screenshot.toPath(), Paths.get(screenshotPath));             
                
                extentTest.addScreenCaptureFromPath(screenshotPath);
               
            } catch (IOException e) {
                extentTest.log(Status.FAIL, "Could not save screenshot: " + e.getMessage());
            }
            driver.quit();
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            extentTest.log(Status.PASS, "Test Passed");
        } else if (result.getStatus() == ITestResult.SKIP) {
            extentTest.log(Status.SKIP, "Test Skipped");
        }
       
        extent.flush();
        System.out.println("Tearing down after the scenario...");
        
    }
    
    

		
	@Override
    @DataProvider(parallel=true)
    public Object[][] scenarios() {
        Object[][] scenarios = super.scenarios();
        Object[][] repeatedScenarios = new Object[scenarios.length * 10][2];

        // Repeat each scenario 10 times in the DataProvider
        for (int i = 0; i < scenarios.length; i++) {
            for (int j = 0; j < 10; j++) {
                repeatedScenarios[i * 10 + j] = scenarios[i];
            }
        }
        return repeatedScenarios;
    }

    @Test(dataProvider = "scenarios")
    public void runScenario(PickleWrapper pickleWrapper, FeatureWrapper featureWrapper) {
        // Run the scenario with the provided arguments
        super.runScenario(pickleWrapper, featureWrapper);
    	
    	
    	
    	
    } 
    
}



