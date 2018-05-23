package com.excilys.computerdatabase.exceptions;

public class NotANumberException extends BadRequestException {

  public NotANumberException(String paramName) {
    super(paramName + " is not numeric.");
  }

}
