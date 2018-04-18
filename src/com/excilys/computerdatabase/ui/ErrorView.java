package com.excilys.computerdatabase.ui;

public class ErrorView extends AbstractView {

	private Exception e;
	
	public ErrorView(Viewer viewer, Exception e) {
		super(viewer);
		
		this.e = e;
	}

	@Override
	public void display() {
		System.out.println("The error below occured : ");
		System.out.println(e.getMessage());
		
		System.out.println();
		
		System.out.println("Press Enter to return to main menu...");
		scanner.nextLine();
		
		viewer.setView(new MenuView(viewer));
	}

}
