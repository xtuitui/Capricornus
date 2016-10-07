package com.xiaotuitui.capricornus.application.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.xiaotuitui.capricornus.application.CategorySrv;
import com.xiaotuitui.capricornus.domain.model.Category;
import com.xiaotuitui.capricornus.domain.repository.CategoryRep;
import com.xiaotuitui.capricornus.domain.repository.ProjectRep;

@Service
public class CategorySrvImpl implements CategorySrv{
	
	@Autowired
	private CategoryRep categoryRep;
	
	@Autowired
	private ProjectRep projectRep;

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Category> queryAllCategory() {
		return categoryRep.queryAllCategory();
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Category queryCategoryByName(String name) {
		return categoryRep.queryCategoryByName(name);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Category createCategory(Category category) {
		return categoryRep.createCategory(category);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void removeCategory(Integer categoryId) {
		Category category = categoryRep.loadCategory(categoryId);
		categoryRep.removeCategory(category);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateCategory(Category category) {
		Category originalCategory = categoryRep.loadCategory(category.getId());
		originalCategory.setName(category.getName());
		originalCategory.setDescription(category.getDescription());
	}

	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Category loadCategory(Integer categoryId) {
		return categoryRep.loadCategory(categoryId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void updateProjectCategory(List<Integer> projectIds, List<Integer> categoryIds) {
		for(int i=0;i<projectIds.size();i++){
			Integer projectId = projectIds.get(i);
			Integer categoryId = categoryIds.get(i);
			if(categoryId!=null){
				projectRep.loadProject(projectId).setCategory(categoryRep.loadCategory(categoryId));
			}
		}
	}

}