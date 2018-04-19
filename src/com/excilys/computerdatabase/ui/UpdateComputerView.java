package com.excilys.computerdatabase.ui;

import java.sql.Date;
import java.sql.Timestamp;

import com.excilys.computerdatabase.mapper.DateMapper;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;

public class UpdateComputerView extends AbstractView {

	private int computerId;
	private String name;
	private Date introduction;
	private Date discontinuation;
	private Integer companyId;
	
	private Computer computer;
	
	public UpdateComputerView(Viewer viewer, int id) {
		super(viewer);

		computerId = id;
		
		//computer = null;
		
		computer = new Computer("Test");
		computer.setIntroduced(new Date(1234567));
		computer.setDiscontinued(new Date(23454567));
		Company company = new Company("TestCompany");
		company.setId(1234);
		computer.setCompany(company);
		
		//computer = ComputerService.getById(id);
		
		if (computer != null) {
			name = computer.getName();
			introduction = computer.getIntroduced();
			discontinuation = computer.getDiscontinued();
			companyId = computer.getCompany().getId();
		}
	}

	@Override
	public void display() {
		System.out.println("\nUpdating the computer #" + computerId + "\n");
		
		if (computer == null) {
			System.out.println("Computer #" + computerId + " not found");
			
			System.out.println();
			
			System.out.println("Press Enter to return to main menu...");
			scanner.nextLine();
			
			viewer.setView(new MenuView(viewer));
		} else {
			readName();
			readIntroductionDate();
			readDiscontinuationDate();
			readCompanyId();
			
			//ComputerService.update(computer);
			//viewer.setView(new ComputerDetailsView(viewer, computer.getId()));
			
			System.out.println(computerId + "\t" + name + "\t" + introduction + "\t" + discontinuation + "\t" + companyId);
			
			System.out.println();
			
			System.out.println("Press Enter to return to main menu...");
			scanner.nextLine();
			
			viewer.setView(new MenuView(viewer));
		}
	}
	
	private void readName() {
		System.out.println("Please enter the name of the computer");
		System.out.println("Default : " + name);
		
		String nameTemp = scanner.nextLine().trim();
		if (!nameTemp.equals(""))
			name = nameTemp;
	}

	private void readIntroductionDate() {
		System.out.println("Please enter the introduction date (DD/MM/YYYY format !)");
		System.out.println("Default : null\tCurrent : " + DateMapper.toDailyFormat(introduction));
		
		String introductionString = scanner.nextLine().trim();
		
		while (true) {
			if (introductionString.equals("")) {
				introduction = null;
				return;
			} else if (!DateMapper.isValidFormat(introductionString)) {
				System.out.println("Please enter a valid date format");
				introductionString = scanner.nextLine().trim();
			} else {
				introduction = DateMapper.fromString(introductionString);
				return;
			}
		}
	}
	
	private void readDiscontinuationDate() {
		System.out.println("Please enter the discontinuation date (DD/MM/YYYY format !)");
		System.out.println("Default : null\tCurrent : " + DateMapper.toDailyFormat(discontinuation));
		
		String discontinuationString = scanner.nextLine().trim();
		
		while (true) {
			if (discontinuationString.equals("") || DateMapper.isValidFormat(discontinuationString)) {
				if (discontinuationString.equals("")) {
					discontinuation = null;
					return;
				}
					
				discontinuation = DateMapper.fromString(discontinuationString);
			
				if (introduction != null)
					if (discontinuation.after(introduction))
						return;
					else {
						System.out.println("Please enter a date greater than the introduction date");
						discontinuationString = scanner.nextLine().trim();
					}
				else
					return;
			} else {
				System.out.println("Please enter a valid date format");
				discontinuationString = scanner.nextLine().trim();
			}
		}
	}

	private void readCompanyId() {
		System.out.println("Please enter the company ID");
		System.out.println("Default : null\tCurrent : " + companyId);
		
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
			else {
				companyId = null;
				return;
			}
		}
	}
}
