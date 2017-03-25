/**
 * 
 */
package com.PageObjectModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;

/**
 * @author Admin
 *
 */
public class Operations 
{
	WebDriver driver;
	public Operations(WebDriver driver)
	{
		this.driver = driver;
	}
	
	// Open URL.
	public void OpenEventsURL()
	{
		driver.get("http://localhost:9999/eventlist/events.jsp");
	}
	
	// Check the header links.
	// Upload.
	
	

}
