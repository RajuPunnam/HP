package com.inventory.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.dao.InventoryDaoFinal;
import com.inventory.finalpojos.MasterBomPojo;
import com.inventory.finalpojos.MovingAvgBo;
import com.inventory.finalpojos.PriceMaster;
import com.inventory.pojos.DOIBase;
import com.inventory.pojos.DOI_IO;
import com.inventory.utill.AgingBucketAggregation;
import com.inventory.utill.DOIDateUtill;
import com.inventory.utill.SkuDatesDescendingComp;
import com.io.common.Resources;
import com.io.utill.Mail;

@Service
public class DOIServicefinal {
	private static Logger LOG=Logger.getLogger(DOIServicefinal.class);
	@Autowired
	private InventoryDaoFinal dao;
	static List<String> doiIOdatesList=new ArrayList<String>();
	
	
	public void agg(){
		List<AgingBucketAggregation> list=dao.agingBucketAggregation("02-05-2016");
	}
	
	synchronized public String processDOI(){
		
		long oldRecords=0;	
		long countingrecords=0;
		long totalRecords=0;
		
		try{
		LOG.info("################# started  processDOI DOIService Method     ######################");
		BomUtillFinal bomUtill=new BomUtillFinal(dao.getMasterBomData());
		final String avsBom="MASTERAV_PN_BOM_V11";
		final String skuBom="MASTERSKUAVBOM";
		final String doiCollection="DOI";
		final String doi_IOCollection="DOI_IO";	
		
			List<String> doiAllMonthsDates=dao.getDistinctStringValues(doiCollection, "Date");
			
			List<String> doi_ioDates=dao.getDistinctStringValues(doi_IOCollection, "Date");	
			List<String> skusList=dao.getDistinctStringValues(skuBom, "sku");
			List<String> avsList=dao.getDistinctStringValues(avsBom, "AV");		 
			List<String> partsList=dao.getDistinctStringValues(avsBom, "part number");
			
			oldRecords=dao.getCollectionCount("DOI_IO");
			
			 int count=0;
			 for(String monthDate:doiAllMonthsDates){		
			 count++;		 
			 boolean status=true; 
		 if((doi_ioDates != null)&&(!doi_ioDates.isEmpty()) ){
			 for(String ioDate:doi_ioDates){
				 if(monthDate.equals(ioDate)){
					 //count++;	
					// doiIOdatesList.add(monthDate);
					 status=false;
					break;
				 }
			 }
			 }
			 if(status){							 
				 Map<String,DOI_IO> doiMap=new HashMap<String,DOI_IO>();
				 Map<String,DOI_IO> notMatchedParts=new HashMap<String,DOI_IO>();
				List<DOIBase> doiData= removeHPMfromParts(monthDate,doiCollection);
				doiIOdatesList.add(monthDate);
				 for(DOIBase dOIBase:doiData){
					 String partId=dOIBase.getItem();						
					 if(skusList.contains(partId)){
						 List<String> avs=dao.getSkuUniqueAvs(monthDate,partId, skuBom);
						 if((avs != null)&&(! avs.isEmpty())){
							 for(String av:avs){
								 LOG.info("av===="+av);
								 LOG.info("Date::"+monthDate);
								List<MasterBomPojo> lastLevelParts= bomUtill.getPrimaryData(av, monthDate);
								 processLogic(dOIBase, lastLevelParts, doiMap);			 
							 }
						 }					 
					 }//skuCheck
					 else if(avsList.contains(partId)){
						 List<MasterBomPojo> lastLevelParts=bomUtill.getPrimaryData(partId, monthDate);
						 processLogic(dOIBase, lastLevelParts, doiMap);							 
					 }
					 else if(partsList.contains(partId)){						
						 List<MasterBomPojo> subParts=bomUtill.getSubParts(partId, monthDate);				 
							if((subParts !=null)&&(!subParts.isEmpty())){
								if(bomUtill.isLastLevel()){
									for(MasterBomPojo priPart:subParts){
									priPart.setQtyPer(String.valueOf(1));
									}
								processLogic(dOIBase,subParts,doiMap);
								}else{												
									processLogic(dOIBase,subParts,doiMap);
								}							
							}
							}
					 else{
						 processNotMatchedParts( dOIBase, notMatchedParts);
					 }
				
				 
			 }				
			if(!doiMap.isEmpty()){
			List<DOI_IO> matchedList=new ArrayList<DOI_IO>();
			for(Map.Entry<String,DOI_IO> doimapValues:doiMap.entrySet()){	
				matchedList.add(doimapValues.getValue());
			}		
			dao.insertDoiIOData(matchedList,doi_IOCollection);
			countingrecords+=matchedList.size();
			//System.out.println("=========================================================================");
			//System.out.println("Matched Parts count    :"+matchedList.size()+":    Date       :"+monthDate);
			}	
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(!notMatchedParts.isEmpty()){
				List<DOI_IO> notmatchedList=new ArrayList<DOI_IO>();
			for(Map.Entry<String,DOI_IO> doimapValues:notMatchedParts.entrySet()){	
				notmatchedList.add(doimapValues.getValue());
			}
			dao.insertDoiIOData(notmatchedList,doi_IOCollection);
			countingrecords+=notmatchedList.size();
			//System.out.println("=========================================================================");
			//System.out.println("Not Matched Parts Count  :"+notmatchedList.size()+":  Date    :"+monthDate);
			}		
			//System.out.println("=========================================================================");
			//System.out.println("Weeks Completed   ==   :"+count+" :  == Total Weeks   ==   :"+ doiAllMonthsDates.size());			
			 }//end of status
			 try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 }//for monthdate
			   if(!doiIOdatesList.isEmpty()){
					activeAndDeadInventoryCalculation();
				}
			    totalRecords=dao.getCollectionCount(doi_IOCollection);
			    Mail.sendMail("DOI_IO updated sucessully "+"Old count is "+oldRecords+" Processed count "+countingrecords+"Total count "+totalRecords+" "+new Date());
				LOG.info("************************   DOI Completed      **************************");
				return "Success"+" "+oldRecords+" "+countingrecords+" "+totalRecords;
		}catch (Exception e) {
			e.printStackTrace();
			Mail.sendMail("Error while Executing IORedistrubution Daily JOB (DOI_IO) DOIServiceFinal"+e.getMessage()+"Old count is "+oldRecords+" Processed count "+countingrecords+"Total count "+oldRecords+" "+new Date());
		    LOG.info("Error while Executing IORedistrubution Daily JOB (DOI_IO) DOIServiceFinal"+e.getMessage()+"Old count is "+oldRecords+" Processed count "+countingrecords+"Total count "+oldRecords+" "+new Date());
		    return e.getMessage()+" "+oldRecords+" "+countingrecords+" "+oldRecords;
		}
		
		
	}
	
	
	
