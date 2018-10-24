package com.techouts.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
@Document(collection="User")
@TypeAlias("User")
public class User {
	
	@Id
	private String Id;

	@Indexed
	@Field("username")
	private String username;
	@Field("password")
	private String password;
	@Field("roles")
	private String roles;
	@Field("isActive")
	private boolean isActive;
	private boolean revokeAccess;
	
	



	private String firstname;
	private String lastname;
	private String  designation;	
	private String  organization;
	
	@Field("managersname")
	private String  managers_name;
	
	@Field("managershpname")
	private String  managers_hp_email;
	
	@Field("businessjustificationforaccessrequest")
	private String  business_justification_for_access_request;
	
	private String empID;
	
	private boolean isForgot;
	//where is tha path
	
	private long lastLogin;
	private long currentLogin;
	private boolean resetPassword;
	
	
	


	public boolean isResetPassword() {
		return resetPassword;
	}




	public void setResetPassword(boolean resetPassword) {
		this.resetPassword = resetPassword;
	}




	@Override
	public String toString() {
		return "User [Id=" + Id + ", username=" + username + ", password=" + password + ", roles=" + roles
				+ ", isActive=" + isActive + ", firstname=" + firstname + ", lastname=" + lastname + ", designation="
				+ designation + ", organization=" + organization + ", managers_name=" + managers_name
				+ ", managers_hp_email=" + managers_hp_email + ", business_justification_for_access_request="
				+ business_justification_for_access_request + ", empID=" + empID + ", isForgot=" + isForgot
				+ ", lastLogin=" + lastLogin + ", currentLogin=" + currentLogin + "]";
	}
	
	
	
	
	// Setters and Getters
	public boolean isForgot() {
		return isForgot;
	}
	public void setForgot(boolean isForgot) {
		this.isForgot = isForgot;
	}
	public String getEmpID() {
		return empID;
	}
	public void setEmpID(String empID) {
		this.empID = empID;
	}
	public String getUsername() {
		return username;
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
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public String getOrganization() {
		return organization;
	}
	public void setOrganization(String organization) {
		this.organization = organization;
	}
	public String getManagers_name() {
		return managers_name;
	}
	public void setManagers_name(String managers_name) {
		this.managers_name = managers_name;
	}
	public String getManagers_hp_email() {
		return managers_hp_email;
	}
	
	public void setManagers_hp_email(String managers_hp_email) {
		this.managers_hp_email = managers_hp_email;
	}
	public String getBusiness_justification_for_access_request() {
		return business_justification_for_access_request;
	}
	public void setBusiness_justification_for_access_request(
			String business_justification_for_access_request) {
		this.business_justification_for_access_request = business_justification_for_access_request;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}




	public long getLastLogin() {
	    return lastLogin;
	}




	public void setLastLogin(long lastLogin) {
	    this.lastLogin = lastLogin;
	}




	public long getCurrentLogin() {
	    return currentLogin;
	}




	public void setCurrentLogin(long currentLogin) {
	    this.currentLogin = currentLogin;
	}
	public boolean isRevokeAccess() {
		return revokeAccess;
	}




	public void setRevokeAccess(boolean revokeAccess) {
		this.revokeAccess = revokeAccess;
	}

	
	
	
	
	
	
	
}
