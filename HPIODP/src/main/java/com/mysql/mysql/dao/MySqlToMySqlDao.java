package com.mysql.mysql.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.io.utill.Mail;

@Repository
public class MySqlToMySqlDao {
	
	@Resource(name="hibernate3AnnotatedSessionFactory")
	private SessionFactory sessionFactory;
	@Resource(name="hibernate3AnnotatedSessionFactoryProduction")
	private SessionFactory sessionFactoryProduction;
	private static Logger LOG=Logger.getLogger(MySqlToMySqlDao.class);
	
	//get latest date from mysql production
	public String getMaxDate(String entityName, String sqlFieldName,String id) {
		String sql="select distinct "+sqlFieldName+" from "+entityName+" WHERE "+id+"=(SELECT MAX("+id+") FROM "+entityName+")";
		
		//String hql="select max("+sqlFieldName +") from "+entityName;
		  Session session = sessionFactoryProduction.openSession();
	      Transaction transaction = null;
	      String str = null;
	     
		  transaction=session.beginTransaction();
		  org.hibernate.Query query = session.createQuery(sql);
		  List listResult = query.list();
		   str=(String) listResult.get(0);
		  transaction.commit();
		  session.close();
		 
		 /* try{  }catch(HibernateException e){
	    	  if(transaction!=null) transaction.rollback();
	    	  e.printStackTrace();
	      }finally{
		  session.close();
	      }*/
		  
		return str;
	}
	
   //get data from mysql staging	
   public List<?> getDataFromMySql(String date,String entityName, String sqlFieldName,String id){
	  
	   //String sql="SELECT max("+id+") FROM "+entityName+" where "+sqlFieldName+" = "+date;
	   
	   String hql = "from "+ entityName +" where "+id+ ">( SELECT max("+id+") FROM "+entityName+" where "+sqlFieldName+" = "+"'"+date+"')";
	   
	   Session session = sessionFactory.openSession();
	   Transaction transaction = null;
	   List<?> list = null;
	     
	    	   transaction = session.beginTransaction();
	           Query query = session.createQuery(hql);
	           list = query.list();
	           transaction.commit();
	           session.close();
	           
	           /*try{ }catch(HibernateException e){
	    	  if(transaction!=null) transaction.rollback();
	    	  e.printStackTrace();
	      }finally{
		  session.close();
	      }*/
	           
	  return list;
	   
   }
   
   //save data to mysql production
   public String saveDataToMysql(final List<?> list, String entityName){
		String status="Failure";
	      Session session = sessionFactoryProduction.openSession();
	      Transaction transaction = null;
	      int count=0;
	      try{
	    	  transaction = session.beginTransaction();
	         for ( int i=0; i<list.size(); i++ ) {
	        	 session.save(list.get(i));
	          	if( i % 50 == 0 ) {
	                session.flush();
	                session.clear();
	             }
	          	count++;
	          }
	    	 transaction.commit();
	    	 LOG.info("-----Size of data inserted into mySql production "+entityName+"--------------->"+count);
	    	 Mail.sendMail(entityName+" Replication from MySql to MySql completed Successfully"+new Date());
	    	 LOG.info(entityName+" Replication from MySql to MySql completed Successfully"+new Date());
	    	 LOG.info("---------------------------no of records inserted into production table "+entityName+" are "+list.size());
	    	 status="Success";
	      }catch (HibernateException e) {
	         if (transaction!=null) transaction.rollback();
	         e.printStackTrace(); 
	         Mail.sendMail("Error while Executing "+entityName+" Replication from MySql to MySql )"+e.getMessage()+new Date());
	         LOG.info("!!!! Error while Executing "+entityName+" Replication from MySql to MySql"+e.getMessage());
	         status=e.getMessage();
	      }finally {
	         session.close(); 
	      }
		return status;
		
	}
}