	public List<DOIBase> removeHPMfromParts(String date,String CollectionName){
		 List<DOIBase> doiMonthData=dao.getDOIMonthData(date,CollectionName);		
			for(DOIBase doiBase:doiMonthData){	
				String part=doiBase.getItem();
			if(part.contains("HPM1-")){			
				part=part.substring(5);
				doiBase.setItem(part);
			     }
		     }
	return doiMonthData;
	}
	
	public void processLogic(DOIBase doiBase,  List<MasterBomPojo> partsdata,Map<String,DOI_IO> doiMap){
		for(MasterBomPojo avpart:partsdata){
			 String partid=avpart.getPartNumber();
		if(doiMap.containsKey(partid)){	
			 DOI_IO doi_IO=doiMap.get(partid);
			 double doiIO_totalstk=doi_IO.getTotalStock();
			 double doiIO_netavbl=doi_IO.getNetQty();
			double reqQty=avpart.getQtyPer() ;
			double doiIO_transit=doi_IO.getTransit();
			double doiIO_toatal_demand=doi_IO.getTotalDemand();
			 //System.out.println("before netAvbl: doi netavbl :"+doiBase.getNetQty()+"  :map netavl:"+doiIO_netavbl+" :reqQtr  :"+reqQty);
			 doi_IO.setNetQty(doiIO_netavbl+(doiBase.getNetQty()*reqQty));
			 //System.out.println("after adding net available  :"+doi_IO.getNetQty());
			 //System.out.println("before totalstock: doi totalstock :"+doiBase.getTotalStock()+"  :map totalstock:"+doiIO_totalstk+" :reqQtr  :"+reqQty);
			doi_IO.setTotalStock(doiIO_totalstk+(doiBase.getTotalStock()*reqQty));
			//System.out.println("after adding  total stock    :"+doi_IO.getTotalStock());
			doi_IO.setTotalDemand(doiIO_toatal_demand+(doiBase.getTotal_Demand()*reqQty));
			doi_IO.setTransit(doiIO_transit+(doiBase.getTransit()*reqQty));
		}//map contains					 
		else{
			 DOI_IO doiIO=new DOI_IO(); 
			 doiIO.setDate(doiBase.getDate());
				doiIO.setPartNumber(partid);			
				doiIO.setDescription(avpart.getPartDescription());
				doiIO.setMakebuy(doiBase.getMakebuy());
				doiIO.setNetQty(doiBase.getNetQty()*avpart.getQtyPer());
				doiIO.setTotalStock(doiBase.getTotalStock()*avpart.getQtyPer());
				doiIO.setTotalDemand(doiBase.getTotal_Demand()*avpart.getQtyPer());
				doiIO.setTransit(doiBase.getTransit()*avpart.getQtyPer());
				doiIO.setSku(doiBase.getSku());				
				doiIO.setBaseunit(doiBase.getBaseunit());
				doiIO.setNotMatched(false);	
				doiIO.setDead(false);;	
		 		doiIO.setpCBABOM(avpart.getPcba());
		 		doiIO.setSupplier(avpart.getSupplier());		 		
				doiMap.put(partid,doiIO);
				
		}

		}// for AVtoPN
		}//method end
	
