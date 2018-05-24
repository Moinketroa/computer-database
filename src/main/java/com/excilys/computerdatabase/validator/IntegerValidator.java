package com.excilys.computerdatabase.validator;

import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.exceptions.NegativeNumberException;

@Component
public class IntegerValidator {

  public void mustBePositive(Integer integer, String paramName) throws NegativeNumberException {
    if (integer < 0) {
      throw new NegativeNumberException(paramName);
    }
  }

}
