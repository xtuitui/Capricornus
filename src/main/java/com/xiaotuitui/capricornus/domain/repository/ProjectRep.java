package com.xiaotuitui.capricornus.domain.repository;

import java.util.List;

import com.xiaotuitui.capricornus.domain.model.Project;

public interface ProjectRep {

	public Project queryProjectByName(String name);

	public Project queryProjectByKey(String projectKey);

	public Project createProject(Project project);

	public Project loadProject(Integer projectId);

	public void removeProject(Project project);

	public List<Project> queryNoneCategoryProject();
	

}