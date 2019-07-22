package org.nico.codegenerator.parser;

import java.util.List;

import org.nico.codegenerator.parser.entity.Data;

import com.alibaba.fastjson.JSON;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.create.table.CreateTable;

public class Test {

	public static void main(String[] args) throws JSQLParserException {
		List<Data> datas = new MysqlParser().parse("CREATE TABLE `ppm_bas_app_info` (\r\n" + 
				"  `id` bigint(20) NOT NULL COMMENT '主键',\r\n" + 
				"  `name` varchar(32) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '名称',\r\n" + 
				"  `code` varchar(32) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '应用编号',\r\n" + 
				"  `secret1` varchar(32) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '秘钥1',\r\n" + 
				"  `secret2` varchar(32) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '秘钥2',\r\n" + 
				"  `owner` varchar(0) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '负责人',\r\n" + 
				"  `check_status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '审核状态,1待审核,2审核通过,3审核未通过',\r\n" + 
				"  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态,1可用,0不可用',\r\n" + 
				"  `creator` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',\r\n" + 
				"  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\r\n" + 
				"  `updator` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',\r\n" + 
				"  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\r\n" + 
				"  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',\r\n" + 
				"  `is_delete` tinyint(4) NOT NULL DEFAULT '0' COMMENT '是否删除,1是,0否',\r\n" + 
				"  PRIMARY KEY (`id`),\r\n" + 
				"  KEY `index_ppm_bas_app_info_create_time` (`create_time`),\r\n" + 
				"  KEY `index_ppm_bas_app_info_code` (`code`)\r\n" + 
				") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='接入应用信息';" + 
				"");
		
		System.out.println(datas.get(0).getFields());
		
		String d = JSON.toJSONString(datas);
		System.out.println(d);
		
	}
}
