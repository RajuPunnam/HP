package com.mongo.mysql.service;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.utill.DateUtil;
import com.io.utill.Mail;
import com.mongo.mysql.dao.DataTransferDao;
import com.mongo.mysql.pojos.Consumption_tbl;
import com.mongo.mysql.pojos.SkuToAvToPnPojo;

@Service
public class DataTransferService {
	private static Logger LOG=Logger.getLogger(DataTransferService.class);
@Autowired
private DataTransferDao daoDataTransferDao;


synchronized public String MigrateData(String entityName, String sqlFieldName, String collectionName, String mongoFieldName, Class<?> clazz,String id){
	
	List<Date> originalsqlDates=null;
	Date maxSqlDate=null;
	
	List<String> mySqlDates=daoDataTransferDao.getUniqueDatesFromMySql(entityName,sqlFieldName);
	List<String> mongoDates=getMongoDates(collectionName,mongoFieldName);
	
	LOG.info(" The Distinct dates for Mongo Collection "+collectionName+" are "+mongoDates.size());
	LOG.info(" The Distinct dates for MYSql Table "+entityName+" are "+mySqlDates.size());
	
	LOG.info("Mongo Dates before"+mongoDates);
	LOG.info("MySql Dates before"+mySqlDates);
	
	if(mySqlDates.size()!=0){
		try{
	Map<String, String> map=	DateUtil.getOrig_Comm_Dates(mongoDates);
	Set<String> keys=map.keySet();
	List<String> mongo_keyDates=new ArrayList<String>(keys); 
	List<String> mySql_keyDates=DateUtil.getOrig_Comm_Date(mySqlDates);
	
	
	LOG.info("Mongo Dates After"+mongo_keyDates);
	LOG.info("Mysql Dates After"+mySql_keyDates);
	List<String> finalMongDates=new ArrayList<String>(); 
	
	if(mySqlDates.size() == mySql_keyDates.size() && mongoDates.size() == mongo_keyDates.size()){
		LOG.info("MySql Dates before :::" +mySqlDates.size()+" After :" +mySql_keyDates.size() +"Mongo Dates before  ::: " +mongoDates.size()+" After: "+ mongo_keyDates.size());
	mongo_keyDates.removeAll(mySql_keyDates);
	for(String str:mongo_keyDates){
		finalMongDates.add(map.get(str));
	}
	
	}
	LOG.info("Final mongo dates::::::"+finalMongDates);
	
	 originalsqlDates=getDates(mySqlDates);
	 maxSqlDate = getHighestDate(originalsqlDates);
	 
			if (finalMongDates.size() != 0) {
				return getDataFromMongo(finalMongDates, clazz, mongoFieldName,
						collectionName);
			} else {
				/*Mail.sendMail("---------------------no new dates found after "
						+ maxSqlDate + " in " + collectionName
						+ "  to move---------------------" + mongoDates);
				*/LOG.info("---------------------no new dates found after "
						+ maxSqlDate + " in " + collectionName
						+ "  to move---------------------" + mongoDates);
				return "No data found!";
			}
			
		}catch(Exception e){
			//   Mail.sendMail("-------Error in Migration of "+collectionName+" "+e.getMessage());
	    	  LOG.info("!!Exception in "+collectionName+" "+e.getMessage());
	    	  return e.getMessage();
			
		}
	}else{
		 int count=daoDataTransferDao.getMySqlDataCount(entityName);
		 if(count == 0) {
		 LOG.info("-------count--------"+count);
		// Mail.sendMail("NO data found in MySql table "+entityName+" so moving entire mongo collection to mysql");
		 LOG.info("NO data found in MySql table "+entityName+" so moving entire mongo collection to mysql");
		 return moveData(collectionName, clazz); 
		 }else
		 return "No dates found in "+entityName;
		
	}
}

//Old logic
synchronized public String MigrateData_Old(String entityName, String sqlFieldName, String collectionName, String mongoFieldName, Class<?> clazz,String id){
		
		//List<String> mySqlDates=daoDataTransferDao.getUniqueDatesFromMySql(entityName,sqlFieldName);
		
		LOG.info("Migration Of "+collectionName+" data ---------------------->Started"+new Date());
		 try{ 
		 Date maxSqlDate=daoDataTransferDao.getMaxDate(entityName,sqlFieldName,id);
		 LOG.info(entityName+"-----------sql max date-----------"+maxSqlDate);
		 
		List<String> mongoDates=getMongoDates(collectionName,mongoFieldName);
		LOG.info(mongoDates.size()+"\t"+collectionName+"--------mongoDates"+mongoDates);
		
		List<String> finalMongoDates=new ArrayList<String>();
		
		finalMongoDates=adjustDateFormate(mongoDates,maxSqlDate);
		
		LOG.info(finalMongoDates.size()+"\t"+collectionName+"-----------dates after maxSqlDate are--------------"+finalMongoDates);
		if(finalMongoDates.size()!=0){
		return getDataFromMongo(finalMongoDates,clazz,mongoFieldName,collectionName);
		}else{
			//Mail.sendMail("---------------------no new dates found after "+maxSqlDate+" in "+collectionName+"  to move---------------------"+finalMongoDates);
			LOG.info("---------------------no new dates found after "+maxSqlDate+" in "+collectionName+"  to move---------------------"+finalMongoDates);
		    return "No data found!";
		}
		 }catch(java.lang.IndexOutOfBoundsException e){
			 //......need to call moveData() for moving all entire collection if it is index out of bound
			 int count=daoDataTransferDao.getMySqlDataCount(entityName);
			 if(count == 0) {
			 LOG.info("-------count--------"+count);
		//	 Mail.sendMail("NO data found in MySql table "+entityName+" so moving entire mongo collection to mysql");
			 LOG.info("NO data found in MySql table "+entityName+" so moving entire mongo collection to mysql");
			 return moveData(collectionName, clazz); 
			 }else
			 return "No dates found in "+entityName;
	      }catch(Exception e){
			  //Mail.sendMail("-------Error in Migration of "+collectionName+" "+e.getMessage());
	    	  LOG.info("!!Exception in "+collectionName+" "+e.getMessage());
	    	  return e.getMessage();
	      }
		
	}
	
