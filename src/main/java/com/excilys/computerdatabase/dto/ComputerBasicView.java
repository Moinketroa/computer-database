package com.excilys.computerdatabase.dto;

import java.time.LocalDate;

import com.excilys.computerdatabase.model.pojo.Computer;

public class ComputerBasicView {

  private int id;
  private String name;
  private LocalDate introduced;
  private LocalDate discontinued;

  /**
   * Creates a basic view of a computer for the ComputerDto.
   * @param computer the computer to be copied
   */
  public ComputerBasicView(Computer computer) {
    this.id = computer.getId();
    this.name = computer.getName();
    this.introduced = computer.getIntroduced();
    this.discontinued = computer.getDiscontinued();
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
