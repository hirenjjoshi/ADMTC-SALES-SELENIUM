package libraries;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.internal.Coordinates;
//import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
//import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;

import org.openqa.selenium.internal.Locatable;





public class ADMTcFunctions {
	
//	WebDriver objDriver;
	public WebDriver driver;
	public WebDriverWait wait;
	public JavascriptExecutor js;
	public WebElement parentElement;
	Actions action;
	public Robot r;
	
	Date date = new Date();	
	DateFormat dateFormat2 = new SimpleDateFormat("ddMMyyyyHHmmss");
	String claimNo1 = "a".concat(dateFormat2.format(date));
	String claimNo2 = "01";
	String vCurrentDataFile;
	FileInputStream inputStream;
	FileOutputStream outputStream;
	File vfile;
	int vRowIndex;
	boolean bFileWritten;
	
	GenericFunctions general = new GenericFunctions();
//	CommonFunctionLibrary commonFunction;
	
	
	public ADMTcFunctions()
	{		
		general.driver = driver;
		general.wait = wait;		
	}
	
	
	
	
	
	
	
	private static boolean isNumeric(String string) 
	{
		if(string == null)
		{
			return false;
		}
		else
		{
			return string.matches("^[-+]?\\d+(\\.\\d+)?$");
		}
	}
	

	

	
	
	public void AJAxLoading()
//	public void AJAxLoading() throws Exception
	{
		try
		{
			if(driver.findElements(By.className("preloaderContainer")).size()!=0)
			{
				  wait.until(ExpectedConditions.presenceOfElementLocated(By.className("preloaderContainer")));			  
				  wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("preloaderContainer")));
			}
		}
		catch (Exception e)
		{
			System.out.println("JavaScript Error occurred during AJAX loading.");
//			e.printStackTrace();			
		}
	}
	
	public void WaitForPageLoad()
	{
		
		try
		{		

				ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>(){
//					@Override			
					public Boolean apply(WebDriver driver)
					{
						js = (JavascriptExecutor) driver;
						try
						{					
							return js.executeScript("return document.readyState").equals("complete");
							
						}
						
						catch(WebDriverException e)
						{
							System.out.println("JAVA SCRIPT ERROR.");
							return false;
						}					
					}
				};
				
				try 
				{
						wait.until(expectation);						
				} 
		
				catch(Throwable error) 
				{
					error.printStackTrace();
				}				


		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}
	}
	
	
	public boolean isClickable(WebElement ele)
	{
		try
		{
//			ele.click();
			general.advancedClick(ele);
			general.waitForPageLoad();
			return true;			
		}
		catch (Exception e) {
			return false;
		}
	}

	public void login(String EnvURL, String UserID, String Password)
	{
		  
		  driver.get(EnvURL);
		  general.waitForPageLoad();
		  driver.findElement(By.id("md-input-1")).clear();
		  driver.findElement(By.id("md-input-1")).sendKeys(UserID);
		  driver.findElement(By.id("md-input-3")).clear();
		  driver.findElement(By.id("md-input-3")).sendKeys(Password);
		  general.waitForPageLoad();
//		  fluentWait;
		  driver.findElement(By.xpath("//button[@type='submit']")).sendKeys(Keys.ENTER);		  
		  general.waitForPageLoad();		  
	}
	
	public void logout()
	{
//		js.executeScript("window.focus();");
//		driver.findElement(By.linkText("Settings")).sendKeys(Keys.ENTER);
//		driver.findElement(By.xpath("//a[@class='dropdown-toggle']")).sendKeys(Keys.ENTER);
		driver.findElement(By.xpath("//md-toolbar-row/button[2]")).sendKeys(Keys.ENTER);
//		general.advancedClick(driver.findElement(By.xpath("//button[@type='button']")));
		driver.findElement(By.xpath(".//*[@id='cdk-overlay-0']/div/div/button[4]")).sendKeys(Keys.ENTER);
//		driver.findElement(By.partialLinkText("Logout")).sendKeys(Keys.ENTER);		  
		general.waitForPageLoad();		  
	}	

}
