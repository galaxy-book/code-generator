package org.nico.codegenerator.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.nico.codegenerator.parser.entity.Data;
import org.nico.codegenerator.parser.entity.Data.Field;
import org.nico.codegenerator.parser.entity.Type;
import org.nico.codegenerator.utils.NameUtils;
import org.springframework.util.CollectionUtils;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.CreateTable;

public class MysqlParser extends AbstractParser{

	public final static Map<String, Type> MYSQL_TYPE_MAP = new HashMap<String, Type>() {
		private static final long serialVersionUID = 1L;
		{
			put("int", Type.INT);
			put("integer", Type.INT);
			put("tinyint", Type.INT);
			put("smallint", Type.INT);
			put("mediumint", Type.INT);
			put("bigint", Type.LONG);
			put("int unsigned", Type.INT);
			put("integer unsigned", Type.INT);
			put("tinyint unsigned", Type.INT);
			put("smallint unsigned", Type.INT);
			put("mediumint unsigned", Type.INT);
			put("bigint unsigned", Type.LONG);
			put("bit", Type.INT);
			put("bool", Type.BOOL);
			put("enum", Type.STRING);
			put("set", Type.STRING);
			put("varchar", Type.STRING);
			put("char", Type.STRING);
			put("tinytext", Type.STRING);
			put("mediumtext", Type.STRING);
			put("text", Type.STRING);
			put("longtext", Type.STRING);
			put("blob", Type.STRING);
			put("tinyblob", Type.STRING);
			put("mediumblob", Type.STRING);
			put("longblob", Type.STRING);
			put("date", Type.DATE);
			put("datetime", Type.DATE);
			put("timestamp", Type.DATE);
			put("datetime", Type.DATE);
			put("time", Type.STRING);
			put("float", Type.FLOAT);
			put("double", Type.DOUBLE);
			put("decimal", Type.BIGDECIMAL);
			put("binary", Type.STRING);
			put("varbinary", Type.STRING);
		}
	};
	
	@Override
	public List<Data> parse(String input) throws JSQLParserException {
		String[] ddls = input.split(";" + System.lineSeparator());
		
		List<Data> datas = new ArrayList<>(ddls.length);
		for(String ddl: ddls) {
			if(StringUtils.isBlank(ddl)) {
				continue;
			}
			ddl += ";";
			ddl = ddl.trim();
			
			CreateTable c = (CreateTable) CCJSqlParserUtil.parse(ddl);
			
			Data data = new Data();
			data.setName(NameUtils.all2Slide(c.getTable().getName()));
			
			List<String> primaryKeyList = new ArrayList<>();
			c.getIndexes().stream().filter(e -> e.getType().equalsIgnoreCase("PRIMARY KEY")).forEach(e -> {
				e.getColumnsNames().forEach(key -> {
					primaryKeyList.add(NameUtils.all2Slide(key));
				});
			});
			
			List<Field> fields = new ArrayList<>(c.getColumnDefinitions().size());
			StringBuilder specBuilder = new StringBuilder();
			c.getColumnDefinitions().forEach(col -> {
				if(! CollectionUtils.isEmpty(col.getColumnSpecStrings())) {
					col.getColumnSpecStrings().forEach(s -> specBuilder.append(s.toUpperCase() + " "));
				}
				String name = NameUtils.all2Slide(col.getColumnName());
				
				boolean required = specBuilder.toString().contains("NOT NULL");
				boolean primarily = primaryKeyList.contains(name);
				fields.add(new Field(name, MYSQL_TYPE_MAP.get(col.getColDataType().getDataType().toLowerCase()), required, primarily));
			});
			data.setFields(fields);
			datas.add(data);
		}
		
		return datas;
	}
	
}
