package com.excilys.computerdatabase.dao;

import com.excilys.computerdatabase.model.pojo.QComputer;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.ComparableExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;

public enum OrderByComputer {

  ID("computer.id", QComputer.computer.id), NAME("computer.name", QComputer.computer.name), INTRODUCED(
      "computer.introduced", QComputer.computer.introduced), DISCONTINUED("computer.discontinued",
          QComputer.computer.discontinued), COMPANY("company.name", QComputer.computer.company.name);

  private String property;
  private ComparableExpressionBase<?> column;

  OrderByComputer(String property, ComparableExpressionBase<?> column) {
    this.property = property;
    this.column = column;
  }

  public ComparableExpressionBase<?> getColumn() {
    return column;
  }

  @Override
  public String toString() {
    return property;
  }

}
