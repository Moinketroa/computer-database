package com.excilys.computerdatabase.ui;

import java.util.Scanner;

public abstract class AbstractView {

	protected Scanner scanner;
	
	public AbstractView() {
		scanner = new Scanner(System.in);
	}
	
}
