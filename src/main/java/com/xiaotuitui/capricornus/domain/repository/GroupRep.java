package com.xiaotuitui.capricornus.domain.repository;

import java.util.List;

import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.framework.util.page.PageObject;

public interface GroupRep {
	
	public List<Group> queryAllGroup();

	public List<Group> queryGroupByPage(String groupName, PageObject pageObject);

}