package com.techouts.services;

import java.awt.Font;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.dao.RegistrationDao;
import com.techouts.pojo.User;
import com.techouts.util.CommonFeatures;
import com.techouts.util.SendMails;
import com.techouts.util.ShaAlgorithm;

@Service
public class ForgetPasswordService {
	private static final Logger LOG = Logger
			.getLogger(ForgetPasswordService.class);
	@Autowired
	private RegistrationDao rigistrarionDao;

	@Resource(name = "myProps")
	private Properties properties;

	public User checkUserExist(String username) {
		return rigistrarionDao.mailidExistingCheck(username);
	}

	public void resetPassword(User user) {

		SecureRandom random = null;
		random = new SecureRandom();
		String password = null;
		password = new BigInteger(40, random).toString(32);
		Font newPassword = new Font(password, Font.BOLD, 40);
		user.setPassword(ShaAlgorithm.encriptData(password));
		user.setLastLogin(0);
		rigistrarionDao.updateUser(user);
		String msgBody = "Hi  " + user.getFirstname().substring(0, 1)
				+ user.getFirstname().substring(1)
				+ properties.getProperty("Reset.Password.body1") + " "
				+ newPassword.getName()
				+ properties.getProperty("Reset.Password.body2")
				+ properties.getProperty("Reset.Password.body3")
				+ properties.getProperty("Reset.Password.body4")
				+ properties.getProperty("Reset.Password.body5");
		SendMails.sendMail(user.getUsername(),
				properties.getProperty("Reset.Password.Subject"), msgBody);
	}

	public void changePassword(User user) {
		LOG.info("user last login isssssssss:::::" + user.getLastLogin());
		String password = null;
		password = user.getPassword();
		user.setPassword(ShaAlgorithm.encriptData(password));
		if (user.getLastLogin() == 0) {
			user.setCurrentLogin(new Date().getTime());
			user.setLastLogin(new Date().getTime());
		}
		rigistrarionDao.updateUser(user);
		String msgBody = "Hi  " + user.getFirstname().substring(0, 1)
				+ user.getFirstname().substring(1)
				+ properties.getProperty("Change.Password.body1") + " "
				+ password + "";
		SendMails.sendMail(user.getUsername(),
				"HP CleverCheck Password changed ", msgBody);

	}

}
