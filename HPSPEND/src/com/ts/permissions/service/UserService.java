package com.ts.permissions.service;

import com.ts.permissions.model.UserModel;

public interface UserService {
	boolean userRegistration(UserModel user);

	void userLogin(String email, String password) throws Exception;
}
