package com.excilys.computerdatabase.ui;

import java.util.InputMismatchException;

public class MenuView extends AbstractView {
	
	public MenuView(Viewer viewer) {
		super(viewer);
	}

	@Override
	public void display() {
		System.out.println("Please select an option : ");
		System.out.println("\t1 : Display all computers");
		System.out.println("\t2 : Display all companies");
		System.out.println("\t3 : Show computer details");
		System.out.println("\t4 : Add a computer");
		System.out.println("\t5 : Update a computer");
		System.out.println("\t6 : Remove a computer");
		System.out.println("\t7 : Exit");
		
		try {
			int choosenOption = scanner.nextInt();
			scanner.nextLine();
				
			switch(choosenOption) {
			case 1:
				this.viewer.setView(new ComputerListView(viewer));
				break;
			case 2:
				this.viewer.setView(new CompanyListView(viewer));
				break;
			case 3:
				computerDetails();
				break;
			case 4:
				this.viewer.setView(new AddComputerView(viewer));
				break;
			case 5:
				updateComputer();
				break;
			case 6:	
				removeComputer();
				break;
			case 7:
				viewer.setView(new EndView(viewer));
				break;
			default:
				tryAgain();
				break;
			}
		} catch (InputMismatchException e) {
			scanner.nextLine();
			tryAgain();
		}
	}
	
	private void computerDetails() {
		int computerId = readComputerId();
		
		viewer.setView(new ComputerDetailsView(viewer, computerId));
	}
	
	private void updateComputer() {
		int computerId = readComputerId();
		
		viewer.setView(new UpdateComputerView(viewer, computerId));
	}
	
	private void removeComputer() {
		int computerId = readComputerId();
		
		viewer.setView(new DeleteComputerView(viewer, computerId));
	}
	
	private int readComputerId() {
		int computerId;
		
		while (true) {
			try {
				System.out.println("Please enter the choosen computer's ID");
		
				computerId = scanner.nextInt();
				scanner.nextLine();
				break;
			} catch (InputMismatchException e) {
				scanner.nextLine();
				System.out.println("Please enter a valid number");
			}
		}
		
		return computerId;
	}
	
	private void tryAgain() {
		System.out.println("Please enter a number from 1 to 7\n");
		viewer.setView(this);
	}

}
