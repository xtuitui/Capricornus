package com.xiaotuitui.capricornus.domain.repository;

import java.util.List;

import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.util.dto.UserDto;
import com.xiaotuitui.framework.util.page.PageObject;

public interface UserRep {
	
	public List<User> queryAllUser();
	
	public User queryUserByUsername(String username);
	
	public void deleteUser(User user);
	
	public List<User> queryUserByNickname(String nickname);
	
	public User createUser(User user);

	public List<User> queryUserByPage(UserDto userDto, PageObject pageObject);

	public User loadUser(Integer id);

}