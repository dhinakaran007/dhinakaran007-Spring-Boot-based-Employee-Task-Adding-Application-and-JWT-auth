package com.employee.timetrack.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.employee.timetrack.bean.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
