package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

public enum ComputerDao {

	INSTANCE;
	
	private static final String SQL_SELECT_COMPUTER = "SELECT computer.*, company.name AS company_name FROM computer JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
	private static final String SQL_SELECT_COMPUTERS = "SELECT computer.*, company.name AS company_name FROM computer JOIN company ON computer.company_id = company.id ORDER BY computer.id";
	
	private static final String SQL_INSERT_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE computer.id = ?";
	private static final String SQL_DELETE_COMPUTER = "DELETE FROM computer WHERE computer.id = ?";

	private DaoFactory daoFactory = DaoFactory.INSTANCE;
	
	public int add(Computer computer) {
		Integer companyId = null;
        
		if (computer.getCompany() != null)
        	companyId = computer.getCompany().getId();
		
	    try (	Connection connexion = daoFactory.getConnection();
	    		PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_INSERT_COMPUTER, true, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), companyId)) {
            
            preparedStatement.executeUpdate();
            
            try (ResultSet result = preparedStatement.getGeneratedKeys()) {
            
	            if (result.next())
	            	computer.setId(result.getInt(1));
            
            } catch (SQLException e) {
            	e.printStackTrace();
			}
         
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } 
	    
	    return computer.getId();
	}

	public void update(Computer computer) {
		Integer companyId = null;
		
        if (computer.getCompany() != null)
        	companyId = computer.getCompany().getId();
	    
	    try (	Connection connexion = daoFactory.getConnection();
	            PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_UPDATE_COMPUTER, false, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), companyId, computer.getId())) {
            
            preparedStatement.executeUpdate();
            
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
	}

	public void delete(int id) {
	    try (	Connection connexion = daoFactory.getConnection();
		    	PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_DELETE_COMPUTER, false, id)) {
	    	
	    	preparedStatement.executeUpdate();
            
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } 
	}

	public Computer fetchOne(int computerId) {
		Computer computer = null;
        
        try (	Connection connexion = daoFactory.getConnection();
        		PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_SELECT_COMPUTER, false);
        		ResultSet result = preparedStatement.executeQuery()) {
            
            if (result.first()) {
            	computer = ComputerMapper.fromResultSet(result);
            }
            
        } catch (SQLException e) {
			e.printStackTrace();
		}

        return computer;
	}

	public List<Computer> fetchAll() {
		List<Computer> computers = new ArrayList<>();
        
        try (	Connection connexion = daoFactory.getConnection();
        		PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_SELECT_COMPUTERS, false);
        		ResultSet result = preparedStatement.executeQuery()) {
            
            while (result.next()) {
            	computers.add(ComputerMapper.fromResultSet(result));
            }
            
        } catch (SQLException e) {
			e.printStackTrace();
		}

        return computers;
	}
	
	private static PreparedStatement initializationPreparedStatement(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException {
	    PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

	    for (int i = 0; i < objets.length; i++)
	        preparedStatement.setObject(i + 1, objets[i]);

	    return preparedStatement;
	}

}
