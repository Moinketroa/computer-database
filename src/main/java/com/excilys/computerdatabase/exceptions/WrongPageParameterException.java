package com.excilys.computerdatabase.exceptions;

public class WrongPageParameterException extends CDBException {

	public WrongPageParameterException() {
		super("Cannot have a negative page offset or less than 1 entity per page");
	}

}
