package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;

/**
 * The class helps accessing Computer entries of the database.
 *
 * @author jmdebicki
 *
 */
public enum ComputerDao {

  INSTANCE;

  private static final String SQL_SELECT_COMPUTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
  private static final String SQL_SELECT_COMPUTERS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id = company.id";
  private static final String SQL_SELECT_COUNT = "SELECT COUNT(*) FROM computer";

  private static final String SQL_INSERT_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
  private static final String SQL_UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE computer.id = ?";
  private static final String SQL_DELETE_COMPUTER = "DELETE FROM computer WHERE computer.id = ?";

  private static final String SQL_SEARCH_COMPUTERS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?";
  private static final String SQL_SEARCH_COUNT = "SELECT COUNT(*) FROM (SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?)";

  private static final String SQL_LIMIT = " LIMIT ? OFFSET ?";

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);

  private DaoFactory daoFactory = DaoFactory.INSTANCE;

  /**
   * Adds a new computer to the database.
   *
   * @param computer
   *          The computer to be added to the database
   * @return The id of the added computer
   */
  public int add(Computer computer) {
    Integer companyId = null;

    if (computer.getCompany() != null) {
      companyId = computer.getCompany().getId();
    }

    Date introduced = computer.getIntroduced() == null ? null : Date.valueOf(computer.getIntroduced());
    Date discontinued = computer.getDiscontinued() == null ? null : Date.valueOf(computer.getDiscontinued());

    try (Connection connexion = daoFactory.getConnection();
        PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_INSERT_COMPUTER, true,
            computer.getName(), introduced, discontinued, companyId)) {

      preparedStatement.executeUpdate();

      try (ResultSet result = preparedStatement.getGeneratedKeys()) {

        if (result.next()) {
          computer.setId(result.getInt(1));
        }

      } catch (SQLException e) {
        LOGGER.error("Something went wrong with the ResultSet", e);
      }

    } catch (SQLException e) {
      LOGGER.error("Something went wrong while building the statement", e);
    }

    return computer.getId();
  }

  /**
   * Updates a computer in the database.
   *
   * @param computer
   *          The computer to be updated in the database with its fields changed
   */
  public void update(Computer computer) {
    Integer companyId = null;

    if (computer.getCompany() != null) {
      companyId = computer.getCompany().getId();
    }

    Date introduced = computer.getIntroduced() == null ? null : Date.valueOf(computer.getIntroduced());
    Date discontinued = computer.getDiscontinued() == null ? null : Date.valueOf(computer.getDiscontinued());

    try (Connection connexion = daoFactory.getConnection();
        PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_UPDATE_COMPUTER, false,
            computer.getName(), introduced, discontinued, companyId, computer.getId())) {

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      LOGGER.error("Something went wrong while building the statement", e);
    }
  }

  /**
   * Deletes a computer in the database.
   *
   * @param id
   *          The id of the computer to be deleted
   */
  public void delete(int id) {
    try (Connection connexion = daoFactory.getConnection();
        PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_DELETE_COMPUTER, false,
            id)) {

      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      LOGGER.error("Something went wrong while building the statement", e);
    }
  }

  /**
   * Deletes several computer in one call, if one deletion go wrong then all
   * wanted deletions won't occur.
   *
   * @param idVarargs
   *          one or more id of computers wanted to be deleted
   */
  public void deleteSeveral(Integer... idVarargs) {
    try (Connection connexion = daoFactory.getConnection()) {
      connexion.setAutoCommit(false);

      Savepoint beforeMultipleDeletion = connexion.setSavepoint();

      try {
        for (int id : idVarargs) {
          try (PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_DELETE_COMPUTER,
              false, id)) {

            preparedStatement.executeUpdate();

          } catch (SQLException e) {
            throw e;
          }
        }

        connexion.commit();
      } catch (SQLException e) {
        connexion.rollback(beforeMultipleDeletion);
      } finally {
        connexion.setAutoCommit(true);
      }
    } catch (SQLException e) {
      LOGGER.error("Something went wrong during the multiple deletion", e);
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
    Computer computer = null;

    try (Connection connexion = daoFactory.getConnection();
        PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_SELECT_COMPUTER, false,
            computerId);
        ResultSet result = preparedStatement.executeQuery()) {

      if (result.next()) {
        computer = ComputerMapper.fromResultSet(result);
      }

    } catch (SQLException e) {
      LOGGER.error("Something went wrong with the ResultSet or while building the statement", e);
    }

    return computer;
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

    String completeSQLQuery = SQL_SELECT_COMPUTERS + " ORDER BY " + orderBy + " " + mode + SQL_LIMIT;

    try (Connection connexion = daoFactory.getConnection();
        PreparedStatement preparedSelectStatement = initializationPreparedStatement(connexion, completeSQLQuery, false,
            numberOfElementsPerPage, offset);
        PreparedStatement preparedCountStatement = initializationPreparedStatement(connexion, SQL_SELECT_COUNT, false);
        ResultSet selectResult = preparedSelectStatement.executeQuery();
        ResultSet countResult = preparedCountStatement.executeQuery()) {

      while (selectResult.next()) {
        computers.add(ComputerMapper.fromResultSet(selectResult));
      }

      if (countResult.next()) {
        totalNumberOfElements = countResult.getInt(1);
      }

    } catch (SQLException e) {
      LOGGER.error("Something went wrong with the ResultSet or while building the statement", e);
    }

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
    String keywordLike = "%" + keyword + "%";

    String completeSQLQuery = SQL_SEARCH_COMPUTERS + " ORDER BY " + orderBy + " " + mode + SQL_LIMIT;

    try (Connection connexion = daoFactory.getConnection();
        PreparedStatement preparedSearchStatement = initializationPreparedStatement(connexion, completeSQLQuery, false,
            keywordLike, keywordLike, numberOfElementsPerPage, offset);
        PreparedStatement preparedCountStatement = initializationPreparedStatement(connexion, SQL_SEARCH_COUNT, false,
            keywordLike, keywordLike);
        ResultSet searchResult = preparedSearchStatement.executeQuery();
        ResultSet countResult = preparedCountStatement.executeQuery()) {

      while (searchResult.next()) {
        computers.add(ComputerMapper.fromResultSet(searchResult));
      }

      if (countResult.next()) {
        totalNumberOfElements = countResult.getInt(1);
      }

    } catch (SQLException e) {
      LOGGER.error("Something went wrong with the ResultSet or while building the statement", e);
    }

    return new Page<>(computers, offset, numberOfElementsPerPage, totalNumberOfElements);
  }

  /**
   * Initialize a {@link PreparedStatement} with a given SQL query. Sets the
   * parameters of the query if any.
   *
   * @param connection
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
  private static PreparedStatement initializationPreparedStatement(Connection connection, String sql,
      boolean returnGeneratedKeys, Object... objets) throws SQLException {
    PreparedStatement preparedStatement = connection.prepareStatement(sql,
        returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

    for (int i = 0; i < objets.length; i++) {
      preparedStatement.setObject(i + 1, objets[i]);
    }

    LOGGER.debug("Query string built : " + preparedStatement.toString());

    return preparedStatement;
  }

}
