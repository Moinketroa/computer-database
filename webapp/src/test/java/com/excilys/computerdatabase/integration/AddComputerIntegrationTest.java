package com.excilys.computerdatabase.integration;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class AddComputerIntegrationTest extends AbstractIntegrationTest {

  @Test
  public void addComputerTest() {
    driver.navigate().to("http://localhost:8086/computer-database/addComputer");

    assertEquals("Computer Database", driver.getTitle());

    WebElement nameInput = driver.findElement(By.id("name"));
    WebElement introducedInput = driver.findElement(By.id("introduced"));
    WebElement discontinuedInput = driver.findElement(By.id("discontinued"));
    WebElement companyInput = driver.findElement(By.id("companyId"));

    nameInput.sendKeys("Integration Test");
    introducedInput.sendKeys("12/12/2012");
    discontinuedInput.sendKeys("13/12/2012");

    List<WebElement> companyInputList = companyInput.findElements(By.xpath("./option"));
    for (WebElement companyOption : companyInputList) {
      if (companyOption.getAttribute("value").equals("5")) {
        companyOption.click();
      }
    }

    WebElement addComputerButton = driver.findElement(By.id("addComputerButton"));
    //addComputerButton.click();
  }

}
