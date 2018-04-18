package com.excilys.computerdatabase.mapper;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TimestampMapper {

	public static boolean isValidFormat(String date) {
		return date.matches("[0-9]{2}/[0-9]{2}/[0-9]{4}");
	}
	
	public static Timestamp fromString(String date) {
		String[] dateArrayString = date.split("/");
		int [] dateArrayInt = new int[3];
		
		for (int i = 0; i < dateArrayString.length; i++)
			dateArrayInt[i] = Integer.parseInt(dateArrayString[i]);
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(dateArrayInt[2], (dateArrayInt[1] - 1), dateArrayInt[0]);
		return new Timestamp(calendar.getTime().getTime());
	}
	
	public static String toDailyFormat(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(timestamp.getTime()));
		
		String fillerDay = "", fillerMonth = "";
		
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int month = calendar.get(Calendar.MONTH) + 1;
		int year = calendar.get(Calendar.YEAR);
		
		if (day < 10)
			fillerDay = "0";
		if (month < 10)
			fillerMonth = "0";
		
		return fillerDay + day + "/" + fillerMonth + month + "/" + year;
	}
}
