package com.xiaotuitui.capricornus.interfaces.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaotuitui.framework.interfaces.BaseCtrl;

@Controller
@RequestMapping(value = "/system/configuration")
public class SystemConfigurationCtrl extends BaseCtrl{
	
	@RequestMapping(value = "/toSystemConfiguration", method = RequestMethod.GET)
	public String toSystemConfiguration(){
		return "/system/management/management";
	}

}