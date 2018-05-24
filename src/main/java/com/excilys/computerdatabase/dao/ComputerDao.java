package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;

/**
 * The class helps accessing Computer entries of the database.
 *
 * @author jmdebicki
 *
 */
@Repository
public class ComputerDao {

  private static final String SQL_SELECT_COMPUTER = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
  private static final String SQL_SELECT_COMPUTERS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id = company.id";
  private static final String SQL_SELECT_COUNT = "SELECT COUNT(*) FROM computer";

  private static final String SQL_INSERT_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
  private static final String SQL_UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE computer.id = ?";
  private static final String SQL_DELETE_COMPUTER = "DELETE FROM computer WHERE computer.id = ?";

  private static final String SQL_SEARCH_COMPUTERS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?";
  private static final String SQL_SEARCH_COUNT = "SELECT COUNT(*) FROM (SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id, company.name AS company_name FROM computer LEFT JOIN company ON computer.company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ?) AS search";

  private static final String SQL_LIMIT = " LIMIT ? OFFSET ?";

  private static final Logger LOGGER = LoggerFactory.getLogger(ComputerDao.class);

  @Autowired
  private ComputerMapper computerMapper;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /**
   * Adds a new computer to the database.
   *
   * @param computer
   *          The computer to be added to the database
   * @return The id of the added computer
   */
  public int add(Computer computer) {
    Integer companyId = computer.getCompany() == null ? null : computer.getCompany().getId();
    Date introduced = computer.getIntroduced() == null ? null : Date.valueOf(computer.getIntroduced());
    Date discontinued = computer.getDiscontinued() == null ? null : Date.valueOf(computer.getDiscontinued());

    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbcTemplate.update((Connection connection) -> {
      return initializationPreparedStatement(connection, SQL_INSERT_COMPUTER, true, computer.getName(), introduced,
          discontinued, companyId);
    }, keyHolder);

    computer.setId(keyHolder.getKey().intValue());

    return keyHolder.getKey().intValue();
  }

  /**
   * Updates a computer in the database.
   *
   * @param computer
   *          The computer to be updated in the database with its fields changed
   */
  public void update(Computer computer) {
    Integer companyId = computer.getCompany() == null ? null : computer.getCompany().getId();
    Date introduced = computer.getIntroduced() == null ? null : Date.valueOf(computer.getIntroduced());
    Date discontinued = computer.getDiscontinued() == null ? null : Date.valueOf(computer.getDiscontinued());

    jdbcTemplate.update(SQL_UPDATE_COMPUTER, computer.getName(), introduced, discontinued, companyId, computer.getId());
  }

  /**
   * Deletes a computer in the database.
   *
   * @param id
   *          The id of the computer to be deleted
   */
  public void delete(int id) {
    jdbcTemplate.update(SQL_DELETE_COMPUTER, id);
  }

  /**
   * Deletes several computer in one call, if one deletion go wrong then all
   * wanted deletions won't occur.
   *
   * @param idVarargs
   *          one or more id of computers wanted to be deleted
   */
  @Transactional(rollbackFor = Throwable.class)
  public void deleteSeveral(Integer... idVarargs) {
    for (int id : idVarargs) {
      jdbcTemplate.update(SQL_DELETE_COMPUTER, id);
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

    try {
      computer = (Computer) jdbcTemplate.queryForObject(SQL_SELECT_COMPUTER, computerMapper, computerId);
    } catch (EmptyResultDataAccessException e) {
      LOGGER.debug("No computer found with ID #" + computerId);
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

    computers = jdbcTemplate.query(completeSQLQuery, computerMapper, numberOfElementsPerPage, offset);
    totalNumberOfElements = jdbcTemplate.queryForObject(SQL_SELECT_COUNT, Integer.class);

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

    computers = jdbcTemplate.query(completeSQLQuery, computerMapper, keywordLike, keywordLike, numberOfElementsPerPage,
        offset);
    totalNumberOfElements = jdbcTemplate.queryForObject(SQL_SEARCH_COUNT, Integer.class, keywordLike, keywordLike);

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
