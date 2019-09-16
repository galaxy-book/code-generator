package org.nico.codegenerator.model.po;

import java.util.Date;

import org.nico.ourbatis.annotation.RenderName;
import org.nico.ourbatis.annotation.RenderPrimary;

@RenderName("model")
public class Model {

	@RenderPrimary
    private Long id;
	
	private Long projectId;
	
	private Long userId;
    
    private String name;
    
    private String content;
    
    private String comment;
    
    private Integer cacheEnable;
    
    private Integer deleted;
    
    private Date createTime;
    
    private Date updateTime;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getCacheEnable() {
		return cacheEnable;
	}

	public void setCacheEnable(Integer cacheEnable) {
		this.cacheEnable = cacheEnable;
	}
    
    
}
