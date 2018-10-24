package com.techouts.beans;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

import com.techouts.pojo.PasswordReset;
import com.techouts.pojo.User;
import com.techouts.services.ForgetPasswordService;
import com.techouts.services.UserInfoService;
import com.techouts.util.ShaAlgorithm;

@ManagedBean
@ViewScoped
public class UserInfoBean {

	private static final Logger LOGGER = Logger.getLogger(UserInfoBean.class);
	@ManagedProperty("#{userInfoService}")
	private UserInfoService userInfoService;

	@ManagedProperty("#{forgetPasswordService}")
	private ForgetPasswordService forgetPasswordService;

	private User user;
	private User userdata;

	private boolean changepassword;

	private String message;

	private PasswordReset passwordReset;
	private List<User> allUsers = null;
	private List<User> newUsersRequestlist;
	private List<User> filteredNewUserslist;
	private List<User> filteredexistingUserslist;
	private List<User> existingUserslist;
	private String selectDesicion;
	private int existinguserCount;
	private int newUserCount;

	@PostConstruct
	public void init() {
		existinguserCount = 0;
		newUserCount = 0;
		// changepassword=false;
		message = null;
		passwordReset = new PasswordReset();
		passwordReset.setPassword(null);
		String username = SecurityContextHolder.getContext()
				.getAuthentication().getName();
		userdata = userInfoService.getUserData(username);
		user = new User();
		user.setFirstname(userdata.getFirstname().substring(0, 1).toUpperCase()
				+ userdata.getFirstname().substring(1) + "" + " "
				+ userdata.getLastname().substring(0, 1).toUpperCase()
				+ userdata.getLastname().substring(1));
		user.setEmpID(username);
		user.setDesignation(userdata.getDesignation());

		if (userdata.getRoles().contains("ROLE_ADMIN")) {
			user.setRoles("Administrator");

			allUsers = userInfoService.getAllUsers();
			existingUserslist = new ArrayList<User>();
			newUsersRequestlist = new ArrayList<User>();
			for (User extractUser : allUsers) {
				if (extractUser.isActive()) {
					existinguserCount++;
					String fname = extractUser.getFirstname().substring(0, 1)
							.toUpperCase()
							+ extractUser.getFirstname().substring(1)
							+ " "
							+ extractUser.getLastname().substring(0, 1)
									.toUpperCase()
							+ extractUser.getLastname().substring(1);
					extractUser.setFirstname(fname);
					existingUserslist.add(extractUser);
				} else {
					newUserCount++;
					String fname = extractUser.getFirstname().substring(0, 1)
							.toUpperCase()
							+ extractUser.getFirstname().substring(1)
							+ " "
							+ extractUser.getLastname().substring(0, 1)
									.toUpperCase()
							+ extractUser.getLastname().substring(1);
					extractUser.setFirstname(fname);
					newUsersRequestlist.add(extractUser);
				}
			}

		} else {
			user.setRoles("Regular");
		}

	}

	public void changePwd(ActionEvent event) {
		changepassword = true;
	}

