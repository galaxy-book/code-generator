package org.nico.codegenerator.parser;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.nico.codegenerator.parser.entity.Data;

public abstract class AbstractParser {

	public static final char UNDERLINE='_';
	
	public abstract List<Data> parse(String input) throws Exception;
	
	protected String all2Slide(String value) {
		if (StringUtils.isBlank(value)){
            return "";
        }
        int len = value.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = value.charAt(i);
            
            if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_') {
            	 if (Character.isUpperCase(c)){
                 	if(i > 0) {
                 		sb.append(UNDERLINE);
                 	}
                     sb.append(Character.toLowerCase(c));
                 }else{
                     sb.append(c);
                 }
            }
        }
        return sb.toString();
	}
}
