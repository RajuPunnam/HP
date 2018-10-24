package com.techouts.services;

import java.util.Properties;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.dao.RegistrationDao;
import com.techouts.pojo.User;
import com.techouts.pojo.UserRegistration;
import com.techouts.util.SendMails;
import com.techouts.util.ShaAlgorithm;

@Service
public class RegistrationService {

	@Autowired
	private RegistrationDao rigistrarionDao;

	@Resource(name = "myProps")
	private Properties properties;

	private static final Logger logger = Logger
			.getLogger(RegistrationService.class.getName());

	/*
	 * public void registerUser(User user){ rigistrarionDao.registerUser(user);
	 * logger.info("Service : registerUser()"); String msgBody =
	 * "Hi "+user.getUsername()+", you have Registered successfully";
	 * SendMails.sendMail
	 * (user.getUsername(),"Successfully Registered for HP",msgBody); }
	 */

	public boolean mailidExistingCheck(String mailid) {
		User existingmailid = rigistrarionDao.mailidExistingCheck(mailid);
		if (existingmailid == null) {
			return false;
		} else {
			return true;
		}
	}

	public void registerUser(UserRegistration userRegistration) {
		userRegistration.setLastLogin(0);
		rigistrarionDao.registerUser(userRegistration);

		String msgBody = "Hi  Admin, \n"
				+ properties.getProperty("Registration.Admin");
		SendMails.sendMail(properties.getProperty("Admin.Mail"),
				"HP CleverCheck New User Registration", msgBody);

		logger.info("Service : registerUser()");

	}
}
