package org.nico.codegenerator.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.nico.codegenerator.consts.AppConsts;
import org.nico.codegenerator.consts.DelFlag;
import org.nico.codegenerator.consts.RespCode;
import org.nico.codegenerator.mapper.ModelMapper;
import org.nico.codegenerator.mapper.ProjectMapper;
import org.nico.codegenerator.model.po.Model;
import org.nico.codegenerator.model.po.Project;
import org.nico.codegenerator.model.po.Template;
import org.nico.codegenerator.model.vo.CreateModelReqVo;
import org.nico.codegenerator.model.vo.CreateModelRespVo;
import org.nico.codegenerator.model.vo.GetModelRespVo;
import org.nico.codegenerator.model.vo.GetTemplateRespVo;
import org.nico.codegenerator.model.vo.ListVo;
import org.nico.codegenerator.model.vo.RespVo;
import org.nico.codegenerator.parser.AbstractParser;
import org.nico.codegenerator.parser.entity.Data;
import org.nico.codegenerator.utils.HttpContextUtils;
import org.nico.codegenerator.utils.ModelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ModelService {

	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RespVo<?> create(CreateModelReqVo modelReqVo){
		Long projectId = modelReqVo.getProjectId();
		Long currentUserId = HttpContextUtils.getUserId();
		
		Project query = new Project();
		query.setId(projectId);
		query.setDeleted(DelFlag.NOTDEL.getCode());
		query.setUserId(currentUserId);
		Project project = projectMapper.selectEntity(query);
		if(project == null) {
			return RespVo.failure(RespCode.PROJECT_NOT_EXIST);
		}
		
		List<Data> datas;
		try {
			datas = AbstractParser.getParser(modelReqVo.getDatasType()).parse(modelReqVo.getDatas());
			List<Model> beInsertedModels = datas.stream().map(data -> {
				Model m = new Model();
				m.setName(data.getName());
				m.setComment(data.getComment());
				m.setProjectId(projectId);
				m.setContent(JSON.toJSONString(data.getFields()));
				m.setDeleted(DelFlag.NOTDEL.getCode());
				m.setUserId(currentUserId);
				return m;
			}).collect(Collectors.toList());
			
			if(beInsertedModels.size() > 0) {
				modelMapper.insertBatch(beInsertedModels);
			}
			
			return RespVo.success();
		} catch (Exception e) {
			e.printStackTrace();
			return RespVo.failure(RespCode.FAILURE);
		}
		
	}
	
	public RespVo<?> delete(Long id){
		Long currentUserId = HttpContextUtils.getUserId();
		
		Model query = new Model();
		query.setId(id);
		query.setDeleted(DelFlag.NOTDEL.getCode());
		query.setUserId(currentUserId);
		if(modelMapper.selectCount(query) == 0) {
			return RespVo.failure(RespCode.MODEL_NOT_EXIST);
		}
		Model model = new Model();
		model.setId(id);
		model.setDeleted(DelFlag.DELED.getCode());
		int row = modelMapper.updateSelective(model);
		if(row > 0) {
			return RespVo.success();	
		}else {
			return RespVo.failure(RespCode.UPDATE_FAILURE);
		}
	}
	
	public RespVo<ListVo<GetModelRespVo>> listOfPage(Long projectId, int page, int size){
		Page<Model> pages = PageHelper.startPage(page, size, "create_time desc");
		Model query = new Model();
		if(projectId != null) {
			query.setProjectId(projectId);
		}
		query.setUserId(HttpContextUtils.getUserId());
		query.setDeleted(DelFlag.NOTDEL.getCode());
		modelMapper.selectList(query);
		return RespVo.success(ListVo.list(ModelUtils.convert(pages.getResult(), GetModelRespVo.class), pages.getTotal()));
	}
}
