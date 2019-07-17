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
	STRING("varchar", "string", "String"),
	CHAR("varchar", "string", "Charset"),
	
	BYTE("bit", "int8", "Byte"),
	SHORT("varchar", "string", "String"),
	INT("integer", "int", "Integer"),
	LONG("bigint", "int64", "Long"),
	FLOAT("float", "float32", "Float"),
	DOUBLE("double", "float64", "Double"),
	BIGDECIMAL("varchar", "string", "BigDecimal"),
	
	BOOL("tinyint", "bool", "Boolean"),
	
	DATE("datetime", "time.Time", "Date"),
	;
	
	private String sqlType;
	
	private String goType;
	
	private String javaType;

	private Type(String sqlType, String goType, String javaType) {
		this.sqlType = sqlType;
		this.goType = goType;
		this.javaType = javaType;
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
