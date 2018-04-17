package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.DaoFactory;

public class AbstractService {

	protected DaoFactory daoFactory = DaoFactory.getInstance();
	
	public AbstractService() {
		
	}
	
}
