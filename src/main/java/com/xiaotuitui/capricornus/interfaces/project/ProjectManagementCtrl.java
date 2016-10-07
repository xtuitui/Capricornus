package com.xiaotuitui.capricornus.interfaces.project;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiaotuitui.capricornus.application.CategorySrv;
import com.xiaotuitui.capricornus.application.ProjectSrv;
import com.xiaotuitui.capricornus.domain.model.Category;
import com.xiaotuitui.capricornus.domain.model.Project;
import com.xiaotuitui.capricornus.util.constant.MessageCode;
import com.xiaotuitui.framework.interfaces.BaseCtrl;

@Controller
@RequestMapping(value = "/project/project")
public class ProjectManagementCtrl extends BaseCtrl{
	
	@Autowired
	private ProjectSrv projectSrv;
	
	@Autowired
	private CategorySrv categorySrv;
	
	@RequestMapping(value = "/toProjectManagement", method = RequestMethod.GET)
	public String toProjectManagement(){
		return "/project/management/management";
	}
	
	@RequestMapping(value = "/toProject", method = RequestMethod.GET)
	public String toProject(HttpServletRequest request){
		List<Category> categoryList = categorySrv.queryAllCategory();
		List<Project> noneCategoryProjectList = projectSrv.queryNoneCategoryProject();
		request.setAttribute("categoryList", categoryList);
		request.setAttribute("noneCategoryProjectList", noneCategoryProjectList);
		return "/project/project/project";
	}
	
	@RequestMapping(value = "/addProject", method = RequestMethod.POST)
	public void addProject(HttpServletResponse response, Project project){
		convertCategory(project);
		if(validateProject(response, project)){
			projectSrv.createProject(project);
			ajaxSuccess(response);
		}
	}
	
	@RequestMapping(value = "/deleteProject", method = RequestMethod.POST)
	public void deleteProject(HttpServletResponse response, @RequestParam(value = "projectId") Integer projectId){
		projectSrv.removeProject(projectId);
		ajaxSuccess(response);
	}
	
	@RequestMapping(value = "/updateProject", method = RequestMethod.POST)
	public void updateProject(HttpServletResponse response, Project project){
		convertCategory(project);
		if(validateProject(response, project)){
			projectSrv.updateProject(project);
			ajaxSuccess(response);
		}
	}

	private void convertCategory(Project project) {
		if(project.getCategory().getId() == null){
			project.setCategory(null);
		}
	}

	private boolean validateProject(HttpServletResponse response, Project project){
		Project checkProjectName = projectSrv.queryProjectByName(project.getName());
		if(checkProjectName!=null && ! checkProjectName.getId().equals(project.getId())){
			ajaxErrorData(response, MessageCode.PROJECT_ALREADY_EXIST);
			return false;
		}
		Project checkProjectKey = projectSrv.queryProjectByKey(project.getProjectKey());
		if(checkProjectKey!=null && ! checkProjectKey.getId().equals(project.getId())){
			ajaxErrorData(response, MessageCode.PROJECT_KEY_ALREADY_EXIST);
			return false;
		}
		return true;
	}

}