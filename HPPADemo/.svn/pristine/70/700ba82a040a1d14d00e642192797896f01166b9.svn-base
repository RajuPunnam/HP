package com.techouts.AvList;

import java.util.List;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class AllAvsDao {

	Logger log = Logger.getLogger(AllAvsDao.class);
	@Autowired
	private MongoOperations mongoTemplate;

	@SuppressWarnings("unchecked")
	public List<String> getMasterSuperFlexBomData(String collectionName,
			String coloumnName) {

		if (collectionName.equals("BOM_AMLCOSTEDBOM_AVSUMMARY")) {
			List<String> level = new ArrayList<String>();
			level.add("0.1");
			level.add(".1");
			Query q = new Query(Criteria.where("Level").in(level));
			return mongoTemplate.getCollection(collectionName).distinct(
					coloumnName);
		} else {
			return mongoTemplate.getCollection(collectionName).distinct(
					coloumnName);
		}
	}

}
