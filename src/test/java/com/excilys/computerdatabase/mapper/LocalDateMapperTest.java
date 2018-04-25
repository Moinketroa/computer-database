package com.excilys.computerdatabase.mapper;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Test;

public class LocalDateMapperTest {

	private String wrongDayDate = "123/11/2012";
	private String wrongMonthDate = "12/113/2012";
	private String wrongYearDate = "12/11/201";
	private String goodDate = "12/11/2012";
	
	private LocalDate twelveOctober2012 = LocalDate.of(2012, 11, 12);

	@Test
	public void testIsValidFormat() {
		assertFalse(LocalDateMapper.isValidFormat(wrongDayDate));
		assertFalse(LocalDateMapper.isValidFormat(wrongMonthDate));
		assertFalse(LocalDateMapper.isValidFormat(wrongYearDate));
		assertTrue(LocalDateMapper.isValidFormat(goodDate));
	}

	@Test
	public void testFromString() {
		LocalDate localDateFromGoodDate = LocalDateMapper.fromString(goodDate);
		
		assertEquals(12, localDateFromGoodDate.getDayOfMonth());
		assertEquals(11, localDateFromGoodDate.getMonthValue());
		assertEquals(2012, localDateFromGoodDate.getYear());
	}

	@Test
	public void testToFormattedString() {
		String stringDateFromTwelveOctober2012 = LocalDateMapper.toFormattedString(twelveOctober2012);
		
		assertEquals("12/11/2012", stringDateFromTwelveOctober2012);
	}

}
