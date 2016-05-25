package com.xiaotuitui.capricornus.interfaces.user.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaotuitui.capricornus.application.GroupSrv;
import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.framework.interfaces.BaseCtrl;

@Controller
@RequestMapping(value = "/user/management")
public class UserManagementCtrl extends BaseCtrl{
	
	@Autowired
	private GroupSrv groupSrv;
	
	@RequestMapping(value = "/toUserManagement", method = RequestMethod.GET)
	public String toUserManagement(){
		return "/user/management/management";
	}
	
	@RequestMapping(value = "/toUser", method = RequestMethod.GET)
	public String toUser(HttpServletRequest request){
		List<Group> groupList = groupSrv.queryAllGroup();
		request.setAttribute("groupList", groupList);
		return "/user/management/user";
	}

}