package com.excilys.computerdatabase.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class LocalDateMapperTest {

  private String goodDate = "12/11/2012";

  private LocalDate twelveOctober2012 = LocalDate.of(2012, 11, 12);

  @Test
  public void testFromString() {
    LocalDate localDateFromGoodDate = LocalDateMapper.fromString(goodDate);

    assertEquals(12, localDateFromGoodDate.getDayOfMonth());
    assertEquals(11, localDateFromGoodDate.getMonthValue());
    assertEquals(2012, localDateFromGoodDate.getYear());
  }

  @Test
  public void testToFormattedString() {
    String stringDateFromTwelveOctober2012 = LocalDateMapper.toFormattedString(twelveOctober2012);

    assertEquals("12/11/2012", stringDateFromTwelveOctober2012);
  }

}
