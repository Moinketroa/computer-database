package com.excilys.computerdatabase.mapper;

import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.dao.OrderByComputer;

@Component
public class OrderByComputerMapper {

  public OrderByComputer fromString(String order) {
    if (order == null) {
      return OrderByComputer.ID;
    }

    switch (order) {
    case "name":
      return OrderByComputer.NAME;
    case "introduced":
      return OrderByComputer.INTRODUCED;
    case "discontinued":
      return OrderByComputer.DISCONTINUED;
    case "company":
      return OrderByComputer.COMPANY;
    default:
      return OrderByComputer.ID;
    }
  }

}
