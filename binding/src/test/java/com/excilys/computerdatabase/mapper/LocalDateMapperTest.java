package com.excilys.computerdatabase.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.config.ApplicationConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
public class LocalDateMapperTest {

  @Autowired
  private LocalDateMapper localDateMapper;

  private String goodDate = "12/11/2012";

  private LocalDate twelveOctober2012 = LocalDate.of(2012, 11, 12);

  @Test
  public void testFromString() {
    LocalDate localDateFromGoodDate = localDateMapper.fromString(goodDate);

    assertEquals(12, localDateFromGoodDate.getDayOfMonth());
    assertEquals(11, localDateFromGoodDate.getMonthValue());
    assertEquals(2012, localDateFromGoodDate.getYear());
  }

  @Test
  public void testToFormattedString() {
    String stringDateFromTwelveOctober2012 = localDateMapper.toFormattedString(twelveOctober2012);

    assertEquals("12/11/2012", stringDateFromTwelveOctober2012);
  }

}
