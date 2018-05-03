package com.excilys.computerdatabase.automation;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.excilys.computerdatabase.dao.HSQLDatabase;

public class DashboardAutomationTest {

  private HSQLDatabase hsqldb = HSQLDatabase.INSTANCE;

  private WebDriver driver;

  @BeforeClass
  public static void init() {
    System.setProperty("webdriver.chrome.driver", "/home/debicki/chromedriver");
  }

  @Before
  public void setUp() throws Exception {
    hsqldb.init();
    this.driver = new ChromeDriver();
  }

  @After
  public void tearDown() throws Exception {
    //driver.close();
    //driver.quit();
    //hsqldb.destroy();
  }

  @Test
  public void test() {

    driver.navigate().to("http://localhost:8086/computer-database/dashboard");

    WebElement name = driver.findElement(By.id("name"));

    //Step 3- Assertion: Check its title is correct
    //assertEquals method Parameters: Message, Expected Value, Actual Value
    assertEquals("Title check failed!", "Computer Database", driver.getTitle());

  }

}
