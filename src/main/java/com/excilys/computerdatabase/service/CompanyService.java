package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exceptions.WrongPageParameterException;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.page.Page;

/**
 * The class encapsulates the calls to the {@link CompanyDao} while verifying the parameters.
 *
 * @author debicki
 *
 */
public enum CompanyService {

  INSTANCE;

  private CompanyDao companyDao;
  private DaoFactory daoFactory = DaoFactory.INSTANCE;

  /**
   * Constructor that fetches the DAO from the DAOFactory.
   */
  CompanyService() {
    companyDao = daoFactory.getCompanyDao();
  }

  /**
   * Fetches one company from the {@link CompanyDao} by searching by the company id.
   *
   * @param id
   *          The id of the wanted company
   * @return The found company or null if no company were found
   */
  public Company getById(int id) {
    return companyDao.fetchOne(id);
  }

  /**
   * Fetches a given number (or fewer) of companies from the {@link CompanyDao} under the
   * form of a {@link Page}.
   *
   * @param offset
   *          the index of the first entity wanted in the page
   * @param numberOfElementsPerPage
   *          maximum number of elements in the wanted page
   * @return A {@link Page} of the found companies
   * @throws WrongPageParameterException if the parameters are incorrect
   */
  public Page<Company> getAll(int offset, int numberOfElementsPerPage) throws WrongPageParameterException {
    if ((offset < 0) || (numberOfElementsPerPage <= 0)) {
      throw new WrongPageParameterException();
    }

    return companyDao.fetchAll(offset, numberOfElementsPerPage);
  }

}
