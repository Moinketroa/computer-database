package com.excilys.computerdatabase.ui;

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
			addComputer();
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
	}
	
	private void computerDetails() {
		
	}
	
	private void addComputer() {
		
	}
	
	private void updateComputer() {
		
	}
	
	private void removeComputer() {
		
	}
	
	private int readComputerId() {
		System.out.println("Please enter the choosen computer's ID");
		return scanner.nextInt();
	}
	
	private void tryAgain() {
		System.out.println("Please enter a number from 1 to 7\n");
		viewer.setView(this);
	}

}