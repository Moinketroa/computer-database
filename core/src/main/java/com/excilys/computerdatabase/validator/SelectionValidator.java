package com.excilys.computerdatabase.validator;

import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.exceptions.badrequest.WrongFormatOfSelectionException;

@Component
public class SelectionValidator {

  public void mustBeAValidFormat(String selection) throws WrongFormatOfSelectionException {
    if (!selection.matches("[[1-9][0-9]*,]*[1-9][0-9]*")) {
      throw new WrongFormatOfSelectionException();
    }
  }
  
}
