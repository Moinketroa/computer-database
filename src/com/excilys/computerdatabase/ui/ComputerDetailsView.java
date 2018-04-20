package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.mapper.DateMapper;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.ComputerService;

public class ComputerDetailsView extends AbstractView {

	private int computerId;
	private Computer computer;
	private ComputerService computerService;
	
	public ComputerDetailsView(Viewer viewer, int computerId) {
		super(viewer);
		
		this.computerId = computerId;
		computerService = ComputerService.INSTANCE;
		computer = computerService.getById(computerId);
	}
	
	@Override
	public void display() {
		System.out.println("\nDetails for computer #" + computerId + "\n");
		
		if (computer == null) {
			System.out.println("Computer #" + computerId + " not found");
		} else {
			System.out.println();
			String format = "|%1$-7s|%2$-25s|%3$-10s|%4$-12s|%5$-10s|\n";
			
			System.out.println("----------------------------------------------------------------------");
			System.out.format(format, "ID", "NAME", "INTRODUCED", "DISCONTINUED", "COMPANY ID");
			System.out.println("----------------------------------------------------------------------");
			
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
								DateMapper.toDailyFormat(computer.getIntroduced()),
								DateMapper.toDailyFormat(computer.getDiscontinued()),
								companyId);
						
			System.out.println("----------------------------------------------------------------------");
			System.out.println();
		}
		
		System.out.println();
		
		System.out.println("Press Enter to return to main menu...");
		scanner.nextLine();
		
		viewer.setView(new MenuView(viewer));
	}

}
