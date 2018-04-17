package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

public class ComputerDao extends AbstractDao {

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

	public void delete(Computer computer) {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet result = null;
	    
	    try {
            connexion = daoFactory.getConnection();
            
            preparedStatement = initializationPreparedStatement(connexion, SQL_DELETE_COMPUTER, false, computer.getId());
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
        Statement statement = null;
        ResultSet result = null;
        
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            result = statement.executeQuery("SELECT * FROM computer WHERE id = " + computerId + ";");
            
            if (result.first()) {
            	int id = result.getInt("id");
            	String name = result.getString("name");
            	Timestamp introduced = null;
            	Timestamp discontinued = null;
            	int company_id = result.getInt("company_id");
            	
            	try {
            		introduced = result.getTimestamp("introduced");
            		discontinued = result.getTimestamp("discontinued");
            	} catch (Exception e) {
            		
				}
            	
            	computer = new Computer(name);
            	computer.setId(id);
            	computer.setIntroduced(introduced);
            	computer.setDiscontinued(discontinued);
            	computer.setCompany(fetchCompany(connexion, company_id));
            }
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
        	closeResources(statement, connexion, result);
        }

        return computer;
	}

	public List<Computer> fetchAll() {
		List<Computer> computers = new ArrayList<>();
		Connection connexion = null;
        Statement statement = null;
        ResultSet result = null;
        
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            result = statement.executeQuery("SELECT * FROM computer;");
            
            while (result.next()) {
            	int id = result.getInt("id");
            	String name = result.getString("name");
            	Timestamp introduced = null;
            	Timestamp discontinued = null;
            	int company_id = result.getInt("company_id");
            	
            	try {
            		introduced = result.getTimestamp("introduced");
            		discontinued = result.getTimestamp("discontinued");
            	} catch (Exception e) {
            		
				}
            	
            	Computer computer = new Computer(name);
            	computer.setId(id);
            	computer.setIntroduced(introduced);
            	computer.setDiscontinued(discontinued);
            	computer.setCompany(fetchCompany(connexion, company_id));
            	
            	computers.add(computer);
            }
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
        	closeResources(statement, connexion, result);
        }

        return computers;
	}
	
	private Company fetchCompany(Connection connexion, int id) {
		Company company = null;
        Statement statement = null;
        ResultSet result = null;
        
        try {
            statement = connexion.createStatement();
            result = statement.executeQuery("SELECT * FROM company WHERE id = " + id + ";");
            
            if (result.first()) {
            	int companyId = result.getInt("id");
            	String name = result.getString("name");
            	
            	company = new Company(name);
            	company.setId(companyId);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
        	closeResources(statement, result);
        }
        
        return company;
	}

}
