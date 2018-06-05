package com.excilys.computerdatabase.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.OrderByComputer;
import com.excilys.computerdatabase.dao.OrderByMode;
import com.excilys.computerdatabase.exceptions.badrequest.DiscontinuationPriorToIntroductionExpection;
import com.excilys.computerdatabase.exceptions.badrequest.WrongPageParameterException;
import com.excilys.computerdatabase.exceptions.notfound.ComputerNotFoundException;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;

/**
 * The class encapsulates the calls to the {@link ComputerDao} while verifying
 * the parameters.
 *
 * @author debicki
 *
 */
@Service
public class ComputerService {

  @Autowired
  private ComputerDao computerDao;

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
  public Page<Computer> getAll(OrderByComputer order, OrderByMode mode, int offset, int numberOfElementsPerPage)
      throws WrongPageParameterException {
    if ((offset < 0) || (numberOfElementsPerPage <= 0)) {
      throw new WrongPageParameterException();
    }

    return computerDao.fetchAll(order, mode, offset, numberOfElementsPerPage);
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
    return getAll(OrderByComputer.ID, OrderByMode.ASCENDING, offset, numberOfElementsPerPage);
  }

  /**
   * Fetches one computer from the {@link ComputerDao} by searching by the
   * computer id.
   *
   * @param id
   *          The id of the wanted computer
   * @return The found computer
   * @throws ComputerNotFoundException
   *           if no computer were found
   */
  public Computer getById(int id) throws ComputerNotFoundException {
    Computer computer = computerDao.fetchOne(id);

    if (computer == null) {
      throw new ComputerNotFoundException(id);
    }

    return computer;
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

  /**
   * Deletes several computer in one call, if one deletion go wrong then all
   * wanted deletions won't occur.
   *
   * @param ids
   *          one or more id of computers wanted to be deleted
   */
  public void deleteSeveral(List<Integer> ids) {
    computerDao.deleteSeveral(ids);
  }

  public Page<Computer> search(String keyword, OrderByComputer order, OrderByMode mode, int offset,
      int numberOfElementsPerPage) throws WrongPageParameterException {
    if ((offset < 0) || (numberOfElementsPerPage <= 0)) {
      throw new WrongPageParameterException();
    }

    return computerDao.search(keyword, order, mode, offset, numberOfElementsPerPage);
  }

  public Page<Computer> search(String keyword, int offset, int numberOfElementsPerPage)
      throws WrongPageParameterException {
    return search(keyword, OrderByComputer.ID, OrderByMode.ASCENDING, offset, numberOfElementsPerPage);
  }
}
