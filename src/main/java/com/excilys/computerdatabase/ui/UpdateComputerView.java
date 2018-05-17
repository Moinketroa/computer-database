package com.excilys.computerdatabase.ui;

import java.time.LocalDate;

import com.excilys.computerdatabase.exceptions.DiscontinuationPriorToIntroductionExpection;
import com.excilys.computerdatabase.mapper.LocalDateMapper;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.validator.LocalDateValidator;

/**
 * The class directs the user in order to update a computer.
 *
 * @author debicki
 *
 */
public class UpdateComputerView extends AbstractView {

  private int computerId;
  private String name;
  private LocalDate introduction;
  private LocalDate discontinuation;
  private Integer companyId;

  private Computer computer;

  private LocalDateMapper localDateMapper;

  private ComputerService computerService = (ComputerService) context.getBean("computerService");

  /**
   * Constructor that sets the view's viewer and the id of the computer to be
   * updated.
   *
   * @param viewer
   *          the viewer that display the current view
   * @param id
   *          the id of the computer to be updated
   */
  public UpdateComputerView(Viewer viewer, int id) {
    super(viewer);

    computerId = id;
    localDateMapper = new LocalDateMapper();
  }

  @Override
  public void display() {
    System.out.println("\nUpdating the computer #" + computerId + "\n");

    computer = computerService.getById(computerId);

    if (computer == null) {
      System.out.println("Computer #" + computerId + " not found");

      System.out.println();

      System.out.println("Press Enter to return to main menu...");
      scanner.nextLine();

      viewer.setView(new MenuView(viewer));
    } else {
      name = computer.getName();
      introduction = computer.getIntroduced();
      discontinuation = computer.getDiscontinued();
      companyId = computer.getCompany().getId();

      readName();
      readIntroductionDate();
      readDiscontinuationDate();
      readCompanyId();

      try {
        computerService.update(computer);
        viewer.setView(new ComputerDetailsView(viewer, computer.getId()));
      } catch (DiscontinuationPriorToIntroductionExpection e) {
        viewer.setView(new ErrorView(viewer, e));
      }
    }
  }

  /**
   * Read the new name of the computer. If nothing was entered, the computer keeps
   * its previous name.
   */
  private void readName() {
    System.out.println("Please enter the name of the computer");
    System.out.println("Default : " + name);

    String nameTemp = scanner.nextLine().trim();
    if (!nameTemp.equals("")) {
      name = nameTemp;
    }
  }

  /**
   * Read the new introduction date of the computer. If nothing was entered, the
   * computer will have null as its introduction date.
   */
  private void readIntroductionDate() {
    System.out.println("Please enter the introduction date (DD/MM/YYYY format !)");
    if (introduction != null) {
      System.out.println("Default : null\tCurrent : " + localDateMapper.toFormattedString(introduction));
    } else {
      System.out.println("Default : null\tCurrent : null");
    }

    String introductionString = scanner.nextLine().trim();

    while (true) {
      if (introductionString.equals("")) {
        introduction = null;
        return;
      } else if (!LocalDateValidator.isValidFormat(introductionString)) {
        System.out.println("Please enter a valid date format");
        introductionString = scanner.nextLine().trim();
      } else {
        introduction = localDateMapper.fromString(introductionString);
        return;
      }
    }
  }

  /**
   * Read the new discontinuation date of the computer. If nothing was entered,
   * the computer will have null as its discontinuation date.
   */
  private void readDiscontinuationDate() {
    System.out.println("Please enter the discontinuation date (DD/MM/YYYY format !)");
    if (discontinuation != null) {
      System.out.println("Default : null\tCurrent : " + localDateMapper.toFormattedString(discontinuation));
    } else {
      System.out.println("Default : null\tCurrent : null");
    }

    String discontinuationString = scanner.nextLine().trim();

    while (true) {
      if (discontinuationString.equals("") || LocalDateValidator.isValidFormat(discontinuationString)) {
        if (discontinuationString.equals("")) {
          discontinuation = null;
          return;
        }

        discontinuation = localDateMapper.fromString(discontinuationString);

        if (introduction != null) {
          if (discontinuation.isAfter(introduction)) {
            return;
          } else {
            System.out.println("Please enter a date greater than the introduction date");
            discontinuationString = scanner.nextLine().trim();
          }
        } else {
          return;
        }
      } else {
        System.out.println("Please enter a valid date format");
        discontinuationString = scanner.nextLine().trim();
      }
    }
  }

  /**
   * Read the new company id of the computer. If nothing was entered, the computer
   * will have null as its company reference.
   */
  private void readCompanyId() {
    System.out.println("Please enter the company ID");
    System.out.println("Default : null\tCurrent : " + companyId);

    String companyIdString = scanner.nextLine().trim();

    while (true) {
      if (!companyIdString.equals("")) {
        try {
          companyId = Integer.parseInt(companyIdString);
          return;
        } catch (Exception e) {
          System.out.println("Please enter a valid number");
          companyIdString = scanner.nextLine().trim();
        }
      } else {
        companyId = null;
        return;
      }
    }
  }
}
