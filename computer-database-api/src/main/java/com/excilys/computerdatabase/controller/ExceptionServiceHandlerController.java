package com.excilys.computerdatabase.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.excilys.computerdatabase.exceptions.CDBException;

@ControllerAdvice
public class ExceptionServiceHandlerController {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionServiceHandlerController.class);

  @ExceptionHandler(CDBException.class)
  public ResponseEntity<?> handleCDBException(CDBException e) {
    LOGGER.error("CDB Exception", e);
    return new ResponseEntity<>(HttpStatus.BAD_REQUEST); 
  }
}
