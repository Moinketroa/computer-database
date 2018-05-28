package com.excilys.computerdatabase.exceptions.badrequest;

/**
 * The exception is used to inform that the discontinuation date of a computer
 * is prior to its introduction date.
 *
 * @author jmdebicki
 *
 */
public class DiscontinuationPriorToIntroductionExpection extends BadRequestException {

  /**
   * Constructor which initialize the exception with the following message : "The
   * discontinuation date of the computer is prior to its introduction date.".
   */
  public DiscontinuationPriorToIntroductionExpection() {
    super("The discontinuation date of the computer is prior to its introduction date.");
  }

}
