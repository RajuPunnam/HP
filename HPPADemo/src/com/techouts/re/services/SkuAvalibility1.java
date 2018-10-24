package com.techouts.re.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.re.dao.SkuAvailDao;
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
import com.techouts.pojo.PriAltBom;
import com.techouts.pojo.SkuSimilarityMatrix;

@Service
public class SkuAvalibility1 {
	private static final Logger LOG = Logger.getLogger(SkuAvalibility1.class);
	@Autowired
	private SkuAvailDao skuAvailDao;
	Logger logger = Logger.getLogger(SkuAvalibility.class);

	Map<String, Double> finalReversedMap = new LinkedHashMap<String, Double>();
	Map<String, Double> avAVBLMap = new HashMap<String, Double>();
	Map<String, List<String>> buFamilyAvMap = new HashMap<String, List<String>>();
	Map<String, List<String>> comodityAvListMap = new HashMap<String, List<String>>();
	Map<String, String> avComodityMap = new HashMap<String, String>();

	/**
	 * Method to generate Sku Availability
	 */
	public void generateSkuAvilability() {
		List<MasterSkuAvBom_PA> masterSkuAvBom_PAList = skuAvailDao.getMasterAvBomData();
		LOG.info("Got the SKUBOM Data");
		List<DOInventory> doiList = skuAvailDao.getDoiData();
		LOG.info("Got the DOI Data");
		List<PriAltBom> priAltList = skuAvailDao.getPriAltData();
		LOG.info("Got the PRIALT Data");
		List<AvPartFinalQtyPer> avPartFinalQtyPerList = skuAvailDao.getAvPartFinalQtyPer();
		LOG.info("Got the PART QTYPer Data");
		List<FlexBom> flexBomList = skuAvailDao.getFlexBomData();
		LOG.info("Got the FLEXBOM Data");
		List<PNLT> pnltList = skuAvailDao.getPnLTData();
		LOG.info("Got the Lead Time Data");
		List<OpenOrderPipeLine> openOrderPipeLineList = skuAvailDao.getOpenOrdersData();
		LOG.info("Got the Open Orders Data");
		Map<String,String> skuDesc = skuAvailDao.getSkuDesc();
		LOG.info("Got the Sku Desc Data");
		Map<String, String> skuFamilyBUMap = new HashMap<String, String>();
		Map<String, Set<String>> skuAvsMap = new HashMap<String, Set<String>>();
		for (MasterSkuAvBom_PA masterSkuAvBom_PA : masterSkuAvBom_PAList) {
			String buFamily = null;
			Set<String> avsList = new TreeSet<String>();
			String sku = masterSkuAvBom_PA.getSku();
			if (skuAvsMap.containsKey(sku)) {
				avsList.addAll(skuAvsMap.get(sku));
			}
			avsList.add(masterSkuAvBom_PA.getAv());
			skuAvsMap.put(sku, avsList);
			if (masterSkuAvBom_PA.getFamily() != null && masterSkuAvBom_PA.getFamily().length() > 0
					&& masterSkuAvBom_PA.getBu() != null && masterSkuAvBom_PA.getBu().length() > 0) {
				buFamily = masterSkuAvBom_PA.getFamily() + "::" + masterSkuAvBom_PA.getBu();
				skuFamilyBUMap.put(sku, buFamily);
				List<String> avList = new ArrayList<String>();
				if (buFamilyAvMap.containsKey(buFamily)) {
					avList.addAll(buFamilyAvMap.get(buFamily));
				}
				avList.add(masterSkuAvBom_PA.getAv());
				buFamilyAvMap.put(buFamily, avList);
			}
		}
		Map<String, Double> ltMap = new HashMap<String, Double>();
		for (PNLT pnlt : pnltList) {
			ltMap.put(pnlt.getPartId(), pnlt.getLeadtime());
		}

		Map<String, List<AvPartFinalQtyPer>> skuPartsMap = new HashMap<String, List<AvPartFinalQtyPer>>();
		Map<String, List<FlexBom>> skuFlexBomMap = new HashMap<String, List<FlexBom>>();
		Map<String, List<AvPartFinalQtyPer>> avPartsMap = new HashMap<String, List<AvPartFinalQtyPer>>();
		Map<String, List<FlexBom>> avFlexMap = new HashMap<String, List<FlexBom>>();

		for (AvPartFinalQtyPer avPartFinalQtyPer : avPartFinalQtyPerList) {
			List<AvPartFinalQtyPer> partList = new ArrayList<AvPartFinalQtyPer>();
			if (avPartsMap.containsKey(avPartFinalQtyPer.getAv())) {
				partList.addAll(avPartsMap.get(avPartFinalQtyPer.getAv()));
			}
			partList.add(avPartFinalQtyPer);
			avPartsMap.put(avPartFinalQtyPer.getAv(), partList);
		}
		List<AvAvbail> avAvbailList = new ArrayList<AvAvbail>();
		for (FlexBom flexBom : flexBomList) {
			List<FlexBom> partList = new ArrayList<FlexBom>();
			AvAvbail avAvbail = new AvAvbail();
			if (flexBom.getAVBL_SCREEN_COMMODITY().equals("Base Unit")
					&& flexBom.getGROUP_SECONDARY().equals("CPU")) {
				avAvbail.setComodity("CPU");
				avAvbail.setComodityDesc(flexBom.getSCREEN_COMMODITY_GROUP_DESC());
				avAvbail.setAvId(flexBom.getAv());
				avAvbail.setAvbail(-1);
			} else if (flexBom.getLevel() == 1 && !flexBom.getAVBL_SCREEN_COMMODITY().equals("Base Unit")) {
				avAvbail.setComodity(flexBom.getAVBL_SCREEN_COMMODITY());
				avAvbail.setComodityDesc(flexBom.getSCREEN_COMMODITY_GROUP_DESC());
				avAvbail.setAvId(flexBom.getAv());
				avAvbail.setAvbail(-1);				
			}
			if (flexBom.getLevel() == 1 && flexBom.getAVBL_SCREEN_COMMODITY().equals("Motherboard")) {
				avAvbail.setComodity("Motherboard");
				avAvbail.setComodityDesc(flexBom.getSCREEN_COMMODITY_GROUP_DESC());
				avAvbail.setAvId(flexBom.getAv());
				avAvbail.setAvbail(-1);
			}
			if(avAvbail.getAvId() != null && avAvbail.getAvId().length() > 0)
				avAvbailList.add(avAvbail);
			if (avFlexMap.containsKey(flexBom.getAv())) {
				partList.addAll(avFlexMap.get(flexBom.getAv()));
			}
			partList.add(flexBom);
			avFlexMap.put(flexBom.getAv(), partList);
			if (flexBom.getLevel() == 1) {
				List<String> avList = new ArrayList<String>();
				if (comodityAvListMap.containsKey(flexBom.getAVBL_SCREEN_COMMODITY())) {
					avList.addAll(comodityAvListMap.get(flexBom.getAVBL_SCREEN_COMMODITY()));
				}
				avList.add(flexBom.getAv());
				comodityAvListMap.put(flexBom.getAVBL_SCREEN_COMMODITY(), avList);
				avComodityMap.put(flexBom.getAv(), flexBom.getAVBL_SCREEN_COMMODITY());
			}
		}

		for (Entry<String, Set<String>> skuAvs : skuAvsMap.entrySet()) {
			if (skuAvs.getValue().contains("Total"))
				continue;
			List<FlexBom> tempFlexBomList = new ArrayList<FlexBom>();
			List<AvPartFinalQtyPer> tempAvPartFinalQtyPerList = new ArrayList<AvPartFinalQtyPer>();
			for (String av : skuAvs.getValue()) {
				if (avPartsMap.containsKey(av))
					tempAvPartFinalQtyPerList.addAll(avPartsMap.get(av));
				if (avFlexMap.containsKey(av))
					tempFlexBomList.addAll(avFlexMap.get(av));
			}
			skuPartsMap.put(skuAvs.getKey(), tempAvPartFinalQtyPerList);
			skuFlexBomMap.put(skuAvs.getKey(), tempFlexBomList);
		}

		// Code to make a map of alternative parts for all parts.
		Map<String, List<String>> priAltMap = new HashMap<String, List<String>>();
		for (PriAltBom priAltBom : priAltList) {
			String id = priAltBom.getPriPart();
			List<String> tempList = new ArrayList<String>();
			if (priAltMap.containsKey(id))
				tempList.addAll(priAltMap.get(id));
			tempList.add(priAltBom.getAltPart());
			priAltMap.put(id, tempList);
		}

		// Code to make a doi map with partnumber and quantity
		Map<String, Double> mainDoiMap = new HashMap<String, Double>();
		for (DOInventory doInventory : doiList) {
			mainDoiMap.put(doInventory.getPartId(), (double) doInventory.getQuantity());
		}
		Map<String, List<OverAllSkusAvailability>> buFamilySkuMap = new HashMap<String, List<OverAllSkusAvailability>>();
		LOG.info("Finished all the mappings");
		List<OverAllSkusAvailability> overAllSkusAvailabilityList = new ArrayList<OverAllSkusAvailability>();

		for (Map.Entry<String, String> skuFamilyBU : skuFamilyBUMap.entrySet()) {
			String sku = skuFamilyBU.getKey();
			String[] familyBU = skuFamilyBU.getValue().split("::");
			if (skuPartsMap.get(sku) != null && skuFlexBomMap.get(sku) != null) {
				Map<String, Double> skuDoiMap = new HashMap<String, Double>();
				skuDoiMap.putAll(mainDoiMap);
				OverAllSkusAvailability overAllSkusAvailability = new OverAllSkusAvailability();
				overAllSkusAvailability.setSkuId(sku);
				overAllSkusAvailability.setFamily(familyBU[0]);
				overAllSkusAvailability.setBu(familyBU[1]);
				overAllSkusAvailability.setNewOrderDeliveryWeeks((int) skuLeadtime(skuPartsMap.get(sku), ltMap));
				if(skuDesc.containsKey(sku))
					overAllSkusAvailability.setSkuDesc(skuDesc.get(sku));
				overAllSkusAvailability.setConfiguration(
						getCommodityDesc(new ArrayList<String>(skuAvsMap.get(sku)), avAvbailList));
				double skuQty = generateskuQuantity(sku, skuDoiMap, priAltMap, skuPartsMap.get(sku),
						skuFlexBomMap.get(sku), avPartsMap,avAvbailList, overAllSkusAvailability);
				overAllSkusAvailability.setAvailability(skuQty + (mainDoiMap.get(sku) != null ? mainDoiMap.get(sku) : 0));
				inventoryReduction(skuQty, skuFlexBomMap.get(sku), skuDoiMap,new ArrayList<String>(skuAvsMap.get(sku)));
				setPipeLineData(openOrderPipeLineList,skuDoiMap,overAllSkusAvailability,priAltMap,skuPartsMap.get(sku), skuFlexBomMap.get(sku), avPartsMap,new ArrayList<String>(skuAvsMap.get(sku)));				
				overAllSkusAvailabilityList.add(overAllSkusAvailability);
				List<OverAllSkusAvailability> buOverAllSkusAvailabilityList = new ArrayList<OverAllSkusAvailability>();
				List<OverAllSkusAvailability> buFamilyOverAllSkusAvailabilityList = new ArrayList<OverAllSkusAvailability>();
				if (buFamilySkuMap.containsKey(overAllSkusAvailability.getBu()))
					buOverAllSkusAvailabilityList.addAll(buFamilySkuMap.get(overAllSkusAvailability.getBu()));
				buOverAllSkusAvailabilityList.add(overAllSkusAvailability);
				buFamilySkuMap.put(overAllSkusAvailability.getBu(), buOverAllSkusAvailabilityList);
				if (buFamilySkuMap
						.containsKey(overAllSkusAvailability.getBu() + "::" + overAllSkusAvailability.getFamily()))
					buFamilyOverAllSkusAvailabilityList.addAll(buFamilySkuMap
							.get(overAllSkusAvailability.getBu() + "::" + overAllSkusAvailability.getFamily()));
				buFamilyOverAllSkusAvailabilityList.add(overAllSkusAvailability);
				buFamilySkuMap.put(overAllSkusAvailability.getBu() + "::" + overAllSkusAvailability.getFamily(),
						buFamilyOverAllSkusAvailabilityList);
				logger.info("-------Sku and Avilability------------;" + overAllSkusAvailabilityList.size() + ":" + sku
						+ "=" + skuQty);
				skuDoiMap.clear();
			} else {
				// logger.info("-------Failed------------;" + sku
				// +"=="+doiMap.get(sku));
			}

		}
//		List<BUFamilyAvailability> buFamilyAvailabilityList = new ArrayList<BUFamilyAvailability>();
//		for (Map.Entry<String, List<OverAllSkusAvailability>> buFamilySku : buFamilySkuMap.entrySet()) {
//			Map<String, Double> tempDoiMap = new HashMap<String, Double>();
//			tempDoiMap.putAll(mainDoiMap);
//			List<OverAllSkusAvailability> tempOverAllSkusAvailabilityList = buFamilySku.getValue();
//			Collections.sort(tempOverAllSkusAvailabilityList, new Comparator<OverAllSkusAvailability>() {
//				public int compare(OverAllSkusAvailability o1, OverAllSkusAvailability o2) {
//					return ((Double) o2.getAvailability()).compareTo(o1.getAvailability());
//				}
//			});
//			double buFamilyAvail = 0.0;
//			for (OverAllSkusAvailability overAllSkusAvailability : tempOverAllSkusAvailabilityList) {
//				double skuQty = generateskuQuantity(overAllSkusAvailability.getSkuId(), tempDoiMap, priAltMap,
//						skuPartsMap.get(overAllSkusAvailability.getSkuId()),
//						skuFlexBomMap.get(overAllSkusAvailability.getSkuId()), avPartsMap, null,null);
//				inventoryReduction(skuQty, skuFlexBomMap.get(overAllSkusAvailability.getSkuId()), tempDoiMap,
//						new ArrayList<String>(skuAvsMap.get(overAllSkusAvailability.getSkuId())));
//				buFamilyAvail = buFamilyAvail + skuQty + (mainDoiMap.get(overAllSkusAvailability.getSkuId()) != null
//						? mainDoiMap.get(overAllSkusAvailability.getSkuId()) : 0);
//				if(skuQty>0)
//					logger.info(overAllSkusAvailability.getSkuId()+"============="+skuQty + (mainDoiMap.get(overAllSkusAvailability.getSkuId())));
//			}
//			BUFamilyAvailability buFamilyAvailability = new BUFamilyAvailability();
//			if (buFamilySku.getKey().contains("::")) {
//				String[] buFamily = buFamilySku.getKey().split("::");
//				buFamilyAvailability.setBu(buFamily[0]);
//				buFamilyAvailability.setFamily(buFamily[1]);
//				buFamilyAvailability.setFamilyAvail(buFamilyAvail);
//				buFamilyAvailability.setBuAvail(0.0);
//				logger.info(buFamilySku.getKey() + "==========" + buFamilyAvail);
//			} else {
//				buFamilyAvailability.setBu(buFamilySku.getKey());
//				buFamilyAvailability.setFamily("");
//				buFamilyAvailability.setFamilyAvail(0.0);
//				buFamilyAvailability.setBuAvail(buFamilyAvail);
//				logger.info(buFamilySku.getKey() + "==========" + buFamilyAvail);
//			}
//			buFamilyAvailabilityList.add(buFamilyAvailability);
//			tempOverAllSkusAvailabilityList.clear();
//		}
//		List<OverAllSkusAvailability> overAllSkuConfigsAvailabilityList = new ArrayList<OverAllSkusAvailability>();
//		List<SkuSimilarityMatrix> skuSimilarityMatrixList = new ArrayList<SkuSimilarityMatrix>();
//		int skuCount = 0;
//		for (OverAllSkusAvailability overAllSkusAvailability : overAllSkusAvailabilityList) {
//			if(overAllSkusAvailability.getSkuId().equalsIgnoreCase("W5W99LA#AC4"))
//				LOG.info();;
//			skuCount++;
//			List<String> finalAvList = new ArrayList<String>();
//			List<AvAvbail> avList = new ArrayList<AvAvbail>();
//			List<String> shortageList = new ArrayList<String>();
//			List<String> minAvList = new ArrayList<String>();
//			List<String> comodityList = new ArrayList<String>();
//			List<String> completeAvList = new ArrayList<String>();
//
//			if (overAllSkusAvailability.getShortage().size() > 0) {
//				for (String av : overAllSkusAvailability.getShortage()) {
//					shortageList.add(av.substring(0, av.indexOf(":")));
//				}
//			}
//			if (shortageList.size() > 4)
//				continue;
//			for (String av : overAllSkusAvailability.getConfiguration()) {
//				completeAvList.add(av.substring(0, av.indexOf(":")));
//				comodityList.add(av.substring(0, av.indexOf(":")));
//				if (avAVBLMap.containsKey(av.substring(0, av.indexOf(":")))) {
//					AvAvbail avAvbail = new AvAvbail();
//					avAvbail.setAvId(av.substring(0, av.indexOf(":")));
//					avAvbail.setAvbail(avAVBLMap.get(avAvbail.getAvId()));
//					avList.add(avAvbail);
//					finalAvList.add(avAvbail.getAvId());
//				}
//			}
//			Collections.sort(avList, new Comparator<AvAvbail>() {
//				public int compare(AvAvbail o1, AvAvbail o2) {
//					return ((Double) o1.getAvbail()).compareTo(o2.getAvbail());
//				}
//			});
//			for (AvAvbail avAvbail : avList) {
//				minAvList.add(avAvbail.getAvId());
//				if (minAvList.size() == 4)
//					break;
//			}
//
//			HashMap<String, List<AvAvbail>> minAvMap = new HashMap<String, List<AvAvbail>>();
//			for (String minAv : minAvList) {
//				List<AvAvbail> tempAvlist = new ArrayList<AvAvbail>();
//				List<String> avComoditiesList = comodityAvListMap.get(avComodityMap.get(minAv));
//				List<String> buFamilyAvList = buFamilyAvMap
//						.get(overAllSkusAvailability.getFamily() + "::" + overAllSkusAvailability.getBu());
//				for (String comodityAv : avComoditiesList) {
//					if (buFamilyAvList != null && buFamilyAvList.contains(comodityAv)
//							&& avAVBLMap.containsKey(comodityAv) && !completeAvList.contains(comodityAv) && avAVBLMap.get(comodityAv) > 0) {
////							&& avAVBLMap.get(comodityAv) > overAllSkusAvailability.getAvailability() && !completeAvList.contains(comodityAv)) {
//						AvAvbail avAvbail = new AvAvbail();
//						avAvbail.setAvId(comodityAv);
//						avAvbail.setAvbail(avAVBLMap.get(comodityAv));
//						tempAvlist.add(avAvbail);
//					}
//				}
//				Collections.sort(tempAvlist, new Comparator<AvAvbail>() {
//					public int compare(AvAvbail o1, AvAvbail o2) {
//						return ((Double) o2.getAvbail()).compareTo(o1.getAvbail());
//					}
//				});
//				minAvMap.put(minAv, tempAvlist);
//			}
//			if (shortageList.size() > 0) {
//				boolean hasAltAvs = true;
//				List<String> shrtFinalAvList = new ArrayList<String>();
//				shrtFinalAvList.addAll(finalAvList);
//				List<List<String>> altAvList = new ArrayList<List<String>>();
//				for (Map.Entry<String, List<AvAvbail>> minAv : minAvMap.entrySet()) {
//					shrtFinalAvList.remove(minAv.getKey());
//					comodityList.remove(minAv.getKey());
//					List<String> tempList = new ArrayList<String>();
//					for (AvAvbail avAvbail : minAv.getValue()) {
//							tempList.add(avAvbail.getAvId());
//						if (tempList.size() >= 3)
//							break;
//					}
//					if (tempList.isEmpty())
//						hasAltAvs = false;
//					altAvList.add(tempList);
//					if (shortageList.size() == altAvList.size())
//						break;
//				}
//				if (hasAltAvs) {
//					ArrayList<String> combinations = new ArrayList<String>();
//					GeneratePermutations(altAvList, combinations, 0, "");
//					int count = 1;
//					for (String combination : combinations) {
//						String[] altAvs = combination.split("::");
//						for (String altAv : altAvs){
//							shrtFinalAvList.add(altAv);
//							comodityList.add(altAv);
//						}						
//						List<Double> avQtyList = new ArrayList<Double>();
//						for (String av : shrtFinalAvList)
//							avQtyList.add(avAVBLMap.containsKey(av) ? avAVBLMap.get(av) : 0);
//						double availab= avQtyList.size() > 0 ? Collections.min(avQtyList) : 0;						
//						if (availab > 0) {
//							OverAllSkusAvailability overAllSkuConfigsAvailability = new OverAllSkusAvailability();
//							overAllSkuConfigsAvailability.setAvailability(availab);
//							overAllSkuConfigsAvailability.setBu(overAllSkusAvailability.getBu());
//							overAllSkuConfigsAvailability.setFamily(overAllSkusAvailability.getFamily());
//							overAllSkuConfigsAvailability
//									.setNewOrderDeliveryWeeks(overAllSkusAvailability.getNewOrderDeliveryWeeks());
//							overAllSkuConfigsAvailability.setSkuId(overAllSkusAvailability.getSkuId() + "#Config#" + count);
//							overAllSkuConfigsAvailability.setSkuDesc("Alternative "+count);
//							List<AvPartFinalQtyPer> skuPartsList = new ArrayList<AvPartFinalQtyPer>();
//							List<FlexBom> skuFlexBom = new ArrayList<FlexBom>();
//							for(String av:shrtFinalAvList){
//								if(avPartsMap.containsKey(av))
//									skuPartsList.addAll(avPartsMap.get(av));
//								if(avFlexMap.containsKey(av))
//									skuFlexBom.addAll(avFlexMap.get(av));
//							}
//							inventoryReduction(availab, skuFlexBom, mainDoiMap,comodityList);
//							
//							setPipeLineData(openOrderPipeLineList,mainDoiMap,overAllSkuConfigsAvailability,priAltMap,skuPartsList,skuFlexBom, avPartsMap,comodityList);				
//
//							SkuSimilarityMatrix skuSimilarityMatrix = new SkuSimilarityMatrix();
//							skuSimilarityMatrix.setActualSku(overAllSkusAvailability.getSkuId());
//							skuSimilarityMatrix.setMatchedSku(overAllSkuConfigsAvailability.getSkuId());
//							skuSimilarityMatrix.setSimilarityIndex(
//									(double) (comodityList.size() - 4) / (double) comodityList.size());
//							overAllSkuConfigsAvailabilityList.add(overAllSkuConfigsAvailability);
//							overAllSkuConfigsAvailability.setConfiguration(getCommodityDesc(comodityList, avAvbailList));
//							count++;
//							logger.info(skuCount + "===" + overAllSkuConfigsAvailability.getSkuId() + "====="
//									+ overAllSkuConfigsAvailability.getAvailability());
//							skuSimilarityMatrixList.add(skuSimilarityMatrix);
//						}
//						for (String altAv : altAvs){
//							shrtFinalAvList.remove(altAv);
//							comodityList.remove(altAv);
//						}
//					}
//
//				}
//
//			} else {
//				int count = 1;
//				for (int i = 1; i <= 4; i++) {
//					int hasAltAvs = 0;
//					List<String> configFinalAvList = new ArrayList<String>();
//					configFinalAvList.addAll(finalAvList);
//					List<List<String>> altAvList = new ArrayList<List<String>>();
//					for (Map.Entry<String, List<AvAvbail>> minAv : minAvMap.entrySet()) {
//						List<String> tempList = new ArrayList<String>();
//						for (AvAvbail avAvbail : minAv.getValue()) {
//							if(!(completeAvList.contains(minAv.getValue())))
//								tempList.add(avAvbail.getAvId());
//							if (tempList.size() >= 3)
//								break;
//						}
//						if (!tempList.isEmpty()) {
//							configFinalAvList.remove(minAv.getKey());
//							comodityList.remove(minAv.getKey());
//							hasAltAvs++;
//						}
//
//						altAvList.add(tempList);
//						if (altAvList.size() == i)
//							break;
//					}
//					if (hasAltAvs == i) {
//						ArrayList<String> combinations = new ArrayList<String>();
//						GeneratePermutations(altAvList, combinations, 0, "");
//						for (String combination : combinations) {
//							String[] altAvs = combination.split("::");
//							for (String altAv : altAvs) {
//								if (altAv.length() > 0){
//									configFinalAvList.add(altAv);
//									comodityList.add(altAv);
//								}
//							}
//							List<Double> avQtyList = new ArrayList<Double>();
//							for (String av : configFinalAvList)
//								avQtyList.add(avAVBLMap.containsKey(av) ? avAVBLMap.get(av) : 0);
//							double availab = avQtyList.size() > 0 ? Collections.min(avQtyList) : 0;												
//							if (availab > 0) {
//								OverAllSkusAvailability overAllSkuConfigsAvailability = new OverAllSkusAvailability();							
//								overAllSkuConfigsAvailability.setAvailability(availab);		
//								overAllSkuConfigsAvailability.setBu(overAllSkusAvailability.getBu());
//								overAllSkuConfigsAvailability.setFamily(overAllSkusAvailability.getFamily());
//								overAllSkuConfigsAvailability
//										.setNewOrderDeliveryWeeks(overAllSkusAvailability.getNewOrderDeliveryWeeks());
//								overAllSkuConfigsAvailability
//										.setSkuId(overAllSkusAvailability.getSkuId() + "#Config#" + count);
//								overAllSkuConfigsAvailability.setSkuDesc("Alternative "+count);
//								List<AvPartFinalQtyPer> skuPartsList = new ArrayList<AvPartFinalQtyPer>();
//								List<FlexBom> skuFlexBom = new ArrayList<FlexBom>();
//								for(String av:configFinalAvList){
//									if(avPartsMap.containsKey(av))
//										skuPartsList.addAll(avPartsMap.get(av));
//									if(avFlexMap.containsKey(av))
//										skuFlexBom.addAll(avFlexMap.get(av));
//								}
//								inventoryReduction(availab, skuFlexBom, mainDoiMap,comodityList);
//								setPipeLineData(openOrderPipeLineList,mainDoiMap,overAllSkuConfigsAvailability,priAltMap,skuPartsList,skuFlexBom,avPartsMap,comodityList);				
//								overAllSkuConfigsAvailability
//								.setConfiguration(getCommodityDesc(comodityList, avAvbailList));
//								SkuSimilarityMatrix skuSimilarityMatrix = new SkuSimilarityMatrix();
//								skuSimilarityMatrix.setActualSku(overAllSkusAvailability.getSkuId());
//								skuSimilarityMatrix.setMatchedSku(overAllSkuConfigsAvailability.getSkuId());
//								skuSimilarityMatrix.setSimilarityIndex(
//										(double) (comodityList.size() - i) / (double) comodityList.size());
//								overAllSkuConfigsAvailabilityList.add(overAllSkuConfigsAvailability);
//								count++;
//								logger.info(skuCount + "===" + overAllSkuConfigsAvailability.getSkuId() + "====="
//										+ overAllSkuConfigsAvailability.getAvailability());
//								skuSimilarityMatrixList.add(skuSimilarityMatrix);
//							}
//							for (String altAv : altAvs) {
//								if (altAv.length() > 0){
//									configFinalAvList.remove(altAv);
//									comodityList.remove(altAv);
//								}
//							}
//						}
//					}
//
//				}
//			}
//		}
		for(AvAvbail avAvbail:avAvbailList){
			if(avAVBLMap.containsKey(avAvbail.getAvId()))
				avAvbail.setAvbail(avAVBLMap.get(avAvbail.getAvId()));	
		}
		skuAvailDao.saveOverAllSkusAvailability(overAllSkusAvailabilityList);
//		skuAvailDao.saveBUFamilyAvailability(buFamilyAvailabilityList);
//		skuAvailDao.saveOverAllSkuConfigssAvailability(overAllSkuConfigsAvailabilityList);
//		skuAvailDao.saveSkuSimilarityMatrix(skuSimilarityMatrixList);
//		skuAvailDao.savAvAVBAIL(avAvbailList);
	}

