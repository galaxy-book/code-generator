package org.nico.codegenerator.render;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.StringTemplateResourceLoader;
import org.nico.codegenerator.parser.MysqlParser;
import org.nico.codegenerator.parser.entity.Data;
import org.nico.codegenerator.render.function.PrintCapitalize;
import org.nico.codegenerator.render.function.PrintHump;
import org.nico.ourbatis.utils.StreamUtils;

import net.sf.jsqlparser.JSQLParserException;

public class Test {

	public static void main(String[] args) throws IOException, JSQLParserException {
		//初始化代码
		StringTemplateResourceLoader resourceLoader = new StringTemplateResourceLoader();
		Configuration cfg = Configuration.defaultConfiguration();
		GroupTemplate gt = new GroupTemplate(resourceLoader, cfg);
		gt.registerFunction("printHump", new PrintHump());
		gt.registerFunction("printCapitalize", new PrintCapitalize());
		
		//获取模板
		Template t = gt.getTemplate(StreamUtils.convertToString(Test.class.getClassLoader().getResourceAsStream("test.tmp")));
		
		List<Data> ds = new MysqlParser().parse("CREATE TABLE `ppm_pro_project` (\r\n" + 
				"  `id` bigint(20) NOT NULL COMMENT '主键',\r\n" + 
				"  `org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '组织id,应该是全局的,因此填0',\r\n" + 
				"  `code` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '编号',\r\n" + 
				"  `name` varchar(256) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '名称',\r\n" + 
				"  `pre_code` varchar(16) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '前缀编号',\r\n" + 
				"  `owner` bigint(20) NOT NULL DEFAULT '0' COMMENT '项目负责人',\r\n" + 
				"  `project_type_id` bigint(20) NOT NULL DEFAULT '1' COMMENT '项目类型',\r\n" + 
				"  `priority_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '项目优先级',\r\n" + 
				"  `public_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '项目公开性,1公开,2私有',\r\n" + 
				"  `resource_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '项目标识',\r\n" + 
				"  `remark` varchar(512) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '描述',\r\n" + 
				"  `status` bigint(20) NOT NULL DEFAULT '0' COMMENT '项目状态,从状态表取',\r\n" + 
				"  `creator` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',\r\n" + 
				"  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\r\n" + 
				"  `updator` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',\r\n" + 
				"  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\r\n" + 
				"  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',\r\n" + 
				"  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除,1是,0否',\r\n" + 
				"  PRIMARY KEY (`id`),\r\n" + 
				"  KEY `index_ppm_pro_project_org_id` (`org_id`),\r\n" + 
				"  KEY `index_ppm_pro_project_pre_code` (`pre_code`),\r\n" + 
				"  KEY `index_ppm_pro_project_code` (`code`),\r\n" + 
				"  KEY `index_ppm_pro_project_owner` (`owner`),\r\n" + 
				"  KEY `index_ppm_pro_project_name` (`name`),\r\n" + 
				"  KEY `index_ppm_pro_project_create_time` (`create_time`)\r\n" + 
				") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='项目';\r\n" + 
				"\r\n" + 
				"");
		
		
		
		Map<String, Object> properties = new HashMap<>();
		properties.put("package", "resolvers");
		
		ds.get(0).setProperties(properties);
		t.binding("r", ds.get(0));
		
		//渲染结果
		String str = t.render();
//		System.out.println(str);
		
	}
}
