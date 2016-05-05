package com.xiaotuitui.framework.infrastructure.persistence;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.xiaotuitui.framework.domain.model.SqlParameters;
import com.xiaotuitui.framework.domain.repository.JPABaseRep;
import com.xiaotuitui.framework.util.beanutil.ReflectUtil;

public abstract class JPABaseRepImpl<T> implements JPABaseRep<T>{

	private Class<T> persistentClass;
	
	protected abstract EntityManager getEntityManager();
	
	public JPABaseRepImpl() {
		this.persistentClass = ReflectUtil.getGenericTypeArgument(this.getClass());
	}

	public T create(T t) {
		getEntityManager().persist(t);
		return t;
	}
	
	public T update(T t) {
		return getEntityManager().merge(t);
	}
	
	public void remove(T t) {
		getEntityManager().remove(update(t));
	}

	public void remove(Serializable id) {
		this.remove(find(id));
	}
	
	public T find(Serializable id) {
		return getEntityManager().find(persistentClass, id);
	}
	
	public T getReference(Serializable id) {
		return getEntityManager().getReference(persistentClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> query(String sql) {
		return getEntityManager().createQuery(sql).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> query(SqlParameters sqlParameters) {
		return createQuery(getEntityManager(), sqlParameters).getResultList();
	}
	
	public List<T> namedQuery(String name){
		return getEntityManager().createNamedQuery(name, persistentClass).getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> namedQuery(SqlParameters sqlParameters) {
		return createNamedQuery(getEntityManager(), sqlParameters).getResultList();
	}
	
	public T queryFirstResult(String sql) {
		return getFirst(query(sql));
	}

	public T queryFirstResult(SqlParameters sqlParameters) {
		return getFirst(query(sqlParameters));
	}
	
	public T namedQueryFirstResult(String name) {
		return getFirst(namedQuery(name));
	}

	public T namedQueryFirstResult(SqlParameters sqlParameters) {
		return getFirst(namedQuery(sqlParameters));
	}
	
	private T getFirst(List<T> resultList) {
		if(resultList==null||resultList.size()==0){
			return null;
		}else{
			return resultList.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public T querySingleResult(String sql) {
		return (T) getEntityManager().createQuery(sql).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public T querySingleResult(SqlParameters sqlParameters) {
		return (T) createQuery(getEntityManager(), sqlParameters).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public T namedQuerySingleResult(String name) {
		return (T) getEntityManager().createNamedQuery(name).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public T namedQuerySingleResult(SqlParameters sqlParameters) {
		return (T) createNamedQuery(getEntityManager(), sqlParameters).getSingleResult();
	}
	
	public List<T> queryByPage(String sql, int pageNumber, int pageSize) {
		return executeQueryPageResult(getEntityManager().createQuery(sql), pageNumber, pageSize);
	}
	
	public List<T> queryByPage(SqlParameters sqlParameters, int pageNumber, int pageSize) {
		return executeQueryPageResult(createQuery(getEntityManager(), sqlParameters), pageNumber, pageSize);
	}
	
	public List<T> namedQueryByPage(String name, int pageNumber, int pageSize) {
		return executeQueryPageResult(getEntityManager().createNamedQuery(name), pageNumber, pageSize);
	}
	
	public List<T> namedQueryByPage(SqlParameters sqlParameters, int pageNumber, int pageSize) {
		return executeQueryPageResult(createNamedQuery(getEntityManager(), sqlParameters), pageNumber, pageSize);
	}
	
	@SuppressWarnings("unchecked")
	private List<T> executeQueryPageResult(Query query, int pageNumber, int pageSize){
		return query.setFirstResult((pageNumber - 1) * pageSize).setMaxResults(pageSize).getResultList();
	}
	
	public long count(SqlParameters sqlParameters) {
		SqlParameters countSqlParameters = new SqlParameters();
		StringBuilder countSqlStringBuilder = buildCountSql(sqlParameters.getSqlStringBuilder());
		countSqlParameters.setSqlStringBuilder(countSqlStringBuilder);
		countSqlParameters.setParameters(sqlParameters.getParameters());
		return (Long) createQuery(getEntityManager(), countSqlParameters).getSingleResult();
	}
	
	private Query createQuery(EntityManager entityManager, SqlParameters sqlParameters) {
		Query query = entityManager.createQuery(sqlParameters.getSqlStringBuilder().toString());
		return setQueryParameter(query, sqlParameters.getParameters());
	}
	
	private Query createNamedQuery(EntityManager entityManager, SqlParameters sqlParameters){
		Query query = entityManager.createNamedQuery(sqlParameters.getSqlStringBuilder().toString());
		return setQueryParameter(query, sqlParameters.getParameters());
	}
	
	private Query setQueryParameter(Query query, Map<String, Object> parameters){
		Set<String> keySet = parameters.keySet();
		for(String key:keySet){
			query.setParameter(key, parameters.get(key));
		}
		return query;
	}
	
	private StringBuilder buildCountSql(StringBuilder sql) {
		int beginIndex_from = sql.toString().indexOf("from");
		String sql_from_ex = sql.substring(0, beginIndex_from);
		sql_from_ex = sql_from_ex.replaceAll("select", "");
		sql_from_ex = sql_from_ex.replaceAll("distinct", "");
		sql_from_ex = sql_from_ex.trim();
		String sql_where_rear = "";
		int beginIndex_where = sql.toString().indexOf("where");
		if (beginIndex_where != -1) {
			sql_where_rear = sql.substring(beginIndex_where).trim();
			sql_where_rear = sql_where_rear.replaceAll("1=1 and", "");
			sql_where_rear = sql_where_rear.replaceAll("1=1", "");
			int beginIndex_orderby = sql_where_rear.toString().indexOf("order by");
			if (beginIndex_orderby != -1){
				sql_where_rear = sql_where_rear.substring(0, beginIndex_orderby);
			}
			if (sql_where_rear.trim().length() == "where".length()){
				sql_where_rear = "";
			}
		}
		String sql_from_where = "";
		if (beginIndex_where != -1){
			sql_from_where = sql.substring(beginIndex_from, beginIndex_where);
		}else{
			sql_from_where = sql.substring(beginIndex_from);
		}
		return new StringBuilder("select count(" + sql_from_ex + ") " + sql_from_where + sql_where_rear);
	}

}