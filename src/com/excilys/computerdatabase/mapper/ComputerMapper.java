package com.excilys.computerdatabase.mapper;

import java.sql.Timestamp;

import com.excilys.computerdatabase.model.pojo.Computer;

public class ComputerMapper {

	public static Computer fromParameters(String name, Timestamp introduced, Timestamp discontinued, Integer companyId) {
		Computer computer = new Computer(name);
		computer.setIntroduced(introduced);
		computer.setDiscontinued(discontinued);
		
		if (companyId != null) {
			//TODO: Set company from service
		}
		
		return computer;
	}
	
}
