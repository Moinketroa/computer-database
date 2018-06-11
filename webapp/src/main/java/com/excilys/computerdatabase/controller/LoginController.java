package com.excilys.computerdatabase.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

  @RequestMapping(value = "/login", method = RequestMethod.GET)
  public ModelAndView showLogin() {
    ModelAndView modelAndView = new ModelAndView(View.LOGIN.toString());
    
    return modelAndView;
  }
  
  @RequestMapping(value = "/logout", method = RequestMethod.GET)
  public ModelAndView showLogout() {
    ModelAndView modelAndView = new ModelAndView(View.LOGOUT.toString());
    
    return modelAndView;
  }

}
