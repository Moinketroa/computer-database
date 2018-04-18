package com.excilys.computerdatabase.ui;

import java.sql.Timestamp;
import java.util.InputMismatchException;

import com.excilys.computerdatabase.mapper.TimestampMapper;

public class AddComputerView extends AbstractView {

	private String name;
	private Timestamp introduction;
	private Timestamp discontinuation;
	private Integer companyId;
	
	public AddComputerView(Viewer viewer) {
		super(viewer);
	}

	@Override
	public void display() {
		System.out.println("\nAdding a computer\n");
		
		readName();
		readIntroductionDate();
		readDiscontinuationDate();
		readCompanyId();
		
		//Computer computer = ComputerService.add(ComputerMapper.fromParameters(name, introduction, discontinuation, companyId));
		
		//viewer.setView(new ComputerDetailsView(viewer, computer.getId()));
		
		System.out.println(name + "\t" + introduction + "\t" + discontinuation + "\t" + companyId);
		
		System.out.println();
		
		System.out.println("Press Enter to return to main menu...");
		scanner.nextLine();
		
		viewer.setView(new MenuView(viewer));
	}

	private void readName() {
		System.out.println("Please enter the name of the computer /!\\ MANDATORY /!\\");
		
		name = scanner.nextLine().trim();
		while (name.equals("")) {
			System.out.println("Please enter a valid name");
			name = scanner.nextLine().trim();
		}
	}
	
	private void readIntroductionDate() {
		System.out.println("Please enter the introduction date (DD/MM/YYYY format !)");
		System.out.println("Default : null");
		
		String introductionString = scanner.nextLine().trim();
		
		while (!introductionString.equals("") && !TimestampMapper.isValidFormat(introductionString)) {
			System.out.println("Please enter a valid date format");
			introductionString = scanner.nextLine().trim();
		}

		introduction = TimestampMapper.fromString(introductionString);
	}
	
	private void readDiscontinuationDate() {
		System.out.println("Please enter the discontinuation date (DD/MM/YYYY format !)");
		System.out.println("Default : null");
		
		String discontinuationString = scanner.nextLine().trim();
		
		while (true) {
			while (!discontinuationString.equals("") && !TimestampMapper.isValidFormat(discontinuationString)) {
				System.out.println("Please enter a valid date format");
				discontinuationString = scanner.nextLine().trim();
			}

			discontinuation = TimestampMapper.fromString(discontinuationString);
			
			if (discontinuation.after(introduction))
				return;
			else {
				System.out.println("Please enter a date greater than the introduction date");
				discontinuationString = scanner.nextLine().trim();
			}
		}
	}
	
	private void readCompanyId() {
		System.out.println("Please enter the company ID");
		System.out.println("Default : null");
		
		String companyIdString = scanner.nextLine().trim();
		
		while (true) {
			if (!companyIdString.equals(""))
				try {
					companyId = Integer.parseInt(companyIdString);
					return;
				} catch (Exception e) {
					System.out.println("Please enter a valid number");
					companyIdString = scanner.nextLine().trim();
				}
		}
	}
}
