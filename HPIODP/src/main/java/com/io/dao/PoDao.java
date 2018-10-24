package com.io.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inventory.finalpojos.InvPNConversion;
import com.inventory.finalpojos.InvoicesBaseData;
import com.io.pojos.MasterSkuAvBomBo;
import com.io.pojos.POOrder;
import com.io.pojos.POOrderDB;
import com.io.utill.Mail;
@Repository
public class PoDao extends CommonDao {
	private String collectionName;
	private static final Logger LOG = Logger.getLogger(PoDao.class.getName());

	public List<POOrderDB> getPOOrderDB() {
		LOG.debug("Fetching Data from OPEN_ORDERS");
		return mongoTemplate.findAll(POOrderDB.class, "OPEN_ORDERS");
	}
	
	//----------------method to get poorder data--------------------//
	public List<POOrderDB> getOredrDataByDate(List<String> date){
		Query query = new Query(Criteria.where("File Date").in(date));
		return mongoTemplate.find(query,POOrderDB.class);
	}

	public void savePOOrderClean(POOrderDB order) {
		LOG.debug("insert into OPEN_ORDER_CLEAN :: \t" + order);
		mongoTemplate.insert(order, "OPEN_ORDER_CLEAN");
	}

	public List<POOrderDB> getOpenOrderClean() {
		LOG.debug("Fetching data form OPEN_ORDER_CLEAN");
		return mongoTemplate.findAll(POOrderDB.class, "OPEN_ORDER_CLEAN");
	}
	
	// Save Data in PO_ORDERS_SKU_AV_PN
		public void createPO(POOrder po) {
			LOG.debug("insert into PO_ORDERS_SKU_AV_PN :: \t"+po);
			mongoTemplate.insert(po, "PO_ORDERS_SKU_AV_PN");
		}
		public void tempcreatePO(POOrder po) {
			LOG.debug("insert into PO_ORDERS_SKU_AV_PN :: \t"+po);
			mongoTemplate.insert(po, "PO_ORDERS_SKU_AV_PN1");
		}
		
		
		public List<POOrderDB> getPOOrderDB(List<String> list) {
			Query query = new Query(Criteria.where("File Date").in(list));
			LOG.debug(query+"Collection :: OPEN_ORDERS");
			return mongoTemplate.find(query, POOrderDB.class, "OPEN_ORDERS");
		}
		
		//Save Data in ORIG_PO_SHORTAGE
		public void createPOShortage(POOrder po) {
			LOG.debug("Insert into ORIG_PO_SHORTAGE :: \t"+po);
			mongoTemplate.insert(po, "ORIG_PO_SHORTAGE");
		}
		
		//Fetching data from MASTERSKUAVBOM for an sku with date
		public List<MasterSkuAvBomBo> getALLskuTOav(String sku, String date) {
			collectionName = "MASTERSKUAVBOM";
			String arr[] = null;
			if (date.contains("-")) {
				arr = date.split("-");
			} else {
				arr = date.split("/");
			}

			Query query = new Query()
					.addCriteria(Criteria.where("sku").is(sku).and("date").regex("^" + arr[0] + ".*" + arr[2] + "$"));
			LOG.debug(query+"Collection :: "+collectionName);

			return mongoTemplate.find(query, MasterSkuAvBomBo.class, collectionName);
		}
		
		//Fetching data from MASTERSKUAVBOM for an sku 
		public List<MasterSkuAvBomBo> getALLskuTOav(String sku) {
			collectionName = "MASTERSKUAVBOM";
			Query query = new Query().addCriteria(Criteria.where("sku").is(sku));
			LOG.debug(query+"Collection MASTERSKUAVBOM ");
			return mongoTemplate.find(query, MasterSkuAvBomBo.class, collectionName);
		}
		
		
		
		
		//--------------------PO processing----------------------//
		@SuppressWarnings("unchecked")
		public List<String> getDistinctStringValues(String collectionName,String colunmName){
			return mongoTemplate.getCollection(collectionName).distinct(colunmName);		
		}
		
		public String savePOOrder(List<POOrder> pOOrderList){
			try{
//			mongoTemplate.remove(new Query(), "FORECAST_COMBINED_DATA_NEW_W0To26");
			LOG.info("----------------ORIG_PO_SHORTAGE data size-------------"+pOOrderList.size());
			int count = 0;
			int printCount = 100000;
			for(POOrder pOOrder: pOOrderList){	
				if(count == printCount){
					LOG.info("ORIG_PO_SHORTAGE "+printCount+" records inserted");
					printCount+=100000;
				}
				count++;
				mongoTemplate.insert(pOOrder, "ORIG_PO_SHORTAGE");
			}		
			
			return "Success";
			}catch(Exception e){
				Mail.sendMail("!!!------ Error while inserting ORIG_PO_SHORTAGE  "+new Date());
				return e.getMessage();
			}
			
		 }
		public String savePoSkuAvPn(List<POOrder> pOOrderList){
			try{
			LOG.info("----------------PO_ORDERS_SKU_AV_PN data size-------------"+pOOrderList.size());
//			mongoTemplate.remove(new Query(), "FORECAST_COMBINED_DATA_NEW_W0To26");
			int count = 0;
			int printCount = 100000;
			for(POOrder pOOrder: pOOrderList){	
				if(count == printCount){
					LOG.info("PO_ORDERS_SKU_AV_PN "+printCount+" records inserted");
					printCount+=100000;
				}
				count++;
				mongoTemplate.insert(pOOrder, "PO_ORDERS_SKU_AV_PN");
			}	
			
			return "Success";
			}catch(Exception e){
				Mail.sendMail("!!!------ Error while inserting PO_ORDERS_SKU_AV_PN  "+new Date());
				return e.getMessage();
			}
		 }
		
		
		
		//---------------------given by srinivas-------------------------------//
		public List<InvoicesBaseData> getInvoiceDateforparticularDate(List<String> invoiceDate){
			 Query query = new Query(Criteria.where("File Date").in(invoiceDate));
			 List<InvoicesBaseData> list= mongoTemplate.find(query, InvoicesBaseData.class);
			LOG.info("----------invoicesBaseData for number of dates--------"+invoiceDate+" are "+list.size());
			return list;
		 }
		
		public String saveIvnoiceAVPN(List<InvPNConversion> invoiceAVPNList){
			try{
				LOG.info("--------------INVOICE_AV_PN  data size---------------"+invoiceAVPNList.size());
//			mongoTemplate.remove(new Query(), "FORECAST_COMBINED_DATA_NEW_W0To26");
			int count = 0;
			int printCount = 100000;
			for(InvPNConversion invoiceAVPN: invoiceAVPNList){	
				if(count == printCount){
					LOG.info("INVOICE_AV_PN "+printCount+" records inserted");
					printCount+=100000;
				}
				count++;
				mongoTemplate.insert(invoiceAVPN, "INVOICE_AV_PN");
			}	
			return "Success";
			}catch(Exception e){
				Mail.sendMail("!!!------ Error while inserting INVOICE_AV_PN  "+new Date());
				return e.getMessage();
			}
		 }
		
		public long getCollectionCount(String collectionName) {
			LOG.info("Requested Collection count for "+collectionName);
			return mongoTemplate.getCollection(collectionName).count();
		}
}
