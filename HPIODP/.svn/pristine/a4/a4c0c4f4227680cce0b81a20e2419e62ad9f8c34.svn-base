package com.io.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.io.pojos.Combined_Price;
import com.io.pojos.MasterBomPojo;
import com.io.pojos.SkuToAvToPnPojo;
import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBObject;
import com.mongodb.Cursor;
import com.mongodb.DBObject;
@Repository
public class ForecastDao extends CommonDao{
	private static final Logger log = Logger.getLogger(ForecastDao.class);
	public List<MasterBomPojo> getMasterBom() {
		// Query query=new Query(Criteria.where("AV").is(av));
		System.out.println("---------iam in ForecastDao------------>");
		log.info("Fetching all records from collectionName :: MASTERAV_PN_BOM_V11");
		return mongoTemplate.findAll(MasterBomPojo.class, "MASTERAV_PN_BOM_V11");
		                                                   
	}
	
	// method to get price master data
		public List<Combined_Price> getPriceMasterData(String item) {
			Query query=new Query(Criteria.where("Item").is(item));
			log.debug(query+"\t collectionName :: Combined_PriceMaster_Corrected");
			List<Combined_Price> list=mongoTemplate.find(query,Combined_Price.class, "Combined_PriceMaster_Corrected");
			log.info(item+"getPriceMasterData for item -------------------"+list.size());
			return list;
		}
		
		/*public List<Combined_Price> getPriceMasterData() {
			log.info("---------Loading data from collectionName :: Combined_PriceMaster_Corrected");
			List<Combined_Price> list=mongoTemplate.findAll(Combined_Price.class, "Combined_PriceMaster_Corrected");
			log.info("getPriceMasterData size -------------------"+list.size());
			return list;
		}*/
		
		public int saveSkuToAvToPnPojo(List<SkuToAvToPnPojo> skuToAvToPnPojoList){
//			mongoTemplate.remove(new Query(), "FORECAST_COMBINED_DATA_NEW_W0To26");
			
			int count = 0;
			int printCount = 100000;
			for(SkuToAvToPnPojo skuToAvToPnPojo: skuToAvToPnPojoList){	
				if(count == printCount){
					System.out.println(printCount+" records inserted");
					printCount+=100000;
				}
				count++;
				//mongoTemplate.insert(skuToAvToPnPojo, "FORECAST_COMBINED_DATA_NEW_W0To26");
				mongoTemplate.insert(skuToAvToPnPojo, "FORECAST_COMBINED_DATA_NEW_W0To26");
			}
			return count;		
		 }

		public List<String> getFcst_DistinctDates() {
			return mongoTemplate.getCollection("FORECAST_COMBINED_DATA_NEW_W0To26").distinct("FcstGD");
			
		}
		
		
		public List<SkuToAvToPnPojo> getFcst_Aggregation(
			List<Date> doiDates, String excessShortage_Unit,
			String qty_Units_$Value, List<String> uniquePOList) {

		MatchOperation match;
		GroupOperation group;
		
		// ProjectionOperation project;
		if (excessShortage_Unit.isEmpty()
				|| excessShortage_Unit.equalsIgnoreCase("excess")) {
			match = Aggregation.match(Criteria.where("DateObj").in(doiDates)
					.and("PO").in(uniquePOList).and("Excess/Shortage").gt(0));
		} else if (excessShortage_Unit.equalsIgnoreCase("shortage")) {
			match = Aggregation.match(Criteria.where("DateObj").in(doiDates)
					.and("PO").in(uniquePOList).and("Excess/Shortage").lt(0));
		} else {
			match = Aggregation.match(Criteria.where("DateObj").in(doiDates)
					.and("PO").in(uniquePOList));
		}

		if (qty_Units_$Value.isEmpty()
				|| qty_Units_$Value.equalsIgnoreCase("units")) {
			// project=Aggregation.project("DateObj","Commodity","Date","Excess/Shortage");
			group = Aggregation.group("DateObj", "Commodity", "Date")
					.sum("Excess/Shortage").as("total");

		} else {
			// project=Aggregation.project("DateObj","Commodity","Date","Excess/Shortage","Price").and("Excess/Shortage").multiply("Price").as("qty");
			group = Aggregation.group("DateObj", "Commodity", "Date")
					.sum("Price").as("total");
		}
		

		Aggregation agg = newAggregation(match, group,sort(Sort.Direction.DESC, "DateObj", "Commodity"));
		// group("DateObj","Commodity","Date").sum("qty").as("total"),
		// Convert the aggregation result into a List
		AggregationResults<SkuToAvToPnPojo> groupResults = mongoTemplate
				.aggregate(agg, "ExcessAndShortage_WithPO",
						SkuToAvToPnPojo.class);
		List<SkuToAvToPnPojo> result = groupResults.getMappedResults();
		return result;

	}

	public List<String> getDistinct_FcstDateBU(String collectionName){
		log.info("requested db for Distinct Date & Bu in Fcst "+new Date());
		List<String> dateBUList=new ArrayList<String>();
        GroupOperation group;
       
		group = Aggregation.group("FcstGD","Type");
		Aggregation agg =newAggregation(group,sort(Sort.Direction.ASC, "FcstGD","Type"));
		AggregationResults<SkuToAvToPnPojo> groupResults = mongoTemplate
				.aggregate(agg, collectionName,
						SkuToAvToPnPojo.class);
		List<SkuToAvToPnPojo> result = groupResults.getMappedResults();
		for(SkuToAvToPnPojo pojo:result){
			dateBUList.add(pojo.getFGD()+"="+pojo.getType());
			log.info("Distinct_FcstDateBU "+pojo.getFGD()+"="+pojo.getType());
		}
		return dateBUList;
		
	}

	public long getCollectionCount(String collectionName) {
		log.info("Requested Collection count for "+collectionName);
		return mongoTemplate.getCollection(collectionName).count();
	}
	
	public List<String> getIndexData(String collectionName){
		List<String> dateBUList=new ArrayList<String>();
		DBObject group=new BasicDBObject("$group",new BasicDBObject("FcstGD", "Type"));
		DBObject hint=new BasicDBObject("$hint","distinct_date_bu");
		AggregationOutput output =mongoTemplate.getCollection(collectionName).aggregate(group, hint);

		 for (DBObject dbObject : output.results()) {
			 dateBUList.add(dbObject.get("FcstGD")+"="+dbObject.get("Type"));
		 }
		
		return null;
		
	}
}
