package com.techouts.beans;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.techouts.pojo.User;
import com.techouts.services.ChangePasswordService;
import com.techouts.services.HpServices;

@ManagedBean
@SessionScoped
public class LoginBean {

	private String username;
	private String password;
	private long lastLgoin;

	private String rePassword;

	@ManagedProperty("#{changePasswordService}")
	private ChangePasswordService changePasswordService;

	@ManagedProperty("#{hpServices}")
	private HpServices hpServices;

	private static final Logger LOG = Logger.getLogger(LoginBean.class);

	@PostConstruct
	public void init() {
		LOG.info("LoginBean.init()");
		username = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		User currentUser = hpServices.getCurrentUser(username);
		if (currentUser != null) {
			lastLgoin = currentUser.getLastLogin();
		} else
			lastLgoin = 0;
		LOG.info("LoginBean.login() Username :" + username + "\t currentUser"
				+ currentUser);

		username = currentUser.getFirstname();
		/* username.substring(0, username.indexOf(".")); */
		LOG.info("LoginBean.login() Username :" + username + "\t currentUser"
				+ currentUser);

	}

	public void login(ActionEvent event) {
		LOG.info("LoginBean.login() Username :" + username + "\t Password"
				+ password);
	}

	public String logout() {
		LOG.info("LoginBean.logout() : you have logout successfully");
		ServletContext context = (ServletContext) FacesContext
				.getCurrentInstance().getExternalContext().getContext();
		String path = context.getContextPath();
		LOG.info("path is :" + path);
		return "/" + path + "/j_spring_security_logout";
	}

	public void changePassword() {
		String mail_id = SecurityContextHolder.getContext().getAuthentication()
				.getName();

		LOG.info("changePassword()");
		LOG.info("mailid------" + mail_id + "--------------password" + password);
		String results = changePasswordService
				.changePassword(mail_id, password);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage("changepasswordresult", new FacesMessage(results));
	}

	// Setters and Getters
	public String getUsername() {
		return username.substring(0, 1).toUpperCase() + username.substring(1);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ChangePasswordService getChangePasswordService() {
		return changePasswordService;
	}

	public void setChangePasswordService(
			ChangePasswordService changePasswordService) {
		this.changePasswordService = changePasswordService;
	}

	public String getLastLgoin() {
		DateFormat format = new SimpleDateFormat("MMM dd yyyy hh:mm a");
		format.setTimeZone(TimeZone.getTimeZone("Brazil/DeNoronha"));

		if (lastLgoin > 0) {
			String lldate = format
					.format(new Date(lastLgoin - (1000 * 60 * 60)));
			LOG.info("LastLoin Date is :" + lldate);
			return lldate;
		}

		return "No Data";
		// return lastLgoin;
	}

	public void setLastLgoin(long lastLgoin) {
		this.lastLgoin = lastLgoin;
	}

	public HpServices getHpServices() {
		return hpServices;
	}

	public void setHpServices(HpServices hpServices) {
		this.hpServices = hpServices;
	}

}
