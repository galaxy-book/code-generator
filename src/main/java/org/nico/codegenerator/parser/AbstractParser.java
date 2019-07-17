package org.nico.codegenerator.parser;

import java.util.List;

import org.nico.codegenerator.parser.entity.Data;

public abstract class AbstractParser {

	public abstract List<Data> parse(String input) throws Exception;
	
}
