package com.io.services.po;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.io.dao.PoDao;
import com.io.pojos.MasterBomPojo;
import com.io.pojos.MasterSkuAvBomBo;
import com.io.pojos.POOrder;
import com.io.pojos.POOrderDB;
import com.io.utill.BomUtill;
import com.io.utill.DateUtil;
import com.io.utill.Mail;
import com.io.utill.POUtill;
import com.io.utill.PriceUtill;

@Service
public class PORedistrubutionService {
	@Autowired
	private PoDao podao;
	@Autowired
	private BomUtill bomUtillFinal;
	@Autowired
	private POUtill poUtill;
	@Autowired
	private PriceUtill priceUtill;
	
	private static Logger LOG=Logger.getLogger(PORedistrubutionService.class);
	
	
	// TO Create OPEN_ORDER_CLEAN. 	
	public void CreateCleanPO() throws ParseException {
		try{
			LOG.info("------------------------stared ------------>CreateCleanPO");
		podao.dropCollection("OPEN_ORDER_CLEAN");

		List<POOrderDB> orderDb = podao.getPOOrderDB();
		Collections.sort(orderDb, new Comparator<POOrderDB>() {
			public int compare(POOrderDB o1, POOrderDB o2) {
				int poComp = o1.getPo().compareTo(o2.getPo());
				return ((poComp == 0) ? o1.getFileDate().compareTo(o2.getFileDate()) : poComp);
			}
		});

		Map<String, Map<Date, List<POOrderDB>>> dataMap = new LinkedHashMap<String, Map<Date, List<POOrderDB>>>();
		for (POOrderDB podb : orderDb) {
			//LOG.info(podb);
			
			String porecdate = "";
			if (podb.getPoRecDate() != null || !podb.getPoRecDate().isEmpty()) {
				porecdate = podb.getPoRecDate();
			}
			if (porecdate == null || porecdate.isEmpty() || porecdate == ""
					|| porecdate.equalsIgnoreCase("Agdo PO")) {
				porecdate = podb.getFileDate();
			}

			// checking file formate for PoRecDate with MM-dd-yyyy
			if (porecdate.contains("/"))
			porecdate = porecdate.replace("/", "-");
			if (porecdate.length() != 10) {
				String date[] = porecdate.split("-");
				if (date[0].length() != 2)
					date[0] = "0" + date[0];
				if (date[1].length() != 2)
					date[1] = "0" + date[1];
				if (date[2].length() != 4)
					date[2] = "20" + date[2];
				porecdate=date[0] + "-" + date[1] + "-" + date[2];
			}
			podb.setAdjustedDate(porecdate);
			
			String po = podb.getPo();
			String filedate = podb.getFileDate().replace("-", "/");
			if (filedate.length() != 10) {
				String date3[] = filedate.split("/");
				if (date3[0].length() != 2)
					date3[0] = "0" + date3[0];
				if (date3[1].length() != 2)
					date3[1] = "0" + date3[1];
				if (date3[2].length() != 4)
					date3[2] = "20" + date3[2];
				filedate = (date3[0] + "/" + date3[1] + "/" + date3[2]);
			}
			Date date = DateUtil.stringToDate(filedate, "MM/dd/yyyy");
			Map<Date, List<POOrderDB>> existing = new TreeMap<Date, List<POOrderDB>>();
			List<POOrderDB> list = new ArrayList<POOrderDB>();
			if (dataMap.containsKey(po)) {
				existing = dataMap.get(po);
				if (existing.containsKey(date)) {
					list.addAll(existing.get(date));
				}
			}
			list.add(podb);
			existing.put(date, list);
			dataMap.put(po, existing);

		}
		List<POOrderDB> order = new ArrayList<POOrderDB>();
		for (String s : dataMap.keySet()) {
			//LOG.info(s);

			for (Date s1 : dataMap.get(s).keySet()) {
				order.addAll(dataMap.get(s).get(s1));
				//LOG.info(s1 + "\n" + dataMap.get(s).get(s1));
				break;
			}
		}

		for (POOrderDB ord : order) {
			podao.savePOOrderClean(ord);
		}
		Mail.sendMail("CreateCleanPO updated sucessully "+new Date());
		LOG.info(new Date()+"------------------completed--------------CreateCleanPO"+order.size());
		}catch (Exception e) {
			e.printStackTrace();
			Mail.sendMail("Error while Executing IORedistrubution Daily JOB (CreateCleanPO) "+e.getMessage()+new Date());
		LOG.info("Error while Executing IORedistrubution Daily JOB (CreateCleanPO) "+e.getMessage()+new Date());
		}
		
	}
	
	
	
	static List<List<POOrderDB>> chopped(List<POOrderDB> list, final int L) {
		List<List<POOrderDB>> parts = new ArrayList<List<POOrderDB>>();
		final int N = list.size();
		for (int i = 0; i < N; i += L) {
			parts.add(new ArrayList<POOrderDB>(list.subList(i, Math.min(N, i + L))));
		}
		return parts;
	}
	public void remainingDatesTest(){
		for(String s:poUtill.getRemaingDatesShortage()){
			LOG.info(s);
		}
	}
	
	
	
