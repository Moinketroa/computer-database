package com.excilys.computerdatabase.ui;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * The class displays the menu of the CLI and make available different options.
 *
 * @author debicki
 *
 */
public class MenuView extends AbstractView {

  private List<MenuOption> options;

  /**
   * Constructor that sets the view's viewer.
   *
   * @param viewer
   *          the viewer that display the current view
   */
  public MenuView(Viewer viewer) {
    super(viewer);

    options = Arrays.asList(MenuOption.values());
    Collections.sort(options, (a, b) -> a.getOptionNumber() - b.getOptionNumber());
  }

  @Override
  public void display() {
    System.out.println("Please select an option : ");

    for (MenuOption option : options) {
      System.out.println("\t" + option);
    }

    try {
      MenuOption choosenOption = MenuOption.valueOf(scanner.nextInt()).get();
      scanner.nextLine();

      switch (choosenOption) {
      case DISPLAY_COMPUTERS:
        viewer.setView(new ComputerListView(viewer));
        break;
      case DISPLAY_COMPANIES:
        viewer.setView(new CompanyListView(viewer));
        break;
      case COMPUTER_DETAILS:
        computerDetails();
        break;
      case ADD_COMPUTER:
        viewer.setView(new AddComputerView(viewer));
        break;
      case UPDATE_COMPUTER:
        updateComputer();
        break;
      case REMOVE_COMPUTER:
        removeComputer();
        break;
      case REMOVE_COMPANY:
        removeCompany();
        break;
      case EXIT:
        viewer.setView(new EndView(viewer));
        break;
      default:
        tryAgain();
        break;
      }
    } catch (NoSuchElementException e) {
      scanner.nextLine();
      tryAgain();
    }
  }

  /**
   * Asks the user to enter a valid computer ID and changes the current view to a
   * {@link ComputerDetailsView} which will display the details of the wanted
   * computer.
   */
  private void computerDetails() {
    int computerId = readComputerId();

    viewer.setView(new ComputerDetailsView(viewer, computerId));
  }

  /**
   * Asks the user to enter a valid computer ID and changes the current view to a
   * {@link UpdateComputerView} which will direct the user in order to update the
   * wanted computer.
   */
  private void updateComputer() {
    int computerId = readComputerId();

    viewer.setView(new UpdateComputerView(viewer, computerId));
  }

  /**
   * Asks the user to enter a valid computer ID and changes the current view to a
   * {@link DeleteComputerView} which will direct the user in order to delete the
   * wanted computer.
   */
  private void removeComputer() {
    int computerId = readComputerId();

    viewer.setView(new DeleteComputerView(viewer, computerId));
  }

  /**
   * Asks the user to enter a valid company ID and changes the current view to a
   * {@link DeleteCompanyView} which will direct the user in order to delete the
   * wanted company.
   */
  private void removeCompany() {
    int companyId = readCompanyId();

    viewer.setView(new DeleteCompanyView(viewer, companyId));
  }

  /**
   * Asks the user to enter a valid computer ID.
   *
   * @return the ID provided by the user
   */
  private int readComputerId() {
    return readId("computer");
  }

  /**
   * Asks the user to enter a valid company ID.
   *
   * @return the ID provided by the user
   */
  private int readCompanyId() {
    return readId("company");
  }

  /**
   * Asks the user to enter a valid ID.
   *
   * @param entity
   *          the entity name to be displayed in the CLI
   *
   * @return the ID provided by the user
   */
  private int readId(String entity) {
    int id;

    while (true) {
      try {
        System.out.println("Please enter the choosen " + entity + "'s ID");

        id = scanner.nextInt();
        scanner.nextLine();
        break;
      } catch (InputMismatchException e) {
        scanner.nextLine();
        System.out.println("Please enter a valid number");
      }
    }

    return id;
  }

  /**
   * Informs the user that he choose an unavailable option and ask him to re-enter
   * an option number.
   */
  private void tryAgain() {
    System.out.println("Please enter a number corresponding to an option\n");
    viewer.setView(this);
  }

}
