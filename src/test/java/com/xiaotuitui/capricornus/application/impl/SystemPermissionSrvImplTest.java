package com.xiaotuitui.capricornus.application.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.xiaotuitui.capricornus.application.SystemPermissionSrv;
import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.domain.model.SystemPermission;
import com.xiaotuitui.capricornus.domain.repository.GroupRep;
import com.xiaotuitui.capricornus.domain.repository.SystemPermissionRep;
import com.xiaotuitui.testframework.EntityUtil;
import com.xiaotuitui.testframework.ProxyUtil;

@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SystemPermissionSrvImplTest {
	
	@Autowired
	private SystemPermissionSrv systemPermissionSrv;
	
	@Mock
	private SystemPermissionRep systemPermissionRep;
	
	@Mock
	private GroupRep groupRep;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		systemPermissionSrv = (SystemPermissionSrv) ProxyUtil.unwrapProxy(systemPermissionSrv);
		ReflectionTestUtils.setField(systemPermissionSrv, "systemPermissionRep", systemPermissionRep);
		ReflectionTestUtils.setField(systemPermissionSrv, "groupRep", groupRep);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testQueryAllUser(){
		List<SystemPermission> systemPermissions = EntityUtil.createEntityList(SystemPermission.class, 3);
		Mockito.when(systemPermissionRep.queryAllSystemPermission()).thenReturn(systemPermissions);
		List<SystemPermission> actualSystemPermissions = systemPermissionSrv.queryAllSystemPermission();
		Mockito.verify(systemPermissionRep).queryAllSystemPermission();
		Assert.assertEquals(systemPermissions, actualSystemPermissions);
	}
	
	@Test
	public void testUpdateSystemPermissionGroup(){
		SystemPermission systemPermission = (SystemPermission) EntityUtil.createEntity(SystemPermission.class, 1);
		systemPermission.setGroups(new ArrayList<Group>());
		Group group = (Group) EntityUtil.createEntity(Group.class, 1);
		Mockito.when(systemPermissionRep.loadSystemPermission(systemPermission.getId())).thenReturn(systemPermission);
		Mockito.when(groupRep.loadGroup(group.getId())).thenReturn(group);
		
		List<Integer> groupIdList = Arrays.asList(new Integer[]{group.getId(), group.getId(), group.getId()});
		systemPermissionSrv.updateSystemPermissionGroup(systemPermission.getId(), groupIdList);
		
		Mockito.verify(systemPermissionRep).loadSystemPermission(systemPermission.getId());
		List<Group> expectedGroupList = new ArrayList<Group>();
		for(int i=0;i<groupIdList.size();i++){
			expectedGroupList.add(group);
		}
		Mockito.verify(groupRep, Mockito.times(groupIdList.size())).loadGroup(group.getId());
		List<Group> actualGroupList = systemPermission.getGroups();
		Assert.assertEquals(expectedGroupList, actualGroupList);
	}

}