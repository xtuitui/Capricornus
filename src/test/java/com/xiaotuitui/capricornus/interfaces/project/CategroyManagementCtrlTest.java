package com.xiaotuitui.capricornus.interfaces.project;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.xiaotuitui.capricornus.application.CategorySrv;
import com.xiaotuitui.capricornus.domain.model.Category;
import com.xiaotuitui.capricornus.util.constant.MessageCode;
import com.xiaotuitui.testframework.EntityUtil;
import com.xiaotuitui.testframework.HttpUtil;

@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml", "file:src/main/resources/spring/spring-mvc.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CategroyManagementCtrlTest {
	
	@Autowired
	private WebApplicationContext webApplicationContext;
	
	private MockMvc mockMvc;
	
	@InjectMocks
	@Autowired
	private CategroyManagementCtrl categroyManagementCtrl;
	
	@Mock
	private CategorySrv categorySrv;
	
	@Before
	public void setUp(){
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testToCategory() throws Exception{
		List<Category> categroyList = EntityUtil.createEntityList(Category.class, 4);
		Mockito.when(categorySrv.queryAllCategory()).thenReturn(categroyList);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/project/category/toCategory"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Mockito.verify(categorySrv).queryAllCategory();
		String viewName = mvcResult.getModelAndView().getViewName();
		Assert.assertEquals("/project/category/category", viewName);
		MockHttpServletRequest request = mvcResult.getRequest();
		Assert.assertEquals(categroyList, request.getAttribute("categoryList"));
	}
	
	@Test
	public void testAddCategorySuccess() throws Exception{
		Category category = (Category) EntityUtil.createEntity(Category.class, 1);
		Mockito.when(categorySrv.queryCategoryByName(category.getName())).thenReturn(null);
		Mockito.when(categorySrv.createCategory(category)).thenReturn(category);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/project/category/addCategory")
				.param("name", category.getName()).param("description", category.getDescription()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Mockito.verify(categorySrv).queryCategoryByName(category.getName());
		Mockito.verify(categorySrv).createCategory(Mockito.any(Category.class));
		MockHttpServletResponse response = mvcResult.getResponse();
		String actualResponse = response.getContentAsString();
		Assert.assertEquals(HttpUtil.ajaxSuccess(), actualResponse);
	}
	
	@Test
	public void testAddCategoryAlreadyExist() throws Exception{
		Category category = (Category) EntityUtil.createEntity(Category.class, 1);
		Mockito.when(categorySrv.queryCategoryByName(category.getName())).thenReturn(category);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/project/category/addCategory")
				.param("name", category.getName()).param("description", category.getDescription()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Mockito.verify(categorySrv).queryCategoryByName(category.getName());
		MockHttpServletResponse response = mvcResult.getResponse();
		String actualResponse = response.getContentAsString();
		Assert.assertEquals(HttpUtil.ajaxErrorData(MessageCode.CATEGORY_ALREADY_EXIST), actualResponse);
	}
	
	@Test
	public void testDeleteCategory() throws Exception{
		Integer id = 1;
		Mockito.doNothing().when(categorySrv).removeCategory(id);
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/project/category/deleteCategory")
				.param("categoryId", id.toString())).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		Mockito.verify(categorySrv).removeCategory(id);
		MockHttpServletResponse response = mvcResult.getResponse();
		String actualResponse = response.getContentAsString();
		Assert.assertEquals(HttpUtil.ajaxSuccess(), actualResponse);
	}
	
	@Test
	public void testUpdateCategorySuccess() throws Exception{
		Integer id = 1;
		Category category = (Category) EntityUtil.createEntity(Category.class, id);
		Mockito.when(categorySrv.queryCategoryByName(category.getName())).thenReturn(null);
		Mockito.doNothing().when(categorySrv).updateCategory(category);
		MvcResult mvcResult = postUpdateCategory(category);
		Mockito.verify(categorySrv).queryCategoryByName(category.getName());
		Mockito.verify(categorySrv).updateCategory(Mockito.any(Category.class));
		MockHttpServletResponse response = mvcResult.getResponse();
		String actualResponse = response.getContentAsString();
		Assert.assertEquals(HttpUtil.ajaxSuccess(), actualResponse);
	}
	
	@Test
	public void testUpdateCategoryAlreadyExistWithSameId() throws Exception{
		Integer id = 1;
		Category category = (Category) EntityUtil.createEntity(Category.class, id);
		Mockito.when(categorySrv.queryCategoryByName(category.getName())).thenReturn(category);
		Mockito.doNothing().when(categorySrv).updateCategory(category);
		MvcResult mvcResult = postUpdateCategory(category);
		Mockito.verify(categorySrv).queryCategoryByName(category.getName());
		Mockito.verify(categorySrv).updateCategory(Mockito.any(Category.class));
		MockHttpServletResponse response = mvcResult.getResponse();
		String actualResponse = response.getContentAsString();
		Assert.assertEquals(HttpUtil.ajaxSuccess(), actualResponse);
	}
	
	@Test
	public void testUpdateCategoryAlreadyExistWithDifferentId() throws Exception{
		Integer id = 1;
		Category category = (Category) EntityUtil.createEntity(Category.class, id);
		Category categoryInDataBase = (Category) EntityUtil.createEntity(Category.class, id + 1);
		Mockito.when(categorySrv.queryCategoryByName(category.getName())).thenReturn(categoryInDataBase);
		MvcResult mvcResult = postUpdateCategory(category);
		Mockito.verify(categorySrv).queryCategoryByName(category.getName());
		MockHttpServletResponse response = mvcResult.getResponse();
		String actualResponse = response.getContentAsString();
		Assert.assertEquals(HttpUtil.ajaxErrorData(MessageCode.CATEGORY_ALREADY_EXIST), actualResponse);
	}
	
	private MvcResult postUpdateCategory(Category category) throws Exception{
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/project/category/updateCategory")
				.param("id", category.getId().toString()).param("name", category.getName())
				.param("description", category.getDescription()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		return mvcResult;
	}

}