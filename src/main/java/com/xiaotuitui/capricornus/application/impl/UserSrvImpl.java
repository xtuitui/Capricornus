package com.xiaotuitui.capricornus.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiaotuitui.capricornus.application.UserSrv;
import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.domain.repository.UserRep;

@Service
public class UserSrvImpl implements UserSrv{
	
	@Autowired
	private UserRep userRep;

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

}