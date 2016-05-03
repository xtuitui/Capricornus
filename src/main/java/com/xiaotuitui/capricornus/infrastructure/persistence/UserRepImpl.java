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
		String sql = "from User";
		return super.query(sql);
	}

	public User queryUserByUsername(String username) {
		StringBuilder sql = new StringBuilder("from User u where u.username = :username");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("username", username);
		SqlParameters sqlParameters = new SqlParameters(sql, parameters);
		return super.queryFirstResult(sqlParameters);
	}

	public void deleteUser(User user) {
		super.remove(user);
	}

	public List<User> queryUserByNickname(String nickname) {
		StringBuilder sql = new StringBuilder("from User u where u.nickname like :nickname");
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("nickname", "%"+nickname+"%");
		SqlParameters sqlParameters = new SqlParameters(sql, parameters);
		return super.query(sqlParameters);
	}

	public User createUser(User user) {
		return super.create(user);
	}

}