package com.excilys.computerdatabase.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

public class EditComputerIntegrationTest extends AbstractIntegrationTest {

  @Test
  public void test() {
    driver.navigate().to("http://localhost:8086/computer-database-webapp/computer?&computerId=8");

    WebElement editComputerButton = driver.findElement(By.id("editComputer"));

    editComputerButton.click();

    // EDIT COMPUTER PAGE

    WebElement title = driver.findElement(By.id("title"));
    WebElement errorMsg = driver.findElement(By.id("errorMsg"));
    WebElement computerName = driver.findElement(By.id("name"));
    WebElement computerIntroduced = driver.findElement(By.id("introduced"));
    WebElement computerDiscontinued = driver.findElement(By.id("discontinued"));
    List<WebElement> companies = driver.findElements(By.tagName("option"));
    WebElement editButtonForm = driver.findElement(By.id("editComputerButton"));

    assertEquals("Edit Computer #8", title.getText());
    assertEquals("Apple IIc", computerName.getAttribute("value"));
    assertEquals("", computerIntroduced.getText());
    assertEquals("", computerDiscontinued.getText());

    assertTrue(editButtonForm.isEnabled());
    assertFalse(errorMsg.isDisplayed());

    computerName.clear();
    computerName.sendKeys("Apple IIC");
    computerIntroduced.sendKeys("12/12/2012");
    computerDiscontinued.sendKeys("11/12/2012");

    assertFalse(editButtonForm.isEnabled());
    assertTrue(errorMsg.isDisplayed());

    computerName.clear();
    computerIntroduced.sendKeys("12/12/2012");
    computerDiscontinued.sendKeys("13/12/2012");

    assertFalse(editButtonForm.isEnabled());
    assertTrue(errorMsg.isDisplayed());

    computerName.sendKeys("Apple IIC");
    for (WebElement companyOption : companies) {
      if (companyOption.getAttribute("value").equals("1")) {
        companyOption.click();
      }
    }

    assertTrue(editButtonForm.isEnabled());
    assertFalse(errorMsg.isDisplayed());

    editButtonForm.click();

    // COMPUTER PAGE

    computerName = driver.findElement(By.id("computerName"));
    computerIntroduced = driver.findElement(By.id("computerIntroduced"));
    computerDiscontinued = driver.findElement(By.id("computerDiscontinued"));
    WebElement computerCompany = driver.findElement(By.id("computerCompany"));

    assertEquals("Apple IIC", computerName.getText());
    assertEquals("12/12/2012", computerIntroduced.getText());
    assertEquals("13/12/2012", computerDiscontinued.getText());
    assertEquals("Apple Inc.", computerCompany.getText());

    driver.findElement(By.id("editComputer")).click();

    // EDIT COMPUTER PAGE
    editButtonForm = driver.findElement(By.id("editComputerButton"));

    computerName = driver.findElement(By.id("name"));
    computerIntroduced = driver.findElement(By.id("introduced"));
    computerDiscontinued = driver.findElement(By.id("discontinued"));
    companies = driver.findElements(By.tagName("option"));

    for (WebElement companyOption : companies) {
      if (companyOption.getText().equals("Apple Inc.")) {
        assertTrue(companyOption.isSelected());
      }
    }

    assertTrue(editButtonForm.isEnabled());

    computerIntroduced.sendKeys(Keys.DELETE);
    computerIntroduced.sendKeys(Keys.ARROW_RIGHT);
    computerIntroduced.sendKeys(Keys.DELETE);
    computerIntroduced.sendKeys(Keys.ARROW_RIGHT);
    computerIntroduced.sendKeys(Keys.DELETE);

    assertTrue(editButtonForm.isEnabled());

    editButtonForm.click();

    // COMPUTER PAGE
    computerIntroduced = driver.findElement(By.id("computerIntroduced"));

    assertEquals("", computerIntroduced.getText());
  }

}
