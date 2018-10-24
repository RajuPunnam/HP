package com.techouts.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.dao.DataMoveDao;
import com.techouts.dto.PipelineAvAvil;
import com.techouts.pojo.AvAvbail;
import com.techouts.pojo.AvPartFinalQtyPer;
import com.techouts.pojo.BUFamilyAvailability;
import com.techouts.pojo.BomStatus;
import com.techouts.pojo.DOInventory;
import com.techouts.pojo.OpenOrderPipeLine;
import com.techouts.pojo.OpenOrders;
import com.techouts.pojo.OverAllSkusAvailability;
import com.techouts.pojo.PNLT;
import com.techouts.pojo.SkuBom;
import com.techouts.pojo.SkuSimilarityMatrix;

/**
 * service class for moving collections from stag to prod
 * 
 * @author p.raju
 *
 */
@Service
public class DataMoveService {
	@Autowired
	private DataMoveDao dataMoveDao;

	/**
	 * metod for replicating data from stag to production database
	 * 
	 * @return replicationStatusMap
	 */
	public synchronized Map<String, String> moveProceessCollectionDataToProduction() {
		List<DOInventory> doi1_1List = null;
		List<OpenOrders> openOrdersLatestList = null;
		List<AvAvbail> avAvilableList = null;
		List<OverAllSkusAvailability> overAllSkusAvailabilityList = null;
		List<OverAllSkusAvailability> overallSkuConfigLIst = null;
		List<SkuSimilarityMatrix> skuSimilarityMatrixList = null;
		List<BUFamilyAvailability> BUFamilyAvailabilityList = null;
		List<PipelineAvAvil> pipelineAvAvilList = null;
		List<OpenOrderPipeLine> ordersPipeLineList = null;
		List<AvPartFinalQtyPer> AvPartFinalQtyPerList = null;
		List<PNLT> PNLTList = null;
		List<SkuBom> paFlexsuperBomList = null;
		Map<Class, String> replicaMap = new HashMap<Class, String>();
		replicaMap.put(DOInventory.class, "DOI_1.1" + "/" + doi1_1List);
		replicaMap.put(OpenOrders.class, "NEWOPNORDERS_LATEST" + "/"
				+ openOrdersLatestList);
		replicaMap.put(AvAvbail.class, "AVAVBAIL" + "/" + avAvilableList);
		replicaMap.put(OverAllSkusAvailability.class, "All_Skus_Availability"
				+ "/" + overAllSkusAvailabilityList);
		replicaMap.put(OverAllSkusAvailability.class,
				"All_Sku_Configs_Availability" + "/" + overallSkuConfigLIst);
		replicaMap.put(SkuSimilarityMatrix.class, "SKu_Similarity_Matrix" + "/"
				+ skuSimilarityMatrixList);
		replicaMap.put(BUFamilyAvailability.class, "BUFamilyAvailability" + "/"
				+ BUFamilyAvailabilityList);
		replicaMap.put(PipelineAvAvil.class, "PipeLineAvAvbail" + "/"
				+ pipelineAvAvilList);
		replicaMap.put(OpenOrderPipeLine.class, "OrdersPipeLine" + "/"
				+ ordersPipeLineList);
		replicaMap.put(AvPartFinalQtyPer.class, "PA_AvPartFinalQtyPer" + "/"
				+ AvPartFinalQtyPerList);
		replicaMap.put(PNLT.class, "PN_LT" + "/" + PNLTList);
		replicaMap.put(SkuBom.class, "PA_FLEX_SUPER_BOM" + "/"
				+ paFlexsuperBomList);
		Map<String, String> replicationStatusMap = new LinkedHashMap<String, String>();

		for (java.util.Map.Entry<Class, String> replica : replicaMap.entrySet()) {
			boolean status = dataMoveDao.replicateCollectionFromStagToProd(
					replica.getValue().split("/")[0],
					Arrays.asList(replica.getValue().split("/")[1]),
					replica.getKey());
			if (status) {
				replicationStatusMap.put(replica.getValue().split("/")[0],
						"SUCESS");
			} else {
				replicationStatusMap.put(replica.getValue().split("/")[0],
						"FAILURE");
			}
		}

		boolean pipelineDoiStatus = dataMoveDao.replicatePipelineDoi();
		if (pipelineDoiStatus) {
			replicationStatusMap.put("PipeLineDOI", "SUCESS");
		} else {
			replicationStatusMap.put("PipeLineDOI", "FAILURE");
		}

		return replicationStatusMap;
	}

}
