package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

public class CompanyDao extends AbstractDao {

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
        Statement statement = null;
        ResultSet result = null;
        
        try {
        	connexion = daoFactory.getConnection();
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
        	closeResources(statement, connexion, result);
        }
        
        return company;
	}
}
