package com.xiaotuitui.capricornus.domain.repository;

import java.util.List;

import com.xiaotuitui.capricornus.domain.model.Category;

public interface CategoryRep {

	public List<Category> queryAllCategory();

	public Category queryCategoryByName(String name);

	public Category createCategory(Category category);

	public Category loadCategory(Integer categoryId);

	public void removeCategory(Category category);

}