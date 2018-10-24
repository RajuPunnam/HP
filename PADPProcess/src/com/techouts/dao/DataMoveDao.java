package com.techouts.dao;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.techouts.pojo.BomStatus;
import com.techouts.pojo.PipelineDoiPojo;
import com.techouts.pojo.SkuBom;

/**
 * dao class for replicating collections from stag to prod
 * 
 * @author P.Raju
 *
 */
@Repository
public class DataMoveDao {
	private static final Logger LOGGER = Logger.getLogger(DataMoveDao.class);
	@Resource(name = "stagingMongoTemplate")
	private MongoTemplate stagMongoTemplate;
	@Resource(name = "productionMongoTemplate")
	private MongoTemplate procMongoTemplate;

	/**
	 * method for moving data from stag to prod schema
	 * 
	 * @param collectioName
	 * @param holeCollectionList
	 * @param clazzName
	 * @return
	 */
	public boolean replicateCollectionFromStagToProd(String collectioName,
			List<?> holeCollectionList, Class<?> clazzName) {

		boolean movingStatus = false;
		try {
			if (stagMongoTemplate.getCollection(collectioName).count() > 0) {
				holeCollectionList = stagMongoTemplate.findAll(
						(Class<?>) clazzName, collectioName);
				if (holeCollectionList != null && holeCollectionList.size() > 0) {
					if (procMongoTemplate.getCollection(collectioName).count() > 0) {
						procMongoTemplate.dropCollection(collectioName);
					}
					procMongoTemplate.insert(holeCollectionList, collectioName);
					if (procMongoTemplate.getCollection(collectioName).count() > 0) {
						movingStatus = true;
						LOGGER.info(collectioName + " moved sucessfully");
					} else {

						LOGGER.info(collectioName + " moving failured");
					}
				}
			} else {

				LOGGER.info(collectioName + " collection not found");
			}
		} catch (Exception exception) {
			LOGGER.info(collectioName + "has exception"
					+ exception.getMessage());

		} catch (Error error) {
			LOGGER.info(error.getMessage());
		}

		return movingStatus;
	}

	/**
	 * method for replicating pipeline data from stag to prod schema
	 * 
	 * @return boolean
	 */
	public boolean replicatePipelineDoi() {
		boolean uploadStatus = false;
		try {
			if (stagMongoTemplate.getCollection("PipeLineDOI").count() > 0) {
				int UploadCount = 0;
				long pipelineRecordsCount = stagMongoTemplate.getCollection(
						"PipeLineDOI").count();
				long iterationCount = pipelineRecordsCount / 100000;
				long remainsRecords = pipelineRecordsCount % 100000;
				if (remainsRecords > 0) {
					iterationCount++;
				}
				LOGGER.info("Number of Db iterations are " + iterationCount);
				Query query = new Query();
				for (int i = 0; i < iterationCount; i++) {
					query.withHint("_id_");
					query.skip(i * 100000);
					query.limit(100000);
					List<PipelineDoiPojo> pipelineDoiList = stagMongoTemplate
							.find(query, PipelineDoiPojo.class, "PipeLineDOI");

					if (pipelineDoiList != null && pipelineDoiList.size() > 0) {
						if (i == 0) {
							if (procMongoTemplate.getCollection("PipeLineDOI")
									.count() > 0) {
								procMongoTemplate.dropCollection("PipeLineDOI");
							}
						}
						procMongoTemplate
								.insert(pipelineDoiList, "PipeLineDOI");
						UploadCount++;
						LOGGER.info(UploadCount + " th time " + UploadCount
								* 100000);
						pipelineDoiList.clear();
					}
				}
				if (procMongoTemplate.getCollection("PipeLineDOI").count() > 0) {
					LOGGER.info("PipeLineDOI" + " moved sucessfully");
					uploadStatus = true;
				} else {

					LOGGER.info("PipeLineDOI" + " moving failured");
				}

			} else {

				LOGGER.info("PipeLineDOI" + " collection not found");
			}
		} catch (Exception exception) {
			LOGGER.info("Exception in pipeline doi" + exception.getMessage());

		} catch (Error error) {
			LOGGER.info("Exception in pipeline doi" + error.getMessage());
		}
		return uploadStatus;
	}

	/**
	 * method for replicating Flexbom data from stag to prod schema
	 * 
	 * @return boolean
	 */
	public boolean replicatePaFlexSuperBomCollection() {
		List<SkuBom> paFlexsuperBomList = null;
		boolean uploadStatus = false;
		try {
			if (procMongoTemplate.getCollection("PA_FLEX_SUPER_BOM").count() == 0) {
				if (stagMongoTemplate.getCollection("PA_FLEX_SUPER_BOM")
						.count() > 0) {
					paFlexsuperBomList = stagMongoTemplate.findAll(
							SkuBom.class, "PA_FLEX_SUPER_BOM");
					if (paFlexsuperBomList != null
							&& paFlexsuperBomList.size() > 0) {
						procMongoTemplate.insert(paFlexsuperBomList,
								"PA_FLEX_SUPER_BOM");
						if (procMongoTemplate
								.getCollection("PA_FLEX_SUPER_BOM").count() > 0) {
							LOGGER.info("PaFlexSuperBom" + " moved sucessfully");
							uploadStatus = true;
						} else {
							LOGGER.info("PaFlexSuperBom" + " moveing failed");
						}

					}
				}
			}
		} catch (Exception exception) {
			LOGGER.info("exception in paflex super bom"
					+ exception.getMessage());
		} catch (Throwable throwable) {
			LOGGER.info("exception in paflex super bom"
					+ throwable.getMessage());
		}
		return uploadStatus;
	}

}
