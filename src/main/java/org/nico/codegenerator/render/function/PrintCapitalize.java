package org.nico.codegenerator.render.function;

import org.beetl.core.Context;
import org.beetl.core.Function;
import org.nico.codegenerator.utils.NameUtils;

public class PrintCapitalize implements Function{

	@Override
	public Object call(Object[] paras, Context ctx) {
		Object o = paras[0];
	    return NameUtils.capitalize(String.valueOf(o));
	}

}
