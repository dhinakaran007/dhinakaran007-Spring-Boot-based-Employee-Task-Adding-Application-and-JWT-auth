package com.employee.timetrack.bean;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "report")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "associate_id", referencedColumnName = "associate_id")
	private Associate associate;

	@ManyToOne
	@JoinColumn(name = "task_id", referencedColumnName = "task_id")
	private Task task;

	// Constructors
	public Report() {
	}

	public Report(Associate associate, Task task) {
		this.associate = associate;
		this.task = task;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Associate getAssociate() {
		return associate;
	}

	public void setAssociate(Associate associate) {
		this.associate = associate;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "Report{" +
				"id=" + id +
				", associate=" + associate +
				", task=" + task +
				'}';
	}
}
