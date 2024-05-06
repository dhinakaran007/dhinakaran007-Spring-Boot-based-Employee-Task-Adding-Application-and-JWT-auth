package com.employee.timetrack.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;



@Entity
public class Project {
	@Id
	@Column(name = "project_id")
	private long projectId;
	@Column(name = "project_name")
	private String projectName;
	@Column(name = "project_desc")
	private String desc;
	
	public Project() {
		
	}

	public Project(long projectId, String projectName, String desc) {
		super();
		this.projectId = projectId;
		this.projectName = projectName;
		this.desc = desc;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "Project [projectId=" + projectId + ", projectName=" + projectName + ", desc=" + desc + "]";
	}
	
	
}
