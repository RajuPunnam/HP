package com.techouts.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.techouts.dto.All_Skus_Availability;
import com.techouts.pojo.BUFamily;
import com.techouts.pojo.BUFamilyAvailability;

@Repository
public class HPChartsDao {
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<BUFamily> getBuQuantity() {
		GroupOperation group;
		group = Aggregation.group("bu").sum("familyAvail").as("totalCount")
				.addToSet("bu").as("bu");
		Aggregation agg = newAggregation(group);
		// Convert the aggregation result into a List
		AggregationResults<BUFamily> groupResults = mongoTemplate.aggregate(
				agg, BUFamilyAvailability.class, BUFamily.class);
		return groupResults.getMappedResults();
	}
	
	public List<All_Skus_Availability> getSkuAvilabilty()
	{
		Query query=new Query();
		query.limit(10);
		query.with(new Sort(Sort.Direction.DESC,"skuAvailability"));
		return mongoTemplate.find(query, All_Skus_Availability.class,"All_Skus_Availability");
	}
}
