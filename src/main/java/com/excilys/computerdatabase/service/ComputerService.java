package com.excilys.computerdatabase.service;

import java.time.LocalDate;

import com.excilys.computerdatabase.dao.ComputerDao;
import com.excilys.computerdatabase.dao.DaoFactory;
import com.excilys.computerdatabase.exceptions.DiscontinuationPriorToIntroductionExpection;
import com.excilys.computerdatabase.exceptions.WrongPageParameterException;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.page.Page;

public enum ComputerService {

	INSTANCE;
	
	private ComputerDao computerDao;
	private DaoFactory daoFactory = DaoFactory.INSTANCE;
	
	private ComputerService() {
		computerDao = daoFactory.getComputerDao();
	}
	
	public Page<Computer> getAll(int offset, int numberOfElementsPerPage) throws WrongPageParameterException {
		if ((offset < 0) || (numberOfElementsPerPage <= 0))
			throw new WrongPageParameterException();
		
		return computerDao.fetchAll(offset, numberOfElementsPerPage);
	}
	
	public Computer getById(int id) {
		return computerDao.fetchOne(id);
	}
	
	public int create(Computer computer) throws DiscontinuationPriorToIntroductionExpection {
		LocalDate introduced = computer.getIntroduced();
		LocalDate discontinued = computer.getDiscontinued();
		
		if (introduced.isAfter(discontinued))
			throw new DiscontinuationPriorToIntroductionExpection();
		
		return computerDao.add(computer);
	}
	
	public void update(Computer computer) throws DiscontinuationPriorToIntroductionExpection {
		LocalDate introduced = computer.getIntroduced();
		LocalDate discontinued = computer.getDiscontinued();
		
		if (introduced.isAfter(discontinued))
			throw new DiscontinuationPriorToIntroductionExpection();
		
		computerDao.update(computer);
	}
	
	public void delete(int id) {
		computerDao.delete(id);
	}

}
