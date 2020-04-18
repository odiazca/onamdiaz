package org.choucairtesting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.choucairtesting.report.ReportToFile;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver ;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;


@SuppressWarnings("unused")
public class ChoucairTestingServicios { 
	
	private static ArrayList<String> urlListofCSV = new ArrayList<String>();
	private static Object crunchifyBuffer;

	@Test 
	public static void main(String[] args) {
	
		WebDriver driver ;
	
		ChromeOptions options = new ChromeOptions();
		options.addArguments("enable-automation");
		options.addArguments("--headless");
		options.addArguments("--window-size=1920,1080");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-extensions");
		options.addArguments("--dns-prefetch-disable");
		options.addArguments("--disable-gpu");
		options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		
		WaitForPageLoad waitForPageLoad = new WaitForPageLoad();
	

		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver.exe");
		disableSeleniumLogs();


		System.out.println("[ -----   Inicia prueba de disponibilidad de urls internas  ----- ] ");
		getLinkToTest( new ChromeDriver(options) );
		System.out.println(" [ -----   Fin de la Prueba  ----- ] ");
		
			
	}
	
	@Test 
	public static String testServiciosURL(String url, WebDriver driver ) {

		WaitForPageLoad waitForPageLoad = new WaitForPageLoad();
		String actualResult =  "";
		String expectedResult = "";
		driver.get(url);
		
		long startTime = System.currentTimeMillis();
		waitForPageLoad.waitForPageLoaded(driver);
		long endTime = System.currentTimeMillis();
		long timeElapsed = endTime - startTime;
		
		actualResult = driver.getTitle();
		Assert.assertNotNull(actualResult);
 
		 return "Execution time in milliseconds: " + timeElapsed + " Pagina cargada con exito: [" + url + "] -  Con titulo [ " + actualResult + "  ";
	
	}
	
	
	
	@Ignore  
	public static void disableSeleniumLogs() {    
	    System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
	    Logger.getLogger("org.openqa.selenium").setLevel(Level.OFF);
	}
	
	
	@Ignore  
	public static void getLinkToTest( WebDriver driver ) {
		
		try {
			
			DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
			String formattedDate = formatter.format(LocalDate.now());
			String crunchifyLine , string;			
			BufferedReader crunchifyBuffer = new BufferedReader(new FileReader("servicios.csv"));
			
			while ((crunchifyLine = crunchifyBuffer.readLine()) != null) {
				urlListofCSV.add(crunchifyLine.trim());
			}
		
			 Iterator<String> iterator = urlListofCSV.iterator();
			 
			 ReportToFile.print(formattedDate, false);
			 ReportToFile.print("[ -----   Inicia prueba de disponibilidad de urls internas  ----- ]", true);
				while (iterator.hasNext())
					{
					 	string = testServiciosURL( iterator.next(), driver);			
					 	ReportToFile.print(string, true);
					}	
				ReportToFile.print("[ -----   Fin de la Prueba  ----- ]", true);
				
		driver.close();
		driver.quit();
			 		
			crunchifyBuffer.close();
			
		} catch (IOException e) {
				driver.close();
				driver.quit();
				e.printStackTrace();
		} finally {
				try {
					if (crunchifyBuffer != null) ((BufferedReader) crunchifyBuffer).close();
				} catch (IOException crunchifyException) {
					crunchifyException.printStackTrace();
				}
		}
	}
		
	@Ignore 
		public static ArrayList<String> crunchifyCSVtoArrayList(String crunchifyCSV) {
			ArrayList<String> crunchifyResult = new ArrayList<String>();
			
			if (crunchifyCSV != null) {
				String[] splitData = crunchifyCSV.split("\\s*,\\s*");
				for (int i = 0; i < splitData.length; i++) {
					if (!(splitData[i] == null) || !(splitData[i].length() == 0)) {
						crunchifyResult.add(splitData[i].trim());
					}
				}
			}
			 return crunchifyResult;
		}	
		
		
	

	}

