package org.nico.codegenerator.model.po;

import java.util.Date;

import org.nico.ourbatis.annotation.RenderName;
import org.nico.ourbatis.annotation.RenderPrimary;

@RenderName("user")
public class User {

    @RenderPrimary
    private Long id;
    
    private String username;
    
    private String password;
    
    private String nickname;
    
    private Integer deleted;
    
    private Date createTime;
    
    private Date updateTime;

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

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
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