	public void convertSKUtoAVtoPN() throws ParseException {
		try{
		LOG.info("----------------------started---------convertSKUtoAVtoPN->"+new Date());
		Map<String, POOrderDB> poMap = new HashMap<String, POOrderDB>();
		List<POOrderDB> poOrders = new ArrayList<POOrderDB>();
		List<POOrder> finalPOOrders = null;
		for (POOrderDB po : podao.getOpenOrderClean()) {
			String key = po.getFileDate() + po.getPo() + po.getSku();
			String porecdate=po.getFileDate().replace("-", "/");
			if (porecdate.length() != 10) {
				String date[] = porecdate.split("/");
				if (date[0].length() != 2)
					date[0] = "0" + date[0];
				if (date[1].length() != 2)
					date[1] = "0" + date[1];
				if (date[2].length() != 4)
					date[2] = "20" + date[2];
				porecdate=date[0] + "/" + date[1] + "/" + date[2];
			}
			
			Date date=DateUtil.stringToDate(porecdate,"MM/dd/yyyy");
			if(date.before(DateUtil.stringToDate("01/01/2016", "MM/dd/yyyy"))){
				continue;
			}
			
//			String total = po.getTotal();
			if (poMap.containsKey(key)) {
				POOrderDB exisobj = poMap.get(key);
//				total = String.valueOf(Double.parseDouble(total) + Double.parseDouble(exisobj.getTotal()));
			}
//			po.setTotal(total);
			poMap.put(key, po);
		}

		for (String key : poMap.keySet()) {
			POOrderDB po = poMap.get(key);
			poOrders.add(po);

		}
		for (List<POOrderDB> porders : chopped(poOrders, 100)) {
			finalPOOrders = new ArrayList<POOrder>();
			for (POOrderDB po : porders) {
				
				List<MasterSkuAvBomBo> skuboms = null;
				List<MasterBomPojo> avParts =null;
				String porecdate="";
				if(po.getPoRecDate() != null || !po.getPoRecDate().equalsIgnoreCase("Agdo PO")){
					porecdate=po.getPoRecDate().replace("-", "/");
					}
					if(porecdate==null || porecdate.isEmpty() || porecdate=="" || porecdate.equalsIgnoreCase("Agdo PO")){
						porecdate=po.getFileDate().replace("-", "/");
					}
				
					porecdate=porecdate.replace("-", "/");
					if (porecdate.length() != 10) {
						String date[] = porecdate.split("/");
						if (date[0].length() != 2)
							date[0] = "0" + date[0];
						if (date[1].length() != 2)
							date[1] = "0" + date[1];
						if (date[2].length() != 4)
							date[2] = "20" + date[2];
						porecdate=date[0] + "/" + date[1] + "/" + date[2];
					}
					
					Date date=DateUtil.stringToDate(porecdate,"MM/dd/yyyy");
					
					//System.out.println("POORDERSKUAVPN :: po date::"+porecdate+" sku::"+po.getSku()+" filedate::"+po.getFileDate());
					skuboms = new ArrayList<MasterSkuAvBomBo>();

					if(getSkubomAvs(po.getSku(),porecdate)!=null){
					skuboms.addAll(getSkubomAvs(po.getSku(),porecdate));
					}
					if(!skuboms.isEmpty()){
						for(MasterSkuAvBomBo skuAv:skuboms){
							avParts = new ArrayList<MasterBomPojo>();
							avParts.addAll(bomUtillFinal.getPrimaryData(skuAv.getAv(), porecdate));
							if(!avParts.isEmpty()){
							for(MasterBomPojo part:avParts){
								POOrder order =null;
								order = new POOrder();
								
								order.setAvCons(po.getTotal());
								order.setAvId(part.getAv());
								order.setAdjustedDate(getFormattedDate(po.getFileDate()));
								order.setCustomer(po.getCustomer());
								order.setFamily(skuAv.getFamily());
								order.setFileDate(po.getFileDate());
								order.setCommodity(part.getCommodity());
								order.setSupplier(part.getSupplier());
								order.setFlag(false);
								order.setImportLocal(part.getLocalImport());
								order.setMpc(part.getMpc());
								order.setPartId(part.getPartNumber());
								order.setPartLevel(String.valueOf(part.getLevel()));
								order.setPl(po.getPl());
								order.setPo(po.getPo());
								order.setPoRecDate(po.getPoRecDate());
//								if(po.getTotal()!=null || !po.getTotal().isEmpty() || po.getTotal()!=""){
//									//System.out.println("PO :: "+order.getPo()+"\tqty :: "+part.getQtyPer() * Double.valueOf(po.getTotal()));
//									order.setQuantity(part.getQtyPer()
//										* Double.valueOf(po.getTotal()));
//								}
//								else{
//									order.setQuantity(part.getQtyPer());
//								}
								order.setSku(po.getSku());
								order.setSkuCons(po.getTotal());
								finalPOOrders.add(order);
							}
							continue;
						 }
							else{

								POOrder order =null;
								order = new POOrder();
								order.setAvCons(po.getTotal());
								order.setAvId(skuAv.getAv());
								order.setAdjustedDate(getFormattedDate(po.getFileDate()));
								order.setCustomer(po.getCustomer());
								order.setFamily(skuAv.getFamily());
								order.setFileDate(po.getFileDate());
								order.setFlag(false);
								order.setImportLocal("");
								order.setCommodity("");
								order.setSupplier("");
								order.setMpc("");
								order.setPartId(skuAv.getAv());
								order.setPartLevel("");
								order.setPl(po.getPl());
								order.setPo(po.getPo());
								order.setPoRecDate(po.getPoRecDate());
//								if(po.getTotal()!=null || !po.getTotal().isEmpty() || po.getTotal()!=""){
//									order.setQuantity(Double.parseDouble(po.getTotal()));
//								}
//								else{
//									order.setQuantity(0.0);
//								}
								order.setSku(po.getSku());
								order.setSkuCons(po.getTotal());
								finalPOOrders.add(order);
							
								dataNotFound.add(skuAv.getAv()+"::AV");
								continue;
							}
						}
						continue;
					}
					else{
						avParts = new ArrayList<MasterBomPojo>();
						avParts=bomUtillFinal.getPrimaryData(po.getSku(), porecdate);
						if(!avParts.isEmpty()){
						for(MasterBomPojo part:avParts){
							POOrder order =null;
							order = new POOrder();
							order.setAvCons(po.getTotal());
							order.setAvId(part.getAv());
							order.setCustomer(po.getCustomer());
							order.setFamily("NA");
							order.setCommodity(part.getCommodity());
							order.setSupplier(part.getSupplier());
							order.setFileDate(po.getFileDate());
							order.setFlag(false);
							order.setImportLocal(part.getLocalImport());
							order.setMpc(part.getMpc());
							order.setPartId(part.getPartNumber());
							order.setPartLevel(String.valueOf(part.getLevel()));
							order.setPl(po.getPl());
							order.setPo(po.getPo());
							order.setAdjustedDate(getFormattedDate(po.getFileDate()));
							order.setPoRecDate(po.getPoRecDate());
//							if(po.getTotal()!=null || !po.getTotal().isEmpty() || po.getTotal()!=""){
//							//	System.out.println("PO :: "+order.getPo()+"\tqty :: "+part.getQtyPer() * Double.valueOf(po.getTotal()));
//								order.setQuantity(part.getQtyPer()
//									* Double.valueOf(po.getTotal()));
//							}
//							else{
//								order.setQuantity(part.getQtyPer());
//							}
							order.setSku(po.getSku());
							order.setSkuCons(po.getTotal());
							finalPOOrders.add(order);
						}
						continue;
					 }
						else{
							POOrder order =null;
							order = new POOrder();
//							order.setAvCons("");
							order.setAvId(po.getSku());
							order.setAdjustedDate(getFormattedDate(po.getFileDate()));
							order.setCustomer(po.getCustomer());
							order.setFamily("NA");
							order.setCommodity("");
							order.setSupplier("");
							order.setFileDate(po.getFileDate());
							order.setFlag(false);
							order.setImportLocal("");
							order.setMpc("");
							order.setPartId(po.getSku());
							order.setPartLevel("");
							order.setPl(po.getPl());
							order.setPo(po.getPo());
							order.setPoRecDate(po.getPoRecDate());
//							if(po.getTotal()!=null || !po.getTotal().isEmpty() || po.getTotal()!=""){
//								order.setQuantity(Double.parseDouble(po.getTotal()));
//							}
//							else{
//								order.setQuantity(0.0);
//							}
							order.setSku(po.getSku());
							order.setSkuCons(po.getTotal());
							finalPOOrders.add(order);
							dataNotFound.add(po.getSku()+"::Sku");
							continue;
						}
					}
			}

			for (POOrder poorder : finalPOOrders) {
				String porecdate = "";
				if (poorder.getPoRecDate() != null || !poorder.getPoRecDate().isEmpty()) {
					porecdate = poorder.getPoRecDate();
				}
				if (porecdate == null || porecdate.isEmpty() || porecdate == ""
						|| porecdate.equalsIgnoreCase("Agdo PO")) {
					porecdate = poorder.getFileDate();
				}

				// checking file formate for PoRecDate with MM-dd-yyyy
				if (porecdate.contains("/"))
					poorder.setPoRecDate(porecdate.replace("/", "-"));
				porecdate = porecdate.replace("/", "-");
				if (porecdate.length() != 10) {
					String date[] = porecdate.split("-");
					if (date[0].length() != 2)
						date[0] = "0" + date[0];
					if (date[1].length() != 2)
						date[1] = "0" + date[1];
					if (date[2].length() != 4)
						date[2] = "20" + date[2];
					poorder.setPoRecDate(date[0] + "-" + date[1] + "-" + date[2]);
				}

				// adjusted date arranging based on PoRecDate
				Calendar cal = Calendar.getInstance();
				try {
					Date originaldate = DateUtil.stringToDate(porecdate, "MM-dd-yyyy");
					cal.setTime(originaldate);
					while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
						cal.add(Calendar.DATE, -1);
					}
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					poorder.setAdjustedDate(DateUtil.dateToString(cal.getTime(), "MM/dd/yyyy"));
					poorder.setDateobj(cal.getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
					poorder.setBu(getPlMaping(poorder.getPl()));
					poorder.setSkuPrice(priceUtill.getDateItemPrice(poorder.getSku(), porecdate));
					poorder.setAvPrice(priceUtill.getDateItemPrice(poorder.getAvId(), porecdate));
					poorder.setPartPrice(priceUtill.getDateItemPrice(poorder.getPartId(), porecdate));
				podao.createPO(poorder);
				podao.tempcreatePO(poorder);
			}
			Properties prop = new Properties();
			for (String s : dataNotFound) {
				// LOG.info(s);
				prop.setProperty(s, " not found");
			}
			// TODO
			addtoProperties(prop);
			LOG.info("Created " + finalPOOrders.size());
			finalPOOrders.clear();
		}
		Mail.sendMail("convertSKUtoAVtoPN updated sucessully "+new Date());
		LOG.info("----------------------completed---------convertSKUtoAVtoPN->"+new Date());
		}catch (Exception e) {
			e.printStackTrace();
			Mail.sendMail("Error while Executing IORedistrubution Daily JOB (convertSKUtoAVtoPN)"+e.getMessage()+new Date());
			LOG.info("Error while Executing IORedistrubution Daily JOB (convertSKUtoAVtoPN)"+e.getMessage()+new Date());
		}
		
	}
	
	public String getPlMaping(String existingPL){

		Map<String,String> plBaseUnitMap=new HashMap<String,String>();
			  
			  plBaseUnitMap.put("AN", "BNB");
			  plBaseUnitMap.put("KV", "CNB");
			  plBaseUnitMap.put("TAB", "CNB");
			  plBaseUnitMap.put("7F", "BPC");
			  plBaseUnitMap.put("BPC", "BPC");
			  plBaseUnitMap.put("5X", "WST");
			  plBaseUnitMap.put("CNB", "CNB");
			  plBaseUnitMap.put("6U", "BNB");
			  plBaseUnitMap.put("bNB", "BNB");
			  plBaseUnitMap.put("BNB", "BNB");
			  plBaseUnitMap.put("6J", "CPC");
			  plBaseUnitMap.put("4T", "CNB");
			  plBaseUnitMap.put("4t", "CNB");
			  plBaseUnitMap.put("5U", "BPC");
			  plBaseUnitMap.put("CPC", "CPC");
			  plBaseUnitMap.put("WKS", "WST");
			  plBaseUnitMap.put("AIO", "BPC");
			  plBaseUnitMap.put("TA", "BNB");
			  plBaseUnitMap.put("8J", "BNB");
			  plBaseUnitMap.put("te chamei", "BNB");
			  plBaseUnitMap.put("TB", "BNB");
			  plBaseUnitMap.put("", "EMT");
			  if(plBaseUnitMap.containsKey(existingPL))
			  return plBaseUnitMap.get(existingPL);
			  else
				  return existingPL;
	}
	
	
	public void generateOrginalOrderShortag() throws ParseException{
		try{
		LOG.info("----------------------started---------generateOrginalOrderShortag->"+new Date());
		Map<String,POOrderDB> poMap=new HashMap<String,POOrderDB>();
		List<POOrderDB> poOrders=new ArrayList<POOrderDB>();
		List<POOrder> finalPOOrders=null;

		for(POOrderDB po:podao.getPOOrderDB(poUtill.getRemaingDatesShortage())){
			String porecdate=po.getFileDate().replace("-", "/");
			if (porecdate.length() != 10) {
				String date[] = porecdate.split("/");
				if (date[0].length() != 2)
					date[0] = "0" + date[0];
				if (date[1].length() != 2)
					date[1] = "0" + date[1];
				if (date[2].length() != 4)
					date[2] = "20" + date[2];
				porecdate=date[0] + "/" + date[1] + "/" + date[2];
			}
			
			Date date=DateUtil.stringToDate(porecdate,"MM/dd/yyyy");
			if(date.before(DateUtil.stringToDate("01/01/2016", "MM/dd/yyyy"))){
				continue;
			}
			String key = po.getFileDate()+po.getPo()+po.getSku();
//			String total=po.getTotal();
//			if(poMap.containsKey(key)){
//				POOrderDB exisobj=poMap.get(key);
//				total=String.valueOf(Double.parseDouble(total)+Double.parseDouble(exisobj.getTotal()));
//			}
//			po.setTotal(total);
			poMap.put(key, po);
		}
		
		for(String key:poMap.keySet()){
			POOrderDB po=poMap.get(key);
			poOrders.add(po);

		}
		for(List<POOrderDB> porders:chopped(poOrders, 100)){
			finalPOOrders=new ArrayList<POOrder>();
		for(POOrderDB po:porders){
			List<MasterSkuAvBomBo> skuboms = null;
			List<MasterBomPojo> avParts =null;
			String porecdate="";
			if(po.getPoRecDate() != null || !po.getPoRecDate().equalsIgnoreCase("Agdo PO")){
				porecdate=po.getPoRecDate().replace("-", "/");
				}
				if(porecdate==null || porecdate.isEmpty() || porecdate=="" || porecdate.equalsIgnoreCase("Agdo PO")){
					porecdate=po.getFileDate().replace("-", "/");
				}
				porecdate=porecdate.replace("-", "/");
				if (porecdate.length() != 10) {
					String date[] = porecdate.split("/");
					if (date[0].length() != 2)
						date[0] = "0" + date[0];
					if (date[1].length() != 2)
						date[1] = "0" + date[1];
					if (date[2].length() != 4)
						date[2] = "20" + date[2];
					porecdate=date[0] + "/" + date[1] + "/" + date[2];
				}
				
				Date date=DateUtil.stringToDate(porecdate,"MM/dd/yyyy");
				
				//System.out.println("ORGINAL SHORTAGE po date::"+porecdate+" sku::"+po.getSku()+" filedate::"+po.getFileDate());
				skuboms = new ArrayList<MasterSkuAvBomBo>();

				if(getSkubomAvs(po.getSku(),porecdate)!=null){
				skuboms.addAll(getSkubomAvs(po.getSku(),porecdate));
				}
				if(!skuboms.isEmpty()){
					for(MasterSkuAvBomBo skuAv:skuboms){
						avParts = new ArrayList<MasterBomPojo>();
						avParts.addAll(bomUtillFinal.getPrimaryData(skuAv.getAv(), porecdate));
						if(!avParts.isEmpty()){
						for(MasterBomPojo part:avParts){
							POOrder order =null;
							order = new POOrder();
							order.setAvCons(po.getTotal());
							order.setAvId(part.getAv());
							order.setAdjustedDate(getFormattedDate(po.getFileDate()));
							order.setCustomer(po.getCustomer());
							order.setFamily(skuAv.getFamily());
							order.setCommodity(part.getCommodity());
							order.setSupplier(part.getSupplier());
							order.setFileDate(po.getFileDate());
							order.setFlag(false);
							order.setImportLocal(part.getLocalImport());
							order.setMpc(part.getMpc());
							order.setPartId(part.getPartNumber());
							order.setPartLevel(String.valueOf(part.getLevel()));
							order.setPl(po.getPl());
							order.setPo(po.getPo());
							order.setPoRecDate(po.getPoRecDate());
//							if(po.getTotal()!=null || !po.getTotal().isEmpty() || po.getTotal()!=""){
//								order.setQuantity(part.getQtyPer()
//									* Double.valueOf(po.getTotal()));
//							}
//							else{
//								order.setQuantity(part.getQtyPer());
//							}
							order.setSku(po.getSku());
							order.setSkuCons(po.getTotal());
							finalPOOrders.add(order);
						}
						continue;
					 }
						else{

							POOrder order =null;
							order = new POOrder();
							order.setAvCons(po.getTotal());
							order.setAvId(skuAv.getAv());
							order.setAdjustedDate(getFormattedDate(po.getFileDate()));
							order.setCustomer(po.getCustomer());
							order.setFamily(skuAv.getFamily());
							order.setFileDate(po.getFileDate());
							order.setCommodity("");
							order.setSupplier("");
							order.setFlag(false);
							order.setImportLocal("");
							order.setMpc("");
							order.setPartId(skuAv.getAv());
							order.setPartLevel("");
							order.setPl(po.getPl());
							order.setPo(po.getPo());
							order.setPoRecDate(po.getPoRecDate());
//							if(po.getTotal()!=null || !po.getTotal().isEmpty() || po.getTotal()!=""){
//								order.setQuantity(Double.parseDouble(po.getTotal()));
//							}
//							else{
//								order.setQuantity(0.0);
//							}
							order.setSku(po.getSku());
							order.setSkuCons(po.getTotal());
							finalPOOrders.add(order);
						
							dataNotFound.add(skuAv.getAv()+"::AV");
							continue;
						}
					}
					continue;
				}
				else{
					avParts = new ArrayList<MasterBomPojo>();
					avParts=bomUtillFinal.getPrimaryData(po.getSku(), porecdate);
					if(!avParts.isEmpty()){
					for(MasterBomPojo part:avParts){
						POOrder order =null;
						order = new POOrder();
						order.setAvCons(po.getTotal());
						order.setAvId(part.getAv());
						order.setCustomer(po.getCustomer());
						order.setFamily("NA");
						order.setCommodity(part.getCommodity());
						order.setSupplier(part.getSupplier());
						order.setFileDate(po.getFileDate());
						order.setFlag(false);
						order.setImportLocal(part.getLocalImport());
						order.setMpc(part.getMpc());
						order.setPartId(part.getPartNumber());
						order.setPartLevel(String.valueOf(part.getLevel()));
						order.setPl(po.getPl());
						order.setPo(po.getPo());
						order.setAdjustedDate(getFormattedDate(po.getFileDate()));
						order.setPoRecDate(po.getPoRecDate());
//						if(po.getTotal()!=null || !po.getTotal().isEmpty() || po.getTotal()!=""){
//							//System.out.println("PO :: "+order.getPo()+"\tqty :: "+part.getQtyPer() * Double.valueOf(po.getTotal()));
//							order.setQuantity(part.getQtyPer()
//								* Double.valueOf(po.getTotal()));
//						}
//						else{
//							order.setQuantity(part.getQtyPer());
//						}
						order.setSku(po.getSku());
						order.setSkuCons(po.getTotal());
						finalPOOrders.add(order);
					}
					continue;
				 }
					else{
						POOrder order =null;
						order = new POOrder();
//						order.setAvCons("");
						order.setAvId(po.getSku());
						order.setAdjustedDate(getFormattedDate(po.getFileDate()));
						order.setCustomer(po.getCustomer());
						order.setFamily("NA");
						order.setCommodity("");
						order.setSupplier("");
						order.setFileDate(po.getFileDate());
						order.setFlag(false);
						order.setImportLocal("");
						order.setMpc("");
						order.setPartId(po.getSku());
						order.setPartLevel("");
						order.setPl(po.getPl());
						order.setPo(po.getPo());
						order.setPoRecDate(po.getPoRecDate());
//						if(po.getTotal()!=null || !po.getTotal().isEmpty() || po.getTotal()!=""){
//							order.setQuantity(Double.parseDouble(po.getTotal()));
//						}
//						else{
//							order.setQuantity(0.0);
//						}
						order.setSku(po.getSku());
						order.setSkuCons(po.getTotal());
						finalPOOrders.add(order);
						dataNotFound.add(po.getSku()+"::Sku");
						continue;
					}
				}
		}
		
		for(POOrder poorder:finalPOOrders){

			String porecdate = "";
			if (poorder.getPoRecDate() != null || !poorder.getPoRecDate().isEmpty()) {
				porecdate = poorder.getPoRecDate();
			}
			if (porecdate == null || porecdate.isEmpty() || porecdate == "" || porecdate.equalsIgnoreCase("Agdo PO")) {
				porecdate = poorder.getFileDate();
			}

			// checking file formate for PoRecDate with MM-dd-yyyy
			if (porecdate.contains("/"))
			porecdate=porecdate.replace("/", "-");
			if (porecdate.length() != 10) {
				String date[] = porecdate.split("-");
				if (date[0].length() != 2)
					date[0] = "0" + date[0];
				if (date[1].length() != 2)
					date[1] = "0" + date[1];
				if (date[2].length() != 4)
					date[2] = "20" + date[2];
				poorder.setPoRecDate(date[0] + "-" + date[1] + "-" + date[2]);
			}

			// adjusted date arranging based on PoRecDate
			Calendar cal = Calendar.getInstance();
			try {
				Date originaldate = DateUtil.stringToDate(porecdate, "MM-dd-yyyy");
				cal.setTime(originaldate);
				while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
					cal.add(Calendar.DATE, -1);
				}
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				poorder.setAdjustedDate(DateUtil.dateToString(cal.getTime(), "MM/dd/yyyy"));
				poorder.setDateobj(cal.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
				poorder.setBu(getPlMaping(poorder.getPl()));
				poorder.setSkuPrice(priceUtill.getDateItemPrice(poorder.getSku(), porecdate));
				poorder.setAvPrice(priceUtill.getDateItemPrice(poorder.getAvId(), porecdate));
				poorder.setPartPrice(priceUtill.getDateItemPrice(poorder.getPartId(), porecdate));
			podao.createPOShortage(poorder);
		}
		Properties prop = new Properties();
		for (String s : dataNotFound) {
			// LOG.info(s);
			prop.setProperty(s, " not found");
		}
		//TODO
		addtoProperties(prop);
		//LOG.info("Created " + finalPOOrders.size());
		finalPOOrders.clear();
		}
		Mail.sendMail("generateOrginalOrderShortag updated sucessully "+new Date());
		LOG.info("-----------------completed----------generateOrginalOrderShortag"+new Date());
		}catch (Exception e) {
			e.printStackTrace();
			Mail.sendMail("Error while Executing IORedistrubution Daily JOB (generateOrginalOrderShortag) "+e.getMessage()+new Date());
		LOG.info("Error while Executing IORedistrubution Daily JOB (generateOrginalOrderShortag) "+e.getMessage()+new Date());
		}
		
	}
	
	
	public String getFormattedDate(String date) {
		String cDate = date;
		if (cDate.contains("-")) {
			cDate = cDate.replace("-", "/");
		}
		cDate = getPrevMonday(cDate);
		return cDate;
	}
	public String getPrevMonday(String date) {

		DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
		Calendar cal = Calendar.getInstance();
		Date cDate = null;

		try {
			cDate = format.parse(date);
		} catch (ParseException e) {
			LOG.info("Incorrect Date format " + date);
		}

		if (cDate != null) {
			cal.setTime(cDate);
			while (cal.get(cal.DAY_OF_WEEK) != Calendar.MONDAY) {
				cal.add(cal.DATE, -1);
			}
		}

		return format.format(cal.getTime());
	}
	private SortedSet<String> dataNotFound = new TreeSet<String>();
	
	/*public void test(){
		for(MasterSkuAvBomBo bo:podao.getALLskuTOav("F5W00AV#020", "02-16-2016"))
		System.out.println(bo);
	}*/
	
	public List<MasterSkuAvBomBo> getSkubomAvs(String skuId,String date) throws ParseException {
		if(date.length()!=10)
		{
			String date3[]=date.split("/");
			    if(date3[0].length()!=2)
				date3[0]="0"+date3[0];
			    if(date3[1].length()!=2)
				date3[1]="0"+date3[1];
			    if(date3[2].length()!=4)
					date3[2]="20"+date3[2];
			    date=(date3[0]+"/"+date3[1]+"/"+date3[2]);
		}
		List<MasterSkuAvBomBo> skuBomList=null;
		skuBomList=new ArrayList<MasterSkuAvBomBo>();
		skuBomList.addAll(podao.getALLskuTOav(skuId.trim(), date));
		List<MasterSkuAvBomBo> finalList= new ArrayList<MasterSkuAvBomBo>();
		if((skuBomList!=null)&&(!skuBomList.isEmpty())&&(skuBomList.size()!=0))
		{
			//LOG.info("Data Exist FOR ::"+skuId);
			
					String date1=skuBomList.get(0).getDate();
			for(MasterSkuAvBomBo msab:skuBomList){
				if(date1.equals(msab.getDate())){
					finalList.add(msab);
				}
			}
			return finalList;
		}else{
			List<MasterSkuAvBomBo> skuBomNearestList=null;
			skuBomNearestList=new ArrayList<MasterSkuAvBomBo>();
			skuBomNearestList.addAll(podao.getALLskuTOav(skuId.trim()));
			if((skuBomNearestList!=null)&&(!skuBomNearestList.isEmpty())&&(skuBomNearestList.size()!=0))
			{
				//LOG.info("Data Exist FOR ::"+skuId);

				Date date2 = null;
				try {
					date2 = closerDate(DateUtil.stringToDate(date, "MM/dd/yyyy"), getDatesList(skuBomNearestList));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			//TODO
				for(MasterSkuAvBomBo msab:skuBomNearestList){
					if(msab.getDate().equals(DateUtil.dateToString(date2, "MM-dd-yyyy"))){
						finalList.add(msab);
					}
				}
			}else{
				dataNotFound.add(skuId);
				//LOG.info(skuId + "Not available");
				return null;
			}
			}
		return finalList;
	}
	private static List<Date> getDatesList(List<MasterSkuAvBomBo> list)
	{
		List<Date> datesList=new ArrayList<Date>();
		for(MasterSkuAvBomBo skuBom:list)
		{
			try {
				datesList.add(DateUtil.stringToDate(skuBom.getDate(), "MM-dd-yyyy"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return datesList;
		
	}
	public static Date closerDate(Date originalDate, Collection<Date> unsortedDates) {
	     List<Date> dateList = new LinkedList<Date>(unsortedDates);
	     Collections.sort(dateList);
	     Iterator<Date> iterator = dateList.iterator();
	     Date previousDate = null;
	     while (iterator.hasNext()) {
	         Date nextDate = iterator.next();
	         if (nextDate.before(originalDate)) {
	             previousDate = nextDate;
	             continue;
	         } else if (nextDate.after(originalDate)) {
	             if (previousDate == null || isCloserToNextDate(originalDate, previousDate, nextDate)) {
	                 return nextDate;
	             }
	         } else {
	             return nextDate;
	         }
	     }
	     return previousDate;
	 }

	private static boolean isCloserToNextDate(Date originalDate, Date previousDate, Date nextDate) {
	     if(previousDate.after(nextDate))
	         throw new IllegalArgumentException("previousDate > nextDate");
	     return ((nextDate.getTime() - previousDate.getTime()) / 2 + previousDate.getTime() <= originalDate.getTime());
	 }
	public void addtoProperties(Properties prop) {
		LOG.info("*****Properties file*****" + prop.size());
		OutputStream output = null;

		try {

			output = new FileOutputStream("invoiceNotMatchedSkus.properties");

			// save properties to project root folder
			prop.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

	
/*	public void createPNPODATA() throws ParseException{
		podao.dropCollection("PNPO_ORDERS");
		Map<String,PNMaster> uid=new LinkedHashMap<String,PNMaster>();
		LOG.info("Start");
		for(POORDERNEW po:podao.getPOdata()){
			LOG.info(po);
			String porecdate="";
			if(po.getPoRecDate() != null || !po.getPoRecDate().equalsIgnoreCase("Agdo PO")){
				porecdate=po.getPoRecDate();
				}
				if(porecdate==null || porecdate.isEmpty() || porecdate=="" || porecdate.equalsIgnoreCase("Agdo PO")){
					porecdate=po.getFileDate();
				}
				porecdate=porecdate.replace("-", "/");
				if(porecdate.length()!=10)
				{
					String date3[]=porecdate.split("/");
					    if(date3[0].length()!=2)
						date3[0]="0"+date3[0];
					    if(date3[1].length()!=2)
						date3[1]="0"+date3[1];
					    if(date3[2].length()!=4)
							date3[2]="20"+date3[2];
					    porecdate=(date3[0]+"/"+date3[1]+"/"+date3[2]);
				}
			String key=getFormattedDate(porecdate)+po.getPartId();

			PNMaster pnm=new PNMaster();
			
			pnm.setPn(po.getPartId());
			pnm.setOriginalDate(porecdate);
			pnm.setpOReceivedDate(getFormattedDate(porecdate));
			if(uid.containsKey(key)){
				PNMaster pn=uid.get(key);
				pnm.setQty(pn.getQty()+po.getQuantity());
			}
			else{
				pnm.setQty(po.getQuantity());
			}
			pnm.setId(po.getPartId()+"&"+getFormattedDate(porecdate));
			uid.put(key, pnm);
		}
		
		for(String key:uid.keySet()){
			podao.savePNMData(uid.get(key));
		}
		podao.dropCollection("PO_ORDERS_SKU_AV_PN1");
	}*/
	
	
	/*public List<String> getDates(){
		List<String> str;
		str=new ArrayList<String>();
		Date startdate=DateUtil.stringToDatev01("02-01-2016", "MM-dd-yyyy");
		Date enddate=DateUtil.stringToDatev01("05-01-2016", "MM-dd-yyyy");
		for(int i=0;i<=200;i++){
		if(startdate.before(enddate)){
			str.add(DateUtil.dateToString(startdate, "MM-dd-yyyy"));
			 Calendar calendar = Calendar.getInstance();
			    calendar.setTime( startdate );
			    calendar.add(Calendar.DATE, 1);
			    startdate=calendar.getTime();
		}
		else{
			break;
		}
		}
		
		return str;
	}*/
	
	/*public void customPO() throws ParseException{
		Map<String,POOrderDB> poMap=new HashMap<String,POOrderDB>();
		List<POOrderDB> poOrders=new ArrayList<POOrderDB>();
		List<POOrder> finalPOOrders=null;
		List<String> dates=new ArrayList<String>();
		for(POOrderDB po:podao.getPOOrderDB(getDates())){
			String key = po.getFileDate()+po.getPo()+po.getSku();
			String total=po.getTotal();
			if(poMap.containsKey(key)){
				POOrderDB exisobj=poMap.get(key);
				total=String.valueOf(Double.parseDouble(total)+Double.parseDouble(exisobj.getTotal()));
			}
			po.setTotal(total);
			poMap.put(key, po);
		}
		
		for(String key:poMap.keySet()){
			POOrderDB po=poMap.get(key);
			poOrders.add(po);

		}
		for(List<POOrderDB> porders:chopped(poOrders, 100)){
			finalPOOrders=new ArrayList<POOrder>();
		for(POOrderDB po:porders){
			List<MasterSkuAvBomBo> skuboms = null;
			List<MasterBomPojo> avParts =null;
			String porecdate="";
			if(po.getPoRecDate() != null || !po.getPoRecDate().equalsIgnoreCase("Agdo PO")){
				porecdate=po.getPoRecDate().replace("-", "/");
				}
				if(porecdate==null || porecdate.isEmpty() || porecdate=="" || porecdate.equalsIgnoreCase("Agdo PO")){
					porecdate=po.getFileDate().replace("-", "/");
				}
				porecdate=porecdate.replace("-", "/");
				if (porecdate.length() != 10) {
					String date[] = porecdate.split("/");
					if (date[0].length() != 2)
						date[0] = "0" + date[0];
					if (date[1].length() != 2)
						date[1] = "0" + date[1];
					if (date[2].length() != 4)
						date[2] = "20" + date[2];
					porecdate=date[0] + "/" + date[1] + "/" + date[2];
				}
				
				Date date=DateUtil.stringToDate(porecdate,"MM/dd/yyyy");
				
				System.out.println("ORGINAL SHORTAGE po date::"+porecdate+" sku::"+po.getSku()+" filedate::"+po.getFileDate());
				skuboms = new ArrayList<MasterSkuAvBomBo>();

				if(getSkubomAvs(po.getSku(),porecdate)!=null){
				skuboms.addAll(getSkubomAvs(po.getSku(),porecdate));
				}
				if(!skuboms.isEmpty()){
					for(MasterSkuAvBomBo skuAv:skuboms){
						avParts = new ArrayList<MasterBomPojo>();
						avParts.addAll(bomUtillFinal.getPrimaryData(skuAv.getAv(), porecdate));
						if(!avParts.isEmpty()){
						for(MasterBomPojo part:avParts){
							POOrder order =null;
							order = new POOrder();
							order.setAvCons(po.getTotal());
							order.setAvId(part.getAv());
							order.setAdjustedDate(getFormattedDate(po.getFileDate()));
							order.setCustomer(po.getCustomer());
							order.setFamily(skuAv.getFamily());
							order.setCommodity(part.getCommodity());
							order.setSupplier(part.getSupplier());
							order.setFileDate(po.getFileDate());
							order.setFlag(false);
							order.setImportLocal(part.getLocalImport());
							order.setMpc(part.getMpc());
							order.setPartId(part.getPartNumber());
							order.setPartLevel(String.valueOf(part.getLevel()));
							order.setPl(po.getPl());
							order.setPo(po.getPo());
							order.setPoRecDate(po.getPoRecDate());
							if(po.getTotal()!=null || !po.getTotal().isEmpty() || po.getTotal()!=""){
								order.setQuantity(part.getQtyPer()
									* Double.valueOf(po.getTotal()));
							}
							else{
								order.setQuantity(part.getQtyPer());
							}
							order.setSku(po.getSku());
							order.setSkuCons(po.getTotal());
							finalPOOrders.add(order);
						}
						continue;
					 }
						else{

							POOrder order =null;
							order = new POOrder();
							order.setAvCons(po.getTotal());
							order.setAvId(skuAv.getAv());
							order.setAdjustedDate(getFormattedDate(po.getFileDate()));
							order.setCustomer(po.getCustomer());
							order.setFamily(skuAv.getFamily());
							order.setFileDate(po.getFileDate());
							order.setCommodity("");
							order.setSupplier("");
							order.setFlag(false);
							order.setImportLocal("");
							order.setMpc("");
							order.setPartId(skuAv.getAv());
							order.setPartLevel("");
							order.setPl(po.getPl());
							order.setPo(po.getPo());
							order.setPoRecDate(po.getPoRecDate());
							if(po.getTotal()!=null || !po.getTotal().isEmpty() || po.getTotal()!=""){
								order.setQuantity(Double.parseDouble(po.getTotal()));
							}
							else{
								order.setQuantity(0.0);
							}
							order.setSku(po.getSku());
							order.setSkuCons(po.getTotal());
							finalPOOrders.add(order);
						
							dataNotFound.add(skuAv.getAv()+"::AV");
							continue;
						}
					}
					continue;
				}
				else{
					avParts = new ArrayList<MasterBomPojo>();
					avParts=bomUtillFinal.getPrimaryData(po.getSku(), porecdate);
					if(!avParts.isEmpty()){
					for(MasterBomPojo part:avParts){
						POOrder order =null;
						order = new POOrder();
						order.setAvCons(po.getTotal());
						order.setAvId(part.getAv());
						order.setCustomer(po.getCustomer());
						order.setFamily("NA");
						order.setCommodity(part.getCommodity());
						order.setSupplier(part.getSupplier());
						order.setFileDate(po.getFileDate());
						order.setFlag(false);
						order.setImportLocal(part.getLocalImport());
						order.setMpc(part.getMpc());
						order.setPartId(part.getPartNumber());
						order.setPartLevel(String.valueOf(part.getLevel()));
						order.setPl(po.getPl());
						order.setPo(po.getPo());
						order.setAdjustedDate(getFormattedDate(po.getFileDate()));
						order.setPoRecDate(po.getPoRecDate());
						if(po.getTotal()!=null || !po.getTotal().isEmpty() || po.getTotal()!=""){
							System.out.println("PO :: "+order.getPo()+"\tqty :: "+part.getQtyPer() * Double.valueOf(po.getTotal()));
							order.setQuantity(part.getQtyPer()
								* Double.valueOf(po.getTotal()));
						}
						else{
							order.setQuantity(part.getQtyPer());
						}
						order.setSku(po.getSku());
						order.setSkuCons(po.getTotal());
						finalPOOrders.add(order);
					}
					continue;
				 }
					else{
						POOrder order =null;
						order = new POOrder();
						order.setAvCons("");
						order.setAvId(po.getSku());
						order.setAdjustedDate(getFormattedDate(po.getFileDate()));
						order.setCustomer(po.getCustomer());
						order.setFamily("NA");
						order.setCommodity("");
						order.setSupplier("");
						order.setFileDate(po.getFileDate());
						order.setFlag(false);
						order.setImportLocal("");
						order.setMpc("");
						order.setPartId(po.getSku());
						order.setPartLevel("");
						order.setPl(po.getPl());
						order.setPo(po.getPo());
						order.setPoRecDate(po.getPoRecDate());
						if(po.getTotal()!=null || !po.getTotal().isEmpty() || po.getTotal()!=""){
							order.setQuantity(Double.parseDouble(po.getTotal()));
						}
						else{
							order.setQuantity(0.0);
						}
						order.setSku(po.getSku());
						order.setSkuCons(po.getTotal());
						finalPOOrders.add(order);
						dataNotFound.add(po.getSku()+"::Sku");
						continue;
					}
				}
		}
		
		for(POOrder poorder:finalPOOrders){

			String porecdate = "";
			if (poorder.getPoRecDate() != null || !poorder.getPoRecDate().isEmpty()) {
				porecdate = poorder.getPoRecDate();
			}
			if (porecdate == null || porecdate.isEmpty() || porecdate == "" || porecdate.equalsIgnoreCase("Agdo PO")) {
				porecdate = poorder.getFileDate();
			}

			// checking file formate for PoRecDate with MM-dd-yyyy
			if (porecdate.contains("/"))
			porecdate=porecdate.replace("/", "-");
			if (porecdate.length() != 10) {
				String date[] = porecdate.split("-");
				if (date[0].length() != 2)
					date[0] = "0" + date[0];
				if (date[1].length() != 2)
					date[1] = "0" + date[1];
				if (date[2].length() != 4)
					date[2] = "20" + date[2];
				poorder.setPoRecDate(date[0] + "-" + date[1] + "-" + date[2]);
			}

			// adjusted date arranging based on PoRecDate
			Calendar cal = Calendar.getInstance();
			try {
				Date originaldate = DateUtil.stringToDate(porecdate, "MM-dd-yyyy");
				cal.setTime(originaldate);
				while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
					cal.add(Calendar.DATE, -1);
				}
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				poorder.setAdjustedDate(DateUtil.dateToString(cal.getTime(), "MM/dd/yyyy"));
				poorder.setDateobj(cal.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
				poorder.setBu(getPlMaping(poorder.getPl()));
				poorder.setSkuPrice(priceUtill.getDateItemPrice(poorder.getSku(), porecdate));
				poorder.setAvPrice(priceUtill.getDateItemPrice(poorder.getAvId(), porecdate));
				poorder.setPartPrice(priceUtill.getDateItemPrice(poorder.getPartId(), porecdate));
			podao.createPOShortageback(poorder);
		}
		Properties prop = new Properties();
		for (String s : dataNotFound) {
			// LOG.info(s);
			prop.setProperty(s, " not found");
		}
		//TODO
		addtoProperties(prop);
		LOG.info("Created " + finalPOOrders.size());
		finalPOOrders.clear();
		}
	}*/
	
}

