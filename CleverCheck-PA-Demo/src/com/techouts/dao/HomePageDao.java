package com.techouts.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.techouts.dto.All_Skus_Availability;
import com.techouts.dto.SKu_Similarity_Matrix;
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

}
