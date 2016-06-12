package com.xiaotuitui.capricornus.interfaces.system;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/system/permission")
public class SystemPermissionCtrl {
	
	@RequestMapping(value = "/toSystemPermission", method = RequestMethod.GET)
	public String toUser(HttpServletRequest request){
		return "/system/permission/permission";
	}

}