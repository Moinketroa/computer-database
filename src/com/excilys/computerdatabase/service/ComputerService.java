package com.excilys.computerdatabase.service;

import java.sql.Timestamp;
import java.util.List;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

public class ComputerService extends AbstractService {

	private CompanyDao companyDao;
	private ComputerDao computerDao;
	
	public ComputerService() {
		companyDao = daoFactory.getCompanyDao();
		computerDao = daoFactory.getComputerDao();
	}
	
	public List<Computer> getAll() {
		return computerDao.fetchAll();
	}
	
	public Computer getById(int id) {
		try {
			return computerDao.fetchOne(id);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Computer create(String name, Timestamp introduced, Timestamp discontinued) {
		if (name == null) 
			return null;
		
		Computer computer = new Computer(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		
		try {
			computerDao.add(computer);
		} catch (Exception e) {
			return null;
		}
		
		return computer;
	}
	
	public Computer create(String name, Timestamp introduced, Timestamp discontinued, int companyId) {
		if (name == null) 
			return null;
		
		Computer computer = new Computer(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		
		Company company = null;
		
		try {
			companyDao.fetchOne(companyId);
		} catch (Exception e) {
			
		}
		
		computer.setCompany(company);
		
		try {
			computerDao.add(computer);
		} catch (Exception e) {
			return null;
		}
		
		return computer;
	}
	
	public Computer update(int id, String name, Timestamp introduced, Timestamp discontinued) {
		if (name == null) 
			return null;
		
		Computer computer = null;
		
		try {
			computer = computerDao.fetchOne(id);
		} catch (Exception e) {
			return null;
		}
		
		computer.setName(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		
		try {
			computerDao.update(computer);
		} catch (Exception e) {
			return null;
		}
		
		return computer;
	}
	
	public Computer update(int id, String name, Timestamp introduced, Timestamp discontinued, int companyId) {
		if (name == null) 
			return null;
		
		Computer computer = null;
		
		try {
			computer = computerDao.fetchOne(id);
		} catch (Exception e) {
			return null;
		}
		
		computer.setName(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		
		Company company = null;
		
		try {
			companyDao.fetchOne(companyId);
		} catch (Exception e) {
			
		}
		
		computer.setCompany(company);
		
		try {
			computerDao.update(computer);
		} catch (Exception e) {
			return null;
		}
		
		return computer;
	}
	
	public void delete(int id) {
		try {
			computerDao.delete(id);
		} catch (Exception e) {
			
		}
	}
}
