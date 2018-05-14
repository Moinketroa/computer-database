package com.excilys.computerdatabase.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

public class ComputerDaoTest {

  private HSQLDatabase hsqldb = HSQLDatabase.INSTANCE;
  private ComputerDao computerDao = ComputerDao.INSTANCE;

  /**
   * Initializes the database before each test.
   */
  @Before
  public void setUp() {
    hsqldb.init();
  }

  /**
   * Drops all the tables of the database after each test.
   */
  @After
  public void tearDown() {
    hsqldb.destroy();
  }

  @Test
  public void testAdd() {
    String basicName = "TestComputer";
    LocalDate basicIntroductionLocalDate = LocalDate.of(2012, 11, 12);
    LocalDate basicDiscontinuationLocalDate = LocalDate.of(2013, 9, 10);

    Company company = new Company("Apple Inc.");
    company.setId(1);

    Computer newComputer = new Computer(basicName);
    newComputer.setIntroduced(basicIntroductionLocalDate);
    newComputer.setDiscontinued(basicDiscontinuationLocalDate);
    newComputer.setCompany(company);

    int computerId = computerDao.add(newComputer);

    assertEquals(575, computerId);

    Computer newComputerOtherReference = computerDao.fetchOne(computerId);

    assertEquals(basicName, newComputerOtherReference.getName());
    assertEquals(basicIntroductionLocalDate, newComputerOtherReference.getIntroduced());
    assertEquals(basicDiscontinuationLocalDate, newComputerOtherReference.getDiscontinued());
    assertEquals(1, newComputerOtherReference.getCompany().getId());
    assertEquals("Apple Inc.", newComputerOtherReference.getCompany().getName());
  }

  @Test
  public void testUpdate() {
    String basicName = "TestComputerUpdate";
    LocalDate basicIntroductionLocalDate = LocalDate.of(2012, 11, 12);
    LocalDate basicDiscontinuationLocalDate = LocalDate.of(2013, 9, 10);

    Company company = new Company("Apple Inc.");
    company.setId(1);

    Computer updateComputerWithCompany = new Computer(basicName);
    updateComputerWithCompany.setId(570);
    updateComputerWithCompany.setIntroduced(basicIntroductionLocalDate);
    updateComputerWithCompany.setDiscontinued(basicDiscontinuationLocalDate);
    updateComputerWithCompany.setCompany(company);

    Computer updateComputerWithoutCompany = new Computer(basicName);
    updateComputerWithoutCompany.setId(568);
    updateComputerWithoutCompany.setIntroduced(basicIntroductionLocalDate);
    updateComputerWithoutCompany.setDiscontinued(basicDiscontinuationLocalDate);

    computerDao.update(updateComputerWithCompany);
    computerDao.update(updateComputerWithoutCompany);

    Computer computerWithCompanyOtherReference = computerDao.fetchOne(570);
    Computer computerWithoutCompanyOtherReference = computerDao.fetchOne(568);

    assertEquals(basicName, computerWithCompanyOtherReference.getName());
    assertEquals(basicIntroductionLocalDate, computerWithCompanyOtherReference.getIntroduced());
    assertEquals(basicDiscontinuationLocalDate, computerWithCompanyOtherReference.getDiscontinued());
    assertEquals(1, computerWithCompanyOtherReference.getCompany().getId());
    assertEquals("Apple Inc.", computerWithCompanyOtherReference.getCompany().getName());

    assertEquals(basicName, computerWithoutCompanyOtherReference.getName());
    assertEquals(basicIntroductionLocalDate, computerWithoutCompanyOtherReference.getIntroduced());
    assertEquals(basicDiscontinuationLocalDate, computerWithoutCompanyOtherReference.getDiscontinued());
    assertNull(computerWithoutCompanyOtherReference.getCompany());
  }

  @Test
  public void testDelete() {
    Computer computerNumber23 = computerDao.fetchOne(23);

    assertNotNull(computerNumber23);

    computerDao.delete(23);

    Computer computerNumber23OtherReference = computerDao.fetchOne(23);

    assertNull(computerNumber23OtherReference);
  }