	public List<String> getMongoDates(String collectionName, String mongoFieldName){
		List<String> mongoDates=daoDataTransferDao.getDatesFromMongo(collectionName,mongoFieldName);
		return mongoDates;
	}
	public String getDataFromMongo(List<String> mongoDates, Class<?> clazz, String mongoFieldName, String collectionName){
		List<?> list=daoDataTransferDao.getMongoData(mongoDates,clazz,mongoFieldName);
		LOG.info("size of data taken from mongo "+collectionName+"------------------------------->"+list.size());
		return getSaveDataIntoMysql(list,collectionName);
	}
	
	public String getSaveDataIntoMysql(List<?> list, String collectionName){
		
		return daoDataTransferDao.saveDataToMysql(list,collectionName);
	}
	
	//Old logic
	public List<String> adjustDateFormate(List<String> list, Date maxSqlDate) throws Exception{
		String dateFormateArray[]={"MM/dd/yyyy","yyyy/MM/dd"};
		Set<String> set=new HashSet<String>();
	
		List<String> finalDatesList=new ArrayList<String>();
		String ndate = null;
		for(String origDate:list){
			for(String df:dateFormateArray){
			try{
			ndate=origDate.replaceAll("-|\\.", "/");
			SimpleDateFormat sf=new SimpleDateFormat(df);
			sf.setLenient(false);
			Date date1=sf.parse(ndate);
			if(date1.after(maxSqlDate)){
	            finalDatesList.add(origDate);
	        }
				if(set.contains(origDate)){
					set.remove(origDate);}
				break;
			}catch(Exception e){
				set.add(origDate);
				continue;
			}
		  }
		}//dates ending loop.................	
		if(set.size()>0){
		LOG.info(set.size()+"----------------------Final set which contains diff formates---------"+new ArrayList<String>(set));
		throw new Exception("--Different formate date came in Migration please solve that--------in---------");
		}
		return finalDatesList;
	}

	   //To move entire collection to mySql..............
	   synchronized public String moveData(String collection,Class<?> clazz) {
		try{
		String status=null;	
		long count=daoDataTransferDao.getCollectionCount(collection);
		LOG.info("No of records found in Mongo "+collection+" are "+count);
		int noOfRecords=(int)count;
		int index=noOfRecords/1000000;
		if(noOfRecords%1000000 != 0){
			index=index+1;}
		LOG.info("--------total number of records and partitions are "+count+"\t"+index);
		for(int i=0;i<index;i++){
			List<?> list=daoDataTransferDao.getCollectionData(i, clazz);
			LOG.info("--------data taken from collection "+collection+" for "+i+"th time is"+list.size()+" "+new Date());
		    daoDataTransferDao.saveCollectionToMySql(i,list, collection);
		    if(i == index) status="Success";
		}
		//Mail.sendMail(collection+" Moving to mysql Completed.... "+new Date());
		return status;
		}catch(Exception e){
			//Mail.sendMail("!!Exception came while saving data in >"+collection+" ........");
			LOG.info(e.getMessage());
			return e.getMessage();
		}
		
	}

	synchronized public String migrateBomData(String entityName, String collectionName, Class<?> clazz) {
		try{
			int count=daoDataTransferDao.truncateTable(entityName);
		LOG.info("-------------no of rows deleted from "+entityName+"-----------"+count);
		List<?> list=daoDataTransferDao.getBomData(clazz);
		LOG.info("---------no of records taken from mongo "+collectionName+" are "+list.size()+" "+new Date());
		return daoDataTransferDao.saveDataToMysql(list, collectionName);
		}catch(Exception e){
			LOG.info(e.getMessage());
			e.printStackTrace();
			return e.getMessage();
		}
		
	}
	
	public  Date getHighestDate(List<Date> sqlDates) {
		Date date1=sqlDates.get(0);
			for (int i = 1; i < sqlDates.size(); i++) {
				if(date1.after(sqlDates.get(i))){
					continue;
				}else{
					date1=sqlDates.get(i);
				}
			}
			return date1;
		}
	
	 public static List<Date> getDates(List<String> Dates){
		  List<Date> originalDates = new ArrayList<Date>();
		  Set<String> set=new HashSet<String>();
		  String dateFormateArray[]={"MM/dd/yyyy","yyyy/MM/dd"};
		  
		  for (int i = 0; i < dateFormateArray.length; i++) {
		  
		   System.out.println("required Date format::::"+dateFormateArray[i]);
		   for (String string : Dates) {
		    try{
		    
		     String dateString=string.replaceAll("-|\\.", "/");
		    SimpleDateFormat sf=new SimpleDateFormat(dateFormateArray[i]);
		    sf.setLenient(false);
		    Date date1=sf.parse(dateString);
		    originalDates.add(date1);
		     if(set.contains(string)){
		      set.remove(string);
		      }
		    }catch(Exception e){
		     set.add(string);
		     break;
		    }
		         
		   }
		   
		  if(set.size()>0){
		   continue;
		  }else{
		   break;
		  }
		  }
		  
		  return originalDates;
		 }
	
	

}