	void GeneratePermutations(List<List<String>> Lists, List<String> result, int depth, String current) {
		if (depth == Lists.size()) {
			result.add(current);
			return;
		}

		for (int i = 0; i < Lists.get(depth).size(); ++i) {
			GeneratePermutations(Lists, result, depth + 1, current + "::" + Lists.get(depth).get(i));
		}
	}

	public Double generateskuQuantity(String sku, Map<String, Double> doiMap, Map<String, List<String>> priAltMap,
			List<AvPartFinalQtyPer> skuAllParts, List<FlexBom> pa_Super_flexBom,
			Map<String, List<AvPartFinalQtyPer>> avPartsMap, List<AvAvbail> avAvbailList, OverAllSkusAvailability overAllSkusAvailability) {

		// LOG.info("-------------------the sku all parts size
		// is--------------"+skuAllParts.size());
		Map<String, Double> partQtyMap = new HashMap<String, Double>();
		Map<String, Double> partMilageMap = new HashMap<String, Double>();
		Map<String, Double> partInventoryMap = new HashMap<String, Double>();
		
		// -------------All parts under sku with total FinalQuantiryPer
		// iteration----------------------------//
		for (AvPartFinalQtyPer avPartFinalQtyPer : skuAllParts) {
			String val = avPartFinalQtyPer.getPartNumber();
			double qty;
			if (partQtyMap.containsKey(val)) {
				qty = partQtyMap.get(val) + avPartFinalQtyPer.getFinalQty();
				partQtyMap.put(val, qty);
			} else {
				qty = avPartFinalQtyPer.getFinalQty();
				partQtyMap.put(val, qty);
			}
		}
		for (Entry<String, Double> partQtyPer : partQtyMap.entrySet()) {
			String partId = partQtyPer.getKey();
			// ------------priparts which came newly for sku will execute this
			// logic-------------
			if (!partMilageMap.containsKey(partId)) {
				double doiQty = 0;
				double totalQtyPer = partQtyPer.getValue();
				if (doiMap.containsKey(partId)) {
					doiQty = doiMap.get(partId);
				}
				List<String> priParts = new ArrayList<String>();				
				if (priAltMap.containsKey(partId)) {
					List<String> altPartList = priAltMap.get(partId);
					for (String altPart : altPartList) {
						if (doiMap.containsKey(altPart))
							doiQty = doiQty + doiMap.get(altPart);
						if (partQtyMap.containsKey(altPart)) {
							totalQtyPer = totalQtyPer + partQtyMap.get(altPart);
							priParts.add(altPart);
						} else {
							if (doiMap.containsKey(altPart))
								doiMap.put(altPart, 0.0);
						}
					}
				}
				double milage = 0;
				if (totalQtyPer > 0)
					milage = (doiQty / totalQtyPer);
				double partQty = milage * partQtyPer.getValue();
				partInventoryMap.put(partId, partQty);
				partMilageMap.put(partId, milage);
				if (doiMap.containsKey(partId))
					doiMap.put(partId, partQty);

				for (String priPart : priParts) {
					partQty = milage * partQtyMap.get(priPart);
					partInventoryMap.put(priPart, partQty);
					partMilageMap.put(priPart, milage);
					if (doiMap.containsKey(priPart))
						doiMap.put(priPart, partQty);
				}
			} // map ending.................
		} // for ending for sku parts parts............

		// for(Entry<String, Double> entry:partInventoryMap.entrySet()){
		// logger.info("Milage
		// map-------------"+entry.getKey()+"\t"+entry.getValue());
		// }
		//
		// ---------------getting super bom data--------------------//
		Map<String, List<FlexBom>> avpartQtyMap = new HashMap<String, List<FlexBom>>();
		for (FlexBom flexBom : pa_Super_flexBom) {
			String input = flexBom.getAv();
			List<FlexBom> temp = new ArrayList<FlexBom>();
			if (avpartQtyMap.containsKey(input))
				temp.addAll(avpartQtyMap.get(input));
			temp.add(flexBom);
			avpartQtyMap.put(input, temp);
		}
		List<String> avList = new ArrayList<String>(avpartQtyMap.keySet());
		List<Double> minskuQtyList = new ArrayList<Double>();
		List<String> shortageAvList = new ArrayList<String>();
		for (int v = 0; v < avList.size(); v++) {
			allocateQty(avpartQtyMap, avList, partInventoryMap, v, avPartsMap);
			if (finalReversedMap.get(avList.get(v) + "=" + 1) != null) {
				minskuQtyList.add(finalReversedMap.get(avList.get(v) + "=" + 1));
				if (finalReversedMap.get(avList.get(v) + "=" + 1) <= 0)
					shortageAvList.add(avList.get(v));
//				 logger.info(avList.get(v) + "======" +finalReversedMap.get(avList.get(v) + "=" + 1));
				if (overAllSkusAvailability != null) {
					avAVBLMap.put(avList.get(v), finalReversedMap.get(avList.get(v) + "=" + 1));
					
				}
			}

			finalReversedMap.clear();
		} // av iteration ending.................
		if (overAllSkusAvailability != null && avAvbailList != null)
			overAllSkusAvailability.setShortage(getCommodityDesc(shortageAvList, avAvbailList));
		// logger.info(sku+":- "+minskuQtyList.size());
		if (minskuQtyList.size() > 0) {
			return Collections.min(minskuQtyList);
		} else
			return 0.0;
	}

