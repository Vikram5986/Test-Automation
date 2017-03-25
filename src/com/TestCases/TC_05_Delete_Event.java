/**
 * 
 */
package com.TestCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.PageObjectModel.Operations;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * @author Admin
 *
 */
public class TC_05_Delete_Event 
{
	WebDriver driver;
	WebDriverWait wait;
	Operations ops;
	ExtentReports report;
	ExtentTest extent;
	HSSFWorkbook wb;
	HSSFSheet sheet;
	HSSFCell cell;
	
	@BeforeClass(alwaysRun = true, description = "To check the functionality of updating an event")
	@Parameters("Browser")
	public void TestSetup(String Browser)
	{
		// managing Test Setup Fields.
		if(Browser.equalsIgnoreCase("Firefox"))
		{
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\geckodriver.exe");
			driver = new FirefoxDriver();
		}
				
		// Chrome
		else if(Browser.equalsIgnoreCase("Chrome"))
		{
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\chromedriver.exe");
			driver = new ChromeDriver();
		}
				
				
		ops = new Operations(driver);
		// Opening URL through Page Object Model.
		ops.OpenEventsURL();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver,30);
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		// Initialize Extent Reports. 
		report = new ExtentReports("C:\\Users\\Admin\\workspace\\Events\\Execution Reports\\Extent Report\\TC_05_Delete_Event.html", true);
		extent = report.startTest("Add Event", "To check the functionality of deleting an event.");
	}
	
	@Test(priority = 0, groups = "test", description = "Deleting event")
	public void AddDate() throws IOException, InterruptedException
	{
		// Import Object Repository File.
		
		File src1 = new File("C:\\Users\\Admin\\workspace\\Events\\bin\\com\\PageObjectModel\\Object_Repo.properties");
					
		FileInputStream fin1=new FileInputStream(src1);
		// Create Properties class object to read properties file
					
		Properties pro=new Properties();
		pro.load(fin1);
		
		try {
			// Clear the text field for date picker.
			for(int i = 0; i < 20; i++)
			{
				driver.findElement(By.id(pro.getProperty("events.datePicker.id"))).sendKeys(Keys.BACK_SPACE);
			}
			
			// Import Excel.
			File src = new File("C:\\Users\\Admin\\workspace\\Events\\Data Driven Excel\\Delete Event.xls");
			FileInputStream fin = new FileInputStream(src);
			wb = new HSSFWorkbook(fin);
			sheet = wb.getSheetAt(0);
			
			for(int i = 1; i<= 1; i++)
			{
				// Import data for "Date".
				try {
					cell = sheet.getRow(i).getCell(1);
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					
					// Adjust date format.
					DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = cell.getDateCellValue();
					String date1 = dateformat.format(date);
					driver.findElement(By.id(pro.getProperty("events.datePicker.id"))).sendKeys(date1);  
					driver.findElement(By.id(pro.getProperty("events.datePicker.id"))).sendKeys(Keys.ENTER);
					
					// Click on delete icon.
					driver.findElement(By.cssSelector(pro.getProperty("events.deleteBtn.css"))).click();
					// Navigate to alert.
					Alert alert = driver.switchTo().alert();
					String msgDisplayed =  alert.getText();
					alert.accept();
					
					// Set data into excel.
					
					sheet.getRow(i).createCell(2).setCellValue(msgDisplayed);
					FileOutputStream fout = new FileOutputStream(src);
					wb.write(fout);
					fout.close();
				} catch (Exception e)
				{
					// TODO Auto-generated catch block
					System.out.println("No record found");	
					extent.log(LogStatus.PASS, "No record found");
					sheet.getRow(i).createCell(4).setCellValue("No record found");
					FileOutputStream fout = new FileOutputStream(src);
					wb.write(fout);
					fout.close();
					File src2= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                    
	                // now copy the  screenshot to desired location using copyFile //method
	                FileUtils.copyFile(src2, new File("C:\\Users\\Admin\\workspace\\Events\\Execution Reports\\Failed Screenshots\\Delete_Event_error.png"));
				}

     }
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   }
	@AfterClass(alwaysRun = true)
	public void TestClosure()
	{
		report.endTest(extent);
		report.flush();
		driver.quit();
	}
}