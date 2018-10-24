package com.io.utill;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Monday {
	
	static String[] result;
	static String year; 
	static DateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
	
	public static Date getPreviousMonday(String date){
		date=date.replace("/", "-");
		result=date.split("-");
		if(date.length()!=10)
		{
			result=date.split("-");
			    if(result[0].length()!=2)
				result[0]="0"+result[0];
			    if(result[1].length()!=2)
				result[1]="0"+result[1];
			    if(result[2].length()!=4)
			    	year="20"+result[2];
			    date=(result[0]+"/"+result[1]+"/"+result[2]);
		}
		year=result[2];
		if(year.length()<4){
			year="20"+year;
		}
		Calendar date1 = Calendar.getInstance();
		date1.set(Integer.valueOf(year), Integer.valueOf(result[0])-1, Integer.valueOf(result[1]));

		while (date1.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
		    date1.add(Calendar.DATE, -1);
		}
		
		return setTimeToMidnight(date1.getTime());
	}
	public static Date getNextMonday(String date){
		date=date.replace("/", "-");
		result=date.split("-");
		
		year=result[2];
		if(year.length()<4){
			year="20"+year;
		}
		Calendar date1 = Calendar.getInstance();
		date1.set(Integer.valueOf(year), Integer.valueOf(result[0])-1, Integer.valueOf(result[1]));

		while (date1.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
		    date1.add(Calendar.DATE, 1);
		}
		return setTimeToMidnight(date1.getTime());
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

}
