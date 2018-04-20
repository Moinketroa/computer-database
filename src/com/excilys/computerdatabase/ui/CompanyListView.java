package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.service.CompanyService;

public class CompanyListView extends AbstractListView<Company> {

	private CompanyService companyService;
	
	public CompanyListView(Viewer viewer) {
		super(viewer);
		
		companyService = CompanyService.INSTANCE;
		
		page = companyService.getAll(0, ENTITIES_PER_PAGE);
	}
	
	public void display() {
		System.out.println("\nComplete list of all the companies\n");
		
		if (page.isEmpty()) {
			System.out.println("There is currently no company");
		} else {
			displayPage();
			readResponse();
		}
	}

	@Override
	protected void displayPage() {
		System.out.println();
		String format = "|%1$-7s|%2$-25s|\n";
		
		System.out.println("-----------------------------------");
		System.out.format(format, "ID", "NAME");
		System.out.println("-----------------------------------");
		
		for (Company company : page.result()) {
			String companyName = company.getName();
			
			if (companyName.length() >= 25)
				companyName = companyName.substring(0, 25);
			
			System.out.format(format, company.getId(), companyName);
		}
		
		System.out.println("-----------------------------------");
		System.out.println();
	}

	@Override
	protected void previousPage() {
		page = companyService.getAll(page.getPreviousPageOffset(), ENTITIES_PER_PAGE);
	}

	@Override
	protected void nextPage() {
		page = companyService.getAll(page.getNextPageOffset(), ENTITIES_PER_PAGE);
	}
	
}
