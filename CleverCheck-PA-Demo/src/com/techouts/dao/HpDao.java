package com.techouts.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.techouts.pojo.PADashboardSkuAvbl;
import com.techouts.pojo.User;

@Repository
public class HpDao {
	@Autowired
	private MongoOperations mongoTemplate;

	private static final Logger LOG = Logger.getLogger(HpDao.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	Date now = new Date();
	String Currentdate = sdf.format(now);

	// user Athentication
	public User checkAuthenticate(String username, String password) {
		Query query = new Query(Criteria.where("username").is(username)
				.and("password").is(password));
		// LOG.info("CurrentTime: "+Currentdate+"   Module name: Cart  "+query);
		return mongoTemplate.findOne(query, User.class);
	}

	/**
	 * it will update current user current login details to database
	 * 
	 * @param user
	 * @param currentLogin
	 */
	public void updateUserLoginDetails(User user) {
		LOG.info("updateUserLoginDetails :" + user.getUsername() + "\t"
				+ user.getCurrentLogin());

		Query query = new Query(Criteria.where("username").is(
				user.getUsername()));
		// update lastLogin details
		// LOG.info("CurrentTime: "+Currentdate+"   Module name: Cart  "+query);
		Update update = new Update();
		update.set("currentLogin", user.getCurrentLogin());
		update.set("lastLogin", user.getLastLogin());
		mongoTemplate.updateFirst(query, update, User.class);

	}

	/**
	 * it will return current User basend on username
	 * 
	 * @param username
	 * @return
	 */
	public User getCurrentUser(String username) {
		return mongoTemplate.findOne(
				new Query(Criteria.where("username").is(username)), User.class);
	}

	public List<PADashboardSkuAvbl> getTopThreeShipSkus() {
		// TODO Auto-generated method stub
		LOG.info("CurrentTime: " + Currentdate
				+ "   Module name: PADASHBOARDSKUAVBL  "
				+ new Query().with(new Sort(Direction.DESC, "MAXAVAILABILITY")));
		return mongoTemplate.find(
				new Query().with(new Sort(Direction.DESC, "MAXAVAILABILITY")),
				PADashboardSkuAvbl.class);
	}

	public List<PADashboardSkuAvbl> getTopThreeShipSkus(String bu) {
		// TODO Auto-generated method stub
		Query query = new Query(Criteria.where("BU").is(bu));
		query.with(new Sort(Direction.DESC, "MAXAVAILABILITY"));
		LOG.info("CurrentTime: " + Currentdate
				+ "   Module name: PADASHBOARDSKUAVBL  " + query);
		return mongoTemplate.find(query, PADashboardSkuAvbl.class);
	}

	public List<PADashboardSkuAvbl> getTopThreeShipSkus(String bu, String family) {
		// TODO Auto-generated method stub
		Query query = null;
		if (bu == null)
			query = new Query(Criteria.where("FAMILY").is(family));
		else
			query = new Query(Criteria.where("BU").is(bu).and("FAMILY")
					.is(family));
		query.with(new Sort(Direction.DESC, "MAXAVAILABILITY"));
		LOG.info("CurrentTime: " + Currentdate
				+ "   Module name: PADASHBOARDSKUAVBL  " + query);
		return mongoTemplate.find(query, PADashboardSkuAvbl.class);
	}

}