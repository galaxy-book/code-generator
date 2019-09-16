package org.nico.codegenerator.controller;

import java.io.IOException;

import org.nico.codegenerator.config.BaseConfig;
import org.nico.codegenerator.consts.RespCode;
import org.nico.codegenerator.model.vo.CreateModelReqVo;
import org.nico.codegenerator.model.vo.GetModelRespVo;
import org.nico.codegenerator.model.vo.ListVo;
import org.nico.codegenerator.model.vo.RespVo;
import org.nico.codegenerator.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/models")
public class ModelController {

	@Autowired
	private ModelService modelService;
	
	
	@ApiOperation(value = "创建模型")
	@PostMapping
	public RespVo<?> post(@RequestBody CreateModelReqVo modelReqVo) throws IOException{
	    return modelService.create(modelReqVo);
	}
	
	@ApiOperation(value = "删除模型")
	@DeleteMapping("/{id}")
	public RespVo<?> del(@PathVariable Long id){
	    return modelService.delete(id);
	}
	
	@ApiOperation(value = "获取模板列表")
	@GetMapping
	public RespVo<ListVo<GetModelRespVo>> list(
			@RequestParam(required = false) Long projectId,
			@RequestParam int page, 
            @RequestParam int size){
		if(size > BaseConfig.pageMaxLength) {
            return RespVo.failure(RespCode.PARAMS_OVERFLOW_LIMIT, "size", "0", BaseConfig.pageMaxLength);
        }
	    return modelService.listOfPage(projectId, page, size);
	}
	
	
}
