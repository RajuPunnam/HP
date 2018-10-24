package com.techouts.services;

import java.awt.Font;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.techouts.dao.RegistrationDao;
import com.techouts.exceptions.HPPAException;
import com.techouts.pojo.User;
import com.techouts.util.SendMails;
import com.techouts.util.ShaAlgorithm;

@Service
public class UserInfoService {

	@Autowired
	private RegistrationDao registrationDao;
	
	@Resource(name = "myProps")
	private Properties properties;

	
	public User getUserData(String username){
	try{
		return registrationDao.mailidExistingCheck(username);
	}catch(Exception e){
		throw new HPPAException("Faild To get USER Data"+e.getMessage());
	} 
	}
	public List<User> getAllUsers() {
		  return registrationDao.getUsers();
	 }
	/*public void saveNewUser(User user) {
		SecureRandom random = null;
		 random = new SecureRandom();
		String password=null;
		password=new BigInteger(40, random).toString(32);
		user.setPassword(ShaAlgorithm.encriptData(password));
		user.setActive(true);
		registrationDao.updateNewUser(user);
		String msgBody = "Hi  "+user.getFirstname().substring(0, 1)+user.getFirstname().substring(1)+",\n\nThanks for applying for Clevercheck access. Your access has been approved and your default password is "+"\n"+password+
				".On your first login, Cleverchck will ask you to change your password before you can begin using it.\n\nPlease keep the new password safe as Clevercheck contains sensitive business data."
				+ "Please connect with \nAdministrator if you have any questions. Please do not reply here as this mailbox doesn't accept replies.\n\nThanks,\nClevercheck.";
		SendMails.sendMail(user.getUsername(),"HP CleaverCheck Registration",msgBody);
	}*/
	
	public void saveNewUser(User user) {
		SecureRandom random = null;
		 random = new SecureRandom();
		String password=null;
		password=new BigInteger(40, random).toString(32);
		Font newPassword =new Font(password, Font.BOLD, 50);
		user.setPassword(ShaAlgorithm.encriptData(password));
		user.setActive(true);
		registrationDao.updateNewUser(user);
		String msgBody = "Hi  "+user.getFirstname().substring(0, 1)+user.getFirstname().substring(1)+properties.getProperty("Register.body1")+properties.getProperty("Register.body2")+
				properties.getProperty("Register.body3")+" "+user.getUsername()+properties.getProperty("Register.body4")+" "+newPassword.getName()+
				properties.getProperty("Register.body5")+properties.getProperty("Register.body6")+properties.getProperty("Register.body7")+properties.getProperty("Register.body8") ;
		SendMails.sendMail(user.getUsername(),properties.getProperty("Register.subject"),msgBody);
	}
	
	
	public void removeNewUser(User user) {
		registrationDao.deleteUser(user);
		String msgBody="Hi   "+user.getFirstname().substring(0, 1)+user.getFirstname().substring(1)+",\n\nThanks for applying for Clevercheck access. You access has been rejected post review. Please contact your "
				+ "\nadministrator directly.Please do not reply here as this mailbox doesnt accept replies.\n\nThanks,\nClevercheck ";
		SendMails.sendMail(user.getUsername(),"HP CleverCheck Access Denied",msgBody);
	}
	
	
	
	public void removeExistingUser(User user) {
		registrationDao.deleteUser(user);
		String msgBody="Hi  "+user.getFirstname().substring(0, 1)+user.getFirstname().substring(1)+properties.getProperty("Remove.Existing.User1.body")+properties.getProperty("Remove.Existing.User2.body");
				
		SendMails.sendMail(user.getUsername(),"HP CleverCheck Access Revoked",msgBody);
	}
	
}
