package org.nico.codegenerator.parser.entity;

import java.util.List;

public class Data {
	
	private List<Field> fields;
	
	private String name;
	
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

		public Field() {
		}

		public Field(String name, Type type, boolean required) {
			this.name = name;
			this.type = type;
			this.required = required;
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
		
	}
}
