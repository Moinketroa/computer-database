package com.excilys.computerdatabase.exceptions;

/**
 * 
 * The class is to be extended by exceptions attended be only used by the application
 * 
 * @author jmdebicki
 *
 */
public abstract class CDBException extends Exception {

	/**
	 * Basic constructor which initialize the exception with an error message
	 * 
	 * @param errorMessage the message to be displayed
	 */
	public CDBException(String errorMessage) {
		super(errorMessage);
	}
	
}
