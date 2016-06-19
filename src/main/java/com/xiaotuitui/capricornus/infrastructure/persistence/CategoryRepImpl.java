package com.xiaotuitui.capricornus.infrastructure.persistence;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.xiaotuitui.capricornus.domain.model.Category;
import com.xiaotuitui.capricornus.domain.repository.CategoryRep;
import com.xiaotuitui.framework.domain.model.SqlParameters;
import com.xiaotuitui.framework.infrastructure.persistence.JPABaseRepImpl;

@Repository
public class CategoryRepImpl extends JPABaseRepImpl<Category> implements CategoryRep{

	@PersistenceContext
	private EntityManager entityManager;
	
	protected EntityManager getEntityManager() {
		return entityManager;
	}

	public List<Category> queryAllCategory() {
		return super.namedQuery("Category.queryAllCategory");
	}

	public Category queryCategoryByName(String name) {
		SqlParameters sqlParameters = new SqlParameters("Category.queryCategoryByName", new String[]{"name"}, new Object[]{name});
		return super.namedQueryFirstResult(sqlParameters);
	}

	public Category createCategory(Category category) {
		return super.create(category);
	}

	public Category loadCategory(Integer categoryId) {
		return super.getReference(categoryId);
	}

	public void removeCategory(Category category) {
		super.remove(category);
	}

}