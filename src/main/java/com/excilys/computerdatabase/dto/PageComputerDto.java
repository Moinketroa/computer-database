package com.excilys.computerdatabase.dto;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;

public class PageComputerDto {

  private int totalNumberOfComputers;
  private boolean isPreviousPageAvailable;
  private boolean isNextPageAvailable;
  private int previousPageOffset, nextPageOffset;
  private List<ComputerDto> computers;
  
  public PageComputerDto(Page<Computer> page) {
    totalNumberOfComputers = page.getTotalNumberOfElements();
    isPreviousPageAvailable = page.isPreviousPageAvailable();
    isNextPageAvailable = page.isNextPageAvailable();
    previousPageOffset = page.getPreviousPageOffset();
    nextPageOffset = page.getNextPageOffset();
    
    computers = new ArrayList<>();
    
    for (Computer computer : page.result()) {
      computers.add(new ComputerDto(computer));
    }
  }

  public int getTotalNumberOfComputers() {
    return totalNumberOfComputers;
  }

  public boolean getIsPreviousPageAvailable() {
    return isPreviousPageAvailable;
  }

  public boolean getIsNextPageAvailable() {
    return isNextPageAvailable;
  }

  public int getPreviousPageOffset() {
    return previousPageOffset;
  }

  public int getNextPageOffset() {
    return nextPageOffset;
  }

  public List<ComputerDto> getComputers() {
    return computers;
  }

}
