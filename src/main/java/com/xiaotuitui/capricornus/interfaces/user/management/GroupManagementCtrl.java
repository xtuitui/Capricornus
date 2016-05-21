package com.xiaotuitui.capricornus.interfaces.user.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaotuitui.framework.interfaces.BaseCtrl;

@Controller
@RequestMapping(value = "/groupManagement")
public class GroupManagementCtrl extends BaseCtrl{
	
	@RequestMapping(value = "/toGroup", method = RequestMethod.GET)
	public String toUser(){
		return "/user/management/group";
	}

}