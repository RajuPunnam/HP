package com.inventory.utill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.mongo.mysql.service.DataTransferService;

public class DateUtil {

	private static SimpleDateFormat formatter;
	private static Logger LOG=Logger.getLogger(DataTransferService.class);
	public static Date stringToDate(String date,String formate) throws ParseException{
		formatter=new SimpleDateFormat(formate);
		return formatter.parse(date);
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
	
	//-----------------method to return String date in MM-dd-yyyy formate for any input formate.
	public static String getRequiredDateFornate(String stringDate){
		String dateForamate[]={"MM/dd/yyyy","yyyy/MM/dd"};
		String finalDate = null;
		  for(String df:dateForamate){
		  try {
			  SimpleDateFormat sf=new SimpleDateFormat(df);
			  sf.setLenient(false);
			  Date date= sf.parse(stringDate.replaceAll("-|\\.", "/"));
			  SimpleDateFormat nsdf=new SimpleDateFormat("MM-dd-yyyy");
			  finalDate=nsdf.format(date);
			  break;
		    } catch (ParseException e) {
			continue;
		   }  
		  }
		return finalDate;
		
	}
	
	
	//------------method for get date of any formate.
	public static Date getDateForAnyFormate(String str){
		 String dateForamate[]={"MM/dd/yyyy","yyyy/MM/dd"};
		 Date date = null;
		  for(String df:dateForamate){
		  try {
			  SimpleDateFormat sf=new SimpleDateFormat(df);
			  sf.setLenient(false);
			  date= sf.parse(str.replaceAll("-|\\.", "/"));
			  break;
		    } catch (ParseException e) {
			continue;
		   }  
		  }
		return date;
	}
	
	
	//-----------send diff dates------------
	public static Map<String,String> getOrig_Comm_Dates(List<String> list) throws Exception{

		String dateFormateArray[]={"MM/dd/yyyy","yyyy/MM/dd"};
		Set<String> set=new HashSet<String>();
	  
		Map<String,String> map=new HashMap<String,String>();
		
		SimpleDateFormat finalDateF=new SimpleDateFormat("MM-dd-yyyy");
		
		List<String> finalDatesList=new ArrayList<String>();
		String ndate = null;
		for(String origDate:list){
			
			for(String df:dateFormateArray){
			try{
			ndate=origDate.replaceAll("-|\\.", "/");
			SimpleDateFormat sf=new SimpleDateFormat(df);
			sf.setLenient(false);
			Date date1=sf.parse(ndate);
			map.put( finalDateF.format(date1),origDate);
				if(set.contains(origDate)){
					set.remove(origDate);}
				break;
			}catch(Exception e){
				set.add(origDate);
				continue;
			}
		  }
		}//dates ending loop.................	
		if(set.size()>0){
		LOG.info(set.size()+"----------------------Final set which contains diff formates Dateutil class ---------"+new ArrayList<String>(set));
		throw new Exception("--Different formate date came in Migration Dateutil class  please solve that--------in---------");
		}
		return map;
	
	}
	
	//-----------
	public static List<String> getOrig_Comm_Date(List<String> list) throws Exception{

		String dateFormateArray[]={"MM/dd/yyyy","yyyy/MM/dd"};
		Set<String> set=new HashSet<String>();
	  
		List<String> map=new ArrayList<String>();
		
		SimpleDateFormat finalDateF=new SimpleDateFormat("MM-dd-yyyy");
		
		List<String> finalDatesList=new ArrayList<String>();
		String ndate = null;
		for(String origDate:list){
			
			for(String df:dateFormateArray){
			try{
			ndate=origDate.replaceAll("-|\\.", "/");
			SimpleDateFormat sf=new SimpleDateFormat(df);
			sf.setLenient(false);
			Date date1=sf.parse(ndate);
			map.add( finalDateF.format(date1));
				if(set.contains(origDate)){
					set.remove(origDate);}
				break;
			}catch(Exception e){
				set.add(origDate);
				continue;
			}
		  }
		}//dates ending loop.................	
		if(set.size()>0){
		LOG.info(set.size()+"----------------------Final set which contains diff formates Dateutil class ---------"+new ArrayList<String>(set));
		throw new Exception("--Different formate date came in Migration Dateutil class  please solve that--------in---------");
		}
		return map;
	
	}
}
