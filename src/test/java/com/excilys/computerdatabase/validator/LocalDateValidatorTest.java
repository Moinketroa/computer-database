package com.excilys.computerdatabase.validator;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excilys.computerdatabase.mapper.LocalDateMapper;

public class LocalDateValidatorTest {

  private String wrongDayDate = "123/11/2012";
  private String wrongMonthDate = "12/113/2012";
  private String wrongYearDate = "12/11/201";
  private String goodDate = "12/11/2012";

  @Test
  public void testIsValidFormat() {
      assertFalse(LocalDateValidator.isValidFormat(wrongDayDate));
      assertFalse(LocalDateValidator.isValidFormat(wrongMonthDate));
      assertFalse(LocalDateValidator.isValidFormat(wrongYearDate));
      assertTrue(LocalDateValidator.isValidFormat(goodDate));
  }

}
