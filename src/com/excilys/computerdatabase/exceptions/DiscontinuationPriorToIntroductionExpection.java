package com.excilys.computerdatabase.exceptions;

public class DiscontinuationPriorToIntroductionExpection extends CDBException {

	public DiscontinuationPriorToIntroductionExpection() {
		super("The discontinuation date of the computer is prior to its introduction date");	
	}
	
}
