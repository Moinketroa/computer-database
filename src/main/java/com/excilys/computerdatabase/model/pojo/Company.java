package com.excilys.computerdatabase.model.pojo;

/**
 * The class represents companies like those in the database.
 *
 * @author jmdebicki
 *
 */
public class Company {

  private int id;
  private String name;

  /**
   * A constructor which initialize a new company object with a name which is
   * mandatory.
   *
   * @param name
   *          the needed name of the company
   */
  public Company(String name) {
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
   * Sets the company name. If the parameter is null the company keeps its previous name.
   *
   * @param name the new name to give to the company
   */
  public void setName(String name) {
    if (name != null) {
      this.name = name;
    }
  }

  @Override
  public String toString() {
    return "Company : " + id + '\t' + name;
  }
}
