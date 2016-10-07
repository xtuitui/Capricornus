package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.xiaotuitui.capricornus.domain.model.Project;
import com.xiaotuitui.capricornus.domain.repository.ProjectRep;
import com.xiaotuitui.framework.domain.model.SqlParameters;
import com.xiaotuitui.framework.infrastructure.persistence.JPABaseRepImpl;

@Repository
public class ProjectRepImpl extends JPABaseRepImpl<Project> implements ProjectRep{

	@PersistenceContext
	private EntityManager entityManager;
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}
	
	public Project queryProjectByName(String name) {
		SqlParameters sqlParameters = new SqlParameters("Project.queryProjectByName", new String[]{"name"}, new Object[]{name});
		return super.namedQueryFirstResult(sqlParameters);
	}

	public Project queryProjectByKey(String projectKey) {
		SqlParameters sqlParameters = new SqlParameters("Project.queryProjectByKey", new String[]{"projectKey"}, new Object[]{projectKey});
		return super.namedQueryFirstResult(sqlParameters);
	}

	public Project createProject(Project project) {
		return super.create(project);
	}

	public Project loadProject(Integer projectId) {
		return super.getReference(projectId);
	}

	public void removeProject(Project project) {
		super.remove(project);
	}

	public List<Project> queryNoneCategoryProject() {
		return super.namedQuery("Project.queryNoneCategoryProject");
	}

}