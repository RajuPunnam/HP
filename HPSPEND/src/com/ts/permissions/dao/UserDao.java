package com.ts.permissions.dao;

import java.util.List;

import com.ts.permissions.model.UserModel;

public interface UserDao {

	List<UserModel> getUserByEmailId(String emailId);
}
