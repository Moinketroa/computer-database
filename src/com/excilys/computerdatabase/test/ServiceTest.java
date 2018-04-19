package com.excilys.computerdatabase.test;

import java.sql.Date;
import java.util.List;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

public class ServiceTest {

	public static void main(String[] args) {

		CompanyService companyService = CompanyService.INSTANCE;
		ComputerService computerService = ComputerService.INSTANCE;
		
		List<Company> companies = companyService.getAll();
		List<Computer> computers = computerService.getAll();
		
		for (Company company : companies)
			System.out.println(company);
		
		for (Computer computer : computers)
			System.out.println(computer);
		
		Computer computer = new Computer("TESTCOMPUTER");
		computer.setIntroduced(new Date(0));
		computer.setDiscontinued(new Date(100000));
		
		Company company = new Company("IBMTEST");
		company.setId(13);
		
		computer.setCompany(company);
		
		computerService.create(computer);
		
		Computer computerFromDB = computerService.getById(computer.getId());
		
		System.out.println(computerFromDB);
		
		computerFromDB.setDiscontinued(null);
		
		computerService.update(computerFromDB);
		computerFromDB = computerService.getById(computerFromDB.getId());
		
		System.out.println(computerFromDB);
		
		computerService.delete(computerFromDB);
		computerFromDB = computerService.getById(computerFromDB.getId());
		
		System.out.println(computerFromDB);
		
	}

}
