package com.excilys.computerdatabase.validator;

import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.exceptions.badrequest.InvalidIdException;

@Component
public class ComputerValidator {

  public void mustHaveValidId(int computerId) throws InvalidIdException {
    if (computerId == 0) {
      throw new InvalidIdException();
    }
  }

}
