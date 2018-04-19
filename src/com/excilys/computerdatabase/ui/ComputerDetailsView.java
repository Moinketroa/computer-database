package com.excilys.computerdatabase.ui;

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
			System.out.println("ID\tNAME\t\tINTRODUCED\tDISCONTINUED\tCOMPANY ID");
			System.out.println("--------------------------------------------------------------------------------------");
			
			System.out.print(computer.getId());
			System.out.print("\t");
			System.out.print(computer.getName());
			System.out.print("\t");
			System.out.print(computer.getIntroduced());
			System.out.print("\t\t");
			System.out.print(computer.getDiscontinued());
			System.out.print("\t\t");
			
			if (computer.getCompany() != null)
				System.out.print(computer.getCompany().getId());
			else
				System.out.println("null");
		}
		
		System.out.println();
		
		System.out.println("Press Enter to return to main menu...");
		scanner.nextLine();
		
		viewer.setView(new MenuView(viewer));
	}

}
