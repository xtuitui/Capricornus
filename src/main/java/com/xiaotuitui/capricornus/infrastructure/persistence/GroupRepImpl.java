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
import com.xiaotuitui.framework.domain.model.SqlParameters;
import com.xiaotuitui.framework.infrastructure.persistence.JPABaseRepImpl;
import com.xiaotuitui.framework.util.page.PageObject;

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

}