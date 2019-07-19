package org.nico.codegenerator.parser.entity;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;

/**
 * Type intermediate state
 * 
 * 
 * @author admin
 */
public enum Type {
	STRING("varchar", "string", "String", "String"),
	CHAR("varchar", "string", "String", "String"),
	
	BYTE("bit", "int8", "Byte", "Int"),
	SHORT("varchar", "string", "String", "Int"),
	INT("integer", "int", "Integer", "Int"),
	LONG("bigint", "int64", "Long", "Int64"),
	FLOAT("float", "float32", "Float", "Float"),
	DOUBLE("double", "float64", "Double", "Float"),
	BIGDECIMAL("varchar", "string", "BigDecimal", "Float"),
	
	BOOL("tinyint", "bool", "Boolean", "Boolean"),
	
	DATE("datetime", "time.Time", "Date", "Time"),
	;
	
	private String sqlType;
	
	private String goType;
	
	private String javaType;
	
	private String graphqlType;

	private Type(String sqlType, String goType, String javaType, String graphqlType) {
		this.sqlType = sqlType;
		this.goType = goType;
		this.javaType = javaType;
		this.graphqlType = graphqlType;
	}

	public String getGraphqlType() {
		return graphqlType;
	}

	public void setGraphqlType(String graphqlType) {
		this.graphqlType = graphqlType;
	}

	public String getSqlType() {
		return sqlType;
	}

	public void setSqlType(String sqlType) {
		this.sqlType = sqlType;
	}

	public String getGoType() {
		return goType;
	}

	public void setGoType(String goType) {
		this.goType = goType;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}
	
}
