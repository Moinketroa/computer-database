package com.excilys.computerdatabase.ui;

import java.util.ArrayList;
import java.util.List;

import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.ComputerService;

public class ComputerListView extends AbstractView {

	private List<Computer> computerList;
	private ComputerService computerService;
	
	public ComputerListView(Viewer viewer) {
		super(viewer);
		
		computerList = new ArrayList<>();
		computerService = ComputerService.INSTANCE;
	}

	public void display() {
		System.out.println("\nComplete list of all the computers\n");
		
		computerList = computerService.getAll();
		
		if (computerList.size() == 0) {
			System.out.println("There is currently no computer");	
		} else {
			System.out.println("ID\tNAME\t\tINTRODUCED\tDISCONTINUED\tCOMPANY ID");
			System.out.println("--------------------------------------------------------------------------------------");
			
			for (Computer computer : computerList) {
				System.out.print(computer.getId());
				System.out.print("\t");
				System.out.print(computer.getName());
				System.out.print("\t");
				System.out.print(computer.getIntroduced());
				System.out.print("\t\t");
				System.out.print(computer.getDiscontinued());
				System.out.print("\t\t");
				
				if (computer.getCompany() != null)
					System.out.println(computer.getCompany().getId());
				else
					System.out.println("null");
			}
		}

		System.out.println();
		
		System.out.println("Press Enter to return to main menu...");
		scanner.nextLine();
		
		viewer.setView(new MenuView(viewer));
	}
	
}