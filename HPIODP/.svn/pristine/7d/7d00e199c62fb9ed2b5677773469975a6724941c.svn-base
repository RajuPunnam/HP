package com.io.services.po;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.finalpojos.InvPNConversion;
import com.inventory.finalpojos.InvoicesBaseData;
import com.inventory.utill.DateUtil;
import com.io.dao.CommonDao;
import com.io.dao.PoDao;
import com.io.pojos.AvPartFinalQtyPer;
import com.io.pojos.Combined_Price;
import com.io.pojos.SkuBom;
import com.io.utill.Mail;

@Service
public class InvoiceService {

	@Autowired
	CommonDao commonDao;
	@Autowired
	PoDao poDao;
	private static final Logger LOG = Logger.getLogger(InvoiceService.class.getName());
	synchronized public String processInvoice() {
		
		long oldRecords=0;	
		long countingrecords=0;
		long totalRecords=0;
		
		List<String> exsistingDates = poDao.getDistinctStringValues("INVOICE_AV_PN", "File Date");
		List<String> poFileDates = poDao.getDistinctStringValues("INVOICES", "File Date");
	
		List<Date> avPnDatesList = new ArrayList<Date>();
		List<String> rawDateList=new ArrayList<String>();
		
		for(String edate:exsistingDates){
			avPnDatesList.add(DateUtil.getDateForAnyFormate(edate));
		}
		if(!avPnDatesList.isEmpty() && avPnDatesList.size()!=0){
		LOG.info("---------------existing invoicesavpn dates size is"+avPnDatesList.size()+" "+exsistingDates);
		for(String rawDate:poFileDates){
			if(!avPnDatesList.contains(DateUtil.getDateForAnyFormate(rawDate)))
				rawDateList.add(rawDate);
		}
		}else{
			rawDateList.addAll(poFileDates);
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
		SimpleDateFormat extFrmt = new SimpleDateFormat("MM/dd/yyyy");
		
		oldRecords=poDao.getCollectionCount("INVOICE_AV_PN");
		
		/*Date latestDate = null;
		String latestStringDate=null;
		try {
			//latestDate = sdf.parse(poFileDates.get(0));
			latestDate=DateUtil.getDateForAnyFormate(poFileDates.get(0));
			System.out.println("-----------before checking latest date > -----------"+latestDate);
			for (String orderDate : poFileDates) {
				//Date newDate = sdf.parse(orderDate);
				Date newDate = DateUtil.getDateForAnyFormate(orderDate);
				if (newDate.getTime() > latestDate.getTime()){
					latestDate = newDate;
					latestStringDate=orderDate;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(latestStringDate+"-------after checking latest date-------"+latestDate);
		if (!exsistingDates.contains(latestStringDate)) {}*/
		
		
		if(rawDateList.size()>0){
			LOG.info(" new dates are "+rawDateList.size()+" "+rawDateList);
			try{
			List<SkuBom> skuBomList = commonDao.getALLskuTOav();
			System.out.println("Got the SKUBOM Data");
			List<AvPartFinalQtyPer> avpartList = commonDao.getAVPartFinalLevel();
			System.out.println("Got the av part Data");
			Map<String, Double> priceMasterMap = commonDao.getPriceMasterData_New();
			System.out.println("Got the price master Data");
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
			
			

			//List<InvoicesBaseData> invoiceList = poDao.getInvoiceDateforparticularDate(latestStringDate);
			
			List<InvoicesBaseData> invoiceList = poDao.getInvoiceDateforparticularDate(rawDateList);
			
			ArrayList<InvPNConversion> invoiceAVPNList = new ArrayList<InvPNConversion>();
			String output=null;
			for (InvoicesBaseData invoice : invoiceList) {
				InvPNConversion invoiceAVPN = new InvPNConversion();
				Calendar fileDate = Calendar.getInstance();
				try {
					fileDate.setTime(extFrmt.parse(invoice.getFileDate()));
				} catch (ParseException e) {
					try {
						fileDate.setTime(sdf.parse(invoice.getFileDate()));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				invoiceAVPN.setFileDate(sdf.format(fileDate.getTime()));
				while (fileDate.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)
					fileDate.add(Calendar.DATE, -1);
				String fileDateString = sdf.format(fileDate.getTime());
				invoiceAVPN.setAdjustedDate(fileDateString);
				try {
					fileDate.setTime(extFrmt.parse(invoice.getInvoiceDate()));
				} catch (ParseException e) {
					try {
						fileDate.setTime(sdf.parse(invoice.getInvoiceDate()));
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				invoiceAVPN.setPoDate(sdf.format(fileDate.getTime()));
				invoiceAVPN.setSku(invoice.getSku().trim());
				invoiceAVPN.setPo(invoice.getPo());
				invoiceAVPN.setSkuQty(invoice.getSkuQuantity());
				invoiceAVPN.setPl(invoice.getPl().trim());
				if (invoiceAVPN.getPl() == null || invoiceAVPN.getPl().length() <= 0) {
					invoiceAVPN.setPl(skuBU.containsKey(invoiceAVPN.getSku()) ? skuBU.get(invoiceAVPN.getSku()) : "NA");
				}
				invoiceAVPN.setSku_Price(
						priceMasterMap.containsKey(invoiceAVPN.getSku()) ? priceMasterMap.get(invoiceAVPN.getSku()) : 0);
//				if (invoiceAVPN.getFamily() == null || invoiceAVPN.getFamily().length() <= 0) {
//					invoiceAVPN.setFamily(skuFamily.containsKey(invoiceAVPN.getSku()) ? skuFamily.get(poOrder.getSku()) : "NA");
//				}
				invoiceAVPNList = breakDownAVPNLevel(invoiceAVPNList, invoiceAVPN, skuAvMap, avPartMap, priceMasterMap);				
				//LOG.info("SKU:-" + invoice.getSku());
				
				if (invoiceAVPNList.size() > 1000000) {
					output=poDao.saveIvnoiceAVPN(invoiceAVPNList);
					countingrecords+=invoiceAVPNList.size();
					invoiceAVPNList.clear();
					
				}
			}
			
			if (invoiceAVPNList.size() > 0) {
				output=poDao.saveIvnoiceAVPN(invoiceAVPNList);
				countingrecords+=invoiceAVPNList.size();
				invoiceAVPNList.clear();
			}else{
				LOG.info("-------------------no data found for invoiceAVPNList-----------");
				if(output == null) output="No data found!";
			}
			totalRecords=poDao.getCollectionCount("INVOICE_AV_PN");
			Mail.sendMail("-------INVOICE_AV_PN Executed Successfully"+"Old count is "+oldRecords+" Processed count "+countingrecords+"Total count "+totalRecords+" "+new Date());
			output=output+" "+oldRecords+" "+countingrecords+" "+totalRecords;
			return output;
			}catch(Exception e){
				LOG.info(e.getMessage());
				return e.getMessage()+" "+oldRecords+" "+countingrecords+" "+totalRecords;
			}
		}else{
			LOG.info("---------------no new data found---------------");
			//Mail.sendMail("----------No new data found in Invoices---------"+"Old count is "+oldRecords+" Processed count "+countingrecords+"Total count "+totalRecords+" "+new Date());
			return "No data found!"+" "+oldRecords+" "+countingrecords+" "+totalRecords;
		}
		
	}

	public ArrayList<InvPNConversion> breakDownAVPNLevel(ArrayList<InvPNConversion> invoiceAVPNList, InvPNConversion invoiceAVPN,
			Map<String, List<SkuBom>> skuAvMap, Map<String, List<AvPartFinalQtyPer>> avPartMap,
			Map<String, Double> priceMasterMap) {
		if (skuAvMap.containsKey(invoiceAVPN.getSku())) {
			List<SkuBom> avList = skuAvMap.get(invoiceAVPN.getSku());
			for (SkuBom skuBom : avList) {
				if (avPartMap.containsKey(skuBom.getAvs())) {
					List<AvPartFinalQtyPer> avPartFinalQtyPerList = avPartMap.get(skuBom.getAvs());
					for (AvPartFinalQtyPer avPartFinalQtyPer : avPartFinalQtyPerList) {
						InvPNConversion invoicePN = new InvPNConversion(invoiceAVPN);
						invoicePN.setAv(avPartFinalQtyPer.getAv().trim());
						invoicePN.setAvQty(invoiceAVPN.getSkuQty());
						invoicePN.setAv_Price(priceMasterMap.containsKey(invoicePN.getAv())
								? priceMasterMap.get(invoicePN.getAv()) : 0.0);
						invoicePN.setPartId(avPartFinalQtyPer.getPartNumber().trim());
						invoicePN.setPartDescription(avPartFinalQtyPer.getCommodity());
						invoicePN.setPartQty(invoicePN.getAvQty() * avPartFinalQtyPer.getFinalQty());
						invoicePN.setPart_Price(priceMasterMap.containsKey(invoicePN.getPartId())
								? priceMasterMap.get(invoicePN.getPartId()) : 0.0);
						invoiceAVPNList.add(invoicePN);
					}
				} else {
					InvPNConversion invoicePN = new InvPNConversion(invoiceAVPN);
					invoicePN.setAv(skuBom.getAvs());
					invoicePN.setAvQty(invoicePN.getSkuQty());
					invoicePN.setAv_Price(priceMasterMap.containsKey(invoicePN.getAv())
							? priceMasterMap.get(invoicePN.getAv()) : 0.0);					invoicePN.setPartId(skuBom.getAvs());
					invoicePN.setPartQty(invoicePN.getSkuQty());
					invoicePN.setPart_Price(0.0);
					invoiceAVPNList.add(invoicePN);
				}
			}
		} else {
			invoiceAVPN.setAv(invoiceAVPN.getSku());
			invoiceAVPN.setAvQty(invoiceAVPN.getSkuQty());
			invoiceAVPN.setAv_Price(0.0);
			invoiceAVPN.setPartId(invoiceAVPN.getSku());
			invoiceAVPN.setPartQty(invoiceAVPN.getSkuQty());
			invoiceAVPN.setPart_Price(0.0);
			invoiceAVPNList.add(invoiceAVPN);
		}
		return invoiceAVPNList;
	}
}
