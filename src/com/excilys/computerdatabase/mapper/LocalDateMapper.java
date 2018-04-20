package com.excilys.computerdatabase.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateMapper {
	
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static boolean isValidFormat(String date) {
		return date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}");
	}
	
	public static LocalDate fromString(String date) {
		return LocalDate.parse(date, dateTimeFormatter);
	}
	
	public static String toFormattedString(LocalDate date) {
		return date == null ? "null" : date.format(dateTimeFormatter);
	}
}
