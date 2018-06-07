package com.excilys.computerdatabase.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(ServiceConfig.class)
@ComponentScan(basePackages = { "com.excilys.computerdatabase.mapper" })
public class BindingConfig {}