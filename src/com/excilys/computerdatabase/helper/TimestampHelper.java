package com.excilys.computerdatabase.helper;

import java.sql.Timestamp;
import java.util.Calendar;

public class TimestampHelper {

	public static boolean isValidFormat(String date) {
		return date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}");
	}
	
	public static Timestamp fromStringToTimestamp(String date) {
		String[] dateArrayString = date.split("/");
		int [] dateArrayInt = new int[3];
		
		for (int i = 0; i < dateArrayString.length; i++)
			dateArrayInt[i] = Integer.parseInt(dateArrayString[i]);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(dateArrayInt[2], (dateArrayInt[1] - 1), dateArrayInt[0]);
		return new Timestamp(calendar.getTime().getTime());
	}
}
