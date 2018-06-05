package com.excilys.computerdatabase.validator;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.computerdatabase.config.ApplicationConfig;
import com.excilys.computerdatabase.mapper.LocalDateMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ApplicationConfig.class)
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
