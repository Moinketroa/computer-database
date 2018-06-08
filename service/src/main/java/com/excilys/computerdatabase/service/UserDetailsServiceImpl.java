package com.excilys.computerdatabase.service;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.UserDao;
import com.excilys.computerdatabase.model.pojo.User;

@Service
public class UserDetailsServiceImpl implements org.springframework.security.core.userdetails.UserDetailsService{

  private UserDao userDao;

  @Autowired
  private UserDetailsServiceImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userDao.fetchOneByUsername(username);
    UserBuilder userBuilder = null;
    
    if (user != null) {
      userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
      userBuilder.disabled(!user.isEnabled());
      userBuilder.password(user.getPassword());
      String[] authorities =  user.getAuthorities()
                                  .stream()
                                  .map(a -> a.getAuthority())
                                  .toArray(String[]::new);
                                  
      userBuilder.authorities(authorities);
    } else {
      throw new UsernameNotFoundException("Username not found");
    }
    
    return userBuilder.build();
  }
  
  

}
