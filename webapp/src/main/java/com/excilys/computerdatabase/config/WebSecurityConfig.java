package com.excilys.computerdatabase.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.excilys.computerdatabase.model.pojo.type.AuthorityType;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private UserDetailsService userDetailsService;
  
  @Autowired
  public WebSecurityConfig(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }
  
  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
  
  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
  }
  
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    String[] authorities = Arrays.stream(AuthorityType.values()).map(a -> a.name()).toArray(String[]::new);
    http.authorizeRequests().anyRequest().hasAnyRole(authorities)
    .and()
    .authorizeRequests().antMatchers("/login**").permitAll()
    .and()
    .formLogin().loginPage("/login").loginProcessingUrl("/loginAction").permitAll()
    .and()
    .logout().logoutSuccessUrl("/login").permitAll()
    .and()
    .csrf().disable();
  }

}
