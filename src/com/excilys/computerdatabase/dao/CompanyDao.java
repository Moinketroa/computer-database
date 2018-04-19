package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.mapper.CompanyMapper;
import com.excilys.computerdatabase.model.pojo.Company;

public enum CompanyDao {

	INSTANCE;
	
	private static final String SQL_SELECT_COMPANY = "SELECT * FROM company WHERE id = ?";
	private static final String SQL_SELECT_COMPANIES = "SELECT * FROM company";
	
	private DaoFactory daoFactory = DaoFactory.INSTANCE;

	public List<Company> fetchAll() {
		List<Company> companies = new ArrayList<>();
        
        try (	Connection connexion = daoFactory.getConnection();
        		PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_SELECT_COMPANIES, false);
        		ResultSet result = preparedStatement.executeQuery()) {
            
            while (result.next()) {
            	companies.add(CompanyMapper.fromResultSet(result));
            }
            
        } catch (SQLException e) {
			e.printStackTrace();
		} 


        return companies;
	}

	public Company fetchOne(int id) {
		Company company = null;
        
        try (	Connection connexion = daoFactory.getConnection();
        		PreparedStatement preparedStatement = initializationPreparedStatement(connexion, SQL_SELECT_COMPANY, false, id);
        		ResultSet result = preparedStatement.executeQuery()) {
            
            if (result.first()) {
            	company = CompanyMapper.fromResultSet(result);
            }
            
        } catch (SQLException e) {
        	e.printStackTrace();
        }
        
        return company;
	}
	
	private static PreparedStatement initializationPreparedStatement(Connection connexion, String sql, boolean returnGeneratedKeys, Object... objets) throws SQLException {
	    PreparedStatement preparedStatement = connexion.prepareStatement(sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);

	    for (int i = 0; i < objets.length; i++)
	        preparedStatement.setObject(i + 1, objets[i]);

	    return preparedStatement;
	}
}
