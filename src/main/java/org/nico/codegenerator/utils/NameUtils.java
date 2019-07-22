package org.nico.codegenerator.utils;

import java.nio.charset.Charset;

import org.apache.commons.lang3.StringUtils;

public class NameUtils {

	public static final char UNDERLINE='_';

	public static String capitalize(String value) {
		return Character.toUpperCase(value.charAt(0)) + value.substring(1);
	}
	
	public static String all2Slide(String value) {
		if (StringUtils.isBlank(value)){
			return "";
		}
		int len = value.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = value.charAt(i);

			if((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || (c >= '0' && c <= '9') || c == '_') {
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

	public static String all2Hump(String value) {
		int len = value.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = value.charAt(i);
			if (c == UNDERLINE){
				if(i < len - 1) {
					sb.append(Character.toUpperCase(value.charAt(++ i)));
				}
			}else{
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(capitalize("abc"));
	}
}
