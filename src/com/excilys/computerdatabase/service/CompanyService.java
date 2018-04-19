package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.model.pojo.Company;

public enum CompanyService {

	INSTANCE;
	
	private CompanyDao companyDao;
	private DaoFactory daoFactory = DaoFactory.INSTANCE;
	
	private CompanyService() {
		companyDao = daoFactory.getCompanyDao();
	}
	
	public Company getById(int id) {
		return companyDao.fetchOne(id);
	}
	
	public List<Company> getAll() {
		return companyDao.fetchAll();
	}
	
}