	public String conform() {
		if (!passwordReset.getConfpassword().equals(
				passwordReset.getReconfpassword())) {
			addMessage("Password missmatch");
			return null;
		}
		LOGGER.info("Checking" + passwordReset.getPassword()
				+ passwordReset.getConfpassword());
		LOGGER.info(ShaAlgorithm.encriptData(passwordReset.getPassword())
				+ "==" + userdata.getPassword());
		if (ShaAlgorithm.encriptData(passwordReset.getPassword()).equals(
				userdata.getPassword())) {
			userdata.setPassword(passwordReset.getConfpassword());
			LOGGER.info("in if" + userdata.getPassword());
			forgetPasswordService.changePassword(userdata);
			addMessage("Password Updated Successfully...");
			message = "Your password has been changed. Please login with new password";
			FacesContext.getCurrentInstance().getExternalContext()
					.invalidateSession();

			HttpServletRequest request = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();
			try {
				request.logout();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return "newhome.xhtml?faces-redirect=true";

		} else {
			addMessage("You have entered Wrong Password, Please Retry again...");

			return null;
		}
	}

	public void saveNewUser(User newUser) {

		LOGGER.info("new user is" + newUser.isActive());
		if (newUser.isActive()) {
			userInfoService.saveNewUser(newUser);
			addMessage("User updated successfully");
		} else {
			userInfoService.removeNewUser(newUser);
			addMessage("User removed successfully");
		}
		init();
	}

	public void saveAllNewUsers() {

		LOGGER.info("saveAllNewUsers");
		List<User> newUsers = getNewUsersRequestlist();
		if (!newUsers.isEmpty()) {
			for (User user : newUsers) {
				userInfoService.saveNewUser(user);
			}
			addMessage("new requests are updated successfully");
			init();
		} else {
			addMessage("new requests are not found");
		}
	}

	public void denyAllNewUsers() {
		LOGGER.info("denyAllNewUsers");

		List<User> newUsers = getNewUsersRequestlist();

		if (!newUsers.isEmpty()) {
			for (User user : newUsers) {
				userInfoService.removeNewUser(user);
			}
			addMessage("deleted successfully");
			init();
		} else {
			addMessage(" new requests are not found");
		}
	}

	public void saveExistingUser(User saveUser) {
		LOGGER.info("========saveExistingUser============");

		if (saveUser.isRevokeAccess()) {
			LOGGER.info("revoke access " + saveUser.isRevokeAccess());
			if (saveUser.isResetPassword()) {
				LOGGER.info("resetPasword access " + saveUser.isResetPassword());
				addMessage("revoke access Should not allowed while reset Password");
			} else {
				LOGGER.info("delete user");

				userInfoService.removeExistingUser(saveUser);
				addMessage("User Revoked Successfully");
				init();
			}
		} else {
			if (saveUser.isResetPassword()) {
				forgetPasswordService.resetPassword(saveUser);
				addMessage("Password updated");
			}

		}
	}

	public void saveAllExistingUser(ActionEvent event) {
		LOGGER.info("============saveAllExistingUser================");

		List<User> existuser = getExistingUserslist();
		for (User user : existuser) {
			if (user.isRevokeAccess()) {
				if (user.isResetPassword()) {
					addMessage("revoke access Should not be ALLOW while reset Password");
				} else {
					userInfoService.removeExistingUser(user);
					addMessage("deleted" + user.getUsername());
					init();
				}
			} else {

				if (user.isResetPassword()) {
					LOGGER.info("updated");
					forgetPasswordService.resetPassword(user);
					addMessage("password Updated Successfully"
							+ user.getUsername());
				}
			}
		}
	}

	public String controlPage() {

		passwordReset.setPassword(null);

		if (userdata.getRoles().contains("ROLE_ADMIN")) {
			return "AdminControl.xhtml?faces-redirect=true";
		} else {
			user.setRoles("Regular");
			if(userdata.getRoles().contains("ROLE_SCM")){
				return "DisUserInfo.xhtml?faces-redirect=true";
			}else{
				return "UserInfo.xhtml?faces-redirect=true";
			}
		}
	}

	public String mobileControlPage() {

		if (userdata.getRoles().contains("ROLE_ADMIN")) {
			return "AdminControlMob.xhtml?faces-redirect=true";
		} else {
			user.setRoles("Regular");
			return "UserInformation.xhtml?faces-redirect=true";
		}
	}

	public void saveNewUserForMob(User mobileUser) {

		LOGGER.info("new user is" + mobileUser.isActive());

		userInfoService.saveNewUser(mobileUser);
		addMessage("User access Updated");
		init();

	}

	public void removeNewUserForMob(User mobileUser) {
		LOGGER.info("new user is" + mobileUser.isActive());

		userInfoService.removeNewUser(mobileUser);

		addMessage("User removed");
		init();

	}

	public void revokeAccessForMob(User mobileUser) {

		LOGGER.info("revokeAccessForMob  ::   " + mobileUser.getUsername());
		userInfoService.removeNewUser(mobileUser);
		addMessage("User removed");
		init();
	}

	public void resetPwdForMob(User mobileUser) {

		LOGGER.info("resetPwdForMob  ::   " + mobileUser.getUsername());

		forgetPasswordService.resetPassword(mobileUser);
		addMessage("Password reset Successfully");

	}

	public void addMessage(String summary) {
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage("", summary));

	}

	public UserInfoService getUserInfoService() {
		return userInfoService;
	}

	public void setUserInfoService(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isChangepassword() {
		return changepassword;
	}

	public void setChangepassword(boolean changepassword) {
		this.changepassword = changepassword;
	}

	public ForgetPasswordService getForgetPasswordService() {
		return forgetPasswordService;
	}

	public void setForgetPasswordService(
			ForgetPasswordService forgetPasswordService) {
		this.forgetPasswordService = forgetPasswordService;
	}

	public PasswordReset getPasswordReset() {
		return passwordReset;
	}

	public void setPasswordReset(PasswordReset passwordReset) {
		this.passwordReset = passwordReset;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<User> getFilteredNewUserslist() {
		return filteredNewUserslist;
	}

	public void setFilteredNewUserslist(List<User> filteredNewUserslist) {
		this.filteredNewUserslist = filteredNewUserslist;
	}

	public List<User> getFilteredexistingUserslist() {
		return filteredexistingUserslist;
	}

	public void setFilteredexistingUserslist(
			List<User> filteredexistingUserslist) {
		this.filteredexistingUserslist = filteredexistingUserslist;
	}

	public User getUserdata() {
		return userdata;
	}

	public void setUserdata(User userdata) {
		this.userdata = userdata;
	}

	public List<User> getNewUsersRequestlist() {
		return newUsersRequestlist;
	}

	public void setNewUsersRequestlist(List<User> newUsersRequestlist) {
		this.newUsersRequestlist = newUsersRequestlist;
	}

	public List<User> getExistingUserslist() {
		return existingUserslist;
	}

	public void setExistingUserslist(List<User> existingUserslist) {
		this.existingUserslist = existingUserslist;
	}

	public String getSelectDesicion() {
		return selectDesicion;
	}

	public void setSelectDesicion(String selectDesicion) {
		this.selectDesicion = selectDesicion;
	}

	public int getExistinguserCount() {
		return existinguserCount;
	}

	public void setExistinguserCount(int existinguserCount) {
		this.existinguserCount = existinguserCount;
	}

	public int getNewUserCount() {
		return newUserCount;
	}

	public void setNewUserCount(int newUserCount) {
		this.newUserCount = newUserCount;
	}

}
