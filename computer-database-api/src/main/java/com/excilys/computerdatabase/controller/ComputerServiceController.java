package com.excilys.computerdatabase.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.exceptions.CDBException;
import com.excilys.computerdatabase.exceptions.badrequest.NegativeNumberException;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.validator.ComputerValidator;
import com.excilys.computerdatabase.validator.IntegerValidator;

@RestController
@RequestMapping("/computers")
public class ComputerServiceController {

  private ComputerService computerService;

  private IntegerValidator integerValidator;
  private ComputerValidator computerValidator;

  @Autowired
  public ComputerServiceController(ComputerService computerService, IntegerValidator integerValidator,
      ComputerValidator computerValidator) {
    this.computerService = computerService;
    this.integerValidator = integerValidator;
    this.computerValidator = computerValidator;
  }

  @GetMapping("/{computerId}")
  public ComputerDto getComputer(@PathVariable(required = true) Integer computerId) throws CDBException {

    integerValidator.mustBePositive(computerId, "Computer Id");
    computerValidator.mustHaveValidId(computerId);
    
    Computer computer = computerService.getById(computerId);
    
    if (computer != null) {
      return new ComputerDto(computer);
    } else {
      return null;
    }
  }

}
