package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.mapper.LocalDateMapper;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.ComputerService;

public class ComputerListView extends AbstractListView<Computer> {

	private ComputerService computerService;
	
	public ComputerListView(Viewer viewer) {
		super(viewer);
		
		computerService = ComputerService.INSTANCE;
		
		page = computerService.getAll(0, ENTITIES_PER_PAGE);
	}

	public void display() {
		System.out.println("\nComplete list of all the computers\n");
			
		if (page.isEmpty()) {
			System.out.println("There is currently no computer");	
		} else {
			displayPage();
			readResponse();
		}
	}

	@Override
	protected void displayPage() {
		System.out.println();
		String format = "|%1$-7s|%2$-25s|%3$-10s|%4$-12s|%5$-10s|\n";
		
		System.out.println("----------------------------------------------------------------------");
		System.out.format(format, "ID", "NAME", "INTRODUCED", "DISCONTINUED", "COMPANY ID");
		System.out.println("----------------------------------------------------------------------");
		
		for (Computer computer : page.result()) {
			String companyId;
			String computerName = computer.getName();
			
			if (computer.getCompany() != null)
				companyId = computer.getCompany().getId() + "";
			else
				companyId = "null";
			
			if (computerName.length() >= 25)
				computerName = computerName.substring(0, 25);
			
			System.out.format(	format, 
								computer.getId(), 
								computerName, 
								LocalDateMapper.toFormattedString(computer.getIntroduced()),
								LocalDateMapper.toFormattedString(computer.getDiscontinued()),
								companyId);
			
		}
		
		System.out.println("----------------------------------------------------------------------");
		System.out.println();
	}

	@Override
	protected void previousPage() {
		page = computerService.getAll(page.getPreviousPageOffset(), ENTITIES_PER_PAGE);
	}

	@Override
	protected void nextPage() {
		page = computerService.getAll(page.getNextPageOffset(), ENTITIES_PER_PAGE);
	}
	
}