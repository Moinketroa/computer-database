package com.excilys.computerdatabase.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private String driver;

  private static final Logger LOGGER = LoggerFactory.getLogger(DaoFactory.class);

  /**
   * Loads connection informations form the config.properties file.
   */
  DaoFactory() {
    final Properties properties = new Properties();
    final InputStream path = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");

    try {
      properties.load(path);

      driver = properties.getProperty("driver");

      url = properties.getProperty("databaseUrl");
      username = properties.getProperty("username");
      password = properties.getProperty("password");
    } catch (FileNotFoundException e) {
      // LOGGER.error(e.getMessage());
    } catch (IOException e) {
      // LOGGER.error(e.getMessage());
    }

    /*
     * ResourceBundle input = ResourceBundle.getBundle("config");
     * 
     * url = input.getString("databaseUrl"); username = input.getString("username");
     * password = input.getString("password"); driver = input.getString("driver");
     */
  }

  /**
   * Builds and retrieves the connection to the DB.
   *
   * @return Connection to the database
   * @throws SQLException
   *           if something went wrong while trying to connect to the DB
   */
  public Connection getConnection() {
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      LOGGER.error("Driver not found", e);
    }

    try {
      return DriverManager.getConnection(url, username, password);
    } catch (SQLException e) {
      LOGGER.error("Can't establish connection", e);
    }

    return null;
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