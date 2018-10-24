package com.ts.permissions.controller;

import java.io.IOException;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.permissions.facade.UserFacade;
import com.ts.permissions.user.dto.ResponseData;
import com.ts.permissions.user.dto.UserSignUpDTO;

@Controller
public class UsersController {
	@Resource(name = "defaultUserFacade")
	private UserFacade userFacade;

	@ResponseStatus(value = HttpStatus.OK)
	@RequestMapping(value = "/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseData registration(@RequestBody String userSignUpDTO) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		UserSignUpDTO usersignup = objectMapper.readValue(userSignUpDTO, UserSignUpDTO.class);
		return userFacade.registration(usersignup);
	}

	@RequestMapping(value = "/login")
	public @ResponseBody ResponseData doUserLogin(@RequestParam("email") String email, @RequestParam("password") String password) throws Exception {
		return userFacade.userLogin(email, password);

	}
}
