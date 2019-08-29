package org.nico.codegenerator.parser;

import java.util.List;

import org.nico.codegenerator.parser.entity.Data;

import com.alibaba.fastjson.JSON;

import net.sf.jsqlparser.JSQLParserException;

public class Test {

	public static void main(String[] args) throws JSQLParserException {
		List<Data> datas = new MysqlParser().parse("CREATE TABLE `ppm_org_user_out_info` (\r\n" + 
				"  `id` bigint(20) NOT NULL COMMENT '主键',\r\n" + 
				"  `org_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '组织id',\r\n" + 
				"  `user_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '用户id',\r\n" + 
				"  `source_channel` varchar(16) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '来源渠道',\r\n" + 
				"  `out_org_user_id` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '外部组织用户id',\r\n" + 
				"  `out_user_id` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '外部用户唯一id',\r\n" + 
				"  `name` varchar(64) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '名字',\r\n" + 
				"  `avatar` varchar(1024) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '用户头像',\r\n" + 
				"  `is_active` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否已激活,1已激活,2未激活',\r\n" + 
				"  `job_number` varchar(32) COLLATE utf8mb4_bin NOT NULL DEFAULT '' COMMENT '工号',\r\n" + 
				"  `status` tinyint(4) NOT NULL DEFAULT '1' COMMENT '状态, 1可用,2禁用',\r\n" + 
				"  `creator` bigint(20) NOT NULL DEFAULT '0' COMMENT '创建人',\r\n" + 
				"  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\r\n" + 
				"  `updator` bigint(20) NOT NULL DEFAULT '0' COMMENT '更新人',\r\n" + 
				"  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',\r\n" + 
				"  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',\r\n" + 
				"  `is_delete` tinyint(4) NOT NULL DEFAULT '2' COMMENT '是否删除,1是,2否',\r\n" + 
				"  PRIMARY KEY (`id`),\r\n" + 
				"  KEY `index_ppm_org_user_out_info_org_id` (`org_id`),\r\n" + 
				"  KEY `index_ppm_org_user_out_info_user_id` (`user_id`),\r\n" + 
				"  KEY `index_ppm_org_user_out_info_out_org_user_id` (`out_org_user_id`),\r\n" + 
				"  KEY `index_ppm_org_user_out_info_out_user_id` (`out_user_id`),\r\n" + 
				"  KEY `index_ppm_org_user_out_info_create_time` (`create_time`)\r\n" + 
				") ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin COMMENT='用户外部信息';\r\n" + 
				"\r\n" + 
				"");
		
		System.out.println(datas.get(0).getFields());
		
		String d = JSON.toJSONString(datas);
		System.out.println(d);
		
	}
}
