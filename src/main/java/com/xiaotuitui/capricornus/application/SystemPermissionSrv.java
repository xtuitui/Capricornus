package com.xiaotuitui.capricornus.application;

import java.util.List;

import com.xiaotuitui.capricornus.domain.model.SystemPermission;

public interface SystemPermissionSrv {

	public List<SystemPermission> queryAllSystemPermission();

	public void updateSystemPermissionGroup(Integer id, List<Integer> groupIdList);

}