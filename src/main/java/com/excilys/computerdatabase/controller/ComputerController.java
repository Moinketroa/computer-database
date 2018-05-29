package com.excilys.computerdatabase.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.dao.OrderByComputer;
import com.excilys.computerdatabase.dao.OrderByMode;
import com.excilys.computerdatabase.dto.CompanyDto;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.exceptions.CDBException;
import com.excilys.computerdatabase.exceptions.badrequest.BadRequestException;
import com.excilys.computerdatabase.exceptions.badrequest.NegativeNumberException;
import com.excilys.computerdatabase.exceptions.badrequest.WrongFormatOfSelectionException;
import com.excilys.computerdatabase.exceptions.badrequest.WrongPageParameterException;
import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.mapper.IntegerMapper;
import com.excilys.computerdatabase.mapper.OrderByComputerMapper;
import com.excilys.computerdatabase.mapper.OrderByModeMapper;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.validator.ComputerValidator;
import com.excilys.computerdatabase.validator.IntegerValidator;
import com.excilys.computerdatabase.validator.SelectionValidator;

@Controller
public class ComputerController {

  @Autowired
  private ComputerService computerService;
  @Autowired
  private CompanyService companyService;

  @Autowired
  private ComputerMapper computerMapper;
  @Autowired
  private OrderByComputerMapper orderByComputerMapper;
  @Autowired
  private OrderByModeMapper orderByModeMapper;
  @Autowired
  private IntegerMapper integerMapper;

  @Autowired
  private IntegerValidator integerValidator;
  @Autowired
  private ComputerValidator computerValidator;
  @Autowired
  private SelectionValidator selectionValidator;

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);

  @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
  public ModelAndView showDashboard(@RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "entitiesPerPage", defaultValue = "10") int entitiesPerPage,
      @RequestParam(value = "keyword", defaultValue = "") String keyword,
      @RequestParam(value = "order", defaultValue = "id") String orderParameter,
      @RequestParam(value = "mode", defaultValue = "asc") String modeParameter,
      @RequestParam Map<String, Object> allParams) throws BadRequestException {
    ModelAndView modelAndView = new ModelAndView(View.DASHBOARD.toString());

    integerValidator.mustBePositive(offset, "Offset");
    integerValidator.mustBePositive(entitiesPerPage, "Number of entities per page");

    OrderByComputer order = orderByComputerMapper.fromString(orderParameter);
    OrderByMode mode = orderByModeMapper.fromString(modeParameter);

    Page<Computer> page = null;

    if (keyword.equals("")) {
      page = computerService.getAll(order, mode, offset, entitiesPerPage);
    } else {
      page = computerService.search(keyword, order, mode, offset, entitiesPerPage);
    }

    modelAndView.addAllObjects(allParams);
    modelAndView.addObject("page", page.convertToAnotherType(computer -> new ComputerDto(computer)));
    modelAndView.addObject("mode", modeParameter.equals("desc") ? "desc" : "asc");

    return modelAndView;
  }

  @RequestMapping(value = "/computer", method = RequestMethod.GET)
  public ModelAndView displayComputer(@RequestParam(value = "computerId", defaultValue = "0") int computerId,
      @RequestParam Map<String, Object> allParams) throws CDBException {
    ModelAndView modelAndView = new ModelAndView(View.COMPUTER.toString());

    integerValidator.mustBePositive(computerId, "Computer Id");
    computerValidator.mustHaveValidId(computerId);

    Computer computer = computerService.getById(computerId);

    if (computer != null) {
      modelAndView.addObject("computer", new ComputerDto(computer));
    }

    modelAndView.addAllObjects(allParams);

    return modelAndView;
  }

  @RequestMapping(value = "/deleteComputer", method = RequestMethod.GET)
  public ModelAndView deleteOneComputer(@RequestParam(value = "computerId", defaultValue = "0") int computerId,
      @RequestParam Map<String, Object> allParams) throws BadRequestException {
    ModelAndView modelAndView = new ModelAndView(View.NO_CONTENT.toString());

    integerValidator.mustBePositive(computerId, "Computer Id");
    computerValidator.mustHaveValidId(computerId);

    computerService.delete(computerId);

    modelAndView.addAllObjects(allParams);
    modelAndView.addObject("msg", "Computer #" + computerId + " deleted !");

    return modelAndView;
  }

  @RequestMapping(value = "/deleteComputer", method = RequestMethod.POST)
  public ModelAndView deleteSeveralComputers(@RequestParam(value = "selection", defaultValue = "") String selection,
      @RequestParam Map<String, Object> allParams) throws BadRequestException {
    ModelAndView modelAndView = new ModelAndView(View.NO_CONTENT.toString());

    selectionValidator.mustBeAValidFormat(selection);
    List<Integer> selectedIdList = integerMapper.listFromSelection(selection);

    computerService.deleteSeveral(selectedIdList);

    modelAndView.addAllObjects(allParams);
    modelAndView.addObject("msg", "Computers deleted !");

    return modelAndView;
  }

  @RequestMapping(value = "/addComputer", method = RequestMethod.GET)
  public ModelAndView displayAddComputer() throws BadRequestException {
    ModelAndView modelAndView = new ModelAndView(View.ADD_COMPUTER.toString());

    modelAndView.addObject("computerDto", new ComputerDto());
    setCompaniesToMAV(modelAndView);

    return modelAndView;
  }

  @RequestMapping(value = "/addComputer", method = RequestMethod.POST)
  public ModelAndView addComputer(@Valid @ModelAttribute("computerDto") ComputerDto computerDto, BindingResult result,
      ModelMap model) throws CDBException {
    ModelAndView modelAndView = new ModelAndView(View.COMPUTER.toString());

    if (result.hasErrors()) {
      ModelAndView returnToAddPage = new ModelAndView();
      setCompaniesToMAV(returnToAddPage);
      return returnToAddPage;
    }
    
    Computer computerToAdd = computerMapper.fromComputerDto(computerDto);
    int computerId = computerService.create(computerToAdd);
    
    modelAndView.addObject("computerId", computerId);
    modelAndView.addObject("computer", new ComputerDto(computerToAdd));

    return modelAndView;
  }

  @RequestMapping(value = "/editComputer", method = RequestMethod.GET)
  public ModelAndView displayEditComputer(@RequestParam(value = "computerId", defaultValue = "0") int computerId,
      @RequestParam Map<String, Object> allParams) throws CDBException {
    ModelAndView modelAndView = new ModelAndView(View.EDIT_COMPUTER.toString());

    integerValidator.mustBePositive(computerId, "Computer Id");
    computerValidator.mustHaveValidId(computerId);

    ComputerDto computerDto = new ComputerDto(computerService.getById(computerId));

    modelAndView.addAllObjects(allParams);
    modelAndView.addObject("computer", computerDto);
    setCompaniesToMAV(modelAndView);

    return modelAndView;
  }

  private void setCompaniesToMAV(ModelAndView modelAndView) throws WrongPageParameterException {
    Page<Company> page = companyService.getAll(0, 100);
    List<CompanyDto> companies = new ArrayList<>();

    for (Company company : page.getElements()) {
      companies.add(new CompanyDto(company));
    }

    modelAndView.addObject("companies", companies);
  }
}
