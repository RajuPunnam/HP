package com.techouts.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

	private static SimpleDateFormat formatter;

	public static Date stringToDate(String date, String formate)
			throws ParseException {
		formatter = new SimpleDateFormat(formate);
		return formatter.parse(date);
	}

	public static String dateToString(Date date, String formate) {
		formatter = new SimpleDateFormat(formate);
		return formatter.format(date);
	}

	public static Date setTimeToMidnight(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static String getBrazilTime() {
		SimpleDateFormat sdfAmerica = new SimpleDateFormat(
				"E hh:mm a MMM dd, yyyy");
		sdfAmerica.setTimeZone(TimeZone.getTimeZone("Brazil/West"));
		String sDateInAmerica = sdfAmerica.format(new Date());
		return sDateInAmerica;
	}

}
