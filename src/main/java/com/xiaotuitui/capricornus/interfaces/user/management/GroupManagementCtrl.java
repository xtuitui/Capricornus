package com.xiaotuitui.capricornus.interfaces.user.management;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiaotuitui.capricornus.application.GroupSrv;
import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.util.constant.MessageCode;
import com.xiaotuitui.framework.interfaces.BaseCtrl;
import com.xiaotuitui.framework.util.page.PageObject;

@Controller
@RequestMapping(value = "/user/management")
public class GroupManagementCtrl extends BaseCtrl{
	
	@Autowired
	private GroupSrv groupSrv;
	
	@RequestMapping(value = "/toGroup", method = RequestMethod.GET)
	public String toUser(){
		return "/user/management/group";
	}
	
	@RequestMapping(value = "/queryGroupByPage", method = RequestMethod.POST)
	public String queryUserByPage(HttpServletRequest request, @RequestParam(value = "groupName") String groupName, PageObject pageObject){
		List<Group> groupList = groupSrv.queryGroupByPage(groupName, pageObject);
		request.setAttribute("groupList", groupList);
		request.setAttribute("pageObject", pageObject);
		return "/user/management/groupTable";
	}
	
	@RequestMapping(value = "/addGroup", method = RequestMethod.POST)
	public void addGroup(HttpServletRequest request, HttpServletResponse response, Group group){
		Group checkGroup = groupSrv.queryGroupByName(group.getName());
		if(checkGroup!=null){
			ajaxErrorData(request, response, MessageCode.GROUP_ALREADY_EXIST);
		}else{
			groupSrv.createGroup(group);
			ajaxSuccess(request, response);
		}
	}

}