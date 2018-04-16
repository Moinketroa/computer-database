package com.excilys.computerdatabase.dao;

import java.util.List;

import com.excilys.computerdatabase.model.pojo.Company;

public interface CompanyDao {

	public List<Company> fetchAll();
	public Company fetchOne(int id);
}
