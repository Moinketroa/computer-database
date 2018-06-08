package com.excilys.computerdatabase.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.computerdatabase.model.pojo.type.AuthorityType;

@Entity
@Table(name = "AUTHORITIES")
public class Authorities {

  @Enumerated(EnumType.STRING)
  @Column(name = "AUTHORITY")
  private AuthorityType authority;

  @ManyToOne
  @JoinColumn(name = "USERNAME")
  private User user;

  public Authorities() {
  
  }

  public AuthorityType getAuthority() {
    return authority;
  }

  public void setAuthority(AuthorityType authority) {
    this.authority = authority;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}