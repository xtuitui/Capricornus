package com.xiaotuitui.capricornus.domain.repository;

import java.util.List;

import com.xiaotuitui.capricornus.domain.model.User;

public interface UserRep {
	
	public List<User> queryAllUser();
	
	public User queryUserByUsername(String username);
	
	public void deleteUser(User user);
	
	public List<User> queryUserByNickname(String nickname);
	
	public User createUser(User user);
	
}