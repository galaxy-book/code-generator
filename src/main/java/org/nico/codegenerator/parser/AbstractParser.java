package org.nico.codegenerator.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nico.codegenerator.parser.entity.Data;

public abstract class AbstractParser {

	public final static Map<String, AbstractParser> SUPPORT_PARSER = new HashMap<>();
	static {
		SUPPORT_PARSER.put("MYSQL", new MysqlParser());
	}
	
	public abstract List<Data> parse(String input) throws Exception;
	
	public static AbstractParser getParser(String type) {
		return SUPPORT_PARSER.get(type);
	}
}
