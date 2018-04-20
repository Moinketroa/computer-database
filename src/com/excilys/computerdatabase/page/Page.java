package com.excilys.computerdatabase.page;

import java.util.List;

public class Page<E> {

	private int nextPageOffset;
	private int currentPageOffset;
	private int totalNumberOfElements;
	private int numberOfElementsPerPage;
	
	private List<E> elements;
	
	public Page(List<E> elements, int currentPageOffset, int numberOfElementsPerPage, int totalNumberOfElements) {
		
		this.elements = elements;
		
		this.currentPageOffset = currentPageOffset;
		this.nextPageOffset = currentPageOffset + numberOfElementsPerPage;
		this.totalNumberOfElements = totalNumberOfElements;
		this.numberOfElementsPerPage = numberOfElementsPerPage;
		
	}
	
	public boolean isPreviousPageAvailable() {
		return currentPageOffset != 0;
	}
	
	public boolean isNextPageAvailable() {
		return (totalNumberOfElements - currentPageOffset) > numberOfElementsPerPage;
	}
	
	public int getNextPageOffset() {
		return nextPageOffset;
	}
	
	public int getPreviousPageOffset() {
		int previousPageOffset = 0;
		
		if (currentPageOffset - numberOfElementsPerPage > 0)
			previousPageOffset = currentPageOffset - numberOfElementsPerPage;
		
		return previousPageOffset;
	}
	
	public boolean isEmpty() {
		return totalNumberOfElements == 0;
	}
	
	public List<E> result() {
		return elements;
	}
}
 