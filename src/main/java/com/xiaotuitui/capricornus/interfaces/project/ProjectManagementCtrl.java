package com.xiaotuitui.capricornus.interfaces.project;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaotuitui.framework.interfaces.BaseCtrl;

@Controller
@RequestMapping(value = "/project/project")
public class ProjectManagementCtrl extends BaseCtrl{
	
	@RequestMapping(value = "/toProjectManagement", method = RequestMethod.GET)
	public String toProjectManagement(){
		return "/project/management/management";
	}
	
	@RequestMapping(value = "/toProject", method = RequestMethod.GET)
	public String toProject(){
		return "/project/project/project";
	}

}