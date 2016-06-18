package com.xiaotuitui.capricornus.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiaotuitui.capricornus.application.SystemPermissionSrv;
import com.xiaotuitui.capricornus.domain.model.SystemPermission;
import com.xiaotuitui.capricornus.domain.repository.GroupRep;
import com.xiaotuitui.capricornus.domain.repository.SystemPermissionRep;

@Service
public class SystemPermissionSrvImpl implements SystemPermissionSrv{
	
	@Autowired
	private SystemPermissionRep systemPermissionRep;
	
	@Autowired
	private GroupRep groupRep;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<SystemPermission> queryAllSystemPermission() {
		return systemPermissionRep.queryAllSystemPermission();
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateSystemPermissionGroup(Integer id, List<Integer> groupIdList) {
		SystemPermission systemPermission = systemPermissionRep.loadSystemPermission(id);
		systemPermission.getGroups().clear();
		for(Integer groupId:groupIdList){
			systemPermission.getGroups().add(groupRep.loadGroup(groupId));
		}
	}

}