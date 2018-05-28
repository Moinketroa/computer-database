package com.excilys.computerdatabase.exceptions;

public class WrongFormatOfSelectionException extends BadRequestException {

  public WrongFormatOfSelectionException() {
    super("Wrong format for the selection parameter. Please don't try to hack our site. It's hella rude.");
  }
  
}