	/**
	 * Allocation logic
	 * 
	 * @param avpartQtyMap
	 * @param avList
	 * @param partInventoryMap
	 * @param v
	 */
	public void allocateQty(Map<String, List<FlexBom>> avpartQtyMap, List<String> avList,
			Map<String, Double> partMilageMap, int v, Map<String, List<AvPartFinalQtyPer>> avPartsMap) {

		List<FlexBom> finalAllocatedList = new ArrayList<FlexBom>();
		List<FlexBom> bomList = avpartQtyMap.get(avList.get(v));
		List<AvPartFinalQtyPer> list = avPartsMap.get(avList.get(v));
		Map<String, Double> partFinalQty = new HashMap<String, Double>();

		for (AvPartFinalQtyPer avPartFinalQtyPer : list) {
			String id = avPartFinalQtyPer.getPartNumber() + "=" + avPartFinalQtyPer.getLevel();
			partFinalQty.put(id, avPartFinalQtyPer.getFinalQty());
		}
		int bomSize = bomList.size();
		for (int c = 0; c < bomSize; c++) {
			FlexBom flexBom = bomList.get(c);
			String key = flexBom.getPartNumber();
			flexBom.setFinalQty(partFinalQty.get(key + "=" + flexBom.getLevel()));
			if (partMilageMap.containsKey(key)) {
				flexBom.setAllocatedQty(partMilageMap.get(key) * flexBom.getFinalQty());
			} else {
				flexBom.setAllocatedQty(0);
			}
			if (flexBom.getFinalQty() > 0)
				flexBom.setQtyPerAv(flexBom.getAllocatedQty() / flexBom.getFinalQty());
			else
				flexBom.setQtyPerAv(0);
			finalAllocatedList.add(flexBom);
		} 
		RollUpLogic(finalAllocatedList, avList.get(v), partFinalQty);

	}

