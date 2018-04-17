package com.excilys.computerdatabase.ui;

public class Viewer {

	private AbstractView view;
	
	public Viewer() {
		
	}
	
	public void setView(AbstractView view) {
		this.view = view;
		this.view.display();
	}
	
	public void close() {
		AbstractView.closeView();
	}
	
	public static void main(String[] args) {
		Viewer viewer = new Viewer();
		viewer.setView(new MenuView(viewer));
		viewer.close();
	}

}
