package com.xiaotuitui.capricornus.domain.repository;

import java.util.List;

import com.xiaotuitui.capricornus.domain.model.SystemPermission;

public interface SystemPermissionRep {

	public List<SystemPermission> queryAllSystemPermission();

	public SystemPermission loadSystemPermission(Integer id);

}