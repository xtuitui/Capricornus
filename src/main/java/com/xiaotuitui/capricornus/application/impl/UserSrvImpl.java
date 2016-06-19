package com.xiaotuitui.capricornus.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiaotuitui.capricornus.application.UserSrv;
import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.domain.repository.GroupRep;
import com.xiaotuitui.capricornus.domain.repository.UserRep;
import com.xiaotuitui.capricornus.util.dto.UserDto;
import com.xiaotuitui.framework.domain.model.PageObject;

@Service
public class UserSrvImpl implements UserSrv{
	
	@Autowired
	private UserRep userRep;
	
	@Autowired
	private GroupRep groupRep;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<User> queryAllUser() {
		return userRep.queryAllUser();
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public User queryUserByUsername(String username) {
		return userRep.queryUserByUsername(username);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void deleteUser(User user) {
		User originalUser = userRep.queryUserByUsername(user.getUsername());
		if(originalUser!=null){
			userRep.deleteUser(originalUser);
		}
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<User> queryUserByNickname(String nickname) {
		return userRep.queryUserByNickname(nickname);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public User createUser(User user) {
		return userRep.createUser(user);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<User> queryUserByPage(UserDto userDto, PageObject pageObject) {
		return userRep.queryUserByPage(userDto, pageObject);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateUserGroup(Integer id, List<Integer> groupIdList) {
		User user = userRep.loadUser(id);
		user.getGroups().clear();
		for(Integer groupId:groupIdList){
			user.getGroups().add(groupRep.loadGroup(groupId));
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeUser(Integer userId) {
		User user = userRep.loadUser(userId);
		user.getGroups().clear();
		userRep.removeUser(user);
	}

}