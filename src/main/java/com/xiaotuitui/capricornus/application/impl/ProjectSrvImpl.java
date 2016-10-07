package com.xiaotuitui.capricornus.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiaotuitui.capricornus.application.ProjectSrv;
import com.xiaotuitui.capricornus.domain.model.Project;
import com.xiaotuitui.capricornus.domain.repository.ProjectRep;

@Service
public class ProjectSrvImpl implements ProjectSrv{
	
	@Autowired
	private ProjectRep projectRep;
	
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Project queryProjectByName(String name) {
		return projectRep.queryProjectByName(name);
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Project queryProjectByKey(String projectKey) {
		return projectRep.queryProjectByKey(projectKey);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Project createProject(Project project) {
		return projectRep.createProject(project);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeProject(Integer projectId) {
		Project project = projectRep.loadProject(projectId);
		projectRep.removeProject(project);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateProject(Project project) {
		Project originalProject = projectRep.loadProject(project.getId());
		originalProject.setName(project.getName());
		originalProject.setProjectKey(project.getProjectKey());
		originalProject.setCategory(project.getCategory());
		originalProject.setDescription(project.getDescription());
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Project> queryNoneCategoryProject() {
		return projectRep.queryNoneCategoryProject();
	}

}