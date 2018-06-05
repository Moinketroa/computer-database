package com.excilys.computerdatabase.validator;

public class LocalDateValidator {

  /**
   * Determines if a {@link String} representing a date is in the wanted format
   * which is "dd/mm/yyyy".
   *
   * @param date
   *          the date to be verified
   * @return true if String matches the wanted format "dd/mm/yyyy"
   */
  public static boolean isValidFormat(String date) {
    return date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}");
  }

}
