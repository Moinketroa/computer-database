package com.excilys.computerdatabase.dto;

import com.excilys.computerdatabase.model.pojo.Company;

public class CompanyBasicView {

  private int id;
  private String name;

  /**
   * Creates a basic view of a company for the CompanyDto.
   * @param company the company to be copied
   */
  public CompanyBasicView(Company company) {
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
