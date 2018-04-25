package com.excilys.computerdatabase.mapper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.computerdatabase.dao.HSQLDatabase;
import com.excilys.computerdatabase.exceptions.CompanyNotFoundException;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.CompanyService;

public class ComputerMapperTest {
	
	@Before
	public void setUp() {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFromResultSet() {
		ResultSet mockResultSetNormal = mock(ResultSet.class);
		ResultSet mockResultSetWithOnlyMandatoryFields = mock(ResultSet.class);
		ResultSet mockResultSetWithNoCompany = mock(ResultSet.class);
		ResultSet mockResultSetWithOnlyCompany = mock(ResultSet.class);
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.set(2012, 11, 12);
		Date basicIntroductionDate = new Date(calendar.getTimeInMillis());
		
		calendar.set(2013, 9, 10);
		Date basicDiscontinuationDate = new Date(calendar.getTimeInMillis());
		
		try {
			when(mockResultSetNormal.getInt("id")).thenReturn(7);
			when(mockResultSetWithOnlyMandatoryFields.getInt("id")).thenReturn(7);
			when(mockResultSetWithNoCompany.getInt("id")).thenReturn(7);
			when(mockResultSetWithOnlyCompany.getInt("id")).thenReturn(7);
			
			when(mockResultSetNormal.getString("name")).thenReturn("Apple IIe");
			when(mockResultSetWithOnlyMandatoryFields.getString("name")).thenReturn("Apple IIe");
			when(mockResultSetWithNoCompany.getString("name")).thenReturn("Apple IIe");
			when(mockResultSetWithOnlyCompany.getString("name")).thenReturn("Apple IIe");
			
			when(mockResultSetNormal.getDate("introduced")).thenReturn(basicIntroductionDate);
			when(mockResultSetWithOnlyMandatoryFields.getDate("introduced")).thenReturn(null);
			when(mockResultSetWithNoCompany.getDate("introduced")).thenReturn(basicIntroductionDate);
			when(mockResultSetWithOnlyCompany.getDate("introduced")).thenReturn(null);
			
			when(mockResultSetNormal.getDate("discontinued")).thenReturn(basicDiscontinuationDate);
			when(mockResultSetWithOnlyMandatoryFields.getDate("discontinued")).thenReturn(null);
			when(mockResultSetWithNoCompany.getDate("discontinued")).thenReturn(basicDiscontinuationDate);
			when(mockResultSetWithOnlyCompany.getDate("discontinued")).thenReturn(null);
			
			when(mockResultSetNormal.getInt("company_id")).thenReturn(1);
			when(mockResultSetWithOnlyMandatoryFields.getInt("company_id")).thenReturn(0);
			when(mockResultSetWithNoCompany.getInt("company_id")).thenReturn(0);
			when(mockResultSetWithOnlyCompany.getInt("company_id")).thenReturn(1);
			
			when(mockResultSetNormal.getString("company_name")).thenReturn("Apple Inc.");
			when(mockResultSetWithOnlyMandatoryFields.getString("company_name")).thenReturn(null);
			when(mockResultSetWithNoCompany.getString("company_name")).thenReturn(null);
			when(mockResultSetWithOnlyCompany.getString("company_name")).thenReturn("Apple Inc.");
		} catch (SQLException e) {
			fail(e.getMessage());
		}
		
		Computer 	computerFromResultSetNormal, 
					computerFromResultSetWithOnlyMandatoryFields, 
					computerFromResultSetWithNoCompany, 
					computerFromResultSetWithOnlyCompany;
		
		try {
			computerFromResultSetNormal = ComputerMapper.fromResultSet(mockResultSetNormal);
			computerFromResultSetWithOnlyMandatoryFields = ComputerMapper.fromResultSet(mockResultSetWithOnlyMandatoryFields);
			computerFromResultSetWithNoCompany = ComputerMapper.fromResultSet(mockResultSetWithNoCompany);
			computerFromResultSetWithOnlyCompany = ComputerMapper.fromResultSet(mockResultSetWithOnlyCompany);
			
			assertEquals(7, computerFromResultSetNormal.getId());
			assertEquals(7, computerFromResultSetWithOnlyMandatoryFields.getId());
			assertEquals(7, computerFromResultSetWithNoCompany.getId());
			assertEquals(7, computerFromResultSetWithOnlyCompany.getId());
			
			assertEquals("Apple IIe", computerFromResultSetNormal.getName());
			assertEquals("Apple IIe", computerFromResultSetWithOnlyMandatoryFields.getName());
			assertEquals("Apple IIe", computerFromResultSetWithNoCompany.getName());
			assertEquals("Apple IIe", computerFromResultSetWithOnlyCompany.getName());
			
			assertEquals(basicIntroductionDate.toLocalDate(), computerFromResultSetNormal.getIntroduced());
			assertNull(computerFromResultSetWithOnlyMandatoryFields.getIntroduced());
			assertEquals(basicIntroductionDate.toLocalDate(), computerFromResultSetWithNoCompany.getIntroduced());
			assertNull(computerFromResultSetWithOnlyCompany.getIntroduced());
			
			assertEquals(basicDiscontinuationDate.toLocalDate(), computerFromResultSetNormal.getDiscontinued());
			assertNull(computerFromResultSetWithOnlyMandatoryFields.getDiscontinued());
			assertEquals(basicDiscontinuationDate.toLocalDate(), computerFromResultSetWithNoCompany.getDiscontinued());
			assertNull(computerFromResultSetWithOnlyCompany.getDiscontinued());

			assertNull(computerFromResultSetWithOnlyMandatoryFields.getCompany());
			assertNull(computerFromResultSetWithNoCompany.getCompany());
			
			assertEquals(1, computerFromResultSetNormal.getCompany().getId());
			assertEquals(1, computerFromResultSetWithOnlyCompany.getCompany().getId());
			
			assertEquals("Apple Inc.", computerFromResultSetNormal.getCompany().getName());
			assertEquals("Apple Inc.", computerFromResultSetWithOnlyCompany.getCompany().getName());
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

	@Test
	public void testFromParameters() {		
		HSQLDatabase.INSTANCE.init();
		
		String basicName = "TestComputer";
		LocalDate basicIntroductionLocalDate = LocalDate.of(2012, 11, 12);
		LocalDate basicDiscontinuationLocalDate = LocalDate.of(2013, 9, 10);
		
		try {
			Computer computerNormal = ComputerMapper.fromParameters(basicName, basicIntroductionLocalDate, basicDiscontinuationLocalDate, 13);
			Computer computerWithOnlyCompany = ComputerMapper.fromParameters(basicName, null, null, 13);
			
			assertEquals(basicName, computerNormal.getName());
			assertEquals(basicName, computerWithOnlyCompany.getName());
			
			assertEquals(basicIntroductionLocalDate, computerNormal.getIntroduced());
			assertNull(computerWithOnlyCompany.getIntroduced());
			
			assertEquals(basicDiscontinuationLocalDate, computerNormal.getDiscontinued());
			assertNull(computerWithOnlyCompany.getDiscontinued());
			
			assertEquals(13, computerNormal.getCompany().getId());
			assertEquals(13, computerWithOnlyCompany.getCompany().getId());
			
			assertEquals("IBM", computerNormal.getCompany().getName());
			assertEquals("IBM", computerWithOnlyCompany.getCompany().getName());
		} catch (CompanyNotFoundException e) {
			fail(e.getMessage());
		}
		
		try {
			Computer computerWithCompanyNumber122 = ComputerMapper.fromParameters(basicName, basicIntroductionLocalDate, basicDiscontinuationLocalDate, 122);
			fail("computerWithCompanyNumber122 created");
		} catch (CompanyNotFoundException e) {
			assertEquals("Company #122 not found. Unable to add the company into the computer.", e.getMessage());
		}
		
		try {
			Computer computerWithOnlyCompanyNumber122 = ComputerMapper.fromParameters(basicName, null, null, 122);
			fail("computerWithOnlyCompanyNumber122 created");
		} catch (CompanyNotFoundException e) {
			assertEquals("Company #122 not found. Unable to add the company into the computer.", e.getMessage());
		}
		
		HSQLDatabase.INSTANCE.destroy();
	}

}