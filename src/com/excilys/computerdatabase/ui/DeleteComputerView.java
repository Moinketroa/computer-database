package com.excilys.computerdatabase.ui;

import java.sql.Timestamp;

import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

public class DeleteComputerView extends AbstractView {
	
	private Computer computer;
	private int computerId;
	
	public DeleteComputerView(Viewer viewer, int id) {
		super(viewer);
		
		this.computerId = id;
		//computer = computerService.getById(computerId);
		
		computer = null;
		
		/*
		computer = new Computer("Test");
		computer.setIntroduced(new Timestamp(1234567));
		computer.setDiscontinued(new Timestamp(23454567));
		Company company = new Company("TestCompany");
		company.setId(1234);
		computer.setCompany(company);
		*/
	}

	@Override
	public void display() {
		System.out.println("\nDeleting the computer #" + computerId + "\n");
		
		if (computer == null) {
			System.out.println("Computer #" + computerId + " not found");
		} else {
			System.out.println("Are you sure you want to delete the computer #" + computerId + " named \"" + computer.getName() + "\"");
			System.out.println("(y/n)?");
			
			readResponse();
		}
		
		System.out.println();
		
		System.out.println("Press Enter to return to main menu...");
		scanner.nextLine();
		
		viewer.setView(new MenuView(viewer));
	}

	private void readResponse() {
		while (true) {
			String response = scanner.nextLine();
		
			if (response.matches("[yn]")) {
				if (response.equals("y")) {
					//ComputerService.delete(computer);
					System.out.println("Computer #" + computerId + " deleted !");
					return;
				} else {
					System.out.println("Deleting canceled !");
					return;
				}
			} else {
				System.out.println("Please enter \"y\" (to confirm deleting the computer) or \"n\" (to cancel)");
			}
		}
	}
}
