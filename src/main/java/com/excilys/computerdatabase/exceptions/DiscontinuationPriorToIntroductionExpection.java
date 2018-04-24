package com.excilys.computerdatabase.exceptions;

/**
 * The class is used to inform that the discontinuation date of a computer is prior to its introduction date
 * 
 * @author jmdebicki
 *
 */
public class DiscontinuationPriorToIntroductionExpection extends CDBException {

	/**
	 * Constructor which initialize the exception with a message describing the problem
	 */
	public DiscontinuationPriorToIntroductionExpection() {
		super("The discontinuation date of the computer is prior to its introduction date");	
	}
	
}
