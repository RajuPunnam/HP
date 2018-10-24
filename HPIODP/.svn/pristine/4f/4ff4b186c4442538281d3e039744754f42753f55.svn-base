package com.io.common;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.inventory.service.DOIServicefinal;
import com.io.services.forecast.ForeCastService;
import com.io.services.po.InvoiceService;
import com.io.services.po.POService;
import com.io.utill.Mail;
import com.mongo.mysql.pojos.Consumption_tbl;
import com.mongo.mysql.pojos.DOI;
import com.mongo.mysql.pojos.DOI_IO;
import com.mongo.mysql.pojos.InvPNConversion;
import com.mongo.mysql.pojos.MASTERAV_PN_BOM_V11;
import com.mongo.mysql.pojos.MASTERSKUAVBOM;
import com.mongo.mysql.pojos.MASTERSKUAVBOM_PA;
import com.mongo.mysql.pojos.Master_Bom_Flex_Mongo;
import com.mongo.mysql.pojos.OpenOrdersRaw;
import com.mongo.mysql.pojos.OpenOrders_SkuAvPn;
import com.mongo.mysql.pojos.Openorders_Org_Po_Shortage;
import com.mongo.mysql.pojos.PA_FLEX_SUPER_BOM;
import com.mongo.mysql.pojos.PRI_ALT_PN_LIST;
import com.mongo.mysql.pojos.ShipmentsToHpPojo;
import com.mongo.mysql.pojos.SkuToAvToPnPojo;
import com.mongo.mysql.service.DataTransferService;
import com.mongo.mysql.service.ExceltoPojoService;
import com.mongo.mysql.service.FTPService;
import com.mysql.mysql.service.MySqlToMySqlService;

@Service
@Path("/inventoryOptimization")
public class Resources{
	
	private static Logger LOG=Logger.getLogger(Resources.class);
	
	@Autowired
	private DataTransferService dataTransferService;
	
	@Autowired
	private MySqlToMySqlService mySqlToMySqlService;
	 
	@Autowired
	private FTPService fTPService;
	
	@Context
	HttpServletRequest request;
	@Context
	ApplicationContext appContext;
	
	
	@Autowired
	private ForeCastService foreCastService;
	@Autowired
	private POService poService;
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private DOIServicefinal dOIServicefinal;
	@Autowired
	private MongoTemplate mongo;
	
