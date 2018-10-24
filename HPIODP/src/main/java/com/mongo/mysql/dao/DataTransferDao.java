package com.mongo.mysql.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.io.pojos.SkuToAvToPnPojo;
import com.io.utill.Mail;
import com.mongo.mysql.pojos.Consumption_tbl;
import com.mongo.mysql.pojos.Consumption_tbl_MySql;
import com.mongo.mysql.pojos.FTPFile_Status_Sosa_Shortage;
import com.mongo.mysql.pojos.MASTER_BOM_FLEX;
import com.mongo.mysql.pojos.Master_Bom_Flex_Mongo;


@Repository
public class DataTransferDao {
	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	@Qualifier("hibernate3AnnotatedSessionFactory")
	private SessionFactory sessionFactory;
	private static Logger LOG=Logger.getLogger(DataTransferDao.class);
	
	public  List<String> getUniqueDatesFromMySql(String entityName, String sqlFieldName){
		String sql="select distinct fileDate from Clean ";
		String hql="select distinct "+sqlFieldName+" from "+entityName;
		
		
		  Session session = sessionFactory.openSession();
	      Transaction transaction = null;
	      List<String> list=null;
	      try{
		  transaction=session.beginTransaction();
		  org.hibernate.Query query = session.createQuery(hql);
		  list= query.list();
		  transaction.commit();
	      }catch(HibernateException e){
	    	  if(transaction!=null) transaction.rollback();
	    	  e.printStackTrace();
	      }finally{
		  session.close();
	      }
		return list;
		 
	}
	
	public List<String> getDatesFromMongo(String collectionName, String mongoFieldName){
		List<String> list=mongoTemplate.getCollection(collectionName).distinct(mongoFieldName);
		return list;
	}
	
	public List<?> getMongoData(List<String> mongoDates, Class<?> clazz, String mongoFieldName){
		Query query=new Query(Criteria.where(mongoFieldName).in(mongoDates));
		List<?> list=mongoTemplate.find(query, (Class<?>)clazz);
		return list;
	}
	
	public String saveDataToMysql(final List<?> list, String collectionName){
		String status="Failure";
	      Session session = sessionFactory.openSession();
	      Transaction transaction = null;
	      try{
	    	  transaction = session.beginTransaction();
	    	  int count=0;
	         for ( int i=0; i<list.size(); i++ ) {
	        	 if(collectionName.equalsIgnoreCase("PPB_PC")){
	        		 session.save(check(list.get(i)));
	        	 }else if(collectionName.equalsIgnoreCase("MASTER_BOM_FLEX")){
	        		 session.save(flexBom(list.get(i)));
	        	 }else{
	        	 session.save(list.get(i));
	        	 }
	          	if( i % 100 == 0 ) {
	                session.flush();
	                session.clear();
	             }
	          	count++;
	          }
	    	 transaction.commit();
	    	 LOG.info("---Size of data inserted into MySql "+collectionName+"----------------->"+count);
	    	 Mail.sendMail(collectionName+"  Migration from Mongo to MySql completed Successfully"+new Date());
	    	 LOG.info("--"+collectionName+"  Migration from Mongo to MySql completed Successfully"+new Date());
	    	 status="Success";
	      }catch (HibernateException e) {
	         if (transaction!=null) transaction.rollback();
	         Mail.sendMail("Error while Executing "+collectionName+" Migration from Mongo to MySql )"+e.getMessage()+new Date());
	         LOG.info("!!!! Error while Executing "+collectionName+" Migration from Mongo to MySql"+e.getMessage());
	         status=e.getMessage();
	      }finally {
	         session.close(); 
	      }
		return status;
		
	}

