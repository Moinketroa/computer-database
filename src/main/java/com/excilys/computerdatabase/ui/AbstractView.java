package com.excilys.computerdatabase.ui;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The abstract class is to be extended in order to be compatible with a Viewer.
 *
 * @author debicki
 *
 */
public abstract class AbstractView {

  private static final Logger LOGGER = LoggerFactory.getLogger(AbstractView.class);

  protected static Scanner scanner = new Scanner(System.in);
  protected Viewer viewer;

  /**
   * Constructor that sets the view's viewer.
   *
   * @param viewer
   *          the viewer that display the current view
   */
  public AbstractView(Viewer viewer) {
    this.viewer = viewer;
  }

  /**
   * Displays useful informations associated with the view's role.
   */
  public abstract void display();

  /**
   * Closes the resources associated with the views.
   */
  public static void closeView() {
    LOGGER.warn("Closing the resources for the views");
    scanner.close();
  }

  /**
   * "Cleans" the console by printing 50 empty lines.
   */
  public static void cleanConsole() {
    for (int i = 0; i < 50; i++) {
      System.out.println();
    }
  }
}
