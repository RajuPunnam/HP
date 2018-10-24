package com.mysql.mysql.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.utill.DateUtil;
import com.io.utill.Mail;
import com.mysql.mysql.dao.MySqlToMySqlDao;

@Service
public class MySqlToMySqlService {
	private static Logger LOG=Logger.getLogger(MySqlToMySqlService.class);
	@Autowired
	private MySqlToMySqlDao mySqlToMySqlDao;
	
	synchronized public String ReplicateData(String entityName, String sqlFieldName,String id) {
		try{  
		 String maxDate=mySqlToMySqlDao.getMaxDate(entityName, sqlFieldName,id);
		 LOG.info("MySql Replication process-----------sql max date in "+entityName+" is-----------"+maxDate);
		 
		 List<?> list=mySqlToMySqlDao.getDataFromMySql(maxDate, entityName, sqlFieldName,id);
		 LOG.info("MySql Replication process--------no of records found in "+entityName+" where "+sqlFieldName+" > "+maxDate+"-----are"+list.size());
		 
		 if(list.size()>0){
			String result= mySqlToMySqlDao.saveDataToMysql(list, entityName);
		 return result;
		 }else{
			   Mail.sendMail("MySql Replication process--------no data found in "+entityName+" where "+sqlFieldName+" > "+maxDate);
				LOG.info("MySql Replication process--------no data found in "+entityName+" where "+sqlFieldName+" > "+maxDate);
				return "No data found!"; 
		 }
		}catch(HibernateException e){
	    	 LOG.info(e.getMessage());
	    	  return e.getMessage();
	      }
	}
	
	//--------------method to identify two years before dates 
	public List<String> getDatesBefore(List<String> allDates,String maxDate){
		List<String> list=new ArrayList<String>();
		Date date=DateUtil.getDateForAnyFormate(maxDate);
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.YEAR,-2);
		Date twoYerasBefDate=now.getTime();
		for(String strDate:allDates){
			Date currDate=DateUtil.getDateForAnyFormate(strDate);
			if(currDate.before(twoYerasBefDate)){
				list.add(strDate);
			}
		}
		
		return list;
		
	}
	
}
