package com.excilys.computerdatabase.dao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 
 * The class helps retrieving the different DAOs and the connection to the database
 * 
 * @author jmdebicki
 *
 */
public enum DaoFactory {
	
	INSTANCE;

	private String url;
	private String username;
	private String password;
	
    private DaoFactory() {
    	try (InputStream input = new FileInputStream("config.properties");){
            Class.forName("com.mysql.jdbc.Driver");
            
            Properties properties = new Properties();
            properties.load(input);
            
            url = properties.getProperty("databaseUrl");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        } catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
    }

    /**
     * Builds and retrieves the connection to the DB
     * 
     * @return Connection to the database
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    /**
     * Retrieves the DAO for computer entities
     * 
     * @return The DAO for computer entities
     */
    public ComputerDao getComputerDao() {
    	return ComputerDao.INSTANCE;
    }
    
    /**
     * Retrieves the DAO for company entities
     * 
     * @return The DAO for company entities
     */
    public CompanyDao getCompanyDao() {
    	return CompanyDao.INSTANCE;
    }
    
}