package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

/**
 * 
 * The class helps retrieving the different DAOs and the connection to the database
 * 
 * @author jmdebicki
 *
 */
public enum DaoFactory {
	
	INSTANCE;
	
    private String url = "jdbc:mysql://localhost:3306/computer-database-db?autoReconnect=true&useSSL=false";
    private String username = "admincdb";
    private String password = "qwerty1234";

    private DaoFactory() {
    	try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
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