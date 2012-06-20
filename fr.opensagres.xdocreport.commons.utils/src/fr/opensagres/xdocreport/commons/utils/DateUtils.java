package fr.opensagres.xdocreport.commons.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	public static final String FRENCH_PATTERN = "dd/MM/yyyy";
	public static final String ENGLISH_PATTERN = "MM/dd/yyyy";

	public static Date toDate(String date, String outputPattern)
			throws ParseException {
		DateFormat df = new SimpleDateFormat(outputPattern);
		return df.parse(date);
	}
	
	public static int getDateYear(Date date) {
		if (date == null) {
			return -1;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.YEAR);

	}

	public static int getDateMonth(Date date) {
		if (date == null) {
			return -1;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}
}
