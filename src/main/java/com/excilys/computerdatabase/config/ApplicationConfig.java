package com.excilys.computerdatabase.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.excilys.computerdatabase.dao.DaoFactory;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = "com.excilys.computerdatabase")
public class ApplicationConfig {

  private final Logger LOGGER = LoggerFactory.getLogger(ApplicationConfig.class);

  @Autowired
  private Environment env;

  @Bean
  public DataSource dataSource() {
    DataSource dataSource = null;

    final Properties properties = new Properties();
    final InputStream path = ClassLoader.getSystemClassLoader().getResourceAsStream("config.properties");
    HikariConfig config = new HikariConfig();

    try {
      properties.load(path);

      config.setJdbcUrl(properties.getProperty("jdbcUrl"));
      config.setUsername(properties.getProperty("username"));
      config.setPassword(properties.getProperty("password"));
      config.setDriverClassName(properties.getProperty("driver"));

      dataSource = new HikariDataSource(config);

    } catch (FileNotFoundException e) {
      LOGGER.error(e.getMessage());
    } catch (IOException e) {
      LOGGER.error(e.getMessage());
    }

    return dataSource;
  }

}