  @Test
  public void testDeleteSeveralFail() {
    Integer[] idsToBeDeletedWithOneNonExistent = { 1, 2, 3, 4, 5678 };

    for (int i = 0; i < 4; i++) {
      assertNotNull(computerDao.fetchOne(idsToBeDeletedWithOneNonExistent[i]));
    }
    assertNull(computerDao.fetchOne(idsToBeDeletedWithOneNonExistent[4]));

    computerDao.deleteSeveral(idsToBeDeletedWithOneNonExistent);

    for (int i = 0; i < 4; i++) {
      assertNotNull("Computer #" + idsToBeDeletedWithOneNonExistent[i] + " is null",
          computerDao.fetchOne(idsToBeDeletedWithOneNonExistent[i]));
    }
    assertNull(computerDao.fetchOne(idsToBeDeletedWithOneNonExistent[4]));
  }

  @Test
  public void testDeleteSeveralOk() {
    Integer[] idsToBeDeleted = { 1, 2, 3, 4, 5 };

    for (int id : idsToBeDeleted) {
      assertNotNull(computerDao.fetchOne(id));
    }

    computerDao.deleteSeveral(idsToBeDeleted);

    for (int id : idsToBeDeleted) {
      assertNull(computerDao.fetchOne(id));
    }
  }

  @Test
  public void testSearch() {
    List<Computer> appleComputers = computerDao.search("apple", 0, 10).result();

    assertEquals(10, appleComputers.size());
    
    assertEquals("MacBook Pro 15.4 inch", getFirstElement(appleComputers).getName());
    assertEquals("Apple II", getLastElement(appleComputers).getName());
  }

  @Test
  public void testFetchOne() {
    Computer computerNumber12 = computerDao.fetchOne(12);
    Computer computerNumber1234 = computerDao.fetchOne(1234);
    Computer computerNumberMinus1 = computerDao.fetchOne(-1);

    assertEquals(12, computerNumber12.getId());
    assertEquals("Apple III", computerNumber12.getName());
    assertEquals(LocalDate.of(1980, 5, 1), computerNumber12.getIntroduced());
    assertEquals(LocalDate.of(1984, 4, 1), computerNumber12.getDiscontinued());
    assertEquals(1, computerNumber12.getCompany().getId());
    assertEquals("Apple Inc.", computerNumber12.getCompany().getName());

    assertNull(computerNumber1234);
    assertNull(computerNumberMinus1);
  }

  @Test
  public void testFetchAll() {
    List<Computer> computerListNormal = computerDao.fetchAll(0, 12).result();
    List<Computer> computerListEnd = computerDao.fetchAll(570, 12).result();

    Computer firstOfListNormal = getFirstElement(computerListNormal);
    Computer lastOfListNormal = getLastElement(computerListNormal);
    Computer firstOfListEnd = getFirstElement(computerListEnd);
    Computer lastOfListEnd = getLastElement(computerListEnd);

    assertEquals(1, firstOfListNormal.getId());
    assertEquals(12, lastOfListNormal.getId());

    assertEquals(571, firstOfListEnd.getId());
    assertEquals(574, lastOfListEnd.getId());
  }

  /**
   * Return the first element of an iterable.
   * 
   * @param <T>
   *          the type of the iterable set
   * @param elements
   *          iterable set of elements
   * @return the first element of the iterable set
   */
  private static <T> T getFirstElement(final Iterable<T> elements) {
    if (elements == null) {
      return null;
    }

    return elements.iterator().next();
  }

  /**
   * Return the last element of an iterable.
   * 
   * @param <T>
   *          the type of the iterable set
   * @param elements
   *          iterable set of elements
   * @return the last element of the iterable set
   */
  private static <T> T getLastElement(final Iterable<T> elements) {
    final Iterator<T> itr = elements.iterator();
    T lastElement = itr.next();

    while (itr.hasNext()) {
      lastElement = itr.next();
    }

    return lastElement;
  }
}
