package com.excilys.computerdatabase.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum HSQLDatabase {

  INSTANCE;

  private static final String DROP_CONSTRAINT = "ALTER TABLE computer DROP CONSTRAINT FK_COMPUTER_COMPANY_1";
  private static final String DROP_COMPUTER = "DROP TABLE computer";
  private static final String DROP_COMPANY = "DROP TABLE company";

  private DaoFactory daoFactory = DaoFactory.INSTANCE;

  private static final Logger LOGGER = LoggerFactory.getLogger(HSQLDatabase.class);

  /**
   * Initializes the HSQL Database with tables and entries.
   */
  public void init() {
    try (Connection connexion = daoFactory.getConnection()) {

      String[] tablesStrings = transferDataFromFile("db/1-SCHEMA.sql");
      String[] entriesStrings = transferDataFromFile("db/3-ENTRIES.sql");

      Statement statement = connexion.createStatement();

      LOGGER.info("Creating the schema for the HSQLDB");
      executeScript(tablesStrings, statement);
      LOGGER.info("Inserting entries in the HSQLDB tables");
      executeScript(entriesStrings, statement);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Destroys the tables previously added.
   */
  public void destroy() {
    try (Connection connexion = daoFactory.getConnection()) {
      Statement statement = connexion.createStatement();

      LOGGER.info("Dropping HSQLDB tables");
      statement.executeUpdate(DROP_CONSTRAINT);
      statement.executeUpdate(DROP_COMPANY);
      statement.executeUpdate(DROP_COMPUTER);
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Creates a String array representing a SQL file.
   *
   * @param filename
   *          the path to the SQL file to be represented
   * @return String array representing a SQL file
   * @throws IOException
   */
  private String[] transferDataFromFile(String filename) {
    try (FileReader fileReader = new FileReader(getClass().getClassLoader().getResource(filename).getFile());
        BufferedReader bufferedReader = new BufferedReader(fileReader)) {

      String tempString = new String();
      StringBuilder stringBuilder = new StringBuilder();

      while ((tempString = bufferedReader.readLine()) != null) {
        stringBuilder.append(tempString);
      }

      bufferedReader.close();

      return stringBuilder.toString().split(";");
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   * Executes several SQL statements and avoids executing empty statements.
   *
   * @param sqlLines
   *          an array of String representing SQL statements
   * @param statement
   *          a {@link Statement} connected to the database executing the SQL
   *          queries
   * @throws SQLException
   *           if something went wrong while executing the script
   */
  private void executeScript(String[] sqlLines, Statement statement) throws SQLException {
    for (int i = 0; i < sqlLines.length; i++) {
      if (!sqlLines[i].trim().equals("")) {
        statement.executeUpdate(sqlLines[i] + ";");
      }
    }
  }
}
