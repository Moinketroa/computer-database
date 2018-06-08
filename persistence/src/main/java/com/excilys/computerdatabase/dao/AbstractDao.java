package com.excilys.computerdatabase.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.querydsl.jpa.hibernate.HibernateQueryFactory;

public abstract class AbstractDao {

  SessionFactory sessionFactory;

  protected AbstractDao(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
  }
  
  HibernateQueryFactory queryFactoryFromSession(Session session) {
    return new HibernateQueryFactory(session);
  }

  void decideForRollbackOrCommit(boolean condition, Transaction transaction) {
    if (condition) {
      transaction.commit();
    } else {
      transaction.rollback();
    }
  }
  
  HibernateQueryFactory createQueryFactory() {
    Session session = sessionFactory.openSession();
    return new HibernateQueryFactory(session);
  }

}
