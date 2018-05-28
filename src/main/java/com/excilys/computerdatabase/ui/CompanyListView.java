package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.exceptions.badrequest.WrongPageParameterException;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.service.CompanyService;

/**
 * The class let the user browse the company collection via some pages.
 *
 * @author debicki
 *
 */
public class CompanyListView extends AbstractListView<Company> {

  private CompanyService companyService = (CompanyService) context.getBean("companyService");

  /**
   * Constructor that sets the view's viewer and fetches the first page of the
   * Company collection.
   *
   * @param viewer
   *          the viewer that display the current view
   */
  public CompanyListView(Viewer viewer) {
    super(viewer);
  }

  @Override
  public void display() {
    System.out.println("\nComplete list of all the companies\n");

    try {
      page = companyService.getAll(0, ENTITIES_PER_PAGE);

      if (page.isEmpty()) {
        System.out.println("There is currently no company");
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
    String format = "|%1$-7s|%2$-25s|\n";

    System.out.println("-----------------------------------");
    System.out.format(format, "ID", "NAME");
    System.out.println("-----------------------------------");

    for (Company company : page.getElements()) {
      String companyName = company.getName();

      if (companyName.length() >= 25) {
        companyName = companyName.substring(0, 25);
      }

      System.out.format(format, company.getId(), companyName);
    }

    System.out.println("-----------------------------------");
    System.out.println();
  }

  @Override
  protected void previousPage() {
    try {
      page = companyService.getAll(page.getPreviousPageOffset(), ENTITIES_PER_PAGE);
    } catch (WrongPageParameterException e) {
      viewer.setView(new ErrorView(viewer, e));
    }
  }

  @Override
  protected void nextPage() {
    try {
      page = companyService.getAll(page.getNextPageOffset(), ENTITIES_PER_PAGE);
    } catch (WrongPageParameterException e) {
      viewer.setView(new ErrorView(viewer, e));
    }
  }

}
