package com.techouts.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.techouts.pojo.User;
import com.techouts.pojo.UserRegistration;

@Repository
public class RegistrationDao {
	private static final Logger LOG = Logger.getLogger(RegistrationDao.class);
	@Autowired
	private MongoOperations mongoTemplate;

	public void registerUser(User user) {
		mongoTemplate.insert(user);
		LOG.info("registerUser(User user) in registerdao ");
	}

	public void updateUser(User user) {
		LOG.info(user);
		Query query = new Query(Criteria.where("username").is(
				user.getUsername()));
		Update update = new Update();
		update.set("lastLogin", user.getLastLogin());
		update.set("password", user.getPassword());
		mongoTemplate.updateFirst(query, update, User.class);
		LOG.info("Password Updated Sucessfully");
	}

	public User mailidExistingCheck(String mailid) {
		Query query = new Query();
		query.addCriteria(Criteria.where("username").is(mailid));
		// query.fields().include("username");

		User existmailid = mongoTemplate.findOne(query, User.class);
		return existmailid;
	}

	public void registerUser(UserRegistration user) {
		mongoTemplate.insert(user, "User");
		LOG.info("registerUser(User user) in registerdao ");
	}

	public User checkUser(String username) {
		Query query = new Query(Criteria.where("username").is(username));

		return mongoTemplate.findOne(query, User.class);

	}

	public List<User> getUsers() {
		return mongoTemplate.findAll(User.class, "User");
	}

	public void updateNewUser(User user) {

		Query query = new Query(Criteria.where("username").is(
				user.getUsername()));
		Update update = new Update();
		update.set("lastLogin", 0);
		update.set("isActive", user.isActive());
		update.set("Password", user.getPassword());
		mongoTemplate.updateFirst(query, update, User.class);
	}

	public void deleteUser(User user) {
		Query query = new Query(Criteria.where("username").is(
				user.getUsername()));
		LOG.info("in deleteUser" + user.getUsername());
		mongoTemplate.remove(query, User.class);

	}

}
