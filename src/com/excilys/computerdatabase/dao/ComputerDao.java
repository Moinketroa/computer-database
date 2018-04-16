package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.model.pojo.Computer;

public interface ComputerDao {

	public void add(Computer computer);
	public void update(Computer computer);
	public void delete(Computer computer);
	public Computer fetchOne(int computerId);
	public List<Computer> fetchAll();
	
}
