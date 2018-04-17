package com.excilys.computerdatabase.model.pojo;

import java.sql.Timestamp;

public class Computer {

	private int id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company company;
	
	public Computer(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder("Computer : ");
		sb.append(id + '\t');
		sb.append(name + '\t');
		
		if (introduced != null)
			sb.append(introduced.toString() + '\t');
		else
			sb.append("null\t");
		
		if (discontinued != null)
			sb.append(discontinued.toString() + '\t');
		else
			sb.append("null\t");
		
		if (company != null)
			sb.append(company.toString() + '\t');
		else
			sb.append("Company : null\t");
		
		return sb.toString();
	}
	
}
