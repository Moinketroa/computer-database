package com.excilys.computerdatabase.dao;

public class AbstractDao {

	protected DaoFactory daoFactory;
	
	public AbstractDao(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}
	
}