	/**
	 * Roll Up logic calling Roll Down logic
	 * 
	 * @param finalAllocatedList
	 * @param av
	 * @param partFinalQty
	 */
	public void RollUpLogic(List<FlexBom> finalAllocatedList, String av, Map<String, Double> partFinalQty) {
		double avQty = 0;
		String avId = null;
		boolean isNoOfPartsEmpty = false;
		if (finalAllocatedList.size() > 1) {

			Set<Integer> levelSet = new TreeSet<Integer>();
			// LOG.info("--------------before sorting------------");
			for (FlexBom flexBom : finalAllocatedList) {
				// LOG.info(flexBom);
				levelSet.add(flexBom.getLevel());
			}
			// Collections.reverse(finalAllocatedList);
			List<Integer> levelList = new ArrayList<Integer>(levelSet);
			// Collections.reverse(levelList);
			int maxLevel = Collections.max(levelList);
			int levelSize = levelList.size();
			int bomSize = finalAllocatedList.size();

			Map<String, List<Map<String, Double>>> outerMap = new LinkedHashMap<String, List<Map<String, Double>>>();

			Map<String, Double> originalAvQtyPer = new HashMap<String, Double>();

			// ---------------reverse level iteration
			// loop----------------------------//
			for (int count = levelSize - 1; count > 0; count--) {
				List<Map<String, Double>> listMap = new ArrayList<Map<String, Double>>();
				List<Double> noOfParts = new ArrayList<Double>();
				Map<String, Double> innerMap;
				int index = bomSize - 1;
				int breakedLevel = 0;
				boolean flag = false;
				// ----------------bom reverse iteration from
				// bottom--------------------------//
				while (index >= 0) {
					FlexBom innerFlexBom = finalAllocatedList.get(index);
					String part = innerFlexBom.getPartNumber();
					double avQtyPer = innerFlexBom.getQtyPerAv();
					int level = innerFlexBom.getLevel();
					if (level > breakedLevel) {
						if (level == levelList.get(count)) {
							flag = true;
							breakedLevel = 0;
							if (innerFlexBom.getINCLUDE_ON_SCREEN().equalsIgnoreCase("Yes")) {
								if ((count != maxLevel) || innerFlexBom.getMpc().equalsIgnoreCase("P"))
									noOfParts.add(avQtyPer);

							}
							innerMap = new HashMap<String, Double>();
							innerMap.put(part + "=" + level, avQtyPer);
							if (!originalAvQtyPer.containsKey(part + "=" + level))
								originalAvQtyPer.put(part + "=" + level, avQtyPer);
							// avId = part + "=" + level;
							listMap.add(innerMap);
						} else if (flag == true && (level == levelList.get(count - 1))) {
							double min;
							if (noOfParts.size() > 0) {
								min = Collections.min(noOfParts);
								isNoOfPartsEmpty = false;
							} else {
								min = 0.0;
								isNoOfPartsEmpty = true;
							}
							if (levelList.get(count - 1) == 1) {
								// LOG.info("--------------i entered
								// into 1st level-----------------");
								avQty = avQtyPer + Math
										.floor(min / (innerFlexBom.getQtyPer() > 0 ? innerFlexBom.getQtyPer() : 1));
								innerFlexBom.setQtyPerAv(avQtyPer + (min
										/ Math.floor(innerFlexBom.getQtyPer() > 0 ? innerFlexBom.getQtyPer() : 1)));
								avId = part + "=" + level;
								// System.out.println("-------------------avid
								// and qty
								// are------------------------"+avId+"\t"+avQty);
							} else {
								if (!originalAvQtyPer.containsKey(part + "=" + level))
									originalAvQtyPer.put(part + "=" + level, avQtyPer);
								// avId = part + "=" + level;
								innerFlexBom.setQtyPerAv(avQtyPer + Math
										.floor(min / (innerFlexBom.getQtyPer() > 0 ? innerFlexBom.getQtyPer() : 1)));
							}

							finalAllocatedList.set(index, innerFlexBom);
							outerMap.put(part + "=" + level, listMap);
							breakedLevel = level;
							listMap = new ArrayList<Map<String, Double>>();
							noOfParts = new ArrayList<Double>();
							flag = false;
						}
					}
					index--;
				}
			} // roll up ending....................
			if (!isNoOfPartsEmpty)
				finalReversedMap.put(avId, avQty);

		} else {
			if (finalAllocatedList.get(0).getINCLUDE_ON_SCREEN().equalsIgnoreCase("Yes"))
				finalReversedMap.put(finalAllocatedList.get(0).getPartNumber() + "=" + 1, 0.0);
		}

	}

