package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.domain.repository.UserRep;
import com.xiaotuitui.testframework.EntityUtil;

@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserRepImplTest{
	
	@Autowired
	private UserRep userRep;
	
	@Before
	public void setup(){
		System.out.println("setup...");
	}
	
	@Test
	public void testQueryAllUser(){
		List<User> userList = userRep.queryAllUser();
		System.out.println(userList);
	}
	
	@Test
	public void testQueryUserByUsername(){
		User user = userRep.queryUserByUsername("admin");
		System.out.println(user.getNickname());
		user = userRep.queryUserByUsername("ASNPH35");
		System.out.println(user.getNickname());
	}
	
	@Test
	public void testQueryUserByNickname(){
		List<User> userList = userRep.queryUserByNickname("jac");
		System.out.println(userList);
	}
	
	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void testCreateUser(){
		User user = (User) EntityUtil.createEntity(User.class, 100);
		user.setId(null);
		userRep.createUser(user);
		System.out.println(user.getId());
	}
	
	@Test
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void testDeleteUser(){
		User user = userRep.queryUserByUsername("username10");
		userRep.deleteUser(user);
	}

}