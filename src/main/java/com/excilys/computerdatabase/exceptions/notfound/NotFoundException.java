package com.excilys.computerdatabase.exceptions.notfound;

import com.excilys.computerdatabase.controller.View;
import com.excilys.computerdatabase.exceptions.CDBException;

public class NotFoundException extends CDBException {

  public NotFoundException(String errorMessage) {
    super(errorMessage, View.NOT_FOUND);
  }

}
