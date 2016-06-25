package com.xiaotuitui.capricornus.interfaces.user.management;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml", "file:src/main/resources/spring/spring-mvc.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserManagementCtrlTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@Before
	public void setUp(){
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@Test
	public void testToLogin() throws Exception{
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/index/toLogin")).andExpect(MockMvcResultMatchers.view().name("/index/login")).andReturn();
		System.out.println(mvcResult.getRequest().getAttribute("asd"));
	}

}