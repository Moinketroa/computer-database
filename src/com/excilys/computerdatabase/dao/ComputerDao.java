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

public class ComputerDao extends AbstractDao {

	private static final String SQL_SELECT_COMPUTER = "SELECT computer.*, company.name AS company_name FROM computer JOIN company ON computer.company_id = company.id WHERE computer.id = ?";
	private static final String SQL_SELECT_COMPUTERS = "SELECT computer.*, company.name AS company_name FROM computer JOIN company ON computer.company_id = company.id ORDER BY computer.id";
	
	private static final String SQL_INSERT_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE computer.id = ?";
	private static final String SQL_DELETE_COMPUTER = "DELETE FROM computer WHERE computer.id = ?";
	
	public ComputerDao(DaoFactory daoFactory) {
		super(daoFactory);
	}

	public void add(Computer computer) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet result = null;
	    
	    try {
            connexion = daoFactory.getConnection();
            
            Integer companyId = null;
            if (computer.getCompany() != null)
            	companyId = computer.getCompany().getId();
            
            preparedStatement = initializationPreparedStatement(connexion, SQL_INSERT_COMPUTER, true, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), companyId);
            preparedStatement.executeUpdate();
            
            result = preparedStatement.getGeneratedKeys();
            
            if (result.next())
            	computer.setId(result.getInt(1));
         
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
        	closeResources(preparedStatement, connexion, result);
        }
	}

	public void update(Computer computer) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet result = null;
	    
	    try {
            connexion = daoFactory.getConnection();
            
            Integer companyId = null;
            if (computer.getCompany() != null)
            	companyId = computer.getCompany().getId();
            
            preparedStatement = initializationPreparedStatement(connexion, SQL_UPDATE_COMPUTER, false, computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), companyId, computer.getId());
            preparedStatement.executeUpdate();
            
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
        	closeResources(preparedStatement, connexion, result);
        }
	}

	public void delete(int id) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet result = null;
	    
	    try {
            connexion = daoFactory.getConnection();
            
            preparedStatement = initializationPreparedStatement(connexion, SQL_DELETE_COMPUTER, false, id);
            preparedStatement.executeUpdate();
            
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } finally {
        	closeResources(preparedStatement, connexion, result);
        }
	}

	public Computer fetchOne(int computerId) {
		Computer computer = null;
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            connexion = daoFactory.getConnection();
            
            preparedStatement = initializationPreparedStatement(connexion, SQL_SELECT_COMPUTER, false, computerId);
            result = preparedStatement.executeQuery();
            
            if (result.first()) {
            	computer = ComputerMapper.fromResultSet(result);
            }
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
        	closeResources(preparedStatement, connexion, result);
        }

        return computer;
	}

	public List<Computer> fetchAll() {
		List<Computer> computers = new ArrayList<>();
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
            connexion = daoFactory.getConnection();
            
            preparedStatement = initializationPreparedStatement(connexion, SQL_SELECT_COMPUTERS, false);
            result = preparedStatement.executeQuery();
            
            while (result.next()) {
            	computers.add(ComputerMapper.fromResultSet(result));
            }
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
        	closeResources(preparedStatement, connexion, result);
        }

        return computers;
	}

}
