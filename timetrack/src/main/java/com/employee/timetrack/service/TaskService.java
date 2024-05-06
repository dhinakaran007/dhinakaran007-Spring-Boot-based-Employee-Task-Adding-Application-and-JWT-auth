package com.employee.timetrack.service;

import java.sql.Date;
import java.util.List;

import com.employee.timetrack.bean.Project;
import com.employee.timetrack.bean.Task;
import org.springframework.stereotype.Service;

public interface TaskService {
	public Task saveTask(Task task);
	public Task editTask(Task task);
	public Task updateTask(Task task);
	public void deleteTask(long taskId);
	public List<Task> getTasksByAssociateId(long associateId);
	public List<Task> getTaskList();
	public Task getTaskById(long taskId);
	public List<Project> getListofAllProjects();
	public Date getTaskDate(long taskId);
	//public List<Task> getTasksByAssociateIdAndDate(long associateId, Date taskDate);
}
