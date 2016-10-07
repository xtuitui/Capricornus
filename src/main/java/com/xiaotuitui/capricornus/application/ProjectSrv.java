package com.xiaotuitui.capricornus.application;

import java.util.List;

import com.xiaotuitui.capricornus.domain.model.Project;

public interface ProjectSrv {

	public Project queryProjectByName(String name);
	
	public Project queryProjectByKey(String projectKey);

	public Project createProject(Project project);

	public void removeProject(Integer projectId);

	public void updateProject(Project project);

	public List<Project> queryNoneCategoryProject();

}