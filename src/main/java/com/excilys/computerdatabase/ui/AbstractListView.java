package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.page.Page;

/**
 * The class let the user browse a collection via some pages.
 *
 * @author debicki
 *
 * @param <E> the type of the elements to be displayed
 */
public abstract class AbstractListView<E> extends AbstractView {

  protected static final int ENTITIES_PER_PAGE = 5;

  protected Page<E> page;

  /**
   * Constructor that sets the view's viewer.
   *
   * @param viewer
   *          the viewer that display the current view
   */
  public AbstractListView(Viewer viewer) {
    super(viewer);
  }

  /**
   * Displays the current page of the collection.
   */
  protected abstract void displayPage();

  /**
   * Fetches the previous page of the collection.
   */
  protected abstract void previousPage();

  /**
   * Fetches the next page of the collection.
   */
  protected abstract void nextPage();

  /**
   * Reads the user's response regarding the browsing of the collection via some
   * pages. "n" displays the next page of the collection (if available). "p"
   * displays the previous page of the collection (if available). "q" quits to the
   * main menu.
   */
  protected void readResponse() {
    while (true) {

      System.out.println("What do you want to do ?");
      if (page.isNextPageAvailable()) {
        System.out.println("Enter \"n\" to display next page");
      }
      if (page.isPreviousPageAvailable()) {
        System.out.println("Enter \"p\" to display previous page");
      }
      System.out.println("Enter \"q\" to return to main menu");

      String response = scanner.nextLine();

      if (response.matches("[pnq]")) {
        if (response.equals("p")) {
          if (page.isPreviousPageAvailable()) {
            previousPage();
            displayPage();
          } else {
            System.out.println("Previous page is not available, please enter a valid answer");
          }
        } else if (response.equals("n")) {
          if (page.isNextPageAvailable()) {
            nextPage();
            displayPage();
          } else {
            System.out.println("Next page is not available, please enter a valid answer");
          }
        } else if (response.equals("q")) {
          System.out.println("Returning to main menu");

          viewer.setView(new MenuView(viewer));
        }
      } else {
        System.out.println("Please enter a valid answer");
      }
    }
  }
}
