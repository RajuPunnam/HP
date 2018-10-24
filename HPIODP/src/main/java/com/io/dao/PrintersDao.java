package com.io.dao;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.io.pojos.AgingPrinters;
import com.io.pojos.EoCommodityPojo;
import com.io.pojos.ForeCastStatus;
import com.io.pojos.OpenOrderPrinters;


@Repository
public class PrintersDao {

	@Autowired
	@Qualifier("hibernate3AnnotatedSessionFactory")
	private SessionFactory sessionFactory;
	
	private static Logger LOG=Logger.getLogger(PrintersDao.class);
		
	public Date getMaxDate(Class<?> clazz) {
		
		Session session = sessionFactory.openSession();
	      Transaction transaction = null;
	      Date date = null;
	     
		  transaction=session.beginTransaction();
				  Criteria cr = session.createCriteria(clazz);

		// To get total row count.
		
	    cr.setProjection(Projections.max("fileDate"));
		List list = cr.list();
	   if(list.size()>0)
		date = (Date)list.get(0);
		transaction.commit();
		return date;
		
	}


	public void upDateForecastStatus(ForeCastStatus foreCastStatus) {
		
		String hql = "UPDATE ForeCastStatus set maxDate = :maxDate "  + 
	             "WHERE fileName = :OpenOrderPrinters";
		Session session = sessionFactory.openSession();
		Query query = session.createQuery(hql);
	query.setParameter("maxDate", foreCastStatus.getMaxDate());
	query.setParameter("OpenOrderPrinters", foreCastStatus.getFileName());
	int result = query.executeUpdate();
	System.out.println("Rows affected: " + result);
		
		
	}

	public Date getMaxDateofEOC() {
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

	
	
}
