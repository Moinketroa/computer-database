package com.excilys.computerdatabase.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.excilys.computerdatabase.validator", "com.excilys.computerdatabase.dto" })
public class CoreConfig {}
