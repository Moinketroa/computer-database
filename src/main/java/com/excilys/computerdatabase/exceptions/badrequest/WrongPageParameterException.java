package com.excilys.computerdatabase.exceptions.badrequest;

/**
 * The exception inform that the parameters given in order to get a page are
 * incorrect.
 *
 * @author debicki
 *
 */
public class WrongPageParameterException extends BadRequestException {

  /**
   * Constructor which initialize the exception with the following message :
   * "Cannot have a negative page offset or less than 1 entity per page".
   */
  public WrongPageParameterException() {
    super("Cannot have a negative page offset or less than 1 entity per page");
  }

}
