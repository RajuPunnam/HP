package com.io.dao;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.io.pojos.ForeCastPrintProcessed;
import com.io.pojos.ForeCastStatus;
import com.io.pojos.ForeCast_IPS_HW;
import com.io.pojos.ForeCast_IPS_Supplies;
import com.io.pojos.ForeCast_LES_Supplies;
import com.io.pojos.ForeCast_Les_HW;
import com.io.pojos.AvPartFinalQtyPer;
import com.io.pojos.EoCommodityPojo;
import com.mongo.mysql.dao.DataTransferDao;

@Repository
public class ForecastIps_SuppliesDao {
	@Autowired
	@Qualifier("hibernate3AnnotatedSessionFactory")
	private SessionFactory sessionFactory;
	
	@Resource(name = "hibernate3AnnotatedSessionFactorySqlServer")
	private SessionFactory sessionFactorySqlServer;
	private static Logger LOG=Logger.getLogger(ForecastIps_SuppliesDao.class);

	//method to insert
	public boolean insertForecastIPSHW(List<ForeCast_IPS_HW> list,String entityName) {

		boolean status = false;
		LOG.info(list.size());
		Session session1 = sessionFactory.openSession();
		Session session2 = sessionFactorySqlServer.openSession();
		Transaction transaction1 = null;
		Transaction transaction2 = null;
		try {
			transaction1 = session1.beginTransaction();
			transaction2 = session2.beginTransaction();
			int count = 0;

			if (entityName.equalsIgnoreCase("fcst_print_ips_hw")) {

				int i = 0;
				for (ForeCast_IPS_HW foreCast_IPS_HW : list) {
					i++;
					session1.save(foreCast_IPS_HW);
					session2.save(foreCast_IPS_HW);
					if (i % 100 == 0) {
						session1.flush();
						session1.clear();
						session2.flush();
						session2.clear();
					}
					count++;
				}
				transaction1.commit();
				transaction2.commit();
				session1.clear();
				session2.clear();
				LOG.info("Total records inserted for Table " + entityName
						+ " are " + count);
			}
		status=true;
		} catch (HibernateException e) {
			if (transaction1!=null )
			transaction1.rollback();
			if (transaction2!=null )
				transaction2.rollback();
			e.printStackTrace();
			LOG.info("!!!! Error while Executing "+entityName+" Migration from Mongo to MySql"+e.getMessage());
		}finally{
			session1.close();
			session2.close();
		}
	  return status;
	}

	public boolean insertForecastIPSSupplies(List<ForeCast_IPS_Supplies> list,String entityName){
		LOG.info(list.size());
		boolean status =false;
		Session session1 = sessionFactory.openSession();
		Session session2 = sessionFactorySqlServer.openSession();
		Transaction transaction1 = null;
		Transaction transaction2 = null;
		 try{
	    	 transaction1 = session1.beginTransaction();
	    	 transaction2 = session2.beginTransaction();
	    	 int count=0;
	  
	    	 if(entityName.equalsIgnoreCase("fcst_print_ips_supplies")){
		    		int i=0;
		    		for (ForeCast_IPS_Supplies foreCast_IPS_Supplies : list) {
		    			i++;
		    			session1.save(foreCast_IPS_Supplies);
		    			session2.save(foreCast_IPS_Supplies);
		    			if( i % 100 == 0 ) {
			                session1.flush();
			                session1.clear();
			                session2.flush();
			                session2.clear();
			             }
			          	count++;
		    		}
		    		transaction1.commit();
		    		transaction2.commit();
		    		session1.clear();
		    		session2.clear();
	    	 LOG.info("Total records inserted for Table "+entityName+" are "+count);
	  status =true;
	    	 }
	     }catch(Exception e){
	    	 
	    	 e.printStackTrace();
	     }finally{
	    	 
	    	 session1.close();
	    	 session2.close();
	     }
		
	return status;
	}


	public boolean insertForecastLESHW(List<ForeCast_Les_HW> list,String entityName){
	boolean status = false;
	LOG.info(list.size());
	
	Session session1 = sessionFactory.openSession();
	Session session2 = sessionFactorySqlServer.openSession();
	Transaction transaction1 = null;
	Transaction transaction2 = null;

	try{
    	 transaction1 = session1.beginTransaction();
    	 transaction2 = session2.beginTransaction();
    	 int count=0;
    	 
    	 if (entityName.equalsIgnoreCase("fcst_print_les_hw")) {
				
	    		int i=0;
	    		for (ForeCast_Les_HW foreCast_Les_HW : list) {
	    			i++;
	    			session1.save(foreCast_Les_HW);
	    			session2.save(foreCast_Les_HW);
	    			if( i % 100 == 0 ) {
		                session1.flush();
		                session1.clear();
		                session2.flush();
		                session2.clear();
		             }
		          	count++;
	    		}
	    		transaction1.commit();
	    		transaction2.commit();
		session1.clear();
		session2.clear();
    	 }
    		    	
    	 LOG.info("Total records inserted for Table "+entityName+" are "+count);
     status = true;
     }catch(Exception e){
    	 
    	 e.printStackTrace();
     }finally{
    	 session1.close();
    	 session2.close();
     }
	return status;
}


