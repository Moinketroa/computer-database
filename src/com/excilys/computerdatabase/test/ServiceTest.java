package com.excilys.computerdatabase.test;

import java.sql.Timestamp;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.service.ComputerService;

public class ServiceTest {

	public static void main(String[] args) {
		
		System.out.println("/*************** SERVICE TESTS ***************/\n\n");
		
		System.out.println("/*****GET ALL COMPANIES*****/");
		CompanyService companyService = new CompanyService();
		
		for (Company company : companyService.getAll())
			System.out.println(company);
		
		System.out.println("\n/*****GET ALL COMPUTERS*****/");
		ComputerService computerService = new ComputerService();
		
		for (Computer computer : computerService.getAll())
			System.out.println(computer);
		
		System.out.println("\n/*****GET ONE COMPUTER BY ID*****/");
		System.out.println(computerService.getById(354));
		
		System.out.println("\n/*****CREATE COMPUTER*****/");
		Computer addedComputer = computerService.create(
				"nAcer Portable", 
				new Timestamp(123456), 
				new Timestamp(876544321));
		int addedComputerId = addedComputer.getId();
		
		System.out.println("/*****PRINT RECEIVED COMPUTER*****/");
		System.out.println(addedComputer);
		System.out.println("/*****PRINT COMPUTER BY ID*****/");
		System.out.println(computerService.getById(addedComputerId));
		
		System.out.println("\n/*****UPDATE COMPUTER*****/");
		Computer updatedComputer = computerService.update(
				addedComputerId, 
				"nAcer Boudjlida", 
				null, 
				null, 
				12);
		
		System.out.println("/*****PRINT RECEIVED COMPUTER*****/");
		System.out.println(updatedComputer);
		System.out.println("/*****PRINT COMPUTER BY ID*****/");
		System.out.println(computerService.getById(addedComputerId));
		
		System.out.println("\n/*****DELETE COMPUTER*****/");
		computerService.delete(addedComputerId);
		System.out.println("/*****TRYING TO PRINT COMPUTER BY ID*****/");
		System.out.println(computerService.getById(addedComputerId));
	}

}
