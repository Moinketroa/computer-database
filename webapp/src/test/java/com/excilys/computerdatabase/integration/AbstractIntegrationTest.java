package com.excilys.computerdatabase.integration;

import java.util.ResourceBundle;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.config.ControllerConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ControllerConfig.class)
public abstract class AbstractIntegrationTest {

  protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractIntegrationTest.class);

  protected static WebDriver driver;

  @BeforeClass
  public static void init() {
    ResourceBundle config = ResourceBundle.getBundle("config");

    LOGGER.debug("Locating the selenium chrome driver");
    System.setProperty("webdriver.chrome.driver", config.getString("driverPath"));
    driver = new ChromeDriver();
  }

  @AfterClass
  public static void quit() {
    driver.close();
    driver.quit();
  }
}
