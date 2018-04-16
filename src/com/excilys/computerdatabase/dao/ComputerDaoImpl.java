package com.excilys.computerdatabase.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.pojo.Computer;

public class ComputerDaoImpl extends AbstractDao implements ComputerDao {

	public ComputerDaoImpl(DaoFactory daoFactory) {
		super(daoFactory);
	}

	@Override
	public void add(Computer computer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Computer computer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Computer computer) {
		// TODO Auto-generated method stub

	}

	@Override
	public Computer fetchOne(int computerId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Computer> fetchAll() {
		List<Computer> computers = new ArrayList<>();
		Connection connexion = null;
        Statement statement = null;
        ResultSet result = null;
        
        try {
            connexion = daoFactory.getConnection();
            statement = connexion.createStatement();
            result = statement.executeQuery("SELECT * FROM computer;");
            
            while (result.next()) {
            	int id = result.getInt("id");
            	String name = result.getString("name");
            	Date introduced = null;
            	Date discontinued = null;
            	int company_id = result.getInt("company_id");
            	
            	try {
            		introduced = result.getDate("introduced");
            		discontinued = result.getDate("discontinued");
            	} catch (Exception e) {
            		
				}
            	
            	Computer computer = new Computer(name);
            	computer.setId(id);
            	computer.setIntroduced(introduced);
            	computer.setDiscontinued(discontinued);
            	//TODO : set company
            	
            	computers.add(computer);
            }
        } catch (SQLException e) {
			e.printStackTrace();
		}

        return computers;
	}

}
