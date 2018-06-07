package com.excilys.computerdatabase.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.config.BindingConfig;
import com.excilys.computerdatabase.config.ServiceConfig;
import com.excilys.computerdatabase.model.pojo.Company;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {ServiceConfig.class, BindingConfig.class})
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
  public void testMapRow() {
    ResultSet mockResultSet = mock(ResultSet.class);
    Integer rowNum = 0;

    try {
      when(mockResultSet.getInt("id")).thenReturn(13);
      when(mockResultSet.getString("name")).thenReturn("IBM");
    } catch (SQLException e) {
      fail(e.getMessage());
    }

    Company companyFromResultSet;
    try {
      companyFromResultSet = companyMapper.mapRow(mockResultSet, rowNum);
      assertEquals("IBM", companyFromResultSet.getName());
      assertEquals(13, companyFromResultSet.getId());
    } catch (SQLException e) {
      fail(e.getMessage());
    }
  }

}
