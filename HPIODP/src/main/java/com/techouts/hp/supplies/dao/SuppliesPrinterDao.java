/**
 * 
 */
package com.techouts.hp.supplies.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.io.dao.PrintersDao;

/**
 * @author TO-OWDG-02
 *
 */
@Repository
public class SuppliesPrinterDao {
	@Autowired
	@Qualifier("hibernate3AnnotatedSessionFactory")
	private SessionFactory sessionFactory;
	private static Logger LOG=Logger.getLogger(PrintersDao.class);
	
	public boolean insertData(List<?> dataList){
		boolean status = false;
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			int count = 0;
				int i = 0;
				for (Object openOrderPrinters : dataList) 
				{
					i++;
					session.save(openOrderPrinters);
					if (i % 100 == 0) 
					{
						session.flush();
						session.clear();
					}
					count++;
				}
				transaction.commit();
				session.clear();
				LOG.info("Total records inserted for Table openorders_print" 
						+ " are " + count);
		status=true;
		} catch (HibernateException e) 
		{
			if (transaction!=null)
			transaction.rollback();
			LOG.info("!!!! Error while Executing openorders_print  Migration from Mongo to MySql"+e.getMessage());
		}
		
		return status;
	}
}
