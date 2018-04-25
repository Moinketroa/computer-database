package com.excilys.computerdatabase.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Iterator;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.computerdatabase.model.pojo.Company;

public class CompanyDaoTest {

	private HSQLDatabase hsqldb = HSQLDatabase.INSTANCE;
	private CompanyDao companyDao = CompanyDao.INSTANCE;
	
	@Before
	public void setUp() throws Exception {
		hsqldb.init();
	}

	@After
	public void tearDown() throws Exception {
		hsqldb.destroy();
	}

	@Test
	public void testFetchAll() {
		List<Company> companyListNormal = companyDao.fetchAll(0, 12).result();
		List<Company> companyListEnd	= companyDao.fetchAll(40, 12).result();
		
		Company firstOfListNormal = getFirstElement(companyListNormal);
		Company lastOfListNormal  = getLastElement(companyListNormal);
		Company firstOfListEnd	  = getFirstElement(companyListEnd);
		Company lastOfListEnd	  = getLastElement(companyListEnd);
		
		assertEquals(1, firstOfListNormal.getId());
		assertEquals(12, lastOfListNormal.getId());
		
		assertEquals(42, firstOfListEnd.getId());
		assertEquals(43, lastOfListEnd.getId());
	}

	@Test
	public void testFetchOne() {
		Company companyNumber13 = companyDao.fetchOne(13);
		Company companyNumber123 = companyDao.fetchOne(123);
		Company companyNumberMinus1 = companyDao.fetchOne(-1);
		
		assertEquals(13 , companyNumber13.getId());
		assertEquals("IBM", companyNumber13.getName());
		
		assertNull(companyNumber123);
		assertNull(companyNumberMinus1);
	}
	
	private static <T> T getFirstElement(final Iterable<T> elements) {
        if (elements == null) 
            return null;

        return elements.iterator().next();
    }

	private static <T> T getLastElement(final Iterable<T> elements) {
        final Iterator<T> itr = elements.iterator();
        T lastElement = itr.next();

        while(itr.hasNext()) {
            lastElement=itr.next();
        }

        return lastElement;
    }
}
