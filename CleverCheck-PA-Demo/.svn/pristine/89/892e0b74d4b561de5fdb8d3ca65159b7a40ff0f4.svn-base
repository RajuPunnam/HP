package com.techouts.util;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.stereotype.Service;

import com.mongodb.DBObject;
import com.techouts.pojo.DOInventory;

@Service
public class DOIListener extends AbstractMongoEventListener<DOInventory> {
	private static final Logger LOG = Logger.getLogger(DOIListener.class);
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public void onBeforeSave(DOInventory source, DBObject dbo) {
		LOG.info("BEFORE Save/update " + source);
	}

	@Override
	public void onAfterSave(DOInventory source, DBObject dbo) {
		LOG.info("After Save/update " + source);

		/*
		 * Aggregation aggr = newAggregation(
		 * match(Criteria.where("ITEM").is(source.getItem())),
		 * group("item").sum("totalDemandToOrder").as("totalDemandToOrder"),
		 * sort(Sort.Direction.ASC,"item") );
		 * 
		 * AggregationResults<AggQuantityByItemId> groupRes =
		 * mongoTemplate.aggregate(aggr, DOInventory.class,
		 * AggQuantityByItemId.class ); for( AggQuantityByItemId ag :
		 * groupRes.getMappedResults()){ mongoTemplate.save(ag); }
		 */
	}

	@Override
	public void onBeforeDelete(DBObject dbo) {
		LOG.info("BEFORE Delete ");
	}

	@Override
	public void onAfterDelete(DBObject dbo) {
		LOG.info("After Delete ");
		DOInventory source = mongoTemplate.getConverter().read(
				DOInventory.class, dbo);

		/*
		 * Aggregation aggr = newAggregation(
		 * match(Criteria.where("ITEM").is(source.getItem())),
		 * group("item").sum("totalDemandToOrder").as("totalDemandToOrder"),
		 * sort(Sort.Direction.ASC,"item") );
		 * 
		 * AggregationResults<AggQuantityByItemId> groupRes =
		 * mongoTemplate.aggregate(aggr, DOInventory.class,
		 * AggQuantityByItemId.class ); for( AggQuantityByItemId ag :
		 * groupRes.getMappedResults()){ mongoTemplate.save(ag); }
		 */
	}

}
