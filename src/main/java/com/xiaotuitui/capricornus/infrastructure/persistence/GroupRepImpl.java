package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.domain.repository.GroupRep;
import com.xiaotuitui.framework.domain.model.PageObject;
import com.xiaotuitui.framework.domain.model.SqlParameters;
import com.xiaotuitui.framework.infrastructure.persistence.JPABaseRepImpl;

@Repository
public class GroupRepImpl extends JPABaseRepImpl<Group> implements GroupRep{

	@PersistenceContext
	private EntityManager entityManager;
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public List<Group> queryAllGroup() {
		return super.namedQuery("Group.queryAllGroup");
	}

	public List<Group> queryGroupByPage(String groupName, PageObject pageObject) {
		SqlParameters sqlParameters = buildSqlParameters(groupName);
		return super.queryByPage(sqlParameters, pageObject);
	}

	private SqlParameters buildSqlParameters(String groupName) {
		StringBuilder sqlStringBuilder = new StringBuilder();
		Map<String, Object> parameters = new HashMap<String, Object>();
		sqlStringBuilder.append("from Group g");
		if(!StringUtils.isBlank(groupName)){
			sqlStringBuilder.append(" where g.name like :name");
			parameters.put("name", "%"+groupName+"%");
		}
		sqlStringBuilder.append(" order by g.id");
		return new SqlParameters(sqlStringBuilder, parameters);
	}

	public Group queryGroupByName(String name) {
		SqlParameters sqlParameters = new SqlParameters("Group.queryGroupByName", new String[]{"name"}, new Object[]{name});
		return super.namedQueryFirstResult(sqlParameters);
	}

	public Group createGroup(Group group) {
		return super.create(group);
	}

	public Group loadGroup(Integer groupId) {
		return super.getReference(groupId);
	}

	public void removeGroup(Group group) {
		super.remove(group);
	}

	public Group findGroup(Integer id) {
		return super.find(id);
	}

}