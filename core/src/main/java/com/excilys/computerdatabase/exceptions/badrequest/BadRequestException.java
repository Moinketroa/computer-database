package com.excilys.computerdatabase.exceptions.badrequest;

import com.excilys.computerdatabase.controller.View;
import com.excilys.computerdatabase.exceptions.CDBException;

public class BadRequestException extends CDBException {

  public BadRequestException(String errMessage) {
    super(errMessage, View.BAD_REQUEST);
  }

}
