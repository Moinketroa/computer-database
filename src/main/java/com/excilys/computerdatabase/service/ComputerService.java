package com.excilys.computerdatabase.service;

import java.time.LocalDate;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exceptions.DiscontinuationPriorToIntroductionExpection;
import com.excilys.computerdatabase.exceptions.WrongPageParameterException;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;

/**
 * The class encapsulates the calls to the {@link ComputerDao} while verifying
 * the parameters.
 *
 * @author debicki
 *
 */
public enum ComputerService {

  INSTANCE;

  private ComputerDao computerDao;
  private DaoFactory daoFactory = DaoFactory.INSTANCE;

  /**
   * Constructor that fetches the DAO from the DAOFactory.
   */
  ComputerService() {
    computerDao = daoFactory.getComputerDao();
  }

  /**
   * Fetches a given number (or fewer) of computers from the {@link ComputerDao}
   * under the form of a {@link Page}.
   *
   * @param offset
   *          the index of the first entity wanted in the page
   * @param numberOfElementsPerPage
   *          maximum number of elements in the wanted page
   * @return A {@link Page} of the found computers
   * @throws WrongPageParameterException
   *           if the parameters are incorrect
   */
  public Page<Computer> getAll(int offset, int numberOfElementsPerPage) throws WrongPageParameterException {
    if ((offset < 0) || (numberOfElementsPerPage <= 0)) {
      throw new WrongPageParameterException();
    }

    return computerDao.fetchAll(offset, numberOfElementsPerPage);
  }

  /**
   * Fetches one computer from the {@link ComputerDao} by searching by the
   * computer id.
   *
   * @param id
   *          The id of the wanted computer
   * @return The found computer or null if no computer were found
   */
  public Computer getById(int id) {
    return computerDao.fetchOne(id);
  }

  /**
   * Adds a new computer to the database.
   *
   * @param computer
   *          The computer to be added to the database
   * @return The id of the added computer
   * @throws DiscontinuationPriorToIntroductionExpection
   *           if the continuity of the dates are incorrect
   */
  public int create(Computer computer) throws DiscontinuationPriorToIntroductionExpection {
    LocalDate introduced = computer.getIntroduced();
    LocalDate discontinued = computer.getDiscontinued();

    if (introduced != null && discontinued != null) {
      if (introduced.isAfter(discontinued)) {
        throw new DiscontinuationPriorToIntroductionExpection();
      }
    }

    return computerDao.add(computer);
  }

  /**
   * Updates a computer in the database.
   *
   * @param computer
   *          The computer to be updated in the database with its fields changed
   * @throws DiscontinuationPriorToIntroductionExpection
   *           if the continuity of the dates are incorrect
   */
  public void update(Computer computer) throws DiscontinuationPriorToIntroductionExpection {
    LocalDate introduced = computer.getIntroduced();
    LocalDate discontinued = computer.getDiscontinued();

    if (introduced != null && discontinued != null) {
      if (introduced.isAfter(discontinued)) {
        throw new DiscontinuationPriorToIntroductionExpection();
      }
    }

    computerDao.update(computer);
  }

  /**
   * Deletes a computer in the database.
   *
   * @param id
   *          The id of the computer to be deleted
   */
  public void delete(int id) {
    computerDao.delete(id);
  }

}
