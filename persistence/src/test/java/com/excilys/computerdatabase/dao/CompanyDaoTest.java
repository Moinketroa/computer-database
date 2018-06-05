package com.excilys.computerdatabase.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.config.ApplicationConfig;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class CompanyDaoTest {

  @Autowired
  private HSQLDatabase hsqldb;
  @Autowired
  private CompanyDao companyDao;
  @Autowired
  private ComputerDao computerDao;

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

  /**
   * Tests various cases of fetching all companies.
   */
  @Test
  public void testFetchAll() {
    Page<Company> companyPageNormal = companyDao.fetchAll(0, 12);
    Page<Company> companyPageEnd = companyDao.fetchAll(40, 12);
  
    List<Company> companyListNormal = companyPageNormal.getElements();
    List<Company> companyListEnd = companyPageEnd.getElements();

    assertEquals(42, companyPageNormal.getTotalNumberOfElements());
    assertEquals(42, companyPageEnd.getTotalNumberOfElements());

    Company firstOfListNormal = getFirstElement(companyListNormal);
    Company lastOfListNormal = getLastElement(companyListNormal);
    Company firstOfListEnd = getFirstElement(companyListEnd);
    Company lastOfListEnd = getLastElement(companyListEnd);

    assertEquals(1, firstOfListNormal.getId());
    assertEquals(12, lastOfListNormal.getId());

    assertEquals(42, firstOfListEnd.getId());
    assertEquals(43, lastOfListEnd.getId());
  }

  /**
   * Tests various cases of fetching a specific company.
   */
  @Test
  public void testFetchOne() {
    Company companyNumber13 = companyDao.fetchOne(13);
    Company companyNumber123 = companyDao.fetchOne(123);
    Company companyNumberMinus1 = companyDao.fetchOne(-1);

    assertEquals(13, companyNumber13.getId());
    assertEquals("IBM", companyNumber13.getName());

    assertNull(companyNumber123);
    assertNull(companyNumberMinus1);
  }

  @Test
  public void testDelete() {
    Computer HTCSnap = computerDao.fetchOne(446);
    Computer HTCDream = computerDao.fetchOne(530);

    Company HTC = companyDao.fetchOne(41);

    assertNotNull(HTCSnap);
    assertNotNull(HTCDream);

    assertNotNull(HTC);

    companyDao.delete(41);

    Computer HTCSnapOtherReference = computerDao.fetchOne(446);
    Computer HTCDreamOtherReference = computerDao.fetchOne(530);

    Company HTCOtherReference = companyDao.fetchOne(41);

    assertNull(HTCSnapOtherReference);
    assertNull(HTCDreamOtherReference);

    assertNull(HTCOtherReference);
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
