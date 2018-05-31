package com.excilys.computerdatabase.integration;

import java.util.ResourceBundle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.config.ApplicationConfig;
import com.excilys.computerdatabase.dao.HSQLDatabase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public abstract class AbstractIntegrationTest {

  @Autowired
  protected HSQLDatabase hsqldb;

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
    driver.close();
    driver.quit();
  }
}
