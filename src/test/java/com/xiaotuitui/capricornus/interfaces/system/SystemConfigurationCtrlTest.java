package com.xiaotuitui.capricornus.interfaces.system;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml", "file:src/main/resources/spring/spring-mvc.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class SystemConfigurationCtrlTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Autowired
	private SystemPermissionCtrl systemPermissionCtrl;
	
	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testToSystemConfiguration() throws Exception{
		mockMvc.perform(MockMvcRequestBuilders.get("/system/configuration/toSystemConfiguration"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.view().name("/system/management/management"))
				.andReturn();
	}
	
}