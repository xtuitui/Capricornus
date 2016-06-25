package com.xiaotuitui.capricornus.interfaces.index;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml", "file:src/main/resources/spring/spring-mvc.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class IndexCtrlTest {
	
	@Autowired
	private IndexCtrl indexCtrl;
	
	@Autowired
	private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
	
	@Autowired
	private RequestMappingHandlerMapping requestMappingHandlerMapping;
	
	private MockHttpServletRequest request;
	
	private MockHttpServletResponse response;
	
	@Before
	public void setUp(){
		request = new MockHttpServletRequest();
        response = new MockHttpServletResponse();
        request.setAttribute(HandlerMapping.INTROSPECT_TYPE_LEVEL_MAPPING, true);
        request.setRequestURI("/index/toLogin");
        request.setMethod("GET");
	}
	
	@Test
	public void testToLogin() throws Exception{
		HandlerExecutionChain handlerExecutionChain = requestMappingHandlerMapping.getHandler(request);
		ModelAndView modelAndView = requestMappingHandlerAdapter.handle(request, response, handlerExecutionChain.getHandler());
		System.out.println(modelAndView.getViewName());
	}

}