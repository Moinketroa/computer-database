package com.excilys.computerdatabase.dto;

import java.time.LocalDate;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.validator.annotation.DateIntegrity;

@DateIntegrity(introduction = "introduced", discontinuation = "discontinued")
public class ComputerDto {

  private int id;

  @NotBlank
  private String name;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate introduced;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate discontinued;
  private CompanyDto company;

  /**
   * Turns a computer to a ComputerDto.
   *
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

  public ComputerDto() {
    this.company = new CompanyDto();
  }

  public CompanyDto getCompany() {
    return company;
  }

  public void setCompany(CompanyDto company) {
    this.company = company;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public LocalDate getIntroduced() {
    return introduced;
  }

  public void setIntroduced(LocalDate introduced) {
    this.introduced = introduced;
  }

  public LocalDate getDiscontinued() {
    return discontinued;
  }

  public void setDiscontinued(LocalDate discontinued) {
    this.discontinued = discontinued;
  }

}
