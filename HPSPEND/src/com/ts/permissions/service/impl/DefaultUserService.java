package com.ts.permissions.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.ts.permissions.dao.UserDao;
import com.ts.permissions.model.UserModel;
import com.ts.permissions.service.ModelService;
import com.ts.permissions.service.UserService;

@Service("userService")
public class DefaultUserService implements UserService {

	@Resource(name = "modelService")
	private ModelService modelService;

	@Resource(name = "defaultUserDao")
	private UserDao userDao;

	@Override
	public boolean userRegistration(UserModel user) {
		return modelService.save(user);

	}

	@Override
	public void userLogin(String email, String password) throws Exception {
		List<UserModel> users = userDao.getUserByEmailId(email);
		if (CollectionUtils.isEmpty(users)) {
			throw new Exception("User not find with eamil id " + email);
		}
		if (users.size() > 1) {
			throw new Exception("duplicates users find with email id " + email);
		}

		if (!users.get(0).getPassword().equals(password)) {
			throw new Exception("Password not matched with given email id");
		}
	}
}
