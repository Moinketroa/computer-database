package com.excilys.computerdatabase.ui;

import java.sql.Timestamp;

import com.excilys.computerdatabase.helper.TimestampHelper;

public class AddComputerView extends AbstractView {

	public AddComputerView(Viewer viewer) {
		super(viewer);
	}

	@Override
	public void display() {
		String name = null, introduction = null, discontinuation = null;
		
		System.out.println("\nAdding a computer\n");
		
		name = readName();
		readIntroductionDate();
		
		System.out.println("Please enter the introduction date (DD/MM/YYYY format !)");
		System.out.println("Default : null");
		System.out.println("Please enter the discontinuation date (DD/MM/YYYY format !)");
		System.out.println("Default : null");
		System.out.println("Please enter the company ID");
		System.out.println("Default : null");
		
		System.out.println();
		
		System.out.println("Press Enter to return to main menu...");
		scanner.nextLine();
		
		viewer.setView(new MenuView(viewer));
	}

	private String readName() {
		System.out.println("Please enter the name of the computer /!\\ MANDATORY /!\\");
		
		String name = scanner.nextLine();
		while (name.trim().equals("")) {
			System.out.println("Please enter a valid name");
			name = scanner.nextLine();
		}
		
		System.out.println(name);
		return name;
	}
	
	private Timestamp readIntroductionDate() {
		System.out.println("Please enter the introduction date (DD/MM/YYYY format !)");
		System.out.println("Default : null");
		
		String introduction = scanner.nextLine();
		System.out.println(TimestampHelper.fromStringToTimestamp(introduction).toString());
		return null;
	}
}
