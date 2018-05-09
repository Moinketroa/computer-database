package com.excilys.computerdatabase.integration;

import java.util.ResourceBundle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.HSQLDatabase;

public abstract class AbstractIntegrationTest {

  protected HSQLDatabase hsqldb = HSQLDatabase.INSTANCE;

  protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractIntegrationTest.class);

  protected static WebDriver driver;

  @BeforeClass
  public static void init() {
    ResourceBundle config = ResourceBundle.getBundle("config");

    LOGGER.debug("Locating the selenium chrome driver");
    System.setProperty("webdriver.chrome.driver", config.getString("driverPath"));
    driver = new ChromeDriver();
  }

  @Before
  public void setUp() throws Exception {
    hsqldb.init();
  }

  @After
  public void tearDown() throws Exception {
    hsqldb.destroy();
  }

  @AfterClass
  public static void quit() {
    //driver.close();
    //driver.quit();
  }
}
