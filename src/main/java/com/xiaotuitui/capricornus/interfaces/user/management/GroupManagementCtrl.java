package com.xiaotuitui.capricornus.interfaces.user.management;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.xiaotuitui.capricornus.application.GroupSrv;
import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.domain.model.User;
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
	
	@RequestMapping(value = "/deleteGroup", method = RequestMethod.POST)
	public void deleteGroup(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "groupId") Integer groupId){
		groupSrv.removeGroup(groupId);
		ajaxSuccess(request, response);
	}
	
	@RequestMapping(value = "/updateGroup", method = RequestMethod.POST)
	public void updateGroup(HttpServletRequest request, HttpServletResponse response, Group group){
		Group checkGroup = groupSrv.queryGroupByName(group.getName());
		if(checkGroup != null && ! checkGroup.getId().equals(group.getId())){
			ajaxErrorData(request, response, MessageCode.GROUP_ALREADY_EXIST);
		}else{
			groupSrv.updateGroup(group);
			ajaxSuccess(request, response);
		}
	}
	
	@RequestMapping(value = "/queryUserByGroup", method = RequestMethod.POST)
	public void searchUserByGroup(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") Integer id){
		List<User> userList = groupSrv.queryUserByGroup(id);
		ajaxSuccessData(request, response, userList);
	}
	
	@RequestMapping(value = "/updateGroupUser", method = RequestMethod.POST)
	public void updateGroupUser(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") Integer id, @RequestParam(value = "userIdStringList") String userIdStringList){
		List<Integer> userIdList = convertStringArrayToList(StringUtils.split(userIdStringList, ","));
		groupSrv.updateGroupUser(id, userIdList);
		ajaxSuccess(request, response);
	}

	private List<Integer> convertStringArrayToList(String[] userIdArray) {
		List<Integer> userIdList = new ArrayList<Integer>();
		for(String userIdString:userIdArray){
			userIdList.add(Integer.valueOf(userIdString));
		}
		return userIdList;
	}

}