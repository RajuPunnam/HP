package com.techouts.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.techouts.pojo.User;

@Repository
public class ChangePasswordDao {

	@Autowired
	private MongoOperations mongoTemplate;

	public String changePassword(String mail_id, String changedpassword) {

		Query query = new Query(Criteria.where("username").is(mail_id));
		User user = mongoTemplate.findOne(query, User.class);
		try {
			user.setPassword(changedpassword);

			mongoTemplate.save(user);
			return "your password is changed";
		} catch (Exception e) {
			return "your password changes process faild";
		}

	}
}
