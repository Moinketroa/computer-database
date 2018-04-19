package com.excilys.computerdatabase.mapper;

import java.sql.Date;

import com.excilys.computerdatabase.model.pojo.Computer;

public class ComputerMapper {

	public static Computer fromParameters(String name, Date introduced, Date discontinued, Integer companyId) {
		Computer computer = new Computer(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		
		if (companyId != null) {
			//TODO: Set company from service
		}
		
		return computer;
	}
	
}
