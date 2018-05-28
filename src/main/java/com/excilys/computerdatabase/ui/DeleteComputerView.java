package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.exceptions.notfound.ComputerNotFoundException;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * The class directs the user in order to delete a computer.
 *
 * @author debicki
 *
 */
public class DeleteComputerView extends AbstractView {

  private Computer computer;
  private int computerId;

  private ComputerService computerService = (ComputerService) context.getBean("computerService");

  /**
   * Constructor that sets the view's viewer and the id of the computer to be
   * deleted.
   *
   * @param viewer
   *          the viewer that display the current view
   * @param id
   *          the id of the computer to be deleted
   */
  public DeleteComputerView(Viewer viewer, int id) {
    super(viewer);

    computerId = id;
  }

  @Override
  public void display() {
    System.out.println("\nDeleting the computer #" + computerId + "\n");

    try {
      computer = computerService.getById(computerId);

      System.out.println(
          "Are you sure you want to delete the computer #" + computerId + " named \"" + computer.getName() + "\"");
      System.out.println("(y/n)?");

      readResponse();
    } catch (ComputerNotFoundException e) {
      System.out.println("Computer #" + computerId + " not found");
    }

    System.out.println();

    System.out.println("Press Enter to return to main menu...");
    scanner.nextLine();

    viewer.setView(new MenuView(viewer));
  }

  /**
   * Asks the user if he's certain to delete the specified computer. If yes, the
   * computer is deleted. If no, the user returns to the main menu.
   */
  private void readResponse() {
    while (true) {
      String response = scanner.nextLine();

      if (response.matches("[yn]")) {
        if (response.equals("y")) {
          computerService.delete(computerId);
          System.out.println("Computer #" + computerId + " deleted !");
          return;
        } else {
          System.out.println("Deleting canceled !");
          return;
        }
      } else {
        System.out.println("Please enter \"y\" (to confirm deleting the computer) or \"n\" (to cancel)");
      }
    }
  }
}
