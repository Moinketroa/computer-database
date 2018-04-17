package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.model.pojo.Company;

public class CompanyService extends AbstractService {

	private CompanyDao companyDao;
	
	public CompanyService() {
		companyDao = daoFactory.getCompanyDao();
	}
	
	public List<Company> getAll() {
		return companyDao.fetchAll();
	}
	
}
