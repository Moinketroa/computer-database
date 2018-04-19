package com.excilys.computerdatabase.service;

import java.util.List;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.model.pojo.Computer;

public enum ComputerService {

	INSTANCE;
	
	private ComputerDao computerDao;
	private DaoFactory daoFactory = DaoFactory.INSTANCE;
	
	private ComputerService() {
		computerDao = daoFactory.getComputerDao();
	}
	
	public List<Computer> getAll() {
		return computerDao.fetchAll();
	}
	
	public Computer getById(int id) {
		return computerDao.fetchOne(id);
	}
	
	public void create(Computer computer) {
		computerDao.add(computer);
	}
	
	public void update(Computer computer) {
		computerDao.update(computer);
	}
	
	public void delete(Computer computer) {
		computerDao.delete(computer.getId());
	}

}
