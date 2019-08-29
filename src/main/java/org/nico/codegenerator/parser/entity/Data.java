package org.nico.codegenerator.parser.entity;

import java.util.List;
import java.util.Map;

public class Data {
	
	private List<Field> fields;
	
	private Map<String, Object> properties;
	
	private String name;
	
	private String comment;
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Map<String, Object> getProperties() {
		return properties;
	}

	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static class Field{
		
		private String name;
		
		private Type type;
		
		private boolean required;
		
		private boolean primarily;
		
		private String comment;

		public Field() {
		}

		public String getComment() {
			return comment;
		}

		public Field setComment(String comment) {
			this.comment = comment;
			return this;
		}

		public boolean isPrimarily() {
			return primarily;
		}

		public Field setPrimarily(boolean primarily) {
			this.primarily = primarily;
			return this;
		}

		public boolean isRequired() {
			return required;
		}

		public Field setRequired(boolean required) {
			this.required = required;
			return this;
		}

		public String getName() {
			return name;
		}

		public Field setName(String name) {
			this.name = name;
			return this;
		}

		public Type getType() {
			return type;
		}

		public Field setType(Type type) {
			this.type = type;
			return this;
		}

		@Override
		public String toString() {
			return "Field [name=" + name + ", type=" + type + ", required=" + required + ", primarily=" + primarily
					+ "]";
		}
		
	}
}
