package com.excilys.computerdatabase.model.pojo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.excilys.computerdatabase.model.pojo.type.AuthorityType;

@Entity
@Table(name = "authorities")
public class Authorities {

  @Id
  @Column(name = "authority")
  @Enumerated(EnumType.STRING)
  private AuthorityType authority;

  @ManyToOne
  @JoinColumn(name = "username")
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