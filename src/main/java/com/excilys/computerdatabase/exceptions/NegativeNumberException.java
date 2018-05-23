package com.excilys.computerdatabase.exceptions;

public class NegativeNumberException extends BadRequestException {

  public NegativeNumberException(String paramName) {
    super(paramName + " is negative");
  }

}
