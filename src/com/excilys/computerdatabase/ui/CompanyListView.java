package com.excilys.computerdatabase.ui;

import java.util.ArrayList;

import com.excilys.computerdatabase.model.pojo.Company;

public class CompanyListView extends AbstractView {

	private ArrayList<Company> companyList;
	
	public CompanyListView(Viewer viewer) {
		super(viewer);
		
		companyList = new ArrayList<>();
	}
	
	public void display() {
		System.out.println("\nComplete list of all the companies\n");
		
		//companyList = companyService.getAll();
		
		if (companyList.size() == 0) {
			System.out.println("There is currently no company");
		} else {
			System.out.println("ID\tNAME");
			System.out.println("-----------------------");
			
			for (Company company : companyList) {
				System.out.println(company.getId() + "\t" + company.getName());
			}
		}
		
		System.out.println();
		
		System.out.println("Press Enter to return to main menu...");
		scanner.nextLine();
		
		viewer.setView(new MenuView(viewer));
	}
	
}
