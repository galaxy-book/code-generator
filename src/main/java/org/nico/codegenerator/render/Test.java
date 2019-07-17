package org.nico.codegenerator.render;

import java.io.IOException;
import java.util.Arrays;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;

public class Test {

	public static void main(String[] args) throws IOException {
		//初始化代码
		StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
		Configuration cfg = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		//获取模板
		Template t = gt.getTemplate("<%\r\n" + 
				"for(user in userList){\r\n" + 
				"        println(userLP.index);\r\n" + 
				"}\r\n" + 
				"%>");
		t.binding("userList", Arrays.asList(1,2,3,4));
		//渲染结果
		String str = t.render();
		System.out.println(str);
	}
}
