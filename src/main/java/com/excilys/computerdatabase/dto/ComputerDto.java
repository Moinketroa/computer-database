package com.excilys.computerdatabase.dto;

import java.time.LocalDate;

import com.excilys.computerdatabase.model.pojo.Computer;

public class ComputerDto {

  private int id;
  private String name;
  private LocalDate introduced;
  private LocalDate discontinued;
  private CompanyDto company;

  /**
   * Turns a computer to a ComputerDto.
   * @param computer
   *          the computer to be transformed
   */
  public ComputerDto(Computer computer) {
    this.id = computer.getId();
    this.name = computer.getName();
    this.introduced = computer.getIntroduced();
    this.discontinued = computer.getDiscontinued();

    this.company = null;

    if (computer.getCompany() != null) {
      this.company = new CompanyDto(computer.getCompany());
    }
  }

  public CompanyDto getCompany() {
    return company;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public LocalDate getIntroduced() {
    return introduced;
  }

  public LocalDate getDiscontinued() {
    return discontinued;
  }
}
