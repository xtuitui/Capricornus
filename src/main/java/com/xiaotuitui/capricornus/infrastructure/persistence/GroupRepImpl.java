package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.domain.repository.GroupRep;
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

}