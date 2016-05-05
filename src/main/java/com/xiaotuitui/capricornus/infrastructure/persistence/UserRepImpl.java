package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.util.List;

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
		SqlParameters sqlParameters = new SqlParameters("User.queryUserByUsername", new String[]{"username"}, new Object[]{username});
		return super.namedQueryFirstResult(sqlParameters);
	}

	public void deleteUser(User user) {
		super.remove(user);
	}

	public List<User> queryUserByNickname(String nickname) {
		SqlParameters sqlParameters = new SqlParameters("User.queryUserByNickname", new String[]{"nickname"}, new Object[]{"%"+nickname+"%"});
		return super.namedQuery(sqlParameters);
	}

	public User createUser(User user) {
		return super.create(user);
	}

}