	public void inventoryReduction(double skuQty, List<FlexBom> pa_Super_flexBom, Map<String, Double> doiMap,
			List<String> avs) {
		HashMap<String, List<FlexBom>> avFlexBomMap = new HashMap<String, List<FlexBom>>();
		for (FlexBom flexBom : pa_Super_flexBom) {
			List<FlexBom> tempFlexBomList = new ArrayList<FlexBom>();
			if (avFlexBomMap.containsKey(flexBom.getAv()))
				tempFlexBomList.addAll(avFlexBomMap.get(flexBom.getAv()));
			tempFlexBomList.add(flexBom);
			avFlexBomMap.put(flexBom.getAv(), tempFlexBomList);

		}
		for (String av : avs) {
			HashMap<Integer, Double> levelQty = new HashMap<Integer, Double>();
			if (doiMap.containsKey(av)) {
				double reducedInventory = skuQty - doiMap.get(av);
				if (reducedInventory >= 0) {
					doiMap.put(av, 0.0);
					levelQty.put(1, reducedInventory);
				} else {
					doiMap.put(av, Math.abs(reducedInventory));
					levelQty.put(1, 0.0);
				}
			} else {
				levelQty.put(1, skuQty);
			}
			for (FlexBom flexBom : (avFlexBomMap.get(av) != null ? avFlexBomMap.get(av) : new ArrayList<FlexBom>())) {
				if (flexBom.getLevel() == 1)
					continue;
				if (flexBom.getINCLUDE_ON_SCREEN().equalsIgnoreCase("Yes")) {
					double usedInventory = levelQty.get(flexBom.getLevel() - 1) * flexBom.getQtyPer();
					if (doiMap.containsKey(flexBom.getPartNumber())) {
						double remaningQty = usedInventory - doiMap.get(flexBom.getPartNumber());
						if (remaningQty >= 0) {
							doiMap.put(flexBom.getPartNumber(), 0.0);
							levelQty.put(flexBom.getLevel(), remaningQty);
						} else {
							doiMap.put(flexBom.getPartNumber(), Math.abs(remaningQty));
							levelQty.put(flexBom.getLevel(), 0.0);
						}
					} else {
						levelQty.put(flexBom.getLevel(), levelQty.get(flexBom.getLevel() - 1));
					}
				} else {
					levelQty.put(flexBom.getLevel(), levelQty.get(flexBom.getLevel() - 1));
				}
			}
		}
	}

