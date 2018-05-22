package com.excilys.computerdatabase.dao;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.mapper.CompanyMapper;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.page.Page;

/**
 * The class helps accessing Company entries of the database.
 *
 * @author jmdebicki
 *
 */
@Repository
public class CompanyDao {

  private static final String SQL_SELECT_COMPANY = "SELECT id, name FROM company WHERE id = ?";
  private static final String SQL_SELECT_COMPANIES = "SELECT id, name FROM company LIMIT ? OFFSET ?";
  private static final String SQL_SELECT_COUNT = "SELECT COUNT(*) FROM company";

  private static final String SQL_DELETE_COMPANY = "DELETE FROM company WHERE id = ?";
  private static final String SQL_DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id = ?";

  private static final Logger LOGGER = LoggerFactory.getLogger(CompanyDao.class);

  @Autowired
  private CompanyMapper companyMapper;

  @Autowired
  private JdbcTemplate jdbcTemplate;

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

    companies = jdbcTemplate.query(SQL_SELECT_COMPANIES, companyMapper, numberOfElementsPerPage, offset);
    totalNumberOfElements = jdbcTemplate.queryForObject(SQL_SELECT_COUNT, Integer.class);

    return new Page<>(companies, offset, numberOfElementsPerPage, totalNumberOfElements);
  }

  /**
   * Fetches one company from the database by searching by the company id.
   *
   * @param id
   *          The id of the wanted company
   * @return The found company or null if no company were found
   */
  public Company fetchOne(int id) {
    Company company = null;

    try {
      company = (Company) jdbcTemplate.queryForObject(SQL_SELECT_COMPANY, companyMapper, id);
    } catch (EmptyResultDataAccessException e) {
      LOGGER.debug("No company found with ID #" + id);
    }

    return company;
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
    jdbcTemplate.update(SQL_DELETE_COMPUTERS, id);
    jdbcTemplate.update(SQL_DELETE_COMPANY, id);
  }
}
