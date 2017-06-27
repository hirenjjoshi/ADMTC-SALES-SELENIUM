package testCases;

import libraries.ADMTcFunctions;
import libraries.GenericFunctions;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
//import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;


public class LoginTest {
	ProfilesIni allProfiles = new ProfilesIni();
	FirefoxProfile myProfile = allProfiles.getProfile("default");
//	public WebDriver driver = new FirefoxDriver(myProfile);
	public WebDriver driver;
	ADMTcFunctions admtc = new ADMTcFunctions();
	GenericFunctions general = new GenericFunctions();
	Boolean SettingsLinkExists, deleteExists, userIconExists;
	WebDriverWait wait;
	String appURL = "http://54.254.181.218/session/signin";
	String messageIncorrectCredentials = "Invalid username or password.";
	
	
	  @BeforeClass
	  public void beforeClass() {
		  
		  System.setProperty("webdriver.gecko.driver","D:\\Software\\Testing Tools\\geckodriver.exe");
		  ProfilesIni allProfiles = new ProfilesIni();
		  FirefoxProfile myProfile = allProfiles.getProfile("default");
		  driver = new FirefoxDriver(myProfile);
		  
//		  System.setProperty("webdriver.chrome.driver","D:\\Software\\Testing Tools\\chromedriver.exe");
//		  driver = new ChromeDriver();
			
		  driver.manage().window().maximize();
//		  driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		  wait = new WebDriverWait(driver,30);
		  admtc.driver = general.driver =driver;
		  admtc.wait = general.wait = wait;
	  }
	
	  @AfterClass
	  public void afterClass() {
		  driver.quit();
	  }
	
	
		
	//Verify that user can login successfully into ADMTC application, using valid credentials.	
	  @Test(priority=1)
	  public void LoginByValidCredentialsTest() throws Exception{
		  
		  //Login by specific UserID & Password
		  admtc.login(appURL, "sales@gmail.com","123");
		  Thread.sleep(4000);

		  deleteExists = driver.findElements(By.xpath("//md-toolbar-row/button[1]")).size()!=0;
		  userIconExists = driver.findElements(By.xpath("//md-toolbar-row/button[2]")).size()!=0;
	  
		  
		  if (deleteExists && userIconExists)
		  {
			  if(driver.findElement(By.xpath("//md-toolbar-row/button[1]")).isDisplayed() &&
				 driver.findElement(By.xpath("//md-toolbar-row/button[2]")).isDisplayed())
			  {
				  Reporter.log("User logged in successfully by valid credentials.", true);
				  Assert.assertTrue(true, "User logged in successfully by given valid credentials.");
				  admtc.logout();				  
			  }
			  else
			  {
		    	  Reporter.log("User could NOT logged in successfully by valid credentials.", true);
		    	  Assert.assertTrue(false, "User could NOT logged in successfully by given valid credentials.");
				  
			  }
		  }
		  else
			  Assert.assertTrue(false, "Error: Delete and/or User button(s) doesn't exist.");			  
		  		  
	  }
	
	  
	//Verify that user can NOT login into ADMTC application, using invalid email ID.	
	  @Test(priority=2)
	  public void LoginByIncorrectEmailTest() throws Exception{
		  String message;
		  
		  //Login by specific UserID & Password
		  admtc.login(appURL, "sales123@gmail.com","anything");
		  Thread.sleep(4000);

		  deleteExists = driver.findElements(By.xpath("//md-toolbar-row/button[1]")).size()!=0;
		  userIconExists = driver.findElements(By.xpath("//md-toolbar-row/button[2]")).size()!=0;	  
		  
		  if (!deleteExists && !userIconExists)
		  {
			  message = driver.findElement(By.cssSelector(".mat-text-warn>li")).getText();
			  System.out.println("Message text: "+message);
			  if(message.contains("sales123@gmail.com"))
			  {
				  Reporter.log("User log in failed for incorrect Email ID.", true);
				  Assert.assertTrue(true, "User could NOT login by incorrect Email ID.");				  
			  }
			  else
			  {
		    	  Reporter.log("Validation failed for incorrect Email ID.", true);
		    	  Assert.assertTrue(false, "Either user logged in by incorrect Email ID or no proper validation for it.");				  
			  }
		  }
		  else
			  Assert.assertTrue(false, "Error: Delete and/or User button(s) found.");		  
	  }
	  
	//Verify that user can NOT login into ADMTC application, using invalid password.	
	  @Test(priority=3)
	  public void LoginByIncorrectPasswordTest() throws Exception{
		  String message;
		  
		  //Login by specific UserID & Password
		  admtc.login(appURL, "sales@gmail.com","ABCD");
		  Thread.sleep(4000);

		  deleteExists = driver.findElements(By.xpath("//md-toolbar-row/button[1]")).size()!=0;
		  userIconExists = driver.findElements(By.xpath("//md-toolbar-row/button[2]")).size()!=0;	  
		  
		  if (!deleteExists && !userIconExists)
		  {
			  message = driver.findElement(By.cssSelector(".mat-text-warn>li")).getText();
			  System.out.println("Message text 2: "+message);
			  if(message.contains("Password is wrong"))
			  {
				  Reporter.log("User log in failed for incorrect Password.", true);
				  Assert.assertTrue(true, "User could NOT login by incorrect Password.");				  
			  }
			  else
			  {
		    	  Reporter.log("Validation failed for incorrect Password.", true);
		    	  Assert.assertTrue(false, "Either user logged in by incorrect Password or no proper validation for it.");				  
			  }
		  }
		  else
			  Assert.assertTrue(false, "Error: Delete and/or User button(s) found.");		  
	  }
	  
	  
	//Verify that login button remains disabled before entering Email ID & Password.	
	  @Test(priority=4)
	  public void LoginButtonBeforeCredentialsTest() throws Exception{
		  driver.get(appURL);
		  
		  WebElement loginButton = driver.findElement(By.cssSelector(".mat-raised-button.mat-primary"));

		  if(!(loginButton.isEnabled()))
		  {
			  Reporter.log("Login button disabled by default.", true);
			  Assert.assertTrue(true, "Login button is disabled before entering credentials.");				  
		  }
		  else
		  {
	    	  Reporter.log("Login button NOT disabled by default.", true);
	    	  Assert.assertTrue(false, "Login button does NOT disabled before entering credentials.");				  
		  }
	  }
	  
}