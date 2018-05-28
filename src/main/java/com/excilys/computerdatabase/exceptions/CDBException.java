package com.excilys.computerdatabase.exceptions;

import com.excilys.computerdatabase.controller.View;

/**
 * The exception is to be extended by exceptions attended to be only used by the
 * application.
 *
 * @author jmdebicki
 *
 */
public abstract class CDBException extends Exception {

  private View associatedView;

  /**
   * Basic constructor which initialize the exception with an error message.
   *
   * @param errorMessage
   *          the message to be displayed
   */
  public CDBException(String errorMessage, View associatedView) {
    super(errorMessage);
    this.associatedView = associatedView;
  }

  public View getAssociatedView() {
    return associatedView;
  }

}
