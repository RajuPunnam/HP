package com.inventory.utill;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class SkuDatesDescendingComp implements Comparator<String> {
	public int compare(String firstStrDate, String secondStrDate) {
		// TODO Auto-generated method stub
		DateFormat format=new SimpleDateFormat("MM-dd-yyyy");
		String firstStrDatetrim=firstStrDate.trim();
		String firstStrDatereplace=firstStrDatetrim.replaceAll("['/','_','.', ]","-");
		String secondStrDatetrim=secondStrDate.trim();
		String secondStrDatereplace=secondStrDatetrim.replaceAll("['/','_','.', ]","-");
		try {
			Date date1=format.parse(firstStrDatereplace);
		Date date2=format.parse(secondStrDatereplace);			
			return -date1.compareTo(date2);
	
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	
}
