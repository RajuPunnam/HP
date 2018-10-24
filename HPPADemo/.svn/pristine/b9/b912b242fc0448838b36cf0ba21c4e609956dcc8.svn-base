package com.techouts.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.techouts.dto.All_Skus_Availability;
import com.techouts.dto.SKu_Similarity_Matrix;
import com.techouts.pojo.AltDisplayAvData;
import com.techouts.pojo.AvAvbail;
import com.techouts.pojo.BUFamilyAvailability;
import com.techouts.pojo.COMBINED_SKUQUOTATIONSUMMARY;
import com.techouts.pojo.MasterSkuAvBom_PA;

@Repository
public class HomePageDao {
	private static final Logger LOG = Logger.getLogger(HomePageDao.class);
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<All_Skus_Availability> getAllSkuAvailability() {
		LOG.info("IN Dao");
		return mongoTemplate.findAll(All_Skus_Availability.class,
				"All_Skus_Availability");
	}

	public List<All_Skus_Availability> getAllSkuAvailability(String family,
			String bu) {
		Query query = new Query(Criteria.where("Family").is(family).and("BU")
				.is(bu));
		return mongoTemplate.find(query, All_Skus_Availability.class,
				"All_Skus_Availability");
	}

	public List<AvAvbail> getAvAVBAIL() {
		return mongoTemplate.findAll(AvAvbail.class, "AVAVBAIL");
	}

	@SuppressWarnings("unchecked")
	public List<String> getDistinctSkusforFamilyBU(String family, String bu) {
		BasicDBObject query = new BasicDBObject();
		query.put("BU", bu);
		query.put("Family", family);
		return mongoTemplate.getCollection("All_Skus_Availability").distinct(
				"SKU", query);
	}

