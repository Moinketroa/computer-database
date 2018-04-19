package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.service.CompanyService;
import com.excilys.computerdatabase.ui.page.Page;

public class CompanyListView extends AbstractView {

	private Page<Company> companyList;
	private CompanyService companyService;
	
	public CompanyListView(Viewer viewer) {
		super(viewer);
		
		companyService = CompanyService.INSTANCE;
		
		companyList = new Page<Company>(companyService.getAll());
		
	}
	
	public void display() {
		System.out.println("\nComplete list of all the companies\n");
		
		if (companyList.isEmpty()) {
			System.out.println("There is currently no company");
		} else {
			System.out.println("ID\tNAME");
			System.out.println("-----------------------");
			
			for (Company company : companyList.currentPage()) {
				System.out.println(company.getId() + "\t" + company.getName());
			}
		}
		
		System.out.println();
		
		System.out.println("Press Enter to return to main menu...");
		scanner.nextLine();
		
		viewer.setView(new MenuView(viewer));
	}
	
}
