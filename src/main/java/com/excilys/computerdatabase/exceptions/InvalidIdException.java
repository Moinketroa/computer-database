package com.excilys.computerdatabase.exceptions;

public class InvalidIdException extends BadRequestException {

  public InvalidIdException() {
    super("Invalid ID");
  }

}
