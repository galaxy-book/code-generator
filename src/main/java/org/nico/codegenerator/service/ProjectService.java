package org.nico.codegenerator.service;

import org.nico.codegenerator.consts.DelFlag;
import org.nico.codegenerator.consts.RespCode;
import org.nico.codegenerator.mapper.ProjectMapper;
import org.nico.codegenerator.model.po.Project;
import org.nico.codegenerator.model.vo.CreateProjectReqVo;
import org.nico.codegenerator.model.vo.CreateProjectRespVo;
import org.nico.codegenerator.model.vo.GetProjectRespVo;
import org.nico.codegenerator.model.vo.ListVo;
import org.nico.codegenerator.model.vo.RespVo;
import org.nico.codegenerator.model.vo.UpdateProjectReqVo;
import org.nico.codegenerator.utils.HttpContextUtils;
import org.nico.codegenerator.utils.ModelUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ProjectService {

	@Autowired
	private ProjectMapper projectMapper;

	public RespVo<CreateProjectRespVo> create(CreateProjectReqVo projectReqVo){
		Project project = ModelUtils.convert(projectReqVo, Project.class);
		project.setUserId(HttpContextUtils.getUserId());
		int row = projectMapper.insertSelective(project);
		if(row > 0) {
			return RespVo.success(ModelUtils.convert(project, CreateProjectRespVo.class));	
		}else {
			return RespVo.failure(RespCode.INSERT_FAILURE);
		}
	}
	
	public RespVo<?> delete(Long id){
		Project query = new Project();
		query.setId(id);
		query.setDeleted(DelFlag.NOTDEL.getCode());
		query.setUserId(HttpContextUtils.getUserId());
		if(projectMapper.selectCount(query) == 0) {
			return RespVo.failure(RespCode.PROJECT_NOT_EXIST);
		}
		Project project = new Project();
		project.setId(id);
		project.setDeleted(DelFlag.DELED.getCode());
		int row = projectMapper.updateSelective(project);
		if(row > 0) {
			return RespVo.success();	
		}else {
			return RespVo.failure(RespCode.UPDATE_FAILURE);
		}
	}
	
	public RespVo<GetProjectRespVo> get(Long id){
		Project query = new Project();
		query.setId(id);
		query.setDeleted(DelFlag.NOTDEL.getCode());
		query.setUserId(HttpContextUtils.getUserId());
		
		Project project = projectMapper.selectEntity(query);
		if(project == null) {
			return RespVo.failure(RespCode.PROJECT_NOT_EXIST);
		}
		return RespVo.success(ModelUtils.convert(project, GetProjectRespVo.class));
	}
	
	public RespVo<?> update(Long id, UpdateProjectReqVo projectReqVo){
		Project query = new Project();
		query.setId(id);
		query.setDeleted(DelFlag.NOTDEL.getCode());
		query.setUserId(HttpContextUtils.getUserId());
		if(projectMapper.selectCount(query) == 0) {
			return RespVo.failure(RespCode.UNAUTHORIZED_OPERATION);
		}
		Project project = ModelUtils.convert(projectReqVo, Project.class);
		project.setId(id);
		int row = projectMapper.updateSelective(project);
		if(row > 0) {
			return RespVo.success();	
		}else {
			return RespVo.failure(RespCode.UPDATE_FAILURE);
		}
	}
	
	public RespVo<ListVo<GetProjectRespVo>> listOfPage(Long userId, int page, int size){
		if(! userId.equals(HttpContextUtils.getUserId())) {
			return RespVo.failure(RespCode.UNAUTHORIZED_OPERATION);
		}
		Page<Project> pages = PageHelper.startPage(page, size, "create_time desc");
		pages.setPageSizeZero(size == 0);
		Project query = new Project();
		query.setUserId(HttpContextUtils.getUserId());
		query.setDeleted(DelFlag.NOTDEL.getCode());
		projectMapper.selectList(query);
		return RespVo.success(ListVo.list(ModelUtils.convert(pages.getResult(), GetProjectRespVo.class), pages.getTotal()));
	}
}
