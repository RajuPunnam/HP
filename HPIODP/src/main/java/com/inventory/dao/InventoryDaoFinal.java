package com.inventory.dao;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.group;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.inventory.finalpojos.DoiAgingDefaultChart;
import com.inventory.finalpojos.InvPNConversion;
import com.inventory.finalpojos.InvPNConversionFinal;
import com.inventory.finalpojos.InvoicesBaseData;
import com.inventory.finalpojos.MasterBomPojo;
import com.inventory.finalpojos.MovingAvgBo;
import com.inventory.finalpojos.PriceMaster;
import com.inventory.finalpojos.SKUBOM;
import com.inventory.pojos.DOIBase;
import com.inventory.pojos.DOI_IO;
import com.inventory.utill.AgingBucketAggregation;
import com.inventory.utill.DOIDateUtill;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

@Repository
public class InventoryDaoFinal {
	@Autowired
	private MongoOperations hpCleverPlanNew;
	
	static DOIDateUtill dOIDateUtill=new DOIDateUtill();
	public List<MasterBomPojo> getMasterBomData(){		
		List<MasterBomPojo> list=hpCleverPlanNew.findAll(MasterBomPojo.class);
		System.out.println("-------DATA LOADED INTO MASTERAV_PN_BOM_V11 LIST BOMUTILL CLASS IS "+list.size());
		return 	list;	
	}
	public List<String> getDistinctStringValues(String collectionName,String colunmName){
		return hpCleverPlanNew.getCollection(collectionName).distinct(colunmName);		
	}
	public List<String> getSkuUniqueAvs(String doiDate,String skuId,String CollName ){		
		BasicDBObject sku = new BasicDBObject("sku",new BasicDBObject("$eq",skuId));
		List<String> skuDates=hpCleverPlanNew.getCollection(CollName).distinct("date",sku); 
	   if(skuDates!=null && !skuDates.isEmpty()){		  
		String skuDate=dOIDateUtill.getNearestSKUMatchedDate("MM-dd-yyyy", doiDate, skuDates);
		sku.append("date", skuDate);
		return hpCleverPlanNew.getCollection(CollName).distinct("av",sku); 
		
		/*Query query = new Query(Criteria.where("sku").is(skuId).and("date").is(skuDate));		
	     List<SKUBOM> skubom=hpCleverPlanNew.find(query,SKUBOM.class);	    
	     for(SKUBOM bom:skubom){
	    	 avs.add(bom.getAv());	    	 
	     }*/
	     }
	   return null;
	   }
	 public void  insertDoiIOData(List<DOI_IO> dio_IOlist){
		  System.out.println("dio_IOlist  in dao  :"+dio_IOlist.size());
		  hpCleverPlanNew.insert(dio_IOlist,"DOI_IO");
		  System.out.println("***********inserted all********************");
		  
	  }
	 
	 public void  insertDoiIOData(List<DOI_IO> dio_IOlist,String collectionName){
		  String tempCollection=collectionName+"_SecurityTest";
		  System.out.println("dio_IOlist  in dao  :"+dio_IOlist.size());
		  hpCleverPlanNew.insert(dio_IOlist,collectionName);
		 
		  System.out.println("***********inserted all********************");
		  
	  }
	 public List<DOIBase> getDOIMonthData(final String Mth_Date ,String collection){    	
	    	return hpCleverPlanNew.find(new Query(Criteria.where("Date").is(Mth_Date)),DOIBase.class,collection);
	    }
	 public List<DOI_IO> getDOI_IOData(final String Mth_Date ,String collection){    	
	    	return hpCleverPlanNew.find(new Query(Criteria.where("Date").is(Mth_Date)),DOI_IO.class,collection);
	    }
	 public List<String> getConditionalDistinctValues(String collection,String outPutColumn,String conditionalColumn,String conditionalValue){
	 DBObject condition = new BasicDBObject(conditionalColumn,new BasicDBObject("$eq",conditionalValue));	
		return hpCleverPlanNew.getCollection(collection).distinct(outPutColumn,condition);
	 }
	 
	 public PriceMaster getPrice(String doiDate,String item,String type ){		
			Query query = new Query(Criteria.where("Item").is(item).and("Type").is(type));
			List<PriceMaster> priceMasterData=hpCleverPlanNew.find(query,PriceMaster.class);
			List<String> priceDates=new ArrayList<String>();
			for(PriceMaster pmaster:priceMasterData){				
				priceDates.add(pmaster.getDate());
			}
			if(!priceDates.isEmpty()){
				String priceDate=dOIDateUtill.getNearestSKUMatchedDate("MM-dd-yyyy", doiDate, priceDates);
				for(PriceMaster pmaster:priceMasterData){
					if((priceDate.equals(pmaster.getDate()))&&(item.equals(pmaster.getItem()))){
						return pmaster;
					}		
					
				}
			}				
				return null;
			
		   }
	 
