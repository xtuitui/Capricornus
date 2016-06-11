package com.xiaotuitui.capricornus.application;

import java.util.List;

import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.framework.util.page.PageObject;

public interface GroupSrv {
	
	public List<Group> queryAllGroup();
	
	public List<Group> queryGroupByPage(String groupName, PageObject pageObject);

	public Group queryGroupByName(String name);

	public Group createGroup(Group group);

	public void removeGroup(Integer groupId);

	public void updateGroup(Group group);

	public List<User> queryUserByGroup(Integer id);

	public void updateGroupUser(Integer id, List<Integer> userIdList);
	
}