package com.excilys.computerdatabase.page;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PageTest {

  private List<Object> list;
  private static final int NUMBER_OF_ELEMENTS = 12;
  private static final int TOTAL_NUMBER_OF_ELEMENTS = 27;

  @Before
  public void setUp() throws Exception {
    list = new ArrayList<>(NUMBER_OF_ELEMENTS);

    for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
      list.add(new Object());
    }
  }

  @After
  public void tearDown() throws Exception {
    list = null;
  }

  @Test
  public void testIsPreviousPageAvailable() {
    Page<Object> pageWhichStartsAt0 = new Page<>(list, 0, NUMBER_OF_ELEMENTS, TOTAL_NUMBER_OF_ELEMENTS);
    Page<Object> pageWhichStartsAtNumberOfElements = new Page<>(list, NUMBER_OF_ELEMENTS, NUMBER_OF_ELEMENTS,
        TOTAL_NUMBER_OF_ELEMENTS);
    Page<Object> pageWhichStartsAt1 = new Page<>(list, 1, NUMBER_OF_ELEMENTS, TOTAL_NUMBER_OF_ELEMENTS);

    assertFalse(pageWhichStartsAt0.isPreviousPageAvailable());
    assertTrue(pageWhichStartsAtNumberOfElements.isPreviousPageAvailable());
    assertTrue(pageWhichStartsAt1.isPreviousPageAvailable());
  }

  @Test
  public void testIsNextPageAvailable() {
    Page<Object> pageWhichStartsAt0 = new Page<>(list, 0, NUMBER_OF_ELEMENTS, TOTAL_NUMBER_OF_ELEMENTS);
    Page<Object> pageWhichStartsAtTotalNumberOfElementsMinusElementsPerPage = new Page<>(list,
        TOTAL_NUMBER_OF_ELEMENTS - NUMBER_OF_ELEMENTS, NUMBER_OF_ELEMENTS, TOTAL_NUMBER_OF_ELEMENTS);
    Page<Object> pageWhichStartsAtTotalNumberOfElementsMinusElementsPerPagePlus1 = new Page<>(list,
        TOTAL_NUMBER_OF_ELEMENTS - (NUMBER_OF_ELEMENTS + 1), NUMBER_OF_ELEMENTS, TOTAL_NUMBER_OF_ELEMENTS);

    assertTrue(pageWhichStartsAt0.isNextPageAvailable());
    assertFalse(pageWhichStartsAtTotalNumberOfElementsMinusElementsPerPage.isNextPageAvailable());
    assertTrue(pageWhichStartsAtTotalNumberOfElementsMinusElementsPerPagePlus1.isNextPageAvailable());
  }

  @Test
  public void testGetNextPageOffset() {
    Page<Object> pageWhichStartsAt0 = new Page<>(list, 0, NUMBER_OF_ELEMENTS, TOTAL_NUMBER_OF_ELEMENTS);

    assertEquals(0 + NUMBER_OF_ELEMENTS, pageWhichStartsAt0.getNextPageOffset());
  }

  @Test
  public void testGetPreviousPageOffset() {
    Page<Object> pageWhichStartsAtNumberOfElements = new Page<>(list, NUMBER_OF_ELEMENTS, NUMBER_OF_ELEMENTS,
        TOTAL_NUMBER_OF_ELEMENTS);
    Page<Object> pageWhichStartsAtNumberOfElementsPlusOne = new Page<>(list, NUMBER_OF_ELEMENTS + 1, NUMBER_OF_ELEMENTS,
        TOTAL_NUMBER_OF_ELEMENTS);
    Page<Object> pageWhichStartsAt1 = new Page<>(list, 1, NUMBER_OF_ELEMENTS, TOTAL_NUMBER_OF_ELEMENTS);

    assertEquals(0, pageWhichStartsAtNumberOfElements.getPreviousPageOffset());
    assertEquals(1, pageWhichStartsAtNumberOfElementsPlusOne.getPreviousPageOffset());
    assertEquals(0, pageWhichStartsAt1.getPreviousPageOffset());
  }

}