	public void processNotMatchedParts(DOIBase doiBase,Map<String,DOI_IO> notMatchedParts){
		 String partid=doiBase.getItem();
		 if(notMatchedParts.containsKey(partid)){	
		 	 DOI_IO doi_IO=notMatchedParts.get(partid);
		 	 double doiIO_totalstk=doi_IO.getTotalStock();
		 	 double doiIO_netavbl=doi_IO.getNetQty();	 	
		 	 doi_IO.setNetQty(doiIO_netavbl+doiBase.getNetQty());		 	 
		 	doi_IO.setTotalStock(doiIO_totalstk+doiBase.getTotalStock());		 	
		 }//map contains					 
		 else{
		 	 DOI_IO doiIO=new DOI_IO(); 
		 	 doiIO.setDate(doiBase.getDate());
		 		doiIO.setPartNumber(partid);			
		 		doiIO.setDescription(doiBase.getDescription());
		 		doiIO.setMakebuy(doiBase.getMakebuy());
		 		doiIO.setNetQty(doiBase.getNetQty());
		 		doiIO.setTotalStock(doiBase.getTotalStock());
		 		doiIO.setTotalDemand(doiBase.getTotal_Demand());
		 		doiIO.setTransit(doiBase.getTransit());
		 		doiIO.setSku(doiBase.getSku());				
		 		doiIO.setBaseunit(doiBase.getBaseunit());
		 		doiIO.setNotMatched(true);
		 		doiIO.setDead(false);	
		 		doiIO.setpCBABOM("NA");
		 		doiIO.setSupplier("");
		 		notMatchedParts.put(partid,doiIO);		
		 }
		
	}
	public void processDOI2(){
		System.out.println("#################   DOIService Method     ######################");
		BomUtillFinal bomUtill=new BomUtillFinal(dao.getMasterBomData());
		final String avsBom="MASTERAV_PN_BOM_V11";
		final String skuBom="MASTERSKUAVBOM";
		final String doiCollection="DOI";
		final String doi_IOCollection="DOI_IO";		
			 
			List<String> doiAllMonthsDates=dao.getDistinctStringValues(doiCollection, "Date");			
			/*List<String> doiAllMonthsDates=new ArrayList<String>();
			doiAllMonthsDates.add("01-05-2016");*/
			List<String> doi_ioDates=dao.getDistinctStringValues(doi_IOCollection, "Date");	
			List<String> skusList=dao.getDistinctStringValues(skuBom, "sku");
			List<String> avsList=dao.getDistinctStringValues(avsBom, "AV");		 
			List<String> partsList=dao.getDistinctStringValues(avsBom, "part number");
			 int count=0;
			 for(String monthDate:doiAllMonthsDates){		
			 count++;		 
			 boolean status=true; 
			 if((doi_ioDates != null)&&(!doi_ioDates.isEmpty()) ){
			 for(String ioDate:doi_ioDates){
				 if(monthDate.equals(ioDate)){
					status=false; 
				 }
			 }
			 }
			 if(status){			
				 List<DOI_IO> ioMatchedlList=new ArrayList<DOI_IO>();
				 List<DOI_IO> ioNotMatchedlList=new ArrayList<DOI_IO>();				
				 List<DOIBase> doiData=dao.getDOIMonthData(monthDate,doiCollection);				
				 for(DOIBase dOIBase:doiData){
					 String partId=dOIBase.getItem();
						if(partId.contains("HPM1-")){			
							partId=partId.substring(5);							
						     }					 
					 //String partId=dOIBase.getItem();
					 if(skusList.contains(partId)){
						 List<String> avs=dao.getSkuUniqueAvs(monthDate,partId, skuBom);
						 if((avs != null)&&(! avs.isEmpty())){
							 for(String av:avs){
								List<MasterBomPojo> lastLevelParts= bomUtill.getPrimaryData(av, monthDate);
								 processLogic11(dOIBase, lastLevelParts, ioMatchedlList);			 
							 }
						 }					 
					 }//skuCheck
					 else if(avsList.contains(partId)){
						 List<MasterBomPojo> lastLevelParts=bomUtill.getPrimaryData(partId, monthDate);
						 processLogic11(dOIBase, lastLevelParts, ioMatchedlList);							 
					 }
					 else if(partsList.contains(partId)){						
						 List<MasterBomPojo> subParts=bomUtill.getSubParts(partId, monthDate);				 
							if((subParts !=null)&&(!subParts.isEmpty())){
								if(bomUtill.isLastLevel()){
									for(MasterBomPojo priPart:subParts){
									priPart.setQtyPer(String.valueOf(1));
									}
								processLogic11(dOIBase,subParts,ioMatchedlList);
								}else{												
									processLogic11(dOIBase,subParts,ioMatchedlList);
								}							
							}
							}
					 else{
						 processNotMatchedParts( dOIBase, ioNotMatchedlList);
					 }
				
				 
			 }	
			
			 try {
				 
			if(!ioMatchedlList.isEmpty()){
				 dao.insertDoiIOData(ioMatchedlList);
				 System.out.println("=====================     total MATCHED recordas  ================     :"+ioMatchedlList.size());
			}
				Thread.sleep(10000);
				if(!ioNotMatchedlList.isEmpty()){
					 dao.insertDoiIOData(ioNotMatchedlList);
					 System.out.println("*******************    total Not MATCHED recordas  **********************     :"+ioNotMatchedlList.size());
				}
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 }
			 }//for monthdate
			 System.out.println("************************   DOI Completed      **************************");
	}
	
	public void processLogic11(DOIBase doiBase,  List<MasterBomPojo> partsdata,List<DOI_IO> ioMatchedlList){
		for(MasterBomPojo avpart:partsdata){	
			 DOI_IO doiIO=new DOI_IO(); 
			    doiIO.setDate(doiBase.getDate());
				doiIO.setPartNumber(avpart.getPartNumber());			
				doiIO.setDescription(avpart.getPartDescription());
				doiIO.setMakebuy(doiBase.getMakebuy());
				doiIO.setNetQty(doiBase.getNetQty()*avpart.getQtyPer());
				doiIO.setTotalStock(doiBase.getTotalStock()*avpart.getQtyPer());
				doiIO.setSku(doiBase.getSku());				
				doiIO.setBaseunit(doiBase.getBaseunit());
				doiIO.setBaseItem(doiBase.getItem());
				doiIO.setTotalDemand(doiBase.getTotal_Demand());
				doiIO.setNotMatched(false);		 	
		 		doiIO.setpCBABOM(avpart.getPcba());
		 		ioMatchedlList.add(doiIO);
		}// for AVtoPN
		}//method end
	public void processNotMatchedParts(DOIBase doiBase,List<DOI_IO> ioNotMatchedlList){
		
		 	    DOI_IO doiIO=new DOI_IO(); 
		 	    doiIO.setDate(doiBase.getDate());
		 		//doiIO.setPartNumber(partid);	
		 	    doiIO.setBaseItem(doiBase.getItem());
		 		doiIO.setDescription(doiBase.getDescription());
		 		doiIO.setMakebuy(doiBase.getMakebuy());
		 		doiIO.setNetQty(doiBase.getNetQty());
		 		doiIO.setTotalStock(doiBase.getTotalStock());
		 		doiIO.setSku(doiBase.getSku());				
		 		doiIO.setBaseunit(doiBase.getBaseunit());
		 		doiIO.setTotalDemand(doiBase.getTotal_Demand());
		 		doiIO.setNotMatched(true);		 				 		
		 		doiIO.setpCBABOM("NA");
		 		ioNotMatchedlList.add(doiIO);	
	}
	
	public void avslastlevelParts(){
		List<MasterBomPojo> masterBomList=dao.getMasterBomData();
		BomUtillFinal bomUtill=new BomUtillFinal(masterBomList);
		String avs[]={"N3C30AV#AC4","J1B49AV","N1W37AV#AC4","G0S70AV","E8V40AV","F5R74AV","C0F35AV#AC4","C0G05AV","M1T51AV","F5R63AV","E8X60AV","E8V35AV#ABM","E8V69AV","J1B66AV"};
		List<MasterBomPojo> avsData=new ArrayList<MasterBomPojo>();
		for(MasterBomPojo mbom:masterBomList){
		for(int i=0;i<avs.length;i++){
		if((mbom.getAv()).equals(avs[i])){
			avsData.add(mbom);
			break;
		}
			}
		}
		excelprocess(avsData,"AV_FullData");
		List<MasterBomPojo> lastLevelParts=new ArrayList<MasterBomPojo>();
		for(int i=0;i<avs.length;i++){
		lastLevelParts.addAll(bomUtill.getPrimaryData(avs[i], "12-30-2015"));		
		}
		//return lastLevelParts;
		excelprocess(lastLevelParts,"AV_LastLevelConverson");
	}
	public void testCaseForSKU(){
		List<MasterBomPojo> masterBomList=dao.getMasterBomData();
		BomUtillFinal bomUtill=new BomUtillFinal(masterBomList);		
		final String avsBom="MASTERAV_PN_BOM_V11";
		final String skuBom="MASTERSKUAVBOM";
		final String doiCollection="DOI";
		final String dOIDate="";
			List<String> skusList=dao.getDistinctStringValues(skuBom, "sku");												 
				 Map<String,DOI_IO> doiMap=new HashMap<String,DOI_IO>();				
				List<DOIBase> doiData= removeHPMfromParts(dOIDate,doiCollection);							
				 for(DOIBase dOIBase:doiData){
					 String partId=dOIBase.getItem();						
					 if(skusList.contains(partId)){
						 List<String> skuMatchedDates=dao.getDistinctStringValues(skuBom, "date");
						 List<String> avs=dao.getSkuUniqueAvs(dOIDate,partId, skuBom);
						 if((avs != null)&&(! avs.isEmpty())){
							 for(String av:avs){
								List<MasterBomPojo> lastLevelParts= bomUtill.getPrimaryData(av, dOIDate);
								 processLogic(dOIBase, lastLevelParts, doiMap);			 
							 }
						 }					 
					 }//skuCheck
					 
				 
						
			if(!doiMap.isEmpty()){
			List<DOI_IO> matchedList=new ArrayList<DOI_IO>();
			for(Map.Entry<String,DOI_IO> doimapValues:doiMap.entrySet()){	
				matchedList.add(doimapValues.getValue());
			}		
			
			
			 }//for monthdate
			
				 }
	}
	
	
	
	
	public void excelprocess(List<MasterBomPojo> lastLevelParts,String file_name){		
		 Workbook workbook = new XSSFWorkbook();		  
		Sheet skusheet = workbook.createSheet("AVSLastLevelParts");		
		         int rowIndex =0;	 
		             Row row = skusheet.createRow(rowIndex++);	
		             int cellIndex1 = 0;		
		             //first place in row is name		
		             row.createCell(cellIndex1++).setCellValue("AV");			             
		             row.createCell(cellIndex1++).setCellValue("PN");
		             row.createCell(cellIndex1++).setCellValue("part description");
		             row.createCell(cellIndex1++).setCellValue("Commodity");
		             row.createCell(cellIndex1++).setCellValue("commodityGroup");
		             row.createCell(cellIndex1++).setCellValue("Level");			             
		             row.createCell(cellIndex1++).setCellValue("QTY_Per");			             
		             row.createCell(cellIndex1++).setCellValue("position");	
		             row.createCell(cellIndex1++).setCellValue("type");	
		             row.createCell(cellIndex1++).setCellValue("MPC");	
		             row.createCell(cellIndex1++).setCellValue("eff date");	
		             row.createCell(cellIndex1++).setCellValue("end date");
		             row.createCell(cellIndex1++).setCellValue("Last Level");
		             row.createCell(cellIndex1++).setCellValue("PCBA");
		             row.createCell(cellIndex1++).setCellValue("Local/Import");
		        	 for(MasterBomPojo part:lastLevelParts){
		        		 row = skusheet.createRow(rowIndex++);
		        	 int cellIndex = 0;		
		             //first place in row is name	
		             row.createCell(cellIndex++).setCellValue(part.getAv());			             
		             row.createCell(cellIndex++).setCellValue(part.getPartNumber());
		             row.createCell(cellIndex++).setCellValue(part.getPartDescription());
		             row.createCell(cellIndex++).setCellValue(part.getCommodity());
		             row.createCell(cellIndex++).setCellValue(part.getCommodityGroup());
		             row.createCell(cellIndex++).setCellValue(part.getLevel());			             
		             row.createCell(cellIndex++).setCellValue(part.getQtyPer());			             
		             row.createCell(cellIndex++).setCellValue(part.getPosition());	
		             row.createCell(cellIndex++).setCellValue(part.getType());	
		             row.createCell(cellIndex++).setCellValue(part.getMpc());	
		             row.createCell(cellIndex++).setCellValue(part.getEffDate());	
		             row.createCell(cellIndex++).setCellValue(part.getEndDate());
		             row.createCell(cellIndex++).setCellValue(part.getLastLevel());
		             row.createCell(cellIndex++).setCellValue(part.getPcba());
		             row.createCell(cellIndex++).setCellValue(part.getLocalImport());
		       		        	 
		         }		        
		         //write this workbook in excel file.
		 
		         try {		
		             FileOutputStream fos = new FileOutputStream("C:/Users/Techout/Desktop/doi/"+file_name+".xlsx");		 
		             workbook.write(fos);		
		             fos.close();	
		             System.out.println("AVS Data written in Excel file ");
		             System.out.println("LOCATION ::  ---> C:/Users/Techout/Desktop/doi/"+file_name+".xlsx ");
		         } catch (FileNotFoundException e) {
	
		             e.printStackTrace();
		
		         } catch (IOException e) {
		
		             e.printStackTrace();
		
		         }
		
	}
	public void price(){	
		//List<DOIBase> doiData=dao.getDOIMonthData(monthDate,doiCollection);
		//DOIDateUtill doiDateUtill=new DOIDateUtill();
		final String doiCollection="DOI";
		final String doi_IOCollection="DOI_IO";
		final String priceMasterCollection="Combined_PriceMaster_Corrected";
		final String dOIDate="12-28-2015";			 
			 List<String> priceMasterParts=dao.getConditionalDistinctValues(priceMasterCollection,"Item","Type","PN");
			 List<String> priceMasterAVs=dao.getConditionalDistinctValues(priceMasterCollection,"Item","Type","AV");
			 List<String> priceMasterSKUs=dao.getConditionalDistinctValues(priceMasterCollection,"Item","Type","SKU");
			 List<DOIBase> doiBaseData=removeHPMfromParts(dOIDate, doiCollection);
			 for(DOIBase base:doiBaseData){
				 String part=base.getItem();
				 if(priceMasterSKUs.contains(part)){
					 PriceMaster priceMaster=dao.getPrice(dOIDate,part,"SKU" ) ;
					 base.setDollarPrice(priceMaster.getDollarPrice()); 
				 }
				 else if(priceMasterAVs.contains(part)){
					 PriceMaster priceMaster=dao.getPrice(dOIDate,part,"AV" ) ;
					 base.setDollarPrice(priceMaster.getDollarPrice());
				 }
				 else if(priceMasterParts.contains(part)){
					 PriceMaster priceMaster=dao.getPrice(dOIDate,part,"PN" ) ;
					 base.setDollarPrice(priceMaster.getDollarPrice());
				 }
				 else{
					 base.setDollarPrice(0); 
				 }
			 }
			 excelprocessforDOIBase(doiBaseData,"DOI_BaseFile"+dOIDate,"DOI");
			 List<DOI_IO> dOI_IOData=dao.getDOI_IOData(dOIDate,doi_IOCollection);
			 for(DOI_IO io:dOI_IOData){
				 String part=io.getPartNumber();
				 if(priceMasterParts.contains(part)){
					 PriceMaster priceMaster=dao.getPrice(dOIDate,part,"PN" ) ;
					 io.setPrice(priceMaster.getDollarPrice());
				 }
				 else{
					 io.setPrice(0);
				 }
				 
			 }
			 excelprocessforDOI_IO(dOI_IOData,"DOI_PNlevelConversion"+dOIDate,"PN_Conversion");
			 
			 
	}
	
	public void excelprocessforDOIBase(List<DOIBase> lastLevelParts,String file_name,String sheetName){		
		 Workbook workbook = new XSSFWorkbook();		  
		Sheet skusheet = workbook.createSheet(sheetName);		
		         int rowIndex =0;	 
		             Row row = skusheet.createRow(rowIndex++);	
		             int cellIndex1 = 0;		
		             //first place in row is name		
		             row.createCell(cellIndex1++).setCellValue("Date");			             
		             row.createCell(cellIndex1++).setCellValue("ITEM");
		             row.createCell(cellIndex1++).setCellValue("NETTABLE_INVENTORY");
		             row.createCell(cellIndex1++).setCellValue("TOTAL_STK");
		             row.createCell(cellIndex1++).setCellValue("Transit");
		             row.createCell(cellIndex1++).setCellValue("Total Demand to order");			             
		             row.createCell(cellIndex1++).setCellValue("Unit price$US");	            
		        	 for(DOIBase part:lastLevelParts){
		        		 row = skusheet.createRow(rowIndex++);
		        	 int cellIndex = 0;		
		             //first place in row is name	
		             row.createCell(cellIndex++).setCellValue(part.getDate());			             
		             row.createCell(cellIndex++).setCellValue(part.getItem());
		             row.createCell(cellIndex++).setCellValue(part.getNetQty());
		             row.createCell(cellIndex++).setCellValue(part.getTotalStock());
		             row.createCell(cellIndex++).setCellValue(part.getTransit());
		             row.createCell(cellIndex++).setCellValue(part.getTotal_Demand());			             
		             row.createCell(cellIndex++).setCellValue(part.getDollarPrice());         
		       		        	 
		         }		        
		         //write this workbook in excel file.
		 
		         try {		
		             FileOutputStream fos = new FileOutputStream("C:/Users/Techout/Desktop/doi/"+file_name+".xlsx");		 
		             workbook.write(fos);		
		             fos.close();	
		             System.out.println("File is succussfully written ");
		             System.out.println("LOCATION ::  ---> C:/Users/Techout/Desktop/doi/"+file_name+".xlsx ");
		         } catch (FileNotFoundException e) {
	
		             e.printStackTrace();
		
		         } catch (IOException e) {
		
		             e.printStackTrace();
		
		         }
		
	}
	
	public void excelprocessforDOI_IO(List<DOI_IO> lastLevelParts,String file_name,String sheetName){		
		 Workbook workbook = new XSSFWorkbook();		  
		Sheet skusheet = workbook.createSheet(sheetName);		
		         int rowIndex =0;	 
		             Row row = skusheet.createRow(rowIndex++);	
		             int cellIndex1 = 0;		
		             //first place in row is name		
		             row.createCell(cellIndex1++).setCellValue("Date");			             
		             row.createCell(cellIndex1++).setCellValue("PN");
		             row.createCell(cellIndex1++).setCellValue("NETTABLE_INVENTORY");
		             row.createCell(cellIndex1++).setCellValue("TOTAL_STK");
		             row.createCell(cellIndex1++).setCellValue("Transit");
		             row.createCell(cellIndex1++).setCellValue("Total Demand to order");			             
		             row.createCell(cellIndex1++).setCellValue("Unit price$US");		            
		             row.createCell(cellIndex1++).setCellValue("Part_in_PCBABOM");		            
		        	 for(DOI_IO part:lastLevelParts){
		        		 row = skusheet.createRow(rowIndex++);
		        	 int cellIndex = 0;		
		             //first place in row is name	
		             row.createCell(cellIndex++).setCellValue(part.getDate());			             
		             row.createCell(cellIndex++).setCellValue(part.getPartNumber());
		             row.createCell(cellIndex++).setCellValue(part.getNetQty());
		             row.createCell(cellIndex++).setCellValue(part.getTotalStock());
		             row.createCell(cellIndex++).setCellValue(part.getTransit());
		             row.createCell(cellIndex++).setCellValue(part.getTotalDemand());			             
		             row.createCell(cellIndex++).setCellValue(part.getPrice());
		             row.createCell(cellIndex++).setCellValue(part.getpCBABOM());  
		       		        	 
		         }		        
		         //write this workbook in excel file.
		 
		         try {		
		             FileOutputStream fos = new FileOutputStream("C:/Users/Techout/Desktop/doi/"+file_name+".xlsx");		 
		             workbook.write(fos);		
		             fos.close();	
		             System.out.println("File is succussfully written ");
		             System.out.println("LOCATION ::  ---> C:/Users/Techout/Desktop/doi/"+file_name+".xlsx ");
		         } catch (FileNotFoundException e) {
	
		             e.printStackTrace();
		
		         } catch (IOException e) {
		
		             e.printStackTrace();
		
		         }
		
	}
	
	public void activeAndDeadInventoryCalculation(){
		String outputcollection="DOI_IO_ActiveDeadInv";
		String doi_ioCollection="DOI_IO";
		//List<String> doiIOdatesList=dao.getDistinctStringValues(doi_ioCollection, "Date");	
		//List<String> doiIOActDeadDates=dao.getDistinctStringValues(outputcollection, "Date");
		//List<String> doiIOdatesList=new ArrayList<String>();	
		//doiIOdatesList.removeAll(doiIOActDeadDates);
		//List<String> doiIOdatesList=getDOIDates();
		Map<String,List<String>> partsPOrecivedDates=dao.getPartsPOrecievedDates();	
		System.out.println("PO orders parts Dates from activeAndDeadInventoryCalculation :"+partsPOrecivedDates.size());
		DOIDateUtill doiDateUtil=new DOIDateUtill();
		Map<String,Map<String,Double>> partsPrice=getPriceDataInMapforActivandDeadInv();
		System.out.println("price map count  from activeAndDeadInventoryCalculation :"+partsPrice.size());
		int count=0;
		for(String doiioDate:doiIOdatesList){
			List<DOI_IO> doiIOdata=dao.getDOIPNdataforDate(doiioDate,doi_ioCollection);
			System.out.println("DOI_IO data List size from activeAndDeadInventoryCalculation  :"+doiIOdata.size());
			for(DOI_IO doiIO:doiIOdata){
				String part=doiIO.getPartNumber();
				//PriceMaster price=dao.getPrice(doiioDate, part, "PN");				
				if(partsPrice.containsKey(part)){
					Map<String,Double> priceAtDate=partsPrice.get(part);
					Set<String> dates=priceAtDate.keySet();
			        List<String> datesList=new ArrayList<String>(dates);
					Collections.sort(datesList,new SkuDatesDescendingComp());
					String date=doiDateUtil.getNearestSKUMatchedDate("MM-dd-yyyy", doiioDate, datesList);					
					doiIO.setPrice(priceAtDate.get(date));
				}
				else{
					doiIO.setPrice(0);
				}
				/*if(price != null){
					doiIO.setPrice(price.getDollarPrice());
				}*/
				if(partsPOrecivedDates.containsKey(part)){					
					List<String> porecievedDates=partsPOrecivedDates.get(part);
					Map<String,Integer> lastorderDate=doiDateUtil.getPOrecievedDaysDifferencefromDOIDate("MM-dd-yyyy", doiioDate, porecievedDates);
					if(lastorderDate != null){					
						for(Map.Entry<String, Integer> entry:lastorderDate.entrySet()){
						doiIO.setLastOrderDate(entry.getKey());						
						int diffDays=(int)entry.getValue();
						doiIO.setDiffenceDays(diffDays);
						if((diffDays>0)&&(diffDays<=15)){
							doiIO.setGroup("0-15");									
						}else if (diffDays <= 30) {							
							doiIO.setGroup("16-30");
						} else if (diffDays <= 45) {
							doiIO.setGroup("31-45");
						} else if (diffDays <= 60) {
							doiIO.setGroup("46-60");
						} else if (diffDays <= 90) {
							doiIO.setGroup("61-90");
						} else if (diffDays <= 120) {
							doiIO.setGroup("91-120");
						} else if (diffDays <= 150) {
							doiIO.setGroup("121-150");
						} else if (diffDays <= 180) {
							doiIO.setGroup("151-180");
						} else if (diffDays <= 210) {
							doiIO.setGroup("181-210");
						} else if (diffDays > 210) {
							doiIO.setGroup("211-ABOUE");
						}
						
						break;
						}
					}else{
						doiIO.setDead(true);
					}
					
					
				}else{
					doiIO.setGroup("");
					doiIO.setDiffenceDays(0);
					doiIO.setLastOrderDate("");	
					doiIO.setDead(true);
				}
				doiIO.setTotalPrice(doiIO.getNetQty()*doiIO.getPrice());
			}//doiIOdata
			//dao.insertDoiIOData(doiIOdata,outputcollection);
			dao.updateDOIIOwithActiveandDeadIvn(doiIOdata,doi_ioCollection);
			count++;
			//System.out.println("============================================================================");
			//System.out.println("============================================================================");
			//LOG.info("completed weeks  :"+count+"  :   out of    :"+doiIOdatesList.size());
			//System.out.println("============================================================================");
			//System.out.println("============================================================================");
		}
		
		LOG.info("---------------->completed activeAndDeadInventoryCalculation()");
	}
	public void movingAverage(){
		final String field= "FCSTGENDATE";
		final String movingAvgCollections[]={"movingAvg6"};
		SimpleDateFormat dateFormat=new SimpleDateFormat("MM/dd/yyyy");
		for(int i=0;i<movingAvgCollections.length;i++){
		List<String> forecastgenDates=dao.getDistinctStringValues(movingAvgCollections[i], field);
		int count=0;
		for(String fcstGenDate:forecastgenDates){
			List<MovingAvgBo> movingAvgData=dao.getMovingAvgList(fcstGenDate,movingAvgCollections[i]);
			for(MovingAvgBo data:movingAvgData){
				String fcstDate=data.getFcstDate().trim();
		
				try {
					Date fcstDt=dateFormat.parse(fcstDate);
					Date fcstGenDt=dateFormat.parse(fcstGenDate.trim());
					int diffDays=getDifferenceDays(fcstDt,fcstGenDt);					
					data.setWeek(diffDays/7);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			dao.updateMovingAvg(movingAvgData,movingAvgCollections[i]);
			count++;
			//System.out.println("================================================================================");
			//System.out.println("================================================================================");
			//System.out.println("completed weeks   :"+count+":   out of  :"+forecastgenDates.size()+": collection name :"+movingAvgCollections[i]);
			//System.out.println("================================================================================");
			//System.out.println("================================================================================");
		}
		}
	}
	private  int getDifferenceDays(Date date1,Date date2){		
		long diffenceTime=(date1.getTime()-date2.getTime());
		int differencesDays=(int)(diffenceTime / (24 * 60 * 60 * 1000));		
	return differencesDays;
}
	public Map<String,Map<String,Double>> getPriceDataInMapforActivandDeadInv(){
		LOG.info("price processing method");
		final String collectionName="Combined_PriceMaster_Corrected";		
		Map<String,Map<String,Double>> priceMap=new HashMap<String,Map<String,Double>>();
		List<String> parts=dao.getConditionalDistinctValues(collectionName,"Item", "Type","PN");
		List<PriceMaster> partsPriceList=dao.getPriceDataList("PN");
		for(String part:parts){
			Map<String,Double> partPrices=new HashMap<String,Double>();
			for(PriceMaster price:partsPriceList){
				if(part.equals(price.getItem())){
					partPrices.put(price.getDate(), price.getDollarPrice());
				}				
			}
			priceMap.put(part,partPrices);
		}
		return priceMap;
	}



public void update() {
	List<String> doiAllMonthsDates=dao.getDistinctStringValues("DOI_IO", "Date");
	int count=0;
	for(String date:doiAllMonthsDates){
 List<DOI_IO> ioData=dao. getDOI_IOData(date ,"DOI_IO");
	for(DOI_IO io:ioData){		
		io.setTotalPrice(io.getNetQty()*io.getPrice());
	}
	dao.insertDoiIOData(ioData,"DOI_IO_total");
	try {
		Thread.sleep(5000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	count++;
	System.out.println("total competed  :"+count+"  : toatal   :"+doiAllMonthsDates.size());
	}
}


}
