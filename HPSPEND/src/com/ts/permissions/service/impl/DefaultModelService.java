package com.ts.permissions.service.impl;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ts.permissions.dao.impl.DefaultUserDao;
import com.ts.permissions.service.ModelService;

@Service("modelService")
public class DefaultModelService implements ModelService {
	private static final Logger LOGGER = Logger.getLogger(DefaultUserDao.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean save(Object object) {
		Session session = sessionFactory.openSession();
		Transaction transaction = null;
		try {
			transaction = session.beginTransaction();
			session.save(object);
			transaction.commit();
			session.evict(object);
		} catch (Exception exception) {
			if (transaction != null) {
				transaction.rollback();
			}
			LOGGER.warn(exception);
			throw exception;
		} finally {
			session.close();
		}
		return true;
	}

}
