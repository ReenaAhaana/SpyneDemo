package spyne;

import java.io.File;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class SpyneImageTest {

	private static WebDriver driver;
	String url = "https://www.spyne.ai/image-upscaler";
	private static final String URL = "https://www.spyne.ai/image-upscaler";
	private static final String VALID_IMAGE = "test-image.jpg"; 
	private static final String CHROME_DRIVER = "c://Users//satij//Downloads//selenium-java-4.24.0//selenium-chrome-driver-4.24.0";
	private static final String EDGE_DRIVER = "c://Users//satij//Downloads//selenium-java-4.24.0//selenium-edge-driver-4.24.0";
	
	@Parameters({"Browser"})
	@BeforeClass
	public void setUp(ITestContext context,String Browser) {
		if (Browser.equalsIgnoreCase("chrome")) {
		System.setProperty("webDriver.chrome.driver",
				CHROME_DRIVER);
		driver = new ChromeDriver();
		}
		
		if (Browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.ie.driver",
					EDGE_DRIVER);
			driver = new EdgeDriver();
			}
		
		context.setAttribute("driver", driver);
	}

	@Test(description="Verify Navigation",priority = 1)
	public void testNavigation() {
		//Open URL and Verify Elements
		driver.get(URL);
		driver.navigate().to(URL);
		Assert.assertEquals(URL,driver.getCurrentUrl(),"Spyne Image Upscaler is opened");
		Assert.assertTrue(driver.findElement(By.xpath("//img[@alt='spyne logo']")).isDisplayed(),"Logo is displayed successfully");
		Assert.assertTrue(driver.findElement(By.xpath("//button[contains(text(),'Get a Demo')]")).isDisplayed(),"Get a Demo button is displayed");
		
	}
	
	
		
	@Test(description="Verify FileUpload Scenario",priority = 2)
	public void testFileUpload() throws InterruptedException {
		//Verify FileUpload scenario
		String filePath = new File(getClass().getClassLoader().getResource(VALID_IMAGE).getFile()).getAbsolutePath();
		driver.findElement(By.xpath("//div[contains(@class,'uploadBox')]//button")).click(); 
		driver.switchTo().activeElement();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		Thread.sleep(5000);
		driver.findElement(By.xpath("//input[@type='file']")).sendKeys(filePath);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.close();");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Upscaler')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Uploading')]")).isDisplayed(),"Image is uploading");
		wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@src,'test-image')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@src,'test-image')]")).isDisplayed(),"Image uploaded successfully");
		
	}
	
	@Test(description="Verify Process Upscaling Scenario",priority = 3)
	public void testProcessUpscaling() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
		Thread.sleep(10000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click()", driver.findElement(By.xpath("//button[contains(text(),'Process')]"))); 
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(),'Processing')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//*[contains(text(),'Processing')]")).isDisplayed(),"Image is Processing");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@alt,'After Image')]")));
		Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@alt,'After Image')]")).isDisplayed(),"Image is Processed");
		
	}
	
	@Test(description="Verify UI Elements",priority = 4)
	public void testUIValidation() throws InterruptedException {
		Assert.assertTrue(driver.findElement(By.xpath("//button[contains(text(),'Download')]")).isDisplayed(),"Download button is displayed");
		Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(),'Upload')]")).isDisplayed(),"Upload button is displayed");
		Assert.assertTrue(driver.findElement(By.xpath("//img[contains(@alt,'After Image')]")).isDisplayed(),"Image Preview is displayed");
		
	}
	
	
	@Test(description="Verify Download Scenario",priority = 5)
	public void testDownload() throws InterruptedException {
		String downloadDir = "C:\\Users\\satij\\Downloads";
		driver.findElement(By.xpath("//button[contains(text(),'Download')]")).click(); 
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		File downloadedFile = new File(downloadDir, "output_image.jpeg");
		int count=0;
		while (!downloadedFile.exists() || downloadedFile.length() == 0) {
		    Thread.sleep(1000);
		    count++;
		    if (count==10)
		    	break;
		}	       
		
		 // Verify file exists and is not empty
        Assert.assertTrue(downloadedFile.exists());
        Assert.assertTrue(downloadedFile.length() > 0);
        driver.close();

	}
	
	
	
}
