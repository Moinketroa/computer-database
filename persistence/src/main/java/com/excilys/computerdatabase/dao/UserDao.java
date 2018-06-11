package com.excilys.computerdatabase.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.computerdatabase.model.pojo.QUser;
import com.excilys.computerdatabase.model.pojo.User;

@Repository
public class UserDao extends AbstractDao {

  private final static QUser MODEL = QUser.user;

  @Autowired
  public UserDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Fetches one user from the database by searching by the username.
   *
   * @param username
   *          The username of the wanted user
   * @return The found user
   */
  public User fetchOneByUsername(String username) {
    User user = createQueryFactory().selectFrom(MODEL).where(MODEL.username.eq(username)).fetchOne();
    return user;
  }

}
