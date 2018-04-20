package com.excilys.computerdatabase.ui;

import com.excilys.computerdatabase.page.Page;

public abstract class AbstractListView<E> extends AbstractView {

	protected static final int ENTITIES_PER_PAGE = 5;
	
	protected Page<E> page;
	
	public AbstractListView(Viewer viewer) {
		super(viewer);
	}

	protected abstract void displayPage();
	
	protected abstract void previousPage();
	
	protected abstract void nextPage();
	
	protected void readResponse() {
		while (true) {
			
			System.out.println("What do you want to do ?");
			if (page.isNextPageAvailable())
				System.out.println("Enter \"n\" to display next page");
			if (page.isPreviousPageAvailable())
				System.out.println("Enter \"p\" to display previous page");
			System.out.println("Enter \"q\" to return to main menu");
			
			String response = scanner.nextLine();
		
			if (response.matches("[pnq]")) {
				if (response.equals("p")) {
					if (page.isPreviousPageAvailable()) {
						previousPage();
						displayPage();
					} else {
						System.out.println("Previous page is not available, please enter a valid answer");
					}
				} else if (response.equals("n")){
					if (page.isNextPageAvailable()) {
						nextPage();
						displayPage();
					} else {
						System.out.println("Next page is not available, please enter a valid answer");
					}
				} else if (response.equals("q")) {
					System.out.println("Returning to main menu");
					
					viewer.setView(new MenuView(viewer));
				}
			} else {
				System.out.println("Please enter a valid answer");
			}
		}
	}
}
