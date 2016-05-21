package com.xiaotuitui.capricornus.interfaces.user.management;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaotuitui.framework.interfaces.BaseCtrl;

@Controller
@RequestMapping(value = "/user/management")
public class UserManagementCtrl extends BaseCtrl{
	
	@RequestMapping(value = "/toUserManagement", method = RequestMethod.GET)
	public String toUserManagement(){
		return "/user/management/management";
	}
	
	@RequestMapping(value = "/toUser", method = RequestMethod.GET)
	public String toUser(){
		return "/user/management/user";
	}

}