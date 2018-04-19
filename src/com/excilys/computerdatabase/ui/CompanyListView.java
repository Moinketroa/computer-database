package com.excilys.computerdatabase.ui;

import java.util.List;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.service.CompanyService;

public class CompanyListView extends AbstractView {

	private List<Company> companyList;
	private CompanyService companyService;
	
	public CompanyListView(Viewer viewer) {
		super(viewer);
		
		companyService = CompanyService.INSTANCE;
		
		companyList = companyService.getAll();
		
	}
	
	public void display() {
		System.out.println("\nComplete list of all the companies\n");
		
		if (companyList.isEmpty()) {
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