	/**
	 * RollDowm recurcive methos
	 * 
	 * @param outerMap
	 * @param part
	 * @param originalAvQtyPer
	 * @param parentQty
	 * @param partFinalQty
	 */
	public void rollDownLogic(Map<String, List<Map<String, Double>>> outerMap, String part,
			Map<String, Double> originalAvQtyPer, double parentQty, Map<String, Integer> partFinalQty) {
		if (outerMap.containsKey(part)) {
			List<Map<String, Double>> list = outerMap.get(part);
			for (Map<String, Double> map : list) {
				for (Entry<String, Double> innerMapValue : map.entrySet()) {
					String presentPart = innerMapValue.getKey();
					double origValue = originalAvQtyPer.get(presentPart);
					double summedValue = innerMapValue.getValue();
					double res = parentQty - origValue;
					if (res > 0) {
						innerMapValue.setValue(0.0);
						if (finalReversedMap.containsKey(presentPart))
							finalReversedMap.put(presentPart, finalReversedMap.get(presentPart) + 0.0);
						else
							finalReversedMap.put(presentPart, 0.0);
						double finalres = summedValue - (origValue + res);
						rollDownLogic(outerMap, presentPart, originalAvQtyPer, finalres, partFinalQty);

					} else if (res < 0) {
						double value = partFinalQty.get(presentPart) * (-(res));
						if (finalReversedMap.containsKey(presentPart))
							finalReversedMap.put(presentPart, finalReversedMap.get(presentPart) + value);
						else
							finalReversedMap.put(presentPart, value);
						innerMapValue.setValue(value);
						double finalres = summedValue - (origValue);
						rollDownLogic(outerMap, presentPart, originalAvQtyPer, finalres, partFinalQty);
					} else {
						innerMapValue.setValue(0.0);
						if (finalReversedMap.containsKey(presentPart))
							finalReversedMap.put(presentPart, finalReversedMap.get(presentPart) + 0.0);
						else
							finalReversedMap.put(presentPart, 0.0);
						double finalres = summedValue - (origValue);
						rollDownLogic(outerMap, presentPart, originalAvQtyPer, finalres, partFinalQty);
					}

				}
			}
		}

	}

//	private List<String> getCommodityDesc(List<String> avs, List<FlexBom> flexBomList) {
//		List<String> comodities = new ArrayList<String>();
//		comodities.add("CPU");
//		comodities.add("Base Unit");
//		comodities.add("Memory");
//		comodities.add("HDD");
//		comodities.add("SSD");
//		comodities.add("LCD / LED");
//		comodities.add("AC Adapter / Power Supply");
//		comodities.add("Battery");
//		comodities.add("Wireless Card");
//		comodities.add("Graphic Card");
//		HashMap<String, String> descMap = new HashMap<String, String>();
//		for (FlexBom flexBom : flexBomList) {
//			if (avs.contains(flexBom.getAv())) {
//				if (flexBom.getAVBL_SCREEN_COMMODITY().equals("Base Unit")
//						&& flexBom.getGROUP_SECONDARY().equals("CPU")) {
//					descMap.put("CPU", flexBom.getAv() + ": " + flexBom.getGROUP_SECONDARY_DESC());
//				} else if (flexBom.getLevel() == 1 && !flexBom.getAVBL_SCREEN_COMMODITY().equals("Base Unit")) {
//					descMap.put(flexBom.getAVBL_SCREEN_COMMODITY(),
//							flexBom.getAv() + ": " + flexBom.getSCREEN_COMMODITY_GROUP_DESC());
//				}
//			}
//			if (flexBom.getLevel() == 1 && flexBom.getAVBL_SCREEN_COMMODITY().equals("Motherboard")
//					&& avs.contains(flexBom.getAv())) {
//				descMap.put("Motherboard", flexBom.getAv() + ": " + flexBom.getSCREEN_COMMODITY_GROUP_DESC());
//			}
//		}
//		if (!(descMap.get("CPU") != null) && descMap.get("Motherboard") != null) {
//			descMap.put("CPU", descMap.get("Motherboard"));
//			descMap.remove("Motherboard");
//		}
//		List<String> descList = new ArrayList<String>();
//		for (String commodity : comodities) {
//			if (descMap.containsKey(commodity)) {
//				descList.add(descMap.get(commodity));
//				descMap.remove(commodity);
//			}
//		}
//		for (Map.Entry<String, String> entry : descMap.entrySet()) {
//			descList.add(entry.getValue());
//		}
//		return descList;
//
//	}
	
	private List<String> getCommodityDesc(List<String> avs, List<AvAvbail> avAvbailList) {
		List<String> comodities = new ArrayList<String>();
		comodities.add("CPU");
		comodities.add("Base Unit");
		comodities.add("Memory");
		comodities.add("HDD");
		comodities.add("SSD");
		comodities.add("LCD / LED");
		comodities.add("AC Adapter / Power Supply");
		comodities.add("Battery");
		comodities.add("Wireless Card");
		comodities.add("Graphic Card");
		HashMap<String, String> descMap = new HashMap<String, String>();
		for (AvAvbail avAvbail : avAvbailList) {
			if (avs.contains(avAvbail.getAvId())) {
				descMap.put(avAvbail.getComodity(), avAvbail.getAvId() + ": " + avAvbail.getComodityDesc());				
			}			
		}
		if (!(descMap.get("CPU") != null) && descMap.get("Motherboard") != null) {
			descMap.put("CPU", descMap.get("Motherboard"));
			descMap.remove("Motherboard");
		}
		List<String> descList = new ArrayList<String>();
		for (String commodity : comodities) {
			if (descMap.containsKey(commodity)) {
				descList.add(descMap.get(commodity));
				descMap.remove(commodity);
			}
		}
		for (Map.Entry<String, String> entry : descMap.entrySet()) {
			descList.add(entry.getValue());
		}
		return descList;
	}
	
	public double skuLeadtime(List<AvPartFinalQtyPer> avPartFinalQtyPerList, Map<String, Double> ltMap) {
		double maxValue = 0;
		for (AvPartFinalQtyPer avPartFinalQtyPer : avPartFinalQtyPerList) {
			if (ltMap.containsKey(avPartFinalQtyPer.getPartNumber())) {
				if (maxValue < ltMap.get(avPartFinalQtyPer.getPartNumber())) {
					maxValue = ltMap.get(avPartFinalQtyPer.getPartNumber());
				}
			}
		}

		return maxValue;
	}