	public Date getMaxDate(String entityName, String sqlFieldName,String id) throws Exception{
	
		 String sql="select distinct "+sqlFieldName+" from "+entityName+" WHERE "+id+"=(SELECT MAX("+id+") FROM "+entityName+")";
		 
		  Session session = sessionFactory.openSession();
	      Transaction transaction = null;
	      Date date = null;
	     
		  transaction=session.beginTransaction();
		  org.hibernate.Query query = session.createQuery(sql);
		  List listResult = query.list();
		  String str=(String) listResult.get(0);
		  
		  String dateForamate[]={"MM/dd/yyyy","yyyy/MM/dd"};
		  for(String df:dateForamate){
		  try {
			  SimpleDateFormat sf=new SimpleDateFormat(df);
			  sf.setLenient(false);
			  date= sf.parse(str.replaceAll("-|\\.", "/"));
			  break;
		    } catch (ParseException e) {
			continue;
		   }  
		  }
		  transaction.commit();
		  session.close();
		return date;
	}
	
	public Consumption_tbl_MySql check(Object object){
			Consumption_tbl_MySql cons=new Consumption_tbl_MySql();
			Consumption_tbl consumption_tbl=(Consumption_tbl)object;
			
			cons.setAlm(consumption_tbl.getAlm());
			cons.setDate(consumption_tbl.getDate());
			cons.setDescription(consumption_tbl.getDescription());
			cons.setEstafterTrans(consumption_tbl.getEstafterTrans());
			cons.setHr_4Hrs(consumption_tbl.getHr_4Hrs());
			cons.setId1(consumption_tbl.getId1());
			cons.setIp(consumption_tbl.getIp());
			cons.setItemCode(consumption_tbl.getItemCode());
			cons.setLoginCode(consumption_tbl.getLoginCode());
			cons.setOpBalance(consumption_tbl.getOpBalance());
			cons.setQtyLow(consumption_tbl.getQtyLow());
			cons.setRetrab(consumption_tbl.getRetrab());
			cons.setSku(consumption_tbl.getSku());
			cons.setTotalOp(consumption_tbl.getTotalOp());
			cons.setTransactionType(consumption_tbl.getTransactionType());
			cons.setTransHr(consumption_tbl.getTransHr());
		return cons;
	}
	
	public MASTER_BOM_FLEX flexBom(Object object){
		Master_Bom_Flex_Mongo mbom=(Master_Bom_Flex_Mongo)object;
		MASTER_BOM_FLEX master_BOM_FLEX=new MASTER_BOM_FLEX();
		
		master_BOM_FLEX.setBom_Type(mbom.getBom_Type());
		master_BOM_FLEX.setBuyer(mbom.getBuyer());
		master_BOM_FLEX.setChange(mbom.getChange());
		master_BOM_FLEX.setCode(mbom.getCode());
		master_BOM_FLEX.setCumulative(mbom.getCumulative());
		master_BOM_FLEX.setDate(mbom.getDate());
		master_BOM_FLEX.setDescription(mbom.getDescription());
		master_BOM_FLEX.setFlatBom(mbom.getFlatBom());
		master_BOM_FLEX.setFlatComp(mbom.getFlatComp());
		master_BOM_FLEX.setGroup(mbom.getGroup());
		master_BOM_FLEX.setIn(mbom.getIn());
		master_BOM_FLEX.setLevel(mbom.getLevel());
		master_BOM_FLEX.setOn_Hand(mbom.getOn_Hand());
		master_BOM_FLEX.setOrders(mbom.getOrders());
		master_BOM_FLEX.setOut(mbom.getOut());
		master_BOM_FLEX.setPart(mbom.getPart());
		master_BOM_FLEX.setPath(mbom.getPath());
		master_BOM_FLEX.setPlanner(mbom.getPlanner());
		master_BOM_FLEX.setQuantity_Per(mbom.getQuantity_Per());
		master_BOM_FLEX.setQuantityPer1(mbom.getQuantityPer1());
		master_BOM_FLEX.setReceipts(mbom.getReceipts());
		master_BOM_FLEX.setSequence(mbom.getSequence());
		master_BOM_FLEX.setSite(mbom.getSite());
		master_BOM_FLEX.setSite1(mbom.getSite1());
		master_BOM_FLEX.setSupplier(mbom.getSupplier());
		master_BOM_FLEX.setSupply_Type(mbom.getSupply_Type());
		master_BOM_FLEX.setTarget(mbom.getTarget());
		master_BOM_FLEX.setThis_Level(mbom.getThis_Level());
		master_BOM_FLEX.setTotal_Demand(mbom.getTotal_Demand());
		master_BOM_FLEX.setUnit_Cost(mbom.getUnit_Cost());
		master_BOM_FLEX.setUom(mbom.getUom());
		
		return master_BOM_FLEX;
	}
	
