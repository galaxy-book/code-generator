package org.nico.codegenerator.parser.entity;

import java.util.List;
import java.util.Map;

public class Data {
	
	private List<Field> fields;
	
	private Map<String, Object> properties;
	
	private String name;
	
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

		public Field() {
		}

		public Field(String name, Type type, boolean required, boolean primarily) {
			this.name = name;
			this.type = type;
			this.required = required;
			this.primarily = primarily;
		}

		public boolean isPrimarily() {
			return primarily;
		}

		public void setPrimarily(boolean primarily) {
			this.primarily = primarily;
		}

		public boolean isRequired() {
			return required;
		}

		public void setRequired(boolean required) {
			this.required = required;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Type getType() {
			return type;
		}

		public void setType(Type type) {
			this.type = type;
		}

		@Override
		public String toString() {
			return "Field [name=" + name + ", type=" + type + ", required=" + required + ", primarily=" + primarily
					+ "]";
		}
		
	}
}