	public void createPipeLineCollection() throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		String currentDate = dateFormat.format(new Date());
		String ordersCollection = "NEW_OPEN_ORDERS";
		List<String> orderedParts = skuAvailDao.getDistinctStringValues(ordersCollection, "item");
		List<String> orderedDates = skuAvailDao.getDistinctStringValues(ordersCollection, "Date");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
		Date latestDate = sdf.parse(orderedDates.get(0));
		for(String orderDate:orderedDates){			
			Date newDate = sdf.parse(orderDate);
			if(newDate.getTime() > latestDate.getTime())
				latestDate = newDate;
		}
		Map<String, List<OpenOrders>> partOrdersMap = new HashMap<String, List<OpenOrders>>();
		List<OpenOrders> orderDataList = skuAvailDao.getOredrData(sdf.format(latestDate));
		for (OpenOrders orderData : orderDataList) {
			ArrayList<OpenOrders> tempOpenOrders = new ArrayList<OpenOrders>();
			if (partOrdersMap.containsKey(orderData.getPartNumber())) {
				tempOpenOrders.addAll(partOrdersMap.get(orderData.getPartNumber()));
			}
			tempOpenOrders.add(orderData);
			partOrdersMap.put(orderData.getPartNumber(), tempOpenOrders);
		}
		Date currentdate = null;
		try {
			currentdate = dateFormat.parse(currentDate);
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		List<OpenOrderPipeLine> finalList = new ArrayList<OpenOrderPipeLine>();
		int count = 0;
		for (String partId : orderedParts) {
			List<OpenOrders> partData = partOrdersMap.get(partId);

			if (partData != null && !partData.isEmpty()) {
				double qty = 0;
				double daysQty15 = 0;
				double daysQty30 = 0;
				double daysQty45 = 0;
				double daysQty60 = 0;
				double daysQty75 = 0;
				double daysQty90 = 0;

				for (OpenOrders order : partData) {
					String conformdelivery = order.getConformDliveryDate();
					if (conformdelivery != null && !conformdelivery.isEmpty()) {
						String conformDevDate = conformdelivery.replaceAll("['/','_','.', ]", "-");
						try {
							Date conformDate = dateFormat.parse(conformDevDate);
							int diffDays = getDifferenceDays(conformDate, currentdate);
							if (diffDays >= 0 && diffDays <= 90) {
								qty = qty + order.getQty();
								if (diffDays <= 15) {
									daysQty15 = daysQty15 + order.getQty();
								}
								if (diffDays > 15 && diffDays <= 30) {
									daysQty30 = daysQty30 + order.getQty();
								}
								if (diffDays > 30 && diffDays <= 45) {
									daysQty45 = daysQty45 + order.getQty();
								}
								if (diffDays > 45 && diffDays <= 60) {
									daysQty60 = daysQty60 + order.getQty();
								}
								if (diffDays > 60 && diffDays <= 75) {
									daysQty75 = daysQty75 + order.getQty();
								}
								if (diffDays > 75 && diffDays <= 90) {
									daysQty90 = daysQty90 + order.getQty();
								}

							}
						} catch (Exception e) {
							String etd = order.getEtdDate();

							if (etd != null && !etd.isEmpty()) {
								String etdDate = etd.replaceAll("['/','_','.', ]", "-");
								try {
									Date etddate = dateFormat.parse(etdDate);
									int diffDays = getDifferenceDays(etddate, currentdate);
									if (diffDays >= 0 && diffDays <= 90) {
										qty = qty + order.getQty();
										if (diffDays <= 15) {
											daysQty15 = daysQty15 + order.getQty();
										}
										if (diffDays > 15 && diffDays <= 30) {
											daysQty30 = daysQty30 + order.getQty();
										}
										if (diffDays > 30 && diffDays <= 45) {
											daysQty45 = daysQty45 + order.getQty();
										}
										if (diffDays > 45 && diffDays <= 60) {
											daysQty60 = daysQty60 + order.getQty();
										}
										if (diffDays > 60 && diffDays <= 75) {
											daysQty75 = daysQty75 + order.getQty();
										}
										if (diffDays > 75 && diffDays <= 90) {
											daysQty90 = daysQty90 + order.getQty();
										}

									}
								} catch (Exception exp) {
								}
							}

						} // catch
					} else {
						String etd = order.getEtdDate();
						if (etd != null && !etd.isEmpty()) {
							String etdDate = etd.replaceAll("['/','_','.', ]", "-");
							try {
								Date etddate = dateFormat.parse(etdDate);
								int diffDays = getDifferenceDays(etddate, currentdate);
								if (diffDays >= 0 && diffDays <= 90) {
									qty = qty + order.getQty();
									if (diffDays <= 15) {
										daysQty15 = daysQty15 + order.getQty();
									}
									if (diffDays > 15 && diffDays <= 30) {
										daysQty30 = daysQty30 + order.getQty();
									}
									if (diffDays > 30 && diffDays <= 45) {
										daysQty45 = daysQty45 + order.getQty();
									}
									if (diffDays > 45 && diffDays <= 60) {
										daysQty60 = daysQty60 + order.getQty();
									}
									if (diffDays > 60 && diffDays <= 75) {
										daysQty75 = daysQty75 + order.getQty();
									}
									if (diffDays > 75 && diffDays <= 90) {
										daysQty90 = daysQty90 + order.getQty();
									}

								}
							} catch (Exception exp) {
							}
						}else{
							try {
								Calendar cal = Calendar.getInstance();
								cal.add(Calendar.DAY_OF_MONTH, 7);
								Date etddate = cal.getTime();
								int diffDays = getDifferenceDays(etddate, currentdate);
								if (diffDays >= 0 && diffDays <= 90) {
									qty = qty + order.getQty();
									if (diffDays <= 15) {
										daysQty15 = daysQty15 + order.getQty();
									}
									if (diffDays > 15 && diffDays <= 30) {
										daysQty30 = daysQty30 + order.getQty();
									}
									if (diffDays > 30 && diffDays <= 45) {
										daysQty45 = daysQty45 + order.getQty();
									}
									if (diffDays > 45 && diffDays <= 60) {
										daysQty60 = daysQty60 + order.getQty();
									}
									if (diffDays > 60 && diffDays <= 75) {
										daysQty75 = daysQty75 + order.getQty();
									}
									if (diffDays > 75 && diffDays <= 90) {
										daysQty90 = daysQty90 + order.getQty();
									}

								}
							} catch (Exception exp) {
							}
						}

					}

				}

				if (qty > 0) {
					OpenOrderPipeLine pipeLine = new OpenOrderPipeLine();
					if (partId.contains("HPM1-")) {
						partId = partId.substring(5);
					}
					pipeLine.setDate(currentDate);
					pipeLine.setQty(qty);
					pipeLine.setPartNumber(partId);
					pipeLine.setQty_days15(daysQty15);
					pipeLine.setQty_days30(daysQty30);
					pipeLine.setQty_days45(daysQty45);
					pipeLine.setQty_days60(daysQty60);
					pipeLine.setQty_days75(daysQty75);
					pipeLine.setQty_days90(daysQty90);

					finalList.add(pipeLine);
				}

			}

			count++;
			LOG.info("=====================================================");
			LOG.info(count + "  :  parts processed out of    :" + orderedParts.size());
			LOG.info("=====================================================");
		}

		skuAvailDao.createPipeLineCollection(finalList);
		LOG.info("  :   total   inserted records    :" + finalList.size());
		LOG.info("============   process   completed    ===========");
	}

	private void setPipeLineData(List<OpenOrderPipeLine> openOrderPipeLineList, Map<String, Double> doiMap,
			OverAllSkusAvailability overAllSkusAvailability, Map<String, List<String>> priAltMap,
			List<AvPartFinalQtyPer> skuAllParts, List<FlexBom> pa_Super_flexBom,Map<String, List<AvPartFinalQtyPer>> avPartsMap,List<String> avs) {
		HashMap<String, Double> tempSkuDoiDays = new HashMap<String, Double>();		
		HashMap<String, Double> tempSkuDoi = new HashMap<String, Double>();
		tempSkuDoi.putAll(doiMap);
		tempSkuDoiDays.putAll(doiMap);		

		for (OpenOrderPipeLine openOrderPipeLine : openOrderPipeLineList) {
			if (tempSkuDoiDays.containsKey(openOrderPipeLine.getPartNumber())) {
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),
						openOrderPipeLine.getQty_days15() + tempSkuDoiDays.get(openOrderPipeLine.getPartNumber()));				
			}else{
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),openOrderPipeLine.getQty_days15());
			}
		}		
		overAllSkusAvailability.setpAvail15Days(generateskuQuantity(overAllSkusAvailability.getSkuId(), tempSkuDoiDays,
				priAltMap, skuAllParts, pa_Super_flexBom, avPartsMap, null,null));
//		logger.info("====================================15Days======================="+overAllSkusAvailability.getpAvail15Days());
		inventoryReduction(overAllSkusAvailability.getpAvail15Days(), pa_Super_flexBom, tempSkuDoiDays, avs);
		
		for (OpenOrderPipeLine openOrderPipeLine : openOrderPipeLineList) {
			if (tempSkuDoiDays.containsKey(openOrderPipeLine.getPartNumber())) {
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),
						openOrderPipeLine.getQty_days30() + tempSkuDoiDays.get(openOrderPipeLine.getPartNumber()));
			}else{
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),openOrderPipeLine.getQty_days30());
			}
		}
		overAllSkusAvailability.setpAvail30Days(generateskuQuantity(overAllSkusAvailability.getSkuId(), tempSkuDoiDays,
				priAltMap, skuAllParts, pa_Super_flexBom, avPartsMap, null,null));