	public boolean insertForecastLESSupplies(List<?> list,String entityName){
	
		boolean status = false;
	LOG.info(list.size());
	
	Session session1 = sessionFactory.openSession();
	Session session2 = sessionFactorySqlServer.openSession();
	Transaction transaction1 = null;
	Transaction transaction2 = null;

	try{
    	 transaction1 = session1.beginTransaction();
    	 transaction2 = session2.beginTransaction();
    	 int count=0;
    	 if(entityName.equalsIgnoreCase("fcst_print_les_supplies")){
 			
				int i=0;
	    		for (Object object : list) {
	    			i++;
	    			ForeCast_LES_Supplies foreCast_Les_supplies =(ForeCast_LES_Supplies)object;
	    			session1.save(foreCast_Les_supplies);
	    			session2.save(foreCast_Les_supplies);
	    			if( i % 100 == 0 ) {
		                session1.flush();
		                session1.clear();
		                session2.flush();
		                session2.clear();
		             }
		          	count++;
	    		}
	    		transaction1.commit();
	    		session1.clear();
	    		transaction2.commit();
	    		session2.clear();
 		} 
    	 LOG.info("Total records inserted for Table "+entityName+" are "+count);
    	 status=true;
     }catch(Exception e){
    	 
    	 e.printStackTrace();
     }finally{
    	 session1.close();
    	 session2.close();
     }
	
    return status;
	}

	public void saveForecastStatus(ForeCastStatus foreCastStatus) {
		
		Session session1 = sessionFactory.openSession();
		Session session2 = sessionFactorySqlServer.openSession();
		Transaction transaction1 = null;
		Transaction transaction2 = null;

		try {
			transaction1 = session1.beginTransaction();
			transaction2 = session2.beginTransaction();
             LOG.info(foreCastStatus);
			session1.save(foreCastStatus);
			session1.flush();
			session1.clear();
			transaction1.commit();
			
			session2.save(foreCastStatus);
			session2.flush();
			session2.clear();
			transaction2.commit();
		}
		catch (Exception e) {
			e.printStackTrace();
		}finally{
			
			session1.close();
			session2.close();

		}
		
	}

	public List<?> getDatafromforeCast(String entityName) {


		String sql="select * from "+entityName;
		
		//String hql="select max("+sqlFieldName +") from "+entityName;
		  Session session = sessionFactorySqlServer.openSession();
	      Transaction transaction = null;
	      String str = null;
	     
		  transaction=session.beginTransaction();
		  org.hibernate.SQLQuery query = session.createSQLQuery(sql);
		
		  if (entityName.equals("fcst_print_ips_hw")) {
			query.addEntity(ForeCast_IPS_HW.class);
			List<ForeCast_IPS_HW> listResult = query.list();
			transaction.commit();
			session.close();
			return listResult;
		} else if (entityName.equals("fcst_print_ips_supplies")) {
			query.addEntity(ForeCast_IPS_Supplies.class);
			List<ForeCast_IPS_Supplies> listResult = query.list();
			transaction.commit();
			session.close();
			return listResult;
		} else if (entityName.equals("fcst_print_les_hw")) {

			query.addEntity(ForeCast_Les_HW.class);
			List<ForeCast_Les_HW> listResult = query.list();
			transaction.commit();
			session.close();
			return listResult;
		} else if (entityName.equals("fcst_print_les_supplies")) {
			query.addEntity(ForeCast_LES_Supplies.class);
			List<ForeCast_LES_Supplies> listResult = query.list();
			transaction.commit();
			session.close();
			return listResult;
		}
		return null;
	}
	
	public boolean insertForeCastProcessedData(List<ForeCastPrintProcessed> list){
		boolean status = false;
		
		
		Session session1 = sessionFactory.openSession();
		Session session2 = sessionFactorySqlServer.openSession();
		Transaction transaction1 = null;
		Transaction transaction2 = null;

	     try{
	    	 transaction1 = session1.beginTransaction();
	    	 transaction2 = session2.beginTransaction();
	    	
	    	int count=0;
	 			int i=0;
		    		for (ForeCastPrintProcessed foreCastPrintProcessed : list) {
		    			count++;
		    			i++;
		    		   LOG.info(foreCastPrintProcessed);
		    			session1.save(foreCastPrintProcessed);
		    			session2.save(foreCastPrintProcessed);
		    			if( i % 100 == 0 ) {
			                session1.flush();
			                session1.clear();
			                session2.flush();
			                session2.clear();
			             }
		    		}
		    		transaction1.commit();
		    		session1.clear();
		    		transaction2.commit();
		    		session2.clear();
	    	 LOG.info("Total records inserted for Forecast Processed table "+count);
	    	 status=true;
	     }catch(Exception e){
	    	 e.printStackTrace();
	     }finally{
	    	 session1.close();
	    	 session2.close();
	     }
	
	return status;
	}

	/*public Date getMaxDate() {
		String sql="select max(FileDate) from eo_hp_l06_l10_data";
		
		Session session = sessionFactory.openSession();
	      Transaction transaction = null;
	      Date date = null;
	     
		  transaction=session.beginTransaction();
				  Criteria cr = session.createCriteria(EoCommodityPojo.class);

		// To get total row count.
		
	    cr.setProjection(Projections.max("fileDate"));
		 date = (Date) cr.list().get(0);		  
		transaction.commit();
		return date;
	}
*/	
	
	

	
}
