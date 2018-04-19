package com.excilys.computerdatabase.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerdatabase.model.pojo.Company;

public class CompanyMapper {

	public static Company fromResultSet(ResultSet result) throws SQLException {
		int id = result.getInt("id");
    	String name = result.getString("name");
    	
    	Company company = new Company(name);
    	company.setId(id);
    	
    	return company;
	}
	
}
