package com.ts.permissions.model;

import java.io.Serializable;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "customer")
@Access(value = AccessType.FIELD)
public class UserModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GenericGenerator(name = "kaugen", strategy = "increment")
	@GeneratedValue(generator = "kaugen")
	@Column(name = "ID", unique = true, nullable = false)
	private int id;
	@Column(name = "first_name", nullable = false)
	transient private String firstName;
	@Column(name = "last_name", nullable = false)
	private String lastName;
	@Column(name = "ex_police_id", nullable = false)
	private String exPoliceId;
	@Column(name = "email", unique = true)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
	@Column(name = "mobile", nullable = false)
	private String mobile;
	@Column(name = "state", nullable = false)
	private String state;
	@Column(name = "city", nullable = false)
	private String city;
	@Column(name = "area", nullable = false)
	private String area;
	@Column(name = "user_img", nullable = false)
	private String userImg;
	@Column(name = "user_type", nullable = false)
	private String userType;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getExPoliceId() {
		return exPoliceId;
	}

	public void setExPoliceId(String exPoliceId) {
		this.exPoliceId = exPoliceId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