	@SuppressWarnings("unchecked")
	public List<String> getdistinctfamilies(String bu) {
		BasicDBObject query = new BasicDBObject();
		query.put("BU", bu);
		return mongoTemplate.getCollection("BUFamilyAvailability").distinct(
				"Family", query);
		/*
		 * Query query=new Query(Criteria.where("BU").is(bu)); return
		 * mongoTemplate
		 * .find(query,All_Skus_Availability.class,"All_Skus_Availability")
		 */
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllbuSkus(String bu) {
		BasicDBObject query = new BasicDBObject();
		query.put("BU", bu);
		return mongoTemplate.getCollection("All_Skus_Availability").distinct(
				"SKU", query);
	}

	public long getfamilycount(String family) {
		Query query = new Query(Criteria.where("Family").is(family));
		return mongoTemplate.count(query, "All_Skus_Availability");
	}

	public long getbucount(String bu) {
		Query query = new Query(Criteria.where("BU").is(bu));
		return mongoTemplate.count(query, "All_Skus_Availability");
	}
	public List<BUFamilyAvailability> getAllAvailabilitycount(String bu) {
		Query query = new Query(Criteria.where("BU").is(bu));
		return mongoTemplate.find(query, BUFamilyAvailability.class);
	}

	/*
	 * public List<FamilyAvailabilityAggregation> getAllAvailabilitycount(String
	 * bu){
	 * 
	 * Aggregation agg = newAggregation( match(Criteria.where("BU").is(bu)),
	 * group("Family").sum("AVBL").as("AVBL"),
	 * project("AVBL").and("Family").previousOperation() );
	 * 
	 * 
	 * AggregationResults<FamilyAvailabilityAggregation> groupResults =
	 * mongoTemplate.aggregate(agg,
	 * "All_Skus_Availability",FamilyAvailabilityAggregation.class);
	 * List<FamilyAvailabilityAggregation> result =
	 * groupResults.getMappedResults();
	 * 
	 * return result;
	 * 
	 * }
	 */
	public All_Skus_Availability getSkuData(String skuId) {
		Query query = new Query(Criteria.where("SKU").is(skuId));
		return mongoTemplate.findOne(query, All_Skus_Availability.class,
				"All_Skus_Availability");
	}
	
	public MasterSkuAvBom_PA getSkuDataSales(String skuId) {
		Query query = new Query(Criteria.where("sku").is(skuId));
		return mongoTemplate.findOne(query, MasterSkuAvBom_PA.class,
				"MASTERSKUAVBOM_PA");
	}
	
	public List<All_Skus_Availability> getAllbuAvailability(String bu) {
		Query query = new Query(Criteria.where("BU").is(bu));
		return mongoTemplate.find(query, All_Skus_Availability.class,
				"All_Skus_Availability");
	}

	@SuppressWarnings("unchecked")
	public List<String> getdistinctskus() {
		return mongoTemplate.getCollection("All_Skus_Availability").distinct(
				"SKU");
	}

	public List<SKu_Similarity_Matrix> getSku_SimilarityMatrix(String sku) {
		LOG.info("in dao :: " + sku);
		Query query = new Query(Criteria.where("ActualSku").is(sku)
				.and("Similarity_Index").gt(0.8));
		return mongoTemplate.find(query, SKu_Similarity_Matrix.class,
				"SKu_Similarity_Matrix");
	}

	public List<All_Skus_Availability> getAllSkuAvailability(List<String> skus) {
		Query query = new Query(Criteria.where("SKU").in(skus));
		return mongoTemplate.find(query, All_Skus_Availability.class,
				"All_Skus_Availability");
	}

	public List<All_Skus_Availability> getAllSkuconfigAvailability(
			List<String> skus) {
		LOG.info("in dao :: " + skus);
		Query query = new Query(Criteria.where("SKU").in(skus));
		return mongoTemplate.find(query, All_Skus_Availability.class,
				"All_Sku_Configs_Availability");
	}

	public List<All_Skus_Availability> getTop5SkuAvailabilityforBU(String bu) {
		Query query = new Query(Criteria.where("BU").is(bu));
		return mongoTemplate.find(query, All_Skus_Availability.class,
				"All_Skus_Availability");
	}

	public All_Skus_Availability getSkuconfigData(String selectedSkuId) {
		Query query = new Query(Criteria.where("SKU").is(selectedSkuId));
		return mongoTemplate.findOne(query, All_Skus_Availability.class,
				"All_Sku_Configs_Availability");
	}

	public COMBINED_SKUQUOTATIONSUMMARY getSkuDescription(String skuid) {
		Query query = new Query(Criteria.where("SKU PN").is(skuid));
		return mongoTemplate.findOne(query, COMBINED_SKUQUOTATIONSUMMARY.class,
				"COMBINED_SKUQUOTATIONSUMMARY");
	}

	public List<All_Skus_Availability> getAllSkuConfAvailability() {
		return mongoTemplate.findAll(All_Skus_Availability.class,
				"All_Sku_Configs_Availability");
	}

	@SuppressWarnings("unchecked")
	public List<String> getDistinctfamilys() {
		return mongoTemplate.getCollection("All_Skus_Availability").distinct(
				"Family");
	}

	@SuppressWarnings("unchecked")
	public List<String> getdistinctskusForFamily(String family) {
		BasicDBObject query = new BasicDBObject();
		query.put("Family", family);
		return mongoTemplate.getCollection("All_Skus_Availability").distinct(
				"SKU", query);
	}
	
	public List<MasterSkuAvBom_PA> getMasterAvBomData(String family) {
		Query query = new Query().addCriteria(Criteria.where("family").is(family));
		return mongoTemplate.find(query, MasterSkuAvBom_PA.class,
				"MASTERSKUAVBOM_PA");
	}
	
	public Set<String> getSkuAvList(String skuId) {
		Query query = new Query().addCriteria(Criteria.where("sku").is(skuId));
		Set<String> avList = new HashSet<String>();
		List<MasterSkuAvBom_PA> masterSkuAvBom_PAList = mongoTemplate.find(query, MasterSkuAvBom_PA.class,"MASTERSKUAVBOM_PA");
		for(MasterSkuAvBom_PA masterSkuAvBom_PA:masterSkuAvBom_PAList){
			avList.add(masterSkuAvBom_PA.getAv());
		}
		return avList;
	}
	
	public List<MasterSkuAvBom_PA> getMasterAvBomDataSales(String family) {
		Query query = new Query().addCriteria(Criteria.where("FAMILY").regex("^" + family + "$", "i"));
		List<MasterSkuAvBom_PA> masterSkuAvBom_PAList = mongoTemplate.find(query, MasterSkuAvBom_PA.class,
				"MASTERSKUAVBOM_PA_SALES");	
		return  masterSkuAvBom_PAList;
	}
	
	public List<String> getAltAvs(String orgAv,boolean isCpu,String commodity){
		List<String> altAvList = new ArrayList<String>();
		Query query = new Query(Criteria.where("AV").is(orgAv));
		AltDisplayAvData altDisplayAvData = mongoTemplate.findOne(query, AltDisplayAvData.class,"ALT_DISPLAY_AV_DATA");
		if(isCpu && altDisplayAvData != null){
			query = new Query(Criteria.where("COMMODITY_ATTRIBUTE").is(altDisplayAvData.getCommodityAttr()).and("COMMODITY").is(altDisplayAvData.getCommodity()));
		}else if(altDisplayAvData != null){
			query = new Query(Criteria.where("COMMODITY").is(altDisplayAvData.getCommodity()));
		}		
		List<AltDisplayAvData> altDisplayAvDataList = mongoTemplate.find(query, AltDisplayAvData.class,"ALT_DISPLAY_AV_DATA");
		for(AltDisplayAvData altDisplayAvDataTemp:altDisplayAvDataList){
			if(isCpu){
				altAvList.add(altDisplayAvDataTemp.getAv());
			}else if(Double.parseDouble(altDisplayAvDataTemp.getCommodityAttr()) >= Double.parseDouble(altDisplayAvData.getCommodityAttr())){
				altAvList.add(altDisplayAvDataTemp.getAv());
			}			
		}
		return altAvList;
	}
}
