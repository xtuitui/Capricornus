package com.xiaotuitui.capricornus.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}