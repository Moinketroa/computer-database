package com.excilys.computerdatabase.ui;

/**
 * The class displays a goodbye message.
 *
 * @author debicki
 *
 */
public class EndView extends AbstractView {

  /**
   * Constructor that sets the view's viewer.
   *
   * @param viewer
   *          the viewer that display the current view
   */
  public EndView(Viewer viewer) {
    super(viewer);
  }

  @Override
  public void display() {
    System.out.println("Good bye ! Have a nice day !");
    viewer.close();
    System.exit(0);
  }

}
