package com.excilys.computerdatabase.ui.page;

import java.util.List;

public class Page<E> {

	private static final int ENTITIES_PER_PAGE = 5;
	
	private List<E> elements;
	private int currentPage;

	public Page(List<E> elements) {
		this.elements = elements;
		this.currentPage = 0;
	}
	
	public boolean isNextPageAvailable() {
		return (elements.size() > (currentPage + 1) * ENTITIES_PER_PAGE);
	}
	
	public boolean isPreviousPageAvailable() {
		return currentPage != 0;
	}
	
	public boolean isEmpty() {
		return elements.isEmpty();
	}
	
	public List<E> currentPage() {
		return elements.subList(currentPage * ENTITIES_PER_PAGE, (currentPage + 1) * ENTITIES_PER_PAGE);
	}
	
	public List<E> next() {
		currentPage++;
		return currentPage();
	}
	
	public List<E>previous() {
		currentPage--;
		return currentPage();
	}
}
