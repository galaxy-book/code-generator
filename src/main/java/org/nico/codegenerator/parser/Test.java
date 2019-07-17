package org.nico.codegenerator.parser;

import com.alibaba.fastjson.JSON;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.CreateTable;

public class Test {

	public static void main(String[] args) throws JSQLParserException {
		String d = JSON.toJSONString(new MysqlParser().parse("CREATE TABLE `ppm_bas_object_id` (\r\n" + 
				"  `id` bigint(20) NOT NULL COMMENT '主键',\r\n" + 
				"  `org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '组织id',\r\n" + 
				"  `code` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '对象编号',\r\n" + 
				"  `max_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前最大编号',\r\n" + 
				"  `step` int(11) NOT NULL DEFAULT '100' COMMENT '步长',\r\n" + 
				"  `creator` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',\r\n" + 
				"  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\r\n" + 
				"  `updator` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',\r\n" + 
				"  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\r\n" + 
				"  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',\r\n" + 
				"  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除,1是,0否',\r\n" + 
				"  PRIMARY KEY (`id`),\r\n" + 
				"  KEY `index_ppm_bas_object_id_create_time` (`create_time`),\r\n" + 
				"  KEY `index_ppm_bas_object_id_org_id` (`org_id`)\r\n" + 
				") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='对象编号生成';\r\n" + 
				"\r\n" + 
				""));
		System.out.println(d);
		
	}
}
