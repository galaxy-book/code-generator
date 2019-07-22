package org.nico.codegenerator.render.function;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.nico.codegenerator.parser.entity.Type;

public class ConvertType implements Function{

	@Override
	public Object call(Object[] paras, Context ctx) {
		if(paras.length < 2) {
			return "#ERR: convertType need 2 params!";
		}
		Object o = paras[0];
		Object t = paras[1];

		Type type = Type.valueOf(String.valueOf(o));
		switch(String.valueOf(t).toUpperCase()) {
		case "MYSQL":
			return type.getSqlType();
		case "GOLANG":
			return type.getGoType();
		case "JAVA":
			return type.getJavaType();
		case "GRAPHQL":
			return type.getGraphqlType();
		default:
			return o;
		}

	}

}
