package com.xiaotuitui.capricornus.interfaces.user.management;

import java.util.ArrayList;
import java.util.Date;
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
import com.xiaotuitui.capricornus.application.UserSrv;
import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.util.constant.Constant;
import com.xiaotuitui.capricornus.util.constant.MessageCode;
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
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public void addUser(HttpServletRequest request, HttpServletResponse response, User user, @RequestParam(value = "invite") String invite){
		User checkUser = userSrv.queryUserByUsername(user.getUsername());
		if(checkUser!=null){
			ajaxErrorData(request, response, MessageCode.USER_ALREADY_EXIST);
		}else{
			initUserProfile(user);
			if(Constant.TRUE.equalsIgnoreCase(invite)){
				System.out.println("need to invite user...");
			}
			userSrv.createUser(user);
			ajaxSuccess(request, response);
		}
	}
	
	private void initUserProfile(User user){
		user.setLoginSuccessTimes(0);
		user.setLastLoginFailTimes(0);
		user.setLastUpdatedTime(new Date());
		
		System.out.println("need to reset user pwd...");
		user.setPassword(user.getPassword());
	}
	
	@RequestMapping(value = "/updateUserGroup", method = RequestMethod.POST)
	public void updateUserGroup(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "id") Integer id, @RequestParam(value = "groupIdStringList") String groupIdStringList){
		List<Integer> groupIdList = convertStringArrayToList(StringUtils.split(groupIdStringList, ","));
		userSrv.updateUserGroup(id, groupIdList);
		ajaxSuccess(request, response);
	}

	private List<Integer> convertStringArrayToList(String[] groupIdArray) {
		List<Integer> groupIdList = new ArrayList<Integer>();
		for(String groupIdString:groupIdArray){
			groupIdList.add(Integer.valueOf(groupIdString));
		}
		return groupIdList;
	}
	
	@RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
	public void deleteUser(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "userId") Integer userId){
		userSrv.removeUser(userId);
		ajaxSuccess(request, response);
	}
	
	/**
	 * Copy From queryUserByPage
	 * Use For searchUserModal
	 */
	@RequestMapping(value = "/searchUserModal", method = RequestMethod.POST)
	public String searchUserModal(HttpServletRequest request, UserDto userDto, PageObject pageObject){
		List<User> userList = userSrv.queryUserByPage(userDto, pageObject);
		request.setAttribute("userList", userList);
		request.setAttribute("pageObject", pageObject);
		return "/user/management/searchUserModal";
	}

}