	@Autowired
	private ExceltoPojoService exceltoPojoService;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/iodp")
	public Response ioDataProcess(){
		try {
			LOG.info("---------------------IODP webservice call started at----------------------->"+new Date());
			Map<String,String> buMap=new HashMap<String, String>();
			   buMap.put("5X", "WKS");
			   buMap.put("4T", "CPC");
			   buMap.put("8J", "BNB");
			   buMap.put("TB", "BNB");
			   buMap.put("AN", "BNB");
			   buMap.put("%W%", "WKS");
			   buMap.put("6J", "CPC");
			   buMap.put("KV", "CNB");
			   buMap.put("5U", "BPC");
			   buMap.put("AIO", "BPC");
			   buMap.put("TE CHAMEI", "BNB");
			   buMap.put("6U", "BNB");
			   buMap.put("TA", "BNB");
			   buMap.put("7F", "BPC");
			Map<String,String> map=new LinkedHashMap<String, String>();
			map.put("DOI_IO",dOIServicefinal.processDOI());
			map.put("OPEN_ORDERS",poService.processPO(buMap));
			map.put("INVOICE_AV_PN", invoiceService.processInvoice());
			 
			//mongo.getDb().getMongo().close();
			/*XmlWebApplicationContext context = ((XmlWebApplicationContext) ContextLoader.getCurrentWebApplicationContext());
			context.refresh();*/
			
		    HttpSession session=request.getSession();
			session.setAttribute("iODPStatus", map);
			
			 
			 LOG.info("---------------------IODP webservice call completd at----------------------->"+new Date());
	} catch (Exception e) {
		e.printStackTrace();
	//	Mail.sendMail("Error while Executing IORedistrubution Daily JOB (DOI,Invoces PO Shortages and PO Demand)"+e.getMessage()+new Date());
		return  Response.ok("Error while Executing PO Redistrubution Daily JOB (DOI,Invoces PO Shortages and PO Demand").build();
	}
		
	//Mail.sendMail("Started Testing DOI,Invoces PO Shortages and PO Demand "+new Date());
	java.net.URI location = null;
    try {
		location = new java.net.URI("http://localhost:8080/HPIODP/iodp.jsp");
	} catch (URISyntaxException e) {
		e.printStackTrace();
	}  
   return  Response.temporaryRedirect(location).build();
	//return  Response.ok("Started Testing DOI,Invoces PO Shortages and PO Demand ").build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/generateAV_part_Final_Level")
	public Response generateAV_part_Final_Level(){
		try {
			LOG.info("---------------------generateAV_part_Final_Level webservice call started at----------------------->"+new Date());
   		    foreCastService.generateFinalQty();	
		   
			 LOG.info("---------------------generateAV_part_Final_Level webservice call completd at----------------------->"+new Date());
	} catch (Exception e) {
		e.printStackTrace();
	}
	return  Response.ok(" generateAV_part_Final_Level table executed  ").build();
	}
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/forecastiodp")
	public Response ioDataProcess_Forecast(){
		try {
			LOG.info("---------------------IODP webservice call started at----------------------->"+new Date());
			Map<String,String> map=new LinkedHashMap<String, String>();
			foreCastService.generateFinalQty();
			map.put("FCST",foreCastService.readFile("E:\\Forecast_new"));	
		    HttpSession session=request.getSession();
			session.setAttribute("iODPStatus", map);
			
			 
			 LOG.info("---------------------IODP webservice call completd at----------------------->"+new Date());
	} catch (Exception e) {
		e.printStackTrace();
		//Mail.sendMail("Error while Executing IORedistrubution Weekly Forecast JOB "+e.getMessage()+new Date());
		return  Response.ok("Error while Executing PO Redistrubution Weekly Forecast JOB ").build();
	}
		
	///Mail.sendMail("Completd running Weekly Forecast JOB "+new Date());
	java.net.URI location = null;
    try {
		location = new java.net.URI("http://localhost:8080/HPIODP/iodp.jsp");
	} catch (URISyntaxException e) {
		e.printStackTrace();
	}  
   return  Response.temporaryRedirect(location).build();
	//return  Response.ok("Started Testing DOI,Invoces PO Shortages and PO Demand ").build();
	}
	
	
	
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/mongoTomysql")
	public Response MongoMySqlProcess(){
		try {
			
			LOG.info("---------------------MongotoMySql webservice call started at----------------------->"+new Date());
			
			Map<String,String> map=new LinkedHashMap<String, String>();
			
			/**
			 * Need to pass String entityName(pojo), String sqlFieldName(pojo field),
			 * String collectionName, String mongoFieldName, Class<?> clazz,String id
			 */
			
			map.put("OPEN_ORDERS",dataTransferService.MigrateData("OpenOrdersRaw","File_Date","OPEN_ORDERS","File Date",OpenOrdersRaw.class,"id1"));
		    map.put("PO_ORDERS_SKU_AV_PN",dataTransferService.MigrateData("OpenOrders_SkuAvPn","fileDate","PO_ORDERS_SKU_AV_PN","File Date",OpenOrders_SkuAvPn.class,"id1"));
			map.put("ORIG_PO_SHORTAGE",dataTransferService.MigrateData("Openorders_Org_Po_Shortage","fileDate","ORIG_PO_SHORTAGE","File Date",Openorders_Org_Po_Shortage.class,"id1"));
			map.put("INVOICES",dataTransferService.MigrateData("ShipmentsToHpPojo","fileDate","INVOICES","File Date",ShipmentsToHpPojo.class,"id1"));
			map.put("INVOICE_AV_PN",dataTransferService.MigrateData("InvPNConversion","fileDate","INVOICE_AV_PN","File Date",InvPNConversion.class,"id1"));
			map.put("DOI_IO",dataTransferService.MigrateData("DOI_IO","date","DOI_IO","Date",DOI_IO.class,"id1"));
			map.put("DOI",dataTransferService.MigrateData("DOI","date","DOI","Date",DOI.class,"id1"));
			map.put("PPB_PC",dataTransferService.MigrateData("Consumption_tbl", "date", "PPB_PC", "DataTrans",Consumption_tbl.class,"id1"));
			
			map.put("FORECAST_COMBINED_DATA_NEW_W0To26",dataTransferService.MigrateData("SkuToAvToPnPojo","FGD","FORECAST_COMBINED_DATA_NEW_W0To26","FcstGD",SkuToAvToPnPojo.class,"id1"));
			
			
		   HttpSession session=request.getSession();
		   session.setAttribute("migrationStatus", map);
		
			LOG.info("---------------------MongotoMySql webservice call completd at----------------------->"+new Date());
		} catch (Exception e) {
			e.printStackTrace();
		//	Mail.sendMail("Error while Executing Migration from Mongo to MySql )"+e.getMessage()+new Date());
			return  Response.ok("Error while Executing Migration from Mongo to MySql ").build();
		}
		
		//Mail.sendMail("Started Migration from Mongo to MySql "+new Date());
		java.net.URI location = null;
	       try {
			location = new java.net.URI("http://localhost:8080/HPIODP/migration.jsp");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}  
	    return  Response.temporaryRedirect(location).build();
	    
		//return  Response.ok("Started Migration from Mongo to MySql  ").build();
	}
	
    @GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/mysqlReplication")
	public Response MySqlToMySql_Replication(){
		try {
			LOG.info("---------------------Replication from MySql to MySql webservice call started at----------------------->"+new Date());
			
			Map<String,String> map=new LinkedHashMap<String, String>();
			
			       /**
			        * Need to pass String entityName(pojo), String sqlFieldName(pojo field),String id
			        */
			
			       map.put("OpenOrdersRaw",mySqlToMySqlService.ReplicateData("OpenOrdersRaw","File_Date","id1"));
			       map.put("OpenOrders_SkuAvPn",mySqlToMySqlService.ReplicateData("OpenOrders_SkuAvPn","fileDate","id1"));
			       map.put("Openorders_Org_Po_Shortage",mySqlToMySqlService.ReplicateData("Openorders_Org_Po_Shortage","fileDate","id1"));
			       map.put("InvPNConversion",mySqlToMySqlService.ReplicateData("InvPNConversion","fileDate","id1"));
			       map.put("ShipmentsToHpPojo",mySqlToMySqlService.ReplicateData("ShipmentsToHpPojo","fileDate","id1"));
			       map.put("DOI",mySqlToMySqlService.ReplicateData("DOI","date","id1"));
			       map.put("DOI_IO",mySqlToMySqlService.ReplicateData("DOI_IO","date","id1"));
			       map.put("Consumption_tbl",mySqlToMySqlService.ReplicateData("Consumption_tbl_MySql", "date", "id1"));
			    
			       HttpSession session=request.getSession();
			       session.setAttribute("tableStatus", map);
			LOG.info("---------------------Replication from MySql to MySql webservice call completd at----------------------->"+new Date());
		} catch (Exception e) {
			e.printStackTrace();
		//	Mail.sendMail("Error while Executing Replication from MySql to MySql )"+e.getMessage()+new Date());
			return  Response.ok("Error while Executing Replication from MySql to MySql ").build();
		}
		  //  Mail.sendMail("Started Replication from MySql to MySql "+new Date());
		    java.net.URI location = null;
		       try {
				location = new java.net.URI("http://localhost:8080/HPIODP/replication.jsp");
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}  
		return  Response.temporaryRedirect(location).build();
		//return  Response.ok("Started Replication from MySql to MySql  ").build();
	}

    
    @GET
   	@Produces(MediaType.TEXT_PLAIN)
  @Path("/moveCollectionToMySql")
   	public Response FcstMySql_Migration(){
    	try {
   	   LOG.info("---------------------Executing moveCollectionToMySql webservice call started at----------------------->"+new Date());

      /* dataTransferService.moveData("PO_ORDERS_SKU_AV_PN",OpenOrders_SkuAvPn.class);
       dataTransferService.moveData("INVOICE_AV_PN",InvPNConversion.class);
       dataTransferService.moveData("ORIG_PO_SHORTAGE",Openorders_Org_Po_Shortage.class);
       dataTransferService.moveData("FORECAST_COMBINED_DATA_NEW_W0To26",SkuToAvToPnPojo.class);*/
      
 //      Mail.sendMail("completed moveCollectionToMySql "+new Date());
       return  Response.ok("completed moveCollectionToMySql "+new Date()).build();
       } catch (Exception e) {
        e.printStackTrace();
   //  Mail.sendMail("Error while Executing moveCollectionToMySql  "+e.getMessage()+new Date());
     return  Response.ok("Error while Executing moveCollectionToMySql ").build();
   }
    }
    
    
    @GET
   	@Produces(MediaType.TEXT_PLAIN)
   	@Path("/weekly_BomToMySql")
   	public Response Weekly_BOMMySql_Migration(){
    	try {
			LOG.info("---------------------BOMMySql_Migration webservice call started at----------------------->"+new Date());
			Map<String,String> map=new LinkedHashMap<String, String>();
			
			/**
    	     *need to pass String entityName(pojo), String collectionName, Class<?> clazz
    	     */
			map.put("MASTER_BOM_FLEX",dataTransferService.migrateBomData("MASTER_BOM_FLEX", "MASTER_BOM_FLEX",Master_Bom_Flex_Mongo.class));
			map.put("MASTERSKUAVBOM_PA",dataTransferService.migrateBomData("MASTERSKUAVBOM_PA", "MASTERSKUAVBOM_PA", MASTERSKUAVBOM_PA.class));		
    	   
			HttpSession session=request.getSession();
 		    session.setAttribute("bomMigrationStatus", map);
 		  //  Mail.sendMail(" Executing BOMMySql_Migration completed Successfully )"+new Date());
 		    LOG.info("---------------------BOMMySql_Migration webservice call completd at----------------------->"+new Date());
    	} catch (Exception e) {
			 LOG.info(e.getMessage()); 
		//	 Mail.sendMail("Error while Executing BOMMySql_Migration )"+e.getMessage()+new Date());
			 return  Response.ok("Error while Executing BOMMySql_Migration ").build();
		}
    	
    	java.net.URI location = null;
	       try {
			location = new java.net.URI("http://localhost:8080/HPIODP/bommigration.jsp");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}  
     	return  Response.temporaryRedirect(location).build();
     	//return  Response.ok("Tested BOMMySql_Migration  ").build();
    }
    
    
    @GET
   	@Produces(MediaType.TEXT_PLAIN)
   	@Path("/monthly_BomToMySql")
   	public Response Monthly_BOMMySql_Migration(){
    	try {
			LOG.info("---------------------BOMMySql_Migration webservice call started at----------------------->"+new Date());
			Map<String,String> map=new LinkedHashMap<String, String>();
			
			/**
    	     *need to pass String entityName(pojo), String collectionName, Class<?> clazz
    	     */
			map.put("MASTERSKUAVBOM",dataTransferService.migrateBomData("MASTERSKUAVBOM", "MASTERSKUAVBOM", MASTERSKUAVBOM.class));
			map.put("MASTERAV_PN_BOM_V11",dataTransferService.migrateBomData("MASTERAV_PN_BOM_V11", "MASTERAV_PN_BOM_V11", MASTERAV_PN_BOM_V11.class));
    	
    	    HttpSession session=request.getSession();
 		    session.setAttribute("bomMigrationStatus", map);
 		  //  Mail.sendMail(" Executing BOMMySql_Migration completed Successfully )"+new Date());
 		    LOG.info("---------------------BOMMySql_Migration webservice call completd at----------------------->"+new Date());
    	} catch (Exception e) {
			 LOG.info(e.getMessage()); 
			// Mail.sendMail("Error while Executing BOMMySql_Migration )"+e.getMessage()+new Date());
			 return  Response.ok("Error while Executing BOMMySql_Migration ").build();
		}
    	
    	java.net.URI location = null;
	       try {
			location = new java.net.URI("http://localhost:8080/HPIODP/bommigration.jsp");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}  
     	return  Response.temporaryRedirect(location).build();
     	//return  Response.ok("Tested BOMMySql_Migration  ").build();
    }
    
    
    @GET
   	@Produces(MediaType.TEXT_PLAIN)
   	@Path("/bhuvanesh_BomToMySql")
   	public Response Weekly_Bhuvanesh_BOMMySql_Migration(){
    	try {
			LOG.info("---------------------BOMMySql_Migration webservice call started at----------------------->"+new Date());
			Map<String,String> map=new LinkedHashMap<String, String>();
			
			/**
    	     *need to pass String entityName(pojo), String collectionName, Class<?> clazz
    	     */
        	map.put("PRI_ALT_PN_LIST", dataTransferService.migrateBomData("PRI_ALT_PN_LIST","PRI_ALT_PN_LIST",PRI_ALT_PN_LIST.class));
    	    map.put("PA_FLEX_SUPER_BOM", dataTransferService.migrateBomData("PA_FLEX_SUPER_BOM", "PA_FLEX_SUPER_BOM", PA_FLEX_SUPER_BOM.class)) ;  
			
    	    HttpSession session=request.getSession();
 		    session.setAttribute("bomMigrationStatus", map);
 		//    Mail.sendMail(" Executing BOMMySql_Migration completed Successfully )"+new Date());
 		    LOG.info("---------------------BOMMySql_Migration webservice call completd at----------------------->"+new Date());
    	} catch (Exception e) {
			 LOG.info(e.getMessage()); 
			// Mail.sendMail("Error while Executing BOMMySql_Migration )"+e.getMessage()+new Date());
			 return  Response.ok("Error while Executing BOMMySql_Migration ").build();
		}
    	
    	java.net.URI location = null;
	       try {
			location = new java.net.URI("http://localhost:8080/HPIODP/bommigration.jsp");
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}  
     	return  Response.temporaryRedirect(location).build();
     	//return  Response.ok("Tested BOMMySql_Migration  ").build();
    }
    
    @GET
   	@Produces(MediaType.TEXT_PLAIN)
   	@Path("/runExcelCode")
   	public Response ExcelProcess(){
    	try{
    		 String shortage[]={
   	    		  "part",	"description",	"buyerName",	"leadTimeFixed",
   	    		  "priSupplier_Id",	"priSupplier_Name",	"net_QOH",	"nonNet_Qoh",
   	    		  "vmi_Qoh ","sheduleRecQty",	"sku",	"av",	"pl",	"boxPCBA",
   	    		  "totalDemand",	"bal_PO",	"line_Down",	"jit",	"comments",
   	    		  "shortage_Status",	"projDate_Past",	"proj_Date_1",	"proj_Date_2",
   	    		  "proj_Date_3",	"proj_Date_4",	"proj_Date_5",	"proj_Date_6",	"proj_Date_7",
   	    		  "proj_Date_8",	"proj_Date_9",	"proj_Date_10",	"proj_Date_11",	"proj_Date_12",
   	    		  "proj_Date_13",	"proj_Date_14",	"proj_Date_15",	"proj_Date_16",	"proj_Date_17",
   	    		  "proj_Date_18",	"proj_Date_19",	"proj_Date_20",	"proj_Date_21",	"proj_Date_22",
   	    		  "proj_Date_23",	"proj_Date_24",	"proj_Date_25",	"proj_Date_26",	"proj_Date_27",
   	    		  "proj_Date_28",	"proj_Date_29",	"proj_Date_30",	"proj_Date_31",	"proj_Date_32",
   	    		  "proj_Date_33",	"proj_Date_34",	"proj_Date_35",	"proj_Date_36",	"proj_Date_37",
   	    		  "proj_Date_38",	"proj_Date_39",	"proj_Date_40",	"proj_Date_41",	"proj_Date_42",
   	    		  "proj_Date_43",	"proj_Date_44",	"proj_Date_45",	"proj_Date_46",	"proj_Date_47",
   	    		  "proj_Date_48",	"proj_Date_49",	"proj_Date_50",	"proj_Date_51",	"proj_Date_52",
   	    		  "proj_Date_53",	"proj_Date_54",	"proj_Date_55",	"proj_Date_Future",	"seq",	"count",	"occurence"
   	      };
   		 
   		 String headers[]={
   				 "Part","Description","Buyer Name","Lead TimeFixed","PrimarySupplierID","Name","NettableQOH",
   				 "NonNettable QOH","VMIQOH","Scheduled Receipt Quantity","SKU","AV","PL","BOX/PCBA","Total Demand","Balance PO",
   				 "Line Down","JIT","Comments","Shortage_Status","Projected DatePast","Projected Date1","Projected Date2",
   				 "Projected Date3","Projected Date4","Projected Date5","Projected Date6","Projected Date7",
   				 "Projected Date8","Projected Date9","Projected Date10","Projected Date11","Projected Date12",
   				 "Projected Date13","Projected Date14","Projected Date15","Projected Date16","Projected Date17",
   				 "Projected Date18","Projected Date19","Projected Date20","Projected Date21","Projected Date22",
   				 "Projected Date23","Projected Date24","Projected Date25","Projected Date26","Projected Date27",
   				 "Projected Date28","Projected Date29","Projected Date30","Projected Date31","Projected Date32",
   				 "Projected Date33","Projected Date34","Projected Date35","Projected Date36","Projected Date37",
   				 "Projected Date38","Projected Date39","Projected Date40","Projected Date41","Projected Date42",
   				 "Projected Date43","Projected Date44","Projected Date45","Projected Date46","Projected Date47",
   				 "Projected Date48","Projected Date49","Projected Date50","Projected Date51","Projected Date52",
   				 "Projected Date53","Projected Date54","Projected Date55","Future","Seq#","Count","Occurrence"};
   		
   		 Map<String,String> shortageMap=new LinkedHashMap<String, String>();
   		 for(int k=0;k<shortage.length;k++){
   			 shortageMap.put(headers[k], shortage[k]);
   		 }
   		 
   		 String key[]={"Date","ITEM","DESCRIPTION","MAKE_BUY","ABC_CODE",
    				"BUYER","CST_STD","NETTABLE_INVENTORY","TOTAL USD",
    				"PLANNER","MINIMUM_ORDER_QUANTITY","FIXED_LOT_MULTIPLIER",
    				"DEMANDA_3M","DEMANDA_6M","DEMANDA_12M","TOTAL_DEMAND","ONORDER_3M","ONORDER_6M",
    				"ONORDER_12M","EXCESSO_3M","EXCESSO_6M","EXCESSO_12M","EXCESSO_ONORDER_3M",
    				"EXCESSO_ONORDER_6M","EXCESSO_ONORDER_12M","TOTAL_OH (712, 713, 715)",
    				"IN TRANSIT QTY","IN TRANSIT USD","OPEN PO QTY","OPEN PO USD","Supplier Code",
    				"Supplier Name","Commodities","L06 / L10","E&O UNDER","E&O FLEX","E&O HP","0 day to 7 days",">8 days <14 days",">15 days <21 days",
    				">22 days <30 days","> 31 days < 60 days","> 61 days < 90 days","> 91 days < 120 days",
    				"> 121 days < 150 days","> 151 days","> 1 year","DEMANDA_30_DAYS"};
    		
    		String value[]={"fileDate","item","description","makeBuy","abc_Code","buyer","cst_Std",
    				"net_inventory","usdNet_Inventory","palnner","minOrderQty","fixedLot_Multiplier",
    				"demand3M","demand6M","demand12M","totalDemand","onOrder3M","onOrder6M","onOrder12M","excess3M",
    				"excess6M","excess12M","excessOnOrder3M","excessOnOrder6M","excessOnOrder12M","total_Oh",
    				"inTransitQty","inTransitUSD","openPOQty","openPoUSD","supplierCode","supplier",
    				"commodities","zeroSix_Ten","eo_Under","eo_Flex","eo_Hp",
    				"zero_Sevendays","eight_FourTeenDays","fifteen_TwentyOneDays","twentyTwo_ThirtyDays",
    				"ThirtyOne_SixtyDays","sixtyOne_NintyDays","nintyOne_OneTwentyDays",
    				"oneTwnetyOne_OneFifty","above150Dayes","oneYear","demand30Days"};
    		
    		
    		Map<String,String> resumeMap=new LinkedHashMap<String, String>();
    		for(int i=0;i<key.length;i++){
    			resumeMap.put(key[i].trim(), value[i].trim());
    		}
     		String path=fTPService.getDownloadFtpFiles(0);
     		
    		final File folder = new File(path);
    	    listFilesForFolder(folder,resumeMap,shortageMap);
    
    		LOG.info("---------completd runExcelCode "+new Date());
    	    return  Response.ok("completed  runExcelCode  "+new Date()).build();
    	} catch (Exception e) {
    		 e.printStackTrace();
			 LOG.info(e.getMessage()); 
			// Mail.sendMail("Error while Executing runExcelCode )"+e.getMessage()+new Date());
			 return  Response.ok("Error while Executing runExcelCode ").build();
		}
    }
    
    public void listFilesForFolder(final File folder, Map<String, String> resumeMap, Map<String, String> shortageMap) {
    	
	    for (final File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry,resumeMap,shortageMap);
	        } else {
	            System.out.println("***********"+fileEntry.getPath());
	            if(fileEntry.getName().startsWith("DOS")){
	            String result=exceltoPojoService.processExcel(fileEntry,resumeMap);
	            LOG.info(result);
	            if(result.contains("Success")){
	            	fTPService.getMoveFilesToSucessOrFailureFolder(new File("D:\\BINOY\\Sosa"), "D:\\HP\\Data\\Sosa", fileEntry);
	            }
	            }
	            else if(fileEntry.getName().startsWith("Shortage")){
	            String result=exceltoPojoService.processExcel(fileEntry,shortageMap);	
	            LOG.info(result);
	            if(result.contains("Success")){
	            	fTPService.getMoveFilesToSucessOrFailureFolder(new File("D:\\BINOY\\HP Computing - Shortage"), "D:\\HP\\Data\\HP Computing - Shortage", fileEntry);
	            	
	            }
	            }
	        }
	        
	    }

	}
     
}