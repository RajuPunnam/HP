package com.inventory.utill;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DOIDateUtill {
	public static void main(String[] args) {
		List<String> dates=new ArrayList<String>();
		dates.add("09-01-2015");
		dates.add("10-01-2015");
		dates.add("11-01-2015");
		
	System.out.println(dates);
	for(String date:dates){
		System.out.println("selected date       :"+date);
		
	}
	DOIDateUtill ut=new DOIDateUtill();
	String date=ut.getNearestSKUMatchedDate("MM-dd-yyyy","1/2/2015",dates);
	System.out.println("=== selected date       :"+date);
	}
	public  String getNearestSKUMatchedDate(String dateFormat,String doiStrDate,List<String> skuMatchedDates){
		if(skuMatchedDates != null && !skuMatchedDates.isEmpty()){			
			Collections.sort(skuMatchedDates,new SkuDatesDescendingComp());
			DateFormat format=new SimpleDateFormat(dateFormat);
			try {
				//System.out.println(":    pick SKU matched date    :=====:  DOI Date     :"+doiStrDate);
				String doidatetrim=doiStrDate.trim();
				String doidatereplace=doidatetrim.replaceAll("['/','_','.', ]","-");				
				//System.out.println("replaced date   :"+doidatereplace);
				
				Date doiDate=format.parse(doidatereplace);			
		for(String skuStrDate:skuMatchedDates){
			String skuStrDatetrim=skuStrDate.trim();
			String skuStrDatereplace=skuStrDatetrim.replaceAll("['/','_','.', ]","-");	
			Date skuDate=format.parse(skuStrDatereplace);
			int index = skuMatchedDates.indexOf(skuStrDate);
			if(doiDate.equals(skuDate)){
				return skuStrDate;
			}
			else if(doiDate.after(skuDate)){					 
						if ((index - 1) >= 0) {
							String skuFutureStrDate = skuMatchedDates
									.get((index - 1));
							Date skuFutureDate = format.parse(skuFutureStrDate);
							int pastDiffDays = getDifferenceDays(doiDate,
									skuDate);
							int futurDiffDays = getDifferenceDays(
									skuFutureDate, doiDate);
							if (pastDiffDays == futurDiffDays) {
								return skuFutureStrDate;
							} else if (pastDiffDays < futurDiffDays) {

								return skuStrDate;
							} else {
								return skuFutureStrDate;
							}
						} //
						else {
							return skuStrDate;
						}//(index - 1) >= 0
			
		}//doiDate.after(skuDate)
			else if(index==(skuMatchedDates.size()-1)){
				
				return skuStrDate;
			}	
				
		}//for loop
		
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}		
		return "";
	}
	
	private  int getDifferenceDays(Date date1,Date date2){		
			long diffenceTime=(date1.getTime()-date2.getTime());
			int differencesDays=(int)(diffenceTime / (24 * 60 * 60 * 1000));		
		return differencesDays;
	}
	
	public Map<String,Integer> getPOrecievedDaysDifferencefromDOIDate(String dateFormat,String doiIODate,List<String> partPORecievedDates){
		
		
		if(partPORecievedDates != null && !partPORecievedDates.isEmpty()){			
			Collections.sort(partPORecievedDates,new SkuDatesDescendingComp());
			DateFormat format=new SimpleDateFormat(dateFormat);
			try {
				//System.out.println(":    pick SKU matched date    :=====:  DOI Date     :"+doiStrDate);
				String doidatetrim=doiIODate.trim();
				String doidatereplace=doidatetrim.replaceAll("['/','_','.', ]","-");				
				Date doiDate=format.parse(doidatereplace);			
		for(String porecievedStrDate:partPORecievedDates){
			String poStrDatetrim=porecievedStrDate.trim();
			String poStrDatereplace=poStrDatetrim.replaceAll("['/','_','.', ]","-");	
			Date poDate=format.parse(poStrDatereplace);
			int index = partPORecievedDates.indexOf(porecievedStrDate);
			if(doiDate.equals(poDate)){
				Integer diffdays=1;
				Map<String,Integer> map=new HashMap<String,Integer>();
				map.put(porecievedStrDate,diffdays);
				return map;
			}
			else if(doiDate.after(poDate)){	
				Integer diffdays=getDifferenceDays(doiDate,poDate);
				Map<String,Integer> map=new HashMap<String,Integer>();
				map.put(porecievedStrDate,diffdays);
				return map;
			
		}//doiDate.after(skuDate)				
		}//for loop
		
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}		
		return null;
		
	
	}
}
