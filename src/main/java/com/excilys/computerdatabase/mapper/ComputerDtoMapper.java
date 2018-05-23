package com.excilys.computerdatabase.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;

@Component
public class ComputerDtoMapper {

  public List<ComputerDto> listFromPage(Page<Computer> page) {
    List<ComputerDto> computers = new ArrayList<>();

    for (Computer computer : page.result()) {
      computers.add(new ComputerDto(computer));
    }
    
    return computers;
  }

}
