package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.exceptions.badrequest.WrongPageParameterException;
import com.excilys.computerdatabase.mapper.LocalDateMapper;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.ComputerService;

/**
 * The class let the user browse the computer collection via some pages.
 *
 * @author debicki
 *
 */
public class ComputerListView extends AbstractListView<Computer> {

  private ComputerService computerService = (ComputerService) context.getBean("computerService");

  private LocalDateMapper localDateMapper = (LocalDateMapper) context.getBean("localDateMapper");

  /**
   * Constructor that sets the view's viewer and fetches the first page of the
   * Computer collection.
   *
   * @param viewer
   *          the viewer that display the current view
   */
  public ComputerListView(Viewer viewer) {
    super(viewer);
  }

  @Override
  public void display() {
    System.out.println("\nComplete list of all the computers\n");

    try {
      page = computerService.getAll(0, ENTITIES_PER_PAGE);

      if (page.isEmpty()) {
        System.out.println("There is currently no computer");
      } else {
        displayPage();
        readResponse();
      }
    } catch (WrongPageParameterException e) {
      viewer.setView(new ErrorView(viewer, e));
    }
  }

  @Override
  protected void displayPage() {
    System.out.println();
    String format = "|%1$-7s|%2$-25s|%3$-10s|%4$-12s|%5$-10s|\n";

    System.out.println("----------------------------------------------------------------------");
    System.out.format(format, "ID", "NAME", "INTRODUCED", "DISCONTINUED", "COMPANY ID");
    System.out.println("----------------------------------------------------------------------");

    for (Computer computer : page.getElements()) {
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

    }

    System.out.println("----------------------------------------------------------------------");
    System.out.println();
  }

  @Override
  protected void previousPage() {
    try {
      page = computerService.getAll(page.getPreviousPageOffset(), ENTITIES_PER_PAGE);
    } catch (WrongPageParameterException e) {
      viewer.setView(new ErrorView(viewer, e));
    }
  }

  @Override
  protected void nextPage() {
    try {
      page = computerService.getAll(page.getNextPageOffset(), ENTITIES_PER_PAGE);
    } catch (WrongPageParameterException e) {
      viewer.setView(new ErrorView(viewer, e));
    }
  }

}