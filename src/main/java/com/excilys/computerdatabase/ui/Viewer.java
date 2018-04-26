package com.excilys.computerdatabase.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The class helps changing and displaying the views.
 *
 * @author debicki
 *
 */
public class Viewer {

  private static final Logger LOGGER = LoggerFactory.getLogger(Viewer.class);

  private AbstractView view;

  /**
   * Cleans the console and displays the new view.
   *
   * @param view
   *          the new view to be displayed
   */
  public void setView(AbstractView view) {
    LOGGER.info("Setting a new view for the Viewer");
    AbstractView.cleanConsole();
    this.view = view;
    this.view.display();
  }

  /**
   * Closes the resources linked to the viewer and the views.
   */
  public void close() {
    LOGGER.info("Closing the Viewer");
    AbstractView.closeView();
  }

  /**
   * Starts the ComputerDatabase CLI.
   *
   * @param args
   *          no arguments are taken into account
   */
  public static void main(String[] args) {
    Viewer viewer = new Viewer();
    viewer.setView(new WelcomeView(viewer));
  }

}
