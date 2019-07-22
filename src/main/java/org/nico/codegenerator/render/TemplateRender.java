package org.nico.codegenerator.render;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.nico.codegenerator.parser.entity.Data;
import org.nico.codegenerator.render.function.ConvertType;
import org.nico.codegenerator.render.function.PrintCapitalize;
import org.nico.codegenerator.render.function.PrintHump;

public class TemplateRender {

	private GroupTemplate gt;
	
	private volatile static TemplateRender instance;
	
	private TemplateRender() throws IOException {
		init();
	}
	
	public static TemplateRender getInstance() throws IOException {
		if(instance == null) {
			synchronized (TemplateRender.class) {
				if(instance == null) {
					instance = new TemplateRender();
				}
			}
		}
		return instance;
	}
	
	public void init() throws IOException {
		StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
		Configuration cfg = Configuration.defaultConfiguration();
		gt = new GroupTemplate(resourceLoader, cfg);
		gt.registerFunction("printHump", new PrintHump());
		gt.registerFunction("printCapitalize", new PrintCapitalize());
		gt.registerFunction("convertType", new ConvertType());
	}
	
	public String rending(String template, Map<String, Object> datas) {
		Template t = gt.getTemplate(template);
		t.binding(datas);
		return t.render();
	}
	
	public boolean isValid(String template) {
		Template t = gt.getTemplate(template);
		return null == t.validate();
	}

	public GroupTemplate getGt() {
		return gt;
	}
}
