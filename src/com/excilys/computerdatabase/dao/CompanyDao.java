package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.pojo.Company;

public class CompanyDao extends AbstractDao {

	private static final String SQL_SELECT_COMPANY = "SELECT * FROM company WHERE id = ?";
	
	public CompanyDao(DaoFactory daoFactory) {
		super(daoFactory);
	}

	public List<Company> fetchAll() {
		List<Company> companies = new ArrayList<>();
		Connection connexion = null;
        Statement statement = null;
        ResultSet result = null;
        
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            result = statement.executeQuery("SELECT * FROM company;");
            
            while (result.next()) {
            	int id = result.getInt("id");
            	String name = result.getString("name");
            	
            	Company company = new Company(name);
            	company.setId(id);
            	
            	companies.add(company);
            }
        } catch (SQLException e) {
			e.printStackTrace();
		} finally {
        	closeResources(statement, connexion, result);
        }

        return companies;
	}

	public Company fetchOne(int id) {
		Company company = null;
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
        ResultSet result = null;
        
        try {
        	connexion = daoFactory.getConnection();
        	preparedStatement = initializationPreparedStatement(connexion, SQL_SELECT_COMPANY, false, id);
            result = preparedStatement.executeQuery();
            
            if (result.first()) {
            	int companyId = result.getInt("id");
            	String name = result.getString("name");
            	
            	company = new Company(name);
            	company.setId(companyId);
            }
        } catch (SQLException e) {
        	e.printStackTrace();
        } finally {
        	closeResources(preparedStatement, connexion, result);
        }
        
        return company;
	}
}
