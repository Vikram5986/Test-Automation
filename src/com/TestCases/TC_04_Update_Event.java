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
public class TC_04_Update_Event 
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
		report = new ExtentReports("C:\\Users\\Admin\\workspace\\Events\\Execution Reports\\Extent Report\\TC_04_Update_Event.html", true);
		extent = report.startTest("Add Event", "To check the functionality of updating an event.");
	}
	
	@Test(priority = 0, groups = "test", description = "Updating event")
	public void AddDate() throws IOException, InterruptedException
	{
		
		// Import object repository.
		File src1 = new File("C:\\Users\\Admin\\workspace\\Events\\bin\\com\\PageObjectModel\\Object_Repo.properties");
		FileInputStream fin1 = new FileInputStream(src1);
		Properties pro = new Properties();
		pro.load(fin1);
		
		try {
			// Clear the text field for date picker.
			for(int i = 0; i < 20; i++)
			{
				driver.findElement(By.id(pro.getProperty("events.datePicker.id"))).sendKeys(Keys.BACK_SPACE);
			}
			
			// Import Excel.
			File src = new File("C:\\Users\\Admin\\workspace\\Events\\Data Driven Excel\\Update Event.xls");
			FileInputStream fin = new FileInputStream(src);
			wb = new HSSFWorkbook(fin);
			sheet = wb.getSheetAt(0);
			
			for(int i = 1; i<= 1; i++)
			{
				try {
					// Import data for "Date".
					cell = sheet.getRow(i).getCell(1);
					cell.setCellType(Cell.CELL_TYPE_NUMERIC);
					
					// Adjust date format.
					DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
					Date date = cell.getDateCellValue();
					String date1 = dateformat.format(date);
					driver.findElement(By.id(pro.getProperty("events.datePicker.id"))).sendKeys(date1);  
					driver.findElement(By.id(pro.getProperty("events.datePicker.id"))).sendKeys(Keys.ENTER);			
					
					// Open the edit wizard.
					driver.findElement(By.xpath(pro.getProperty("events.rowedit_update.xpath"))).click();
					Thread.sleep(2500);
					
					// Clear all the fields.
					
                    // Import data for "Date".
                     cell = sheet.getRow(i).getCell(1);
                     cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                    // Adjust date format.
                    driver.findElement(By.id(pro.getProperty("events.dateFieldTextBox.id"))).clear();
                    driver.findElement(By.id(pro.getProperty("events.dateFieldTextBox.id"))).sendKeys(date1);
                    extent.log(LogStatus.INFO, "Data used for date field is = " +date);
							
                   // Import data for "Type".
                   cell = sheet.getRow(i).getCell(2);
                   cell.setCellType(Cell.CELL_TYPE_STRING);
                   driver.findElement(By.id(pro.getProperty("events.typeFieldTextBox.id"))).clear();
                   driver.findElement(By.id(pro.getProperty("events.typeFieldTextBox.id"))).sendKeys(cell.getStringCellValue());
                   extent.log(LogStatus.INFO, "Data used for type is = " +cell.getStringCellValue());
							
                  // Import data for "Summary".
                  cell = sheet.getRow(i).getCell(3);
                  cell.setCellType(Cell.CELL_TYPE_STRING);
                  driver.findElement(By.id(pro.getProperty("events.summaryFieldTextBox.id"))).clear();
                  driver.findElement(By.id(pro.getProperty("events.summaryFieldTextBox.id"))).sendKeys(cell.getStringCellValue());
                  extent.log(LogStatus.INFO, "Data used for summary is = " +cell.getStringCellValue());
							
                 // Import data for "Metric".
                 cell = sheet.getRow(i).getCell(4);
                 cell.setCellType(Cell.CELL_TYPE_STRING);
                 driver.findElement(By.id(pro.getProperty("events.metricFieldTextBox.id"))).clear();
                 driver.findElement(By.id(pro.getProperty("events.metricFieldTextBox.id"))).sendKeys(cell.getStringCellValue());
                 extent.log(LogStatus.INFO, "Data used for Metric is = " +cell.getStringCellValue());
							
                 // Import data for "Event Details".
                 cell = sheet.getRow(i).getCell(5);
                 cell.setCellType(Cell.CELL_TYPE_STRING);
                 driver.findElement(By.id(pro.getProperty("events.detailsFieldTextBox.id"))).clear();
                 driver.findElement(By.id(pro.getProperty("events.detailsFieldTextBox.id"))).sendKeys(cell.getStringCellValue());
                 extent.log(LogStatus.INFO, "Data used for Event field is = " +cell.getStringCellValue());
							
                 // Click on save button.
                 driver.findElement(By.id(pro.getProperty("events.saveBtnAdd_Update.id"))).click();
                 extent.log(LogStatus.INFO, "Clicked on save button.");
                 Thread.sleep(3000);
                 if(driver.findElement(By.id(pro.getProperty("events.saveBtnAdd_Update.id"))).isDisplayed() )
					{
						extent.log(LogStatus.FAIL, "Record updation failed.");
						sheet.getRow(i).createCell(6).setCellValue("Record updation failed.");
						System.out.println("Record updation failed.");
						FileOutputStream fout = new FileOutputStream(src);
						wb.write(fout);
						fout.close();
					}
                else
					{
					    System.out.println("Record updated successfully");	
						extent.log(LogStatus.PASS, "Record updated successfully");
						sheet.getRow(i).createCell(6).setCellValue("Record updated successfully.");
						FileOutputStream fout = new FileOutputStream(src);
						wb.write(fout);
						fout.close();
					}
				} catch (Exception e) 
				{
					// TODO Auto-generated catch block
					System.out.println("No record found");	
					extent.log(LogStatus.PASS, "No record found");
					sheet.getRow(i).createCell(6).setCellValue("No record found");
					FileOutputStream fout = new FileOutputStream(src);
					wb.write(fout);
					fout.close();
					File src2= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                    // now copy the  screenshot to desired location using copyFile //method
	                FileUtils.copyFile(src2, new File("C:\\Users\\Admin\\workspace\\Events\\Execution Reports\\Failed Screenshots\\Update_Event_error.png"));
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