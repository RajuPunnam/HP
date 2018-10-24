package com.io.services.po;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.utill.DateUtil;
import com.io.dao.CommonDao;
import com.io.dao.PoDao;
import com.io.pojos.AvPartFinalQtyPer;
import com.io.pojos.POOrder;
import com.io.pojos.POOrderDB;
import com.io.pojos.SkuBom;
import com.io.utill.Mail;

@Service
public class POService {

	@Autowired
	CommonDao commonDao;
	@Autowired
	PoDao poDao;
 
	private static final Logger LOG = Logger.getLogger(POService.class.getName());
	
	synchronized public String processPO(Map<String,String> buMap) {
		long oldRecords=0;	
		long oldRecords_skuavpn=0;	
		long countingrecords=0;
		long countingrecords_skuavpn=0;
		long totalRecords=0;
		long totalRecords_skuavpn=0;	
		StringBuffer sb=new StringBuffer();
		
		List<String> exsistingDates = poDao.getDistinctStringValues("ORIG_PO_SHORTAGE", "File Date");
		List<String> poFileDates = poDao.getDistinctStringValues("OPEN_ORDERS", "File Date");
		LOG.info("------------existing dates from ORIG_PO_SHORTAGE_New-----------"+exsistingDates.size()+" "+exsistingDates);
		LOG.info("------------all dates from open orders are----------"+poFileDates.size());
		List<Date> origPoShortageDatesList = new ArrayList<Date>();
		List<String> rawDateList=new ArrayList<String>();
		
		for(String edate:exsistingDates){
			origPoShortageDatesList.add(DateUtil.getDateForAnyFormate(edate));
		}
		if(!origPoShortageDatesList.isEmpty() && origPoShortageDatesList.size()!=0){
		LOG.info("---------------existing ORIG_PO_SHORTAGE dates size is"+origPoShortageDatesList.size());
		for(String rawDate:poFileDates){
			if(!origPoShortageDatesList.contains(DateUtil.getDateForAnyFormate(rawDate)))
				rawDateList.add(rawDate);
		}
		}else{
			rawDateList.addAll(poFileDates);
		}
		
		oldRecords=poDao.getCollectionCount("ORIG_PO_SHORTAGE");
		oldRecords_skuavpn=poDao.getCollectionCount("PO_ORDERS_SKU_AV_PN");
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		
		if(rawDateList.size()>0){
			LOG.info(" new dates are "+rawDateList.size()+" "+rawDateList);
			try{
			List<String> exsistingPos = poDao.getDistinctStringValues("PO_ORDERS_SKU_AV_PN", "PO");
			List<String> currentPos = new ArrayList<String>();
			List<SkuBom> skuBomList = commonDao.getALLskuTOav();
			LOG.info("Got the SKUBOM Data");
			List<AvPartFinalQtyPer> avpartList = commonDao.getAVPartFinalLevel();
			LOG.info("Got the av part Data");
			
			Map<String, Double> priceMasterMap = commonDao.getPriceMasterData_New();
			
			LOG.info("Got the price master Data");
			Map<String, List<SkuBom>> skuAvMap = new HashMap<String, List<SkuBom>>();
			Map<String, String> skuFamily = new HashMap<String, String>();
			Map<String, String> skuBU = new HashMap<String, String>();
			for (SkuBom skuBom : skuBomList) {
				skuFamily.put(skuBom.getSku(), skuBom.getFamily());
				skuBU.put(skuBom.getSku(), skuBom.getBu());
				List<SkuBom> avSkuBom = new ArrayList<SkuBom>();
				if (skuAvMap.containsKey(skuBom.getSku())) {
					avSkuBom.addAll(skuAvMap.get(skuBom.getSku()));
				}
				avSkuBom.add(skuBom);
				skuAvMap.put(skuBom.getSku(), avSkuBom);
			}
			Map<String, List<AvPartFinalQtyPer>> avPartMap = new HashMap<String, List<AvPartFinalQtyPer>>();
			for (AvPartFinalQtyPer avPartFinalQtyPer : avpartList) {
				List<AvPartFinalQtyPer> avPart = new ArrayList<AvPartFinalQtyPer>();
				if (avPartMap.containsKey(avPartFinalQtyPer.getAv())) {
					avPart.addAll(avPartMap.get(avPartFinalQtyPer.getAv()));
				}
				avPart.add(avPartFinalQtyPer);
				avPartMap.put(avPartFinalQtyPer.getAv(), avPart);
			}

			//List<POOrderDB> openOrderList = poDao.getOredrDataByDate(sdf.format(latestDate));
			List<POOrderDB> openOrderList = poDao.getOredrDataByDate(rawDateList);
			String output=null;
			String output1=null;
			ArrayList<POOrder> org_Po_ShrtgList = new ArrayList<POOrder>();
			ArrayList<POOrder> poOPrdersSKuAvPnList = new ArrayList<POOrder>();
			SimpleDateFormat fileSDF = new SimpleDateFormat("MM-dd-yyyy");
			for (POOrderDB poOrderDB : openOrderList) {
				POOrder poOrder = new POOrder();
				Calendar fileDate = Calendar.getInstance();
				try {
					fileDate.setTime(fileSDF.parse(poOrderDB.getFileDate()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				while (fileDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
					fileDate.add(Calendar.DATE, -1);
				String fileDateString = fileSDF.format(fileDate.getTime());
				poOrder.setAdjustedDate(fileDateString);
				poOrder.setFileDate(poOrderDB.getFileDate());
				if(buMap.containsKey(poOrderDB.getPl().trim())){
				    poOrder.setBu(buMap.get(poOrderDB.getPl()));
				    }
				    else{
				     poOrder.setBu(poOrderDB.getPl());
				    }
				if (poOrder.getBu() == null || poOrder.getBu().length() <= 0) {
					poOrder.setBu(skuBU.containsKey(poOrder.getSku()) ? skuBU.get(poOrder.getSku()) : "NA");
				}
				poOrder.setPl(poOrderDB.getPl());
				poOrder.setPo(poOrderDB.getPo());
				String poRecDateString = null;
				try {
					fileSDF.parse(poOrderDB.getPoRecDate());
					poRecDateString = poOrderDB.getPoRecDate();
				} catch (ParseException e) {
					//e.printStackTrace();
				}				
				poOrder.setPoRecDate(poRecDateString);
				poOrder.setSO(poOrderDB.getSO());
				poOrder.setSku(poOrderDB.getSku());
				poOrder.setSkuCons(poOrderDB.getTotal());
				poOrder.setSKUQty_Del(poOrderDB.getDelta());
				poOrder.setSkuPrice(
						priceMasterMap.containsKey(poOrder.getSku()) ? priceMasterMap.get(poOrder.getSku()) : 0);
				poOrder.setFamily(poOrderDB.getFamily());
				if (poOrder.getFamily() == null || poOrder.getFamily().length() <= 0) {
					poOrder.setFamily(skuFamily.containsKey(poOrder.getSku()) ? skuFamily.get(poOrder.getSku()) : "NA");
				}
				org_Po_ShrtgList = breakDownAVPNLevel(org_Po_ShrtgList, poOrder, skuAvMap, avPartMap, priceMasterMap);
				if (!exsistingPos.contains(poOrder.getPo()) && !currentPos.contains(poOrderDB.getPo())) {
					poOPrdersSKuAvPnList = breakDownAVPNLevel(poOPrdersSKuAvPnList, poOrder, skuAvMap, avPartMap,
							priceMasterMap);
					currentPos.add(poOrderDB.getPo());
				}else{
					
					LOG.info(poOrder.getPo()+" Exist.Open Order List size: "+openOrderList.size());
				}
				//LOG.info("SKU:-" + poOrder.getSku());
				if(org_Po_ShrtgList.size() > 1000000){
					output=poDao.savePOOrder(org_Po_ShrtgList);
					countingrecords+=org_Po_ShrtgList.size();
					org_Po_ShrtgList.clear();
				}
				
				if (poOPrdersSKuAvPnList.size() >1000000) {
					output1=poDao.savePoSkuAvPn(poOPrdersSKuAvPnList);
					countingrecords_skuavpn+= poOPrdersSKuAvPnList.size();
					poOPrdersSKuAvPnList.clear();
				}
			}
			
			sb.append("ORIG_PO_SHORTAGE ");
			if (org_Po_ShrtgList.size() > 0) {
				output=poDao.savePOOrder(org_Po_ShrtgList);
				countingrecords+=org_Po_ShrtgList.size();
				org_Po_ShrtgList.clear();
			}else {
				LOG.info("-----------no data found for org_Po_ShrtgList----------");
				if(output == null) output="No data found!";
			}
			
			sb.append(output+" ");
			sb.append(oldRecords+" ");
			sb.append(countingrecords+" ");
			totalRecords=poDao.getCollectionCount("ORIG_PO_SHORTAGE");
			sb.append(totalRecords);
			sb.append("\n");
			
			sb.append("PO_ORDERS_SKU_AV_PN ");
			if (poOPrdersSKuAvPnList.size() > 0) {
				output1=poDao.savePoSkuAvPn(poOPrdersSKuAvPnList);
				countingrecords_skuavpn+=poOPrdersSKuAvPnList.size();
				poOPrdersSKuAvPnList.clear();
			}else {
				LOG.info("-----------no data found for poOPrdersSKuAvPnList----------");
				if(output1 == null) output1="No data found!";
			}
			
			sb.append(output1+" ");
			sb.append(oldRecords_skuavpn+" ");
			sb.append(countingrecords_skuavpn+" ");
			totalRecords_skuavpn=poDao.getCollectionCount("PO_ORDERS_SKU_AV_PN");
			sb.append(totalRecords_skuavpn);
			
			Mail.sendMail("-------ORIG_PO_SHORTAGE && PO_ORDERS_SKU_AV_PN Executed Successfully"+sb.toString()+" "+new Date());
			
			return sb.toString();
		}catch(Exception e){
			LOG.info(e.getMessage());
			
			return e.getMessage()+" ORIG_PO_SHORTAGE "+oldRecords+" PO_ORDERS_SKU_AV_PN "+oldRecords_skuavpn;
		}
		}else{
			LOG.info("---------------no new data found IODP Openorders---------------");
			Mail.sendMail("----------No new data found in IODP Openorders---------ORIG_PO_SHORTAGE "+oldRecords+" PO_ORDERS_SKU_AV_PN "+oldRecords_skuavpn);
			return "No data found! ORIG_PO_SHORTAGE "+oldRecords+" PO_ORDERS_SKU_AV_PN "+oldRecords_skuavpn;
		}
	}

	public ArrayList<POOrder> breakDownAVPNLevel(ArrayList<POOrder> org_Po_ShrtgList, POOrder pOOrder,
			Map<String, List<SkuBom>> skuAvMap, Map<String, List<AvPartFinalQtyPer>> avPartMap,
			Map<String, Double> priceMasterMap) {
		if (skuAvMap.containsKey(pOOrder.getSku())) {
			List<SkuBom> avList = skuAvMap.get(pOOrder.getSku());
			for (SkuBom skuBom : avList) {
				if (avPartMap.containsKey(skuBom.getAvs())) {
					List<AvPartFinalQtyPer> avPartFinalQtyPerList = avPartMap.get(skuBom.getAvs());
					for (AvPartFinalQtyPer avPartFinalQtyPer : avPartFinalQtyPerList) {
						POOrder pOOrderPN = new POOrder(pOOrder);
						pOOrderPN.setAvId(avPartFinalQtyPer.getAv());
						pOOrderPN.setAvCons(pOOrderPN.getSkuCons());
						pOOrderPN.setAVQty_Del(pOOrderPN.getSKUQty_Del());
						pOOrderPN.setPartId(avPartFinalQtyPer.getPartNumber());
						pOOrderPN.setCommodity(avPartFinalQtyPer.getCommodity());
						pOOrderPN.setQuantity(pOOrderPN.getAvCons() * avPartFinalQtyPer.getFinalQty());
						pOOrderPN.setPNQty_Del(pOOrderPN.getAVQty_Del() * avPartFinalQtyPer.getFinalQty());
						pOOrderPN.setPartPrice(priceMasterMap.containsKey(pOOrderPN.getPartId())
								? priceMasterMap.get(pOOrderPN.getPartId()) : 0);
						org_Po_ShrtgList.add(pOOrderPN);
					}
				} else {
					POOrder pOOrderPN = new POOrder(pOOrder);
					pOOrderPN.setAvId(skuBom.getAvs());
					pOOrderPN.setAvCons(pOOrderPN.getSkuCons());
					pOOrderPN.setAVQty_Del(pOOrderPN.getSKUQty_Del());
					pOOrderPN.setPartId(skuBom.getAvs());
					pOOrderPN.setQuantity(pOOrderPN.getSkuCons());
					pOOrderPN.setPNQty_Del(pOOrderPN.getSKUQty_Del());
					pOOrderPN.setPartPrice(0.0);
					org_Po_ShrtgList.add(pOOrderPN);
				}
			}
		} else {
			pOOrder.setAvId(pOOrder.getSku());
			pOOrder.setAvCons(pOOrder.getSkuCons());
			pOOrder.setAVQty_Del(pOOrder.getSKUQty_Del());
			pOOrder.setAvPrice(0.0);
			pOOrder.setPartId(pOOrder.getSku());
			pOOrder.setQuantity(pOOrder.getSkuCons());
			pOOrder.setPNQty_Del(pOOrder.getSKUQty_Del());
			pOOrder.setPartPrice(0.0);
			org_Po_ShrtgList.add(pOOrder);
		}
		return org_Po_ShrtgList;
	}
}
