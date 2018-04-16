package com.excilys.computerdatabase.ui;

import java.util.ArrayList;

import com.excilys.computerdatabase.model.pojo.Company;

public class CompanyListView extends AbstractView {

	private ArrayList<Company> companyList;
	
	public CompanyListView() {
		companyList = new ArrayList<>();
		
		System.out.println("Complete list of all the companies\n");
		
		display();
		
		System.out.println();
		
		System.out.println("Press Enter to return to main menu...");
		scanner.nextLine();
		scanner.close();
	}
	
	private void display() {
		if (companyList.size() == 0) {
			System.out.println("There is currently no company");
			return;
		}
		
		System.out.println("ID\tNAME");
		System.out.println("-----------------------");
		
		for (Company company : companyList) {
			System.out.println(company.getId() + "\t" + company.getName());
		}
	}
	
}
