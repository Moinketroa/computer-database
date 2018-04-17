package com.excilys.computerdatabase.model.pojo;

public class Company {

	private int id;
	private String name;
	
	public Company(String name) {
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
		if (name != null)
			this.name = name;
	}
	
	@Override
	public String toString() {
		return "Company : " + id + '\t' + name;
	}
}