package com.io.utill;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.io.dao.ForecastDao;
import com.io.pojos.Combined_Price;

@Service
public class PriceUtill {

	@Autowired
	private ForecastDao forecastDao;
	
	private Map<String,List<Combined_Price>> priceMap = null;

	//@PostConstruct
	public void init(){
		
		List<Combined_Price> cList=null;
		priceMap = new HashMap<String,List<Combined_Price>>();
		for(Combined_Price cp:forecastDao.getPriceMasterData()){
			cList=new ArrayList<Combined_Price>();
			String key =cp.getItem();
			if(priceMap.containsKey(key)){
				cList.addAll(priceMap.get(key));
			}
			cList.add(cp);
			priceMap.put(key, cList);
		}
	}
	
	public Double getDateItemPrice(String item, String date) {
		//init(item);
		List<Combined_Price> list=new ArrayList<Combined_Price>();
		List<Date> datesList = new ArrayList<Date>();
		Double price = 0.0;
		if ((item != null && !item.isEmpty())
				&& (date != null && !date.isEmpty())) {
			try {
				if(priceMap.containsKey(item)){
					list.addAll(priceMap.get(item));
				for (Combined_Price bom : list) {
						datesList.add(DateUtil.stringToDate(bom.getDate(),
								"MM-dd-yyyy"));
				}
				Date closerDate = closerDate(
						DateUtil.stringToDate(date, "MM-dd-yyyy"), datesList);
				if (closerDate != null) {
					String closeDate = DateUtil.dateToString(closerDate,
							"MM-dd-yyyy");
					for (Combined_Price bom : list) {
						if (bom.getDate().equals(closeDate))
							price =bom.getPrice();
					}
				}
			}//------------------>executes if map contains item
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return price;

	}
	// method for closer date
		private static Date closerDate(Date originalDate,
				Collection<Date> unsortedDates) {
			List<Date> dateList = new LinkedList<Date>(unsortedDates);
			Collections.sort(dateList);
			Iterator<Date> iterator = dateList.iterator();
			Date previousDate = null;
			while (iterator.hasNext()) {
				Date nextDate = iterator.next();
				if (nextDate.before(originalDate)) {
					previousDate = nextDate;
					continue;
				} else if (nextDate.after(originalDate)) {
					if (previousDate == null
							|| isCloserToNextDate(originalDate, previousDate,
									nextDate)) {
						return nextDate;
					}
				} else {
					return nextDate;
				}
			}
			return previousDate;
		}

		private static boolean isCloserToNextDate(Date originalDate,
				Date previousDate, Date nextDate) {
			if (previousDate.after(nextDate))
				throw new IllegalArgumentException("previousDate > nextDate");
			return ((nextDate.getTime() - previousDate.getTime()) / 2
					+ previousDate.getTime() <= originalDate.getTime());
		}
		
}
