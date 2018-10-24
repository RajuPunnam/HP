package com.techouts.beans;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import com.techouts.pojo.UserRegistration;
import com.techouts.services.RegistrationService;
import com.techouts.util.DateUtil;

@ManagedBean
public class RegistrationBean {
	@ManagedProperty("#{registrationService}")
	private RegistrationService registrationService;
	private UserRegistration userRegistration;
	private String showMessage;
	private final static Logger logger = Logger.getLogger(RegistrationBean.class.getName());
	
	@PostConstruct
	public void init(){
	 userRegistration=new UserRegistration();
	}
	
	// user registration  
	public void registerUser(ActionEvent event) throws NoSuchAlgorithmException{
		// userRegistration.setPassword(ShaAlgorithm.encriptData("12345"));
		 userRegistration.setActive(false);
		 if(userRegistration.getBussinessunit().equals("Sales")){
			 userRegistration.setRole("ROLE_SALES");
		 }else if(userRegistration.getBussinessunit().equals("Distributor")){
			 userRegistration.setRole("ROLE_SCM");
		 }		 
		 userRegistration.setRegistredDate(DateUtil.getBrazilTime());
	    boolean mailidExist=registrationService.mailidExistingCheck(userRegistration.getUsername());
	    if(mailidExist){
		logger.log(Level.INFO, userRegistration.getUsername()+" is already exists");
		addMessage(userRegistration.getUsername()+" is already exist! Pls try with another..");
	    }else{
	    showMessage="Product administrator will review your request and will inform through email to you";
		registrationService.registerUser(userRegistration);
		logger.log(Level.INFO, userRegistration.getUsername()+" has registrered Successfully");
		addMessage(userRegistration.getUsername()+" has registrered Successfully"+showMessage);
		userRegistration=null;
	    }			
				
	}	
	public void addMessage(String summary) {
		 FacesContext context = FacesContext.getCurrentInstance();
	        context.addMessage(null, new FacesMessage("",summary) );
	       
	}
	public RegistrationService getRegistrationService() {
		return registrationService;
	}
	public void setRegistrationService(RegistrationService registrationService) {
		this.registrationService = registrationService;
	}
	public UserRegistration getUserRegistration()
	{
		return userRegistration;
	}


	public void setUserRegistration(UserRegistration userRegistration)
	{
		this.userRegistration = userRegistration;
	}

	public String getShowMessage() {
		return showMessage;
	}

	public void setShowMessage(String showMessage) {
		this.showMessage = showMessage;
	}

}