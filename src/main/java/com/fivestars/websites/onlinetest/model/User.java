package com.fivestars.websites.onlinetest.model;
// Generated Jan 15, 2016 12:20:03 AM by Hibernate Tools 4.3.1.Final

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", catalog = "online_test")
public class User implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	private String email;
	private String phone;
	private String graduate;
	private String major;
	private String background;
	private Byte isAdmin;
	private Byte isTeacher;

	public User() {
	}

	public User(String userName) {
		this.userName = userName;
	}

	public User(String userName, String password, String email, String phone, String graduate, String major,
			String background, Byte isAdmin, Byte isTeacher) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.graduate = graduate;
		this.major = major;
		this.background = background;
		this.isAdmin = isAdmin;
		this.isTeacher = isTeacher;
	}

	@Id

	@Column(name = "userName", unique = true, nullable = false, length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email", length = 50)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "phone", length = 50)
	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "graduate", length = 50)
	public String getGraduate() {
		return this.graduate;
	}

	public void setGraduate(String graduate) {
		this.graduate = graduate;
	}

	@Column(name = "major", length = 50)
	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Column(name = "background", length = 65535)
	public String getBackground() {
		return this.background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	@Column(name = "isAdmin")
	public Byte getIsAdmin() {
		return this.isAdmin;
	}

	public void setIsAdmin(Byte isAdmin) {
		this.isAdmin = isAdmin;
	}

	@Column(name = "isTeacher")
	public Byte getIsTeacher() {
		return this.isTeacher;
	}

	public void setIsTeacher(Byte isTeacher) {
		this.isTeacher = isTeacher;
	}

}