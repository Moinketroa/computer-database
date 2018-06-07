package com.excilys.computerdatabase.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(PersistenceConfig.class)
@ComponentScan(basePackages = { "com.excilys.computerdatabase.service" })
public class ServiceConfig {}
