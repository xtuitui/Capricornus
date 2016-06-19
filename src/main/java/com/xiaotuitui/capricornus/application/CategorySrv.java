package com.xiaotuitui.capricornus.application;

import java.util.List;

import com.xiaotuitui.capricornus.domain.model.Category;

public interface CategorySrv {

	public List<Category> queryAllCategory();

	public Category queryCategoryByName(String name);

	public Category createCategory(Category category);

	public void removeCategory(Integer categoryId);

	public void updateCategory(Category category);

}