	 public List<InvoicesBaseData> getInvoiceDateforparticularDate(String invoiceDate){
		 Query query = new Query(Criteria.where("Date").is(invoiceDate));
		return hpCleverPlanNew.find(query, InvoicesBaseData.class);
	 }
	 
	 public void savePNlevalInvoiceData(List<InvPNConversion> outputList,String collection_name){
		 hpCleverPlanNew.insert(outputList, collection_name);
	 }
	 public void savePNlevalInvoiceData(List<InvPNConversion> outputList){
		 hpCleverPlanNew.insert(outputList,InvPNConversion.class);
	 }
	 
	 public List<DOI_IO> getDOIPNdataforDate(String doiDate,String collectionName){
		 Query query = new Query(Criteria.where("Date").is(doiDate));
		 return hpCleverPlanNew.find(query, DOI_IO.class,collectionName);
	 }
	 public Map<String,List<String>> getPartsPOrecievedDates(){	
		 List<String> parts=hpCleverPlanNew.getCollection("PNPOMaster").distinct("PN");
		 Map<String,List<String>> partsRecievedDates=new HashMap<String,List<String>>();
		 for(String part:parts){
			 BasicDBObject pn = new BasicDBObject("PN",new BasicDBObject("$eq",part));
			 List<String> porecievedDates=hpCleverPlanNew.getCollection("PNPOMaster").distinct("PO Received Date",pn);
			 //Collections.sort(porecievedDates,new SkuDatesDescendingComp());
			 partsRecievedDates.put(part,porecievedDates);
		
		 }
		 return partsRecievedDates;
	 }
	 
	 public List<MovingAvgBo> getMovingAvgList(String fcstGenDate,String collectionName){
		 Query query = new Query(Criteria.where("FCSTGENDATE").is(fcstGenDate));
		 return hpCleverPlanNew.find(query, MovingAvgBo.class,collectionName);
		 
	 }
	 
	 public void updateMovingAvg(List<MovingAvgBo> mvgAvgList,String collectionName){
		 for(MovingAvgBo data:mvgAvgList){
		 hpCleverPlanNew.save(data,collectionName);	
		 }
	 }
	 public List<PriceMaster> getPriceDataList(String partType){
		 Query query = new Query(Criteria.where("Type").is(partType));
		 return hpCleverPlanNew.find(query,PriceMaster.class);	
	 }
	 public List<SKUBOM> getSkuData(String sku){		 
		 Query query = new Query(Criteria.where("sku").is(sku));
		 return hpCleverPlanNew.find(query,SKUBOM.class);	
		 
	 }
	 public void savePNlevalInvoiceData2(List<InvPNConversionFinal> outputList,String collection_name){
		   hpCleverPlanNew.insert(outputList, collection_name);
		  }
	 
	 public void  updateDOIIOwithActiveandDeadIvn(List<DOI_IO> dio_IOlist,String collectionName){
		 String temp=collectionName+"_SecurityTest";
		  System.out.println("dio_IOlist  in dao  :"+dio_IOlist.size());
		  for(DOI_IO io:dio_IOlist){
		  hpCleverPlanNew.save(io,collectionName);
		  }
		  System.out.println("***********  update data ********************");
		  
	  }
	 
	 public List<DOI_IO> getAgingIventoryData(String doiDate){
			Query query=new Query(Criteria.where("Date").is(doiDate).and("Age").ne(""));
			/*Query query=new Query(Criteria.where("Date").is(doiDate));
			query.addCriteria(Criteria.where("Date").ne(""));*/
			return  hpCleverPlanNew.find(query, DOI_IO.class);		
		}
		public List<String> getUniqueDOI_IODateList(){		
			 return  hpCleverPlanNew.getCollection("DOI_IO").distinct("Date");
		}
		
		public void createOrUpdateDefaultChart(DoiAgingDefaultChart chart){
			 hpCleverPlanNew.insert(chart);
		}
		
		public List<MasterBomPojo> getAVdatafromMasterBom(String av){
			
			Query query = new Query(Criteria.where("AV").is(av));
			return hpCleverPlanNew.find(query, MasterBomPojo.class);
			
		}
		
		public List<AgingBucketAggregation> agingBucketAggregation(String date){	
				
				Aggregation agg = newAggregation(
					match(Criteria.where("Date").is(date)),
					group("Age","Date").sum("NETTABLE_INVENTORY").as("bucketTotalPrice"),
					project("bucketTotalPrice").and("Age").previousOperation()
					//sort(Sort.Direction.DESC, "Age")
						
				);

				//Convert the aggregation result into a List
				AggregationResults<AgingBucketAggregation> groupResults 
					= hpCleverPlanNew.aggregate(agg, "DOI_IO", AgingBucketAggregation.class);
				List<AgingBucketAggregation> result = groupResults.getMappedResults();
				
				return result;
				
			}
		
		public long getCollectionCount(String collectionName) {
			return hpCleverPlanNew.getCollection(collectionName).count();
		} 
}
