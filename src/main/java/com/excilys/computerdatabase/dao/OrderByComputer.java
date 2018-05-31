package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.model.pojo.QComputer;
import com.querydsl.core.types.Path;

public enum OrderByComputer {

  ID("computer.id", QComputer.computer.id), NAME("computer.name", QComputer.computer.name), INTRODUCED(
      "computer.introduced", QComputer.computer.introduced), DISCONTINUED("computer.discontinued",
          QComputer.computer.discontinued), COMPANY("company.name", QComputer.computer.company);

  private String property;
  private Path<?> column;

  OrderByComputer(String property, Path<?> column) {
    this.property = property;
    this.column = column;
  }

  public Path<?> getColumn() {
    return column;
  }

  @Override
  public String toString() {
    return property;
  }

}
