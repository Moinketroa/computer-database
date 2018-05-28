package com.excilys.computerdatabase.exceptions.notfound;

public class ComputerNotFoundException extends NotFoundException {

  public ComputerNotFoundException(int computerId) {
    super("Computer #" + computerId + " not found");
  }

}
