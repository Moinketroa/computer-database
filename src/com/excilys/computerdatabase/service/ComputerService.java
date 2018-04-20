package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;

public enum ComputerService {

	INSTANCE;
	
	private ComputerDao computerDao;
	private DaoFactory daoFactory = DaoFactory.INSTANCE;
	
	private ComputerService() {
		computerDao = daoFactory.getComputerDao();
	}
	
	public Page<Computer> getAll(int offset, int numberOfElementsPerPage) {
		return computerDao.fetchAll(offset, numberOfElementsPerPage);
	}
	
	public Computer getById(int id) {
		return computerDao.fetchOne(id);
	}
	
	public int create(Computer computer) {
		return computerDao.add(computer);
	}
	
	public void update(Computer computer) {
		computerDao.update(computer);
	}
	
	public void delete(Computer computer) {
		computerDao.delete(computer.getId());
	}

}
