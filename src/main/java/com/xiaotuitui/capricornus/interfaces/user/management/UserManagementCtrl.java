package com.xiaotuitui.capricornus.interfaces.user.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.xiaotuitui.capricornus.application.GroupSrv;
import com.xiaotuitui.capricornus.application.UserSrv;
import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.util.dto.UserDto;
import com.xiaotuitui.framework.interfaces.BaseCtrl;
import com.xiaotuitui.framework.util.page.PageObject;

@Controller
@RequestMapping(value = "/user/management")
public class UserManagementCtrl extends BaseCtrl{
	
	@Autowired
	private UserSrv userSrv;
	
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
	
	@RequestMapping(value = "/queryUserByPage", method = RequestMethod.POST)
	public String queryUserByPage(HttpServletRequest request, UserDto userDto, PageObject pageObject){
		List<User> userList = userSrv.queryUserByPage(userDto, pageObject);
		request.setAttribute("userList", userList);
		request.setAttribute("pageObject", pageObject);
		return "/user/management/userTable";
	}

}