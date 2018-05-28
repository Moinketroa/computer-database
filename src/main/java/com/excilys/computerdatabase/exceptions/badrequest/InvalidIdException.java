package com.excilys.computerdatabase.exceptions.badrequest;

public class InvalidIdException extends BadRequestException {

  public InvalidIdException() {
    super("Invalid ID");
  }

}
