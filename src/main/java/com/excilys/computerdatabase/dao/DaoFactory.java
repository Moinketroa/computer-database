package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * The class helps retrieving the different DAOs and the connection to the
 * database.
 *
 * @author jmdebicki
 *
 */
public enum DaoFactory {

  INSTANCE;

  private String url;
  private String username;
  private String password;

  /**
   * Loads connection informations form the config.properties file.
   */
  DaoFactory() {
    try {
      ResourceBundle input = ResourceBundle.getBundle("config");

      url = input.getString("databaseUrl");
      username = input.getString("username");
      password = input.getString("password");

      Class.forName(input.getString("driver"));
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /**
   * Builds and retrieves the connection to the DB.
   *
   * @return Connection to the database
   * @throws SQLException
   *           if something went wrong while trying to connect to the DB
   */
  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(url, username, password);
  }

  /**
   * Retrieves the DAO for computer entities.
   *
   * @return The DAO for computer entities
   */
  public ComputerDao getComputerDao() {
    return ComputerDao.INSTANCE;
  }

  /**
   * Retrieves the DAO for company entities.
   *
   * @return The DAO for company entities
   */
  public CompanyDao getCompanyDao() {
    return CompanyDao.INSTANCE;
  }

}