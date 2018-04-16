package com.excilys.computerdatabase.ui;

public class MainView extends AbstractView {
	
	public MainView() {
		System.out.println("Welcome to Computer Database \n");
		
		display();
		
		System.out.println("Goodbye ! Have a nice day !");
		scanner.close();
	}

	private void display() {
		while (true) {
			System.out.println("Please select an option : ");
			System.out.println("\t1 : Display all computers");
			System.out.println("\t2 : Display all companies");
			System.out.println("\t3 : Show computer details");
			System.out.println("\t4 : Add a computer");
			System.out.println("\t5 : Update a computer");
			System.out.println("\t6 : Remove a computer");
			System.out.println("\t7 : Exit");
			
			int choosenOption = scanner.nextInt();
			
			switch(choosenOption) {
			case 1:
				computerList();
				break;
			case 2:
				companyList();
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
				return;
			default:
				tryAgain();
			}
		}
	}
	
	private void computerList() {
		
	}
	
	private void companyList() {
		new CompanyListView();
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
		System.out.println("Please enter a number from 1 to 7");
	}
	
	public static void main(String[] args) {
		new MainView();

	}

}
