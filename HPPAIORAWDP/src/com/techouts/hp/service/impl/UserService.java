package com.techouts.hp.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techouts.hp.dao.impl.UserAuthenticationDao;
import com.techouts.hp.pojo.User;
/**
 * UserService class for user login
 * @author raju.p
 *
 */
@Service
public class UserService {
	public static final Logger log = Logger.getLogger(UserService.class);
	@Autowired
	private UserAuthenticationDao userAuthenticationDao;

	public User loadUserByUsername(String username, String password)
			throws UsernameNotFoundException {
		User user = userAuthenticationDao
				.loadUserByUsername(username, password);
		return user;
	}

	public Set<String> getUserRoles(String roles) {
		String[] role = roles.split(",");
		Set<String> userRoles = new HashSet<String>();
		if (role.length > 0) {
			for (String r : role) {
				userRoles.add(r);
			}
			return userRoles;
		}
		return null;
	}

	public String userRegistration(User user) {
		user.getUsername();
		user.getPassword();
		user.setActive(true);
		user.setRoles("ROLE_SALES");
		String statusMessage = "User Name And Password Should not Empty";
		if (user != null) {
			if (user.getUsername() != null && user.getPassword() != null) {
				statusMessage = userAuthenticationDao.userRegistration(user);
			}
		}

		return statusMessage;
	}
}