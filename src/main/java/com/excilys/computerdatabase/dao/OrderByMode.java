package com.excilys.computerdatabase.dao;

import com.querydsl.core.types.Order;

public enum OrderByMode {
  ASCENDING ("ASC", Order.ASC),
  DESCENDING ("DESC", Order.DESC);
  
  private String mode;
  private Order order;
  
  OrderByMode(String mode, Order order) {
    this.mode = mode;
    this.order = order;
  }
  
  public Order getOrder() {
    return order;
  }
  
  @Override
  public String toString() {
    return mode;
  }
}
