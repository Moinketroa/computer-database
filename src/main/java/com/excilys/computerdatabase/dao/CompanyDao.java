package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
  private DataSource dataSource;

  private CompanyMapper companyMapper = new CompanyMapper();

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

    try (Connection connexion = dataSource.getConnection();
        PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_SELECT_COMPANIES, false,
            numberOfElementsPerPage, offset);
        ResultSet result = preparedStatement.executeQuery()) {

      while (result.next()) {
        companies.add(companyMapper.fromResultSet(result));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    try (Connection connexion = dataSource.getConnection();
        PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_SELECT_COUNT, false);
        ResultSet result = preparedStatement.executeQuery()) {

      if (result.next()) {
        totalNumberOfElements = result.getInt(1);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

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

    try (Connection connexion = dataSource.getConnection();
        PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_SELECT_COMPANY, false, id);
        ResultSet result = preparedStatement.executeQuery()) {

      if (result.next()) {
        company = companyMapper.fromResultSet(result);
      }

    } catch (SQLException e) {
      e.printStackTrace();
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
  public void delete(int id) {
    try (Connection connexion = dataSource.getConnection()) {
      connexion.setAutoCommit(false);

      Savepoint beforeCompanyDeletion = connexion.setSavepoint();

      try (
          PreparedStatement deleteComputersPreparedStatement = initializationPreparedStatement(connexion,
              SQL_DELETE_COMPUTERS, false, id);
          PreparedStatement deleteCompanyPreparedStatement = initializationPreparedStatement(connexion,
              SQL_DELETE_COMPANY, false, id)) {

        deleteComputersPreparedStatement.executeUpdate();
        deleteCompanyPreparedStatement.executeUpdate();

        connexion.commit();
      } catch (SQLException e) {
        LOGGER.error("Something went wrong while building or executing the company delete query, rolling back");
        connexion.rollback(beforeCompanyDeletion);
      } finally {
        connexion.setAutoCommit(true);
      }

    } catch (SQLException e) {
      LOGGER.error("Something went wrong during the company deletion", e);
    }
  }

  /**
   * Initialize a {@link PreparedStatement} with a given SQL query. Sets the
   * parameters of the query if any.
   *
   * @param connexion
   *          the connection to the database
   * @param sql
   *          the SQL query to be represented
   * @param returnGeneratedKeys
   *          to be set to true if generated keys due to the query are wanted to
   *          be retrieved
   * @param objets
   *          the parameters to be set in the SQL query
   * @return A {@link PreparedStatement} representing the wanted SQL query with
   *         the parameters set
   * @throws SQLException
   *           if something went wrong while executing the query
   */
  private static PreparedStatement initializationPreparedStatement(Connection connexion, String sql,
      boolean returnGeneratedKeys, Object... objets) throws SQLException {
    PreparedStatement preparedStatement = connexion.prepareStatement(sql,
        returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

    for (int i = 0; i < objets.length; i++) {
      preparedStatement.setObject(i + 1, objets[i]);
    }

    return preparedStatement;
  }
}
