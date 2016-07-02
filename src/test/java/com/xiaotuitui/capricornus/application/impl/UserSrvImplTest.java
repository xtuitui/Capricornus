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

import com.xiaotuitui.capricornus.application.UserSrv;
import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.domain.repository.GroupRep;
import com.xiaotuitui.capricornus.domain.repository.UserRep;
import com.xiaotuitui.capricornus.util.dto.UserDto;
import com.xiaotuitui.framework.domain.model.PageObject;
import com.xiaotuitui.testframework.EntityUtil;
import com.xiaotuitui.testframework.ProxyUtil;

@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserSrvImplTest {
	
	@Autowired
	private UserSrv userSrv;
	
	@Mock
	private UserRep userRep;
	
	@Mock
	private GroupRep groupRep;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		userSrv = (UserSrv) ProxyUtil.unwrapProxy(userSrv);
		ReflectionTestUtils.setField(userSrv, "userRep", userRep);
		ReflectionTestUtils.setField(userSrv, "groupRep", groupRep);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testQueryAllUser(){
		List<User> expectedUserList = EntityUtil.createEntityList(User.class, 3);
		Mockito.when(userRep.queryAllUser()).thenReturn(expectedUserList);
		List<User> userList = userSrv.queryAllUser();
		Mockito.verify(userRep).queryAllUser();
		Assert.assertEquals(expectedUserList, userList);
	}
	
	@Test
	public void testQueryUserByUsername(){
		User user = (User) EntityUtil.createEntity(User.class, 1);
		Mockito.when(userRep.queryUserByUsername(user.getUsername())).thenReturn(user);
		User actualUser = userSrv.queryUserByUsername(user.getUsername());
		Mockito.verify(userRep).queryUserByUsername(user.getUsername());
		Assert.assertEquals(user, actualUser);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testQueryUserByPage(){
		List<User> expectedUserList = EntityUtil.createEntityList(User.class, 3);
		UserDto userDto = new UserDto();
		PageObject pageObject = new PageObject();
		Mockito.when(userRep.queryUserByPage(userDto, pageObject)).thenReturn(expectedUserList);
		List<User> actualUserList = userSrv.queryUserByPage(userDto, pageObject);
		Mockito.verify(userRep).queryUserByPage(userDto, pageObject);
		Assert.assertEquals(expectedUserList, actualUserList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testQueryUserByNickname(){
		List<User> userList = EntityUtil.createEntityList(User.class, 3);
		Mockito.when(userRep.queryUserByNickname(userList.get(0).getNickname())).thenReturn(userList);
		List<User> actualUserList = userSrv.queryUserByNickname(userList.get(0).getNickname());
		Mockito.verify(userRep).queryUserByNickname(userList.get(0).getNickname());
		Assert.assertEquals(userList, actualUserList);
	}
	
	@Test
	public void testCreateUser(){
		User user = (User) EntityUtil.createEntity(User.class, 1);
		Mockito.when(userRep.createUser(user)).thenReturn(user);
		User actualUser = userSrv.createUser(user);
		Mockito.verify(userRep).createUser(user);
		Assert.assertEquals(user, actualUser);
	}
	
	@Test
	public void testUpdateUserGroup(){
		User user = (User) EntityUtil.createEntity(User.class, 1);
		user.setGroups(new ArrayList<Group>());
		Group group = (Group) EntityUtil.createEntity(Group.class, 1);
		Mockito.when(userRep.loadUser(user.getId())).thenReturn(user);
		Mockito.when(groupRep.loadGroup(group.getId())).thenReturn(group);
		
		List<Integer> groupIdList = Arrays.asList(new Integer[]{user.getId(), user.getId(), user.getId()});
		userSrv.updateUserGroup(user.getId(), groupIdList);
		
		Mockito.verify(userRep).loadUser(user.getId());
		List<Group> expectedGroupList = new ArrayList<Group>();
		for(int i=0;i<groupIdList.size();i++){
			expectedGroupList.add(group);
		}
		Mockito.verify(groupRep, Mockito.times(groupIdList.size())).loadGroup(user.getId());
		List<Group> actualGroupList = user.getGroups();
		Assert.assertEquals(expectedGroupList, actualGroupList);
	}
	
	@Test
	public void testRemoveUser(){
		User user = (User) EntityUtil.createEntity(User.class, 1);
		user.setGroups(new ArrayList<Group>());
		Mockito.when(userRep.loadUser(user.getId())).thenReturn(user);
		Mockito.doNothing().when(userRep).removeUser(user);
		
		userSrv.removeUser(user.getId());
		
		Mockito.verify(userRep).loadUser(user.getId());
		Mockito.verify(userRep).removeUser(user);
	}
	
}