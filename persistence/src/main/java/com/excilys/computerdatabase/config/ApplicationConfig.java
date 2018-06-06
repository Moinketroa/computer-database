package com.excilys.computerdatabase.config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

@Configuration
@ComponentScan(basePackages = { "com.excilys.computerdatabase.dao", "com.excilys.computerdatabase.service",
    "com.excilys.computerdatabase.mapper", "com.excilys.computerdatabase.servlet",
    "com.excilys.computerdatabase.validator" })
public class ApplicationConfig {

  private final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

  private String driver;

  private final Properties properties = new Properties();
  private final InputStream path = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");

  private DriverManagerDataSource dataSource;

  {
    try {
      properties.load(path);

      driver = properties.getProperty("driver");

      try {
        Class.forName(driver);
      } catch (ClassNotFoundException e) {
        LOGGER.error("Driver not found", e);
      }
      
      dataSource = new DriverManagerDataSource();
      
      dataSource.setUrl(properties.getProperty("jdbcUrl"));
      dataSource.setUsername(properties.getProperty("username"));
      dataSource.setPassword(properties.getProperty("password"));
      dataSource.setDriverClassName(driver);

      if (properties.getProperty("hsql.initDB").equals("true")) {
        initHSQLDB();
      }
    } catch (FileNotFoundException e) {
      LOGGER.error(e.getMessage());
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }
  }

  @Bean
  public DataSource dataSource() {
    try {
      Class.forName(driver);
    } catch (ClassNotFoundException e) {
      LOGGER.error("Driver not found", e);
    }
    return dataSource;
  }

  @Bean
  public LocalSessionFactoryBean sessionFactory() {
    LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
    sessionFactory.setDataSource(dataSource);
    sessionFactory.setHibernateProperties(hibernateProperties());
    sessionFactory.setAnnotatedClasses(Computer.class, Company.class);
    sessionFactory.setPackagesToScan("com.excilys.computerdatabase.model.pojo");

    return sessionFactory;
  }

  private Properties hibernateProperties() {
    return new Properties() {
      private static final long serialVersionUID = -6659937884292195075L;
      {
        /*
         * setProperty("hibernate.connection.driver", driver);
         * setProperty("hibernate.connection.url", properties.getProperty("jdbcUrl"));
         * setProperty("hibernate.connection.username",
         * properties.getProperty("username"));
         * setProperty("hibernate.connection.password",
         * properties.getProperty("password"));
         */

        setProperty("hibernate.hbm2ddl.auto", properties.getProperty("hibernate.hbm2ddl.auto"));
        setProperty("hibernate.dialect", properties.getProperty("hibernate.dialect"));
        setProperty("hibernate.show_sql", properties.getProperty("hibernate.show_sql"));
        setProperty("hibernate.current_session_context_class",
            properties.getProperty("hibernate.current_session_context_class"));
        setProperty("hibernate.globally_quoted_identifiers",
            properties.getProperty("hibernate.globally_quoted_identifiers"));
      }
    };
  }

  /**
   * Initializes the HSQL Database with tables and entries. Used only for ITs.
   */
  private void initHSQLDB() {
    try (Connection connexion = dataSource.getConnection()) {

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
   * Creates a String array representing a SQL file.
   *
   * @param filename
   *          the path to the SQL file to be represented
   * @return String array representing a SQL file
   * @throws IOException
   */
  private String[] transferDataFromFile(String filename) {
    try (
        FileReader fileReader = new FileReader(
            ApplicationConfig.class.getClassLoader().getResource(filename).getFile());
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