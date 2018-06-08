package com.excilys.computerdatabase.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.model.pojo.QComputer;
import com.excilys.computerdatabase.page.Page;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

/**
 * The class helps accessing Computer entries of the database.
 *
 * @author jmdebicki
 *
 */
@Repository
public class ComputerDao extends AbstractDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);

  private static final QComputer MODEL = QComputer.computer;

  @Autowired
  public ComputerDao(SessionFactory sessionFactory) {
    super(sessionFactory);
  }

  /**
   * Adds a new computer to the database.
   *
   * @param computer
   *          The computer to be added to the database
   * @return The id of the added computer
   */
  public int add(Computer computer) {

    try (Session session = sessionFactory.openSession()) {
      session.save(computer);
      return computer.getId();
    } catch (Exception e) {
      LOGGER.error("Cannot persist computer", e);
      throw e;
    }
  }

  /**
   * Updates a computer in the database.
   *
   * @param computer
   *          The computer to be updated in the database with its fields changed
   */
  public void update(Computer computer) {
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      long result = queryFactoryFromSession(session).update(MODEL).where(MODEL.id.eq(computer.getId()))
          .set(MODEL.name, computer.getName())
          .set(MODEL.introduced, computer.getIntroduced())
          .set(MODEL.discontinued, computer.getDiscontinued())
          .set(MODEL.company, computer.getCompany()).execute();

      decideForRollbackOrCommit((result == 1), transaction);
    } catch (Exception e) {
      LOGGER.error("Cannot update computer", e);
    }
  }

  /**
   * Deletes a computer in the database.
   *
   * @param id
   *          The id of the computer to be deleted
   */
  public void delete(int id) {
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      long result = queryFactoryFromSession(session).delete(MODEL).where(MODEL.id.eq(id)).execute();

      decideForRollbackOrCommit((result == 1), transaction);
    } catch (Exception e) {
      LOGGER.error("Cannot delete selected computer", e);
      throw e;
    }
  }

  /**
   * Deletes several computer in one call, if one deletion go wrong then all
   * wanted deletions won't occur.
   *
   * @param ids
   *          one or more id of computers wanted to be deleted
   */
  @Transactional(rollbackFor = Throwable.class)
  public void deleteSeveral(List<Integer> ids) {
    try (Session session = sessionFactory.openSession()) {
      Transaction transaction = session.beginTransaction();
      long result = queryFactoryFromSession(session).delete(MODEL).where(MODEL.id.in(ids)).execute();

      decideForRollbackOrCommit((result == ids.size()), transaction);
    } catch (Exception e) {
      LOGGER.error("Cannot delete selected computers", e);
      throw e;
    }
  }

  /**
   * Fetches one computer from the database by searching by the computer id.
   *
   * @param computerId
   *          The id of the wanted computer
   * @return The found computer or null if no computer were found
   */
  public Computer fetchOne(int computerId) {
    return createQueryFactory().selectFrom(MODEL).where(MODEL.id.eq(computerId)).fetchOne();
  }

  /**
   * Fetches a given number (or fewer) of computers from the database under the
   * form of a {@link Page}. The computers are ordered by the wanted property and
   * by the wanted mode
   *
   * @param orderBy
   *          The wanted "order by" property
   * @param mode
   *          The wanted ordering mode, ascending or descending
   * @param offset
   *          the index of the first entity wanted in the page
   * @param numberOfElementsPerPage
   *          maximum number of elements in the wanted page
   * @return A {@link Page} of the found computers
   */
  public Page<Computer> fetchAll(OrderByComputer orderBy, OrderByMode mode, int offset, int numberOfElementsPerPage) {
    List<Computer> computers = new ArrayList<>();
    int totalNumberOfElements = 0;

    OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(mode.getOrderMode(), orderBy.getColumn());
    computers = createQueryFactory().selectFrom(MODEL).orderBy(orderSpecifier).offset(offset)
        .limit(numberOfElementsPerPage).fetch();

    totalNumberOfElements = (int) createQueryFactory().selectFrom(MODEL).fetchCount();

    return new Page<>(computers, offset, numberOfElementsPerPage, totalNumberOfElements);
  }

  /**
   * Fetches a given number (or fewer) of computers, corresponding to the wanted
   * keyword, from the database under the form of a {@link Page}. The computers
   * are ordered by the wanted property and by the wanted mode
   *
   * @param keyword
   *          The wanted keyword for the search
   * @param orderBy
   *          The wanted "order by" property
   * @param mode
   *          The wanted ordering mode, ascending or descending
   * @param offset
   *          the index of the first entity wanted in the page
   * @param numberOfElementsPerPage
   *          maximum number of elements in the wanted page
   * @return A {@link Page} of the found computers
   */
  public Page<Computer> search(String keyword, OrderByComputer orderBy, OrderByMode mode, int offset,
      int numberOfElementsPerPage) {
    List<Computer> computers = new ArrayList<>();
    int totalNumberOfElements = 0;

    OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(mode.getOrderMode(), orderBy.getColumn());
    BooleanExpression computerNameContains = MODEL.name.contains(keyword);
    BooleanExpression companyNameContains = MODEL.company.name.contains(keyword);
    computers = createQueryFactory().selectFrom(MODEL).where(computerNameContains.or(companyNameContains))
        .orderBy(orderSpecifier).offset(offset).limit(numberOfElementsPerPage).fetch();

    totalNumberOfElements = (int) createQueryFactory().selectFrom(MODEL)
        .where(computerNameContains.or(companyNameContains)).fetchCount();

    return new Page<>(computers, offset, numberOfElementsPerPage, totalNumberOfElements);
  }
}
