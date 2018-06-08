package com.excilys.computerdatabase.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.QCompany;
import com.excilys.computerdatabase.model.pojo.QComputer;
import com.excilys.computerdatabase.page.Page;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

/**
 * The class helps accessing Company entries of the database.
 *
 * @author jmdebicki
 *
 */
@Repository
public class CompanyDao extends AbstractDao {

  private static final QCompany MODEL = QCompany.company;
  
  @Autowired
  private CompanyDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Fetches a given number (or fewer) of companies from the database under the
   * form of a {@link Page}.
   *
   * @param offset
   *          the index of the first entity wanted in the page
   * @param numberOfElementsPerPage
   *          maximum number of elements in the wanted page
   * @return A {@link Page} of the found companies
   */
  public Page<Company> fetchAll(int offset, int numberOfElementsPerPage) {
    List<Company> companies = new ArrayList<>();
    int totalNumberOfElements = 0;

    companies = createQueryFactory().selectFrom(MODEL).offset(offset).limit(numberOfElementsPerPage).fetch();
    totalNumberOfElements = (int) createQueryFactory().selectFrom(MODEL).fetchCount();

    return new Page<>(companies, offset, numberOfElementsPerPage, totalNumberOfElements);
  }

  /**
   * Fetches one company from the database by searching by the company id.
   *
   * @param id
   *          The id of the wanted company
   * @return The found company
   */
  public Company fetchOne(int id) {
    return createQueryFactory().selectFrom(MODEL).where(MODEL.id.eq(id)).fetchOne();
  }

  /**
   * Deletes a company after deleting all the computers referencing the wanted
   * company. If one deletion go wrong, all wanted deletions won't occur.
   *
   * @param id
   *          The id of the company to be deleted
   */
  @Transactional(rollbackFor = Throwable.class)
  public void delete(int id) {
    QComputer qcomputer = QComputer.computer;
  
    createQueryFactory().delete(qcomputer).where(qcomputer.company.id.eq(id)).execute();
    createQueryFactory().delete(MODEL).where(MODEL.id.eq(id)).execute();
  }

}
