package com.excilys.computerdatabase.dto;

import com.excilys.computerdatabase.model.pojo.Company;

public class CompanyDto {

  private CompanyBasicView company;

  /**
   * Turns a company to a CompanyDto.
   *
   * @param company
   *          the company to be transformed
   */
  public CompanyDto(Company company) {
    this.company = new CompanyBasicView(company);
  }

  public String getName() {
    return company.getName();
  }

  public int getId() {
    return company.getId();
  }
}
