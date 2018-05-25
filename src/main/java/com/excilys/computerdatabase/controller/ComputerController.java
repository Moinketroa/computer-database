package com.excilys.computerdatabase.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.computerdatabase.dao.OrderByComputer;
import com.excilys.computerdatabase.dao.OrderByMode;
import com.excilys.computerdatabase.dto.ComputerDto;
import com.excilys.computerdatabase.exceptions.BadRequestException;
import com.excilys.computerdatabase.mapper.OrderByComputerMapper;
import com.excilys.computerdatabase.mapper.OrderByModeMapper;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;
import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.validator.ComputerValidator;
import com.excilys.computerdatabase.validator.IntegerValidator;

@Controller
public class ComputerController extends AbstractController {

  @Autowired
  private ComputerService computerService;

  @Autowired
  private OrderByComputerMapper orderByComputerMapper;
  @Autowired
  private OrderByModeMapper orderByModeMapper;

  @Autowired
  private IntegerValidator integerValidator;
  @Autowired
  private ComputerValidator computerValidator;

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerController.class);

  @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
  public ModelAndView showDashboard(@RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "entitiesPerPage", defaultValue = "10") int entitiesPerPage,
      @RequestParam(value = "keyword", defaultValue = "") String keyword,
      @RequestParam(value = "order", defaultValue = "id") String orderParameter,
      @RequestParam(value = "mode", defaultValue = "asc") String modeParameter,
      @RequestParam Map<String, Object> allParams) {
    ModelAndView modelAndView = new ModelAndView();

    try {
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
      modelAndView.setViewName(View.DASHBOARD.toString());
    } catch (BadRequestException e) {
      LOGGER.error("Bad Request", e);
      handleError(modelAndView, View.BAD_REQUEST, e.getMessage());
    } catch (Throwable t) {
      LOGGER.error("Internal Server Error", t);
      handleError(modelAndView, View.INTERNAL_SERVER_ERROR, "Something went wrong : " + t.getMessage());
    }

    return modelAndView;
  }

  @RequestMapping(value = "/computer", method = RequestMethod.GET)
  public ModelAndView displayComputer(@RequestParam(value = "computerId", defaultValue = "0") int computerId,
      @RequestParam Map<String, Object> allParams) {
    ModelAndView modelAndView = new ModelAndView();

    try {
      integerValidator.mustBePositive(computerId, "Computer Id");
      computerValidator.mustHaveValidId(computerId);

      Computer computer = computerService.getById(computerId);

      if (computer != null) {
        modelAndView.addObject("computer", new ComputerDto(computer));
      }

      modelAndView.addAllObjects(allParams);
      modelAndView.setViewName(View.COMPUTER.toString());
    } catch (BadRequestException e) {
      LOGGER.error("Bad Request", e);
      handleError(modelAndView, View.BAD_REQUEST, e.getMessage());
    } catch (Throwable t) {
      LOGGER.error("Internal Server Error", t);
      handleError(modelAndView, View.INTERNAL_SERVER_ERROR, "Something went wrong : " + t.getMessage());
    }

    return modelAndView;
  }

  @RequestMapping(value = "/deleteComputer", method = RequestMethod.POST)
  public ModelAndView deleteOneComputer(@RequestParam(value = "computerId", defaultValue = "0") int computerId,
      @RequestParam Map<String, Object> allParams) {
    ModelAndView modelAndView = new ModelAndView();

    try {
      integerValidator.mustBePositive(computerId, "Computer Id");
      computerValidator.mustHaveValidId(computerId);

      computerService.delete(computerId);

      modelAndView.addAllObjects(allParams);
      modelAndView.addObject("msg", "Computer #" + computerId + " deleted !");
      modelAndView.setViewName(View.NO_CONTENT.toString());
    } catch (BadRequestException e) {
      LOGGER.error("Bad Request", e);
      handleError(modelAndView, View.BAD_REQUEST, e.getMessage());
    } catch (Throwable t) {
      LOGGER.error("Internal Server Error", t);
      handleError(modelAndView, View.INTERNAL_SERVER_ERROR, "Something went wrong : " + t.getMessage());
    }

    return modelAndView;
  }
}
