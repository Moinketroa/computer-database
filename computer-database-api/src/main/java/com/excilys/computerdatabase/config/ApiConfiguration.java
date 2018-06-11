package com.excilys.computerdatabase.config;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@Import(value = {CoreConfig.class, BindingConfig.class})
@ComponentScan(basePackages = { "com.excilys.computerdatabase.controller" })
public class ApiConfiguration implements WebMvcConfigurer {
  
  @Override
  public void configureMessageConverters(
    List<HttpMessageConverter<?>> converters) {
      converters.add(new MappingJackson2HttpMessageConverter());
  }
}