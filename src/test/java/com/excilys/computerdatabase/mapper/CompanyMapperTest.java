package com.excilys.computerdatabase.mapper;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.config.ApplicationConfig;
import com.excilys.computerdatabase.model.pojo.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class CompanyMapperTest {
  
  @Autowired
  CompanyMapper companyMapper;

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
      companyFromResultSet = companyMapper.fromResultSet(mockResultSet);
      assertEquals("IBM", companyFromResultSet.getName());
      assertEquals(13, companyFromResultSet.getId());
    } catch (SQLException e) {
      fail(e.getMessage());
    }
  }

}
