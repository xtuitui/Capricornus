package com.xiaotuitui.capricornus.interfaces.system;

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
import com.xiaotuitui.capricornus.application.SystemPermissionSrv;
import com.xiaotuitui.framework.interfaces.BaseCtrl;
import com.xiaotuitui.framework.util.list.ListUtil;

@Controller
@RequestMapping(value = "/system/permission")
public class SystemPermissionCtrl extends BaseCtrl{
	
	@Autowired
	private SystemPermissionSrv systemPermissionSrv;
	
	@Autowired
	private GroupSrv groupSrv;
	
	@RequestMapping(value = "/toSystemPermission", method = RequestMethod.GET)
	public String toUser(HttpServletRequest request){
		request.setAttribute("systemPermissionList", systemPermissionSrv.queryAllSystemPermission());
		request.setAttribute("groupList", groupSrv.queryAllGroup());
		return "/system/permission/permission";
	}

	@RequestMapping(value = "/updateSystemPermissionGroup", method = RequestMethod.POST)
	public void updateSystemPermissionGroup(HttpServletResponse response, @RequestParam(value = "id") Integer id, @RequestParam(value = "groupIdStringList") String groupIdStringList){
		List<Integer> groupIdList = ListUtil.convertStringArrayToIntegerList(StringUtils.split(groupIdStringList, ","));
		systemPermissionSrv.updateSystemPermissionGroup(id, groupIdList);
		ajaxSuccess(response);
	}
	
}