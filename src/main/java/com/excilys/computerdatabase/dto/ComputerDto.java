package com.excilys.computerdatabase.dto;

import java.time.LocalDate;

import com.excilys.computerdatabase.model.pojo.Computer;

public class ComputerDto {

  private CompanyBasicView company;
  private ComputerBasicView computer;

  /**
   * Turns a computer to a ComputerDto.
   * @param computer
   *          the computer to be transformed
   */
  public ComputerDto(Computer computer) {
    this.computer = new ComputerBasicView(computer);

    this.company = null;

    if (computer.getCompany() != null) {
      this.company = new CompanyBasicView(computer.getCompany());
    }
  }

  public CompanyBasicView getCompany() {
    return company;
  }

  public int getId() {
    return computer.getId();
  }

  public String getName() {
    return computer.getName();
  }

  public LocalDate getIntroduced() {
    return computer.getIntroduced();
  }

  public LocalDate getDiscontinued() {
    return computer.getDiscontinued();
  }
}
