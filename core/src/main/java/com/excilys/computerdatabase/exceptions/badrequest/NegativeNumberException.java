package com.excilys.computerdatabase.exceptions.badrequest;

public class NegativeNumberException extends BadRequestException {

  public NegativeNumberException(String paramName) {
    super(paramName + " is negative");
  }

}
