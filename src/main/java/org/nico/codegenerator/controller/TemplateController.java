package org.nico.codegenerator.controller;

import org.apache.commons.lang3.StringUtils;
import org.nico.codegenerator.config.BaseConfig;
import org.nico.codegenerator.consts.RespCode;
import org.nico.codegenerator.model.vo.CreateTemplateReqVo;
import org.nico.codegenerator.model.vo.CreateTemplateRespVo;
import org.nico.codegenerator.model.vo.GetProjectRespVo;
import org.nico.codegenerator.model.vo.GetTemplateRespVo;
import org.nico.codegenerator.model.vo.ListVo;
import org.nico.codegenerator.model.vo.RespVo;
import org.nico.codegenerator.model.vo.UpdateProjectReqVo;
import org.nico.codegenerator.model.vo.UpdateTemplateReqVo;
import org.nico.codegenerator.service.ProjectService;
import org.nico.codegenerator.service.TemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/templates")
public class TemplateController {

	@Autowired
	private TemplateService templateService;
	
	@ApiOperation(value = "创建模板")
	@PostMapping
	public RespVo<CreateTemplateRespVo> post(@RequestBody CreateTemplateReqVo templateReqVo){
	    String name = templateReqVo.getName();
	    String suffix = templateReqVo.getSuffix();
	    String content = templateReqVo.getContent();
	    String desc = templateReqVo.getDesc();		
	    Long projectId = templateReqVo.getProjectId();
	    if(StringUtils.isBlank(name) || name.length() > 50) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "name");
	    }
	    if(StringUtils.isBlank(suffix) || suffix.length() > 20) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "suffix");
	    }
	    if(StringUtils.isNotBlank(desc) && desc.length() > 1000) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "desc");
	    }
	    if(StringUtils.isBlank(content)) {
	    	return RespVo.failure(RespCode.PARAMS_ERROR, "content");
        }
	    if(projectId == null) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "projectId");
	    }
	    return templateService.create(templateReqVo);
	}
	
	@ApiOperation(value = "删除模板")
	@DeleteMapping("/{id}")
	public RespVo<?> del(@PathVariable Long id){
	    return templateService.delete(id);
	}
	
	@ApiOperation(value = "更新模板")
	@PutMapping("/{id}")
	public RespVo<?> put(@PathVariable Long id, @RequestBody UpdateTemplateReqVo templateReqVo){
		String name = templateReqVo.getName();
	    String suffix = templateReqVo.getSuffix();
	    String desc = templateReqVo.getDesc();		
	    if(StringUtils.isNotBlank(name) && name.length() > 50) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "name");
	    }
	    if(StringUtils.isNotBlank(suffix) && suffix.length() > 20) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "suffix");
	    }
	    if(StringUtils.isNotBlank(desc) && desc.length() > 1000) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "desc");
	    }
	    return templateService.update(id, templateReqVo);
	}
}
