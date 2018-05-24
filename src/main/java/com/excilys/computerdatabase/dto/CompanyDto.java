package com.excilys.computerdatabase.dto;

import com.excilys.computerdatabase.model.pojo.Company;

public class CompanyDto {

  private int id;
  private String name;

  /**
   * Turns a company to a CompanyDto.
   *
   * @param company
   *          the company to be transformed
   */
  public CompanyDto(Company company) {
    this.id = company.getId();
    this.name = company.getName();
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }
}
