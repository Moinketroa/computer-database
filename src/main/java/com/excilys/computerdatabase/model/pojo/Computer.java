package com.excilys.computerdatabase.model.pojo;

import java.time.LocalDate;

/**
 * The class represents computers like those in the database.
 *
 * @author jmdebicki
 *
 */
public class Computer {

  private int id;
  private String name;
  private LocalDate introduced;
  private LocalDate discontinued;
  private Company company;

  /**
   * A constructor which initialize a new computer object with a name which is
   * mandatory.
   *
   * @param name
   *          the needed name of the computer
   */
  public Computer(String name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  /**
   * Sets the computer name. If the parameter is null the computer keeps its
   * previous name.
   *
   * @param name
   *          the new name to give to the computer
   */
  public void setName(String name) {
    if (name != null) {
      this.name = name;
    }
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

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("Computer : ");
    sb.append(id + "\t");
    sb.append(name + '\t');

    if (introduced != null) {
      sb.append(introduced.toString() + '\t');
    } else {
      sb.append("null\t");
    }

    if (discontinued != null) {
      sb.append(discontinued.toString() + '\t');
    } else {
      sb.append("null\t");
    }

    if (company != null) {
      sb.append(company.toString() + '\t');
    } else {
      sb.append("Company : null\t");
    }

    return sb.toString();
  }

}
