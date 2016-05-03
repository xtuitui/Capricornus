package com.xiaotuitui.capricornus.application.impl;

import java.util.List;

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
import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.domain.repository.UserRep;
import com.xiaotuitui.testframework.EntityUtil;
import com.xiaotuitui.testframework.ProxyUtil;

@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserSrvImplTest {
	
	@Autowired
	private UserSrv userSrv;
	
	@Mock
	private UserRep userRep;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		userSrv = (UserSrv) ProxyUtil.unwrapProxy(userSrv);
		ReflectionTestUtils.setField(userSrv, "userRep", userRep);
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testQueryAllUser(){
		Mockito.when(userRep.queryAllUser()).thenReturn(EntityUtil.createEntityList(User.class, 3));
		List<User> userList = userSrv.queryAllUser();
		System.out.println(userList);
		Mockito.verify(userRep).queryAllUser();
	}

}