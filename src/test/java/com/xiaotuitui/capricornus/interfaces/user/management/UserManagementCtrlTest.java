package com.xiaotuitui.capricornus.interfaces.user.management;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import mockit.Deencapsulation;
import mockit.Expectations;
import mockit.NonStrictExpectations;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.xiaotuitui.capricornus.application.GroupSrv;
import com.xiaotuitui.capricornus.application.UserSrv;
import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.domain.model.User;
import com.xiaotuitui.capricornus.util.constant.MessageCode;
import com.xiaotuitui.capricornus.util.dto.UserDto;
import com.xiaotuitui.framework.domain.model.PageObject;
import com.xiaotuitui.framework.util.list.ListUtil;
import com.xiaotuitui.testframework.EntityUtil;
import com.xiaotuitui.testframework.HttpUtil;

@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml", "file:src/main/resources/spring/spring-mvc.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserManagementCtrlTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@InjectMocks
	@Autowired
	private UserManagementCtrl userManagementCtrl;
	
	@Mock
	private UserSrv userSrv;
	
	@Mock
	private GroupSrv groupSrv;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testToUserManagement() throws Exception{
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/management/toUserManagement"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Assert.assertEquals("/user/management/management", mvcResult.getModelAndView().getViewName());
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testToUser() throws Exception{
		List<Group> groupList = EntityUtil.createEntityList(Group.class, 4);
		Mockito.when(groupSrv.queryAllGroup()).thenReturn(groupList);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user/management/toUser"))
				.andExpect(MockMvcResultMatchers.view().name("/user/management/user")).andReturn();
		Mockito.verify(groupSrv).queryAllGroup();
		List<Group> actualGroupList = (List<Group>) mvcResult.getRequest().getAttribute("groupList");
		Assert.assertEquals(groupList, actualGroupList);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testQueryUserByPage() throws Exception{
		List<User> userList = EntityUtil.createEntityList(User.class, 4);
		Mockito.when(userSrv.queryUserByPage(Mockito.any(UserDto.class), Mockito.any(PageObject.class))).thenReturn(userList);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/management/queryUserByPage")
				.param("username", "username").param("nickname", "nickname").param("currentPage", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Mockito.verify(userSrv).queryUserByPage(Mockito.any(UserDto.class), Mockito.any(PageObject.class));
		Assert.assertEquals("/user/management/userTable", mvcResult.getModelAndView().getViewName());
		List<User> actualUserList = (List<User>) mvcResult.getRequest().getAttribute("userList");
		PageObject pageObject = (PageObject) mvcResult.getRequest().getAttribute("pageObject");
		Assert.assertEquals(userList, actualUserList);
		Assert.assertNotNull(pageObject);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testSearchUserModal() throws Exception{
		List<User> userList = EntityUtil.createEntityList(User.class, 4);
		Mockito.when(userSrv.queryUserByPage(Mockito.any(UserDto.class), Mockito.any(PageObject.class))).thenReturn(userList);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/management/searchUserModal")
				.param("username", "username").param("nickname", "nickname").param("currentPage", "1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Mockito.verify(userSrv).queryUserByPage(Mockito.any(UserDto.class), Mockito.any(PageObject.class));
		Assert.assertEquals("/user/management/searchUserModal", mvcResult.getModelAndView().getViewName());
		List<User> actualUserList = (List<User>) mvcResult.getRequest().getAttribute("userList");
		PageObject pageObject = (PageObject) mvcResult.getRequest().getAttribute("pageObject");
		Assert.assertEquals(userList, actualUserList);
		Assert.assertNotNull(pageObject);
	}
	
	@Test
	public void testDeleteUser() throws Exception{
		Mockito.doNothing().when(userSrv).removeUser(Mockito.anyInt());
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/management/deleteUser")
				.param("userId", "1")).andReturn();
		Mockito.verify(userSrv).removeUser(Mockito.anyInt());
		String actualResponse = mvcResult.getResponse().getContentAsString();
		Assert.assertEquals(HttpUtil.ajaxSuccess(), actualResponse);
	}
	
	@Test
	public void testUpdateUserGroup() throws Exception{
		final String groupIdStringList = "1,12,13,4";
		final List<Integer> groupList = Arrays.asList(new Integer[]{1, 2});
		new NonStrictExpectations(ListUtil.class) {
			{
				ListUtil.convertStringArrayToIntegerList(StringUtils.split(groupIdStringList, ","));
				result = groupList;
			}
		};
		Mockito.doNothing().when(userSrv).updateUserGroup(Mockito.anyInt(), Mockito.anyListOf(Integer.class));
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/management/updateUserGroup")
				.param("id", "1").param("groupIdStringList", groupIdStringList)).andReturn();
		Mockito.verify(userSrv).updateUserGroup(Mockito.anyInt(), Mockito.anyListOf(Integer.class));
		String actualResponse = mvcResult.getResponse().getContentAsString();
		Assert.assertEquals(HttpUtil.ajaxSuccess(), actualResponse);
	}
	
	@Test
	public void testAddUser() throws Exception{
		final User user = (User) EntityUtil.createEntity(User.class, 1);
		Mockito.when(userSrv.queryUserByUsername(user.getUsername())).thenReturn(null);
		Mockito.when(userSrv.createUser(Mockito.any(User.class))).thenReturn(Mockito.any(User.class));
		
		new Expectations(userManagementCtrl) {
			{
				Deencapsulation.invoke(userManagementCtrl, "initUserProfile", withAny(User.class));
			}
		};
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/management/addUser")
				.param("username", user.getUsername()).param("invite", "true"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		
		Mockito.verify(userSrv).queryUserByUsername(user.getUsername());
		Mockito.verify(userSrv).createUser(Mockito.any(User.class));
		Assert.assertEquals(HttpUtil.ajaxSuccess(), mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testAddUserAlreadyExist() throws Exception{
		User user = (User) EntityUtil.createEntity(User.class, 1);
		Mockito.when(userSrv.queryUserByUsername(user.getUsername())).thenReturn(user);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/management/addUser")
				.param("username", user.getUsername()).param("invite", "true"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		
		Mockito.verify(userSrv).queryUserByUsername(user.getUsername());
		Assert.assertEquals(HttpUtil.ajaxErrorData(MessageCode.USER_ALREADY_EXIST), mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testAddUserDisableEmail() throws Exception{
		User user = (User) EntityUtil.createEntity(User.class, 1);
		Mockito.when(userSrv.queryUserByUsername(user.getUsername())).thenReturn(null);
		Mockito.when(userSrv.createUser(Mockito.any(User.class))).thenReturn(Mockito.any(User.class));
		
		new Expectations(userManagementCtrl) {
			{
				Deencapsulation.invoke(userManagementCtrl, "initUserProfile", withAny(User.class));
			}
		};
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user/management/addUser")
				.param("username", user.getUsername()).param("invite", "false"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		
		Mockito.verify(userSrv).queryUserByUsername(user.getUsername());
		Mockito.verify(userSrv).createUser(Mockito.any(User.class));
		Assert.assertEquals(HttpUtil.ajaxSuccess(), mvcResult.getResponse().getContentAsString());
	}
	
	@Test
	public void testInitUserProfile() throws SecurityException, NoSuchMethodException, IllegalArgumentException, IllegalAccessException, InvocationTargetException{
		Class<?> cls = userManagementCtrl.getClass();
		Method method = cls.getDeclaredMethod("initUserProfile", User.class);
		method.setAccessible(true);
		User user = (User) EntityUtil.createEntity(User.class, 1);
		method.invoke(userManagementCtrl, user);
		Assert.assertEquals(0, user.getLoginSuccessTimes().intValue());
		Assert.assertEquals(0, user.getLastLoginFailTimes().intValue());
	}

}