package libraries;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
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
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;






public class GenericFunctions {
	
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
	
	


	
	
	public void ajaxLoading()
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
		}
		

	}
	
	public void waitForPageLoad()
	{
//		System.out.println("Wait for page load started");
//		objDriver = driver;
		
		try
		{		
//				ajaxLoading();				
//				ajaxLoading();
				ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>(){
		//			@Override			
					public Boolean apply(WebDriver driver)
					{
						js = (JavascriptExecutor) driver;
						try
						{					
							return js.executeScript("return document.readyState").equals("complete");

						}
						
						catch(WebDriverException e)
						{
							System.out.println("\n" + "JAVA SCRIPT ERROR." + "\n");
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
				
//				ajaxLoading();
//				ajaxLoading();
		}
		catch (Exception e)
		{
			e.printStackTrace();			
		}
	}	
	
	public void advancedClick(WebElement element)
	{
		try
		{		
		  js =(JavascriptExecutor)driver;
		  js.executeScript("arguments[0].click();", element);
		}
		catch (Exception e)
		{
			System.out.println("JavaScript Error occurred during advanced click.");
			e.printStackTrace();
		}
		
	}
	

	public void captureScreenshot(String screenshotName)
	{
		 
		try 
		{
			TakesScreenshot ts=(TakesScreenshot)driver;			 
			File source=ts.getScreenshotAs(OutputType.FILE);			 
			FileUtils.copyFile(source, new File("./Screenshots/"+screenshotName+".png"));
			System.out.println("Screenshot taken");			
		} 
		catch (Exception e)
		{		 
			System.out.println("Exception while taking screenshot "+e.getMessage());
		} 
	}

}
