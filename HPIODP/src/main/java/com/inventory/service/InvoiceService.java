package com.inventory.service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.inventory.dao.InventoryDaoFinal;
import com.inventory.finalpojos.InvPNConversion;
import com.inventory.finalpojos.InvPNConversionFinal;
import com.inventory.finalpojos.InvoicesBaseData;
import com.inventory.finalpojos.MasterBomPojo;
import com.inventory.finalpojos.PriceMaster;
import com.inventory.finalpojos.SKUBOM;
import com.inventory.utill.DOIDateUtill;
import com.inventory.utill.SkuDatesDescendingComp;
import com.io.common.POFromHpCreateCleanPO;
import com.io.common.Resources;
import com.io.utill.Mail;

public class InvoiceService {
	private static Logger LOG=Logger.getLogger(InvoiceService.class);
	@Autowired
	private InventoryDaoFinal dao;
	
	/*public static  ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("Spring.xml");
	public static InventoryDaoFinal dao=ctx.getBean("inventoryDaoFinal", InventoryDaoFinal.class);	
	public static void main(String[] args) {
		InvoiceService invoiceService=new InvoiceService();
		//invoiceService.invoiceProcess();
		invoiceService.invoiceProcess2();
		}	*/
	
	public void invoiceProcess(){	
		
		final String skuBom="MASTERSKUAVBOM";
		final String inv_PN="INVOICES_PN";
		final String invoiceCollection="INVOICES";		
		List<String> invoiceDates=dao.getDistinctStringValues(invoiceCollection, "Date");
		List<String> invoicePNDates=dao.getDistinctStringValues(inv_PN, "Date");
		List<String> skusList=dao.getDistinctStringValues(skuBom, "sku");
		/*		
		invoiceDates.removeAll(invoicePNDates);
		for(String invoiceDate:invoiceDates){
			System.out.println("after remove  :"+invoiceDate);
		}	*/
		
		int count=0;
		BomUtillFinal bomUtill=new BomUtillFinal(dao.getMasterBomData());
		loop1:for(String invoiceDate:invoiceDates){
			for(String pnDate:invoicePNDates){
				if(invoiceDate.equals(pnDate)){
					count++;
					continue loop1;
				}
			}						
			List<InvoicesBaseData> invoiceData=dao.getInvoiceDateforparticularDate(invoiceDate);
			//System.out.println("invoice data size  :"+invoiceData.size());
			Map<String,List<InvPNConversion>> outputMap=new HashMap<String,List<InvPNConversion>>();
			List<InvPNConversion> notMatchedList=new ArrayList<InvPNConversion>();
			Map<String,Double> partsPrice=new HashMap<String,Double>();
			//System.out.println("skus  list from Bom   :"+skusList.size());
			for(InvoicesBaseData invoice:invoiceData){
				String invoiceSku=invoice.getSku();							
				if(skusList.contains(invoiceSku)){
					String skuandPO=invoiceSku+invoice.getPo();	
					if(!outputMap.containsKey(skuandPO)){						
						List<InvPNConversion> outputList =new ArrayList<InvPNConversion>();
					List<String> avs=dao.getSkuUniqueAvs(invoiceDate,invoiceSku,skuBom);
					PriceMaster skuPrice=dao.getPrice(invoiceDate,invoiceSku,"SKU" );
					double sku_Price=0;
					if(skuPrice !=null)
						sku_Price=skuPrice.getDollarPrice();
					for(String av:avs){
						PriceMaster avPrice=dao.getPrice(invoiceDate,av,"AV" );
						double av_Price=0;
						if(avPrice !=null)
							av_Price=avPrice.getDollarPrice();
						String replaceinvoiceDate=invoiceDate.replaceAll("['/','_','.', ]","-");
						List<MasterBomPojo> partsperAV=bomUtill.getPrimaryData(av, replaceinvoiceDate);
						for(MasterBomPojo partpojo:partsperAV){
							String partID=partpojo.getPartNumber();
							double part_Price=0;
							if(partsPrice.containsKey(partID)){
								part_Price=partsPrice.get(partID);
							}else{
								PriceMaster partPrice=dao.getPrice(invoiceDate,partID,"PN" );								
								if(partPrice !=null)
									part_Price=partPrice.getDollarPrice();	
							}													
							outputList.add(processNotContainedSKUinOutPutMap(sku_Price,av_Price,part_Price,partpojo,invoice));
						}//parts loop
					}//avs loop
					outputMap.put(skuandPO,outputList);
					}// outputmap check 
					else{
						processContainedSKUinOutPutMap(skuandPO, invoice,outputMap);
					}
					
				}//sku checking
				else{
					notMatchedList.add(notMatchedSkus(invoice));
				}
				
			}//invoicedata loop	
			if(!notMatchedList.isEmpty()){
				dao.savePNlevalInvoiceData(notMatchedList);	
				System.out.println("Not matche skus  count    :"+notMatchedList.size());
			}
			if(!outputMap.isEmpty()){
				
				for (Map.Entry<String,List<InvPNConversion>> entry : outputMap
						.entrySet()) {
					
					Map<String,InvPNConversion> invoice_PNMap=new HashMap<String,InvPNConversion>();
					processInvoice_PN(invoice_PNMap,entry.getValue());					
					dao.savePNlevalInvoiceData(entry.getValue());
					if(!invoice_PNMap.isEmpty()){	
						List<InvPNConversion> ouputPNList=new ArrayList<InvPNConversion>();
						for (Map.Entry<String,InvPNConversion> entryPn : invoice_PNMap
								.entrySet()) {
							ouputPNList.add(entryPn.getValue());						
						}
						dao.savePNlevalInvoiceData(ouputPNList,inv_PN);
					}
				}				
				
				//System.out.println("===============================================================================");
				//System.out.println("total PO orders  :"+outputMap.size()+" :     for  invoic Date  :"+invoiceDate);				
				//System.out.println("===============================================================================");
				count++;
			}//map empty check
			//System.out.println(" invoice data completed count  :"+count+" :    total count    :"+invoiceDates.size());
			//System.out.println("===============================================================================");
		}
		LOG.info("--------------------------    INVOICES PN CONVERSION COMPLETED    =============================");
		
	}
 public InvPNConversion processNotContainedSKUinOutPutMap(double sku_Price,double av_Price,double part_Price,MasterBomPojo part,InvoicesBaseData invoice){
	 InvPNConversion pn_invoice=new InvPNConversion();
	 pn_invoice.setAdjustedDate("");
	 pn_invoice.setAv(part.getAv());
	 pn_invoice.setAv_Price(av_Price);	
	 pn_invoice.setFileDate(invoice.getFileDate());
	 pn_invoice.setPart_Price(part_Price);
	 pn_invoice.setPartId(part.getPartNumber());
	 pn_invoice.setPl(invoice.getPl());
	 pn_invoice.setPo(invoice.getPo());
	 pn_invoice.setPoDate(invoice.getInvoiceDate());
	 pn_invoice.setQtyPer(part.getQtyPer());	
	 pn_invoice.setSku(invoice.getSku());
	 pn_invoice.setSku_Price(sku_Price);
	 pn_invoice.setSkuQty(invoice.getSkuQuantity());
	 pn_invoice.setAvQty(invoice.getSkuQuantity());
	 pn_invoice.setPartQty(invoice.getSkuQuantity()*part.getQtyPer());
	 pn_invoice.setPartDescription(part.getPartDescription());
	 pn_invoice.setNotMatched(false);
	 pn_invoice.setSupplier(part.getSupplier());
	 return pn_invoice;
 }
 public void processContainedSKUinOutPutMap(String skuandPO,InvoicesBaseData invoice,Map<String,List<InvPNConversion>> outputMap){
	 List<InvPNConversion> invoice_pnList=outputMap.get(skuandPO);
	 for(InvPNConversion pnInvoice:invoice_pnList){		 
		 int skuqty=pnInvoice.getSkuQty();
		 pnInvoice.setSkuQty(skuqty+invoice.getSkuQuantity());
		 pnInvoice.setAvQty(skuqty+invoice.getSkuQuantity());
		 pnInvoice.setPartQty((skuqty+invoice.getSkuQuantity())*pnInvoice.getQtyPer());
	 }
 }
 public InvPNConversion notMatchedSkus(InvoicesBaseData invoice){
	 InvPNConversion notMatchedInv=new InvPNConversion();	
	 PriceMaster skuPrice=dao.getPrice(invoice.getInvoiceDate(),invoice.getSku(),"SKU" );
		double sku_Price=0;
		if(skuPrice !=null)
			sku_Price=skuPrice.getDollarPrice();
		notMatchedInv.setSku_Price(sku_Price);
		 notMatchedInv.setPl(invoice.getPl());
		 notMatchedInv.setPo(invoice.getPo());
		 notMatchedInv.setPoDate(invoice.getInvoiceDate());
		 notMatchedInv.setSkuQty(invoice.getSkuQuantity());
		 notMatchedInv.setSku(invoice.getSku());
		 notMatchedInv.setFileDate(invoice.getFileDate());
		 notMatchedInv.setNotMatched(true);
		 return notMatchedInv;
 } 
 public void processInvoice_PN(Map<String,InvPNConversion> invoice_PNMap,List<InvPNConversion> pnList){	 
	 if(!pnList.isEmpty()){
		 for(InvPNConversion pn:pnList){
			 String part=pn.getPartId();
			 if(invoice_PNMap.containsKey(part)){
				 InvPNConversion containPN=invoice_PNMap.get(part);
				 double partQty=containPN.getPartQty();
				 containPN.setPartQty(partQty+pn.getPartQty());	
				 double pnPrice=containPN.getPart_Price();
				 containPN.setTotalPrice((partQty+pn.getPartQty())*pnPrice);
			 }
			 else{
				 InvPNConversion uniquePN=new InvPNConversion();
				 uniquePN.setFileDate(pn.getFileDate());
				 uniquePN.setPo(pn.getPo());
				 uniquePN.setPoDate(pn.getPoDate());
				 uniquePN.setAdjustedDate("");
				 uniquePN.setPartId(pn.getPartId());
				 uniquePN.setPartDescription(pn.getPartDescription());
				 uniquePN.setPartQty(pn.getPartQty());
				 uniquePN.setPart_Price(pn.getPart_Price());
				 uniquePN.setSupplier(pn.getSupplier());
				 uniquePN.setTotalPrice(pn.getPartQty()*pn.getPart_Price());
				 invoice_PNMap.put(part,uniquePN);
			 }
			 
		 }
	 }
 }

