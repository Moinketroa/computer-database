package com.excilys.computerdatabase.ui;

import java.util.Scanner;

public abstract class AbstractView {

	protected static Scanner scanner = new Scanner(System.in);;
	protected Viewer viewer;
	
	public AbstractView(Viewer viewer) {
		this.viewer = viewer;
	}
	
	public abstract void display();
	
	public static void closeView() {
		scanner.close();
	}
	
}
