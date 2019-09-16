package org.nico.codegenerator.model.po;

import java.util.Date;

import org.nico.ourbatis.annotation.RenderName;
import org.nico.ourbatis.annotation.RenderPrimary;

@RenderName("model")
public class ModelRelation {

	@RenderPrimary
    private Long id;
	
	private Long projectId;
	
	private Long userId;
    
    private String modelLeft;
    
    private String modelRight;
    
    private String on;
    
    private String hard;
    
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getModelLeft() {
		return modelLeft;
	}

	public void setModelLeft(String modelLeft) {
		this.modelLeft = modelLeft;
	}

	public String getModelRight() {
		return modelRight;
	}

	public void setModelRight(String modelRight) {
		this.modelRight = modelRight;
	}

	public String getOn() {
		return on;
	}

	public void setOn(String on) {
		this.on = on;
	}

	public String getHard() {
		return hard;
	}

	public void setHard(String hard) {
		this.hard = hard;
	}
    
}
