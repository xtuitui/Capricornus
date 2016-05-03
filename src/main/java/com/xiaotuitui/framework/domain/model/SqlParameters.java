package com.xiaotuitui.framework.domain.model;

import java.util.HashMap;
import java.util.Map;

public class SqlParameters {

	private StringBuilder sqlStringBuilder;
	
	private Map<String, Object> parameters;
	
	public SqlParameters () {
		sqlStringBuilder = new StringBuilder("");
		parameters = new HashMap<String, Object>();
	}
	
	public SqlParameters (StringBuilder sqlStringBuilder, Map<String, Object> parameters) {
		this.sqlStringBuilder = sqlStringBuilder;
		this.parameters = parameters;
	}
	
	public StringBuilder getSqlStringBuilder() {
		return sqlStringBuilder;
	}

	public void setSqlStringBuilder(StringBuilder sqlStringBuilder) {
		this.sqlStringBuilder = sqlStringBuilder;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}
	
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	
}