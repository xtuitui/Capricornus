package com.xiaotuitui.capricornus.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiaotuitui.capricornus.application.GroupSrv;
import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.domain.repository.GroupRep;
import com.xiaotuitui.framework.util.page.PageObject;

@Service
public class GroupSrvImpl implements GroupSrv{

	@Autowired
	private GroupRep groupRep;
	
	public List<Group> queryAllGroup() {
		return groupRep.queryAllGroup();
	}

	public List<Group> queryGroupByPage(String groupName, PageObject pageObject) {
		return groupRep.queryGroupByPage(groupName, pageObject);
	}

	public Group queryGroupByName(String name) {
		return groupRep.queryGroupByName(name);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Group createGroup(Group group) {
		return groupRep.createGroup(group);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeGroup(Integer groupId) {
		Group group = groupRep.loadGroup(groupId);
		groupRep.removeGroup(group);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateGroup(Group group) {
		Group originalGroup = groupRep.loadGroup(group.getId());
		originalGroup.setName(group.getName());
		originalGroup.setDescription(group.getDescription());
	}

}