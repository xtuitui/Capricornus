package com.xiaotuitui.capricornus.interfaces.system;

import java.util.Arrays;
import java.util.List;

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
import com.xiaotuitui.capricornus.application.SystemPermissionSrv;
import com.xiaotuitui.capricornus.domain.model.Group;
import com.xiaotuitui.capricornus.domain.model.SystemPermission;
import com.xiaotuitui.framework.util.list.ListUtil;
import com.xiaotuitui.testframework.EntityUtil;
import com.xiaotuitui.testframework.HttpUtil;

@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml", "file:src/main/resources/spring/spring-mvc.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SystemPermissionCtrlTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@InjectMocks
	@Autowired
	private SystemPermissionCtrl systemPermissionCtrl;
	
	@Mock
	private SystemPermissionSrv systemPermissionSrv;
	
	@Mock
	private GroupSrv groupSrv;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testToSystemPermission() throws Exception{
		List<SystemPermission> systemPermissionList = EntityUtil.createEntityList(SystemPermission.class, 4);
		List<Group> groupList = EntityUtil.createEntityList(Group.class, 4);
		Mockito.when(systemPermissionSrv.queryAllSystemPermission()).thenReturn(systemPermissionList);
		Mockito.when(groupSrv.queryAllGroup()).thenReturn(groupList);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/system/permission/toSystemPermission"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		
		Assert.assertEquals(systemPermissionList, mvcResult.getRequest().getAttribute("systemPermissionList"));
		Assert.assertEquals(groupList, mvcResult.getRequest().getAttribute("groupList"));
		Assert.assertEquals("/system/permission/permission", mvcResult.getModelAndView().getViewName());
	}
	
	@Test
	public void testUpdateSystemPermissionGroup() throws Exception{
		final String groupIdStringList = "1,2,3";
		final List<Integer> groupList = Arrays.asList(new Integer[]{1, 2});
		Mockito.doNothing().when(systemPermissionSrv).updateSystemPermissionGroup(Mockito.anyInt(), Mockito.anyListOf(Integer.class));
		new NonStrictExpectations(ListUtil.class) {
			{
				ListUtil.convertStringArrayToIntegerList(StringUtils.split(groupIdStringList, ","));
				result = groupList;
			}
		};
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/system/permission/updateSystemPermissionGroup")
				.param("id", "1").param("groupIdStringList", groupIdStringList))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		
		Assert.assertEquals(HttpUtil.ajaxSuccess(), mvcResult.getResponse().getContentAsString());
	}

}