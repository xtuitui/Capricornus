package com.xiaotuitui.capricornus.util.dto;

public class UserDto {
	
	private String username;
	
	private String nickname;
	
	private String email;
	
	private Integer groupId;

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String toString() {
		return "UserDto [username=" + username + ", nickname=" + nickname
				+ ", email=" + email + ", groupId=" + groupId + "]";
	}

}