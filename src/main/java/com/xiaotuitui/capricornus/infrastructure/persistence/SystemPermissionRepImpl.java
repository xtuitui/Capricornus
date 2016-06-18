package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.xiaotuitui.capricornus.domain.model.SystemPermission;
import com.xiaotuitui.capricornus.domain.repository.SystemPermissionRep;
import com.xiaotuitui.framework.infrastructure.persistence.JPABaseRepImpl;

@Repository
public class SystemPermissionRepImpl extends JPABaseRepImpl<SystemPermission> implements SystemPermissionRep{
	
	@PersistenceContext
	private EntityManager entityManager;

	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public List<SystemPermission> queryAllSystemPermission() {
		return super.namedQuery("SystemPermission.queryAllSystemPermission");
	}

	public SystemPermission loadSystemPermission(Integer id) {
		return super.getReference(id);
	}

}