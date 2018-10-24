package com.ts.permissions.facade.impl;

import javax.annotation.Resource;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.ts.permissions.facade.UserFacade;
import com.ts.permissions.model.UserModel;
import com.ts.permissions.service.UserService;
import com.ts.permissions.user.dto.ResponseData;
import com.ts.permissions.user.dto.UserSignUpDTO;

@Component
public class DefaultUserFacade implements UserFacade {

	@Resource(name = "userService")
	private UserService userService;

	@Resource(name = "converter")
	private Converter<UserSignUpDTO, UserModel> converter;

	public ResponseData registration(@RequestBody UserSignUpDTO userSignUpDTO) {
		ResponseData responseData = new ResponseData();
		UserModel user = converter.convert(userSignUpDTO);
		try {
			if (userService.userRegistration(user)) {
				responseData.setMsg(user.getEmail() + " user registration successful");
				responseData.setStatus(true);
			} else {
				responseData.setMsg(user.getEmail() + " user registration failed");
			}
		} catch (Exception exception) {
			responseData.setMsg(user.getEmail() + " User existed already ");
		}
		return responseData;
	}

	@Override
	public ResponseData userLogin(String emailid, String password) throws Exception {
		ResponseData responseData = new ResponseData();
		try {
			userService.userLogin(emailid, password);
			responseData.setMsg("User login successful");
			responseData.setStatus(true);
		} catch (Exception exception) {
			responseData.setMsg(exception.getMessage());
		}
		return responseData;
	}
}
