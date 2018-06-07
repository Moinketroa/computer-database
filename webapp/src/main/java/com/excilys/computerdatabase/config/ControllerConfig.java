package com.excilys.computerdatabase.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {CoreConfig.class, BindingConfig.class})
@ComponentScan(basePackages = { "com.excilys.computerdatabase.controller" })
public class ControllerConfig {}