//		logger.info("====================================30Days======================="+overAllSkusAvailability.getpAvail30Days());
		inventoryReduction(overAllSkusAvailability.getpAvail30Days(), pa_Super_flexBom, tempSkuDoiDays, avs);
		
		for (OpenOrderPipeLine openOrderPipeLine : openOrderPipeLineList) {
			if (tempSkuDoiDays.containsKey(openOrderPipeLine.getPartNumber())) {
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),
						openOrderPipeLine.getQty_days45() + tempSkuDoiDays.get(openOrderPipeLine.getPartNumber()));
			}else{
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),openOrderPipeLine.getQty_days45());
			}
		}
		overAllSkusAvailability.setpAvail45Days(generateskuQuantity(overAllSkusAvailability.getSkuId(), tempSkuDoiDays,
				priAltMap, skuAllParts, pa_Super_flexBom, avPartsMap, null,null));
//		logger.info("====================================45Days======================="+overAllSkusAvailability.getpAvail45Days());
		inventoryReduction(overAllSkusAvailability.getpAvail45Days(), pa_Super_flexBom, tempSkuDoiDays, avs);
		
		for (OpenOrderPipeLine openOrderPipeLine : openOrderPipeLineList) {
			if (tempSkuDoiDays.containsKey(openOrderPipeLine.getPartNumber())) {
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),
						openOrderPipeLine.getQty_days60() + tempSkuDoiDays.get(openOrderPipeLine.getPartNumber()));
			}else{
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),openOrderPipeLine.getQty_days60());
			}
		}
		overAllSkusAvailability.setpAvail60Days(generateskuQuantity(overAllSkusAvailability.getSkuId(), tempSkuDoiDays,
				priAltMap, skuAllParts, pa_Super_flexBom, avPartsMap, null,null));
//		logger.info("====================================60Days======================="+overAllSkusAvailability.getpAvail60Days());
		inventoryReduction(overAllSkusAvailability.getpAvail60Days(), pa_Super_flexBom, tempSkuDoiDays, avs);
		
		for (OpenOrderPipeLine openOrderPipeLine : openOrderPipeLineList) {
			if (tempSkuDoiDays.containsKey(openOrderPipeLine.getPartNumber())) {
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),
						openOrderPipeLine.getQty_days75() + tempSkuDoiDays.get(openOrderPipeLine.getPartNumber()));
			}else{
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),openOrderPipeLine.getQty_days75());
			}
		}
		overAllSkusAvailability.setpAvail75Days(generateskuQuantity(overAllSkusAvailability.getSkuId(), tempSkuDoiDays,
				priAltMap, skuAllParts, pa_Super_flexBom, avPartsMap, null,null));
//		logger.info("====================================75Days======================="+overAllSkusAvailability.getpAvail75Days());
		inventoryReduction(overAllSkusAvailability.getpAvail75Days(), pa_Super_flexBom, tempSkuDoiDays, avs);
		
		for (OpenOrderPipeLine openOrderPipeLine : openOrderPipeLineList) {
			if (tempSkuDoiDays.containsKey(openOrderPipeLine.getPartNumber())) {
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),
						openOrderPipeLine.getQty_days90() + tempSkuDoiDays.get(openOrderPipeLine.getPartNumber()));
			}else{
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),openOrderPipeLine.getQty_days90());
			}
		}
		overAllSkusAvailability.setpAvail90Days(generateskuQuantity(overAllSkusAvailability.getSkuId(), tempSkuDoiDays,
				priAltMap, skuAllParts, pa_Super_flexBom, avPartsMap, null,null));
//		logger.info("====================================90Days======================="+overAllSkusAvailability.getpAvail90Days());
		inventoryReduction(overAllSkusAvailability.getpAvail90Days(), pa_Super_flexBom, tempSkuDoiDays, avs);
		
		for (OpenOrderPipeLine openOrderPipeLine : openOrderPipeLineList) {
			if (doiMap.containsKey(openOrderPipeLine.getPartNumber())) {
				tempSkuDoi.put(openOrderPipeLine.getPartNumber(),
						openOrderPipeLine.getQty() + doiMap.get(openOrderPipeLine.getPartNumber()));
			}else{
				tempSkuDoiDays.put(openOrderPipeLine.getPartNumber(),openOrderPipeLine.getQty());
			}
		}
		overAllSkusAvailability.setPipeLineAvailability(generateskuQuantity(overAllSkusAvailability.getSkuId(),
				tempSkuDoi, priAltMap, skuAllParts, pa_Super_flexBom, avPartsMap,null, null));
//		logger.info("====================================Total======================="+overAllSkusAvailability.getpAvail30Days());
		overAllSkusAvailability.setPipeLineWeeks(12);

	}

	private int getDifferenceDays(Date date1, Date date2) {
		long diffenceTime = (date1.getTime() - date2.getTime());
		int differencesDays = (int) (diffenceTime / (24 * 60 * 60 * 1000));
		return differencesDays;
	}
	
	public void generateFinalQty() 
	{
		//List<MasterSkuAvBom_PA> masterSkuAvBom_PAlist=homeDao.getMasterAvBomData();
		Map<String,List<FlexBom>> flexBomMap=new HashMap<String,List<FlexBom>>();

		List<FlexBom> initialflexBomList=skuAvailDao.getFlexBomData();
		for (FlexBom flexBom : initialflexBomList)
		{
			List<FlexBom> flexBoms = new ArrayList<FlexBom>();
			String id = flexBom.getAv();
			if (flexBomMap.containsKey(id))
			{
				flexBoms.addAll(flexBomMap.get(id));
			}
			flexBoms.add(flexBom);
			flexBomMap.put(id, flexBoms);
		}
		
		/*for(int var=0;var<masterSkuAvBom_PAlist.size();var++){
		//List<FlexBom> flexBomList=homeDao.getFlexBomData(masterSkuAvBom_PAlist.get(var).getAv());
			String key=masterSkuAvBom_PAlist.get(var).getAv();
		}//matsrerbom ending..................
*/		
		List<AvPartFinalQtyPer> finalFlexBomList = new ArrayList<AvPartFinalQtyPer>();
		List<String> keyList = new ArrayList<String>(flexBomMap.keySet());
		for (String key : keyList)
		{
			List<FlexBom> flexBomList = flexBomMap.get(key);
			for (int count = flexBomList.size() - 1; count >= 0; count--)
			{
				FlexBom outerFlexBom = flexBomList.get(count);
				int check = outerFlexBom.getLevel();
				AvPartFinalQtyPer avPartFinalQtyPer = new AvPartFinalQtyPer();
				double finalQty = outerFlexBom.getQtyPer();

				StringBuffer buffer = new StringBuffer();
				buffer.append(finalQty + "*");
				for (int index = count - 1; index >= 0; index--)
				{
					FlexBom innerFlexBom = flexBomList.get(index);
					try
					{
						if (outerFlexBom.getLevel() > innerFlexBom.getLevel())
						{
							// check!=innerFlexBom.getLevel() &&
							if (check > innerFlexBom.getLevel())
							{
								finalQty = finalQty * (innerFlexBom.getQtyPer()>0?innerFlexBom.getQtyPer():1);
								buffer.append(innerFlexBom.getQtyPer() + "*");
								check = innerFlexBom.getLevel();
							}
						}
					} catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				/*
				 * outerFlexBom.setBu(masterSkuAvBom_PAlist.get(var).getBu());
				 * outerFlexBom
				 * .setFamily(masterSkuAvBom_PAlist.get(var).getFamily());
				 * outerFlexBom.setSku(masterSkuAvBom_PAlist.get(var).getSku());
				 */

				avPartFinalQtyPer.setAv(outerFlexBom.getAv());
				avPartFinalQtyPer.setLevel(outerFlexBom.getLevel());
				avPartFinalQtyPer.setPartNumber(outerFlexBom.getPartNumber());
				avPartFinalQtyPer.setBomTypep(outerFlexBom.getBomTypep());
				avPartFinalQtyPer.setFinalQty(finalQty);
				avPartFinalQtyPer.setMPC(outerFlexBom.getMpc());
				avPartFinalQtyPer.setINCLUDE_ON_SCREEN(outerFlexBom.getINCLUDE_ON_SCREEN());
				finalFlexBomList.add(avPartFinalQtyPer);
				logger.info(avPartFinalQtyPer + "\t" + buffer.toString());
			}
			skuAvailDao.saveFinalQuantity(finalFlexBomList);
			finalFlexBomList.clear();
		}// map ending ..........................

	}

}
