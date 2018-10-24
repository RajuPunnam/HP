package com.techouts.re.dao;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.techouts.pojo.AvAvbail;
import com.techouts.pojo.AvPartFinalQtyPer;
import com.techouts.pojo.BUFamilyAvailability;
import com.techouts.pojo.DOInventory;
import com.techouts.pojo.FlexBom;
import com.techouts.pojo.MasterSkuAvBom_PA;
import com.techouts.pojo.OpenOrderPipeLine;
import com.techouts.pojo.OpenOrders;
import com.techouts.pojo.OverAllSkusAvailability;
import com.techouts.pojo.PNLT;
import com.techouts.pojo.PipeLineAvAvbail;
import com.techouts.pojo.PipeLineDOI;
import com.techouts.pojo.PriAltBom;
import com.techouts.pojo.SerialNumberCompare;
import com.techouts.pojo.SkuSimilarityMatrix;

@Repository
public class SkuAvailDao {
	private static final Logger logger = Logger.getLogger(SkuAvailDao.class);
	@Autowired
	private MongoTemplate mongoTemplate;

	public List<MasterSkuAvBom_PA> getMasterAvBomData() {
		Query query = new Query().addCriteria(Criteria.where("family").ne(""));
		return mongoTemplate.find(query, MasterSkuAvBom_PA.class,
				"MASTERSKUAVBOM_PA");
	}

	// ---------------getting pri alt data ----------------------------//
	public List<PriAltBom> getPriAltData() {
		Query query = new Query();
		query.fields().include("PRI");
		query.fields().include("ALT");
		List<PriAltBom> list = mongoTemplate.find(query, PriAltBom.class,
				"PRI_ALT_PN_LIST");
		return list;
	}

	public List<FlexBom> getFlexBomData() {
		Query query = new Query().addCriteria(Criteria.where("BOM_TYPE").is(
				"PRI"));
		List<FlexBom> list = mongoTemplate.find(query, FlexBom.class,
				"PA_FLEX_SUPER_BOM");
		list.sort(new SerialNumberCompare());
		return list;
	}

	public List<FlexBom> getCartFlexBomData() {
		Query query = new Query().addCriteria(Criteria
				.where("INCLUDE_ON_SCREEN").is("Yes").and("BOM_LEVEL").ne(0.1)
				.and("BOM_TYPE").is("PRI"));
		List<FlexBom> list = mongoTemplate.find(query, FlexBom.class,
				"PA_FLEX_SUPER_BOM");
		list.sort(new SerialNumberCompare());
		return list;
	}

	// -------------getting DOI_1.1
	// data----------------------------------------------------//
	public List<DOInventory> getDoiData() {
		Query query = new Query();
		query.fields().include("PartID");
		query.fields().include("Quantity");
		query.fields().include("Date");
		return mongoTemplate.find(query, DOInventory.class);
	}

	public Map<String, String> getSkuDesc() {
		DBCursor result = null;
		Map<String, String> skuDescMap = new HashMap<String, String>();
		try {
			result = mongoTemplate
					.getCollection("COMBINED_SKUQUOTATIONSUMMARY").find();
			while (result.hasNext()) {
				DBObject record = result.next();
				skuDescMap.put((String) record.get("SKU PN"),
						(String) record.get("DESCRIPTION"));
			}
		} finally {
			if (result != null) {
				result.close();
			}
		}
		return skuDescMap;
	}

	public List<AvPartFinalQtyPer> getAvPartFinalQtyPer() {
		Query query = new Query();
		return mongoTemplate.find(query, AvPartFinalQtyPer.class,
				"PA_AvPartFinalQtyPer");
	}

	public List<PNLT> getPnLTData() {
		return mongoTemplate.findAll(PNLT.class);
	}

	public List<OpenOrderPipeLine> getOpenOrdersData() {
		return mongoTemplate.findAll(OpenOrderPipeLine.class);
	}

	public List<OverAllSkusAvailability> getOverAllSkusAvailability() {
		return mongoTemplate.findAll(OverAllSkusAvailability.class);
	}

	public void saveOverAllSkusAvailability(
			List<OverAllSkusAvailability> overAllSkusAvailabilityList) {
		mongoTemplate.remove(new Query(), "All_Skus_Availability");
		for (OverAllSkusAvailability overAllSkusAvailability : overAllSkusAvailabilityList)
			mongoTemplate.insert(overAllSkusAvailability,
					"All_Skus_Availability");
	}

