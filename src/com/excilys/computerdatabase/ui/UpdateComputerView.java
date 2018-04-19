package com.excilys.computerdatabase.ui;

import java.sql.Date;

import com.excilys.computerdatabase.mapper.DateMapper;
import com.excilys.computerdatabase.model.pojo.Company;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.ComputerService;

public class UpdateComputerView extends AbstractView {

	private int computerId;
	private String name;
	private Date introduction;
	private Date discontinuation;
	private Integer companyId;
	
	private Computer computer;
	private ComputerService computerService;
	
	public UpdateComputerView(Viewer viewer, int id) {
		super(viewer);

		computerId = id;
		computerService = ComputerService.INSTANCE;
		computer = computerService.getById(id);
		
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
			
			computerService.update(computer);
			viewer.setView(new ComputerDetailsView(viewer, computer.getId()));
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
		if (introduction != null)
			System.out.println("Default : null\tCurrent : " + DateMapper.toDailyFormat(introduction));
		else
			System.out.println("Default : null\tCurrent : null");
		
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
		if (discontinuation != null)
			System.out.println("Default : null\tCurrent : " + DateMapper.toDailyFormat(discontinuation));
		else
			System.out.println("Default : null\tCurrent : null");
		
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