		public void invoiceProcess2(){	
			try{
			LOG.info("------------------------>started invoiceProcess2"+new Date());
			final String sku_av="INVOICES_AV_PN";
			final String inv_PN="INVOICES_PN";
			final String invoiceCollection="INVOICES";		
			List<String> invoiceDates=dao.getDistinctStringValues(invoiceCollection, "Date");
			List<String> invoicePNDates=dao.getDistinctStringValues(sku_av, "Date");
			//List<String> skusList=dao.getDistinctStringValues(skuBom, "sku");
			Map<String,Map<String,Set<String>>> skusmap=getSkusMap();
			//System.out.println("===========   skus map  size  =================  :"+skusmap.size());
			Map<String,Map<String,Double>> skusPriceMap= getPriceMapbyType("SKU");
			//System.out.println("===========   skus price map  size  =================  :"+skusPriceMap.size());
			Map<String,Map<String,Double>> avsPriceMap= getPriceMapbyType("AV");
			//System.out.println("===========   AVSs price map  size  =================  :"+avsPriceMap.size());
			Map<String,Map<String,Double>> partsPriceMap= getPriceMapbyType("PN");
			//System.out.println("===========   parts price map  size  =================  :"+partsPriceMap.size());
			/*		
			invoiceDates.removeAll(invoicePNDates);
			for(String invoiceDate:invoiceDates){
				System.out.println("after remove  :"+invoiceDate);
			}	*/
			
			int count=0;
			BomUtillFinal bomUtill=new BomUtillFinal(dao.getMasterBomData());
			loop1:for(String invoiceDate:invoiceDates){
				for(String pnDate:invoicePNDates){
					if(invoiceDate.equals(pnDate)){
						count++;
						continue loop1;
					}
				}						
				List<InvoicesBaseData> invoiceData=dao.getInvoiceDateforparticularDate(invoiceDate);
				//System.out.println("invoice data size  :"+invoiceData.size()+"  :  date   :"+invoiceDate);
				Map<String,List<InvPNConversion>> outputMap=new HashMap<String,List<InvPNConversion>>();
				List<InvPNConversion> notMatchedList=new ArrayList<InvPNConversion>();				
				for(InvoicesBaseData invoice:invoiceData){
					String invoiceSku=invoice.getSku();							
					if(skusmap.containsKey(invoiceSku)){
						String skuandPO=invoiceSku+invoice.getPo();	
						if(!outputMap.containsKey(skuandPO)){						
							List<InvPNConversion> outputList =new ArrayList<InvPNConversion>();
							Map<String,Set<String>> skuDates=skusmap.get(invoiceSku);
							DOIDateUtill doiDateUtil=new DOIDateUtill();							
							Set<String> dates=skuDates.keySet();
					        List<String> datesList=new ArrayList<String>(dates);
							Collections.sort(datesList,new SkuDatesDescendingComp());
							String skuAtDate=doiDateUtil.getNearestSKUMatchedDate("MM-dd-yyyy", invoiceDate, datesList);					
						Set<String> avs=skuDates.get(skuAtDate);
						
						double sku_Price=0;
						if(skusPriceMap.containsKey(invoiceSku)){
							sku_Price=getPricebyPart(skusPriceMap,invoiceSku,invoiceDate);
						}						
						for(String av:avs){							
							double av_Price=0;
							if(avsPriceMap.containsKey(av)){
								av_Price=getPricebyPart(avsPriceMap,av,invoiceDate);
							}
							String replaceinvoiceDate=invoiceDate.replaceAll("['/','_','.', ]","-");
							List<MasterBomPojo> partsperAV=bomUtill.getPrimaryData(av, replaceinvoiceDate);
							for(MasterBomPojo partpojo:partsperAV){
								String partID=partpojo.getPartNumber();
								double part_Price=0;																
									if(partsPriceMap.containsKey(partID)){
										part_Price=getPricebyPart(partsPriceMap,partID,invoiceDate);	
								}													
								outputList.add(processNotContainedSKUinOutPutMap(sku_Price,av_Price,part_Price,partpojo,invoice));
							}//parts loop
						}//avs loop
						outputMap.put(skuandPO,outputList);
						}// outputmap check 
						else{
							processContainedSKUinOutPutMap(skuandPO, invoice,outputMap);
						}
						
					}//sku checking
					else{
						notMatchedList.add(notMatchedSkus(invoice));
					}
					
				}//invoicedata loop	
				if(!notMatchedList.isEmpty()){
					dao.savePNlevalInvoiceData(notMatchedList);	
					//System.out.println("Not matche skus  count    :"+notMatchedList.size());
				}
				if(!outputMap.isEmpty()){
					Map<String,InvPNConversionFinal> invoice_PNMap=new HashMap<String,InvPNConversionFinal>();
					for (Map.Entry<String,List<InvPNConversion>> entry : outputMap
							.entrySet()) {					
						processInvoice_PN2(invoice_PNMap,entry.getValue());					
						dao.savePNlevalInvoiceData(entry.getValue());
					
					}				
					if(!invoice_PNMap.isEmpty()){	
						List<InvPNConversionFinal> ouputPNList=new ArrayList<InvPNConversionFinal>();
						for (Map.Entry<String,InvPNConversionFinal> entryPn : invoice_PNMap
								.entrySet()) {
							ouputPNList.add(entryPn.getValue());						
						}
						dao.savePNlevalInvoiceData2(ouputPNList,inv_PN);						
					}
					//System.out.println("===============================================================================");
					//System.out.println("total PO orders  :"+outputMap.size()+" :     for  invoic Date  :"+invoiceDate);				
					//System.out.println("===============================================================================");
					count++;
				}//map empty check
				//System.out.println("===============================================================================");
				//System.out.println(" invoice data completed count  :"+count+" :    total count    :"+invoiceDates.size());
				//System.out.println("===============================================================================");
			}
			Mail.sendMail("Invoices updated sucessully "+new Date());
			LOG.info("===============================    INVOICES PN CONVERSION COMPLETED    ============================="+new Date());
			}catch (Exception e) {
				e.printStackTrace();
				Mail.sendMail("Error while Executing IORedistrubution Daily JOB (Invoices) InvoiceService"+e.getMessage()+new Date());
			
			LOG.info("Error while Executing IORedistrubution Daily JOB (Invoices) InvoiceService"+e.getMessage()+new Date());
			}
			
		}
		
public Map<String,Map<String,Set<String>>> getSkusMap(){
	final String skuBom="MASTERSKUAVBOM";
	Map<String,Map<String,Set<String>>> skuDatewiseAvs=new HashMap<String,Map<String,Set<String>>>();
	List<String> skuList=dao.getDistinctStringValues(skuBom, "sku");
	int count=0;
	for(String sku:skuList){
		Map<String,Set<String>> skudata=new HashMap<String,Set<String>>();
		List<String> datespersku=dao.getConditionalDistinctValues(skuBom, "date", "sku",sku);
		List<SKUBOM> skudataList=dao.getSkuData(sku);
		for(String date:datespersku){
			Set<String> avs=new HashSet<String>();
			for(SKUBOM skubom:skudataList){
				if((sku.equals(skubom.getSku()) && (date.equals(skubom.getDate())))){
					avs.add(skubom.getAv());					
				}				
			}
			skudata.put(date, avs);
		}
		skuDatewiseAvs.put(sku, skudata);
		count++;

		//System.out.println("=========================================================");
		//System.out.println("completed skus   :"+count+" :out of    :"+skuList.size());
		//System.out.println("=========================================================");
		
	}	
	return skuDatewiseAvs;
}
public Map<String,Map<String,Double>> getPriceMapbyType(String type){
	System.out.println("price processing method");
	final String collectionName="Combined_PriceMaster_Corrected";		
	Map<String,Map<String,Double>> priceMap=new HashMap<String,Map<String,Double>>();
	List<String> parts=dao.getConditionalDistinctValues(collectionName,"Item", "Type",type);
	List<PriceMaster> partsPriceList=dao.getPriceDataList(type);
	int count=0;
	for(String part:parts){
		Map<String,Double> partPrices=new HashMap<String,Double>();
		for(PriceMaster price:partsPriceList){
			if(part.equals(price.getItem())){
				partPrices.put(price.getDate(), price.getDollarPrice());
			}				
		}
		priceMap.put(part,partPrices);
		count++;
		//System.out.println("==============================================================");
		//System.out.println("price process completed parts  :"+count+" :  out of  :"+parts.size());
		//System.out.println("==============================================================");
	}
	return priceMap;
}

public double getPricebyPart(Map<String,Map<String,Double>> priceMap,String part,String date){
	if(priceMap.containsKey(part)){
		DOIDateUtill doiDateUtil=new DOIDateUtill();
		Map<String,Double> priceAtDate=priceMap.get(part);
		Set<String> dates=priceAtDate.keySet();
        List<String> datesList=new ArrayList<String>(dates);
		Collections.sort(datesList,new SkuDatesDescendingComp());
		String priceDate=doiDateUtil.getNearestSKUMatchedDate("MM-dd-yyyy", date, datesList);					
		return priceAtDate.get(priceDate);
	}else{
		return 0;
	}
	
}
public void processInvoice_PN2(Map<String,InvPNConversionFinal> invoice_PNMap,List<InvPNConversion> pnList){  
	  if(!pnList.isEmpty()){		  
	   for(InvPNConversion pn:pnList){
	    String part=pn.getPartId();
	    if(invoice_PNMap.containsKey(part)){
	     InvPNConversionFinal containPN=invoice_PNMap.get(part);
	     double partQty=containPN.getPartQty();
	     containPN.setPartQty(partQty+pn.getPartQty());     
	    }
	    else{
	     InvPNConversionFinal uniquePN=new InvPNConversionFinal();
	     uniquePN.setFileDate(pn.getFileDate());
	     uniquePN.setPoDate(pn.getPoDate());
	     uniquePN.setPo(pn.getPo());
	     uniquePN.setAdjustedDate("");
	     uniquePN.setPartId(pn.getPartId());
	     uniquePN.setPartDescription(pn.getPartDescription());
	     uniquePN.setPartQty(pn.getPartQty());
	     uniquePN.setPart_Price(pn.getPart_Price());
	     invoice_PNMap.put(part,uniquePN);
	    }
	    
	   }
	  }
}
}
