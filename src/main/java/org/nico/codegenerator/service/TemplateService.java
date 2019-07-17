package org.nico.codegenerator.service;

import org.nico.codegenerator.consts.DelFlag;
import org.nico.codegenerator.consts.RespCode;
import org.nico.codegenerator.mapper.ProjectMapper;
import org.nico.codegenerator.mapper.TemplateMapper;
import org.nico.codegenerator.model.po.Project;
import org.nico.codegenerator.model.po.Template;
import org.nico.codegenerator.model.vo.CreateTemplateReqVo;
import org.nico.codegenerator.model.vo.CreateTemplateRespVo;
import org.nico.codegenerator.model.vo.GetProjectRespVo;
import org.nico.codegenerator.model.vo.GetTemplateRespVo;
import org.nico.codegenerator.model.vo.ListVo;
import org.nico.codegenerator.model.vo.RespVo;
import org.nico.codegenerator.model.vo.UpdateTemplateReqVo;
import org.nico.codegenerator.utils.HttpContextUtils;
import org.nico.codegenerator.utils.ModelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class TemplateService {

	@Autowired
	private TemplateMapper templateMapper;
	
	@Autowired
	private ProjectMapper projectMapper;

	public RespVo<CreateTemplateRespVo> create(CreateTemplateReqVo templateReqVo){
		Project query = new Project();
		query.setId(templateReqVo.getProjectId());
		query.setDeleted(DelFlag.NOTDEL.getCode());
		if(projectMapper.selectCount(query) > 0) {
			return RespVo.failure(RespCode.PROJECT_NOT_EXIST);
		}
		Template template = ModelUtils.convert(templateReqVo, Template.class);
		int row = templateMapper.insertSelective(template);
		if(row > 0) {
			return RespVo.success(ModelUtils.convert(template, CreateTemplateRespVo.class));
		}else {
			return RespVo.failure(RespCode.INSERT_FAILURE);
		}
	}
	
	public RespVo<?> delete(Long id){
		Template query = new Template();
		query.setId(id);
		query.setDeleted(DelFlag.NOTDEL.getCode());
		Template template = templateMapper.selectEntity(query);
		if(template == null) {
			return RespVo.failure(RespCode.TEMPLATE_NOT_EXIST);
		}
		
		Project projectQuery = new Project();
		projectQuery.setId(template.getProjectId());
		projectQuery.setUserId(HttpContextUtils.getUserId());
		projectQuery.setDeleted(DelFlag.DELED.getCode());
		Project project = projectMapper.selectEntity(projectQuery);
		if(project == null) {
			return RespVo.failure(RespCode.TEMPLATE_PROJECT_NOT_EXIST);
		}
		
		Template updateQuery = new Template();
		updateQuery.setId(id);
		updateQuery.setDeleted(DelFlag.NOTDEL.getCode());
		int row = templateMapper.updateSelective(updateQuery);
		if(row > 0) {
			return RespVo.success();	
		}else {
			return RespVo.failure(RespCode.UPDATE_FAILURE);
		}
	}
	
	public RespVo<?> update(Long id, UpdateTemplateReqVo templateReqVo){
		Template query = new Template();
		query.setId(id);
		query.setDeleted(DelFlag.NOTDEL.getCode());
		Template template = templateMapper.selectEntity(query);
		if(template == null) {
			return RespVo.failure(RespCode.TEMPLATE_NOT_EXIST);
		}
		
		Project projectQuery = new Project();
		projectQuery.setId(template.getProjectId());
		projectQuery.setUserId(HttpContextUtils.getUserId());
		projectQuery.setDeleted(DelFlag.DELED.getCode());
		Project project = projectMapper.selectEntity(projectQuery);
		if(project == null) {
			return RespVo.failure(RespCode.TEMPLATE_PROJECT_NOT_EXIST);
		}
		
		if(templateReqVo.getProjectId() != null) {
			projectQuery = new Project();
			projectQuery.setUserId(HttpContextUtils.getUserId());
			projectQuery.setId(template.getProjectId());
			projectQuery.setDeleted(DelFlag.NOTDEL.getCode());
			project = projectMapper.selectEntity(projectQuery);
			if(project == null) {
				return RespVo.failure(RespCode.TEMPLATE_PROJECT_NOT_EXIST);
			}
		}
		
		Template updateQuery = ModelUtils.convert(templateReqVo, Template.class);
		updateQuery.setId(id);
		int row = templateMapper.updateSelective(updateQuery);
		
		if(row > 0) {
			return RespVo.success();	
		}else {
			return RespVo.failure(RespCode.UPDATE_FAILURE);
		}
	}
	
	public RespVo<ListVo<GetTemplateRespVo>> listOfPage(Long projectId, int page, int size){
		Page<Template> pages = PageHelper.startPage(page, size, "create_time desc");
		Template query = new Template();
		query.setProjectId(projectId);
		query.setDeleted(DelFlag.NOTDEL.getCode());
		templateMapper.selectList(query);
		return RespVo.success(ListVo.list(ModelUtils.convert(pages.getResult(), GetTemplateRespVo.class), pages.getTotal()));
	}
}
