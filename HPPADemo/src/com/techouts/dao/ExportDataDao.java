package com.techouts.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.techouts.pojo.AvAvailability;
import com.techouts.pojo.DOInventory;
import com.techouts.pojo.PA_FLEX_SUPER_BOM;
import com.techouts.pojo.PipelineDoiPojo;
import com.techouts.pojo.PipelinePojo;
import com.techouts.pojo.PriAltBom;

@Repository
public class ExportDataDao {
	@Autowired
	private MongoTemplate mongoTemplate;
	private static final Logger LOG = Logger.getLogger(ExportDataDao.class);

	public List<PA_FLEX_SUPER_BOM> getSuberBomData(List<String> configurations) {
		Query query = new Query(Criteria.where("AV").in(configurations));
		query.with(new Sort(Sort.Direction.ASC, "SR_NO"));
		LOG.info("CurrentTime: " + new Date() + "   Module name: Cart  "
				+ query);
		List<PA_FLEX_SUPER_BOM> list = mongoTemplate.find(query,
				PA_FLEX_SUPER_BOM.class, "PA_FLEX_SUPER_BOM");
		for (PA_FLEX_SUPER_BOM bom : list) {
			if (bom.getBomLevel().equalsIgnoreCase("0.1")) {
				LOG.info(bom);
			}
		}
		return list;
	}

	public List<DOInventory> getDoiData() {
		return mongoTemplate.findAll(DOInventory.class);
	}

	public List<AvAvailability> getAvAvialbilityData() {
		return mongoTemplate.findAll(AvAvailability.class, "AVAVBAIL");
	}

	public List<PriAltBom> getAltPns(String pn) {
		Query query = new Query(Criteria.where("PRI").in(pn));
		return mongoTemplate.find(query, PriAltBom.class, "PRI_ALT_PN_LIST");
	}

	public List<PipelinePojo> getDistinctSkuAvAvilability(String skuId) {
		Query query = new Query(Criteria.where("sku").is(skuId));
		/* query.withHint("sku"); */
		return mongoTemplate.find(query, PipelinePojo.class);
	}

	public List<PipelineDoiPojo> getDistinctSkuDoiAvilability(String skuId) {
		Query query = new Query(Criteria.where("sku").is(skuId));
		/* query.withHint("sku"); */
		return mongoTemplate.find(query, PipelineDoiPojo.class);

	}

}
