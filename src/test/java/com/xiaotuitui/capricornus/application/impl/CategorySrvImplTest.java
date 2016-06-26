package com.xiaotuitui.capricornus.application.impl;

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

import com.xiaotuitui.capricornus.application.CategorySrv;
import com.xiaotuitui.capricornus.domain.model.Category;
import com.xiaotuitui.capricornus.domain.repository.CategoryRep;
import com.xiaotuitui.testframework.EntityUtil;
import com.xiaotuitui.testframework.ProxyUtil;

@ContextConfiguration(locations={"file:src/main/resources/spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class CategorySrvImplTest {
	
	@Autowired
	private CategorySrv categorySrv;
	
	@Mock
	private CategoryRep categoryRep;
	
	@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		categorySrv = (CategorySrv) ProxyUtil.unwrapProxy(categorySrv);
		ReflectionTestUtils.setField(categorySrv, "categoryRep", categoryRep);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testQueryAllCategory(){
		List<Category> expectedCategoryList = EntityUtil.createEntityList(Category.class, 4);
		Mockito.when(categoryRep.queryAllCategory()).thenReturn(expectedCategoryList);
		List<Category> actualCategoryList = categorySrv.queryAllCategory();
		Mockito.verify(categoryRep).queryAllCategory();
		Assert.assertEquals(expectedCategoryList, actualCategoryList);
	}
	
	@Test
	public void testQueryCategoryByName(){
		Category expectedCategory = (Category) EntityUtil.createEntity(Category.class, 1);
		Mockito.when(categoryRep.queryCategoryByName(expectedCategory.getName())).thenReturn(expectedCategory);
		Category actualCategory = categorySrv.queryCategoryByName(expectedCategory.getName());
		Mockito.verify(categoryRep).queryCategoryByName(expectedCategory.getName());
		Assert.assertEquals(expectedCategory, actualCategory);
	}
	
	@Test
	public void testCreateCategory(){
		Category expectedCategory = (Category) EntityUtil.createEntity(Category.class, 1);
		Mockito.when(categoryRep.createCategory(expectedCategory)).thenReturn(expectedCategory);
		Category actualCategory = categorySrv.createCategory(expectedCategory);
		Mockito.verify(categoryRep).createCategory(expectedCategory);
		Assert.assertEquals(expectedCategory, actualCategory);
	}
	
	@Test
	public void testRemoveCategory(){
		Integer id = 1;
		Category category = (Category) EntityUtil.createEntity(Category.class, id);
		Mockito.when(categoryRep.loadCategory(id)).thenReturn(category);
		Mockito.doNothing().when(categoryRep).removeCategory(category);
		categorySrv.removeCategory(id);
		Mockito.verify(categoryRep).loadCategory(id);
		Mockito.verify(categoryRep).removeCategory(category);
	}
	
	@Test
	public void testUpdateCategory() throws SecurityException, IllegalArgumentException, NoSuchFieldException, IllegalAccessException{
		Integer id = 1;
		Category expectedCategory = (Category) EntityUtil.createEntity(Category.class, id + 1);
		expectedCategory.setId(id);
		Category categoryInDatabase = (Category) EntityUtil.createEntity(Category.class, id);
		Mockito.when(categoryRep.loadCategory(id)).thenReturn(categoryInDatabase);
		categorySrv.updateCategory(expectedCategory);
		Mockito.verify(categoryRep).loadCategory(id);
		boolean isEquals = EntityUtil.objEqualsObj(expectedCategory, categoryInDatabase);
		Assert.assertTrue(isEquals);
	}

}