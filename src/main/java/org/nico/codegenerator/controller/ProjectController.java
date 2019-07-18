package org.nico.codegenerator.controller;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.nico.codegenerator.config.BaseConfig;
import org.nico.codegenerator.consts.RespCode;
import org.nico.codegenerator.model.vo.CreateProjectReqVo;
import org.nico.codegenerator.model.vo.CreateProjectRespVo;
import org.nico.codegenerator.model.vo.GenerateProjectReqVo;
import org.nico.codegenerator.model.vo.GetProjectRespVo;
import org.nico.codegenerator.model.vo.GetTemplateRespVo;
import org.nico.codegenerator.model.vo.ListVo;
import org.nico.codegenerator.model.vo.RespVo;
import org.nico.codegenerator.model.vo.UpdateProjectReqVo;
import org.nico.codegenerator.parser.AbstractParser;
import org.nico.codegenerator.service.ProjectService;
import org.nico.codegenerator.service.TemplateService;
import org.nico.codegenerator.utils.HttpContextUtils;
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
@RequestMapping("/projects")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private TemplateService templateService;
	
	@ApiOperation(value = "创建项目")
	@PostMapping
	public RespVo<CreateProjectRespVo> post(@RequestBody CreateProjectReqVo projectReqVo){
	    String name = projectReqVo.getName();
	    String properties = projectReqVo.getProperties();
	    String desc = projectReqVo.getDesc();		
	    if(StringUtils.isBlank(name) || name.length() > 20) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "name");
	    }
	    if(StringUtils.isNotBlank(desc) && desc.length() > 1000) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "desc");
	    }
	    if(StringUtils.isNotBlank(properties) && !JSON.isValid(properties)) {
            return RespVo.failure(RespCode.PARAMS_ERROR_WITH_REASON, "properties", "Json格式非法");
        }
	    return projectService.create(projectReqVo);
	}
	
	@ApiOperation(value = "删除项目")
	@DeleteMapping("/{id}")
	public RespVo<?> del(@PathVariable Long id){
	    return projectService.delete(id);
	}
	
	@ApiOperation(value = "获取项目详情")
	@GetMapping("/{id}")
	public RespVo<GetProjectRespVo> get(@PathVariable Long id){
	    return projectService.get(id);
	}
	
	@ApiOperation(value = "更新项目")
	@PutMapping("/{id}")
	public RespVo<?> put(@PathVariable Long id, @RequestBody UpdateProjectReqVo projectReqVo){
		String name = projectReqVo.getName();
	    String properties = projectReqVo.getProperties();
	    String desc = projectReqVo.getDesc();		
	    if(StringUtils.isNotBlank(name) && name.length() > 20) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "name");
	    }
	    if(StringUtils.isNotBlank(desc) && desc.length() > 1000) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "desc");
	    }
	    if(StringUtils.isNotBlank(properties) && !JSON.isValid(properties)) {
            return RespVo.failure(RespCode.PARAMS_ERROR_WITH_REASON, "properties", "Json格式非法");
        }
	    if(StringUtils.isNotBlank(properties) && !JSON.isValid(properties)) {
            return RespVo.failure(RespCode.PARAMS_ERROR_WITH_REASON, "properties", "Json格式非法");
        }
	    return projectService.update(id, projectReqVo);
	}
	
	@ApiOperation(value = "获取模板")
	@GetMapping("/{projectId}/templates")
	public RespVo<ListVo<GetTemplateRespVo>> getTemplates(
			@PathVariable Long projectId,
			@RequestParam int page, 
            @RequestParam int size){
		if(size > BaseConfig.pageMaxLength) {
            return RespVo.failure(RespCode.PARAMS_OVERFLOW_LIMIT, "size", "0", BaseConfig.pageMaxLength);
        }
	    return templateService.listOfPage(projectId, page, size);
	}
	
	@ApiOperation(value = "获取项目")
	@GetMapping
	public RespVo<ListVo<GetProjectRespVo>> list(
			@RequestParam int page, 
            @RequestParam int size){
		if(size > BaseConfig.pageMaxLength) {
            return RespVo.failure(RespCode.PARAMS_OVERFLOW_LIMIT, "size", "0", BaseConfig.pageMaxLength);
        }
	    return projectService.listOfPage(HttpContextUtils.getUserId(), page, size);
	}
	
	@ApiOperation(value = "生成项目")
	@PostMapping("/{projectId}/generate")
	public RespVo<?> generate(
			@PathVariable Long projectId,
			@RequestBody GenerateProjectReqVo generateReqVo,
			HttpServletResponse response){
		String datasType = generateReqVo.getDatasType();
		String datas = generateReqVo.getDatas();
		
		if(! AbstractParser.SUPPORT_PARSER.containsKey(datasType.toUpperCase())) {
			return RespVo.failure(RespCode.PARAMS_ERROR, "datasType, support: " + AbstractParser.SUPPORT_PARSER.keySet());
		}
		
		if(StringUtils.isBlank(datas)) {
	        return RespVo.failure(RespCode.PARAMS_ERROR, "datas");
	    }
		
	    return projectService.generate(projectId, generateReqVo, response);
	}
}
