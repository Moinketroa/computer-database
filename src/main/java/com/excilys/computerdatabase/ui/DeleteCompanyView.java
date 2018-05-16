package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.service.CompanyService;

public class DeleteCompanyView extends AbstractView {

  private Company company;
  private int companyId;
  private CompanyService companyService;

  /**
   * Constructor that sets the view's viewer and the id of the company to be
   * deleted.
   *
   * @param viewer
   *          the viewer that display the current view
   * @param id
   *          the id of the company to be deleted
   */
  public DeleteCompanyView(Viewer viewer, int id) {
    super(viewer);

    companyId = id;
    companyService = CompanyService.INSTANCE;
    company = companyService.getById(companyId);
  }
  
  @Override
  public void display() {
    System.out.println("\nDeleting the company #" + companyId + "\n");

    if (company == null) {
      System.out.println("Company #" + companyId + " not found");
    } else {
      System.out.println(
          "Are you sure you want to delete the company #" + companyId + " named \"" + company.getName() + "\" and all computers retaled to this company");
      System.out.println("(y/n)?");

      readResponse();
    }

    System.out.println();

    System.out.println("Press Enter to return to main menu...");
    scanner.nextLine();

    viewer.setView(new MenuView(viewer));
  }

  /**
   * Asks the user if he's certain to delete the specified company. If yes, the
   * company is deleted and the computers linked to the company are also deleted. If no, the user returns to the main menu.
   */
  private void readResponse() {
    while (true) {
      String response = scanner.nextLine();

      if (response.matches("[yn]")) {
        if (response.equals("y")) {
          companyService.delete(companyId);
          System.out.println("Company #" + companyId + " and all computers related the the company are deleted !");
          return;
        } else {
          System.out.println("Deleting canceled !");
          return;
        }
      } else {
        System.out.println("Please enter \"y\" (to confirm deleting the company) or \"n\" (to cancel)");
      }
    }
  }

}
