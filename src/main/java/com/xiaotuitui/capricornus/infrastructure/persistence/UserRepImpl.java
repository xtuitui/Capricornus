package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.domain.repository.UserRep;
import com.xiaotuitui.framework.domain.model.SqlParameters;
import com.xiaotuitui.framework.infrastructure.persistence.JPABaseRepImpl;

@Repository
public class UserRepImpl extends JPABaseRepImpl<User> implements UserRep{

	@PersistenceContext
	private EntityManager entityManager;
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public List<User> queryAllUser() {
		return super.namedQuery("User.queryAllUser");
	}

	public User queryUserByUsername(String username) {
		StringBuilder name = new StringBuilder("User.queryUserByUsername");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);
		SqlParameters sqlParameters = new SqlParameters(name, parameters);
		return super.namedQueryFirstResult(sqlParameters);
	}

	public void deleteUser(User user) {
		super.remove(user);
	}

	public List<User> queryUserByNickname(String nickname) {
		StringBuilder name = new StringBuilder("User.queryUserByNickname");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nickname", "%"+nickname+"%");
		SqlParameters sqlParameters = new SqlParameters(name, parameters);
		return super.namedQuery(sqlParameters);
	}

	public User createUser(User user) {
		return super.create(user);
	}

}