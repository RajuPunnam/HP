package com.techouts.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import com.techouts.pojo.AvAvbail;
import com.techouts.pojo.OverAllSkusAvailability;

@Repository
public class SkuAvilWorkbookDao {
	@Resource(name = "stagingMongoTemplate")
	private MongoTemplate mongoTemplate;
	private static final Logger logger = Logger
			.getLogger(SkuAvilWorkbookDao.class.getName());

	public List<OverAllSkusAvailability> getoverllSkuAvialbilityData() {
		return mongoTemplate.findAll(OverAllSkusAvailability.class,
				"All_Skus_Availability");
	}

	public List<AvAvbail> getavAvilableData() {
		return mongoTemplate.findAll(AvAvbail.class, "AVAVBAIL");
	}
}
