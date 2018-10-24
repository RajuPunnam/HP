package com.techouts.hp.dao.impl;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.techouts.hp.pojo.User;

@Repository
public class UserAuthenticationDao 
{
	public static final Logger log = Logger.getLogger(UserAuthenticationDao.class);
	@Autowired
	private MongoTemplate mongoTemplate;
	public User loadUserByUsername(final String username, String password) 
	{
		Query query = new Query(Criteria.where("username").is(username)
				.and("password").is(password));
		User user = mongoTemplate.findOne(query, User.class);
		return user;
	}
	public String userRegistration(User user)
	{
		String regMessage=null;
		Query query = new Query(Criteria.where("username").is(user.getUsername()));
		try
		{
		List<User> usersList=mongoTemplate.find(query, User.class);
		if(usersList.size()==0)
		{
			mongoTemplate.insert(user);	
			regMessage="User Registration sucessfull";
		}
		else
		{
			regMessage="User Existed already";
		}
		}catch(Exception exception)
		{
			regMessage="User Registration failed";
		}
		return regMessage;
	}
	public long findUser(String userName)
	{
		Query query = new Query(Criteria.where("username").is(userName));
		return mongoTemplate.count(query, User.class);
	}

}
