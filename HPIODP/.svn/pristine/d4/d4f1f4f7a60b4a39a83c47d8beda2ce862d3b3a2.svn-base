package com.io.utill;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongo.mysql.pojos.SkuToAvToPnPojo;

@Service
public class ReadAndWriteUtill {

	@Autowired
	private MongoTemplate mongoTemplate;
	@Autowired
	@Qualifier("hibernate3AnnotatedSessionFactory")
	private SessionFactory sessionFactory;
	private static Logger LOG=Logger.getLogger(ReadAndWriteUtill.class);
	
	public void TransferData(){
		System.out.println("----------enterd into service-----------");
		
		
		long count=getCount("FORECAST_COMBINED_DATA_NEW_W0To26");
		LOG.info("---------total no of records are---------"+count);
		long noOfIterations=count/500000;
		LOG.info("---------no of partitions are-------"+noOfIterations);
		int k=(int) (noOfIterations+1);
		for(int j=0;j<k;j++){
			List<?> list=getMongoData_Limit(j, SkuToAvToPnPojo.class);
			LOG.info("out of "+count+" records -----------no of records taken for "+j+" time are"+list.size());
			saveDataToMysql(list, "FORECAST_COMBINED_DATA_NEW_W0To26");
		}
		
		/*List<String> list=getDistinctDates("FORECAST_COMBINED_DATA_NEW_W0To26","FcstGD");
		LOG.info("----------no of dates are----------->"+list.size());
		for(String date:list){
			List<?> dataList=getMongoData(date, SkuToAvToPnPojo.class, "FcstGD");
			LOG.info("------------data taken from mongo for date "+date+"------->"+dataList.size());
			saveDataToMysql(dataList, "FORECAST_COMBINED_DATA_NEW_W0To26");
		}*/
		
	}
	public List<String> getDistinctDates(String collectionName, String mongoFieldName){
			List<String> list=mongoTemplate.getCollection(collectionName).distinct(mongoFieldName);
			return list;
	}
	
	public List<?> getMongoData(String mongoDate, Class<?> clazz, String mongoFieldName){
			Query query=new Query(Criteria.where(mongoFieldName).is(mongoDate));
			List<?> list=mongoTemplate.find(query, (Class<?>)clazz);
			return list;
	}
	
	
	
	public List<?> getMongoData_Limit(int i,Class<?> clazz){
		int skipvalue=i*500000;
		LOG.info("------------skipping value is---------"+skipvalue);
		Query query=new Query();
		query.skip(i);
		query.limit(500000);
		return mongoTemplate.find(query, (Class<?>)clazz);
		
	}
	
	public long getCount(String collectionName){
		LOG.info("--------------entered into count-------------");
		return mongoTemplate.getCollection(collectionName).count();
	}
		
	public void saveDataToMysql(final List<?> list, String collectionName){
	System.out.println("------------enterd into save method-------------");
	      Session session = sessionFactory.openSession();
	      Transaction transaction = null;
	      try{
	    	  transaction = session.beginTransaction();
	    	  int count=0;
	         for ( int i=0; i<list.size(); i++ ) {
	        	 session.save(list.get(i));
	          	if( i % 50 == 0 ) {
	                session.flush();
	                session.clear();
	             }
	          	count++;
	          }
	    	 transaction.commit();
	    	 LOG.info("---Size of data inserted into MySql "+collectionName+"----------------->"+count);
	    	 System.out.println("---Size of data inserted into MySql "+collectionName+"----------------->"+count);
	    	// Mail.sendMail(collectionName+"  Migration from Mongo to MySql completed Successfully"+new Date());
	    	 LOG.info(collectionName+"  Migration from Mongo to MySql completed Successfully"+new Date());
	         System.out.println(collectionName+"  Migration from Mongo to MySql completed Successfully"+new Date());
	      }catch (HibernateException e) {
	         if (transaction!=null) transaction.rollback();
	       //  Mail.sendMail("Error while Executing "+collectionName+" Migration from Mongo to MySql )"+e.getMessage()+new Date());
	         LOG.info("!!!! Error while Executing "+collectionName+" Migration from Mongo to MySql"+e.getMessage());
	         System.out.println("!!!! Error while Executing "+collectionName+" Migration from Mongo to MySql"+e.getMessage());
	      }finally {
	         session.close(); 
	      }
		
	}
}
