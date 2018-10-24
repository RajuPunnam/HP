package com.io.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class DistinctDao {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	private static Logger LOG=Logger.getLogger(DistinctDao.class);
	@SuppressWarnings("unchecked")
	public List<String> getPOPNDates() {		
		LOG.debug("Distinct PORecDate from Collection :: PO_ORDERS_SKU_AV_PN");
		return mongoTemplate.getCollection("PO_ORDERS_SKU_AV_PN").distinct("AdjustedDate");
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getPOPNMasterDates() {
		LOG.debug("Distinct PO Received Date from Collection :: PNPOMaster");
		return mongoTemplate.getCollection("PNPOMaster").distinct("PO Received Date");
	}
	@SuppressWarnings("unchecked")
	public List<String> getOOCFileDates() {
		LOG.debug("Distinct File Date from Collection :: OPEN_ORDER_CLEAN");
		return mongoTemplate.getCollection("OPEN_ORDER_CLEAN").distinct("File Date");
	}

	@SuppressWarnings("unchecked")
	public List<String> getPOPNFileDates() {
		LOG.debug("Distinct File Date from Collection :: PO_ORDERS_SKU_AV_PN");
		return mongoTemplate.getCollection("PO_ORDERS_SKU_AV_PN").distinct("File Date");
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getOOFileDates() {
		LOG.debug("Distinct File Date from Collection :: OPEN_ORDERS");
		return mongoTemplate.getCollection("OPEN_ORDERS").distinct("File Date");
	}
	@SuppressWarnings("unchecked")
	public List<String> getOOSFileDates() {
		LOG.debug("Distinct File Date from Collection :: ORIG_PO_SHORTAGE");
		return mongoTemplate.getCollection("ORIG_PO_SHORTAGE").distinct("File Date");
	}
	
}
