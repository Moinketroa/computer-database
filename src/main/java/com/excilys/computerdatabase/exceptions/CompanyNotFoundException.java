package com.excilys.computerdatabase.exceptions;

public class CompanyNotFoundException extends CDBException {

	public CompanyNotFoundException(int companyId) {
		super("Company #" + companyId + " not found. Unable to add the company into the computer.");
	}
	
}
