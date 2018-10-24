package com.ts.permissions.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ts.permissions.dao.UserDao;
import com.ts.permissions.model.UserModel;

@Repository("defaultUserDao")
public class DefaultUserDao implements UserDao {

	private static final Logger LOGGER = Logger.getLogger(DefaultUserDao.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<UserModel> getUserByEmailId(String emailId) {
		Criteria criteria = sessionFactory.openSession().createCriteria(UserModel.class);
		criteria.add(Restrictions.eq("email", emailId));
		return criteria.list();
	}

}
