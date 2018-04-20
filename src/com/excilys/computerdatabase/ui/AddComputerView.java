package com.excilys.computerdatabase.ui;

import java.time.LocalDate;

import com.excilys.computerdatabase.mapper.ComputerMapper;
import com.excilys.computerdatabase.mapper.LocalDateMapper;
import com.excilys.computerdatabase.model.pojo.Computer;
import com.excilys.computerdatabase.service.ComputerService;

public class AddComputerView extends AbstractView {

	private String name;
	private LocalDate introduction;
	private LocalDate discontinuation;
	private Integer companyId;
	
	private ComputerService computerService;
	
	public AddComputerView(Viewer viewer) {
		super(viewer);
		
		computerService = ComputerService.INSTANCE;
	}

	@Override
	public void display() {
		System.out.println("\nAdding a computer\n");
		
		readName();
		readIntroductionDate();
		readDiscontinuationDate();
		readCompanyId();
		
		Computer computer = ComputerMapper.fromParameters(name, introduction, discontinuation, companyId);
		
		viewer.setView(new ComputerDetailsView(viewer, computerService.create(computer)));
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
		
		while (!introductionString.equals("") && !LocalDateMapper.isValidFormat(introductionString)) {
			System.out.println("Please enter a valid date format");
			introductionString = scanner.nextLine().trim();
		}

		if (introductionString.equals(""))
			return;
		
		introduction = LocalDateMapper.fromString(introductionString);
	}
	
	private void readDiscontinuationDate() {
		System.out.println("Please enter the discontinuation date (DD/MM/YYYY format !)");
		System.out.println("Default : null");
		
		String discontinuationString = scanner.nextLine().trim();
		
		while (true) {
			while (!discontinuationString.equals("") && !LocalDateMapper.isValidFormat(discontinuationString)) {
				System.out.println("Please enter a valid date format");
				discontinuationString = scanner.nextLine().trim();
			}

			if (discontinuationString.equals(""))
				return;
			
			discontinuation = LocalDateMapper.fromString(discontinuationString);
			
			if (introduction != null)
				if (discontinuation.isAfter(introduction))
					return;
				else {
					System.out.println("Please enter a date greater than the introduction date");
					discontinuationString = scanner.nextLine().trim();
				}
			else
				return;
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
			else
				return;
		}
	}
}
