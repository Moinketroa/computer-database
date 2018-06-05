package com.excilys.computerdatabase.ui;

/**
 * The class displays a welcome message.
 *
 * @author debicki
 *
 */
public class WelcomeView extends AbstractView {

  /**
   * Constructor that sets the view's viewer.
   *
   * @param viewer
   *          the viewer that display the current view
   */
  public WelcomeView(Viewer viewer) {
    super(viewer);
  }

  @Override
  public void display() {
    System.out.println("Welcome to Computer Database \n");

    viewer.setView(new MenuView(viewer));
  }

}
