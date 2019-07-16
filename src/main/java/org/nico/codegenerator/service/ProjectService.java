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
		projectMapper.insertSelective(project);
		return RespVo.success(ModelUtils.convert(project, CreateProjectRespVo.class));
	}
	
	public RespVo<?> delete(Long id){
		Project query = new Project();
		query.setId(id);
		query.setDeleted(DelFlag.NOTDEL.getCode());
		query.setUserId(HttpContextUtils.getUserId());
		if(projectMapper.selectCount(query) == 0) {
			return RespVo.failure(RespCode.UNAUTHORIZED_OPERATION);
		}
		Project project = new Project();
		project.setId(id);
		project.setDeleted(DelFlag.DELED.getCode());
		projectMapper.updateSelective(project);
		return RespVo.success();
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
		projectMapper.updateSelective(project);
		return RespVo.success();
	}
	
	public RespVo<ListVo<GetProjectRespVo>> listOfPage(int page, int size){
		Page<Project> pages = PageHelper.startPage(page, size, "create_time desc");
		Project query = new Project();
		query.setUserId(HttpContextUtils.getUserId());
		query.setDeleted(DelFlag.NOTDEL.getCode());
		projectMapper.selectList(query);
		return RespVo.success(ListVo.list(ModelUtils.convert(pages.getResult(), GetProjectRespVo.class), pages.getTotal()));
	}
}
