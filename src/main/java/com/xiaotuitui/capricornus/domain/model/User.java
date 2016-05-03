package com.xiaotuitui.capricornus.domain.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tcs_user")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "username", nullable = false, unique = true, updatable = false)
	private String username;
	
	@Column(name = "nickname")
	private String nickname;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "login_success_times")
	private Integer loginSuccessTimes;
	
	@Column(name = "last_login_fail_times")
	private Integer lastLoginFailTimes;
	
	@Column(name = "last_updated_time", nullable = false)
	private Date lastUpdatedTime;
	
	public User() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getLoginSuccessTimes() {
		return loginSuccessTimes;
	}

	public void setLoginSuccessTimes(Integer loginSuccessTimes) {
		this.loginSuccessTimes = loginSuccessTimes;
	}

	public Integer getLastLoginFailTimes() {
		return lastLoginFailTimes;
	}

	public void setLastLoginFailTimes(Integer lastLoginFailTimes) {
		this.lastLoginFailTimes = lastLoginFailTimes;
	}

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

}