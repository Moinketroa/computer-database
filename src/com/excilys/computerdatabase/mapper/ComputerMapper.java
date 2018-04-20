package com.excilys.computerdatabase.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.CompanyService;

public class ComputerMapper {

	public static Computer fromResultSet(ResultSet result) throws SQLException {
		int id = result.getInt("id");
    	String name = result.getString("name");
    	
    	LocalDate introduced = null;
    	if (result.getDate("introduced") != null)
    		introduced = result.getDate("introduced").toLocalDate();
    	
    	LocalDate discontinued = null;
    	if (result.getDate("discontinued") != null)
    		discontinued = result.getDate("discontinued").toLocalDate();
    	
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
    	
	public static Computer fromParameters(String name, LocalDate introduced, LocalDate discontinued, Integer companyId) {
		Computer computer = new Computer(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		
		if (companyId != null)
			computer.setCompany(CompanyService.INSTANCE.getById(companyId));
		
		return computer;
	}
}
