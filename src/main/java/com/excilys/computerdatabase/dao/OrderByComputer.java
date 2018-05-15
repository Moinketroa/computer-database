package com.excilys.computerdatabase.dao;

public enum OrderByComputer {

  ID("computer.id"), 
  NAME("computer.name"), 
  INTRODUCED("computer.introduced"), 
  DISCONTINUED("computer.discontinued"), 
  COMPANY("company.name");

  private String property;

  OrderByComputer(String property) {
    this.property = property;
  }

  @Override
  public String toString() {
    return property;
  }

}
