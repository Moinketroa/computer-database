package com.excilys.computerdatabase.controller;

import org.springframework.web.servlet.ModelAndView;

public class AbstractController {

  public void handleError(ModelAndView modelAndView, View view, String message) {
    modelAndView.setViewName(view.toString());
    modelAndView.addObject("error", message);
  }

}
