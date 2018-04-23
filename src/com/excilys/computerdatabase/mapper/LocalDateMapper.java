package com.excilys.computerdatabase.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * 
 * The class helps building a {@link LocalDate} from specific parameters or helps building other objects from a {@link LocalDate}
 * 
 * @author jmdebicki
 *
 */
public class LocalDateMapper {
	
	private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	/**
	 * Determines if a {@link String} representing a date is in the wanted format which is "dd/mm/yyyy"
	 * 
	 * @param date the date to be verified
	 * @return true if String matches the wanted format "dd/mm/yyyy"
	 */
	public static boolean isValidFormat(String date) {
		return date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}");
	}
	
	/**
	 * Transforms a {@link String} in a valid format to its {@link LocalDate} equivalent
	 * 
	 * @param date a date in the "dd/mm/yyyy" format
	 * @return A {@link LocalDate} representing the date given in parameter
	 */
	public static LocalDate fromString(String date) {
		return LocalDate.parse(date, dateTimeFormatter);
	}
	
	/**
	 * Transform a {@link LocalDate} to its {@link String} equivalent in the "dd/mm/yyyy" format
	 * 
	 * @param date A {@link LocalDate} to be transformed to a {@link String}
	 * @return A {@link String} representing the {@link LocalDate} given in parameter
	 */
	public static String toFormattedString(LocalDate date) {
		return date == null ? "null" : date.format(dateTimeFormatter);
	}
}
