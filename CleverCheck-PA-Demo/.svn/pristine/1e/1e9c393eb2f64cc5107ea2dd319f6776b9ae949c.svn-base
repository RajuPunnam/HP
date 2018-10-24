package com.techouts.beans;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.techouts.pojo.User;
import com.techouts.services.ForgetPasswordService;

@ManagedBean
public class ForgetPasswordBean {
	private static final Logger LOG = Logger
			.getLogger(ForgetPasswordBean.class);
	private String emailId;
	private String showMessage;

	@ManagedProperty("#{forgetPasswordService}")
	private ForgetPasswordService forgetPasswordService;

	@PostConstruct
	public void init() {
		showMessage = null;
	}

	public void addMessage(String summary) {
		// LOG.info(summary);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("", summary));

	}

	@SuppressWarnings("unused")
	public void recoveryPassword(ActionEvent event) {
		User user = forgetPasswordService.checkUserExist(emailId);

		LOG.info("is active" + user);
		if (user != null) {

			if (user.isActive()) {
				forgetPasswordService.resetPassword(user);

				showMessage = "Password has been sent to your email id";
				emailId = null;
				addMessage(showMessage);
			} else {
				addMessage("your are inactive please contact admin");
			}

		} else {
			addMessage("Email Id does not exist");
		}

	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public ForgetPasswordService getForgetPasswordService() {
		return forgetPasswordService;
	}

	public void setForgetPasswordService(
			ForgetPasswordService forgetPasswordService) {
		this.forgetPasswordService = forgetPasswordService;
	}

	public String getShowMessage() {
		return showMessage;
	}

	public void setShowMessage(String showMessage) {
		this.showMessage = showMessage;
	}

}
