package com.excilys.computerdatabase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.excilys.computerdatabase.exceptions.CDBException;
import com.excilys.computerdatabase.exceptions.badrequest.BadRequestException;

@ControllerAdvice
public class ExceptionHandlerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);
  
  @ExceptionHandler(CDBException.class)
  public ModelAndView handleCDBException(CDBException e) {
    LOGGER.error("CDB Exception", e);
    return handleError(e.getAssociatedView(), e.getMessage());
  }

  @ExceptionHandler({MethodArgumentTypeMismatchException.class})
  public ModelAndView handleBadRequest(Exception e) {
    LOGGER.error("Bad Request", e);
    return handleError(View.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler({NoHandlerFoundException.class})
  public ModelAndView handleNotFound(Exception e) {
    LOGGER.error("Not Found", e);
    return handleError(View.NOT_FOUND, e.toString());
  }

  @ExceptionHandler(Throwable.class)
  public ModelAndView handleThrowable(Throwable t) {
    LOGGER.error("Internal Server Error", t);
    return handleError(View.INTERNAL_SERVER_ERROR, "Something went wrong : " + t.getMessage());
  }
  
  public ModelAndView handleError(View view, String message) {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.setViewName(view.toString());
    modelAndView.addObject("error", message);
    return modelAndView;
  }

}
