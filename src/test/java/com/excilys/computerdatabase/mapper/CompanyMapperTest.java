package com.excilys.computerdatabase.mapper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.computerdatabase.model.pojo.Company;

public class CompanyMapperTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFromResultSet() {
		ResultSet mockResultSet = mock(ResultSet.class);
		
		try {
			when(mockResultSet.getInt("id")).thenReturn(13);
			when(mockResultSet.getString("name")).thenReturn("IBM");
		} catch (SQLException e) {
			fail(e.getMessage());
		}
		
		Company companyFromResultSet;
		try {
			companyFromResultSet = CompanyMapper.fromResultSet(mockResultSet);
			assertEquals("IBM", companyFromResultSet.getName());
			assertEquals(13, companyFromResultSet.getId());
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}

}
