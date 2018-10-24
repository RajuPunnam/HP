package com.io.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.io.pojos.AvPartFinalQtyPer;
import com.io.pojos.Combined_Price;
import com.io.pojos.FlexBom;
import com.io.pojos.SerialNumberCompare;
import com.io.pojos.SkuBom;

@Repository
public class CommonDao {
	@Autowired
    @Qualifier("mongoTemplate")
	protected MongoTemplate mongoTemplate;
	private static Logger LOG=Logger.getLogger(CommonDao.class);
	public void dropCollection(String collectionName){
		LOG.debug(collectionName+"Dropped");
		mongoTemplate.dropCollection(collectionName);
	}
	
	public List<FlexBom> getFlexBomData() {
//		Query query = new Query().addCriteria(Criteria.where("BOM_TYPE").is("PRI"));		
		List<FlexBom> list = mongoTemplate.findAll(FlexBom.class, "PA_FLEX_SUPER_BOM");
		list.sort(new SerialNumberCompare());
		return list;
	}
	
	public void saveFinalQuantity(List<AvPartFinalQtyPer> finalFlexBomList) {
		mongoTemplate.remove(new Query(), "AVPartFinalLevel");
		mongoTemplate.insertAll(finalFlexBomList);
	}
	
	public void removeFRData(){
		mongoTemplate.remove(new Query(), "FORECAST_COMBINED_DATA_NEW_W0To26");
	}
	
	
	//-----------------PO process-------------------------//
	public List<SkuBom> getALLskuTOav() {
		return mongoTemplate.findAll(SkuBom.class, "MASTERSKUAVBOM_PA");
	}
	public List<AvPartFinalQtyPer> getAVPartFinalLevel() {
		return mongoTemplate.findAll(AvPartFinalQtyPer.class, "AVPartFinalLevel");
	}
	
	public List<Combined_Price> getPriceMasterData() {
		List<String> priceDates = mongoTemplate.getCollection("Combined_PriceMaster_Corrected").distinct("Date");		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		sdf.setLenient(false);
		Date latestDate = null;
		try {
			latestDate = sdf.parse(priceDates.get(0));
			for (String orderDate : priceDates) {
				Date newDate = sdf.parse(orderDate);
				if (newDate.getTime() > latestDate.getTime())
					latestDate = newDate;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Query query = new Query(Criteria.where("Date").is(sdf.format(latestDate)));
		return mongoTemplate.find(query,Combined_Price.class, "Combined_PriceMaster_Corrected");
	}
	
	public Map<String,Double> getPriceMasterData_New() {
		  List<Combined_Price> combined_PriceList = mongoTemplate.findAll(Combined_Price.class,"Combined_PriceMaster_Corrected");
		  Map<String,List<Combined_Price>> mapWithPriceData=new HashMap<String, List<Combined_Price>>();
		  Map<String, Double> priceMasterMap = new HashMap<String, Double>();
		  for(Combined_Price combined_Price:combined_PriceList){
		   String key=combined_Price.getItem();
		         List<Combined_Price> tempPriceList=new ArrayList<Combined_Price>();
		   if(mapWithPriceData.containsKey(key))
		   {
		    tempPriceList.addAll(mapWithPriceData.get(key));
		   }
		       tempPriceList.add(combined_Price);
		       mapWithPriceData.put(key, tempPriceList);
		  }  
		  for(Map.Entry<String, List<Combined_Price>> entry:mapWithPriceData.entrySet()){
		   List<Combined_Price> combined_PriceListTemp = entry.getValue();
		   SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		   sdf.setLenient(false);
		   Date latestDate = null;
		   try {
		    latestDate = sdf.parse(combined_PriceListTemp.get(0).getDate());
		   } catch (ParseException e1) {
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		   }
		   double price = combined_PriceListTemp.get(0).getPrice();
		   for(Combined_Price combined_PriceTemp:combined_PriceListTemp){    
		    try {
		     Date newDate = sdf.parse(combined_PriceTemp.getDate());
		     if (newDate.getTime() > latestDate.getTime())
		      latestDate = newDate;
		      price = combined_PriceTemp.getPrice();
		    } catch (ParseException e) {
		     // TODO Auto-generated catch block
		     e.printStackTrace();
		    }
		   }
		   priceMasterMap.put(entry.getKey(), price);
		  }
		  return priceMasterMap;
		 }
}
