package com.xiaotuitui.framework.domain.repository;

import java.io.Serializable;
import java.util.List;

import com.xiaotuitui.framework.domain.model.SqlParameters;

public interface JPABaseRep<T>{
	
	public T create(T t);
	
	public T update(T t);
	
	public void remove(T t);

	public void remove(Serializable id);
	
	public T find(Serializable id);
	
	public T getReference(Serializable id); 

	public List<T> query(String sql);

	public List<T> query(SqlParameters sqlParameters);
	
	public T queryFirstResult(String sql);
	
	public T queryFirstResult(SqlParameters sqlParameters);
	
	public T querySingleResult(String sql);
	
	public T querySingleResult(SqlParameters sqlParameters);

	public List<T> queryByPage(String sql, int pageNumber, int pageSize);
	
	public List<T> queryByPage(SqlParameters sqlParameters, int pageNumber, int pageSize);
	
	public long count(SqlParameters sqlParameters);
	
}