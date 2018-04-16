package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

public class CompanyDaoImpl extends AbstractDao implements CompanyDao {

	public CompanyDaoImpl(DaoFactory daoFactory) {
		super(daoFactory);
	}

	@Override
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
		}

        return companies;
	}

	@Override
	public Company fetchOne(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
