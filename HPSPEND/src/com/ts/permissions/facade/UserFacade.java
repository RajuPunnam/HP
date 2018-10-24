package com.ts.permissions.facade;

import com.ts.permissions.user.dto.ResponseData;
import com.ts.permissions.user.dto.UserSignUpDTO;

public interface UserFacade {
	ResponseData registration(UserSignUpDTO userSignUp);
	ResponseData userLogin(String userName,String password) throws Exception;
}
