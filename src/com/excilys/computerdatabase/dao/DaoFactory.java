package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

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

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    public ComputerDao getComputerDao() {
    	return ComputerDao.INSTANCE;
    }
    
    public CompanyDao getCompanyDao() {
    	return CompanyDao.INSTANCE;
    }
    
}