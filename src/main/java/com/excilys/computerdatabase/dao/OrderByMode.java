package com.excilys.computerdatabase.dao;

public enum OrderByMode {
  ASCENDING ("ASC"),
  DESCENDING ("DESC");
  
  private String mode;
  
  OrderByMode(String mode) {
    this.mode = mode;
  }
  
  @Override
  public String toString() {
    return mode;
  }
}
