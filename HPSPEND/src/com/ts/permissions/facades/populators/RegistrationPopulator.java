package com.ts.permissions.facades.populators;

import org.springframework.core.convert.ConversionException;

import com.ts.permissions.model.UserModel;
import com.ts.permissions.servicelayer.dto.populator.Populator;
import com.ts.permissions.user.dto.UserSignUpDTO;

public class RegistrationPopulator implements Populator<UserSignUpDTO, UserModel> {

	public void populate(UserSignUpDTO source, UserModel target) throws ConversionException {
		target.setFirstName(source.getFirstName());
		target.setLastName(source.getLastName());
		target.setExPoliceId(source.getExPoliceId());
		target.setEmail(source.getEmail());
		target.setPassword(source.getPassword());
		target.setMobile(source.getMobile());
		target.setState(source.getState());
		target.setUserImg(source.getUserImg());
		target.setUserType(source.getUserType());
		target.setCity(source.getCity());
		target.setArea(source.getArea());
	}

}
