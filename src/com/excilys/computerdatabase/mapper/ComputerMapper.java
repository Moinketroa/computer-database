package com.excilys.computerdatabase.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

public class ComputerMapper {

	public static Computer fromResultSet(ResultSet result) throws SQLException {
		int id = result.getInt("id");
    	String name = result.getString("name");
    	Date introduced = result.getDate("introduced");
    	Date discontinued = result.getDate("discontinued");
    	
    	int company_id = result.getInt("company_id");
    	String company_name = result.getString("company_name");
    	
    	Computer computer = new Computer(name);
    	computer.setId(id);
    	computer.setIntroduced(introduced);
    	computer.setDiscontinued(discontinued);
    	
    	Company company = new Company(company_name);
    	company.setId(company_id);
    	
    	computer.setCompany(company);
    	
    	return computer;
	}
	
}
