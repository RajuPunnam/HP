package com.techouts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.dao.ChangePasswordDao;

@Service
public class ChangePasswordService {

	@Autowired
	private ChangePasswordDao changePasswordDao;

	public String changePassword(String mail_id, String changedpassword) {

		return changePasswordDao.changePassword(mail_id, changedpassword);
	}

}
