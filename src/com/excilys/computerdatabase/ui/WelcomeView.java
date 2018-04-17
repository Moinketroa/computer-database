package com.excilys.computerdatabase.ui;

public class WelcomeView extends AbstractView {

	public WelcomeView(Viewer viewer) {
		super(viewer);
	}

	@Override
	public void display() {
		System.out.println("Welcome to Computer Database \n");
		
		viewer.setView(new MenuView(viewer));
	}

}
