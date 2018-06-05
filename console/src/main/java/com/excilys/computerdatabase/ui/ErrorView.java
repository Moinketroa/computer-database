package com.excilys.computerdatabase.ui;

/**
 * The class displays an error that happened during the execution of the CLI.
 *
 * @author debicki
 *
 */
public class ErrorView extends AbstractView {

  private Exception e;

  /**
   * Constructor that sets the view's viewer and the exception to be displayed.
   *
   * @param viewer
   *          the viewer that display the current view
   * @param e
   *          the exception to be displayed
   */
  public ErrorView(Viewer viewer, Exception e) {
    super(viewer);

    this.e = e;
  }

  @Override
  public void display() {
    System.out.println("The error below occured : ");
    System.out.println(e.getMessage());

    System.out.println();

    System.out.println("Press Enter to return to main menu...");
    scanner.nextLine();

    viewer.setView(new MenuView(viewer));
  }

}
