package com.excilys.computerdatabase.mapper;

import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.dao.OrderByMode;

@Component
public class OrderByModeMapper {

  public OrderByMode fromString(String mode) {
    if (mode == null) {
      return OrderByMode.ASCENDING;
    }

    switch (mode) {
    case "asc":
      return OrderByMode.ASCENDING;
    case "desc":
      return OrderByMode.DESCENDING;
    default:
      return OrderByMode.ASCENDING;
    }
  }

}
