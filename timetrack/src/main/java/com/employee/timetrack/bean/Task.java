package com.employee.timetrack.bean;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Task {
	
	public Task() {
		this.starTime = new Time(System.currentTimeMillis());
	    this.endTime = new Time(System.currentTimeMillis());
	}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "task_id")
	private long taskId;
	@Column(name = "task_name")
	private String taskName;
	@Column(name = "task_date")
	private Date taskDate;
	@Column(name = "Start_time")
	private Time starTime;
	@Column(name = "end_time")
	private Time endTime;
	@Column(name = "associate_id")
	private long associateId;
	@Column(name = "project_id")
	private long projectId;
	@Column(name = "description")
	private String description;
	
	public Task(long taskId, String taskName, Date taskDate, Time starTime, Time endTime, long associateId,
			long projectId, String description) {
		super();
		this.taskId = taskId;
		this.taskName = taskName;
		this.taskDate = taskDate;
		this.starTime = starTime;
		this.endTime = endTime;
		this.associateId = associateId;
		this.projectId = projectId;
		this.description = description;
	}

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getTaskDate() {
		return taskDate;
	}

	public void setTaskDate(Date taskDate) {
		this.taskDate = taskDate;
	}

	public Time getStarTime() {
		return starTime;
	}

	public void setStarTime(Time starTime) {
		this.starTime = starTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public long getAssociateId() {
		return associateId;
	}

	public void setAssociateId(long associateId) {
		this.associateId = associateId;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", taskName=" + taskName + ", taskDate=" + taskDate + ", starTime=" + starTime
				+ ", endTime=" + endTime + ", associateId=" + associateId + ", projectId=" + projectId
				+ ", description=" + description + "]";
	}
	
	
	
}
