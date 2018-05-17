package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.mapper.LocalDateMapper;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * The class displays the details of one computer.
 *
 * @author debicki
 *
 */
public class ComputerDetailsView extends AbstractView {

  private int computerId;
  private Computer computer;

  private ComputerService computerService = (ComputerService) context.getBean("computerService");

  private LocalDateMapper localDateMapper;

  /**
   * Constructor that sets the view's viewer.
   *
   * @param viewer
   *          the viewer that display the current view
   * @param id
   *          the id of the computer to be displayed
   */
  public ComputerDetailsView(Viewer viewer, int id) {
    super(viewer);

    computerId = id;
    localDateMapper = new LocalDateMapper();
  }

  @Override
  public void display() {
    System.out.println("\nDetails for computer #" + computerId + "\n");

    computer = computerService.getById(computerId);

    if (computer == null) {
      System.out.println("Computer #" + computerId + " not found");
    } else {
      System.out.println();
      String format = "|%1$-7s|%2$-25s|%3$-10s|%4$-12s|%5$-10s|\n";

      System.out.println("----------------------------------------------------------------------");
      System.out.format(format, "ID", "NAME", "INTRODUCED", "DISCONTINUED", "COMPANY ID");
      System.out.println("----------------------------------------------------------------------");

      String companyId;
      String computerName = computer.getName();

      if (computer.getCompany() != null) {
        companyId = computer.getCompany().getId() + "";
      } else {
        companyId = "null";
      }

      if (computerName.length() >= 25) {
        computerName = computerName.substring(0, 25);
      }

      System.out.format(format, computer.getId(), computerName,
          localDateMapper.toFormattedString(computer.getIntroduced()),
          localDateMapper.toFormattedString(computer.getDiscontinued()), companyId);

      System.out.println("----------------------------------------------------------------------");
      System.out.println();
    }

    System.out.println();

    System.out.println("Press Enter to return to main menu...");
    scanner.nextLine();

    viewer.setView(new MenuView(viewer));
  }

}