	public int getMySqlDataCount(String entityName) {
		  String sql="select count(*) from "+entityName;
		  int result = 0;
		  Session session = sessionFactory.openSession();
	      Transaction transaction = null;
	      try{
		  transaction=session.beginTransaction();
		  org.hibernate.Query query = session.createQuery(sql);
		  result=((Long)query.uniqueResult()).intValue();
		  transaction.commit();
	      }catch(HibernateException e){
	    	  if(transaction!=null) transaction.rollback();
	    	  e.printStackTrace();
	      }finally{
		  session.close();
	      }
		return result;
		
	}
	
	public long getCollectionCount(String collectionName){
		LOG.info("--------------------dao");
		return mongoTemplate.getCollection(collectionName).count();
	}
	
	public List<?> getCollectionData(int k,Class<?> clazz){
		int skipValue=k*1000000;
		LOG.info("-------skipping value for "+k+"th time is "+skipValue+"\t"+new Date());
		Query query=new Query();
		query.skip(skipValue);
		query.limit(1000000);
		return mongoTemplate.find(query, (Class<?>)clazz);
	}
	
	public void saveCollectionToMySql(int index, final List<?> list, String collectionName) throws HibernateException{
		
	      Session session = sessionFactory.openSession();
	      Transaction transaction = null;
	      try{
	    	  transaction = session.beginTransaction();
	    	  int count=0;
	         for ( int i=0; i<list.size(); i++ ) {
	        	 session.save(list.get(i));
	          	if( i % 100 == 0 ) {
	                session.flush();
	                session.clear();
	             }
	          	count++;
	          }
	    	 transaction.commit();
	    	 LOG.info(index+"th time Size of data inserted into MySql "+collectionName+"----------------->"+count);
	    	 
	      }catch (HibernateException e) {
	         if (transaction!=null) transaction.rollback();
	         LOG.info(index+"th time !!!! Error while Executing "+collectionName+" Migration from Mongo to MySql"+e.getMessage());
	         throw new HibernateException(index+"th time !!!! Error while Executing "+collectionName+" Migration from Mongo to MySql"+e.getMessage());
	      }finally {
	         session.close(); 
	      }
	
		
	}

	public int truncateTable(String entityName) {
		
		  //String sql="truncate table "+entityName;
		  String sql="delete from "+entityName;
		  int result = 0;
		  Session session = sessionFactory.openSession();
	      Transaction transaction = null;
	      try{
		  transaction=session.beginTransaction();
		  org.hibernate.Query query = session.createQuery(sql);
		  result=query.executeUpdate();
		  transaction.commit();
	      }catch(HibernateException e){
	    	  if(transaction!=null) transaction.rollback();
	    	  e.printStackTrace();
	      }finally{
		  session.close();
	      }
		return result;
	}

	public List<?> getBomData(Class<?> clazz) {
		return mongoTemplate.findAll(clazz);
	}

	public void save_DataToMysql(FTPFile_Status_Sosa_Shortage pojo,String collectionName) {
		
		Session session = sessionFactory.openSession();
	      Transaction transaction = null;
	      try{
	    	  transaction = session.beginTransaction();
	          session.save(pojo);
	    	  transaction.commit(); 
	      }catch (HibernateException e) {
	         if (transaction!=null) transaction.rollback();
	         LOG.info("!!!! Error while Executing "+collectionName+" File status "+e.getMessage());
	         throw new HibernateException("!!!! Error while Executing "+collectionName+" File status "+e.getMessage());
	      }finally {
	         session.close(); 
	      }
	}

}

