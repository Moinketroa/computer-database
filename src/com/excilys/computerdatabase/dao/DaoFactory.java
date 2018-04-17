package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

public class DaoFactory {
    private String url;
    private String username;
    private String password;

    DaoFactory(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public static DaoFactory getInstance() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
        	e.printStackTrace();
        }

        DaoFactory instance = new DaoFactory("jdbc:mysql://localhost:3306/computer-database-db?autoReconnect=true&useSSL=false", "admincdb", "qwerty1234");
        return instance;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }
    
    public ComputerDao getComputerDao() {
    	return new ComputerDao(this);
    }
    
    public CompanyDao getCompanyDao() {
    	return new CompanyDao(this);
    }
    
}