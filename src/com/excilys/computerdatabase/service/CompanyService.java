package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.CompanyDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exceptions.WrongPageParameterException;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.page.Page;

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
	
	public Page<Company> getAll(int offset, int numberOfElementsPerPage) throws WrongPageParameterException {
		if ((offset < 0) || (numberOfElementsPerPage <= 0))
			throw new WrongPageParameterException();
		
		return companyDao.fetchAll(offset, numberOfElementsPerPage);
	}
	
}
