package org.nico.codegenerator.model.po;

import java.util.Date;

import org.nico.ourbatis.annotation.RenderName;
import org.nico.ourbatis.annotation.RenderPrimary;

@RenderName("project")
public class Project {

	@RenderPrimary
    private Long id;
    
    private String name;
    
    private Long userId;
    
    private String desc;
    
    private String properties;
    
    private Integer deleted;
    
    private Date createTime;
    
    private Date updateTime;

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public String getProperties() {
		return properties;
	}

	public void setProperties(String properties) {
		this.properties = properties;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
    
    
}
