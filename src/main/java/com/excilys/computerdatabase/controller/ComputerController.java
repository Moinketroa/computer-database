package com.excilys.computerdatabase.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.computerdatabase.service.ComputerService;

@Controller
public class ComputerController {

  @Autowired
  private ComputerService computerService;

  @RequestMapping(value = "/dashboard", method = RequestMethod.GET)
  public String showDashboard(@RequestParam Map<String, String> params) {
 
    return "dashboard";
  }

}
