package com.excilys.computerdatabase.page;

import java.util.List;

/**
 * The class encapsulates a List in order to represent a relatively small number
 * of elements existing in a larger collection and gives information about the
 * page.
 *
 * @author jmdebicki
 *
 * @param <E>
 *          the type of the elements to be contained in the page
 */
public class Page<E> {

  private int nextPageOffset;
  private int currentPageOffset;
  private int totalNumberOfElements;
  private int numberOfElementsPerPage;

  private List<E> elements;

  /**
   * Initialize a new Page with a small list of elements and informations about
   * the type of the page representing the bigger collection.
   *
   * @param elements
   *          list of the elements to be represented in the page
   * @param currentPageOffset
   *          the index from where the page begins in the whole collection
   * @param numberOfElementsPerPage
   *          how many elements would fit at maximum in the page
   * @param totalNumberOfElements
   *          the total number of elements existing in the whole collection
   */
  public Page(List<E> elements, int currentPageOffset, int numberOfElementsPerPage, int totalNumberOfElements) {

    this.elements = elements;

    this.currentPageOffset = currentPageOffset;
    this.nextPageOffset = currentPageOffset + numberOfElementsPerPage;
    this.totalNumberOfElements = totalNumberOfElements;
    this.numberOfElementsPerPage = numberOfElementsPerPage;

  }

  /**
   * Determines if a previous page of the same type is browseable.
   *
   * @return true if the page is not the first page of the same type representing
   *         the same collection
   */
  public boolean isPreviousPageAvailable() {
    return currentPageOffset != 0;
  }

  /**
   * Determines if a next page of the same type is browseable.
   *
   * @return true if the page is not the last page of the same type representing
   *         the same collection
   */
  public boolean isNextPageAvailable() {
    return (totalNumberOfElements - currentPageOffset) > numberOfElementsPerPage;
  }

  /**
   * Returns the index from where the page following the current begins.
   *
   * @return the next page offset
   */
  public int getNextPageOffset() {
    return nextPageOffset;
  }

  /**
   * Returns the index from where the page preceding the current begin.
   *
   * @return the previous page offset
   */
  public int getPreviousPageOffset() {
    int previousPageOffset = 0;

    if ((currentPageOffset - numberOfElementsPerPage) > 0) {
      previousPageOffset = currentPageOffset - numberOfElementsPerPage;
    }

    return previousPageOffset;
  }

  /**
   * Determines if the page is empty and thus if the entire collection is empty.
   *
   * @return true if the total number of elements is zero
   */
  public boolean isEmpty() {
    return totalNumberOfElements == 0;
  }

  public int getTotalNumberOfElements() {
    return totalNumberOfElements;
  }

  public int getNumberOfElementsPerPage() {
    return numberOfElementsPerPage;
  }

  /**
   * Returns the portion of the entire collection that the current page
   * encapsulates.
   *
   * @return the (number of elements per page) elements available in the current
   *         page
   */
  public List<E> result() {
    return elements;
  }
}