	public void saveOverAllSkuConfigssAvailability(
			List<OverAllSkusAvailability> overAllSkusAvailabilityList) {
		mongoTemplate.remove(new Query(), "All_Sku_Configs_Availability");
		for (OverAllSkusAvailability overAllSkusAvailability : overAllSkusAvailabilityList)
			mongoTemplate.insert(overAllSkusAvailability,
					"All_Sku_Configs_Availability");
	}

	public void saveBUFamilyAvailability(
			List<BUFamilyAvailability> buFamilyAvailabilityList) {
		mongoTemplate.remove(new Query(), "BUFamilyAvailability");
		for (BUFamilyAvailability buFamilyAvailability : buFamilyAvailabilityList)
			mongoTemplate.insert(buFamilyAvailability, "BUFamilyAvailability");
	}

	public void saveSkuSimilarityMatrix(
			List<SkuSimilarityMatrix> skuSimilarityMatrixList) {
		mongoTemplate.remove(new Query(), "SKu_Similarity_Matrix");
		for (SkuSimilarityMatrix skuSimilarityMatrix : skuSimilarityMatrixList)
			mongoTemplate.insert(skuSimilarityMatrix, "SKu_Similarity_Matrix");
	}

	public List<AvAvbail> getAvAVBAIL() {
		return mongoTemplate.findAll(AvAvbail.class, "AVAVBAIL");
	}

	public List<OverAllSkusAvailability> getOverAllConfigSkusAvailability() {
		return mongoTemplate.findAll(OverAllSkusAvailability.class,
				"All_Sku_Configs_Availability");
	}

	public void savAvAVBAIL(List<AvAvbail> avAvbailList) {
		mongoTemplate.remove(new Query(), "AVAVBAIL");
		for (AvAvbail avAvbail : avAvbailList) {
			logger.info(avAvbail);
			mongoTemplate.insert(avAvbail, "AVAVBAIL");
		}
	}

	public List<OpenOrders> getpartOredrData(String partId) {
		Query query = new Query(Criteria.where("item").is(partId));
		return mongoTemplate.find(query, OpenOrders.class);
	}

	public List<OpenOrders> getOredrData(String date) {
		Query query = new Query(Criteria.where("Date").is(date));
		return mongoTemplate.find(query, OpenOrders.class);
	}

	@SuppressWarnings("unchecked")
	public List<String> getDistinctStringValues(String collectionName,
			String colunmName) {
		return mongoTemplate.getCollection(collectionName).distinct(colunmName);
	}

	public void createPipeLineCollection(List<OpenOrderPipeLine> finalList) {
		mongoTemplate.remove(new Query(), "OrdersPipeLine");
		for (OpenOrderPipeLine openOrderPipeLine : finalList) {
			mongoTemplate.insert(openOrderPipeLine, "OrdersPipeLine");
		}
	}

	public void saveFinalQuantity(List<AvPartFinalQtyPer> finalFlexBomList) {
		mongoTemplate.remove(new Query(), "PA_AvPartFinalQtyPer");
		for (AvPartFinalQtyPer avPartFinalQtyPer : finalFlexBomList) {
			logger.info(avPartFinalQtyPer);
			mongoTemplate.insert(avPartFinalQtyPer, "PA_AvPartFinalQtyPer");
		}
	}

	public void savePipeLineDOI(List<PipeLineDOI> pipeLineDOIList) {
		mongoTemplate.remove(new Query(), "PipeLineDOI");
		int count = 0;
		int printCount = 10000;
		for (PipeLineDOI PipeLineDOI : pipeLineDOIList) {
			if (count == printCount) {
				logger.info(printCount + " records inserted");
				printCount += 10000;
			}
			count++;
			mongoTemplate.insert(PipeLineDOI, "PipeLineDOI");
		}
	}

	public void savePipeLineAvAvbail(List<PipeLineAvAvbail> pipeLineAvAvbailList) {
		mongoTemplate.remove(new Query(), "PipeLineAvAvbail");
		for (PipeLineAvAvbail pipeLineAvAvbail : pipeLineAvAvbailList) {
			// logger.info(pipeLineAvAvbail);
			mongoTemplate.insert(pipeLineAvAvbail, "PipeLineAvAvbail");
		}
	}
	
	public Set<String> getSalesDisSearchData(){
		Set<String> sku = new HashSet<String>();
		List<MasterSkuAvBom_PA> masterSkuAvBom_PAList=  mongoTemplate.findAll(MasterSkuAvBom_PA.class,"MASTERSKUAVBOM_PA");
		for(MasterSkuAvBom_PA masterSkuAvBom_PA:masterSkuAvBom_PAList){
			if(!masterSkuAvBom_PA.getSku().contains("Total")){
				sku.add(masterSkuAvBom_PA.getSku());
			}
		}
		return sku;	
	}
}
