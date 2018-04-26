package com.excilys.computerdatabase.exceptions;

/**
 * The exception informs that a wanted company is not available.
 *
 * @author debicki
 *
 */
public class CompanyNotFoundException extends CDBException {

  /**
   * Basic constructor which initialize the exception with the following error message :
   * "Company #companyId not found. Unable to add the company into the computer.".
   *
   * @param companyId the id that will be displayed in the error message
   */
  public CompanyNotFoundException(int companyId) {
    super("Company #" + companyId + " not found. Unable to add the company into the computer.");
  }

}
