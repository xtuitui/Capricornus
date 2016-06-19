package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.domain.repository.UserRep;
import com.xiaotuitui.capricornus.util.dto.UserDto;
import com.xiaotuitui.framework.domain.model.PageObject;
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

	public List<User> queryUserByPage(UserDto userDto, PageObject pageObject) {
		SqlParameters sqlParameters = buildSqlParameters(userDto);
		return super.queryByPage(sqlParameters, pageObject);
	}

	private SqlParameters buildSqlParameters(UserDto userDto) {
		StringBuilder sqlStringBuilder = new StringBuilder();
		sqlStringBuilder.append("select distinct u from User u");
		Integer groupId = userDto.getGroupId();
		if(groupId!=null){
			sqlStringBuilder.append(" join u.groups g");
		}
		sqlStringBuilder.append(" where 1=1");
		Map<String, Object> parameters = new HashMap<String, Object>();
		String username = userDto.getUsername();
		String nickname = userDto.getNickname();
		String email = userDto.getEmail();
		if(!StringUtils.isBlank(username)){
			sqlStringBuilder.append(" and u.username like :username");
			parameters.put("username", "%"+username+"%");
		}
		if(!StringUtils.isBlank(nickname)){
			sqlStringBuilder.append(" and u.nickname like :nickname");
			parameters.put("nickname", "%"+nickname+"%");
		}
		if(!StringUtils.isBlank(email)){
			sqlStringBuilder.append(" and u.email like :email");
			parameters.put("email", "%"+email+"%");
		}
		if(groupId!=null){
			sqlStringBuilder.append(" and g.id = :groupId");
			parameters.put("groupId", groupId);
		}
		return new SqlParameters(sqlStringBuilder, parameters);
	}

	public User loadUser(Integer id) {
		return super.getReference(id);
	}

	public void removeUser(User user) {
		super.remove(user);
	}

}