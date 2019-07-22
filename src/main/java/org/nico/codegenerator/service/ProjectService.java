package org.nico.codegenerator.service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.nico.codegenerator.consts.DelFlag;
import org.nico.codegenerator.consts.RespCode;
import org.nico.codegenerator.mapper.ProjectMapper;
import org.nico.codegenerator.mapper.TemplateMapper;
import org.nico.codegenerator.model.po.Project;
import org.nico.codegenerator.model.po.Template;
import org.nico.codegenerator.model.vo.CreateProjectReqVo;
import org.nico.codegenerator.model.vo.CreateProjectRespVo;
import org.nico.codegenerator.model.vo.GenerateProjectReqVo;
import org.nico.codegenerator.model.vo.GetProjectRespVo;
import org.nico.codegenerator.model.vo.ListVo;
import org.nico.codegenerator.model.vo.RespVo;
import org.nico.codegenerator.model.vo.UpdateProjectReqVo;
import org.nico.codegenerator.parser.AbstractParser;
import org.nico.codegenerator.parser.entity.Data;
import org.nico.codegenerator.render.TemplateRender;
import org.nico.codegenerator.utils.FileUtils;
import org.nico.codegenerator.utils.HttpContextUtils;
import org.nico.codegenerator.utils.ModelUtils;
import org.nico.codegenerator.utils.ZipUtils;
import org.nico.codegenerator.utils.ZipUtils.ZipEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ProjectService {

	@Autowired
	private ProjectMapper projectMapper;
	
	@Autowired
	private TemplateMapper templateMapper;

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
	
	@SuppressWarnings("unchecked")
	public RespVo<?> generate(Long projectId, GenerateProjectReqVo generateReqVo, HttpServletResponse response){
		Project query = new Project();
		query.setId(projectId);
		query.setDeleted(DelFlag.NOTDEL.getCode());
		query.setUserId(HttpContextUtils.getUserId());
		Project project = projectMapper.selectEntity(query);
		if(project == null) {
			return RespVo.failure(RespCode.PROJECT_NOT_EXIST);
		}
		Template templateQuery = new Template();
		templateQuery.setProjectId(projectId);
		templateQuery.setDeleted(DelFlag.NOTDEL.getCode());
		List<Template> templates = templateMapper.selectList(templateQuery);
		
		if(CollectionUtils.isEmpty(templates)) {
			return RespVo.failure(RespCode.PROJECT_TEMPLATE_NOT_CONFIG);
		}
		
		try {
			List<Data> datas = AbstractParser.getParser(generateReqVo.getDatasType()).parse(generateReqVo.getDatas());
			String templateProperties = "";
			if(StringUtils.isNotBlank(project.getProperties())) {
				templateProperties = project.getProperties();
			}
			List<ZipEntity> zesBatch = new ArrayList<ZipEntity>();
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("datas", datas);
			
			for(Data data: datas) {
				map.put("data", data);
				
				List<ZipEntity> zes = new ArrayList<ZipEntity>();
				for(Template template: templates) {
					String source = TemplateRender.getInstance().rending(templateProperties + template.getContent(), map);
					String name = TemplateRender.getInstance().rending(templateProperties + template.getName(), map);
					
					zes.add(new ZipEntity(source.getBytes(), FileUtils.joint(name, template.getSuffix())));
				}
				byte[] buf = ZipUtils.compress(zes);
				zesBatch.add(new ZipEntity(buf, FileUtils.joint(data.getName(), "zip")));
			}
			
			byte[] zipDatas = null;
			String zipName = null;
			
			if(zesBatch.size() == 1) {
				ZipEntity target = zesBatch.get(0);
				zipDatas = target.getData();
				zipName = target.getFileName();
			}else {
				zipDatas = ZipUtils.compress(zesBatch);
				zipName = FileUtils.randomFileName("zip");
			}
			
			response.setContentType("application/octet-stream");
			response.addHeader("Content-Disposition", "attachment;filename=" + zipName + ";");
			response.addHeader("Content-Type", "application/octet-stream");
			response.addHeader("Content-Length", String.valueOf(zipDatas.length));
			response.addHeader("Access-Control-Expose-Headers", "FileName");
			response.addHeader("FileName", zipName);
			
			ByteArrayInputStream inputStream = new ByteArrayInputStream(zipDatas);
			int len = -1;
			byte[] bs = new byte[2048];
			
			while((len = inputStream.read(bs)) != -1) {
				response.getOutputStream().write(bs, 0, len);
			}
			response.getOutputStream().flush();
		} catch (Exception e) {
			return RespVo.failure(RespCode.GENERATE_ERROR, e.getMessage());
		}
		
		return null;
	}
}
