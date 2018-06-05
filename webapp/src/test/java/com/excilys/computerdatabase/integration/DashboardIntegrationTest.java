package com.excilys.computerdatabase.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.dao.HSQLDatabase;

public class DashboardIntegrationTest extends AbstractIntegrationTest {

  @Test
  public void checkElementsTest() {
    driver.navigate().to("http://localhost:8086/computer-database/dashboard");

    assertEquals("Computer Database", driver.getTitle());

    WebElement totalNumberOfComputer = driver.findElement(By.id("homeTitle"));
    assertEquals("574 Computers found", totalNumberOfComputer.getText());

    List<WebElement> computerRows = driver.findElements(By.id("computerRow"));
    assertEquals(10, computerRows.size());

    boolean isNextPageLinkPresent = driver.findElements(By.id("nextPageLink")).size() == 1;
    boolean isPreviousPageLinkPresent = driver.findElements(By.id("previousPageLink")).size() == 1;

    assertTrue(isNextPageLinkPresent);
    assertFalse(isPreviousPageLinkPresent);

    WebElement nextPageLink = driver.findElement(By.id("nextPageLink"));
    nextPageLink.click();

    computerRows = driver.findElements(By.id("computerRow"));
    assertEquals(10, computerRows.size());

    isNextPageLinkPresent = driver.findElements(By.id("nextPageLink")).size() == 1;
    isPreviousPageLinkPresent = driver.findElements(By.id("previousPageLink")).size() == 1;

    assertTrue(isNextPageLinkPresent);
    assertTrue(isPreviousPageLinkPresent);

    WebElement tenEntitiesPerPageLink = driver.findElement(By.id("10entitiesPerPage"));
    tenEntitiesPerPageLink.click();

    computerRows = driver.findElements(By.id("computerRow"));
    assertEquals(10, computerRows.size());

    WebElement fiftyEntitiesPerPageLink = driver.findElement(By.id("50entitiesPerPage"));
    fiftyEntitiesPerPageLink.click();

    computerRows = driver.findElements(By.id("computerRow"));
    assertEquals(50, computerRows.size());

    WebElement hundredEntitiesPerPageLink = driver.findElement(By.id("100entitiesPerPage"));
    hundredEntitiesPerPageLink.click();

    computerRows = driver.findElements(By.id("computerRow"));
    assertEquals(100, computerRows.size());

    driver.navigate().to("http://localhost:8086/computer-database/dashboard?&offset=570&entitiesPerPage=10");

    isNextPageLinkPresent = driver.findElements(By.id("nextPageLink")).size() == 1;
    isPreviousPageLinkPresent = driver.findElements(By.id("previousPageLink")).size() == 1;

    assertFalse(isNextPageLinkPresent);
    assertTrue(isPreviousPageLinkPresent);

    computerRows = driver.findElements(By.id("computerRow"));
    assertEquals(4, computerRows.size());

    tenEntitiesPerPageLink = driver.findElement(By.id("10entitiesPerPage"));
    tenEntitiesPerPageLink.click();

    computerRows = driver.findElements(By.id("computerRow"));
    assertEquals(4, computerRows.size());

    fiftyEntitiesPerPageLink = driver.findElement(By.id("50entitiesPerPage"));
    fiftyEntitiesPerPageLink.click();

    computerRows = driver.findElements(By.id("computerRow"));
    assertEquals(4, computerRows.size());

    hundredEntitiesPerPageLink = driver.findElement(By.id("100entitiesPerPage"));
    hundredEntitiesPerPageLink.click();

    computerRows = driver.findElements(By.id("computerRow"));
    assertEquals(4, computerRows.size());

    driver.navigate().to("http://localhost:8086/computer-database/dashboard");

    computerRows = driver.findElements(By.id("computerRow"));
    WebElement macBookPro = computerRows.get(5);

    WebElement macBookProName = macBookPro.findElement(By.id("computerName"));
    assertEquals("MacBook Pro", macBookProName.getText());
    WebElement macBookProLink = macBookProName.findElement(By.xpath("./a"));
    assertEquals("http://localhost:8086/computer-database/computer?&computerId=6", macBookProLink.getAttribute("href"));

    WebElement macBookProIntroduced = macBookPro.findElement(By.id("computerIntroduced"));
    assertEquals("10/01/2006", macBookProIntroduced.getText());

    WebElement macBookProDiscontinued = macBookPro.findElement(By.id("computerDiscontinued"));
    assertEquals("", macBookProDiscontinued.getText());

    WebElement macBookProCompany = macBookPro.findElement(By.id("computerCompany"));
    assertEquals("Apple Inc.", macBookProCompany.getText());
  }

}
