package com.employee.timetrack.repo;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.employee.timetrack.bean.Project;
import com.employee.timetrack.bean.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	public List<Task> getTasksByAssociateId(long associateId);
	public Task findByTaskId(long taskId);
	public Time getTasksByAssociateIdAndTaskDate(long associateId, Date taskDate);
	public long getTaskByTaskId(long taskId);

	// Additional methods for fetching tasks based on different criteria
	List<Task> findByTaskDate(Date taskDate);
	List<Task> findByTaskDateBetween(Date startDate, Date endDate);
	List<Task> findByTaskDateBetweenAndAssociateId(Date startDate, Date endDate, long associateId);
}