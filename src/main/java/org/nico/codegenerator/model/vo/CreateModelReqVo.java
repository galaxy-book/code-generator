package org.nico.codegenerator.model.vo;

public class CreateModelReqVo {
	
	private Long projectId;

	private String datas;
	
	private String datasType;
	
	public String getDatasType() {
		return datasType;
	}

	public void setDatasType(String datasType) {
		this.datasType = datasType;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getDatas() {
		return datas;
	}

	public void setDatas(String datas) {
		this.datas = datas;
	}
	
}
