package com.io.utill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.techouts.io.exceptions.HPIOException;

public class DateUtil {

	private static SimpleDateFormat formatter;
	
	public static Date stringToDate(String date,String formate) throws ParseException{
		formatter=new SimpleDateFormat(formate);
		return setTimeToMidnight(formatter.parse(date));
	}
	
	public static String dateToString(Date date,String formate){
		formatter=new SimpleDateFormat(formate);
		return formatter.format(date);
	}
	public static Date setTimeToMidnight(Date date) {
	    Calendar calendar = Calendar.getInstance();
	    calendar.setTime( date );
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);
	    return calendar.getTime();
	}
	public static Date stringToDatev01(String date,String formate) {
		formatter=new SimpleDateFormat(formate);
		try {
			return setTimeToMidnight(formatter.parse(date));
		} catch (ParseException e) {
			throw new HPIOException(e.getMessage());
		}
	}
	public static List<String> getWeeks(java.util.Date d,int month){
		Date currentDate=null;
		Calendar endDate=null;
		Calendar startDate=null;
		List<String> mondayList=null;
		currentDate=Monday.getPreviousMonday(dateToString(d, "MM-dd-yyyy"));
		endDate=Calendar.getInstance();
		endDate.setTime(currentDate);
		startDate=Calendar.getInstance();
		startDate.setTime(currentDate);
		startDate.add(Calendar.MONTH, month);
		mondayList=new ArrayList<String>();
		if(month < 0){
		while(startDate.before(endDate) || startDate.equals(endDate)){
			if(startDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
				mondayList.add(dateToString(startDate.getTime(), "MM/dd/yyyy"));
			}
			startDate.add(Calendar.DATE, 1);
		}
		}
		else if(month > 0){
				while(startDate.after(endDate) || startDate.equals(endDate)){
					if(startDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY){
						mondayList.add(dateToString(startDate.getTime(), "MM/dd/yyyy"));
					}
					startDate.add(Calendar.DATE, -1);
				}	
		}else{
			throw new HPIOException("MONTH SHOULD NOT BE 0");
		}
		return mondayList;
	 }
	
}
